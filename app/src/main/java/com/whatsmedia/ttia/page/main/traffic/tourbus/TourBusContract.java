package com.whatsmedia.ttia.page.main.traffic.tourbus;

import com.whatsmedia.ttia.newresponse.data.BaseTrafficInfoData;

public interface TourBusContract {
    interface View {
        void getTourBusSucceed(BaseTrafficInfoData response);

        void getTourBusFailed(String message, int status);
    }

    interface Presenter {
        void getTourBusAPI();
    }
}
