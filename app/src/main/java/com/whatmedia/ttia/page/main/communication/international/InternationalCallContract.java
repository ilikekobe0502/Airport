package com.whatmedia.ttia.page.main.communication.international;


import com.whatmedia.ttia.newresponse.data.InternationCallData;

public interface InternationalCallContract {
    interface View {
        void getInternationalCallSucceed(InternationCallData internationCallData);

        void getInternationalCallFailed(String message, boolean timeout);
    }

    interface Presenter {
        void getInternationalCallAPI();
    }
}
