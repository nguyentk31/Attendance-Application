package com.example.attendanceapplication.Migrations;

import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class CustomMigration {
    static public Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE `PhongBan` (`MaPhongBan` INTEGER, "
                    + "`TenPhongBan` TEXT, PRIMARY KEY(`MaPhongBan`))");
            database.execSQL("ALTER TABLE `NhanVien` ADD COLUMN `MaPhongBan` INTEGER REFERENCES `PhongBan` (`MaPhongBan`)");

        }
    };
}
