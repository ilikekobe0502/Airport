package com.whatmedia.ttia.page.main.useful.language;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.connect.NewApiConnect;
import com.whatmedia.ttia.newresponse.data.TravelTypeListData;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.page.Page;
import com.whatmedia.ttia.page.main.useful.language.result.TravelLanguageResultContract;
import com.whatmedia.ttia.utility.Preferences;
import com.whatmedia.ttia.utility.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TravelLanguageFragment extends BaseFragment implements TravelLanguageContract.View, TravelLanguageRecyclerViewAdapter.IOnItemClickListener {
    private static final String TAG = TravelLanguageFragment.class.getSimpleName();

    @BindView(R.id.layout_frame)
    LinearLayout mLayoutFrame;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private TravelLanguageContract.Presenter mPresenter;

    private TravelLanguageRecyclerViewAdapter mAdapter;
    private List<TravelTypeListData> mResponse;

    private boolean mIsScreen34Mode;

    public TravelLanguageFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TravelLanguageFragment newInstance() {
        TravelLanguageFragment fragment = new TravelLanguageFragment();
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
        View view = inflater.inflate(R.layout.fragment_language_list, container, false);
        ButterKnife.bind(this, view);

        mPresenter = new TravelLanguagePresenter(getContext(), this);
        mIsScreen34Mode = Preferences.checkScreenIs34Mode(getContext());

        mPresenter.getTypeListApi();
        mLoadingView.showLoadingView();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mIsScreen34Mode) {
            mLayoutFrame.post(new Runnable() {
                @Override
                public void run() {
                    setData(mLayoutFrame.getHeight());
                }
            });
        } else {
            setData(-1);
        }
        
        if (mResponse != null)
            mAdapter.setData(mResponse);
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
    public void getApiSucceed(final List<TravelTypeListData> response) {
        mLoadingView.goneLoadingView();
        if (isAdded() && !isDetached()) {
            mResponse = response;
            mMainActivity.runOnUI(new Runnable() {
                @Override
                public void run() {
                    mAdapter.setData(response);
                }
            });
        } else {
            Log.e(TAG, "Fragment is not added");
        }
    }

    @Override
    public void getApiFailed(String s, final int status) {
        if (isAdded() && !isDetached()) {
            mMainActivity.runOnUI(new Runnable() {
                @Override
                public void run() {
                    switch (status) {
                        case NewApiConnect.TAG_DEFAULT:
                            showMessage(getString(R.string.server_error));
                            break;
                        case NewApiConnect.TAG_TIMEOUT:
                            Util.showTimeoutDialog(getContext());
                            break;
                        case NewApiConnect.TAG_SOCKET_ERROR:
                            Util.showNetworkErrorDialog(getContext());
                            break;
                    }
                }
            });
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getTag() instanceof TravelTypeListData) {
            TravelTypeListData data = (TravelTypeListData) view.getTag();
            Bundle bundle = new Bundle();
            bundle.putString(TravelLanguageResultContract.TAG_ID, data.getId());
            bundle.putString(TravelLanguageResultContract.TAG_Name, data.getName());
            mMainActivity.addFragment(Page.TAG_USERFUL_LANGUAGE_RESULT, bundle, true);
        }
    }

    private void setData(int height) {
        mAdapter = new TravelLanguageRecyclerViewAdapter(getContext(), height);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setItemClickListener(this);
    }
}
