package edu.awieclawski.api.repeater.services;

import edu.awieclawski.api.repeater.dtos.bases.BaseDto;

public interface GitHubRepeaterService {

    BaseDto callOwnerRepo(String[] array);

    BaseDto callUserRepos(String[] array);
}
