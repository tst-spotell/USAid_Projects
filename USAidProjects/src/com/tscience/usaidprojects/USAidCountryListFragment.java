/**
 * Copyright (c) 2013 Thermopylae Sciences and Technology. All rights reserved.
 */
package com.tscience.usaidprojects;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockListFragment;
import com.tscience.usaidprojects.io.USAidProjectsOverviewTask;

/**
 * This is the fragment for displaying country search results.
 * 
 * @author spotell at t-sciences.com
 */
public class USAidCountryListFragment extends SherlockListFragment {
    
    /** Log id of this class name. */
    private static final String LOG_TAG = "USAidCountryListFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
        // layout the view
        View listView = inflater.inflate(R.layout.usaid_country_layout, container, false);
        
        // TODO load data from country list
        
        return listView;
        
    } // end onCreateView

    @Override
    public void onResume() {
        super.onResume();
        
        if (USAidMainActivity.countryQueryResults == null) {
            
            // get the count
            USAidProjectsOverviewTask usaidProjectsOverviewTask = new USAidProjectsOverviewTask(getActivity(), null);
            usaidProjectsOverviewTask.execute(USAidMainActivity.countryQuery);
            
        } else {
            
            Log.d(LOG_TAG, "-------------------------------- USAidMainActivity.countryQueryResults is null");
            
        }
        
    }
    
    

} // end USAidCountryListFragment
