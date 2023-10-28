package edu.awieclawski.api.repeater.controllers;

import edu.awieclawski.api.repeater.dtos.bases.BaseDto;
import edu.awieclawski.api.repeater.services.RepeaterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping
@RestController
public class GitHubController {
    private final RepeaterService service;

    @GetMapping("/repositories/{owner}/{repo}")
    public ResponseEntity<BaseDto> getOwnerRepo(@PathVariable String owner, @PathVariable String repo) {
        final BaseDto resultDto = service.getOwnerRepo(owner, repo);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .contentType(MediaType.APPLICATION_JSON)
                .body(resultDto);
    }

    @GetMapping("/repositories/{user}")
    public ResponseEntity<BaseDto> getUserRepos(@PathVariable String user) {
        final BaseDto resultDto = service.getUserRepos(user);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .contentType(MediaType.APPLICATION_JSON)
                .body(resultDto);
    }

}
