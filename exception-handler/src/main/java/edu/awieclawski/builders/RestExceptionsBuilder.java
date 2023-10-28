package edu.awieclawski.builders;


import edu.awieclawski.dtos.ExceptionDto;
import edu.awieclawski.localtime.services.impl.RemoteTimeProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestExceptionsBuilder {

    private final MessageSource messageSource;
    private final RemoteTimeProvider timeProvider;

    @Value("${httpError.codeFieldName}")
    private String errorCodeFieldName;

    public ResponseEntity<ExceptionDto> build(Exception exception, HttpStatus status, Locale locale) {
        var httpInnerStatus = getHttpErrorCodeField(exception, errorCodeFieldName);
        return ResponseEntity.status(status)
                .body(new ExceptionDto(httpInnerStatus != null ? httpInnerStatus : status.value(),
                        timeProvider.getInstant(),
                        getDescription(exception, locale, exception.getMessage())));
    }


    public String getDescription(Exception exception, Locale locale, String... parameters) {
        var key = toKey(exception);
        String description;
        try {
            description = messageSource.getMessage(key, parameters, locale);
        } catch (NoSuchMessageException noSuchMessageException) {
            description = key;
        }
        return description;
    }

    private String toKey(Exception exception) {
        return exception.getClass().getSimpleName();
    }

    private Integer getHttpErrorCodeField(Exception exception, String fieldName) {
        Integer result = null;
        if (!StringUtils.isEmpty(fieldName)) {
            try {
                result = (Integer) FieldUtils.getField(exception.getClass(), fieldName, true).get(exception);
            } catch (IllegalAccessException e) {
                log.warn("No Integer field {} found in class {}", fieldName, exception.getClass().getName());
            }
        }
        return result;
    }
}
