package com.whatsmedia.ttia.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {

    private final static String TAG_DATABASE_NAME = "VocabularyCard.db";
    private final static int TAG_VERSION = 1;
    private static SQLiteDatabase mDatabase;

    public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(VocabularyDB.TAG_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + VocabularyDB.TAG_TABLE_NAME);
        onCreate(db);
    }

    public static SQLiteDatabase getDatabase(Context context) {
        if (mDatabase == null || !mDatabase.isOpen()) {
            mDatabase = new MyDBHelper(context, TAG_DATABASE_NAME, null, TAG_VERSION).getWritableDatabase();
        }
        return mDatabase;
    }
}
