package com.whatmedia.ttia.page.main.terminals.toilet;

import com.whatmedia.ttia.newresponse.data.ToiletFacilityListData;

import java.util.List;

public interface PublicToiletContract {
    interface View {
        void getPublicToiletSucceed(List<ToiletFacilityListData> response);

        void getPublicFailed(String message, boolean timeout);
    }

    interface Presenter {
        void getPublicToiletAPI();
    }
}
