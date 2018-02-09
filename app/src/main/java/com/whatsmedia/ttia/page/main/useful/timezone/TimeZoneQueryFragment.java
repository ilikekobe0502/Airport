package com.whatsmedia.ttia.page.main.useful.timezone;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.whatsmedia.ttia.R;
import com.whatsmedia.ttia.component.ClockView;
import com.whatsmedia.ttia.component.MyToolbar;
import com.whatsmedia.ttia.page.BaseFragment;
import com.whatsmedia.ttia.page.IActivityTools;
import com.whatsmedia.ttia.utility.Preferences;
import com.whatsmedia.ttia.utility.Util;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TimeZoneQueryFragment extends BaseFragment implements TimeZoneQueryContract.View {

    private static final String TAG = TimeZoneQueryFragment.class.getSimpleName();

    @BindView(R.id.text_city)
    TextView mTextCity;
    @BindView(R.id.text_date)
    TextView mTextDate;
    @BindView(R.id.text_time)
    TextView mTextTime;
    @BindView(R.id.clockview)
    ClockView mClockView;
    @BindView(R.id.clock_bg)
    ImageView mClockBackground;

    private static final int RUNNING = 1000;
    @BindView(R.id.layout_ok)
    RelativeLayout mLayoutOk;
    @BindView(R.id.number_picker_left)
    NumberPicker mNumberPickerLeft;
    @BindView(R.id.number_picker_right)
    NumberPicker mNumberPickerRight;
    @BindView(R.id.layout_selector)
    RelativeLayout mLayoutSelector;


    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private TimeZoneQueryContract.Presenter mPresenter;
    private RelativeLayout.LayoutParams mClockBackgroundLayoutParams;

    private int mRegion = 0;
    private int mCountry = 0;
    //    private String[] mCodeArray;
    private String[] mItems;
    private String mTimeStamp = "+8";

    private String[] mCountryName;
    private String[] mTownName;
    private Thread mThread;
    private boolean mRunThread;

    public TimeZoneQueryFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TimeZoneQueryFragment newInstance() {
        TimeZoneQueryFragment fragment = new TimeZoneQueryFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        if (mThread == null) {
            mThread = new TimeThread();
            mThread.start();
        }
        mRunThread = true;
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timezone, container, false);
        ButterKnife.bind(this, view);
        mPresenter = TimeZoneQueryPresenter.getInstance(getContext(), this);

        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT" + mTimeStamp));
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);

        String monthtext = "";
        String dattext = "";
        if (month < 10) {
            monthtext = "0" + month;
        } else {
            monthtext = String.valueOf(month);
        }
        if (day < 10) {
            dattext = "0" + day;
        } else {
            dattext = String.valueOf(day);
        }

        mTextDate.setText(year + "/" + monthtext + "/" + dattext);

        long sysTime = System.currentTimeMillis();

        CharSequence sysTimeStr = DateFormat.format("hh:mm:ss", sysTime);
        mTextTime.setText(sysTimeStr);
        mClockView.setHour(Integer.valueOf(sysTimeStr.subSequence(0, 2).toString()));
        mClockView.setMinute(Integer.valueOf(sysTimeStr.subSequence(3, 5).toString()));
        sysTimeStr = DateFormat.format("ss:hh:mm:", sysTime);
        mClockView.setSecond(Integer.valueOf(sysTimeStr.subSequence(0, 2).toString()));
        tool();

        if (Preferences.checkScreenIs34Mode(getContext())) {
            mClockBackgroundLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mClockBackgroundLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            mClockBackgroundLayoutParams.setMargins(getResources().getDimensionPixelSize(R.dimen.dp_pixel_20)
                    , getResources().getDimensionPixelSize(R.dimen.dp_pixel_20)
                    , getResources().getDimensionPixelSize(R.dimen.dp_pixel_20)
                    , getResources().getDimensionPixelSize(R.dimen.dp_pixel_20));

            mClockBackground.setLayoutParams(mClockBackgroundLayoutParams);
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
        mRunThread = false;
        if (mThread != null)
            mThread = null;
        if (mHandler != null)
            mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    @Override
    public void onPause() {
        mRunThread = false;
        if (mThread != null)
            mThread = null;
        if (mHandler != null)
            mHandler.removeCallbacksAndMessages(null);
        super.onPause();
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

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case RUNNING:

                    Calendar c = Calendar.getInstance();
                    c.setTimeZone(TimeZone.getTimeZone("GMT" + mTimeStamp));

                    String hour = "0";
                    String min = "0";
                    String sec = "0";

                    if (c.get(Calendar.HOUR_OF_DAY) < 10) {
                        hour = "0" + c.get(Calendar.HOUR_OF_DAY);
                    } else {
                        hour = c.get(Calendar.HOUR_OF_DAY) + "";
                    }
                    if (c.get(Calendar.MINUTE) < 10) {
                        min = "0" + c.get(Calendar.MINUTE);
                    } else {
                        min = c.get(Calendar.MINUTE) + "";
                    }
                    if (c.get(Calendar.SECOND) < 10) {
                        sec = "0" + c.get(Calendar.SECOND);
                    } else {
                        sec = c.get(Calendar.SECOND) + "";
                    }

                    if (!TextUtils.isEmpty(hour) && !TextUtils.isEmpty(min) && !TextUtils.isEmpty(sec)) {
                        mTextTime.setText(String.format("%1$s:%2$s:%3$s", hour, min, sec));
                        mClockView.setHour(c.get(Calendar.HOUR_OF_DAY));
                        mClockView.setMinute(c.get(Calendar.MINUTE));
                        mClockView.setSecond(c.get(Calendar.SECOND));
                    } else {
                        Log.e(TAG, "Time data Null");
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @OnClick(R.id.layout_ok)
    public void onClick() {
        mLayoutSelector.setVisibility(View.GONE);
        setClock();
    }

    public class TimeThread extends Thread {
        @Override
        public void run() {
            while (mRunThread) {
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = RUNNING;
                    mHandler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void tool() {
        mMainActivity.getMyToolbar().clearState()
                .setTitleText(getString(R.string.title_timezone))
                .setToolbarBackground(ContextCompat.getDrawable(getContext(), R.drawable.toolbar_top_bg))
                .setMoreLayoutVisibility(View.GONE)
                .setRightText(getString(R.string.timezone_other_area))
                .setOnAreaClickListener(new MyToolbar.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mLayoutSelector.isShown()) {
                            mLayoutSelector.setVisibility(View.GONE);
                        } else {
                            mLayoutSelector.setVisibility(View.VISIBLE);
                            initLeftPicker();
                        }
                    }
                })
                .setAreaLayoutVisibility(View.VISIBLE)
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
    }

    private String[] switchRegion() {
        switch (mRegion) {
            case 0:
                mTownName = getResources().getStringArray(R.array.ori_time_zone_africa_array);
//                mCodeArray = getResources().getStringArray(R.array.weather_taiwan_time_stamp_array);
                return mItems = getResources().getStringArray(R.array.time_zone_africa_array);
            case 1:
                mTownName = getResources().getStringArray(R.array.ori_time_zone_america_array);
//                mCodeArray = getResources().getStringArray(R.array.weather_asia_time_stamp_array);
                return mItems = getResources().getStringArray(R.array.time_zone_america_array);
            case 2:
                mTownName = getResources().getStringArray(R.array.ori_time_zone_sg_array);
//                mCodeArray = getResources().getStringArray(R.array.weather_america_time_stamp_array);
                return mItems = getResources().getStringArray(R.array.time_zone_sg_array);
            case 3:
                mTownName = getResources().getStringArray(R.array.ori_time_zone_ng_array);
//                mCodeArray = getResources().getStringArray(R.array.weather_eurpo_time_stamp_array);
                return mItems = getResources().getStringArray(R.array.time_zone_ng_array);
            case 4:
                mTownName = getResources().getStringArray(R.array.ori_time_zone_asia_array);
//                mCodeArray = getResources().getStringArray(R.array.weather_china_time_stamp_array);
                return mItems = getResources().getStringArray(R.array.time_zone_asia_array);
            case 5:
                mTownName = getResources().getStringArray(R.array.ori_time_zone_tcy_array);
                return mItems = getResources().getStringArray(R.array.time_zone_tcy_array);
            case 6:
                mTownName = getResources().getStringArray(R.array.ori_time_zone_a_array);
                return mItems = getResources().getStringArray(R.array.time_zone_a_array);
            case 7:
                mTownName = getResources().getStringArray(R.array.ori_time_zone_zu_array);
                return mItems = getResources().getStringArray(R.array.time_zone_zu_array);
            case 8:
                mTownName = getResources().getStringArray(R.array.ori_time_zone_id_array);
                return mItems = getResources().getStringArray(R.array.time_zone_id_array);
            case 9:
                mTownName = getResources().getStringArray(R.array.ori_time_zone_tpy_array);
                return mItems = getResources().getStringArray(R.array.time_zone_tpy_array);
            default:
                return new String[0];
        }
    }

    private void setClock() {
        if (mItems.length > mCountry) {
            Log.d(TAG, "switchRegion():" + "mCodeArray[mCountry]" + mItems[mCountry]);
        } else {
            Log.d(TAG, "switchRegion():mCodeArray.length < mCountry + mItems.length < mCountry");
            mRegion = 0;
            mCountry = 1;
            switchRegion();
            return;
        }
        mTextCity.setText(mItems[mCountry]);
        String zoneName = mCountryName[mRegion] + "/" + mTownName[mCountry];
        long minutes = TimeUnit.MILLISECONDS.toMinutes(TimeZone.getTimeZone(zoneName).getRawOffset());
        long hour = minutes / 60;
        long halfHour = minutes % 60;
        mTimeStamp = halfHour != 0 ? String.format("%1$d:%2$d", hour, halfHour) : String.valueOf(hour);

        if (mTimeStamp.charAt(0) != '-' && mTimeStamp.charAt(0) != '+') {
            mTimeStamp = String.format(getString(R.string.timezone_combine), mTimeStamp);
        }

        Log.d("TIMEZONE", zoneName + ":" + mTimeStamp);

        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT" + mTimeStamp));
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        String monthtext = "";
        String dattext = "";
        if (month < 10) {
            monthtext = "0" + month;
        } else {
            monthtext = String.valueOf(month);
        }
        if (day < 10) {
            dattext = "0" + day;
        } else {
            dattext = String.valueOf(day);
        }
        mTextDate.setText(year + "/" + monthtext + "/" + dattext);
    }

    /**
     * set Left picker
     */
    private void initLeftPicker() {
        String[] data = getContext().getResources().getStringArray(R.array.time_zone_region_array);
        mCountryName = getContext().getResources().getStringArray(R.array.ori_time_zone_region_array);
        Util.setNumberPickerTextColor(mNumberPickerLeft, ContextCompat.getColor(getContext(), android.R.color.white));
        mNumberPickerLeft.setMinValue(0);
        mNumberPickerLeft.setMaxValue(data.length - 1);
        mNumberPickerLeft.setWrapSelectorWheel(false);
        mNumberPickerLeft.setDisplayedValues(data);
        mNumberPickerLeft.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Log.d("TAG", "old = " + oldVal + " new = " + newVal);
                mRegion = newVal;
                setRightPicker();
            }
        });

        setRightPicker();
    }

    /**
     * Set Right picker
     */
    private void setRightPicker() {
        switchRegion();
        String[] data = mItems;
        Util.setNumberPickerTextColor(mNumberPickerRight, ContextCompat.getColor(getContext(), android.R.color.white));
        mNumberPickerRight.setMinValue(0);
        mNumberPickerRight.setMaxValue(0);
        try {
            mNumberPickerRight.setDisplayedValues(data);
        } catch (Exception e) {
            Log.e(TAG, "[mNumberPickerRight.setDisplayedValues(data) error ] " + e.toString());
        }
        mNumberPickerRight.setMaxValue(data.length - 1);
        mNumberPickerRight.setWrapSelectorWheel(false);
        mNumberPickerRight.setValue(0);
        mCountry = 0;
        mNumberPickerRight.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Log.d("TAG", "old = " + oldVal + " new = " + newVal);
                mCountry = newVal;
            }
        });
    }


}
