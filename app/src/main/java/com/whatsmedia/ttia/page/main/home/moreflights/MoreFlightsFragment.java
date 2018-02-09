package com.whatsmedia.ttia.page.main.home.moreflights;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.whatsmedia.ttia.R;
import com.whatsmedia.ttia.component.MyFlightsDetailInfo;
import com.whatsmedia.ttia.component.MyToolbar;
import com.whatsmedia.ttia.connect.NewApiConnect;
import com.whatsmedia.ttia.interfaces.IOnItemClickListener;
import com.whatsmedia.ttia.newresponse.data.FlightsListData;
import com.whatsmedia.ttia.newresponse.data.FlightsQueryData;
import com.whatsmedia.ttia.page.BaseFragment;
import com.whatsmedia.ttia.page.IActivityTools;
import com.whatsmedia.ttia.page.Page;
import com.whatsmedia.ttia.page.main.flights.my.MyFlightsInfoContract;
import com.whatsmedia.ttia.page.main.flights.result.FlightsSearchResultRecyclerViewAdapter;
import com.whatsmedia.ttia.response.data.ClockTimeData;
import com.whatsmedia.ttia.response.data.DialogContentData;
import com.whatsmedia.ttia.response.data.FlightsInfoData;
import com.whatsmedia.ttia.utility.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MoreFlightsFragment extends BaseFragment implements MoreFlightsContract.View, IOnItemClickListener {
    private static final String TAG = MoreFlightsFragment.class.getSimpleName();

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.textView_last)
    TextView mTextViewLast;
    @BindView(R.id.textView_next)
    TextView mTextViewNext;
    @BindView(R.id.imageView_up)
    ImageView mImageViewUp;
    @BindView(R.id.imageView_down)
    ImageView mImageViewDown;
    @BindView(R.id.textView_now)
    TextView mTextViewNow;

    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private MoreFlightsContract.Presenter mPresenter;

    private FlightsSearchResultRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mManager;
    private String mLastShowDate = Util.getCountDate(-1);
    private String mNowShowDate = Util.getNowDate(Util.TAG_FORMAT_MD);
    private String mNextShowDate = Util.getCountDate(1);
    private String mShowDate = mNowShowDate;
    private String mLastDate = Util.getCountDate(-1, Util.TAG_FORMAT_YMD);
    private String mNowDate = Util.getNowDate();
    private String mNextDate = Util.getCountDate(1, Util.TAG_FORMAT_YMD);
    private String mQueryDate = mNowDate;
    private int mQueryType;
    private boolean mToday = true;


    public MoreFlightsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MoreFlightsFragment newInstance() {
        MoreFlightsFragment fragment = new MoreFlightsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more_flights, container, false);
        ButterKnife.bind(this, view);

        mPresenter = new MoreFlightsPresenter(getContext(), this);

        mMainActivity.getMyToolbar().clearState()
                .setToolbarBackground(ContextCompat.getDrawable(getContext(), R.drawable.toolbar_top_bg))
                .setBackVisibility(View.VISIBLE)
                .setOnBackClickListener(new MyToolbar.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.imageView_back:
                                mMainActivity.backPress();
                                break;
                        }
                    }
                });

        if (getArguments() != null && TextUtils.equals(getArguments().getString(MoreFlightsContract.TAG_KIND), FlightsInfoData.TAG_KIND_DEPARTURE)) {
            mQueryType = FlightsQueryData.TAG_DEPARTURE_ALL;
        } else {
            mQueryType = FlightsQueryData.TAG_ARRIVE_ALL;
        }
        changeState();
        setImageState();
        mPresenter.getFlightByQueryTypeAPI(mQueryType);

        mTextViewLast.setText(mLastShowDate);
        mTextViewNow.setText(mNowShowDate);
        mTextViewNext.setText(mNextShowDate);

        mAdapter = new FlightsSearchResultRecyclerViewAdapter(getContext());
        mManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mManager);
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
    public void getFlightSucceed(final List<FlightsListData> list) {
        mLoadingView.goneLoadingView();
        if (isAdded() && !isDetached()) {
            mMainActivity.runOnUI(new Runnable() {
                @Override
                public void run() {
                    mAdapter.setData(list);
                    int position = 0;
                    boolean match = false;
                    for (int i = 0; i < list.size(); i++) {
                        if (!TextUtils.isEmpty(list.get(i).getExpressTime()) && !TextUtils.isEmpty(list.get(i).getExpressDate())) {
                            ClockTimeData data = ClockTimeData.getInstance(Util.getDifferentTimeWithNowTime(String.format("%1$s %2$s", list.get(i).getExpressDate(), list.get(i).getExpressTime()), Util.TAG_FORMAT_ALL).toString());
                            if (data.getHour() > 0 | data.getMin() > 0 | data.getSec() > 0) {
                                position = i;
                                match = true;
                                break;
                            }
                        } else {
                            Log.e(TAG, "list.get(i).getCExpressTime() error");
                        }
                    }
                    if (mToday && !match) {
                        position = list.size() - 1;
                    }
                    mManager.scrollToPositionWithOffset(position, 0);
                }
            });
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }

    @Override
    public void getFlightFailed(String message, final int status) {
        Log.d(TAG, "getFlightFailed : " + message);
        mLoadingView.goneLoadingView();
        if (isAdded() && !isDetached()) {
            mMainActivity.runOnUI(new Runnable() {
                @Override
                public void run() {
                    switch (status) {
                        case NewApiConnect.TAG_DEFAULT:
                            showMessage(getString(R.string.server_error));
                            break;
                        case NewApiConnect.TAG_TIMEOUT:
                            if (getContext() != null && isAdded() && !isDetached())
                                Util.showTimeoutDialog(getContext());
                            break;
                        case NewApiConnect.TAG_SOCKET_ERROR:
                            if (getContext() != null && isAdded() && !isDetached())
                                Util.showNetworkErrorDialog(getContext());
                            break;
                    }
                }
            });
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
//                    showMessage(!TextUtils.isEmpty(message) ? message : "");
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

            mMainActivity.runOnUI(new Runnable() {
                @Override
                public void run() {
                    switch (status) {
                        case NewApiConnect.TAG_DEFAULT:
                            showMessage(getString(R.string.server_error));
                            break;
                        case NewApiConnect.TAG_TIMEOUT:
                            if (getContext() != null && isAdded() && !isDetached())
                                Util.showTimeoutDialog(getContext());
                            break;
                        case NewApiConnect.TAG_SOCKET_ERROR:
                            if (getContext() != null && isAdded() && !isDetached())
                                Util.showNetworkErrorDialog(getContext());
                            break;
                    }
                }
            });
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }

    @OnClick({R.id.imageView_up, R.id.imageView_down, R.id.textView_last, R.id.textView_next, R.id.textView_now})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageView_up:
                mQueryType = FlightsQueryData.TAG_DEPARTURE_ALL;
                changeState();
                setImageState();
                mPresenter.getFlightByQueryTypeAPI(mQueryType);
                break;
            case R.id.imageView_down:
                mQueryType = FlightsQueryData.TAG_ARRIVE_ALL;
                changeState();
                setImageState();
                mPresenter.getFlightByQueryTypeAPI(mQueryType);
                break;
            case R.id.textView_last:
                mTextViewLast.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.date));
                mTextViewNow.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.date_off));
                mTextViewNext.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.date_off));

                mQueryDate = mLastDate;
                mShowDate = mLastShowDate;
                mToday = false;
                changeState();
                mPresenter.getFlightByDateAPI(mQueryDate);
                break;
            case R.id.textView_now:
                mTextViewLast.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.date_off));
                mTextViewNow.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.date));
                mTextViewNext.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.date_off));
                mQueryDate = mNowDate;
                mShowDate = mNowShowDate;
                mToday = true;
                changeState();
                mPresenter.getFlightByDateAPI(mQueryDate);
                break;
            case R.id.textView_next:
                mTextViewLast.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.date_off));
                mTextViewNow.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.date_off));
                mTextViewNext.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.date));

                mQueryDate = mNextDate;
                mShowDate = mNextShowDate;
                mToday = false;
                changeState();
                mPresenter.getFlightByDateAPI(mQueryDate);
                break;
            case R.id.layout_frame:
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

    private void changeState() {
        if (mQueryType == FlightsQueryData.TAG_DEPARTURE_ALL)
            mMainActivity.getMyToolbar().setTitleText(getString(R.string.tableview_header_takeoff, mShowDate));
        else
            mMainActivity.getMyToolbar().setTitleText(getString(R.string.tableview_header_arrival, mShowDate));
        mLoadingView.showLoadingView();
    }

    private void setImageState() {
        if (mQueryType == FlightsQueryData.TAG_DEPARTURE_ALL) {
            mImageViewUp.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.up_on));
            mImageViewDown.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.dow_off));
        } else {
            mImageViewUp.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.up_off));
            mImageViewDown.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.dow_on));
        }
    }

}