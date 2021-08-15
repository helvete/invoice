package cz.helvete.api.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import cz.helvete.api.util.LocalDateTimeDeserializer;
import cz.helvete.api.util.LocalDateTimeSerializer;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="address")
public class Address extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(length = 64, name = "name")
    private String name;
    @Column(length = 64, name = "street")
    private String street;
    @Column(length = 8, name = "land_registry_number")
    private String landRegistryNumber;
    @Column(length = 8, name = "house_number")
    private String houseNumber;
    @Column(length = 64, name = "city")
    private String city;
    @Column(name = "zip")
    private Integer zip;
    @Column(name = "notes")
    private String notes;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getStreet() {
        return street;
    }
    public String getLandRegistryNumber() {
        return landRegistryNumber;
    }
    public String getHouseNumber() {
        return houseNumber;
    }
    public String getCity() {
        return city;
    }
    public Integer getZip() {
        return zip;
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
    public void setId(Integer id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public void setLandRegistryNumber(String landRegistryNumber) {
        this.landRegistryNumber = landRegistryNumber;
    }
    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setZip(Integer zip) {
        this.zip = zip;
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
}
