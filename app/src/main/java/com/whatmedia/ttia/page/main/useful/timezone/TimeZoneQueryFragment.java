package com.whatmedia.ttia.page.main.useful.timezone;


import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.icu.util.TimeZone;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.component.ClockView;
import com.whatmedia.ttia.component.MyToolbar;
import com.whatmedia.ttia.component.dialog.MyWeatherDialog;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimeZoneQueryFragment extends BaseFragment implements TimeZoneQueryContract.View{

    private static final String TAG = TimeZoneQueryFragment.class.getSimpleName();

    @BindView(R.id.text_city)
    TextView mTextCity;
    @BindView(R.id.text_date)
    TextView mTextDate;
    @BindView(R.id.text_time)
    TextView mTextTime;
    @BindView(R.id.clockview)
    ClockView mClockView;

    private static final int RUNNING = 1000;


    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private TimeZoneQueryContract.Presenter mPresenter;

    private int mRegin = 0;
    private int mContury = 1;
    private String[] mCodeArray;
    private String[] mItems;
    private String mTimeStamp = "+8";

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
        new TimeThread().start();
    }

    @Override
    public void onResume() {
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
        c.setTimeZone(java.util.TimeZone.getTimeZone("GMT"+mTimeStamp));
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;
        int day = c.get(Calendar.DAY_OF_MONTH);

        String monthtext = "";
        String dattext = "";
        if(month<10){
            monthtext = "0"+month;
        }else{
            monthtext = String.valueOf(month);
        }
        if(day<10){
            dattext = "0"+day;
        }else{
            dattext = String.valueOf(day);
        }

        mTextDate.setText(year+"/"+monthtext+"/"+dattext);

        long sysTime = System.currentTimeMillis();

        CharSequence sysTimeStr = DateFormat.format("hh:mm:ss", sysTime);
        mTextTime.setText(sysTimeStr);
        mClockView.setHour(Integer.valueOf(sysTimeStr.subSequence(0,2).toString()));
        mClockView.setMinute(Integer.valueOf(sysTimeStr.subSequence(3,5).toString()));
        sysTimeStr = DateFormat.format("ss:hh:mm:", sysTime);
        mClockView.setSecond(Integer.valueOf(sysTimeStr.subSequence(0,2).toString()));
        tool();
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

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage (Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case RUNNING:
//                    long sysTime = System.currentTimeMillis();
//                    CharSequence sysTimeStr = DateFormat.format("hh:mm:ss", sysTime);
//                    mTextTime.setText(sysTimeStr);
//                    mClockView.setHour(Integer.valueOf(sysTimeStr.subSequence(0,2).toString()));
//                    mClockView.setMinute(Integer.valueOf(sysTimeStr.subSequence(3,5).toString()));
//                    sysTimeStr = DateFormat.format("ss:hh:mm:", sysTime);
//                    mClockView.setSecond(Integer.valueOf(sysTimeStr.subSequence(0,2).toString()));

                    Calendar c = Calendar.getInstance();
                    c.setTimeZone(java.util.TimeZone.getTimeZone("GMT"+mTimeStamp));
//                    long sysTime = c.getTimeInMillis();
//                    CharSequence sysTimeStr = DateFormat.format("hh:mm:ss", sysTime);
//                    mTextTime.setText(sysTimeStr);
//                    mClockView.setHour(Integer.valueOf(sysTimeStr.subSequence(0,2).toString()));
//                    mClockView.setMinute(Integer.valueOf(sysTimeStr.subSequence(3,5).toString()));
//                    sysTimeStr = DateFormat.format("ss:hh:mm:", sysTime);
//                    mClockView.setSecond(Integer.valueOf(sysTimeStr.subSequence(0,2).toString()));

                    String hour,min,sec;

                    if(c.get(Calendar.HOUR_OF_DAY)<10){
                        hour = "0"+c.get(Calendar.HOUR_OF_DAY);
                    }else{
                        hour = c.get(Calendar.HOUR_OF_DAY)+"";
                    }
                    if(c.get(Calendar.MINUTE)<10){
                        min = "0"+c.get(Calendar.MINUTE);
                    }else{
                        min = c.get(Calendar.MINUTE)+"";
                    }
                    if(c.get(Calendar.SECOND)<10){
                        sec = "0"+c.get(Calendar.SECOND);
                    }else{
                        sec = c.get(Calendar.SECOND)+"";
                    }

                    mTextTime.setText(hour+":"+min+":"+sec);
                    mClockView.setHour(c.get(Calendar.HOUR_OF_DAY));
                    mClockView.setMinute(c.get(Calendar.MINUTE));
                    mClockView.setSecond(c.get(Calendar.SECOND));

                    break;

                default:
                    break;
            }
        }
    };

    public class TimeThread extends Thread {
        @Override
        public void run () {
            do {
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = RUNNING;
                    mHandler.sendMessage(msg);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while(true);
        }
    }

    private void tool() {
        mMainActivity.getMyToolbar().clearState()
                .setTitleText(getString(R.string.useful_info_timezone))
                .setBackground(ContextCompat.getColor(getContext(), R.color.colorSubTitle))
                .setMoreLayoutVisibility(View.GONE)
                .setRightText(getString(R.string.currency_conversion_other_area))
                .setOnAreaClickListener(new MyToolbar.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyWeatherDialog dialog = new MyWeatherDialog().setItemClickListener(new IOnItemClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (view.getTag() != null && view.getTag() instanceof Integer) {
                                    mRegin = (int) view.getTag();
                                    MyWeatherDialog dialog = MyWeatherDialog.newInstance().setRegion((Integer) view.getTag()).setItemClickListener(new IOnItemClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if (view.getTag() != null && view.getTag() instanceof Integer) {
                                                mContury = (int) view.getTag();
                                                regin();
                                            }
                                        }
                                    });
                                    dialog.show(getActivity().getFragmentManager(), "dialog");
                                } else {
                                    // TODO: 2017/8/12 error handler
                                }
                            }
                        });
                        dialog.show(getActivity().getFragmentManager(), "dialog");
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

    private void regin() {
        switch (mRegin) {
            case 0:
                mCodeArray = getResources().getStringArray(R.array.weather_taiwan_time_stamp_array);
                mItems = getResources().getStringArray(R.array.weather_taiwan_city_array);
                break;
            case 1:
                mCodeArray = getResources().getStringArray(R.array.weather_asia_time_stamp_array);
                mItems = getResources().getStringArray(R.array.weather_asia_oceania_city_array);
                break;
            case 2:
                mCodeArray = getResources().getStringArray(R.array.weather_america_time_stamp_array);
                mItems = getResources().getStringArray(R.array.weather_america_city_array);
                break;
            case 3:
                mCodeArray = getResources().getStringArray(R.array.weather_eurpo_time_stamp_array);
                mItems = getResources().getStringArray(R.array.weather_eurpo_city_array);
                break;
            case 4:
                mCodeArray = getResources().getStringArray(R.array.weather_china_time_stamp_array);
                mItems = getResources().getStringArray(R.array.weather_china_city_array);
                break;
        }

        Log.e(TAG,"regin():"+mCodeArray[mContury]+mItems[mContury]);

        mTextCity.setText(mItems[mContury]);
        mTimeStamp = mCodeArray[mContury];

        Calendar c = Calendar.getInstance();
        c.setTimeZone(java.util.TimeZone.getTimeZone("GMT"+mTimeStamp));
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        String monthtext = "";
        String dattext = "";
        if(month<10){
            monthtext = "0"+month;
        }else{
            monthtext = String.valueOf(month);
        }
        if(day<10){
            dattext = "0"+day;
        }else{
            dattext = String.valueOf(day);
        }
        mTextDate.setText(year+"/"+monthtext+"/"+dattext);
    }
}
