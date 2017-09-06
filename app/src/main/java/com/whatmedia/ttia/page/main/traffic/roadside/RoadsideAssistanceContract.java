package com.whatmedia.ttia.page.main.traffic.roadside;

import com.whatmedia.ttia.response.data.RoadsideAssistanceData;

import java.util.List;

public interface RoadsideAssistanceContract {
    interface View {
        void getRoadsideAssistanceSucceed(List<RoadsideAssistanceData> response);

        void getRoadsideAssistanceFailed(String message, boolean timeout);
    }

    interface Presenter {
        void getRoadsideAssistanceAPI();
    }
}
