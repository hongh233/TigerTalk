package com.group2.Tiger_Talks.backend.model.User;

import com.group2.Tiger_Talks.backend.model.Utils.ProfileAccessLevel;
import jakarta.persistence.*;

@Entity
public class UserProfile {

    @Id
    private String email;             // PK | FK

    @Column(unique = true)
    private String userName;

    private String personalInterest;

    private String location;

    private String postalCode;

    private String biography;

    private String profileAccessLevel = ProfileAccessLevel.PRIVATE;  // public / protected / private

    private String phoneNumber;

    private int age;

    private String gender;

    private String firstName;

    private String lastName;

    // FK setting
    @OneToOne
    @MapsId
    @JoinColumn(name = "email")
    private UserTemplate userTemplate;


    public UserProfile() {
    }

    public UserProfile(String userName, String email, String personalInterest, String location, String postalCode, String biography, String phoneNumber, int age, String gender, String firstName, String lastName, UserTemplate userTemplate) {
        this.userName = userName;
        this.email = email;
        this.personalInterest = personalInterest;
        this.location = location;
        this.postalCode = postalCode;
        this.biography = biography;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.gender = gender;
        this.firstName = firstName;
        this.lastName = lastName;
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

    public String getProfileAccessLevel() {
        return profileAccessLevel;
    }

    public void setProfileAccessLevel(String peopleAccessLevel) {
        this.profileAccessLevel = peopleAccessLevel;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
 