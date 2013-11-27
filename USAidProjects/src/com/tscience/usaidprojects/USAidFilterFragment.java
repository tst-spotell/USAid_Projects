/**
 * Copyright (c) 2013 Thermopylae Sciences and Technology. All rights reserved.
 */
package com.tscience.usaidprojects;

import java.util.ArrayList;
import java.util.HashMap;

import com.tscience.usaidprojects.io.USAidProjectsOverviewTask;
import com.tscience.usaidprojects.io.USAidProjectsSnapshotTask;
import com.tscience.usaidprojects.utils.USAidProjectsSnapshotObject;
import com.tscience.usaidprojects.utils.USAidProjectsUtility;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;
import android.widget.ListView;


/**
 * This is the fragment for home (filtering).
 * 
 * @author spotell at t-sciences.com
 */
public class USAidFilterFragment extends Fragment {
	
	/** Log id of this class name. */
    private static final String LOG_TAG = "USAidFilterFragment";
    
    ExpandableListAdapter listAdapter;
    
    /** the expandable list view we are using to display filter choices. */
    ExpandableListView expListView;
    
    /** This is the list of headers. */
    public static ArrayList<USAidProjectsSnapshotObject> listDataHeader;
    
    /** Contains the child drop down list items. */
    public static HashMap<String, ArrayList<USAidProjectsSnapshotObject>> listDataChild;
    
    /** List of the sector headings. */
    public static ArrayList<USAidProjectsSnapshotObject> sectorDataHeader;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
        View rootView = inflater.inflate(R.layout.usaid_filter_layout, container, false);
        
        // get the listview
        expListView = (ExpandableListView) rootView.findViewById(R.id.usaid_filter_locations);
        
        // start getting the data
        USAidProjectsSnapshotTask usaidProjectsSnapshotTask = new USAidProjectsSnapshotTask(this);
        usaidProjectsSnapshotTask.execute(USAidProjectsUtility.getUrlSnapshot(this.getActivity(), null));
        
        // get the count
        USAidProjectsOverviewTask usaidProjectsOverviewTask = new USAidProjectsOverviewTask(this);
        usaidProjectsOverviewTask.execute(USAidProjectsUtility.getUrlOverview(this.getActivity(), null));
        
        return rootView;
    }
    
    /**
     * Called when there is no network or cached data to display.
     */
    public void noCachedData() {
        
        Toast.makeText(getActivity(), R.string.usaid_cache_nodata, Toast.LENGTH_LONG).show();
        
    }
    
    /*
     * Populate the list data with our regions and countries.
     * 
     * @param value			The array of USAidProjectsSnapshotObject's used to setup the filter display.
     * @param regions       The list of region data.
     * @param countryMap    The hashmap of countries tied to a region.
     * @param sectors       The list of sector data.
     * @param cachedData    False this is live data or True this is cached data.
     */
    public void prepareListData(ArrayList<USAidProjectsSnapshotObject> value, ArrayList<USAidProjectsSnapshotObject> regions, HashMap<String,
            ArrayList<USAidProjectsSnapshotObject>> countryMap, ArrayList<USAidProjectsSnapshotObject> sectors, boolean cachedData) {
        
        // there was nothing to display
        if ((value == null) && !cachedData) {
            noCachedData();
            return;
        }
        
        listDataHeader = regions;
        listDataChild = countryMap;
 
        // create the adapter with the data
        listAdapter = new USAidExpandableListAdapter(getActivity());
        
        // setting list adapter
        expListView.setAdapter(listAdapter);
        
        expListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        
        // redraw with the new data
        expListView.invalidate();
        
        sectorDataHeader = sectors;
        
    } // end prepareListData

} // end USAidFilterFragment
