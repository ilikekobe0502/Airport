package com.whatsmedia.ttia.page.main.language;


import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.whatsmedia.ttia.R;
import com.whatsmedia.ttia.enums.LanguageSetting;
import com.whatsmedia.ttia.interfaces.IOnItemClickListener;
import com.whatsmedia.ttia.utility.FontFitTextView;
import com.whatsmedia.ttia.utility.Preferences;
import com.whatsmedia.ttia.utility.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LanguageSettingRecyclerViewAdapter extends RecyclerView.Adapter<LanguageSettingRecyclerViewAdapter.ViewHolder> {
    private final static String TAG = LanguageSettingRecyclerViewAdapter.class.getSimpleName();

    private List<LanguageSetting> mItems = LanguageSetting.getPage();
    private Context mContext;
    private IOnItemClickListener mListener;
    private String mSelectLocale;
    private RelativeLayout.LayoutParams mLayoutParamsFrame;

    public LanguageSettingRecyclerViewAdapter(Context context, int height) {
        mContext = context;
        mSelectLocale = Preferences.getLocaleSetting(context);

        if (height != -1) {
            mLayoutParamsFrame = Util.get43LayoutParams(height, context.getResources().getDimensionPixelSize(R.dimen.dp_pixel_95), 0);
        }
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
        LanguageSetting item = mItems.get(position);
        if (item == null) {
            Log.e(TAG, "item is null");
            return;
        }

        if (mLayoutParamsFrame != null) {
            holder.mLayoutFrame.setLayoutParams(mLayoutParamsFrame);
            holder.mLayoutFrame.setPadding(0, 0, 0, 0);
        }

        if (mSelectLocale.equals(item.getLocale().toString())) {
            holder.mImageViewIcon.setBackground(ContextCompat.getDrawable(mContext, R.drawable.language_setting_08_no));
            holder.mTextViewTitle.setTextColor(ContextCompat.getColor(mContext, R.color.colorTextDefault));
        } else {
            holder.mImageViewIcon.setBackground(ContextCompat.getDrawable(mContext, R.drawable.language_setting_08_off));
            holder.mTextViewTitle.setTextColor(Color.WHITE);
        }

        holder.mTextViewTitle.setText(item.getTitle());

        holder.mImageViewIcon.setTag(item);
    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size() : 0;
    }

    public void setClickListener(IOnItemClickListener listener) {
        mListener = listener;
    }

    public void setData() {
        mSelectLocale = Preferences.getLocaleSetting(mContext);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.layout_frame)
        RelativeLayout mLayoutFrame;
        @BindView(R.id.imageView_icon)
        ImageView mImageViewIcon;
        @BindView(R.id.textView_title)
        FontFitTextView mTextViewTitle;

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
