package com.whatmedia.ttia.newresponse.data;

import com.google.gson.annotations.SerializedName;
import com.whatmedia.ttia.response.data.ClockTimeData;

/**
 * Created by neo_mac on 2017/10/29.
 */

public class FlightsListData {
    @SerializedName("terminals")
    private String terminals;
    @SerializedName("kinds")
    private String kinds;
    @SerializedName("airlineCode")
    private String airlineCode;
    @SerializedName("airlineName")
    private String airlineName;
    @SerializedName("shifts")
    private String shifts;
    @SerializedName("gate")
    private String gate;
    @SerializedName("expressDate")
    private String expressDate;
    @SerializedName("expressTime")
    private String expressTime;
    @SerializedName("expectedDate")
    private String expectedDate;
    @SerializedName("expectedTime")
    private String expectedTime;
    @SerializedName("contactsLocation")
    private String contactsLocation;
    @SerializedName("contactsLocationEng")
    private String contactsLocationEng;
    @SerializedName("contactsLocationChinese")
    private String contactsLocationChinese;
    @SerializedName("flightStatus")
    private String flightStatus;
    @SerializedName("planeModel")
    private String planeModel;
    @SerializedName("otherWaypoint")
    private String otherWaypoint;
    @SerializedName("otherWaypointEng")
    private String otherWaypointEng;
    @SerializedName("otherWaypointChinese")
    private String otherWaypointChinese;
    @SerializedName("luggageCarousel")
    private String luggageCarousel;
    @SerializedName("counter")
    private String counter;

    private boolean isCheck;//MyFlights page是否有被勾選
    private int notificationId;//Notification ID
    private ClockTimeData notificationTime;//Notification Time

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

    public String getShifts() {
        return shifts;
    }

    public void setShifts(String shifts) {
        this.shifts = shifts;
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

    public String getPlaneModel() {
        return planeModel;
    }

    public void setPlaneModel(String planeModel) {
        this.planeModel = planeModel;
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

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
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
}
