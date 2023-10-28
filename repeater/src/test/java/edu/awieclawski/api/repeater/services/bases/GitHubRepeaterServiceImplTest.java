package edu.awieclawski.api.repeater.services.bases;

import edu.awieclawski.GitHubRepeaterApp;
import edu.awieclawski.api.repeater.dtos.RepoReportDto;
import edu.awieclawski.api.repeater.dtos.RepoReportList;
import edu.awieclawski.api.repeater.dtos.bases.BaseDto;
import edu.awieclawski.clients.github.services.impl.GithubClientImpl;
import edu.awieclawski.localtime.services.TimeRetryService;
import edu.awieclawski.stubs.GitHubStubs;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = GitHubRepeaterApp.class)
class GitHubRepeaterServiceImplTest extends GitHubStubs {

    @Mock
    private TimeRetryService timeMock;

    @Mock
    private GithubClientImpl clientMock;

    @InjectMocks
    private GitHubRepeaterServiceImpl service;

    private final String owner = "any";
    private final String repo = "any";


    @Test
    void getOwnerRepoWithOkResponsReturnRepoReportDtoInstance() {
        // given
        when(clientMock.getOwnerRepo(anyString(), anyString())).thenReturn(Mono.just(stubGithubResponseDto()));
        when(timeMock.getRegionalTime()).thenReturn(Instant.now());
        String[] array = new String[]{owner, repo};

        // when
        BaseDto dto = service.callOwnerRepo(array);

        // then
        Assertions.assertNotNull(dto);
        Assertions.assertInstanceOf(RepoReportDto.class, dto);
    }

    @Test
    void getUserReposWithOkResponsReturnRepoReportListInstance() {
        // given
        when(clientMock.getUserRepos(anyString())).thenReturn(Flux.fromIterable(stubGithubResponseDtoList()));
        when(timeMock.getRegionalTime()).thenReturn(Instant.now());
        String[] array = new String[]{owner, null};

        // when
        BaseDto dto = service.callUserRepos(array);

        // then
        Assertions.assertNotNull(dto);
        Assertions.assertInstanceOf(RepoReportList.class, dto);
    }

}