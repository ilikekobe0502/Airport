package com.whatmedia.ttia.utility;


import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
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
import com.google.android.gms.maps.model.LatLngBounds;
import com.point_consulting.pc_indoormapoverlaylib.BuildConfig;
import com.point_consulting.pc_indoormapoverlaylib.Coordinate3D;
import com.point_consulting.pc_indoormapoverlaylib.Manager;
import com.point_consulting.pc_indoormapoverlaylib.Utils;

import java.util.ArrayList;
import java.util.List;

public final class MyApplication extends MultiDexApplication implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, LocationListener
{
    public interface GoogleLocationCallback
    {
        void onLocation(android.location.Location aLocation);
    }

    private boolean m_needOutdoorSearch = false;
    private boolean m_showUserLocation = false;
    public Manager m_manager;
    private GoogleApiClient m_googleApiClient;
    private LocationRequest m_locationRequest;
    public LatLngBounds m_bounds;
    public GoogleLocationCallback m_googleLocationCallback;
    private android.location.Location m_lastLocation;
    private boolean m_connected;
    private boolean m_requestedLocationUpdates = !m_showUserLocation;

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
        String m_title;
        String m_subtitle;
        String m_placeId;
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

    boolean searchOutdoorAsync(final String s, final SearchCallback callback)
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
                m_lastLocation = LocationServices.FusedLocationApi.getLastLocation(m_googleApiClient);
                if (m_googleLocationCallback != null) {
                    m_googleLocationCallback.onLocation(m_lastLocation);
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
        m_lastLocation = aLocation;
        if (m_googleLocationCallback != null) {
            m_googleLocationCallback.onLocation(m_lastLocation);
        }
    }

    public Manager.Location getUserLocation(float[] retBearing)
    {
        if (m_lastLocation == null)
        {
            return null;
        }
        final double latitude = m_lastLocation.getLatitude();
        final double longitude = m_lastLocation.getLongitude();
        if (retBearing != null) {
            retBearing[0] = m_lastLocation.getBearing();
        }
        final int ordinal = 0;
        return new Manager.Location(null, -1, new Coordinate3D(latitude, longitude, ordinal), null, "Current location");
    }
}
