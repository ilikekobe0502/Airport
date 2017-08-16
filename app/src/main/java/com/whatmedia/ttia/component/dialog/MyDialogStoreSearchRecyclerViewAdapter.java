package com.whatmedia.ttia.component.dialog;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;
import com.whatmedia.ttia.response.data.AreaCodeData;
import com.whatmedia.ttia.response.data.FloorCodeData;
import com.whatmedia.ttia.response.data.RestaurantCodeData;
import com.whatmedia.ttia.response.data.StoreCodeData;
import com.whatmedia.ttia.response.data.TerminalCodeData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neo_mac on 2017/8/5.
 */

public class MyDialogStoreSearchRecyclerViewAdapter extends RecyclerView.Adapter<MyDialogStoreSearchRecyclerViewAdapter.ViewHolder> {
    private List<TerminalCodeData> mTerminalCodeList;
    private List<AreaCodeData> mAreaCodeList;
    private List<FloorCodeData> mFloorCodeList;
    private List<RestaurantCodeData> mRestaurantCodeList;
    private List<StoreCodeData> mStoreCodeList;

    private IOnItemClickListener mListener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_dialog_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mTerminalCodeList != null) {
            TerminalCodeData item = mTerminalCodeList.get(position);
            if (item == null)
                return;

            holder.mTextViewItem.setText(item.getTerminalsName());
            holder.mTextViewItem.setTag(item);
        } else if (mAreaCodeList != null) {
            AreaCodeData item = mAreaCodeList.get(position);
            if (item == null)
                return;

            holder.mTextViewItem.setText(item.getAreaName());
            holder.mTextViewItem.setTag(item);
        } else if (mFloorCodeList != null) {
            FloorCodeData item = mFloorCodeList.get(position);
            if (item == null)
                return;

            holder.mTextViewItem.setText(item.getFloorName());
            holder.mTextViewItem.setTag(item);
        } else if (mRestaurantCodeList != null) {
            RestaurantCodeData item = mRestaurantCodeList.get(position);
            if (item == null)
                return;

            holder.mTextViewItem.setText(item.getRestaurantTypeName());
            holder.mTextViewItem.setTag(item);
        } else if (mStoreCodeList != null) {
            StoreCodeData item = mStoreCodeList.get(position);
            if (item == null)
                return;

            holder.mTextViewItem.setText(item.getStoreTypeName());
            holder.mTextViewItem.setTag(item);
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (mTerminalCodeList != null)
            count = mTerminalCodeList.size();
        else if (mAreaCodeList != null)
            count = mAreaCodeList.size();
        else if (mFloorCodeList != null)
            count = mFloorCodeList.size();
        else if (mRestaurantCodeList != null)
            count = mRestaurantCodeList.size();
        else if (mStoreCodeList != null)
            count = mStoreCodeList.size();
        return count;
    }


    public void setTerminalCodeData(List<TerminalCodeData> list) {
        mTerminalCodeList = list;
        notifyDataSetChanged();
    }

    public void setAreaCodeData(List<AreaCodeData> list) {
        mAreaCodeList = list;
        notifyDataSetChanged();
    }

    public void setFloorCodeData(List<FloorCodeData> list) {
        mFloorCodeList = list;
        notifyDataSetChanged();
    }

    public void setRestaurantCodeData(List<RestaurantCodeData> list) {
        mRestaurantCodeList = list;
        notifyDataSetChanged();
    }

    public void setStoreCodeData(List<StoreCodeData> list) {
        mStoreCodeList = list;
        notifyDataSetChanged();
    }

    public void setClickListener(IOnItemClickListener listener) {
        mListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textView_item)
        TextView mTextViewItem;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.textView_item)
        public void onClick(View view) {
            if (mListener != null)
                mListener.onClick(view);
        }
    }
}
