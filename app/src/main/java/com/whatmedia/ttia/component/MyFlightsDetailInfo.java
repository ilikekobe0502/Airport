package com.whatmedia.ttia.component;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.component.dialog.MyDialogStoreSearchRecyclerViewAdapter;
import com.whatmedia.ttia.component.dialog.MyDialogTableRecyclerViewAdapter;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;
import com.whatmedia.ttia.newresponse.data.StoreConditionCodeData;
import com.whatmedia.ttia.response.data.DialogContentData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neo_mac on 2017/12/5.
 */

public class MyFlightsDetailInfo extends RelativeLayout {
    @BindView(R.id.layout_frame)
    RelativeLayout mLayoutFrame;
    @BindView(R.id.textView_title)
    TextView mTextViewTitle;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.imageView_close)
    ImageView mImageViewClose;
    @BindView(R.id.textView_left)
    TextView mTextViewLeft;
    @BindView(R.id.textView_right)
    TextView mTextViewRight;
    @BindView(R.id.textView_center)
    TextView mTextViewCenterButton;
    @BindView(R.id.layout_couple)
    LinearLayout mLayoutButtonCouple;

    public final static int TAG_FLIGHTS_DETAIL = 0; //航班詳細資料
    public final static int TAG_SEARCH_TERMINAL = 1; //航廈
    public final static int TAG_SEARCH_AREA = 2; //區域
    public final static int TAG_SEARCH_FLOWER = 3; //樓層
    public final static int TAG_SEARCH_RESTAURANT_TYPE = 4; //餐廳總類
    public final static int TAG_SEARCH_STORE_TYPE = 5; //商店總類

    private MyDialogTableRecyclerViewAdapter mAdapter = new MyDialogTableRecyclerViewAdapter();
    private MyDialogStoreSearchRecyclerViewAdapter mStoreSearchAdapter = new MyDialogStoreSearchRecyclerViewAdapter();
    private IOnItemClickListener mListener;
    private IOnItemClickListener mItemListener = new IOnItemClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.textView_item:
                    if (mListener != null) {
                        mListener.onClick(view);
                    }
                    break;
            }
            hide();
        }
    };

    public MyFlightsDetailInfo(Context context) {
        super(context);
        init();
    }

    public MyFlightsDetailInfo(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyFlightsDetailInfo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_my_flights_detail_info, this);
        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public MyFlightsDetailInfo setTitle(String title) {
        mTextViewTitle.setText(title);
        return this;
    }

    public MyFlightsDetailInfo setRecyclerContent(int type, Object list) {
        switch (type) {
            case TAG_FLIGHTS_DETAIL:
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.setData((List<DialogContentData>) list);
                break;
            case TAG_SEARCH_TERMINAL:
            case TAG_SEARCH_AREA:
            case TAG_SEARCH_FLOWER:
            case TAG_SEARCH_RESTAURANT_TYPE:
            case TAG_SEARCH_STORE_TYPE:
                mRecyclerView.setAdapter(mStoreSearchAdapter);
                mStoreSearchAdapter.setTerminalCodeData((List<StoreConditionCodeData>) list);
                break;
        }
        return this;
    }

    public MyFlightsDetailInfo setSigleButtonText(String text) {
        mLayoutButtonCouple.setVisibility(GONE);
        mTextViewCenterButton.setVisibility(VISIBLE);
        mTextViewCenterButton.setText(text);
        return this;
    }

    public MyFlightsDetailInfo setItemClickListener(IOnItemClickListener listener) {
        mListener = listener;
        mStoreSearchAdapter.setClickListener(mItemListener);
        return this;
    }

    public MyFlightsDetailInfo show() {
        this.setVisibility(VISIBLE);
        mAdapter.notifyDataSetChanged();
        return this;
    }

    public MyFlightsDetailInfo hide() {
        this.setVisibility(GONE);
        restore();
        return this;
    }

    public MyFlightsDetailInfo setClickListener(IOnItemClickListener listener) {
        mListener = listener;
        return this;
    }

    public MyFlightsDetailInfo setLeftText(String title) {
        mLayoutButtonCouple.setVisibility(VISIBLE);
        mTextViewCenterButton.setVisibility(GONE);
        mTextViewLeft.setText(title);
        return this;
    }

    public MyFlightsDetailInfo setRightText(String title) {
        mLayoutButtonCouple.setVisibility(VISIBLE);
        mTextViewCenterButton.setVisibility(GONE);
        mTextViewRight.setText(title);
        return this;
    }

    public MyFlightsDetailInfo restore() {
        mTextViewLeft.setText(getContext().getString(R.string.alert_btn_cancel));
        mTextViewRight.setText(getContext().getString(R.string.navi_title_flight_notify));
        mLayoutButtonCouple.setVisibility(VISIBLE);
        mTextViewCenterButton.setVisibility(GONE);
        mListener = null;
        return this;
    }

    @OnClick({R.id.imageView_close, R.id.textView_left, R.id.textView_right, R.id.textView_center})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_frame:
                break;
            case R.id.imageView_close:
                hide();
                break;
            case R.id.textView_left:
                if (mListener != null) {
                    mListener.onClick(view);
                }
                hide();
                break;
            case R.id.textView_right:
                if (mListener != null) {
                    mListener.onClick(view);
                }
                hide();
                break;
            case R.id.textView_center:
                if (mListener != null) {
                    mListener.onClick(view);
                }
                hide();
                break;
        }
    }
}
