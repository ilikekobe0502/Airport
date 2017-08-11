package com.whatmedia.ttia.page.main.useful.info;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.enums.TerminalInfo;
import com.whatmedia.ttia.enums.UsefulInfo;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;
import com.whatmedia.ttia.page.main.terminals.info.TerminalInfoRecyclerViewAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UsefulInfoRecyclerViewAdapter extends RecyclerView.Adapter<UsefulInfoRecyclerViewAdapter.ViewHolder> {
    private final static String TAG = UsefulInfoRecyclerViewAdapter.class.getSimpleName();

    private List<UsefulInfo> mItems = UsefulInfo.getPage();
    private Context mContext;
    private IOnItemClickListener mListener;

    public UsefulInfoRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feature, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mItems == null) {
            Log.e(TAG, "mItem is null");
            return;
        }
        UsefulInfo item = UsefulInfo.getItemByTag(mItems.get(position));
        if (item == null) {
            Log.e(TAG, "item is null");
            return;
        }

        holder.mTextViewTitle.setText(mContext.getText(item.getTitle()));
        holder.mImageViewIcon.setBackground(ContextCompat.getDrawable(mContext, item.getIcon()));

        holder.mImageViewIcon.setTag(item);
    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size() : 0;
    }

    public void setClickListener(IOnItemClickListener listener) {
        mListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView_icon)
        ImageView mImageViewIcon;
        @BindView(R.id.textView_title)
        TextView mTextViewTitle;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.imageView_icon)
        public void onViewClicked(View view) {
            mListener.onClick(view);
        }
    }
}
