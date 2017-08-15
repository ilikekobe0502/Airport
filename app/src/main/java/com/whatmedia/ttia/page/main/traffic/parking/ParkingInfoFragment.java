package com.whatmedia.ttia.page.main.traffic.parking;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.whatmedia.ttia.R;
import com.whatmedia.ttia.page.BaseFragment;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.response.data.HomeParkingInfoData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ParkingInfoFragment extends BaseFragment implements ParkingInfoContract.View, OnMapReadyCallback, GoogleMap.OnMapClickListener {
    private static final String TAG = ParkingInfoFragment.class.getSimpleName();
    @BindView(R.id.mapView)
    MapView mMapView;

    GoogleMap mMap;

    private IActivityTools.ILoadingView mLoadingView;
    private IActivityTools.IMainActivity mMainActivity;
    private ParkingInfoContract.Presenter mPresenter;

    private List<HomeParkingInfoData> mParkingList;
    private List<HomeParkingInfoData> mParkingDetailList;

    public ParkingInfoFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ParkingInfoFragment newInstance() {
        ParkingInfoFragment fragment = new ParkingInfoFragment();
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
        View view = inflater.inflate(R.layout.fragment_parking_info, container, false);
        ButterKnife.bind(this, view);

        mMapView.onCreate(savedInstanceState);

        mPresenter = ParkingInfoPresenter.getInstance(getContext(), this);
        mLoadingView.showLoadingView();
        mPresenter.getParkingDetailAPI();
        mMapView.getMapAsync(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onStop() {
        mMapView.onStop();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        mMainActivity.getMyToolbar().setOnBackClickListener(null);
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
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
    public void getParkingInfoSucceed(final List<HomeParkingInfoData> response) {
        mLoadingView.goneLoadingView();
        mParkingList = response;

        mMainActivity.runOnUI(new Runnable() {
            @Override
            public void run() {
                MarkerOptions P1 = new MarkerOptions();
                MarkerOptions P2 = new MarkerOptions();
                MarkerOptions P4_B1 = new MarkerOptions();
                MarkerOptions P4_1F = new MarkerOptions();
                String parkingId;
                for (int i = 0; i < response.size(); i++) {
                    parkingId = response.get(i).getId();
                    HomeParkingInfoData homeParkDetailData = mParkingDetailList.get(i);
                    int pin;
                    int available = Integer.valueOf(homeParkDetailData.getAvailableCar());
                    if (available == 0) {
                        pin = R.drawable.parking_info_04_01_03_1;
                    } else if (available <= 30) {
                        pin = R.drawable.parking_info_04_01_01;
                    } else {
                        pin = R.drawable.parking_info_04_01_02_1;
                    }
                    switch (parkingId) {
                        case HomeParkingInfoData.TAG_ID_P1:
                            P1.position(new LatLng(Double.valueOf(response.get(i).getGisY()), Double.valueOf(response.get(i).getGisX()))).title(parkingId)
                                    .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(pin
                                            , getResources().getDimensionPixelSize(R.dimen.dp_pixel_30)
                                            , getResources().getDimensionPixelSize(R.dimen.dp_pixel_37))));

                            mMap.addMarker(P1).setTag(mParkingDetailList.get(i));
                            mMap.setInfoWindowAdapter(new MyInfoWindowAdapter());
                            break;
                        case HomeParkingInfoData.TAG_ID_P2:
                            P2.position(new LatLng(Double.valueOf(response.get(i).getGisY()), Double.valueOf(response.get(i).getGisX()))).title(parkingId)
                                    .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(pin
                                            , getResources().getDimensionPixelSize(R.dimen.dp_pixel_30)
                                            , getResources().getDimensionPixelSize(R.dimen.dp_pixel_37))));
                            mMap.addMarker(P2).setTag(mParkingDetailList.get(i));
                            mMap.setInfoWindowAdapter(new MyInfoWindowAdapter());
                            break;
                        case HomeParkingInfoData.TAG_ID_P3:
                            P4_B1.position(new LatLng(Double.valueOf(response.get(i).getGisY()), Double.valueOf(response.get(i).getGisX()))).title(parkingId)
                                    .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(pin
                                            , getResources().getDimensionPixelSize(R.dimen.dp_pixel_30)
                                            , getResources().getDimensionPixelSize(R.dimen.dp_pixel_37))));
                            mMap.addMarker(P4_B1).setTag(mParkingDetailList.get(i));
                            mMap.setInfoWindowAdapter(new MyInfoWindowAdapter());
                            break;
                        case HomeParkingInfoData.TAG_ID_P4:
                            P4_1F.position(new LatLng(Double.valueOf(response.get(i).getGisY()), Double.valueOf(response.get(i).getGisX()))).title(parkingId)
                                    .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(pin
                                            , getResources().getDimensionPixelSize(R.dimen.dp_pixel_30)
                                            , getResources().getDimensionPixelSize(R.dimen.dp_pixel_37))));
                            mMap.addMarker(P4_1F).setTag(mParkingDetailList.get(i));
                            mMap.setInfoWindowAdapter(new MyInfoWindowAdapter());
                            break;
                    }
                }
            }
        });
    }

    @Override
    public void getParkingInfoFailed(final String message) {
        mLoadingView.goneLoadingView();
    }

    @Override
    public void getParkingDetailSucceed(List<HomeParkingInfoData> response) {
        if (response != null)
            mParkingDetailList = response;
        else
            Log.e(TAG, "getParkingDetailSucceed() response is null");

        mPresenter.getParkingInfoAPI();
    }

    @Override
    public void getParkingDetailFailed(String message) {
        showMessage(message);
        Log.e(TAG, "getParkingDetailFailed() :" + message);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (mMap == null)
            mMap = googleMap;
        mMap.clear();
        //init camera location
        LatLng initLocation = new LatLng(25.082382, 121.236331);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(initLocation, 15));

    }

    public Bitmap resizeMapIcons(int drawable, int width, int height) {
        Bitmap imageBitmap = BitmapFactory.decodeResource(getContext().getResources(), drawable);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    private class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        public MyInfoWindowAdapter() {
        }

        @Override
        public View getInfoWindow(Marker marker) {

            View view = View.inflate(getContext(), R.layout.layout_map_title, null);

            HomeParkingInfoData data = (HomeParkingInfoData) marker.getTag();

            TextView textViewCount = (TextView) view.findViewById(R.id.textView_count);
            TextView textViewName = (TextView) view.findViewById(R.id.textView_name);
            TextView textViewSubName = (TextView) view.findViewById(R.id.textView_sub_name);
            View line = view.findViewById(R.id.line);
            RelativeLayout frame = (RelativeLayout) view.findViewById(R.id.layout_frame);


            if (TextUtils.equals(data.getId(), HomeParkingInfoData.TAG_ID_P1))
                textViewName.setText(getString(R.string.parking_info_parking_space_P1));
            else if (TextUtils.equals(data.getId(), HomeParkingInfoData.TAG_ID_P2))
                textViewName.setText(getString(R.string.parking_info_parking_space_P2));
            else if (TextUtils.equals(data.getId(), HomeParkingInfoData.TAG_ID_P3)) {
                textViewName.setText(getString(R.string.parking_info_parking_space_P4));
                textViewSubName.setText(getString(R.string.parking_info_parking_space_B1_B2));
            } else {
                textViewName.setText(getString(R.string.parking_info_parking_space_P4));
                textViewSubName.setText(getString(R.string.parking_info_parking_space_1F));
            }

            textViewCount.setText(!TextUtils.isEmpty(data.getAvailableCar()) ? data.getAvailableCar() : "0");
            int available = Integer.valueOf(data.getAvailableCar());
            if (available == 0) {
                frame.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.parking_info_04_01_03txt_1));
                textViewName.setTextColor(ContextCompat.getColor(getContext(), R.color.colorTextParkingFull));
                textViewSubName.setTextColor(ContextCompat.getColor(getContext(), R.color.colorTextParkingFull));
                textViewCount.setTextColor(ContextCompat.getColor(getContext(), R.color.colorTextParkingFull));
                line.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorTextParkingFull));
            } else if (available <= 30) {
                frame.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.parking_info_04_01_01txt_1));
                textViewName.setTextColor(ContextCompat.getColor(getContext(), R.color.colorTextParkingEnough));
                textViewSubName.setTextColor(ContextCompat.getColor(getContext(), R.color.colorTextParkingEnough));
                textViewCount.setTextColor(ContextCompat.getColor(getContext(), R.color.colorTextParkingEnough));
                line.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorTextParkingEnough));
            } else {
                frame.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.parking_info_04_01_02txt_1));
                textViewName.setTextColor(ContextCompat.getColor(getContext(), R.color.colorTextParkingMuch));
                textViewSubName.setTextColor(ContextCompat.getColor(getContext(), R.color.colorTextParkingMuch));
                textViewCount.setTextColor(ContextCompat.getColor(getContext(), R.color.colorTextParkingMuch));
                line.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorTextParkingMuch));
            }
            return view;
        }

        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }
    }


}
