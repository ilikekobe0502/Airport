package com.whatsmedia.ttia.page.main.home.parking;

import android.content.Context;

import com.whatsmedia.ttia.connect.NewApiConnect;
import com.whatsmedia.ttia.response.GetHomeParkingInfoListResponse;
import com.whatsmedia.ttia.response.data.HomeParkingFrameData;
import com.whatsmedia.ttia.response.data.HomeParkingInfoData;
import com.whatsmedia.ttia.response.data.HomeParkingListData;
import com.whatsmedia.ttia.utility.Util;

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
