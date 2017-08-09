package com.whatmedia.ttia.page.main.flights.notify;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.gson.Gson;
import com.whatmedia.ttia.R;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;
import com.whatmedia.ttia.response.data.ClockData;
import com.whatmedia.ttia.response.data.ClockDataList;
import com.whatmedia.ttia.response.data.ClockTimeData;
import com.whatmedia.ttia.services.FlightClockBroadcast;
import com.whatmedia.ttia.utility.Preferences;
import com.whatmedia.ttia.utility.Util;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neo_mac on 2017/8/4.
 */

public class MyFlightsNotifyRecyclerViewAdapter extends RecyclerView.Adapter<MyFlightsNotifyRecyclerViewAdapter.ViewHolder> implements CompoundButton.OnCheckedChangeListener {
    private final static String TAG = MyFlightsNotifyRecyclerViewAdapter.class.getSimpleName();
    private List<ClockData> mItems;
    private Context mContext;
    private IOnItemClickListener mListener;

    public MyFlightsNotifyRecyclerViewAdapter(Context context, List<ClockData> data) {
        mContext = context;
        mItems = data;
    }

    public MyFlightsNotifyRecyclerViewAdapter(Context context) {
        mContext = context;
        mItems = ClockDataList.newInstance(Preferences.getClockData(context));

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_flights_notify, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mItems == null)
            return;
        final ClockData item = mItems.get(position);
        if (item == null)
            return;

        holder.mTextViewTime.setText(item.getTimeString());
        holder.mSwitchOpen.setChecked(item.isNotify());
        if (item.isNotify())
            holder.mSwitchOpen.setText(R.string.my_flights_notify_on);
        else
            holder.mSwitchOpen.setText(R.string.my_flights_notify_off);

        holder.mTextViewTime.setTag(item);
        holder.mSwitchOpen.setOnCheckedChangeListener(this);
        holder.mSwitchOpen.setTag(item);
    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size() : 0;
    }

    public void setData(List<ClockData> data) {
        mItems = data;
        Util.setAlertClock(mContext, data.get(data.size() - 1));
        notifyDataSetChanged();
    }

    public void setClickListener(IOnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getTag() != null && buttonView.getTag() instanceof ClockData) {
            ClockData item = (ClockData) buttonView.getTag();
            if (isChecked) {
                Util.setAlertClock(mContext, item);
            } else {
                Util.cancelAlertClock(mContext, item.getId());
            }
        } else {
            Log.d("TAG", "view.getTag error");
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView_icon)
        ImageView mImageViewIcon;
        @BindView(R.id.textView_time)
        TextView mTextViewTime;
        @BindView(R.id.switch_open)
        android.support.v7.widget.SwitchCompat mSwitchOpen;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.textView_time, R.id.switch_open})
        public void onClick(View view) {
            if (view.getTag() != null) {
                final ClockData recyclerViewItem = (ClockData) view.getTag();
                switch (view.getId()) {
                    case R.id.textView_time:

                        Util.showTimePicker(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                HashMap<String, Long> diffTime = Util.getCountTime(hourOfDay + ":" + minute);
                                ClockTimeData clockTimeData = new ClockTimeData();
                                clockTimeData.setHour(diffTime.get(Util.TAG_HOUR));
                                clockTimeData.setMin(diffTime.get(Util.TAG_MIN));
                                clockTimeData.setSec(diffTime.get(Util.TAG_SEC));

                                mTextViewTime.setText(view.getContext().getString(R.string.my_flights_notify, diffTime.get(Util.TAG_HOUR), diffTime.get(Util.TAG_MIN)));

                                Gson gson = new Gson();

                                for (ClockData data : mItems) {
                                    if (data.getId() == recyclerViewItem.getId()) {
                                        data.setTime(clockTimeData);
                                        data.setTimeString(mTextViewTime.getText().toString());
                                        data.setNotify(mSwitchOpen.isChecked());
                                    }
                                }
                                String json = gson.toJson(mItems);

                                Preferences.saveClockData(view.getContext(), json);
                                notifyDataSetChanged();
                            }
                        });
                        break;
                    case R.id.switch_open:
                        for (ClockData data : mItems) {
                            if (data.getId() == recyclerViewItem.getId()) {
                                data.setNotify(mSwitchOpen.isChecked());
                            }
                        }
                        Gson gson = new Gson();
                        String json = gson.toJson(mItems);

                        Preferences.saveClockData(view.getContext(), json);
                        notifyDataSetChanged();
                        break;

                }
            } else {
                Log.e(TAG, "View.getTag() is null");
            }
        }
    }
}