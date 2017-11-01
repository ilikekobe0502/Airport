package com.whatmedia.ttia.page.main.terminals.toilet;

import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.component.MyToolbar;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;
import com.whatmedia.ttia.newresponse.data.TerminalsFacilityData;
import com.whatmedia.ttia.newresponse.data.ToiletFacilityListData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by neo_mac on 2017/8/3.
 */

public class PublicToiletViewPagerAdapter extends PagerAdapter implements IOnItemClickListener, MyToolbar.OnClickListener {
    private final static String TAG = PublicToiletViewPagerAdapter.class.getSimpleName();

    private IOnItemClickListener mListener;
    private List<ToiletFacilityListData> mItems;

    public PublicToiletViewPagerAdapter() {

    }

    @Override
    public int getCount() {
        return mItems != null ? mItems.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_public_toilet_view_pager, container, false);
        final ViewHolder holder = new ViewHolder(view);
        if (mItems != null) {
            ToiletFacilityListData item = mItems.get(position);
            if (item != null) {
                holder.mAdapter.setData(item.getTerminalsToiletList() != null ? item.getTerminalsToiletList() : new ArrayList<TerminalsFacilityData>());
            }
        }

        container.addView(view);
        return view;
    }

    @Override
    public void onClick(View view) {
        if (mListener != null)
            mListener.onClick(view);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View) object);
        object = null;
    }

    public void setClickListener(IOnItemClickListener listener) {
        mListener = listener;
    }

    public void setData(List<ToiletFacilityListData> response) {
        mItems = response;
        notifyDataSetChanged();
    }

    public String getSubTitle(int position) {
        String title = "";
        if (mItems != null && position < mItems.size() && mItems.get(position) != null) {
            title = mItems.get(position).getTerminalsName();
        }
        return title;
    }

    class ViewHolder {
        @BindView(R.id.recyclerView)
        RecyclerView mRecyclerView;
        private PublicToiletRecyclerViewAdapter mAdapter;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);

            mAdapter = new PublicToiletRecyclerViewAdapter(view.getContext());
            mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            mRecyclerView.setAdapter(mAdapter);
        }
    }

}
