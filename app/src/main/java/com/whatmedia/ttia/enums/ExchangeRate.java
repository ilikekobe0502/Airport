package com.whatmedia.ttia.enums;

import com.whatmedia.ttia.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by neo_mac on 2017/8/6.
 */

public enum ExchangeRate {
    TAG_AUD(R.string.currency_conversion_aud, R.drawable.flag_aud_mdpi),
    TAG_CAD(R.string.currency_conversion_cad, R.drawable.flag_cad_mdpi),
    TAG_CHF(R.string.currency_conversion_chf, R.drawable.flag_chf_mdpi),
    TAG_CNY(R.string.currency_conversion_cny, R.drawable.flag_cny_mdpi),
    TAG_EUR(R.string.currency_conversion_eur, R.drawable.flag_eur_mdpi),
    TAG_GBP(R.string.currency_conversion_gbp, R.drawable.flag_gbp_mdpi),
    TAG_HKD(R.string.currency_conversion_hkd, R.drawable.flag_hkd_mdpi),
    TAG_IDR(R.string.currency_conversion_idr, R.drawable.flag_idr_mdpi),
    TAG_JPY(R.string.currency_conversion_jpy, R.drawable.flag_jpy_mdpi),
    TAG_KRW(R.string.currency_conversion_krw, R.drawable.flag_krw_mdpi),
    TAG_MYR(R.string.currency_conversion_myr, R.drawable.flag_myr_mdpi),
    TAG_NZD(R.string.currency_conversion_nzd, R.drawable.flag_nzd_mdpi),
    TAG_PHP(R.string.currency_conversion_php, R.drawable.flag_php_mdpi),
    TAG_SEK(R.string.currency_conversion_sek, R.drawable.flag_sek__mdpi),
    TAG_SGD(R.string.currency_conversion_sgd, R.drawable.flag_sgd_mdpi),
    TAG_THB(R.string.currency_conversion_thb, R.drawable.flag_thb_mdpi),
    TAG_TWD(R.string.currency_conversion_twd, R.drawable.flag_twd_mdpi),
    TAG_USD(R.string.currency_conversion_usd, R.drawable.flag_usd_mdpi),
    TAG_VND(R.string.currency_conversion_vnd, R.drawable.flag_vnd_mdpi),
    TAG_ZAR(R.string.currency_conversion_zar, R.drawable.flag_zar_mdpi);

    private int title;
    private int icon;

    ExchangeRate(int title, int icon) {
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
    public static ExchangeRate getItemByTag(ExchangeRate tag) {
        return ExchangeRate.valueOf(tag.name());
    }

    /**
     * get page data
     *
     * @return
     */
    public static List<ExchangeRate> getPage() {
        List<ExchangeRate> list = new ArrayList<>();

        for (ExchangeRate item : ExchangeRate.values()) {
            list.add(item);
        }
        return list;
    }
}