package com.whatmedia.ttia.response.data;

import android.content.Context;
import android.text.TextUtils;

import com.whatmedia.ttia.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by neo_mac on 2017/8/4.
 */

public class DialogContentData {
    private String title;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Get Flight detail
     *
     * @param context
     * @param data
     * @return
     */
    public static List<DialogContentData> getFlightDetail(Context context, FlightsInfoData data) {
        List<DialogContentData> list = new ArrayList<>();
        DialogContentData item = new DialogContentData();

        item.setTitle(context.getString(R.string.dialog_airline_name));
        item.setContent(!TextUtils.isEmpty(data.getAirLineCName()) ? data.getAirLineCName().trim() : "");
        list.add(item);
        item = new DialogContentData();
        item.setTitle(context.getString(R.string.dialog_flight_code));
        item.setContent(!TextUtils.isEmpty(data.getFlightCode()) ? data.getFlightCode().trim() : "");
        list.add(item);
        item = new DialogContentData();
        item.setTitle(context.getString(R.string.dialog_target_location));
        item.setContent(!TextUtils.isEmpty(data.getContactsLocationChinese()) ? data.getContactsLocationChinese().trim() : "");
        list.add(item);
        item = new DialogContentData();
        item.setTitle(context.getString(R.string.dialog_expected_arrive_time));
        item.setContent(!TextUtils.isEmpty(data.getExpectedTime()) ? data.getExpectedTime().trim() : "");
        list.add(item);
        item = new DialogContentData();
        item.setTitle(context.getString(R.string.dialog_actual_arrive_time));
        item.setContent(!TextUtils.isEmpty(data.getExpressTime()) ? data.getExpressTime().trim() : "");
        list.add(item);
        item = new DialogContentData();
        item.setTitle(context.getString(R.string.dialog_terminal));
        item.setContent(!TextUtils.isEmpty(data.getTerminals()) ? data.getTerminals().trim() : "");
        list.add(item);
        item = new DialogContentData();
        item.setTitle(context.getString(R.string.dialog_carousel));
        item.setContent(!TextUtils.isEmpty(data.getLuggageCarousel()) ? data.getLuggageCarousel().trim() : "");
        list.add(item);
        item = new DialogContentData();
        item.setTitle(context.getString(R.string.dialog_gate));
        item.setContent(!TextUtils.isEmpty(data.getGate()) ? data.getGate().trim() : "");
        list.add(item);
        return list;
    }
}
