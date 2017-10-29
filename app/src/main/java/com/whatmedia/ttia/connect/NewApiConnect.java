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
    private final static MediaType TAG_JSON = MediaType.parse("application/json; charset=utf-8");
    private final static String TAG_AES_KEY = "taoyuanairporttaoyuanairporttaoy";
    private final static String TAG_AES_IV = "taoyuanairportta";

    private static OkHttpClient mClient;
    private static NewApiConnect mApiConnect;

    private static String TAG_DEVICE_ID;
    private static String mToken;

    private void getApi(HttpUrl url, final MyCallback callback) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "[Failure = ]", e);
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

                            baseResponse = baseResponse.getGson(responseString);
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
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "[Failure = ]", e);
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

                    BaseEncodeData baseEncodeData = baseEncodeResponse.getGson(result);
                    try {
                        byte[] decryptBytes = Util.decryptAES(TAG_AES_IV.getBytes("UTF-8"), TAG_AES_KEY.getBytes("UTF-8"), Base64.decode(baseEncodeData.getEncode(), Base64.DEFAULT));
                        if (decryptBytes != null) {
                            String responseString = new String(decryptBytes);
                            Log.d(TAG, String.format("[%1$s] = %2$s", "Response", responseString));

                            baseResponse = baseResponse.getGson(responseString);
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
        return baseEncodeResponse.getData().getEncode();
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
            TAG_DEVICE_ID = Util.getDeviceId();
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
}
