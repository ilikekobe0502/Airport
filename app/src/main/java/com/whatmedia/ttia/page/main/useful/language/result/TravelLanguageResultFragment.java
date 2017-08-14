package com.whatmedia.ttia.page.main.useful.language.result;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.page.main.useful.questionnaire.QuestionnaireContract;
import com.whatmedia.ttia.response.data.LanguageData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TravelLanguageResultFragment extends BaseFragment implements TravelLanguageResultContract.View{

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private static final String TAG = TravelLanguageResultFragment.class.getSimpleName();

    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private TravelLanguageResultContract.Presenter mPresenter;
    private TravelLanguageResultRecyclerViewAdapter mTravelLanguageResultRecyclerViewAdapter;
    private int mQueryId=0;

    public TravelLanguageResultFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TravelLanguageResultFragment newInstance() {
        TravelLanguageResultFragment fragment = new TravelLanguageResultFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey("key")) {
            mQueryId = getArguments().getInt("key");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lauguage_result, container, false);
        ButterKnife.bind(this, view);
        mLoadingView.showLoadingView();
        mPresenter = TravelLanguageResultPresenter.getInstance(getContext(), this);
        mPresenter.getLanguageAPI(mQueryId);

        mTravelLanguageResultRecyclerViewAdapter = new TravelLanguageResultRecyclerViewAdapter(getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mTravelLanguageResultRecyclerViewAdapter);

        return view;
    }

    @Override
    public void onDestroy() {
        mMainActivity.getMyToolbar().setOnBackClickListener(null);
        super.onDestroy();
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
    public void getLanguageSucceed(final List<LanguageData> response) {
        mLoadingView.goneLoadingView();
        mMainActivity.runOnUI(new Runnable() {
            @Override
            public void run() {
                mTravelLanguageResultRecyclerViewAdapter.setData(response);
                mTravelLanguageResultRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void getLanguageFailed(String message) {
        mLoadingView.goneLoadingView();
    }
}
