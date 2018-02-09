package com.whatsmedia.ttia.page.main.secretary.news;

import android.content.Context;

import com.whatsmedia.ttia.R;
import com.whatsmedia.ttia.connect.NewApiConnect;
import com.whatsmedia.ttia.newresponse.GetNewsResponse;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class AirportUserNewsPresenter implements AirportUserNewsContract.Presenter {
    private final static String TAG = AirportUserNewsPresenter.class.getSimpleName();

    private NewApiConnect mApiConnect;
    private AirportUserNewsContract.View mView;
    private Context mContext;


    AirportUserNewsPresenter(Context context, AirportUserNewsContract.View view) {
        mApiConnect = NewApiConnect.getInstance(context);
        mView = view;
        mContext = context;
    }

    @Override
    public void getUserNewsAPI() {
        mApiConnect.getUserNews(new NewApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, int status) {
                mView.getUserNewsFailed(e.toString(), status);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {

                GetNewsResponse getNewsResponse = GetNewsResponse.getGson(response);

                if (getNewsResponse != null && getNewsResponse.getUserNewsDataList() != null) {
                    mView.getUserNewsSucceed(getNewsResponse.getUserNewsDataList());
                } else {
                    mView.getUserNewsFailed(mContext.getString(R.string.data_error), NewApiConnect.TAG_DEFAULT);
                }
            }
        });
    }
}
