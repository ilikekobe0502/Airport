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
import android.view.WindowManager;
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
    private boolean isScreen34Mode;

    public FeatureRecyclerViewAdapter(Context context, List<HomeFeature> items) {
        mItems = items;
        mContext = context;
        mLocale = Preferences.getLocaleSetting(mContext);
        isScreen34Mode = Preferences.checkScreenIs34Mode(mContext);
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

        switch (mLocale){
            case "zh_TW":
            case "zh_CN":
                holder.mTextViewTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContext.getResources().getDimensionPixelSize(R.dimen.sp_pixel_13));
                break;
            default:
                holder.mTextViewTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContext.getResources().getDimensionPixelSize(R.dimen.sp_pixel_8));
                break;
        }

        holder.mTextViewTitle.setText(mContext.getText(item.getTitle()));

        if(isScreen34Mode){
            RelativeLayout.LayoutParams t = new RelativeLayout.LayoutParams(getScreenWidth()/9,getScreenWidth()/9);
            t.addRule(RelativeLayout.CENTER_HORIZONTAL);
            holder.mImageViewIcon.setLayoutParams(t);
        }

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

    public int getScreenWidth(){
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
        //宽度 dm.widthPixels
        //高度 dm.heightPixels
        return  dm.widthPixels ;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
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
}
