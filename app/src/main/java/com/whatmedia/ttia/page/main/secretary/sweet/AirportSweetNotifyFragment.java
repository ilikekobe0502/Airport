package com.whatmedia.ttia.page.main.secretary.sweet;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.whatmedia.ttia.response.data.UserNewsData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AirportSweetNotifyFragment extends BaseFragment implements AirportSweetNotifyContract.View, IOnItemClickListener {
    private static final String TAG = AirportSweetNotifyFragment.class.getSimpleName();

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private AirportSweetNotifyContract.Presenter mPresenter;

    private AirportSweetNotifyRecyclerViewAdapter mAdapter;

    public AirportSweetNotifyFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AirportSweetNotifyFragment newInstance() {
        AirportSweetNotifyFragment fragment = new AirportSweetNotifyFragment();
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
        View view = inflater.inflate(R.layout.fragment_flight_info, container, false);
        ButterKnife.bind(this, view);

        mPresenter = AirportSweetNotifyPresenter.getInstance(getContext(), this);

        mLoadingView.showLoadingView();
        mPresenter.getSweetNotifyAPI();

        mAdapter = new AirportSweetNotifyRecyclerViewAdapter(getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnclick(this);
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
    public void getSweetNotifySucceed(final List<UserNewsData> list) {
        mLoadingView.goneLoadingView();
        mMainActivity.runOnUI(new Runnable() {
            @Override
            public void run() {
                mAdapter.setData(list);
            }
        });
    }

    @Override
    public void getSweetNotifyFailed(final String message) {
        mLoadingView.goneLoadingView();
        Log.e(TAG, "getEmergencyFailed error : " + message);
        mMainActivity.runOnUI(new Runnable() {
            @Override
            public void run() {
                showMessage(message);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_frame:
//                mMainActivity.addFragment(Page);
                break;
        }
    }
}
