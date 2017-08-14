package com.whatmedia.ttia.utility;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.page.main.flights.notify.MyFlightsNotifyContract;
import com.whatmedia.ttia.response.GetFlightsInfoResponse;
import com.whatmedia.ttia.response.data.ClockData;
import com.whatmedia.ttia.response.data.FlightsInfoData;
import com.whatmedia.ttia.services.FlightClockBroadcast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import fr.arnaudguyon.xmltojsonlib.XmlToJson;

/**
 * Created by neo_mac on 2017/8/6.
 */

public class Util {
    private final static String TAG = Util.class.getSimpleName();

    public final static String TAG_FORMAT_YMD = "yyyy/MM/dd";
    public final static String TAG_FORMAT_MD = "MM/dd";
    public final static String TAG_FORMAT_HM = "HH:mm";
    public final static String TAG_FORMAT_HMS = "HH:mm:ss";
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
        if (data != null) {
            int sec = (int) data.getTime().getSec();
            Integer id = data.getId();
            Calendar cal1 = Calendar.getInstance();
            cal1.add(Calendar.SECOND, sec);
            Log.d(TAG, "配置鬧終於" + sec + "秒後: " + cal1);

            Intent intent = new Intent(context, FlightClockBroadcast.class);
            intent.putExtra(MyFlightsNotifyContract.TAG_NOTIFY_TIME_STRING, data.getTimeString());
            intent.putExtra(MyFlightsNotifyContract.TAG_NOTIFY_ID, data.getId());

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, 0);

            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            am.set(AlarmManager.RTC_WAKEUP, cal1.getTimeInMillis(), pendingIntent);
        } else {
            Log.d(TAG, "ClockData is error");
        }
    }

    /**
     * Cancel alert clock
     *
     * @param context
     * @param id
     */
    public static void cancelAlertClock(Context context, int id) {
        Intent intent = new Intent(context, FlightClockBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, 0);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(pendingIntent);

        Log.d(TAG, "cancel alert clock");
    }

    /**
     * Get Marquee Sub Message
     *
     * @param context
     * @return
     */
    public static String getMarqueeSubMessage(Context context) {
        List<FlightsInfoData> datas = GetFlightsInfoResponse.newInstance(Preferences.getMyFlightsDat(context));

        StringBuilder marqueeSubMessage = new StringBuilder();

        if (datas != null) {
            for (FlightsInfoData item : datas) {
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

                marqueeSubMessage.append(!TextUtils.isEmpty(item.getFlightCode()) ? item.getFlightCode().trim() : "")
                        .append(" ")
                        .append(!TextUtils.isEmpty(item.getAirLineCName()) ? item.getAirLineCName().trim() : "")
                        .append(" ")
                        .append(!TextUtils.isEmpty(item.getKinds()) ? item.getKinds().equals(FlightsInfoData.TAG_KIND_ARRIVE)?context.getString(R.string.marquee_arrive):context.getString(R.string.marquee_dexxxxx) : "")
                        .append(!TextUtils.isEmpty(item.getContactsLocationChinese()) ? item.getContactsLocationChinese().trim() : "")
                        .append(" ")
                        .append(context.getString(R.string.marquee_ecpected_time))
                        .append(!TextUtils.isEmpty(item.getCExpectedTime()) ? item.getCExpectedTime().trim() : "")
                        .append(" ")
                        .append(context.getString(R.string.marquee_status))
                        .append(!TextUtils.isEmpty(item.getFlightStatus()) ? item.getFlightStatus().trim() : "")
                        .append(" ")
                        .append(context.getString(R.string.marquee_ecpress_time))
                        .append(!TextUtils.isEmpty(item.getCExpressTime()) ? item.getCExpressTime().trim() : "")
                        .append(" ")
                        .append(context.getString(R.string.marquee_terminal))
                        .append(!TextUtils.isEmpty(item.getTerminals()) ? item.getTerminals().trim() : "")
                        .append(" ")
                        .append(context.getString(R.string.marquee_gate))
                        .append(!TextUtils.isEmpty(item.getGate()) ? item.getGate().trim() : "")
                        .append(" ")
                        .append(!TextUtils.isEmpty(item.getKinds()) ? item.getKinds().equals(FlightsInfoData.TAG_KIND_ARRIVE)?context.getString(R.string.marquee_luggage):context.getString(R.string.marquee_gt) : "")
                        .append(!TextUtils.isEmpty(item.getKinds()) ? item.getKinds().equals(FlightsInfoData.TAG_KIND_ARRIVE)?
                                !TextUtils.isEmpty(item.getLuggageCarousel()) ? item.getLuggageCarousel().trim() : "":
                                !TextUtils.isEmpty(item.getCounter()) ? item.getCounter().trim() : "":"");


            }
        }
        if (marqueeSubMessage.length() == 0) {
            marqueeSubMessage.append(context.getString(R.string.marquee_default_end_message));
        }
        Log.e("Ian",marqueeSubMessage.toString());
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
     *  Bitmap放大的方法  height(想放大的高度) width(想放大的寬度)
     * @param bitmap
     * @param height
     * @param width
     * @return
     */
    public static Bitmap setBitmapScale(Bitmap bitmap,int height,int width) {

        int bitmapHeight = bitmap.getHeight();
        int bitmapWidth = bitmap.getWidth();

        if(bitmapHeight <= 0 || bitmapWidth <= 0){
            return bitmap;
        }

        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width<=0?bitmapWidth:width) / bitmapWidth;
        float scaleHeight = ((float) height<=0?bitmapHeight:height) / bitmapHeight;

        matrix.postScale(scaleWidth, scaleHeight); //長寬比例

        Log.e("Ian","bitmap.getWidth():"+bitmapWidth+", bitmap.getHeight():"+bitmapHeight+", height:"+height+", width:"+width);
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmapWidth, bitmapHeight, matrix, true);
        return resizeBmp;
    }

    /**
     * 取得 deviceId
     * @param context
     * @return
     */
    public static String getDeviceId(Context context){
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }
}
