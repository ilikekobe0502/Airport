package com.whatmedia.ttia.page.main.communication.emergency;


import com.whatmedia.ttia.response.data.EmergenctCallData;

import java.util.List;

public interface EmergencyCallContract {
    interface View {
        void getEmergencyCallSucceed(List<EmergenctCallData> response);

        void getEmergencyCallFailed(String message);
    }

    interface Presenter {
        void getEmergencyCallAPI();
    }
}
