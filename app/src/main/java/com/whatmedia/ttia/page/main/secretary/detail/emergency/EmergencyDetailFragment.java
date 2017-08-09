package com.whatmedia.ttia.page.main.secretary.detail.emergency;

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

import com.squareup.picasso.Picasso;
import com.whatmedia.ttia.R;
import com.whatmedia.ttia.connect.ApiConnect;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.page.main.secretary.sweet.AirportSweetNotifyRecyclerViewAdapter;
import com.whatmedia.ttia.response.data.UserNewsData;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmergencyDetailFragment extends BaseFragment implements EmergencyDetailContract.View {
    private static final String TAG = EmergencyDetailFragment.class.getSimpleName();
    @BindView(R.id.textView_date)
    TextView mTextViewDate;
    @BindView(R.id.textView_title)
    TextView mTextViewTitle;
    @BindView(R.id.textView_content)
    TextView mTextViewContent;
    @BindView(R.id.imageView_picture)
    ImageView mImageViewPicture;

    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private EmergencyDetailContract.Presenter mPresenter;

    private AirportSweetNotifyRecyclerViewAdapter mAdapter;

    public EmergencyDetailFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static EmergencyDetailFragment newInstance() {
        EmergencyDetailFragment fragment = new EmergencyDetailFragment();
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
        View view = inflater.inflate(R.layout.fragment_news_detail, container, false);
        ButterKnife.bind(this, view);

        mPresenter = EmergencyDetailPresenter.getInstance(getContext(), this);

        if (getArguments() != null && getArguments().getSerializable(EmergencyDetailContract.TAG_DATA) != null) {
            UserNewsData item = (UserNewsData) getArguments().getSerializable(EmergencyDetailContract.TAG_DATA);

            mTextViewDate.setText(!TextUtils.isEmpty(item.getaTime()) ? item.getaTime() : "");
            mTextViewTitle.setText(!TextUtils.isEmpty(item.getTitle()) ? item.getTitle() : "");
            mTextViewContent.setText(!TextUtils.isEmpty(item.getContent()) ? item.getContent() : "");
            if (!TextUtils.isEmpty(item.getImageUrl())) {
                mImageViewPicture.setVisibility(View.VISIBLE);
                Picasso.with(getContext()).load(ApiConnect.TAG_IMAGE_HOST + item.getImageUrl())
                        .resize(getResources().getDimensionPixelSize(R.dimen.dp_pixel_250), getResources().getDimensionPixelSize(R.dimen.dp_pixel_152))
                        .centerCrop()
                        .into(mImageViewPicture);
            } else
                mImageViewPicture.setVisibility(View.GONE);
        } else {
            Log.e(TAG, "Fragment getArguments() is error");
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
