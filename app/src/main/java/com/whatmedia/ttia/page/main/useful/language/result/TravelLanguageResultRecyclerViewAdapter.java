package com.whatmedia.ttia.page.main.useful.language.result;


import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.response.data.LanguageData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TravelLanguageResultRecyclerViewAdapter extends RecyclerView.Adapter<TravelLanguageResultRecyclerViewAdapter.ViewHolder>{
    private final static String TAG = TravelLanguageResultRecyclerViewAdapter.class.getSimpleName();

    private List<LanguageData> mItems ;
    private Context mContext;
//    private OnItemClickListener mClickListener;

//    public interface OnItemClickListener {
//        void OnClick(View view, int score, int position);
//    }

    public TravelLanguageResultRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    public TravelLanguageResultRecyclerViewAdapter(Context context,List<LanguageData> lists) {
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
        holder.mText1.setText(item.getTextTW());
        holder.mText2.setText(item.getTextCN());
        holder.mText3.setText(item.getTextJP());
        holder.mText4.setText(item.getTextEN());

        if(position%2 == 1){
            holder.layout_bg.setBackgroundColor(Color.parseColor("#40000000"));
        }else{
            holder.layout_bg.setBackgroundColor(Color.TRANSPARENT);
        }

//        holder.mTextTitle.setText(mContext.getString(R.string.useful_quest_number)+item.getQuestId());
//        holder.mTextQuest.setText(item.getQuestContent());
    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size() : 0;
    }

//    public void setOnClickListener(OnItemClickListener listener) {
//        mClickListener = listener;
//    }

    public void setData(List<LanguageData> list){
        mItems = list;
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

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }


    }
}
