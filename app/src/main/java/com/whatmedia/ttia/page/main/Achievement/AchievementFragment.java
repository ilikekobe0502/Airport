package com.whatmedia.ttia.page.main.Achievement;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.page.Page;
import com.whatmedia.ttia.page.main.Achievement.Detail.AchievementDetailContract;
import com.whatmedia.ttia.response.data.AchievementsData;
import com.whatmedia.ttia.response.data.FlightsInfoData;
import com.whatmedia.ttia.utility.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AchievementFragment extends BaseFragment implements AchievementContract.View, IOnItemClickListener {
    private static final String TAG = AchievementFragment.class.getSimpleName();

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private AchievementContract.Presenter mPresenter;

    private AchievementRecyclerViewAdapter mAdapter;
    private List<AchievementsData> mList;

    public AchievementFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AchievementFragment newInstance() {
        AchievementFragment fragment = new AchievementFragment();
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
        View view = inflater.inflate(R.layout.fragment_store_offer_info, container, false);
        ButterKnife.bind(this, view);

        mPresenter = AchievementPresenter.getInstance(getContext(), this);

        mAdapter = new AchievementRecyclerViewAdapter(getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setClickListener(this);

        if (mPresenter.queryAchievementList())
            mLoadingView.showLoadingView();

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
        AchievementsData data = mList.get(Integer.valueOf(view.getTag().toString()));
        Bundle bundle = new Bundle();
        bundle.putString(AchievementDetailContract.IMAGE_PATH, !TextUtils.isEmpty(data.getImgPath()) ? data.getImgPath() : "");
        bundle.putString(AchievementDetailContract.TEXT_DATE, !TextUtils.isEmpty(data.getDate()) ? data.getDate() : "");
        bundle.putString(AchievementDetailContract.TEXT_TITLE, !TextUtils.isEmpty(data.getTitle()) ? data.getTitle() : "");
        bundle.putString(AchievementDetailContract.TEXT_CONTENT, !TextUtils.isEmpty(data.getContent()) ? data.getContent() : "");
        mMainActivity.addFragment(Page.TAG_ACHIEVEMENT_DETAIL, bundle, true);
    }

    @Override
    public void queryAchievementListSuccess(final List<AchievementsData> list) {
        mLoadingView.goneLoadingView();
        if (isAdded() && !isDetached()) {
            mMainActivity.runOnUI(new Runnable() {
                @Override
                public void run() {
                    mList = list;
                    mAdapter.setmItems(list);
                    mAdapter.notifyDataSetChanged();
                }
            });
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }

    @Override
    public void queryAchievementListFail(String message, boolean timeout) {
        mLoadingView.goneLoadingView();
        if (isAdded() && !isDetached()) {
            if (timeout) {
                mMainActivity.runOnUI(new Runnable() {
                    @Override
                    public void run() {
                        Util.showTimeoutDialog(getContext());
                    }
                });
            }
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }
}
