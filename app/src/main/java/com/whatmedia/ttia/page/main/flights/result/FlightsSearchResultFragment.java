package com.whatmedia.ttia.page.main.flights.result;

import android.content.Context;
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
import com.whatmedia.ttia.component.dialog.MyDialog;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.page.Page;
import com.whatmedia.ttia.page.main.flights.my.MyFlightsInfoContract;
import com.whatmedia.ttia.response.data.ClockTimeData;
import com.whatmedia.ttia.response.data.DialogContentData;
import com.whatmedia.ttia.response.data.FlightSearchData;
import com.whatmedia.ttia.response.data.FlightsInfoData;
import com.whatmedia.ttia.response.GetFlightsInfoResponse;
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
    private List<FlightsInfoData> mDepartureList;
    private List<FlightsInfoData> mArriveList;
    private List<FlightsInfoData> mFilterData = new ArrayList<>();

    private String mLastShowDate = Util.getCountDate(-1);
    private String mNowShowDate = Util.getNowDate(Util.TAG_FORMAT_MD);
    private String mNextShowDate = Util.getCountDate(1);
    private String mShowDate = mNowShowDate;
    private String mLastDate = Util.getCountDate(-1, Util.TAG_FORMAT_YMD);
    private String mNowDate = Util.getNowDate();
    private String mNextDate = Util.getCountDate(1, Util.TAG_FORMAT_YMD);
    private String mQueryDate = mNowDate;
    private String mQueryType = FlightsInfoData.TAG_KIND_DEPARTURE;
    private String mKeyWorld = "";
    private LinearLayoutManager mManager;


    public FlightsSearchResultFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
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

        mPresenter = FlightsSearchResultPresenter.getInstance(getContext(), this);

        if (getArguments() != null && !TextUtils.isEmpty(getArguments().getString(FlightsSearchResultContract.TAG_ARRIVE_FLIGHTS))
                && !TextUtils.isEmpty(getArguments().getString(FlightsSearchResultContract.TAG_DEPARTURE_FLIGHTS))
                && !TextUtils.isEmpty(getArguments().getString(FlightsSearchResultContract.TAG_KEY_WORLD))) {
            mArriveList = GetFlightsInfoResponse.newInstance(getArguments().getString(FlightsSearchResultContract.TAG_ARRIVE_FLIGHTS));
            mDepartureList = GetFlightsInfoResponse.newInstance(getArguments().getString(FlightsSearchResultContract.TAG_DEPARTURE_FLIGHTS));
            mKeyWorld = getArguments().getString(FlightsSearchResultContract.TAG_KEY_WORLD);

//            if (mArriveList.size() > 0)
//                mArriveDate = !TextUtils.isEmpty(mArriveList.get(0).getExpressDate()) ? mArriveList.get(0).getExpressDate() : "";
//            if (mDepartureList.size() > 0)
//                mDepartureDate = !TextUtils.isEmpty(mDepartureList.get(0).getExpressDate()) ? mDepartureList.get(0).getExpressDate() : "";
        } else {
            Log.e(TAG, "data error");
            showMessage(getString(R.string.data_error));
        }

        mTextViewLast.setText(mLastShowDate);
        mTextViewNow.setText(mNowShowDate);
        mTextViewNext.setText(mNextShowDate);
        mAdapter = new FlightsSearchResultRecyclerViewAdapter(getContext(), mDepartureList);
        mManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setClickListener(this);

        goToCurrentPosition(mDepartureList);
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

    @OnClick({R.id.imageView_up, R.id.imageView_down, R.id.textView_last, R.id.textView_next, R.id.textView_now})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageView_up:
                mQueryType = FlightsInfoData.TAG_KIND_DEPARTURE;
                mImageViewUp.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.up_on));
                mImageViewDown.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.dow_off));
//                mAdapter.setData(mDepartureList);
                getFlight();
                break;
            case R.id.imageView_down:
                mQueryType = FlightsInfoData.TAG_KIND_ARRIVE;
                mImageViewUp.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.up_off));
                mImageViewDown.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.dow_on));
