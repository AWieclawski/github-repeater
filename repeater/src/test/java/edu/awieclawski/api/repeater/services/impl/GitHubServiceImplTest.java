package edu.awieclawski.api.repeater.services.impl;

import edu.awieclawski.GitHubRepeaterApp;
import edu.awieclawski.api.repeater.dtos.RepoReportDto;
import edu.awieclawski.api.repeater.dtos.RepoReportList;
import edu.awieclawski.api.repeater.dtos.bases.BaseDto;
import edu.awieclawski.api.repeater.services.bases.GitHubRepeaterServiceImpl;
import edu.awieclawski.stubs.GitHubStubs;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = GitHubRepeaterApp.class)
class GitHubServiceImplTest extends GitHubStubs {
    @Mock
    private GitHubRepeaterServiceImpl repeaterService;

    @InjectMocks
    private RepeaterServiceImpl service;

    private final String owner = "any";
    private final String repo = "any";

    @Test
    void getOwnerRepoWithOkResponsReturnRepoReportDtoInstance() {
        // given
        when(repeaterService.callOwnerRepo(any())).thenReturn(stubRepoReportDto());

        // when
        BaseDto dto = service.getOwnerRepo(owner, repo);

        // then
        Assertions.assertInstanceOf(RepoReportDto.class, dto);
    }

    @Test
    void getUserReposWithOkResponsReturnRepoReportListInstance() {
        // given
        when(repeaterService.callUserRepos(any())).thenReturn(stubRepoReportList());

        // when
        BaseDto dto = service.getUserRepos(owner);

        // then
        Assertions.assertInstanceOf(RepoReportList.class, dto);
    }

}
