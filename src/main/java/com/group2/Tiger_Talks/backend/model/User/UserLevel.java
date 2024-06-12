package com.group2.Tiger_Talks.backend.model.User;

public enum UserLevel {
    ADMIN,
    USER;

    public static UserLevel fromString(String str) {
        return switch (str.toLowerCase()) {
            case "admin" -> ADMIN;
            case "user" -> USER;
            default -> throw new IllegalArgumentException("Unknown user level: " + str);
        };
    }

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
