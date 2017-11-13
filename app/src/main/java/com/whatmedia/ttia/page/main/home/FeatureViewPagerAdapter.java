package com.whatmedia.ttia.page.main.home;

import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.enums.HomeFeature;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by neo_mac on 2017/8/3.
 */

public class FeatureViewPagerAdapter extends PagerAdapter implements IOnItemClickListener {
    private final static String TAG = FeatureViewPagerAdapter.class.getSimpleName();

    private FeatureRecyclerViewAdapter mAdapter;
    private List<List<HomeFeature>> mItems = HomeFeature.getPageFirst();
    private IOnItemClickListener mListener;

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_home_feature, container, false);

        final ViewHolder holder = new ViewHolder(view);
        mAdapter = new FeatureRecyclerViewAdapter(container.getContext(), mItems.get(position));
        holder.mRecyclerView.setLayoutManager(new GridLayoutManager(container.getContext(), 4));
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
        object=null;
    }

    class ViewHolder {
        @BindView(R.id.recyclerView)
        RecyclerView mRecyclerView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
