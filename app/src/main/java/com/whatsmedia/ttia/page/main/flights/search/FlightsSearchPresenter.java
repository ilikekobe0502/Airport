package com.whatsmedia.ttia.page.main.flights.search;

import android.content.Context;
import android.text.TextUtils;

import com.whatsmedia.ttia.R;
import com.whatsmedia.ttia.connect.NewApiConnect;
import com.whatsmedia.ttia.newresponse.GetFlightsQueryResponse;
import com.whatsmedia.ttia.newresponse.data.FlightsQueryData;
import com.whatsmedia.ttia.utility.Util;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class FlightsSearchPresenter implements FlightsSearchContract.Presenter {
    private final static String TAG = FlightsSearchPresenter.class.getSimpleName();

    private NewApiConnect mNewApiConnect;
    private FlightsSearchContract.View mView;
    private Context mContext;


    FlightsSearchPresenter(Context context, FlightsSearchContract.View view) {
        mNewApiConnect = NewApiConnect.getInstance(context);
        mView = view;
        mContext = context;
    }

    @Override
    public void getFlightsInfoAPI(final String keyword) {
        FlightsQueryData data = new FlightsQueryData();
        GetFlightsQueryResponse flightsListResponse = new GetFlightsQueryResponse();


        data.setQueryType(FlightsQueryData.TAG_ALL);
        data.setExpressDate(Util.getNowDate());
        data.setKeyWord(keyword);
        flightsListResponse.setData(data);
        if (TextUtils.isEmpty(flightsListResponse.getJson())) {
            mView.getFlightsDepartureFailed(mContext.getString(R.string.data_error), NewApiConnect.TAG_DEFAULT);
            return;
        }

        mNewApiConnect.getFlightsListInfo(flightsListResponse.getJson(), new NewApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, int status) {
                mView.getFlightsDepartureFailed(e.toString(), status);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {
                mView.getFlightsDepartureSucceed(response);
            }
        });
    }
}
