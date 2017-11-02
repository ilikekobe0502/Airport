package com.whatmedia.ttia.page.main.traffic.roadside;

import android.content.Context;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.connect.NewApiConnect;
import com.whatmedia.ttia.newresponse.GetRoadsideAssistInfoResponse;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class RoadsideAssistancePresenter implements RoadsideAssistanceContract.Presenter {
    private final static String TAG = RoadsideAssistancePresenter.class.getSimpleName();

    private NewApiConnect mNewApiConnect;
    private RoadsideAssistanceContract.View mView;
    private Context mContext;


    RoadsideAssistancePresenter(Context context, RoadsideAssistanceContract.View view) {
        mNewApiConnect = NewApiConnect.getInstance(context);
        mView = view;
        mContext = context;
    }

    @Override
    public void getRoadsideAssistanceAPI() {
        mNewApiConnect.getRoadsideAssistInfo(new NewApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, boolean timeout) {
                mView.getRoadsideAssistanceFailed(e.toString(), timeout);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {
                GetRoadsideAssistInfoResponse roadsideAssistInfoResponse = GetRoadsideAssistInfoResponse.getGson(response);
                if (roadsideAssistInfoResponse.getRoadsideAssist() != null)
                    mView.getRoadsideAssistanceSucceed(roadsideAssistInfoResponse.getRoadsideAssist());
                else
                    mView.getRoadsideAssistanceFailed(mContext.getString(R.string.data_error), false);
            }
        });
    }
}
