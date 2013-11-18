/**
 * Copyright (c) 2013 Thermopylae Sciences and Technology. All rights reserved.
 */
package com.tscience.usaidprojects.io;

import org.json.JSONObject;


/**
 * This task gets a project description.
 * 
 * @author spotell at t-sciences.com
 *
 */
public class USAidProjectsDetailTask extends USAidProjectsBaseNetworkTask {

    /** Log id of this class name. */
    private static final String LOG_TAG = "USAidProjectsDetailTask";

    @Override
    protected void onPostExecute(JSONObject result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
    }
    
    
    
    // get Public_Name__c
    // get secName__c
    // get Start_Date__c
    // get End_Date__c
    // get Public_Photo__c
    // get Project_Description__c
    // get ImpMechs_Projects__r  -- records -- Implementing_Mechanism_Number__r -- Award_Amount__c and Partner_Name__c

} // end USAidProjectsDetailTask
