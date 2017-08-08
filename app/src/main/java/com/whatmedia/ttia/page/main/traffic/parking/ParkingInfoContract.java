package com.whatmedia.ttia.page.main.traffic.parking;

import com.whatmedia.ttia.response.data.HomeParkingInfoData;

import java.util.List;

public interface ParkingInfoContract {
    interface View {
        void getParkingInfoSucceed(List<HomeParkingInfoData> response);

        void getParkingInfoFailed(String message);

        void getParkingDetailSucceed(List<HomeParkingInfoData> response);

        void getParkingDetailFailed(String message);
    }

    interface Presenter {
        void getParkingInfoAPI();

        void getParkingDetailAPI();
    }
}
