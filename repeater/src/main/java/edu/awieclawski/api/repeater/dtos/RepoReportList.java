package edu.awieclawski.api.repeater.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import edu.awieclawski.api.repeater.dtos.bases.BaseDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({"repositories", "error", "timestamp", "nanoDuration"})
public class RepoReportList extends BaseDto {

    private List<BaseDto> repositories;

}
