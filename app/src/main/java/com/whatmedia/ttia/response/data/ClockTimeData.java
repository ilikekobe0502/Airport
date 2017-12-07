package com.whatmedia.ttia.response.data;

import com.google.gson.Gson;

/**
 * Created by neo on 2017/8/8.
 */

public class ClockTimeData {
    private String showHour;
    private String showMinute;
    private long hour;
    private long min;
    private long sec;

    public long getHour() {
        return hour;
    }

    public void setHour(long hour) {
        this.hour = hour;
    }

    public long getMin() {
        return min;
    }

    public void setMin(long min) {
        this.min = min;
    }

    public long getSec() {
        return sec;
    }

    public void setSec(long sec) {
        this.sec = sec;
    }

    public static ClockTimeData getInstance(String data) {
        Gson gson = new Gson();
        return gson.fromJson(data, ClockTimeData.class);
    }

    public String getShowHour() {
        return showHour;
    }

    public void setShowHour(String showHour) {
        this.showHour = showHour;
    }

    public String getShowMinute() {
        return showMinute;
    }

    public void setShowMinute(String showMinute) {
        this.showMinute = showMinute;
    }
}
