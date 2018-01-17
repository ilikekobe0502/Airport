package com.whatmedia.ttia.page.main.home.parking;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.whatmedia.ttia.connect.ApiConnect;
import com.whatmedia.ttia.connect.MyResponse;
import com.whatmedia.ttia.connect.NewApiConnect;
import com.whatmedia.ttia.response.GetHomeParkingInfoListResponse;
import com.whatmedia.ttia.response.data.HomeParkingFrameData;
import com.whatmedia.ttia.response.data.HomeParkingInfoData;
import com.whatmedia.ttia.response.data.HomeParkingListData;
import com.whatmedia.ttia.utility.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class HomeParkingInfoPresenter implements HomeParkingInfoContract.Presenter {
    private final static String TAG = HomeParkingInfoPresenter.class.getSimpleName();

    private NewApiConnect mNewApiConnect;
    private HomeParkingInfoContract.View mView;
    private Context mContext;


    HomeParkingInfoPresenter(Context context, HomeParkingInfoContract.View view) {
        mContext = context;
        mNewApiConnect = NewApiConnect.getInstance(context);
        mView = view;
    }

    @Override
    public void getParkingInfoAPI() {
        mNewApiConnect.getHomeParkIngInfo(new NewApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, int status) {
                mView.getParkingInfoFailed(e.toString(), status);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {
                HomeParkingFrameData frameData = GetHomeParkingInfoListResponse.newInstance(Util.xmlToSJsonString(response));
                HomeParkingListData listData;
                List<HomeParkingInfoData> infoData;
                if (frameData != null) {
                    listData = frameData.getParkingListData();
                } else
                    listData = new HomeParkingListData();

                if (listData.getPark() != null) {
                    infoData = listData.getPark();
                } else {
                    infoData = new ArrayList<HomeParkingInfoData>();
                }
                mView.getParkingInfoSucceed(infoData);
            }
        });
    }
}
