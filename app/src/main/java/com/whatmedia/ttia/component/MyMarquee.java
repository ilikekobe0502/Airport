package com.whatmedia.ttia.component;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.whatmedia.ttia.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by neo on 2017/8/7.
 */

public class MyMarquee extends RelativeLayout {
    @BindView(R.id.imageView_icon)
    ImageView mImageViewIcon;
    @BindView(R.id.textView_message)
    TextView mTextViewMessage;

    public MyMarquee(Context context) {
        super(context);
        init();
    }

    public MyMarquee(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyMarquee(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.layout_my_marquee, this);
        ButterKnife.bind(this);
        mTextViewMessage.setSelected(true);
    }

    public MyMarquee setMessage(String message) {
        mTextViewMessage.setText(!TextUtils.isEmpty(message) ? message : "");
        return this;
    }

    public MyMarquee setIconVisibility(int visibility) {
        mImageViewIcon.setVisibility(visibility);
        return this;
    }

    public MyMarquee setIcon(int resource) {
        mImageViewIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), resource));
        return this;
    }
}
