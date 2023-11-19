package com.example.attendanceapplication.Model;

public class Employee {
    private String id;
    private String name;
    private String department;
    private String position;
    private String avatarURL;

    Employee(){}
    Employee(String id, String name, String department, String position, String avatarURL){
        this.id = id;
        this.name = name;
        this.department = department;
        this.position = position;
        this.avatarURL = avatarURL;
    }
    public String getID(){return id;}
    public String getName(){return name;}
    public String getDepartment(){return department;}
    public String getPosition(){return position;}
    public String getavatarURL() {return avatarURL;}
}
