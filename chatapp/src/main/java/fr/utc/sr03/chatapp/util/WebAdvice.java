package fr.utc.sr03.chatapp.util;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.servlet.http.HttpServletRequest;


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
        return request.getRequestURL().toString()
                + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
    }

    /**
     * Add the URI components builder to the model.
     * 
     * @param request the HTTP request
     * @return the URI components builder
     * @see https://stackoverflow.com/q/45594238
     * @see https://stackoverflow.com/a/44160941
     */
    @ModelAttribute("uriComponentsBuilder")
    public UriComponentsBuilder getUriComponentsBuilder(final HttpServletRequest request) {
        final String requestUriWithQueryString = getRequestUriWithQueryString(request);
        return UriComponentsBuilder.fromUriString(requestUriWithQueryString);
    }

    @ModelAttribute("isDevserver")
    public Boolean getIsDevserver(final HttpServletRequest request) {
        return "1".equals(request.getHeader("X-Devserver"));
    }

}
