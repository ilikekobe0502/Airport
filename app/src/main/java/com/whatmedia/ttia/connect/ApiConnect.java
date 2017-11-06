package com.whatmedia.ttia.connect;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.whatmedia.ttia.enums.LanguageSetting;
import com.whatmedia.ttia.response.data.FlightsInfoData;
import com.whatmedia.ttia.utility.Preferences;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/**
 * Created by neo_mac on 2017/6/6.
 */

public class ApiConnect extends StateCode {
    private final static String TAG = ApiConnect.class.getSimpleName();

    private final static String TAG_HOST = "http://210.241.14.99/api/";
    private final static MediaType TAG_JSON = MediaType.parse("application/json; charset=utf-8");

    public final static String TAG_IMAGE_HOST = "http://210.241.14.99/";

    public final static String TAG_DEVICE_TYPE = "2";

    public static String TAG_DEVICE_ID;

    private static String mLocale = "tw";
    //無提供全語言的API，請使用下面這個
    private static String mLocaleApiError = "tw";

    private static String mToken;

    private static OkHttpClient mClient;
    private static ApiConnect mApiConnect;
    private static Context mContext;

    public interface MyCallback {
        void onFailure(Call call, IOException e, boolean timeout);

        void onResponse(Call call, MyResponse response) throws IOException;
    }

    public ApiConnect() {
    }

    /**
     * API connect instance
     *
     * @param context
     * @return
     */
    public static ApiConnect getInstance(Context context) {

        mClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(3000, TimeUnit.MILLISECONDS)
                .writeTimeout(3000, TimeUnit.MILLISECONDS)
                .build();

        if (mApiConnect == null) {
            mContext = context;
            mApiConnect = new ApiConnect();
        }

//        if (TAG_DEVICE_ID == null) {
//            TAG_DEVICE_ID = Util.getDeviceId(context);
//        }

        if (TextUtils.isEmpty(mToken) || TextUtils.equals(mToken, Preferences.TAG_ERROR)) {
            mToken = Preferences.getFCMToken(context);
        }
        switchLocale();

        return mApiConnect;
    }

