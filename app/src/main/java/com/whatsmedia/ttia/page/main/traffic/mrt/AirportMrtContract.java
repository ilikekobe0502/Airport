package com.whatsmedia.ttia.page.main.traffic.mrt;

import com.whatsmedia.ttia.newresponse.data.BaseTrafficInfoData;

public interface AirportMrtContract {
    interface View {
        void getAirportMrtSucceed(BaseTrafficInfoData response);

        void getAirportMrtFailed(String message, int status);
    }

    interface Presenter {
        void getAirportMrtAPI();
    }
}
