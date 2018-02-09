package com.whatsmedia.ttia.page.main.traffic.taxi;

import com.whatsmedia.ttia.newresponse.data.BaseTrafficInfoData;

public interface TaxiContract {
    interface View {
        void getTaxiSucceed(BaseTrafficInfoData response);

        void getTaxiFailed(String message, int status);
    }

    interface Presenter {
        void getTaxiAPI();
    }
}
