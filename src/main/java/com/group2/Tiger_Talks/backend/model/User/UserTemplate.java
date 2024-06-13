package com.group2.Tiger_Talks.backend.model.User;

import jakarta.persistence.*;


@Entity
@Table(name = "user_template")
public class UserTemplate {
    @Id
    private String email;
    private String bannerID;    // B00XXXXXX (X represents integer from 0 to 9)
    private String password;
    private UserLevel userLevel;   // admin / user
    private UserStatus status;      // blocked / pending / active

    @OneToOne(mappedBy = "userTemplate", cascade = CascadeType.ALL, orphanRemoval = true)
    private User user;

    public UserTemplate(String BannerID,
                        String email,
                        String password) {
        this.bannerID = BannerID;
        this.email = email;
        this.password = password;
        this.user = null;
    }

    public UserTemplate() {
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

    public String getBannerID() {
        return bannerID;
    }

    public void setBannerID(String BannerID) {
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

    public UserLevel getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(UserLevel userLevel) {
        this.userLevel = userLevel;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}

