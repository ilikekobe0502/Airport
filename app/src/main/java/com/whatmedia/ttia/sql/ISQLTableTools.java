package com.whatmedia.ttia.sql;

import android.database.Cursor;

import java.util.List;

public interface ISQLTableTools {
    void close();

    long insert(Object object);

    boolean update(Object object);

    boolean delete(Object object);

    List<Object> getAll();

    List<Object> getByCondition(Object object);

    Object getRecord(Cursor cursor);
}
