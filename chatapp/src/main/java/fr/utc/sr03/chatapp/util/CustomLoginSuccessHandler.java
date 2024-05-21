package fr.utc.sr03.chatapp.util;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import fr.utc.sr03.chatapp.model.HttpUserDetails;
import fr.utc.sr03.chatapp.service.AuthenticationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private AuthenticationService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        HttpUserDetails userDetails = (HttpUserDetails) authentication.getPrincipal();

        if (userDetails.getFailedConnectionAttempts() > 0) {
            userService.resetFailedAttempts(userDetails);
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }

}
