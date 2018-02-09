package com.whatsmedia.ttia.page.main.secretary.sweet;


import com.whatsmedia.ttia.newresponse.data.UserNewsData;

import java.util.List;

public interface AirportSweetNotifyContract {
    interface View {
        void getSweetNotifySucceed(List<UserNewsData> list);

        void getSweetNotifyFailed(String message, int status);

        void deleteSweetNotifySucceed();

        void deleteSweetNotifyFailed(String message, int status);
    }

    interface Presenter {
        void getSweetNotifyAPI();

        void deleteSweetAPI(List<String> deleteList);
    }
}
