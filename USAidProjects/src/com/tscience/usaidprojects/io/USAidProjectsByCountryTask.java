/**
 * Copyright (c) 2013 Thermopylae Sciences and Technology. All rights reserved.
 */
package com.tscience.usaidprojects.io;

import org.json.JSONObject;


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
        // TODO Auto-generated method stub
        super.onPostExecute(result);
    }
    
    
    // attributes  -- type and url
    // get Public_Name__c and Id

} // end USAidProjectsByCountryTask
