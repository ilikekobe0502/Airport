package com.whatsmedia.ttia.page.main.secretary.emergency;

import android.content.Context;

import com.whatsmedia.ttia.R;
import com.whatsmedia.ttia.connect.NewApiConnect;
import com.whatsmedia.ttia.newresponse.GetUserNewsResponse;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class AirportEmergencyPresenter implements AirportEmergencyContract.Presenter {
    private final static String TAG = AirportEmergencyPresenter.class.getSimpleName();

    private NewApiConnect mNewApiConnect;
    private AirportEmergencyContract.View mView;
    private Context mContext;

    AirportEmergencyPresenter(Context context, AirportEmergencyContract.View view) {
        mNewApiConnect = NewApiConnect.getInstance(context);
        mView = view;
        mContext = context;
    }

    @Override
    public void getEmergencyAPI() {
        mNewApiConnect.getUserEmergency(new NewApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, int status) {
                mView.getEmergencyFailed(e.toString(), status);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {
                GetUserNewsResponse getUserNewsResponse = GetUserNewsResponse.getGson(response);

                if(getUserNewsResponse!=null && getUserNewsResponse.getUserNewsDataList()!=null){
                    mView.getEmergencySucceed(getUserNewsResponse.getUserNewsDataList());
                }else{
                    mView.getEmergencyFailed(mContext.getString(R.string.data_error), NewApiConnect.TAG_DEFAULT);
                }
            }
        });
    }
}
