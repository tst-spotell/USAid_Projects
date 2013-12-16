/**
 * Copyright (c) 2013 Thermopylae Sciences and Technology. All rights reserved.
 */
package com.tscience.usaidprojects.io;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.tscience.usaidprojects.R;
import com.tscience.usaidprojects.utils.USAidProjectsListObject;

import android.content.Context;
import android.util.Log;


/**
 * This task gets the project list for a given country.
 * 
 * @author spotell at t-sciences.com
 *
 */
public class USAidProjectsByCountryTask extends USAidProjectsJSONArrayBaseTask {

    /** Log id of this class name. */
    private static final String LOG_TAG = "USAidProjectsByCountryTask";
    
    /**
     * Public constructor.
     * 
     * @param value The launching fragment.
     */
    public USAidProjectsByCountryTask(Context value) {
        
        context = value;
        
    }

    @Override
    protected void onPostExecute(JSONArray result) {
        super.onPostExecute(result);
        
        if (workingData == null) {
            
            Log.d(LOG_TAG, "-----------------------------------------no working data");
            
            return;
            
        } // end no data
        
        Log.d(LOG_TAG, "----------------------------------------- we have working data");
        
        // now parse the json use workingData
        // create the array of data objects
        ArrayList<USAidProjectsListObject> items = new ArrayList<USAidProjectsListObject>();
        
        // get the size of the array
        int arraySize = workingData.length();
        Log.d(LOG_TAG, "-----------------------------------------country projects arraySize: " + arraySize);
        
        JSONObject jsonObject;
        
        // process this data
        // parse the JSONArray and create the USAidProjectsSnapshotObject array
        for (int i = 0; i < arraySize; i++) {
            
            // load the individual objects
            try {
                
             // create the new data object
             USAidProjectsListObject newData = new USAidProjectsListObject();
             
             jsonObject = workingData.getJSONObject(i);
             
             newData.projectName = jsonObject.getString(context.getString(R.string.usaid_projects_country_public_name_jason_array));
             newData.projectID = jsonObject.getString(context.getString(R.string.usaid_projects_country_id_jason_array));
             
             Log.d(LOG_TAG, "-----------------------------------------" + newData.projectName + "  " + newData.projectID);
             
             items.add(newData);
                
            }
            catch (Exception ignore) {
                Log.e(LOG_TAG, "------------------------------projects by country " + ignore.toString());
            }
        
        } // end for loop
        
        // TODO do something with the data
        
        // turn the progress dialog off
        try {
            progressDialog.dismiss();
        } catch (Exception ignore) {}
        
        // little cleanup
        context = null;
        
    } // end onPostExecute
    
    
    // attributes  -- type and url
    // get Public_Name__c and Id

} // end USAidProjectsByCountryTask
