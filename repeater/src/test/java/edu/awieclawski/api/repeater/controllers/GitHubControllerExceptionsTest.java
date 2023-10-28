package edu.awieclawski.api.repeater.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.awieclawski.GitHubRepeaterApp;
import edu.awieclawski.builders.RestExceptionsBuilder;
import edu.awieclawski.configs.RestResponseEntityExceptionHandler;
import edu.awieclawski.dtos.ExceptionDto;
import edu.awieclawski.api.repeater.services.RepeaterService;
import edu.awieclawski.stubs.GitHubStubs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = GitHubRepeaterApp.class)
class GitHubControllerExceptionsTest extends GitHubStubs {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestExceptionsBuilder responseBuilder;

    @MockBean
    private RepeaterService service;


    @InjectMocks
    private GitHubController controller;

    private JacksonTester<ExceptionDto> exceptionDtoJacksonTester;

    private final String owner = "anyOwner";
    private final String repo = "anyRepo";

    @BeforeEach
    public void setup() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        JacksonTester.initFields(this, mapper);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(controller)     // instantiate controller.
                .setControllerAdvice(new RestResponseEntityExceptionHandler(responseBuilder))   // bind with controller advice.
                .build();
    }

    @Test
    void testExceptionThrowingControllerExists() {
        assertThat(controller).isNotNull();
    }

    @Test
    void canRetrieveExceptionDtoByOwnerRepoIfNotExist() throws Exception {
        // given
        final HttpStatus STATUS = HttpStatus.INTERNAL_SERVER_ERROR;
        final Integer INNER_ERR_CODE = HttpStatus.NOT_FOUND.value();
        final String DESC = "WebClientResponseException sample!";
        when(service.getOwnerRepo(anyString(), anyString())).thenThrow(WebClientResponseException.class);
        when(responseBuilder.build(any(Exception.class), any(HttpStatus.class), any(Locale.class)))
                .thenReturn(getResponseEntity(STATUS, INNER_ERR_CODE, DESC));

        // when
        MockHttpServletResponse response = this.mockMvc.perform(
                        get("/repositories/{owner}/{repo}", owner, repo)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        ExceptionDto dto = exceptionDtoJacksonTester.parse(response.getContentAsByteArray()).getObject();

        // Then
        assertThat(response).isNotNull();
        assertThat(STATUS.value()).isNotEqualTo(INNER_ERR_CODE);
        assertThat(dto.getDescription()).isEqualTo(DESC);
        assertThat(dto.getStatus()).isEqualTo(INNER_ERR_CODE);
    }

    @Test
    void canRetrieveExceptionDtoByUserIfNotExist() throws Exception {
        // given
        final HttpStatus STATUS = HttpStatus.INTERNAL_SERVER_ERROR;
        final Integer INNER_ERR_CODE = HttpStatus.NOT_FOUND.value();
        final String DESC = "No user exception sample!";
        when(service.getUserRepos(anyString())).thenThrow(WebClientResponseException.class);
        when(responseBuilder.build(any(Exception.class), any(HttpStatus.class), any(Locale.class)))
                .thenReturn(getResponseEntity(STATUS, INNER_ERR_CODE, DESC));

        // when
        MockHttpServletResponse response = this.mockMvc.perform(
                        get("/repositories/{owner}", owner)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        ExceptionDto dto = exceptionDtoJacksonTester.parse(response.getContentAsByteArray()).getObject();

        // Then
        assertThat(response).isNotNull();
        assertThat(STATUS.value()).isNotEqualTo(INNER_ERR_CODE);
        assertThat(dto.getDescription()).isEqualTo(DESC);
        assertThat(dto.getStatus()).isEqualTo(INNER_ERR_CODE);
    }

}
