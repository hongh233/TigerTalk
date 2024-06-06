package com.example.Tiger_Talks.backend.model.User;


import jakarta.persistence.*;

@Entity
public class UserTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Override
    public String toString() {
        return "UserTemplate{" +
                "\nuserId=" + userId +
                ",\n userName='" + userName + '\'' +
                ",\n B00=" + B00 +
                ",\n hashedPassword='" + hashedPassword + '\'' +
                ",\n firstName='" + firstName + '\'' +
                ",\n lastName='" + lastName + '\'' +
                ",\n age=" + age +
                ",\n email='" + email + '\'' +
                ",\n gender='" + gender + '\'' +
                ",\n isValidated=" + isValidated +
                "\n}\n";
    }

    @Column(unique = true)
    private String userName;

    @Column(unique = true, name = User.B_00)
    private int B00;

    private String hashedPassword;
    private String firstName;
    private String lastName;
    private int age;
    private String email;
    private String gender;
    private boolean isValidated;

    public UserTemplate(String userName,
                        String firstName,
                        String lastName,
                        int age,
                        String gender,
                        String hashedPassword,
                        String email,
                        boolean isValidated) {
        this.userName = userName;
        this.hashedPassword = hashedPassword;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.gender = gender;
        this.isValidated = isValidated;
    }

    public UserTemplate() {

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

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
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

    public boolean isValidated() {
        return isValidated;
    }

    public void setValidated(boolean validated) {
        isValidated = validated;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getB00() {
        return B00;
    }

    public void setB00(int b00) {
        B00 = b00;
    }
}

