package com.example.attendanceapplication.Model;

import android.annotation.SuppressLint;

import java.time.LocalDate;

public class Event {
    public enum Type {
        meeting,
        holiday
    }
    private LocalDate date;
    private Type type;
    private String note;
    private String from;
    private String status;

    public Event() {}

    public LocalDate getDate() {
        return date;
    }

    @SuppressLint("NewApi")
    public void setDate(String date) {
        this.date = LocalDate.parse(date);
    }

    public Type getType() {
        return type;
    }

    public void setType(String type) {
        this.type = Type.valueOf(type);
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
