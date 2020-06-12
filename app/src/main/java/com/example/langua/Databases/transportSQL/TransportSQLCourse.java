package com.example.langua.Databases.transportSQL;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.langua.Databases.DBHelpers.DBHelperCourse;
import com.example.langua.Databases.FirebaseAccess.pojo.Course;

import java.util.ArrayList;

import static com.example.langua.Databases.contracts.CourseContract.COLUMN_ACCESS;
import static com.example.langua.Databases.contracts.CourseContract.COLUMN_NAME;
import static com.example.langua.Databases.contracts.CourseContract.COLUMN_VERSION;
import static com.example.langua.Databases.contracts.CourseContract.TABLE_NAME;

public final class TransportSQLCourse {


    private SQLiteDatabase database;
    private SQLiteOpenHelper dbHelper;


    public TransportSQLCourse(Context context) {
        dbHelper = new DBHelperCourse(context);
        database = dbHelper.getWritableDatabase();
    }

    public void closeDb(){
        database.close();
    }

    public ArrayList<Course> getAllCources(){

        Cursor cursor = database.query(TABLE_NAME,null,
                null, null,
                null, null, null);


        ArrayList<Course> cources = new ArrayList<>();
        while (cursor.moveToNext()) {
            cources.add(getCourse(cursor));
        }
        return cources;
    }


    private Course getCourse(Cursor cursor){
        String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
        int dialect = cursor.getInt(cursor.getColumnIndex(COLUMN_VERSION));
        boolean access;
        //= (boolean)cursor.getInt(cursor.getColumnIndex(COLUMN_ACCESS));
        Log.i("SQL - get", Integer.toString(cursor.getInt(cursor.getColumnIndex(COLUMN_VERSION))));
        //return new Course(name, dialect, true);
        return new Course(name, dialect, true);
    }
}
