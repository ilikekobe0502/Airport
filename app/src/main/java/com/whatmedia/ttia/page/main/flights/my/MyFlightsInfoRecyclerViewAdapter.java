package com.whatmedia.ttia.page.main.flights.my;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.whatmedia.ttia.R;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;
import com.whatmedia.ttia.response.data.FlightsInfoData;
import com.whatmedia.ttia.utility.Preferences;
import com.whatmedia.ttia.utility.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neo_mac on 2017/8/4.
 */

public class MyFlightsInfoRecyclerViewAdapter extends RecyclerView.Adapter<MyFlightsInfoRecyclerViewAdapter.ViewHolder> {
    private final static String TAG = MyFlightsInfoRecyclerViewAdapter.class.getSimpleName();

    private List<FlightsInfoData> mItems;
    private Context mContext;
    private IOnItemClickListener mListener;
    //    private List<FlightsInfoData> mSelectItems = new ArrayList<>();
    private Map<String, FlightsInfoData> mSelectItems = new HashMap<>();
    private String mLocale;

    public MyFlightsInfoRecyclerViewAdapter(Context context, List<FlightsInfoData> list) {
        mContext = context;
        mItems = list;
        mLocale = Preferences.getLocaleSetting(context);
    }

    public MyFlightsInfoRecyclerViewAdapter(Context context) {
        mContext = context;
        mLocale = Preferences.getLocaleSetting(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_flights_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mItems == null)
            return;
        FlightsInfoData item = mItems.get(position);
        if (item == null)
            return;

        holder.mTextViewTime.setText(!TextUtils.isEmpty(item.getExpressTime()) ? Util.getTransformTimeFormat(Util.TAG_FORMAT_HM, item.getExpressTime().trim()) : "");
        holder.mTextViewFlightCode.setText(!TextUtils.isEmpty(item.getFlightCode()) ? item.getFlightCode().trim() : "");
        switch (mLocale) {
            case "zh_TW":
                holder.mTextViewLocation.setText(!TextUtils.isEmpty(item.getContactsLocationChinese()) ? item.getContactsLocationChinese().trim() : "");
                break;
            case "zh_CN":
                holder.mTextViewLocation.setText(!TextUtils.isEmpty(item.getContactsLocationChinese()) ? item.getContactsLocationChinese().trim() : "");
                break;
            case "en":
                holder.mTextViewLocation.setText(!TextUtils.isEmpty(item.getContactsLocationEng()) ? item.getContactsLocationEng().trim() : "");
                break;
            case "ja":
                holder.mTextViewLocation.setText(!TextUtils.isEmpty(item.getContactsLocationEng()) ? item.getContactsLocationEng().trim() : "");
                break;
            default:
                holder.mTextViewLocation.setText(!TextUtils.isEmpty(item.getContactsLocationChinese()) ? item.getContactsLocationChinese().trim() : "");
                break;
        }
        if (TextUtils.equals(item.getKinds(), FlightsInfoData.TAG_KIND_ARRIVE))
            holder.mTextViewGate.setText(!TextUtils.isEmpty(item.getLuggageCarousel()) ? item.getLuggageCarousel().trim() : "");
        else {
            holder.mTextViewGate.setText(!TextUtils.isEmpty(item.getGate()) ? item.getGate().trim() : "");
        }
        if (!TextUtils.isEmpty(item.getTerminals())) {
            StringBuilder builder = new StringBuilder();
            switch (mLocale) {
                case "zh_TW":
                case "zh_CN":
                    builder.append(item.getTerminals()).append(mContext.getString(R.string.flights_search_result_terminal_text));
                    break;
                case "en":
                case "ja":
                    builder.append(mContext.getString(R.string.flights_search_result_terminal_text)).append(item.getTerminals());
                    break;
                default:
                    builder.append(mContext.getString(R.string.flights_search_result_terminal_text)).append(item.getTerminals());
                    break;
            }
//            builder.append(item.getTerminals()).append(mContext.getString(R.string.flights_search_result_terminal_text));
            holder.mTextViewTerminal.setText(builder);
        } else
            holder.mTextViewTerminal.setText("");

        if (!TextUtils.isEmpty(item.getFlightStatus())) {
            if (checkFlightState(item.getFlightStatus()))
                holder.mTextViewState.setTextColor(ContextCompat.getColor(mContext, R.color.colorText));
            else
                holder.mTextViewState.setTextColor(ContextCompat.getColor(mContext, R.color.colorTextSpecial));
            holder.mTextViewState.setText(FlightsInfoData.checkFlightShowText(mContext, item.getFlightStatus()));
        } else
            holder.mTextViewState.setText("");

        if (!TextUtils.isEmpty(item.getAirlineCode())) {
            int sourceId = Util.getDrawableByString(mContext, "airline_" + item.getAirlineCode().toLowerCase());
            if (sourceId != 0)
                Picasso.with(mContext).load(sourceId).into(holder.mImageViewLogo);
            holder.mImageViewLogo.setVisibility(View.VISIBLE);
        } else {
            holder.mImageViewLogo.setVisibility(View.INVISIBLE);
        }

        if (item.getIsCheck()) {
            holder.mImageViewCheck.setBackground(ContextCompat.getDrawable(mContext, R.drawable.my_flight_02_02_yes));
        } else {
            holder.mImageViewCheck.setBackground(ContextCompat.getDrawable(mContext, R.drawable.my_flight_02_02_no));
        }

        holder.mImageViewCheck.setTag(item);
        holder.mLayoutFrame.setTag(item);
    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size() : 0;
    }

