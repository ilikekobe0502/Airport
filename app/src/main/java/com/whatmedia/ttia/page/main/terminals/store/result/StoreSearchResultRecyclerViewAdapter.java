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
import com.whatmedia.ttia.connect.ApiConnect;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;
import com.whatmedia.ttia.response.data.RestaurantInfoData;
import com.squareup.picasso.Picasso;

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

    public StoreSearchResultRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sotre_search_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mItems == null)
            return;
        RestaurantInfoData item = mItems.get(position);
        if (item == null)
            return;

        holder.mTextViewFloor.setText(!TextUtils.isEmpty(item.getFloorId()) ? item.getFloorId() : "");
        holder.mTextViewTitle.setText(!TextUtils.isEmpty(item.getRestaurantName()) ? item.getRestaurantName() : "");
        holder.mTextViewContent.setText(!TextUtils.isEmpty(item.getContenct()) ? item.getContenct() : "");
        String pictureUrl;
        if (!TextUtils.isEmpty(item.getImgPath())) {
            pictureUrl = ApiConnect.TAG_IMAGE_HOST + item.getImgPath();
            // TODO: 2017/8/6 corner mot work
            Picasso.with(mContext).load(pictureUrl).into(holder.mImageViewPicture);
        } else
            pictureUrl = "";

        Log.d(TAG, "Store image url = " + pictureUrl);
        holder.mLayoutFrame.setTag(item);
    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size() : 0;
    }

    public void setData(List<RestaurantInfoData> data) {
        mItems = data;
        notifyDataSetChanged();
    }

    public void setOnClickListener(IOnItemClickListener listener) {
        mListener = listener;
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
