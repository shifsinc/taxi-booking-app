package com.shriram.microservices.exception;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@ControllerAdvice
public class GlobalExceptionHandler {

    Logger logger = Logger.getLogger(GlobalExceptionHandler.class);

    /**
     * Exception handler when query params are missing
     * <p>
     * This method handles the MissingServletRequestParameterException when any query parameter is missing and throws 400 error with
     * {message: "Reason for exception"}
     *
     * @param exception
     *            - The concerned exception
     * @param request
     *            - HttpServletRequest
     * @param response
     *            - HttpServletResponse
     * @return error message
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleParamExceptions(Exception exception) {
        logger.debug(exception.getMessage());
        return exceptionsWithMessages(exception.getMessage());
    }

    /**
     * Exception handler to catch HttpMessageNotReadableException when request receives a bad JSON
     **/
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object handleBadParamsException(Exception exception) {
        logger.debug(exception.getMessage());
        return exceptionsWithMessages("Malformed request");
    }

    /**
     * Exception handler to catch HttpMediaTypeNotSupportedException when a system error occurs
     **/
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object handleHttpMediaTypeNotSupportedException(Exception exception) {
        logger.error(ExceptionUtils.getStackTrace(exception));
        return exceptionsWithMessages(exception.getMessage());
    }

    /**
     * Exception handler to catch HttpRequestMethodNotSupportedException when a incorrect HTTP request method
     **/
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object handleHttpRequestMethodNotSupportedException(Exception exception) {
        logger.error(ExceptionUtils.getStackTrace(exception));
        return exceptionsWithMessages(exception.getMessage());
    }

    /**
     * Exception handler to catch all left out Exceptions
     **/
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleAllExceptions(Exception exception) {
        logger.error(ExceptionUtils.getStackTrace(exception));
        return exceptionsWithMessages("Something went wrong in the servers");
    }

    public ModelAndView exceptionsWithMessages(String errorMessage) {
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        Map<String, String> responseContent = new HashMap<String, String>();
        logger.error("Exception caught: " + errorMessage);
        if (StringUtils.isEmpty(errorMessage))
            errorMessage = "Something went wrong in the servers";
        responseContent.put("message", errorMessage);
        return new ModelAndView(jsonView, responseContent);
    }

}
