/**
 * Copyright (c) 2013 Thermopylae Sciences and Technology. All rights reserved.
 */
package com.tscience.usaidprojects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.tscience.usaidprojects.io.USAidProjectsOverviewTask;
import com.tscience.usaidprojects.io.USAidProjectsSnapshotTask;
import com.tscience.usaidprojects.utils.USAidProjectsUtility;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ListView;


/**
 * This is the fragment for home (filtering).
 * 
 * @author spotell at t-sciences.com
 */
public class USAidFilterFragment extends Fragment {
    
    ExpandableListAdapter listAdapter;
    
    /** the expandable list view we are using to display filter choices. */
    ExpandableListView expListView;
    
    /** This is the list of headers. */
    List<String> listDataHeader;
    
    /** Contains the child drop down list items. */
    HashMap<String, List<String>> listDataChild;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
        View rootView = inflater.inflate(R.layout.usaid_filter_layout, container, false);
        
        // preparing list data
        prepareListData();
        
        // get the listview
        expListView = (ExpandableListView) rootView.findViewById(R.id.usaid_filter_locations);
        
        // create the adapter with the data
        listAdapter = new USAidExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
        
        // setting list adapter
        expListView.setAdapter(listAdapter);
        
        expListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                
                CheckedTextView checkbox = (CheckedTextView)v.findViewById(R.id.lblListItem);
                checkbox.toggle();
                
                // TODO update group
                
                return true;
            }
            
        });
        
        // start getting the data
        USAidProjectsSnapshotTask usaidProjectsSnapshotTask = new USAidProjectsSnapshotTask(this);
        usaidProjectsSnapshotTask.execute(USAidProjectsUtility.getUrlSnapshot(this.getActivity(), null));
        
        // get the count
        USAidProjectsOverviewTask usaidProjectsOverviewTask = new USAidProjectsOverviewTask(this);
        usaidProjectsOverviewTask.execute(USAidProjectsUtility.getUrlSnapshot(this.getActivity(), null));
        
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
     */
    private void prepareListData() {
        
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
 
        // Adding region data
        Resources res = getActivity().getResources();
        listDataHeader= Arrays.asList(res.getStringArray(R.array.usaid_filter_regions));
        
        // Adding child data
        listDataChild.put(listDataHeader.get(USAidConstants.USAID_REGION_AFPAK), Arrays.asList(res.getStringArray(R.array.usaid_filter_region_afpak))); // Header, Child data
        listDataChild.put(listDataHeader.get(USAidConstants.USAID_REGION_ASIA), Arrays.asList(res.getStringArray(R.array.usaid_filter_region_asia)));
        listDataChild.put(listDataHeader.get(USAidConstants.USAID_REGION_EUROPE), Arrays.asList(res.getStringArray(R.array.usaid_filter_region_europe)));
        listDataChild.put(listDataHeader.get(USAidConstants.USAID_REGION_LATIN_AMERICA), Arrays.asList(res.getStringArray(R.array.usaid_filter_region_latin)));
        listDataChild.put(listDataHeader.get(USAidConstants.USAID_REGION_MIDDLE_EAST), Arrays.asList(res.getStringArray(R.array.usaid_filter_region_middle_east)));
        listDataChild.put(listDataHeader.get(USAidConstants.USAID_REGION_AFRICA), Arrays.asList(res.getStringArray(R.array.usaid_filter_region_africa)));
 
    } // end prepareListData

} // end USAidFilterFragment
