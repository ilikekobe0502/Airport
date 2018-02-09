package com.whatsmedia.ttia.page.main.traffic.skytrain;

import com.whatsmedia.ttia.newresponse.data.BaseTrafficInfoData;

public interface SkyTrainContract {
    interface View {
        void getSkyTrainSucceed(BaseTrafficInfoData response);

        void getSkyTrainFailed(String message, int status);
    }

    interface Presenter {
        void getSkyTrainAPI();
    }
}
