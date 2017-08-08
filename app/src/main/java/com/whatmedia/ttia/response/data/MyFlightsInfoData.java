package com.whatmedia.ttia.response.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by neo on 2017/8/4.
 */
public class MyFlightsInfoData {
    @SerializedName("UserID")
    private String userId;
    @SerializedName("Devicetoken")
    private String deviceToken;
    @SerializedName("DeviceType")
    private String deviceType;
    @SerializedName("Terminals")
    private String terminals;
    @SerializedName("Kinds")
    private String kinds;
    @SerializedName("AirlineCode")
    private String airlineCode;
    @SerializedName("AirlineCName")
    private String airlineCName;
    @SerializedName("Shifts")
    private String shifts;
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
    private String contactsLoaction;
    @SerializedName("ContactsLocationEng")
    private String contactsLocaionEng;
    @SerializedName("ContactsLocationChinese")
    private String contactsLocationChinese;
    @SerializedName("FlightStatus")
    private String flightStatus;
    @SerializedName("PlaneModel")
    private String planeModel;
    @SerializedName("OtherWaypoint")
    private String otherWavpoint;
    @SerializedName("OtherWaypointEng")
    private String otherWavpointEng;
    @SerializedName("OtherWaypointChinese")
    private String otherWavpointChinese;
    @SerializedName("LuggageCarousel")
    private String luggageCarousel;
    @SerializedName("CExpressTime")
    private String CExpressTime;
    @SerializedName("CExpectedTime")
    private String CExpectedTime;
    @SerializedName("FlightCode")
    private String flightCode;
    @SerializedName("ID")
    private String id;
    @SerializedName("CTName")
    private String CTName;
    @SerializedName("CSName")
    private String CSName;
    @SerializedName("JName")
    private String JName;
    @SerializedName("EName")
    private String EName;
    private boolean isCheck;

    public boolean getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(boolean check) {
        isCheck = check;
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

    public String getAirlineCName() {
        return airlineCName;
    }

    public void setAirlineCName(String airlineCName) {
        this.airlineCName = airlineCName;
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

    public String getContactsLoaction() {
        return contactsLoaction;
    }

    public void setContactsLoaction(String contactsLoaction) {
        this.contactsLoaction = contactsLoaction;
    }

    public String getContactsLocaionEng() {
        return contactsLocaionEng;
    }

    public void setContactsLocaionEng(String contactsLocaionEng) {
        this.contactsLocaionEng = contactsLocaionEng;
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

    public String getOtherWavpoint() {
        return otherWavpoint;
    }

    public void setOtherWavpoint(String otherWavpoint) {
        this.otherWavpoint = otherWavpoint;
    }

    public String getOtherWavpointEng() {
        return otherWavpointEng;
    }

    public void setOtherWavpointEng(String otherWavpointEng) {
        this.otherWavpointEng = otherWavpointEng;
    }

    public String getOtherWavpointChinese() {
        return otherWavpointChinese;
    }

    public void setOtherWavpointChinese(String otherWavpointChinese) {
        this.otherWavpointChinese = otherWavpointChinese;
    }

    public String getLuggageCarousel() {
        return luggageCarousel;
    }

    public void setLuggageCarousel(String luggageCarousel) {
        this.luggageCarousel = luggageCarousel;
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

    public String getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}