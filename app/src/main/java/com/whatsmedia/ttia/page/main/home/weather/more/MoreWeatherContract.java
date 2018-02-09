package com.whatsmedia.ttia.page.main.home.weather.more;

public interface MoreWeatherContract {
    interface View {
        void showWebView(String cityId);

        void getDeviceIdSucceed(String deviceId);

        void getDeviceIdFailed(String error);
    }

    interface Presenter {

        void getDeviceId();
    }
}
