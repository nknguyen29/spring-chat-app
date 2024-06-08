package fr.utc.sr03.chatapp.controller;

import org.springframework.mail.javamail.JavaMailSender;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
 
@RestController
public class ForgotPasswordController {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.properties.mail.smtp.from}")
    private String sender;

    // @PostMapping("/forgot_password")
    // public String processForgotPassword(HttpServletRequest request, Model model) {
    //     String email = request.getParameter("email");
    //     String token = RandomString.make(30);
        
    //     try {
    //         customerService.updateResetPasswordToken(token, email);
    //         String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
    //         sendEmail(email, resetPasswordLink);
    //         model.addAttribute("message", "We have sent a reset password link to your email. Please check.");
            
    //     } catch (CustomerNotFoundException ex) {
    //         model.addAttribute("error", ex.getMessage());
    //     } catch (UnsupportedEncodingException | MessagingException e) {
    //         model.addAttribute("error", "Error while sending email");
    //     }
            
    //     return "forgot_password_form";
    // }

    @Value("${spring.mail.username}")
    private String fromMail;

    @GetMapping("/mail")
    public String sendEmail() throws MessagingException {
        System.out.println("Sending email...");

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        mimeMessageHelper.setFrom("adhavane.moudougannane@etu.utc.fr");
        mimeMessageHelper.setTo("adhavane.moudougannane@etu.utc.fr");
        mimeMessageHelper.setSubject("Test email");

        Context context = new Context();
        context.setVariable("content", "This is a test email");
        String processedString = templateEngine.process("mail/test", context);
        mimeMessageHelper.setText(processedString, true);

        // String subject = "Here's the link to reset your password";
        // String content = "<p>Hello,</p>"
        //         + "<p>You have requested to reset your password.</p>"
        //         + "<p>Click the link below to change your password:</p>"
        //         + "<p><a href=\"" + "link" + "\">Change my password</a></p>"
        //         + "<br>"
        //         + "<p>Ignore this email if you do remember your password, "
        //         + "or you have not made the request.</p>";
        
        // helper.setSubject(subject);
        // helper.setText(content, true);
        
        mailSender.send(mimeMessage);

        return "Email has been sent";
    }  
     
}
