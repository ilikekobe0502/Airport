package com.whatsmedia.ttia.page.main.traffic;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.whatsmedia.ttia.R;
import com.whatsmedia.ttia.enums.AirportTraffic;
import com.whatsmedia.ttia.interfaces.IOnItemClickListener;
import com.whatsmedia.ttia.utility.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neo_mac on 2017/8/4.
 */

public class AirportTrafficRecyclerViewAdapter extends RecyclerView.Adapter<AirportTrafficRecyclerViewAdapter.ViewHolder> {
    private final static String TAG = AirportTrafficRecyclerViewAdapter.class.getSimpleName();

    private List<AirportTraffic> mItems = AirportTraffic.getPage();
    private Context mContext;
    private IOnItemClickListener mListener;
    private RelativeLayout.LayoutParams mLayoutParamsFrame;

    public AirportTrafficRecyclerViewAdapter(Context context, List<AirportTraffic> items, int mOutFrameHeight) {
        mContext = context;
        mItems = items;

        if (mOutFrameHeight != -1) {
            mLayoutParamsFrame = Util.get43LayoutParams(context, mOutFrameHeight);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feature, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mItems == null) {
            Log.e(TAG, "mItem is null");
            return;
        }
        AirportTraffic item = AirportTraffic.getItemByTag(mItems.get(position));
        if (item == null) {
            Log.e(TAG, "item is null");
            return;
        }

        if (mLayoutParamsFrame != null) {
            holder.mLayoutFrame.setLayoutParams(mLayoutParamsFrame);
        }

        holder.mTextViewTitle.setText(mContext.getText(item.getTitle()));
        holder.mImageViewIcon.setBackground(ContextCompat.getDrawable(mContext, item.getIcon()));

        holder.mImageViewIcon.setTag(item);
    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size() : 0;
    }

    public void setClickListener(IOnItemClickListener listener) {
        mListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.layout_frame)
        RelativeLayout mLayoutFrame;
        @BindView(R.id.imageView_icon)
        ImageView mImageViewIcon;
        @BindView(R.id.textView_title)
        TextView mTextViewTitle;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.imageView_icon)
        public void onViewClicked(View view) {
            mListener.onClick(view);
        }
    }
}
