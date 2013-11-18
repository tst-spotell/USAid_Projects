/**
 * Copyright (c) 2013 Thermopylae Sciences and Technology. All rights reserved.
 */
package com.tscience.usaidprojects.io;

import org.json.JSONObject;

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

    @Override
    protected void onPostExecute(JSONObject result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
    }
    
    
    
    // get subinitiatives  -- name   -- label
    // get sectors  -- name   -- label
    // get regions  -- name   -- label
    // get locations (countries) -- url (to country site)  -- parent  (region name) -- name -- label -- code (country code)
    // get initiatives   -- name   -- label

} // end USAidProjectsSnapshotTask
