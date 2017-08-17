package com.point_consulting.testindoormap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.point_consulting.pc_indoormapoverlaylib.Coordinate3D;
import com.point_consulting.pc_indoormapoverlaylib.Manager;
import com.point_consulting.pc_indoormapoverlaylib.Mathe;

import junit.framework.Assert;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DirectionsActivity extends Activity {

    private static class SItem
    {
        SItem(Manager.Location location)
        {
            m_location = location;
        }
        Manager.Location m_location;
    }

    private SItem m_selectedStart;
    private SItem m_selectedEnd;
    private Manager m_manager;
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

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            final int len = charSequence.length();
            if (len >= 2)
            {
                final boolean c = getString(R.string.current_location).toLowerCase().contains(charSequence.toString().toLowerCase());
                if (c != m_hasCurrentLocationRequest)
                {
                    m_hasCurrentLocationRequest = c;
                    update();
                }

                final String s = charSequence.toString();
                if (m_manager != null)
                {
                    m_manager.searchAsync(s, MyAppUtils.s_searchField, new Manager.SearchCallback() {
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
            updateButtonEnabling();
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

    private static final String s_titleStr = "title";
    private static final String s_subtitleStr = "subtitle";
    private List<Map<String, String> > m_list = new ArrayList<>();
    private SimpleAdapter m_adapter;
    private TextWatcher m_fromTextWatcher;
    private TextWatcher m_toTextWatcher;
    private EditText m_fromEditText;
    private EditText m_toEditText;
    private List<Manager.FeatureDesc> m_featureDescList;
    private List<MyApplication.OutdoorFeature> m_outdoorFeatures;
    private boolean m_hasCurrentLocationRequest;
    private List<Manager.Location> m_locations = new ArrayList<>();

    private void update()
    {
        m_list.clear();
        m_locations.clear();

        if (m_hasCurrentLocationRequest)
        {
            m_locations.add(null);

            Map<String, String> map = new HashMap<String, String>();
            map.put(s_titleStr, getString(R.string.current_location));

            m_list.add(map);
        }

        if (m_featureDescList != null) {
            for (Manager.FeatureDesc fd : m_featureDescList) {
                final Manager.Location location = new Manager.Location(m_manager, fd.m_featureIndex, null, null, null);
                m_locations.add(location);

                Map<String, String> map = new HashMap<>();
                map.put(s_titleStr, fd.m_name);

                if (m_manager != null) {
                    final JSONObject props = m_manager.propsForFeature(fd.m_featureIndex);
                    final String subtitle = props.optString(MyAppUtils.SUBTITLE_FIELD);
                    map.put(s_subtitleStr, subtitle);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);

        MyApplication app = (MyApplication)getApplication();
        m_manager = app.m_manager;

        ListView table = (ListView)findViewById(R.id.directions_table);
        m_adapter = new SimpleAdapter(this, m_list, android.R.layout.simple_list_item_2, new String[]{s_titleStr, s_subtitleStr}, new int[]{android.R.id.text1, android.R.id.text2});
        table.setAdapter(m_adapter);

        table.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                updateButtonEnabling();
            }
        });

        m_fromEditText = (EditText)findViewById(R.id.directions_from);
        m_toEditText = (EditText)findViewById(R.id.directions_to);

        final Intent intent = getIntent();

        final Manager.Location locStart = intent.getParcelableExtra(MyAppUtils.s_extra_start);
        if (locStart == null) {
            final String curLocStr = getString(R.string.current_location);
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

        updateButtonEnabling();

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

    private void updateButtonEnabling()
    {
        final Button b = (Button)findViewById(R.id.directions_route);
        b.setEnabled(m_selectedStart != null && m_selectedEnd != null);
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
}
