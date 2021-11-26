package com.findit.teams.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

/**
 * A Companies.
 */
@Entity
@Table(name = "companies")
public class Companies implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "domain_name")
    private String domainName;

    @Column(name = "owner_name")
    private String ownerName;

    @Column(name = "message")
    private String message;

    @Column(name = "work_language")
    private String workLanguage;

    @Column(name = "status")
    private String status;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_on")
    private Instant updatedOn;

    @Column(name = "updated_by")
    private String updatedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Companies id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Companies name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public Companies email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDomainName() {
        return this.domainName;
    }

    public Companies domainName(String domainName) {
        this.domainName = domainName;
        return this;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getOwnerName() {
        return this.ownerName;
    }

    public Companies ownerName(String ownerName) {
        this.ownerName = ownerName;
        return this;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getMessage() {
        return this.message;
    }

    public Companies message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getWorkLanguage() {
        return this.workLanguage;
    }

    public Companies workLanguage(String workLanguage) {
        this.workLanguage = workLanguage;
        return this;
    }

    public void setWorkLanguage(String workLanguage) {
        this.workLanguage = workLanguage;
    }

    public String getStatus() {
        return this.status;
    }

    public Companies status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public Companies createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Companies createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getUpdatedOn() {
        return this.updatedOn;
    }

    public Companies updatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public Companies updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Companies)) {
            return false;
        }
        return id != null && id.equals(((Companies) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Companies{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", domainName='" + getDomainName() + "'" +
            ", ownerName='" + getOwnerName() + "'" +
            ", message='" + getMessage() + "'" +
            ", workLanguage='" + getWorkLanguage() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            "}";
    }
}
