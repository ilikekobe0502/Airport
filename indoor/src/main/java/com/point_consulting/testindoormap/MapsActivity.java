package com.point_consulting.testindoormap;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.aigestudio.wheelpicker.WheelPicker;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.maps.android.SphericalUtil;
import com.point_consulting.pc_indoormapoverlaylib.AbstractFolder;
import com.point_consulting.pc_indoormapoverlaylib.AssetsFolder;
import com.point_consulting.pc_indoormapoverlaylib.Coordinate3D;
import com.point_consulting.pc_indoormapoverlaylib.ExternalFolder;
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

import junit.framework.Assert;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class MapsActivity extends Activity implements OnMapReadyCallback, DownloadMaps.Delegate, MyApplication.GoogleLocationCallback {
    // (-23.577037474999964, -46.688223968999933) Iguatemi
    // (52.1664162820001, 4.483112697000024) Leiden
    // (30.491355, -81.683837) JAX
    // 52.309893668, 4.76199108900005 AMS
    private static Mathe.IndoorLatLng s_center = new Mathe.IndoorLatLng(25.081723343293813, 121.2408936708626);
    private static Mathe.IndoorLatLng s_hackedCenter = new Mathe.IndoorLatLng(60.040291238122467, 30.392644503671253);

    private static final String s_mapId = "2"; // Iguatemi
//    private static final String s_mapId = "7"; // JAX

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

    private List<MyAppUtils.PropDesc> m_propDesc = new ArrayList<>();
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

    private DownloadMaps m_downloadMaps;
    private ProgressDialog m_progressDialog;

    private boolean m_wantHaveRoute;
    private boolean m_wantRecalcRoute;

    private Typeface m_typeface;

    private static final boolean s_useServer = false;

    private void startMap(AbstractFolder abstractFolder) {
        m_progressDialog.setTitle("Preparing maps...");
        Manager.InitializationCallback initializationCallback = new Manager.InitializationCallback() {
            public void onIndoorMapManagerInitialized() {
                if (m_progressDialog != null && m_progressDialog.isShowing())
                    m_progressDialog.dismiss();
                m_progressDialog = null;
                m_initialized = true;
                MyApplication app = (MyApplication) getApplication();
                app.m_manager = m_manager;

                final List<Integer> ordinals = m_manager.getLevelOrdinals();
                if (ordinals != null) {
                    final int n = ordinals.size();
                    if (n > 0) {
                        int ordinal = ordinals.get(0);
                        for (Integer ordN : ordinals) {
                            if (ordN == 0) {
                                ordinal = 0;
                                break;
                            }
                        }

                        if (null == m_cameraPosition) {
                            m_cameraPosition = new IndoorCameraPosition(s_center, 20.f, 0.f, 0.f);
                        }

                        m_map.setCameraPosition(m_cameraPosition, false);
                        m_manager.showOrdinal(ordinal);
                    }
                }

                // illustrate enumerateFeatures
/*
                final int[] toSelectIndex = {-1};
                m_manager.enumerateFeatures(0, new Manager.EnumerateFeaturesCallback() {
                    @Override
                    public boolean onEnumeratingFeature(int featureIndex, String mapLayer, JSONObject props) {
                        final String name = Utils.OptString(props, "NAME");
                        if (name.equals("Baggage Claim 5") && mapLayer.equals("Points"))
                        {
                            toSelectIndex[0] = featureIndex;
                            return true;
                        }
                        return false;
                    }
                });*/


                // illustrate selectMarket
                /*if (toSelectIndex[0] != -1)
                {
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            final IMarker m = m_manager.getMarker(toSelectIndex[0], false);
                            m_map.selectMarker(m);
                        }
                    }, 200);
                }*/
            }
        };

        final Map<String, String[]> titleFieldsForMapLayer = new HashMap<>();
        titleFieldsForMapLayer.put("Units", new String[]{"CATEGORY"});
        titleFieldsForMapLayer.put("Points", new String[]{"NAME", ""});
        titleFieldsForMapLayer.put("Occupants", new String[]{"NAME", "CATEGORY"});
        titleFieldsForMapLayer.put("Zones", new String[]{"NAME"});

        m_manager.initializeAsync(abstractFolder, titleFieldsForMapLayer, m_isWheelchairMode ? 1 : 0, initializationCallback);
    }

    private void onFinishedDownload() {
        AbstractFolder abstractFolder = null;

        if (s_useServer) {
            File dir = DownloadMaps.GetDir(this, s_mapId);
            final File[] list = dir.listFiles();

            if (list != null && list.length > 0) {
                abstractFolder = new ExternalFolder(dir);
            }
        }

        if (abstractFolder == null) {
            final AssetManager assets = getAssets();
            abstractFolder = new AssetsFolder(assets, "JAX");
        }
        startMap(abstractFolder);
    }

    @Override
    public void onCheckMapUpdatesFinishedWithResult(int checkResult) {
        switch (checkResult) {
            case DownloadMaps.DOWNLOAD_MAPS_CHECK_RESULT_ERROR:
                onFinishedDownload();
                break;
            case DownloadMaps.DOWNLOAD_MAPS_CHECK_RESULT_READY:
                onFinishedDownload();
                break;
            default:
                Assert.assertEquals(checkResult, DownloadMaps.DOWNLOAD_MAPS_CHECK_RESULT_DOWNLOADING);
                m_progressDialog.setTitle("Downloading maps...");
                //self.m_downloadView.hidden = NO;
        }
    }

    @Override
    public void onDownloadMapsFinishedWithError(String error) {
        onFinishedDownload();
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
//            showResults(query);
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

    private void updateNavModeButton() {
        View navModeButton = findViewById(R.id.navModeButton);
        int bgId = m_isWheelchairMode ? R.drawable.icon_small_wheelchair : R.drawable.icon_small_escalator;
        navModeButton.setBackgroundResource(bgId);
    }

    private void updateUserTrackingModeButton(int userTrackingMode) {
        final View button = findViewById(R.id.userTrackingModeButton);
        if (button != null) {
            int id;
            switch (userTrackingMode) {
                case IMap.USER_TRACKING_MODE_NONE:
                    id = R.drawable.target;
                    break;
                case IMap.USER_TRACKING_MODE_FOLLOW:
                    id = R.drawable.target_follow;
                    break;
                default:
                    Assert.assertEquals(userTrackingMode, IMap.USER_TRACKING_MODE_FOLLOW_WITH_HEADING);
                    id = R.drawable.target_follow_with_heading;
                    break;
            }
            button.setBackgroundResource(id);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        m_progressDialog = new ProgressDialog(this);
//        m_progressDialog.show();
        m_typeface = Typeface.createFromAsset(getAssets(), "arial_mt_bold.otf");

        /*Mathe.MapPoint mpCenter = new Mathe.MapPoint(0);
        Mathe.MapPointFromLatLng(s_center.latitude, s_center.longitude, mpCenter);

        Mathe.MapPoint mpHackedCenter = new Mathe.MapPoint(0);
        Mathe.MapPointFromLatLng(s_hackedCenter.latitude, s_hackedCenter.longitude, mpHackedCenter);

        Utils.SetHack(mpHackedCenter.x - mpCenter.x, mpHackedCenter.y - mpCenter.y);
        s_center = s_hackedCenter;*/

        final float density = getResources().getDisplayMetrics().density;

        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(false);
        getActionBar().setIcon(new ColorDrawable(0));

        m_propDesc.add(new MyAppUtils.PropDesc(R.drawable.clock, "HOURS"));
        m_propDesc.add(new MyAppUtils.PropDesc(R.drawable.phone, "PHONE"));
        m_propDesc.add(new MyAppUtils.PropDesc(R.drawable.web, "WEBSITE"));

        if (savedInstanceState != null) {
            final double lat = savedInstanceState.getDouble(s_camPosLat);
            final double lon = savedInstanceState.getDouble(s_camPosLon);
            final float zoom = savedInstanceState.getFloat(s_camPosZoom);
            final float bearing = savedInstanceState.getFloat(s_camPosBearing);
            m_cameraPosition = new IndoorCameraPosition(new Mathe.IndoorLatLng(lat, lon), zoom, 0.f, bearing);
        }

        if (BuildConfig.isOverlay) {
            // set bounds for outdoor search
            final LatLng center = new LatLng(s_center.latitude, s_center.longitude);
            final double radius = 20000;
            final LatLng southwest = SphericalUtil.computeOffset(center, radius * Math.sqrt(2.0), 225);
            final LatLng northeast = SphericalUtil.computeOffset(center, radius * Math.sqrt(2.0), 45);

            MyApplication app = (MyApplication) getApplication();
            app.m_bounds = new LatLngBounds(southwest, northeast);

            setContentView(R.layout.activity_maps_google);
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            com.google.android.gms.maps.MapFragment mapFragment = (com.google.android.gms.maps.MapFragment) getFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        } else {
            setContentView(R.layout.activity_maps_indoor);

            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            com.point_consulting.pc_indoormapoverlaylib.MapFragment mapFragment = (com.point_consulting.pc_indoormapoverlaylib.MapFragment) getFragmentManager()
                    .findFragmentById(R.id.map);
            final IndoorMap indoorMap = mapFragment.getMap();
            indoorMap.setMaxZoom(23.0);
            indoorMap.setCalloutFontSize(density * 12.f, density * 10.f);
            indoorMap.setArrowParams(16, 20, s_routeColorIndoor, 30.f, 120.f);
            start(new MapImplIndoor(indoorMap));
        }

        final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        m_isWheelchairMode = pref.getBoolean(s_navModeKey, false);
        updateNavModeButton();

        m_routeBar = (ViewGroup) findViewById(R.id.routeBar);
        m_descLabel = (TextView) m_routeBar.findViewById(R.id.descLabel);
        m_labelStep = (TextView) m_routeBar.findViewById(R.id.stepLabel);
        m_buttonPrev = (ImageButton) m_routeBar.findViewById(R.id.route_prev);
        m_buttonNext = (ImageButton) m_routeBar.findViewById(R.id.route_next);
        m_distLabel = (TextView) m_routeBar.findViewById(R.id.routeTime);

        m_levelsPicker = (WheelPicker) findViewById(R.id.levelsPicker);
        m_levelsPicker.setAtmospheric(true);
        m_levelsPicker.setCurved(true);
        m_levelsPicker.setIndicator(true);
        m_levelsPicker.setIndicatorColor(0xff000000);
        m_levelsPicker.setIndicatorSize(Math.round(1 * density));
        m_levelsPicker.setItemTextColor(0xff000000);
        m_levelsPicker.setItemTextSize(Math.round(14 * density));
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

        handleIntent(getIntent());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();
        switch (id) {
            case R.id.action_cancel:
                cancelAll();
                return true;
            case R.id.action_route:
                Intent intent = new Intent(this, DirectionsActivity.class);
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

    private Bitmap getBitmap(int id) {
        Drawable dr = ContextCompat.getDrawable(this, id);
        BitmapDrawable bdr = (BitmapDrawable) dr;
        return bdr.getBitmap();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        retryLoadMaps();
    }

    private static final int MY_REQUEST_PERMISSIONS = 1;

    public void onButton(View view) {
        final int id = view.getId();
        if (id == R.id.stepLabel) {
            gotoRouteStep(m_routeStepsIndex, true);
        } else if (id == R.id.levelsButton) {
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
        } else if (id == R.id.route_next) {
            if (m_routeStepsIndex + 1 < m_routeSteps.size()) {
                gotoRouteStep(m_routeStepsIndex + 1, true);
            }
        } else if (id == R.id.route_prev) {
            if (m_routeStepsIndex > 0) {
                gotoRouteStep(m_routeStepsIndex - 1, true);
            }
        } else if (id == R.id.navModeButton) {
            if (m_manager.setNavIndex(m_isWheelchairMode ? 0 : 1)) {
                m_isWheelchairMode = !m_isWheelchairMode;
                final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
                final SharedPreferences.Editor ed = pref.edit();
                ed.putBoolean(s_navModeKey, m_isWheelchairMode);
                ed.commit();
                updateNavModeButton();

                if (m_wantHaveRoute) {
                    recalcRoute(true);
                }
            }
        } else if (id == R.id.userTrackingModeButton) {
            final int curMode = m_map.getUserTrackingMode();
            int newMode;
            switch (curMode) {
                case IMap.USER_TRACKING_MODE_NONE:
                    newMode = IMap.USER_TRACKING_MODE_FOLLOW;
                    break;
                case IMap.USER_TRACKING_MODE_FOLLOW:
                    newMode = IMap.USER_TRACKING_MODE_FOLLOW_WITH_HEADING;
                    break;
                default:
                    Assert.assertEquals(curMode, IMap.USER_TRACKING_MODE_FOLLOW_WITH_HEADING);
                    newMode = IMap.USER_TRACKING_MODE_NONE;
            }
            m_map.setUserTrackingMode(newMode);
        }
    }

    private void retryLoadMaps() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION}, MY_REQUEST_PERMISSIONS);
            return;
        }

        m_map.setMyLocationEnabled(true);

        MyApplication app = (MyApplication) getApplication();
        app.setHaveAccessLocationPermission();

//        m_progressDialog = new ProgressDialog(this);
        m_progressDialog.setCancelable(false);

        if (s_useServer) {
            m_downloadMaps = new DownloadMaps(this, this);
            m_progressDialog.setTitle("Check for map updates...");
            m_progressDialog.show();
            m_downloadMaps.downloadMapId(s_mapId);
        } else {
            onFinishedDownload();
        }
    }

    private void createMark(Manager.Location location, int pinColor, boolean needSelect, String userId) {
        final float w = getResources().getDimension(R.dimen.d3);
        m_manager.createMark(location, 0xff0000ff, w, pinColor, needSelect, userId);
    }

    private void onCalloutDetailTapped(Manager.Location location) {
        final int featureIndex = location.m_featureIndex;
        JSONObject obj;
        ArrayList<MyAppUtils.PropDesc> props = new ArrayList<>();
        int headerColor = 0xffffffff;
        int logoId = 0;
        if (featureIndex != -1) {
            obj = m_manager.propsForFeature(featureIndex);
            for (MyAppUtils.PropDesc pd : m_propDesc) {
                final String text = Utils.OptString(obj, pd.m_string);
                if (!text.isEmpty()) {
                    props.add(new MyAppUtils.PropDesc(pd.m_id, text));
                }
            }
            final String category = Utils.OptString(obj, "CATEGORY");
            if (category.equals("Accessories") || category.equals("Applicances") || category.equals("Art Galleries") || category.equals("Auto Parts & Supplies") || category.equals("Automotive") || category.equals("Beer, Wine & Spirits") || category.equals("Books, Mags, Music & Video") || category.equals("Cards & Stationery") || category.equals("Children's Clothing") || category.equals("Cosmetics & Beauty Supply") || category.equals("Department Stores") || category.equals("Dry Cleaning & Laundry") || category.equals("Eyewear & Opticians") || category.equals("Fashion") || category.equals("Florists") || category.equals("Shopping") || category.equals("Furniture Stores") || category.equals("Grocery") || category.equals("Shopping") || category.equals("Home & Garden") || category.equals("Jewelry") || category.equals("Shopping") || category.equals("Lingerie") || category.equals("Luggage") || category.equals("Shopping") || category.equals("Men's Clothin") || category.equals("Nurseries & Gardening") || category.equals("Pet Stores") || category.equals("Photography Stores & Services") || category.equals("Sewing & Alterations") || category.equals("Shoe Stores") || category.equals("Shopping") || category.equals("Sporting Goods") || category.equals("Toy Stores") || category.equals("Watches") || category.equals("Sporting Goods") || category.equals("Women's Clothing")) {
                headerColor = 0xffcce5ff;
            } else if (category.equals("Cafes") || category.equals("Coffee & Tea") || category.equals("Specialty Food") || category.equals("Restaurants")) {
                headerColor = 0xffa2dcb3;
            } else if (category.equals("Health & Medical") || category.equals("Pharmacy")) {
                headerColor = 0xfffccccf;
            } else if (category.equals("Banks & Credit Unions") || category.equals("Financial Services")) {
                headerColor = 0xffffe4c4;
            } else if (category.equals("Arts & Entertainment") || category.equals("Cinema") || category.equals("Landmarks & Historical") || category.equals("Buildings") || category.equals("Opera & Ballet") || category.equals("Performing Arts")) {
                headerColor = 0xffd45c5c;
            } else if (category.equals("Beauty & Spas") || category.equals("Car Rental") || category.equals("Education") || category.equals("Hotels") || category.equals("Libraries") || category.equals("Local Services") || category.equals("Post Offices") || category.equals("Professional Services") || category.equals("Property Management") || category.equals("Public Services & Government") || category.equals("Real Estate Agents") || category.equals("Real Estate Services") || category.equals("Travel Services") || category.equals("Veterinarians")) {
                headerColor = 0xff5ecfd2;
            }
            final String name = Utils.OptString(obj, "NAME");
            switch (name) {
                case "American Airlines":
                    logoId = R.drawable.aa;
                    break;
                case "Brighton":
                    logoId = R.drawable.brighton;
                    break;
                case "Brooks Brothers":
                    logoId = R.drawable.brooks;
                    break;
                case "Burger King":
                    logoId = R.drawable.burgerking;
                    break;
                case "Chili's Too":
                    logoId = R.drawable.chillis;
                    break;
                case "Ciao":
                    logoId = R.drawable.ciao;
                    break;
                case "CNBC":
                    logoId = R.drawable.cnbc;
                    break;
                case "Delta Air Lines":
                    logoId = R.drawable.delta;
                    break;
                case "Freshens":
                    logoId = R.drawable.freshens;
                    break;
                case "InMotion Entertainment":
                    logoId = R.drawable.inmotion;
                    break;
                case "Insight":
                    logoId = R.drawable.insight;
                    break;
                case "JetBlue":
                    logoId = R.drawable.jetblue;
                    break;
                case "Nathan's Famous":
                    logoId = R.drawable.nathans;
                    break;
                case "PGA TOUR Superstore":
                    logoId = R.drawable.pga;
                    break;
                case "Quiznos":
                    logoId = R.drawable.quiznos;
                    break;
                case "River City Travel Mart":
                    logoId = R.drawable.river_city_travel;
                    break;
                case "Sam Snead's Tavern":
                    logoId = R.drawable.samsneads;
                    break;
                case "SBARRO":
                    logoId = R.drawable.sbarros;
                    break;
                case "Shula's Bar & Grill":
                    logoId = R.drawable.shula;
                    break;
                case "Silver Airways":
                    logoId = R.drawable.silver;
                    break;
                case "Southwest Airlines":
                    logoId = R.drawable.southwest;
                    break;
                case "Starbucks":
                    logoId = R.drawable.starbucks;
                    break;
                case "United Airlines":
                    logoId = R.drawable.united;
                    break;
                case "Vino Volo":
                    logoId = R.drawable.vino_volo;
                    break;
                case "Alamo Rent a Car":
                    logoId = R.drawable.alamo;
                    break;
                case "Avis Rent a Car System":
                    logoId = R.drawable.avis;
                    break;
                case "Budget Rent a Car":
                    logoId = R.drawable.budget;
                    break;
                case "Enterprise Rent-A-Car":
                    logoId = R.drawable.enterprise;
                    break;
                case "The Hertz Corporation":
                    logoId = R.drawable.hertz;
                    break;
                case "National Car Rental":
                    logoId = R.drawable.nationalcar;
                    break;
                case "Dollar Rent A Car":
                    logoId = R.drawable.dollar;
                    break;
                case "Executive Conference Room":
                    logoId = R.drawable.conference;
                    break;
                case "Made-in-JAX":
                    logoId = R.drawable.made_in_jax;
                    break;
            }
        }

        final Intent intent = new Intent(MapsActivity.this, InfoActivity.class);
        intent.putExtra(MyAppUtils.s_extra_location, location);
        intent.putExtra(MyAppUtils.s_extra_color, headerColor);
        intent.putExtra(MyAppUtils.s_extra_logo, logoId);
        intent.putParcelableArrayListExtra(MyAppUtils.s_extra_propsMap, props);
        startActivityForResult(intent, s_requestCodeInfo);
    }

    public void start(IMap map) {
        m_map = map;

        m_map.setCalloutListener(new IMap.CalloutListener() {
            @Override
            public View createCalloutView(Context context, IMarker marker) {
                // illustrate custom callout
                /*
                final Manager.Location location = marker.getLocation();
                JSONObject obj = m_manager.propsForFeature(location.m_featureIndex);
                final String title = Utils.OptString(obj, "NAME");
                final String subtitle = Utils.OptString(obj, "CATEGORY");

                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setBackgroundColor(0xff00ff00);
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
                        m_map.deselectMarker(m_selectedMarker);
                        MapsActivity.this.onCalloutDetailTapped(location);
                    }
                });

                return layout;
                */
                return null;
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
                MapsActivity.this.onCalloutDetailTapped(marker.getLocation());
            }
        });

        updateUserTrackingModeButton(m_map.getUserTrackingMode());
        m_manager = new Manager(map);
        m_manager.setOnChangedUserTrackingModeListener(new IMap.OnChangedUserTrackingModeListener() {
            @Override
            public void onChangedUserTrackingMode(int userTrackingMode) {
                updateUserTrackingModeButton(userTrackingMode);
            }
        });
        MyApplication app = (MyApplication) getApplication();
        if (BuildConfig.isOverlay) {
            m_manager.setGoogleApiKey(getString(R.string.google_maps_key));
        }
        m_manager.m_delegate = new Manager.Delegate() {
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

            public void onLevelLoaded(int ordinal) {
                TextView tv = (TextView) findViewById(R.id.levelLabel);
                tv.setText(String.valueOf(ordinal));
            }

            public IndoorPolylineOptions getPolylineOptions(String mapLayer, JSONObject props) {
                if (mapLayer.equals("Openings")) {
                    final float w = getResources().getDimension(R.dimen.d2);
                    return new IndoorPolylineOptions().width(w).color(0xffffffff);
                }
                return null;
            }

            public TextOptions getTextOptions(String mapLayer, JSONObject props) {
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
                    return new TextOptions(10.f * density, 0xffffffff, 1, minZoomLevel, maxZoomLevel, 0xff4066e2, padding, padding, m_typeface);
                } else {
                    minZoomLevel = 1;
                    maxZoomLevel = 1;
                }
                if (mapLayer.equals("Units")) {
                    minZoomLevel = 1.f;
                    maxZoomLevel = 1.f;
                }
                return new TextOptions(10.f * density, 0xff363840, 1, minZoomLevel, maxZoomLevel, 0, 0, 0, m_typeface);
            }

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

            public String elevatorNameForFeatures(List<Manager.FeatureParams> features) {
                if (features.size() == 1) {
                    Manager.FeatureParams p = features.get(0);
                    final String category = Utils.OptString(p.m_props, "CATEGORY");
                    return category;
                }
                return "Unknown transport";
            }

            public boolean isPointInsideVenue(Manager.Location location, boolean isDestination, boolean hint) {
                return hint;
            }

            public IndoorPolygonOptions getPolygonOptions(String mapLayer, JSONObject props) {
                if (mapLayer.equals("Levels")) {
                    final String category = Utils.OptString(props, "CATEGORY");
                    if (category.equals("Indoor")) {
                        final float w = getResources().getDimension(R.dimen.d3);
                        return new IndoorPolygonOptions().fillColor(0xffbbb8af).strokeColor(0xff4066e2).strokeWidth(w);
                    }
                    return null;
                } else if (mapLayer.equals("Units")) {
                    final String category = Utils.OptString(props, "CATEGORY");
                    final float w = getResources().getDimension(R.dimen.d1);
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

            public void onLongPress(Manager.Location location) {
                createMark(location, s_purplePinColor, true, s_droppedPinId);
            }

            public void onShortClick(Mathe.MapPoint coordinate) {
                m_levelsPicker.setVisibility(View.INVISIBLE);
                findViewById(R.id.levelsButton).setVisibility(View.VISIBLE);
            }
        };

        final String state = Environment.getExternalStorageState();
        if (!state.equals(Environment.MEDIA_MOUNTED)) {
            Toast t = Toast.makeText(this, "Bad state = " + state, Toast.LENGTH_LONG);
            t.show();

            onFinishedDownload();

            return;
        }

        retryLoadMaps();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setIndoorEnabled(false);
        final UiSettings settings = googleMap.getUiSettings();
        settings.setIndoorLevelPickerEnabled(false);
        settings.setMapToolbarEnabled(false);
        settings.setCompassEnabled(false);
        //settings.setMyLocationButtonEnabled(false);
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.my_map_style));

        com.google.android.gms.maps.MapFragment mapFragment = (com.google.android.gms.maps.MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);

        start(new MapImplGoogle(googleMap, mapFragment.getView(), getResources().getDisplayMetrics().density));
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (m_map != null) {
            final IndoorCameraPosition cp = m_map.getCameraPosition();
            bundle.putDouble(s_camPosLat, cp.target.latitude);
            bundle.putDouble(s_camPosLon, cp.target.longitude);
            bundle.putFloat(s_camPosZoom, cp.zoom);
            bundle.putFloat(s_camPosBearing, cp.bearing);
        }
    }

    @Override
    protected void onDestroy() {
        if (m_progressDialog != null) {
            m_progressDialog.dismiss();
            m_progressDialog = null;
        }
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication app = (MyApplication) getApplication();
        app.m_googleLocationCallback = this;
    }

    @Override
    protected void onPause() {
        MyApplication app = (MyApplication) getApplication();
        app.m_googleLocationCallback = null;
        super.onPause();
    }

    @Override
    public void onLocation(android.location.Location aLocation) {
        if (m_wantHaveRoute && (m_manager.markLocation(s_startPinId) == null || m_manager.markLocation(s_endPinId) == null)) {
            recalcRoute(false);
        }
        final MyApplication app = (MyApplication) getApplication();
        float[] bearing = {0};
        final Manager.Location location = app.getUserLocation(bearing);
        if (!BuildConfig.isOverlay) {
            m_manager.setUserLocation(location, bearing[0]);
        }
    }
}
