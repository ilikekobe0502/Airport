package com.whatmedia.ttia.page.main.store.souvenir;


import android.content.Context;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.connect.NewApiConnect;
import com.whatmedia.ttia.newresponse.GetSouvenirDataResponse;

import java.io.IOException;

import okhttp3.Call;

public class SouvenirAreaPresenter implements SouvenirAreaContract.Presenter {
    private final static String TAG = SouvenirAreaPresenter.class.getSimpleName();

    private NewApiConnect mApiConnect;
    private SouvenirAreaContract.View mView;
    private Context mContext;


    public SouvenirAreaPresenter(Context context, SouvenirAreaContract.View view) {
        mApiConnect = NewApiConnect.getInstance(context);
        mView = view;
        mContext = context;
    }

    @Override
    public void querySouvenirList() {
        mApiConnect.getSouvenirList(new NewApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, int status) {
                mView.querySouvenirListFail(e.toString(), status);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {
                GetSouvenirDataResponse getSouvenirDataResponse = GetSouvenirDataResponse.getGson(response);

                if(getSouvenirDataResponse!=null && getSouvenirDataResponse.getSouvenirDataList()!=null){
                    mView.querySouvenirListSuccess(getSouvenirDataResponse.getSouvenirDataList());
                }else{
                    mView.querySouvenirListFail(mContext.getString(R.string.data_error), NewApiConnect.TAG_DEFAULT);
                }
            }
        });
    }
}
