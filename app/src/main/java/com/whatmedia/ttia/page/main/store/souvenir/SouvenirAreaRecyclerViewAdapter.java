package com.whatmedia.ttia.page.main.store.souvenir;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.component.CornorTransform;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;
import com.whatmedia.ttia.newresponse.data.SouvenirData;
import com.whatmedia.ttia.utility.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SouvenirAreaRecyclerViewAdapter extends RecyclerView.Adapter<SouvenirAreaRecyclerViewAdapter.ViewHolder> {
    private final static String TAG = SouvenirAreaRecyclerViewAdapter.class.getSimpleName();

    public void setmItems(List<SouvenirData> mItems) {
        this.mItems = mItems;
    }

    private List<SouvenirData> mItems;
    private Context mContext;
    private IOnItemClickListener mListener;
    private int mRadius;
    private CornorTransform mCornorTransform;

    public SouvenirAreaRecyclerViewAdapter(Context context) {
        mContext = context;
        mRadius = mContext.getResources().getDimensionPixelSize(R.dimen.dp_pixel_5);
        mCornorTransform = new CornorTransform(mRadius, 0);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_souvenir, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mItems == null) {
            Log.e(TAG, "mItem is null");
            return;
        }
        SouvenirData item = mItems.get(position);
        if (item == null) {
            Log.e(TAG, "item is null");
            return;
        }
        if (!TextUtils.isEmpty(item.getImgPath())) {
            holder.mImageIcon.setVisibility(View.VISIBLE);
            Util.getHttpsPicasso(mContext).load(item.getImgPath()).into(holder.mImageIcon);
        } else {
            holder.mImageIcon.setVisibility(View.INVISIBLE);
        }

        holder.mTextName.setText(item.getName());
        holder.mTextPrice.setText("NT$" + item.getPrice());
        holder.mButtonDes.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size() : 0;
    }

    public void setClickListener(IOnItemClickListener listener) {
        mListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_icon)
        ImageView mImageIcon;
        @BindView(R.id.text_name)
        TextView mTextName;
        @BindView(R.id.text_price)
        TextView mTextPrice;
        @BindView(R.id.bt_des)
        Button mButtonDes;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.bt_des)
        public void onViewClicked(View view) {
            mListener.onClick(view);
        }
    }
}
