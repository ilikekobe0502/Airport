package com.whatsmedia.ttia.page.main.traffic;


import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whatsmedia.ttia.R;
import com.whatsmedia.ttia.enums.AirportTraffic;
import com.whatsmedia.ttia.interfaces.IOnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AirportTrafficPagerAdapter extends PagerAdapter implements IOnItemClickListener {
    private final static String TAG = AirportTrafficPagerAdapter.class.getSimpleName();

    private AirportTrafficRecyclerViewAdapter mAdapter;
    private List<List<AirportTraffic>> mItems = AirportTraffic.getPageList();
    private IOnItemClickListener mListener;
    private int mOutFrameHeight = -1;

    public AirportTrafficPagerAdapter(int height) {
        mOutFrameHeight = height;
    }

    @Override
    public int getCount() {
        return mItems.size() % 8;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_home_feature, container, false);

        final ViewHolder holder = new ViewHolder(view);
        mAdapter = new AirportTrafficRecyclerViewAdapter(container.getContext(), mItems.get(position),mOutFrameHeight);
        holder.mRecyclerView.setLayoutManager(new GridLayoutManager(container.getContext(), 2));
        holder.mRecyclerView.setAdapter(mAdapter);
        mAdapter.setClickListener(this);
        container.addView(view);
        return view;
    }

    @Override
    public void onClick(View view) {
        if (mListener != null)
            mListener.onClick(view);
    }

    public void setClickListener(IOnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View) object);
        object = null;
    }

    class ViewHolder {
        @BindView(R.id.recyclerView)
        RecyclerView mRecyclerView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
