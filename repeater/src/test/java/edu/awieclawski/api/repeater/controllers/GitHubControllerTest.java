package edu.awieclawski.api.repeater.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.awieclawski.GitHubRepeaterApp;
import edu.awieclawski.api.repeater.dtos.RepoReportDto;
import edu.awieclawski.api.repeater.dtos.RepoReportList;
import edu.awieclawski.builders.RestExceptionsBuilder;
import edu.awieclawski.dtos.ExceptionDto;
import edu.awieclawski.api.repeater.services.RepeaterService;
import edu.awieclawski.stubs.GitHubStubs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * In this test context @MockBean of RestExceptionsBuilder is required because
 * the @ControllerAdvice RestResponseEntityExceptionHandler is called, so it must use it as the Spring Bean
 */
@WebMvcTest(GitHubController.class)
@ContextConfiguration(classes = GitHubRepeaterApp.class)
@AutoConfigureMockMvc(addFilters = false) // bypass security if any
class GitHubControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RepeaterService service;

    @MockBean
    private RestExceptionsBuilder responseBuilder;

    private JacksonTester<RepoReportDto> jsonRepoReportDto;
    private JacksonTester<RepoReportList> jsonRepoReportList;
    private JacksonTester<ExceptionDto> exceptionDtoJacksonTester;
    private final String owner = "anyOwner";
    private final String repo = "anyRepo";

    @BeforeEach
    public void setup() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        JacksonTester.initFields(this, mapper);
    }

    @Test
    void canRetrieveRepoReportDtoByOwnerRepoIfExist() throws Exception {
        // given
        RepoReportDto dto = GitHubStubs.stubRepoReportDto();
        given(service.getOwnerRepo(owner, repo)).willReturn(dto);

        // when
        MockHttpServletResponse response = this.mockMvc.perform(
                        get("/repositories/{owner}/{repo}", owner, repo)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonRepoReportDto.write(dto).getJson());
    }

    @Test
    void canRetrieveRepoReportListByOwnerIfExist() throws Exception {
        // given
        RepoReportList dto = GitHubStubs.stubRepoReportList();
        given(service.getUserRepos(owner)).willReturn(dto);

        // when
        MockHttpServletResponse response = this.mockMvc.perform(
                        get("/repositories/{owner}", owner)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonRepoReportList.write(dto).getJson());
    }

}
