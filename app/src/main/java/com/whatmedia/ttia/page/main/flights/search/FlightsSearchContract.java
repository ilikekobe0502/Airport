package com.whatmedia.ttia.page.main.flights.search;

public interface FlightsSearchContract {
    interface View {

        void getFlightsDepartureSucceed(String response);

        void getFlightsDepartureFailed(String message, int status);
    }

    interface Presenter {
        void getFlightsInfoAPI(String keyword);
    }
}
