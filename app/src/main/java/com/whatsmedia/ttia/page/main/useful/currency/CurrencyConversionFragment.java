package com.whatsmedia.ttia.page.main.useful.currency;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.whatsmedia.ttia.R;
import com.whatsmedia.ttia.connect.NewApiConnect;
import com.whatsmedia.ttia.enums.ExchangeRate;
import com.whatsmedia.ttia.newresponse.data.ExchangeRateQueryData;
import com.whatsmedia.ttia.page.BaseFragment;
import com.whatsmedia.ttia.page.IActivityTools;
import com.whatsmedia.ttia.utility.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CurrencyConversionFragment extends BaseFragment implements CurrencyConversionContract.View, TextView.OnEditorActionListener, View.OnFocusChangeListener {
    private static final String TAG = CurrencyConversionFragment.class.getSimpleName();
    @BindView(R.id.imageView_source_icon)
    ImageView mImageViewSourceIcon;
    @BindView(R.id.textView_source_code)
    TextView mTextViewSourceCode;
    @BindView(R.id.imageView_target_icon)
    ImageView mImageViewTargetIcon;
    @BindView(R.id.textView_target_code)
    TextView mTextViewTargetCode;
    @BindView(R.id.editText_source_amount)
    EditText mEditTextSourceAmount;
    @BindView(R.id.editText_target_amount)
    TextView mEditTextTargetAmount;
    @BindView(R.id.layout_ok)
    RelativeLayout mLayoutOk;
    @BindView(R.id.number_picker_left)
    NumberPicker mNumberPickerLeft;
    @BindView(R.id.layout_selector)
    RelativeLayout mLayoutSelector;

    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private CurrencyConversionContract.Presenter mPresenter;
    private boolean mIsClickBottom;
    private ExchangeRateQueryData mQueryData = new ExchangeRateQueryData();//上傳的Object

    public CurrencyConversionFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CurrencyConversionFragment newInstance() {
        CurrencyConversionFragment fragment = new CurrencyConversionFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_currency_conversion, container, false);
        ButterKnife.bind(this, view);

        mPresenter = new CurrencyConversionPresenter(getContext(), this);

        setSourceState(ExchangeRate.TAG_TWD);
        setTargetState(ExchangeRate.TAG_USD);

        mEditTextSourceAmount.setOnEditorActionListener(this);
        mEditTextTargetAmount.setOnEditorActionListener(this);

        mEditTextSourceAmount.setOnFocusChangeListener(this);
        mEditTextTargetAmount.setOnFocusChangeListener(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        mMainActivity.getMyToolbar().setOnBackClickListener(null);
        super.onDestroy();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mLoadingView = (IActivityTools.ILoadingView) context;
            mMainActivity = (IActivityTools.IMainActivity) context;
        } catch (ClassCastException castException) {
            Log.e(TAG, castException.toString());
            /** The activity does not implement the listener. */
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void getExchangeRateSucceed(final float response) {
        mLoadingView.goneLoadingView();
        if (isAdded() && !isDetached()) {
            mMainActivity.runOnUI(new Runnable() {
                @Override
                public void run() {
                    if (!mIsClickBottom)
                        mEditTextTargetAmount.setText(String.format("%.6f", response));
                    else
                        mEditTextSourceAmount.setText(String.format("%.6f", response));
                }
            });
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }

    @Override
    public void getExchangeRateFailed(String result, final int status) {
        mLoadingView.goneLoadingView();
        Log.e(TAG, "getExchangeRateFailed : " + (!TextUtils.isEmpty(result) ? result : ""));
        if (isAdded() && !isDetached()) {
            mMainActivity.runOnUI(new Runnable() {
                @Override
                public void run() {
                    switch (status) {
                        case NewApiConnect.TAG_DEFAULT:
                            mEditTextTargetAmount.setText("");
                            showMessage(getString(R.string.server_error));
                            break;
                        case NewApiConnect.TAG_TIMEOUT:
                            if (getContext() != null && isAdded() && !isDetached())
                                Util.showTimeoutDialog(getContext());
                            break;
                        case NewApiConnect.TAG_SOCKET_ERROR:
                            if (getContext() != null && isAdded() && !isDetached())
                                Util.showNetworkErrorDialog(getContext());
                            break;
                    }
                }
            });
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }

    @OnClick({R.id.imageView_source_icon, R.id.editText_source_amount, R.id.imageView_target_icon, R.id.layout_ok, R.id.editText_target_amount})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageView_source_icon:
                Util.hideSoftKeyboard(view);
                if (mLayoutSelector.isShown())
                    mLayoutSelector.setVisibility(View.GONE);
                else {
                    mLayoutSelector.setVisibility(View.VISIBLE);
                    setPicker(true);
                }
                break;
            case R.id.editText_source_amount:
                break;
            case R.id.imageView_target_icon:
                Util.hideSoftKeyboard(view);
                if (mLayoutSelector.isShown())
                    mLayoutSelector.setVisibility(View.GONE);
                else {
                    mLayoutSelector.setVisibility(View.VISIBLE);
                    setPicker(false);
                }
                break;
            case R.id.editText_target_amount:
                break;
//            case R.id.layout_translate:
//                mLayoutSelector.setVisibility(View.GONE);
//                getTransAPI();
//                break;
            case R.id.layout_ok:
                mLayoutSelector.setVisibility(View.GONE);
                getTransAPI();
                break;
        }
    }

    /**
     * Get api
     */
    private void getTransAPI() {
        if (!TextUtils.isEmpty(mTextViewSourceCode.getText().toString()) && !TextUtils.isEmpty(mTextViewTargetCode.getText().toString())) {

            mLoadingView.showLoadingView();

            if (!mIsClickBottom) {
                mQueryData.setSource(mTextViewSourceCode.getText().toString());
                mQueryData.setTarget(mTextViewTargetCode.getText().toString());
                mQueryData.setAmount(!TextUtils.isEmpty(mEditTextSourceAmount.getText().toString()) ? Float.parseFloat(mEditTextSourceAmount.getText().toString()) : 0);
            } else {
                mQueryData.setSource(mTextViewTargetCode.getText().toString());
                mQueryData.setTarget(mTextViewSourceCode.getText().toString());
                mQueryData.setAmount(!TextUtils.isEmpty(mEditTextTargetAmount.getText().toString()) ? Float.parseFloat(mEditTextTargetAmount.getText().toString()) : 0);
            }
            mPresenter.getExchangeRate(mQueryData);
        } else {
            Log.d(TAG, "some data not ok");
        }
    }

    /**
     * Set source state
     *
     * @param rate
     */
    private void setSourceState(ExchangeRate rate) {
        mImageViewSourceIcon.setBackground(ContextCompat.getDrawable(getContext(), ExchangeRate.getItemByTag(rate).getIcon()));
        mTextViewSourceCode.setText(getString(ExchangeRate.getItemByTag(rate).getTitle()));
    }

    /**
     * Set target state
     *
     * @param rate
     */
    private void setTargetState(ExchangeRate rate) {
        mImageViewTargetIcon.setBackground(ContextCompat.getDrawable(getContext(), ExchangeRate.getItemByTag(rate).getIcon()));
        mTextViewTargetCode.setText(getString(ExchangeRate.getItemByTag(rate).getTitle()));
    }

    /**
     * set Left picker
     *
     * @param source
     */
    private void setPicker(final boolean source) {
        String[] data = getContext().getResources().getStringArray(R.array.rateexchange_array);
        Util.setNumberPickerTextColor(mNumberPickerLeft, ContextCompat.getColor(getContext(), android.R.color.white));
        mNumberPickerLeft.setMinValue(0);
        mNumberPickerLeft.setMaxValue(data.length - 1);
        mNumberPickerLeft.setWrapSelectorWheel(false);
        mNumberPickerLeft.setDisplayedValues(data);
        mNumberPickerLeft.setValue(0);
        if (source) {
            setSourceState(0);
        } else {
            setTargetState(0);
        }
        mNumberPickerLeft.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Log.d("TAG", "old = " + oldVal + " new = " + newVal);
                if (source) {
                    setSourceState(newVal);
                } else {
                    setTargetState(newVal);
                }
            }
        });
    }

    /**
     * Set source state
     *
     * @param position
     */
    private void setSourceState(int position) {
        ExchangeRate rate = ExchangeRate.getItemByPosition(position);
        setSourceState(rate);
    }

    /**
     * Set target state
     *
     * @param position
     */
    private void setTargetState(int position) {
        ExchangeRate rate = ExchangeRate.getItemByPosition(position);
        setTargetState(rate);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        getTransAPI();
        Util.hideSoftKeyboard(v);
        return true;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.editText_source_amount:
                mIsClickBottom = false;
                break;
            case R.id.editText_target_amount:
                mIsClickBottom = true;
                break;
        }
    }
}
