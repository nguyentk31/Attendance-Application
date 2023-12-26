package com.example.attendanceapplication.Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Employee implements Serializable {
    private String id;
    private String name;
    private String position;
    private String avatarURL;
    private float baseSalary;
    private ArrayList<LocalDateTime> attendances;


    public Employee(){}
    public Employee(Employee x){
        this.id = x.id;
        this.name = x.name;
        this.position = x.position;
        this.avatarURL = x.avatarURL;
        this.baseSalary = x.baseSalary;
        this.attendances = new ArrayList<>();
    }
    Employee(String id, String name, String position, String avatarURL, float baseSalary){
        this.id = id;
        this.name = name;
        this.position = position;
        this.avatarURL = avatarURL;
        this.baseSalary = baseSalary;
        this.attendances = new ArrayList<>();
    }
    public String getID(){return id;}
    public String getName(){return name;}
    public String getPosition(){return position;}
    public String getAvatarURL() {return avatarURL;}
    public float getBaseSalary() {return baseSalary;}

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public void setBaseSalary(float baseSalary) {
        this.baseSalary = baseSalary;
    }

    public ArrayList<LocalDateTime> getAttendances() {
        return attendances;
    }

    public void setAttendances(ArrayList<LocalDateTime> attendances) {
        this.attendances = attendances;
    }
}
