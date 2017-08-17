package com.whatmedia.ttia.page.main.flights.result;


import com.whatmedia.ttia.response.data.FlightSearchData;
import com.whatmedia.ttia.response.data.FlightsInfoData;

import java.util.List;

public interface FlightsSearchResultContract {
    String TAG_ARRIVE_FLIGHTS = "com.neo.taoyuanairport.page.main.flights.result.arrive_flights";
    String TAG_DEPARTURE_FLIGHTS = "com.neo.taoyuanairport.page.main.flights.result.departure_flights";

    interface View {
        void saveMyFlightSucceed(String message);

        void saveMyFlightFailed(String message);

        void getFlightSucceed(List<FlightsInfoData> list);

        void getFlightFailed(String message);
    }

    interface Presenter {
        void saveMyFlightsAPI(FlightsInfoData data);

        void getFlightAPI(FlightSearchData searchData);

    }
}
