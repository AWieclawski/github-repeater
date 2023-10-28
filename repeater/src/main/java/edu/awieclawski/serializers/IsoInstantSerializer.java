package edu.awieclawski.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * acc. to https://stackoverflow.com/a/72708157/11868833
 */
public class IsoInstantSerializer extends StdSerializer<Instant> {

    private static final long serialVersionUID = -5829708609705898L;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_INSTANT;

    public IsoInstantSerializer() {
        super(Instant.class);
    }

    @Override
    public void serialize(Instant value, JsonGenerator generator, SerializerProvider provider)
            throws IOException {
        generator.writeString(FORMATTER.format(value));
    }

}
