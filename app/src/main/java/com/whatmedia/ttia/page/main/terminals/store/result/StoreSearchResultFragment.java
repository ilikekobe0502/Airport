package com.whatmedia.ttia.page.main.terminals.store.result;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.interfaces.IOnItemClickListener;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.page.Page;
import com.whatmedia.ttia.page.main.terminals.store.info.StoreSearchInfoContract;
import com.whatmedia.ttia.response.GetRestaurantInfoResponse;
import com.whatmedia.ttia.response.GetStoreInfoDataResponse;
import com.whatmedia.ttia.response.data.RestaurantInfoData;
import com.whatmedia.ttia.response.data.StoreInfoData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoreSearchResultFragment extends BaseFragment implements StoreSearchResultContract.View, IOnItemClickListener {
    private static final String TAG = StoreSearchResultFragment.class.getSimpleName();
    @BindView(R.id.textView_subtitle)
    TextView mTextViewSubtitle;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private StoreSearchResultContract.Presenter mPresenter;

    private StoreSearchResultRecyclerViewAdapter mAdapter;

    public StoreSearchResultFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static StoreSearchResultFragment newInstance() {
        StoreSearchResultFragment fragment = new StoreSearchResultFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_store_search_result, container, false);
        ButterKnife.bind(this, view);

        mPresenter = StoreSearchResultPresenter.getInstance(getContext(), this);
        List<RestaurantInfoData> list = null;
        List<StoreInfoData> storeList = null;
        if (getArguments() != null && !TextUtils.isEmpty(getArguments().getString(StoreSearchResultContract.TAG_RESTAURANT_RESULT)))
            list = GetRestaurantInfoResponse.newInstance(getArguments().getString(StoreSearchResultContract.TAG_RESTAURANT_RESULT));
        else if (!TextUtils.isEmpty(getArguments().getString(StoreSearchResultContract.TAG_STORE_RESULT))) {
            storeList = GetStoreInfoDataResponse.newInstance(getArguments().getString(StoreSearchResultContract.TAG_STORE_RESULT));
        }

        // TODO: 2017/8/6 Subtitle
//        if (list.size()>0){
//            mTextViewSubtitle.setText(list.get(0).get);
//        }
        mAdapter = new StoreSearchResultRecyclerViewAdapter(getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);

        if (list != null)
            mAdapter.setData(list);
        else if (storeList != null)
            mAdapter.setStoreData(storeList);

        mAdapter.setOnClickListener(this);

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
        switch (view.getId()) {
            case R.id.layout_frame:
                if (view.getTag() != null && view.getTag() instanceof RestaurantInfoData) {
                    RestaurantInfoData item = (RestaurantInfoData) view.getTag();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(StoreSearchInfoContract.TAG_RESTAURANT_RESULT, item);
                    mMainActivity.addFragment(Page.TAG_STORE_SEARCH_INFO, bundle, true);
                } else if (view.getTag() != null && view.getTag() instanceof StoreInfoData) {
                    StoreInfoData item = (StoreInfoData) view.getTag();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(StoreSearchInfoContract.TAG_STORE_RESULT, item);
                    mMainActivity.addFragment(Page.TAG_STORE_SEARCH_INFO, bundle, true);
                }
                break;
        }
    }
}
