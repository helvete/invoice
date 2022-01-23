package cz.helvete.invoice.db.entity;

import cz.helvete.invoice.rest.HateoasLink;
import cz.helvete.invoice.rest.LinksEnhanced;
import java.util.List;

public abstract class BaseEntity implements LinksEnhanced {

    private List<HateoasLink> links;

    public List<HateoasLink> getLinks() {
        return links;
    }

    public void setLinks(List<HateoasLink> links) {
        this.links = links;
    }
}
