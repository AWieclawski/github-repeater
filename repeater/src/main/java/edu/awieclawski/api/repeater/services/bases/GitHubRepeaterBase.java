package edu.awieclawski.api.repeater.services.bases;

import edu.awieclawski.api.repeater.dtos.bases.BaseDto;
import edu.awieclawski.api.repeater.enums.RepositoryErrorStatus;
import edu.awieclawski.api.repeater.services.GitHubRepeaterService;
import edu.awieclawski.exceptions.GitHubErrorResponseException;
import edu.awieclawski.exceptions.GitHubIntegrationException;
import edu.awieclawski.localtime.services.TimeRetryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Duration;
import java.util.function.Function;

/**
 * The base GitHub repeater handler available in the package only.
 * Contains basic logic of errors handling and runtime context.
 * Require access to `edu.awieclawski.exception-handler` packages.
 */
@Slf4j
@RequiredArgsConstructor
abstract class GitHubRepeaterBase implements GitHubRepeaterService {

    protected final TimeRetryService timeProvider;

    private static final String ISSUE_MSG = "Integration issue!";

    public <T extends BaseDto> T callClient(String[] pair, Function<String[], T> function) {
        T result;
        StopWatch watch = new StopWatch();
        try {
            watch.start();
            result = function.apply(pair);
        } catch (WebClientResponseException wcre) {
            log.warn("Received GitHub error response {}", wcre.getMessage());
            int statusCode = wcre.getStatusCode().value();
            RepositoryErrorStatus status = RepositoryErrorStatus.getByStatus(statusCode);
            throw new GitHubErrorResponseException(status.getDescription(), statusCode);
        } catch (Exception e) {
            log.error(ISSUE_MSG, e);
            throw new GitHubIntegrationException(ISSUE_MSG);
        } finally {
            watch.stop();
        }
        result.setTimestamp(timeProvider.getRegionalTime());
        result.setNanoDuration(Duration.ofNanos(watch.getTime()));
        return result;
    }

    protected WebClientResponseException getWebClientResponseException() {
        throw new WebClientResponseException(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                RepositoryErrorStatus.NOT_KNOWN.getDescription(),
                null, null, null);
    }
}



