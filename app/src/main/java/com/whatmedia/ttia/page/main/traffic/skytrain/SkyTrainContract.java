package com.whatmedia.ttia.page.main.traffic.skytrain;

import com.whatmedia.ttia.response.data.SkyTrainData;

import java.util.List;

public interface SkyTrainContract {
    interface View {
        void getSkyTrainSucceed(List<SkyTrainData> response);

        void getSkyTrainFailed(String message);
    }

    interface Presenter {
        void getSkyTrainAPI();
    }
}
