package cz.helvete.invoice.rest;

import java.util.Arrays;
import java.util.List;

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
                    new HateoasLink("Invoice list", "/invoice", tokens)
            );
        case "invoice":
        case "invoice/":
            return Arrays.asList(
                    new HateoasLink("Invoice detail", "/invoice/%s", tokens)
            );
        default:
            return Arrays.asList(
                    new HateoasLink("TODO", "TODO", Arrays.asList())
            );
        }
    }
}
