/**
 * Copyright (c) 2013 Thermopylae Sciences and Technology. All rights reserved.
 */
package com.tscience.usaidprojects.io;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.tscience.usaidprojects.R;
import com.tscience.usaidprojects.USAidFilterFragment;
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
    
    // weak reference to check and make sure fragment is still there
    private final WeakReference<USAidFilterFragment> usaidFilterFragmentReference;
    
    /**
     * Public constructor with weak reference to the fragment that launched it.
     * 
     * @param value The launching fragment.
     */
    public USAidProjectsOverviewTask(USAidFilterFragment value) {
        
        usaidFilterFragmentReference = new WeakReference<USAidFilterFragment>(value);
        
        context = value.getActivity();
        
    }
    
    @Override
    protected void onPostExecute(JSONObject result) {
        
        cacheFileName = context.getString(R.string.usaid_json_overview_cache_file);
        
        super.onPostExecute(result);
        
        if (workingData == null) {
            
            if (usaidFilterFragmentReference != null) {
                
                USAidFilterFragment usaidFilterFragment = usaidFilterFragmentReference.get();
                usaidFilterFragment.noCachedData();

            }
            
            return;
            
        } // end no data
        
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
            
            // TODO error here is fatal 
            
        }
        
        // get subinitiatives  -- name   -- label
        if (overviewData != null) {
            
            // get the size of the array
            int arraySize = overviewData.length();
            Log.d(LOG_TAG, "-----------------------------------------overviewData arraySize: " + arraySize);
            
            // create the new data object
            USAidProjectsOverviewObject currentValue = new USAidProjectsOverviewObject();
            
            // process this data
            // parse the JSONArray and create the USAidProjectsSnapshotObject array
            for (int i = 0; i < arraySize; i++) {
                
                JSONObject jsonObject;
                
                // load the individual objects
                try {
                    
                    jsonObject = overviewData.getJSONObject(i);
                    
                    if (currentValue.countryID != null) {
                    	
                    	// get the cuntry id for evaluation
                    	String tempCountry = jsonObject.getString(context.getString(R.string.usaid_projects_country_jason_array));
                    	int tempCount = jsonObject.getInt(context.getString(R.string.usaid_projects_total_jason_array));
                    	
                    	// is this the country we are currently counting
                    	if (currentValue.countryID.equalsIgnoreCase(tempCountry)) {
                    		
                    		// add the projects to the count
                    		currentValue.totalProjects += tempCount;
                    		
                    	} else {
                    		
                    		// save the current value
                    		// add to the data array
                            items.add(currentValue);
                            Log.d(LOG_TAG, "----------------------------country: " + currentValue.countryID + " count: " + currentValue.totalProjects);
                    		
                    		// create a new current value
                            currentValue = new USAidProjectsOverviewObject();
                    		
                    		// set the values
                            currentValue.countryID = tempCountry;
                            currentValue.totalProjects = tempCount;
                            // TODO
                        	currentValue.countryCode = 0;
                    		
                    	}
                    	
                    	
                    } else {
                    
                    	// set the first value
                    	currentValue.totalProjects = jsonObject.getInt(context.getString(R.string.usaid_projects_total_jason_array));
                    	currentValue.countryID = jsonObject.getString(context.getString(R.string.usaid_projects_country_jason_array));
	                    // TODO
                    	currentValue.countryCode = 0;
                    
                    }
                    
                }
                catch (Exception ignore) {
                    Log.e(LOG_TAG, "------------------------------subinitiativesData " + ignore.toString());
                }
                
            } // end for loop processing json objects
            
            // clean up
            overviewData = null;
            
        }
        
        // TODO send to fragment
        
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
