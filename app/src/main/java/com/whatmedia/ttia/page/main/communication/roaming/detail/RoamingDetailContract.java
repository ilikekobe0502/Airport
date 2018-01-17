package com.whatmedia.ttia.page.main.communication.roaming.detail;



public interface RoamingDetailContract {
    interface View {
        void getRoamingDetailSucceed(String url,String image,String detailImage);

        void getRoamingDetailFailed(String message, int status);
    }

    interface Presenter {
        void getRoamingDetailAPI(String id);
    }
}
