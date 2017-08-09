package com.whatmedia.ttia.utility;

import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TimePicker;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import fr.arnaudguyon.xmltojsonlib.XmlToJson;

/**
 * Created by neo_mac on 2017/8/6.
 */

public class Util {
    private final static String TAG = Util.class.getSimpleName();

    public final static String TAG_FORMAT_YMD = "yyyy/MM/dd";
    public final static String TAG_FORMAT_MD = "MM/dd";
    public final static String TAG_FORMAT_HM = "HH:mm";
    public final static String TAG_DAY = "day";
    public final static String TAG_HOUR = "hour";
    public final static String TAG_MIN = "min";
    public final static String TAG_SEC = "sec";

    /**
     * Get now data
     *
     * @return
     */
    public static String getNowDate() {
        DateFormat df = new SimpleDateFormat(TAG_FORMAT_YMD);
        String date = df.format(Calendar.getInstance().getTime());
        return date;
    }

    /**
     * Get now date by format
     *
     * @param format
     * @return
     */
    public static String getNowDate(String format) {
        DateFormat df = new SimpleDateFormat(format);
        String date = df.format(Calendar.getInstance().getTime());
        return date;
    }

    /**
     * Get count date
     *
     * @param count
     * @return
     */
    public static String getCountDate(int count) {
        DateFormat df = new SimpleDateFormat(TAG_FORMAT_MD);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, count);
        String date = df.format(calendar.getTime());
        return date;
    }

    /**
     * Get count date customize format
     *
     * @param count
     * @param format
     * @return
     */
    public static String getCountDate(int count, String format) {
        DateFormat df = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, count);
        String date = df.format(calendar.getTime());
        return date;
    }

    /**
     * Get different time with not time
     *
     * @param time
     * @return
     */
    public static HashMap<String, Long> getCountTime(String time) {
        DateFormat df = new SimpleDateFormat(TAG_FORMAT_HM);
        long hours = 0;
        long minutes = 0;
        long diff = 0;
        try {
            Date d1 = df.parse(time);
            Date d2 = df.parse(getNowDate(TAG_FORMAT_HM));
            diff = d1.getTime() - d2.getTime();
            long days = diff / (1000 * 60 * 60 * 24);
            hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        HashMap<String, Long> diffTime = new HashMap<>();
        diffTime.put(TAG_HOUR, hours);
        diffTime.put(TAG_MIN, minutes);
        diffTime.put(TAG_SEC, diff);
        return diffTime;
    }

    /**
     * Get Now time
     *
     * @return
     */
    public static int getNowTime() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    /**
     * Xml to Json String
     *
     * @param xmlSource
     * @return
     */
    public static String xmlToSJsonString(String xmlSource) {
        XmlToJson xmlToJson = new XmlToJson.Builder(xmlSource).build();
        String formatted = xmlToJson.toFormattedString();
        return formatted;
    }

    /**
     * Get drawable by string
     *
     * @param context
     * @param name
     * @return
     */
    public static int getDrawableByString(Context context, String name) {
        int id = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
        return id;
    }

    /**
     * hide soft keyboard
     *
     * @param view
     */
    public static void hideSoftKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * Show time picker
     *
     * @param context
     * @param listener
     */
    public static void showTimePicker(Context context, TimePickerDialog.OnTimeSetListener listener) {
        java.util.GregorianCalendar calendar = new java.util.GregorianCalendar();
        TimePickerDialog timePicker = new TimePickerDialog(context, listener, calendar.get(calendar.HOUR_OF_DAY), calendar.get(calendar.MINUTE), false);
        timePicker.show();
    }
}
