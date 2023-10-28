package edu.awieclawski.localtime.services.impl;

import edu.awieclawski.localtime.services.TimeProvider;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class SystemTimeProvider implements TimeProvider {

    @Override
    public Instant getInstant() {
        return Instant.now();
    }

}
