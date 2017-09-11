package com.whatmedia.ttia.page.main.traffic.parking;

import com.whatmedia.ttia.response.data.HomeParkingInfoData;

import java.util.List;

public interface ParkingInfoContract {
    interface View {
        void getParkingInfoSucceed(List<HomeParkingInfoData> response);

        void getParkingInfoFailed(String message, boolean timeout);

        void getParkingDetailSucceed(List<HomeParkingInfoData> response);

        void getParkingDetailFailed(String message, boolean timeout);
    }

    interface Presenter {
        void getParkingInfoAPI();

        void getParkingDetailAPI();
    }
}
