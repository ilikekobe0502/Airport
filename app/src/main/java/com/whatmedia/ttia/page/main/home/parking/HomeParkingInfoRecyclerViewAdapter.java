package com.whatmedia.ttia.page.main.home.parking;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.response.data.HomeParkingInfoData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by neo_mac on 2017/8/7.
 */

public class HomeParkingInfoRecyclerViewAdapter extends RecyclerView.Adapter<HomeParkingInfoRecyclerViewAdapter.ViewHolder> {

    private List<HomeParkingInfoData> mItems;
    private int mCount = 0;
    private Context mContext;

    public HomeParkingInfoRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_parking_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mItems == null)
            return;
        HomeParkingInfoData item = mItems.get(position);
        if (item == null)
            return;

        String name = "";
        String subName = "";
        if (position == 0) {
            name = mContext.getString(R.string.parking_info_parking_space_P1);
            holder.mTextViewSubName.setVisibility(View.GONE);
        } else if (position == 1) {
            name = mContext.getString(R.string.parking_info_parking_space_P2);
            holder.mTextViewSubName.setVisibility(View.GONE);
        } else if (position == 2) {
            name = mContext.getString(R.string.parking_info_parking_space_P4);
            subName = mContext.getString(R.string.parking_info_parking_space_B1_B2);
            holder.mTextViewSubName.setVisibility(View.VISIBLE);
        } else if (position == 3) {
            name = mContext.getString(R.string.parking_info_parking_space_P4);
            subName = mContext.getString(R.string.parking_info_parking_space_1F);
            holder.mTextViewSubName.setVisibility(View.VISIBLE);
        }
        holder.mTextViewName.setText(name);
        holder.mTextViewSubName.setText(subName);

        String count = "";
        if (!TextUtils.equals(item.getAvailableCar(), "0")) {
            count = mContext.getString(R.string.parking_info_parking_space_number_of, item.getAvailableCar());
            holder.mTextViewCount.setTextColor(ContextCompat.getColor(mContext, android.R.color.white));
        } else {
            count = mContext.getString(R.string.parking_info_parking_space_full_state);
            holder.mTextViewCount.setTextColor(ContextCompat.getColor(mContext, R.color.colorTextHomeParkingInfoFull));
        }

        holder.mTextViewCount.setText(count);

        if (position == mCount - 1)
            holder.mLine.setVisibility(View.GONE);
        else
            holder.mLine.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        mCount = mItems != null ? mItems.size() : 0;
        return mCount;
    }

    public void setData(List<HomeParkingInfoData> data) {
        mItems = data;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textView_name)
        TextView mTextViewName;
        @BindView(R.id.textView_sub_name)
        TextView mTextViewSubName;
        @BindView(R.id.textView_count)
        TextView mTextViewCount;
        @BindView(R.id.layout_info)
        LinearLayout mLayoutInfo;
        @BindView(R.id.line)
        View mLine;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
