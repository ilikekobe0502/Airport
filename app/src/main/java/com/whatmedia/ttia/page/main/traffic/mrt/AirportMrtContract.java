package com.whatmedia.ttia.page.main.traffic.mrt;

import com.whatmedia.ttia.newresponse.data.BaseTrafficInfoData;

public interface AirportMrtContract {
    interface View {
        void getAirportMrtSucceed(BaseTrafficInfoData response);

        void getAirportMrtFailed(String message, boolean timeout);
    }

    interface Presenter {
        void getAirportMrtAPI();
    }
}
