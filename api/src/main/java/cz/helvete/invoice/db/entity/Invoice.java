package cz.helvete.invoice.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import cz.helvete.invoice.util.LocalDateTimeDeserializer;
import cz.helvete.invoice.util.LocalDateTimeSerializer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="invoice")
public class Invoice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "number")
    private Integer number;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id")
    private Subject provider;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acceptor_id")
    private Subject acceptor;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column(name = "issued_at")
    private LocalDateTime issuedAt;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column(name = "due_date")
    private LocalDateTime dueDate;
    @Column(name = "total")
    private Integer total;
    @Column(name = "notes")
    private String notes;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column(name = "created_at") // TODO: generalize
    private LocalDateTime createdAt = LocalDateTime.now();
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Transient
    private Integer acceptorId;
    @Transient
    private Integer providerId;

    @OneToMany(mappedBy = "invoice", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Item> items = new ArrayList<>();

    public Integer getId() {
        return id;
    }
    public Integer getNumber() {
        return number;
    }
    public Subject getProvider() {
        return provider;
    }
    public Subject getAcceptor() {
        return acceptor;
    }
    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }
    public LocalDateTime getDueDate() {
        return dueDate;
    }
    public Integer getTotal() {
        return total;
    }
    public String getNotes() {
        return notes;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    @JsonIgnore
    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }
    public List<Item> getItems() {
        return items;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setNumber(Integer number) {
        this.number = number;
    }
    public void setProvider(Subject provider) {
        this.provider = provider;
    }
    public void setAcceptor(Subject acceptor) {
        this.acceptor = acceptor;
    }
    public void setIssuedAt(LocalDateTime issuedAt) {
        this.issuedAt = issuedAt;
    }
    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }
    public void setTotal(Integer total) {
        this.total = total;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
    public void setItems(List<Item> items) {
        this.items = items;
    }
    public Integer getAcceptorId() {
        return acceptorId;
    }
    public Integer getProviderId() {
        return providerId;
    }
}
