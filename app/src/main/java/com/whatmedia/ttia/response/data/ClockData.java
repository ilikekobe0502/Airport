package com.whatmedia.ttia.response.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by neo_mac on 2017/8/8.
 */

public class ClockData implements Serializable{
    private int id;
    private ClockTimeData time;
    private String timeString;
    private boolean notify;//是否開啟推播
    private boolean isCheck;//是否勾選刪除
    private List<FlightsInfoData> flightsList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ClockTimeData getTime() {
        return time;
    }

    public void setTime(ClockTimeData time) {
        this.time = time;
    }

    public boolean isNotify() {
        return notify;
    }

    public void setNotify(boolean notify) {
        this.notify = notify;
    }

    public String getTimeString() {
        return timeString;
    }

    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }

    public boolean getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }

    public List<FlightsInfoData> getFlightsData() {
        return flightsList;
    }

    public void setFlightsData(List<FlightsInfoData> flightsList) {
        this.flightsList = flightsList;
    }
}
