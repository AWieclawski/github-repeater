package edu.awieclawski.api.repeater.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.awieclawski.api.repeater.dtos.bases.BaseDto;
import edu.awieclawski.serializers.IsoInstantSerializer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonPropertyOrder({"fullName", "description", "cloneUrl", "stars", "createdAt"})
@JsonInclude(Include.NON_NULL)
public class RepoReportDto extends BaseDto {

    private String fullName;

    private String description;

    private String cloneUrl;

    private Long stars;

    @JsonSerialize(using = IsoInstantSerializer.class)
    private Instant createdAt;

}
