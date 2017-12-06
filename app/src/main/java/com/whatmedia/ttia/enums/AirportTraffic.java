package com.whatmedia.ttia.enums;

import com.whatmedia.ttia.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by neo_mac on 2017/8/4.
 */

public enum AirportTraffic {
    TAG_PARKING_INFO(R.string.title_parking_infomation, R.drawable.airport_traffic_04_01),
    TAG_AIRPORT_BUS(R.string.title_airport_bus, R.drawable.airport_traffic_04_02),
    TAG_ROADSIDE_ASSISTANCE(R.string.title_road_rescue, R.drawable.airport_traffic_04_03),
    TAG_TAXI(R.string.title_taxi, R.drawable.airport_traffic_04_04),
    TAG_TOUR_BUS(R.string.title_tourist_bus, R.drawable.airport_traffic_04_05),
    TAG_AIRPORT_MRT(R.string.title_mrt_hsr, R.drawable.airport_traffic_04_06),
    TAG_SKY_TRAIN(R.string.title_tram_car, R.drawable.airport_traffic_04_07);

    private int title;
    private int icon;

    AirportTraffic(int title, int icon) {
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
    public static AirportTraffic getItemByTag(AirportTraffic tag) {
        return AirportTraffic.valueOf(tag.name());
    }

    /**
     * get page data
     *
     * @return
     */
    public static List<AirportTraffic> getPage() {
        List<AirportTraffic> list = new ArrayList<>();

        for (AirportTraffic item : AirportTraffic.values()) {
            list.add(item);
        }
        return list;
    }

    public static List<List<AirportTraffic>> getPageList() {
        List<List<AirportTraffic>> list = new ArrayList<>();
        List<AirportTraffic> first = new ArrayList<>();
        List<AirportTraffic> second = new ArrayList<>();

        for (AirportTraffic item : AirportTraffic.values()) {
            if (first.size() >= 4)
                second.add(item);
            else
                first.add(item);
        }
        list.add(first);
        list.add(second);
        return list;
    }
}
