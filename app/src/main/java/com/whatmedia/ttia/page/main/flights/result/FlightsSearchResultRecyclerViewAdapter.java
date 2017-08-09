package com.whatmedia.ttia.page.main.flights.result;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.whatmedia.ttia.utility.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neo on 2017/8/4.
 */

public class FlightsSearchResultRecyclerViewAdapter extends RecyclerView.Adapter<FlightsSearchResultRecyclerViewAdapter.ViewHolder> {
    private List<FlightsInfoData> mItems;
    private Context mContext;
    private IOnItemClickListener mListener;

    public FlightsSearchResultRecyclerViewAdapter(Context context, List<FlightsInfoData> data) {
        mContext = context;
        mItems = data;
    }

    public FlightsSearchResultRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_flights_search_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mItems == null)
            return;
        FlightsInfoData item = mItems.get(position);
        if (item == null)
            return;

        holder.mTextViewTime.setText(!TextUtils.isEmpty(item.getExpressTime()) ? item.getExpressTime().trim() : "");
        holder.mTextViewFlightCode.setText(!TextUtils.isEmpty(item.getFlightCode()) ? item.getFlightCode().trim() : "");
        holder.mTextViewLocation.setText(!TextUtils.isEmpty(item.getContactsLocationChinese()) ? item.getContactsLocationChinese().trim() : "");
        holder.mTextViewGate.setText(!TextUtils.isEmpty(item.getGate()) ? item.getGate().trim() : "");
        if (!TextUtils.isEmpty(item.getTerminals())) {
            StringBuilder builder = new StringBuilder();
            builder.append(item.getTerminals()).append(mContext.getString(R.string.flights_search_result_terminal_text));
            holder.mTextViewTerminal.setText(builder);
        } else
            holder.mTextViewTerminal.setText("");

        if (!TextUtils.isEmpty(item.getFlightStatus())) {
            if (checkFlightState(item.getFlightStatus()))
                holder.mTextViewState.setTextColor(ContextCompat.getColor(mContext, R.color.colorText));
            else
                holder.mTextViewState.setTextColor(ContextCompat.getColor(mContext, R.color.colorTextSpecial));
            holder.mTextViewState.setText(checkFlightShowText(item.getFlightStatus()));
        } else
            holder.mTextViewState.setText("");

        if (!TextUtils.isEmpty(item.getAirlineCode())) {
            int id = Util.getDrawableByString(mContext, "airline_" + item.getAirlineCode().toLowerCase());
            if (id != 0)
                Picasso.with(mContext).load(id).into(holder.mImageViewLogo);
            holder.mImageViewLogo.setVisibility(View.VISIBLE);
        } else {
            holder.mImageViewLogo.setVisibility(View.INVISIBLE);
        }

        holder.mLayoutFrame.setTag(item);
    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size() : 0;
    }

    public void setData(List<FlightsInfoData> data) {
        mItems = data;
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

        @OnClick(R.id.layout_frame)
        public void onClick(View view) {
            if (mListener != null)
                mListener.onClick(view);
        }

    }

//    private SpannableStringBuilder checkFlightState(String data) {
//        SpannableStringBuilder builder = new SpannableStringBuilder(data);
//        ForegroundColorSpan colorSpan = new ForegroundColorSpan(ContextCompat.getColor(mContext,))
//        if (data.contains(FlightsInfoData.TAG_CANCELLED)) {
//        }
//
//        return builder;
//    }

    /**
     * Check flight is "on time"
     *
     * @param data
     * @return
     */
    private boolean checkFlightState(String data) {
        if (data.contains(FlightsInfoData.TAG_ON_TIME))
            return true;
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
}
