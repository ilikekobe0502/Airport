package com.whatmedia.ttia.page.main.terminals.facility;

import com.whatmedia.ttia.response.data.AirportFacilityData;

import java.util.List;

public interface AirportFacilityContract {
    interface View {
        void getAirportFacilitySucceed(List<AirportFacilityData> response);

        void getAirportFacilityFailed(String message);
    }

    interface Presenter {
        void getAirportFacilityAPI();
    }
}
