package com.whatmedia.ttia.page.main.useful.timezone;


import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.component.ClockView;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;

import java.util.Calendar;
import java.util.Date;

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
                    long sysTime = System.currentTimeMillis();
                    CharSequence sysTimeStr = DateFormat.format("hh:mm:ss", sysTime);
                    mTextTime.setText(sysTimeStr);
                    mClockView.setHour(Integer.valueOf(sysTimeStr.subSequence(0,2).toString()));
                    mClockView.setMinute(Integer.valueOf(sysTimeStr.subSequence(3,5).toString()));
                    sysTimeStr = DateFormat.format("ss:hh:mm:", sysTime);
                    mClockView.setSecond(Integer.valueOf(sysTimeStr.subSequence(0,2).toString()));
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
}
