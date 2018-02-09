package com.whatsmedia.ttia.page.main.useful.language;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.whatsmedia.ttia.R;
import com.whatsmedia.ttia.newresponse.data.TravelTypeListData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neo on 2017/11/2.
 */

public class TravelLanguageRecyclerViewAdapter extends RecyclerView.Adapter<TravelLanguageRecyclerViewAdapter.ViewHolder> {

    private IOnItemClickListener mListener;
    private List<TravelTypeListData> mItems;

    private RelativeLayout.LayoutParams mLayoutParamsFrame;
    private LinearLayout.LayoutParams mIconParamsFrame;

    public TravelLanguageRecyclerViewAdapter(Context context, int height) {
        if (height != -1) {
            get43LayoutParams(context, height);
        }
    }

    interface IOnItemClickListener {
        void onClick(View view);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_travel_launguage, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mItems == null)
            return;

        TravelTypeListData item = mItems.get(position);
        if (item == null)
            return;

        if (mLayoutParamsFrame != null) {
            holder.mLayoutAll.setLayoutParams(mLayoutParamsFrame);
        }

        if (mIconParamsFrame != null) {
            holder.mImageViewBackground.setLayoutParams(mIconParamsFrame);
        }

        holder.mTextViewTitle.setText(item.getName());

        holder.mLayoutFrame.setTag(item);

        switch (position) {
            case 0:
                holder.mImageViewBackground.setImageResource(R.drawable.n05_01a);
                break;
            case 1:
                holder.mImageViewBackground.setImageResource(R.drawable.n05_01b);
                break;
            case 2:
                holder.mImageViewBackground.setImageResource(R.drawable.n05_01c);
                break;
            case 3:
                holder.mImageViewBackground.setImageResource(R.drawable.n05_01d);
                break;
            case 4:
                holder.mImageViewBackground.setImageResource(R.drawable.n05_01e);
                break;
            case 5:
                holder.mImageViewBackground.setImageResource(R.drawable.n05_01f);
                break;
            case 6:
                holder.mImageViewBackground.setImageResource(R.drawable.n05_01g);
                break;
            case 7:
                holder.mImageViewBackground.setImageResource(R.drawable.n05_01h);
                break;
            default:
                holder.mImageViewBackground.setImageResource(R.drawable.n05_01a);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size() : 0;
    }

    void setItemClickListener(IOnItemClickListener listener) {
        mListener = listener;
    }

    public void setData(List<TravelTypeListData> data) {
        mItems = data;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView_background)
        ImageView mImageViewBackground;
        @BindView(R.id.textView_title)
        TextView mTextViewTitle;
        @BindView(R.id.layout_all)
        RelativeLayout mLayoutAll;
        @BindView(R.id.layout_frame)
        LinearLayout mLayoutFrame;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }


        @OnClick(R.id.layout_frame)
        public void onViewClicked(View view) {
            if (mListener != null)
                mListener.onClick(view);
        }
    }

    private void get43LayoutParams(Context context, int height) {
        int frameMargin = context.getResources().getDimensionPixelSize(R.dimen.dp_pixel_10);
        mLayoutParamsFrame = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLayoutParamsFrame.setMargins(0, frameMargin, 0, 0);

        //整個Frame Height - 4個 * Frame Margin 高度 / 4行 * 0.66(Icon 所佔比例)
        int iconHeight = (int) ((height - (frameMargin * 4)) / 4 * 0.66);
        mIconParamsFrame = new LinearLayout.LayoutParams(iconHeight, iconHeight);
        mIconParamsFrame.gravity = Gravity.CENTER;
    }
}
