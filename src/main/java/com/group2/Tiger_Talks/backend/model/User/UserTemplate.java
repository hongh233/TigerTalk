package com.group2.Tiger_Talks.backend.model.User;

import jakarta.persistence.*;


@Entity
@Table(name = "user_template")
public class UserTemplate {
    public static final String ADMIN = "admin";
    public static final String USER = "user";
    public static final String BLOCKED = "blocked";
    public static final String PENDING = "pending";
    public static final String ACTIVE = "active";

    @Id
    private String email;

    @Column(name = "user_name", unique = true)
    private String userName;
    @Column(unique = true)
    private int bannerID;    // B00XXXXXX (X represents integer from 0 to 9)
    private String password;
    private String userLevel;   // admin / user
    private String status;      // blocked / pending / active
    private int age;
    private String gender;
    private String firstName;
    private String lastName;
    private boolean isValidated = false;
    private String[] securityQuestions;
    private String[] securityQuestionsAnswer;
    private String role = "default";        // default / student / instructor / employee
    private boolean isOnline = false;


    @OneToOne(mappedBy = "userTemplate", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserProfile userProfile = null;

    public UserTemplate(String firstName,
                        String lastName,
                        Integer age,
                        String gender,
                        String userName,
                        int bannerID,
                        String email,
                        String password,
                        String[] securityQuestionsAnswer,
                        String[] securityQuestions) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.userName = userName;
        this.bannerID = bannerID;
        this.email = email;
        this.password = password;
        this.securityQuestionsAnswer = securityQuestionsAnswer;
        this.securityQuestions = securityQuestions;
    }

    public UserTemplate() {
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

    private String toString_() {
        return "user_template{" +
                ",\n bannerID='" + bannerID + '\'' +
                ",\n email='" + email + '\'' +
                ",\n password='" + password + '\'' +
                ",\n userLevel='" + userLevel + '\'' +
                ",\n status='" + status + '\'' +
                "\n}\n";
    }

    public int getBannerID() {
        return bannerID;
    }

    public void setBannerID(int BannerID) {
        this.bannerID = BannerID;
    }

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

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

}

