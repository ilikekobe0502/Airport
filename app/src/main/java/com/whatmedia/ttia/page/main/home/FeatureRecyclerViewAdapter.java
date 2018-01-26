package com.whatmedia.ttia.page.main.home;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.enums.HomeFeature;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;
import com.whatmedia.ttia.utility.Preferences;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neo_mac on 2017/8/3.
 */

public class FeatureRecyclerViewAdapter extends RecyclerView.Adapter<FeatureRecyclerViewAdapter.ViewHolder> {
    private final static String TAG = FeatureRecyclerViewAdapter.class.getSimpleName();

    private List<HomeFeature> mItems;
    private Context mContext;
    private IOnItemClickListener mListener;
    private String mLocale;
    private boolean mIsScreen34Mode;
    private RelativeLayout.LayoutParams mAllParamsFrame;
    private RelativeLayout.LayoutParams mFrameParamsFrame;
    private RelativeLayout.LayoutParams mImageParamsFrame;

    public FeatureRecyclerViewAdapter(Context context, List<HomeFeature> items) {
        mItems = items;
        mContext = context;
        mLocale = Preferences.getLocaleSetting(mContext);
        mIsScreen34Mode = Preferences.checkScreenIs34Mode(mContext);
        initParams();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_feature_icon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mItems == null) {
            Log.e(TAG, "mItem is null");
            return;
        }
        HomeFeature item = HomeFeature.getItemByTag(mItems.get(position));
        if (item == null) {
            Log.e(TAG, "item is null");
            return;
        }

        switch (mLocale) {
            case "zh_TW":
            case "zh_CN":
                holder.mTextViewTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContext.getResources().getDimensionPixelSize(R.dimen.sp_pixel_14));
                break;
            default:
                holder.mTextViewTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContext.getResources().getDimensionPixelSize(R.dimen.sp_pixel_8));
                break;
        }

        holder.mTextViewTitle.setText(mContext.getText(item.getTitle()));

//        if (mIsScreen34Mode) {
//            RelativeLayout.LayoutParams t = new RelativeLayout.LayoutParams(getScreenWidth() / 9, getScreenWidth() / 9);
//            t.addRule(RelativeLayout.CENTER_HORIZONTAL);
//            holder.mImageViewIcon.setLayoutParams(t);
//        } else {
        holder.mLayoutAll.setLayoutParams(mAllParamsFrame);
        holder.mLayoutFrame.setLayoutParams(mFrameParamsFrame);
        if (mImageParamsFrame != null)
            holder.mImageViewIcon.setLayoutParams(mImageParamsFrame);

        holder.mImageViewIcon.setBackground(ContextCompat.getDrawable(mContext, item.getIcon()));

        holder.mImageViewIcon.setTag(item);
    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size() : 0;
    }

    public void setClickListener(IOnItemClickListener listener) {
        mListener = listener;
    }

    public int getScreenWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
        //宽度 dm.widthPixels
        //高度 dm.heightPixels
        return dm.widthPixels;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.layout_all)
        RelativeLayout mLayoutAll;
        @BindView(R.id.layout_frame)
        RelativeLayout mLayoutFrame;
        @BindView(R.id.imageView_icon)
        ImageView mImageViewIcon;
        @BindView(R.id.textView_title)
        TextView mTextViewTitle;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.imageView_icon)
        public void onClick(View view) {
            if (mListener != null)
                mListener.onClick(view);
        }

    }

    public void initParams() {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
        //宽度 dm.widthPixels
        //高度 dm.heightPixels
        int itemLayoutFrameHeight;
        int itemLayoutFrameWeight = dm.widthPixels / 4;


        //螢幕高 - toolbar高(dp_pixel_50) - toolbar上面的View高(dp_pixel_8) - 跑馬燈高(dp_pixel_42) - Android status bar高 x 上面layout比率4.2/10
        int height = (int) ((dm.heightPixels - mContext.getResources().getDimensionPixelSize(R.dimen.dp_pixel_50)
                - mContext.getResources().getDimensionPixelSize(R.dimen.dp_pixel_8) - mContext.getResources().getDimensionPixelSize(R.dimen.dp_pixel_42)
                - mContext.getResources().getDimensionPixelSize(mContext.getResources().getIdentifier("status_bar_height", "dimen", "android"))) * 0.42);

        //下方畫面高度 / 兩排item - 1.2倍點
        itemLayoutFrameHeight = (height - mContext.getResources().getDimensionPixelSize(R.dimen.dp_pixel_25)) / 2;

        mAllParamsFrame = new RelativeLayout.LayoutParams(itemLayoutFrameWeight, itemLayoutFrameHeight);

        if (mIsScreen34Mode) {
            int imageHeight = (itemLayoutFrameHeight - (mContext.getResources().getDimensionPixelSize(R.dimen.sp_pixel_14) * 2));
            mImageParamsFrame = new RelativeLayout.LayoutParams(imageHeight, imageHeight);
            mImageParamsFrame.addRule(RelativeLayout.CENTER_HORIZONTAL);
        }


        mFrameParamsFrame = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mFrameParamsFrame.addRule(RelativeLayout.CENTER_IN_PARENT);
//        mAllParamsFrame.setMargins(mContext.getResources().getDimensionPixelOffset(R.dimen.dp_pixel_10), 0
//                , mContext.getResources().getDimensionPixelOffset(R.dimen.dp_pixel_10), 0);


//        mParamsBackground = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemLayoutFrameHeight);
//        mParamsBackground.addRule(RelativeLayout.CENTER_HORIZONTAL);
    }

}
