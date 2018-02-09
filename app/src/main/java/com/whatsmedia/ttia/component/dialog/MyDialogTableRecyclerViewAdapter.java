package com.whatsmedia.ttia.component.dialog;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.whatsmedia.ttia.R;
import com.whatsmedia.ttia.response.data.DialogContentData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by neo_mac on 2017/8/4.
 */

public class MyDialogTableRecyclerViewAdapter extends RecyclerView.Adapter<MyDialogTableRecyclerViewAdapter.ViewHolder> {

    private List<DialogContentData> mItems;

    public MyDialogTableRecyclerViewAdapter(List<DialogContentData> mList) {
        mItems = mList;
    }

    public MyDialogTableRecyclerViewAdapter() {

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_dialog, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mItems == null)
            return;

        DialogContentData item = mItems.get(position);
        if (item == null)
            return;

        if (!TextUtils.isEmpty(item.getTitle())) {
//            StringBuilder builder = new StringBuilder();
//            builder.append(item.getTitle()).append("：");

            holder.mTextViewTitle.setText(item.getTitle());
        } else
            holder.mTextViewTitle.setText("");

        holder.mTextViewContent.setText(!TextUtils.isEmpty(item.getContent()) ? item.getContent() : "");
    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size() : 0;
    }

    public void setData(List<DialogContentData> data) {
        mItems = data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textView_title)
        TextView mTextViewTitle;
        @BindView(R.id.textView_content)
        TextView mTextViewContent;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
