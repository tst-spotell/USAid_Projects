/**
 * Copyright (c) 2013 Thermopylae Sciences and Technology. All rights reserved.
 */
package com.tscience.usaidprojects.io;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.tscience.usaidprojects.R;
import com.tscience.usaidprojects.USAidMainActivity;
import com.tscience.usaidprojects.utils.USAidProjectsOverviewObject;


/**
 * This task gets the overview USAid data.
 * 
 * @author spotell at t-sciences.com
 *
 */
public class USAidProjectsOverviewTask extends USAidProjectsBaseNetworkTask {

    /** Log id of this class name. */
    private static final String LOG_TAG = "USAidProjectsOverviewTask";
    
    
    /**
     * Public constructor with weak reference to the fragment that launched it.
     * 
     * @param value The launching fragment.
     */
    public USAidProjectsOverviewTask(Context value, String cacheFileName) {
        
        context = value;
        
        this.cacheFileName = cacheFileName;
        
    }
    
    @Override
    protected void onPostExecute(JSONObject result) {
        
        super.onPostExecute(result);
        
        if (workingData == null) {
            
            Log.d(LOG_TAG, "-----------------------------------------no working data");
            // TODO no cached data
            
            return;
            
        } // end no data
        
        Log.d(LOG_TAG, "----------------------------------------- we have working data");
        
        // now parse the json use workingData
        // create the array of data objects
        ArrayList<USAidProjectsOverviewObject> items = new ArrayList<USAidProjectsOverviewObject>();
        
        // now parse the json use workingData
        JSONArray overviewData = null;
        
        try {
            overviewData = workingData.getJSONArray(context.getString(R.string.usaid_projects_overview_initiatives_jason_array));
        }
        catch (JSONException e1) {
            e1.printStackTrace();
            
            Log.e(LOG_TAG, "----------------------------------------- " + e1.toString());
            
            // TODO error here is fatal 
            
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
                    USAidProjectsOverviewObject currentValue = new USAidProjectsOverviewObject();
                    
                    jsonObject = overviewData.getJSONObject(i);
                    
//                    if (currentValue.countryID != null) {
//                    	
//                    	// get the cuntry id for evaluation
//                    	String tempCountry = jsonObject.getString(context.getString(R.string.usaid_projects_country_jason_array));
//                    	int tempCount = jsonObject.getInt(context.getString(R.string.usaid_projects_total_jason_array));
//                    	
//                    	// is this the country we are currently counting
//                    	if (currentValue.countryID.equalsIgnoreCase(tempCountry)) {
//                    		
//                    		// add the projects to the count
//                    		currentValue.totalProjects += tempCount;
//                    		
//                    	} else {
//                    		
//                    		// save the current value
//                    		// add to the data array
//                            items.add(currentValue);
//                            Log.d(LOG_TAG, "----------------------------country: " + currentValue.countryID + " count: " + currentValue.totalProjects);
//                    		
//                    		// create a new current value
//                            currentValue = new USAidProjectsOverviewObject();
//                    		
//                    		// set the values
//                            currentValue.countryID = tempCountry;
//                            currentValue.totalProjects = tempCount;
//                            // TODO
//                        	currentValue.countryCode = 0;
//                    		
//                    	}
//                    	
//                    	
//                    } else {
                    
                    	// set the first value
                    	currentValue.totalProjects = jsonObject.getInt(context.getString(R.string.usaid_projects_total_jason_array));
                    	currentValue.countryID = jsonObject.getString(context.getString(R.string.usaid_projects_country_jason_array));
	                    // TODO
                    	currentValue.countryCode = 0;
                    	
                    	Log.d(LOG_TAG, "----------------------------country: " + currentValue.countryID + " count: " + currentValue.totalProjects);
                    	
                    	items.add(currentValue);
                    
//                    }
                    
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
        
        // TODO send to activity
        USAidMainActivity.countryQueryResults = items;
        
        // turn the progress dialog off
        try {
            progressDialog.dismiss();
        } catch (Exception ignore) {}
        
        // little cleanup
        context = null;
        
    } // end onPostExecute
    
    // get total
    // details  -- loop for total and country to get total for a country

} // end USAidProjectsOverviewTask
