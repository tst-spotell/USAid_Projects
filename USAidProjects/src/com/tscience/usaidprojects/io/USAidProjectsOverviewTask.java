/**
 * Copyright (c) 2013 Thermopylae Sciences and Technology. All rights reserved.
 */
package com.tscience.usaidprojects.io;

import java.lang.ref.WeakReference;

import org.json.JSONObject;

import com.tscience.usaidprojects.R;
import com.tscience.usaidprojects.USAidFilterFragment;


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
