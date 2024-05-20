package fr.utc.sr03.chatapp.util;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import fr.utc.sr03.chatapp.domain.User;
import fr.utc.sr03.chatapp.service.UserServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import fr.utc.sr03.chatapp.model.HttpUserDetails;
 
@Component
public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
 
    @Autowired
    private UserServices userService;
     
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        HttpUserDetails userDetails =  (HttpUserDetails) authentication.getPrincipal();
        // User user = userDetails.getUser();
        
        if (userDetails.getFailedConnectionAttempts() > 0) {
        // if (user.getFailedAttempt() > 0) {
            userService.resetFailedAttempts(userDetails.getEmail());
        }
         
        super.onAuthenticationSuccess(request, response, authentication);
    }
     
}