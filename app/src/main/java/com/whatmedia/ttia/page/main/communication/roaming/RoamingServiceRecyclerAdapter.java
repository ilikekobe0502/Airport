package com.whatmedia.ttia.page.main.communication.roaming;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;
import com.whatmedia.ttia.newresponse.data.RoamingServiceData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RoamingServiceRecyclerAdapter extends RecyclerView.Adapter<RoamingServiceRecyclerAdapter.ViewHolder> implements View.OnClickListener{

    private final static String TAG = RoamingServiceRecyclerAdapter.class.getSimpleName();

    private List<RoamingServiceData> mItems ;
    private Context mContext;
    private IOnItemClickListener mListener;

    public RoamingServiceRecyclerAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_roamingservice, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mItems == null) {
            Log.e(TAG, "mItem is null");
            return;
        }
        RoamingServiceData item = mItems.get(position);
        if (item == null) {
            Log.e(TAG, "item is null");
            return;
        }

        holder.mTextTitle.setText(item.getTiName());
        holder.itemView.setTag(position);

    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size() : 0;
    }

    public void setData(List<RoamingServiceData> list){
        mItems = list;
    }

    @Override
    public void onClick(View v) {
        if(mListener!=null){
            mListener.onClick(v);
        }
    }

    public void setClickListener(IOnItemClickListener listener) {
        mListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_title)
        TextView mTextTitle;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public int getId(int tag){
        return Integer.valueOf(mItems.get(tag).getTiId());
    }

    public String getTitle(int tag){
        return mItems.get(tag).getTiName();
    }
}
