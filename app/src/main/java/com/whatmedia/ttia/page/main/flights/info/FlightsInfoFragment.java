package com.whatmedia.ttia.page.main.flights.info;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.enums.FlightInfo;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.page.Page;
import com.whatmedia.ttia.utility.Preferences;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FlightsInfoFragment extends BaseFragment implements FlightsInfoContract.View, IOnItemClickListener {
    private static final String TAG = FlightsInfoFragment.class.getSimpleName();

    @BindView(R.id.layout_frame)
    LinearLayout mLayoutFrame;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.infoView)
    ImageView mInfoView;

    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private FlightsInfoContract.Presenter mPresenter;

    private FlightsInfoRecyclerViewAdapter mAdapter;

    private LinearLayout.LayoutParams mImageParamsFrame;
    private boolean mIsScreen34Mode;

    public FlightsInfoFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FlightsInfoFragment newInstance() {
        FlightsInfoFragment fragment = new FlightsInfoFragment();
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

        mPresenter = FlightsInfoPresenter.getInstance(getContext(), this);
        mIsScreen34Mode = Preferences.checkScreenIs34Mode(getContext());

        mAdapter = new FlightsInfoRecyclerViewAdapter(getContext());
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setClickListener(this);
        return view;
    }

    private void setImage(int h) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(dm);
        mImageParamsFrame = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, h);
        mInfoView.setLayoutParams(mImageParamsFrame);

        setImageSource();
    }

    private void setImageSource() {
        mInfoView.setVisibility(View.VISIBLE);
        mInfoView.setImageResource(R.drawable.bg_02);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (mIsScreen34Mode) {
            mLayoutFrame.post(new Runnable() {
                @Override
                public void run() {
                    setImage((int) (mLayoutFrame.getHeight() * 0.33));
                }
            });
        } else {
            mInfoView.setScaleType(ImageView.ScaleType.FIT_XY);
            setImageSource();
        }
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
        if (view.getTag() instanceof FlightInfo) {
            FlightInfo info = (FlightInfo) view.getTag();
            int page = -1;
            switch (info) {
                case TAG_SEARCH_FLIGHTS://搜尋航班
                    page = Page.TAG_FIGHTS_SEARCH;
                    break;
                case TAG_MY_FLIGHTS://我的航班
                    page = Page.TAG_MY_FIGHTS_INFO;
                    break;
//                case TAG_FLIGHTS_NOTIFY://航班提醒
//                    page = Page.TAG_MY_FIGHTS_NOTIFY;
//                    break;
            }

            if (page != -1)
                mMainActivity.addFragment(page, null, true);
        } else {
            Log.e(TAG, "View.getTag() is not instance of FlightInfo");
        }
    }
}
