package com.whatmedia.ttia.page.main.flights.my;

import com.whatmedia.ttia.response.data.FlightsInfoData;

import java.util.List;

public interface MyFlightsInfoContract {
    String TAG_INSERT = "com.whatmedia.ttia.page.main.flights.my.insert";

    interface View {
        void getMyFlightsInfoSucceed(List<FlightsInfoData> response);

        void getMyFlightsInfoFailed(String message, boolean timeout);

        void deleteMyFlightsInfoSucceed(String response);

        void deleteMyFlightsInfoFailed(String message, boolean timeout);
    }

    interface Presenter {
        void getMyFlightsInfoAPI();

        void deleteMyFlightsInfoAPI(FlightsInfoData data);
    }
}
