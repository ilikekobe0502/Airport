package com.whatsmedia.ttia.page.main.home;

import com.whatsmedia.ttia.newresponse.GetLanguageListResponse;

/**
 * Created by neo_mac on 2017/8/3.
 */

public interface HomeContract {
    String TAG_TYPE = "com.whatmedia.ttia.page.main.home.type";

    interface View {
        void getLanguageListSuccess(GetLanguageListResponse response);

        void getLanguageListFailed(String e, int status);

        void goToNotificationPage(String type);
    }

    interface Presenter {

        void getLanguageList();
    }
}
