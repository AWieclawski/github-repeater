package edu.awieclawski.clients.github.base;

import io.netty.handler.logging.LogLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

@Slf4j
@RequiredArgsConstructor
public abstract class GitHubClientBase {

    protected final WebClient webClient;

    protected GitHubClientBase(String githubUrl, String githubToken, Boolean wiretapEnabled) {
        ClientHttpConnector conn = new ReactorClientHttpConnector(getHttpClient(wiretapEnabled));
        webClient = WebClient.builder()
                .clientConnector(conn)
                .baseUrl(githubUrl)
                .defaultHeaders(httpHeaders -> addAuthenticationHeaders(httpHeaders, githubToken))
                .build();
    }

    protected void addAuthenticationHeaders(HttpHeaders httpHeaders, String githubToken) {
        if (githubToken != null && !githubToken.isEmpty()) {
            httpHeaders.set("Authorization", "token " + githubToken);
            log.info("Created WebClient authentication filter with token: {}...", githubToken.substring(0, 7));
        } else {
            log.warn("WebClient built without authentication headers!");
        }
    }

    protected HttpClient getHttpClient(boolean wiretapEnabled) {
        HttpClient httpClient = HttpClient.create();
        if (Boolean.TRUE.equals(wiretapEnabled)) {
            httpClient = httpClient.wiretap(
                    this.getClass().getCanonicalName(), LogLevel.TRACE, AdvancedByteBufFormat.TEXTUAL);
        }
        return httpClient;
    }

}
