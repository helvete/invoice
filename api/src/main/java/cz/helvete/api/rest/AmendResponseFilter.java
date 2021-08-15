package cz.helvete.api.rest;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class AmendResponseFilter implements ContainerResponseFilter {

    private static Logger logger = Logger.getLogger(
            InputLoggerRequestFilter.class.getName());

    @Override
    public void filter(
            ContainerRequestContext requestContext,
            ContainerResponseContext responseContext
    ) throws IOException {
        List<HateoasLink> hl = new ArrayList<>();
        hl.add(new HateoasLink());

        try {
            Object o = responseContext.getEntity();
            if (o instanceof LinksEnhanced) {
                ((LinksEnhanced)responseContext.getEntity()).setLinks(hl);
            } else if (o instanceof List) {
                for (LinksEnhanced l : (List<LinksEnhanced>)responseContext.getEntity()) {
                    l.setLinks(hl);
                }
            } else {
                return;
            }
        } catch (ClassCastException cce) {
            // just log, do not break anything
            logger.log(Level.SEVERE, "Unknown response type", cce);
        }
    }
}
