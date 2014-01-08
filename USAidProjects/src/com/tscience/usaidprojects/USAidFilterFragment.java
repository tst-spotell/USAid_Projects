/**
 * Copyright (c) 2013 Thermopylae Sciences and Technology. All rights reserved.
 */
package com.tscience.usaidprojects;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.tscience.usaidprojects.io.USAidProjectsSnapshotTask;
import com.tscience.usaidprojects.utils.USAidProjectsLatLngCenterObject;
import com.tscience.usaidprojects.utils.USAidProjectsSnapshotObject;
import com.tscience.usaidprojects.utils.USAidProjectsUtility;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
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
    
    // the adapter for the expandable location list view
    ExpandableListAdapter listAdapter;
    
    /** The expandable list view we are using to display location filter choices. */
    ExpandableListView expListView;
    
    /** This is the list of headers. */
    public static ArrayList<USAidProjectsSnapshotObject> listDataHeader;
    
    /** Contains the child drop down list items. */
    public static HashMap<String, ArrayList<USAidProjectsSnapshotObject>> listDataChild;
    
    ArrayAdapter<USAidProjectsSnapshotObject> sectorAdapter;
    
    ListView sectorList;
    
    /** List of the sector headings. */
    public static ArrayList<USAidProjectsSnapshotObject> sectorDataHeader;
    
    // the adapter for the expandable initiative list view
    ExpandableListAdapter initiativeAdapter;
    
    /** The expandable list view we are using to display initiative filter choices. */
    ExpandableListView initiativeListView;
    
    /** This is the list of initiative headers. */
    public static ArrayList<USAidProjectsSnapshotObject> initiativeDataHeader;
    
    /** Contains the child drop down sub initiative list items. */
    public static HashMap<String, ArrayList<USAidProjectsSnapshotObject>> initiativeDataChild;
    
    RelativeLayout timeFilter;
    
    static long startingDate = 0;
    static long endingDate = 0;
    
    public static USAidFilterFragment usaidFilterFragment;
    
    @SuppressWarnings("unchecked")
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
            
            // initiatives
            try {
                initiativeDataHeader = savedInstanceState.getParcelableArrayList(USAidConstants.USAID_BUNDLE_INIT_DATA);
            }
            catch (Exception ignore) {
                Log.e(LOG_TAG, "---------------------------------------------- no initiativeDataHeader");
                Log.e(LOG_TAG, "---------------------------------------------- " + ignore.toString());
            }
            
            // get the sub initiative arrays
            if (initiativeDataHeader != null) {
                
                int numInit = 1;
                
                try {
                    
                    if (initiativeDataChild == null) {
                        initiativeDataChild = new HashMap<String, ArrayList<USAidProjectsSnapshotObject>>();
                    }
                
                    for (int i = 0; i < numInit; i++) {
                        
                        ArrayList<USAidProjectsSnapshotObject> temp = savedInstanceState.getParcelableArrayList(initiativeDataHeader.get(i).name);
                        
                        initiativeDataChild.put(initiativeDataHeader.get(i).name, temp);
                        
                    }
                
                }
                catch (Exception ignore) {
                    Log.e(LOG_TAG, "---------------------------------------------- saveinstance initiativeDataChild");
                    Log.e(LOG_TAG, "---------------------------------------------- " + ignore.toString());
                }
            
            }
            
            // time set data
            try {
                startingDate = savedInstanceState.getLong(USAidConstants.USAID_BUNDLE_START_DATA);
            }
            catch (Exception ignore) {
                startingDate = 0;
                Log.e(LOG_TAG, "---------------------------------------------- no startingDate");
                Log.e(LOG_TAG, "---------------------------------------------- " + ignore.toString());
            }
            
            try {
                endingDate = savedInstanceState.getLong(USAidConstants.USAID_BUNDLE_END_DATA);
            }
            catch (Exception ignore) {
                endingDate = 0;
                Log.e(LOG_TAG, "---------------------------------------------- no endingDate");
                Log.e(LOG_TAG, "---------------------------------------------- " + ignore.toString());
            }
            
            // usaidCenterHashMap
            try {
                USAidMainActivity.usaidCenterHashMap = (HashMap<String, USAidProjectsLatLngCenterObject>) savedInstanceState.getSerializable(USAidConstants.USAID_BUNDLE_CENTER_DATA);
            }
            catch (Exception ignore) {
                Log.e(LOG_TAG, "---------------------------------------------- no usaidCenterHashMap");
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
        
        // initiatives
        if (initiativeDataHeader != null) {
            try {
                outState.putParcelableArrayList(USAidConstants.USAID_BUNDLE_INIT_DATA, initiativeDataHeader);
            }
            catch (Exception ignore) {
                Log.e(LOG_TAG, "---------------------------------------------- saveinstance initiativeDataHeader");
                Log.e(LOG_TAG, "---------------------------------------------- " + ignore.toString());
            }
        }
        
        if (initiativeDataChild != null) {
            
            int numInitiatives = 1;
            
            try {
            
                for (int i = 0; i < numInitiatives; i++) {
                    outState.putParcelableArrayList(initiativeDataHeader.get(i).name, initiativeDataChild.get(initiativeDataHeader.get(i).name));
                }
            
            }
            catch (Exception ignore) {
                Log.e(LOG_TAG, "---------------------------------------------- saveinstance initiativeDataChild");
                Log.e(LOG_TAG, "---------------------------------------------- " + ignore.toString());
            }
        
        }
        
        // time set data
        try {
            outState.putLong(USAidConstants.USAID_BUNDLE_START_DATA, startingDate);
        }
        catch (Exception ignore) {
            Log.e(LOG_TAG, "---------------------------------------------- saveinstance startingDate");
            Log.e(LOG_TAG, "---------------------------------------------- " + ignore.toString());
        }
        
        try {
            outState.putLong(USAidConstants.USAID_BUNDLE_END_DATA, endingDate);
        }
        catch (Exception ignore) {
            Log.e(LOG_TAG, "---------------------------------------------- saveinstance endingDate");
            Log.e(LOG_TAG, "---------------------------------------------- " + ignore.toString());
        }
        
        // usaidCenterHashMap
        if (USAidMainActivity.usaidCenterHashMap != null) {
            
            try {
                outState.putSerializable(USAidConstants.USAID_BUNDLE_CENTER_DATA, USAidMainActivity.usaidCenterHashMap);
            }
            catch (Exception ignore) {
                Log.e(LOG_TAG, "---------------------------------------------- saveinstance usaidCenterHashMap");
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
            prepareListData(initiativeDataHeader, initiativeDataChild, listDataHeader, listDataChild, sectorDataHeader, false);
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
            
            // TODO reset data
            
            // reset time filter
            TextView startDate = (TextView) timeFilter.findViewById(R.id.usaid_start_date);
            startDate.setText(R.string.usaid_filter_today_label);
            
            TextView endDate = (TextView) timeFilter.findViewById(R.id.usaid_end_date);
            endDate.setText(R.string.usaid_filter_today_label);
            
            startingDate = 0;
            endingDate = 0;

            // reset query
            
            
            return true;
            
        }  else if (currentItemId == R.id.action_filter_reload) {
            
            loadTheData();
            
            return true;
            
        }  else if (currentItemId == R.id.action_about) {
            
            // create the view
            USAidAboutFragment usaidAboutFragment = new USAidAboutFragment();
            usaidAboutFragment.show(getActivity().getSupportFragmentManager(), "about");
            
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
        
        initiativeListView = (ExpandableListView) rootView.findViewById(R.id.initiative_list);
        
        timeFilter = (RelativeLayout) rootView.findViewById(R.id.time_select);
        
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
        
        // select start date
        Button startDateButton = (Button) rootView.findViewById(R.id.usaid_set_start_date_button);
        
        startDateButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                
                showDatePickerDialog(USAidConstants.USAID_SET_START_DATE);
                
            }
            
        });
        
        // select end date
        Button endDateButton = (Button) rootView.findViewById(R.id.usaid_set_end_date_button);
        
        endDateButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                
                showDatePickerDialog(USAidConstants.USAID_SET_END_DATE);
                
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
    public void prepareListData(ArrayList<USAidProjectsSnapshotObject> initiatives, HashMap<String,
            ArrayList<USAidProjectsSnapshotObject>> initiativeMap, ArrayList<USAidProjectsSnapshotObject> regions, HashMap<String,
            ArrayList<USAidProjectsSnapshotObject>> countryMap, ArrayList<USAidProjectsSnapshotObject> sectors, boolean cachedData) {
        
        Log.d(LOG_TAG, "---------------------------------------- prepareListData");
        
        // there was nothing to display
        if ((initiatives == null) && !cachedData) {
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
        
        for (int i = 0; i < sectorDataHeader.size(); i++) {
            
            Log.d(LOG_TAG, "---------------------------------------- sector i: " + sectorDataHeader.get(i).selected);
            
        }
        
        sectorAdapter = new USAidSectorListAdapter(getActivity(), R.layout.usaid_sector_item, sectorDataHeader);
        
        sectorList.setAdapter(sectorAdapter);
        
        sectorList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        
        sectorAdapter.notifyDataSetChanged();
        
        initiativeDataHeader = initiatives;
        initiativeDataChild =  initiativeMap;
        
        // create the adapter with the data
        initiativeAdapter = new USAidInitiativesAdapter(getActivity());
        
        // setting list adapter
        initiativeListView.setAdapter(initiativeAdapter);
        
        initiativeListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        
        // redraw with the new data
        initiativeListView.invalidate();
        
        // set the start and end time
        if (startingDate > 0) {
            setUSAidDateSelect(startingDate, USAidConstants.USAID_SET_START_DATE);
        }
        
        if (endingDate > 0) {
            setUSAidDateSelect(endingDate, USAidConstants.USAID_SET_END_DATE);
        }
        
    } // end prepareListData
    
    /**
     * This is the method called when the date picker returns.
     * 
     * @param year  The year selected.
     * @param month The month selected.
     * @param day   The day selected.
     * @param dateToSet Zero start date and 1 end date.
     */
    public void onUSAidDateSelected(int year, int month, int day, int dateToSet) {
        
        // save the time
        final Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        
        // display the time
        if (dateToSet == USAidConstants.USAID_SET_START_DATE) {
            
            TextView startDate = (TextView) timeFilter.findViewById(R.id.usaid_start_date);
            startDate.setText(USAidProjectsUtility.formatTheDate(year, month, day));
            
            startingDate = c.getTimeInMillis();
            
        } else if (dateToSet == USAidConstants.USAID_SET_END_DATE) {
            
            TextView endDate = (TextView) timeFilter.findViewById(R.id.usaid_end_date);
            endDate.setText(USAidProjectsUtility.formatTheDate(year, month, day));
            
            endingDate = c.getTimeInMillis();
            
        }
        
        // rebuild the query
        USAidMainActivity.countryQuery = makeFilterQuery();
        USAidMainActivity.countryQueryResults = null;
        
    } // end onUSAidDateSelected
    
    private void setUSAidDateSelect(long value, int dateToSet) {
        
        final Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        c.setTimeInMillis(value);
        
        // display the time
        if (dateToSet == USAidConstants.USAID_SET_START_DATE) {
            
            TextView startDate = (TextView) timeFilter.findViewById(R.id.usaid_start_date);
            startDate.setText(USAidProjectsUtility.formatTheDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)));
            
        } else if (dateToSet == USAidConstants.USAID_SET_END_DATE) {
            
            TextView endDate = (TextView) timeFilter.findViewById(R.id.usaid_end_date);
            endDate.setText(USAidProjectsUtility.formatTheDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)));
            
        }
        
    } // end setUSAidDateSelect
    
    /**
     * Used to load the initial data and reload the data.
     */
    private void loadTheData() {
        
        Log.d(LOG_TAG, "---------------------------------------------- loadTheData");
        
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
                                result.append(usaidFilterFragment.getString(R.string.usaid_server_spacer));
                                
                            } else {
                                
                                result.append(usaidFilterFragment.getString(R.string.usaid_server_country_start));
                                
                                firstTime = false;
                                
                            }
                            
                            // if checked add the country name
                            result.append(USAidProjectsUtility.convertName(temp.get(j).name));
                            
                        }
                        
                    } // end for loop j
                    
                } // end for loop i
            
            }
            catch (Exception ignore) {
                Log.e(LOG_TAG, "---------------------------------------------- make query listDataChild");
                Log.e(LOG_TAG, "---------------------------------------------- " + ignore.toString());
            }
        
        } // end listDataHeader
        
        // add the sectors into the query string
        if (sectorDataHeader != null) {
            
            try {
                
                boolean firstTime = true;
                
                int numberSectors = sectorDataHeader.size();
                
                for (int i = 0; i < numberSectors; i++) {
                    
                    if (sectorDataHeader.get(i).selected) {
                        
                        if (!firstTime) {
                            
                            // add the spacer between items
                            result.append(usaidFilterFragment.getString(R.string.usaid_server_spacer));
                            
                        } else {
                            
                            result.append(usaidFilterFragment.getString(R.string.usaid_server_sector_start));
                            
                            firstTime = false;
                            
                        }
                        
                        // if checked add the country name
                        result.append(USAidProjectsUtility.convertName(sectorDataHeader.get(i).name));
                        
                    }
                    
                }
                
            }
            catch (Exception ignore) {
                Log.e(LOG_TAG, "---------------------------------------------- make query sectorDataHeader");
                Log.e(LOG_TAG, "---------------------------------------------- " + ignore.toString());
            }
        
        } // end sectorDataHeader
        
        // TODO add the initiatives into the query string
        
        // add the time into the query string
        if (startingDate > 0) {
            
            // starting time
            result.append(usaidFilterFragment.getString(R.string.usaid_server_time_start));
            result.append(startingDate);
            
        } // end startingDate
        
        if (endingDate > 0) {
            
            // ending time
            result.append(usaidFilterFragment.getString(R.string.usaid_server_time_end));
            result.append(endingDate);
            
        } // end endingDate
        
        
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
                initiativeListView.setVisibility(View.GONE);
                timeFilter.setVisibility(View.GONE);
                
                break;
            }
            
            case USAidConstants.USAID_BUTTON_SECTOR: {
                
                sectorList.setVisibility(View.VISIBLE);
                expListView.setVisibility(View.GONE);
                initiativeListView.setVisibility(View.GONE);
                timeFilter.setVisibility(View.GONE);
                
                break;
            }
            
            case USAidConstants.USAID_BUTTON_INITIATIVE: {
                
                initiativeListView.setVisibility(View.VISIBLE);
                sectorList.setVisibility(View.GONE);
                expListView.setVisibility(View.GONE);
                timeFilter.setVisibility(View.GONE);
                
                break;
            }
            
            case USAidConstants.USAID_BUTTON_TIME: {
                
                timeFilter.setVisibility(View.VISIBLE);
                sectorList.setVisibility(View.GONE);
                initiativeListView.setVisibility(View.GONE);
                expListView.setVisibility(View.GONE);
                
                break;
            }
            
        }
        
    } // end displayFilterList
    
    /**
     * Called to show the date picker.
     * 
     * @param value Zero is start date and one is end date.
     */
    private void showDatePickerDialog(int value) {
        
        SherlockDialogFragment newFragment = new USAidDatePicketFragment();
        
        Bundle newBundle = new Bundle();
        newBundle.putInt(USAidConstants.USAID_SET_DATE, value);
        
        newFragment.setArguments(newBundle);
        
        newFragment.setTargetFragment(this, 1);
        
        newFragment.show(getSherlockActivity().getSupportFragmentManager(), "datePicker");
        
    } // end showDatePickerDialog

} // end USAidFilterFragment
