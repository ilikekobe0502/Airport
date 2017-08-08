package com.whatmedia.ttia.page.main.traffic.bus;

import com.whatmedia.ttia.response.data.AirportBusData;

import java.util.List;

public interface AirportBusContract {
    interface View {
        void getAirportBusSucceed(List<AirportBusData> response);

        void getAirportBusFailed(String message);
    }

    interface Presenter {
        void getAirportBusAPI();
    }
}
