package com.whatsmedia.ttia.page.main.useful.language.result;


import android.content.Context;
import android.graphics.Color;
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
import com.whatsmedia.ttia.newresponse.data.TravelListData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TravelLanguageResultRecyclerViewAdapter extends RecyclerView.Adapter<TravelLanguageResultRecyclerViewAdapter.ViewHolder> {
    private final static String TAG = TravelLanguageResultRecyclerViewAdapter.class.getSimpleName();

    private List<TravelListData> mItems;
    private Context mContext;
    private IOnItemClickListener mListener;

    public TravelLanguageResultRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_language, parent, false);
//        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mItems == null) {
            Log.e(TAG, "mItem is null");
            return;
        }
        TravelListData item = mItems.get(position);
        if (item == null) {
            Log.e(TAG, "item is null");
            return;
        }
        String tw = !TextUtils.isEmpty(item.getTw()) ? item.getTw() : "";
        String cn = !TextUtils.isEmpty(item.getRoman()) ? item.getRoman() : "";
        String jp = !TextUtils.isEmpty(item.getJp()) ? item.getJp() : "";
        String en = !TextUtils.isEmpty(item.getEn()) ? item.getEn() : "";

        holder.mText1.setText(tw);
        holder.mText2.setText(cn);
        holder.mText3.setText(jp);
        holder.mText4.setText(en);

        if (position % 2 == 1) {
            holder.layout_bg.setBackgroundColor(Color.parseColor("#abaeb5"));
        } else {
            holder.layout_bg.setBackgroundColor(Color.TRANSPARENT);
        }

        holder.mImageViewPronunciation.setTag(tw);
    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size() : 0;
    }

    public void setData(List<TravelListData> list) {
        mItems = list;
        notifyDataSetChanged();
    }

    public void setClickListener(IOnItemClickListener listener) {
        mListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_tw)
        TextView mText1;
        @BindView(R.id.text_us)
        TextView mText2;
        @BindView(R.id.text_jp)
        TextView mText3;
        @BindView(R.id.text_lm)
        TextView mText4;
        @BindView(R.id.layout_bg)
        LinearLayout layout_bg;
        @BindView(R.id.imageView_pronunciation)
        ImageView mImageViewPronunciation;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.imageView_pronunciation)
        public void onViewClicked(View view) {
            if (mListener != null) {
                mListener.onClick(view);
            }
        }

    }
}
