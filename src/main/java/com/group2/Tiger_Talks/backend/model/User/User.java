package com.group2.Tiger_Talks.backend.model.User;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User {

    @Id
    private String email;     // PK | FK

    private String role;        // student / instructor / employee
    private String status;      // away / busy / available

    // PK -> FK setting
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserProfile userProfile;


    // FK setting
    @OneToOne
    @MapsId
    @JoinColumn(name = "email")
    private UserTemplate userTemplate;


    public User(String email,  String role, String status, UserTemplate userTemplate) {
        this.email = email;
        this.role = role;
        this.status = status;
        this.userProfile = null;
        this.userTemplate = userTemplate;
    }

    public User() {
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public UserTemplate getUserTemplate() {
        return userTemplate;
    }

    public void setUserTemplate(UserTemplate userTemplate) {
        this.userTemplate = userTemplate;
    }

}
