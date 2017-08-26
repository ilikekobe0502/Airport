package com.whatmedia.ttia.utility;


import android.app.Application;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDexApplication;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.point_consulting.pc_indoormapoverlaylib.BuildConfig;
import com.point_consulting.pc_indoormapoverlaylib.Coordinate3D;
import com.point_consulting.pc_indoormapoverlaylib.Manager;
import com.point_consulting.pc_indoormapoverlaylib.Utils;
import com.whatmedia.ttia.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xmlwise.Plist;

public final class MyApplication extends Application implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, LocationListener
{
    public interface GoogleLocationCallback
    {
        void onLocation();
    }

    private static final String s_currentVenueNameKey = "s_currentVenueNameKey";

    private boolean m_needOutdoorSearch = false;
    private boolean m_showUserLocation = false;
    public Manager m_manager;
    private GoogleApiClient m_googleApiClient;
    private LocationRequest m_locationRequest;
    public LatLngBounds m_bounds;
    public GoogleLocationCallback m_googleLocationCallback;
    private boolean m_lastLocationInited;
    private double m_lastLocationLatitude;
    private double m_lastLocationLongitude;
    private float m_lastLocationBearing;
    private Object m_sync = new Object();
    private boolean m_connected;
    private boolean m_requestedLocationUpdates = !m_showUserLocation;
    private Map<String, String> m_levelId2Name;
    private Map<Integer, String> m_levelOrdinal2ShortName;
    private List<Map<String, Object> > m_venuesList;
    private int m_currentVenueIndex;

