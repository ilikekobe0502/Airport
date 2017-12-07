package com.whatmedia.ttia.component;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.component.dialog.MyDialogTableRecyclerViewAdapter;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;
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

    private MyDialogTableRecyclerViewAdapter mAdapter = new MyDialogTableRecyclerViewAdapter();
    private IOnItemClickListener mListener;

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
        mRecyclerView.setAdapter(mAdapter);
    }

    public MyFlightsDetailInfo setTitle(String title) {
        mTextViewTitle.setText(title);
        return this;
    }

    public MyFlightsDetailInfo setRecyclerContent(List<DialogContentData> list) {
        mAdapter.setData(list);
        return this;
    }

    public MyFlightsDetailInfo show() {
        this.setVisibility(VISIBLE);
        mAdapter.notifyDataSetChanged();
        return this;
    }

    public MyFlightsDetailInfo setClickListener(IOnItemClickListener listener) {
        mListener = listener;
        return this;
    }

    public MyFlightsDetailInfo setLeftText(String title) {
        mTextViewLeft.setText(title);
        return this;
    }

    public MyFlightsDetailInfo setRightText(String title) {
        mTextViewRight.setText(title);
        return this;
    }

    public MyFlightsDetailInfo restore() {
        mTextViewLeft.setText(getContext().getString(R.string.alert_btn_cancel));
        mTextViewRight.setText(getContext().getString(R.string.navi_title_flight_notify));
        return this;
    }

    @OnClick({R.id.imageView_close, R.id.textView_left, R.id.textView_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_frame:
                break;
            case R.id.imageView_close:
                this.setVisibility(GONE);
                break;
            case R.id.textView_left:
                if (mListener != null) {
                    mListener.onClick(view);
                }
                this.setVisibility(GONE);
                break;
            case R.id.textView_right:
                if (mListener != null) {
                    mListener.onClick(view);
                }
                this.setVisibility(GONE);
                break;
        }
        restore();
    }
}
