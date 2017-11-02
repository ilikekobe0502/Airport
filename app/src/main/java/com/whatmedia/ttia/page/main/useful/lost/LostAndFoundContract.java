package com.whatmedia.ttia.page.main.useful.lost;


import com.whatmedia.ttia.newresponse.data.BaseTrafficInfoData;

public interface LostAndFoundContract {
    interface View {
        void getLostAndFoundSucceed(BaseTrafficInfoData response);

        void getLostAndFoundFailed(String message, boolean timeout);
    }

    interface Presenter {
        void getLostAndFoundAPI();
    }
}
