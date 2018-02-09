package com.whatsmedia.ttia.page.main.home.parking;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.whatsmedia.ttia.R;
import com.whatsmedia.ttia.page.BaseFragment;
import com.whatsmedia.ttia.page.IActivityTools;
import com.whatsmedia.ttia.page.main.home.arrive.ArriveFlightsFragment;
import com.whatsmedia.ttia.response.data.HomeParkingInfoData;
import com.whatsmedia.ttia.utility.Preferences;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeParkingInfoFragment extends BaseFragment implements HomeParkingInfoContract.View {
    private static final String TAG = HomeParkingInfoFragment.class.getSimpleName();
    @BindView(R.id.textView_name_P1)
    TextView mTextViewNameP1;
    @BindView(R.id.textView_count_P1)
    TextView mTextViewCountP1;
    @BindView(R.id.textView_name_P2)
    TextView mTextViewNameP2;
    @BindView(R.id.textView_count_P2)
    TextView mTextViewCountP2;
    @BindView(R.id.textView_name_P3)
    TextView mTextViewNameP3;
    @BindView(R.id.textView_sub_name_P3)
    TextView mTextViewSubNameP3;
    @BindView(R.id.textView_count_P3)
    TextView mTextViewCountP3;
    @BindView(R.id.textView_name_P4)
    TextView mTextViewNameP4;
    @BindView(R.id.textView_sub_name_P4)
    TextView mTextViewSubNameP4;
    @BindView(R.id.textView_count_P4)
    TextView mTextViewCountP4;
    @BindView(R.id.loadingView)
    ProgressBar mFragmentLoading;


    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private HomeParkingInfoContract.Presenter mPresenter;
    private boolean is34Mode = false;
    private String mLocale = "";

    private HomeParkingInfoRecyclerViewAdapter mAdapter;
    private ArriveFlightsFragment.IAPIErrorListener errorListener;

    public HomeParkingInfoFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HomeParkingInfoFragment newInstance() {
        HomeParkingInfoFragment fragment = new HomeParkingInfoFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_parking_info, container, false);
        ButterKnife.bind(this, view);

        mPresenter = new HomeParkingInfoPresenter(getContext(), this);
        mFragmentLoading.setVisibility(View.VISIBLE);
        mPresenter.getParkingInfoAPI();
        is34Mode = Preferences.checkScreenIs34Mode(getContext());
        mLocale = Preferences.getLocaleSetting(getContext());

        if (is34Mode && mLocale.equals("en")) {
            mTextViewCountP1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23);
            mTextViewCountP2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23);
            mTextViewCountP3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23);
            mTextViewCountP4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23);
        }

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
//            mLoadingView = (IActivityTools.ILoadingView) context;
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
    public void getParkingInfoSucceed(final List<HomeParkingInfoData> result) {
        if (isAdded() && !isDetached()) {
            if (result != null) {
                mMainActivity.runOnUI(new Runnable() {
                    @Override
                    public void run() {
                        mFragmentLoading.setVisibility(View.GONE);
                        mTextViewNameP1.setText(getString(R.string.parking_info_parking_space_P1));
                        mTextViewNameP2.setText(getString(R.string.parking_info_parking_space_P2));
                        mTextViewNameP3.setText(getString(R.string.parking_info_parking_space_P4));
                        mTextViewSubNameP3.setText(getString(R.string.parking_info_parking_space_B1_B2));
                        mTextViewNameP4.setText(getString(R.string.parking_info_parking_space_P4));
                        mTextViewSubNameP4.setText(getString(R.string.parking_info_parking_space_1F));

                        String count1 = !TextUtils.isEmpty(result.get(0).getAvailableCar()) ? result.get(0).getAvailableCar() : "0";
                        String count2 = !TextUtils.isEmpty(result.get(1).getAvailableCar()) ? result.get(1).getAvailableCar() : "0";
                        String count3 = !TextUtils.isEmpty(result.get(2).getAvailableCar()) ? result.get(2).getAvailableCar() : "0";
                        String count4 = !TextUtils.isEmpty(result.get(3).getAvailableCar()) ? result.get(3).getAvailableCar() : "0";
                        if (!TextUtils.equals(count1, "0")) {
                            mTextViewCountP1.setText(getString(R.string.parking_info_parking_space_number_of, count1));
                            mTextViewCountP1.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
                        } else {
                            mTextViewCountP1.setText(getString(R.string.parking_info_parking_space_full_state));
                            mTextViewCountP1.setTextColor(ContextCompat.getColor(getContext(), R.color.colorTextHomeParkingInfoFull));
                        }
                        if (!TextUtils.equals(count2, "0")) {
                            mTextViewCountP2.setText(getString(R.string.parking_info_parking_space_number_of, count2));
                            mTextViewCountP2.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
                        } else {
                            mTextViewCountP2.setText(getString(R.string.parking_info_parking_space_full_state));
                            mTextViewCountP2.setTextColor(ContextCompat.getColor(getContext(), R.color.colorTextHomeParkingInfoFull));
                        }
                        if (!TextUtils.equals(count3, "0")) {
                            mTextViewCountP3.setText(getString(R.string.parking_info_parking_space_number_of, count3));
                            mTextViewCountP3.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
                        } else {
                            mTextViewCountP3.setText(getString(R.string.parking_info_parking_space_full_state));
                            mTextViewCountP3.setTextColor(ContextCompat.getColor(getContext(), R.color.colorTextHomeParkingInfoFull));
                        }
                        if (!TextUtils.equals(count4, "0")) {
                            mTextViewCountP4.setText(getString(R.string.parking_info_parking_space_number_of, count4));
                            mTextViewCountP4.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
                        } else {
                            mTextViewCountP4.setText(getString(R.string.parking_info_parking_space_full_state));
                            mTextViewCountP4.setTextColor(ContextCompat.getColor(getContext(), R.color.colorTextHomeParkingInfoFull));
                        }
                    }
//                    mAdapter.setData(result);
                });
            } else {
                Log.e(TAG, "getParkingInfoSucceed Response is null");
            }
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }

    @Override
    public void getParkingInfoFailed(final String message, final int status) {
        if (isAdded() && !isDetached()) {
            if (errorListener != null)
                errorListener.errorStatus(status);
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }

    public void setErrorListener(ArriveFlightsFragment.IAPIErrorListener errorListener) {
        this.errorListener = errorListener;
    }
}
