package com.whatmedia.ttia.response.data;

import android.content.Context;
import android.text.TextUtils;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.utility.Preferences;

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
        String locale = Preferences.getLocaleSetting(context);
        List<DialogContentData> list = new ArrayList<>();
        DialogContentData item = new DialogContentData();

        String contactsLocation;
        String airLine;
        switch (locale) {
            default:
            case "zh_TW":
                contactsLocation = !TextUtils.isEmpty(data.getContactsLocationChinese()) ? data.getContactsLocationChinese().trim() : "";
                airLine = !TextUtils.isEmpty(data.getCTName()) ? data.getCTName().trim() : "";
                break;
            case "zh_CN":
                contactsLocation = !TextUtils.isEmpty(data.getContactsLocationChinese()) ? data.getContactsLocationChinese().trim() : "";
                airLine = !TextUtils.isEmpty(data.getCSName()) ? data.getCSName().trim() : "";
                break;
            case "en":
                contactsLocation = !TextUtils.isEmpty(data.getContactsLocationEng()) ? data.getContactsLocationEng().trim() : "";
                airLine = !TextUtils.isEmpty(data.getEName()) ? data.getEName().trim() : "";
                break;
            case "ja":
                contactsLocation = !TextUtils.isEmpty(data.getContactsLocationEng()) ? data.getContactsLocationEng().trim() : "";
                airLine = !TextUtils.isEmpty(data.getJName()) ? data.getJName().trim() : "";
                break;
        }

        item.setTitle(context.getString(R.string.flight_takeoff_detail_dialog_airname));
        item.setContent(airLine);
        list.add(item);
        item = new DialogContentData();
        item.setTitle(context.getString(R.string.flight_takeoff_detail_dialog_airnumber));
        item.setContent(!TextUtils.isEmpty(data.getFlightCode()) ? data.getFlightCode().trim() : "");
        list.add(item);
        item = new DialogContentData();
        if (!TextUtils.equals(data.getKinds(), FlightsInfoData.TAG_KIND_DEPARTURE))
            item.setTitle(context.getString(R.string.flight_arrival_detail_dialog_arrivelocation));
        else
            item.setTitle(context.getString(R.string.flight_takeoff_detail_dialog_arrivelocation));

        item.setContent(contactsLocation);
        list.add(item);
        item = new DialogContentData();
        item.setTitle(context.getString(R.string.flight_arrival_detail_dialog_exceptime));
        item.setContent(String.format("%1$s %2$s", !TextUtils.isEmpty(data.getExpectedDate()) ? data.getExpectedDate().trim() : ""
                , !TextUtils.isEmpty(data.getExpectedTime()) ? data.getExpectedTime().trim() : ""));
        list.add(item);
        item = new DialogContentData();
        item.setTitle(context.getString(R.string.flight_arrival_detail_dialog_actualtime));
        item.setContent(String.format("%1$s %2$s", !TextUtils.isEmpty(data.getExpressDate()) ? data.getExpressDate().trim() : ""
                , !TextUtils.isEmpty(data.getExpressTime()) ? data.getExpressTime().trim() : ""));
        list.add(item);
        item = new DialogContentData();
        item.setTitle(context.getString(R.string.flight_arrival_detail_dialog_terminal));
        item.setContent(!TextUtils.isEmpty(data.getTerminals()) ? data.getTerminals().trim() : "");
        list.add(item);
        item = new DialogContentData();
        item.setTitle(context.getString(R.string.flight_arrival_detail_dialog_package));
        item.setContent(!TextUtils.isEmpty(data.getLuggageCarousel()) ? data.getLuggageCarousel().trim() : "");
        list.add(item);
        item = new DialogContentData();
        item.setTitle(context.getString(R.string.flight_takeoff_detail_dialog_gate));
        item.setContent(!TextUtils.isEmpty(data.getGate()) ? data.getGate().trim() : "");
        list.add(item);
        return list;
    }
}
