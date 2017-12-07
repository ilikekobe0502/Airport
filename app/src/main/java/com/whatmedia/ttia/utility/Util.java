package com.whatmedia.ttia.utility;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.whatmedia.ttia.R;
import com.whatmedia.ttia.component.CornorTransform;
import com.whatmedia.ttia.connect.HttpUtils;
import com.whatmedia.ttia.newresponse.GetFlightsListResponse;
import com.whatmedia.ttia.newresponse.data.FlightsListData;
import com.whatmedia.ttia.page.main.flights.notify.MyFlightsNotifyContract;
import com.whatmedia.ttia.response.data.ClockData;
import com.whatmedia.ttia.response.GetClockDataResponse;
import com.whatmedia.ttia.response.data.ClockTimeData;
import com.whatmedia.ttia.response.data.FlightsInfoData;
import com.whatmedia.ttia.services.FlightClockBroadcast;

import java.lang.reflect.Field;
import java.security.spec.AlgorithmParameterSpec;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import fr.arnaudguyon.xmltojsonlib.XmlToJson;

/**
 * Created by neo_mac on 2017/8/6.
 */

public class Util {
    private final static String TAG = Util.class.getSimpleName();

    public final static String TAG_FORMAT_ALL = "yyyy/MM/dd HH:mm";
    public final static String TAG_FORMAT_YMD = "yyyy/MM/dd";
    public final static String TAG_FORMAT_MD = "MM/dd";
    public final static String TAG_FORMAT_HM = "HH:mm";
    public final static String TAG_FORMAT_HMS = "HH:mm:ss";
    public final static String TAG_FORMAT_HH = "HH";
    public final static String TAG_FORMAT_mm = "mm";
    public final static String TAG_DAY = "day";
    public final static String TAG_HOUR = "hour";
    public final static String TAG_MIN = "min";
    public final static String TAG_SEC = "sec";

    public final static String TAG_BLACK = "black";
    public final static String TAG_ARIAL = "arial.ttf";

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
     * Get different time with now time
     *
     * @param time
     * @return
     */
    public static HashMap<String, Long> getDifferentTimeWithNowTime(String time) {
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
        diffTime.put(TAG_SEC, diff / 1000);
        return diffTime;
    }

