package com.whatmedia.ttia.page.main.home.moreflights;


import com.whatmedia.ttia.response.data.FlightSearchData;
import com.whatmedia.ttia.response.data.FlightsInfoData;

import java.util.List;

public interface MoreFlightsContract {
    String TAG_KIND = "com.whatmedia.ttia.page.main.home.moreflights.Kind";

    interface View {
        void getFlightSucceed(List<FlightsInfoData> list);

        void getFlightFailed(String message, boolean timeout);

        void saveMyFlightSucceed(String message);

        void saveMyFlightFailed(String message, boolean timeout);
    }

    interface Presenter {
        void getFlightAPI(FlightSearchData searchData);

        void saveMyFlightsAPI(FlightsInfoData data);
    }
}
