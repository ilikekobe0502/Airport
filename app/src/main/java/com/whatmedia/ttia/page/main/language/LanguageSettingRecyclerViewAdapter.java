package com.whatmedia.ttia.page.main.language;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.enums.UsefulInfo;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LanguageSettingRecyclerViewAdapter extends RecyclerView.Adapter<LanguageSettingRecyclerViewAdapter.ViewHolder> {
    private final static String TAG = LanguageSettingRecyclerViewAdapter.class.getSimpleName();

    private String[] mItems = new String[4];
    private Context mContext;
    private IOnItemClickListener mListener;

    public LanguageSettingRecyclerViewAdapter(Context context) {
        mContext = context;
        mItems[0] = (context.getString(R.string.zhtw));
        mItems[1] = (context.getString(R.string.zhcn));
        mItems[2] = (context.getString(R.string.ja));
        mItems[3] = (context.getString(R.string.en));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_language_setting_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mItems == null) {
            Log.e(TAG, "mItem is null");
            return;
        }
        String item = mItems[position];
        if (item == null) {
            Log.e(TAG, "item is null");
            return;
        }

        holder.mTextViewTitle.setText(item);

        holder.mImageViewIcon.setTag(item);
    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.length : 0;
    }

    public void setClickListener(IOnItemClickListener listener) {
        mListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView_icon)
        ImageView mImageViewIcon;
        @BindView(R.id.textView_title)
        TextView mTextViewTitle;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.imageView_icon)
        public void onViewClicked(View view) {
            if (mListener != null)
                mListener.onClick(view);
        }
    }
}