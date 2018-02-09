package com.whatsmedia.ttia.page.main.terminals.facility;

import android.content.Context;

import com.whatsmedia.ttia.R;
import com.whatsmedia.ttia.connect.NewApiConnect;
import com.whatsmedia.ttia.newresponse.GetTerminalsFacilityListResponse;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class AirportFacilityPresenter implements AirportFacilityContract.Presenter {
    private final static String TAG = AirportFacilityPresenter.class.getSimpleName();

    private NewApiConnect mNewApiConnect;
    private AirportFacilityContract.View mView;
    private Context mContext;


    AirportFacilityPresenter(Context context, AirportFacilityContract.View view) {
        mNewApiConnect = NewApiConnect.getInstance(context);
        mView = view;
        mContext = context;
    }

    @Override
    public void getAirportFacilityAPI() {

        mNewApiConnect.getTerminalsFacilityList(new NewApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, int status) {
                mView.getAirportFacilityFailed(e.toString(), status);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {
                GetTerminalsFacilityListResponse terminalsFacilityListResponse = GetTerminalsFacilityListResponse.getGson(response);
                if (terminalsFacilityListResponse.getTerminalsFacilityList() != null)
                    mView.getAirportFacilitySucceed(terminalsFacilityListResponse.getTerminalsFacilityList());
                else
                    mView.getAirportFacilityFailed(mContext.getString(R.string.data_error), NewApiConnect.TAG_DEFAULT);
            }
        });
    }
}
