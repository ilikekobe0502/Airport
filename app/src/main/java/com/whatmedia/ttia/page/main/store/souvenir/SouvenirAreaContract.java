package com.whatmedia.ttia.page.main.store.souvenir;


import com.whatmedia.ttia.response.data.SouvenirData;

import java.util.List;

public interface SouvenirAreaContract {
    interface View {
        void querySouvenirListSuccess(List<SouvenirData> list);
        void querySouvenirListFail(String message);
    }

    interface Presenter {
        void querySouvenirList();
    }
}
