package com.whatmedia.ttia.response.data;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by neo_mac on 2017/8/8.
 */

public class ClockData implements Serializable{
    private int id;
    private ClockTimeData time;
    private String timeString;
    private boolean notify;

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
}
