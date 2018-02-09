package com.whatsmedia.ttia.page.main.home.arrive;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.whatsmedia.ttia.R;
import com.whatsmedia.ttia.component.MyFlightsDetailInfo;
import com.whatsmedia.ttia.interfaces.IOnItemClickListener;
import com.whatsmedia.ttia.newresponse.data.FlightsListData;
import com.whatsmedia.ttia.page.BaseFragment;
import com.whatsmedia.ttia.page.IActivityTools;
import com.whatsmedia.ttia.page.Page;
import com.whatsmedia.ttia.page.main.flights.my.MyFlightsInfoContract;
import com.whatsmedia.ttia.page.main.flights.result.FlightsSearchResultRecyclerViewAdapter;
import com.whatsmedia.ttia.response.data.DialogContentData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArriveFlightsFragment extends BaseFragment implements ArriveFlightsContract.View, IOnItemClickListener {
    private static final String TAG = ArriveFlightsFragment.class.getSimpleName();

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.loadingView)
    ProgressBar mFragmentLoadingView;

    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private ArriveFlightsContract.Presenter mPresenter;

    private FlightsSearchResultRecyclerViewAdapter mAdapter;
    private int mRetryCount = 0;
    private IOnSetCurrentPositionListener mListener;
    private IAPIErrorListener mErrorListener;


    public interface IOnSetCurrentPositionListener {
        void setCurrentPosition(int position);
    }

    public interface IAPIErrorListener {
        void errorStatus(int status);
    }

    public void setErrorListener(IAPIErrorListener listener) {
        mErrorListener = listener;
    }

    public void setListener(IOnSetCurrentPositionListener listener) {
        mListener = listener;
    }

    public ArriveFlightsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ArriveFlightsFragment newInstance() {
        ArriveFlightsFragment fragment = new ArriveFlightsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_info_arrive, container, false);
        ButterKnife.bind(this, view);

        mPresenter = new ArriveFlightsPresenter(getContext(), this);
        mFragmentLoadingView.setVisibility(View.VISIBLE);
        mPresenter.getArriveFlightAPI();

        mAdapter = new FlightsSearchResultRecyclerViewAdapter(getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setClickListener(this);
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
    public void getArriveFlightSucceed(final List<FlightsListData> list) {
        if (isAdded() && !isDetached()) {
            mRetryCount = 0;

            mMainActivity.runOnUI(new Runnable() {
                @Override
                public void run() {
                    mFragmentLoadingView.setVisibility(View.GONE);
                    mAdapter.setData(list);
                }
            });
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }

    @Override
    public void getArriveFlightFailed(String message, final int status) {
        Log.d(TAG, "getArriveFlightFailed : " + message);
        if (isAdded() && !isDetached()) {
            if (mRetryCount < 5) {
                mRetryCount++;
                mPresenter.getArriveFlightAPI();
            } else {
                mRetryCount = 0;
                if (mErrorListener != null)
                    mErrorListener.errorStatus(status);
            }
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }

    @Override
    public void saveMyFlightSucceed(final String message, final String sentJson) {

        mLoadingView.goneLoadingView();
        if (isAdded() && !isDetached()) {
            mMainActivity.runOnUI(new Runnable() {
                @Override
                public void run() {
                    mListener.setCurrentPosition(0);
                    Bundle bundle = new Bundle();
                    bundle.putString(MyFlightsInfoContract.TAG_INSERT, sentJson);
                    mMainActivity.addFragment(Page.TAG_MY_FIGHTS_INFO, bundle, true);
                }
            });
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }

    @Override
    public void saveMyFlightFailed(final String message, final int status) {

        mLoadingView.goneLoadingView();
        if (isAdded() && !isDetached()) {
            if (mErrorListener != null)
                mErrorListener.errorStatus(status);
        } else {
            Log.d(TAG, "Fragment is not add");
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_frame:
                if (mFragmentLoadingView.isShown())
                    return;
                if (view.getTag() instanceof FlightsListData) {
                    final FlightsListData tag = (FlightsListData) view.getTag();
                    mMainActivity.getFlightsDetailInfo()
                            .setTitle(getString(R.string.flight_dialog_title))
                            .setRecyclerContent(MyFlightsDetailInfo.TAG_FLIGHTS_DETAIL, DialogContentData.getFlightDetail(getContext(), tag))
                            .setClickListener(new IOnItemClickListener() {
                                @Override
                                public void onClick(View view) {
                                    switch (view.getId()) {
                                        case R.id.textView_right:
                                            if (tag != null &&
                                                    !TextUtils.isEmpty(tag.getAirlineCode()) &&
                                                    !TextUtils.isEmpty(tag.getShifts()) &&
                                                    !TextUtils.isEmpty(tag.getExpressDate()) &&
                                                    !TextUtils.isEmpty(tag.getExpressTime())) {
                                                mLoadingView.showLoadingView();
                                                mPresenter.saveMyFlightsAPI(tag);
                                            } else {
                                                Log.e(TAG, "view.getTag() content is error");
                                                showMessage(getString(R.string.data_error));
                                            }
                                            mMainActivity.getFlightsDetailInfo().setVisibility(View.GONE);
                                            break;
                                    }
                                }
                            })
                            .show();
                } else {
                    Log.e(TAG, "recycler view.getTag is error");
                    showMessage(getString(R.string.data_error));
                }
        }
    }
}