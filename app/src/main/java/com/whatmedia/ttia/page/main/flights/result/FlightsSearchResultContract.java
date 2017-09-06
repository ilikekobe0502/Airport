package com.whatmedia.ttia.page.main.flights.result;


import com.whatmedia.ttia.response.data.FlightSearchData;
import com.whatmedia.ttia.response.data.FlightsInfoData;

import java.util.List;

public interface FlightsSearchResultContract {
    String TAG_ARRIVE_FLIGHTS = "com.neo.taoyuanairport.page.main.flights.result.arrive_flights";
    String TAG_DEPARTURE_FLIGHTS = "com.neo.taoyuanairport.page.main.flights.result.departure_flights";
    String TAG_KEY_WORLD = "com.whatmedia.ttia.page.main.flights.result.key_world";

    interface View {
        void saveMyFlightSucceed(String message);

        void saveMyFlightFailed(String message, boolean timeout);

        void getFlightSucceed(List<FlightsInfoData> list);

        void getFlightFailed(String message, boolean timeout);
    }

    interface Presenter {
        void saveMyFlightsAPI(FlightsInfoData data);

        void getFlightAPI(FlightSearchData searchData);

        void getFlightByKeywordAPI(FlightSearchData searchData);

    }
}
