package com.whatmedia.ttia.page.main.flights.my;

import com.whatmedia.ttia.newresponse.data.FlightsListData;

import java.util.List;

public interface MyFlightsInfoContract {
    String TAG_INSERT = "com.whatmedia.ttia.page.main.flights.my.insert";

    interface View {
        void getMyFlightsInfoSucceed(List<FlightsListData> response);

        void getMyFlightsInfoFailed(String message, boolean timeout);

        void deleteMyFlightsInfoSucceed();

        void deleteMyFlightsInfoFailed(String message, boolean timeout);

        void addNotification(FlightsListData data);

        void deleteNotification();
    }

    interface Presenter {
        void getMyFlightsInfoAPI();

        void deleteMyFlightsInfoAPI(List<FlightsListData> selectList);
    }
}
