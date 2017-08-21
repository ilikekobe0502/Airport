package com.whatmedia.ttia.page.main.store.souvenir;


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
import com.whatmedia.ttia.interfaces.IOnItemClickListener;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.page.Page;
import com.whatmedia.ttia.page.main.store.souvenir.detail.SouvenirDetailContract;
import com.whatmedia.ttia.response.data.SouvenirData;

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

        mPresenter = SouvenirAreaPresenter.getInstance(getContext(), this);

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
        bundle.putString(SouvenirDetailContract.IMG_PATH, data.getImgPath());
        bundle.putString(SouvenirDetailContract.TEXT_PRICE, "NT$" + data.getPrice());
        bundle.putString(SouvenirDetailContract.TEXT_ADDRESS, getString(R.string.souvenir_detail_address) + data.getTerminalsName() + "-" + data.getFloorName());
        bundle.putString(SouvenirDetailContract.TEXT_TIME, getString(R.string.souvenir_detail_time) + data.getOpenTime() + "-" + data.getCloseTime());
        bundle.putString(SouvenirDetailContract.TEXT_PHONE, getString(R.string.souvenir_detail_phone) + data.getTel());
        bundle.putString(SouvenirDetailContract.TEXT_DES, data.getContent());
        bundle.putString(SouvenirDetailContract.TEXT_NAME, data.getName());
        mMainActivity.addFragment(Page.TAG_SOUVENIR_DETAIL, bundle, true);
    }

    @Override
    public void querySouvenirListSuccess(final List<SouvenirData> list) {
        mMainActivity.runOnUI(new Runnable() {
            @Override
            public void run() {
                mList = list;
                mLoadingView.goneLoadingView();
                mAdapter.setmItems(list);
                mAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void querySouvenirListFail(final String message) {
        mLoadingView.goneLoadingView();
        mMainActivity.runOnUI(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "querySouvenirListFail : " + message);
                showMessage(message);
            }
        });
    }
}
