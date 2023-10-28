package edu.awieclawski.localtime.services.impl;

import edu.awieclawski.localtime.services.TimeRetryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Slf4j
@RequiredArgsConstructor
public class TimeRetryServiceImpl implements TimeRetryService {

    private final SystemTimeProvider systemTimeProvider;

    private final RemoteTimeProvider remoteTimeProvider;

    @Override
    public Instant getRegionalTime() {
        return remoteTimeProvider.getInstant();
    }

    @Override
    public Instant getSystemTime() {
        log.warn("Time provided from The server Operating System. Probably remote time source is not available.");
        return systemTimeProvider.getInstant();
    }

}
