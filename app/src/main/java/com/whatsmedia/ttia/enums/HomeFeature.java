package com.whatsmedia.ttia.enums;

import com.whatsmedia.ttia.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by neo_mac on 2017/8/3.
 */

public enum HomeFeature {
    TAG_FLIGHTS_INFO(R.string.title_flight, R.drawable.home_02),
    TAG_TERMINAL_INFO(R.string.home_terminal_info_title, R.drawable.home_03),
    TAG_AIRPORT_TRAFFIC(R.string.title_traffic, R.drawable.home_04),
    TAG_PRACTICAL_INFO(R.string.title_utility, R.drawable.home_05),
    TAG_STORE_OFFERS(R.string.home_store_offers_title, R.drawable.home_06),
    TAG_COMMUNICATION_SERVICE(R.string.title_communication, R.drawable.home_07),
    TAG_AIRPORT_SECRETARY(R.string.home_airport_secretary_title, R.drawable.home_09),
    TAG_LANGUAGE_SETTING(R.string.home_language_setting_title, R.drawable.home_08);
//    TAG_INDOOR_MAP(R.string.home_indoor_map_title, R.drawable.home_10),
//    TAG_AIRPORT_ACHIEVEMENT(R.string.home_airport_achievement_title, R.drawable.home_11);

    private int title;
    private int icon;

    HomeFeature(int title, int icon) {
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
    public static HomeFeature getItemByTag(HomeFeature tag) {
        return HomeFeature.valueOf(tag.name());
    }

    /**
     * Get Home page data
     *
     * @return
     */
    public static List<List<HomeFeature>> getPageFirst() {
        List<List<HomeFeature>> list = new ArrayList<>();
        List<HomeFeature> first = new ArrayList<>();
        List<HomeFeature> second = new ArrayList<>();

        for (HomeFeature item : HomeFeature.values()) {
            if (first.size() >= 8)
                second.add(item);
            else
                first.add(item);
        }
        list.add(first);
        if (second.size() > 0)
            list.add(second);
        return list;
    }
}
