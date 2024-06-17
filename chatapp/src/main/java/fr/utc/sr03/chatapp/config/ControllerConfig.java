package fr.utc.sr03.chatapp.config;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;


/**
 * This class is used to trim all the strings that are sent to the controllers
 * It is used to remove the leading and trailing whitespaces from the strings
 * This is done to ensure that the strings are clean and do not contain any
 * unwanted whitespaces
 */
@ControllerAdvice
public class ControllerConfig {

    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

}
