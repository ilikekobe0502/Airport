package com.whatsmedia.ttia.page.main.Achievement;


import android.content.Context;

import com.whatsmedia.ttia.R;
import com.whatsmedia.ttia.connect.NewApiConnect;
import com.whatsmedia.ttia.newresponse.GetAchievementsDataResponse;

import java.io.IOException;

import okhttp3.Call;

public class AchievementPresenter implements AchievementContract.Presenter {
    private final static String TAG = AchievementPresenter.class.getSimpleName();

    private NewApiConnect mNewApiConnect;
    private AchievementContract.View mView;
    private Context mContext;


    AchievementPresenter(Context context, AchievementContract.View view) {
        mNewApiConnect = NewApiConnect.getInstance(context);
        mView = view;
        mContext = context;
    }

    @Override
    public void queryAchievementList() {
        mNewApiConnect.getAchievementList(new NewApiConnect.MyCallback() {

            @Override
            public void onFailure(Call call, IOException e, int status) {
                mView.queryAchievementListFail(e.toString(), status);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {

                GetAchievementsDataResponse achievementsDataResponse = GetAchievementsDataResponse.getGson(response);

                if(achievementsDataResponse!=null && achievementsDataResponse.getAchievementList()!=null){
                    mView.queryAchievementListSuccess(achievementsDataResponse.getAchievementList());
                }else{
                    mView.queryAchievementListFail(mContext.getString(R.string.data_error) , NewApiConnect.TAG_DEFAULT);
                }
            }
        });

    }
}
