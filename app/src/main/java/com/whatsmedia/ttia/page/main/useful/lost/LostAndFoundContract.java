package com.whatsmedia.ttia.page.main.useful.lost;


import com.whatsmedia.ttia.newresponse.data.BaseTrafficInfoData;

public interface LostAndFoundContract {
    interface View {
        void getLostAndFoundSucceed(BaseTrafficInfoData response);

        void getLostAndFoundFailed(String message, int status);
    }

    interface Presenter {
        void getLostAndFoundAPI();
    }
}
