package com.whatmedia.ttia.page.main.terminals.facility.detail;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.whatmedia.ttia.R;
import com.whatmedia.ttia.connect.ApiConnect;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.response.data.AirportFacilityData;
import com.whatmedia.ttia.utility.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
//        System.gc();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_facility_detail, container, false);
        ButterKnife.bind(this, view);

        mPresenter = FacilityDetailPresenter.getInstance(getContext(), this);

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
//        final int width = dm.widthPixels;
//        final int height = dm.heightPixels / 5;
//        final float space = getResources().getDimensionPixelSize(R.dimen.dp_pixel_20);

        if (getArguments() != null && getArguments().getSerializable(FacilityDetailContract.TAG_DATA) != null) {
            final AirportFacilityData facilityData = (AirportFacilityData) getArguments().getSerializable(FacilityDetailContract.TAG_DATA);
            mTextViewSubTitle.setText(facilityData.getFloorName());
            final List<String> imageList = new ArrayList<>();
            imageList.add(facilityData.getMainImgPath());
            imageList.add(facilityData.getLegendImgPath());
            imageList.add(facilityData.getClassImgPath());

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String path = !TextUtils.isEmpty(facilityData.getLegendImgPath()) ? facilityData.getLegendImgPath() : "";
                        Log.e(TAG, "img path:" + path);
                        mBitmaps = getBitmap(getContext(), path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (isAdded() && !isDetached()) {
                        mMainActivity.runOnUI(new Runnable() {
                            @Override
                            public void run() {
                                if (ImageSource.bitmap(mBitmaps) != null)
                                    mImagePicture.setImage(ImageSource.bitmap(mBitmaps));
                            }
                        });
                    } else {
                        Log.d(TAG, "Fragment is not add");
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
        Bitmap bitmap = Picasso.with(context).load(path)
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
