package com.whatsmedia.ttia.page.main.communication.roaming;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whatsmedia.ttia.R;
import com.whatsmedia.ttia.connect.NewApiConnect;
import com.whatsmedia.ttia.interfaces.IOnItemClickListener;
import com.whatsmedia.ttia.newresponse.data.RoamingServiceData;
import com.whatsmedia.ttia.page.BaseFragment;
import com.whatsmedia.ttia.page.IActivityTools;
import com.whatsmedia.ttia.page.Page;
import com.whatsmedia.ttia.utility.Util;

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

        mPresenter = new RoamingServicePresenter(getContext(), this);
        mLoadingView.showLoadingView();
        mPresenter.getRoamingServiceAPI();

        mRoamingServiceRecyclerAdapter = new RoamingServiceRecyclerAdapter(getContext());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mRoamingServiceRecyclerAdapter);
        mRoamingServiceRecyclerAdapter.setClickListener(new IOnItemClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(RoamingServiceContract.ARG_KEY, mRoamingServiceRecyclerAdapter.getId((int) view.getTag()) + "");
                bundle.putString(RoamingServiceContract.ARG_TITLE, mRoamingServiceRecyclerAdapter.getTitle((int) view.getTag()));
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
    public void getRoamingServiceFailed(final String message, final int status) {
        Log.d(TAG, message);
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