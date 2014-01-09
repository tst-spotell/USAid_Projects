/**
 * Copyright (c) 2013 Thermopylae Sciences and Technology. All rights reserved.
 */
package com.tscience.usaidprojects.io;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.tscience.usaidprojects.R;
import com.tscience.usaidprojects.USAidMainActivity;
import com.tscience.usaidprojects.io.USAidProjectsSnapshotTask.DisplayNameComparator;
import com.tscience.usaidprojects.utils.USAidProjectsCountryObject;
import com.tscience.usaidprojects.utils.USAidProjectsSnapshotObject;


/**
 * This task gets the overview USAid data.
 * 
 * @author spotell at t-sciences.com
 *
 */
public class USAidProjectsCountryTask extends USAidProjectsBaseNetworkTask {

    /** Log id of this class name. */
    private static final String LOG_TAG = "USAidProjectsCountryTask";
    
    
    /**
     * Public constructor.
     * 
     * @param value The launching fragment.
     */
    public USAidProjectsCountryTask(Context value, String cacheFileName) {
        
        context = value;
        
        this.cacheFileName = cacheFileName;
        
    }
    
    @Override
    protected void onPostExecute(JSONObject result) {
        
        super.onPostExecute(result);
        
        if (workingData == null) {
            
            Log.d(LOG_TAG, "-----------------------------------------no working data");
            
            return;
            
        } // end no data
        
        Log.d(LOG_TAG, "----------------------------------------- we have working data");
        
        // now parse the json use workingData
        // create the array of data objects
        ArrayList<USAidProjectsCountryObject> items = new ArrayList<USAidProjectsCountryObject>();
        
        // now parse the json use workingData
        JSONArray overviewData = null;
        
        try {
            overviewData = workingData.getJSONArray(context.getString(R.string.usaid_projects_overview_initiatives_jason_array));
        }
        catch (JSONException e1) {
            e1.printStackTrace();
            
            Log.e(LOG_TAG, "----------------------------------------- " + e1.toString());
            
        }
        
        // get subinitiatives  -- name   -- label
        if (overviewData != null) {
            
            // get the size of the array
            int arraySize = overviewData.length();
            Log.d(LOG_TAG, "-----------------------------------------overviewData arraySize: " + arraySize);
            
            JSONObject jsonObject;
            
            // process this data
            // parse the JSONArray and create the USAidProjectsSnapshotObject array
            for (int i = 0; i < arraySize; i++) {
                
                // load the individual objects
                try {
                    
                    // create the new data object
                    USAidProjectsCountryObject currentValue = new USAidProjectsCountryObject();
                    
                    jsonObject = overviewData.getJSONObject(i);
                    
                    
                	// set the first value
                	currentValue.totalProjects = jsonObject.getInt(context.getString(R.string.usaid_projects_total_jason_array));
                	currentValue.countryID = jsonObject.getString(context.getString(R.string.usaid_projects_country_jason_array));
                    
                	// TODO do I need this not currently used
                	currentValue.countryCode = 0;
                	
                	Log.d(LOG_TAG, "----------------------------country: " + currentValue.countryID + " count: " + currentValue.totalProjects);
                	
                	items.add(currentValue);
                    
                }
                catch (Exception ignore) {
                    Log.e(LOG_TAG, "------------------------------country overview " + ignore.toString());
                }
                
            } // end for loop processing json objects
            
            // clean up
            overviewData = null;
            
        } else {
            
            Log.d(LOG_TAG, "-----------------------------------------overviewData data is null");
            
        }
        
        // sort the sector names
        Collections.sort(items, new CountryNameComparator());
        
        // send to activity
        USAidMainActivity.setCountryQueryResults(items);
        
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
    class CountryNameComparator implements Comparator<USAidProjectsCountryObject> {
        public int compare(USAidProjectsCountryObject object1, USAidProjectsCountryObject object2) {
            return ((object1.countryID).compareTo(object2.countryID));
        }
    }
    
    // get total
    // details  -- loop for total and country to get total for a country

} // end USAidProjectsCountryTask
