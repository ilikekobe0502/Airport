package com.whatmedia.ttia.page.main.secretary.news;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.connect.ApiConnect;
import com.whatmedia.ttia.connect.MyResponse;
import com.whatmedia.ttia.connect.NewApiConnect;
import com.whatmedia.ttia.newresponse.GetNewsResponse;
import com.whatmedia.ttia.response.GetUserNewsResponse;
import com.whatmedia.ttia.response.data.UserNewsData;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class AirportUserNewsPresenter implements AirportUserNewsContract.Presenter {
    private final static String TAG = AirportUserNewsPresenter.class.getSimpleName();

    private NewApiConnect mApiConnect;
    private AirportUserNewsContract.View mView;
    private Context mContext;


    public AirportUserNewsPresenter (Context context, AirportUserNewsContract.View view) {
        mApiConnect = NewApiConnect.getInstance(context);
        mView = view;
        mContext = context;
    }

    @Override
    public void getUserNewsAPI() {
        mApiConnect.getUserNews(new NewApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, boolean timeout) {
                mView.getUserNewsFailed(e.toString(), timeout);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {

                GetNewsResponse getNewsResponse = GetNewsResponse.getGson(response);

                if(getNewsResponse!=null && getNewsResponse.getUserNewsDataList()!=null){
                    mView.getUserNewsSucceed(getNewsResponse.getUserNewsDataList());
                }else{
                    mView.getUserNewsFailed(mContext.getString(R.string.data_error), false);
                }
            }
        });
    }
}
