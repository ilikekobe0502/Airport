package com.whatsmedia.ttia.page.main.useful.language.result;


import com.whatsmedia.ttia.newresponse.data.TravelListData;

import java.util.List;

public interface TravelLanguageResultContract {
    String TAG_ID = "com.whatmedia.ttia.page.main.useful.language.result.id";
    String TAG_Name = "com.whatmedia.ttia.page.main.useful.language.result.name";

    interface View {
        void getLanguageSucceed(List<TravelListData> response);

        void getLanguageFailed(String message, int status);
    }

    interface Presenter {
        void getLanguageAPI(String id);
    }
}