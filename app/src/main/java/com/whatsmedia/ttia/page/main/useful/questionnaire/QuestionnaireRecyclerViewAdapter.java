package com.whatsmedia.ttia.page.main.useful.questionnaire;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.whatsmedia.ttia.R;
import com.whatsmedia.ttia.interfaces.IOnItemClickListener;
import com.whatsmedia.ttia.newresponse.data.AnswerListData;
import com.whatsmedia.ttia.newresponse.data.QuestionnairesListData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuestionnaireRecyclerViewAdapter extends RecyclerView.Adapter<QuestionnaireRecyclerViewAdapter.ViewHolder> implements View.OnClickListener {
    private final static String TAG = QuestionnaireRecyclerViewAdapter.class.getSimpleName();

    private List<QuestionnairesListData> mItems;
    private Context mContext;
    private IOnItemClickListener mListener;

    public QuestionnaireRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quest, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mItems == null) {
            Log.e(TAG, "mItem is null");
            return;
        }
        QuestionnairesListData item = mItems.get(position);
        if (item == null) {
            Log.e(TAG, "item is null");
            return;
        }

        holder.mTextTitle.setText(mContext.getString(R.string.useful_quest_number) + (position + 1));
        holder.mTextQuest.setText(item.getQuestion());
        if (TextUtils.equals(item.getAnswer(), "0")) {
            holder.mViewScore1.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.quest_05_06_no));
            holder.mViewScore2.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.quest_05_06_no));
            holder.mViewScore3.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.quest_05_06_no));
            holder.mViewScore4.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.quest_05_06_no));
            holder.mViewScore5.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.quest_05_06_no));
        } else if (TextUtils.equals(item.getAnswer(), "1")) {
            holder.mViewScore1.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.quest_05_06_yes));
            holder.mViewScore2.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.quest_05_06_no));
            holder.mViewScore3.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.quest_05_06_no));
            holder.mViewScore4.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.quest_05_06_no));
            holder.mViewScore5.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.quest_05_06_no));
        } else if (TextUtils.equals(item.getAnswer(), "2")) {
            holder.mViewScore1.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.quest_05_06_no));
            holder.mViewScore2.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.quest_05_06_yes));
            holder.mViewScore3.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.quest_05_06_no));
            holder.mViewScore4.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.quest_05_06_no));
            holder.mViewScore5.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.quest_05_06_no));
        } else if (TextUtils.equals(item.getAnswer(), "3")) {
            holder.mViewScore1.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.quest_05_06_no));
            holder.mViewScore2.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.quest_05_06_no));
            holder.mViewScore3.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.quest_05_06_yes));
            holder.mViewScore4.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.quest_05_06_no));
            holder.mViewScore5.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.quest_05_06_no));
        } else if (TextUtils.equals(item.getAnswer(), "4")) {
            holder.mViewScore1.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.quest_05_06_no));
            holder.mViewScore2.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.quest_05_06_no));
            holder.mViewScore3.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.quest_05_06_no));
            holder.mViewScore4.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.quest_05_06_yes));
            holder.mViewScore5.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.quest_05_06_no));
        } else if (TextUtils.equals(item.getAnswer(), "5")) {
            holder.mViewScore1.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.quest_05_06_no));
            holder.mViewScore2.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.quest_05_06_no));
            holder.mViewScore3.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.quest_05_06_no));
            holder.mViewScore4.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.quest_05_06_no));
            holder.mViewScore5.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.quest_05_06_yes));
        } else {
            holder.mViewScore1.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.quest_05_06_no));
            holder.mViewScore2.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.quest_05_06_no));
            holder.mViewScore3.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.quest_05_06_no));
            holder.mViewScore4.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.quest_05_06_no));
            holder.mViewScore5.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.quest_05_06_no));
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

        if (position == mItems.size() - 1) {
            holder.mViewSend.setVisibility(View.VISIBLE);
        } else {
            holder.mViewSend.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size() : 0;
    }

    public void setClickListener(IOnItemClickListener listener) {
        mListener = listener;
    }

    public void setData(List<QuestionnairesListData> list) {
        mItems = list;
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        Log.e(TAG, "position:" + position + ",v.getId():" + v.getId());
        switch (v.getId()) {
            case R.id.img_1:
                mItems.get(position).setAnswer("1");
                notifyDataSetChanged();
                break;
            case R.id.img_2:
                mItems.get(position).setAnswer("2");
                notifyDataSetChanged();
                break;
            case R.id.img_3:
                mItems.get(position).setAnswer("3");
                notifyDataSetChanged();
                break;
            case R.id.img_4:
                mItems.get(position).setAnswer("4");
                notifyDataSetChanged();
                break;
            case R.id.img_5:
                mItems.get(position).setAnswer("5");
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
        @BindView(R.id.view_delete)
        RelativeLayout mViewSend;
        @BindView(R.id.layout_delete)
        RelativeLayout mButtonSend;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.layout_delete)
        public void onViewClicked(View view) {
            if (mListener != null)
                mListener.onClick(view);
        }
    }

    boolean checkAnswer() {
        for (QuestionnairesListData item : mItems) {
            if (TextUtils.isEmpty(item.getAnswer()))
                return false;
        }
        return true;
    }

    List<AnswerListData> getAnswer() {
        List<AnswerListData> answerListData = new ArrayList<>();
        for (QuestionnairesListData item : mItems) {
            answerListData.add(new AnswerListData(item.getId(), item.getAnswer()));
        }
        return answerListData;
    }
}
