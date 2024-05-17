package fr.utc.sr03.chatapp.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.util.UriComponentsBuilder;

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

    /**
     * Add the request query string to the model.
     * 
     * @param request the HTTP request
     * @return the request query string
     */
    @ModelAttribute("requestQueryString")
    public String getRequestQueryString(final HttpServletRequest request) {
        return request.getQueryString();
    }

    @ModelAttribute("requestUriWithQueryString")
    public String getRequestUriWithQueryString(final HttpServletRequest request) {
        return request.getRequestURI() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""); 
    }

    @ModelAttribute("requestUrl")
    public String getRequestUrl(final HttpServletRequest request) {
        return request.getRequestURL().toString();
    }

    @ModelAttribute("requestUrlWithQueryString")
    public String getRequestUrlWithQueryString(final HttpServletRequest request) {
        return request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""); 
    }

    /**
     * Add the URI components builder to the model.
     * 
     * @param request the HTTP request
     * @return the URI components builder
     * @see https://stackoverflow.com/q/45594238
     */
    @ModelAttribute("uriComponentsBuilder")
    public UriComponentsBuilder getUriComponentsBuilder(final HttpServletRequest request) {
        final String requestUrlWithQueryString = request.getRequestURL().toString() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
        return UriComponentsBuilder.fromUriString(requestUrlWithQueryString);
    }

    @ModelAttribute("isDevserver")
    public Boolean getIsDevserver(final HttpServletRequest request) {
        return "1".equals(request.getHeader("X-Devserver"));
    }

}
