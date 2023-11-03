package com.example.attendanceapplication.Models.ConfigureRelation;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.attendanceapplication.Models.RoomModel;
import com.example.attendanceapplication.Models.UserModel;

import java.util.List;

public class RoomWithUsers {
    @Embedded
    public RoomModel roomModel;
    @Relation(
            parentColumn = "MaPhongBan",
            entityColumn = "MaPhongBan"
    )
    public List<UserModel> users;
}
