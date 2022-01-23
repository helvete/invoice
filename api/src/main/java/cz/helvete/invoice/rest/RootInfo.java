package cz.helvete.invoice.rest;

import cz.helvete.invoice.db.entity.BaseEntity;

public class RootInfo extends BaseEntity {
    private String application = "Invoice";
    private String userName;
    private String version = "0.1"; // TODO make dynamic

    public RootInfo(String userName) {
        this.userName = userName;
    }

    public Integer getId() {
        return -1; // TODO: what to do w/ this?
    }

    public String getApplication() {
        return application;
    }
    public String getUserName() {
        return userName;
    }
    public String getVersion() {
        return version;
    }
}
