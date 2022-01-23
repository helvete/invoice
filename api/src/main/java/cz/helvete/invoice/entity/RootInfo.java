package cz.helvete.invoice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.helvete.invoice.db.entity.BaseEntity;

public class RootInfo extends BaseEntity {
    private String application = "Invoice";
    private String userName;
    private String version = "0.1"; // TODO make dynamic

    public RootInfo(String userName) {
        this.userName = userName;
    }

    @JsonIgnore
    public Integer getId() {
        return -1;
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
