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
    private PeopleAccessLevel peopleAccessLevel;  // public / protected / private
    private String phoneNumber;

    // FK setting
    @OneToOne
    @MapsId
    @JoinColumn(name = "email")
    private User user;


    public UserProfile() {
    }

    public UserProfile(String email,
                       String personalInterest,
                       String location,
                       String postalCode,
                       String biography,
                       String peopleAccessLevel,
                       String phoneNumber,
                       User user) {
        this.email = email;
        this.personalInterest = personalInterest;
        this.location = location;
        this.postalCode = postalCode;
        this.biography = biography;
        this.phoneNumber = phoneNumber;
        this.peopleAccessLevel = PeopleAccessLevel.fromString(peopleAccessLevel);
        this.user = user;
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

    public PeopleAccessLevel getPeopleAccessLevel() {
        return peopleAccessLevel;
    }

    public void setPeopleAccessLevel(String peopleAccessLevel) {
        this.peopleAccessLevel = PeopleAccessLevel.fromString(peopleAccessLevel);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

