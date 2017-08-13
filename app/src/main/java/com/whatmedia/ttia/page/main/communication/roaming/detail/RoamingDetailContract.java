package com.whatmedia.ttia.page.main.communication.roaming.detail;


import com.whatmedia.ttia.response.data.RoamingDetailData;

import java.util.List;

public interface RoamingDetailContract {
    interface View {
        void getRoamingDetailSucceed(List<RoamingDetailData> response);

        void getRoamingDetailFailed(String message);
    }

    interface Presenter {
        void getRoamingDetailAPI(String id);
    }
}
