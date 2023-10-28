package edu.awieclawski.stubs;

import edu.awieclawski.api.repeater.dtos.RepoReportDto;
import edu.awieclawski.api.repeater.dtos.RepoReportList;
import edu.awieclawski.api.repeater.dtos.bases.BaseDto;
import edu.awieclawski.clients.github.dtos.GithubResponseDto;
import edu.awieclawski.dtos.ExceptionDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class GitHubStubs {

    public static GithubResponseDto stubGithubResponseDto() {
        return GithubResponseDto.builder()
                .cloneUrl("some.url")
                .createdAt(Instant.now())
                .description("some description")
                .stargazersCount(1L)
                .fullName("full name of some repository")
                .build();
    }

    public static GithubResponseDto stubGithubResponseDto(String fullName, String cloneUrl) {
        return GithubResponseDto.builder()
                .cloneUrl("some/url")
                .createdAt(Instant.now())
                .description("some description")
                .stargazersCount(1L)
                .fullName(fullName)
                .build();
    }

    public static RepoReportDto stubRepoReportDto() {
        return RepoReportDto.builder()
                .cloneUrl("some/url")
                .createdAt(Instant.now())
                .description("some description")
                .stars(1L)
                .fullName("full name of some repository")
                .nanoDuration(Duration.ofNanos(87654))
                .build();
    }

    public static RepoReportDto stubRepoReportDto(String fullName, String cloneUrl) {
        return RepoReportDto.builder()
                .cloneUrl(cloneUrl)
                .createdAt(Instant.now())
                .description("some description")
                .stars(1L)
                .fullName(fullName)
                .nanoDuration(Duration.ofNanos(87654))
                .build();
    }

    public static RepoReportList stubRepoReportList() {
        List<BaseDto> list = new ArrayList<>();
        list.add(stubRepoReportDto("full name one of some repository", "some/url/one"));
        list.add(stubRepoReportDto("full name two of some repository", "some/url/two"));
        return RepoReportList.builder()
                .repositories(list)
                .nanoDuration(Duration.ofNanos(87654))
                .build();
    }


    public static List<GithubResponseDto> stubGithubResponseDtoList() {
        List<GithubResponseDto> repos = new ArrayList<>();
        repos.add(stubGithubResponseDto("full name one of some repository", "some/url/one"));
        repos.add(stubGithubResponseDto("full name two of some repository", "some/url/two"));
        return repos;
    }

    protected ResponseEntity<ExceptionDto> getResponseEntity(HttpStatus status, Integer innerErrorCode, String description) {
        return ResponseEntity.status(status)
                .body(getExceptionDto(innerErrorCode, description));
    }

    private ExceptionDto getExceptionDto(Integer innerErrorCode, String description) {
        return new ExceptionDto(innerErrorCode,
                Instant.now(),
                description);
    }

    protected Throwable stubWebClientResponseException() {
        return new WebClientResponseException(HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(), new HttpHeaders(), null, null);
    }

}
