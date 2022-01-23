package cz.helvete.invoice.rest;

import java.util.List;
import javax.ws.rs.HttpMethod;

public class HateoasLink {
    private String name;
    private String link;
    private String method;
    // TODO: payload description

    public HateoasLink(
            String name,
            String link,
            List<String> tokens,
            String method
    ) {
        this.name = name;
        this.link = resolve(link, tokens);
        this.method = method;
    }

    public HateoasLink(String name, String link, List<String> tokens) {
        this(name, link, tokens, HttpMethod.GET);
    }

    private String resolve(String template, List<String> tokens) {
        return String.format(template, tokens.toArray());
    }

    public String getName() {
        return name;
    }
    public String getLink() {
        return link;
    }
    public String getMethod() {
        return method;
    }
}
