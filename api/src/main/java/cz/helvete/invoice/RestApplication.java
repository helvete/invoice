package cz.helvete.invoice;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Initialize REST application that will be available under URL /rest/*
 */
@ApplicationPath("/rest")
public class RestApplication extends ResourceConfig {
    public RestApplication() {
        super();
        register(MultiPartFeature.class);
        super.packages(true, "cz.helvete.invoice");
        register(JacksonFeature.class);
    }
}
