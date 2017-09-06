package com.whatmedia.ttia.page.main.terminals.facility;

import android.content.Context;
import android.text.TextUtils;

import com.whatmedia.ttia.connect.ApiConnect;
import com.whatmedia.ttia.response.data.AirportFacilityData;
import com.whatmedia.ttia.response.GetAirPortFacilityResponse;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class AirportFacilityPresenter implements AirportFacilityContract.Presenter {
    private final static String TAG = AirportFacilityPresenter.class.getSimpleName();

    private static AirportFacilityPresenter mAirportFacilityPresenter;
    private static ApiConnect mApiConnect;
    private static AirportFacilityContract.View mView;


    public static AirportFacilityPresenter getInstance(Context context, AirportFacilityContract.View view) {
        mAirportFacilityPresenter = new AirportFacilityPresenter();
        mApiConnect = ApiConnect.getInstance(context);
        mView = view;
        return mAirportFacilityPresenter;
    }

    @Override
    public void getAirportFacilityAPI() {
        mApiConnect.getAirportFacility(new ApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, boolean timeout) {
                mView.getAirportFacilityFailed(e.toString(), timeout);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    String result = response.body().string();
                    List<AirportFacilityData> list = GetAirPortFacilityResponse.newInstance(result);
                    mView.getAirportFacilitySucceed(list);
                } else {
                    mView.getAirportFacilityFailed(!TextUtils.isEmpty(response.message()) ? response.message() : "", false);
                }
            }
        });
    }
}
