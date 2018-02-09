package com.whatsmedia.ttia.component.dialog;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.whatsmedia.ttia.R;
import com.whatsmedia.ttia.interfaces.IOnItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neo_mac on 2017/8/5.
 */

public class MyWeatherRecyclerViewAdapter extends RecyclerView.Adapter<MyWeatherRecyclerViewAdapter.ViewHolder> {
    private String[] mItems;

    private IOnItemClickListener mListener;
    private Context mContext;

    public MyWeatherRecyclerViewAdapter(Context context, int region) {
        mContext = context;
        switch (region) {
            case -1:
                mItems = context.getResources().getStringArray(R.array.weather_region_array);
                break;
            case 0:
                mItems = context.getResources().getStringArray(R.array.weather_taiwan_city_array);
                break;
            case 1:
                mItems = context.getResources().getStringArray(R.array.weather_asia_oceania_city_array);
                break;
            case 2:
                mItems = context.getResources().getStringArray(R.array.weather_america_city_array);
                break;
            case 3:
                mItems = context.getResources().getStringArray(R.array.weather_eurpo_city_array);
                break;
            case 4:
                mItems = context.getResources().getStringArray(R.array.weather_china_city_array);
                break;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_dialog_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (mItems == null)
            return;

        String item = mItems[position];
        if (item == null)
            return;
        holder.mTextViewItem.setText(item);
        holder.mTextViewItem.setTag(position);

    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.length : 0;
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
