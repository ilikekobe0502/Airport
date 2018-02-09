package com.whatsmedia.ttia.response.data;

import android.content.Context;
import android.text.TextUtils;

import com.whatsmedia.ttia.R;
import com.whatsmedia.ttia.newresponse.data.FlightsListData;
import com.whatsmedia.ttia.utility.Preferences;
import com.whatsmedia.ttia.utility.Util;

import java.util.ArrayList;
import java.util.HashMap;
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
    public static List<DialogContentData> getFlightDetail(Context context, FlightsListData data) {
        String locale = Preferences.getLocaleSetting(context);
        List<DialogContentData> list = new ArrayList<>();
        DialogContentData item = new DialogContentData();

        String contactsLocation = "";
        String airLine = "";
        boolean isDeparture;
        HashMap<String, Long> expectTimeDif;

        // TODO: 2017/10/30 多語系部分要看名字要怎麼處理
        airLine = !TextUtils.isEmpty(data.getAirlineName()) ? data.getAirlineName().trim() : "";

        switch (locale) {
            default:
            case "zh_TW":
                contactsLocation = !TextUtils.isEmpty(data.getContactsLocationChinese()) ? data.getContactsLocationChinese().trim() : "";
//                airLine = !TextUtils.isEmpty(data.getCTName()) ? data.getCTName().trim() : "";
                break;
            case "zh_CN":
                contactsLocation = !TextUtils.isEmpty(data.getContactsLocationChinese()) ? data.getContactsLocationChinese().trim() : "";
//                airLine = !TextUtils.isEmpty(data.getCSName()) ? data.getCSName().trim() : "";
                break;
            case "en":
                contactsLocation = !TextUtils.isEmpty(data.getContactsLocationEng()) ? data.getContactsLocationEng().trim() : "";
//                airLine = !TextUtils.isEmpty(data.getEName()) ? data.getEName().trim() : "";
                break;
            case "ja":
                contactsLocation = !TextUtils.isEmpty(data.getContactsLocationEng()) ? data.getContactsLocationEng().trim() : "";
//                airLine = !TextUtils.isEmpty(data.getJName()) ? data.getJName().trim() : "";
                break;
        }


        item.setTitle(context.getString(R.string.flight_takeoff_detail_dialog_airname));
        item.setContent(airLine);
        list.add(item);
        item = new DialogContentData();
        item.setTitle(context.getString(R.string.flight_takeoff_detail_dialog_airnumber));
        item.setContent(!TextUtils.isEmpty(data.getAirlineCode()) ?
                !TextUtils.isEmpty(data.getShifts()) ? String.format("%1$s%2$s", data.getAirlineCode().trim(), data.getShifts()) : data.getAirlineCode()
                : "");
        list.add(item);
        item = new DialogContentData();

        //抵達航班
        if (!TextUtils.equals(data.getKinds(), FlightsInfoData.TAG_KIND_DEPARTURE)) {
            item.setTitle(context.getString(R.string.flight_arrival_detail_dialog_arrivelocation));
            isDeparture = false;
        } else {//出發航班
            item.setTitle(context.getString(R.string.flight_takeoff_detail_dialog_arrivelocation));
            isDeparture = true;
        }

        item.setContent(contactsLocation);
        list.add(item);
        item = new DialogContentData();

        /*
        /如果航班狀態為"時間更改"或"延遲"或"取消航班"
        /則只有[預計時間]需要顯示
        /並且將[實際時間]的欄位塞入預計時間
        */
        if (data.getFlightStatus().contains(FlightsInfoData.TAG_SCHEDULE_CHANGE) ||
                data.getFlightStatus().contains(FlightsInfoData.TAG_DELAY) ||
                data.getFlightStatus().contains(FlightsInfoData.TAG_CANCELLED)) {
            //出發航班
            if (isDeparture) {
                item.setTitle(context.getString(R.string.flight_takeoff_detail_dialog_exceptime));
                item.setContent(String.format("%1$s %2$s", !TextUtils.isEmpty(data.getExpectedDate()) ? data.getExpectedDate().trim() : ""
                        , !TextUtils.isEmpty(data.getExpectedTime()) ? data.getExpectedTime().trim() : ""));
                list.add(item);
                item = new DialogContentData();
                item.setTitle(context.getString(R.string.flight_takeoff_detail_dialog_actualtime));
            } else {//抵達航班
                item.setTitle(context.getString(R.string.flight_arrival_detail_dialog_exceptime));
                item.setContent(String.format("%1$s %2$s", !TextUtils.isEmpty(data.getExpectedDate()) ? data.getExpectedDate().trim() : ""
                        , !TextUtils.isEmpty(data.getExpectedTime()) ? data.getExpectedTime().trim() : ""));
                list.add(item);
                item = new DialogContentData();
                item.setTitle(context.getString(R.string.flight_arrival_detail_dialog_actualtime));
            }
            item.setContent("");
            list.add(item);
        } else if (data.getFlightStatus().contains(FlightsInfoData.TAG_ARRIVED) ||
                data.getFlightStatus().contains(FlightsInfoData.TAG_DEPARTED)) {
            //出發航班
            if (isDeparture) {
                item.setTitle(context.getString(R.string.flight_takeoff_detail_dialog_exceptime));
                if (!TextUtils.isEmpty(data.getExpectedDate()) && !TextUtils.isEmpty(data.getExpectedTime())) {
                    item.setContent(String.format("%1$s %2$s", !TextUtils.isEmpty(data.getExpectedDate()) ? data.getExpectedDate().trim() : ""
                            , !TextUtils.isEmpty(data.getExpectedTime()) ? data.getExpectedTime().trim() : ""));
                } else {
                    item.setContent(String.format("%1$s %2$s", !TextUtils.isEmpty(data.getExpressDate()) ? data.getExpressDate().trim() : ""
                            , !TextUtils.isEmpty(data.getExpressTime()) ? data.getExpressTime().trim() : ""));
                }
                list.add(item);
                item = new DialogContentData();
                item.setTitle(context.getString(R.string.flight_takeoff_detail_dialog_actualtime));
            } else {//抵達航班
                item.setTitle(context.getString(R.string.flight_arrival_detail_dialog_exceptime));
                if (!TextUtils.isEmpty(data.getExpectedDate()) && !TextUtils.isEmpty(data.getExpectedTime())) {
                    item.setContent(String.format("%1$s %2$s", !TextUtils.isEmpty(data.getExpectedDate()) ? data.getExpectedDate().trim() : ""
                            , !TextUtils.isEmpty(data.getExpectedTime()) ? data.getExpectedTime().trim() : ""));
                } else {
                    item.setContent(String.format("%1$s %2$s", !TextUtils.isEmpty(data.getExpressDate()) ? data.getExpressDate().trim() : ""
                            , !TextUtils.isEmpty(data.getExpressTime()) ? data.getExpressTime().trim() : ""));
                }
                list.add(item);
                item = new DialogContentData();
                item.setTitle(context.getString(R.string.flight_arrival_detail_dialog_actualtime));
            }


            item.setContent(String.format("%1$s %2$s", !TextUtils.isEmpty(data.getExpectedDate()) ? data.getExpectedDate().trim() : ""
                    , !TextUtils.isEmpty(data.getExpectedTime()) ? data.getExpectedTime().trim() : ""));
            list.add(item);
        } else {
            //出發航班
            if (isDeparture) {
                item.setTitle(context.getString(R.string.flight_takeoff_detail_dialog_exceptime));
                item.setContent(String.format("%1$s %2$s", !TextUtils.isEmpty(data.getExpressDate()) ? data.getExpressDate().trim() : ""
                        , !TextUtils.isEmpty(data.getExpressTime()) ? data.getExpressTime().trim() : ""));
                list.add(item);
                item = new DialogContentData();
                item.setTitle(context.getString(R.string.flight_takeoff_detail_dialog_actualtime));
            } else {//抵達航班
                item.setTitle(context.getString(R.string.flight_arrival_detail_dialog_exceptime));
                item.setContent(String.format("%1$s %2$s", !TextUtils.isEmpty(data.getExpressDate()) ? data.getExpressDate().trim() : ""
                        , !TextUtils.isEmpty(data.getExpressTime()) ? data.getExpressTime().trim() : ""));
                list.add(item);
                item = new DialogContentData();
                item.setTitle(context.getString(R.string.flight_arrival_detail_dialog_actualtime));
            }

            expectTimeDif = Util.getDifferentTimeWithNowTime(String.format("%1$s %2$s", data.getExpectedDate(), data.getExpectedTime()), Util.TAG_FORMAT_ALL);
            if (expectTimeDif.get(Util.TAG_HOUR) <= 0 && expectTimeDif.get(Util.TAG_MIN) <= 0 && expectTimeDif.get(Util.TAG_SEC) <= 0) {
                item.setContent(String.format("%1$s %2$s", !TextUtils.isEmpty(data.getExpectedDate()) ? data.getExpectedDate().trim() : ""
                        , !TextUtils.isEmpty(data.getExpectedTime()) ? data.getExpectedTime().trim() : ""));
            } else {
                item.setContent("");
            }
            list.add(item);
        }

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