    /**
     * Get different time
     *
     * @param timeSource
     * @param timeTarget
     * @return
     */
    public static String getDifferentTime(String timeSource, String timeTarget) {
        DateFormat df = new SimpleDateFormat(TAG_FORMAT_HM);
        DateFormat dfSource = new SimpleDateFormat(TAG_FORMAT_HH);

        Date targetDate = null;
        Date sourceDate = null;
        try {
            targetDate = df.parse(timeTarget);
            sourceDate = df.parse(timeSource);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String hourSource = dfSource.format(sourceDate);
        dfSource = new SimpleDateFormat(TAG_FORMAT_mm);
        String minSource = dfSource.format(sourceDate);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(targetDate);
        calendar.add(Calendar.HOUR, -Integer.parseInt(hourSource));
        calendar.add(Calendar.MINUTE, -Integer.parseInt(minSource));
        Date resultD = calendar.getTime();
        String resultT = df.format(resultD);
        Log.d("TAG", resultT);
        return resultT;
    }

    /**
     * Get different time with now time
     *
     * @param time
     * @return
     */
    public static HashMap<String, Long> getDifferentTimeWithNowTime(String time, String format) {
        DateFormat df = new SimpleDateFormat(format);
        long hours = 0;
        long minutes = 0;
        long diff = 0;
        try {
            Date d1 = df.parse(time);
            Date d2 = df.parse(getNowDate(format));
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
        diffTime.put(TAG_SEC, diff / 1000);
        return diffTime;
    }

    public static String getTransformTimeFormat(String formatTarget, String time) {
        DateFormat df = new SimpleDateFormat(formatTarget);
        Date date = null;
        try {
            date = df.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String result = df.format(date.getTime());
        return result;
    }

    /**
     * 用Timestamp 計算跟現在的時間差
     *
     * @param time
     * @return
     */
    public static HashMap<String, Long> getDifferentTimeWithNowTime(long time) {
        DateFormat df = new SimpleDateFormat(TAG_FORMAT_ALL);
        long hours = 0;
        long minutes = 0;
        long diff = time - Calendar.getInstance().getTime().getTime();
        if (diff < 0) {
            return null;
        }
        try {
            Date date = df.parse(df.format(diff));
            diff = date.getTime();
            long days = diff / (1000 * 60 * 60 * 24);
            hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        HashMap<String, Long> diffTime = new HashMap<>();
        diffTime.put(TAG_HOUR, hours);
        diffTime.put(TAG_MIN, minutes);
        diffTime.put(TAG_SEC, diff / 1000);
        return diffTime;
    }

    /**
     * 計算扣除時間
     *
     * @param sourceTime
     * @param hour
     * @param min
     * @return
     */
    public static long reduceTime(String sourceTime, int hour, int min) {
        DateFormat sdf = new SimpleDateFormat(TAG_FORMAT_ALL);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(sourceTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (hour > 0)
            calendar.add(Calendar.HOUR, -hour);
        if (min > 0) {
            calendar.add(Calendar.MINUTE, -min);
        }

        return calendar.getTimeInMillis();
    }

    public static void transformateTimeStamp(long time) {
        DateFormat sdf = new SimpleDateFormat(TAG_FORMAT_ALL);
    }

    /**
     * Get Now time
     *
     * @return
     */
    public static float getNowTime() {
        return System.currentTimeMillis();
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
     * Show soft keyboard
     *
     * @param view
     */
    public static void showSoftKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInputFromInputMethod(view.getWindowToken(), InputMethodManager.SHOW_IMPLICIT);
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
        TimePickerDialog timePicker = new TimePickerDialog(context, listener, calendar.get(calendar.HOUR_OF_DAY), calendar.get(calendar.MINUTE), true);
        timePicker.show();
    }

    /**
     * time's format yyyy-MM-ddTtt:tt:tt
     * <p>
     * use this methods will return yyyy-MM-dd
     *
     * @param time
     */
    public static String justShowDate(String time) {
        String[] result = time.split("T");
        return !TextUtils.isEmpty(result[0]) && result != null && result.length > 0 ? result[0] : time;
    }

    /**
     * Set Alert clock
     *
     * @param context
     * @param data
     */
    public static void setAlertClock(Context context, ClockData data) {
        if (data != null && data.getFlightsData() != null) {
            for (FlightsListData item : data.getFlightsData()) {
                if (item.getNotificationTime() != null && item.getNotificationId() != 0) {
                    int sec = (int) item.getNotificationTime().getSec();
                    Integer id = item.getNotificationId();
                    Calendar cal1 = Calendar.getInstance();
                    cal1.add(Calendar.SECOND, sec);
                    Log.d(TAG, "ID : " + id + " 配置鬧終於" + sec + "秒後: " + cal1);
                    Gson gson = new Gson();
                    String flightData = gson.toJson(item, FlightsListData.class);

                    Intent intent = new Intent(context, FlightClockBroadcast.class);
                    intent.putExtra(MyFlightsNotifyContract.TAG_NOTIFY_Flight_DATA, flightData);
                    intent.putExtra(MyFlightsNotifyContract.TAG_NOTIFY_ID, id);

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, 0);

                    AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    am.set(AlarmManager.RTC_WAKEUP, cal1.getTimeInMillis(), pendingIntent);
                } else {
                    Log.e(TAG, "FlightsInfoData notification info in error");
                }
            }
        } else {
            Log.d(TAG, "ClockData is error or data.getFlightsData() is error");
        }
    }

    /**
     * Cancel alert clock
     *
     * @param context
     * @param flightsInfoDataList
     */
    public static void cancelAlertClock(Context context, List<FlightsListData> flightsInfoDataList) {
        Intent intent = new Intent(context, FlightClockBroadcast.class);
        for (FlightsListData item : flightsInfoDataList) {
            int id = item.getNotificationId();
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, 0);
            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            if (am != null)
                am.cancel(pendingIntent);

            Log.d(TAG, "indoor_cancel alert clock : " + id);
        }
    }

    /**
     * Get Marquee Sub Message
     *
     * @param context
     * @return
     */
    public static String getMarqueeSubMessage(Context context) {
        GetFlightsListResponse flightsListResponse = GetFlightsListResponse.getGson(Preferences.getMyFlightsData(context));
        List<FlightsListData> datas = flightsListResponse != null ? flightsListResponse.getFlightList() : null;

        StringBuilder marqueeSubMessage = new StringBuilder();

        if (datas != null) {
            for (FlightsListData item : datas) {
                if (marqueeSubMessage.length() > 0) {
                    marqueeSubMessage.append(", ");
                }
//                marqueeSubMessage.append(!TextUtils.isEmpty(item.getCExpectedTime()) ? item.getCExpectedTime() : "")
//                        .append(" ")
//                        .append(!TextUtils.isEmpty(item.getCTName()) ? item.getCTName() : "")
//                        .append(" ")
//                        .append(!TextUtils.isEmpty(item.getFlightCode()) ? item.getFlightCode() : "")
//                        .append(" ")
//                        .append(!TextUtils.isEmpty(item.getGate()) ? item.getGate() : "")
//                        .append(" ")
//                        .append(!TextUtils.isEmpty(item.getFlightStatus()) ? item.getFlightStatus() : "");

                marqueeSubMessage.append(!TextUtils.isEmpty(item.getAirlineCode()) ? item.getAirlineCode().trim() : "")
                        .append(" ")
                        .append(!TextUtils.isEmpty(item.getAirlineName()) ? item.getAirlineName().trim() : "")
                        .append(" ")
                        .append(!TextUtils.isEmpty(item.getKinds()) ? item.getKinds().equals(FlightsInfoData.TAG_KIND_ARRIVE) ? context.getString(R.string.marquee_arrive) : context.getString(R.string.marquee_dexxxxx) : "")
                        .append(!TextUtils.isEmpty(item.getContactsLocationChinese()) ? item.getContactsLocationChinese().trim() : "")
                        .append(" ")
                        .append(context.getString(R.string.marquee_ecpected_time))
                        .append(!TextUtils.isEmpty(item.getExpectedTime()) ? item.getExpectedTime().trim() : "")
                        .append(" ")
                        .append(context.getString(R.string.marquee_status))
                        .append(!TextUtils.isEmpty(item.getFlightStatus()) ? item.getFlightStatus().trim() : "")
                        .append(" ")
                        .append(context.getString(R.string.marquee_ecpress_time))
                        .append(!TextUtils.isEmpty(item.getExpressTime()) ? item.getExpressTime().trim() : "")
                        .append(" ")
                        .append(context.getString(R.string.marquee_terminal))
                        .append(!TextUtils.isEmpty(item.getTerminals()) ? item.getTerminals().trim() : "")
                        .append(" ")
                        .append(context.getString(R.string.marquee_gate))
                        .append(!TextUtils.isEmpty(item.getGate()) ? item.getGate().trim() : "")
                        .append(" ")
                        .append(!TextUtils.isEmpty(item.getKinds()) ? item.getKinds().equals(FlightsInfoData.TAG_KIND_ARRIVE) ? context.getString(R.string.marquee_luggage) : context.getString(R.string.marquee_gt) : "")
                        .append(!TextUtils.isEmpty(item.getKinds()) ? item.getKinds().equals(FlightsInfoData.TAG_KIND_ARRIVE) ?
                                !TextUtils.isEmpty(item.getLuggageCarousel()) ? item.getLuggageCarousel().trim() : "" :
                                !TextUtils.isEmpty(item.getCounter()) ? item.getCounter().trim() : "" : "");


            }
        }
        if (marqueeSubMessage.length() == 0) {
            marqueeSubMessage.append(context.getString(R.string.marquee_default_end_message));
        }
        Log.e(TAG, marqueeSubMessage.toString());
        return marqueeSubMessage.toString();
    }

    /**
     * 合成Bitmap
     *
     * @param bitmapList
     * @return
     * @Param space --每張圖之間的間隔
     */
    public static Bitmap combineBitmap(Bitmap[] bitmapList, int space) {
        if (bitmapList == null || bitmapList.length <= 0) {
            return null;
        }

        int width = bitmapList[0].getWidth();
        int height = 0;
        for (int i = 0; i < bitmapList.length; i++) {
            height += bitmapList[i].getHeight();
            height += space;
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(bitmapList[0], 0, 0, null);
        int tempHeight = bitmapList[0].getHeight() + space;
        for (int i = 1; i < bitmapList.length; i++) {
            canvas.drawBitmap(bitmapList[i], 0, tempHeight, null);
            tempHeight += bitmapList[i].getHeight();
            tempHeight += space;
        }
        return bitmap;
    }

    /**
     * Bitmap放大的方法  height(想放大的高度) width(想放大的寬度)
     *
     * @param bitmap
     * @param height
     * @param width
     * @return
     */
    public static Bitmap setBitmapScale(Bitmap bitmap, int height, int width) {
        Matrix matrix = new Matrix();
        matrix.postScale((float) width / (float) bitmap.getWidth(), 1f); //長寬比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }

    /**
     * 取得 deviceId
     *
     * @return
     */
    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * Add Notification
     *
     * @param context
     * @param hourOfDay
     * @param minute
     * @return
     */
    public static List<ClockData> addNotification(Context context, int hourOfDay, int minute) {
        String sourceTime = hourOfDay + ":" + minute;
        List<ClockData> clockDataList = GetClockDataResponse.newInstance(Preferences.getClockData(context));
        Gson gson = new Gson();
        List<FlightsListData> myFlightDataList = GetFlightsListResponse.getGson(Preferences.getMyFlightsData(context)).getFlightList();
        ClockData clockData = new ClockData();

        if (myFlightDataList != null) {

            for (FlightsListData item : myFlightDataList) {
                if (item.getNotificationId() == 0) {
                    ClockTimeData clockTimeData = new ClockTimeData();
                    HashMap<String, Long> diffTime = Util.getDifferentTimeWithNowTime(Util.getDifferentTime(sourceTime, item.getExpectedTime()));
                    clockTimeData.setHour(diffTime.get(Util.TAG_HOUR));
                    clockTimeData.setMin(diffTime.get(Util.TAG_MIN));
                    clockTimeData.setSec(diffTime.get(Util.TAG_SEC));
                    if (clockTimeData.getSec() > 0) {

                        item.setNotificationId(new Random().nextInt(9000) + 65);
                        item.setNotificationTime(clockTimeData);
                    }
                }
            }
            clockData.setFlightsData(myFlightDataList);

        } else {
            Log.e(TAG, "myFlightDataList = null");
        }

        clockData.setNotify(true);
        String timeString = context.getString(R.string.my_flights_notify, hourOfDay, minute);
        ClockTimeData clockTimeData = new ClockTimeData();
        clockTimeData.setHour(hourOfDay);
        clockTimeData.setMin(minute);
        clockData.setTime(clockTimeData);
        clockData.setTimeString(timeString);
        clockData.setId(new Random().nextInt(9000) + 65);
        clockDataList.add(clockData);

        String json = gson.toJson(clockDataList);
        Preferences.saveClockData(context, json);
        return clockDataList;
    }

    public static List<ClockData> addNotificationClock(Context context, int hourOfDay, int minute) {
        String sourceTime = String.format("%1$d:%2$d", hourOfDay, minute);
        //取得原本佇列裡面就有的時間列表
        List<ClockData> clockDataList = GetClockDataResponse.newInstance(Preferences.getClockData(context));
        //取得原本佇列裡面就有的我的航班列表
        GetFlightsListResponse response = GetFlightsListResponse.getGson(Preferences.getMyFlightsData(context));
        List<FlightsListData> myFlightDataList = null;
        if (response != null) {
            myFlightDataList = response.getFlightList();
        }
        ClockData clockData = new ClockData();

        if (myFlightDataList != null && myFlightDataList.size() > 0) {
            //將所有MyFlights的資料設入暫存檔裡
            for (FlightsListData item : myFlightDataList) {
                ClockTimeData clockTimeData = new ClockTimeData();
                //先計算出航班時間扣除設定時間
                //再將上面的時間結果與現在時間取時間差
                HashMap<String, Long> diffTime = Util.getDifferentTimeWithNowTime(Util.getDifferentTime(sourceTime, item.getExpectedTime()));
                //將設定好的資料塞入clockTimeData
                clockTimeData.setHour(diffTime.get(Util.TAG_HOUR));
                clockTimeData.setMin(diffTime.get(Util.TAG_MIN));
                clockTimeData.setSec(diffTime.get(Util.TAG_SEC));
                //如果時間差秒數大於0則代表大於現在時間，則設定鬧鐘參數
                if (clockTimeData.getSec() > 0) {
                    item.setNotificationId(new Random().nextInt(9000) + 65);
                    item.setNotificationTime(clockTimeData);
                }
                //設入一筆推播時間資料
                clockData.setFlightsData(myFlightDataList);
            }
        } else {
            Log.e(TAG, "myFlightDataList = null || myFlightDataList < 0");
        }

        //將推播時間的開關開啟
        clockData.setNotify(true);
        String timeString = context.getString(R.string.my_flights_notify, hourOfDay, minute);
        //設置推播畫面的資料
        ClockTimeData clockTimeData = new ClockTimeData();
        clockTimeData.setHour(hourOfDay);
        clockTimeData.setMin(minute);
        clockData.setTime(clockTimeData);
        clockData.setTimeString(timeString);
        clockData.setId(new Random().nextInt(9000) + 65);
        clockDataList.add(clockData);

        Gson gson = new Gson();
        String json = gson.toJson(clockDataList);
        Preferences.saveClockData(context, json);
        return clockDataList;
    }

    /**
     * Modify notification time
     * 先讓此Item刪除 然後重新新增
     *
     * @param context
     * @param hourOfDay
     * @param minute
     * @param selectId
     * @param clockDataList
     * @return
     */
    public static List<ClockData> modifyNotification(Context context, int hourOfDay, int minute, int selectId, List<ClockData> clockDataList) {
        for (ClockData item : clockDataList) {
            if (item.getId() == selectId) {
                item.setIsCheck(true);
                deleteNotification(context, clockDataList);
                break;
            }
        }

        return addNotificationClock(context, hourOfDay, minute);
    }

    /**
     * Delete notification
     *
     * @param context
     * @param selectList
     * @return
     */
    public static List<ClockData> deleteNotification(Context context, List<ClockData> selectList) {

        List<ClockData> cacheData = new ArrayList<>(selectList);

        for (ClockData item : selectList) {
            if (item.getIsCheck()) {
                cacheData.remove(item);
                if (item.getFlightsData() != null)
                    Util.cancelAlertClock(context, item.getFlightsData());
                else {
                    Log.e(TAG, "subItem.getFlightsData() is null");
                }
            }
        }
        Gson gson = new Gson();
        String json = gson.toJson(cacheData);
        Preferences.saveClockData(context, json);
        return cacheData;
    }

    /**
     * Delete notification
     *
     * @param context
     * @param clockList
     * @return
     */
    public static List<ClockData> deleteAllNotification(Context context, List<ClockData> clockList) {

        List<ClockData> cacheData = new ArrayList<>(clockList);

        for (ClockData item : clockList) {
            cacheData.remove(item);
            if (item.getFlightsData() != null)
                Util.cancelAlertClock(context, item.getFlightsData());
            else {
                Log.e(TAG, "subItem.getFlightsData() is null");

            }
        }
        Gson gson = new Gson();
        String json = gson.toJson(cacheData);
        Preferences.saveClockData(context, json);
        return cacheData;
    }

    public static void resetNotification(Context context, List<FlightsListData> myFlightData) {
        if (myFlightData != null && myFlightData.size() > 0) {
            List<ClockData> clockList = GetClockDataResponse.newInstance(Preferences.getClockData(context));

            deleteAllNotification(context, clockList);
            for (ClockData item : clockList) {
                List<ClockData> changeList = addNotification(context, (int) item.getTime().getHour(), (int) item.getTime().getMin());
                for (ClockData subItem : changeList) {
                    setAlertClock(context, subItem);
                }
            }
        }
    }

    /**
     * Set NumberPicker Text color
     *
     * @param numberPicker
     * @param color
     * @return
     */
    public static boolean setNumberPickerTextColor(NumberPicker numberPicker, int color) {
        final int count = numberPicker.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = numberPicker.getChildAt(i);
            if (child instanceof EditText) {
                try {
                    Field selectorWheelPaintField = numberPicker.getClass()
                            .getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
                    ((Paint) selectorWheelPaintField.get(numberPicker)).setColor(color);
                    ((EditText) child).setTextColor(color);
                    numberPicker.invalidate();
                    return true;
                } catch (NoSuchFieldException e) {
                    Log.w("TAG", "NoSuchFieldException " + e.toString());
                } catch (IllegalAccessException e) {
                    Log.w("TAG", "setNumberPickerTextColor" + e);
                } catch (IllegalArgumentException e) {
                    Log.w("TAG", "setNumberPickerTextColor" + e);
                }
            }
        }
        return false;
    }

    /**
     * 取得可以使用Https的Picasso
     *
     * @param context
     * @return
     */
    public static Picasso getHttpsPicasso(Context context) {
        Picasso.Builder picassoBuilder = new Picasso.Builder(context);
        picassoBuilder.downloader(new OkHttp3Downloader(new HttpUtils().getTrustAllClient())).build();
        return picassoBuilder.build();
    }

    /**
     * Set Picasso retry
     *
     * @param context
     * @param imageView
     * @param url
     * @param radius
     * @param count
     */
    public static void setPicassoRetry(final Context context, final ImageView imageView, final String url, final int radius, final int count) {
        if (!TextUtils.isEmpty(url)) {
            Picasso.with(context).load(url).transform(new CornorTransform(radius, 0)).into(imageView, new Callback() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "image load success");
                }

                @Override
                public void onError() {
                    Log.e(TAG, "image load error :" + url);
                    String t = String.valueOf(count);
                    int i = Integer.parseInt(t);
                    Log.e(TAG, "count  :" + i);
                    if (count != 5) {
                        i++;
                        setPicassoRetry(context, imageView, url, radius, i);
                    }
                }
            });
        }
    }

    /**
     * Set picasso retry
     *
     * @param context
     * @param imageView
     * @param url
     * @param radius
     * @param width
     * @param height
     * @param count
     */
    public static void setPicassoRetry(final Context context, final ImageView imageView, final String url, final int radius, final int width, int height, final int count) {
        if (url != null) {
            Picasso.with(context).load(url)
                    .resize(width, height)
                    .transform(new CornorTransform(radius, 0))
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            Log.d(TAG, "image load success");
                        }

                        @Override
                        public void onError() {
                            Log.e(TAG, "image load error :" + url);
                            String t = String.valueOf(count);
                            int i = Integer.parseInt(t);
                            Log.e(TAG, "count  :" + i);
                            if (count != 5) {
                                i++;
                                setPicassoRetry(context, imageView, url, radius, i);
                            }
                        }
                    });
        }
    }

    /**
     * Set font type
     *
     * @param context
     * @param view
     */
    public static void setTextFont(Context context, TextView view, String type) {
        view.setTypeface(Typeface.createFromAsset(context.getAssets(), String.format("fonts/%s", type)));
    }

    /**
     * Show Time out dialog
     *
     * @param context
     */
    public static void showTimeoutDialog(Context context) {
        new AlertDialog.Builder(context)
                .setTitle(R.string.note)
                .setMessage(context.getString(R.string.timeout_message))
                .setPositiveButton(R.string.alert_btn_ok, null)
                .show();
    }

    /**
     * Filter symbol
     *
     * @param str
     * @return
     */
    public static String filterSymbol(String str) {
        String regEx = "[`_＿~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/／?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(str);
        return matcher.replaceAll("").trim();
    }

    /**
     * Wake up screen
     *
     * @param context
     * @param second
     * @param tag
     */
    public static void wakeUpScreen(Context context, int second, String tag) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        final PowerManager.WakeLock mWakelock = pm.newWakeLock(
                PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, tag);
        mWakelock.acquire(second);
    }

    /**
     * 解密Response
     *
     * @param ivBytes
     * @param keyBytes
     * @param textBytes
     * @return
     */
    public static byte[] decryptAES(byte[] ivBytes, byte[] keyBytes, byte[] textBytes) {
        try {
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            SecretKeySpec newKey = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);
            return cipher.doFinal(textBytes);
        } catch (Exception ex) {
            Log.e(TAG, "[DecryptAES] ", ex);
            return null;
        }

    }

    /**
     * 加密Data
     *
     * @param ivBytes
     * @param keyBytes
     * @param textBytes
     * @return
     */
    public static byte[] encryptAES(byte[] ivBytes, byte[] keyBytes, byte[] textBytes) {
        try {
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            SecretKeySpec newKey = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);
            return cipher.doFinal(textBytes);
        } catch (Exception ex) {
            Log.e(TAG, "[EncryptAES] ", ex);
            return null;
        }
    }
}
