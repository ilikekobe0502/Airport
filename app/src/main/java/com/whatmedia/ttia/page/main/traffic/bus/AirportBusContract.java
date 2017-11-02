package com.whatmedia.ttia.page.main.traffic.bus;

import com.whatmedia.ttia.newresponse.data.BaseTrafficInfoData;

public interface AirportBusContract {
    interface View {
        void getAirportBusSucceed(BaseTrafficInfoData response);

        void getAirportBusFailed(String message, boolean timeout);
    }

    interface Presenter {
        void getAirportBusAPI();
    }
}
