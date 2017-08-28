package com.whatmedia.ttia.page.main.store.souvenir.detail;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.whatmedia.ttia.R;
import com.whatmedia.ttia.component.CornorTransform;
import com.whatmedia.ttia.component.MyToolbar;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.whatmedia.ttia.connect.ApiConnect.TAG_IMAGE_HOST;

public class SouvenirDetailFragment extends BaseFragment implements SouvenirDetailContract.View {
    private static final String TAG = SouvenirDetailFragment.class.getSimpleName();

    @BindView(R.id.image_icon)
    ImageView mImageIcon;
    @BindView(R.id.text_price)
    TextView mTextPrice;
    @BindView(R.id.text_address)
    TextView mTextAddress;
    @BindView(R.id.text_time)
    TextView mTextTime;
    @BindView(R.id.text_phone)
    TextView mTextPhone;
    @BindView(R.id.text_des)
    TextView mTextDes;

    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private SouvenirDetailContract.Presenter mPresenter;

    private String imagePath;
    private String textPrice;
    private String textAddress;
    private String textTime;
    private String textPhone;
    private String textDes;
    private String textName;
    private int mRadius;

    public SouvenirDetailFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SouvenirDetailFragment newInstance() {
        SouvenirDetailFragment fragment = new SouvenirDetailFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imagePath = getArguments().containsKey(SouvenirDetailContract.IMG_PATH) ?
                    getArguments().getString(SouvenirDetailContract.IMG_PATH, "") : "";
            textPrice = getArguments().containsKey(SouvenirDetailContract.TEXT_PRICE) ?
                    getArguments().getString(SouvenirDetailContract.TEXT_PRICE, "") : "";
            textAddress = getArguments().containsKey(SouvenirDetailContract.TEXT_ADDRESS) ?
                    getArguments().getString(SouvenirDetailContract.TEXT_ADDRESS, "") : "";
            textTime = getArguments().containsKey(SouvenirDetailContract.TEXT_TIME) ?
                    getArguments().getString(SouvenirDetailContract.TEXT_TIME, "") : "";
            textPhone = getArguments().containsKey(SouvenirDetailContract.TEXT_PHONE) ?
                    getArguments().getString(SouvenirDetailContract.TEXT_PHONE, "") : "";
            textDes = getArguments().containsKey(SouvenirDetailContract.TEXT_DES) ?
                    getArguments().getString(SouvenirDetailContract.TEXT_DES, "") : "";
            textName = getArguments().containsKey(SouvenirDetailContract.TEXT_NAME) ?
                    getArguments().getString(SouvenirDetailContract.TEXT_NAME, "") : "";
            textPhone = getArguments().containsKey(SouvenirDetailContract.TEXT_PHONE) ?
                    getArguments().getString(SouvenirDetailContract.TEXT_PHONE, "") : "";
        }
        mRadius = getResources().getDimensionPixelSize(R.dimen.dp_pixel_8);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_souvenir_detail, container, false);
        ButterKnife.bind(this, view);

        mPresenter = SouvenirDetailPresenter.getInstance(getContext(), this);
        mLoadingView.showLoadingView();

        Picasso.with(getContext()).load(TAG_IMAGE_HOST + imagePath).transform(new CornorTransform(mRadius, 0)).into(mImageIcon);
        mTextPrice.setText(textPrice);
        mTextAddress.setText(textAddress);
        mTextTime.setText(textTime);
        mTextPrice.setText(textPrice);
        mTextDes.setText(textDes);
        mTextPhone.setText(textPhone);
        init();
        mLoadingView.goneLoadingView();

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

    public void init() {
        mMainActivity.getMyToolbar().clearState()
                .setTitleText(textName)
                .setBackground(ContextCompat.getColor(getContext(), R.color.colorSubTitle))
                .setBackVisibility(View.VISIBLE)
                .setOnBackClickListener(new MyToolbar.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.imageView_back:
                                mMainActivity.backPress();
                                break;
                        }
                    }
                });
    }

}
