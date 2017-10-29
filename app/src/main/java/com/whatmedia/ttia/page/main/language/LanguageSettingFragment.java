package com.whatmedia.ttia.page.main.language;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.enums.LanguageSetting;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.utility.Preferences;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LanguageSettingFragment extends BaseFragment implements LanguageSettingContract.View, IOnItemClickListener {
    private static final String TAG = LanguageSettingFragment.class.getSimpleName();

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private LanguageSettingContract.Presenter mPresenter;

    private LanguageSettingRecyclerViewAdapter mAdapter;
    private LanguageSetting mSetting;

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

        mAdapter = new LanguageSettingRecyclerViewAdapter(getContext());
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setClickListener(this);
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
    public void editUserLanguageFailed(final String error, boolean timeout) {
        mLoadingView.goneLoadingView();
        if (isAdded() && !isDetached()) {
            if (!timeout) {
                mMainActivity.runOnUI(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialog.Builder(getContext())
                                .setTitle(R.string.note)
                                .setMessage(String.format("%1$s\n%2$s", getString(R.string.server_error), error))
                                .setPositiveButton(R.string.ok, null)
                                .show();
                    }
                });
            } else {
                mMainActivity.runOnUI(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialog.Builder(getContext())
                                .setTitle(R.string.note)
                                .setMessage(getString(R.string.timeout_message))
                                .setPositiveButton(R.string.ok, null)
                                .show();
                    }
                });
            }
        }
    }
}
