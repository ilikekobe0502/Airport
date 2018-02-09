package com.whatsmedia.ttia.page.main.communication.international;


import android.content.Context;

import com.whatsmedia.ttia.R;
import com.whatsmedia.ttia.connect.NewApiConnect;
import com.whatsmedia.ttia.newresponse.GetInternationCallResponse;

import java.io.IOException;

import okhttp3.Call;

public class InternationalCallPresenter implements InternationalCallContract.Presenter {
    private final static String TAG = InternationalCallPresenter.class.getSimpleName();

    private NewApiConnect mApiConnect;
    private InternationalCallContract.View mView;
    private Context mContext;


    InternationalCallPresenter(Context context, InternationalCallContract.View view) {
        mApiConnect = NewApiConnect.getInstance(context);
        mView = view;
        mContext = context;
    }

    @Override
    public void getInternationalCallAPI() {
        mApiConnect.getInternationCall(new NewApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, int status) {
                mView.getInternationalCallFailed(e.toString(), status);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {
                GetInternationCallResponse getInternationCallResponse = GetInternationCallResponse.getGson(response);

                if(getInternationCallResponse!=null && getInternationCallResponse.getOnlyContentData()!=null){
                    mView.getInternationalCallSucceed(getInternationCallResponse.getOnlyContentData());
                }else{
                    mView.getInternationalCallFailed(mContext.getString(R.string.data_error), NewApiConnect.TAG_DEFAULT);
                }
            }
        });
    }
}
