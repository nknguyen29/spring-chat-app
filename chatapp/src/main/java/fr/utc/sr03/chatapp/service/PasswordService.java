package fr.utc.sr03.chatapp.service;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
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


@Service
@Transactional
public class PasswordService {

    private static final Duration RESET_PASSWORD_TOKEN_VALIDITY = Duration.ofMinutes(15);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String sender;

    @Value("${spring.mail.properties.mail.smtp.from}")
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
        resetToken.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        resetToken.setExpiresAt(Timestamp.from(now.plus(RESET_PASSWORD_TOKEN_VALIDITY)));
        return resetToken;
    }

    public void resetAllResetPasswordTokens(final User user) {
        final List<Token> resetTokens = tokenRepository.findByUserAndType(user, "reset_password");
        resetTokens.forEach(resetToken -> {
            if (resetToken.getExpiresAt().after(Timestamp.from(Instant.now()))) {
                resetToken.setExpiresAt(Timestamp.from(Instant.now()));
                tokenRepository.save(resetToken);
            }
        });
    }

    public void updateResetPasswordToken(final String token, final String email) throws UsernameNotFoundException {
        final User user = userRepository.findByEmail(email);
        if (user != null) {
            // Reset all previous reset password tokens
            resetAllResetPasswordTokens(user);
            final Token resetToken = createResetPasswordToken(token, user);
            tokenRepository.save(resetToken);
        } else {
            throw new UsernameNotFoundException("No user found with email: " + email);
        }
    }

    public User getByResetPasswordToken(final String token) {
        final Token resetToken = tokenRepository.findByTokenAndType(token, "reset_password");
        if (resetToken != null && resetToken.getExpiresAt().after(Timestamp.from(Instant.now()))) {
            return resetToken.getUser();
        }
        return null;
    }

    public void updatePassword(final User user, final String password) {
        user.setPassword(passwordEncoder.encode(password));
        resetAllResetPasswordTokens(user);
        userRepository.save(user);
    }

    public void sendResetPasswordEmail(final String recipientEmail, final String resetPasswordLink)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromMail, sender);
        helper.setTo(recipientEmail);

        final Context context = new Context();
        context.setVariable("resetPasswordLink", resetPasswordLink);
        final String processedString = templateEngine.process("mail/reset_password_email", context);
        helper.setText(processedString, true);

        final String subject = "[Diskette] Réinitialisation de mot de passe";

        helper.setSubject(subject);

        helper.setText(processedString, true);

        mailSender.send(message);
    }

}
