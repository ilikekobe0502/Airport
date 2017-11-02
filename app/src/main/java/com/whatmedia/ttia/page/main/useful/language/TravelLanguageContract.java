package com.whatmedia.ttia.page.main.useful.language;


import com.whatmedia.ttia.newresponse.data.TravelTypeListData;

import java.util.List;

public interface TravelLanguageContract {
    interface View {
        void getApiSucceed(List<TravelTypeListData> response);

        void getApiFailed(String s, boolean timeout);
    }

    interface Presenter {
        void getTypeListApi();
    }
}
