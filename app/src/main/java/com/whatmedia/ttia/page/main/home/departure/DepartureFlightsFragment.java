package com.whatmedia.ttia.page.main.home.departure;

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
import com.whatmedia.ttia.component.dialog.MyDialog;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.page.Page;
import com.whatmedia.ttia.page.main.flights.my.MyFlightsInfoContract;
import com.whatmedia.ttia.page.main.flights.result.FlightsSearchResultRecyclerViewAdapter;
import com.whatmedia.ttia.response.data.DialogContentData;
import com.whatmedia.ttia.response.data.FlightSearchData;
import com.whatmedia.ttia.response.data.FlightsInfoData;
import com.whatmedia.ttia.utility.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DepartureFlightsFragment extends BaseFragment implements DepartureFlightsContract.View, IOnItemClickListener {
    private static final String TAG = DepartureFlightsFragment.class.getSimpleName();

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private DepartureFlightsContract.Presenter mPresenter;

    private FlightsSearchResultRecyclerViewAdapter mAdapter;
    private List<FlightsInfoData> mDepartureList;

    private String mDepartureDate;
    private String mArriveDate;


    public DepartureFlightsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DepartureFlightsFragment newInstance() {
        DepartureFlightsFragment fragment = new DepartureFlightsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_info_departure, container, false);
        ButterKnife.bind(this, view);

        mPresenter = DepartureFlightsPresenter.getInstance(getContext(), this);

        FlightSearchData data = new FlightSearchData();
        data.setQueryType(FlightsInfoData.TAG_KIND_TOP4_DEPARTURE);
        data.setExpressTime(Util.getNowDate());
        mPresenter.getDepartureFlightAPI(data);

        mAdapter = new FlightsSearchResultRecyclerViewAdapter(getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
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
    public void getDepartureFlightSucceed(final List<FlightsInfoData> list) {
        mMainActivity.runOnUI(new Runnable() {
            @Override
            public void run() {
                mAdapter.setData(list);
            }
        });
    }

    @Override
    public void getDepartureFlightFailed(String message) {
        Log.d(TAG, "getArriveFlightFailed : " + message);
        mMainActivity.runOnUI(new Runnable() {
            @Override
            public void run() {
                showMessage(getString(R.string.server_error));
            }
        });
    }

    @Override
    public void saveMyFlightSucceed(final String message) {

        mLoadingView.goneLoadingView();
        mMainActivity.runOnUI(new Runnable() {
            @Override
            public void run() {
                showMessage(!TextUtils.isEmpty(message) ? message : "");
                Bundle bundle = new Bundle();
                bundle.putBoolean(MyFlightsInfoContract.TAG_INSERT, true);
                mMainActivity.addFragment(Page.TAG_MY_FIGHTS_INFO, bundle, true);
            }
        });
    }

    @Override
    public void saveMyFlightFailed(final String message) {

        mLoadingView.goneLoadingView();
        mMainActivity.runOnUI(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, message);
                showMessage(message);
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_frame:
                if (view.getTag() instanceof FlightsInfoData) {
                    final FlightsInfoData tag = (FlightsInfoData) view.getTag();

                    final MyDialog myDialog = MyDialog.newInstance()
                            .setTitle(getString(R.string.flight_dialog_title))
                            .setRecyclerContent(DialogContentData.getFlightDetail(getContext(), tag))
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
                                            mPresenter.saveMyFlightsAPI(tag);
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
                    Log.e(TAG, "recycler view.getTag is error");
                    showMessage(getString(R.string.data_error));
                }
        }

    }
}
