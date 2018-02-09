package com.whatsmedia.ttia.page.main.useful.currency;


import com.whatsmedia.ttia.newresponse.data.ExchangeRateQueryData;

public interface CurrencyConversionContract {

    interface View {
        void getExchangeRateSucceed(float response);

        void getExchangeRateFailed(String result, int status);
    }

    interface Presenter {

        void getExchangeRate(ExchangeRateQueryData queryData);
    }
}
