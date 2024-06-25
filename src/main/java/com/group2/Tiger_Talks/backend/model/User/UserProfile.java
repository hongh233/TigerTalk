package com.group2.Tiger_Talks.backend.model.User;

import jakarta.persistence.*;

@Entity
@Table(name = "user_profile")
public class UserProfile {

    @Id
    private String email;             // PK | FK
    private String personalInterest;
    private String location;
    private String postalCode;
    private String biography;
    private String peopleAccessLevel;  // public / protected / private
    private String phoneNumber;

    // FK setting
    @OneToOne
    @MapsId
    @JoinColumn(name = "email")
    private UserTemplate userTemplate;


    public UserProfile() {
    }

    public UserProfile(String email,
                       String personalInterest,
                       String location,
                       String postalCode,
                       String biography,
                       String peopleAccessLevel,
                       String phoneNumber,
                       UserTemplate userTemplate) {
        this.email = email;
        this.personalInterest = personalInterest;
        this.location = location;
        this.postalCode = postalCode;
        this.biography = biography;
        this.phoneNumber = phoneNumber;
        this.peopleAccessLevel = peopleAccessLevel;
        this.userTemplate = userTemplate;
    }

    public String getPersonalInterest() {
        return personalInterest;
    }

    public void setPersonalInterest(String personalInterest) {
        this.personalInterest = personalInterest;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getPeopleAccessLevel() {
        return peopleAccessLevel;
    }

    public void setPeopleAccessLevel(String peopleAccessLevel) {
        this.peopleAccessLevel = peopleAccessLevel;
    }

    public UserTemplate getUserTemplate() {
        return userTemplate;
    }

    public void setUserTemplate(UserTemplate userTemplate) {
        this.userTemplate = userTemplate;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

