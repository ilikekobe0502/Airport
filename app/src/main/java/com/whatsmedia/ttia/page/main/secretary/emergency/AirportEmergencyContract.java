package com.whatsmedia.ttia.page.main.secretary.emergency;


import com.whatsmedia.ttia.newresponse.data.UserNewsData;

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
