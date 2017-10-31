package com.whatmedia.ttia.page.main.home;

import com.whatmedia.ttia.newresponse.GetLanguageListResponse;

/**
 * Created by neo_mac on 2017/8/3.
 */

public interface HomeContract {
    interface View {
        void getLanguageListSuccess(GetLanguageListResponse response);

        void getLanguageListFailed(String e, boolean timeout);
    }

    interface Presenter {

        void getLanguageList();
    }
}
