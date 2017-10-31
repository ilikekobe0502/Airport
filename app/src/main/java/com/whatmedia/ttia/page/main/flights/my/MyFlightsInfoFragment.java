package com.whatmedia.ttia.page.main.flights.my;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.whatmedia.ttia.R;
import com.whatmedia.ttia.component.dialog.MyDialog;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;
import com.whatmedia.ttia.newresponse.data.FlightsListData;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.response.data.DialogContentData;
import com.whatmedia.ttia.utility.Preferences;
import com.whatmedia.ttia.utility.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyFlightsInfoFragment extends BaseFragment implements MyFlightsInfoContract.View, IOnItemClickListener {
    private static final String TAG = MyFlightsInfoFragment.class.getSimpleName();
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.layout_delete)
    RelativeLayout mLayoutDelete;

    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private MyFlightsInfoContract.Presenter mPresenter;
    private List<FlightsListData> mSelectList;
    private boolean mIsInsert;//是否從新增過來，更新notification 佇列


    private MyFlightsInfoRecyclerViewAdapter mAdapter;

    public MyFlightsInfoFragment() {
        // Required empty public constructor
    }

    public static MyFlightsInfoFragment newInstance() {
        MyFlightsInfoFragment fragment = new MyFlightsInfoFragment();
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
        View view = inflater.inflate(R.layout.fragment_my_flights_info, container, false);
        ButterKnife.bind(this, view);

        if (getArguments() != null) {
            mIsInsert = getArguments().getBoolean(MyFlightsInfoContract.TAG_INSERT);
        }
        mPresenter = new MyFlightsInfoPresenter(getContext(), this);
        mLoadingView.showLoadingView();
        mPresenter.getMyFlightsInfoAPI();

        mAdapter = new MyFlightsInfoRecyclerViewAdapter(getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
    public void getMyFlightsInfoSucceed(final List<FlightsListData> response) {

        if (isAdded() && !isDetached()) {
            if (response == null) {
                Log.e(TAG, "getMyFlightsInfoSucceed response is null");
            } else {
                mMainActivity.runOnUI(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setData(response);
                    }
                });

                Gson gson = new Gson();
                String json = gson.toJson(response);
                Preferences.saveMyFlightsData(getContext(), json);
                if (mIsInsert)
                    Util.resetNotification(getContext(), response);

                mMainActivity.setMarqueeMessage(Util.getMarqueeSubMessage(getContext()));

                mLoadingView.goneLoadingView();
            }
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }

    @Override
    public void getMyFlightsInfoFailed(String message, boolean timeout) {
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
                Log.e(TAG, message);
            }
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }

    @Override
    public void deleteMyFlightsInfoSucceed() {
        mLoadingView.goneLoadingView();
        if (isAdded() && !isDetached()) {
            mMainActivity.runOnUI(new Runnable() {
                @Override
                public void run() {
                    mAdapter.setData(null);
                    mPresenter.getMyFlightsInfoAPI();
                }
            });
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }

    @Override
    public void deleteMyFlightsInfoFailed(String message, boolean timeout) {
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
                Log.e(TAG, message);
                showMessage(message);
            }
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }

    @OnClick(R.id.layout_delete)
    public void onClick() {
        if (mAdapter.getSelectData().size() > 0) {
            new AlertDialog.Builder(getContext())
                    .setTitle(R.string.note)
                    .setMessage(R.string.my_flights_delete_message)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mSelectList = mAdapter.getSelectData();
                            deleteData();
                        }
                    })
                    .setNegativeButton(R.string.alert_btn_cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
        } else {
            Log.d(TAG, "mAdapter.getSelectData().size() < 1");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_frame:
                if (view.getTag() instanceof FlightsListData) {
                    final FlightsListData tag = (FlightsListData) view.getTag();
                    if (tag != null) {
                        final MyDialog myDialog = MyDialog.newInstance()
                                .setTitle(getString(R.string.flight_dialog_title))
                                .setRecyclerContent(DialogContentData.getFlightDetail(getContext(), tag))
                                .setRightText(getString(R.string.ok))
                                .setLeftVisibility(View.GONE);
                        myDialog.show(getActivity().getFragmentManager(), "dialog");
                    } else {
                        Log.d(TAG, "recycler view.getTag() = null");
                    }
                } else {
                    Log.e(TAG, "recycler view.getTag is error");
                    showMessage(getString(R.string.data_error));
                }
        }
    }

    /**
     * Delete data
     */
    private void deleteData() {
        mLoadingView.showLoadingView();
        mPresenter.deleteMyFlightsInfoAPI(mSelectList);
    }

    /**
     * Show no Data dialog
     */
    private void showNoDataDialog() {
        if (isAdded() && !isDetached()) {
            mMainActivity.runOnUI(new Runnable() {
                @Override
                public void run() {
                    new AlertDialog.Builder(getContext())
                            .setTitle(R.string.note)
                            .setMessage(R.string.data_not_found)
                            .setPositiveButton(R.string.ok, null)
                            .show();
                    mAdapter.setData(null);
                }
            });
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }
}
