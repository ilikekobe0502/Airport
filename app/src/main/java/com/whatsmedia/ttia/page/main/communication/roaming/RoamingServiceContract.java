package com.whatsmedia.ttia.page.main.communication.roaming;

import com.whatsmedia.ttia.newresponse.data.RoamingServiceData;

import java.util.List;

public interface RoamingServiceContract {

    String ARG_KEY = "key";

    String ARG_TITLE = "title";

    interface View {
        void getRoamingServiceSucceed(List<RoamingServiceData> response);

        void getRoamingServiceFailed(String message, int status);
    }

    interface Presenter {
        void getRoamingServiceAPI();
    }

}