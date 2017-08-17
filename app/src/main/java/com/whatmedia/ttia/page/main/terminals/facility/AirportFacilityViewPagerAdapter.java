package com.whatmedia.ttia.page.main.terminals.facility;

import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.component.MyToolbar;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;
import com.whatmedia.ttia.response.data.AirportFacilityData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by neo_mac on 2017/8/3.
 */

public class AirportFacilityViewPagerAdapter extends PagerAdapter implements IOnItemClickListener, MyToolbar.OnClickListener {
    private final static String TAG = AirportFacilityViewPagerAdapter.class.getSimpleName();

    private AirportFacilityRecyclerViewAdapter mAdapter;
    private IOnItemClickListener mListener;
    private AirportFacilityRecyclerViewAdapter.OnImageLoadingListener mLoadingListener;
    private List<AirportFacilityData> mItems;

    @Override
    public int getCount() {
        return mItems != null ? 2 : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_airport_facility_view_pager, container, false);

        final ViewHolder holder = new ViewHolder(view);
        mAdapter = new AirportFacilityRecyclerViewAdapter(view.getContext());
        holder.mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        holder.mRecyclerView.setAdapter(mAdapter);
        mAdapter.setData(mItems);
        if (position == 0) {
            mAdapter.setTerminal(true);
        } else {
            mAdapter.setTerminal(false);
        }
        mAdapter.setOnclickListener(this);
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

    public void setData(List<AirportFacilityData> response) {
        mItems = response;
        notifyDataSetChanged();
    }

    class ViewHolder {
        @BindView(R.id.recyclerView)
        RecyclerView mRecyclerView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
