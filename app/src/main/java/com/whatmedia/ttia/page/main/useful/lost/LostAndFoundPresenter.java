package com.whatmedia.ttia.page.main.useful.lost;


import android.content.Context;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.connect.NewApiConnect;
import com.whatmedia.ttia.newresponse.GetLostAndFoundInfoResponse;

import java.io.IOException;

import okhttp3.Call;

public class LostAndFoundPresenter implements LostAndFoundContract.Presenter {
    private final static String TAG = LostAndFoundPresenter.class.getSimpleName();

    private NewApiConnect mNewApiConnect;
    private LostAndFoundContract.View mView;
    private Context mContext;


    LostAndFoundPresenter(Context context, LostAndFoundContract.View view) {
        mNewApiConnect = NewApiConnect.getInstance(context);
        mView = view;
        mContext = context;
    }

    @Override
    public void getLostAndFoundAPI() {
        mNewApiConnect.getLostAndFoundInfo(new NewApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, int status) {
                mView.getLostAndFoundFailed(e.toString(), status);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {
                GetLostAndFoundInfoResponse lostAndFoundInfoResponse = GetLostAndFoundInfoResponse.getGson(response);
                if (lostAndFoundInfoResponse.getLostAndFound() != null)
                    mView.getLostAndFoundSucceed(lostAndFoundInfoResponse.getLostAndFound());
                else
                    mView.getLostAndFoundFailed(mContext.getString(R.string.data_error), NewApiConnect.TAG_DEFAULT);
            }
        });
    }
}
