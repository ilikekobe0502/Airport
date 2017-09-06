package com.whatmedia.ttia.page.main.useful.currency;


import com.whatmedia.ttia.response.data.ExchangeRateData;

public interface CurrencyConversionContract {

    interface View {
        void getExchangeRateSucceed(ExchangeRateData response);

        void getExchangeRateFailed(String result, boolean timeout);
    }

    interface Presenter {

        void getExchangeRate(ExchangeRateData data);
    }
}
