package com.example.attendanceapplication.Model;

public class Employee {
    private String ID;
    private String Name;
    private String Position;
    private int Avatar;
    Employee(String ID,String Name,String Position, int Avatar){
        this.ID = ID;
        this.Name = Name;
        this.Position = Position;
        this.Avatar = Avatar;
    }
    public String getName(){
        return Name;
    }
    public String getID(){
        return ID;
    }
    public String getPosition(){
        return Position;
    }
    public int getAvatar(){ return Avatar;}
}
