package com.whatmedia.ttia.page.main.terminals.facility;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.page.main.terminals.toilet.PublicToiletRecyclerViewAdapter;
import com.whatmedia.ttia.response.data.AirportFacilityData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AirportFacilityFragment extends BaseFragment implements AirportFacilityContract.View {
    private static final String TAG = AirportFacilityFragment.class.getSimpleName();
    @BindView(R.id.textView_subtitle)
    TextView mTextViewSubtitle;
    @BindView(R.id.imageView_left)
    ImageView mImageViewLeft;
    @BindView(R.id.imageView_right)
    ImageView mImageViewRight;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;


    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private AirportFacilityContract.Presenter mPresenter;

    private AirportFacilityRecyclerViewAdapter mAdapter;
    private boolean mIsZoomIn = false;

    public AirportFacilityFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AirportFacilityFragment newInstance() {
        AirportFacilityFragment fragment = new AirportFacilityFragment();
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
        View view = inflater.inflate(R.layout.fragment_airport_facility, container, false);
        ButterKnife.bind(this, view);

        mPresenter = AirportFacilityPresenter.getInstance(getContext(), this);
        mLoadingView.showLoadingView();
        mPresenter.getAirportFacilityAPI();

        mAdapter = new AirportFacilityRecyclerViewAdapter(getContext());
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
    public void getAirportFacilitySucceed(final List<AirportFacilityData> response) {
        mLoadingView.goneLoadingView();
        mMainActivity.runOnUI(new Runnable() {
            @Override
            public void run() {
                mTextViewSubtitle.setText(mAdapter.setData(response));
            }
        });
    }

    @Override
    public void getAirportFacilityFailed(String message) {
        mLoadingView.goneLoadingView();
    }

    @OnClick({R.id.imageView_left, R.id.imageView_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageView_left:
                mImageViewLeft.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.left_on));
                mImageViewRight.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.right_off));
                mTextViewSubtitle.setText(mAdapter.setTerminal(true));
                break;
            case R.id.imageView_right:
                mImageViewLeft.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.left_off));
                mImageViewRight.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.right_on));
                mTextViewSubtitle.setText(mAdapter.setTerminal(false));
                break;
        }
    }
}
