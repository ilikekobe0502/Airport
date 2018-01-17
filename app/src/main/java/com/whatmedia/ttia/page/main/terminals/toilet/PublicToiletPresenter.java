package com.whatmedia.ttia.page.main.terminals.toilet;

import android.content.Context;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.connect.NewApiConnect;
import com.whatmedia.ttia.newresponse.GetToiletFacilityListResponse;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class PublicToiletPresenter implements PublicToiletContract.Presenter {
    private final static String TAG = PublicToiletPresenter.class.getSimpleName();

    private NewApiConnect mNewApiConnect;
    private PublicToiletContract.View mView;
    private Context mContext;


    PublicToiletPresenter(Context context, PublicToiletContract.View view) {
        mNewApiConnect = NewApiConnect.getInstance(context);
        mView = view;
        mContext = context;
    }

    @Override
    public void getPublicToiletAPI() {
        mNewApiConnect.getTerminalsToiletList(new NewApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, int status) {
                mView.getPublicFailed(e.toString(), status);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {
                GetToiletFacilityListResponse toiletFacilityListResponse = GetToiletFacilityListResponse.getGson(response);
                if (toiletFacilityListResponse.getTerminalsToiletList() != null)
                    mView.getPublicToiletSucceed(toiletFacilityListResponse.getTerminalsToiletList());
                else
                    mView.getPublicFailed(mContext.getString(R.string.data_error), NewApiConnect.TAG_DEFAULT);
            }
        });
    }
}