//                mAdapter.setData(mArriveList);
                getFlight();
                break;
            case R.id.layout_frame:
                if (view.getTag() instanceof FlightsInfoData) {
                    final FlightsInfoData tag = (FlightsInfoData) view.getTag();

                    final MyDialog myDialog = MyDialog.newInstance()
                            .setTitle(getString(R.string.flight_dialog_title))
                            .setRecyclerContent(DialogContentData.getFlightDetail(getContext(), tag))
                            .setRightClickListener(new IOnItemClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (tag != null) {
//                                        FlightsInfoData data = new FlightsInfoData();
                                        if (!TextUtils.isEmpty(tag.getAirlineCode()) && !TextUtils.isEmpty(tag.getShift()) && !TextUtils.isEmpty(tag.getExpressDate()) && !TextUtils.isEmpty(tag.getExpressTime())) {
                                            mLoadingView.showLoadingView();
                                            tag.setAirlineCode(tag.getAirlineCode());
                                            if (tag.getShift().length() == 2) {
                                                tag.setShift("  " + tag.getShift());
                                            } else if (tag.getShift().length() == 3) {
                                                tag.setShift(" " + tag.getShift());
                                            }
                                            tag.setShift(tag.getShift());
                                            tag.setExpressDate(tag.getExpressDate());
                                            tag.setExpressTime(tag.getExpressTime());
                                            tag.setType("0");
                                            mPresenter.saveMyFlightsAPI(tag);
                                        } else {
                                            Log.e(TAG, "view.getTag() content is error");
                                            showMessage(getString(R.string.data_error));
                                        }
                                    } else {
                                        Log.e(TAG, "view.getTag() is null");
                                        showMessage(getString(R.string.data_error));
                                    }
                                }
                            });
                    myDialog.show(getActivity().getFragmentManager(), "dialog");
                } else {
                    Log.e(TAG, "recycler view.getTag is error");
                    showMessage(getString(R.string.data_error));
                }
                break;
            case R.id.textView_last:
                mTextViewLast.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.date_bg01));
                mTextViewLast.setTextColor(ContextCompat.getColor(getContext(), android.R.color.black));
                mTextViewNow.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.date_bg));
                mTextViewNow.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
                mTextViewNext.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.date_bg));
                mTextViewNext.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));

                mQueryDate = mLastDate;
                mShowDate = mLastShowDate;
                getFlight();
                break;
            case R.id.textView_now:
                mTextViewLast.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.date_bg));
                mTextViewLast.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
                mTextViewNow.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.date_bg01));
                mTextViewNow.setTextColor(ContextCompat.getColor(getContext(), android.R.color.black));
                mTextViewNext.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.date_bg));
                mTextViewNext.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
                mQueryDate = mNowDate;
                mShowDate = mNowShowDate;
                getFlight();
                break;
            case R.id.textView_next:
                mTextViewLast.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.date_bg));
                mTextViewLast.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
                mTextViewNow.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.date_bg));
                mTextViewNow.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
                mTextViewNext.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.date_bg01));
                mTextViewNext.setTextColor(ContextCompat.getColor(getContext(), android.R.color.black));

                mQueryDate = mNextDate;
                mShowDate = mNextShowDate;
                getFlight();
                break;
        }
    }


    @Override
    public void saveMyFlightSucceed(final String message) {
        mLoadingView.goneLoadingView();
        if (isAdded() && !isDetached()) {
            mMainActivity.runOnUI(new Runnable() {
                @Override
                public void run() {
                    showMessage(!TextUtils.isEmpty(message) ? message : "");
                    Bundle bundle = new Bundle();
                    bundle.putBoolean(MyFlightsInfoContract.TAG_INSERT, true);
                    mMainActivity.addFragment(Page.TAG_MY_FIGHTS_INFO, bundle, true);
                }
            });
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }

    @Override
    public void saveMyFlightFailed(final String message) {
        mLoadingView.goneLoadingView();
        if (isAdded() && !isDetached()) {
            mMainActivity.runOnUI(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, message);
                    showMessage(message);
                }
            });
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }

    @Override
    public void getFlightSucceed(final List<FlightsInfoData> list) {

        mLoadingView.goneLoadingView();
        if (isAdded() && !isDetached()) {
            mFilterData.clear();
            for (FlightsInfoData item : list) {
                if (item.getAirLineCName().contains(mKeyWorld) || item.getAirlineCode().contains(mKeyWorld)
                        || item.getContactsLocation().contains(mKeyWorld) || item.getContactsLocationEng().contains(mKeyWorld) || item.getContactsLocationChinese().contains(mKeyWorld)
                        || item.getFlightCode().contains(mKeyWorld) || item.getCTName().contains(mKeyWorld) || item.getCSName().contains(mKeyWorld) || item.getJName().contains(mKeyWorld)
                        || item.getEName().contains(mKeyWorld)) {
                    mFilterData.add(item);
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
    public void getFlightFailed(String message) {

        Log.d(TAG, "getFlightFailed : " + message);
        mLoadingView.goneLoadingView();
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

    private void getFlight() {
        if (TextUtils.equals(mQueryType, FlightsInfoData.TAG_KIND_DEPARTURE))
            mMainActivity.getMyToolbar().setTitleText(getString(R.string.tableview_header_takeoff, mShowDate));
        else
            mMainActivity.getMyToolbar().setTitleText(getString(R.string.tableview_header_arrival, mShowDate));

        FlightSearchData data = new FlightSearchData();
        data.setQueryType(mQueryType);
        data.setExpressTime(mQueryDate);
        mLoadingView.showLoadingView();
        mPresenter.getFlightAPI(data);
    }

    private void goToCurrentPosition(List<FlightsInfoData> list) {
        if (list != null) {
            int position = 0;
            for (int i = 0; i < list.size(); i++) {
                if (!TextUtils.isEmpty(list.get(i).getCExpressTime())) {
                    ClockTimeData data = ClockTimeData.getInstance(Util.getDifferentTimeWithNowTime(list.get(i).getCExpressTime(), Util.TAG_FORMAT_ALL).toString());
                    if (data.getHour() > 0 | data.getMin() > 0 | data.getSec() > 0) {
                        position = i;
                        break;
                    }
                } else {
                    Log.e(TAG, "list.get(i).getCExpressTime() error");
                }
            }
            mManager.scrollToPositionWithOffset(position, 0);
        }
    }
}
