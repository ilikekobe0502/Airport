package com.whatsmedia.ttia.page.main.communication.emergency;


import android.content.Context;

import com.whatsmedia.ttia.R;
import com.whatsmedia.ttia.connect.NewApiConnect;
import com.whatsmedia.ttia.newresponse.GetEmergenctCallResponse;

import java.io.IOException;
import okhttp3.Call;

public class EmergencyCallPresenter implements EmergencyCallContract.Presenter {
    private final static String TAG = EmergencyCallPresenter.class.getSimpleName();

    private NewApiConnect mApiConnect;
    private EmergencyCallContract.View mView;
    private Context mContext;


    EmergencyCallPresenter(Context context, EmergencyCallContract.View view) {
        mApiConnect = NewApiConnect.getInstance(context);
        mView = view;
        mContext = context;
    }

    @Override
    public void getEmergencyCallAPI() {
        mApiConnect.getEmergencyCall(new NewApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, int status) {
                mView.getEmergencyCallFailed(e.toString(), status);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {
                GetEmergenctCallResponse getEmergenctCallResponse = GetEmergenctCallResponse.getGson(response);

                if(getEmergenctCallResponse!=null && getEmergenctCallResponse.getOnlyContentData()!=null){
                    mView.getEmergencyCallSucceed(getEmergenctCallResponse.getOnlyContentData());
                }else{
                    mView.getEmergencyCallFailed(mContext.getString(R.string.data_error), NewApiConnect.TAG_DEFAULT);
                }
            }
        });
    }
}
