/**
 * Copyright (c) 2013 Thermopylae Sciences and Technology. All rights reserved.
 */
package com.tscience.usaidprojects.io;

import java.lang.ref.WeakReference;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.tscience.usaidprojects.R;
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
        
        if (workingData == null) {
            
            Log.d(LOG_TAG, "-----------------------------------------no working data");
            
            return;
            
        } // end no data
        
        Log.d(LOG_TAG, "----------------------------------------- we have working data");
        
        USAidProjectsObject descriptionObject = null;
        
        // process this data
        try {
            
            descriptionObject = new USAidProjectsObject();
            
            descriptionObject.projectName = workingData.getString(context.getString(R.string.usaid_projects_country_public_name_jason_array));
            descriptionObject.sectorName = workingData.getString(context.getString(R.string.usaid_projects_detail_sector_name_jason_array));
            
            descriptionObject.startDate = workingData.getString(context.getString(R.string.usaid_projects_detail_start_date_jason_array));
            descriptionObject.stopDate = workingData.getString(context.getString(R.string.usaid_projects_detail_end_date_jason_array));
            
            descriptionObject.description = workingData.getString(context.getString(R.string.usaid_projects_detail_project_description_jason_array));
            
            // get the object with partner and amount
            JSONObject partnerObject = (JSONObject) workingData.get(context.getString(R.string.usaid_projects_detail_mechs_jason_array));
            
            JSONArray partnerArray = partnerObject.getJSONArray(context.getString(R.string.usaid_projects_detail_records_jason_array));
            
            JSONObject tempObject = partnerArray.getJSONObject(0).getJSONObject(context.getString(R.string.usaid_projects_detail_imn_jason_array));
            
            descriptionObject.awardAmount = tempObject.getString(context.getString(R.string.usaid_projects_detail_award_amount_jason_array));
            descriptionObject.partner = tempObject.getString(context.getString(R.string.usaid_projects_detail_partner_name_jason_array));
        
        }
        catch (Exception ignore) {
            Log.e(LOG_TAG, "------------------------------description " + ignore.toString());
        }
        
        
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
