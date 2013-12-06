/**
 * Copyright (c) 2013 Thermopylae Sciences and Technology. All rights reserved.
 */
package com.tscience.usaidprojects;

import java.util.ArrayList;
import java.util.HashMap;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.tscience.usaidprojects.io.USAidProjectsCountryTask;
import com.tscience.usaidprojects.io.USAidProjectsSnapshotTask;
import com.tscience.usaidprojects.utils.USAidProjectsSnapshotObject;
import com.tscience.usaidprojects.utils.USAidProjectsUtility;

import android.os.Bundle;
import android.util.Log;
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
public class USAidFilterFragment extends SherlockFragment {
	
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Log.d(LOG_TAG, "---------------------------------------------- onCreate");
        
        setHasOptionsMenu(true);
        
        // get the data if available
        if (savedInstanceState != null) {
            Log.d(LOG_TAG, "---------------------------------------------- savedInstanceState not null");
            
            try {
                listDataHeader = savedInstanceState.getParcelableArrayList(USAidConstants.USAID_BUNDLE_HEADER_DATA);
            }
            catch (Exception ignore) {
                Log.e(LOG_TAG, "---------------------------------------------- no listDataHeader");
                Log.e(LOG_TAG, "---------------------------------------------- " + ignore.toString());
            }
            
            // get the country arrays
            if (listDataHeader != null) {
                
                int numRegions = listDataHeader.size();
                
                try {
                    
                    if (listDataChild == null) {
                        listDataChild = new HashMap<String, ArrayList<USAidProjectsSnapshotObject>>();
                    }
                
                    for (int i = 0; i < numRegions; i++) {
                        
                        ArrayList<USAidProjectsSnapshotObject> temp = savedInstanceState.getParcelableArrayList(listDataHeader.get(i).name);
                        
                        listDataChild.put(listDataHeader.get(i).name, temp);
                        
                    }
                
                }
                catch (Exception ignore) {
                    Log.e(LOG_TAG, "---------------------------------------------- saveinstance listDataChild");
                    Log.e(LOG_TAG, "---------------------------------------------- " + ignore.toString());
                }
            
            }
            
            try {
                sectorDataHeader = savedInstanceState.getParcelableArrayList(USAidConstants.USAID_BUNDLE_SECTOR_DATA);
            }
            catch (Exception ignore) {
                Log.e(LOG_TAG, "---------------------------------------------- no sectorDataHeader");
                Log.e(LOG_TAG, "---------------------------------------------- " + ignore.toString());
            }
            
        }
        
    } // end onCreate

    @Override
    public void onSaveInstanceState(Bundle outState) {
        
        Log.d(LOG_TAG, "---------------------------------------------- onSaveInstanceState");
        
        // save the data
        if (listDataHeader != null) {
            try {
                outState.putParcelableArrayList(USAidConstants.USAID_BUNDLE_HEADER_DATA, listDataHeader);
            }
            catch (Exception ignore) {
                Log.e(LOG_TAG, "---------------------------------------------- saveinstance listDataHeader");
                Log.e(LOG_TAG, "---------------------------------------------- " + ignore.toString());
            }
        }
        
        // save the country arrays
        if (listDataChild != null) {
            
            int numRegions = listDataHeader.size();
            
            try {
            
                for (int i = 0; i < numRegions; i++) {
                    outState.putParcelableArrayList(listDataHeader.get(i).name, listDataChild.get(listDataHeader.get(i).name));
                }
            
            }
            catch (Exception ignore) {
                Log.e(LOG_TAG, "---------------------------------------------- saveinstance listDataChild");
                Log.e(LOG_TAG, "---------------------------------------------- " + ignore.toString());
            }
        
        }
        
        
        if (sectorDataHeader != null) {
            try {
                outState.putParcelableArrayList(USAidConstants.USAID_BUNDLE_SECTOR_DATA, sectorDataHeader);
            }
            catch (Exception ignore) {
                Log.e(LOG_TAG, "---------------------------------------------- saveinstance sectorDataHeader");
                Log.e(LOG_TAG, "---------------------------------------------- " + ignore.toString());
            }
        }
        
        super.onSaveInstanceState(outState);
    }
    
    @Override
    public void onResume() {
        super.onResume();
        
        if (listDataHeader == null) {
            
            loadTheData();
            
        } else {
            // load the list
            prepareListData(new ArrayList<USAidProjectsSnapshotObject>(), listDataHeader, listDataChild, sectorDataHeader, false);
        }
        
    }
    
    
    
    @Override
    public void onPause() {
        super.onPause();
        
        USAidMainActivity.countryQuery = makeFilterQuery();
        USAidMainActivity.countryQueryResults = null;
        
        Log.d(LOG_TAG, "---------------------------------------------- countryQuery: " + USAidMainActivity.countryQuery);
        
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        
        inflater.inflate(R.menu.usaid_main, menu);
        
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        
        int currentItemId = item.getItemId();
        
        if (currentItemId == R.id.action_filter_reset) {
            
            // TODO
            
            return true;
            
        }  else if (currentItemId == R.id.action_filter_reload) {
            
            loadTheData();
            
            return true;
            
        }  else if (currentItemId == R.id.action_about) {
            
            // TODO
            
            return true;
            
        }
        
        return false;
        
    } // end onOptionsItemSelected

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
        View rootView = inflater.inflate(R.layout.usaid_filter_layout, container, false);
        
        // get the listview
        expListView = (ExpandableListView) rootView.findViewById(R.id.usaid_filter_locations);
        
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
    
    /**
     * Used to load the initial data and reload the data.
     */
    private void loadTheData() {
        
        // start getting the data
        USAidProjectsSnapshotTask usaidProjectsSnapshotTask = new USAidProjectsSnapshotTask(this);
        usaidProjectsSnapshotTask.execute(USAidProjectsUtility.getUrlSnapshot(this.getActivity(), null));
        
//        // get the count
//        USAidProjectsCountryTask usaidProjectsOverviewTask = new USAidProjectsCountryTask(getActivity(), getString(R.string.usaid_json_overview_cache_file));
//        usaidProjectsOverviewTask.execute(USAidProjectsUtility.getUrlOverview(this.getActivity(), null));
        
    }
    
    private String makeFilterQuery() {
        
        StringBuffer result = new StringBuffer();
        result.append(getString(R.string.usaid_server_url));
        result.append(getString(R.string.usaid_server_overview));
        result.append(getString(R.string.usaid_server_flag));
        result.append(getString(R.string.usaid_server_country_start));
        
        // get the countries
        if ((listDataHeader != null) && (listDataChild != null)) {
            
            int numRegions = listDataHeader.size();
            
            try {
                
                boolean firstTime = true;
            
                for (int i = 0; i < numRegions; i++) {
                    
                    ArrayList<USAidProjectsSnapshotObject> temp = listDataChild.get(listDataHeader.get(i).name);
                    
                    int tempArraySize = temp.size();
                    
                    for (int j = 0; j < tempArraySize; j++) {
                        
                        if (temp.get(j).selected) {
                            
                            if (!firstTime) {
                                
                                // add the spacer between items
                                result.append(getString(R.string.usaid_server_spacer));
                                
                            } else {
                                
                                firstTime = false;
                                
                            }
                            
                            // if checked add the country name
                            result.append(USAidProjectsUtility.convertName(temp.get(j).name));
                            
                        }
                        
                    }
                    
                    
                }
            
            }
            catch (Exception ignore) {
                Log.e(LOG_TAG, "---------------------------------------------- saveinstance listDataChild");
                Log.e(LOG_TAG, "---------------------------------------------- " + ignore.toString());
            }
        
        }
        
        return result.toString();
        
    }

} // end USAidFilterFragment
