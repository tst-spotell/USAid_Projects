/**
 * Copyright (c) 2013 Thermopylae Sciences and Technology. All rights reserved.
 */
package com.tscience.usaidprojects.io;

import java.util.ArrayList;

import org.json.JSONObject;

import com.tscience.usaidprojects.utils.USAidProjectsListObject;

import android.util.Log;


/**
 * This task gets the project list for a given country.
 * 
 * @author spotell at t-sciences.com
 *
 */
public class USAidProjectsByCountryTask extends USAidProjectsBaseNetworkTask {

    /** Log id of this class name. */
    private static final String LOG_TAG = "USAidProjectsByCountryTask";

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
        ArrayList<USAidProjectsListObject> items = new ArrayList<USAidProjectsListObject>();
        
    } // end onPostExecute
    
    
    // attributes  -- type and url
    // get Public_Name__c and Id

} // end USAidProjectsByCountryTask
