package com.whatmedia.ttia.response.data;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.whatmedia.ttia.R;

import java.io.Serializable;

/**
 * Created by neo on 2017/8/4.
 */
public class FlightsInfoData implements Serializable {
    public final static String TAG_KIND_ARRIVE = "A";
    public final static String TAG_KIND_DEPARTURE = "D";
    public final static String TAG_KIND_TOP4_ARRIVE = "TOP4A";
    public final static String TAG_KIND_TOP4_DEPARTURE = "TOP4D";
    public final static String TAG_KIND_TOP4 = "TOP4";
    public final static String TAG_ON_TIME = "準時";
    public final static String TAG_DELAY = "延遲";
    public final static String TAG_ARRIVED = "已到";
    public final static String TAG_CANCELLED = "取消";
    public final static String TAG_SCHEDULE_CHANGE = "時間更改";
    public final static String TAG_DEPARTED = "出發";

    public final static String TAG_ON_TIME_SHOW_TEXT = "準時";
    public final static String TAG_DELAY_SHOW_TEXT = "延遲\nDelay";
    public final static String TAG_ARRIVED_SHOW_TEXT = "已到\nArrive";
    public final static String TAG_CANCELLED_SHOW_TEXT = "取消\nCancelled";
    public final static String TAG_SCHEDULE_CHANGE_SHOW_TEXT = "時間更改\nSchedule change";
    public final static String TAG_DEPARTED_SHOW_TEXT = "出發\nDeparture";

    public final static String TAG_TYPE_INSERT = "0";//新增
    public final static String TAG_TYPE_DELETE = "1";//刪除
    @SerializedName("UserID")
    private String userId;
    @SerializedName("Devicetoken")
    private String deviceToken;
    @SerializedName("DeviceType")
    private String deviceType;
    @SerializedName("OtherWaypoint")
    private String otherWavpoint;
    @SerializedName("CExpressTime")
    private String CExpressTime;
    @SerializedName("CExpectedTime")
    private String CExpectedTime;
    @SerializedName("CTName")
    private String CTName;
    @SerializedName("CSName")
    private String CSName;
    @SerializedName("JName")
    private String JName;
    @SerializedName("EName")
    private String EName;
    @SerializedName("ID")
    private String id;
    @SerializedName("Terminals")
    private String terminals;
    @SerializedName("Kinds")
    private String kinds;
    @SerializedName("AirlineCode")
    private String airlineCode;
    @SerializedName("AirlineCName")
    private String airLineCName;
    @SerializedName("Shifts")
    private String shift;//新增及刪除時需要補滿四位數
    @SerializedName("Gate")
    private String gate;
    @SerializedName("ExpressDate")
    private String expressDate;
    @SerializedName("ExpressTime")
    private String expressTime;
    @SerializedName("ExpectedDate")
    private String expectedDate;
    @SerializedName("ExpectedTime")
    private String expectedTime;
    @SerializedName("ContactsLocation")
    private String contactsLocation;
    @SerializedName("ContactsLocationEng")
    private String contactsLocationEng;
    @SerializedName("ContactsLocationChinese")
    private String contactsLocationChinese;
    @SerializedName("FlightStatus")
    private String flightStatus;
    @SerializedName("PlaneModel")
    private String plabeModel;
    @SerializedName("OtherWaypointEng")
    private String otherWaypointEng;
    @SerializedName("OtherWaypointChinese")
    private String otherWaypointChinese;
    @SerializedName("LuggageCarousel")
    private String luggageCarousel;
    @SerializedName("Counter")
    private String counter;
    @SerializedName("FlightCode")
    private String flightCode;
    private String type;
    private boolean isCheck;
    //Notification!!!!!!!!
    private int notificationId;
    private ClockTimeData notificationTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTerminals() {
        return terminals;
    }

    public void setTerminals(String terminals) {
        this.terminals = terminals;
    }

    public String getKinds() {
        return kinds;
    }

