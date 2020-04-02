package com.example.langua.contracts;

import android.provider.BaseColumns;

public abstract class Entry implements BaseColumns {

    public static final String COLUMN_REPEAT_LEVEL              = "repeat";
    public static final String COLUMN_PRACTICE_LEVEL            = "practice";
    public static final String COLUMN_REPETITION_DAY            = "day";
    public static final String COLUMN_SIMILAR                   = "similar";
    public static final String COLUMN_MISTAKES                  = "mistakes";

    public static final String TYPE_TEXT                        = "TEXT";
    public static final String TYPE_INTEGER                     = "INTEGER";
}