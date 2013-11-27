/**
 * Copyright (c) 2013 Thermopylae Sciences and Technology. All rights reserved.
 */
package com.tscience.usaidprojects.io;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tscience.usaidprojects.R;
import com.tscience.usaidprojects.USAidConstants;
import com.tscience.usaidprojects.USAidFilterFragment;
import com.tscience.usaidprojects.utils.USAidProjectsSnapshotObject;

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
        
        // create the array of data objects
        ArrayList<USAidProjectsSnapshotObject> items = new ArrayList<USAidProjectsSnapshotObject>();
        
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
                    items.add(tempValue);
                    
                }
                catch (Exception ignore) {
                    Log.e(LOG_TAG, "------------------------------subinitiativesData " + ignore.toString());
                }
                
            } // end for loop processing json objects
            
            // clean up
            subinitiativesData = null;
            
        }
        
        
        JSONArray sectorsData = null;
        
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
                    items.add(tempValue);
                    
                }
                catch (Exception ignore) {
                    Log.e(LOG_TAG, "------------------------------sectorsData " + ignore.toString());
                }
                
            } // end for loop processing json objects
            
            // clean up
            sectorsData = null;
            
        }
        
        
        JSONArray regionsData = null;
        
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
                    items.add(tempValue);
                    
                }
                catch (Exception ignore) {
                    Log.e(LOG_TAG, "------------------------------regionsData " + ignore.toString());
                }
                
            } // end for loop processing json objects
            
            // clean up
            regionsData = null;
            
        }
        
        
        JSONArray locationsData = null;
        
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
                    items.add(tempValue);
                    
                }
                catch (Exception ignore) {
                    Log.e(LOG_TAG, "------------------------------locationsData " + ignore.toString());
                }
                
            } // end for loop processing json objects
            
            // clean up
            locationsData = null;
            
        }
        
        JSONArray initiativesData = null;
        
        try {
            initiativesData = workingData.getJSONArray(context.getString(R.string.usaid_projects_snapshot_initiatives_jason_array));
        }
        catch (JSONException e1) {
            e1.printStackTrace();
            
            // TODO error here is fatal 
            
        }
        
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
                    items.add(tempValue);
                    
                }
                catch (Exception ignore) {
                    Log.e(LOG_TAG, "------------------------------initiativesData " + ignore.toString());
                }
                
            } // end for loop processing json objects
            
            // clean up
            initiativesData = null;
            
        }
        
        // send data to fragment
        if (usaidFilterFragmentReference != null) {
            
        	USAidFilterFragment usaidFilterFragment = usaidFilterFragmentReference.get();
        	usaidFilterFragment.prepareListData(items, usingChachedData);
            
        }
        
        // turn the progress dialog off
        try {
            progressDialog.dismiss();
        } catch (Exception ignore) {}
        
        // little cleanup
        context = null;
        
    } // end onPostExecute

} // end USAidProjectsSnapshotTask
