package cz.helvete.invoice.rest;

import java.util.Arrays;
import java.util.List;
import javax.ws.rs.HttpMethod;

public class HateoasLink {
    private String name;
    private String link;
    private String method;
    private boolean autoredir = false;
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

    public HateoasLink(String name, String link, List<String> tokens, boolean autoredir) {
        this(name, link, tokens, HttpMethod.GET);
        this.autoredir = autoredir;
    }

    public HateoasLink(String name, String link, List<String> tokens) {
        this(name, link, tokens, HttpMethod.GET);
    }

    public HateoasLink(String name, String link, String method) {
        this(name, link, Arrays.asList(), method);
    }

    public HateoasLink(String name, String link) {
        this(name, link, Arrays.asList());
    }

    public HateoasLink(String name, String link, boolean autoredir) {
        this(name, link, Arrays.asList());
        this.autoredir = autoredir;
    }

    public HateoasLink(String name, String link, String method, boolean autoredir) {
        this(name, link, Arrays.asList(), method);
        this.autoredir = autoredir;
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
    public boolean getAutoredir() {
        return autoredir;
    }
}
