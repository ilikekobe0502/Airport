package com.whatmedia.ttia.page.main.communication.roaming.detail;


import android.content.Context;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.connect.NewApiConnect;
import com.whatmedia.ttia.newresponse.GetRoamingDetailResponse;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class RoamingDetailPresenter implements RoamingDetailContract.Presenter {
    private final static String TAG = RoamingDetailPresenter.class.getSimpleName();

    private NewApiConnect mApiConnect;
    private RoamingDetailContract.View mView;
    private Context mContext;


    RoamingDetailPresenter(Context context, RoamingDetailContract.View view) {
        mApiConnect = NewApiConnect.getInstance(context);
        mView = view;
        mContext = context;
    }

    @Override
    public void getRoamingDetailAPI(String id) {
        Map<String, Integer> request = new HashMap<>();
        request.put("telecommunicationIndustryId", Integer.valueOf(id));
        JSONObject json = new JSONObject(request);

        mApiConnect.getRoamingDetail(json.toString(), new NewApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, int status) {
                mView.getRoamingDetailFailed(e.toString(), status);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {

                GetRoamingDetailResponse getRoamingDetailResponse = GetRoamingDetailResponse.getGson(response);

                if(getRoamingDetailResponse!=null && getRoamingDetailResponse.getIrHtml() !=null && getRoamingDetailResponse.getIrUrl()!=null){
                    mView.getRoamingDetailSucceed(getRoamingDetailResponse.getIrUrl(),getRoamingDetailResponse.getIrHtml(),getRoamingDetailResponse.getImgDetailUrl());
                }else{
                    mView.getRoamingDetailFailed(mContext.getString(R.string.data_error), NewApiConnect.TAG_DEFAULT);
                }
            }
        });
    }
}
