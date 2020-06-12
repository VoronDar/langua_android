package com.example.langua.Databases.DBHelpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static com.example.langua.Databases.contracts.PhraseologyContract.PhraseologyEntry.CREATE_COMMON_COMMAND;
import static com.example.langua.Databases.contracts.PhraseologyContract.PhraseologyEntry.DROP_TABLE;

public class DBHelperPhraseology extends SQLiteOpenHelper {

    public static final String NAME = "phraseologyRUS.db";
    public static final int VERSION = 1;

    public DBHelperPhraseology(@Nullable Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_COMMON_COMMAND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }
}
