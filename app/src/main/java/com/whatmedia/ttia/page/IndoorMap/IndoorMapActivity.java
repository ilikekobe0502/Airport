package com.whatmedia.ttia.page.IndoorMap;


import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.aigestudio.wheelpicker.WheelPicker;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.point_consulting.pc_indoormapoverlaylib.AbstractFolder;
import com.point_consulting.pc_indoormapoverlaylib.AssetsFolder;
import com.point_consulting.pc_indoormapoverlaylib.BuildConfig;
import com.point_consulting.pc_indoormapoverlaylib.Coordinate3D;
import com.point_consulting.pc_indoormapoverlaylib.FeatureOptions;
import com.point_consulting.pc_indoormapoverlaylib.IMap;
import com.point_consulting.pc_indoormapoverlaylib.IMarker;
import com.point_consulting.pc_indoormapoverlaylib.IconOptions;
import com.point_consulting.pc_indoormapoverlaylib.IndoorCameraPosition;
import com.point_consulting.pc_indoormapoverlaylib.IndoorMap;
import com.point_consulting.pc_indoormapoverlaylib.IndoorPolygonOptions;
import com.point_consulting.pc_indoormapoverlaylib.IndoorPolylineOptions;
import com.point_consulting.pc_indoormapoverlaylib.Manager;
import com.point_consulting.pc_indoormapoverlaylib.MapImplGoogle;
import com.point_consulting.pc_indoormapoverlaylib.MapImplIndoor;
import com.point_consulting.pc_indoormapoverlaylib.Mathe;
import com.point_consulting.pc_indoormapoverlaylib.TextOptions;
import com.point_consulting.pc_indoormapoverlaylib.Utils;
import com.whatmedia.ttia.R;
import com.whatmedia.ttia.component.MyMarquee;
import com.whatmedia.ttia.component.MyToolbar;
import com.whatmedia.ttia.page.BaseActivity;
import com.whatmedia.ttia.page.IActivityTools;
import com.whatmedia.ttia.utility.MyAppUtils;
import com.whatmedia.ttia.utility.MyApplication;
import com.whatmedia.ttia.utility.MyStateDrawable;
import com.whatmedia.ttia.utility.Util;

