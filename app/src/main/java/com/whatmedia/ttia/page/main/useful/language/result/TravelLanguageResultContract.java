package com.whatmedia.ttia.page.main.useful.language.result;


import com.whatmedia.ttia.newresponse.data.TravelListData;

import java.util.List;

public interface TravelLanguageResultContract {
    String TAG_ID = "com.whatmedia.ttia.page.main.useful.language.result.id";
    String TAG_Name = "com.whatmedia.ttia.page.main.useful.language.result.name";

    interface View {
        void getLanguageSucceed(List<TravelListData> response);

        void getLanguageFailed(String message, boolean timeout);
    }

    interface Presenter {
        void getLanguageAPI(String id);
    }
}
