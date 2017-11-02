package com.whatmedia.ttia.page.main.secretary.sweet;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.connect.NewApiConnect;
import com.whatmedia.ttia.newresponse.GetUserSweetNotifyResponse;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class AirportSweetNotifyPresenter implements AirportSweetNotifyContract.Presenter {
    private final static String TAG = AirportSweetNotifyPresenter.class.getSimpleName();

    private NewApiConnect mApiConnect;
    private AirportSweetNotifyContract.View mView;
    private Context mContext;


    public AirportSweetNotifyPresenter(Context context, AirportSweetNotifyContract.View view) {
        mApiConnect = NewApiConnect.getInstance(context);
        mView = view;
        mContext = context;
    }

    @Override
    public void getSweetNotifyAPI() {
        mApiConnect.getUserSweetNotify(new NewApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, boolean timeout) {
                mView.getSweetNotifyFailed(e.toString(), timeout);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {

                GetUserSweetNotifyResponse getUserSweetNotifyResponse = GetUserSweetNotifyResponse.getGson(response);

                if(getUserSweetNotifyResponse!=null && getUserSweetNotifyResponse.getUserNewsDataList()!=null){
                    mView.getSweetNotifySucceed(getUserSweetNotifyResponse.getUserNewsDataList());
                }else{
                    mView.getSweetNotifyFailed(mContext.getString(R.string.data_error), false);
                }

            }
        });
    }
}
