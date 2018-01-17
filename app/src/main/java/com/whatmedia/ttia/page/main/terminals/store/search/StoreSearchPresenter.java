package com.whatmedia.ttia.page.main.terminals.store.search;

import android.content.Context;
import android.text.TextUtils;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.connect.NewApiConnect;
import com.whatmedia.ttia.newresponse.GetAreaListResponse;
import com.whatmedia.ttia.newresponse.GetFloorListResponse;
import com.whatmedia.ttia.newresponse.GetRestaurantListResponse;
import com.whatmedia.ttia.newresponse.GetRestaurantQueryResponse;
import com.whatmedia.ttia.newresponse.GetStoreListResponse;
import com.whatmedia.ttia.newresponse.GetStoreQueryResponse;
import com.whatmedia.ttia.newresponse.GetTerminalListResponse;
import com.whatmedia.ttia.newresponse.data.RestaurantQueryData;
import com.whatmedia.ttia.newresponse.data.StoreQueryData;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class StoreSearchPresenter implements StoreSearchContract.Presenter {
    private final static String TAG = StoreSearchPresenter.class.getSimpleName();

    private NewApiConnect mNewApiConnect;
    private StoreSearchContract.View mView;
    private Context mContext;


    StoreSearchPresenter(Context context, StoreSearchContract.View view) {
        mNewApiConnect = NewApiConnect.getInstance(context);
        mView = view;
        mContext = context;
    }

    @Override
    public void getTerminalCodeAPI() {
        mNewApiConnect.getTerminalList(new NewApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, int status) {
                mView.getTerminalFailed(e.toString(), status);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {
                GetTerminalListResponse terminalListResponse = GetTerminalListResponse.getGson(response);
                if (terminalListResponse.getTerminalsList() != null)
                    mView.getTerminalSucceed(terminalListResponse.getTerminalsList());
                else
                    mView.getTerminalFailed(mContext.getString(R.string.data_error), NewApiConnect.TAG_DEFAULT);
            }
        });
    }

    @Override
    public void getAreaCodeAPI() {
        mNewApiConnect.getAreaList(new NewApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, int status) {
                mView.getTerminalFailed(e.toString(), status);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {
                GetAreaListResponse areaListResponse = GetAreaListResponse.getGson(response);
                if (areaListResponse.getAreaList() != null)
                    mView.getAreaSucceed(areaListResponse.getAreaList());
                else
                    mView.getTerminalFailed(mContext.getString(R.string.data_error),  NewApiConnect.TAG_DEFAULT);
            }
        });
    }

    @Override
    public void getFloorCodeAPI() {
        mNewApiConnect.getFloorList(new NewApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, int status) {
                mView.getTerminalFailed(e.toString(), status);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {
                GetFloorListResponse floorListResponse = GetFloorListResponse.getGson(response);
                if (floorListResponse.getFloorList() != null)
                    mView.getFloorSucceed(floorListResponse.getFloorList());
                else
                    mView.getTerminalFailed(mContext.getString(R.string.data_error),  NewApiConnect.TAG_DEFAULT);
            }
        });
    }

    @Override
    public void getKindOfRestaurantCodeAPI() {
        mNewApiConnect.getRestaurantTypeList(new NewApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, int status) {
                mView.getTerminalFailed(e.toString(), status);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {
                GetRestaurantListResponse restaurantListResponse = GetRestaurantListResponse.getGson(response);
                if (restaurantListResponse.getRestaurantList() != null)
                    mView.getKindOfRestaurantCodeSucceed(restaurantListResponse.getRestaurantList());
                else
                    mView.getTerminalFailed(mContext.getString(R.string.data_error),  NewApiConnect.TAG_DEFAULT);
            }
        });
    }

    @Override
    public void getRestaurantInfoAPI(String terminalsID, String areaID, String restaurantTypeID, String floorID) {
        RestaurantQueryData queryData = new RestaurantQueryData();
        GetRestaurantQueryResponse upload = new GetRestaurantQueryResponse();

        queryData.setTerminalsId(terminalsID);
        queryData.setAreaId(areaID);
        queryData.setRestaurantTypeId(restaurantTypeID);
        queryData.setFloorId(floorID);

        upload.setData(queryData);

        String json = upload.getJson();
        if (TextUtils.isEmpty(json)) {
            mView.getRestaurantInfoFailed(mContext.getString(R.string.data_error),  NewApiConnect.TAG_DEFAULT);
            return;
        }

        mNewApiConnect.getRestaurantInfoList(json, new NewApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, int status) {
                mView.getRestaurantInfoFailed(e.toString(), status);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {
                mView.getRestaurantInfoSucceed(response);
            }
        });
    }

    @Override
    public void getStoreCodeAPI() {
        mNewApiConnect.getStoreList(new NewApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, int status) {
                mView.getTerminalFailed(e.toString(), status);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {
                GetStoreListResponse getStoreListResponse = GetStoreListResponse.getGson(response);
                if (getStoreListResponse.getStoreTypeList() != null)
                    mView.getStoreCodeSuccess(getStoreListResponse.getStoreTypeList());
                else
                    mView.getTerminalFailed(mContext.getString(R.string.data_error),  NewApiConnect.TAG_DEFAULT);
            }
        });
    }

    @Override
    public void getStoreInfoAPI(String terminalsID, String areaID, String storeTypeID, String floorID) {
        StoreQueryData queryData = new StoreQueryData();
        GetStoreQueryResponse upload = new GetStoreQueryResponse();

        queryData.setTerminalsId(terminalsID);
        queryData.setAreaId(areaID);
        queryData.setStoreTypeId(storeTypeID);
        queryData.setFloorId(floorID);

        upload.setData(queryData);

        String json = upload.getJson();
        if (TextUtils.isEmpty(json)) {
            mView.getRestaurantInfoFailed(mContext.getString(R.string.data_error),  NewApiConnect.TAG_DEFAULT);
            return;
        }
        mNewApiConnect.getStoreInfoList(json, new NewApiConnect.MyCallback() {
            @Override
            public void onFailure(Call call, IOException e, int status) {
                mView.getRestaurantInfoFailed(e.toString(), status);
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {
                mView.getStoreSuccess(response);
            }
        });
    }
}
