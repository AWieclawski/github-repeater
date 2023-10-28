package edu.awieclawski.configs;

import edu.awieclawski.builders.RestExceptionsBuilder;
import edu.awieclawski.dtos.ExceptionDto;
import edu.awieclawski.api.repeater.controllers.GitHubController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Locale;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice(assignableTypes = GitHubController.class)
@ResponseBody
@RequiredArgsConstructor
public class RestResponseEntityExceptionHandler {

    @Value("${base.error.msg}")
    private String baseErrorMsg;

    private final RestExceptionsBuilder responseBuilder;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> onException(Exception exception, Locale locale) {
        log.warn("{}: {}", baseErrorMsg, getCause(exception));
        return responseBuilder.build(exception, HttpStatus.INTERNAL_SERVER_ERROR, locale);
    }

    private String getCause(Throwable ex) {
        if (ex != null) {
            return ex.getMessage() != null ? ex.getMessage() : ex.getClass().getSimpleName();
        }
        return "Unrecognized issue!";
    }
}
