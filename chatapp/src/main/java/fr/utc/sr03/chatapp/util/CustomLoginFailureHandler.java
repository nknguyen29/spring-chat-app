package fr.utc.sr03.chatapp.util;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import fr.utc.sr03.chatapp.model.HttpUserDetails;
import fr.utc.sr03.chatapp.service.AuthenticationService;
import fr.utc.sr03.chatapp.service.HttpUserDetailsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private HttpUserDetailsService httpUserService;

    @Autowired
    private AuthenticationService authService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        final String email = request.getParameter("email");
        final HttpUserDetails httpUserDetails = httpUserService.findUserByEmail(email);

        if (httpUserDetails != null) {
            if (httpUserDetails.isEnabled() && httpUserDetails.isAccountNonLocked()) {
                if (httpUserDetails.getFailedConnectionAttempts() < AuthenticationService.MAX_FAILED_ATTEMPTS - 1) {
                    authService.increaseFailedAttempts(httpUserDetails);
                    exception = new LockedException("Email ou mot de passe incorrect.");
                } else {
                    authService.lock(httpUserDetails);
                    exception = new LockedException(
                            "Votre compte a été bloqué suite à de trop nombreuses tentatives de connexion infructueuses. Veuillez réessayer plus tard.");
                }
            } else if (!httpUserDetails.isAccountNonLocked()) {
                if (authService.unlockWhenTimeExpired(httpUserDetails)) {
                    exception = new LockedException("Votre compte a été débloqué. Veuillez réessayer.");
                }
                exception = new LockedException(
                        "Votre compte est bloqué. Veuillez réessayer plus tard ou contactez un administrateur.");
            }
        } else {
            exception = new LockedException("Email ou mot de passe incorrect.");
        }

        super.setDefaultFailureUrl("/login?login_error");
        super.onAuthenticationFailure(request, response, exception);
    }

}
