package edu.awieclawski.api.repeater.services.impl;

import edu.awieclawski.api.repeater.dtos.bases.BaseDto;
import edu.awieclawski.api.repeater.services.GitHubRepeaterService;
import edu.awieclawski.api.repeater.services.RepeaterService;
import edu.awieclawski.api.repeater.services.bases.GitHubRepeaterServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RepeaterServiceImpl implements RepeaterService {

    @Qualifier(GitHubRepeaterServiceImpl.BEAN_NAME)
    @Autowired
    private GitHubRepeaterService repeaterService;

    @Override
    public BaseDto getOwnerRepo(String owner, String repo) {
        String[] array = new String[]{owner, repo};
        return repeaterService.callOwnerRepo(array);
    }

    @Override
    public BaseDto getUserRepos(String owner) {
        String[] array = new String[]{owner, null};
        return repeaterService.callUserRepos(array);
    }


}
