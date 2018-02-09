package com.whatsmedia.ttia.page.main.home.weather;

public interface HomeWeatherInfoContract {
    interface View {
        void getDeviceIdSuccess(String deviceId);

        void getDeviceIdFailed(String deviceId);
    }

    interface Presenter {
        void getDeviceId();
    }
}
