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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    private Bitmap[] mBitmaps = new Bitmap[3];

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
        final int width = dm.widthPixels;
        final int height = dm.heightPixels/5;

        final float space = getResources().getDimensionPixelSize(R.dimen.dp_pixel_20);
//        final int width = getResources().getDimensionPixelSize(R.dimen.dp_pixel_250);
//        final int height = getResources().getDimensionPixelSize(R.dimen.dp_pixel_100);

        if (getArguments() != null && getArguments().getSerializable(FacilityDetailContract.TAG_DATA) != null) {
            AirportFacilityData facilityData = (AirportFacilityData) getArguments().getSerializable(FacilityDetailContract.TAG_DATA);
            mTextViewSubTitle.setText(facilityData.getFloorName());
            final List<String> imageList = new ArrayList<>();
            imageList.add(facilityData.getMainImgPath());
            imageList.add(facilityData.getLegendImgPath());
            imageList.add(facilityData.getClassImgPath());

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        mBitmaps[0] = Picasso.with(getContext()).load(ApiConnect.TAG_IMAGE_HOST + imageList.get(0))
                                .config(Bitmap.Config.ARGB_4444)
                                .resize(width,height)
                                .priority(Picasso.Priority.HIGH)
                                .get();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    for (int i = 1; i < imageList.size(); i++) {
                        try {
                            mBitmaps[i] = Picasso.with(getContext()).load(ApiConnect.TAG_IMAGE_HOST + imageList.get(i))
                                    .config(Bitmap.Config.ARGB_4444)
//                                    .resize(width,height)
                                    .priority(Picasso.Priority.HIGH)
                                    .get();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    // 這就是那個解 在高解析的情況下 會進行放大的動作(低解析也有可能 只是比較少)
                    if(mBitmaps[1].getWidth()<mBitmaps[0].getWidth()/2 || mBitmaps[2].getWidth()<mBitmaps[0].getWidth()/2){
                        mBitmaps[1] = Util.setBitmapScale(mBitmaps[1],mBitmaps[1].getHeight(),mBitmaps[1].getWidth()*3/2);
                        mBitmaps[2] = Util.setBitmapScale(mBitmaps[2],mBitmaps[2].getHeight(),mBitmaps[2].getWidth()*3/2);
                    }

                    //在最後combine前 將超過Ａ圖寬的通通進行縮小的動作
                    if(mBitmaps[1].getWidth()>mBitmaps[0].getWidth()){
                        mBitmaps[1] = Util.setBitmapScale(mBitmaps[1],mBitmaps[1].getHeight(),mBitmaps[0].getWidth());
                    }
                    //在最後combine前 將超過Ａ圖寬的通通進行縮小的動作
                    if(mBitmaps[2].getWidth()>mBitmaps[0].getWidth()){
                        mBitmaps[2] = Util.setBitmapScale(mBitmaps[2],mBitmaps[2].getHeight(),mBitmaps[0].getWidth());
                    }

                    mMainActivity.runOnUI(new Runnable() {
                        @Override
                        public void run() {
                            mImagePicture.setImage(ImageSource.bitmap(Util.combineBitmap(mBitmaps,(int)space)));
                        }
                    });
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
}
