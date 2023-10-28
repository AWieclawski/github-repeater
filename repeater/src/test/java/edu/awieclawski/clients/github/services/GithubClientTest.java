package edu.awieclawski.clients.github.services;


import edu.awieclawski.GitHubRepeaterApp;
import edu.awieclawski.clients.github.dtos.GithubResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SpringBootTest(classes = GitHubRepeaterApp.class)
class GithubClientTest {

    @Autowired
    private GithubClient client;

    @Test
    void getOwnerRepoForValidOwnerAndRepoReturnOK() {
        // given
        String owner = "awieclawski";
        String repo = "BackEndCalculator";
        GithubResponseDto[] repos = new GithubResponseDto[1];

        // when
        Assertions.assertNotNull(client);
        Assertions.assertDoesNotThrow(() -> repos[0] = (client.getOwnerRepo(owner, repo).block()));

        // then
        Assertions.assertTrue(repos[0].getFullName().toLowerCase().contains(owner));
    }

    @Test
    void getOwnerRepoForNotValidOwnerAndRepoReturnError() {
        // given
        String owner = "wrongUser";
        String repo = "BackEndCalculator";
        GithubResponseDto[] repos = new GithubResponseDto[1];

        // when
        Assertions.assertNotNull(client);
        WebClientResponseException throwable = Assertions.assertThrows(WebClientResponseException.class,
                () -> repos[0] = (client.getOwnerRepo(owner, repo).block()));

        // then
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(),
                throwable.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.NOT_FOUND.getReasonPhrase(),
                throwable.getStatusText());
    }

    @Test
    void getOwnerRepoForValidOwnerAndNotValidRepoReturnError() {
        // given
        String owner = "awieclawski";
        String repo = "wrongRepo";
        GithubResponseDto[] repos = new GithubResponseDto[1];

        // when
        Assertions.assertNotNull(client);
        WebClientResponseException throwable = Assertions.assertThrows(WebClientResponseException.class,
                () -> repos[0] = (client.getOwnerRepo(owner, repo).block()));

        // then
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(),
                throwable.getStatusCode().value());
        Assertions.assertEquals(HttpStatus.NOT_FOUND.getReasonPhrase(),
                throwable.getStatusText());
    }

    @Test
    void getOwnerReposForValidOwnerReturnOkList() {
        // given
        String owner = "awieclawski";
        List<GithubResponseDto> repos = new ArrayList<>();

        // when
        Assertions.assertNotNull(client);
        Assertions.assertDoesNotThrow(() -> repos.addAll(Objects.requireNonNull(client.getUserRepos(owner).collectList().block())));

        // then
        Assertions.assertTrue(repos.get(0).getFullName().toLowerCase().contains(owner));
    }

}