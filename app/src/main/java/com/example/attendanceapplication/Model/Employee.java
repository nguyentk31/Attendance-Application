package com.example.attendanceapplication.Model;

import android.os.Build;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

public class Employee {

    public enum Gender {
        male,
        female
    }
    public enum Status {
        requesting,
        work,
        disabling,
        disable,
        recovering,
        firing,
        fired,
    }
    public enum Position {
        manager,
        staff
    }

    private String authid;
    private String id;
    private String mail;
    private String name;
    private Gender gender;
    private String birthday;
    private Position position;
    private String tagid;
    private String phone;
    private Status status;
    private String avatarURL;
    private HashMap<LocalDate, LocalTime> attendances;

    public Employee(){}

    public Employee(Employee x){
        this.authid = x.getAuthid();
        this.id = x.getId();
        this.mail = x.getMail();
        this.name = x.getName();
        this.gender = x.getGender();
        this.birthday = x.getBirthday();
        this.position = x.getPosition();
        this.tagid = x.getTagid();
        this.phone = x.getPhone();
        this.status = x.getStatus();
        this.avatarURL = x.getAvatarURL();
        if (this.attendances == null) this.attendances = new HashMap<>();
        else this.attendances.clear();
        x.getAttendances().forEach((d, t) -> this.attendances.put(d, t));
    }

    public String getAuthid() {
        if (authid == null) {
            authid = position.name()+id;
        }
        return authid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMail() {
        if (mail == null) {
            mail = id+"@nhom1.com";
        }
        return mail;
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

    public void setGender(String gender) {
        this.gender = Gender.valueOf(gender);
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = Position.valueOf(position);
    }

    public String getTagid() {
        return tagid;
    }

    public void setTagid(String tagid) {
        this.tagid = tagid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = Status.valueOf(status);
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public HashMap<LocalDate, LocalTime> getAttendances() {
        return attendances;
    }

    public void setAttendances(HashMap<String, String> attendances) {
        if (this.attendances == null) this.attendances = new HashMap<>();
        else this.attendances.clear();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            attendances.forEach((d, t) -> this.attendances.put(LocalDate.parse(d), LocalTime.parse(t)));
        }
    }
}
