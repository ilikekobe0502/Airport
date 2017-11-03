package com.whatmedia.ttia.page.main.terminals.store.result;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.component.CornorTransform;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;
import com.whatmedia.ttia.newresponse.data.RestaurantInfoData;
import com.whatmedia.ttia.newresponse.data.StoreInfoData;
import com.whatmedia.ttia.utility.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neo_mac on 2017/8/6.
 */

public class StoreSearchResultRecyclerViewAdapter extends RecyclerView.Adapter<StoreSearchResultRecyclerViewAdapter.ViewHolder> {
    private final static String TAG = StoreSearchResultRecyclerViewAdapter.class.getSimpleName();

    private List<RestaurantInfoData> mItems;
    private Context mContext;
    private IOnItemClickListener mListener;
    private int mRadius;
    private List<StoreInfoData> mStoreItems;

    public StoreSearchResultRecyclerViewAdapter(Context context) {
        mContext = context;
        mRadius = mContext.getResources().getDimensionPixelSize(R.dimen.dp_pixel_6);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sotre_search_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mItems != null) {
            RestaurantInfoData item = mItems.get(position);
            if (item == null)
                return;

            holder.mTextViewTerminal.setText(!TextUtils.isEmpty(item.getTerminalsName()) ? StoreInfoData.getSimpleTerminalText(mContext, item.getTerminalsName()) : "");
            holder.mTextViewFloor.setText(!TextUtils.isEmpty(item.getFloorName()) ? StoreInfoData.getFloorShowText(mContext, item.getFloorName()) : "");
            holder.mTextViewTitle.setText(!TextUtils.isEmpty(item.getRestaurantTypeName()) ? item.getRestaurantTypeName() : "");
            holder.mTextViewContent.setText(!TextUtils.isEmpty(item.getContent()) ? item.getContent() : "");
            String pictureUrl;
            if (!TextUtils.isEmpty(item.getImgUrl())) {
                pictureUrl = item.getImgUrl();
                Util.getHttpsPicasso(mContext).load(pictureUrl).transform(new CornorTransform(mRadius, 0)).into(holder.mImageViewPicture);
            } else
                pictureUrl = "";

            Log.d(TAG, "Store image url = " + pictureUrl);
            holder.mLayoutFrame.setTag(item);
        } else if (mStoreItems != null) {

            StoreInfoData storeItem = mStoreItems.get(position);
            if (storeItem == null)
                return;

            holder.mTextViewTerminal.setText(!TextUtils.isEmpty(storeItem.getTerminalsName()) ? StoreInfoData.getSimpleTerminalText(mContext, storeItem.getTerminalsName()) : "");
            holder.mTextViewFloor.setText(!TextUtils.isEmpty(storeItem.getFloorName()) ? StoreInfoData.getFloorShowText(mContext, storeItem.getFloorName()) : "");
            holder.mTextViewTitle.setText(!TextUtils.isEmpty(storeItem.getStoreTypeName()) ? storeItem.getStoreTypeName() : "");
            holder.mTextViewContent.setText(!TextUtils.isEmpty(storeItem.getContent()) ? storeItem.getContent() : "");
            String pictureUrl;
            if (!TextUtils.isEmpty(storeItem.getImgUrl())) {
                pictureUrl = storeItem.getImgUrl();
                Util.getHttpsPicasso(mContext).load(pictureUrl).transform(new CornorTransform(mRadius, 0)).into(holder.mImageViewPicture);
            } else
                pictureUrl = "";

            Log.d(TAG, "Store image url = " + pictureUrl);
            holder.mLayoutFrame.setTag(storeItem);
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (mItems != null)
            count = mItems.size();
        else if (mStoreItems != null) {
            count = mStoreItems.size();
        }
        return count;
    }

    public void setData(List<RestaurantInfoData> data) {
        mItems = data;
        notifyDataSetChanged();
    }

    public void setOnClickListener(IOnItemClickListener listener) {
        mListener = listener;
    }

    public void setStoreData(List<StoreInfoData> storeData) {
        mStoreItems = storeData;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView_picture)
        ImageView mImageViewPicture;
        @BindView(R.id.textView_floor)
        TextView mTextViewFloor;
        @BindView(R.id.textView_title)
        TextView mTextViewTitle;
        @BindView(R.id.textView_content)
        TextView mTextViewContent;
        @BindView(R.id.layout_frame)
        RelativeLayout mLayoutFrame;
        @BindView(R.id.layout_view)
        RelativeLayout mLatoutView;
        @BindView(R.id.temId)
        TextView mTextViewTerminal;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.layout_frame)
        public void onClick(View view) {
            if (mListener != null)
                mListener.onClick(view);
        }
    }
}
