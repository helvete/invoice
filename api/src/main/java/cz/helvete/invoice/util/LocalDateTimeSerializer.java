package cz.helvete.invoice.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    @Override
    public void serialize(
            LocalDateTime datetime,
            JsonGenerator generator,
            SerializerProvider provider
    ) throws IOException, JsonProcessingException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                LocalDateTimeFormat.FORMAT_DATABASE);
        generator.writeString(datetime.format(formatter));
    }
}
