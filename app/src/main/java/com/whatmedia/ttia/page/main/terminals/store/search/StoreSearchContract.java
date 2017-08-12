package com.whatmedia.ttia.page.main.terminals.store.search;

import com.whatmedia.ttia.response.data.AreaCodeData;
import com.whatmedia.ttia.response.data.FloorCodeData;
import com.whatmedia.ttia.response.data.RestaurantCodeData;
import com.whatmedia.ttia.response.data.TerminalCodeData;

import java.util.List;

public interface StoreSearchContract {
    String TAG_FROM_PAGE = "com.whatmedia.ttia.page.main.terminals.store.search.from_page";

    interface View {
        void getTerminalSucceed(List<TerminalCodeData> response);

        void getTerminalFailed(String message);

        void getAreaSucceed(List<AreaCodeData> response);

        void getFloorSucceed(List<FloorCodeData> response);

        void getKindOfRestaurantCodeSucceed(List<RestaurantCodeData> response);

        void getRestaurantInfoSucceed(String response);

        void getRestaurantInfoFailed(String message);
    }

    interface Presenter {
        void getTerminalAPI();

        void getAreaAPI();

        void getFloorAPI();

        void getKindOfRestaurantAPI();

        void getRestaurantInfoAPI(String terminalsID, String areaID, String restaurantTypeID, String floorID);
    }
}
