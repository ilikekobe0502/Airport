package com.whatmedia.ttia.component.dialog;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.enums.ExchangeRate;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;
import com.whatmedia.ttia.response.data.AreaCodeData;
import com.whatmedia.ttia.response.data.FloorCodeData;
import com.whatmedia.ttia.response.data.RestaurantCodeData;
import com.whatmedia.ttia.response.data.TerminalCodeData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neo_mac on 2017/8/5.
 */

public class MyCurrencyConversionRecyclerViewAdapter extends RecyclerView.Adapter<MyCurrencyConversionRecyclerViewAdapter.ViewHolder> {
    private List<ExchangeRate> mItems = ExchangeRate.getPage();

    private IOnItemClickListener mListener;
    private Context mContext;

    public MyCurrencyConversionRecyclerViewAdapter(Context context) {
        mContext = context;
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

        ExchangeRate item = mItems.get(position);
        if (item == null)
            return;
        holder.mTextViewItem.setText(mContext.getString(item.getTitle()));
        holder.mTextViewItem.setTag(item);

    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size() : 0;
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
