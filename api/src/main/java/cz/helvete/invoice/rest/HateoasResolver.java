package cz.helvete.invoice.rest;

import java.util.Arrays;
import java.util.List;
import javax.ws.rs.HttpMethod;

public class HateoasResolver {

    public static List<HateoasLink> resolve(
            String location,
            List<String> tokens
    ) {
        // TODO: move to xml schema
        // utilize location enum
        // replace switch with pattern matching
        switch (location) {
        case "":
            return Arrays.asList(
                    new HateoasLink("Invoice list", "/invoice"),
                    new HateoasLink("New Invoice", "/invoice", HttpMethod.POST)
            );
        case "invoice":
        case "invoice/":
            return Arrays.asList(
                    new HateoasLink("Invoice detail", "/invoice/%s", tokens),
                    new HateoasLink("Edit Invoice", "/invoice/%s", tokens, HttpMethod.PUT),
                    new HateoasLink("Delete Invoice", "/invoice/%s", tokens, HttpMethod.DELETE)
            );
        default:
            return Arrays.asList(
                    new HateoasLink("TODO", "Not implemented yet")
            );
        }
    }
}
