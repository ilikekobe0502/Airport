package com.whatmedia.ttia.page.main.flights.notify;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
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

public class MyFlightsNotifyRecyclerViewAdapter extends RecyclerView.Adapter<MyFlightsNotifyRecyclerViewAdapter.ViewHolder> {
    private final static String TAG = MyFlightsNotifyRecyclerViewAdapter.class.getSimpleName();
    private List<ClockData> mItems;
    private Context mContext;
    private IOnItemClickListener mListener;
    private java.util.GregorianCalendar mCalendar = new java.util.GregorianCalendar();

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
        ClockData item = mItems.get(position);
        if (item == null)
            return;

        holder.mTextViewTime.setText(item.getTimeString());
        holder.mSwitchOpen.setChecked(item.isNotify());
        if (item.isNotify())
            holder.mSwitchOpen.setText(R.string.my_flights_notify_on);
        else
            holder.mSwitchOpen.setText(R.string.my_flights_notify_off);

        holder.mTextViewTime.setTag(item);
        holder.mSwitchOpen.setTag(item);
    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size() : 0;
    }

    public void setData(List<ClockData> data) {
        mItems = data;
        setBroadcast(data.get(data.size() - 1));
        notifyDataSetChanged();
    }

    public void setClickListener(IOnItemClickListener listener) {
        mListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView_icon)
        ImageView mImageViewIcon;
        @BindView(R.id.textView_time)
        TextView mTextViewTime;
        @BindView(R.id.switch_open)
        Switch mSwitchOpen;

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

//                                List<ClockData> clockDataList = mItems;
                                for (ClockData data : mItems) {
                                    if (TextUtils.equals(data.getId(), recyclerViewItem.getId())) {
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
//                        List<ClockData> clockDataList = mItems;
                        for (ClockData data : mItems) {
                            if (TextUtils.equals(data.getId(), recyclerViewItem.getId())) {
                                data.setNotify(mSwitchOpen.isChecked());
                                if (mSwitchOpen.isChecked())
                                    setBroadcast(data);
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

    /**
     * Set broadcast notification
     *
     * @param data
     */
    private void setBroadcast(ClockData data) {
        int sec = (int) data.getTime().getSec();
        Integer id = Integer.parseInt(data.getId());
        Calendar cal1 = Calendar.getInstance();
        cal1.add(Calendar.SECOND, sec);
        Log.d(TAG, "配置鬧終於" + sec + "秒後: " + cal1);

        Intent intent = new Intent(mContext, FlightClockBroadcast.class);
        intent.putExtra(MyFlightsNotifyContract.TAG_NOTIFY_KEY, data.getTimeString());


        PendingIntent pi1 = PendingIntent.getBroadcast(mContext, 1, intent, 0);

        AlarmManager am = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, cal1.getTimeInMillis(), pi1);
    }
}