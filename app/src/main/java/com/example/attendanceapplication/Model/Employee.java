package com.example.attendanceapplication.Model;

public class Employee {
    private String id;
    private String name;
    private String department;
    private String position;
    private String avatarURL;

    Employee(){}
    public Employee(Employee x){
        this.id = x.id;
        this.name = x.name;
        this.department = x.department;
        this.position = x.position;
        this.avatarURL = x.avatarURL;
    }
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
    public String getAvatarURL() {return avatarURL;}
}
