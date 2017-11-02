package com.whatmedia.ttia.page.main.home.weather;

public interface HomeWeatherInfoContract {
    interface View {
        void getApiSucceed(String response);

        void getApiFailed(String message, boolean timeout);
    }

    interface Presenter {
        void getWeatherAPI(String cityId);
    }
}
