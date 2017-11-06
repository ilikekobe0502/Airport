package com.whatmedia.ttia.page.main.communication.emergency;


import com.whatmedia.ttia.newresponse.data.onlyContentData;

public interface EmergencyCallContract {
    interface View {
        void getEmergencyCallSucceed(onlyContentData onlyContentData);

        void getEmergencyCallFailed(String message, boolean timeout);
    }

    interface Presenter {
        void getEmergencyCallAPI();
    }
}
