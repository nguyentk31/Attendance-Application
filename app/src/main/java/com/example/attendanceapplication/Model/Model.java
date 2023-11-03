package com.example.attendanceapplication.Model;

public class Model {

    String id,mail,name,profilepic,role;

    public Model() {
    }

    public Model(String id, String mail, String name, String profilepic, String role) {
        this.id = id;
        this.mail = mail;
        this.name = name;
        this.profilepic = profilepic;
        this.role = role;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getProfilepic() {
        return profilepic;
    }
    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}

