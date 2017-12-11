package com.whatmedia.ttia.page.main.home.arrive;


import com.whatmedia.ttia.newresponse.data.FlightsListData;

import java.util.List;

public interface ArriveFlightsContract {

    interface View {
        void getArriveFlightSucceed(List<FlightsListData> list);

        void getArriveFlightFailed(String message, boolean timeout);

        void saveMyFlightSucceed(String message, String sentJson);

        void saveMyFlightFailed(String message, boolean timeout);
    }

    interface Presenter {
        void getArriveFlightAPI();

        void saveMyFlightsAPI(FlightsListData flightsListData);
    }
}
