package com.point_consulting.testindoormap;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.provider.BaseColumns;

import com.point_consulting.pc_indoormapoverlaylib.Manager;

import org.json.JSONObject;

import java.util.List;

public class MyCustomSuggestionProvider extends ContentProvider {

    @Override
    public boolean onCreate() {
        //mDictionary = new DictionaryDatabase(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        final String q = selectionArgs[0];

        final Context context = getContext();
        MyApplication app = (MyApplication)context;
        Manager manager = app.m_manager;
        if (manager == null)
        {
            return null;
        }

        final List<Manager.FeatureDesc> fdl = manager.search(q, MyAppUtils.s_searchField);
        List<MyApplication.OutdoorFeature> ofdl = app.searchOutdoorSync(q);

        if (fdl == null && ofdl == null)
        {
            return null;
        }

        int sz = 0;
        if (fdl != null)
        {
            sz += fdl.size();
        }
        if (ofdl != null)
        {
            sz += ofdl.size();
        }

        String[] obj = new String[5];
        MatrixCursor mq = new MatrixCursor(new String[]{BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1, SearchManager.SUGGEST_COLUMN_TEXT_2, SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID, SearchManager.SUGGEST_COLUMN_INTENT_EXTRA_DATA}, sz);

        int k = 0;
        if (fdl != null) {
            for (Manager.FeatureDesc fd : fdl) {
                final JSONObject props = manager.propsForFeature(fd.m_featureIndex);
                final String subtitle = props.optString(MyAppUtils.SUBTITLE_FIELD);

                obj[0] = String.valueOf(k);
                obj[1] = fd.m_name;
                obj[2] = subtitle;
                obj[3] = String.valueOf(fd.m_featureIndex);
                obj[4] = null;
                mq.addRow(obj);
                ++k;
            }
        }

        if (ofdl != null) {
            for (MyApplication.OutdoorFeature ofd : ofdl) {
                obj[0] = String.valueOf(k);
                obj[1] = ofd.m_title;
                obj[2] = ofd.m_subtitle;
                obj[3] = "-1";
                obj[4] = ofd.m_placeId;
                mq.addRow(obj);
                ++k;
            }
        }

        return mq;
    }

    @Override
    public String getType(Uri uri)
    {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }
}
