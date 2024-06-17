package fr.utc.sr03.chatapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import fr.utc.sr03.chatapp.domain.User;
import fr.utc.sr03.chatapp.repos.TokenRepository;
import fr.utc.sr03.chatapp.repos.UserRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class PasswordService {
    
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
    private final PasswordEncoder passwordEncoder;

    public void updateResetPasswordToken(String token, String email) throws UsernameNotFoundException {
        final User user = userRepository.findByEmail(email);
        if (user != null) {
            final String tokenValue = token;

            user.setResetPasswordToken(token);
            userRepository.save(user);
        } else {
            throw new UsernameNotFoundException("Could not find any user with the email " + email);
        }
    }

    public Customer getByResetPasswordToken(String token) {
        return customerRepo.findByResetPasswordToken(token);
    }

    public void updatePassword(Customer customer, String newPassword) {
        customer.setPassword(passwordEncoder.encode(newPassword));
        customer.setResetPasswordToken(null);
        customerRepo.save(customer);
    }
       
    
    public void sendEmail(String recipientEmail, String link)
        throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();              
        MimeMessageHelper helper = new MimeMessageHelper(message);
        
        helper.setFrom("contact@shopme.com", "Shopme Support");
        helper.setTo(recipientEmail);
        
        String subject = "Here's the link to reset your password";
        
        String content = "<p>Hello,</p>"
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
