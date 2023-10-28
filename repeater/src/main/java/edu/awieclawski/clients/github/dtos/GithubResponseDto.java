package edu.awieclawski.clients.github.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import edu.awieclawski.serializers.IsoInstantDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GithubResponseDto {

    private String description;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("created_at")
    @JsonDeserialize(using = IsoInstantDeserializer.class)
    private Instant createdAt;

    @JsonProperty("clone_url")
    private String cloneUrl;

    @JsonProperty("stargazers_count")
    private Long stargazersCount;
}
