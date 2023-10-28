package edu.awieclawski.localtime.services.impl;

import edu.awieclawski.ExceptionHandlerApp;
import edu.awieclawski.localtime.exceptions.ServiceUnavailableException;
import edu.awieclawski.localtime.services.TimeRetryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Instant;

import static org.mockito.Mockito.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ExceptionHandlerApp.class)
class TimeRetryServiceImplTest {

    @MockBean
    private SystemTimeProvider systemTimeProvider;

    @MockBean
    private RemoteTimeProvider remoteTimeProvider;

    @Autowired
    private TimeRetryService service;

    @Test
    void execute_remoteTimeProvider_atMost_maxAttempts() {
        // given -> must be maxAttempts value in '@Retryable' annotation at method in TimeRetryService
        final int retryTrials = 2;

        // when
        when(remoteTimeProvider.getInstant()).thenThrow(ServiceUnavailableException.class);

        // then
        service.getRegionalTime();
        verify(remoteTimeProvider, atMost(retryTrials)).getInstant();
    }

    @Test
    void execute_systemTimeProvider_only_once_if_remoteTimeProvider_throw_exception() {
        // when
        when(remoteTimeProvider.getInstant()).thenThrow(ServiceUnavailableException.class);

        // then
        service.getRegionalTime();
        verify(systemTimeProvider, atMostOnce()).getInstant();
    }

    @Test
    void execute_systemTimeProvider_never_if_remoteTimeProvider_OK() {
        // when
        when(remoteTimeProvider.getInstant()).thenReturn(Instant.now());

        // then
        service.getRegionalTime();
        verify(systemTimeProvider, never()).getInstant();
    }
}