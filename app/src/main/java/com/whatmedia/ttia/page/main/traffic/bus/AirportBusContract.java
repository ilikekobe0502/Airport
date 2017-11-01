package com.whatmedia.ttia.page.main.traffic.bus;

import com.whatmedia.ttia.newresponse.data.BusInfoData;

public interface AirportBusContract {
    interface View {
        void getAirportBusSucceed(BusInfoData response);

        void getAirportBusFailed(String message, boolean timeout);
    }

    interface Presenter {
        void getAirportBusAPI();
    }
}
