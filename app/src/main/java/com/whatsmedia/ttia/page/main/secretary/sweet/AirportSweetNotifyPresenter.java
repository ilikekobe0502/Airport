package com.whatsmedia.ttia.page.main.secretary.sweet;

import android.content.Context;
import android.text.TextUtils;

import com.whatsmedia.ttia.R;
import com.whatsmedia.ttia.connect.NewApiConnect;
import com.whatsmedia.ttia.newresponse.GetSweetDeleteResponse;
import com.whatsmedia.ttia.newresponse.GetUserSweetNotifyResponse;
import com.whatsmedia.ttia.newresponse.data.SweetDeleteData;

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
            public void onFailure(Call call, IOException e, int status) {
                mView.getSweetNotifyFailed(e.toString(), status);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {

                GetUserSweetNotifyResponse getUserSweetNotifyResponse = GetUserSweetNotifyResponse.getGson(response);

                if (getUserSweetNotifyResponse != null && getUserSweetNotifyResponse.getUserNewsDataList() != null) {
                    mView.getSweetNotifySucceed(getUserSweetNotifyResponse.getUserNewsDataList());
                } else {
                    mView.getSweetNotifyFailed(mContext.getString(R.string.data_error), NewApiConnect.TAG_DEFAULT);
                }

            }
        });
    }

    @Override
    public void deleteSweetAPI(List<String> deleteList) {
        GetSweetDeleteResponse response = new GetSweetDeleteResponse();
        SweetDeleteData deleteData = new SweetDeleteData();
        deleteData.setBeaconId(deleteList);
        response.setData(deleteData);
        String json = response.getJson();
        if (TextUtils.isEmpty(json)) {
            mView.deleteSweetNotifyFailed(mContext.getString(R.string.data_error), NewApiConnect.TAG_DEFAULT);
            return;
        }
        mApiConnect.deleteSweetNotify(json, new NewApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, int status) {
                mView.deleteSweetNotifyFailed(e.toString(), status);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {
                mView.deleteSweetNotifySucceed();
            }
        });
    }
}
