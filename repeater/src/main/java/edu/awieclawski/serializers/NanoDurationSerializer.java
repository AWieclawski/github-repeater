package edu.awieclawski.serializers;

import java.io.IOException;
import java.time.Duration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * acc. to https://stackoverflow.com/a/69533201
 * 
 * @author awieclawski
 *
 */
public class NanoDurationSerializer extends JsonSerializer<Duration> {

	@Override
	public void serialize(Duration value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		long nanoseconds = value.toNanos();
		gen.writeNumber(nanoseconds);
	}
}
