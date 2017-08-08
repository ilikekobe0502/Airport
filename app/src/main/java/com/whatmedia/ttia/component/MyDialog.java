package com.whatmedia.ttia.component;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;
import com.whatmedia.ttia.response.data.DialogContentData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by neo_mac on 2017/7/1.
 */

public class MyDialog extends DialogFragment {
    private final static String TAG = MyDialog.class.getSimpleName();

    @BindView(R.id.textView_title)
    TextView mTextViewTitle;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.button_cancel)
    Button mButtonCancel;
    @BindView(R.id.button_ok)
    Button mButtonOk;

    private static MyDialog mMyDialog;
    private IOnItemClickListener mLeftListener;
    private IOnItemClickListener mRightListener;

    private List<DialogContentData> mTableList;
    private String mTitle;
    private int mButtonType;
    private int mRecyclerViewType;
    private MyDialogTableRecyclerViewAdapter mTableAdapter;

    public static MyDialog newInstance() {
        if (mMyDialog == null) {
            mMyDialog = new MyDialog();
        }
        return mMyDialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_dialog, container);
        ButterKnife.bind(this, view);
        return view;
    }


    @OnClick({R.id.button_cancel, R.id.button_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_cancel:
                if (mLeftListener != null)
                    mLeftListener.onClick(view);
                dismiss();
                break;
            case R.id.button_ok:
                if (mRightListener != null)
                    mRightListener.onClick(view);

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

        if (mTableList != null) {
            mTableAdapter = new MyDialogTableRecyclerViewAdapter(mTableList);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            mRecyclerView.setAdapter(mTableAdapter);

        }
    }

    public MyDialog setLeftClickListener(IOnItemClickListener listener) {
        mLeftListener = listener;
        return this;
    }

    public MyDialog setRightClickListener(IOnItemClickListener listener) {
        mRightListener = listener;
        return this;
    }


    public MyDialog setRecyclerContent(List<DialogContentData> list) {
        mTableList = list;
        return this;
    }

    public MyDialog setTitle(String title) {
        mTitle = title;
        return this;
    }
}
