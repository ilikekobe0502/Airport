package com.whatmedia.ttia.page.main.secretary.sweet;


import com.whatmedia.ttia.newresponse.data.UserNewsData;

import java.util.List;

public interface AirportSweetNotifyContract {
    interface View {
        void getSweetNotifySucceed(List<UserNewsData> list);

        void getSweetNotifyFailed(String message, boolean timeout);
    }

    interface Presenter {
        void getSweetNotifyAPI();
    }
}
