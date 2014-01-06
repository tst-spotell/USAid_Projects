/**
 * Copyright (c) 2013 Thermopylae Sciences and Technology. All rights reserved.
 */
package com.tscience.usaidprojects.io;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tscience.usaidprojects.R;
import com.tscience.usaidprojects.USAidConstants;
import com.tscience.usaidprojects.USAidFilterFragment;
import com.tscience.usaidprojects.USAidMainActivity;
import com.tscience.usaidprojects.utils.USAidProjectsLatLngCenterObject;
import com.tscience.usaidprojects.utils.USAidProjectsSnapshotObject;
import com.tscience.usaidprojects.utils.USAidProjectsUtility;

import android.util.Log;


/**
 * This task gets the snapshot USAid data.
 * 
 * @author spotell at t-sciences.com
 *
 */
public class USAidProjectsSnapshotTask extends USAidProjectsBaseNetworkTask {

    /** Log id of this class name. */
    private static final String LOG_TAG = "USAidProjectsSnapshotTask";
    
    // weak reference to check and make sure fragment is still there
    private final WeakReference<USAidFilterFragment> usaidFilterFragmentReference;
    
    /**
     * Public constructor with weak reference to the fragment that launched it.
     * 
     * @param value The launching fragment.
     */
    public USAidProjectsSnapshotTask(USAidFilterFragment value) {
        
        usaidFilterFragmentReference = new WeakReference<USAidFilterFragment>(value);
        
        context = value.getActivity();
        
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        
        // create the cache file name prior to super
        cacheFileName = context.getString(R.string.usaid_json_snapshot_cache_file);
        
        super.onPostExecute(result);
        
        if (workingData == null) {
            
            if (usaidFilterFragmentReference != null) {
                
                USAidFilterFragment usaidFilterFragment = usaidFilterFragmentReference.get();
                usaidFilterFragment.noCachedData();

            }
            
            return;
            
        } // end no data
        
        // create the array of data objects for subinitiatives
        ArrayList<USAidProjectsSnapshotObject> subInitiatives = new ArrayList<USAidProjectsSnapshotObject>();
        
        // now parse the json use workingData
        JSONArray subinitiativesData = null;
        
        try {
            subinitiativesData = workingData.getJSONArray(context.getString(R.string.usaid_projects_snapshot_subinitiatives_jason_array));
        }
        catch (JSONException e1) {
            e1.printStackTrace();
            
            // TODO error here is fatal 
            
        }
        
        // get subinitiatives  -- name   -- label
        if (subinitiativesData != null) {
            
            // get the size of the array
            int arraySize = subinitiativesData.length();
            Log.d(LOG_TAG, "-----------------------------------------subinitiativesData arraySize: " + arraySize);
            
            // process this data
            // parse the JSONArray and create the USAidProjectsSnapshotObject array
            for (int i = 0; i < arraySize; i++) {
                
                JSONObject jsonObject;
                
                // load the individual objects
                try {
                    
                    jsonObject = subinitiativesData.getJSONObject(i);
                    
                    // create the new data object
                    USAidProjectsSnapshotObject tempValue = new USAidProjectsSnapshotObject();
                    
                    tempValue.objectType = USAidConstants.USAID_TYPE_SUBINITIATIVES;
                    tempValue.name = jsonObject.getString(context.getString(R.string.usaid_projects_name_jason_array));
                    tempValue.label = jsonObject.getString(context.getString(R.string.usaid_projects_label_jason_array));
                    
                    // add to the data array
                    subInitiatives.add(tempValue);
                    
                }
                catch (Exception ignore) {
                    Log.e(LOG_TAG, "------------------------------subinitiativesData " + ignore.toString());
                }
                
            } // end for loop processing json objects
            
            // clean up
            subinitiativesData = null;
            
        }
        
        
        JSONArray sectorsData = null;
        
        ArrayList<USAidProjectsSnapshotObject> sectorArray = new ArrayList<USAidProjectsSnapshotObject>();
        
        try {
            sectorsData = workingData.getJSONArray(context.getString(R.string.usaid_projects_snapshot_sectors_jason_array));
        }
        catch (JSONException e1) {
            e1.printStackTrace();
            
            // TODO error here is fatal 
            
        }
        
        // get sectors  -- name   -- label
        if (sectorsData != null) {
            
            // get the size of the array
            int arraySize = sectorsData.length();
            Log.d(LOG_TAG, "-----------------------------------------sectorsData arraySize: " + arraySize);
            
            // process this data
            // parse the JSONArray and create the USAidProjectsSnapshotObject array
            for (int i = 0; i < arraySize; i++) {
                
                JSONObject jsonObject;
                
                // load the individual objects
                try {
                    
                    jsonObject = sectorsData.getJSONObject(i);
                    
                    // create the new data object
                    USAidProjectsSnapshotObject tempValue = new USAidProjectsSnapshotObject();
                    
                    tempValue.objectType = USAidConstants.USAID_TYPE_SECTORS;
                    tempValue.name = jsonObject.getString(context.getString(R.string.usaid_projects_name_jason_array));
                    tempValue.label = jsonObject.getString(context.getString(R.string.usaid_projects_label_jason_array));
                    
                    // add to the data array
                    sectorArray.add(tempValue);
                    
                }
                catch (Exception ignore) {
                    Log.e(LOG_TAG, "------------------------------sectorsData " + ignore.toString());
                }
                
            } // end for loop processing json objects
            
            // clean up
            sectorsData = null;
            
        }
        
        // sort the sector names
        Collections.sort(sectorArray, new DisplayNameComparator());
        
        
        JSONArray regionsData = null;
        
        ArrayList<USAidProjectsSnapshotObject> regionArray = new ArrayList<USAidProjectsSnapshotObject>();
        
        try {
            regionsData = workingData.getJSONArray(context.getString(R.string.usaid_projects_snapshot_regions_jason_array));
        }
        catch (JSONException e1) {
            e1.printStackTrace();
            
            // TODO error here is fatal 
            
        }
        
        // get regions  -- name   -- label
        if (regionsData != null) {
            
            // get the size of the array
            int arraySize = regionsData.length();
            Log.d(LOG_TAG, "-----------------------------------------regionsData arraySize: " + arraySize);
            
            // process this data
            // parse the JSONArray and create the USAidProjectsSnapshotObject array
            for (int i = 0; i < arraySize; i++) {
                
                JSONObject jsonObject;
                
                // load the individual objects
                try {
                    
                    jsonObject = regionsData.getJSONObject(i);
                    
                    // create the new data object
                    USAidProjectsSnapshotObject tempValue = new USAidProjectsSnapshotObject();
                    
                    tempValue.objectType = USAidConstants.USAID_TYPE_REGIONS;
                    tempValue.name = jsonObject.getString(context.getString(R.string.usaid_projects_name_jason_array));
                    tempValue.label = jsonObject.getString(context.getString(R.string.usaid_projects_label_jason_array));
                    
                    // add to the data array
                    regionArray.add(tempValue);
                    
                }
                catch (Exception ignore) {
                    Log.e(LOG_TAG, "------------------------------regionsData " + ignore.toString());
                }
                
            } // end for loop processing json objects
            
            // clean up
            regionsData = null;
            
        }
        
        // sort the region names
        Collections.sort(regionArray, new DisplayNameComparator());
        
        // set the hashmap
        if (USAidMainActivity.usaidCenterHashMap == null) {
            USAidMainActivity.usaidCenterHashMap = new HashMap<String, USAidProjectsLatLngCenterObject>();
        } else {
            // for reload
            USAidMainActivity.usaidCenterHashMap.clear();
        }
        
        JSONArray locationsData = null;
        
        // create the country map
        HashMap<String, ArrayList<USAidProjectsSnapshotObject>> countryMap = new HashMap<String, ArrayList<USAidProjectsSnapshotObject>>();
        
        int numRegions = regionArray.size();
        
        for (int i = 0; i < numRegions; i++) {
            
            countryMap.put(regionArray.get(i).name, new ArrayList<USAidProjectsSnapshotObject>());
            
        }
        
        try {
            locationsData = workingData.getJSONArray(context.getString(R.string.usaid_projects_snapshot_locations_jason_array));
        }
        catch (JSONException e1) {
            e1.printStackTrace();
            
            // TODO error here is fatal 
            
        }
        
        // get locations (countries) -- url (to country site)  -- parent  (region name) -- name -- label -- code (country code)
        if (locationsData != null) {
            
            // get the size of the array
            int arraySize = locationsData.length();
            Log.d(LOG_TAG, "-----------------------------------------locationsData arraySize: " + arraySize);
            
            // process this data
            // parse the JSONArray and create the USAidProjectsSnapshotObject array
            for (int i = 0; i < arraySize; i++) {
                
                JSONObject jsonObject;
                
                // load the individual objects
                try {
                    
                    jsonObject = locationsData.getJSONObject(i);
                    
                    // create the new data object
                    USAidProjectsSnapshotObject tempValue = new USAidProjectsSnapshotObject();
                    
                    tempValue.objectType = USAidConstants.USAID_TYPE_LOCATIONS;
                    tempValue.countryUrl = jsonObject.getString(context.getString(R.string.usaid_projects_url_jason_array));
                    tempValue.parentRegion = jsonObject.getString(context.getString(R.string.usaid_projects_parent_jason_array));
                    tempValue.name = jsonObject.getString(context.getString(R.string.usaid_projects_name_jason_array));
                    tempValue.label = jsonObject.getString(context.getString(R.string.usaid_projects_label_jason_array));
                    tempValue.countryCode = jsonObject.getString(context.getString(R.string.usaid_projects_code_jason_array));
                    
                    // add to the data array
                    countryMap.get(tempValue.parentRegion).add(tempValue);
                    
                    // get the bounds
                    String boundString = jsonObject.getString(context.getString(R.string.usaid_projects_snapshot_bound));
                    
                    USAidProjectsLatLngCenterObject newObject = new USAidProjectsLatLngCenterObject();
                    newObject.center = USAidProjectsUtility.convertStringToLatLng(boundString);
                    
                    USAidMainActivity.usaidCenterHashMap.put(tempValue.name, newObject);
                    
                }
                catch (Exception ignore) {
                    Log.e(LOG_TAG, "------------------------------locationsData " + ignore.toString());
                }
                
            } // end for loop processing json objects
            
            // clean up
            locationsData = null;
            
        }
        
        // before we display this lets sort by displayname
        for (int j = 0; j < numRegions; j++) {
            Collections.sort(countryMap.get(regionArray.get(j).name), new DisplayNameComparator());
        }
        
        JSONArray initiativesData = null;
        
        try {
            initiativesData = workingData.getJSONArray(context.getString(R.string.usaid_projects_snapshot_initiatives_jason_array));
        }
        catch (JSONException e1) {
            e1.printStackTrace();
            
            // TODO error here is fatal 
            
        }
        
        ArrayList<USAidProjectsSnapshotObject> initiatives = new ArrayList<USAidProjectsSnapshotObject>();
        
        // get initiatives   -- name   -- label
        if (initiativesData != null) {
            
            // get the size of the array
            int arraySize = initiativesData.length();
            Log.d(LOG_TAG, "-----------------------------------------initiativesData arraySize: " + arraySize);
            
            // process this data
            // parse the JSONArray and create the USAidProjectsSnapshotObject array
            for (int i = 0; i < arraySize; i++) {
                
                JSONObject jsonObject;
                
                // load the individual objects
                try {
                    
                    jsonObject = initiativesData.getJSONObject(i);
                    
                    // create the new data object
                    USAidProjectsSnapshotObject tempValue = new USAidProjectsSnapshotObject();
                    
                    tempValue.objectType = USAidConstants.USAID_TYPE_INITIATIVES;
                    tempValue.name = jsonObject.getString(context.getString(R.string.usaid_projects_name_jason_array));
                    tempValue.label = jsonObject.getString(context.getString(R.string.usaid_projects_label_jason_array));
                    
                    // add to the data array
                    initiatives.add(tempValue);
                    
                }
                catch (Exception ignore) {
                    Log.e(LOG_TAG, "------------------------------initiativesData " + ignore.toString());
                }
                
            } // end for loop processing json objects
            
            // clean up
            initiativesData = null;
            
        }
        
        // create the initiatives map
        HashMap<String, ArrayList<USAidProjectsSnapshotObject>> initiativeMap = new HashMap<String, ArrayList<USAidProjectsSnapshotObject>>();
        
        initiativeMap.put(initiatives.get(0).name, subInitiatives);
        
//        int numInitiatives = initiatives.size();
//        
//        
//        
//        for (int i = 0; i < numInitiatives; i++) {
//            
//            if (i)
//            initiativeMap.put(initiatives.get(i).name, new ArrayList<USAidProjectsSnapshotObject>());
//            
//        }
        
        // send data to fragment
        if (usaidFilterFragmentReference != null) {
            
        	USAidFilterFragment usaidFilterFragment = usaidFilterFragmentReference.get();
        	usaidFilterFragment.prepareListData(initiatives, initiativeMap, regionArray, countryMap, sectorArray, usingChachedData);
            
        }
        
        // turn the progress dialog off
        try {
            progressDialog.dismiss();
        } catch (Exception ignore) {}
        
        // little cleanup
        context = null;
        
    } // end onPostExecute
    
    /**
     * This class sorts data by published date.
     * 
     * @author spotell at t-sciences.com
     *
     */
    class DisplayNameComparator implements Comparator<USAidProjectsSnapshotObject> {
        public int compare(USAidProjectsSnapshotObject object1, USAidProjectsSnapshotObject object2) {
            return ((object1.label).compareTo(object2.label));
        }
    }

} // end USAidProjectsSnapshotTask
