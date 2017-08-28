package com.whatmedia.ttia.page.IndoorMap;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.point_consulting.pc_indoormapoverlaylib.Coordinate3D;
import com.point_consulting.pc_indoormapoverlaylib.Manager;
import com.point_consulting.pc_indoormapoverlaylib.Mathe;
import com.whatmedia.ttia.R;
import com.whatmedia.ttia.component.MyMarquee;
import com.whatmedia.ttia.component.MyToolbar;
import com.whatmedia.ttia.page.IndoorBaseActivity;
import com.whatmedia.ttia.utility.MyAppUtils;
import com.whatmedia.ttia.utility.MyApplication;
import com.whatmedia.ttia.utility.Util;

import junit.framework.Assert;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IndoorSearchActivity extends IndoorBaseActivity {

    @BindView(R.id.myToolbar)
    MyToolbar mMyToolbar;
    @BindView(R.id.myMarquee)
    MyMarquee mMyMarquee;
    @BindView(R.id.loadingView)
    FrameLayout mLoadingView;
    @BindView(R.id.imageView_home)
    ImageView mImageViewHome;

    @BindView(R.id.directions_table)
    ListView mTable;
    @BindView(R.id.directions_route)
    Button mButton_route;
    @BindView(R.id.directions_from)
    EditText m_fromEditText;
    @BindView(R.id.directions_to)
    EditText m_toEditText;




    private String mMarqueeMessage;

    private SItem m_selectedStart;
    private SItem m_selectedEnd;
    private Manager m_manager;
    private static final String s_titleStr = "title";
    private static final String s_subtitleStr = "subtitle";
    private List<Map<String, String> > m_list = new ArrayList<>();
    private SimpleAdapter m_adapter;
    private TextWatcher m_fromTextWatcher;
    private TextWatcher m_toTextWatcher;
    private List<Manager.FeatureDesc> m_featureDescList;
    private List<MyApplication.OutdoorFeature> m_outdoorFeatures;
    private boolean m_hasCurrentLocationRequest;
    private List<Manager.Location> m_locations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indoor_search);
        ButterKnife.bind(this);
        initAppbar();
        initMarquee();

        MyApplication app = (MyApplication)getApplication();
        m_manager = app.m_manager;

        m_adapter = new SimpleAdapter(this, m_list, android.R.layout.simple_list_item_2, new String[]{s_titleStr, s_subtitleStr}, new int[]{android.R.id.text1, android.R.id.text2});
        mTable.setAdapter(m_adapter);

        mTable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Manager.Location location = m_locations.get(i);
                final Map<String, String> m = m_list.get(i);
                if (m_fromEditText.isFocused())
                {
                    m_fromEditText.removeTextChangedListener(m_fromTextWatcher);
                    m_fromEditText.setText(m.get(s_titleStr));
                    m_fromEditText.addTextChangedListener(m_fromTextWatcher);
                    m_selectedStart = new SItem(location);
                }
                else
                {
                    Assert.assertTrue(m_toEditText.isFocused());
                    m_toEditText.removeTextChangedListener(m_toTextWatcher);
                    m_toEditText.setText(m.get(s_titleStr));
                    m_toEditText.addTextChangedListener(m_toTextWatcher);
                    m_selectedEnd = new SItem(location);
                }
                mButton_route.setEnabled(m_selectedStart != null && m_selectedEnd != null);
            }
        });

        final Intent intent = getIntent();

        final Manager.Location locStart = intent.getParcelableExtra(MyAppUtils.s_extra_start);
        if (locStart == null) {
            final String curLocStr = "Current location";
            if (!curLocStr.isEmpty()) {
                m_selectedStart = new SItem(null);
                m_fromEditText.setText(curLocStr);
            }
        }
        else
        {
            m_selectedStart = new SItem(locStart);
            final String[] titles = new String[2];
            m_manager.getTitleForLocation(locStart, titles);
            m_fromEditText.setText(titles[0]);
        }

        final Manager.Location locEnd = intent.getParcelableExtra(MyAppUtils.s_extra_end);
        if (locEnd != null) {
            m_selectedEnd = new SItem(locEnd);
            final String[] titles = new String[2];
            m_manager.getTitleForLocation(locEnd, titles);
            m_toEditText.setText(titles[0]);
        }

        mButton_route.setEnabled(m_selectedStart != null && m_selectedEnd != null);

        m_fromTextWatcher = new MyTextWatcher(m_fromEditText);
        m_toTextWatcher = new MyTextWatcher(m_toEditText);
        m_fromEditText.addTextChangedListener(m_fromTextWatcher);
        m_toEditText.addTextChangedListener(m_toTextWatcher);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (m_selectedStart == null)
        {
            m_fromEditText.requestFocus();
        }
        else if (m_selectedEnd == null)
        {
            m_toEditText.requestFocus();
        }
    }

    public void onSwap(View view)
    {
        final SItem tmp = m_selectedEnd;
        m_selectedEnd = m_selectedStart;
        m_selectedStart = tmp;

        final String t1 = m_fromEditText.getText().toString();
        m_fromEditText.removeTextChangedListener(m_fromTextWatcher);
        m_toEditText.removeTextChangedListener(m_toTextWatcher);
        m_fromEditText.setText(m_toEditText.getText().toString());
        m_toEditText.setText(t1);
        m_fromEditText.addTextChangedListener(m_fromTextWatcher);
        m_toEditText.addTextChangedListener(m_toTextWatcher);

        if (m_fromEditText.hasFocus())
        {
            m_toEditText.requestFocus();
        }
        else if (m_toEditText.hasFocus())
        {
            m_fromEditText.requestFocus();
        }
    }

    private void done(final Manager.Location startLocation, final Manager.Location endLocation)
    {
        final Intent intent = new Intent();
        intent.putExtra(MyAppUtils.s_extra_start, startLocation);
        intent.putExtra(MyAppUtils.s_extra_end, endLocation);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onRoute(View view)
    {
        if (m_selectedStart != null && m_selectedEnd != null) {
            final Manager.Location startLocation = m_selectedStart.m_location;
            final Manager.Location endLocation = m_selectedEnd.m_location;
            final String pStart = startLocation != null ? startLocation.m_placeId : null;
            final String pEnd = endLocation != null ? endLocation.m_placeId : null;
            int c = 0;
            if (pStart != null)
            {
                ++c;
            }
            if (pEnd != null)
            {
                ++c;
            }
            if (c > 0)
            {
                int k = 0;
                final String[] ar = new String[c];
                if (pStart != null)
                {
                    ar[k++] = pStart;
                }
                if (pEnd != null)
                {
                    ar[k++] = pEnd;
                }
                MyApplication app = (MyApplication)getApplication();
                PendingResult<PlaceBuffer> pbuf = Places.GeoDataApi.getPlaceById(app.getGoogleApiClient(), ar);
                pbuf.setResultCallback(new ResultCallback<PlaceBuffer>() {
                    @Override
                    public void onResult(@NonNull PlaceBuffer places) {
                        int k = 0;
                        Manager.Location newStartLocation = startLocation;
                        Manager.Location newEndLocation = endLocation;
                        for (Place place : places)
                        {
                            final LatLng latLng = place.getLatLng();
                            final Coordinate3D coord3d = new Coordinate3D(latLng.latitude, latLng.longitude, 0);
                            final String placeName = place.getName().toString();
                            if (ar[k] == pStart)
                            {
                                newStartLocation = new Manager.Location(null, -1, coord3d, pStart, placeName);
                            }
                            else if (ar[k] == pEnd)
                            {
                                newEndLocation = new Manager.Location(null, -1, coord3d, pEnd, placeName);
                            }
                            ++k;
                        }
                        places.release();
                        done(newStartLocation, newEndLocation);
                    }
                });
            }
            else {
                done(startLocation, endLocation);
            }
        }
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

    private static class SItem
    {
        SItem(Manager.Location location)
        {
            m_location = location;
        }
        Manager.Location m_location;
    }

    private void update()
    {
        m_list.clear();
        m_locations.clear();

        if (m_hasCurrentLocationRequest)
        {
            m_locations.add(null);

            Map<String, String> map = new HashMap<String, String>();
            map.put(s_titleStr, "current_location");

            m_list.add(map);
        }

        if (m_featureDescList != null) {
            final MyApplication app = (MyApplication)getApplication();
            for (Manager.FeatureDesc fd : m_featureDescList) {
                final Manager.Location location = new Manager.Location(m_manager, fd.m_featureIndex, null, null, null);
                m_locations.add(location);

                Map<String, String> map = new HashMap<>();
                map.put(s_titleStr, fd.m_name);

                if (m_manager != null) {
                    final Map<String, String> props = m_manager.propsForFeature(fd.m_featureIndex);
                    final String category = MyAppUtils.OptString(props, MyAppUtils.SUBTITLE_FIELD);
                    final String levelId = MyAppUtils.OptString(props, "LEVEL_ID");
                    final String levelName = app.nameForLevelId(levelId);
                    map.put(s_subtitleStr, String.format("%s - %s", category, levelName));
                }

                m_list.add(map);
            }
        }

        if (m_outdoorFeatures != null) {
            for (MyApplication.OutdoorFeature oft : m_outdoorFeatures) {
                Mathe.MapPoint mp = new Mathe.MapPoint(0.0, 0.0);
                final Manager.Location location = new Manager.Location(m_manager, -1, null, oft.m_placeId, null);
                m_locations.add(location);

                Map<String, String> map = new HashMap<>();
                map.put(s_titleStr, oft.m_title);
                map.put(s_subtitleStr, oft.m_subtitle);

                m_list.add(map);
            }
        }

        m_adapter.notifyDataSetChanged();
    }

    private class MyTextWatcher implements TextWatcher
    {
        private EditText m_editText;

        MyTextWatcher(EditText et)
        {
            m_editText = et;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        private Coordinate3D c3dForSearch()
        {
            if (m_selectedStart != null && m_selectedStart.m_location != null)
            {
                return m_selectedStart.m_location.m_coord3D;
            }
            if (m_selectedEnd != null && m_selectedEnd.m_location != null)
            {
                return m_selectedEnd.m_location.m_coord3D;
            }
            final Manager.Location userLocation = ((MyApplication)getApplication()).getUserLocation(null);
            return userLocation != null ? userLocation.m_coord3D : null;
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            final int len = charSequence.length();
            if (len >= 2)
            {
                final boolean c = "Current location".toLowerCase().contains(charSequence.toString().toLowerCase());
                if (c != m_hasCurrentLocationRequest)
                {
                    m_hasCurrentLocationRequest = c;
                    update();
                }

                final String s = charSequence.toString();
                if (m_manager != null)
                {
                    m_manager.searchAsync(s, MyAppUtils.s_searchField, c3dForSearch(), new Manager.SearchCallback() {
                        @Override
                        public void onSearchDone(List<Manager.FeatureDesc> list) {
                            m_featureDescList = list;
                            update();
                        }
                    });
                }

                MyApplication app = (MyApplication)getApplication();
                app.searchOutdoorAsync(s, new MyApplication.SearchCallback() {
                    @Override
                    public void onOutdoorSearchDone(List<MyApplication.OutdoorFeature> features) {
                        m_outdoorFeatures = features;
                        update();
                    }
                });
            }
            else
            {
                m_hasCurrentLocationRequest = false;
                m_featureDescList = null;
                m_outdoorFeatures = null;
                update();
            }
            if (m_editText == m_fromEditText)
            {
                m_selectedStart = null;
            }
            else
            {
                Assert.assertTrue(m_editText == m_toEditText);
                m_selectedEnd = null;
            }
            mButton_route.setEnabled(m_selectedStart != null && m_selectedEnd != null);
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }
}
