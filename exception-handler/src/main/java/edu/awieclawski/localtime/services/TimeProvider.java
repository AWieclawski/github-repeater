package edu.awieclawski.localtime.services;

import java.time.Instant;

public interface TimeProvider {

    Instant getInstant();

}
