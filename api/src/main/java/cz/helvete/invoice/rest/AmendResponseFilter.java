package cz.helvete.invoice.rest;

import cz.helvete.invoice.auth.entity.AuthUser;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class AmendResponseFilter implements ContainerResponseFilter {

    private static Logger logger = Logger.getLogger(
            InputLoggerRequestFilter.class.getName());

    @Inject
    private AuthUser user;

    @Override
    public void filter(
            ContainerRequestContext requestContext,
            ContainerResponseContext responseContext
    ) throws IOException {
        try {
            Object o = responseContext.getEntity();
            if (o instanceof LinksEnhanced) {
                setLinks((LinksEnhanced)responseContext.getEntity(), requestContext);
            } else if (o instanceof List) {
                ((List<LinksEnhanced>)responseContext.getEntity())
                    .forEach(i -> setLinks(i, requestContext));
            } else {
                return;
            }
        } catch (ClassCastException cce) {
            // just log, do not break anything
            logger.log(Level.SEVERE, "Unknown response type", cce);
        }
    }

    private void setLinks(
            LinksEnhanced linksEnhanced,
            ContainerRequestContext requestContext
    ) {
        if (user.getEmail() == null) {
            linksEnhanced.setLinks(HateoasResolver.forUnauthorized());
            return;
        }
        List<HateoasLink> hateoasLinks = HateoasResolver.resolve(
                requestContext.getUriInfo().getPath(),
                Arrays.asList(String.valueOf(linksEnhanced.getId())));
        linksEnhanced.getLinks().addAll(hateoasLinks);
    }
}
