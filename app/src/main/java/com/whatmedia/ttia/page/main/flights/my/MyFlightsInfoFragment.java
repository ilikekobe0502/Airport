package com.whatmedia.ttia.page.main.flights.my;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.response.data.FlightsInfoData;
import com.whatmedia.ttia.response.data.MyFlightsInfoData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyFlightsInfoFragment extends BaseFragment implements MyFlightsInfoContract.View {
    private static final String TAG = MyFlightsInfoFragment.class.getSimpleName();
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.button_delete)
    Button mButtonDelete;

    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private MyFlightsInfoContract.Presenter mPresenter;
    int mSelectSize = 0;
    int mDeleteCount = 0;


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
    public void getMyFlightsInfoSucceed(final List<MyFlightsInfoData> response) {
        mLoadingView.goneLoadingView();
        mMainActivity.runOnUI(new Runnable() {
            @Override
            public void run() {
                mAdapter.setData(response);
                if (response == null)
                    Log.e(TAG, "getMyFlightsInfoSucceed response is null");
            }
        });
    }

    @Override
    public void getMyFlightsInfoFailed(String message) {
        mLoadingView.goneLoadingView();
        Log.e(TAG, message);
        showMessage(message);
    }

    @Override
    public void deleteMyFlightsInfoSucceed(final String response) {
        if (mSelectSize == mDeleteCount) {//代表佇列中的資料已刪完
            mSelectSize = 0;
            mDeleteCount = 0;
            mMainActivity.runOnUI(new Runnable() {
                @Override
                public void run() {
                    showMessage(response);
                    mPresenter.getMyFlightsInfoAPI();
                }
            });
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
        if (mAdapter.getSelectData() != null) {
            Map<String, MyFlightsInfoData> list = mAdapter.getSelectData();
            mSelectSize = list.size();
            List<String> keyList = new ArrayList<>();
            //save key
            for (String key : list.keySet()) {
                keyList.add(key);
            }
            // TODO: 2017/8/8 不應該同時call
            //判斷資料是否正確即組成上傳資料
            for (int i = 0; i < list.size(); i++) {
                MyFlightsInfoData item = list.get(keyList.get(i));
                if (item.getIsCheck()) {
                    mLoadingView.showLoadingView();
                    mDeleteCount++;
                    FlightsInfoData data = new FlightsInfoData();
                    data.setAirlineCode(item.getAirlineCode());
                    if (item.getShifts().length() == 2) {
                        item.setShifts("  " + item.getShifts());
                    } else if (item.getShifts().length() == 3) {
                        item.setShifts(" " + item.getShifts());
                    }
                    data.setShift(item.getShifts());
                    data.setExpressDate(item.getExpressDate());
                    data.setExpressTime(item.getExpressTime());
                    data.setType("1");
                    mPresenter.deleteMyFlightsInfoAPI(data);
                }
            }
        } else {
            Log.d(TAG, "SelectData is null");
        }
    }
}
