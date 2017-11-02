package com.whatmedia.ttia.page.main.communication.roaming;


import android.content.Context;
import android.text.TextUtils;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.connect.ApiConnect;
import com.whatmedia.ttia.connect.MyResponse;
import com.whatmedia.ttia.connect.NewApiConnect;
import com.whatmedia.ttia.newresponse.GetRoamingServiceResponse;

import java.io.IOException;

import okhttp3.Call;

public class RoamingServicePresenter implements RoamingServiceContract.Presenter {
    private final static String TAG = RoamingServicePresenter.class.getSimpleName();

    private NewApiConnect mApiConnect;
    private RoamingServiceContract.View mView;
    private Context mContext;


    RoamingServicePresenter(Context context, RoamingServiceContract.View view) {
        mApiConnect = NewApiConnect.getInstance(context);
        mView = view;
        mContext = context;
    }

    @Override
    public void getRoamingServiceAPI() {
        mApiConnect.getRoamingService(new NewApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, boolean timeout) {
                mView.getRoamingServiceFailed(e.toString(), timeout);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {
                GetRoamingServiceResponse getRoamingServiceResponse = GetRoamingServiceResponse.getGson(response);

                if(getRoamingServiceResponse!=null && getRoamingServiceResponse.getRoamingServiceDataList()!=null){
                    mView.getRoamingServiceSucceed(getRoamingServiceResponse.getRoamingServiceDataList());
                }else{
                    mView.getRoamingServiceFailed(mContext.getString(R.string.data_error), false);
                }
            }
        });
    }
}
