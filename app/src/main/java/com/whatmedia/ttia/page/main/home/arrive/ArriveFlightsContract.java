package com.whatmedia.ttia.page.main.home.arrive;


import com.whatmedia.ttia.response.data.FlightSearchData;
import com.whatmedia.ttia.response.data.FlightsInfoData;

import java.util.List;

public interface ArriveFlightsContract {

    interface View {
        void getArriveFlightSucceed(List<FlightsInfoData> list);

        void getArriveFlightFailed(String message);
    }

    interface Presenter {
        void getArriveFlightAPI(FlightSearchData searchData);
    }
}