    public void setKinds(String kinds) {
        this.kinds = kinds;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    public String getAirLineCName() {
        return airLineCName;
    }

    public void setAirLineCName(String airLineCName) {
        this.airLineCName = airLineCName;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getGate() {
        return gate;
    }

    public void setGate(String gate) {
        this.gate = gate;
    }

    public String getExpressDate() {
        return expressDate;
    }

    public void setExpressDate(String expressDate) {
        this.expressDate = expressDate;
    }

    public String getExpressTime() {
        return expressTime;
    }

    public void setExpressTime(String expressTime) {
        this.expressTime = expressTime;
    }

    public String getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(String expectedDate) {
        this.expectedDate = expectedDate;
    }

    public String getExpectedTime() {
        return expectedTime;
    }

    public void setExpectedTime(String expectedTime) {
        this.expectedTime = expectedTime;
    }

    public String getContactsLocation() {
        return contactsLocation;
    }

    public void setContactsLocation(String contactsLocation) {
        this.contactsLocation = contactsLocation;
    }

    public String getContactsLocationEng() {
        return contactsLocationEng;
    }

    public void setContactsLocationEng(String contactsLocationEng) {
        this.contactsLocationEng = contactsLocationEng;
    }

    public String getContactsLocationChinese() {
        return contactsLocationChinese;
    }

    public void setContactsLocationChinese(String contactsLocationChinese) {
        this.contactsLocationChinese = contactsLocationChinese;
    }

    public String getFlightStatus() {
        return flightStatus;
    }

    public void setFlightStatus(String flightStatus) {
        this.flightStatus = flightStatus;
    }

    public String getPlabeModel() {
        return plabeModel;
    }

    public void setPlabeModel(String plabeModel) {
        this.plabeModel = plabeModel;
    }

    public String getOtherWaypointEng() {
        return otherWaypointEng;
    }

    public void setOtherWaypointEng(String otherWaypointEng) {
        this.otherWaypointEng = otherWaypointEng;
    }

    public String getOtherWaypointChinese() {
        return otherWaypointChinese;
    }

    public void setOtherWaypointChinese(String otherWaypointChinese) {
        this.otherWaypointChinese = otherWaypointChinese;
    }

    public String getLuggageCarousel() {
        return luggageCarousel;
    }

    public void setLuggageCarousel(String luggageCarousel) {
        this.luggageCarousel = luggageCarousel;
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getOtherWavpoint() {
        return otherWavpoint;
    }

    public void setOtherWavpoint(String otherWavpoint) {
        this.otherWavpoint = otherWavpoint;
    }

    public String getCExpressTime() {
        return CExpressTime;
    }

    public void setCExpressTime(String CExpressTime) {
        this.CExpressTime = CExpressTime;
    }

    public String getCExpectedTime() {
        return CExpectedTime;
    }

    public void setCExpectedTime(String CExpectedTime) {
        this.CExpectedTime = CExpectedTime;
    }

    public String getCTName() {
        return CTName;
    }

    public void setCTName(String CTName) {
        this.CTName = CTName;
    }

    public String getCSName() {
        return CSName;
    }

    public void setCSName(String CSName) {
        this.CSName = CSName;
    }

    public String getJName() {
        return JName;
    }

    public void setJName(String JName) {
        this.JName = JName;
    }

    public String getEName() {
        return EName;
    }

    public void setEName(String EName) {
        this.EName = EName;
    }

    public boolean getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(boolean check) {
        isCheck = check;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public ClockTimeData getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(ClockTimeData notificationTime) {
        this.notificationTime = notificationTime;
    }

    /**
     * check flights show text
     *
     * @param context
     * @param data
     * @return
     */
    public static String checkFlightShowText(Context context, String data) {
        String text = context.getString(R.string.flights_search_tag_on_time_show_text);
        if (data.contains(FlightsInfoData.TAG_ON_TIME))
            text = context.getString(R.string.flights_search_tag_on_time_show_text);
        else if (data.contains(FlightsInfoData.TAG_DELAY))
            text = context.getString(R.string.flights_search_tag_delay_show_text);
        else if (data.contains(FlightsInfoData.TAG_ARRIVED))
            text = context.getString(R.string.flights_search_tag_arrived_show_text);
        else if (data.contains(FlightsInfoData.TAG_CANCELLED))
            text = context.getString(R.string.flights_search_tag_cancelled_show_text);
        else if (data.contains(FlightsInfoData.TAG_SCHEDULE_CHANGE))
            text = context.getString(R.string.flights_search_tag_schedule_change_show_text);
        else if (data.contains(FlightsInfoData.TAG_DEPARTED))
            text = context.getString(R.string.flights_search_tag_departed_show_text);
        return text;
    }
}
