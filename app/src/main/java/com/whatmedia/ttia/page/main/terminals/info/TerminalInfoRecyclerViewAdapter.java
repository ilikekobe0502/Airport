package com.whatmedia.ttia.page.main.terminals.info;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.enums.TerminalInfo;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neo_mac on 2017/8/4.
 */

public class TerminalInfoRecyclerViewAdapter extends RecyclerView.Adapter<TerminalInfoRecyclerViewAdapter.ViewHolder> {
    private final static String TAG = TerminalInfoRecyclerViewAdapter.class.getSimpleName();

    private List<TerminalInfo> mItems = TerminalInfo.getPage();
    private Context mContext;
    private IOnItemClickListener mListener;

    private LinearLayout.LayoutParams mLayoutParamsFrame;
    private int mIconPadding = -1;

    public TerminalInfoRecyclerViewAdapter(Context context, int layoutHeight) {
        mContext = context;

        if (layoutHeight != -1) {
            //扣除上方的Image之後的高度 - (Icon高度 - 字體高度) * 2行 / 4(總共4個margin高度)
            mIconPadding = (layoutHeight -
                    (context.getResources().getDimensionPixelSize(R.dimen.dp_pixel_80)
                            + context.getResources().getDimensionPixelSize(R.dimen.sp_pixel_13))
                            * 2) / 4;
            mLayoutParamsFrame = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mLayoutParamsFrame.setMargins(0, mIconPadding, 0, mIconPadding);
        }
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
        TerminalInfo item = TerminalInfo.getItemByTag(mItems.get(position));
        if (item == null) {
            Log.e(TAG, "item is null");
            return;
        }

        holder.mTextViewTitle.setText(mContext.getText(item.getTitle()));
        holder.mImageViewIcon.setBackground(ContextCompat.getDrawable(mContext, item.getIcon()));

        holder.mImageViewIcon.setTag(item);

        if (mIconPadding != -1 && mLayoutParamsFrame != null) {
            holder.mLayoutFrame.setLayoutParams(mLayoutParamsFrame);
        }
    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size() : 0;
    }

    public void setClickListener(IOnItemClickListener listener) {
        mListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.layout_frame)
        RelativeLayout mLayoutFrame;
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
