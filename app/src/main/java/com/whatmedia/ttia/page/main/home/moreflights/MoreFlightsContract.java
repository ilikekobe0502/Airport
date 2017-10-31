package com.whatmedia.ttia.page.main.home.moreflights;


import com.whatmedia.ttia.newresponse.data.FlightsListData;

import java.util.List;

public interface MoreFlightsContract {
    String TAG_KIND = "com.whatmedia.ttia.page.main.home.moreflights.Kind";

    interface View {
        void getFlightSucceed(List<FlightsListData> list);

        void getFlightFailed(String message, boolean timeout);

        void saveMyFlightSucceed(String message);

        void saveMyFlightFailed(String message, boolean timeout);
    }

    interface Presenter {
        void getFlightAPI();

        void getFlightByQueryTypeAPI(int queryType);

        void getFlightByDateAPI(String date);

        void saveMyFlightsAPI(FlightsListData flightsListData);
    }
}
