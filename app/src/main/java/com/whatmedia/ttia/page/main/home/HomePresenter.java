package com.whatmedia.ttia.page.main.home;

import android.content.Context;
import android.text.TextUtils;

import com.whatmedia.ttia.connect.NewApiConnect;
import com.whatmedia.ttia.newresponse.GetLanguageListResponse;
import com.whatmedia.ttia.newresponse.GetRegisterUserResponse;
import com.whatmedia.ttia.newresponse.data.RegisterUserData;
import com.whatmedia.ttia.utility.Preferences;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.Call;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class HomePresenter implements HomeContract.Presenter {

    private static final int TAG_DEFAULT_LANGUAGE = 1; // 預設語系為中文
    private static WeakReference<NewApiConnect> mNewApiConnect;
    private Context mContext;
    private HomeContract.View mView;


    HomePresenter(Context context, HomeContract.View view) {
        mNewApiConnect = new WeakReference<NewApiConnect>(NewApiConnect.getInstance(context));
        mView = view;
        mContext = context;
    }

    @Override
    public void getLanguageList() {
        mNewApiConnect.get().getLangList(new NewApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, boolean timeout) {
                mView.getLanguageListFailed(e.toString(), timeout);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {
                GetLanguageListResponse result = GetLanguageListResponse.getInstance(mContext, response);
                if (result.getData() != null)
                    mView.getLanguageListSuccess(result);
                else
                    onFailure(call, new IOException("Data is empty"), false);
            }
        });
    }

    @Override
    public void registerUser() {
        RegisterUserData data = new RegisterUserData();
        GetRegisterUserResponse response = new GetRegisterUserResponse();

        if (!TextUtils.isEmpty(NewApiConnect.getDeviceID())) {
            data.setDeviceID(NewApiConnect.getDeviceID());
        } else {
            // TODO: 2017/10/29 error flow
            return;
        }
        if (!TextUtils.isEmpty(Preferences.getFCMToken(mContext))) {
            data.setPushToken(Preferences.getFCMToken(mContext));
        } else {
            // TODO: 2017/10/29 error flow
            return;
        }
        data.setLangId(TAG_DEFAULT_LANGUAGE);

        response.setData(data);
        String json = response.getJson();
        mNewApiConnect.get().registerUser(json, new NewApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, boolean timeout) {
                mView.registerUserFailed(e.toString(), timeout);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {
                mView.registerUserSuccess();
            }
        });
    }
}
