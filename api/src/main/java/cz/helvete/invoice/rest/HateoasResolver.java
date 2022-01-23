package cz.helvete.invoice.rest;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import javax.ws.rs.HttpMethod;

public class HateoasResolver {

    private static final Pattern L1 = Pattern.compile("^[a-z-]+\\/*$");
    private static final Pattern L2 = Pattern.compile("^[a-z-]+\\/[0-9]+\\/*$");
    private static final Pattern L3 = Pattern.compile("^[a-z-]+\\/[0-9]+\\/.+$");
    private static final Pattern SLASH = Pattern.compile("\\/");
    private static final Pattern NUMERIC = Pattern.compile("^\\d$");

    public static List<HateoasLink> resolve(
            String location,
            List<String> tokens
    ) {
        // TODO: move to xml schema
        // utilize location enum
        if (location.equals("")) {
            return Arrays.asList(
                    new HateoasLink("Invoice list", "/invoice"),
                    new HateoasLink("New Invoice", "/invoice", HttpMethod.POST),
                    new HateoasLink("Subject list", "/subject"),
                    new HateoasLink("New Subject", "/subject", HttpMethod.POST)
                    // TODO Addressess
            );
        }
        String item = getResourceName(location);
        if (L2.matcher(location).matches()) {
            return Arrays.asList(
                    new HateoasLink("Edit", "/" + item + "/%s", tokens, HttpMethod.PUT),
                    new HateoasLink("Delete", "/" + item + "/%s", tokens, HttpMethod.DELETE)
            );
        }
        if (L1.matcher(location).matches()) {
            return Arrays.asList(
                    new HateoasLink("Detail", "/" + item + "/%s", tokens)
            );
        }
        // TODO: L3. Make more universal to allow arbitrary levels?

        return Arrays.asList();
    }

    private static String getResourceName(String location) {
        String[] parts = SLASH.split(location);
        for (int i = parts.length - 1; i >= 0; i--) {
            if (!NUMERIC.matcher(parts[i]).matches()) {
                return parts[i];
            }
        }
        return "-1";
    }
}
