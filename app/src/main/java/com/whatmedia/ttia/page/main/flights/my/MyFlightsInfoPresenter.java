package com.whatmedia.ttia.page.main.flights.my;

import android.content.Context;
import android.text.TextUtils;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.connect.NewApiConnect;
import com.whatmedia.ttia.newresponse.GetFlightsListResponse;
import com.whatmedia.ttia.newresponse.data.FlightsListData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
        GetFlightsListResponse uploadObject = new GetFlightsListResponse();

        List<HashMap<String,String>> uploadList = new ArrayList<>();
        for (FlightsListData item : selectList) {

            HashMap<String,String> map = new HashMap<>();
            map.put("airlineCode",!TextUtils.isEmpty(item.getAirlineCode()) ? item.getAirlineCode() : "");
            map.put("shifts",!TextUtils.isEmpty(item.getShifts()) ? item.getShifts() : "");
            map.put("expressDate",!TextUtils.isEmpty(item.getExpressDate()) ? item.getExpressDate() : "");
            map.put("expressTime",!TextUtils.isEmpty(item.getExpressTime()) ? item.getExpressTime() : "");
            uploadList.add(map);
        }

        uploadObject.getDeleteJson(uploadList);

        mNewApiConnect.deleteMyFlights(uploadObject.getDeleteJson(uploadList), new NewApiConnect.MyCallback() {
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
