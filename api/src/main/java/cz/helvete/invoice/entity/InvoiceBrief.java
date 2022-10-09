package cz.helvete.invoice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import cz.helvete.invoice.db.entity.BaseEntity;
import cz.helvete.invoice.db.entity.Invoice;
import cz.helvete.invoice.rest.HateoasLink;
import cz.helvete.invoice.util.LocalDateTimeSerializer;
import java.time.LocalDateTime;
import java.util.Arrays;

public class InvoiceBrief extends BaseEntity {
    private transient Integer id;
    private Integer number;
    private String providerName;
    private String acceptorName;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime issuedAt;
    private Integer total;

    public InvoiceBrief(Invoice invoice) {
        id = invoice.getId();
        number = invoice.getNumber();
        providerName = invoice.getProvider().getName();
        acceptorName = invoice.getAcceptor().getName();
        issuedAt = invoice.getIssuedAt();
        total = invoice.getTotal();
        getLinks().add(new HateoasLink(
                    "Download",
                    "/invoice/%s/pdf",
                    Arrays.asList(String.valueOf(id)),
                    true));
    }

    @JsonIgnore
    public Integer getId() {
        return id;
    }
    public Integer getNumber() {
        return number;
    }
    public String getProviderName() {
        return providerName;
    }
    public String getAcceptorName() {
        return acceptorName;
    }
    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }
    public Integer getTotal() {
        return total;
    }
}