    /**
     * Switch locale
     */
    private static void switchLocale() {
        String localeCache = Preferences.getLocaleSetting(mContext);
        if (TextUtils.equals(localeCache, LanguageSetting.TAG_TRADITIONAL_CHINESE.getLocale().toString())) {
            mLocale = "tw";
        } else if (TextUtils.equals(localeCache, LanguageSetting.TAG_SIMPLIFIED_CHINESE.getLocale().toString())) {
            mLocale = "cn";
        } else if (TextUtils.equals(localeCache, LanguageSetting.TAG_JAPANESE.getLocale().toString())) {
            mLocale = "jp";
        } else if (TextUtils.equals(localeCache, LanguageSetting.TAG_ENGLISH.getLocale().toString())) {
            mLocale = "en";
        } else {
            mLocale = "tw";
        }
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
     * Get Api method
     *
     * @param url
     * @param callback
     */
    private static void getApi(HttpUrl url, final MyCallback callback) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (e.toString().contains("SocketTimeoutException")) {
                    Log.e(TAG,"Timeout");
                    callback.onFailure(call, e, true);

                }else {
                    Log.e(TAG,"not timeout");
                    callback.onFailure(call, e, false);

                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    MyResponse myResponse = new MyResponse();
                    myResponse.setMyResponseBody(response.body().string());
                    myResponse.setMessage(response.message());

                    Log.e(TAG,myResponse.message());
                    myResponse.setCode(response.code());
                    callback.onResponse(call, myResponse);
                } catch (IOException e) {
                    onFailure(call, e);
                }
            }
        });
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
     * Get my flights info
     *
     * @param callback
     */
    public static void getMyFlightsInfo(MyCallback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "get_myflight")
                .newBuilder()
                .addQueryParameter("UserID", TAG_DEVICE_ID)
                .addQueryParameter("Devicetoken", mToken)
                .addQueryParameter("DeviceType", TAG_DEVICE_TYPE)
                .addQueryParameter("lan", mLocaleApiError)
                .build();
        getApi(url, callback);
    }

    /**
     * Save my flights
     *
     * @param data
     * @param callback
     */
    public static void doMyFlights(FlightsInfoData data, MyCallback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "save_flight")
                .newBuilder()
                .addQueryParameter("UserID", TAG_DEVICE_ID)
                .addQueryParameter("Devicetoken", mToken)
                .addQueryParameter("DeviceType", TAG_DEVICE_TYPE)
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
    public static void getPublicToilet(MyCallback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "get_PublicToilet")
                .newBuilder()
                .addQueryParameter("lan", mLocale)
                .build();
        getApi(url, callback);
    }

    /**
     * 機場設施
     *
     * @param callback
     */
    public static void getAirportFacility(MyCallback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "get_airport_facility_info")
                .newBuilder()
                .addQueryParameter("lan", mLocale)
                .build();
        getApi(url, callback);
    }

    /**
     * 航廈類別代碼
     *
     * @param callback
     */
    public static void getTerminalCode(MyCallback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "get_C_TerminalsCode")
                .newBuilder()
                .addQueryParameter("lan", mLocale)
                .build();
        getApi(url, callback);
    }

    /**
     * 區域類別代碼
     *
     * @param callback
     */
    public static void getAreaCode(MyCallback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "get_C_AreaCode")
                .newBuilder()
                .addQueryParameter("lan", mLocale)
                .build();
        getApi(url, callback);
    }

    /**
     * 樓層類別代碼
     *
     * @param callback
     */
    public static void getFloorCode(MyCallback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "get_C_FloorCode")
                .newBuilder()
                .addQueryParameter("lan", mLocale)
                .build();
        getApi(url, callback);
    }

    /**
     * 餐廳類別代碼
     *
     * @param callback
     */
    public static void getRestaurantCode(MyCallback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "get_C_RestaurantTypeCode")
                .newBuilder()
                .addQueryParameter("lan", mLocale)
                .build();
        getApi(url, callback);
    }

    /**
     * 商店類別代碼
     *
     * @param callback
     */
    public static void getStoreCode(MyCallback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "get_StoreCode")
                .newBuilder()
                .addQueryParameter("lan", mLocale)
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
    public static void getRestaurantInfo(String terminalsID, String areaID, String restaurantTypeID, String floorID, MyCallback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "seach_restaurant")
                .newBuilder()
                .addQueryParameter("lan", mLocale)
                .addQueryParameter("TerminalsID", terminalsID)
                .addQueryParameter("AreaID", areaID)
                .addQueryParameter("RestaurantTypeID", restaurantTypeID)
                .addQueryParameter("FloorID", floorID)
                .build();
        getApi(url, callback);
    }

    /**
     * 取得商店資訊
     *
     * @param terminalsID
     * @param areaID
     * @param StoreTypeID
     * @param floorID
     * @param callback
     */
    public static void getStoreInfo(String terminalsID, String areaID, String StoreTypeID, String floorID, MyCallback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "get_Store")
                .newBuilder()
                .addQueryParameter("lan", mLocale)
                .addQueryParameter("TerminalsID", terminalsID)
                .addQueryParameter("AreaID", areaID)
                .addQueryParameter("Store_type", StoreTypeID)
                .addQueryParameter("FloorID", floorID)
                .build();
        getApi(url, callback);
    }

    /**
     * 巴士資訊
     *
     * @param callback
     */
    public static void getAirportBus(MyCallback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "get_C_Buses")
                .newBuilder()
                .addQueryParameter("lan", mLocale)
                .build();
        getApi(url, callback);
    }

    /**
     * 道路救援資訊
     *
     * @param callback
     */
    public static void getRoadsideAssistance(MyCallback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "get_C_RoadsideAssistance")
                .newBuilder()
                .addQueryParameter("lan", mLocale)
                .build();
        getApi(url, callback);
    }

    /**
     * 計程車資訊
     *
     * @param callback
     */
    public static void getTaxi(MyCallback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "get_C_Taxi")
                .newBuilder()
                .addQueryParameter("lan", mLocale)
                .build();
        getApi(url, callback);
    }

    /**
     * 巡迴巴士資訊
     *
     * @param callback
     */
    public static void getTourBus(MyCallback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "get_C_Shuttles")
                .newBuilder()
                .addQueryParameter("lan", mLocale)
                .build();
        getApi(url, callback);
    }

    /**
     * 機場捷運/高鐵資訊
     *
     * @param callback
     */
    public static void getAirportMrt(MyCallback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "get_C_HighTrail")
                .newBuilder()
                .addQueryParameter("lan", mLocale)
                .build();
        getApi(url, callback);
    }

    /**
     * 電車資訊
     *
     * @param callback
     */
    public static void getSkyTrain(MyCallback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "get_C_Skytrain")
                .newBuilder()
                .addQueryParameter("lan", mLocale)
                .build();
        getApi(url, callback);
    }

    /**
     * 首頁停車資訊
     *
     * @param callback
     */
    public static void getHomeParkIngInfo(MyCallback callback) {
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
    public static void getParkIngInfo(MyCallback callback) {
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
    public static void getUserNews(MyCallback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "get_AirportSecretary")
                .newBuilder()
                .addQueryParameter("UserID", TAG_DEVICE_ID)
                .addQueryParameter("SearchType", "News")
                .addQueryParameter("lan", mLocaleApiError)
                .build();
        getApi(url, callback);
    }

    /**
     * 取得使用者緊急通知
     *
     * @param callback
     */
    public static void getUserEmergency(MyCallback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "get_AirportSecretary")
                .newBuilder()
                .addQueryParameter("UserID", TAG_DEVICE_ID)
                .addQueryParameter("SearchType", "Emer")
                .addQueryParameter("lan", mLocale)
                .build();
        getApi(url, callback);
    }

    /**
     * 取得使用者貼心提醒
     *
     * @param callback
     */
    public static void getUserSweetNotify(MyCallback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "get_AirportSecretary")
                .newBuilder()
                .addQueryParameter("UserID", TAG_DEVICE_ID)
                .addQueryParameter("SearchType", "IRemind")
                .addQueryParameter("lan", mLocale)
                .build();
        getApi(url, callback);
    }

    /**
     * 失物協尋資訊
     *
     * @param callback
     */
    public static void getLostAndFound(MyCallback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "get_C_Lost")
                .newBuilder()
                .addQueryParameter("lan", mLocale)
                .build();
        getApi(url, callback);
    }


    /**
     * 問卷調查題目
     *
     * @param callback
     */
    public static void getQuestionnaire(MyCallback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "get_Questions")
                .newBuilder()
                .addQueryParameter("lan", mLocale)
                .build();
        getApi(url, callback);
    }

    /**
     * 送出問卷調查結果
     *
     * @param callback
     */
    public static boolean sendQuestionnaireResult(String answer, MyCallback callback) {
        if (!TextUtils.isEmpty(mToken) && !TextUtils.equals(mToken, Preferences.TAG_ERROR)) {
            HttpUrl url = HttpUrl.parse(TAG_HOST + "save_Answers")
                    .newBuilder()
                    .addQueryParameter("UserID", TAG_DEVICE_ID)
                    .addQueryParameter("Devicetoken", mToken)
                    .addQueryParameter("DeviceType", TAG_DEVICE_TYPE)
                    .addQueryParameter("answer", answer)
                    .build();
            getApi(url, callback);
            return true;
        }
        return false;
    }

    /**
     * 取得旅行外文
     *
     * @param callback
     */
    public static void getLanguages(String id, MyCallback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "get_travel_language")
                .newBuilder()
                .addQueryParameter("type", id)
                .build();
        getApi(url, callback);
    }

    /**
     * 取得國際電話html
     *
     * @param callback
     */
    public static void getInternationCall(MyCallback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "get_InternationalCall")
                .newBuilder()
                .addQueryParameter("lan", mLocale)
                .build();
        getApi(url, callback);
    }

    /**
     * 取得緊急電話html
     *
     * @param callback
     */
    public static void getEmergencyCall(MyCallback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "get_EmergencyCall")
                .newBuilder()
                .addQueryParameter("lan", mLocale)
                .build();
        getApi(url, callback);
    }

    /**
     * 取得電信業者清單
     *
     * @param callback
     */
    public static void getRoamingService(MyCallback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "get_TelecommunicationsIndustryCode")
                .newBuilder()
                .addQueryParameter("lan", mLocale)
                .build();
        getApi(url, callback);
    }

    /**
     * 取得漫遊上網HTML
     *
     * @param callback
     */
    public static void getRoamingDetail(String id, MyCallback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "get_InternationalRoaming")
                .newBuilder()
                .addQueryParameter("lan", mLocale)
                .addQueryParameter("TIID", id)
                .build();
        getApi(url, callback);
    }

    /**
     * 取得紀念品資訊
     *
     * @param callback
     */
    public static void getSouvenirList(MyCallback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "get_Souvenir")
                .newBuilder()
                .addQueryParameter("lan", mLocale)
                .build();
        getApi(url, callback);
    }

    /**
     * 取得機場成就
     *
     * @param callback
     */
    public static boolean getAchievementList(MyCallback callback) {
        if (!TextUtils.isEmpty(mToken) && !TextUtils.equals(mToken, Preferences.TAG_ERROR)) {
            HttpUrl url = HttpUrl.parse(TAG_HOST + "get_Achievement")
                    .newBuilder()
                    .addQueryParameter("UserID", TAG_DEVICE_ID)
                    .addQueryParameter("lan", mLocale)
                    .build();
            getApi(url, callback);
            return true;
        }
        return false;
    }

    /**
     * @param minorID
     * @param callback
     */
    public static boolean saveAchievement(String minorID, Callback callback) {
        if (!TextUtils.isEmpty(mToken) && !TextUtils.equals(mToken, Preferences.TAG_ERROR)) {
            HttpUrl url = HttpUrl.parse(TAG_HOST + "save_Achievement")
                    .newBuilder()
                    .addQueryParameter("UserID", TAG_DEVICE_ID)
                    .addQueryParameter("Devicetoken", mToken)
                    .addQueryParameter("DeviceType", TAG_DEVICE_TYPE)
                    .addQueryParameter("Beacon_ID", minorID)
                    .build();
            getApi(url, callback);
            return true;
        }
        return false;
    }

    /**
     * 寫入/刪除場域內使用者
     */
    public static boolean registerUser(Callback callback) {
        if (!TextUtils.isEmpty(mToken) && !TextUtils.equals(mToken, Preferences.TAG_ERROR)) {
            HttpUrl url = HttpUrl.parse(TAG_HOST + "registerUser")
                    .newBuilder()
                    .addQueryParameter("UserID", TAG_DEVICE_ID)
                    .addQueryParameter("token", mToken)
                    .addQueryParameter("DeviceType", TAG_DEVICE_TYPE)
                    .build();
            getApi(url, callback);
            return true;
        }
        Log.e(TAG, "mToken is error");
        return false;
    }
    /**
     * 寫入/刪除場域內使用者
     */
    public static boolean pushNFtoUser(String minorID,Callback callback) {
        if (!TextUtils.isEmpty(mToken) && !TextUtils.equals(mToken, Preferences.TAG_ERROR)) {
            HttpUrl url = HttpUrl.parse(TAG_HOST + "PushNFtoUser")
                    .newBuilder()
                    .addQueryParameter("UserID", TAG_DEVICE_ID)
                    .addQueryParameter("token", mToken)
                    .addQueryParameter("DeviceType", TAG_DEVICE_TYPE)
                    .addQueryParameter("BeaconID", minorID)
                    .build();
            getApi(url, callback);
            return true;
        }
        Log.e(TAG, "mToken is error");
        return false;
    }
}
