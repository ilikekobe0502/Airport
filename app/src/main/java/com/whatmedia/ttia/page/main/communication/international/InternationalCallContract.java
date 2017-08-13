package com.whatmedia.ttia.page.main.communication.international;


import com.whatmedia.ttia.response.data.InternationCallData;

import java.util.List;

public interface InternationalCallContract {
    interface View {
        void getInternationalCallSucceed(List<InternationCallData> response);

        void getInternationalCallFailed(String message);
    }

    interface Presenter {
        void getInternationalCallAPI();
    }
}
