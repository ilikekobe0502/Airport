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

        boolean addNotification(FlightsListData data);

        void deleteNotification(int notificationId);

        boolean modifyNotification(FlightsListData data);

        void marginDisplayData();
    }

    interface Presenter {
        void getMyFlightsInfoAPI();

        void deleteMyFlightsInfoAPI(List<FlightsListData> selectList);
    }
}
