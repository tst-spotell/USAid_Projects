/**
 * Copyright (c) 2013 Thermopylae Sciences and Technology. All rights reserved.
 */
package com.tscience.usaidprojects;

import com.actionbarsherlock.app.SherlockFragment;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * @author spotell at t-sciences.com
 *
 */
@SuppressLint("NewApi")
public class USAidMapFragment extends SherlockFragment {
    
    /** Log id of this class name. */
    private static final String LOG_TAG = "USAidMainFragment";
    
    private MapView mMapView;
    
   
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
        View rootView = inflater.inflate(R.layout.usaid_map_layout, container, false);
        
        mMapView = (MapView) rootView.findViewById(R.id.usaid_map);
        
        // inflat and return the layout
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();// needed to get the map to display immediately
        
        try {
            MapsInitializer.initialize(getActivity());
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
        GoogleMap googleMap = mMapView.getMap();
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        
        return rootView;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRetainInstance(true);
    }

    /*
     * Using a mapview in a fragment requires you to 'route'
     * the lifecycle events of the fragment to the mapview
     */
    @Override
    public void onResume() {
        super.onResume();
        if (null != mMapView)
            mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (null != mMapView)
            mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mMapView)
            mMapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (null != mMapView)
            mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (null != mMapView)
            mMapView.onLowMemory();
    }
    
    public void updateData() {
        
        Log.d(LOG_TAG, "---------------------------------------- updateData: ");
        
    }

} // end USAidMapFragment
