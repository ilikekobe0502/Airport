package com.whatmedia.ttia.page.main.useful.language;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.newresponse.data.TravelTypeListData;

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

        holder.mTextViewTitle.setText(item.getName());

        holder.mLayoutFrame.setTag(item);
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
        @BindView(R.id.layout_frame)
        RelativeLayout mLayoutFrame;

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
}
