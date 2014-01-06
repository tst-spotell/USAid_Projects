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
import com.tscience.usaidprojects.io.USAidProjectsSnapshotTask;
import com.tscience.usaidprojects.utils.USAidProjectsSnapshotObject;
import com.tscience.usaidprojects.utils.USAidProjectsUtility;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
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
    
    // the adapter for the expandable list view
    ExpandableListAdapter listAdapter;
    
    /** The expandable list view we are using to display filter choices. */
    ExpandableListView expListView;
    
    /** This is the list of headers. */
    public static ArrayList<USAidProjectsSnapshotObject> listDataHeader;
    
    /** Contains the child drop down list items. */
    public static HashMap<String, ArrayList<USAidProjectsSnapshotObject>> listDataChild;
    
    ListView sectorList;
    
    /** List of the sector headings. */
    public static ArrayList<USAidProjectsSnapshotObject> sectorDataHeader;
    
    public static USAidFilterFragment usaidFilterFragment;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Log.d(LOG_TAG, "---------------------------------------------- onCreate");
        
        setHasOptionsMenu(true);
        
        usaidFilterFragment = this;
        
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
            
            // TODO usaidCenterHashMap
            
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
        
        // TODO usaidCenterHashMap
        
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
        
        sectorList = (ListView) rootView.findViewById(R.id.sector_list);
        
        // location
        Button locationButton = (Button) rootView.findViewById(R.id.usaid_filter_buttons_locations);
        
        locationButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                
                displayFilterList(USAidConstants.USAID_BUTTON_LOCATION);
                
            }
            
        });
        
        // sector
        Button sectorButton = (Button) rootView.findViewById(R.id.usaid_filter_buttons_sectors);
        
        sectorButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                
                displayFilterList(USAidConstants.USAID_BUTTON_SECTOR);
                
            }
            
        });
        
        // initiatives
        Button initiativeButton = (Button) rootView.findViewById(R.id.usaid_filter_buttons_initiatives);
        
        initiativeButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                
                displayFilterList(USAidConstants.USAID_BUTTON_INITIATIVE);
                
            }
            
        });
        
        // time
        Button timeButton = (Button) rootView.findViewById(R.id.usaid_filter_buttons_dates);
        
        timeButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                
                displayFilterList(USAidConstants.USAID_BUTTON_TIME);
                
            }
            
        });
        
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
        
        sectorList.setAdapter(new USAidSectorListAdapter(getActivity(), R.layout.usaid_sector_item, sectorDataHeader));
        
        sectorList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        
        sectorList.invalidate();
        
    } // end prepareListData
    
    /**
     * Used to load the initial data and reload the data.
     */
    private void loadTheData() {
        
        // start getting the data
        USAidProjectsSnapshotTask usaidProjectsSnapshotTask = new USAidProjectsSnapshotTask(this);
        usaidProjectsSnapshotTask.execute(USAidProjectsUtility.getUrlSnapshot(this.getActivity(), null));
        
    }
    
    /**
     * Creates a filter query based on what was selected.
     * 
     * @return  The query string;
     */
    public static String makeFilterQuery() {
        
        StringBuffer result = new StringBuffer();
        result.append(usaidFilterFragment.getString(R.string.usaid_server_url));
        result.append(usaidFilterFragment.getString(R.string.usaid_server_overview));
        result.append(usaidFilterFragment.getString(R.string.usaid_server_flag));
        result.append(usaidFilterFragment.getString(R.string.usaid_server_country_start));
        
        // get the countries
        if ((listDataHeader != null) && (listDataChild != null)) {
            
            int numRegions = listDataHeader.size();
            
            boolean hasLocations = false;
            
            try {
                
                boolean firstTime = true;
            
                for (int i = 0; i < numRegions; i++) {
                    
                    ArrayList<USAidProjectsSnapshotObject> temp = listDataChild.get(listDataHeader.get(i).name);
                    
                    int tempArraySize = temp.size();
                    
                    for (int j = 0; j < tempArraySize; j++) {
                        
                        if (temp.get(j).selected) {
                            
                            hasLocations = true;
                            
                            if (!firstTime) {
                                
                                // add the spacer between items
                                result.append(usaidFilterFragment.getString(R.string.usaid_server_spacer));
                                
                            } else {
                                
                                firstTime = false;
                                
                            }
                            
                            // if checked add the country name
                            result.append(USAidProjectsUtility.convertName(temp.get(j).name));
                            
                        }
                        
                    } // end for loop j
                    
                } // end for loop i
            
            }
            catch (Exception ignore) {
                Log.e(LOG_TAG, "---------------------------------------------- saveinstance listDataChild");
                Log.e(LOG_TAG, "---------------------------------------------- " + ignore.toString());
            }
        
        }
        
        // TODO add the sectors into the query string
        
        // TODO add the initiatives into the query string
        
        // TODO add the time into the query string
        
        
        Log.e(LOG_TAG, "---------------------------------------------- makeFilterQuery complete");
        
        return result.toString();
        
    }
    
    /**
     * Displays the views based on buttons selected.
     * 
     * @param value The id of the button selected.
     */
    private void displayFilterList(int value) {
        
        switch (value) {
            
            case USAidConstants.USAID_BUTTON_LOCATION: {
                
                expListView.setVisibility(View.VISIBLE);
                sectorList.setVisibility(View.GONE);
                
                break;
            }
            
            case USAidConstants.USAID_BUTTON_SECTOR: {
                
                sectorList.setVisibility(View.VISIBLE);
                expListView.setVisibility(View.GONE);
                
                break;
            }
            
            case USAidConstants.USAID_BUTTON_INITIATIVE: {
                
                break;
            }
            
            case USAidConstants.USAID_BUTTON_TIME: {
                
                break;
            }
            
        }
        
    } // end displayFilterList
    
    /**
     * Tis is the adapter for displaying the sector data.
     * 
     * @author spotell at t-sciences.com
     */
    private class USAidSectorListAdapter extends ArrayAdapter<USAidProjectsSnapshotObject> {

        private ArrayList<USAidProjectsSnapshotObject> items;
        
        private LayoutInflater inflater;
        
        /**
         * @param context
         * @param resource
         */
        public USAidSectorListAdapter(Context context, int resource, ArrayList<USAidProjectsSnapshotObject> value) {
            super(context, resource, value);
            
            items = value;
            
            inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            
            View currentView = convertView;
            
            // if the view has not been created create it
            if (currentView == null) {
                
                currentView = inflater.inflate(R.layout.usaid_sector_item, null);
                
                // create the tag we are using
                currentView.setTag(new USAidSectorHolder());
                
            }
            
            USAidSectorHolder usaidSectorHolder = (USAidSectorHolder) currentView.getTag();
            
            // load the holder if empty
            if (usaidSectorHolder.sectorNameView == null) {
                
//                usaidSectorHolder.typeImageView = (ImageView) currentView.findViewById(R.id.usaid_country_flag);
                usaidSectorHolder.sectorNameView = (TextView) currentView.findViewById(R.id.sectorNameItem);
                usaidSectorHolder.sectorCheckBox = (CheckBox) currentView.findViewById(R.id.sectorCheckbox);
                
            }
            
            // the usaid DataObject object we are working with
            usaidSectorHolder.usaidDataObject = items.get(position);
            
            // TODO set the image
            
            // set the name
            usaidSectorHolder.sectorNameView.setText(usaidSectorHolder.usaidDataObject.label);
            
            usaidSectorHolder.sectorCheckBox.setSelected(usaidSectorHolder.usaidDataObject.selected);
            
            return currentView;
            
        }
        
    } // end USAidSectorListAdapter
    
    static class USAidSectorHolder {
        
        ImageView typeImageView;
        TextView sectorNameView;
        CheckBox sectorCheckBox;
        
        USAidProjectsSnapshotObject usaidDataObject;
        
    }

} // end USAidFilterFragment
