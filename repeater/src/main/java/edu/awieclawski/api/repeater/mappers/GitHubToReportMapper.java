package edu.awieclawski.api.repeater.mappers;

import edu.awieclawski.api.repeater.dtos.RepoReportDto;
import edu.awieclawski.api.repeater.dtos.RepoReportList;
import edu.awieclawski.api.repeater.dtos.bases.BaseDto;
import edu.awieclawski.clients.github.dtos.GithubResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GitHubToReportMapper {

    public static BaseDto toReport(GithubResponseDto input) {
        if (input != null) {
            return RepoReportDto.builder()
                    .cloneUrl(input.getCloneUrl())
                    .createdAt(input.getCreatedAt())
                    .description(input.getDescription())
                    .stars(input.getStargazersCount())
                    .fullName(input.getFullName())
                    .build();
        }
        return null;
    }

    public static RepoReportList toReportList(List<GithubResponseDto> input) {
        if (input != null) {
            List<BaseDto> dtos = new ArrayList<>();
            input.stream().filter(Objects::nonNull).forEach(repo ->
                    dtos.add(GitHubToReportMapper.toReport(repo))
            );
            return RepoReportList.builder()
                    .repositories(dtos)
                    .build();
        }
        return null;
    }

}
