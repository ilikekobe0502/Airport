package com.whatmedia.ttia.page.main.communication.international;


import android.content.Context;
import android.text.TextUtils;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.connect.ApiConnect;
import com.whatmedia.ttia.connect.MyResponse;
import com.whatmedia.ttia.connect.NewApiConnect;
import com.whatmedia.ttia.newresponse.GetInternationCallResponse;

import java.io.IOException;
import java.util.List;

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
            public void onFailure(Call call, IOException e, boolean timeout) {
                mView.getInternationalCallFailed(e.toString(), timeout);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {
                GetInternationCallResponse getInternationCallResponse = GetInternationCallResponse.getGson(response);

                if(getInternationCallResponse!=null && getInternationCallResponse.getInternationCallData()!=null){
                    mView.getInternationalCallSucceed(getInternationCallResponse.getInternationCallData());
                }else{
                    mView.getInternationalCallFailed(mContext.getString(R.string.data_error), false);
                }
            }
        });
    }
}
