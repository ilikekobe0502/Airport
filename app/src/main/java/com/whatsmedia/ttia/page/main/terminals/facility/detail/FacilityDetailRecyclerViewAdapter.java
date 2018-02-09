package com.whatsmedia.ttia.page.main.terminals.facility.detail;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.whatsmedia.ttia.R;
import com.whatsmedia.ttia.connect.ApiConnect;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by neo_mac on 2017/8/5.
 */

public class FacilityDetailRecyclerViewAdapter extends RecyclerView.Adapter<FacilityDetailRecyclerViewAdapter.ViewHolder> {
    private final static String TAG = FacilityDetailRecyclerViewAdapter.class.getSimpleName();

    private List<String> mItems;
    private Context mContext;

    public FacilityDetailRecyclerViewAdapter(Context context, List<String> imageList) {
        mContext = context;
        mItems = imageList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_facility, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final String item = mItems.get(position);
        if (item == null)
            return;

        if (!TextUtils.isEmpty(item)) {
            holder.mImageViewPicture.setVisibility(View.VISIBLE);
            Picasso.with(mContext).load(ApiConnect.TAG_IMAGE_HOST + item).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    holder.mImageViewPicture.setImage(ImageSource.bitmap(bitmap));
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });
        } else {
            holder.mImageViewPicture.setVisibility(View.INVISIBLE);
        }

        holder.mImageViewPicture.setTag(item);
    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView_picture)
        SubsamplingScaleImageView mImageViewPicture;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
