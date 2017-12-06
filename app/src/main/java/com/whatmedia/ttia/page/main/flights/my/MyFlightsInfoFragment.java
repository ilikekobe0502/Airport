package com.whatmedia.ttia.page.main.flights.my;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.whatmedia.ttia.R;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;
import com.whatmedia.ttia.newresponse.GetFlightsListResponse;
import com.whatmedia.ttia.newresponse.data.FlightsListData;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.page.main.flights.notify.MyFlightsNotifyContract;
import com.whatmedia.ttia.response.data.ClockTimeData;
import com.whatmedia.ttia.response.data.DialogContentData;
import com.whatmedia.ttia.services.FlightClockBroadcast;
import com.whatmedia.ttia.utility.Preferences;
import com.whatmedia.ttia.utility.Util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyFlightsInfoFragment extends BaseFragment implements MyFlightsInfoContract.View, IOnItemClickListener {
    private static final String TAG = MyFlightsInfoFragment.class.getSimpleName();
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.textView_cancel)
    TextView mTextViewCancel;
    @BindView(R.id.textView_ok)
    TextView mTextViewOk;
    @BindView(R.id.textView_select_title)
    TextView mTextViewSelectTitle;
    @BindView(R.id.number_picker_left)
    NumberPicker mNumberPickerLeft;
    @BindView(R.id.number_picker_right)
    NumberPicker mNumberPickerRight;
    @BindView(R.id.layout_selector)
    RelativeLayout mLayoutSelector;

    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private MyFlightsInfoContract.Presenter mPresenter;
    private List<FlightsListData> mSelectList = new ArrayList<>();
    private boolean mIsInsert;//是否從新增過來，更新notification 佇列
    private List<FlightsListData> mResponse;//Server原始檔案

    private MyFlightsInfoRecyclerViewAdapter mAdapter;

    private int mSelectPosition;//被點選的item position
    private String[] mHours;
    private String[] mMinutes;
    private String mHour = "00";//被選取的小時
    private String mMinute = "00";//被選取的分

    public MyFlightsInfoFragment() {
        // Required empty public constructor
    }

    public static MyFlightsInfoFragment newInstance() {
        MyFlightsInfoFragment fragment = new MyFlightsInfoFragment();
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
        View view = inflater.inflate(R.layout.fragment_my_flights_info, container, false);
        ButterKnife.bind(this, view);

        if (getArguments() != null) {
            mIsInsert = getArguments().getBoolean(MyFlightsInfoContract.TAG_INSERT);
        }
        mPresenter = new MyFlightsInfoPresenter(getContext(), this);
        mLoadingView.showLoadingView();
        mPresenter.getMyFlightsInfoAPI();

        mAdapter = new MyFlightsInfoRecyclerViewAdapter(getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setClickListener(this);

        mHours = getResources().getStringArray(R.array.hours_array);
        mMinutes = getResources().getStringArray(R.array.minutes_array);
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
    public void getMyFlightsInfoSucceed(final List<FlightsListData> response) {

        if (isAdded() && !isDetached()) {
            if (response == null) {
                Log.e(TAG, "getMyFlightsInfoSucceed response is null");
            } else {
                mResponse = response;
                mMainActivity.runOnUI(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setData(mResponse);
                    }
                });

                GetFlightsListResponse flightsListResponse = new GetFlightsListResponse();
                flightsListResponse.setFlightList(response);
                String json = flightsListResponse.getListJson();
                if (!TextUtils.isEmpty(json)) {
                    Preferences.saveMyFlightsData(getContext(), json);
                }
                if (mIsInsert)
                    Util.resetNotification(getContext(), response);

                mMainActivity.setMarqueeMessage(Util.getMarqueeSubMessage(getContext()));

                mLoadingView.goneLoadingView();
            }
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }

    @Override
    public void getMyFlightsInfoFailed(String message, boolean timeout) {
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
            }
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }

    @Override
    public void deleteMyFlightsInfoSucceed() {
        mLoadingView.goneLoadingView();
        if (isAdded() && !isDetached()) {
            mMainActivity.runOnUI(new Runnable() {
                @Override
                public void run() {
                    mAdapter.setData(null);
                    mPresenter.getMyFlightsInfoAPI();
                }
            });
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }

    @Override
    public void deleteMyFlightsInfoFailed(String message, boolean timeout) {
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
    public void addNotification(FlightsListData data) {
        //step1. 計算出提醒時間的實際時間
        long timeDiff = Util.reduceTime(String.format("%1$s %2$s", data.getExpressDate(), data.getExpressTime()), Integer.parseInt(mHour), Integer.parseInt(mMinute));
        //step2. 計算與手機當前時間的時間差
        HashMap<String, Long> nowTimeDiff = Util.getDifferentTimeWithNowTime(timeDiff);
        //step3. 將時間數據存錄item中
        ClockTimeData clockTimeData = new ClockTimeData();
        clockTimeData.setShowHour(mHour);
        clockTimeData.setShowMinute(mMinute);
        if (nowTimeDiff != null) {
            clockTimeData.setHour(nowTimeDiff.get(Util.TAG_HOUR));
            clockTimeData.setMin(nowTimeDiff.get(Util.TAG_MIN));
            clockTimeData.setSec(nowTimeDiff.get(Util.TAG_SEC));
        } else {
            mAdapter.setData(mResponse);
            return;
        }
        data.setNotificationTime(clockTimeData);
        //step4. 產出此則提醒的ID並存入item中
        data.setNotificationId(new Random().nextInt(9000) + 65);
        //step6. 設置提醒
        int sec = (int) data.getNotificationTime().getSec();
        Integer id = data.getNotificationId();
        Calendar cal1 = Calendar.getInstance();
        cal1.add(Calendar.SECOND, sec);
        Log.d(TAG, "ID : " + id + " 配置鬧終於" + sec + "秒後: " + cal1.getTime());
        Gson gson = new Gson();
        String flightData = gson.toJson(data, FlightsListData.class);

        Intent intent = new Intent(getContext(), FlightClockBroadcast.class);
        intent.putExtra(MyFlightsNotifyContract.TAG_NOTIFY_Flight_DATA, flightData);
        intent.putExtra(MyFlightsNotifyContract.TAG_NOTIFY_ID, id);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), id, intent, 0);

        AlarmManager am = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        if (am != null) {
            am.set(AlarmManager.RTC_WAKEUP, cal1.getTimeInMillis(), pendingIntent);
            Preferences.saveMyFlightsData(getContext(), flightData);
        }
    }

    @Override
    public void deleteNotification() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_frame:
                if (view.getTag() instanceof FlightsListData) {
                    final FlightsListData tag = (FlightsListData) view.getTag();
                    mMainActivity.getFlightsDetailInfo()
                            .setTitle(getString(R.string.flight_dialog_title))
                            .setRecyclerContent(DialogContentData.getFlightDetail(getContext(), tag))
                            .setLeftText(getString(R.string.alert_btn_cancel))
                            .setRightText(getString(R.string.cell_btn_delete))
                            .setClickListener(new IOnItemClickListener() {
                                @Override
                                public void onClick(View view) {
                                    switch (view.getId()) {
                                        case R.id.textView_left://取消按鈕
                                            break;
                                        case R.id.textView_right://刪除按鈕
                                            mSelectList.add(tag);
                                            deleteData();
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
            case R.id.layout_notification://提醒
                if (view.getTag() instanceof Integer) {
                    mSelectPosition = (int) view.getTag();
                    FlightsListData data = mResponse.get(mSelectPosition);
                    mLayoutSelector.setVisibility(View.VISIBLE);
                    mTextViewSelectTitle.setText(getString(R.string.notify_alerm_selector_title,
                            !TextUtils.isEmpty(data.getAirlineCode()) ? data.getAirlineCode() : ""
                            , !TextUtils.isEmpty(data.getShifts()) ? data.getShifts() : ""));
                    initLeftPicker();
                    initRightPicker();
                } else {
                    Log.e(TAG, "recycler view.getTag is error");
                    showMessage(getString(R.string.data_error));
                }
                break;
        }
    }

    /**
     * Delete data
     */
    private void deleteData() {
        mLoadingView.showLoadingView();
        mPresenter.deleteMyFlightsInfoAPI(mSelectList);
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

    @OnClick({R.id.textView_cancel, R.id.textView_ok})
    public void onViewClicked(View view) {
        FlightsListData data = mResponse.get(mSelectPosition);
        switch (view.getId()) {
            case R.id.textView_cancel:
                mLayoutSelector.setVisibility(View.GONE);
                data.setNotificationTime(null);
                mAdapter.setData(mResponse);
                break;
            case R.id.textView_ok:
                mLayoutSelector.setVisibility(View.GONE);

                addNotification(data);
                mAdapter.setData(mResponse);
                break;
        }
    }

    /**
     * set Left picker
     */
    private void initLeftPicker() {
        mHour = "00";
        Util.setNumberPickerTextColor(mNumberPickerLeft, ContextCompat.getColor(getContext(), android.R.color.white));
        mNumberPickerLeft.setMinValue(0);
        mNumberPickerLeft.setDisplayedValues(mHours);
        mNumberPickerLeft.setMaxValue(mHours.length - 1);
        mNumberPickerLeft.setWrapSelectorWheel(false);
        mNumberPickerRight.setValue(0);
        mNumberPickerLeft.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Log.d("TAG", "old = " + oldVal + " new = " + newVal);
                mHour = String.format("%02d", newVal);
            }
        });
    }

    /**
     * Set Right picker
     */
    private void initRightPicker() {
        mMinute = "00";
        Util.setNumberPickerTextColor(mNumberPickerRight, ContextCompat.getColor(getContext(), android.R.color.white));
        mNumberPickerRight.setMinValue(0);
        mNumberPickerRight.setDisplayedValues(mMinutes);
        mNumberPickerRight.setMaxValue(mMinutes.length - 1);
        mNumberPickerRight.setWrapSelectorWheel(false);
        mNumberPickerRight.setValue(0);
        mNumberPickerRight.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Log.d("TAG", "old = " + oldVal + " new = " + newVal);
                mMinute = String.format("%d0", newVal);
            }
        });
    }
}
