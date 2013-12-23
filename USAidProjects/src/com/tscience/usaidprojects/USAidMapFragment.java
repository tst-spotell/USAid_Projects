/**
 * Copyright (c) 2013 Thermopylae Sciences and Technology. All rights reserved.
 */
package com.tscience.usaidprojects;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tscience.usaidprojects.utils.USAidProjectsCountryObject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * @author spotell at t-sciences.com
 *
 */
@SuppressLint("NewApi")
public class USAidMapFragment extends SupportMapFragment {  // SherlockFragment
    
    /** Log id of this class name. */
    private static final String LOG_TAG = "USAidMainFragment";
    
    /**
     * Listener interface to tell when the map is ready
     */
    public static interface OnMapReadyListener {
        void onMapReady();
    }
    
//    private MapView mMapView;
    
    public USAidMapFragment() {
    	super();
    }
    
    public static USAidMapFragment newInstance() {
    	
    	USAidMapFragment fragment = new USAidMapFragment();
    	return fragment;
    	
    }
   
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    	View v = super.onCreateView(inflater, container, savedInstanceState);
    	
    	Fragment fragment = getParentFragment();
        if (fragment != null && fragment instanceof OnMapReadyListener) {
            ((OnMapReadyListener) fragment).onMapReady();
            Log.d(LOG_TAG, "----------------------------------------onCreateView onMapReady");
        }
        return v;
    	
    	
//        View rootView = inflater.inflate(R.layout.usaid_map_layout, container, false);
//        
//        mMapView = (MapView) rootView.findViewById(R.id.usaid_map);
//        
//        // inflat and return the layout
//        mMapView.onCreate(savedInstanceState);
//        mMapView.onResume();// needed to get the map to display immediately
//        
//        try {
//            MapsInitializer.initialize(getActivity());
//        } catch (GooglePlayServicesNotAvailableException e) {
//            e.printStackTrace();
//        }
//        GoogleMap googleMap = mMapView.getMap();
//        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
//        
//        return rootView;
    }
    
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setRetainInstance(true);
//    }

//    /*
//     * Using a mapview in a fragment requires you to 'route'
//     * the lifecycle events of the fragment to the mapview
//     */
//    @Override
//    public void onResume() {
//        super.onResume();
//        if (null != mMapView)
//            mMapView.onResume();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        if (null != mMapView)
//            mMapView.onPause();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (null != mMapView)
//            mMapView.onDestroy();
//    }
//
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        if (null != mMapView)
//            mMapView.onSaveInstanceState(outState);
//    }
//
//    @Override
//    public void onLowMemory() {
//        super.onLowMemory();
//        if (null != mMapView)
//            mMapView.onLowMemory();
//    }
    
    public void updateData() {
        
        Log.d(LOG_TAG, "---------------------------------------- updateData: ");
        
        // make sure we have query results
        if (USAidMainActivity.countryQueryResults != null) {
            
            int numResults = USAidMainActivity.countryQueryResults.size();
            
            GoogleMap mMap = this.getMap();
            
            USAidProjectsCountryObject data = null;
            
            // cycle through the results and make the pins
            for (int i = 0; i < numResults; i++) {
                
                data = USAidMainActivity.countryQueryResults.get(i);
                
                mMap.addMarker(new MarkerOptions()
                    .position(USAidMainActivity.usaidCenterHashMap.get(data.countryID).center)
                    .title(data.countryID));
                
            }
            
        }
        
    } // end updateData

} // end USAidMapFragment
