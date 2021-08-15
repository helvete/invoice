package cz.helvete.api.rest;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
import org.apache.commons.io.input.TeeInputStream;

@Provider
@Priority(Priorities.ENTITY_CODER)
public class InputLoggerRequestFilter
    implements ContainerRequestFilter, WriterInterceptor {

    private static final String PROP_ESC = "ENTITY_STREAM_COPY";
    private static final String PROP_PR = "PATH_REQUESTED";
    private static final String UNLOGGABLE = "password";

    private static Logger logger = Logger.getLogger(
            InputLoggerRequestFilter.class.getName());

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        MediaType mt = requestContext.getMediaType();
        if (mt != null && mt.isCompatible(MediaType.MULTIPART_FORM_DATA_TYPE)) {
            logger.info("Incoming FILE upload, length: " + requestContext.getLength());
            return;
        }
        if (requestContext.hasEntity()) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            requestContext.setEntityStream(new TeeInputStream(
                        requestContext.getEntityStream(),
                        baos));
            requestContext.setProperty(PROP_ESC, baos);
            requestContext.setProperty(
                    PROP_PR,
                    requestContext.getUriInfo().getPath());
        }
    }

    @Override
    public void aroundWriteTo(final WriterInterceptorContext wic) throws IOException,
            WebApplicationException {
        final ByteArrayOutputStream baos
            = (ByteArrayOutputStream) wic.getProperty(PROP_ESC);
        String path = (String) wic.getProperty(PROP_PR);
        wic.proceed();
        if (baos != null) {
            logger.info(String.format(
                        "Incoming JSON in %s : %s",
                        path,
                        stripPassword(baos.toString())));
        }
    }

    private String stripPassword(String input) {
        JsonElement jsonElement;
        try {
            jsonElement = new JsonParser().parse(input);
        } catch (Exception e) {
            return input; // no JSON apparently
        }
        return replaceDeep(jsonElement).toString();
    }

    private JsonElement replaceDeep(JsonElement element) {
        if (element.isJsonArray()) {
            JsonArray ja = new JsonArray();
            for (JsonElement arrayElement : element.getAsJsonArray()) {
                ja.add(replaceDeep(arrayElement));
            }
            return ja;
        } else if (element.isJsonObject()) {
            JsonObject jo = new JsonObject();
            Set<Map.Entry<String, JsonElement>> members
                    = element.getAsJsonObject().entrySet();
            for (Map.Entry<String, JsonElement> objElement : members) {
                jo.add(objElement.getKey(), objElement.getKey().equals(UNLOGGABLE)
                        ? new JsonPrimitive(UNLOGGABLE)
                        : replaceDeep(objElement.getValue()));
            }
            return jo;
        }
        return element;
    }
}
