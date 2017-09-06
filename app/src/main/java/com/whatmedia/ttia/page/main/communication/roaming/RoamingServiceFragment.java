package com.whatmedia.ttia.page.main.communication.roaming;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.page.Page;
import com.whatmedia.ttia.response.data.RoamingServiceData;
import com.whatmedia.ttia.utility.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RoamingServiceFragment extends BaseFragment implements RoamingServiceContract.View {
    private static final String TAG = RoamingServiceFragment.class.getSimpleName();

    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private RoamingServiceContract.Presenter mPresenter;

    private RoamingServiceRecyclerAdapter mRoamingServiceRecyclerAdapter;

    @BindView(R.id.layout_recycleview)
    RecyclerView mRecyclerView;

    public RoamingServiceFragment() {
        // Required empty public constructor
    }

    public static RoamingServiceFragment newInstance() {
        RoamingServiceFragment fragment = new RoamingServiceFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_roaming_service, container, false);
        ButterKnife.bind(this, view);

        mPresenter = RoamingServicePresenter.getInstance(getContext(), this);
        mLoadingView.showLoadingView();
        mPresenter.getRoamingServiceAPI();

        mRoamingServiceRecyclerAdapter = new RoamingServiceRecyclerAdapter(getContext());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mRoamingServiceRecyclerAdapter);
        mRoamingServiceRecyclerAdapter.setClickListener(new IOnItemClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("key", mRoamingServiceRecyclerAdapter.getId((int) view.getTag()) + "");
                mMainActivity.addFragment(Page.TAG_COMMUNICATION_ROAMING_DETAIL, bundle, true);
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        mMainActivity.getMyToolbar().setOnBackClickListener(null);
        super.onDestroy();
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
    public void getRoamingServiceSucceed(final List<RoamingServiceData> response) {
        mLoadingView.goneLoadingView();
        if (isAdded() && !isDetached()) {
            if (response != null && response.size() > 0) {
                mMainActivity.runOnUI(new Runnable() {
                    @Override
                    public void run() {
                        mRoamingServiceRecyclerAdapter.setData(response);
                        mRoamingServiceRecyclerAdapter.notifyDataSetChanged();
                    }
                });
            } else {
                Log.e(TAG, "Response is error");
            }
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }

    @Override
    public void getRoamingServiceFailed(final String message, boolean timeout) {
        Log.d(TAG, message);
        mLoadingView.goneLoadingView();
        if (isAdded() && !isDetached()) {
            if (timeout) {
                mMainActivity.runOnUI(new Runnable() {
                    @Override
                    public void run() {
                        Util.showTimeoutDialog(getContext());
                    }
                });
            } else {
                mMainActivity.runOnUI(new Runnable() {
                    @Override
                    public void run() {
                        showMessage(message);
                    }
                });
            }
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }
}
