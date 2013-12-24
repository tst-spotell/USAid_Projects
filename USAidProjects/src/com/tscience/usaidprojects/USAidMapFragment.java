/**
 * Copyright (c) 2013 Thermopylae Sciences and Technology. All rights reserved.
 */
package com.tscience.usaidprojects;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
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

    }
    
    public void updateData() {
        
        Log.d(LOG_TAG, "---------------------------------------- updateData: ");
        
        // make sure we have query results
        if (USAidMainActivity.countryQueryResults != null) {
            
            int numResults = USAidMainActivity.countryQueryResults.size();
            
            GoogleMap mMap = this.getMap();
            
            // clear any old data
            mMap.clear();
            
            mMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

                @SuppressWarnings("static-access")
                @Override
                public void onInfoWindowClick(Marker marker) {
                    
                    // run the country list
                    
                    // Create new fragment and transaction
                    USAidCountryProjectsListFragment newFragment = new USAidCountryProjectsListFragment();
                    newFragment.countryName = marker.getTitle();
                    
                    USAidMainActivity.mapUSAidWrapperFragment.
                    
                    getChildFragmentManager().beginTransaction()
                    
                    // Replace whatever is in the fragment_container view with this fragment,
                    // and add the transaction to the back stack
                    .replace(R.id.fragment_container, newFragment, USAidConstants.USAID_FRAGMENT_NAME_PROJECTS)
                    .addToBackStack(null)
                
                    // Commit the transaction
                    .commit();
                    
                }
                
            });
            
            USAidProjectsCountryObject data = null;
            
            // cycle through the results and make the pins
            for (int i = 0; i < numResults; i++) {
                
                data = USAidMainActivity.countryQueryResults.get(i);
                
                mMap.addMarker(new MarkerOptions()
                    .position(USAidMainActivity.usaidCenterHashMap.get(data.countryID).center)
                    .title(data.countryID)
                    .snippet("Projects: " + data.totalProjects));
                
            }
            
        }
        
    } // end updateData

} // end USAidMapFragment
