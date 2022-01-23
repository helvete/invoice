package cz.helvete.invoice.rest;

import java.util.List;

public interface LinksEnhanced {
    public Integer getId();
    public List<HateoasLink> getLinks();
    public void setLinks(List<HateoasLink> links);
}
