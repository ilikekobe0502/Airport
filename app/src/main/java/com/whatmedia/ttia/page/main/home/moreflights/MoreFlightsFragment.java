package com.whatmedia.ttia.page.main.home.moreflights;

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

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.component.MyToolbar;
import com.whatmedia.ttia.component.dialog.MyDialog;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.page.Page;
import com.whatmedia.ttia.page.main.flights.my.MyFlightsInfoContract;
import com.whatmedia.ttia.page.main.flights.result.FlightsSearchResultRecyclerViewAdapter;
import com.whatmedia.ttia.response.data.DialogContentData;
import com.whatmedia.ttia.response.data.FlightSearchData;
import com.whatmedia.ttia.response.data.FlightsInfoData;
import com.whatmedia.ttia.utility.Util;

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
    private String mLastShowDate = Util.getCountDate(-1);
    private String mNowShowDate = Util.getNowDate(Util.TAG_FORMAT_MD);
    private String mNextShowDate = Util.getCountDate(1);
    private String mShowDate = mNowShowDate;
    private String mLastDate = Util.getCountDate(-1, Util.TAG_FORMAT_YMD);
    private String mNowDate = Util.getNowDate();
    private String mNextDate = Util.getCountDate(1, Util.TAG_FORMAT_YMD);
    private String mQueryDate = mNowDate;
    private String mQueryType;


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

        mPresenter = MoreFlightsPresenter.getInstance(getContext(), this);

        mMainActivity.getMyToolbar().clearState()
                .setBackground(ContextCompat.getColor(getContext(), R.color.colorSubTitle))
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
            mQueryType = FlightsInfoData.TAG_KIND_DEPARTURE;
            getFlight();
            setImageState(mQueryType);
        } else {
            mQueryType = FlightsInfoData.TAG_KIND_ARRIVE;
            getFlight();
            setImageState(mQueryType);
        }
        mTextViewLast.setText(mLastShowDate);
        mTextViewNow.setText(mNowShowDate);
        mTextViewNext.setText(mNextShowDate);

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
    public void getFlightSucceed(final List<FlightsInfoData> list) {
        mLoadingView.goneLoadingView();
        mMainActivity.runOnUI(new Runnable() {
            @Override
            public void run() {
                mAdapter.setData(list);
            }
        });
    }

    @Override
    public void getFlightFailed(String message) {
        Log.d(TAG, "getFlightFailed : " + message);
        mLoadingView.goneLoadingView();
        mMainActivity.runOnUI(new Runnable() {
            @Override
            public void run() {
                showMessage(getString(R.string.server_error));
            }
        });
    }

    @Override
    public void saveMyFlightSucceed(final String message) {

        mLoadingView.goneLoadingView();
        mMainActivity.runOnUI(new Runnable() {
            @Override
            public void run() {
                showMessage(!TextUtils.isEmpty(message) ? message : "");
                Bundle bundle = new Bundle();
                bundle.putBoolean(MyFlightsInfoContract.TAG_INSERT, true);
                mMainActivity.addFragment(Page.TAG_MY_FIGHTS_INFO, bundle, true);
            }
        });
    }

    @Override
    public void saveMyFlightFailed(final String message) {

        mLoadingView.goneLoadingView();
        mMainActivity.runOnUI(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, message);
                showMessage(message);
            }
        });

    }

    @OnClick({R.id.imageView_up, R.id.imageView_down, R.id.textView_last, R.id.textView_next, R.id.textView_now})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageView_up:
                mQueryType = FlightsInfoData.TAG_KIND_DEPARTURE;
                getFlight();
                setImageState(mQueryType);
                break;
            case R.id.imageView_down:
                mQueryType = FlightsInfoData.TAG_KIND_ARRIVE;
                getFlight();
                setImageState(mQueryType);
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

    private void setImageState(String kind) {
        if (TextUtils.equals(kind, FlightsInfoData.TAG_KIND_DEPARTURE)) {
            mImageViewUp.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.up_on));
            mImageViewDown.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.dow_off));
        } else {
            mImageViewUp.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.up_off));
            mImageViewDown.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.dow_on));
        }
    }
}
