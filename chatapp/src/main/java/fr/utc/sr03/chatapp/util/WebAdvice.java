package fr.utc.sr03.chatapp.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Provide attributes available in all templates.
 */
@ControllerAdvice
public class WebAdvice {

    /**
     * Add the request URI to the model.
     * 
     * @param request the HTTP request
     * @return the request URI
     * @see https://stackoverflow.com/a/75241636
     */
    @ModelAttribute("requestUri")
    public String getRequestUri(final HttpServletRequest request) {
        return request.getRequestURI();
    }

    @ModelAttribute("isDevserver")
    public Boolean getIsDevserver(final HttpServletRequest request) {
        return "1".equals(request.getHeader("X-Devserver"));
    }

}
