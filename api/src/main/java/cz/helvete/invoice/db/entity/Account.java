package cz.helvete.invoice.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import cz.helvete.invoice.util.LocalDateTimeDeserializer;
import cz.helvete.invoice.util.LocalDateTimeSerializer;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="account")
public class Account extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(length = 255, unique = true)
    private String email;
    @Column(length = 255)
    private String password;
    @Column
    private Integer role;
    @Column(name = "email_verification_hash", nullable = true)
    private String emailVerificationHash;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column(name = "email_verified_at", nullable = true)
    private LocalDateTime emailVerifiedAt;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column(name = "deleted_at", nullable = true)
    private LocalDateTime deletedAt;

    public Integer getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public Integer getRole() {
        return role;
    }
    public String getEmailVerificationHash() {
        return emailVerificationHash;
    }
    public LocalDateTime getEmailVerifiedAt() {
        return emailVerifiedAt;
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
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRole(Integer role) {
        this.role = role;
    }
    public void setEmailVerificationHash(String emailVerificationHash) {
        this.emailVerificationHash = emailVerificationHash;
    }
    public void setEmailVerifiedAt(LocalDateTime emailVerifiedAt) {
        this.emailVerifiedAt = emailVerifiedAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
