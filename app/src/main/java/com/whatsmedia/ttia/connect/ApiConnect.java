package com.whatsmedia.ttia.connect;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.whatsmedia.ttia.enums.LanguageSetting;
import com.whatsmedia.ttia.utility.Preferences;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by neo_mac on 2017/6/6.
 */

public class ApiConnect extends StateCode {
    private final static String TAG = ApiConnect.class.getSimpleName();

    private final static String TAG_HOST = "http://210.241.14.99";
    private final static MediaType TAG_JSON = MediaType.parse("application/json; charset=utf-8");

    public final static String TAG_IMAGE_HOST = "http://210.241.14.99";

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
}
