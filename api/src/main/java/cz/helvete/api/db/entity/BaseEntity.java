package cz.helvete.api.db.entity;

import cz.helvete.api.rest.HateoasLink;
import cz.helvete.api.rest.LinksEnhanced;
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
