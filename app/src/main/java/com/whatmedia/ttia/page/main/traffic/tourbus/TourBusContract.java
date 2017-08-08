package com.whatmedia.ttia.page.main.traffic.tourbus;

import com.whatmedia.ttia.response.data.TourBusData;

import java.util.List;

public interface TourBusContract {
    interface View {
        void getTourBusSucceed(List<TourBusData> response);

        void getTourBusFailed(String message);
    }

    interface Presenter {
        void getTourBusAPI();
    }
}
