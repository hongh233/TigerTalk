package com.group2.Tiger_Talks.backend.model.User;

import jakarta.persistence.*;

@Entity
@Table(name = "user_profile")
public class UserProfile {
    @Id
    private String email;             // PK | FK
    private String personalInterest;
    private String phoneNumber;
    private Integer age;
    private String gender;
    private String location;
    private String postalCode;
    private String biography;
    private PeopleAccessLevel peopleAccessLevel;  // public / protected / private
    private String firstName;
    private String lastName;


    // FK setting
    @OneToOne
    @MapsId
    @JoinColumn(name = "email")
    private User user;


    public UserProfile() {
    }

    public UserProfile(String email,
                       String personalInterest,
                       String phoneNumber,
                       Integer age,
                       String gender,
                       String location,
                       String postalCode,
                       String biography,
                       String peopleAccessLevel,
                       String firstName,
                       String lastName,
                       User user) {
        this.email = email;
        this.personalInterest = personalInterest;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.gender = gender;
        this.location = location;
        this.postalCode = postalCode;
        this.biography = biography;
        this.peopleAccessLevel = PeopleAccessLevel.fromString(peopleAccessLevel);
        this.firstName = firstName;
        this.lastName = lastName;
        this.user = user;
    }

    public String getPersonalInterest() {
        return personalInterest;
    }

    public void setPersonalInterest(String personalInterest) {
        this.personalInterest = personalInterest;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
}

