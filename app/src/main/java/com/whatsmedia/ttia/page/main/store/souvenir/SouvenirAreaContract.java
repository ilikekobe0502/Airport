package com.whatsmedia.ttia.page.main.store.souvenir;


import com.whatsmedia.ttia.newresponse.data.SouvenirData;

import java.util.List;

public interface SouvenirAreaContract {
    interface View {
        void querySouvenirListSuccess(List<SouvenirData> list);
        void querySouvenirListFail(String message, int status);
    }

    interface Presenter {
        void querySouvenirList();
    }
}
