package fr.utc.sr03.chatapp.controller;

import org.springframework.mail.javamail.JavaMailSender;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import fr.utc.sr03.chatapp.domain.User;
import fr.utc.sr03.chatapp.service.PasswordService;
import fr.utc.sr03.chatapp.util.WebUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@RestController
public class ForgotPasswordController {

    @Autowired
    private PasswordService passwordService;

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "security/forgot_password_form";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(final HttpServletRequest request, final Model model) {
        final String email = request.getParameter("email");
        final String token = passwordService.generateResetPasswordToken(email);

        try {
            passwordService.updateResetPasswordToken(token, email);
            final String resetPasswordLink = WebUtils.getSiteURL(request) + "/reset-password?token=" + token;
            passwordService.sendResetPasswordEmail(email, resetPasswordLink);
            model.addAttribute(WebUtils.MSG_SUCCESS, "authentication.forgot.password.success");
        } catch (UsernameNotFoundException ex) {
            model.addAttribute(WebUtils.MSG_ERROR, ex.getMessage());
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute(WebUtils.MSG_ERROR, "authentication.forgot.password.error");
        }

        return "redirect:/login";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        final User user = passwordService.getByResetPasswordToken(token);
        model.addAttribute("token", token);

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        }

        return "security/reset_password_form";
    }

    @PostMapping("/reset-password")
    public String processResetPassword(final HttpServletRequest request, final Model model) {
        final String token = request.getParameter("token");
        final String password = request.getParameter("password");

        final User user = passwordService.getByResetPasswordToken(token);
        model.addAttribute("title", "Reset your password");
        
        if (customer == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        } else {           
            customerService.updatePassword(customer, password);
            
            model.addAttribute("message", "You have successfully changed your password.");
        }
        
        return "message";
    }

}
