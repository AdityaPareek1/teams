package com.findit.teams.domain;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import javax.persistence.*;

/**
 * A Developers.
 */
@Entity
@Table(name = "developers")
public class Developers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "username")
    private String username;

    @Column(name = "skills")
    private String skills;

    @Column(name = "language")
    private String language;

    @Column(name = "profile_url")
    private String profileUrl;

    @Column(name = "one_line_description")
    private String oneLineDescription;

    @Column(name = "describe_yourself")
    private String describeYourself;

    @Column(name = "speak_language")
    private String speakLanguage;

    @Column(name = "born")
    private LocalDate born;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zip_post_code")
    private String zipPostCode;

    @Column(name = "country")
    private String country;

    @Column(name = "expriace")
    private String expriace;

    @Column(name = "work_type")
    private String workType;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_on")
    private Instant updatedOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "status")
    private String status;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Developers id(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Developers firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Developers lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public Developers email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public Developers password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public Developers username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSkills() {
        return this.skills;
    }

    public Developers skills(String skills) {
        this.skills = skills;
        return this;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getLanguage() {
        return this.language;
    }

    public Developers language(String language) {
        this.language = language;
        return this;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getProfileUrl() {
        return this.profileUrl;
    }

    public Developers profileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
        return this;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getOneLineDescription() {
        return this.oneLineDescription;
    }

    public Developers oneLineDescription(String oneLineDescription) {
        this.oneLineDescription = oneLineDescription;
        return this;
    }

    public void setOneLineDescription(String oneLineDescription) {
        this.oneLineDescription = oneLineDescription;
    }

    public String getDescribeYourself() {
        return this.describeYourself;
    }

    public Developers describeYourself(String describeYourself) {
        this.describeYourself = describeYourself;
        return this;
    }

    public void setDescribeYourself(String describeYourself) {
        this.describeYourself = describeYourself;
    }

    public String getSpeakLanguage() {
        return this.speakLanguage;
    }

    public Developers speakLanguage(String speakLanguage) {
        this.speakLanguage = speakLanguage;
        return this;
    }

    public void setSpeakLanguage(String speakLanguage) {
        this.speakLanguage = speakLanguage;
    }

    public LocalDate getBorn() {
        return this.born;
    }

    public Developers born(LocalDate born) {
        this.born = born;
        return this;
    }

    public void setBorn(LocalDate born) {
        this.born = born;
    }

    public String getAddress() {
        return this.address;
    }

    public Developers address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return this.city;
    }

    public Developers city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return this.state;
    }

    public Developers state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipPostCode() {
        return this.zipPostCode;
    }

    public Developers zipPostCode(String zipPostCode) {
        this.zipPostCode = zipPostCode;
        return this;
    }

    public void setZipPostCode(String zipPostCode) {
        this.zipPostCode = zipPostCode;
    }

    public String getCountry() {
        return this.country;
    }

    public Developers country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getExpriace() {
        return this.expriace;
    }

    public Developers expriace(String expriace) {
        this.expriace = expriace;
        return this;
    }

    public void setExpriace(String expriace) {
        this.expriace = expriace;
    }

    public String getWorkType() {
        return this.workType;
    }

    public Developers workType(String workType) {
        this.workType = workType;
        return this;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public Developers createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Developers createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getUpdatedOn() {
        return this.updatedOn;
    }

    public Developers updatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public Developers updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getStatus() {
        return this.status;
    }

    public Developers status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Developers)) {
            return false;
        }
        return id != null && id.equals(((Developers) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Developers{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", password='" + getPassword() + "'" +
            ", username='" + getUsername() + "'" +
            ", skills='" + getSkills() + "'" +
            ", language='" + getLanguage() + "'" +
            ", profileUrl='" + getProfileUrl() + "'" +
            ", oneLineDescription='" + getOneLineDescription() + "'" +
            ", describeYourself='" + getDescribeYourself() + "'" +
            ", speakLanguage='" + getSpeakLanguage() + "'" +
            ", born='" + getBorn() + "'" +
            ", address='" + getAddress() + "'" +
            ", city='" + getCity() + "'" +
            ", state='" + getState() + "'" +
            ", zipPostCode='" + getZipPostCode() + "'" +
            ", country='" + getCountry() + "'" +
            ", expriace='" + getExpriace() + "'" +
            ", workType='" + getWorkType() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
