package com.whatmedia.ttia.page.main.useful.currency;


import com.whatmedia.ttia.newresponse.data.ExchangeRateQueryData;

public interface CurrencyConversionContract {

    interface View {
        void getExchangeRateSucceed(float response);

        void getExchangeRateFailed(String result, int status);
    }

    interface Presenter {

        void getExchangeRate(ExchangeRateQueryData queryData);
    }
}
