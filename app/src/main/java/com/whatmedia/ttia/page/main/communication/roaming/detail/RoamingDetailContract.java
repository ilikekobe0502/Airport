package com.whatmedia.ttia.page.main.communication.roaming.detail;



public interface RoamingDetailContract {
    interface View {
        void getRoamingDetailSucceed(String url,String image);

        void getRoamingDetailFailed(String message, boolean timeout);
    }

    interface Presenter {
        void getRoamingDetailAPI(String id);
    }
}
