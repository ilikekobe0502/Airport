package com.whatmedia.ttia.page.main.traffic.taxi;

import com.whatmedia.ttia.newresponse.data.BaseTrafficInfoData;

public interface TaxiContract {
    interface View {
        void getTaxiSucceed(BaseTrafficInfoData response);

        void getTaxiFailed(String message, int status);
    }

    interface Presenter {
        void getTaxiAPI();
    }
}
