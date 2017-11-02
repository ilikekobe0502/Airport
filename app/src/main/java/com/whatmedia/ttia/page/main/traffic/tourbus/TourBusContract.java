package com.whatmedia.ttia.page.main.traffic.tourbus;

import com.whatmedia.ttia.newresponse.data.BaseTrafficInfoData;

public interface TourBusContract {
    interface View {
        void getTourBusSucceed(BaseTrafficInfoData response);

        void getTourBusFailed(String message, boolean timeout);
    }

    interface Presenter {
        void getTourBusAPI();
    }
}
