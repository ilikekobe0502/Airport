package com.whatmedia.ttia.page.main.terminals.facility.detail;

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
import com.whatmedia.ttia.interfaces.IOnItemClickListener;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.page.main.terminals.facility.AirportFacilityRecyclerViewAdapter;
import com.whatmedia.ttia.response.data.AirportFacilityData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FacilityDetailFragment extends BaseFragment implements FacilityDetailContract.View {
    private static final String TAG = FacilityDetailFragment.class.getSimpleName();
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.textView_subtitle)
    TextView mTextViewSubTitle;


    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private FacilityDetailContract.Presenter mPresenter;

    private FacilityDetailRecyclerViewAdapter mAdapter;

    public FacilityDetailFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FacilityDetailFragment newInstance() {
        FacilityDetailFragment fragment = new FacilityDetailFragment();
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
        View view = inflater.inflate(R.layout.fragment_facility_detail, container, false);
        ButterKnife.bind(this, view);

        mPresenter = FacilityDetailPresenter.getInstance(getContext(), this);

        if (getArguments() != null && getArguments().getSerializable(FacilityDetailContract.TAG_DATA) != null) {
            AirportFacilityData facilityData = (AirportFacilityData) getArguments().getSerializable(FacilityDetailContract.TAG_DATA);
            mTextViewSubTitle.setText(facilityData.getFloorName());
            List<String> imageList = new ArrayList<>();
            imageList.add(facilityData.getMainImgPath());
            imageList.add(facilityData.getLegendImgPath());
            imageList.add(facilityData.getClassImgPath());
            mAdapter = new FacilityDetailRecyclerViewAdapter(getContext(), imageList);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mRecyclerView.setAdapter(mAdapter);
        } else {
            Log.e(TAG, "getArguments() is error");
            showMessage(getString(R.string.data_error));
        }
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
}
