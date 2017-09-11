package com.whatmedia.ttia.page.main.terminals.toilet;

import com.whatmedia.ttia.response.data.AirportFacilityData;

import java.util.List;

public interface PublicToiletContract {
    interface View {
        void getPublicToiletSucceed(List<AirportFacilityData> response);

        void getPublicFailed(String message, boolean timeout);
    }

    interface Presenter {
        void getPublicToiletAPI();
    }
}
