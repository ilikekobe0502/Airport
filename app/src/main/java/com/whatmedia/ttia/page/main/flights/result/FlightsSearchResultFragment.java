package com.whatmedia.ttia.page.main.flights.result;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.component.MyFlightsDetailInfo;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;
import com.whatmedia.ttia.newresponse.GetFlightsListResponse;
import com.whatmedia.ttia.newresponse.data.FlightsListData;
import com.whatmedia.ttia.newresponse.data.FlightsQueryData;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.page.Page;
import com.whatmedia.ttia.page.main.flights.my.MyFlightsInfoContract;
import com.whatmedia.ttia.response.data.ClockTimeData;
import com.whatmedia.ttia.response.data.DialogContentData;
import com.whatmedia.ttia.utility.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FlightsSearchResultFragment extends BaseFragment implements FlightsSearchResultContract.View, IOnItemClickListener {
    private static final String TAG = FlightsSearchResultFragment.class.getSimpleName();

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.imageView_up)
    ImageView mImageViewUp;
    @BindView(R.id.imageView_down)
    ImageView mImageViewDown;
    @BindView(R.id.textView_last)
    TextView mTextViewLast;
    @BindView(R.id.textView_next)
    TextView mTextViewNext;
    @BindView(R.id.textView_now)
    TextView mTextViewNow;

    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private FlightsSearchResultContract.Presenter mPresenter;

    private FlightsSearchResultRecyclerViewAdapter mAdapter;
    private List<FlightsListData> mDepartureList;
    private List<FlightsListData> mFilterData = new ArrayList<>();

    private String mLastShowDate = Util.getCountDate(-1);
    private String mNowShowDate = Util.getNowDate(Util.TAG_FORMAT_MD);
    private String mNextShowDate = Util.getCountDate(1);
    private String mShowDate = mNowShowDate;
    private String mLastDate = Util.getCountDate(-1, Util.TAG_FORMAT_YMD);
    private String mNowDate = Util.getNowDate();
    private String mNextDate = Util.getCountDate(1, Util.TAG_FORMAT_YMD);
    private String mQueryDate = mNowDate;
    private int mQueryType = FlightsQueryData.TAG_DEPARTURE_ALL;
    private String mKeyWorld = "";
    private boolean mToday = true;
    private LinearLayoutManager mManager;


    public FlightsSearchResultFragment() {
        // Required empty public constructor
    }

    public static FlightsSearchResultFragment newInstance() {
        FlightsSearchResultFragment fragment = new FlightsSearchResultFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_flights_search_result, container, false);
        ButterKnife.bind(this, view);

        mPresenter = new FlightsSearchResultPresenter(getContext(), this);

        if (getArguments() != null && !TextUtils.isEmpty(getArguments().getString(FlightsSearchResultContract.TAG_DEPARTURE_FLIGHTS))
                && !TextUtils.isEmpty(getArguments().getString(FlightsSearchResultContract.TAG_KEY_WORLD))) {
            mDepartureList = GetFlightsListResponse.getGson(getArguments().getString(FlightsSearchResultContract.TAG_DEPARTURE_FLIGHTS)).getFlightList();
            mKeyWorld = getArguments().getString(FlightsSearchResultContract.TAG_KEY_WORLD).toLowerCase();
        } else {
            Log.e(TAG, "data error");
            showMessage(getString(R.string.data_error));
        }

        mTextViewLast.setText(mLastShowDate);
        mTextViewNow.setText(mNowShowDate);
        mTextViewNext.setText(mNextShowDate);
        mFilterData.clear();
        if (mDepartureList != null && mDepartureList.size() > 0) {
            for (FlightsListData item : mDepartureList) {
                if (item.getExpressDate().contains(mNowShowDate)) {
                    mFilterData.add(item);
                }
            }
            mAdapter = new FlightsSearchResultRecyclerViewAdapter(getContext(), mFilterData);
            mManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(mManager);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.setClickListener(this);

            goToCurrentPosition(mFilterData);
        } else {
            new AlertDialog.Builder(getContext())
                    .setMessage(R.string.flights_search_not_found_flights_message)
                    .setPositiveButton(R.string.alert_btn_cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
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
    public void onSaveInstanceState(Bundle outState) {
        getArguments().putString(FlightsSearchResultContract.TAG_DEPARTURE_FLIGHTS, "");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @OnClick({R.id.imageView_up, R.id.imageView_down, R.id.textView_last, R.id.textView_next, R.id.textView_now})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageView_up:
                mQueryType = FlightsQueryData.TAG_DEPARTURE_ALL;
                mImageViewUp.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.up_on));
                mImageViewDown.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.dow_off));

                changeState();

                mPresenter.getFlightByQueryTypeAPI(mQueryType);
                break;
            case R.id.imageView_down:
                mQueryType = FlightsQueryData.TAG_ARRIVE_ALL;
                mImageViewUp.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.up_off));
                mImageViewDown.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.dow_on));

                changeState();

                mPresenter.getFlightByQueryTypeAPI(mQueryType);
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
    public void saveMyFlightFailed(final String message, boolean timeout) {
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
                mMainActivity.runOnUI(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(TAG, message);
                        showMessage(message);
                    }
                });
            }
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }

    @Override
    public void getFlightSucceed(final List<FlightsListData> list) {

        mLoadingView.goneLoadingView();
        if (isAdded() && !isDetached()) {
            mFilterData.clear();
            for (FlightsListData item : list) {
                if (item.getAirlineName().contains(mKeyWorld) || item.getAirlineCode().toLowerCase().contains(mKeyWorld)
                        || item.getContactsLocation().toLowerCase().contains(mKeyWorld) || item.getContactsLocationEng().toLowerCase().contains(mKeyWorld) || item.getContactsLocationChinese().contains(mKeyWorld)) {
                    if (mToday) {
                        if (item.getExpressDate().contains(mNowShowDate)) {
                            mFilterData.add(item);
                        }
                    } else {
                        mFilterData.add(item);
                    }
                }
            }
            mMainActivity.runOnUI(new Runnable() {
                @Override
                public void run() {
                    mAdapter.setData(mFilterData);
                    goToCurrentPosition(mFilterData);
                }
            });
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }

    @Override
    public void getFlightFailed(String message, boolean timeout) {

        Log.d(TAG, "getFlightFailed : " + message);
        mLoadingView.goneLoadingView();
        if (timeout) {
            mMainActivity.runOnUI(new Runnable() {
                @Override
                public void run() {
                    Util.showTimeoutDialog(getContext());
                }
            });
        } else {
            if (isAdded() && !isDetached()) {
                mMainActivity.runOnUI(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialog.Builder(getContext())
                                .setTitle(R.string.note)
                                .setMessage(R.string.data_not_found)
                                .setPositiveButton(R.string.ok, null)
                                .show();
                        mAdapter.setData(null);
                    }
                });
            } else {
                Log.d(TAG, "Fragment is not add");
            }
        }
    }

    @Override
    public String getKeyword() {
        return !TextUtils.isEmpty(mKeyWorld) ? mKeyWorld : "";
    }

    /**
     * 更改Title bar
     */
    private void changeState() {
        if (mQueryType == FlightsQueryData.TAG_DEPARTURE_ALL)
            mMainActivity.getMyToolbar().setTitleText(getString(R.string.tableview_header_takeoff, mShowDate));
        else
            mMainActivity.getMyToolbar().setTitleText(getString(R.string.tableview_header_arrival, mShowDate));
        mLoadingView.showLoadingView();
    }

    /**
     * 移到最相近時間的item
     *
     * @param list
     */
    private void goToCurrentPosition(List<FlightsListData> list) {
        if (list != null) {
            int position = 0;
            boolean match = false;
            for (int i = 0; i < list.size(); i++) {
                if (!TextUtils.isEmpty(list.get(i).getExpressTime())) {
                    ClockTimeData data = ClockTimeData.getInstance(Util.getDifferentTimeWithNowTime(String.format("%1$s %2$s", list.get(i).getExpressDate(), list.get(i).getExpressTime()), Util.TAG_FORMAT_ALL).toString());
                    if (data.getHour() >= 0 && data.getMin() >= 0 && data.getSec() >= 0) {
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
    }
}
