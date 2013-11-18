/**
 * Copyright (c) 2013 Thermopylae Sciences and Technology. All rights reserved.
 */
package com.tscience.usaidprojects.io;

import java.lang.ref.WeakReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tscience.usaidprojects.R;
import com.tscience.usaidprojects.USAidFilterFragment;

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
    
    // weak reference to check and make sure fragment is still there
    private final WeakReference<USAidFilterFragment> usaidFilterFragmentReference;
    
    /**
     * Public constructor with weak reference to the fragment that launched it.
     * 
     * @param value The launching fragment.
     */
    public USAidProjectsSnapshotTask(USAidFilterFragment value) {
        
        usaidFilterFragmentReference = new WeakReference<USAidFilterFragment>(value);
        
        context = value.getActivity();
        
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        
        cacheFileName = context.getString(R.string.usaid_json_snapshot_cache_file);
        
        super.onPostExecute(result);
        
        if (workingData == null) {
            
            if (usaidFilterFragmentReference != null) {
                
                USAidFilterFragment usaidFilterFragment = usaidFilterFragmentReference.get();
                usaidFilterFragment.noCachedData();

            }
            
            return;
            
        } // end no data
        
        // now parse the json use workingData
        JSONArray subinitiativesData = null;
        
        try {
            subinitiativesData = result.getJSONArray(context.getString(R.string.usaid_projects_snapshot_subinitiatives_jason_array));
        }
        catch (JSONException e1) {
            e1.printStackTrace();
            
            // TODO error here is fatal 
            
        }
        
        // TODO process this data
        
        
        JSONArray sectorsData = null;
        
        try {
            sectorsData = result.getJSONArray(context.getString(R.string.usaid_projects_snapshot_sectors_jason_array));
        }
        catch (JSONException e1) {
            e1.printStackTrace();
            
            // TODO error here is fatal 
            
        }
        
        // TODO process this data
        
        
        JSONArray regionsData = null;
        
        try {
            regionsData = result.getJSONArray(context.getString(R.string.usaid_projects_snapshot_regions_jason_array));
        }
        catch (JSONException e1) {
            e1.printStackTrace();
            
            // TODO error here is fatal 
            
        }
        
        // TODO process this data
        
        
        JSONArray locationsData = null;
        
        try {
            locationsData = result.getJSONArray(context.getString(R.string.usaid_projects_snapshot_locations_jason_array));
        }
        catch (JSONException e1) {
            e1.printStackTrace();
            
            // TODO error here is fatal 
            
        }
        
        // TODO process this data
        
        
        JSONArray initiativesData = null;
        
        try {
            initiativesData = result.getJSONArray(context.getString(R.string.usaid_projects_snapshot_initiatives_jason_array));
        }
        catch (JSONException e1) {
            e1.printStackTrace();
            
            // TODO error here is fatal 
            
        }
        
        // TODO process this data
        
        
        
        // turn the progress dialog off
        try {
            progressDialog.dismiss();
        } catch (Exception ignore) {}
        
        // little cleanup
        context = null;
        
    } // end onPostExecute
    
    // get subinitiatives  -- name   -- label
    // get sectors  -- name   -- label
    // get regions  -- name   -- label
    // get locations (countries) -- url (to country site)  -- parent  (region name) -- name -- label -- code (country code)
    // get initiatives   -- name   -- label

} // end USAidProjectsSnapshotTask
