package com.whatsmedia.ttia.page.main.home;

import android.content.Context;

import com.whatsmedia.ttia.connect.NewApiConnect;
import com.whatsmedia.ttia.newresponse.GetLanguageListResponse;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class HomePresenter implements HomeContract.Presenter {

    private NewApiConnect mNewApiConnect;
    private Context mContext;
    private HomeContract.View mView;


    HomePresenter(Context context, HomeContract.View view) {
        mNewApiConnect = NewApiConnect.getInstance(context);
        mView = view;
        mContext = context;
    }

    @Override
    public void getLanguageList() {
        mNewApiConnect.getLangList(new NewApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, int status) {
                mView.getLanguageListFailed(e.toString(), status);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {
                GetLanguageListResponse result = GetLanguageListResponse.getInstance(mContext, response);
                if (result != null && result.getData() != null)
                    mView.getLanguageListSuccess(result);
                else
                    onFailure(call, new IOException("Data is empty"), NewApiConnect.TAG_DEFAULT);
            }
        });
    }
}
