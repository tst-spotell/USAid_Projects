/**
 * Copyright (c) 2013 Thermopylae Sciences and Technology. All rights reserved.
 */
package com.tscience.usaidprojects;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListFragment;

import com.tscience.usaidprojects.io.USAidProjectsCountryTask;
import com.tscience.usaidprojects.utils.USAidProjectsCountryObject;

/**
 * This is the fragment for displaying country search results.
 * 
 * @author spotell at t-sciences.com
 */
public class USAidCountryListFragment extends SherlockListFragment {
    
    /** Log id of this class name. */
    private static final String LOG_TAG = "USAidCountryListFragment";
    
    private USAidCountryListAdapter myCountryListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
        // layout the view
        View listView = inflater.inflate(R.layout.usaid_country_layout, container, false);
        
        // TODO load data from country list
        
        return listView;
        
    } // end onCreateView
    

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        
        // get the data object
        USAidProjectsCountryObject usaidProjectsCountryObject = USAidMainActivity.countryQueryResults.get(position);
        
        // TODO get a list of projects by country
        
//        // make the new bundle
//        Bundle bundle = new Bundle();
//        bundle.putParcelable(USAidConstants.USAID_BUNDLE_DATA_OBJECT, usaidProjectsCountryObject);
//        
//        USAidProjectDialog usaidProjectDialog = USAidProjectDialog.newInstance(bundle);
//        usaidProjectDialog.show(getActivity().getSupportFragmentManager(), "project");
        
    }



    @Override
    public void onResume() {
        super.onResume();
        
        if (USAidMainActivity.countryQueryResults == null) {
            
            // get the count
            USAidProjectsCountryTask usaidProjectsOverviewTask = new USAidProjectsCountryTask(getActivity(), null);
            usaidProjectsOverviewTask.execute(USAidMainActivity.countryQuery);
            
        } else {
            
            Log.d(LOG_TAG, "-------------------------------- USAidMainActivity.countryQueryResults all ready loaded");
            
        }
        
    }
    
    /**
     * This method is called from a thread to update the query results.
     * 
     * @param countryQueryResults
     */
    public void setTheListData() {
        
        Log.d(LOG_TAG, "-------------------------------- setTheListData");
        
        // create a new list adapter
        myCountryListAdapter = new USAidCountryListAdapter(getActivity(), R.layout.usaid_country_item, USAidMainActivity.countryQueryResults);
        
        // set list adapter
        this.setListAdapter(myCountryListAdapter);
        
        // update list data
        myCountryListAdapter.notifyDataSetChanged();
        
    } // end setTheListData
    
    /**
     * This is the array adapter class used for our custom view.
     * 
     * @author spotell at t-sciences.com
     */
    private class USAidCountryListAdapter extends ArrayAdapter<USAidProjectsCountryObject> {
        
        ArrayList<USAidProjectsCountryObject> items;
        
        private LayoutInflater inflater;
        
        public USAidCountryListAdapter (Context context, int textViewResourceId, ArrayList<USAidProjectsCountryObject> value) {
            super(context, textViewResourceId, value);
            
            items = value;
            
            inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            
            View currentView = convertView;
            
            // if the view has not been created create it
            if (currentView == null) {
                
                currentView = inflater.inflate(R.layout.usaid_country_item, null);
                
                // create the tag we are using
                currentView.setTag(new USAidCountryHolder());
                
            }
            
            USAidCountryHolder usaidCountryHolder = (USAidCountryHolder) currentView.getTag();
            
            // load the holder if empty
            if (usaidCountryHolder.countryNameView == null) {
                
                usaidCountryHolder.flagImageView = (ImageView) currentView.findViewById(R.id.usaid_country_flag);
                usaidCountryHolder.countryNameView = (TextView) currentView.findViewById(R.id.usaid_country_name);
                
            }
            
            // the usaid DataObject object we are working with
            usaidCountryHolder.usaidDataObject = items.get(position);
            
            // set the name
            usaidCountryHolder.countryNameView.setText(usaidCountryHolder.usaidDataObject.countryID);
            
            // TODO set the flag
            
            return currentView;
            
        } // end getView
        
        
        
    } // end USAidCountryListAdapter
    
    static class USAidCountryHolder {
        
        ImageView flagImageView;
        TextView countryNameView;
        
        USAidProjectsCountryObject usaidDataObject;
        
    }

} // end USAidCountryListFragment
