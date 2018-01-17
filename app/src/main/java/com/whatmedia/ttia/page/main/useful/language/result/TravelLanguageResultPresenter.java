package com.whatmedia.ttia.page.main.useful.language.result;


import android.content.Context;
import android.text.TextUtils;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.connect.NewApiConnect;
import com.whatmedia.ttia.newresponse.GetTravelListQueryResponse;
import com.whatmedia.ttia.newresponse.GetTravelListResponse;
import com.whatmedia.ttia.newresponse.data.TravelListQueryData;

import java.io.IOException;

import okhttp3.Call;

public class TravelLanguageResultPresenter implements TravelLanguageResultContract.Presenter {
    private final static String TAG = TravelLanguageResultPresenter.class.getSimpleName();

    private NewApiConnect mNewApiConnect;
    private TravelLanguageResultContract.View mView;
    private Context mContext;


    TravelLanguageResultPresenter(Context context, TravelLanguageResultContract.View view) {
        mNewApiConnect = NewApiConnect.getInstance(context);
        mView = view;
        mContext = context;
    }

    @Override
    public void getLanguageAPI(String id) {
        TravelListQueryData data = new TravelListQueryData();
        GetTravelListQueryResponse travelListQueryResponse = new GetTravelListQueryResponse();

        data.setTravelSessionTypeId(id);
        travelListQueryResponse.setData(data);

        String json = travelListQueryResponse.getJson();
        if (TextUtils.isEmpty(json)) {
            mView.getLanguageFailed(mContext.getString(R.string.data_error), NewApiConnect.TAG_DEFAULT);
            return;
        }

        mNewApiConnect.getTravelSessionList(json, new NewApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, int status) {
                mView.getLanguageFailed(e.toString(), status);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {
                GetTravelListResponse travelListResponse = GetTravelListResponse.getGson(response);
                if (travelListResponse.getTravelSessionList() != null)
                    mView.getLanguageSucceed(travelListResponse.getTravelSessionList());
                else
                    mView.getLanguageFailed(mContext.getString(R.string.data_error), NewApiConnect.TAG_DEFAULT);
            }
        });
    }
}
