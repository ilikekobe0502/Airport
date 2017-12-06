package com.whatmedia.ttia.page.main.Achievement;


import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;
import com.whatmedia.ttia.newresponse.data.AchievementsData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AchievementRecyclerViewAdapter extends RecyclerView.Adapter<AchievementRecyclerViewAdapter.ViewHolder> {
    private final static String TAG = AchievementRecyclerViewAdapter.class.getSimpleName();

    public void setmItems(List<AchievementsData> mItems) {
        this.mItems = mItems;
    }

    private List<AchievementsData> mItems;
    private Context mContext;
    private IOnItemClickListener mListener;
    private final String DONE ="done";

    public AchievementRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_achievement, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mItems == null) {
            Log.e(TAG, "mItem is null");
            return;
        }
        AchievementsData item = mItems.get(position);
        if (item == null) {
            Log.e(TAG, "item is null");
            return;
        }

        if(item.isComplete()){
            holder.mImageIcon.setImageResource(R.drawable.achievement_11_01);
        }else{
            holder.mImageIcon.setImageResource(R.drawable.achievement_11_02);
        }

        if(position%2 == 0){
            holder.mView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.colorAchievementBlock));
        }else{
            holder.mView.setBackgroundColor(Color.TRANSPARENT);
        }

        holder.mTextDate.setText(item.getStartDate());
        holder.mTextTitle.setText(item.getTitle());
        holder.mView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size() : 0;
    }

    public void setClickListener(IOnItemClickListener listener) {
        mListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_icon)
        ImageView mImageIcon;
        @BindView(R.id.text_date)
        TextView mTextDate;
        @BindView(R.id.text_title)
        TextView mTextTitle;
        @BindView(R.id.layout_all)
        LinearLayout mView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.layout_all)
        public void onViewClicked(View view) {
            mListener.onClick(view);
        }
    }
}
