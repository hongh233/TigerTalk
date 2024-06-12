package com.group2.Tiger_Talks.backend.model.User;

public enum UserStatus {
    BLOCK,
    PENDING,
    ACTIVE;

    public static UserStatus fromString(String str) {
        return switch (str.toLowerCase()) {
            case "block" -> BLOCK;
            case "pending" -> PENDING;
            case "active" -> ACTIVE;
            default -> throw new IllegalArgumentException("Unknown status: " + str);
        };
    }

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}