    public void setData(List<FlightsInfoData> data) {
        mItems = data;
        mSelectItems.clear();
        notifyDataSetChanged();
    }

    public void setClickListener(IOnItemClickListener listener) {
        mListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.layout_frame)
        RelativeLayout mLayoutFrame;
        @BindView(R.id.textView_time)
        TextView mTextViewTime;
        @BindView(R.id.imageView_logo)
        ImageView mImageViewLogo;
        @BindView(R.id.imageView_check)
        ImageView mImageViewCheck;
        @BindView(R.id.textView_flight_code)
        TextView mTextViewFlightCode;
        @BindView(R.id.textView_location)
        TextView mTextViewLocation;
        @BindView(R.id.textView_terminal)
        TextView mTextViewTerminal;
        @BindView(R.id.textView_gate)
        TextView mTextViewGate;
        @BindView(R.id.layout_terminal)
        RelativeLayout mLayoutTerminal;
        @BindView(R.id.textView_state)
        TextView mTextViewState;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.imageView_check, R.id.layout_frame})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.imageView_check:
                    if (view.getTag() != null) {
                        FlightsInfoData selectData = (FlightsInfoData) view.getTag();
                        if (selectData.getIsCheck()) {//yes to no
                            selectData.setIsCheck(false);
                            mImageViewCheck.setBackground(ContextCompat.getDrawable(mContext, R.drawable.my_flight_02_02_no));
                        } else {// no to yes
                            mImageViewCheck.setBackground(ContextCompat.getDrawable(mContext, R.drawable.my_flight_02_02_yes));
                            selectData.setIsCheck(true);
                        }
                    } else {
                        Log.e(TAG, "view.getTag() is null");
                    }
                    break;
                case R.id.layout_frame:
                    if (mListener != null)
                        mListener.onClick(view);
                    break;
            }
        }

    }

    /**
     * Check flight is "on time" or "arrived" or "departure"
     *
     * @param data
     * @return
     */
    private boolean checkFlightState(String data) {
        if (data.contains(FlightsInfoData.TAG_ON_TIME) || data.contains(FlightsInfoData.TAG_ARRIVED) || data.contains(FlightsInfoData.TAG_DEPARTED)) {
            return true;
        }
        return false;
    }

    /**
     * check flights show text
     *
     * @param data
     * @return
     */
    private String checkFlightShowText(String data) {
        String text = FlightsInfoData.TAG_ON_TIME_SHOW_TEXT;
        if (data.contains(FlightsInfoData.TAG_ON_TIME))
            text = FlightsInfoData.TAG_ON_TIME_SHOW_TEXT;
        else if (data.contains(FlightsInfoData.TAG_DELAY))
            text = FlightsInfoData.TAG_DELAY_SHOW_TEXT;
        else if (data.contains(FlightsInfoData.TAG_ARRIVED))
            text = FlightsInfoData.TAG_ARRIVED_SHOW_TEXT;
        else if (data.contains(FlightsInfoData.TAG_CANCELLED))
            text = FlightsInfoData.TAG_CANCELLED_SHOW_TEXT;
        else if (data.contains(FlightsInfoData.TAG_SCHEDULE_CHANGE))
            text = FlightsInfoData.TAG_SCHEDULE_CHANGE_SHOW_TEXT;
        else if (data.contains(FlightsInfoData.TAG_DEPARTED))
            text = FlightsInfoData.TAG_DEPARTED_SHOW_TEXT;
        return text;
    }

    /**
     * Get Select Data
     *
     * @return
     */
    public Map<String, FlightsInfoData> getSelectData() {
        if (mItems != null) {
            for (FlightsInfoData item : mItems) {
                if (item.getIsCheck())
                    mSelectItems.put(item.getId(), item);
            }
        }
        return mSelectItems != null ? mSelectItems : new HashMap<String, FlightsInfoData>();
    }
}