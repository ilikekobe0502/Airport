package com.whatmedia.ttia.page.main.home.departure;


import com.whatmedia.ttia.newresponse.data.FlightsListData;

import java.util.List;

public interface DepartureFlightsContract {

    interface View {
        void getDepartureFlightSucceed(List<FlightsListData> list);

        void getDepartureFlightFailed(String message, boolean timeout);

        void saveMyFlightSucceed(String message, String sentJson);

        void saveMyFlightFailed(String message, boolean timeout);
    }

    interface Presenter {
        void getDepartureFlightAPI();

        void saveMyFlightsAPI(FlightsListData flightsListData);
    }
}
