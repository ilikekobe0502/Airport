package com.whatsmedia.ttia.page.main.home.departure;


import com.whatsmedia.ttia.newresponse.data.FlightsListData;

import java.util.List;

public interface DepartureFlightsContract {

    interface View {
        void getDepartureFlightSucceed(List<FlightsListData> list);

        void getDepartureFlightFailed(String message, int status);

        void saveMyFlightSucceed(String message, String sentJson);

        void saveMyFlightFailed(String message, int status);
    }

    interface Presenter {
        void getDepartureFlightAPI();

        void saveMyFlightsAPI(FlightsListData flightsListData);
    }
}
