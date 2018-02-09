package com.whatsmedia.ttia.page.main.secretary.sweet;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.whatsmedia.ttia.R;
import com.whatsmedia.ttia.interfaces.IOnItemClickListener;
import com.whatsmedia.ttia.interfaces.IOnItemLongClickListener;
import com.whatsmedia.ttia.newresponse.data.UserNewsData;
import com.whatsmedia.ttia.utility.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neo_mac on 2017/8/4.
 */

public class AirportSweetNotifyRecyclerViewAdapter extends RecyclerView.Adapter<AirportSweetNotifyRecyclerViewAdapter.ViewHolder> {
    private final static String TAG = AirportSweetNotifyRecyclerViewAdapter.class.getSimpleName();

    private List<UserNewsData> mItems;
    private IOnItemClickListener mListener;
    private IOnItemLongClickListener mLongListener;
    private Context mContext;
    private boolean mCheckIsShow;
    private List<String> mDeleteList = new ArrayList<>();

    public AirportSweetNotifyRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (mItems == null) {
            Log.e(TAG, "mItem is null");
            return;
        }
        final UserNewsData item = mItems.get(position);
        if (item == null) {
            Log.e(TAG, "item is null");
            return;
        }

        if (mCheckIsShow) {
            holder.mImageViewCheck.setVisibility(View.VISIBLE);
            if (item.getIsSelect()) {
                holder.mImageViewCheck.setBackground(ContextCompat.getDrawable(mContext, R.drawable.a09_yes));
            } else {
                holder.mImageViewCheck.setBackground(ContextCompat.getDrawable(mContext, R.drawable.a09_no));
            }
        } else {
            holder.mImageViewCheck.setVisibility(View.GONE);
        }

        holder.mTextViewDate.setText(!TextUtils.isEmpty(item.getPushTime()) ? Util.justShowDate(item.getPushTime()) : "");
        holder.mTextViewMessage.setText(!TextUtils.isEmpty(item.getTitle()) ? item.getTitle() : "");

        if ((position + 1) % 2 != 0) {
            holder.mLayoutFrame.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorBlockDefault));
        } else {
            holder.mLayoutFrame.setBackgroundColor(Color.TRANSPARENT);
        }

        holder.mImageViewCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getIsSelect()) {
                    item.setIsSelect(false);
                    mDeleteList.remove(item.getId());
                } else {
                    item.setIsSelect(true);
                    mDeleteList.add(item.getId());
                }
                notifyDataSetChanged();
            }
        });

        holder.mLayoutMessage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mLongListener != null)
                    mLongListener.onLongClick(v);
                return true;
            }
        });
        holder.mLayoutMessage.setTag(item);
    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size() : 0;
    }

    public void setData(List<UserNewsData> data) {
        mItems = data;
        if (!mCheckIsShow && mItems != null && mItems.size() > 0) {
            for (UserNewsData item : mItems) {
                item.setIsSelect(false);
            }
        }
        notifyDataSetChanged();
    }

    public void setOnclick(IOnItemClickListener listener) {
        mListener = listener;
    }

    public void setOnLongClick(IOnItemLongClickListener listener) {
        mLongListener = listener;
    }

    public void setCheckShow(boolean show) {
        mCheckIsShow = show;
        notifyDataSetChanged();
    }

    public List<String> getDeleteList() {
        return mDeleteList != null ? mDeleteList : new ArrayList<String>();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.layout_frame)
        LinearLayout mLayoutFrame;
        @BindView(R.id.layout_message)
        LinearLayout mLayoutMessage;
        @BindView(R.id.textView_date)
        TextView mTextViewDate;
        @BindView(R.id.textView_message)
        TextView mTextViewMessage;
        @BindView(R.id.imageView_check)
        ImageView mImageViewCheck;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.layout_message, R.id.imageView_check})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.layout_message:
                    if (mListener != null)
                        mListener.onClick(view);
                    break;
                case R.id.imageView_check:
                    if (mListener != null)
                        mListener.onClick(view);
                    break;
            }
        }
    }
}
