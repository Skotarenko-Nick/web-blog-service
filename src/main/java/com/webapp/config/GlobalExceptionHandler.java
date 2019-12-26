package com.webapp.config;

import com.webapp.exception.CommonException;
import com.webapp.model.ExceptionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Global exception handler
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ExceptionResponse exception(final Throwable throwable) throws CommonException {

        logger.error("Exception during execution of SpringSecurity application", throwable);

        String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
        return new ExceptionResponse(errorMessage);
    }

}
