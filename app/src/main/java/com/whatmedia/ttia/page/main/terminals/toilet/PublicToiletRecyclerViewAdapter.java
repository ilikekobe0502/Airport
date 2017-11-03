package com.whatmedia.ttia.page.main.terminals.toilet;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.whatmedia.ttia.R;
import com.whatmedia.ttia.newresponse.data.TerminalsFacilityData;
import com.whatmedia.ttia.utility.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by neo_mac on 2017/8/5.
 */

public class PublicToiletRecyclerViewAdapter extends RecyclerView.Adapter<PublicToiletRecyclerViewAdapter.ViewHolder> {
    private final static String TAG = PublicToiletRecyclerViewAdapter.class.getSimpleName();

    private List<TerminalsFacilityData> mFirstItems;
    private Context mContext;

    public PublicToiletRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_public_toilet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (mFirstItems == null)
            return;

        TerminalsFacilityData item = mFirstItems.get(position);
        if (item == null)
            return;

        holder.mTextViewTitle.setText(!TextUtils.isEmpty(item.getFloorName()) ? item.getFloorName() : "");
        if (!TextUtils.isEmpty(item.getImgUrl())) {
            holder.mImageViewPicture.setVisibility(View.VISIBLE);
            Util.getHttpsPicasso(mContext).load(item.getImgUrl()).into(holder.mImageViewPicture, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    holder.mTextViewLoading.setText("ERROR");
                }
            });
        } else {
            holder.mImageViewPicture.setVisibility(View.INVISIBLE);
            holder.mTextViewLoading.setText("ERROR");
        }
    }

    @Override
    public int getItemCount() {
        return mFirstItems != null ? mFirstItems.size() : 0;
    }

    public void setData(List<TerminalsFacilityData> data) {
        mFirstItems = data;
        notifyDataSetChanged();
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
    }
}
