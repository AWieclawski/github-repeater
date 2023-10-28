package edu.awieclawski.api.repeater.mappers;

import edu.awieclawski.api.repeater.dtos.RepoReportDto;
import edu.awieclawski.api.repeater.dtos.RepoReportList;
import edu.awieclawski.clients.github.dtos.GithubResponseDto;
import edu.awieclawski.stubs.GitHubStubs;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class GitHubToReportMapperTest extends GitHubStubs {

    @Test
    void toReportWithValidGithubRepoReturnOK() {
        // given
        GithubResponseDto dto = stubGithubResponseDto();

        // when
        RepoReportDto test = (RepoReportDto) GitHubToReportMapper.toReport(dto);

        // then
        Assertions.assertEquals(test.getStars(), dto.getStargazersCount());
        Assertions.assertEquals(test.getFullName(), dto.getFullName());
        Assertions.assertEquals(test.getDescription(), dto.getDescription());
        Assertions.assertEquals(test.getCloneUrl(), dto.getCloneUrl());
    }

    @Test
    void toReportWithValidGithubRepoListReturnOK() {
        // given
        List<GithubResponseDto> list = stubGithubResponseDtoList();

        // when
        RepoReportList tests = GitHubToReportMapper.toReportList(list);

        // then
        tests.getRepositories().forEach(dto -> {
            Assertions.assertEquals(((RepoReportDto) dto).getStars(),
                    list.get(tests.getRepositories().indexOf(dto)).getStargazersCount());
            Assertions.assertEquals(((RepoReportDto) dto).getFullName(),
                    list.get(tests.getRepositories().indexOf(dto)).getFullName());
            Assertions.assertEquals(((RepoReportDto) dto).getDescription(),
                    list.get(tests.getRepositories().indexOf(dto)).getDescription());
            Assertions.assertEquals(((RepoReportDto) dto).getCloneUrl(),
                    list.get(tests.getRepositories().indexOf(dto)).getCloneUrl());
        });
    }

}
