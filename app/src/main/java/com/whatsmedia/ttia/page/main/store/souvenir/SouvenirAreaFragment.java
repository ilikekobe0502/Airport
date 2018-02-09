package com.whatsmedia.ttia.page.main.store.souvenir;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whatsmedia.ttia.R;
import com.whatsmedia.ttia.connect.NewApiConnect;
import com.whatsmedia.ttia.interfaces.IOnItemClickListener;
import com.whatsmedia.ttia.newresponse.data.SouvenirData;
import com.whatsmedia.ttia.page.BaseFragment;
import com.whatsmedia.ttia.page.IActivityTools;
import com.whatsmedia.ttia.page.Page;
import com.whatsmedia.ttia.page.main.store.souvenir.detail.SouvenirDetailContract;
import com.whatsmedia.ttia.utility.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SouvenirAreaFragment extends BaseFragment implements SouvenirAreaContract.View, IOnItemClickListener {
    private static final String TAG = SouvenirAreaFragment.class.getSimpleName();

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private SouvenirAreaContract.Presenter mPresenter;

    private SouvenirAreaRecyclerViewAdapter mAdapter;
    private List<SouvenirData> mList;

    public SouvenirAreaFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SouvenirAreaFragment newInstance() {
        SouvenirAreaFragment fragment = new SouvenirAreaFragment();
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
        View view = inflater.inflate(R.layout.fragment_souvenir, container, false);
        ButterKnife.bind(this, view);

        mPresenter = new SouvenirAreaPresenter(getContext(), this);

        mAdapter = new SouvenirAreaRecyclerViewAdapter(getContext());
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setClickListener(this);

        mLoadingView.showLoadingView();
        mPresenter.querySouvenirList();

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
    public void onClick(View view) {
        SouvenirData data = mList.get(Integer.valueOf(view.getTag().toString()));
        Bundle bundle = new Bundle();
        bundle.putString(SouvenirDetailContract.IMG_PATH, !TextUtils.isEmpty(data.getImgPath()) ? data.getImgPath() : "");
        bundle.putString(SouvenirDetailContract.TEXT_PRICE, "NT$" + (!TextUtils.isEmpty(data.getPrice()) ? data.getPrice() : ""));
        bundle.putString(SouvenirDetailContract.TEXT_ADDRESS, getString(R.string.souvenir_detail_address) + (!TextUtils.isEmpty(data.getTerminalsName()) ? data.getTerminalsName() : "") + "-" + (!TextUtils.isEmpty(data.getFloorName()) ? data.getFloorName() : ""));
        bundle.putString(SouvenirDetailContract.TEXT_TIME, getString(R.string.souvenir_detail_time) + (!TextUtils.isEmpty(data.getOpenTime()) ? data.getOpenTime() : "") + "-" + (!TextUtils.isEmpty(data.getCloseTime()) ? data.getCloseTime() : ""));
        bundle.putString(SouvenirDetailContract.TEXT_PHONE, getString(R.string.souvenir_detail_phone) + (!TextUtils.isEmpty(data.getTel()) ? data.getTel() : ""));
        bundle.putString(SouvenirDetailContract.TEXT_DES, !TextUtils.isEmpty(data.getContent()) ? data.getContent() : "");
        bundle.putString(SouvenirDetailContract.TEXT_NAME, !TextUtils.isEmpty(data.getName()) ? data.getName() : "");
        mMainActivity.addFragment(Page.TAG_SOUVENIR_DETAIL, bundle, true);
    }

    @Override
    public void querySouvenirListSuccess(final List<SouvenirData> list) {

        mLoadingView.goneLoadingView();
        if (isAdded() && !isDetached()) {
            mMainActivity.runOnUI(new Runnable() {
                @Override
                public void run() {
                    mList = list;
                    mAdapter.setmItems(list);
                    mAdapter.notifyDataSetChanged();
                }
            });
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }

    @Override
    public void querySouvenirListFail(final String message, final int status) {
        if (isAdded() && !isDetached()) {
            mMainActivity.runOnUI(new Runnable() {
                @Override
                public void run() {
                    switch (status) {
                        case NewApiConnect.TAG_DEFAULT:
                            showMessage(getString(R.string.server_error));
                            break;
                        case NewApiConnect.TAG_TIMEOUT:
                            if (getContext() != null && isAdded() && !isDetached())
                                Util.showTimeoutDialog(getContext());
                            break;
                        case NewApiConnect.TAG_SOCKET_ERROR:
                            if (getContext() != null && isAdded() && !isDetached())
                                Util.showNetworkErrorDialog(getContext());
                            break;
                    }
                }
            });
        } else {
            Log.d(TAG, "Fragment is not add");
        }
    }
}
