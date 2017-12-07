package com.whatmedia.ttia.component.dialog;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.whatmedia.ttia.newresponse.data.StoreConditionCodeData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neo_mac on 2017/7/1.
 */

public class MyStoreDialog extends DialogFragment {
    private final static String TAG = MyStoreDialog.class.getSimpleName();

    @BindView(R.id.textView_title)
    TextView mTextViewTitle;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.button_cancel)
    Button mButtonCancel;

    private static MyStoreDialog mMyDialog;
    private IOnItemClickListener mCancelListener;
    private IOnItemClickListener mItemListener;

    private String mTitle;
    private MyDialogStoreSearchRecyclerViewAdapter mSearchAdapter;
    private List<StoreConditionCodeData> mTerminalCodeList;
    private List<StoreConditionCodeData> mAreaCodeList;
    private List<StoreConditionCodeData> mFloorCodeList;
    private List<StoreConditionCodeData> mRestaurantCodeList;
    private List<StoreConditionCodeData> mStoreCodeList;

    public static MyStoreDialog newInstance() {
        if (mMyDialog == null) {
            mMyDialog = new MyStoreDialog();
        }
        return mMyDialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store_my_dialog, container);
        ButterKnife.bind(this, view);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return view;
    }


    @OnClick({R.id.button_cancel,R.id.view_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_cancel:
            case R.id.view_delete:
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
        mSearchAdapter = new MyDialogStoreSearchRecyclerViewAdapter();
        mSearchAdapter.setClickListener(new IOnItemClickListener() {
            @Override
            public void onClick(View view) {
                mItemListener.onClick(view);
                dismiss();
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mSearchAdapter);
        if (mTerminalCodeList != null)
            mSearchAdapter.setTerminalCodeData(mTerminalCodeList);
        if (mAreaCodeList != null)
            mSearchAdapter.setAreaCodeData(mAreaCodeList);
        if (mFloorCodeList != null)
            mSearchAdapter.setFloorCodeData(mFloorCodeList);
        if (mRestaurantCodeList != null)
            mSearchAdapter.setRestaurantCodeData(mRestaurantCodeList);
        if (mStoreCodeList != null)
            mSearchAdapter.setStoreCodeData(mStoreCodeList);
    }

    public MyStoreDialog setCancelClickListener(IOnItemClickListener listener) {
        mCancelListener = listener;
        return this;
    }

    public MyStoreDialog setItemClickListener(IOnItemClickListener listener) {
        mItemListener = listener;
        return this;
    }

    public MyStoreDialog setTitle(String title) {
        mTitle = title;
        return this;
    }

    public MyStoreDialog setTerminalCodeData(List<StoreConditionCodeData> list) {
        clearData();
        mTerminalCodeList = list;
        return this;
    }

    public MyStoreDialog setAreaCodeData(List<StoreConditionCodeData> list) {
        clearData();
        mAreaCodeList = list;
        return this;
    }

    public MyStoreDialog setFloorCodeData(List<StoreConditionCodeData> list) {
        clearData();
        mFloorCodeList = list;
        return this;
    }

    public MyStoreDialog setRestaurantCodeData(List<StoreConditionCodeData> list) {
        clearData();
        mRestaurantCodeList = list;
        return this;
    }

    public MyStoreDialog setStoreCodeData(List<StoreConditionCodeData> list) {
        clearData();
        mStoreCodeList = list;
        return this;
    }

    public MyStoreDialog clearData() {
        mTerminalCodeList = null;
        mAreaCodeList = null;
        mFloorCodeList = null;
        mRestaurantCodeList = null;
        mStoreCodeList = null;
        return this;
    }
}
