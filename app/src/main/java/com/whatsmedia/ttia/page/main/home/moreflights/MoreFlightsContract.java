package com.whatsmedia.ttia.page.main.home.moreflights;


import com.whatsmedia.ttia.newresponse.data.FlightsListData;

import java.util.List;

public interface MoreFlightsContract {
    String TAG_KIND = "com.whatmedia.ttia.page.main.home.moreflights.Kind";

    interface View {
        void getFlightSucceed(List<FlightsListData> list);

        void getFlightFailed(String message, int status);

        void saveMyFlightSucceed(String message, String sentJson);

        void saveMyFlightFailed(String message, int status);
    }

    interface Presenter {
        void getFlightAPI();

        void getFlightByQueryTypeAPI(int queryType);

        void getFlightByDateAPI(String date);

        void saveMyFlightsAPI(FlightsListData flightsListData);
    }
}
