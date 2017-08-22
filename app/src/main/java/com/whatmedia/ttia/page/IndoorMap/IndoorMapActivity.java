package com.whatmedia.ttia.page.IndoorMap;


import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.point_consulting.pc_indoormapoverlaylib.AbstractFolder;
import com.point_consulting.pc_indoormapoverlaylib.AssetsFolder;
import com.point_consulting.pc_indoormapoverlaylib.FeatureOptions;
import com.point_consulting.pc_indoormapoverlaylib.IMap;
import com.point_consulting.pc_indoormapoverlaylib.IconOptions;
import com.point_consulting.pc_indoormapoverlaylib.IndoorCameraPosition;
import com.point_consulting.pc_indoormapoverlaylib.IndoorPolygonOptions;
import com.point_consulting.pc_indoormapoverlaylib.IndoorPolylineOptions;
import com.point_consulting.pc_indoormapoverlaylib.Manager;
import com.point_consulting.pc_indoormapoverlaylib.MapImplGoogle;
import com.point_consulting.pc_indoormapoverlaylib.Mathe;
import com.point_consulting.pc_indoormapoverlaylib.TextOptions;
import com.point_consulting.pc_indoormapoverlaylib.Utils;
import com.whatmedia.ttia.R;
import com.whatmedia.ttia.component.MyMarquee;
import com.whatmedia.ttia.component.MyToolbar;
import com.whatmedia.ttia.page.BaseActivity;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.utility.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IndoorMapActivity extends BaseActivity implements IActivityTools.IIndoorMapActivity, OnMapReadyCallback {

    @BindView(R.id.myToolbar)
    MyToolbar mMyToolbar;
    @BindView(R.id.myMarquee)
    MyMarquee mMyMarquee;
    @BindView(R.id.imageView_home)
    ImageView mImageViewHome;
    @BindView(R.id.loadingView)
    FrameLayout mLoadingView;
    @BindView(R.id.editText_search)
    EditText mEditTextSearch;
    @BindView(R.id.imageView_clear)
    ImageView mImageViewClear;
    @BindView(R.id.imageView_search)
    ImageView mImageViewSearch;
    @BindView(R.id.imageView_location)
    ImageView mImageViewLocation;

    private IMap m_map;
    private Manager m_manager;
    private String mMarqueeMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_indoormap);
        ButterKnife.bind(this);

        initAppbar();
        initMarquee();

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.e("Ian", "onMapReady call");
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        start(new MapImplGoogle(googleMap, mapFragment.getView(), 1.0f));
    }

    private void start(IMap map) {
        m_map = map;
        m_manager = new Manager(m_map);
        m_manager.m_delegate = new Manager.Delegate() {

            @Override
            public FeatureOptions getFeatureOptions(String mapLayer, JSONObject props) {
//                final String category = Utils.OptString(props, "CATEGORY");
//                final boolean selectable = mapLayer.equals("Units") && (category.equals("Elevator") || category.equals("Stairs") || category.equals("Escalator") || category.equals("Room") || category.contains("Restroom"));
//                int priority = 9;
//                if (mapLayer.equals("Occupants")) {
//                    if (category.equals("Name")) {
//                        priority = 58;
//                    } else {
//                        priority = 3;
//                    }
//                } else if (mapLayer.equals("Openings")) {
//                    priority = 7;
//                } else if (mapLayer.equals("Units")) {
//                    priority = 100;
//                } else if (mapLayer.equals("Fixtures")) {
//                    priority = 101;
//                }
//                return new FeatureOptions(selectable, priority);
                return null;
            }

            @Override
            public void onLevelLoaded(int i) {
            }

            @Override
            public IndoorPolylineOptions getPolylineOptions(String s, JSONObject jsonObject) {
                return null;
            }

            @Override
            public IndoorPolygonOptions getPolygonOptions(String mapLayer, JSONObject props) {
                if (mapLayer.equals("Fixtures")) {
                    return null;
                }
                String category = null;
                try {
                    category = props.get("CATEGORY").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                Log.e("Ian","s:"+s+", category:"+category.toString());
                if (mapLayer.equals("Units") && category.equals("Walkway")) {
                    return new IndoorPolygonOptions().fillColor(0xffffffff);
                }
                return new IndoorPolygonOptions().fillColor(0);

//                if (mapLayer.equals("Levels")) {
//                    final String category = Utils.OptString(props, "CATEGORY");
//                    if (category.equals("Indoor")) {
//                        final float w = getResources().getDimension(R.dimen.dp_pixel_3);
//                        return new IndoorPolygonOptions().fillColor(0xffbbb8af).strokeColor(0xff4066e2).strokeWidth(w);
//                    }
//                    return null;
//                } else if (mapLayer.equals("Units")) {
//                    final String category = Utils.OptString(props, "CATEGORY");
//                    final float w = getResources().getDimension(R.dimen.dp_pixel_1);
//                    IndoorPolygonOptions polygonOptions = new IndoorPolygonOptions().strokeColor(0xffffffff).strokeWidth(w);
//
//                    switch (category) {
//                        case "Walkway":
//                            polygonOptions.strokeColor(0xfffffdfd).fillColor(0xfffffdfd);
//                            break;
//                        case "Elevator":
//                        case "Stairs":
//                        case "Escalator":
//                            polygonOptions.fillColor(0xff99cbff);
//                            break;
//                        case "Room":
//                            polygonOptions.fillColor(0xffe0e0e0);
//                            break;
//                        case "Non-Public":
//                            polygonOptions.fillColor(0xffd0caca);
//                            break;
//                        case "Transit Platform":
//                            polygonOptions.fillColor(0xffbebeff);
//                            break;
//                        case "Ramp":
//                            polygonOptions.fillColor(0xffe0e0e0);
//                            break;
//                        case "Open to Below":
//                            polygonOptions.fillColor(0xffcccaca);
//                            break;
//                    }
//                    if (category.contains("Restroom")) {
//                        polygonOptions.fillColor(0xffe0e0e0);
//                    }
//
//                    return polygonOptions;
//                }
//                return null;
            }

            @Override
            public void onLongPress(Manager.Location location) {

            }

            @Override
            public void onShortClick(Mathe.MapPoint mapPoint) {

            }

            @Override
            public TextOptions getTextOptions(String s, JSONObject jsonObject) {
                TextOptions textOptions = new TextOptions(30.0F, -16777216, 0, -1.0F, 200.0F, 0, 0, 0, (Typeface) null);
                return textOptions;
            }

            @Override
            public IconOptions getIconOptions(String mapLayer, JSONObject props) {
                int bmId = 0;
                final String name = Utils.OptString(props, "NAME");
                final String category = Utils.OptString(props, "CATEGORY");
                if (mapLayer.equals("Points")) {
                    if (name.contains("Baggage")) {
                        bmId = R.drawable.icon_small_baggage;
                    } else if (name.contains("Security")) {
                        bmId = R.drawable.icon_small_security;
                    } else if (name.contains("ATM")) {
                        bmId = R.drawable.icon_small_atm;
                    } else if (category.equals("Boarding Gate")) {
                        bmId = R.drawable.icon_small_gate;
                    }
                } else if (mapLayer.equals("Occupants")) {
                    if (name.contains("Conference")) {
                        bmId = R.drawable.icon_small_conference;
                    } else if (name.contains("Pet")) {
                        bmId = R.drawable.icon_small_pet;
                    } else if (name.contains("Lost")) {
                        bmId = R.drawable.icon_small_lost_and_found;
                    } else if (category.equals("Shopping") || category.equals("Fashion") || category.equals("Accessories") || category.equals("Appliances") || category.equals("Art Galleries") || category.equals("Auto Parts & Supplies") || category.equals("Automotive") || category.equals("Beer, Wine & Spirits") || category.equals("Books, Mags, Music & Video") || category.equals("Cards & Stationery") || category.equals("Children's Clothing") || category.equals("Cosmetics & Beauty Supply") || category.equals("Department Stores") || category.equals("Dry Cleaning & Laundry") || category.equals("Eyewear & Opticians") || category.equals("Fashion") || category.equals("Florists") || category.equals("Furniture Stores") || category.equals("Grocery") || category.equals("Health & Medical") || category.equals("Hobby Shops") || category.equals("Home & Garden") || category.equals("Jewelry") || category.equals("Lingerie") || category.equals("Luggage") || category.equals("Men's Clothing") || category.equals("Nurseries & Gardening") || category.equals("Pet Stores") || category.equals("Photography Stores & Services") || category.equals("Sewing & Alterations") || category.equals("Shoe Stores") || category.equals("Shopping") || category.equals("Sporting Goods") || category.equals("Toy Stores") || category.equals("Watches") || category.equals("Women's Clothing")) {
                        bmId = R.drawable.o;
                    } else if (category.equals("Cafes") || category.equals("Coffee & Tea")) {
                        bmId = R.drawable.icon_small_coffee;
                    } else if (category.equals("Specialty Food") || category.equals("Restaurants")) {
                        bmId = R.drawable.icon_small_restaurant;
                    } else if (category.equals("Banks & Credit Unions") || category.equals("Financial Services")) {
                        //bmId = R.drawable.icon_bank_small; kma
                    } else if (category.equals("Travel Services")) {
                        bmId = R.drawable.icon_small_checkin;
                    } else if (category.equals("Car Rental")) {
                        bmId = R.drawable.icon_small_car;
                    }
                }

                switch (category) {
                    case "Entry":
                        bmId = R.drawable.entry;
                        break;
                    case "Elevator":
                        bmId = R.drawable.icon_small_lift;
                        break;
                    case "Escalator":
                        bmId = R.drawable.icon_small_escalator;
                        break;
                    case "Stairs":
                        //bmId = R.drawable.icon_stairs_small; kma
                        break;
                    case "Restroom":
                        bmId = R.drawable.icon_small_toilets;
                        break;
                    case "Restroom (Female)":
                        bmId = R.drawable.icon_small_toilets_female;
                        break;
                    case "Restroom (Male)":
                        bmId = R.drawable.icon_small_toilets_male;
                        break;
                }
                if (bmId != 0) {
                    return new IconOptions(getBitmap(bmId), 2, 18.f, 22.f);
                }
                return null;
            }

            @Override
            public Manager.PinInfo getPinInfo(int i) {
                return null;
            }

            @Override
            public String elevatorNameForFeatures(List<Manager.FeatureParams> list) {
                return null;
            }

            @Override
            public boolean isPointInsideVenue(Manager.Location location, boolean b, boolean b1) {
                return b1;
            }
        };
        AbstractFolder abstractFolder = new AssetsFolder(getResources().getAssets(), "TPE");
        Manager.InitializationCallback initializationCallback = new Manager.InitializationCallback() {
            @Override
            public void onIndoorMapManagerInitialized() {
                final Mathe.MapPoint mapCenter = new Mathe.MapPoint(0);
                final Mathe.MapRect mapRect = m_manager.getMapBounds();
                mapRect.getCenter(mapCenter);
                Mathe.IndoorLatLng center = Mathe.LatLngFromMapPoint(mapCenter);
                final IndoorCameraPosition cameraPosition = new IndoorCameraPosition(center, 21.f, 0.f, 0.f);
                m_map.setCameraPosition(cameraPosition, false);
                m_manager.showOrdinal(0);
            }
        };
        final Map<String, String[]> titleFieldsForMapLayer = new HashMap<>();
        titleFieldsForMapLayer.put("Units", new String[]{"SUITE", "CATEGORY"});
        titleFieldsForMapLayer.put("Points", new String[]{"NAME", "CATEGORY"});
        titleFieldsForMapLayer.put("Occupants", new String[]{"NAME", "CATEGORY"});
        titleFieldsForMapLayer.put("Zones", new String[]{"NAME"});
        m_manager.initializeAsync(abstractFolder, titleFieldsForMapLayer, 0, initializationCallback);
    }

    private Bitmap getBitmap(int id) {
        Drawable dr = ContextCompat.getDrawable(this, id);
        BitmapDrawable bdr = (BitmapDrawable) dr;
        return bdr.getBitmap();
    }

    @Override
    public MyToolbar getMyToolbar() {
        return null;
    }

    @Override
    public MyMarquee getMyMarquee() {
        return null;
    }

    @Override
    public void setMarqueeMessage(String subMessage) {

    }

    @Override
    public void backPress() {

    }

    @Override
    public void runOnUI(Runnable runnable) {

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    /**
     * init App bar
     */
    private void initAppbar() {
        mMyToolbar.clearState()
                .setBackground(ContextCompat.getColor(getApplicationContext(), R.color.colorMarquee))
                .setBackIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.back))
                .setTitleText(getString(R.string.home_indoor_map_title))
                .setOnBackClickListener(new MyToolbar.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
    }

    /**
     * init marquee
     */
    private void initMarquee() {
        mMarqueeMessage = getString(R.string.marquee_parking_info, Util.getMarqueeSubMessage(getApplicationContext()));
        mMyMarquee.clearState()
                .setMessage(mMarqueeMessage)
                .setIconVisibility(View.GONE);
    }

    @OnClick({R.id.imageView_clear, R.id.imageView_search, R.id.imageView_location, R.id.imageView_home})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageView_clear:
                mEditTextSearch.setText("");
                break;
            case R.id.imageView_search:
                // TODO: 2017/8/22  
                break;
            case R.id.imageView_location:
                // TODO: 2017/8/22
                break;
            case R.id.imageView_home:
                onBackPressed();
                break;
        }
    }
}
