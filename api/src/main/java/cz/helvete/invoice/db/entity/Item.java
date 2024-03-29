package cz.helvete.invoice.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;
    @Column(length = 255)
    private String name;
    @Column(name = "price_per_unit")
    private Integer pricePerUnit;
    @Column(name = "units_count")
    private Double unitsCount;
    @Column
    private Integer total;
    @Column
    private Integer ordering;

    public Integer getId() {
        return id;
    }
    @JsonIgnore
    public Invoice getInvoice() {
        return invoice;
    }
    public String getName() {
        return name;
    }
    public Integer getPricePerUnit() {
        return pricePerUnit;
    }
    public Double getUnitsCount() {
        return unitsCount;
    }
    public Integer getTotal() {
        return total;
    }
    @JsonIgnore
    public Integer getOrdering() {
        return ordering;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setInvoice(Invoice invoice) {
        ordering = invoice.getItems().size();
        this.invoice = invoice;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPricePerUnit(Integer pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }
    public void setUnitsCount(Double unitsCount) {
        this.unitsCount = unitsCount;
    }
    public void setTotal(Integer total) {
        this.total = total;
    }
    public void setOrdering(Integer ordering) {
        this.ordering = ordering;
    }
}
