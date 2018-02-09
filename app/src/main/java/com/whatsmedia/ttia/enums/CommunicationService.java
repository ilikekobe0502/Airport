package com.whatsmedia.ttia.enums;

import com.whatsmedia.ttia.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by neo_mac on 2017/8/6.
 */

public enum CommunicationService {
    TAG_INTERNATIONAL_CALL(R.string.title_international_call, R.drawable.commnunication_service_07_01),
    TAG_EMERGENCY_CALL(R.string.title_emergency_phone, R.drawable.commnunication_service_07_02),
    TAG_ROAMING_SERVICE(R.string.title_roaming_service, R.drawable.commnunication_service_07_03);

    private int title;
    private int icon;

    CommunicationService(int title, int icon) {
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
    public static CommunicationService getItemByTag(CommunicationService tag) {
        return CommunicationService.valueOf(tag.name());
    }

    /**
     * get page data
     *
     * @return
     */
    public static List<CommunicationService> getPage() {
        List<CommunicationService> list = new ArrayList<>();

        for (CommunicationService item : CommunicationService.values()) {
            list.add(item);
        }
        return list;
    }
}