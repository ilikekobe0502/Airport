package com.whatmedia.ttia.page.main.useful.currency;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.component.dialog.MyCurrencyConversionDialog;
import com.whatmedia.ttia.enums.ExchangeRate;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.response.data.ExchangeRateData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CurrencyConversionFragment extends BaseFragment implements CurrencyConversionContract.View {
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
    @BindView(R.id.textView_target_amount)
    TextView mTextViewTargetAmount;
    @BindView(R.id.layout_translate)
    RelativeLayout mLayoutTranslate;

    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private CurrencyConversionContract.Presenter mPresenter;
    private ExchangeRateData mExchangeRateData = new ExchangeRateData();

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

        mPresenter = CurrencyConversionPresenter.getInstance(getContext(), this);

        setSourceState(ExchangeRate.TAG_TWD);
        setTargetState(ExchangeRate.TAG_USD);
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
    public void getExchangeRateSucceed(final ExchangeRateData response) {
        mLoadingView.goneLoadingView();
        if (response != null && !TextUtils.isEmpty(response.getAmount())) {
            mMainActivity.runOnUI(new Runnable() {
                @Override
                public void run() {
                    mTextViewTargetAmount.setText(response.getAmount());
                }
            });
        } else {
            Log.e(TAG, "getExchangeRateSucceed response error");
            mMainActivity.runOnUI(new Runnable() {
                @Override
                public void run() {
                    mTextViewTargetAmount.setText("");
                    showMessage(getString(R.string.data_error));
                }
            });
        }
    }

    @Override
    public void getExchangeRateFailed(String result) {
        mLoadingView.goneLoadingView();
        Log.e(TAG, "getExchangeRateFailed : " + (!TextUtils.isEmpty(result) ? result : ""));
        mMainActivity.runOnUI(new Runnable() {
            @Override
            public void run() {
                mTextViewTargetAmount.setText("");
                showMessage(getString(R.string.data_error));
            }
        });
    }

    @OnClick({R.id.imageView_source_icon, R.id.editText_source_amount, R.id.imageView_target_icon, R.id.layout_translate})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageView_source_icon:
                MyCurrencyConversionDialog dialog = MyCurrencyConversionDialog.newInstance()
                        .clearData()
                        .setTitle(getString(R.string.currency_conversion_dialog_title))
                        .setItemClickListener(new IOnItemClickListener() {
                            @Override
                            public void onClick(View view) {
                                ExchangeRate rate = (ExchangeRate) view.getTag();
                                setSourceState(rate);
                            }
                        });
                dialog.show(getActivity().getFragmentManager(), "dialog");
                break;
            case R.id.editText_source_amount:
                break;
            case R.id.imageView_target_icon:
                dialog = MyCurrencyConversionDialog.newInstance()
                        .clearData()
                        .setTitle(getString(R.string.currency_conversion_dialog_title))
                        .setItemClickListener(new IOnItemClickListener() {
                            @Override
                            public void onClick(View view) {
                                ExchangeRate rate = (ExchangeRate) view.getTag();
                                setTargetState(rate);
                            }
                        });
                dialog.show(getActivity().getFragmentManager(), "dialog");
                break;
            case R.id.layout_translate:
                getTransAPI();
                break;
        }
    }

    /**
     * Get api
     */
    private void getTransAPI() {
        if (!TextUtils.isEmpty(mEditTextSourceAmount.getText().toString()) &&
                !TextUtils.isEmpty(mTextViewSourceCode.getText().toString()) &&
                !TextUtils.isEmpty(mTextViewTargetCode.getText().toString())) {

            mLoadingView.showLoadingView();

            ExchangeRateData data = new ExchangeRateData();
            data.setSource(mTextViewSourceCode.getText().toString());
            data.setTarget(mTextViewTargetCode.getText().toString());
            data.setAmount(mEditTextSourceAmount.getText().toString());
            mPresenter.getExchangeRate(data);
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
}
