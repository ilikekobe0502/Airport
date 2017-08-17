package com.whatmedia.ttia.page.main.terminals.toilet;

import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.component.MyToolbar;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.page.main.terminals.facility.AirportFacilityViewPagerAdapter;
import com.whatmedia.ttia.response.data.AirportFacilityData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by neo_mac on 2017/8/3.
 */

public class PublicToiletViewPagerAdapter extends PagerAdapter implements IOnItemClickListener, MyToolbar.OnClickListener {
    private final static String TAG = PublicToiletViewPagerAdapter.class.getSimpleName();

    private IOnItemClickListener mListener;
    private List<AirportFacilityData> mItems;
    private PublicToiletFragment mFragment;
    private IActivityTools.ILoadingView mLoadingView;

    public PublicToiletViewPagerAdapter(PublicToiletFragment publicToiletFragment, IActivityTools.ILoadingView loadingView) {
        mFragment = publicToiletFragment;
        mLoadingView = loadingView;
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
            mLoadingView.showLoadingView();
            AirportFacilityData item = mItems.get(position);
            if (!TextUtils.isEmpty(item.getContent())) {
                holder.mWebView.loadData(item.getContent(), "text/html; charset=utf-8", "UTF-8");
                holder.mWebView.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        mLoadingView.goneLoadingView();
                    }
                });
            } else {
                Log.e(TAG, "mTerminalTwo.getContent() is error");
                mFragment.showMessage(mFragment.getString(R.string.data_error));
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

    public void setClickListener(IOnItemClickListener listener) {
        mListener = listener;
    }

    public void setData(List<AirportFacilityData> response) {
        mItems = response;
        notifyDataSetChanged();
    }

    class ViewHolder {
        @BindView(R.id.webView)
        WebView mWebView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);

            mWebView.getSettings().setBuiltInZoomControls(true);
            mWebView.getSettings().setDisplayZoomControls(false);
            mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            mWebView.setBackgroundColor(0);
            mWebView.setInitialScale(50);
        }
    }

}
