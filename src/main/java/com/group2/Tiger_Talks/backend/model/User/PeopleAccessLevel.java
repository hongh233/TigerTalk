package com.group2.Tiger_Talks.backend.model.User;

public enum PeopleAccessLevel {
    PUBLIC,
    PROTECTED,
    PRIVATE;

    public static PeopleAccessLevel fromString(String accessLevel) {
        return switch (accessLevel.toLowerCase()) {
            case "public" -> PUBLIC;
            case "protected" -> PROTECTED;
            case "private" -> PRIVATE;
            default -> throw new IllegalArgumentException("Unknown access level: " + accessLevel);
        };
    }

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}