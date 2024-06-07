package com.group2.Tiger_Talks.backend.model.User;

import jakarta.persistence.*;


@Entity
public class User {
    static final String B_00 = "B00";
    @Id
    @Column(name = B_00)
    private int B00;

    @OneToOne
    @JoinColumn(referencedColumnName = User.B_00)
    private UserProfile userProfile;

    @OneToOne
    @JoinColumn(referencedColumnName = User.B_00)
    private UserTemplate userTemplate;

    public User(int b00, UserProfile userProfile, UserTemplate userTemplate) {
        B00 = b00;
        this.userProfile = userProfile;
        this.userTemplate = userTemplate;
    }

    public User() {
    }

    public int getB00() {
        return B00;
    }

    public void setB00(int b00) {
        B00 = b00;
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
