package com.whatsmedia.ttia.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class VocabularyDB implements ISQLTableTools {
    public final static String TAG_TABLE_NAME = VocabularyDB.class.getSimpleName();
    public final static long TAG_INSERT_FAILED = -1;//插入data 失敗

    private final static String TAG_ID = "_id";
    private final static String TAG_VOCABULARY = "vocabulary";
    private final static String TAG_TRANSLATION = "translation";
    private final static String TAG_EXAMPLE = "example";
    public final static String TAG_CREATE_TABLE = "CREATE TABLE " +
            TAG_TABLE_NAME + " ( " +
            TAG_ID + " INTEGER NOT NULL, " +
            TAG_VOCABULARY + " TEXT NOT NULL, " +
            TAG_TRANSLATION + " TEXT NOT NULL, " +
            TAG_EXAMPLE + " TEXT NOT NULL) ";

    private SQLiteDatabase mDb;

    public VocabularyDB(Context context) {
        mDb = MyDBHelper.getDatabase(context);
    }

    @Override
    public void close() {
        if (mDb != null)
            mDb.close();
    }

    @Override
    public long insert(Object object) {
        VocabularyData data = (VocabularyData) object;
        ContentValues values = new ContentValues();

        values.put(TAG_ID, System.currentTimeMillis());
        values.put(TAG_VOCABULARY, data.getVocabulary());
        values.put(TAG_TRANSLATION, data.getTranslation());
        values.put(TAG_EXAMPLE, data.getExample());
        return mDb.insert(TAG_TABLE_NAME, null, values);
    }

    @Override
    public boolean update(Object object) {
        VocabularyData data = (VocabularyData) object;
        ContentValues values = new ContentValues();
        if (data != null) {
            if (data.getVocabulary() != null)
                values.put(TAG_VOCABULARY, data.getVocabulary());
            if (data.getTranslation() != null)
                values.put(TAG_TRANSLATION, data.getTranslation());
            if (data.getExample() != null)
                values.put(TAG_EXAMPLE, data.getExample());

            String where = TAG_ID + "=" + data.getId();
            Log.i(TAG_TABLE_NAME, "Update:" + where);
            return mDb.update(TAG_TABLE_NAME, values, where, null) > 0;
        } else {
            Log.e(TAG_TABLE_NAME, "data is null");
            return false;
        }
    }

    @Override
    public boolean delete(Object object) {
        String id = (String) object;
        String where = TAG_ID + "=" + id;
        Log.i(TAG_TABLE_NAME, "delete:" + where);
        return mDb.delete(TAG_TABLE_NAME, where, null) > 0;
    }

    @Override
    public List<Object> getAll() {
        List<Object> result = new ArrayList<>();
        Cursor cursor = mDb.query(TAG_TABLE_NAME, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }
        return result;
    }

    @Override
    public List<Object> getByCondition(Object object) {
        return null;
    }

    @Override
    public Object getRecord(Cursor cursor) {
        VocabularyData data = new VocabularyData();
        data.setId(cursor.getString(cursor.getColumnIndex(TAG_ID)));
        data.setVocabulary(cursor.getString(cursor.getColumnIndex(TAG_VOCABULARY)));
        data.setTranslation(cursor.getString(cursor.getColumnIndex(TAG_TRANSLATION)));
        data.setExample(cursor.getString(cursor.getColumnIndex(TAG_EXAMPLE)));
        return data;
    }
}
