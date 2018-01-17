package com.whatmedia.ttia.page.main.secretary.news;

import com.whatmedia.ttia.newresponse.data.UserNewsData;

import java.util.List;

public interface AirportUserNewsContract {
    interface View {
        void getUserNewsSucceed(List<UserNewsData> list);

        void getUserNewsFailed(String message, int status);
    }

    interface Presenter {
        void getUserNewsAPI();
    }
}
