package com.whatsmedia.ttia.page.main.flights.result;


import com.whatsmedia.ttia.newresponse.data.FlightsListData;

import java.util.List;

public interface FlightsSearchResultContract {
    String TAG_ARRIVE_FLIGHTS = "com.whatmedia.ttia.page.main.flights.result.arrive_flights";
    String TAG_DEPARTURE_FLIGHTS = "com.whatmedia.ttia.page.main.flights.result.departure_flights";
    String TAG_ALL_FLIGHTS = "com.whatmedia.ttia.page.main.flights.result.all_flights";
    String TAG_KEY_WORLD = "com.whatmedia.ttia.page.main.flights.result.key_world";

    interface View {
        void saveMyFlightSucceed(String message, String sentJson);

        void saveMyFlightFailed(String message, int status);

        void getFlightSucceed(List<FlightsListData> list);

        void getFlightFailed(String message, int status);

        String getKeyword();
    }

    interface Presenter {
        void saveMyFlightsAPI(FlightsListData flightsListData);

        void getFlightAPI();

        void getFlightByDateAPI(String date);

        void getFlightByQueryTypeAPI(int queryType);
    }
}
