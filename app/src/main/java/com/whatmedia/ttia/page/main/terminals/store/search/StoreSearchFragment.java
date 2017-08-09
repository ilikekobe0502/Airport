package com.whatmedia.ttia.page.main.terminals.store.search;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.page.Page;
import com.whatmedia.ttia.page.main.terminals.store.result.StoreSearchResultContract;
import com.whatmedia.ttia.response.data.AreaCodeData;
import com.whatmedia.ttia.response.data.FloorCodeData;
import com.whatmedia.ttia.response.data.RestaurantCodeData;
import com.whatmedia.ttia.response.data.TerminalCodeData;

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


    private List<TerminalCodeData> mTerminalCodeList;
    private List<AreaCodeData> mAreaCodeList;
    private List<FloorCodeData> mFloorCodeList;
    private List<RestaurantCodeData> mRestaurantCodeList;

    private TerminalCodeData mTerminalCodeData;
    private AreaCodeData mAreaCodeData;
    private FloorCodeData mFloorCodeData;
    private RestaurantCodeData mRestaurantCodeData;

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

        mPresenter = StoreSearchPresenter.getInstance(getContext(), this);
        mLoadingView.showLoadingView();
        mPresenter.getTerminalAPI();

        mTextViewSubtitle.setText(getString(R.string.restaurant_store_search_subtitle));

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
    public void getTerminalSucceed(final List<TerminalCodeData> response) {
        mTerminalCodeList = response;
        TerminalCodeData nonSelect = new TerminalCodeData();
        nonSelect.setTerminalsName(getString(R.string.restaurant_store_search_non_select_terminal));
        mTerminalCodeList.add(0, nonSelect);

        mPresenter.getAreaAPI();
    }

    @Override
    public void getTerminalFailed(String message) {
        mLoadingView.goneLoadingView();
        Log.e(TAG, message);
        showMessage(message);
    }

    @Override
    public void getAreaSucceed(List<AreaCodeData> response) {
        mAreaCodeList = response;
        AreaCodeData nonSelect = new AreaCodeData();
        nonSelect.setAreaName(getString(R.string.restaurant_store_search_non_select_area));
        mAreaCodeList.add(0, nonSelect);
        mPresenter.getFloorAPI();
    }

    @Override
    public void getFloorSucceed(List<FloorCodeData> response) {
        mFloorCodeList = response;
        FloorCodeData nonSelect = new FloorCodeData();
        nonSelect.setFloorName(getString(R.string.restaurant_store_search_non_select_floor));
        mFloorCodeList.add(0, nonSelect);
        mPresenter.getKindOfRestaurantAPI();
    }

    @Override
    public void getKindOfRestaurantCodeSucceed(List<RestaurantCodeData> response) {
        mRestaurantCodeList = response;
        RestaurantCodeData nonSelect = new RestaurantCodeData();
        nonSelect.setRestaurantTypeName(getString(R.string.restaurant_store_search_non_select_kind_of_restaurant));
        mRestaurantCodeList.add(0, nonSelect);
        mLoadingView.goneLoadingView();
    }

    @Override
    public void getRestaurantInfoSucceed(String response) {
        if (!TextUtils.isEmpty(response)) {
            mLoadingView.goneLoadingView();
            Bundle bundle = new Bundle();
            bundle.putString(StoreSearchResultContract.TAG_RESULT, response);
            mMainActivity.addFragment(Page.TAG_STORE_SEARCH_RESULT, bundle, true);
        } else {
            Log.e(TAG, "getRestaurantInfoSucceed response is null");
            mMainActivity.runOnUI(new Runnable() {
                @Override
                public void run() {
                    showMessage(getString(R.string.data_not_found));
                }
            });
        }
    }

    @Override
    public void getRestaurantInfoFailed(final String message) {
        mLoadingView.goneLoadingView();
        if (isAdded()) {
            mMainActivity.runOnUI(new Runnable() {
                @Override
                public void run() {
                    showMessage(message);
                }
            });
        }
    }

    @OnClick({R.id.layout_search, R.id.textView_terminal, R.id.textView_area, R.id.textView_floor, R.id.textView_restaurant})
    public void onClick(View view) {
        MyStoreDialog dialog;
        switch (view.getId()) {
            case R.id.layout_search:
                mLoadingView.showLoadingView();
                mPresenter.getRestaurantInfoAPI(mTerminalCodeData != null ? mTerminalCodeData.getTerminalsId() : "",
                        mAreaCodeData != null ? mAreaCodeData.getAreaId() : "", mFloorCodeData != null ? mFloorCodeData.getFloorId() : ""
                        , mRestaurantCodeData != null ? mRestaurantCodeData.getRestaurantTypeId() : "");
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
                                if (view.getTag() != null && view.getTag() instanceof TerminalCodeData) {
                                    mTerminalCodeData = (TerminalCodeData) view.getTag();
                                    mTextViewTerminal.setText(mTerminalCodeData.getTerminalsName());
                                } else {
                                    Log.e(TAG, "View.getTag() is null or data error");
                                }
                            }
                        })
                        .setTerminalCodeData(mTerminalCodeList);
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
                                if (view.getTag() != null && view.getTag() instanceof AreaCodeData) {
                                    mAreaCodeData = (AreaCodeData) view.getTag();
                                    mTextViewArea.setText(mAreaCodeData.getAreaName());
                                } else {
                                    Log.e(TAG, "View.getTag() is null or data error");
                                }
                            }
                        })
                        .setAreaCodeData(mAreaCodeList);
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
                                if (view.getTag() != null && view.getTag() instanceof FloorCodeData) {
                                    mFloorCodeData = (FloorCodeData) view.getTag();
                                    mTextViewFloor.setText(mFloorCodeData.getFloorName());
                                } else {
                                    Log.e(TAG, "View.getTag() is null or data error");
                                }
                            }
                        })
                        .setFloorCodeData(mFloorCodeList);
                dialog.show(getActivity().getFragmentManager(), "dialog");
                break;
            case R.id.textView_restaurant:
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
                                if (view.getTag() != null && view.getTag() instanceof RestaurantCodeData) {
                                    mRestaurantCodeData = (RestaurantCodeData) view.getTag();
                                    mTextViewRestaurant.setText(mRestaurantCodeData.getRestaurantTypeName());
                                } else {
                                    Log.e(TAG, "View.getTag() is null or data error");
                                }
                            }
                        })
                        .setRestaurantCodeData(mRestaurantCodeList);
                dialog.show(getActivity().getFragmentManager(), "dialog");
                break;
        }
    }
}
