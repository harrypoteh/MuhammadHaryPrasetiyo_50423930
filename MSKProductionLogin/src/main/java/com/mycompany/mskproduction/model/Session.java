package com.mycompany.mskproduction.model;

public final class Session {

    private static User user;

    private Session() {
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User value) {
        user = value;
    }

    public static void clear() {
        user = null;
    }

    public static String getNama() {
        return user == null ? "Admin" : user.getNama();
    }
}
