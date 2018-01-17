package com.whatmedia.ttia.page.main.home.parking;

import com.whatmedia.ttia.response.data.HomeParkingInfoData;

import java.util.List;

public interface HomeParkingInfoContract {
    interface View {
        void getParkingInfoSucceed(List<HomeParkingInfoData> result);

        void getParkingInfoFailed(String message, int status);
    }

    interface Presenter {
        void getParkingInfoAPI();
    }
}
