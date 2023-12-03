package com.jobf.finder.common.rest.exception;

import com.jobf.finder.common.exception.FinderAppDataNotFoundException;
import com.jobf.finder.common.rest.dto.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

@RestControllerAdvice
@RequiredArgsConstructor
public class Handler {

    private final MessageSource messageSource;

    @ExceptionHandler(FinderAppDataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleFinderAppDataNotFoundException(FinderAppDataNotFoundException ex, Locale locale) {
        String message = messageSource.getMessage(ex.getKey(), ex.getArgs(), locale);
        List<String> errorData = Pattern.compile(";").splitAsStream(message).toList();
        return new ErrorResponse(errorData.get(0), errorData.get(1));
    }

    //todo:add account already exists
}
