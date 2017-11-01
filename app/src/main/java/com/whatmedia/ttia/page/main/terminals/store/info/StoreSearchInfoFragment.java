package com.whatmedia.ttia.page.main.terminals.store.info;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.whatmedia.ttia.R;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.newresponse.data.RestaurantInfoData;
import com.whatmedia.ttia.newresponse.data.StoreInfoData;
import com.whatmedia.ttia.utility.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoreSearchInfoFragment extends BaseFragment implements StoreSearchInfoContract.View {
    private static final String TAG = StoreSearchInfoFragment.class.getSimpleName();
    @BindView(R.id.imageView_picture)
    ImageView mImageViewPicture;
    @BindView(R.id.textView_title)
    TextView mTextViewTitle;
    @BindView(R.id.textView_location)
    TextView mTextViewLocation;
    @BindView(R.id.textView_time)
    TextView mTextViewTime;
    @BindView(R.id.textView_phone)
    TextView mTextViewPhone;
    @BindView(R.id.textView_content)
    TextView mTextViewContent;

    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private StoreSearchInfoContract.Presenter mPresenter;
    private int mRadius;

    public StoreSearchInfoFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static StoreSearchInfoFragment newInstance() {
        StoreSearchInfoFragment fragment = new StoreSearchInfoFragment();
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
        View view = inflater.inflate(R.layout.fragment_store_search_info, container, false);
        ButterKnife.bind(this, view);

        mPresenter = StoreSearchInfoPresenter.getInstance(getContext(), this);
        mRadius = getContext().getResources().getDimensionPixelSize(R.dimen.dp_pixel_7);
        RestaurantInfoData data;//for搜尋餐廳
        StoreInfoData storeData;//for搜尋商店
        if (getArguments() != null && getArguments().getSerializable(StoreSearchInfoContract.TAG_RESTAURANT_RESULT) != null) {
            data = (RestaurantInfoData) getArguments().getSerializable(StoreSearchInfoContract.TAG_RESTAURANT_RESULT);
            Log.d(TAG, data.toString());

            mTextViewTitle.setText(!TextUtils.isEmpty(data.getRestaurantTypeName()) ? data.getRestaurantTypeName() : "");

            if (!TextUtils.isEmpty(data.getImgUrl())) {
                String image = data.getImgUrl();
                Log.d(TAG, "image url = " + image);
                Util.setPicassoRetry(getContext()
                        , mImageViewPicture
                        , image
                        , mRadius
                        , getResources().getDimensionPixelSize(R.dimen.dp_pixel_250)
                        , getResources().getDimensionPixelSize(R.dimen.dp_pixel_145)
                        , 0);
            }

            mTextViewLocation.setText(getString(R.string.restaurant_store_search_info_location, !TextUtils.isEmpty(data.getTerminalsName()) ? data.getTerminalsName() : "", !TextUtils.isEmpty(data.getFloorName()) ? data.getFloorName() : ""));
            mTextViewTime.setText(getString(R.string.restaurant_store_search_info_time,
                    !TextUtils.isEmpty(data.getOpenTime()) ? data.getOpenTime() : "",
                    !TextUtils.isEmpty(data.getCloseTime()) ? data.getCloseTime() : ""));
            mTextViewPhone.setText(getString(R.string.restaurant_store_search_info_phone, !TextUtils.isEmpty(data.getTel()) ? data.getTel() : ""));
            mTextViewContent.setText(!TextUtils.isEmpty(data.getContent()) ? data.getContent() : "");
        } else if (getArguments() != null && getArguments().getSerializable(StoreSearchInfoContract.TAG_STORE_RESULT) != null) {
            storeData = (com.whatmedia.ttia.newresponse.data.StoreInfoData) getArguments().getSerializable(StoreSearchInfoContract.TAG_STORE_RESULT);
            Log.d(TAG, storeData.toString());

            mTextViewTitle.setText(!TextUtils.isEmpty(storeData.getStoreTypeName()) ? storeData.getStoreTypeName() : "");

            if (!TextUtils.isEmpty(storeData.getImgUrl())) {
                String image = storeData.getImgUrl();
                Log.d(TAG, "image url = " + image);
                Util.setPicassoRetry(getContext()
                        , mImageViewPicture
                        , image
                        , mRadius
                        , getResources().getDimensionPixelSize(R.dimen.dp_pixel_250)
                        , getResources().getDimensionPixelSize(R.dimen.dp_pixel_145)
                        , 0);
            }

            mTextViewLocation.setText(getString(R.string.restaurant_store_search_info_location, storeData.getTerminalsName(), !TextUtils.isEmpty(storeData.getFloorName()) ? storeData.getFloorName() : ""));
            mTextViewTime.setText(getString(R.string.restaurant_store_search_info_time,
                    !TextUtils.isEmpty(storeData.getOpenTime()) ? storeData.getOpenTime() : "",
                    !TextUtils.isEmpty(storeData.getCloseTime()) ? storeData.getCloseTime() : ""));
            mTextViewPhone.setText(getString(R.string.restaurant_store_search_info_phone, !TextUtils.isEmpty(storeData.getTel()) ? storeData.getTel() : ""));
            mTextViewContent.setText(!TextUtils.isEmpty(storeData.getContent()) ? storeData.getContent() : "");
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
