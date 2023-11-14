package com.example.attendanceapplication.DataAccess;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import com.example.attendanceapplication.Converters.Converter;
import com.example.attendanceapplication.Models.AcceptReport;
import com.example.attendanceapplication.Models.ManagerModel;
import com.example.attendanceapplication.Models.ReportModel;
import com.example.attendanceapplication.Models.RoleModel;
import com.example.attendanceapplication.Models.RoomModel;
import com.example.attendanceapplication.Models.StatusModel;
import com.example.attendanceapplication.Models.TimeKeepingModel;
import com.example.attendanceapplication.Models.UserModel;
import com.example.attendanceapplication.Repository.RoleDao;
import com.example.attendanceapplication.Repository.RoomDao;
import com.example.attendanceapplication.Repository.UserDao;

@Database(entities = {UserModel.class , RoomModel.class , TimeKeepingModel.class , StatusModel.class, RoleModel.class, ReportModel.class, ManagerModel.class, AcceptReport.class}, version = 6 , autoMigrations = {
        @AutoMigration(from = 1 , to=2),
        @AutoMigration(from = 2 , to=3),
        @AutoMigration(from = 3 , to=4),
})
@TypeConverters({Converter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract RoomDao roomDao();
    public abstract RoleDao roleDao();
}