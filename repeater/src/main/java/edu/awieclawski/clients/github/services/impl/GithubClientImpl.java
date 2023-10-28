package edu.awieclawski.clients.github.services.impl;

import edu.awieclawski.clients.github.base.GitHubClientBase;
import edu.awieclawski.clients.github.dtos.GithubResponseDto;
import edu.awieclawski.clients.github.services.GithubClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GithubClientImpl extends GitHubClientBase implements GithubClient {

    public GithubClientImpl(@Value("${github.url}") String githubUrl,
                            @Value("${github.token}") String githubToken,
                            @Value("${webclient.wiretap.enabled}") Boolean wiretapEnabled) {
        super(githubUrl, githubToken, wiretapEnabled);
    }

    @Override
    public Mono<GithubResponseDto> getOwnerRepo(String owner, String repo) {
        return webClient.get()
                .uri("/repos/{owner}/{repo}", owner, repo)
                .exchangeToMono(response -> {
                    if (response.statusCode().isError()) {
                        return response.createException().flatMap(Mono::error);
                    } else {
                        return response.bodyToMono(GithubResponseDto.class);
                    }
                });
    }

    @Override
    public Flux<GithubResponseDto> getUserRepos(String user) {
        return webClient.get()
                .uri("/users/{user}/repos", user)
                .exchangeToFlux(response -> {
                    if (response.statusCode().isError()) {
                        return response.createException().flatMapMany(Flux::error);
                    } else {
                        return response.bodyToFlux(GithubResponseDto.class);
                    }
                });
    }

}
