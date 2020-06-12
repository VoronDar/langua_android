package com.example.langua.Databases.DBHelpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.langua.Databases.contracts.CourseContract;
import com.example.langua.Databases.contracts.VocabularyContract;

public class DBHelperCourse extends SQLiteOpenHelper {

    private static final String NAME = "course.db";
    private static final int VERSION = 1;

    public DBHelperCourse(@Nullable Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CourseContract.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(CourseContract.DROP_TABLE);
        onCreate(db);
    }
}
