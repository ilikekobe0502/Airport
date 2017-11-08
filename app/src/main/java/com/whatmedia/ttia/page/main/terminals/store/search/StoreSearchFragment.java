package com.whatmedia.ttia.page.main.terminals.store.search;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.component.dialog.MyStoreDialog;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;
import com.whatmedia.ttia.newresponse.data.StoreConditionCodeData;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.page.Page;
import com.whatmedia.ttia.page.main.terminals.store.result.StoreSearchResultContract;
import com.whatmedia.ttia.utility.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StoreSearchFragment extends BaseFragment implements StoreSearchContract.View {
    private static final String TAG = StoreSearchFragment.class.getSimpleName();
    @BindView(R.id.textView_subtitle)
    TextView mTextViewSubtitle;
    @BindView(R.id.layout_search)
    RelativeLayout mLayoutSearch;
    @BindView(R.id.textView_terminal)
    TextView mTextViewTerminal;
    @BindView(R.id.textView_area)
    TextView mTextViewArea;
    @BindView(R.id.textView_floor)
    TextView mTextViewFloor;
    @BindView(R.id.textView_restaurant)
    TextView mTextViewRestaurant;
    
    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private StoreSearchContract.Presenter mPresenter;

    private List<StoreConditionCodeData> mTerminalCodeList;
    private List<StoreConditionCodeData> mAreaCodeList;
    private List<StoreConditionCodeData> mFloorCodeList;
    private List<StoreConditionCodeData> mRestaurantCodeList;
    private List<StoreConditionCodeData> mStoreCodeList;

    private StoreConditionCodeData mTerminalCodeData;
    private StoreConditionCodeData mAreaCodeData;
    private StoreConditionCodeData mFloorCodeData;
    private StoreConditionCodeData mRestaurantCodeData;
    private StoreConditionCodeData mStoreCodeData;
    private int mFromPage = 0;

    public StoreSearchFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static StoreSearchFragment newInstance() {
        StoreSearchFragment fragment = new StoreSearchFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_store_search, container, false);
        ButterKnife.bind(this, view);

        if (getArguments() != null) {
            mFromPage = getArguments().getInt(StoreSearchContract.TAG_FROM_PAGE);
        }
        mPresenter =new StoreSearchPresenter(getContext(), this);
        mLoadingView.showLoadingView();
        mPresenter.getTerminalCodeAPI();


        if (mFromPage == Page.TAG_STORE_OFFERS) {
            mTextViewSubtitle.setText(getString(R.string.restaurant_store_search_subtitle));
            mTextViewRestaurant.setText(getString(R.string.restaurant_store_search_select_kind_of_store_title));
        } else {
            mTextViewSubtitle.setText(getString(R.string.restaurant_search_subtitle));
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        mMainActivity.getMyToolbar().setOnBackClickListener(null);
        super.onDestroy();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mLoadingView = (IActivityTools.ILoadingView) context;
            mMainActivity = (IActivityTools.IMainActivity) context;
        } catch (ClassCastException castException) {
            Log.e(TAG, castException.toString());
            /** The activity does not implement the listener. */
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void getTerminalSucceed(final List<StoreConditionCodeData> response) {
        if (isAdded() && !isDetached()) {
            mTerminalCodeList = response;
            StoreConditionCodeData nonSelect = new StoreConditionCodeData();
            nonSelect.setName(getString(R.string.restaurant_store_search_non_select_terminal));
            mTerminalCodeList.add(0, nonSelect);

            mPresenter.getAreaCodeAPI();
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }

    @Override
    public void getTerminalFailed(String message, boolean timeout) {
        mLoadingView.goneLoadingView();
        if (isAdded() && !isDetached()) {
            if (timeout) {
                mMainActivity.runOnUI(new Runnable() {
                    @Override
                    public void run() {
                        Util.showTimeoutDialog(getContext());
                    }
                });
            } else {
                Log.e(TAG, message);
                showMessage(message);
            }
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }

    @Override
    public void getAreaSucceed(List<StoreConditionCodeData> response) {
        if (isAdded() && !isDetached()) {
            mAreaCodeList = response;
            StoreConditionCodeData nonSelect = new StoreConditionCodeData();
            nonSelect.setName(getString(R.string.restaurant_store_search_non_select_area));
            mAreaCodeList.add(0, nonSelect);
            mPresenter.getFloorCodeAPI();
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }

    @Override
    public void getFloorSucceed(List<StoreConditionCodeData> response) {
        if (isAdded() && !isDetached()) {
            mFloorCodeList = response;
            StoreConditionCodeData nonSelect = new StoreConditionCodeData();
            nonSelect.setName(getString(R.string.restaurant_store_search_non_select_floor));
            mFloorCodeList.add(0, nonSelect);

            if (mFromPage == Page.TAG_STORE_OFFERS)
                mPresenter.getStoreCodeAPI();
            else
                mPresenter.getKindOfRestaurantCodeAPI();
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }

    @Override
    public void getKindOfRestaurantCodeSucceed(List<StoreConditionCodeData> response) {
        if (isAdded() && !isDetached()) {
            mRestaurantCodeList = response;
            StoreConditionCodeData nonSelect = new StoreConditionCodeData();
            nonSelect.setName(getString(R.string.restaurant_store_search_non_select_kind_of_restaurant));
            mRestaurantCodeList.add(0, nonSelect);
            mLoadingView.goneLoadingView();
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }

    @Override
    public void getRestaurantInfoSucceed(String response) {
        if (isAdded() && !isDetached()) {
            mLoadingView.goneLoadingView();
            if (!TextUtils.isEmpty(response)) {
                Bundle bundle = new Bundle();
                bundle.putString(StoreSearchResultContract.TAG_RESTAURANT_RESULT, response);
                mMainActivity.addFragment(Page.TAG_STORE_SEARCH_RESULT, bundle, true);
            } else {
                Log.e(TAG, "getRestaurantInfoSucceed response is null");
                showNoDataDialog();
            }
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }

    @Override
    public void getRestaurantInfoFailed(final String message, boolean timeout) {
        mLoadingView.goneLoadingView();
        if (isAdded() && !isDetached()) {
            if (timeout) {
                mMainActivity.runOnUI(new Runnable() {
                    @Override
                    public void run() {
                        Util.showTimeoutDialog(getContext());
                    }
                });
            } else {
                Log.e(TAG, "getRestaurantInfoFailed() :" + message);
                showNoDataDialog();
            }
        }
    }

    @Override
    public void getStoreCodeSuccess(List<StoreConditionCodeData> response) {
        mLoadingView.goneLoadingView();
        if (isAdded() && !isDetached()) {
            mStoreCodeList = response;
            StoreConditionCodeData nonSelect = new StoreConditionCodeData();
            nonSelect.setName(getString(R.string.restaurant_store_search_non_select_kind_of_store_title));
            mStoreCodeList.add(0, nonSelect);
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }

    @Override
    public void getStoreSuccess(String response) {
        if (isAdded() && !isDetached()) {
            if (!TextUtils.isEmpty(response)) {
                mLoadingView.goneLoadingView();
                Bundle bundle = new Bundle();
                bundle.putString(StoreSearchResultContract.TAG_STORE_RESULT, response);
                mMainActivity.addFragment(Page.TAG_STORE_SEARCH_RESULT, bundle, true);
            } else {
                Log.e(TAG, "getRestaurantInfoSucceed response is null");
                showNoDataDialog();
            }
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }

    @OnClick({R.id.layout_search, R.id.textView_terminal, R.id.textView_area, R.id.textView_floor, R.id.textView_restaurant})
    public void onClick(View view) {
        MyStoreDialog dialog = null;
        switch (view.getId()) {
            case R.id.layout_search:
                mLoadingView.showLoadingView();
                if (mFromPage == Page.TAG_STORE_OFFERS) {//商店資訊
                    mPresenter.getStoreInfoAPI(mTerminalCodeData != null ? mTerminalCodeData.getId() : "",
                            mAreaCodeData != null ? mAreaCodeData.getId() : "", mStoreCodeData != null ? mStoreCodeData.getId() : ""
                            , mFloorCodeData != null ? mFloorCodeData.getId() : "");
                } else {//餐廳資訊
                    mPresenter.getRestaurantInfoAPI(mTerminalCodeData != null ? mTerminalCodeData.getId() : "",
                            mAreaCodeData != null ? mAreaCodeData.getId() : "", mRestaurantCodeData != null ? mRestaurantCodeData.getId() : ""
                            , mFloorCodeData != null ? mFloorCodeData.getId() : "");
                }
                break;
            case R.id.textView_terminal:
                dialog = MyStoreDialog.newInstance()
                        .setTitle(getString(R.string.restaurant_store_search_select_terminal_title))
                        .setCancelClickListener(new IOnItemClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setItemClickListener(new IOnItemClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (view.getTag() != null && view.getTag() instanceof StoreConditionCodeData) {
                                    mTerminalCodeData = (StoreConditionCodeData) view.getTag();
                                    mTextViewTerminal.setText(mTerminalCodeData.getName());
                                } else {
                                    Log.e(TAG, "View.getTag() is null or data error");
                                }
                            }
                        })
                        .setTerminalCodeData(mTerminalCodeList);
                if (dialog.isAdded()) {
                    dialog.clearData().dismiss();
                } else
                    dialog.show(getActivity().getFragmentManager(), "dialog");
                break;
            case R.id.textView_area:
                dialog = MyStoreDialog.newInstance()
                        .setTitle(getString(R.string.restaurant_store_search_select_area_title))
                        .setCancelClickListener(new IOnItemClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setItemClickListener(new IOnItemClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (view.getTag() != null && view.getTag() instanceof StoreConditionCodeData) {
                                    mAreaCodeData = (StoreConditionCodeData) view.getTag();
                                    mTextViewArea.setText(mAreaCodeData.getName());
                                } else {
                                    Log.e(TAG, "View.getTag() is null or data error");
                                }
                            }
                        })
                        .setAreaCodeData(mAreaCodeList);
                if (dialog.isAdded()) {
                    dialog.clearData().dismiss();
                } else
                    dialog.show(getActivity().getFragmentManager(), "dialog");
                break;
            case R.id.textView_floor:
                dialog = MyStoreDialog.newInstance()
                        .setTitle(getString(R.string.restaurant_store_search_select_floor_title))
                        .setCancelClickListener(new IOnItemClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setItemClickListener(new IOnItemClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (view.getTag() != null && view.getTag() instanceof StoreConditionCodeData) {
                                    mFloorCodeData = (StoreConditionCodeData) view.getTag();
                                    mTextViewFloor.setText(mFloorCodeData.getName());
                                } else {
                                    Log.e(TAG, "View.getTag() is null or data error");
                                }
                            }
                        })
                        .setFloorCodeData(mFloorCodeList);
                if (dialog.isAdded()) {
                    dialog.clearData().dismiss();
                } else
                    dialog.show(getActivity().getFragmentManager(), "dialog");
                break;
            case R.id.textView_restaurant:
                if (mFromPage == Page.TAG_STORE_OFFERS) {
                    dialog = MyStoreDialog.newInstance()
                            .setTitle(getString(R.string.restaurant_store_search_select_kind_of_store_title))
                            .setCancelClickListener(new IOnItemClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            })
                            .setItemClickListener(new IOnItemClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (view.getTag() != null && view.getTag() instanceof StoreConditionCodeData) {
                                        mStoreCodeData = (StoreConditionCodeData) view.getTag();
                                        mTextViewRestaurant.setText(mStoreCodeData.getName());
                                    } else {
                                        Log.e(TAG, "View.getTag() is null or data error");
                                    }
                                }
                            })
                            .setStoreCodeData(mStoreCodeList);
                    if (dialog.isAdded()) {
                        dialog.clearData().dismiss();
                    } else
                        dialog.show(getActivity().getFragmentManager(), "dialog");
                } else {

                    dialog = MyStoreDialog.newInstance()
                            .setTitle(getString(R.string.restaurant_store_search_select_kind_of_restaurant_title))
                            .setCancelClickListener(new IOnItemClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            })
                            .setItemClickListener(new IOnItemClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (view.getTag() != null && view.getTag() instanceof StoreConditionCodeData) {
                                        mRestaurantCodeData = (StoreConditionCodeData) view.getTag();
                                        mTextViewRestaurant.setText(mRestaurantCodeData.getName());
                                    } else {
                                        Log.e(TAG, "View.getTag() is null or data error");
                                    }
                                }
                            })
                            .setRestaurantCodeData(mRestaurantCodeList);
                    if (dialog.isAdded()) {
                        dialog.clearData().dismiss();
                    } else
                        dialog.show(getActivity().getFragmentManager(), "dialog");
                }
                break;
        }
    }

    /**
     * Show no Data dialog
     */

    private void showNoDataDialog() {
        if (isAdded() && !isDetached()) {
            mMainActivity.runOnUI(new Runnable() {
                @Override
                public void run() {
                    new AlertDialog.Builder(getContext())
                            .setTitle(R.string.note)
                            .setMessage(mFromPage == Page.TAG_STORE_OFFERS ? R.string.restaurant_store_search_not_found_store : R.string.restaurant_store_search_not_found)
                            .setPositiveButton(R.string.ok, null)
                            .show();
                }
            });
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }
}
