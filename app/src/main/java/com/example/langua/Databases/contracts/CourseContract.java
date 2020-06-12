package com.example.langua.Databases.contracts;


import static com.example.langua.Databases.contracts.Entry.TYPE_BOOL;
import static com.example.langua.Databases.contracts.Entry.TYPE_INTEGER;
import static com.example.langua.Databases.contracts.Entry.TYPE_TEXT;

public class CourseContract {

    public static final String TABLE_NAME = "courses";


    public static final String COLUMN_VERSION = "vers";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ACCESS = "accss";

    private static final String CREATE_HEADER_COMMAND = " (" + COLUMN_NAME + " " + TYPE_TEXT + ", " +
            COLUMN_ACCESS + " " + TYPE_BOOL + ", " +
            COLUMN_VERSION + " " + TYPE_INTEGER +  ")";

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
            CREATE_HEADER_COMMAND;


    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
