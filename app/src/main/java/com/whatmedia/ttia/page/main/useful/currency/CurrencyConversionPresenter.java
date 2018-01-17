package com.whatmedia.ttia.page.main.useful.currency;

import android.content.Context;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.connect.NewApiConnect;
import com.whatmedia.ttia.newresponse.GetExchangeRateQueryResponse;
import com.whatmedia.ttia.newresponse.GetExchangeRateResponse;
import com.whatmedia.ttia.newresponse.data.ExchangeRateQueryData;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class CurrencyConversionPresenter implements CurrencyConversionContract.Presenter {
    private final static String TAG = CurrencyConversionPresenter.class.getSimpleName();

    private NewApiConnect mNewApiConnect;
    private CurrencyConversionContract.View mView;
    private Context mContext;


    CurrencyConversionPresenter(Context context, CurrencyConversionContract.View view) {
        mNewApiConnect = NewApiConnect.getInstance(context);
        mView = view;
        mContext = context;
    }

    @Override
    public void getExchangeRate(ExchangeRateQueryData queryData) {
        GetExchangeRateQueryResponse response = new GetExchangeRateQueryResponse();
        response.setData(queryData);

        mNewApiConnect.exchangeRate(response.getJson(), new NewApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, int status) {
                mView.getExchangeRateFailed(e.toString(), status);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {
                GetExchangeRateResponse exchangeRateResponse = GetExchangeRateResponse.getGson(response);
                if (exchangeRateResponse != null)
                    mView.getExchangeRateSucceed(exchangeRateResponse.getAmount());
                else
                    mView.getExchangeRateFailed(mContext.getString(R.string.data_error), NewApiConnect.TAG_DEFAULT);
            }
        });
    }
}
