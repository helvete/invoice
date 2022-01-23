package cz.helvete.invoice.util;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.core.JsonParser;

public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(
            JsonParser parser,
            DeserializationContext context
    ) throws IOException {
        String node = parser.getValueAsString();
        if (node.equals("") || node == null) {
            return null;
        }
        try {
            return LocalDateTime.parse(node, DateTimeFormatter.ofPattern(
                        LocalDateTimeFormat.FORMAT_DATABASE));
        } catch (Exception e){
            Logger.getLogger(LocalDateTimeDeserializer.class.getName()).log(
                    Level.SEVERE,
                    "Problem processing JSON date: " + node,
                    e);
        }
        return null;
    }
}