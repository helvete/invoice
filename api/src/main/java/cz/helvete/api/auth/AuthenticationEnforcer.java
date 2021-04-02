package cz.helvete.api.auth;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

/**
 * Enforce authentication when accessing JAX-RS endpoints.
 *
 * By default all endpoints require authentication. One can annotate
 * endpoint with @AllowUnauthenticated to remove this restriction
 */
@Provider
public class AuthenticationEnforcer implements DynamicFeature {
    @Override
    public void configure(ResourceInfo r, FeatureContext context) {
        if (!r.getResourceClass().isAnnotationPresent(AllowUnauthenticated.class)
            && !r.getResourceMethod().isAnnotationPresent(AllowUnauthenticated.class)
        ) {
            context.register(AuthenticationRequestFilter.class);
        }
    }
}
