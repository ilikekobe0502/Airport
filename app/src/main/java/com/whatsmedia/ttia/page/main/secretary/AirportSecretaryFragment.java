package com.whatsmedia.ttia.page.main.secretary;

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

import com.whatsmedia.ttia.R;
import com.whatsmedia.ttia.enums.AirportSecretary;
import com.whatsmedia.ttia.interfaces.IOnItemClickListener;
import com.whatsmedia.ttia.page.BaseFragment;
import com.whatsmedia.ttia.page.IActivityTools;
import com.whatsmedia.ttia.page.Page;
import com.whatsmedia.ttia.utility.Preferences;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AirportSecretaryFragment extends BaseFragment implements AirportSecretaryContract.View, IOnItemClickListener {
    private static final String TAG = AirportSecretaryFragment.class.getSimpleName();
    @BindView(R.id.layout_frame)
    LinearLayout mLayoutFrame;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.infoView)
    ImageView mInfoView;

    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private AirportSecretaryContract.Presenter mPresenter;

    private AirportSecretaryRecyclerViewAdapter mAdapter;

    private LinearLayout.LayoutParams mImageParamsFrame;
    private boolean mIsScreen34Mode;

    public AirportSecretaryFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AirportSecretaryFragment newInstance() {
        AirportSecretaryFragment fragment = new AirportSecretaryFragment();
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

        mPresenter = AirportSecretaryPresenter.getInstance(getContext(), this);
        mIsScreen34Mode = Preferences.checkScreenIs34Mode(getContext());

        mInfoView.setImageResource(R.drawable.bg_09);

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
        mInfoView.setImageResource(R.drawable.bg_09);
    }

    private void setIcon(int height) {
        mAdapter = new AirportSecretaryRecyclerViewAdapter(getContext(), height);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mIsScreen34Mode) {
            mLayoutFrame.post(new Runnable() {
                @Override
                public void run() {
                    int frameLayoutHeight = mLayoutFrame.getHeight();
                    int mLayoutHeight = (int) (frameLayoutHeight * 0.33);
                    setImage(mLayoutHeight);
                    setIcon(frameLayoutHeight - mLayoutHeight);
                }
            });
        } else {
            mInfoView.setScaleType(ImageView.ScaleType.FIT_XY);
            setImageSource();
            setIcon(-1);
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
        if (view.getTag() instanceof AirportSecretary) {
            AirportSecretary info = (AirportSecretary) view.getTag();
            int page = -1;
            switch (info) {
                case TAG_AIRPORT_SECRETARY_MEWS://最新消息
                    page = Page.TAG_AIRPORT_USER_NEWS;
                    break;
                case TAG_AIRPORT_SECRETARY_EMERGENCY://緊急事件
                    page = Page.TAG_AIRPORT_EMERGENCY;
                    break;
                case TAG_AIRPORT_SECRETARY_NOTIFY://貼心提醒
                    page = Page.TAG_AIRPORT_SWEET_NOTIFY;
                    break;
            }

            if (page != -1)
                mMainActivity.replaceFragment(page, null, true);
        } else {
            Log.e(TAG, "View.getTag() is not instance of FlightInfo");
        }
    }
}
