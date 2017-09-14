package com.whatmedia.ttia.page.main.useful.language.result;


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

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;
import com.whatmedia.ttia.response.data.LanguageData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TravelLanguageResultRecyclerViewAdapter extends RecyclerView.Adapter<TravelLanguageResultRecyclerViewAdapter.ViewHolder> {
    private final static String TAG = TravelLanguageResultRecyclerViewAdapter.class.getSimpleName();

    private List<LanguageData> mItems;
    private Context mContext;
    private IOnItemClickListener mListener;
//    private OnItemClickListener mClickListener;

    //    public interface OnItemClickListener {
//        void OnClick(View view, int score, int position);
//    }
    public TravelLanguageResultRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    public TravelLanguageResultRecyclerViewAdapter(Context context, List<LanguageData> lists) {
        mContext = context;
        mItems = lists;
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
        LanguageData item = mItems.get(position);
        if (item == null) {
            Log.e(TAG, "item is null");
            return;
        }
        String tw = !TextUtils.isEmpty(item.getTextTW()) ? item.getTextTW() : "";
        String cn = !TextUtils.isEmpty(item.getTextCN()) ? item.getTextCN() : "";
        String jp = !TextUtils.isEmpty(item.getTextJP()) ? item.getTextJP() : "";
        String en = !TextUtils.isEmpty(item.getTextEN()) ? item.getTextEN() : "";

        holder.mText1.setText(tw);
        holder.mText2.setText(cn);
        holder.mText3.setText(jp);
        holder.mText4.setText(en);

        if (position % 2 == 1) {
            holder.layout_bg.setBackgroundColor(Color.parseColor("#40000000"));
        } else {
            holder.layout_bg.setBackgroundColor(Color.TRANSPARENT);
        }

//        holder.mTextTitle.setText(mContext.getString(R.string.useful_quest_number)+item.getQuestId());
//        holder.mTextQuest.setText(item.getQuestContent());

        holder.mImageViewPronunciation.setTag(tw);
    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size() : 0;
    }

//    public void setOnClickListener(OnItemClickListener listener) {
//        mClickListener = listener;
//    }

    public void setData(List<LanguageData> list) {
        mItems = list;
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
