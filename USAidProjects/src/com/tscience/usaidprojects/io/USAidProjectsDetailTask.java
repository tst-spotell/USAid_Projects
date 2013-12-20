/**
 * Copyright (c) 2013 Thermopylae Sciences and Technology. All rights reserved.
 */
package com.tscience.usaidprojects.io;

import java.lang.ref.WeakReference;

import org.json.JSONObject;

import com.tscience.usaidprojects.USAidCountryProjectsListFragment;
import com.tscience.usaidprojects.utils.USAidProjectsObject;


/**
 * This task gets a project description.
 * 
 * @author spotell at t-sciences.com
 *
 */
public class USAidProjectsDetailTask extends USAidProjectsBaseNetworkTask {

    /** Log id of this class name. */
    private static final String LOG_TAG = "USAidProjectsDetailTask";
    
    // weak reference to check and make sure fragment is still there
    private final WeakReference<USAidCountryProjectsListFragment> usaidCountryProjectsListFragmentReference;
    
    public USAidProjectsDetailTask(USAidCountryProjectsListFragment value) {
    	
    	usaidCountryProjectsListFragmentReference = new WeakReference<USAidCountryProjectsListFragment>(value);
        
        context = value.getActivity();
        
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        super.onPostExecute(result);
        
        USAidProjectsObject descriptionObject = null;
        
        // do something with the data
        if (usaidCountryProjectsListFragmentReference != null) {
            
            USAidCountryProjectsListFragment usaidCountryProjectsListFragment = usaidCountryProjectsListFragmentReference.get();
            usaidCountryProjectsListFragment.displayProject(descriptionObject);
            
        }
        
        // turn the progress dialog off
        try {
            progressDialog.dismiss();
        } catch (Exception ignore) {}
        
        // little cleanup
        context = null;
        
        
    }
    
    
    
    // get Public_Name__c
    // get secName__c
    // get Start_Date__c
    // get End_Date__c
    // get Public_Photo__c
    // get Project_Description__c
    // get ImpMechs_Projects__r  -- records -- Implementing_Mechanism_Number__r -- Award_Amount__c and Partner_Name__c

} // end USAidProjectsDetailTask
