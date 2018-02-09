package com.whatsmedia.ttia.page.main.Achievement;


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

import com.whatsmedia.ttia.R;
import com.whatsmedia.ttia.connect.NewApiConnect;
import com.whatsmedia.ttia.interfaces.IOnItemClickListener;
import com.whatsmedia.ttia.newresponse.data.AchievementsData;
import com.whatsmedia.ttia.page.BaseFragment;
import com.whatsmedia.ttia.page.IActivityTools;
import com.whatsmedia.ttia.page.Page;
import com.whatsmedia.ttia.page.main.Achievement.Detail.AchievementDetailContract;
import com.whatsmedia.ttia.utility.Util;

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

        mPresenter = new AchievementPresenter(getContext(), this);

        mAdapter = new AchievementRecyclerViewAdapter(getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setClickListener(this);

        mLoadingView.showLoadingView();
        mPresenter.queryAchievementList();

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
        bundle.putString(AchievementDetailContract.IMAGE_PATH, !TextUtils.isEmpty(data.getImgUrl()) ? data.getImgUrl() : "");
        bundle.putString(AchievementDetailContract.TEXT_DATE, !TextUtils.isEmpty(data.getStartDate()) ? data.getStartDate() : "");
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
    public void queryAchievementListFail(String message, final int status) {
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
}
