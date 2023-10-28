package edu.awieclawski.api.repeater.services;

import edu.awieclawski.api.repeater.dtos.bases.BaseDto;

public interface RepeaterService {

    BaseDto getOwnerRepo(String owner, String repo);

    BaseDto getUserRepos(String owner);

}