import junit.framework.Assert;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IndoorMapActivity extends BaseActivity implements IActivityTools.IIndoorMapActivity, OnMapReadyCallback ,MyApplication.GoogleLocationCallback {

    @BindView(R.id.myToolbar)
    MyToolbar mMyToolbar;
    @BindView(R.id.myMarquee)
    MyMarquee mMyMarquee;
    @BindView(R.id.imageView_home)
    ImageView mImageViewHome;
    @BindView(R.id.loadingView)
    FrameLayout mLoadingView;
    @BindView(R.id.editText_search)
    SearchView mEditTextSearch;
    @BindView(R.id.imageView_clear)
    ImageView mImageViewClear;
    @BindView(R.id.imageView_search)
    ImageView mImageViewSearch;
    @BindView(R.id.imageView_location)
    ImageView mImageViewLocation;

    private static Mathe.IndoorLatLng s_center = new Mathe.IndoorLatLng(25.081723343293813, 121.2408936708626);
    private static Mathe.IndoorLatLng s_hackedCenter = new Mathe.IndoorLatLng(60.040291238122467, 30.392644503671253);

    private static final String s_droppedPinId = "dropped_pin";
    private static final String s_startPinId = "start_pin";
    private static final String s_endPinId = "end_pin";

    private static final int s_purplePinColor = 0xffda70d6;
    private static final int s_greenPinColor = 0xff00ff00;
    private static final int s_redPinColor = 0xffff0000;

    private static final String s_camPosLat = "s_camPosLat";
    private static final String s_camPosLon = "s_camPosLon";
    private static final String s_camPosZoom = "s_camPosZoom";
    private static final String s_camPosBearing = "s_camPosBearing";

    private static final int s_routeColorIndoor = 0xff337fff;
    private static final int s_routeColorOutdoor = 0xffff7f33;

    private static final int s_requestCodeInfo = 1;
    private static final int s_requestCodeDirections = 2;

    private static final String s_navModeKey = "s_navModeKey";

    private String mMarqueeMessage;
    private float mDensity;

    private IMap m_map;
    private IMarker m_selectedMarker;
    private Manager m_manager;
    private final String m_routeIdIndoor = "routeIndoor";
    private final String m_routeIdOutdoor = "routeOutdoor";
    private IndoorCameraPosition m_cameraPosition;
    private WheelPicker m_levelsPicker;
    private boolean m_initialized;
    private List<Manager.IndoorMapStep> m_routeSteps;
    private int m_routeStepsIndex;
    private boolean m_isWheelchairMode;

    private ViewGroup m_routeBar;
    private TextView m_labelStep;
    private ImageButton m_buttonPrev;
    private ImageButton m_buttonNext;
    private TextView m_descLabel;
    private TextView m_distLabel;

    private ProgressDialog m_progressDialog;

    private boolean m_wantHaveRoute;
    private boolean m_wantRecalcRoute;

    private Typeface m_typeface;

    private static final boolean s_useServer = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_indoormap);
        ButterKnife.bind(this);

        mDensity = getResources().getDisplayMetrics().density;

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mEditTextSearch.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        mEditTextSearch.setIconifiedByDefault(false);

        initAppbar();
        initMarquee();
        initLevelSwitch();

        com.point_consulting.pc_indoormapoverlaylib.MapFragment mapFragment = (com.point_consulting.pc_indoormapoverlaylib.MapFragment) getFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
        final IndoorMap indoorMap = mapFragment.getMap();

        start(new MapImplIndoor(indoorMap));

        handleIntent(getIntent());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setIndoorEnabled(false);
        final UiSettings settings = googleMap.getUiSettings();
        settings.setIndoorLevelPickerEnabled(false);
        settings.setMapToolbarEnabled(false);
        settings.setCompassEnabled(false);
        Log.e("Ian","onMapReady call");
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.my_map_style));

        com.point_consulting.pc_indoormapoverlaylib.MapFragment mapFragment = (com.point_consulting.pc_indoormapoverlaylib.MapFragment)getFragmentManager().findFragmentById(R.id.map);
        start(new MapImplGoogle(googleMap, mapFragment.getView(),getResources().getDisplayMetrics().density));
    }

    private void start(IMap map) {
        m_initialized = true;
        m_map = map;
        m_map.setCalloutListener(new IMap.CalloutListener() {
            @Override
            public View createCalloutView(Context context, IMarker marker) {
                // illustrate custom callout

                final Manager.Location location = marker.getLocation();
                JSONObject obj = m_manager.propsForFeature(location.m_featureIndex);
                final String title = Utils.OptString(obj, "NAME");
                final String subtitle = Utils.OptString(obj, "CATEGORY");

                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setBackground(ContextCompat.getDrawable(getBaseContext(),R.drawable.indoor_10_05));
                RelativeLayout.LayoutParams llp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layout.setLayoutParams(llp);
                layout.setGravity(Gravity.CENTER_HORIZONTAL);

                TextView tvTitle = new TextView(context);
                tvTitle.setText(title);
                LinearLayout.LayoutParams llp1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                tvTitle.setLayoutParams(llp1);
                layout.addView(tvTitle);

                if (subtitle.length() > 0) {
                    TextView tvSubtitle = new TextView(context);
                    tvSubtitle.setText(subtitle);
                    LinearLayout.LayoutParams llp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    tvSubtitle.setLayoutParams(llp2);
                    layout.addView(tvSubtitle);
                }

                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO 點擊後進入IndoorSearchActivity 進行路由的動作
//                        m_map.deselectMarker(m_selectedMarker);
//                        MapsActivity.this.onCalloutDetailTapped(location);
                    }
                });

                return layout;
            }

            @Override
            public void onSelectedMarker(IMarker marker) {
                m_selectedMarker = marker;
            }

            @Override
            public void onDeselectedMarker(IMarker marker) {
                m_selectedMarker = null;
            }

            @Override
            public void onCalloutClick(IMarker marker) {
//                MapsActivity.this.onCalloutDetailTapped(marker.getLocation());
            }
        });




        m_manager = new Manager(m_map);

        MyApplication app = (MyApplication) getApplication();
        app.m_manager = m_manager;

        m_manager.m_delegate =new Manager.Delegate() {

            @Override
            public FeatureOptions getFeatureOptions(String mapLayer, JSONObject props) {
                final String category = Utils.OptString(props, "CATEGORY");
                final boolean selectable = mapLayer.equals("Units") && (category.equals("Elevator") || category.equals("Stairs") || category.equals("Escalator") || category.equals("Room") || category.contains("Restroom"));
                int priority = 9;
                if (mapLayer.equals("Occupants")) {
                    if (category.equals("Name")) {
                        priority = 58;
                    } else {
                        priority = 3;
                    }
                } else if (mapLayer.equals("Openings")) {
                    priority = 7;
                } else if (mapLayer.equals("Units")) {
                    priority = 100;
                } else if (mapLayer.equals("Fixtures")) {
                    priority = 101;
                }
                return new FeatureOptions(selectable, priority);
            }

            @Override
            public void onLevelLoaded(int ordinal) {
                TextView tv = (TextView) findViewById(R.id.levelLabel);
                tv.setText(String.valueOf(ordinal));
            }

            @Override
            public IndoorPolylineOptions getPolylineOptions(String mapLayer, JSONObject jsonObject) {
                if (mapLayer.equals("Openings")) {
                    final float w = getResources().getDimension(R.dimen.dp_pixel_2);
                    return new IndoorPolylineOptions().width(w).color(0xffffffff);
                }
                return null;
            }

            @Override
            public IndoorPolygonOptions getPolygonOptions(String mapLayer, JSONObject props) {
                if (mapLayer.equals("Fixtures")) {
                    return null;
                }

                if (mapLayer.equals("Levels")) {
                    final String category = Utils.OptString(props, "CATEGORY");
                    if (category.equals("Indoor")) {
                        final float w = getResources().getDimension(R.dimen.dp_pixel_3);
                        return new IndoorPolygonOptions().fillColor(0xffbbb8af).strokeColor(0xff4066e2).strokeWidth(w);
                    }
                    return null;
                } else if (mapLayer.equals("Units")) {
                    final String category = Utils.OptString(props, "CATEGORY");
                    final float w = getResources().getDimension(R.dimen.dp_pixel_1);
                    IndoorPolygonOptions polygonOptions = new IndoorPolygonOptions().strokeColor(0xffffffff).strokeWidth(w);

                    switch (category) {
                        case "Walkway":
                            polygonOptions.strokeColor(0xfffffdfd).fillColor(0xfffffdfd);
                            break;
                        case "Elevator":
                        case "Stairs":
                        case "Escalator":
                            polygonOptions.fillColor(0xff99cbff);
                            break;
                        case "Room":
                            polygonOptions.fillColor(0xffe0e0e0);
                            break;
                        case "Non-Public":
                            polygonOptions.fillColor(0xffd0caca);
                            break;
                        case "Transit Platform":
                            polygonOptions.fillColor(0xffbebeff);
                            break;
                        case "Ramp":
                            polygonOptions.fillColor(0xffe0e0e0);
                            break;
                        case "Open to Below":
                            polygonOptions.fillColor(0xffcccaca);
                            break;
                    }
                    if (category.contains("Restroom")) {
                        polygonOptions.fillColor(0xffe0e0e0);
                    }

                    return polygonOptions;
                }
                return null;

            }

            @Override
            public void onLongPress(Manager.Location location) {
//                createMark(location, s_purplePinColor, true, s_droppedPinId);
            }

            @Override
            public void onShortClick(Mathe.MapPoint mapPoint) {
                m_levelsPicker.setVisibility(View.INVISIBLE);
                findViewById(R.id.levelsButton).setVisibility(View.VISIBLE);
            }

            @Override
            public TextOptions getTextOptions(String mapLayer, JSONObject props) {
//                TextOptions textOptions = new TextOptions(30.0F, -16777216, 0, -1.0F, 200.0F, 0, 0, 0, (Typeface) null);
//                return textOptions;
                final float density = getResources().getDisplayMetrics().density;

                float minZoomLevel, maxZoomLevel;

                final String category = Utils.OptString(props, "CATEGORY");
                if (mapLayer.equals("Occupants")) {
                    minZoomLevel = 18.f;
                    maxZoomLevel = 22.f;
                } else if (mapLayer.equals("Points") & category.contains("Gate")) {
                    minZoomLevel = 18.f;
                    maxZoomLevel = 22.f;
                    final int padding = Math.round(density * 4);
                    return new TextOptions(10.f * density, 0xffffffff, 1, minZoomLevel, maxZoomLevel, 0xff4066e2, padding, padding, Typeface.DEFAULT_BOLD);
                } else {
                    minZoomLevel = 1;
                    maxZoomLevel = 1;
                }
                if (mapLayer.equals("Units")) {
                    minZoomLevel = 1.f;
                    maxZoomLevel = 1.f;
                }
                return new TextOptions(10.f * density, 0xff363840, 1, minZoomLevel, maxZoomLevel, 0, 0, 0, Typeface.DEFAULT_BOLD);
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
            public Manager.PinInfo getPinInfo(int color) {
                int bmId;
                switch (color) {
                    case 0: {
                        // this is blue dot
                        final Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.blue_dot);
                        return new Manager.PinInfo(bm, 0, 0, 0, 0);
                    }
                    case s_redPinColor:
                        bmId = R.drawable.pin_red;
                        break;
                    case s_greenPinColor:
                        bmId = R.drawable.pin_green;
                        break;
                    default:
                        Assert.assertEquals(color, s_purplePinColor);
                        bmId = R.drawable.pin_purple;
                }

                final Bitmap bm = BitmapFactory.decodeResource(getResources(), bmId);
                final int h = bm.getHeight();
                final float nW = 48.f;
                final float nH = 64.f;
                final float scale = (float) h / nH;
                return new Manager.PinInfo(bm, scale * (nW * 0.5f - 12.f), scale * (nH * 0.5f - 58.f), scale * 12.f, scale * 0.f);
            }

            @Override
            public String elevatorNameForFeatures(List<Manager.FeatureParams> features) {
                if (features.size() == 1) {
                    Manager.FeatureParams p = features.get(0);
                    final String category = Utils.OptString(p.m_props, "CATEGORY");
                    return category;
                }
                return "Unknown transport";
            }

            @Override
            public boolean isPointInsideVenue(Manager.Location location, boolean b, boolean b1) {
                return b1;
            }
        };
        AbstractFolder abstractFolder = new AssetsFolder(getResources().getAssets(), "JAX");
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

    private void createMark(Manager.Location location, int pinColor, boolean needSelect, String userId) {
        final float w = getResources().getDimension(R.dimen.dp_pixel_3);
        m_manager.createMark(location, 0xff0000ff, w, pinColor, needSelect, userId);
    }

    private void proceed(Manager.Location location) {
        final Coordinate3D c3d = location.m_coord3D;
        Mathe.IndoorLatLng center = Mathe.LatLngFromMapPoint(c3d.m_coordinate);
        m_cameraPosition = new IndoorCameraPosition(center, 21.f, 0.f, 0.f);
        m_map.setCameraPosition(m_cameraPosition, false);
        m_manager.showOrdinal(c3d.m_ordinal);

        createMark(location, s_redPinColor, true, s_endPinId);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            // handles a click on a search suggestion; launches activity to show word
            //Intent wordIntent = new Intent(this, WordActivity.class);
            //wordIntent.setData(intent.getData());
            //startActivity(wordIntent);
            final String placeId = intent.getStringExtra(SearchManager.EXTRA_DATA_KEY);
            if (placeId == null) {
                Uri uri = intent.getData();
                final String featureIndexStr = uri.getLastPathSegment();
                final int featureIndex = Integer.parseInt(featureIndexStr);

                Coordinate3D c3d = new Coordinate3D(0);
                m_manager.getFeatureCoordinate3D(featureIndex, c3d);

                proceed(new Manager.Location(m_manager, featureIndex, c3d, null, null));
            } else {
                MyApplication app = (MyApplication) getApplication();
                PendingResult<PlaceBuffer> pbuf = Places.GeoDataApi.getPlaceById(app.getGoogleApiClient(), placeId);
                pbuf.setResultCallback(new ResultCallback<PlaceBuffer>() {
                    @Override
                    public void onResult(@NonNull PlaceBuffer places) {
                        final Place place = places.get(0);
                        final LatLng latLng = place.getLatLng();
                        final Coordinate3D coord3d = new Coordinate3D(latLng.latitude, latLng.longitude, 0);
                        final String placeName = place.getName().toString();
                        places.release();
                        proceed(new Manager.Location(null, -1, coord3d, placeId, placeName));
                    }
                });
            }
        } else if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            // handles a search query
            String query = intent.getStringExtra(SearchManager.QUERY);
            //showResults(query);
            Log.e("SearchManager","query:"+query);
        }
    }

    private void setRouteStepButtonEnabled(ImageButton button, boolean enabled) {
        final Resources resources = getResources();
        final Drawable drArrow = resources.getDrawable(R.drawable.arrow);
        drArrow.mutate();

        button.setEnabled(enabled);
        if (enabled) {
            final MyStateDrawable msd = new MyStateDrawable(new Drawable[]{drArrow});
            button.setImageDrawable(msd);
        } else {
            drArrow.setColorFilter(0xff7fb7df, PorterDuff.Mode.SRC_ATOP);
            button.setImageDrawable(drArrow);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();
        switch (id) {
            case R.id.action_cancel:
                cancelAll();
                return true;
            case R.id.action_route:
                Intent intent = new Intent(this, IndoorSearchActivity.class);
                intent.putExtra(MyAppUtils.s_extra_start, m_manager.markLocation(s_startPinId));
                Manager.Location locEnd = m_manager.markLocation(s_endPinId);
                if (locEnd == null) {
                    locEnd = m_manager.markLocation(s_droppedPinId);
                }
                intent.putExtra(MyAppUtils.s_extra_end, locEnd);
                startActivityForResult(intent, s_requestCodeDirections);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // Because this activity has set launchMode="singleTop", the system calls this method
        // to deliver the intent if this activity is currently the foreground activity when
        // invoked again (when the user executes a search from this activity, we don't create
        // a new instance of this activity, so the system delivers the search intent here)
        handleIntent(intent);
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

    private void initLevelSwitch() {

        m_levelsPicker = (WheelPicker) findViewById(R.id.levelsPicker);
        m_levelsPicker.setAtmospheric(true);
        m_levelsPicker.setCurved(true);
        m_levelsPicker.setIndicator(true);
        m_levelsPicker.setIndicatorColor(0xff000000);
        m_levelsPicker.setIndicatorSize(Math.round(1 * mDensity));
        m_levelsPicker.setItemTextColor(0xff000000);
        m_levelsPicker.setItemTextSize(Math.round(14 * mDensity));
        m_levelsPicker.setVisibleItemCount(5);
        m_levelsPicker.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker picker, Object data, int position) {
                m_levelsPicker.setVisibility(View.INVISIBLE);
                findViewById(R.id.levelsButton).setVisibility(View.VISIBLE);
                final List<Integer> ordinals = m_manager.getLevelOrdinals();
                m_manager.showOrdinal(ordinals.get(ordinals.size() - 1 - position));
                // illustrate setZoomLevels
                /*m_manager.setZoomLevels(new Manager.SetZoomLevelsCallback() {
                    @Override
                    public void onMarker(String mapLayer, JSONObject props, boolean isIcon, float[] zoomLevels) {
                        final String category = Utils.OptString(props, "CATEGORY");
                        if (category.equals("Restaurants"))
                        {
                            zoomLevels[0] = 10000.f;
                        }
                    }
                });*/
            }
        });
    }

    @OnClick({R.id.imageView_clear, R.id.imageView_search, R.id.imageView_location, R.id.imageView_home, R.id.levelsButton})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageView_clear:

                break;
            case R.id.imageView_search:
                // TODO: 2017/8/22
                Intent i = new Intent(this, IndoorSearchActivity.class);
                startActivity(i);
                break;
            case R.id.imageView_location:
                // TODO: 2017/8/22
                break;
            case R.id.imageView_home:
                onBackPressed();
                break;
            case R.id.levelsButton:
                if (m_initialized) {
                    final List<Integer> ordinals = m_manager.getLevelOrdinals();
                    if (ordinals != null) {
                        final List<String> ordinalsStrRev = new ArrayList<>();
                        final ListIterator<Integer> li = ordinals.listIterator(ordinals.size());
                        while (li.hasPrevious()) {
                            final Integer val = li.previous();
                            ordinalsStrRev.add(String.valueOf(val));
                        }
                        m_levelsPicker.setData(ordinalsStrRev);

                        final int curOrdinal = m_manager.getOrdinal();
                        final int indexOf = ordinals.indexOf(curOrdinal);
                        view.setVisibility(View.INVISIBLE);
                        m_levelsPicker.setSelectedItemPosition(ordinals.size() - 1 - indexOf);
                        m_levelsPicker.setVisibility(View.VISIBLE);
                    }
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);

        //final Point p = new Point();
        //getWindowManager().getDefaultDisplay().getSize(p);
        //ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(p.x, ViewGroup.LayoutParams.MATCH_PARENT);
        //searchView.setLayoutParams(params);

        return true;
    }

    private void cancelAll() {
        m_wantHaveRoute = false;
        m_manager.dropRoute(m_routeIdIndoor);
        m_manager.dropRoute(m_routeIdOutdoor);
        m_manager.dropMark(s_droppedPinId);
        m_manager.dropMark(s_startPinId);
        m_manager.dropMark(s_endPinId);

        m_routeBar.setVisibility(View.INVISIBLE);
    }

    void gotoRouteStep(int stepIndex, boolean needCenter) {
        m_routeStepsIndex = stepIndex;
        if (stepIndex < m_routeSteps.size()) {
            Manager.IndoorMapStep step = m_routeSteps.get(stepIndex);
            m_labelStep.setText(step.m_instructions);

            if (needCenter) {
                final List<Mathe.MapPoint> polyline = step.m_polyline;
                final int n = polyline.size();
                if (n > 0) {
                    Mathe.MapPoint point = polyline.get(0);
                    double xmin = point.x;
                    double ymin = point.y;
                    double xmax = xmin;
                    double ymax = ymin;
                    for (int i = 1; i < n; ++i) {
                        Mathe.MapPoint p1 = polyline.get(i);

                        xmin = Math.min(xmin, p1.x);
                        xmax = Math.max(xmax, p1.x);
                        ymin = Math.min(ymin, p1.y);
                        ymax = Math.max(ymax, p1.y);
                    }
                    //final double s_margin = 10.;
                    m_map.setVisibleMapRect(new Mathe.MapRect(xmin, ymin, xmax - xmin, ymax - ymin), true);
                }
                m_manager.showOrdinal(step.m_ordinal);
            }
        }
        setRouteStepButtonEnabled(m_buttonNext, stepIndex + 1 < m_routeSteps.size());
        setRouteStepButtonEnabled(m_buttonPrev, stepIndex > 0);
    }

    private void recalcRoute(final boolean needCenter) {
        Assert.assertTrue(m_wantHaveRoute);
        m_wantRecalcRoute = true;
        if (!m_manager.canCalcRoute()) {
            return;
        }
        m_wantRecalcRoute = false;

        Manager.Location locFrom1 = m_manager.markLocation(s_startPinId);
        if (null == locFrom1) {
            locFrom1 = ((MyApplication) getApplication()).getUserLocation(null);
            if (null == locFrom1) {
                return;
            }
        }
        Manager.Location locTo1 = m_manager.markLocation(s_endPinId);
        if (null == locTo1) {
            locTo1 = ((MyApplication) getApplication()).getUserLocation(null);
            if (null == locTo1) {
                return;
            }
        }

        final Manager.Location locFrom = locFrom1;
        final Manager.Location locTo = locTo1;

        m_manager.calcRoute(locFrom, locTo, new Manager.CalcRouteCallback() {
            @Override
            public void onIndoorMapManagerRouteCalculated(Manager.RouteResult[] results) {
                m_manager.dropRoute(m_routeIdIndoor);
                m_manager.dropRoute(m_routeIdOutdoor);
                m_routeBar.setVisibility(View.INVISIBLE);

                if (results == null || !m_wantHaveRoute) {
                    return;
                }

                final float density = getResources().getDisplayMetrics().density;

                List<Manager.IndoorMapStep> steps = new ArrayList<>();
                int duration = 0;
                for (Manager.RouteResult res : results) {
                    duration += res.m_duration;

                    final IndoorPolylineOptions options = new IndoorPolylineOptions();
                    options.color(res.m_isIndoor ? s_routeColorIndoor : s_routeColorOutdoor).width(2.5f * density);
                    m_manager.createRoute(res.m_lines, options, res.m_isIndoor ? m_routeIdIndoor : m_routeIdOutdoor);
                    steps.addAll(res.m_steps);
                }

                m_distLabel.setText(GetTimeString_(duration));

                String titles[] = new String[2];
                m_manager.getTitleForLocation(locTo, titles);
                m_descLabel.setText(String.format(getString(R.string.route_string), titles[0]));

                m_routeSteps = steps;
                gotoRouteStep(0, needCenter);

                m_routeBar.setVisibility(View.VISIBLE);

                if (m_wantRecalcRoute) {
                    recalcRoute(needCenter);
                }
            }
        });
    }

    private static String GetTimeString_(int timeSec) {
        timeSec = ((timeSec + 2) / 5) * 5;
        final int hours = timeSec / 3600;
        timeSec -= hours * 3600;
        final int minutes = timeSec / 60;
        timeSec -= minutes * 60;

        if (hours > 0) {
            return String.format("%dh %dmin", hours, minutes);
        } else if (minutes > 0) {
            return String.format("%dmin %ds", minutes, timeSec);
        }
        return String.format("%ds", timeSec);
    }

    @Override
    public void onLocation(Location aLocation) {
        if (m_wantHaveRoute && (m_manager.markLocation(s_startPinId) == null || m_manager.markLocation(s_endPinId) == null)) {
            recalcRoute(false);
        }
        final MyApplication app = (MyApplication) getApplication();
        float[] bearing = {0};
        final Manager.Location location = app.getUserLocation(bearing);
//        if (!BuildConfig.isOverlay) {
//            m_manager.setUserLocation(location, bearing[0]);
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == s_requestCodeInfo || requestCode == s_requestCodeDirections) && resultCode == RESULT_OK) {
            final Manager.Location start = data.getParcelableExtra(MyAppUtils.s_extra_start);
            final Manager.Location end = data.getParcelableExtra(MyAppUtils.s_extra_end);

            cancelAll();
            if (start != null) {
                createMark(start, s_greenPinColor, true, s_startPinId);
            } else {
                m_manager.dropMark(s_startPinId);
            }
            if (end != null) {
                createMark(end, s_redPinColor, true, s_endPinId);
            } else {
                m_manager.dropMark(s_endPinId);
            }

            m_wantHaveRoute = true;
            recalcRoute(true);
        }
    }


}
