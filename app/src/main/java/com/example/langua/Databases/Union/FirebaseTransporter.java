package com.example.langua.Databases.Union;

import com.example.langua.Databases.FirebaseAccess.pojo.Course;

import java.util.ArrayList;

public interface FirebaseTransporter {
    void onNoCourcesGet();
    void onAllCourcesGet(ArrayList<Course> courses);
}
