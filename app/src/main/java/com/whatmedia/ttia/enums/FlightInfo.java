package com.whatmedia.ttia.enums;

import com.whatmedia.ttia.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by neo_mac on 2017/8/4.
 */

public enum FlightInfo {
    TAG_SEARCH_FLIGHTS(R.string.flights_info_search_flights, R.drawable.flights_02_01),
    TAG_MY_FLIGHTS(R.string.flights_info_my_flights, R.drawable.flights_02_02),
    TAG_FLIGHTS_NOTIFY(R.string.flights_info_flights_notify, R.drawable.flights_02_03);

    private int title;
    private int icon;

    FlightInfo(int title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    /**
     * Get item by tag
     *
     * @param tag
     * @return
     */
    public static FlightInfo getItemByTag(FlightInfo tag) {
        return FlightInfo.valueOf(tag.name());
    }

    /**
     * get page data
     *
     * @return
     */
    public static List<FlightInfo> getPage() {
        List<FlightInfo> list = new ArrayList<>();

        for (FlightInfo item : FlightInfo.values()) {
            list.add(item);
        }
        return list;
    }
}
