package edu.awieclawski.api.repeater.dtos.bases;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.awieclawski.serializers.IsoInstantSerializer;
import edu.awieclawski.serializers.NanoDurationSerializer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Duration;
import java.time.Instant;

/**
 * Technical class used only to accept its child dtos acc. to polymorphism
 *
 * @author awieclawski
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public abstract class BaseDto {

    @JsonProperty("timeElapsed")
    @JsonSerialize(using = NanoDurationSerializer.class)
    protected Duration nanoDuration;

    @JsonSerialize(using = IsoInstantSerializer.class)
    private Instant timestamp;

}
