package com.whatmedia.ttia.page.main.Achievement;


import android.content.Context;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.connect.NewApiConnect;
import com.whatmedia.ttia.newresponse.GetAchievementsDataResponse;
import com.whatmedia.ttia.response.data.AchievementsData;

import java.io.IOException;
import java.util.List;

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
            public void onFailure(Call call, IOException e, boolean timeout) {
                mView.queryAchievementListFail(e.toString(), timeout);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {

                GetAchievementsDataResponse achievementsDataResponse = GetAchievementsDataResponse.getGson(response);

                if(achievementsDataResponse!=null && achievementsDataResponse.getAchievementList()!=null){
                    mView.queryAchievementListSuccess(achievementsDataResponse.getAchievementList());
                }else{
                    mView.queryAchievementListFail(mContext.getString(R.string.data_error) , false);
                }
            }
        });

    }
}
