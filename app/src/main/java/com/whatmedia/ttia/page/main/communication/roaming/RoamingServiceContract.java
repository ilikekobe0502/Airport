package com.whatmedia.ttia.page.main.communication.roaming;

import com.whatmedia.ttia.newresponse.data.RoamingServiceData;

import java.util.List;

public interface RoamingServiceContract {
    interface View {
        void getRoamingServiceSucceed(List<RoamingServiceData> response);

        void getRoamingServiceFailed(String message, boolean timeout);
    }

    interface Presenter {
        void getRoamingServiceAPI();
    }

}