    @Override
    public void onCreate()
    {
        super.onCreate();
        if (m_needOutdoorSearch || m_showUserLocation) {
            m_googleApiClient = new GoogleApiClient.Builder(this).addApi(Places.GEO_DATA_API).addApi(LocationServices.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
            m_googleApiClient.connect();
        }
        if (m_showUserLocation) {
            m_locationRequest = LocationRequest.create().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(10000).setFastestInterval(5000);
        }

        InputStream inputStream = getResources().openRawResource(R.raw.venues);
        try
        {
            int size = inputStream.available();
            byte[] bytes = new byte[size];
            inputStream.read(bytes);
            inputStream.close();

            final String string = new String(bytes, "UTF-8");
            final Map<String, Object> map = Plist.fromXml(string);
            m_venuesList = (List<Map<String, Object> >)map.get("venues");
        }
        catch (IOException ex)
        {
            Utils.Log("Failed to read venues.plist: " + ex);
        }
        catch (xmlwise.XmlParseException ex)
        {
            Utils.Log("Failed to parse venues.plist: " + ex);
        }

        if (m_venuesList != null) {
            final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
            String currentVenue = pref.getString(s_currentVenueNameKey, "");
            int venueIndex = 0;
            for (Map<String, Object> d : m_venuesList)
            {
                final String name = (String)d.get("name");
                if (currentVenue.equals(name))
                {
                    break;
                }
                ++venueIndex;
            }
            if (venueIndex < m_venuesList.size()) {
                m_currentVenueIndex = venueIndex;
            }
        }
    }

    public GoogleApiClient getGoogleApiClient()
    {
        return m_googleApiClient;
    }

    public static class OutdoorFeature
    {
        OutdoorFeature(String title, String subtitle, String placeId)
        {
            m_title = title;
            m_subtitle = subtitle;
            m_placeId = placeId;
        }
        public String m_title;
        public String m_subtitle;
        public String m_placeId;
    }

    public interface SearchCallback
    {
        void onOutdoorSearchDone(List<OutdoorFeature> features);
    }

    private List<OutdoorFeature> getFeatures(AutocompletePredictionBuffer autocompletePredictions)
    {
        final List<OutdoorFeature> features = new ArrayList<>();
        for (AutocompletePrediction pr : autocompletePredictions)
        {
            final String title = pr.getPrimaryText(null).toString();
            final String subtitle = pr.getSecondaryText(null).toString();
            final String placeId = pr.getPlaceId();
            features.add(new OutdoorFeature(title, subtitle, placeId));
        }
        return features;
    }

    List<OutdoorFeature> searchOutdoorSync(final String s)
    {
        if (m_needOutdoorSearch && m_googleApiClient != null)
        {
            PendingResult<AutocompletePredictionBuffer> pres = Places.GeoDataApi.getAutocompletePredictions(m_googleApiClient, s, m_bounds, null);
            AutocompletePredictionBuffer autocompletePredictions = pres.await();
            List<OutdoorFeature> features = getFeatures(autocompletePredictions);
            autocompletePredictions.release();
            return features;
        }
        return null;
    }

    public boolean searchOutdoorAsync(final String s, final SearchCallback callback)
    {
        if ( m_needOutdoorSearch && m_googleApiClient != null)
        {
            PendingResult<AutocompletePredictionBuffer> pres = Places.GeoDataApi.getAutocompletePredictions(m_googleApiClient, s, m_bounds, null);
            pres.setResultCallback(new ResultCallback<AutocompletePredictionBuffer>() {
                @Override
                public void onResult(@NonNull AutocompletePredictionBuffer autocompletePredictions) {
                    final List<OutdoorFeature> features = getFeatures(autocompletePredictions);
                    autocompletePredictions.release();
                    callback.onOutdoorSearchDone(features);
                }
            });
            return true;
        }
        return false;
    }

    private void tryRequestLocationUpdates()
    {
        if (!m_requestedLocationUpdates && m_connected) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                return;
            }
            try {
                android.location.Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(m_googleApiClient);
                synchronized (m_sync)
                {
                    m_lastLocationInited = true;
                    m_lastLocationLatitude = lastLocation.getLatitude();
                    m_lastLocationLongitude = lastLocation.getLongitude();
                    m_lastLocationBearing = lastLocation.getBearing();
                }
                if (m_googleLocationCallback != null) {
                    m_googleLocationCallback.onLocation();
                }
                LocationServices.FusedLocationApi.requestLocationUpdates(m_googleApiClient, m_locationRequest, this);
                m_requestedLocationUpdates = true;
            } catch (SecurityException ex) {
                Utils.Log("LocationServices.FusedLocationApi.requestLocationUpdates exception: " + ex.toString());
            }
        }
    }

    public void setHaveAccessLocationPermission()
    {
        tryRequestLocationUpdates();
    }

    public void onConnected(Bundle bundle)
    {
        Utils.Log("google api client: onConnected");
        m_connected = true;
        tryRequestLocationUpdates();
    }

    public void onConnectionFailed(ConnectionResult result)
    {
        Utils.Log("google api client: onConnectionFailed");
    }

    public void onConnectionSuspended(int val)
    {
        Utils.Log("google api client: onConnectionSuspended");
    }

    @Override
    public void onLocationChanged(android.location.Location aLocation) {
        synchronized (m_sync)
        {
            m_lastLocationInited = true;
            m_lastLocationLatitude = aLocation.getLatitude();
            m_lastLocationLongitude = aLocation.getLongitude();
            m_lastLocationBearing = aLocation.getBearing();
        }
        if (m_googleLocationCallback != null) {
            m_googleLocationCallback.onLocation();
        }
    }

    public Manager.Location getUserLocation(float[] retBearing)
    {
        boolean lastLocationInited;
        double lastLocationLatitude;
        double lastLocationLongitude;
        float lastLocationBearing;
        synchronized (m_sync)
        {
            lastLocationInited = m_lastLocationInited;
            lastLocationLatitude = m_lastLocationLatitude;
            lastLocationLongitude = m_lastLocationLongitude;
            lastLocationBearing = m_lastLocationBearing;
        }
        if (!lastLocationInited)
        {
            return null;
        }
        if (retBearing != null) {
            retBearing[0] = lastLocationBearing;
        }
        final int ordinal = 0;
        return new Manager.Location(null, -1, new Coordinate3D(lastLocationLatitude, lastLocationLongitude, ordinal), null, getString(R.string.current_location));
    }

    public void dropLevelNames()
    {
        if (m_levelId2Name != null)
        {
            m_levelId2Name.clear();
        }
        else {
            m_levelId2Name = new HashMap<>();
        }
        if (m_levelOrdinal2ShortName != null)
        {
            m_levelOrdinal2ShortName.clear();
        }
        else {
            m_levelOrdinal2ShortName = new HashMap<>();
        }
    }

    public void setLevelName(String levelId, String levelName)
    {
        m_levelId2Name.put(levelId, levelName);
    }
    public String nameForLevelId(String levelId)
    {
        return m_levelId2Name.get(levelId);
    }
    public void setOrdinalShortName(int ordinal, String shortName)
    {
        m_levelOrdinal2ShortName.put(ordinal, shortName);
    }
    public String shortNameForOrdinal(int ordinal)
    {
        return m_levelOrdinal2ShortName.get(ordinal);
    }

    int getCurrentVenueIndex()
    {
        return m_currentVenueIndex;
    }

    void setCurrentVenueIndex(int index)
    {
        if (index != m_currentVenueIndex && index < m_venuesList.size())
        {
            m_currentVenueIndex = index;
            final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
            final SharedPreferences.Editor ed = pref.edit();
            ed.putString(s_currentVenueNameKey, getCurrentVenueName());
            ed.commit();
        }
    }

    List<String> getVenueNames()
    {
        List<String> ret = new ArrayList<>(m_venuesList.size());
        for (Map<String, Object> m : m_venuesList) {
            final String name = (String)m.get("name");
            ret.add(name);
        }
        return ret;
    }

    private Object getCurrent_(String key)
    {
        final Map<String, Object> map = m_venuesList.get(m_currentVenueIndex);
        return map.get(key);
    }

    public String getCurrentVenueId()
    {
        return (String)getCurrent_("id");
    }

    public String getCurrentVenueName()
    {
        return (String)getCurrent_("name");
    }

    public LatLng getCurrentVenueCenter()
    {
        Object lat = getCurrent_("latitude");
        Object lon = getCurrent_("longitude");
        final double latitude = (Double)lat;
        final double longitude = (Double)lon;
        return new LatLng(latitude, longitude);
    }
}
