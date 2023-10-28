package edu.awieclawski.clients.github.services;

import edu.awieclawski.clients.github.dtos.GithubResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GithubClient {

    Mono<GithubResponseDto> getOwnerRepo(String owner, String repo);

    Flux<GithubResponseDto> getUserRepos(String user);
}
