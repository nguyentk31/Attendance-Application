package com.example.attendanceapplication.Model;

public class NhanVien {
    private String email;
    private String password;

    public NhanVien(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}