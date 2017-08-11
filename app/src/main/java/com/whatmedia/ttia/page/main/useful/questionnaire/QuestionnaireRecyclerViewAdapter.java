package com.whatmedia.ttia.page.main.useful.questionnaire;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;
import com.whatmedia.ttia.response.data.QuestionnaireData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuestionnaireRecyclerViewAdapter extends RecyclerView.Adapter<QuestionnaireRecyclerViewAdapter.ViewHolder> implements View.OnClickListener{
    private final static String TAG = QuestionnaireRecyclerViewAdapter.class.getSimpleName();

    private List<QuestionnaireData> mItems ;
    private Context mContext;
//    private OnItemClickListener mClickListener;

//    public interface OnItemClickListener {
//        void OnClick(View view, int score, int position);
//    }

    public QuestionnaireRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    public QuestionnaireRecyclerViewAdapter(Context context,List<QuestionnaireData> lists) {
        mContext = context;
        mItems = lists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quest, parent, false);
//        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mItems == null) {
            Log.e(TAG, "mItem is null");
            return;
        }
        QuestionnaireData item = mItems.get(position);
        if (item == null) {
            Log.e(TAG, "item is null");
            return;
        }

        holder.mTextTitle.setText(mContext.getString(R.string.useful_quest_number)+item.getQuestId());
        holder.mTextQuest.setText(item.getQuestContent());
        switch (item.getScore()){
            case 0:
                holder.mViewScore1.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.quest_05_06_no));
                holder.mViewScore2.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.quest_05_06_no));
                holder.mViewScore3.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.quest_05_06_no));
                holder.mViewScore4.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.quest_05_06_no));
                holder.mViewScore5.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.quest_05_06_no));
                break;
            case 1:
                holder.mViewScore1.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.quest_05_06_yes));
                holder.mViewScore2.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.quest_05_06_no));
                holder.mViewScore3.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.quest_05_06_no));
                holder.mViewScore4.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.quest_05_06_no));
                holder.mViewScore5.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.quest_05_06_no));
                break;
            case 2:
                holder.mViewScore1.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.quest_05_06_no));
                holder.mViewScore2.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.quest_05_06_yes));
                holder.mViewScore3.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.quest_05_06_no));
                holder.mViewScore4.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.quest_05_06_no));
                holder.mViewScore5.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.quest_05_06_no));
                break;
            case 3:
                holder.mViewScore1.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.quest_05_06_no));
                holder.mViewScore2.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.quest_05_06_no));
                holder.mViewScore3.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.quest_05_06_yes));
                holder.mViewScore4.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.quest_05_06_no));
                holder.mViewScore5.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.quest_05_06_no));
                break;
            case 4:
                holder.mViewScore1.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.quest_05_06_no));
                holder.mViewScore2.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.quest_05_06_no));
                holder.mViewScore3.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.quest_05_06_no));
                holder.mViewScore4.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.quest_05_06_yes));
                holder.mViewScore5.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.quest_05_06_no));
                break;
            case 5:
                holder.mViewScore1.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.quest_05_06_no));
                holder.mViewScore2.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.quest_05_06_no));
                holder.mViewScore3.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.quest_05_06_no));
                holder.mViewScore4.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.quest_05_06_no));
                holder.mViewScore5.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.quest_05_06_yes));
                break;
        }
        holder.mViewScore1.setOnClickListener(this);
        holder.mViewScore2.setOnClickListener(this);
        holder.mViewScore3.setOnClickListener(this);
        holder.mViewScore4.setOnClickListener(this);
        holder.mViewScore5.setOnClickListener(this);
        holder.mViewScore1.setTag(position);
        holder.mViewScore2.setTag(position);
        holder.mViewScore3.setTag(position);
        holder.mViewScore4.setTag(position);
        holder.mViewScore5.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size() : 0;
    }

//    public void setOnClickListener(OnItemClickListener listener) {
//        mClickListener = listener;
//    }

    public void setData(List<QuestionnaireData> list){
        mItems = list;
    }

    @Override
    public void onClick(View v) {
            int position = (Integer) v.getTag();
        Log.e(TAG,"position:"+position+",v.getId():"+v.getId());
            switch (v.getId()){
                case R.id.img_1:
                    mItems.get(position).setScore(1);
                    notifyDataSetChanged();
                    break;
                case R.id.img_2:
                    mItems.get(position).setScore(2);
                    notifyDataSetChanged();
                    break;
                case R.id.img_3:
                    mItems.get(position).setScore(3);
                    notifyDataSetChanged();
                    break;
                case R.id.img_4:
                    mItems.get(position).setScore(4);
                    notifyDataSetChanged();
                    break;
                case R.id.img_5:
                    mItems.get(position).setScore(5);
                    notifyDataSetChanged();
                    break;
            }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_title)
        TextView mTextTitle;
        @BindView(R.id.text_quest)
        TextView mTextQuest;
        @BindView(R.id.img_1)
        ImageView mViewScore1;
        @BindView(R.id.img_2)
        ImageView mViewScore2;
        @BindView(R.id.img_3)
        ImageView mViewScore3;
        @BindView(R.id.img_4)
        ImageView mViewScore4;
        @BindView(R.id.img_5)
        ImageView mViewScore5;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }


    }

    public String getAnswer(){
        String temp = "";
        for(int i=0;i<mItems.size();i++){
            temp+=mItems.get(i).getQuestId()+"-"+mItems.get(i).getScore();
            if(i != mItems.size()-1){
                temp+=",";
            }
        }
        return temp;
    }


}
