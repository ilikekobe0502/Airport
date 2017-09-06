package com.whatmedia.ttia.page.main.useful.lost;


import com.whatmedia.ttia.response.data.LostAndFoundData;

import java.util.List;

public interface LostAndFoundContract {
    interface View {
        void getLostAndFoundSucceed(List<LostAndFoundData> response);

        void getLostAndFoundFailed(String message, boolean timeout);
    }

    interface Presenter {
        void getLostAndFoundAPI();
    }
}
