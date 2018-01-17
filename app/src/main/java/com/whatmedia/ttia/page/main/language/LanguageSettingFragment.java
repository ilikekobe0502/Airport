package com.whatmedia.ttia.page.main.language;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.connect.NewApiConnect;
import com.whatmedia.ttia.enums.LanguageSetting;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.utility.Preferences;
import com.whatmedia.ttia.utility.Util;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LanguageSettingFragment extends BaseFragment implements LanguageSettingContract.View, IOnItemClickListener {
    private static final String TAG = LanguageSettingFragment.class.getSimpleName();

    @BindView(R.id.layout_frame)
    RelativeLayout mLayoutFrame;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.textView_version)
    TextView mTextViewVersion;
    @BindView(R.id.infoView)
    ImageView mInfoView;

    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private LanguageSettingContract.Presenter mPresenter;

    private LanguageSettingRecyclerViewAdapter mAdapter;
    private LanguageSetting mSetting;

    private RelativeLayout.LayoutParams mImageParamsFrame;
    private boolean mIsScreen34Mode;

    public LanguageSettingFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static LanguageSettingFragment newInstance() {
        LanguageSettingFragment fragment = new LanguageSettingFragment();
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
        View view = inflater.inflate(R.layout.fragment_language_setting_info, container, false);
        ButterKnife.bind(this, view);

        mPresenter = new LanguageSettingPresenter(getContext(), this);
        mIsScreen34Mode = Preferences.checkScreenIs34Mode(getContext());
        return view;
    }

    private void setImage(int h) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(dm);
        mImageParamsFrame = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, h);
        mInfoView.setLayoutParams(mImageParamsFrame);

        setImageSource();

    }

    private void setImageSource() {
        mInfoView.setVisibility(View.VISIBLE);
        mInfoView.setImageResource(R.drawable.bg_08);
    }

    private void setIcon(int height) {
        mAdapter = new LanguageSettingRecyclerViewAdapter(getContext(), height);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mIsScreen34Mode) {
            mLayoutFrame.post(new Runnable() {
                @Override
                public void run() {
                    int frameLayoutHeight = mLayoutFrame.getHeight();
                    int mLayoutHeight = (int) (frameLayoutHeight * 0.33);
                    setImage(mLayoutHeight);
                    setIcon(frameLayoutHeight - mLayoutHeight);
                }
            });
        } else {
            mInfoView.setScaleType(ImageView.ScaleType.FIT_XY);
            setImageSource();
            setIcon(-1);
        }

        String versionName = "";
        int versionCode = 0;
        try {
            versionName = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0).versionName;
            versionCode = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        mTextViewVersion.setText(String.format("Version:%1$s", versionName));
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
    public void onClick(View view) {
        if (view.getTag() instanceof LanguageSetting) {
            mLoadingView.showLoadingView();

            mSetting = (LanguageSetting) view.getTag();
            mPresenter.editUserLanguage(mSetting.ordinal());
        } else {
            Log.e(TAG, "View.getTag() is not instance of FlightInfo");
        }
    }

    @Override
    public void editUserLanguageSuccess() {
        mLoadingView.goneLoadingView();

        Preferences.saveLocaleSetting(getContext(), mSetting.getLocale().toString());
        Intent i = getActivity().getIntent();
        getActivity().finish();
        getActivity().startActivity(i);
    }

    @Override
    public void editUserLanguageFailed(final String error, final int status) {
        mLoadingView.goneLoadingView();
        if (isAdded() && !isDetached()) {
            mMainActivity.runOnUI(new Runnable() {
                @Override
                public void run() {
                    switch (status) {
                        case NewApiConnect.TAG_DEFAULT:
                            showMessage(getString(R.string.server_error));
                            break;
                        case NewApiConnect.TAG_TIMEOUT:
                            Util.showTimeoutDialog(getContext());
                            break;
                        case NewApiConnect.TAG_SOCKET_ERROR:
                            Util.showNetworkErrorDialog(getContext());
                            break;
                    }
                }
            });
        }
    }
}
