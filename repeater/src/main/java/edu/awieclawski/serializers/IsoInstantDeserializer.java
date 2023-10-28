package edu.awieclawski.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import edu.awieclawski.exceptions.DeserializeDateException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

/**
 * acc. to https://www.baeldung.com/java-format-zoned-datetime-string
 */
@Slf4j
public class IsoInstantDeserializer extends StdDeserializer<Instant> {

    private static final long serialVersionUID = -6341381489889984058L;

    protected IsoInstantDeserializer() {
        super(Instant.class);
    }

    @Override
    public Instant deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        try {
            return ZonedDateTime.parse(parser.readValueAs(String.class)).toInstant();
        } catch (DateTimeParseException e) {
            log.error("Instant deserialization error: {} for String value: [{}]", e.getMessage(),
                    e.getParsedString());
        }

        throw new DeserializeDateException("IsoInstantDeserializer error!");
    }

}
