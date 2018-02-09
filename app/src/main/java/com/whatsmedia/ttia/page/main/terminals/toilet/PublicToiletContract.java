package com.whatsmedia.ttia.page.main.terminals.toilet;

import com.whatsmedia.ttia.newresponse.data.ToiletFacilityListData;

import java.util.List;

public interface PublicToiletContract {
    interface View {
        void getPublicToiletSucceed(List<ToiletFacilityListData> response);

        void getPublicFailed(String message, int status);
    }

    interface Presenter {
        void getPublicToiletAPI();
    }
}
