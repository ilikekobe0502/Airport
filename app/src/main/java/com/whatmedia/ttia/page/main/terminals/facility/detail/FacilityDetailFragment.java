package com.whatmedia.ttia.page.main.terminals.facility.detail;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.whatmedia.ttia.R;
import com.whatmedia.ttia.newresponse.data.TerminalsFacilityData;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.utility.Util;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FacilityDetailFragment extends BaseFragment implements FacilityDetailContract.View {
    private static final String TAG = FacilityDetailFragment.class.getSimpleName();
    @BindView(R.id.textView_subtitle)
    TextView mTextViewSubTitle;
    @BindView(R.id.imageView_picture)
    com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView mImagePicture;


    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private FacilityDetailContract.Presenter mPresenter;

    private Bitmap mBitmaps;

    public FacilityDetailFragment() {
        // Required empty public constructor
    }

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
//        System.gc();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_facility_detail, container, false);
        ButterKnife.bind(this, view);

        mPresenter = new FacilityDetailPresenter(getContext(), this);

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

        if (getArguments() != null && getArguments().getSerializable(FacilityDetailContract.TAG_DATA) != null) {
            final TerminalsFacilityData facilityData = (TerminalsFacilityData) getArguments().getSerializable(FacilityDetailContract.TAG_DATA);
            mTextViewSubTitle.setText(!TextUtils.isEmpty(facilityData.getFloorName()) ? facilityData.getFloorName() : "");

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        mBitmaps = getBitmap(getContext(), facilityData.getImgDetailUrl());
                        if (isAdded() && !isDetached()) {
                            mMainActivity.runOnUI(new Runnable() {
                                @Override
                                public void run() {
                                    mImagePicture.setImage(ImageSource.bitmap(mBitmaps));
                                }
                            });
                        } else {
                            Log.d(TAG, "Fragment is not add");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();
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
        mBitmaps = null;
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

    public Bitmap getBitmap(Context context, String path) throws IOException {
        Bitmap bitmap = Util.getHttpsPicasso(context).load(path)
                .config(Bitmap.Config.ARGB_8888)
                .priority(Picasso.Priority.HIGH)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .get();
        if (bitmap == null) {
            return getBitmap(context, path);
        } else {
            return bitmap;
        }
    }
}
