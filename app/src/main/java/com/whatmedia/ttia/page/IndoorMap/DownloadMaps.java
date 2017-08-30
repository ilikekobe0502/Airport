package com.whatmedia.ttia.page.IndoorMap;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class DownloadMaps
{
    private static final int s_goodCode = 200;

    private static final String s_api = "http://maps.point-consulting.com/api/";

    private static final String s_vPrefix = "__v__";

    private static String GetKey_(String mapId, String key)
    {
        return String.format("%s%s_%s", s_vPrefix, mapId, key);
    }

    private Context m_context;
    private Delegate m_delegate;

    static final int DOWNLOAD_MAPS_CHECK_RESULT_ERROR = 0;
    static final int DOWNLOAD_MAPS_CHECK_RESULT_READY = 1;
    static final int DOWNLOAD_MAPS_CHECK_RESULT_DOWNLOADING = 2;

    interface Delegate
    {
        void onCheckMapUpdatesFinishedWithResult(int checkResult);
        void onDownloadMapsFinishedWithError(String error);
    }

    static File GetDir(Context context, String mapId)
    {
        return new File(context.getFilesDir(), mapId);
    }

    DownloadMaps(Context context, Delegate delegate)
    {
        m_context = context;
        m_delegate = delegate;
    }

    private static InputStream GetInputStream(String cmd)
    {
        URL url;
        try {
            url = new URL(cmd);
        }
        catch (MalformedURLException ex)
        {
            return null;
        }

        HttpURLConnection connection;
        try {
            connection = (HttpURLConnection)url.openConnection();
        }
        catch (IOException ex)
        {
            return null;
        }
        if (connection == null)
        {
            return null;
        }

        try {
            connection.setRequestMethod("GET");
        }
        catch (ProtocolException ex)
        {
            return null;
        }

        connection.setRequestProperty("Authorization", "Bearer 123456");

        try {
            connection.connect();
        }
        catch (IOException ex)
        {
            return null;
        }

        int statusCode;
        try
        {
            statusCode = connection.getResponseCode();
        }
        catch (IOException ex)
        {
            return null;
        }
        if (statusCode != s_goodCode)
        {
            return null;
        }

        InputStream ins;
        try
        {
            ins = connection.getInputStream();
        }
        catch (IOException ex)
        {
            return null;
        }
        return ins;
    }

    private String Filename_(String layerName)
    {
        if (layerName.equalsIgnoreCase("routes"))
        {
            return "Routes.json";
        }
        if (layerName.equalsIgnoreCase("categorykeywords"))
        {
            return "CategoryKeywords.txt";
        }
        return layerName.substring(0, 1).toUpperCase() + layerName.substring(1) + ".geojson";
    }

    private class MyDownloadTask extends AsyncTask<Void, Void, Boolean>
    {
        private final Context m_context;
        private final String m_mapId;
        private final List<Map.Entry<String, Integer> > m_entries;

        MyDownloadTask(Context context, String mapId, List<Map.Entry<String, Integer> > entries)
        {
            m_context = context;
            m_mapId = mapId;
            m_entries = entries;
        }

        @Override
        protected Boolean doInBackground(Void... params)
        {
            final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(m_context);
            final SharedPreferences.Editor ed = pref.edit();
            final File dir = GetDir(m_context, m_mapId);
            dir.mkdirs();
            for (Map.Entry<String, Integer> entry: m_entries)
            {
                final String k = entry.getKey();
                final String cmd = String.format("%sget_map/?map_id=%s&type=%s", s_api, m_mapId, k);
                final InputStream ins = GetInputStream(cmd);
                if (ins != null)
                {
                    final File file = new File(dir, Filename_(k));
                    FileOutputStream fos;
                    try {
                        fos = new FileOutputStream(file);
                    }
                    catch (FileNotFoundException ex)
                    {
                        continue;
                    }

                    final InputStream bis = new BufferedInputStream(ins, 8192);
                    final byte[] data = new byte[4096];

                    boolean ok = true;
                    while (true)
                    {
                        int count;
                        try {
                            count = bis.read(data);
                        }
                        catch (IOException ex)
                        {
                            ok = false;
                            break;
                        }
                        if (-1 == count)
                        {
                            break;
                        }
                        try {
                            fos.write(data, 0, count);
                        }
                        catch (IOException ex)
                        {
                            ok = false;
                            break;
                        }
                    }

                    try {
                        fos.close();
                    } catch (IOException ex) {}

                    final String key = GetKey_(m_mapId, k);
                    if (!ok)
                    {
                        file.delete();
                        ed.remove(key);
                    }
                    else
                    {
                        ed.putInt(key, entry.getValue());
                    }
                }
            }
            ed.commit();
            return true; // ktodo
        }

        @Override
        protected void onPostExecute(Boolean result)
        {
            m_delegate.onDownloadMapsFinishedWithError(null);
        }
    }

    private class MyCheckTask extends AsyncTask<Void, Void, JSONObject>
    {
        private final Context m_context;
        private final String m_mapId;

        MyCheckTask(Context context, String mapId)
        {
            m_context = context;
            m_mapId = mapId;
        }
        @Override
        protected JSONObject doInBackground(Void... params)
        {
            final String cmd = String.format("%sget_maps_list/?map_id=%s", s_api, m_mapId);

            final InputStream ins = GetInputStream(cmd);
            if (null == ins)
            {
                return null;
            }

            BufferedReader streamReader;
            try
            {
                streamReader = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
            }
            catch (UnsupportedEncodingException ex)
            {
                return null;
            }

            StringBuilder strBuilder = new StringBuilder();
            try
            {
                String inputStr;
                while ((inputStr = streamReader.readLine()) != null)
                    strBuilder.append(inputStr);
            }
            catch (Exception ex)
            {
                return null;
            }

            JSONObject jo;
            try {
                jo = new JSONObject(strBuilder.toString());
            }
            catch (JSONException ex)
            {
                return null;
            }

            return jo;
        }

        @Override
        protected void onPostExecute(JSONObject result)
        {
            if (null == result) {
                m_delegate.onCheckMapUpdatesFinishedWithResult(DOWNLOAD_MAPS_CHECK_RESULT_ERROR);
                return;
            }

            final JSONArray arr = result.optJSONArray("versions");
            final int n = arr.length();

            Map<String, Integer> latestVersions = new HashMap<>(n);

            for (int i = 0; i < n; ++i)
            {
                final JSONObject d = arr.optJSONObject(i);

                final String type = d.optString("type");
                final int version = d.optInt("version");

                final Integer latest = latestVersions.get(type);
                if (latest == null || latest < version)
                {
                    latestVersions.put(type, version);
                }
            }

            final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(m_context);

            List<Map.Entry<String, Integer> > toDownload = new ArrayList<>(latestVersions.size());

            for (Map.Entry<String, Integer> entry : latestVersions.entrySet())
            {
                final int version = entry.getValue();
                final int curVersion = pref.getInt(GetKey_(m_mapId, entry.getKey()), -1);
                if (version > curVersion)
                {
                    toDownload.add(entry);
                }
            }

            if (!toDownload.isEmpty()) {
                new MyDownloadTask(m_context, m_mapId, toDownload).execute();
            }

            // kill files which are not in versions
            final File dir = GetDir(m_context, m_mapId);
            final File[] files = dir.listFiles();
            if (files != null)
            {
                for (File file : files)
                {
                    final String filename = file.getName();
                    boolean found = false;
                    for (String layerName : latestVersions.keySet())
                    {
                        final String fn = Filename_(layerName);
                        if (fn.equals(filename))
                        {
                            found = true;
                            break;
                        }
                    }
                    if (!found)
                    {
                        file.delete();
                    }
                }
            }

            // kill records about deleted files
            final Map<String, ?> all = pref.getAll();
            final String pr = String.format("%s%s", s_vPrefix, m_mapId);
            final List<String> keysToDel = new ArrayList<>();
            for (String key : all.keySet())
            {
                if (key.startsWith(pr))
                {
                    boolean found = false;
                    for (String layerName : latestVersions.keySet())
                    {
                        final String fn = GetKey_(m_mapId, layerName);
                        if (key.equals(fn))
                        {
                            found = true;
                            break;
                        }
                    }
                    if (!found)
                    {
                        keysToDel.add(key);
                    }
                }
            }
            if (!keysToDel.isEmpty()) {
                SharedPreferences.Editor ed = pref.edit();
                for (String keyToDel : keysToDel) {
                    ed.remove(keyToDel);
                }
                ed.commit();
            }

            m_delegate.onCheckMapUpdatesFinishedWithResult(toDownload.isEmpty() ? DOWNLOAD_MAPS_CHECK_RESULT_READY : DOWNLOAD_MAPS_CHECK_RESULT_DOWNLOADING);
        }
    }

    void downloadMapId(String mapId)
    {
        // ktodo cancel downloads

        new MyCheckTask(m_context, mapId).execute();
    }

}
