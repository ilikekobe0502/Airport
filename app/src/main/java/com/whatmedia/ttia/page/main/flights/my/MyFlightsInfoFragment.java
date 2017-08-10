package com.whatmedia.ttia.page.main.flights.my;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.Gson;
import com.whatmedia.ttia.R;
import com.whatmedia.ttia.component.dialog.MyDialog;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.response.data.DialogContentData;
import com.whatmedia.ttia.response.data.FlightsInfoData;
import com.whatmedia.ttia.utility.Preferences;
import com.whatmedia.ttia.utility.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyFlightsInfoFragment extends BaseFragment implements MyFlightsInfoContract.View, IOnItemClickListener {
    private static final String TAG = MyFlightsInfoFragment.class.getSimpleName();
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.button_delete)
    Button mButtonDelete;

    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private MyFlightsInfoContract.Presenter mPresenter;
    private int mSelectSize = 0;
    private int mDeletePosition = 0;
    private Map<String, FlightsInfoData> mSelectList;


    private MyFlightsInfoRecyclerViewAdapter mAdapter;

    public MyFlightsInfoFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
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

        mPresenter = MyFlightsInfoPresenter.getInstance(getContext(), this);
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
    public void getMyFlightsInfoSucceed(final List<FlightsInfoData> response) {
        mLoadingView.goneLoadingView();
        if (response == null) {
            Log.e(TAG, "getMyFlightsInfoSucceed response is null");
            mMainActivity.runOnUI(new Runnable() {
                @Override
                public void run() {
                    showMessage(getString(R.string.data_not_found));
                }
            });
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

            mMainActivity.setMarqueeMessage(Util.getMarqueeSubMessage(getContext()));
        }

    }

    @Override
    public void getMyFlightsInfoFailed(String message) {
        mLoadingView.goneLoadingView();
        Log.e(TAG, message);
        showMessage(message);
    }

    @Override
    public void deleteMyFlightsInfoSucceed(final String response) {

        mDeletePosition++;
        if (mSelectSize == mDeletePosition) {//代表佇列中的資料已刪完
            mSelectSize = 0;
            mDeletePosition = 0;
            mMainActivity.runOnUI(new Runnable() {
                @Override
                public void run() {
                    showMessage(response);
                    mPresenter.getMyFlightsInfoAPI();
                }
            });
        } else {
            deleteData();
        }

    }

    @Override
    public void deleteMyFlightsInfoFailed(String message) {
        mLoadingView.goneLoadingView();
        Log.e(TAG, message);
        showMessage(message);
    }

    @OnClick(R.id.button_delete)
    public void onClick() {

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
                .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_frame:
                if (view.getTag() instanceof FlightsInfoData) {
                    final FlightsInfoData tag = (FlightsInfoData) view.getTag();
                    if (tag != null) {
                        final MyDialog myDialog = MyDialog.newInstance()
                                .setTitle(getString(R.string.dialog_detail_title))
                                .setRecyclerContent(DialogContentData.getFlightDetail(getContext(), tag))
                                .setRightText(getString(R.string.ok))
                                .setRightClickListener(new IOnItemClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (tag != null) {
//                                        FlightsInfoData data = new FlightsInfoData();
                                            if (!TextUtils.isEmpty(tag.getAirlineCode()) && !TextUtils.isEmpty(tag.getShift()) && !TextUtils.isEmpty(tag.getExpressDate()) && !TextUtils.isEmpty(tag.getExpressTime())) {
                                                mLoadingView.showLoadingView();
                                                tag.setAirlineCode(tag.getAirlineCode());
                                                if (tag.getShift().length() == 2) {
                                                    tag.setShift("  " + tag.getShift());
                                                } else if (tag.getShift().length() == 3) {
                                                    tag.setShift(" " + tag.getShift());
                                                }
                                                tag.setShift(tag.getShift());
                                                tag.setExpressDate(tag.getExpressDate());
                                                tag.setExpressTime(tag.getExpressTime());
                                                tag.setType("0");
                                            } else {
                                                Log.e(TAG, "view.getTag() content is error");
                                                showMessage(getString(R.string.data_error));
                                            }
                                        } else {
                                            Log.e(TAG, "view.getTag() is null");
                                            showMessage(getString(R.string.data_error));
                                        }
                                    }
                                });
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
        mSelectSize = mSelectList.size();
        List<String> keyList = new ArrayList<>();
        //save key
        for (String key : mSelectList.keySet()) {
            keyList.add(key);
        }

        FlightsInfoData item = mSelectList.get(keyList.get(mDeletePosition));
        if (item.getIsCheck()) {
            mLoadingView.showLoadingView();
            FlightsInfoData data = new FlightsInfoData();
            data.setAirlineCode(item.getAirlineCode());
            if (item.getShift().length() == 2) {
                item.setShift("  " + item.getShift());
            } else if (item.getShift().length() == 3) {
                item.setShift(" " + item.getShift());
            }
            data.setShift(item.getShift());
            data.setExpressDate(item.getExpressDate());
            data.setExpressTime(item.getExpressTime());
            data.setType("1");
            mPresenter.deleteMyFlightsInfoAPI(data);
        }
    }
}
