package com.whatmedia.ttia.page.main.flights.my;

import android.content.Context;
import android.text.TextUtils;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.connect.NewApiConnect;
import com.whatmedia.ttia.newresponse.GetFlightsListResponse;
import com.whatmedia.ttia.newresponse.data.FlightsListData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class MyFlightsInfoPresenter implements MyFlightsInfoContract.Presenter {
    private final static String TAG = MyFlightsInfoPresenter.class.getSimpleName();

    private NewApiConnect mNewApiConnect;
    private MyFlightsInfoContract.View mView;
    private Context mContext;


    MyFlightsInfoPresenter(Context context, MyFlightsInfoContract.View view) {
        mNewApiConnect = NewApiConnect.getInstance(context);
        mView = view;
        mContext = context;
    }

    @Override
    public void getMyFlightsInfoAPI() {
        mNewApiConnect.getMyFlights(new NewApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, boolean timeout) {
                mView.getMyFlightsInfoFailed(e.toString(), timeout);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {
                GetFlightsListResponse flightsListResponse = GetFlightsListResponse.getGson(response);
                if (flightsListResponse.getFlightList() != null) {
                    List<FlightsListData> data = flightsListResponse.getFlightList();
                    mView.getMyFlightsInfoSucceed(data);
                } else {
                    mView.getMyFlightsInfoFailed(mContext.getString(R.string.data_error), false);
                }
            }
        });
    }

    @Override
    public void deleteMyFlightsInfoAPI(List<FlightsListData> selectList) {
        List<FlightsListData> uploadList = new ArrayList<>();
        GetFlightsListResponse uploadObject = new GetFlightsListResponse();

        for (FlightsListData item : selectList) {
            FlightsListData uploadData = new FlightsListData();

            uploadData.setAirlineCode(!TextUtils.isEmpty(item.getAirlineCode()) ? item.getAirlineCode() : "");
            uploadData.setShifts(!TextUtils.isEmpty(item.getShifts()) ? item.getShifts() : "");
            uploadData.setExpressDate(!TextUtils.isEmpty(item.getExpressDate()) ? item.getExpressDate() : "");
            uploadData.setExpressTime(!TextUtils.isEmpty(item.getExpressTime()) ? item.getExpressTime() : "");
            uploadList.add(uploadData);
        }

        uploadObject.setFlightList(uploadList);

        mNewApiConnect.deleteMyFlights(uploadObject.getDeleteJson(), new NewApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, boolean timeout) {
                mView.deleteMyFlightsInfoFailed(e.toString(), timeout);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {
                mView.deleteMyFlightsInfoSucceed();
            }
        });
    }
}
