package com.whatmedia.ttia.page.main.communication.international;


import com.whatmedia.ttia.newresponse.data.onlyContentData;

public interface InternationalCallContract {
    interface View {
        void getInternationalCallSucceed(onlyContentData onlyContentData);

        void getInternationalCallFailed(String message, boolean timeout);
    }

    interface Presenter {
        void getInternationalCallAPI();
    }
}
