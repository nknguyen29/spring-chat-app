package fr.utc.sr03.chatapp.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import fr.utc.sr03.chatapp.domain.User;
import fr.utc.sr03.chatapp.service.PasswordService;
import fr.utc.sr03.chatapp.util.WebUtils;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;


@Controller
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
            model.addAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("authentication.forgot-password.success"));
        } catch (UsernameNotFoundException ex) {
            model.addAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage("authentication.forgot-password.user-not-found"));
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute(WebUtils.MSG_ERROR, WebUtils.getMessage("authentication.forgot-password.error"));
        }

        return "security/forgot_password_form";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        final User user = passwordService.getByResetPasswordToken(token);
        model.addAttribute("token", token);

        if (user == null) {
            model.addAttribute(WebUtils.MSG_ERROR, WebUtils.getMessage("authentication.reset-password.invalid-token"));
            return "redirect:/login";
        }

        return "security/reset_password_form";
    }

    @PostMapping("/reset-password")
    public String processResetPassword(final HttpServletRequest request, final Model model) {
        final String token = request.getParameter("token");
        final String password = request.getParameter("password");
        final String confirmPassword = request.getParameter("confirmPassword");

        final User user = passwordService.getByResetPasswordToken(token);

        if (!password.equals(confirmPassword)) {
            model.addAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage("authentication.reset-password.password-mismatch"));
            return "security/reset_password_form";
        }
        if (user == null) {
            model.addAttribute(WebUtils.MSG_ERROR, WebUtils.getMessage("authentication.reset-password.invalid-token"));
        } else {
            passwordService.updatePassword(user, password);
            model.addAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("authentication.reset-password.success"));
        }

        return "redirect:/login";
    }

}
