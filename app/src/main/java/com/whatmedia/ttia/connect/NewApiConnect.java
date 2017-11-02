package com.whatmedia.ttia.connect;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.whatmedia.ttia.newresponse.GetBaseEncodeResponse;
import com.whatmedia.ttia.newresponse.GetBaseResponse;
import com.whatmedia.ttia.newresponse.data.BaseEncodeData;
import com.whatmedia.ttia.utility.Util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by neo on 2017/10/25.
 */

public class NewApiConnect {
    private final static String TAG = NewApiConnect.class.getSimpleName();
    private final static String TAG_HOST = "https://59.127.195.228:11700/api/";
    private final static MediaType TAG_JSON = MediaType.parse("application/json");
    private final static String TAG_AES_KEY = "taoyuanairporttaoyuanairporttaoy";
    private final static String TAG_AES_IV = "taoyuanairportta";

    private static OkHttpClient mClient;
    private static NewApiConnect mApiConnect;

    private static String TAG_DEVICE_ID;
    private static String mToken;

    private void getApi(HttpUrl url, final MyCallback callback) {
        getApi(url, null, callback);
    }

    private void getApi(HttpUrl url, boolean defaultHeaders, final MyCallback callback) {
        if (defaultHeaders) {
            Headers headers = new Headers.Builder()
                    .add("deviceId", TAG_DEVICE_ID)
                    .add("Content-Type", "application/json")
                    .build();
            getApi(url, headers, callback);
        } else {
            getApi(url, null, callback);
        }
    }

