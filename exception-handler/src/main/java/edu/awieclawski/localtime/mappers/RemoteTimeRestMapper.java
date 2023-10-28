package edu.awieclawski.localtime.mappers;


import edu.awieclawski.localtime.dtos.TimestampDto;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class RemoteTimeRestMapper {

    public Instant toSeconds(TimestampDto timestampDto) {
        return Instant.ofEpochSecond(timestampDto.getUnixtime());
    }

    public Instant toMillis(TimestampDto timestampDto) {
        return Instant.ofEpochMilli(timestampDto.getUnixtime());
    }

}
