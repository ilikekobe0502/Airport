package com.whatmedia.ttia.connect;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.whatmedia.ttia.enums.FlightInfo;
import com.whatmedia.ttia.response.data.ExchangeRateData;
import com.whatmedia.ttia.response.data.FlightSearchData;
import com.whatmedia.ttia.response.data.FlightsInfoData;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * Created by neo_mac on 2017/6/6.
 */

public class ApiConnect extends StateCode {
    private final static String TAG = ApiConnect.class.getSimpleName();

    private final static String TAG_HOST = "http://125.227.250.187:8867/api/";
    private final static MediaType TAG_JSON = MediaType.parse("application/json; charset=utf-8");

    public final static String TAG_IMAGE_HOST = "http://125.227.250.187:8866/";

    private static OkHttpClient mClient;
    private static ApiConnect mApiConnect;
    private static Context mContext;

    public ApiConnect() {
    }

    /**
     * API connect instance
     *
     * @param context
     * @return
     */
    public static ApiConnect getInstance(Context context) {

        mClient = new OkHttpClient.Builder().build();

        if (mApiConnect == null) {
            mContext = context;
            mApiConnect = new ApiConnect();
        }
        return mApiConnect;
    }

    /**
     * Get Api method
     *
     * @param url
     * @param callback
     */
    private static void getApi(HttpUrl url, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        mClient.newCall(request).enqueue(callback);
        Log.d(TAG, request.url().toString());
    }

    /**
     * Post Api method
     *
     * @param path
     * @param body
     * @param callback
     */
    private static void postApi(String path, RequestBody body, Callback callback) {
        RequestBody requestBody;
        if (body == null) {
            requestBody = new RequestBody() {
                @Nullable
                @Override
                public MediaType contentType() {
                    return null;
                }

                @Override
                public void writeTo(BufferedSink sink) throws IOException {

                }
            };
        } else
            requestBody = body;

        StringBuilder url = new StringBuilder();
//        url.append(TAG_HTTP).append(path);
        Request request = new Request.Builder()
                .url(url.toString())
                .post(requestBody)
                .build();
        mClient.newCall(request).enqueue(callback);
        Log.d(TAG, request.url().toString());
    }

