package com.whatmedia.ttia.page.main.flights.result;


import com.whatmedia.ttia.response.data.FlightsInfoData;

public interface FlightsSearchResultContract {
    String TAG_ARRIVE_FLIGHTS = "com.neo.taoyuanairport.page.main.flights.result.arrive_flights";
    String TAG_DEPARTURE_FLIGHTS = "com.neo.taoyuanairport.page.main.flights.result.departure_flights";

    interface View {
        void saveMyFlightSucceed(String message);

        void saveMyFlightFailed(String message);
    }

    interface Presenter {
        void saveMyFlightsAPI(FlightsInfoData data);
    }
}
