package com.whatmedia.ttia.page.main.home.arrive;


import com.whatmedia.ttia.response.data.FlightSearchData;
import com.whatmedia.ttia.response.data.FlightsInfoData;

public interface ArriveFlightsContract {

    interface View {
        void getArriveFlightSucceed(String list);

        void getArriveFlightFailed(String message, boolean timeout);

        void saveMyFlightSucceed(String message);

        void saveMyFlightFailed(String message, boolean timeout);
    }

    interface Presenter {
        void getArriveFlightAPI(FlightSearchData searchData);
        void saveMyFlightsAPI(FlightsInfoData data);
    }
}
