package com.whatmedia.ttia.enums;

import com.whatmedia.ttia.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by neo_mac on 2017/8/6.
 */

public enum StoreOffers {
    TAG_SOUVENIR(R.string.title_souvenirs, R.drawable.store_offers_06_01),
    TAG_STORE_INFO(R.string.store_offers_info, R.drawable.store_offers_06_02);

    private int title;
    private int icon;

    StoreOffers(int title, int icon) {
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
    public static StoreOffers getItemByTag(StoreOffers tag) {
        return StoreOffers.valueOf(tag.name());
    }

    /**
     * get page data
     *
     * @return
     */
    public static List<StoreOffers> getPage() {
        List<StoreOffers> list = new ArrayList<>();

        for (StoreOffers item : StoreOffers.values()) {
            list.add(item);
        }
        return list;
    }
}