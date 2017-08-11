package com.whatmedia.ttia.component.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neo_mac on 2017/7/1.
 */

public class MyWeatherDialog extends DialogFragment {
    private final static String TAG = MyWeatherDialog.class.getSimpleName();

    @BindView(R.id.textView_title)
    TextView mTextViewTitle;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.button_cancel)
    Button mButtonCancel;

    private static MyWeatherDialog mMyDialog;
    private IOnItemClickListener mCancelListener;
    private IOnItemClickListener mItemListener;

    private String mTitle;
    private MyWeatherRecyclerViewAdapter mAdapter;
    private int mRegion = -1;

    public static MyWeatherDialog newInstance() {
        if (mMyDialog == null) {
            mMyDialog = new MyWeatherDialog();
        }
        return mMyDialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store_my_dialog, container);
        ButterKnife.bind(this, view);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }


    @OnClick({R.id.button_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_cancel:
                if (mCancelListener != null)
                    mCancelListener.onClick(view);
                dismiss();
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        setContent();
    }

    private void setContent() {
        mTextViewTitle.setText(!TextUtils.isEmpty(mTitle) ? mTitle : "");
        mAdapter = new MyWeatherRecyclerViewAdapter(getActivity(), mRegion);
        mAdapter.setClickListener(new IOnItemClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemListener != null)
                    mItemListener.onClick(view);
                dismiss();
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
    }

    public MyWeatherDialog setCancelClickListener(IOnItemClickListener listener) {
        mCancelListener = listener;
        return this;
    }

    public MyWeatherDialog setItemClickListener(IOnItemClickListener listener) {
        mItemListener = listener;
        return this;
    }


    public MyWeatherDialog setTitle(String title) {
        mTitle = title;
        return this;
    }

    public MyWeatherDialog setRegion(int region) {
        mRegion = region;
        return this;
    }

    public MyWeatherDialog clearData() {
        mTitle = "";
        mItemListener = null;
        mCancelListener = null;
        return this;
    }
}
