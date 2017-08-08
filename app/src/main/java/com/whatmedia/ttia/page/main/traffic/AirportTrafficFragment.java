package com.whatmedia.ttia.page.main.traffic;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.enums.AirportTraffic;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.page.Page;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AirportTrafficFragment extends BaseFragment implements AirportTrafficContract.View, IOnItemClickListener {
    private static final String TAG = AirportTrafficFragment.class.getSimpleName();

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private AirportTrafficContract.Presenter mPresenter;

    private AirportTrafficRecyclerViewAdapter mAdapter;

    public AirportTrafficFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AirportTrafficFragment newInstance() {
        AirportTrafficFragment fragment = new AirportTrafficFragment();
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

        mPresenter = AirportTrafficPresenter.getInstance(getContext(), this);

        mAdapter = new AirportTrafficRecyclerViewAdapter(getContext());
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
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
//            mLoadingView = (IActivityTools.ILoadingView) context;
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
        if (view.getTag() instanceof AirportTraffic) {
            AirportTraffic info = (AirportTraffic) view.getTag();
            int page = -1;
            switch (info) {
                case TAG_PARKING_INFO://停車資訊
                    page = Page.TAG_PARK_INFO;
                    break;
                case TAG_AIRPORT_BUS://機場巴士
                    page = Page.TAG_AIRPORT_BUS;
                    break;
                case TAG_ROADSIDE_ASSISTANCE://道路救援服務
                    page = Page.TAG_ROADSIDE_ASSISTANCE;
                    break;
                case TAG_TAXI://計程車
                    page = Page.TAG_TAXI;
                    break;
                case TAG_TOUR_BUS://巡迴巴士
                    page = Page.TAG_TOUR_BUS;
                    break;
                case TAG_AIRPORT_MRT://機場捷運/高鐵
                    page = Page.TAG_AIRPORT_MRT;
                    break;
                case TAG_SKY_TRAIN://電車
                    page = Page.TAG_SKY_TRAIN;
                    break;
            }

            if (page != -1)
                mMainActivity.replaceFragment(page, null, true);
        } else {
            Log.e(TAG, "View.getTag() is not instance of FlightInfo");
        }
    }
}
