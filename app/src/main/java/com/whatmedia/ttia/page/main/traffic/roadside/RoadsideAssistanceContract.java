package com.whatmedia.ttia.page.main.traffic.roadside;

import com.whatmedia.ttia.newresponse.data.BaseTrafficInfoData;

public interface RoadsideAssistanceContract {
    interface View {
        void getRoadsideAssistanceSucceed(BaseTrafficInfoData response);

        void getRoadsideAssistanceFailed(String message, boolean timeout);
    }

    interface Presenter {
        void getRoadsideAssistanceAPI();
    }
}
