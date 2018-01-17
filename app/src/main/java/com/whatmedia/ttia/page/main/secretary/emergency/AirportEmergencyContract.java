package com.whatmedia.ttia.page.main.secretary.emergency;


import com.whatmedia.ttia.newresponse.data.UserNewsData;

import java.util.List;

public interface AirportEmergencyContract {
    interface View {
        void getEmergencySucceed(List<UserNewsData> list);

        void getEmergencyFailed(String message, int status);
    }

    interface Presenter {
        void getEmergencyAPI();
    }
}
