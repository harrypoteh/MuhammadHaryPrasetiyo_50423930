package com.mycompany.mskproduction.model;

public class User {

    private int id;
    private String username;
    private String nama;
    private String role;

    public User() {
    }

    public User(int id, String username, String nama, String role) {
        this.id = id;
        this.username = username;
        this.nama = nama;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getInisial() {
        if (nama == null || nama.isBlank()) {
            return "A";
        }
        return nama.substring(0, 1).toUpperCase();
    }
}
