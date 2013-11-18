/**
 * Copyright (c) 2013 Thermopylae Sciences and Technology. All rights reserved.
 */
package com.tscience.usaidprojects.io;

import org.json.JSONObject;

import android.os.AsyncTask;


/**
 * This task gets the overview USAid data.
 * 
 * @author spotell at t-sciences.com
 *
 */
public class USAidProjectsOverviewTask extends USAidProjectsBaseNetworkTask {

    /** Log id of this class name. */
    private static final String LOG_TAG = "USAidProjectsOverviewTask";
    
    @Override
    protected void onPostExecute(JSONObject result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
    }
    
    // get total
    // details  -- loop for total and country to get total for a country

} // end USAidProjectsOverviewTask
