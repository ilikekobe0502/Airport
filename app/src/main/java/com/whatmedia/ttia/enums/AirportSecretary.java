package com.whatmedia.ttia.enums;

import com.whatmedia.ttia.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by neo_mac on 2017/8/6.
 */

public enum AirportSecretary {
    TAG_AIRPORT_SECRETARY_MEWS(R.string.airport_secretary_news, R.drawable.airport_secretary_09_01),
    TAG_AIRPORT_SECRETARY_EMERGENCY(R.string.airport_secretary_emergency, R.drawable.airport_secretary_09_02),
    TAG_AIRPORT_SECRETARY_NOTIFY(R.string.airport_secretary_notify, R.drawable.airport_secretary_09_03);

    private int title;
    private int icon;

    AirportSecretary(int title, int icon) {
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
    public static AirportSecretary getItemByTag(AirportSecretary tag) {
        return AirportSecretary.valueOf(tag.name());
    }

    /**
     * get page data
     *
     * @return
     */
    public static List<AirportSecretary> getPage() {
        List<AirportSecretary> list = new ArrayList<>();

        for (AirportSecretary item : AirportSecretary.values()) {
            list.add(item);
        }
        return list;
    }
}