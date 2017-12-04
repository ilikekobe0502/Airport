package com.whatmedia.ttia.component;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.whatmedia.ttia.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neo_mac on 2017/8/3.
 */

public class MyToolbar extends RelativeLayout {
    @BindView(R.id.textView_title)
    TextView mTextViewTitle;
    @BindView(R.id.textView_left)
    TextView mTextViewLeft;
    @BindView(R.id.textView_more)
    TextView mTextViewMore;
    @BindView(R.id.imageView_icon)
    ImageView mImageViewIcon;
    @BindView(R.id.imageView_more)
    ImageView mImageViewMore;
    @BindView(R.id.imageView_back)
    ImageView mImageViewBack;
    @BindView(R.id.layout_frame)
    RelativeLayout mLayoutFrame;
    @BindView(R.id.layout_more)
    RelativeLayout mLayoutMore;
    @BindView(R.id.imageView_right_single)
    ImageView mImageViewRightSingle;
    @BindView(R.id.layout_right)
    RelativeLayout mLayoutRight;
    @BindView(R.id.layout_area)
    RelativeLayout mLayoutArea;

    private OnClickListener mLeftListener;
    private OnClickListener mMoreListener;
    private OnClickListener mAreaListener;

    public interface OnClickListener {
        void onClick(View v);
    }

    public MyToolbar(Context context) {
        super(context);
        init();
    }

    public MyToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.layout_my_tool_bar, this);
        ButterKnife.bind(this);
    }

    public MyToolbar setBackground(int color) {
        mLayoutFrame.setBackgroundColor(color);
        return this;
    }


    public MyToolbar setTitleText(String title) {
        if (!TextUtils.isEmpty(title)) {
            mTextViewTitle.setVisibility(VISIBLE);
            mTextViewTitle.setText(title);
        } else
            mTextViewTitle.setVisibility(GONE);
        return this;
    }

    public MyToolbar setLeftText(String left) {
        if (!TextUtils.isEmpty(left)) {
            mTextViewLeft.setVisibility(VISIBLE);
            mTextViewLeft.setText(left);
        } else
            mTextViewLeft.setVisibility(GONE);
        return this;
    }

    public MyToolbar setRightText(String right) {
        if (!TextUtils.isEmpty(right)) {
            mLayoutMore.setVisibility(VISIBLE);
            mTextViewMore.setVisibility(VISIBLE);
            mTextViewMore.setText(right);
        } else
            mTextViewMore.setVisibility(GONE);
        return this;
    }

    public MyToolbar setMoreIcon(Drawable drawable) {
        if (drawable != null) {
            mLayoutMore.setVisibility(VISIBLE);
            mImageViewMore.setVisibility(VISIBLE);
            mImageViewMore.setBackground(drawable);
        } else
            mImageViewMore.setVisibility(GONE);
        return this;
    }

    public MyToolbar setLeftIcon(Drawable drawable) {
        if (drawable != null) {
            mImageViewIcon.setVisibility(VISIBLE);
            mImageViewIcon.setBackground(drawable);
        } else
            mImageViewIcon.setVisibility(GONE);
        return this;
    }

    public MyToolbar setBackIcon(Drawable drawable) {
        if (drawable != null) {
            mImageViewBack.setVisibility(VISIBLE);
            mImageViewBack.setBackground(drawable);
        } else
            mImageViewBack.setVisibility(GONE);
        return this;
    }

    public MyToolbar setMoreLayoutVisibility(int visibility) {
        mLayoutRight.setVisibility(visibility);
        return this;
    }

    public MyToolbar setOnAreaClickListener(OnClickListener listener) {
        mAreaListener = listener;
        return this;
    }

    public MyToolbar setAreaLayoutVisibility(int visibility) {
        mLayoutArea.setVisibility(visibility);
        return this;
    }

    public MyToolbar setBackVisibility(int visibility) {
        mImageViewBack.setVisibility(visibility);
        return this;
    }

    public MyToolbar setOnMoreClickListener(OnClickListener listener) {
        mMoreListener = listener;
        return this;
    }

    public MyToolbar setOnRightSingleClickListener(OnClickListener listener) {
        mMoreListener = listener;
        return this;
    }

    public MyToolbar setOnBackClickListener(OnClickListener listener) {
        mLeftListener = listener;
        return this;
    }

    public MyToolbar setRightSingleIcon(Drawable drawable) {
        if (drawable != null) {
            mImageViewRightSingle.setVisibility(VISIBLE);
            mImageViewRightSingle.setBackground(drawable);
        } else
            mImageViewRightSingle.setVisibility(GONE);
        return this;
    }

    public MyToolbar clearState() {
        mTextViewTitle.setVisibility(GONE);
        mTextViewLeft.setVisibility(GONE);
        mTextViewMore.setVisibility(GONE);
        mImageViewIcon.setVisibility(GONE);
        mImageViewMore.setVisibility(GONE);
        mLayoutMore.setVisibility(GONE);
        mImageViewBack.setVisibility(GONE);
        mImageViewBack.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.back));
        mImageViewRightSingle.setVisibility(GONE);
        mLayoutArea.setVisibility(GONE);
        mLayoutRight.setVisibility(VISIBLE);
        mLeftListener = null;
        mMoreListener = null;
        mAreaListener = null;
//        mLayoutRight.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorMoreFrame));
//        mLayoutFrame.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.white));
        return this;
    }

    @OnClick({R.id.layout_more, R.id.imageView_back, R.id.imageView_right_single, R.id.layout_area})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_area:
                if (mAreaListener != null)
                    mAreaListener.onClick(v);
                break;
            case R.id.layout_more:
            case R.id.imageView_right_single:
                if (mMoreListener != null) {
                    mMoreListener.onClick(v);
                }
                break;
            case R.id.imageView_back:
                if (mLeftListener != null) {
                    mLeftListener.onClick(v);
                }
                break;
        }
    }
}
