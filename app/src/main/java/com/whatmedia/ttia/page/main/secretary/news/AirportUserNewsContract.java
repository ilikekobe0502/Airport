package com.whatmedia.ttia.page.main.secretary.news;

import com.whatmedia.ttia.response.data.UserNewsData;

import java.util.List;

public interface AirportUserNewsContract {
    interface View {
        void getUserNewsSucceed(List<UserNewsData> list);

        void getUserNewsFailed(String message, boolean timeout);
    }

    interface Presenter {
        void getUserNewsAPI();
    }
}
