package com.whatmedia.ttia.page.main.traffic.mrt;

import com.whatmedia.ttia.response.data.AirportMrtData;

import java.util.List;

public interface AirportMrtContract {
    interface View {
        void getAirportMrtSucceed(List<AirportMrtData> response);

        void getAirportMrtFailed(String message, boolean timeout);
    }

    interface Presenter {
        void getAirportMrtAPI();
    }
}