    private void getApi(HttpUrl url, Headers headers, final MyCallback callback) {
        Request request;
        if (headers == null) {
            request = new Request.Builder()
                    .url(url)
                    .build();
        } else {
            request = new Request.Builder()
                    .url(url)
                    .headers(headers)
                    .build();
        }

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "[Failure]", e);
                if (e.toString().contains("SocketTimeoutException")) {
                    Log.e(TAG, "timeout");
                    callback.onFailure(call, e, true);

                } else {
                    Log.e(TAG, "not timeout");
                    callback.onFailure(call, e, false);

                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    GetBaseResponse baseResponse = new GetBaseResponse(); // 原始Encode過的資料
                    GetBaseEncodeResponse baseEncodeResponse = new GetBaseEncodeResponse();//解析Encode資料
                    String result = response.body().string();

                    BaseEncodeData baseEncodeData = baseEncodeResponse.getGson(result);
                    try {
                        byte[] decryptBytes = Util.decryptAES(TAG_AES_IV.getBytes("UTF-8"), TAG_AES_KEY.getBytes("UTF-8"), Base64.decode(baseEncodeData.getEncode(), Base64.DEFAULT));
                        if (decryptBytes != null) {
                            String responseString = new String(decryptBytes);
                            Log.d(TAG, String.format("[%1$s] = %2$s", "Response", responseString));

                            baseResponse = baseResponse.getBaseGson(responseString);
                            if (baseResponse.getResult().getCode() == 200) {
                                callback.onResponse(call, responseString);
                            } else {
                                onFailure(call, new IOException(baseResponse.getResult().getMessage()));
                            }
                        } else {
                            onFailure(call, new IOException("Decrypt array is null"));
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } catch (IOException e) {
                    onFailure(call, e);
                }
            }
        });
        Log.d(TAG, String.format("%1$s,%2$s", "[GET] ", request.url().toString()));
    }

    private void postApi(HttpUrl url, final MyCallback callback) {
        postApi(url, null, callback);
    }


    private void postApi(HttpUrl url, RequestBody body, final MyCallback callback) {
        postApi(url, body, null, callback);
    }

    private void postApi(HttpUrl url, RequestBody body, boolean defaultHeader, final MyCallback callback) {
        if (defaultHeader) {
            Headers headers = new Headers.Builder()
                    .add("deviceId", TAG_DEVICE_ID)
                    .build();
            postApi(url, body, headers, callback);
        } else {
            postApi(url, body, null, callback);
        }
    }

    private void postApi(HttpUrl url, RequestBody body, Headers headers, final MyCallback callback) {
        final Request request;
        if (headers == null) {
            request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
        } else {
            request = new Request.Builder()
                    .url(url)
                    .headers(headers)
                    .post(body)
                    .build();
        }

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "[Failure]", e);
                if (e.toString().contains("SocketTimeoutException")) {
                    Log.e(TAG, "timeout");
                    callback.onFailure(call, e, true);

                } else {
                    Log.e(TAG, "not timeout");
                    callback.onFailure(call, e, false);

                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() != 200) {
                    onFailure(call, new IOException(response.message()));
                    return;
                }
                try {
                    GetBaseResponse baseResponse = new GetBaseResponse(); // 原始Encode過的資料
                    GetBaseEncodeResponse baseEncodeResponse = new GetBaseEncodeResponse();//解析Encode資料
                    String result = response.body().string();

                    if (result == null)
                        result = "";
                    BaseEncodeData baseEncodeData = baseEncodeResponse.getGson(result);
                    if (baseEncodeData != null) {//如果不是Json會拋出Null有可能是Html
                        try {
                            byte[] decryptBytes = Util.decryptAES(TAG_AES_IV.getBytes("UTF-8"), TAG_AES_KEY.getBytes("UTF-8"), Base64.decode(baseEncodeData.getEncode(), Base64.DEFAULT));
                            if (decryptBytes != null) {
                                String responseString = new String(decryptBytes);
                                Log.d(TAG, String.format("[%1$s] = %2$s", "Response", responseString));

                                baseResponse = baseResponse.getBaseGson(responseString);
                                if (baseResponse.getResult().getCode() == 200) {
                                    callback.onResponse(call, responseString);
                                } else {
                                    onFailure(call, new IOException(baseResponse.getResult().getMessage()));
                                }
                            } else {
                                onFailure(call, new IOException("Decrypt array is null"));
                            }
                        } catch (Exception ex) {
                            Log.e(TAG, "[Post response] ", ex);
                            onFailure(call, new IOException(ex));
                        }
                    } else if (!TextUtils.isEmpty(result)) {
                        Log.i(TAG, "[Post response not Json]" + result);
                        callback.onResponse(call, result);
                    } else {
                        onFailure(call, new IOException("response is error format"));
                    }
                } catch (IOException e) {
                    onFailure(call, e);
                }
            }
        });
        Log.d(TAG, String.format("%1$s,%2$s", "[POST] ", request.url().toString()));
    }

    /**
     * Create Url
     *
     * @param Host
     * @param path
     * @return
     */
    private String createUrl(@Nullable String Host, String path) {
        return String.format("%1$s%2$s", Host, path);
    }

    /**
     * Create Url
     *
     * @param path
     * @return
     */
    private String createUrl(String path) {
        return createUrl(TAG_HOST, path);
    }

    /**
     * Create Upload data
     *
     * @param json
     * @return
     */
    private String createEncodeUploadData(String json) {
        BaseEncodeData encodeData = new BaseEncodeData();
        GetBaseEncodeResponse baseEncodeResponse = new GetBaseEncodeResponse();

        try {
            byte[] a = Util.encryptAES(TAG_AES_IV.getBytes("UTF-8"), TAG_AES_KEY.getBytes("UTF-8"), json.getBytes("UTF-8"));

            encodeData.setEncode(Base64.encodeToString(a, Base64.DEFAULT));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        baseEncodeResponse.setData(encodeData);
        return baseEncodeResponse.getJson();
    }

    public interface MyCallback {
        void onFailure(Call call, IOException e, boolean timeout);

        void onResponse(Call call, String response) throws IOException;
    }

    public static String getDeviceID() {
        return !TextUtils.isEmpty(TAG_DEVICE_ID) ? TAG_DEVICE_ID : "";
    }

    /*********************************

     以上為create API Method

     **********************************/
    public static NewApiConnect getInstance(Context context) {
        if (mClient == null) {
            HttpUtils httpUtils = new HttpUtils();
            mClient = httpUtils.getTrustAllClient();

        }

        if (mApiConnect == null) {
            mApiConnect = new NewApiConnect();
        }

        if (TAG_DEVICE_ID == null) {
            TAG_DEVICE_ID = Util.getDeviceId(context);
        }

//        if (TextUtils.isEmpty(mToken) || TextUtils.equals(mToken, Preferences.TAG_ERROR)) {
//            mToken = Preferences.getFCMToken(context);
//        }

        return mApiConnect;
    }


    /*********************************

     以下為API Method

     **********************************/

    /**
     * 取得語系清單
     */
    public void getLangList(MyCallback callback) {
        HttpUrl url = HttpUrl.parse(createUrl("lang_list"))
                .newBuilder()
                .build();
        getApi(url, callback);
    }

    /**
     * 註冊使用者
     *
     * @param json
     * @param callback
     */
    public void registerUser(String json, MyCallback callback) {
        HttpUrl url = HttpUrl.parse(createUrl("register_user"))
                .newBuilder()
                .build();

        RequestBody body = RequestBody.create(TAG_JSON, createEncodeUploadData(json));
        postApi(url, body, callback);
    }

    /**
     * 修改使用者語言設置
     *
     * @param json
     * @param callback
     */
    public void editUserLanguage(String json, MyCallback callback) {
        HttpUrl url = HttpUrl.parse(createUrl("edit_lang"))
                .newBuilder()
                .build();

        RequestBody body = RequestBody.create(TAG_JSON, createEncodeUploadData(json));
        postApi(url, body, true, callback);
    }

    /**
     * 取得航班資訊
     * 為了帶參數，則使用Post method
     *
     * @param json
     * @param callback
     */
    public void getFlightsListInfo(String json, MyCallback callback) {
        HttpUrl url = HttpUrl.parse(createUrl("flight_list"))
                .newBuilder()
                .build();

        RequestBody body = RequestBody.create(TAG_JSON, createEncodeUploadData(json));
        postApi(url, body, true, callback);
    }

    /**
     * 儲存我的航班
     *
     * @param json
     * @param callback
     */
    public void saveMyFlights(String json, MyCallback callback) {
        HttpUrl url = HttpUrl.parse(createUrl("new_my_flight"))
                .newBuilder()
                .build();

        RequestBody body = RequestBody.create(TAG_JSON, createEncodeUploadData(json));
        postApi(url, body, true, callback);
    }

    /**
     * 取得我的航班清單
     *
     * @param callback
     */
    public void getMyFlights(MyCallback callback) {
        HttpUrl url = HttpUrl.parse(createUrl("my_flight_list"))
                .newBuilder()
                .build();
        getApi(url, true, callback);
    }

    /**
     * 刪除我的航班
     *
     * @param json
     * @param callback
     */
    public void deleteMyFlights(String json, MyCallback callback) {
        HttpUrl url = HttpUrl.parse(createUrl("delete_my_flight"))
                .newBuilder()
                .build();

        RequestBody body = RequestBody.create(TAG_JSON, createEncodeUploadData(json));
        postApi(url, body, true, callback);
    }

    /**
     * 推播Beacon
     *
     * @param json
     * @param callback
     */
    public void uploadBeacon(String json, MyCallback callback) {
        HttpUrl url = HttpUrl.parse(createUrl("push_beacon"))
                .newBuilder()
                .build();

        RequestBody body = RequestBody.create(TAG_JSON, createEncodeUploadData(json));
        postApi(url, body, true, callback);
    }

    /**
     * 取得成就清單
     *
     * @param callback
     */
    public void getAchievementList(MyCallback callback) {
        HttpUrl url = HttpUrl.parse(createUrl("achievement_list"))
                .newBuilder()
                .build();
        getApi(url, true, callback);
    }

    /**
     * 取得餐廳總類清單
     *
     * @param callback
     */
    public void getRestaurantTypeList(MyCallback callback) {
        HttpUrl url = HttpUrl.parse(createUrl("restaurant_type_list"))
                .newBuilder()
                .build();
        getApi(url, true, callback);
    }

    /**
     * 取得航廈清單
     *
     * @param callback
     */
    public void getTerminalList(MyCallback callback) {
        HttpUrl url = HttpUrl.parse(createUrl("terminals_list"))
                .newBuilder()
                .build();
        getApi(url, true, callback);
    }

    /**
     * 取得區域清單
     *
     * @param callback
     */
    public void getAreaList(MyCallback callback) {
        HttpUrl url = HttpUrl.parse(createUrl("area_list"))
                .newBuilder()
                .build();
        getApi(url, true, callback);
    }

    /**
     * 取得樓層清單
     *
     * @param callback
     */
    public void getFloorList(MyCallback callback) {
        HttpUrl url = HttpUrl.parse(createUrl("floor_list"))
                .newBuilder()
                .build();
        getApi(url, true, callback);
    }

    /**
     * 取得餐廳搜尋清單
     *
     * @param json
     * @param callback
     */
    public void getRestaurantInfoList(String json, MyCallback callback) {
        HttpUrl url = HttpUrl.parse(createUrl("restaurant_list"))
                .newBuilder()
                .build();

        RequestBody body = RequestBody.create(TAG_JSON, createEncodeUploadData(json));
        postApi(url, body, true, callback);
    }

    /**
     * 取得商店種類清單
     *
     * @param callback
     */
    public void getStoreList(MyCallback callback) {
        HttpUrl url = HttpUrl.parse(createUrl("store_type_list"))
                .newBuilder()
                .build();
        getApi(url, true, callback);
    }

    /**
     * 取得商店搜尋清單
     *
     * @param json
     * @param callback
     */
    public void getStoreInfoList(String json, MyCallback callback) {
        HttpUrl url = HttpUrl.parse(createUrl("store_list"))
                .newBuilder()
                .build();

        RequestBody body = RequestBody.create(TAG_JSON, createEncodeUploadData(json));
        postApi(url, body, true, callback);
    }

    /**
     * 取得航廈設施清單
     *
     * @param callback
     */
    public void getTerminalsFacilityList(MyCallback callback) {
        HttpUrl url = HttpUrl.parse(createUrl("terminals_facility_list"))
                .newBuilder()
                .build();
        getApi(url, true, callback);
    }

    /**
     * 取得使用者緊急通知
     *
     * @param callback
     */
    public void getUserEmergency(MyCallback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "emergency_list")
                .newBuilder()
                .build();
        getApi(url, true, callback);
    }

    /**
     * 取得使用者貼心提醒
     *
     * @param callback
     */
    public void getUserSweetNotify(MyCallback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "intimate_remind_list")
                .newBuilder()
                .build();
        getApi(url,true, callback);
    }

    /**
     * 取得使用者最新消息
     *
     * @param callback
     */
    public void getUserNews(MyCallback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "news_list")
                .newBuilder()
                .build();
        getApi(url,true, callback);
    }

    /**
     * 取得紀念品資訊
     *
     * @param callback
     */
    public void getSouvenirList(MyCallback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "souvenir_list")
                .newBuilder()
                .build();
        getApi(url,true, callback);
    }

    /**
     * 取得電信業者清單
     *
     * @param callback
     */
    public void getRoamingService(MyCallback callback) {
        HttpUrl url = HttpUrl.parse(TAG_HOST + "telecommunications_industry_list")
                .newBuilder()
                .build();
        getApi(url, true, callback);
    }


    /**
     * 取得航廈廁所清單
     *
     * @param callback
     */
    public void getTerminalsToiletList(MyCallback callback) {
        HttpUrl url = HttpUrl.parse(createUrl("terminals_toilet_list"))
                .newBuilder()
                .build();
        getApi(url, true, callback);
    }

    /**
     * 匯率換算
     *
     * @param json
     * @param callback
     */
    public void exchangeRate(String json, MyCallback callback) {
        HttpUrl url = HttpUrl.parse(createUrl("exchange_rate"))
                .newBuilder()
                .build();

        RequestBody body = RequestBody.create(TAG_JSON, createEncodeUploadData(json));
        postApi(url, body, true, callback);
    }

    /**
     * 取得天氣狀況
     *
     * @param json
     * @param callback
     */
    public void getWeather(String json, MyCallback callback) {
        HttpUrl url = HttpUrl.parse(createUrl("weather"))
                .newBuilder()
                .build();

        RequestBody body = RequestBody.create(TAG_JSON, createEncodeUploadData(json));
        postApi(url, body, true, callback);
    }

    /**
     * 取得巴士資訊
     *
     * @param callback
     */
    public void getBusInfo(MyCallback callback) {
        HttpUrl url = HttpUrl.parse(createUrl("bus"))
                .newBuilder()
                .build();
        getApi(url, true, callback);
    }

    /**
     * 取得機場捷運/高鐵資訊
     *
     * @param callback
     */
    public void getMrtHsrInfo(MyCallback callback) {
        HttpUrl url = HttpUrl.parse(createUrl("mrt_hsr"))
                .newBuilder()
                .build();
        getApi(url, true, callback);
    }

    /**
     * 取得道路救援資料
     *
     * @param callback
     */
    public void getRoadsideAssistInfo(MyCallback callback) {
        HttpUrl url = HttpUrl.parse(createUrl("roadside_assist"))
                .newBuilder()
                .build();
        getApi(url, true, callback);
    }

    /**
     * 取得巡迴巴士資料
     *
     * @param callback
     */
    public void getShuttleInfo(MyCallback callback) {
        HttpUrl url = HttpUrl.parse(createUrl("shuttle"))
                .newBuilder()
                .build();
        getApi(url, true, callback);
    }

    /**
     * 取得機場電車資料
     *
     * @param callback
     */
    public void getTramInfo(MyCallback callback) {
        HttpUrl url = HttpUrl.parse(createUrl("tram"))
                .newBuilder()
                .build();
        getApi(url, true, callback);
    }
}
