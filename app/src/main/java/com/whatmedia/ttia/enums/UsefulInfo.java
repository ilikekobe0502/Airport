package com.whatmedia.ttia.enums;


import com.whatmedia.ttia.R;

import java.util.ArrayList;
import java.util.List;

public enum UsefulInfo {
    TAG_LANGUAGE(R.string.useful_info_language, R.drawable.useful_info_05_01),
    TAG_CURRENCY(R.string.useful_info_currency, R.drawable.useful_info_05_02),
    TAG_WEATHER(R.string.useful_info_weather, R.drawable.useful_info_05_03),
    TAG_TIMEZONE(R.string.useful_info_timezone, R.drawable.useful_info_05_04),
    TAG_LOST(R.string.useful_info_lost, R.drawable.useful_info_05_05),
    TAG_QUESTIONNAIRE(R.string.useful_info_questionnaire, R.drawable.useful_info_05_06);

    private int title;
    private int icon;

    UsefulInfo(int title, int icon) {
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
    public static UsefulInfo getItemByTag(UsefulInfo tag) {
        return UsefulInfo.valueOf(tag.name());
    }

    /**
     * get page data
     *
     * @return
     */
    public static List<UsefulInfo> getPage() {
        List<UsefulInfo> list = new ArrayList<>();

        for (UsefulInfo item : UsefulInfo.values()) {
            list.add(item);
        }
        return list;
    }
}
