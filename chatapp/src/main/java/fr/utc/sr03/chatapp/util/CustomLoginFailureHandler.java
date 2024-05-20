package fr.utc.sr03.chatapp.util;

import java.io.IOException;
 
// import javax.servlet.ServletException;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import fr.utc.sr03.chatapp.domain.User;
import fr.utc.sr03.chatapp.service.UserServices;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
 
@Component
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
     
    @Autowired
    private UserServices userService;
     
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        String email = request.getParameter("email");
        User user = userService.getByEmail(email);
         
        if (user != null) {
            // if (user.isEnabled() && user.isAccountNonLocked()) {
                if (!user.getIsLocked()) {
                // if (user.getFailedAttempt() < UserServices.MAX_FAILED_ATTEMPTS - 1) {
                if (user.getFailedConnectionAttempts() < UserServices.MAX_FAILED_ATTEMPTS - 1) {
                    userService.increaseFailedAttempts(user);
                } else {
                    userService.lock(user);
                    exception = new LockedException("Your account has been locked due to 3 failed attempts."
                            + " It will be unlocked after 24 hours.");
                }
            // } else if (!user.isAccountNonLocked()) {
            //     if (userService.unlockWhenTimeExpired(user)) {
            //         exception = new LockedException("Your account has been unlocked. Please try to login again.");
            //     }
            }
             
        }
         System.out.println("Login failed");
        super.setDefaultFailureUrl("/login?error");
        super.onAuthenticationFailure(request, response, exception);
    }
 
}