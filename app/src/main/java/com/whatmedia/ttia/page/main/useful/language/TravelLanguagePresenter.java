package com.whatmedia.ttia.page.main.useful.language;


import android.content.Context;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.connect.NewApiConnect;
import com.whatmedia.ttia.newresponse.GetTravelTypeListResponse;
import com.whatmedia.ttia.newresponse.data.TravelTypeListData;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

public class TravelLanguagePresenter implements TravelLanguageContract.Presenter {
    private final static String TAG = TravelLanguagePresenter.class.getSimpleName();

    private NewApiConnect mNewApiConnect;
    private TravelLanguageContract.View mView;
    private Context mContext;


    TravelLanguagePresenter(Context context, TravelLanguageContract.View view) {
        mNewApiConnect = NewApiConnect.getInstance(context);
        mView = view;
        mContext = context;
    }

    @Override
    public void getTypeListApi() {
        mNewApiConnect.getTravelSessionTypeList(new NewApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, int status) {
                mView.getApiFailed(e.toString(), status);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {
                GetTravelTypeListResponse travelTypeListResponse = GetTravelTypeListResponse.getGson(response);
                List<TravelTypeListData> listData = travelTypeListResponse.getTravelSessionTypeList();
                if (listData != null && listData.size() > 0)
                    mView.getApiSucceed(listData);
                else
                    mView.getApiFailed(mContext.getString(R.string.data_error), NewApiConnect.TAG_DEFAULT);
            }
        });
    }
}
