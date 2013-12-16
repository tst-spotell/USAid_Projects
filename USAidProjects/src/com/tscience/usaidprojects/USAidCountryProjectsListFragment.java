/**
 * Copyright (c) 2013 Thermopylae Sciences and Technology. All rights reserved.
 */
package com.tscience.usaidprojects;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.tscience.usaidprojects.io.USAidProjectsByCountryTask;
import com.tscience.usaidprojects.utils.USAidProjectsListObject;
import com.tscience.usaidprojects.utils.USAidProjectsUtility;

/**
 * This is the fragment for displaying projects within a country search results.
 * 
 * @author spotell at t-sciences.com
 */
public class USAidCountryProjectsListFragment extends SherlockFragment {
    
    public static String countryName;
    
    private ArrayList<USAidProjectsListObject> serverData;
    
    
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
        // layout the view
        View listView = inflater.inflate(R.layout.usaid_country_projects, container, false);
        
        return listView;
        
    } // end onCreateView

    

    @Override
    public void onResume() {
        super.onResume();
        
        if (serverData == null) {
            
            // get a list of projects by country
            USAidProjectsByCountryTask usaidProjectsByCountryTask = new USAidProjectsByCountryTask(this);
            usaidProjectsByCountryTask.execute(USAidProjectsUtility.getUrlProjectsByCountry(getActivity(), countryName));
            
            return;
            
        }
        
        prepareListData(serverData);
        
    }



    /**
     * Called from the async task that is getting the data from the server.
     * 
     * @param value The data to be displayed.
     */
    public void prepareListData(ArrayList<USAidProjectsListObject> value) {
        
        serverData = value;
        
        
        
    } // end prepareListData

} // end USAidCountryProjectsListFragment
