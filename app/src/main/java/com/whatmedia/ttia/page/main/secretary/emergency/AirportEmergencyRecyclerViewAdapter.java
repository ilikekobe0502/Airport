package com.whatmedia.ttia.page.main.secretary.emergency;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;
import com.whatmedia.ttia.response.data.UserNewsData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neo_mac on 2017/8/4.
 */

public class AirportEmergencyRecyclerViewAdapter extends RecyclerView.Adapter<AirportEmergencyRecyclerViewAdapter.ViewHolder> {
    private final static String TAG = AirportEmergencyRecyclerViewAdapter.class.getSimpleName();

    private List<UserNewsData> mItems;
    private IOnItemClickListener mListener;
    private Context mContext;

    public AirportEmergencyRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mItems == null) {
            Log.e(TAG, "mItem is null");
            return;
        }
        UserNewsData item = mItems.get(position);
        if (item == null) {
            Log.e(TAG, "item is null");
            return;
        }

        holder.mTextViewDate.setText(!TextUtils.isEmpty(item.getaTime()) ? item.getaTime() : "");
        holder.mTextViewMessage.setText(!TextUtils.isEmpty(item.getContent()) ? item.getContent() : "");

        if ((position + 1) % 2 != 0) {
            holder.mLayoutFrame.setBackgroundColor(ContextCompat.getColor(mContext, R.color.black0_25));
        } else {
            holder.mLayoutFrame.setBackgroundColor(0x00000000);
        }

        holder.mLayoutFrame.setTag(item);
    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size() : 0;
    }

    public void setData(List<UserNewsData> data) {
        mItems = data;
        notifyDataSetChanged();
    }

    public void setOnclick(IOnItemClickListener listener) {
        mListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.layout_frame)
        LinearLayout mLayoutFrame;
        @BindView(R.id.textView_date)
        TextView mTextViewDate;
        @BindView(R.id.textView_message)
        TextView mTextViewMessage;

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
}
