package com.whatmedia.ttia.page.main.flights.result;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.component.dialog.MyDialog;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.page.Page;
import com.whatmedia.ttia.page.main.flights.my.MyFlightsInfoRecyclerViewAdapter;
import com.whatmedia.ttia.response.data.DialogContentData;
import com.whatmedia.ttia.response.data.FlightsInfoData;
import com.whatmedia.ttia.response.GetFlightsInfoResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FlightsSearchResultFragment extends BaseFragment implements FlightsSearchResultContract.View, IOnItemClickListener {
    private static final String TAG = FlightsSearchResultFragment.class.getSimpleName();

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.imageView_up)
    ImageView mImageViewUp;
    @BindView(R.id.imageView_down)
    ImageView mImageViewDown;

    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private FlightsSearchResultContract.Presenter mPresenter;

    private FlightsSearchResultRecyclerViewAdapter mAdapter;
    private List<FlightsInfoData> mDepartureList;
    private List<FlightsInfoData> mArriveList;

    private String mDepartureDate;
    private String mArriveDate;


    public FlightsSearchResultFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FlightsSearchResultFragment newInstance() {
        FlightsSearchResultFragment fragment = new FlightsSearchResultFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_flights_search_result, container, false);
        ButterKnife.bind(this, view);

        mPresenter = FlightsSearchResultPresenter.getInstance(getContext(), this);

        if (getArguments() != null && !TextUtils.isEmpty(getArguments().getString(FlightsSearchResultContract.TAG_ARRIVE_FLIGHTS))
                && !TextUtils.isEmpty(getArguments().getString(FlightsSearchResultContract.TAG_DEPARTURE_FLIGHTS))) {
            mArriveList = GetFlightsInfoResponse.newInstance(getArguments().getString(FlightsSearchResultContract.TAG_ARRIVE_FLIGHTS));
            mDepartureList = GetFlightsInfoResponse.newInstance(getArguments().getString(FlightsSearchResultContract.TAG_DEPARTURE_FLIGHTS));

            if (mArriveList.size() > 0)
                mArriveDate = !TextUtils.isEmpty(mArriveList.get(0).getExpressDate()) ? mArriveList.get(0).getExpressDate() : "";
            if (mDepartureList.size() > 0)
                mDepartureDate = !TextUtils.isEmpty(mDepartureList.get(0).getExpressDate()) ? mDepartureList.get(0).getExpressDate() : "";
        } else {
            Log.e(TAG, "data error");
            showMessage(getString(R.string.data_error));
        }

        mAdapter = new FlightsSearchResultRecyclerViewAdapter(getContext(), mDepartureList);
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

    @OnClick({R.id.imageView_up, R.id.imageView_down})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageView_up:
                mImageViewUp.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.up_on));
                mImageViewDown.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.dow_off));
                mAdapter.setData(mDepartureList);
                break;
            case R.id.imageView_down:
                mImageViewUp.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.up_off));
                mImageViewDown.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.dow_on));
                mAdapter.setData(mArriveList);
                break;
            case R.id.layout_frame:
                if (view.getTag() instanceof FlightsInfoData) {
                    final FlightsInfoData tag = (FlightsInfoData) view.getTag();

                    final MyDialog myDialog = MyDialog.newInstance()
                            .setTitle(getString(R.string.dialog_detail_title))
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
                break;
        }
    }


    @Override
    public void saveMyFlightSucceed(final String message) {
        mLoadingView.goneLoadingView();
        mMainActivity.runOnUI(new Runnable() {
            @Override
            public void run() {
                showMessage(!TextUtils.isEmpty(message) ? message : "");
                mMainActivity.addFragment(Page.TAG_MY_FIGHTS_INFO, null, true);
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
}
