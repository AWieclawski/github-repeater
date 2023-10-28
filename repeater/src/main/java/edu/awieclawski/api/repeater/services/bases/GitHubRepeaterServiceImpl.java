package edu.awieclawski.api.repeater.services.bases;

import edu.awieclawski.api.repeater.dtos.bases.BaseDto;
import edu.awieclawski.api.repeater.mappers.GitHubToReportMapper;
import edu.awieclawski.clients.github.dtos.GithubResponseDto;
import edu.awieclawski.clients.github.services.GithubClient;
import edu.awieclawski.localtime.services.TimeRetryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Slf4j
@Component(GitHubRepeaterServiceImpl.BEAN_NAME)
public class GitHubRepeaterServiceImpl extends GitHubRepeaterBase {

    public static final String BEAN_NAME = "GitHubRepeaterService";

    private final GithubClient client;

    public GitHubRepeaterServiceImpl(GithubClient client, TimeRetryService timeProvider) {
        super(timeProvider);
        this.client = client;
    }

    @Override
    public BaseDto callOwnerRepo(String[] array) {
        return callClient(array, dto -> provideOwnerRepo(array));
    }

    @Override
    public BaseDto callUserRepos(String[] array) {
        return callClient(array, dto -> provideUserRepos(array));
    }

    private BaseDto provideOwnerRepo(String[] array) throws WebClientResponseException {
        BaseDto dto;
        String owner = array[0];
        String repo = array[1];
        GithubResponseDto input = client.getOwnerRepo(owner, repo).block();
        if (input != null) {
            dto = GitHubToReportMapper.toReport(input);
            log.info("GitHub owner repository report ready.");
        } else {
            throw getWebClientResponseException();
        }
        return dto;
    }


    private BaseDto provideUserRepos(String[] array) throws WebClientResponseException {
        BaseDto dto;
        String user = array[0];
        List<GithubResponseDto> input = client.getUserRepos(user).collectList().block();
        if (input != null) {
            dto = GitHubToReportMapper.toReportList(input);
            log.info("GitHub user repositories report list ready.");
        } else {
            throw getWebClientResponseException();
        }
        return dto;
    }
}
