package edu.awieclawski.localtime.services.impl;

import edu.awieclawski.localtime.controllers.RestTemplateTimeProvider;
import edu.awieclawski.localtime.exceptions.ServiceUnavailableException;
import edu.awieclawski.localtime.mappers.RemoteTimeRestMapper;
import edu.awieclawski.localtime.services.TimeProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Primary
@Service
@RequiredArgsConstructor
public class RemoteTimeProvider implements TimeProvider {
    private final RestTemplateTimeProvider timeProvider;
    private final RemoteTimeRestMapper mapper;

    @Override
    public Instant getInstant() {
        return timeProvider.getTime()
                .map(mapper::toSeconds)
                .orElseThrow(ServiceUnavailableException::new);
    }

}
