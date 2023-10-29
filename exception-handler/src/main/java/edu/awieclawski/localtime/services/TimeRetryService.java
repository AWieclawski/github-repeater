package edu.awieclawski.localtime.services;


import edu.awieclawski.localtime.exceptions.ServiceUnavailableException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

import java.time.Instant;

public interface TimeRetryService {

    @Retryable(value = ServiceUnavailableException.class,
            maxAttempts = 2, // standard value = 3
            backoff = @Backoff(delay = 1000))
    Instant getRegionalTime();

    @Recover
    Instant getSystemTime();

}
