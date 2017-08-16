package com.whatmedia.ttia.page.main.terminals.store.search;

import android.content.Context;
import android.text.TextUtils;

import com.whatmedia.ttia.connect.ApiConnect;
import com.whatmedia.ttia.response.GetAreaCodeResponse;
import com.whatmedia.ttia.response.GetFloorCodeResponse;
import com.whatmedia.ttia.response.GetRestaurantCodeResponse;
import com.whatmedia.ttia.response.GetTerminalCodeResponse;
import com.whatmedia.ttia.response.data.AreaCodeData;
import com.whatmedia.ttia.response.data.FloorCodeData;
import com.whatmedia.ttia.response.GetStoreCodeResponse;
import com.whatmedia.ttia.response.data.RestaurantCodeData;
import com.whatmedia.ttia.response.data.StoreCodeData;
import com.whatmedia.ttia.response.data.TerminalCodeData;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by neo_mac on 2017/6/17.
 */

public class StoreSearchPresenter implements StoreSearchContract.Presenter {
    private final static String TAG = StoreSearchPresenter.class.getSimpleName();

    private static StoreSearchPresenter mStoreSearchPresenter;
    private static ApiConnect mApiConnect;
    private static StoreSearchContract.View mView;


    public static StoreSearchPresenter getInstance(Context context, StoreSearchContract.View view) {
        mStoreSearchPresenter = new StoreSearchPresenter();
        mApiConnect = ApiConnect.getInstance(context);
        mView = view;
        return mStoreSearchPresenter;
    }

    @Override
    public void getTerminalCodeAPI() {
        mApiConnect.getTerminalCode(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mView.getTerminalFailed(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    String result = response.body().string();
                    List<TerminalCodeData> list = GetTerminalCodeResponse.newInstance(result);
                    mView.getTerminalSucceed(list);
                } else {
                    mView.getTerminalFailed(!TextUtils.isEmpty(response.message()) ? response.message() : "");
                }
            }
        });
    }

    @Override
    public void getAreaCodeAPI() {
        mApiConnect.getAreaCode(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mView.getTerminalFailed(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    String result = response.body().string();
                    List<AreaCodeData> list = GetAreaCodeResponse.newInstance(result);
                    mView.getAreaSucceed(list);
                } else {
                    mView.getTerminalFailed(!TextUtils.isEmpty(response.message()) ? response.message() : "");
                }
            }
        });
    }

    @Override
    public void getFloorCodeAPI() {
        mApiConnect.getFloorCode(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mView.getTerminalFailed(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    String result = response.body().string();
                    List<FloorCodeData> list = GetFloorCodeResponse.newInstance(result);
                    mView.getFloorSucceed(list);
                } else {
                    mView.getTerminalFailed(!TextUtils.isEmpty(response.message()) ? response.message() : "");
                }

            }
        });
    }

    @Override
    public void getKindOfRestaurantCodeAPI() {
        mApiConnect.getRestaurantCode(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mView.getTerminalFailed(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    String result = response.body().string();
                    List<RestaurantCodeData> list = GetRestaurantCodeResponse.newInstance(result);
                    mView.getKindOfRestaurantCodeSucceed(list);
                } else {
                    mView.getTerminalFailed(!TextUtils.isEmpty(response.message()) ? response.message() : "");
                }
            }
        });
    }

    @Override
    public void getRestaurantInfoAPI(String terminalsID, String areaID, String restaurantTypeID, String floorID) {
        mApiConnect.getRestaurantInfo(terminalsID, areaID, restaurantTypeID, floorID, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mView.getRestaurantInfoFailed(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    String result = response.body().string();
                    mView.getRestaurantInfoSucceed(result);
                } else {
                    mView.getRestaurantInfoFailed(!TextUtils.isEmpty(response.message()) ? response.message() : "");
                }
            }
        });
    }

    @Override
    public void getStoreCodeAPI() {
        mApiConnect.getStoreCode(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mView.getTerminalFailed(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    String result = response.body().string();
                    List<StoreCodeData> list = GetStoreCodeResponse.newInstance(result);
                    mView.getStoreCodeSuccess(list);
                } else {
                    mView.getTerminalFailed(!TextUtils.isEmpty(response.message()) ? response.message() : "");
                }
            }
        });
    }

    @Override
    public void getStoreInfoAPI(String terminalsID, String areaID, String storeTypeID, String floorID) {
        mApiConnect.getStoreInfo(terminalsID, areaID, storeTypeID, floorID, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mView.getRestaurantInfoFailed(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    String result = response.body().string();
                    mView.getStoreSuccess(result);
                } else {
                    mView.getRestaurantInfoFailed(!TextUtils.isEmpty(response.message()) ? response.message() : "");
                }
            }
        });
    }
}
