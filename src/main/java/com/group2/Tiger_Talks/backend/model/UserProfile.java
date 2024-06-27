package com.group2.Tiger_Talks.backend.model;

import com.group2.Tiger_Talks.backend.model.Socials.Friendship;
import com.group2.Tiger_Talks.backend.model.Socials.FriendshipRequest;
import com.group2.Tiger_Talks.backend.model.Utils.Role;
import com.group2.Tiger_Talks.backend.model.Utils.UserLevel;
import com.group2.Tiger_Talks.backend.model.Utils.UserStatus;
import com.group2.Tiger_Talks.backend.model.Utils.ProfileAccessLevel;
import jakarta.persistence.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Entity
public class UserProfile {
    @Id
    private String email;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Friendship> sentFriendships = new LinkedList<>();

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Friendship> receivedFriendships = new LinkedList<>();

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FriendshipRequest> sentFriendshipRequests = new LinkedList<>();

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FriendshipRequest> receivedFriendshipRequests = new LinkedList<>();


    private String password;
    private String userLevel = UserLevel.USER;   // admin / user
    private String status = UserStatus.PENDING;      // blocked / pending / active
    private boolean isValidated = false;
    private String[] securityQuestions;
    private String[] securityQuestionsAnswer;
    private String role = Role.DEFAULT;        // default / student / instructor / employee
    private boolean isOnline = false;

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

    public UserProfile(String firstName,
                       String lastName,
                       int age,
                       String gender,
                       String userName,
                       String email,
                       String password,
                       String[] securityQuestionsAnswer,
                       String[] securityQuestions) {
        this.email = email;
        this.password = password;
        this.securityQuestionsAnswer = securityQuestionsAnswer;
        this.securityQuestions = securityQuestions;
        this.userName = userName;
        this.age = age;
        this.gender = gender;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserProfile() {
    }

    // Getters and setters

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isValidated() {
        return isValidated;
    }

    public void setValidated(boolean validated) {
        isValidated = validated;
    }

    public String[] getSecurityQuestionsAnswer() {
        return securityQuestionsAnswer;
    }

    public void setSecurityQuestionsAnswer(String[] securityQuestionsAnswer) {
        this.securityQuestionsAnswer = securityQuestionsAnswer;
    }

    public String[] getSecurityQuestions() {
        return securityQuestions;
    }

    public void setSecurityQuestions(String[] securityQuestions) {
        this.securityQuestions = securityQuestions;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public void setProfileAccessLevel(String profileAccessLevel) {
        this.profileAccessLevel = profileAccessLevel;
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

    public Optional<String> findAnswerForSecurityQuestion(String securityQuestion) {
        for (int i = 0; i < securityQuestions.length; i++) {
            if (securityQuestions[i].equals(securityQuestion)) {
                return Optional.of(securityQuestionsAnswer[i]);
            }
        }
        return Optional.empty();
    }
}
