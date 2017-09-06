package com.whatmedia.ttia.page.main.flights.search;

import com.whatmedia.ttia.response.data.FlightSearchData;

public interface FlightsSearchContract {
    interface View {
        void getFlightsArriveSucceed(String response);

        void getFlightsArriveFailed(String message, boolean timeout);

        void getFlightsDepartureSucceed(String response);

        void getFlightsDepartureFailed(String message, boolean timeout);
    }

    interface Presenter {
        void getFlightsInfoAPI(FlightSearchData searchData);
    }
}
