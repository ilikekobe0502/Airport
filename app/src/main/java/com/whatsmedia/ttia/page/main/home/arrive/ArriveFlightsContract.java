package com.whatsmedia.ttia.page.main.home.arrive;


import com.whatsmedia.ttia.newresponse.data.FlightsListData;

import java.util.List;

public interface ArriveFlightsContract {

    interface View {
        void getArriveFlightSucceed(List<FlightsListData> list);

        void getArriveFlightFailed(String message, int status);

        void saveMyFlightSucceed(String message, String sentJson);

        void saveMyFlightFailed(String message, int status);
    }

    interface Presenter {
        void getArriveFlightAPI();

        void saveMyFlightsAPI(FlightsListData flightsListData);
    }
}
