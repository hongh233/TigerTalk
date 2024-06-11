package com.group2.Tiger_Talks.backend.model.User;
import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User {

    @Id
    private Integer userId;     // PK | FK
    @Column(unique = true)
    private String userName;
    private String role;        // student / instructor / employee
    private String status;      // away / busy / available


    // PK -> FK setting
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserProfile userProfile;


    // FK setting
    @OneToOne
    @MapsId
    @JoinColumn(name = "userId")
    private UserTemplate userTemplate;


    public User(String userName, String role, String status, UserTemplate userTemplate) {
        this.userName = userName;
        this.role = role;
        this.status = status;
        this.userProfile = null;
        this.userTemplate = userTemplate;
    }

    public User() {
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
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
