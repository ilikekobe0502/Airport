package com.whatmedia.ttia.page.main.secretary.emergency;

import com.whatmedia.ttia.response.data.UserNewsData;

import java.util.List;

public interface AirportEmergencyContract {
    interface View {
        void getEmergencySucceed(List<UserNewsData> list);

        void getEmergencyFailed(String message, boolean timeout);
    }

    interface Presenter {
        void getEmergencyAPI();
    }
}