    /**
     * 搜尋航班資訊 by keyWord
     *
     * @param searchData
     * @param callback
     */
    public static void getSearchFlightsInfoByKeyWord(FlightSearchData searchData, Callback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "get_flight_information")
                .newBuilder()
                .addQueryParameter("KeyWord", !TextUtils.isEmpty(searchData.getKeyWord()) ? searchData.getKeyWord() : "")
                .addQueryParameter("lan", "tw")
                .addQueryParameter("QueryType", !TextUtils.isEmpty(searchData.getQueryType()) ? searchData.getQueryType() : "")
                .build();
        getApi(url, callback);
    }

    /**
     * 搜尋航班資訊 by date
     *
     * @param searchData
     * @param callback
     */
    public static void getSearchFlightsInfoByDate(FlightSearchData searchData, Callback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "get_flight_information")
                .newBuilder()
                .addQueryParameter("ExpressTime", !TextUtils.isEmpty(searchData.getExpressTime()) ? searchData.getExpressTime() : "")
                .addQueryParameter("lan", "tw")
                .addQueryParameter("QueryType", !TextUtils.isEmpty(searchData.getQueryType()) ? searchData.getQueryType() : "")
                .build();
        getApi(url, callback);
    }

    /**
     * Get my flights info
     *
     * @param callback
     */
    public static void getMyFlightsInfo(Callback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "get_myflight")
                .newBuilder()
                .addQueryParameter("UserID", "123")
                .addQueryParameter("Devicetoken", "123")
                .addQueryParameter("DeviceType", "1")
                .addQueryParameter("lan", "tw")
                .build();
        getApi(url, callback);
    }

    /**
     * Save my flights
     *
     * @param data
     * @param callback
     */
    public static void doMyFlights(FlightsInfoData data, Callback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "save_flight")
                .newBuilder()
                .addQueryParameter("UserID", "123")
                .addQueryParameter("Devicetoken", "123")
                .addQueryParameter("DeviceType", "1")
                .addQueryParameter("AirlineCode", data.getAirlineCode())
                .addQueryParameter("Shifts", data.getShift())//需要補滿四位數
                .addQueryParameter("ExpressDate", data.getExpressDate())
                .addQueryParameter("ExpressTime", data.getExpressTime())
                .addQueryParameter("type", data.getType())//0為新增1為刪除
                .build();
        getApi(url, callback);
    }

    /**
     * 公共廁所
     *
     * @param callback
     */
    public static void getPublicToilet(Callback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "get_PublicToilet")
                .newBuilder()
                .addQueryParameter("lan", "tw")
                .build();
        getApi(url, callback);
    }

    /**
     * 機場設施
     *
     * @param callback
     */
    public static void getAirportFacility(Callback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "get_airport_facility_info")
                .newBuilder()
                .addQueryParameter("lan", "tw")
                .build();
        getApi(url, callback);
    }

    /**
     * 航廈列別代碼
     *
     * @param callback
     */
    public static void getTerminalCode(Callback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "get_C_TerminalsCode")
                .newBuilder()
                .addQueryParameter("lan", "tw")
                .build();
        getApi(url, callback);
    }

    /**
     * 區域列別代碼
     *
     * @param callback
     */
    public static void getAreaCode(Callback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "get_C_AreaCode")
                .newBuilder()
                .addQueryParameter("lan", "tw")
                .build();
        getApi(url, callback);
    }

    /**
     * 樓層列別代碼
     *
     * @param callback
     */
    public static void getFloorCode(Callback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "get_C_FloorCode")
                .newBuilder()
                .addQueryParameter("lan", "tw")
                .build();
        getApi(url, callback);
    }

    /**
     * 餐廳列別代碼
     *
     * @param callback
     */
    public static void getRestaurantCode(Callback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "get_C_RestaurantTypeCode")
                .newBuilder()
                .addQueryParameter("lan", "tw")
                .build();
        getApi(url, callback);
    }

    /**
     * 取得餐廳資訊
     *
     * @param terminalsID
     * @param areaID
     * @param restaurantTypeID
     * @param floorID
     * @param callback
     */
    public static void getRestaurantInfo(String terminalsID, String areaID, String restaurantTypeID, String floorID, Callback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "seach_restaurant")
                .newBuilder()
                .addQueryParameter("lan", "tw")
                .addQueryParameter("TerminalsID", terminalsID)
                .addQueryParameter("AreaID", areaID)
                .addQueryParameter("RestaurantTypeID", restaurantTypeID)
                .addQueryParameter("FloorID", floorID)
                .build();
        getApi(url, callback);
    }

    /**
     * 巴士資訊
     *
     * @param callback
     */
    public static void getAirportBus(Callback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "get_C_Buses")
                .newBuilder()
                .addQueryParameter("lan", "tw")
                .build();
        getApi(url, callback);
    }

    /**
     * 道路救援資訊
     *
     * @param callback
     */
    public static void getRoadsideAssistance(Callback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "get_C_RoadsideAssistance")
                .newBuilder()
                .addQueryParameter("lan", "tw")
                .build();
        getApi(url, callback);
    }

    /**
     * 計程車資訊
     *
     * @param callback
     */
    public static void getTaxi(Callback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "get_C_Taxi")
                .newBuilder()
                .addQueryParameter("lan", "tw")
                .build();
        getApi(url, callback);
    }

    /**
     * 巡迴巴士資訊
     *
     * @param callback
     */
    public static void getTourBus(Callback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "get_C_Shuttles")
                .newBuilder()
                .addQueryParameter("lan", "tw")
                .build();
        getApi(url, callback);
    }

    /**
     * 機場捷運/高鐵資訊
     *
     * @param callback
     */
    public static void getAirportMrt(Callback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "get_C_HighTrail")
                .newBuilder()
                .addQueryParameter("lan", "tw")
                .build();
        getApi(url, callback);
    }

    /**
     * 電車資訊
     *
     * @param callback
     */
    public static void getSkyTrain(Callback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "get_C_Skytrain")
                .newBuilder()
                .addQueryParameter("lan", "tw")
                .build();
        getApi(url, callback);
    }

    /**
     * 首頁停車資訊
     *
     * @param callback
     */
    public static void getHomeParkIngInfo(Callback callback) {
        HttpUrl url = HttpUrl.parse("http://app.taoyuan-airport.com/newttia/ap_park/park_available_reader.php")
                .newBuilder()
                .build();
        getApi(url, callback);
    }

    /**
     * 停車資訊
     *
     * @param callback
     */
    public static void getParkIngInfo(Callback callback) {
        HttpUrl url = HttpUrl.parse("http://app.taoyuan-airport.com/newttia/ap_park/park_info_reader.php")
                .newBuilder()
                .build();
        getApi(url, callback);
    }

    /**
     * 取得使用者最新消息
     *
     * @param callback
     */
    public static void getUserNews(Callback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "get_NewsUserList")
                .newBuilder()
                .addQueryParameter("UserID", "A123456789")
                .addQueryParameter("Devicetoken", "B123456789")
                .addQueryParameter("DeviceType", "1")
                .addQueryParameter("lan", "tw")
                .build();
        getApi(url, callback);
    }

    /**
     * 取得使用者緊急通知
     *
     * @param callback
     */
    public static void getUserEmergency(Callback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "get_EmergencyUserList")
                .newBuilder()
                .addQueryParameter("UserID", "A123456789")
                .addQueryParameter("Devicetoken", "B123456789")
                .addQueryParameter("DeviceType", "1")
                .addQueryParameter("lan", "tw")
                .build();
        getApi(url, callback);
    }

    /**
     * 取得使用者貼心提醒
     *
     * @param callback
     */
    public static void getUserSweetNotify(Callback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "get_IntimateReminderUserList")
                .newBuilder()
                .addQueryParameter("UserID", "A123456789")
                .addQueryParameter("Devicetoken", "B123456789")
                .addQueryParameter("DeviceType", "1")
                .addQueryParameter("lan", "tw")
                .build();
        getApi(url, callback);
    }

    /**
     * 失物協尋資訊
     *
     * @param callback
     */
    public static void getLostAndFound(Callback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "get_C_Lost")
                .newBuilder()
                .addQueryParameter("lan", "tw")
                .build();
        getApi(url, callback);
    }

    /**
     * 稅率換算
     *
     * @param data
     * @param callback
     */
    public void getExchangeRate(ExchangeRateData data, Callback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "get_exchange_rate")
                .newBuilder()
                .addQueryParameter("source", !TextUtils.isEmpty(data.getSource()) ? data.getSource() : "")
                .addQueryParameter("target", !TextUtils.isEmpty(data.getTarget()) ? data.getTarget() : "")
                .addQueryParameter("amount", !TextUtils.isEmpty(data.getAmount()) ? data.getAmount() : "")
                .build();
        getApi(url, callback);
    }
    /**
     * 問卷調查題目
     *
     * @param callback
     */
    public static void getQuestionnaire(Callback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "get_Questions")
                .newBuilder()
                .addQueryParameter("lan", "tw")
                .build();
        getApi(url, callback);
    }

    /**
     * 送出問卷調查結果
     *
     * @param callback
     */
    public static void sendQuestionnaireResult(String answer,Callback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "save_Answers")
                .newBuilder()
                .addQueryParameter("UserID", "A123456789")
                .addQueryParameter("Devicetoken", "B123456789")
                .addQueryParameter("DeviceType", "1")
                .addQueryParameter("answer", answer)
                .build();
        getApi(url, callback);
    }

    /**
     * 取得旅行外文
     *
     * @param callback
     */
    public static void getLanguages(String id,Callback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "get_travel_language")
                .newBuilder()
                .addQueryParameter("type", id)
                .build();
        getApi(url, callback);
    }
}
