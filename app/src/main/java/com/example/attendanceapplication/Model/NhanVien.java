package com.example.attendanceapplication.Model;

public class NhanVien {
    private static int idCounter = 0;
    private String id;
    private String email;
    private String password;

    public NhanVien(String email, String password) {
        this.id = "NV" + idCounter++;
        this.email = email;
        this.password = password;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}