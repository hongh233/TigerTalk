package com.group2.Tiger_Talks.backend.model.User;


import jakarta.persistence.*;


@Entity
public class UserProfile {

    @Id
    @Column(name = User.B_00)
    @JoinColumn(referencedColumnName = User.B_00)
    int B00;

    private int phone_number;
    private String bio;
    private Role role;
    private String postal_code;
    private Status status;

    public UserProfile(int B00,
                       int phone_number,
                       String bio,
                       Role role,
                       String postal_code,
                       Status status) {
        this.B00 = B00;
        this.phone_number = phone_number;
        this.bio = bio;
        this.role = role;
        this.postal_code = postal_code;
        this.status = status;
    }

    public UserProfile() {

    }
}