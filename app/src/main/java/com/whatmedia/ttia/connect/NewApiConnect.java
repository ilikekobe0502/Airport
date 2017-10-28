package com.whatmedia.ttia.connect;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.whatmedia.ttia.newresponse.GetBaseEncodeResponse;
import com.whatmedia.ttia.newresponse.data.BaseEncodeData;
import com.whatmedia.ttia.utility.Util;

import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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

    private static String TAG_DEVICE_ID;
    private String mToken;

    private static OkHttpClient mClient;
    private static NewApiConnect mApiConnect;

    public interface MyCallback {
        void onFailure(Call call, IOException e, boolean timeout);

        void onResponse(Call call, MyResponse response) throws IOException;
    }

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

    /**
     * 解密Response
     *
     * @param ivBytes
     * @param keyBytes
     * @param textBytes
     * @return
     */
    private static byte[] decryptAES(byte[] ivBytes, byte[] keyBytes, byte[] textBytes) {
        try {
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            SecretKeySpec newKey = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);
            return cipher.doFinal(textBytes);
        } catch (Exception ex) {
            return null;
        }

    }

    /**
     * Get Api method
     *
     * @param url
     * @param callback
     */
    private static void getApi(HttpUrl url, final MyCallback callback) {
        final Request request = new Request.Builder()
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
                    MyResponse myResponse = new MyResponse();
                    String result = response.body().string();
                    myResponse.setMyResponseBody(result);
                    myResponse.setMessage(response.message());

                    BaseEncodeData baseEncodeData = GetBaseEncodeResponse.newInstance(result);
                    try {
                        byte[] descryptBytes = decryptAES(TAG_AES_IV.getBytes("UTF-8"), TAG_AES_KEY.getBytes("UTF-8"), Base64.decode(baseEncodeData.getEncode(), Base64.DEFAULT));
                        if (descryptBytes != null) {
                            String responseString = new String(descryptBytes);
                            Log.d(TAG, String.format("[%1$s] = %2$s", "Response", responseString));
                            myResponse.setMyResponseBody(responseString);
                            myResponse.setCode(response.code());
                            callback.onResponse(call, myResponse);
                        } else {
                            onFailure(call, new IOException());
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } catch (IOException e) {
                    onFailure(call, e);
                }
            }
        });
        Log.d(TAG, request.url().toString());
    }

    /**
     * 取得語系清單
     */
    public void getLangList(MyCallback callback) {
        HttpUrl url = HttpUrl.parse(String.format("%1$s%2$s", TAG_HOST, "lang_list"))
                .newBuilder()
                .build();
        getApi(url, callback);
    }
}
