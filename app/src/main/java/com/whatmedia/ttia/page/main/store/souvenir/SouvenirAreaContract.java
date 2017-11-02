package com.whatmedia.ttia.page.main.store.souvenir;


import com.whatmedia.ttia.newresponse.data.SouvenirData;

import java.util.List;

public interface SouvenirAreaContract {
    interface View {
        void querySouvenirListSuccess(List<SouvenirData> list);
        void querySouvenirListFail(String message, boolean timeout);
    }

    interface Presenter {
        void querySouvenirList();
    }
}
