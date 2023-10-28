package edu.awieclawski.localtime.controllers;


import edu.awieclawski.localtime.dtos.TimestampDto;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

/**
 * GET regional time from public api using preconfigured @timeUrl and @timeZone variables
 */
@Component
@RequiredArgsConstructor
public class RestTemplateTimeProvider {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${api.time.url}")
    @Setter
    private String timeUrl;

    @Value("${api.time.zone}")
    @Setter
    private String timeZone;

    public Optional<TimestampDto> getTime() {
        final String timeApi = getTimeApiPath();
        if (isValidURL(timeApi)) {
            return Optional.ofNullable(restTemplate.getForObject(timeApi, TimestampDto.class));
        }
        return Optional.empty();
    }

    private String getTimeApiPath() {
        if (timeUrl != null) {
            return timeUrl.concat("/").concat(timeZone);
        }
        return "";
    }

    private boolean isValidURL(String url) {
        UrlValidator validator = new UrlValidator();
        return validator.isValid(url);
    }

}
