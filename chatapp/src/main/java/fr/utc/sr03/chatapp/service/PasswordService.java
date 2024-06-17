package fr.utc.sr03.chatapp.service;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Base64;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import fr.utc.sr03.chatapp.domain.Token;
import fr.utc.sr03.chatapp.domain.User;
import fr.utc.sr03.chatapp.repos.TokenRepository;
import fr.utc.sr03.chatapp.repos.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;

import java.sql.Timestamp;

import java.time.Instant;


@Service
@Transactional
public class PasswordService {

    private static final Duration RESET_PASSWORD_TOKEN_VALIDITY = Duration.ofMinutes(15);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.properties.mail.smtp.from}")
    private String sender;

    @Value("${spring.mail.username}")
    private String fromMail;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final SecureRandom secureRandom = new SecureRandom(); // Threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); // Threadsafe

    public String generateResetPasswordToken(final String email) {
        // Generate random 24-byte token
        // See here: https://stackoverflow.com/a/56628391
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    public Token createResetPasswordToken(final String token, final User user) {
        final Instant now = Instant.now();

        final Token resetToken = new Token();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setType("reset_password");
        resetToken.setCreatedAt(Timestamp.from(now));
        resetToken.setExpiresAt(Timestamp.from(now.plusMillis(RESET_PASSWORD_TOKEN_VALIDITY.toMillis())));
        return resetToken;
    }

    public void updateResetPasswordToken(final String token, final String email) throws UsernameNotFoundException {
        final User user = userRepository.findByEmail(email);
        if (user != null) {
            // Reset all previous reset password tokens
            final List<Token> resetTokens = tokenRepository.findByUserAndType(user, "reset_password");
            resetTokens.forEach(resetToken -> {
                if (resetToken.getExpiresAt().after(Timestamp.from(Instant.now()))) {
                    resetToken.setExpiresAt(Timestamp.from(Instant.now()));
                    tokenRepository.save(resetToken);
                }
            });
            final Token resetToken = createResetPasswordToken(token, user);
            tokenRepository.save(resetToken);
        } else {
            throw new UsernameNotFoundException("No user found with email: " + email);
        }
    }

    public User getByResetPasswordToken(final String token) {
        final Token resetToken = tokenRepository.findByTokenAndType(token, "reset_password");
        if (resetToken != null) {
            return resetToken.getUser();
        }
        return null;
    }

    // public void updatePassword(Customer customer, String newPassword) {
    //     customer.setPassword(passwordEncoder.encode(newPassword));
    //     customer.setResetPasswordToken(null);
    //     customerRepo.save(customer);
    // }
       
    
    public void sendResetPasswordEmail(final String recipientEmail, final String link)
        throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();              
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromMail, sender);
        helper.setTo(recipientEmail);

        // Context context = new Context();
        // context.setVariable("content", "This is a test email");
        // String processedString = templateEngine.process("mail/test", context);
        // mimeMessageHelper.setText(processedString, true);

        final String subject = "Here's the link to reset your password";
        
        final String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";
        
        helper.setSubject(subject);
        
        helper.setText(content, true);
        
        mailSender.send(message);
    }

}
