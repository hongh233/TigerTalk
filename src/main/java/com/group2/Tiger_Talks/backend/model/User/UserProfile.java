package com.group2.Tiger_Talks.backend.model.User;
import jakarta.persistence.*;

@Entity
@Table(name = "user_profile")
public class UserProfile {
    @Id
    private Integer userId;             // PK | FK
    private String personalInterest;
    private String phoneNumber;
    private Integer age;
    private String gender;
    private String location;
    private String postalCode;
    private String biography;
    private String peopleAccessLevel;  // public / protected / private
    private String firstName;
    private String lastName;


    // FK setting
    @OneToOne
    @MapsId
    @JoinColumn(name = "userId")
    private User user;


    public UserProfile() {
    }

    public UserProfile(String personalInterest,
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
        this.personalInterest = personalInterest;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.gender = gender;
        this.location = location;
        this.postalCode = postalCode;
        this.biography = biography;
        this.peopleAccessLevel = peopleAccessLevel;
        this.firstName = firstName;
        this.lastName = lastName;
        this.user = user;
    }

    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
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
    public String getPeopleAccessLevel() {
        return peopleAccessLevel;
    }
    public void setPeopleAccessLevel(String peopleAccessLevel) {
        this.peopleAccessLevel = peopleAccessLevel;
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

}

