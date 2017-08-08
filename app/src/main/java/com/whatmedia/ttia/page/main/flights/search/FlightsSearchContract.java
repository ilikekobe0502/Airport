package com.whatmedia.ttia.page.main.flights.search;

import com.whatmedia.ttia.response.data.FlightSearchData;

public interface FlightsSearchContract {
    interface View {
        void getFlightsArriveSucceed(String response);

        void getFlightsArriveFailed(String message);

        void getFlightsDepartureSucceed(String response);

        void getFlightsDepartureFailed(String message);
    }

    interface Presenter {
        void getFlightsInfoAPI(FlightSearchData searchData);
    }
}
