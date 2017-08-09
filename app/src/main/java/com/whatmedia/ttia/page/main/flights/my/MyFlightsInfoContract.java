package com.whatmedia.ttia.page.main.flights.my;

import com.whatmedia.ttia.response.data.FlightsInfoData;
import com.whatmedia.ttia.response.data.FlightsInfoData;

import java.util.List;

public interface MyFlightsInfoContract {
    interface View {
        void getMyFlightsInfoSucceed(List<FlightsInfoData> response);

        void getMyFlightsInfoFailed(String message);

        void deleteMyFlightsInfoSucceed(String response);

        void deleteMyFlightsInfoFailed(String message);
    }

    interface Presenter {
        void getMyFlightsInfoAPI();

        void deleteMyFlightsInfoAPI(FlightsInfoData data);
    }
}
