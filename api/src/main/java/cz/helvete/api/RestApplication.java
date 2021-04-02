package cz.helvete.api;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.ApplicationPath;

/**
 * Initialize REST application that will be available under URL /rest/*
 */
@ApplicationPath("/rest")
public class RestApplication extends ResourceConfig {
    public RestApplication() {
        super();
        register(MultiPartFeature.class);
        super.packages(true, "cz.helvete.api");
        register(JacksonFeature.class);
    }
}
