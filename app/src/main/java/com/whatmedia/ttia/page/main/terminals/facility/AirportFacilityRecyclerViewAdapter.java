package com.whatmedia.ttia.page.main.terminals.facility;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.whatmedia.ttia.R;
import com.whatmedia.ttia.connect.ApiConnect;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;
import com.whatmedia.ttia.response.data.AirportFacilityData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neo_mac on 2017/8/5.
 */

public class AirportFacilityRecyclerViewAdapter extends RecyclerView.Adapter<AirportFacilityRecyclerViewAdapter.ViewHolder> {
    private final static String TAG = AirportFacilityRecyclerViewAdapter.class.getSimpleName();

    private List<AirportFacilityData> mFirstItems;
    private List<AirportFacilityData> mSecondItems;
    private Context mContext;
    private boolean mIsFirst;//判斷是否為第一航廈
    private IOnItemClickListener mListener;

    public AirportFacilityRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_airport_facility, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        AirportFacilityData item;
        if (mIsFirst) {
            if (mFirstItems == null)
                return;
            else
                item = mFirstItems.get(position);
        } else {
            if (mSecondItems == null)
                return;
            else
                item = mSecondItems.get(position);
        }

        if (item == null)
            return;

        holder.mTextViewTitle.setText(!TextUtils.isEmpty(item.getFloorName()) ? item.getFloorName() : "");

        if (!TextUtils.isEmpty(item.getMainImgPath())) {
            holder.mImageViewPicture.setVisibility(View.VISIBLE);
            String imageUrl = ApiConnect.TAG_IMAGE_HOST + item.getMainImgPath();
            Log.d(TAG, imageUrl);
            setPicassoRetry(holder, imageUrl);
        } else
            holder.mImageViewPicture.setVisibility(View.INVISIBLE);

        holder.mImageViewPicture.setTag(item);
    }

    @Override
    public int getItemCount() {
        if (mIsFirst)
            return mFirstItems != null ? mFirstItems.size() : 0;
        else
            return mSecondItems != null ? mSecondItems.size() : 0;
    }

    public String setData(List<AirportFacilityData> data) {
        String outSideTitle = "";
        mIsFirst = true;
        mFirstItems = new ArrayList<>();
        mSecondItems = new ArrayList<>();

        for (AirportFacilityData item : data) {
            if (!TextUtils.isEmpty(item.getTerminalsName())) {
                if (item.getTerminalsName().contains("一"))
                    mFirstItems.add(item);
                else if (item.getTerminalsName().contains("二"))
                    mSecondItems.add(item);
                else if (item.getTerminalsName().contains("1"))
                    mFirstItems.add(item);
                else if (item.getTerminalsName().contains("2"))
                    mSecondItems.add(item);
            }
        }

        notifyDataSetChanged();
        if (mFirstItems.size() > 0)
            outSideTitle = mFirstItems.get(0).getTerminalsName();
        return outSideTitle;
    }

    public void setOnclickListener(IOnItemClickListener listener) {
        mListener = listener;
    }

    /**
     * Set different terminal Data
     *
     * @param isFirst true: terminal 1 , false: terminal 2
     */
    public String setTerminal(boolean isFirst) {
        mIsFirst = isFirst;
        notifyDataSetChanged();
        if (isFirst && mFirstItems != null && mFirstItems.size() > 0)
            return mFirstItems.get(0).getTerminalsName();
        else if (!isFirst && mSecondItems != null && mSecondItems.size() > 0)
            return mSecondItems.get(0).getTerminalsName();
        else
            return "";
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textView_title)
        TextView mTextViewTitle;
        @BindView(R.id.textView_loading)
        TextView mTextViewLoading;
        @BindView(R.id.imageView_picture)
        ImageView mImageViewPicture;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.imageView_picture)
        public void onViewClicked(View view) {
            if (mListener != null)
                mListener.onClick(view);
        }
    }

    /**
     * 不要改！！因為邏輯不同
     *
     * @param holder
     * @param url
     */
    private void setPicassoRetry(final ViewHolder holder, final String url) {
        Picasso.with(mContext).load(url).into(holder.mImageViewPicture, new Callback() {
            @Override
            public void onSuccess() {
                holder.mTextViewLoading.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                setPicassoRetry(holder, url);
            }
        });
    }
}
