package com.example.attendanceapplication.Model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

public class Employee {

    public enum Gender {
        Male,
        Female
    }
    public enum Status {
        Working,
        Fired
    }
    public enum Position {
        Manager,
        Staff
    }

    private String id;
    private String name;
    private Gender gender;
    private Position position;
    private Status status;
    private String avatarURL;
    private LocalDate birthday;
    private String authId;
    private String tagId;


    //private String phone;
    private HashMap<LocalDate, LocalTime> attendances;

    public Employee(){
        attendances = null;
    }

    public Employee(Employee x){
        this.id = x.getId();
        this.name = x.getName();
        this.gender = x.getGender();
        this.position = x.getPosition();
        this.status = x.getStatus();
        this.avatarURL = x.getAvatarURL();
        this.birthday = x.getBirthday();
        this.authId = x.getAuthId();
        this.tagId = x.getTagId();
        //this.phone = x.getPhone();
        if (this.attendances == null) this.attendances = new HashMap<>();
        else this.attendances.clear();
        x.getAttendances().forEach((d, t) -> this.attendances.put(d, t));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = Gender.values()[gender];
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = Position.values()[position];
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = Status.values()[status];
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }
    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthDay) {
        this.birthday = LocalDate.parse(birthDay);
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String fbAuthId) {
        this.authId = fbAuthId;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }
//    public String getPhone(){ return phone; }
//
//    public void setPhone(String phone) { this.phone = phone; }

    public HashMap<LocalDate, LocalTime> getAttendances() {
        return attendances;
    }

    public void setAttendances(HashMap<String, String> attendances) {
        if (this.attendances == null) this.attendances = new HashMap<>();
        else this.attendances.clear();
        attendances.forEach((d, t) -> this.attendances.put(LocalDate.parse(d), LocalTime.parse(t)));
    }
}
