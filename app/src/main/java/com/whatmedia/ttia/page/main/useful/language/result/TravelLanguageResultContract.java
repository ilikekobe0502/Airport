package com.whatmedia.ttia.page.main.useful.language.result;


import com.whatmedia.ttia.response.data.LanguageData;

import java.util.List;

public interface TravelLanguageResultContract {
    interface View {
        void getLanguageSucceed(List<LanguageData> response);

        void getLanguageFailed(String message, boolean timeout);
    }

    interface Presenter {
        void getLanguageAPI(int id);
    }
}
