package com.whatmedia.ttia.enums;

import com.whatmedia.ttia.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by neo_mac on 2017/8/4.
 */

public enum LanguageSetting {
    TAG_TRADITIONAL_CHINESE(R.string.zhtw, Locale.TRADITIONAL_CHINESE),
    TAG_TRADITIONAL_SIMPLIFIED(R.string.zhcn, Locale.SIMPLIFIED_CHINESE),
    TAG_ENGLISH(R.string.en, Locale.ENGLISH),
    TAG_JAPANESE(R.string.ja, Locale.JAPANESE);
    private int title;
    private Locale locale;

    LanguageSetting(int title, Locale locale) {
        this.title = title;
        this.locale = locale;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    /**
     * Get item by tag
     *
     * @param tag
     * @return
     */
    public static LanguageSetting getItemByTag(LanguageSetting tag) {
        return LanguageSetting.valueOf(tag.name());
    }

    /**
     * get page data
     *
     * @return
     */
    public static List<LanguageSetting> getPage() {
        List<LanguageSetting> list = new ArrayList<>();

        for (LanguageSetting item : LanguageSetting.values()) {
            list.add(item);
        }
        return list;
    }
}
