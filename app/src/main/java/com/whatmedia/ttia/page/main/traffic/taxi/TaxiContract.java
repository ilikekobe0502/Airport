package com.whatmedia.ttia.page.main.traffic.taxi;

import com.whatmedia.ttia.response.data.TaxiData;

import java.util.List;

public interface TaxiContract {
    interface View {
        void getTaxiSucceed(List<TaxiData> response);

        void getTaxiFailed(String message);
    }

    interface Presenter {
        void getTaxiAPI();
    }
}
