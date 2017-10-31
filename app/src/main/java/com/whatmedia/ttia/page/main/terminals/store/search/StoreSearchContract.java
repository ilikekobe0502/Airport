package com.whatmedia.ttia.page.main.terminals.store.search;

import com.whatmedia.ttia.newresponse.data.StoreConditionCodeData;
import com.whatmedia.ttia.response.data.StoreCodeData;

import java.util.List;

public interface StoreSearchContract {
    String TAG_FROM_PAGE = "com.whatmedia.ttia.page.main.terminals.store.search.from_page";

    interface View {
        void getTerminalSucceed(List<StoreConditionCodeData> response);

        void getTerminalFailed(String message, boolean timeout);

        void getAreaSucceed(List<StoreConditionCodeData> response);

        void getFloorSucceed(List<StoreConditionCodeData> response);

        void getKindOfRestaurantCodeSucceed(List<StoreConditionCodeData> response);

        void getRestaurantInfoSucceed(String response);

        void getRestaurantInfoFailed(String message, boolean timeout);

        void getStoreCodeSuccess(List<StoreCodeData> response);

        void getStoreSuccess(String response);
    }

    interface Presenter {
        void getTerminalCodeAPI();

        void getAreaCodeAPI();

        void getFloorCodeAPI();

        void getKindOfRestaurantCodeAPI();

        /**
         * 餐廳搜尋
         *
         * @param terminalsID
         * @param areaID
         * @param restaurantTypeID
         * @param floorID
         */
        void getRestaurantInfoAPI(String terminalsID, String areaID, String restaurantTypeID, String floorID);

        void getStoreCodeAPI();

        /**
         * 商店搜尋
         *
         * @param terminalsID
         * @param areaID
         * @param restaurantTypeID
         * @param floorID
         */
        void getStoreInfoAPI(String terminalsID, String areaID, String restaurantTypeID, String floorID);
    }
}
