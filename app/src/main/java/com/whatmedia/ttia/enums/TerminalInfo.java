package com.whatmedia.ttia.enums;

import com.whatmedia.ttia.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by neo_mac on 2017/8/4.
 */

public enum TerminalInfo {
    TAG_STORE(R.string.terminal_info_store, R.drawable.terminal_info_03_01),
    TAG_AIRPORT_FACILITIES(R.string.terminal_info_airport_facilities, R.drawable.terminal_info_03_02),
    TAG_TOILET(R.string.terminal_info_toilet, R.drawable.terminal_info_03_03);

    private int title;
    private int icon;

    TerminalInfo(int title, int icon) {
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
    public static TerminalInfo getItemByTag(TerminalInfo tag) {
        return TerminalInfo.valueOf(tag.name());
    }

    /**
     * get page data
     *
     * @return
     */
    public static List<TerminalInfo> getPage() {
        List<TerminalInfo> list = new ArrayList<>();

        for (TerminalInfo item : TerminalInfo.values()) {
            list.add(item);
        }
        return list;
    }
}
