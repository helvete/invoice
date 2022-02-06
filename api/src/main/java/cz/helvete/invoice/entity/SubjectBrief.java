package cz.helvete.invoice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.helvete.invoice.db.entity.BaseEntity;
import cz.helvete.invoice.db.entity.Subject;

public class SubjectBrief extends BaseEntity {
    private transient Integer id;
    private String businessIdnumber;
    private String name;

    public SubjectBrief(Subject subject) {
        id = subject.getId();
        businessIdnumber = subject.getBusinessIdnumber();
        name = subject.getName();
    }

    @JsonIgnore
    public Integer getId() {
        return id;
    }
    public String getBusinessIdnumber() {
        return businessIdnumber;
    }
    public String getName() {
        return name;
    }
}
