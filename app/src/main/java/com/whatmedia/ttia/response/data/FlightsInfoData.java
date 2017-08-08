package com.whatmedia.ttia.response.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by neo on 2017/8/4.
 */
public class FlightsInfoData {
    public final static String TAG_KIND_ARRIVE = "A";
    public final static String TAG_KIND_DEPARTURE = "D";
    public final static String TAG_KIND_TOP4_ARRIVE = "TOP4A";
    public final static String TAG_KIND_TOP4_DEPARTURE = "TOP4D";
    public final static String TAG_ON_TIME = "準時";
    public final static String TAG_DELAY = "延遲";
    public final static String TAG_ARRIVED = "已到";
    public final static String TAG_CANCELLED = "取消";
    public final static String TAG_SCHEDULE_CHANGE = "時間更改";

    public final static String TAG_TYPE_INSERT = "0";//新增
    public final static String TAG_TYPE_DELETE = "1";//刪除

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
    @SerializedName("OtherWaypoint")
    private String otherWaypoint;
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

    public String getOtherWaypoint() {
        return otherWaypoint;
    }

    public void setOtherWaypoint(String otherWaypoint) {
        this.otherWaypoint = otherWaypoint;
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
}
