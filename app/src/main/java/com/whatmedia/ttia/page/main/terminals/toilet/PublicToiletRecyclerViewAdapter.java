package com.whatmedia.ttia.page.main.terminals.toilet;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.response.data.AirportFacilityData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by neo_mac on 2017/8/5.
 */

public class PublicToiletRecyclerViewAdapter extends RecyclerView.Adapter<PublicToiletRecyclerViewAdapter.ViewHolder> {
    private final static String TAG = PublicToiletRecyclerViewAdapter.class.getSimpleName();

    private List<AirportFacilityData> mFirstItems;
    private List<AirportFacilityData> mSecondItems;
    private Context mContext;
    private boolean mIsFirst;//判斷是否為第一航廈

    public PublicToiletRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_public_toilet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AirportFacilityData item;
        if (mIsFirst) {
            if (mFirstItems == null)
                return;
            else
                item = mFirstItems.get(position);
        } else {
            if (mSecondItems == null)
                return;
            else
                item = mSecondItems.get(position);
        }

        if (item == null)
            return;

        holder.mTextViewTitle.setText(!TextUtils.isEmpty(item.getFloorId()) ? item.getFloorId() : "");
        if (!TextUtils.isEmpty(item.getContent()))
            holder.mWebView.loadData(item.getContent(), "text/html; charset=utf-8", "UTF-8");
        else
            holder.mWebView.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        if (mIsFirst)
            return mFirstItems != null ? mFirstItems.size() : 0;
        else
            return mSecondItems != null ? mSecondItems.size() : 0;
    }

    public String setData(List<AirportFacilityData> data) {
        String outSideTitle = "";
        mIsFirst = true;
        mFirstItems = new ArrayList<>();
        mSecondItems = new ArrayList<>();

        for (AirportFacilityData item : data) {
            if (TextUtils.equals(item.getTerminalsId(), AirportFacilityData.TAG_TERMINAL_FIRST))
                mFirstItems.add(item);
            else
                mSecondItems.add(item);
        }

        notifyDataSetChanged();
        if (mFirstItems.size() > 0)
            outSideTitle = mFirstItems.get(0).getTerminalsName();
        return outSideTitle;
    }

    /**
     * Set different terminal Data
     *
     * @param isFirst true: terminal 1 , false: terminal 2
     */
    public String setTerminal(boolean isFirst) {
        mIsFirst = isFirst;
        notifyDataSetChanged();
        if (isFirst && mFirstItems != null && mFirstItems.size() > 0)
            return mFirstItems.get(0).getTerminalsName();
        else if (!isFirst && mSecondItems != null && mSecondItems.size() > 0)
            return mSecondItems.get(0).getTerminalsName();
        else
            return "";
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textView_title)
        TextView mTextViewTitle;
        @BindView(R.id.webView)
        WebView mWebView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            mWebView.getSettings().setBuiltInZoomControls(true);
            mWebView.getSettings().setDisplayZoomControls(false);
            //TODO 因為怕list的webview太吃效能
        }
    }
}
