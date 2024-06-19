package com.group2.Tiger_Talks.backend.model;

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

    @Column(unique = true)
    private String userName;

    private int bannerID;    // B00XXXXXX (X represents integer from 0 to 9)
    private String password;
    private String userLevel;   // admin / user
    private String status;      // blocked / pending / active
    private Integer age;
    private String gender;
    private String firstName;
    private String lastName;
    private boolean isValidated;

    @OneToOne(mappedBy = "userTemplate", cascade = CascadeType.ALL, orphanRemoval = true)
    private User user;

    public UserTemplate(String firstName,
                        String lastName,
                        Integer age,
                        String userName,
                        String gender,
                        String email,
                        String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.age = age;
        this.gender = gender;
        this.firstName = firstName;
        this.lastName = lastName;

        // Default values
        this.isValidated = false;
        this.user = null;
        this.bannerID = -1;
    }

    public UserTemplate() {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

