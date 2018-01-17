package com.whatmedia.ttia.page.main.terminals.facility;

import com.whatmedia.ttia.newresponse.data.TerminalsFacilityListData;

import java.util.List;

public interface AirportFacilityContract {
    interface View {
        void getAirportFacilitySucceed(List<TerminalsFacilityListData> response);

        void getAirportFacilityFailed(String message, int status);
    }

    interface Presenter {
        void getAirportFacilityAPI();
    }
}
