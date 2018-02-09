package com.whatsmedia.ttia.page.main.useful.language;


import com.whatsmedia.ttia.newresponse.data.TravelTypeListData;

import java.util.List;

public interface TravelLanguageContract {
    interface View {
        void getApiSucceed(List<TravelTypeListData> response);

        void getApiFailed(String s, int status);
    }

    interface Presenter {
        void getTypeListApi();
    }
}
