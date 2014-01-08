/**
 * Copyright (c) 2013 Thermopylae Sciences and Technology. All rights reserved.
 */
package com.tscience.usaidprojects;

import java.util.ArrayList;

import com.tscience.usaidprojects.utils.USAidProjectsSnapshotObject;
import com.tscience.usaidprojects.utils.USAidProjectsUtility;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;


/**
 * This is the array adapter class for displaying the sectors in a list.
 * 
 * @author spotell at t-sciences.com
 *
 */
public class USAidSectorListAdapter extends ArrayAdapter<USAidProjectsSnapshotObject> {
    
    /** Log id of this class name. */
    private static final String LOG_TAG = "USAidSectorListAdapter";
    
    // the view inflater used by this adapter
    private LayoutInflater inflater;
    
    /**
     * Constructor.
     * 
     * @param context
     * @param resource
     */
    public USAidSectorListAdapter(Context context, int resource, ArrayList<USAidProjectsSnapshotObject> value) {
        super(context, resource, value);
        
        // create the inflater
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        
        Log.d(LOG_TAG, "-----------------------------------------getView: " + position);
        
        View currentView = convertView;
        
        // if the view has not been created create it
        if (currentView == null) {
            
            currentView = inflater.inflate(R.layout.usaid_sector_item, null);
            
            // create the tag we are using
            currentView.setTag(new USAidSectorHolder());
            
        }
        
        USAidSectorHolder usaidSectorHolder = (USAidSectorHolder) currentView.getTag();
        
        // load the holder if empty
        if (usaidSectorHolder.sectorNameView == null) {
            
//            usaidSectorHolder.typeImageView = (ImageView) currentView.findViewById(R.id.usaid_country_flag);
            usaidSectorHolder.sectorNameView = (TextView) currentView.findViewById(R.id.sectorNameItem);
            usaidSectorHolder.sectorCheckBox = (CheckBox) currentView.findViewById(R.id.sectorCheckbox);
            
        }
        
        // TODO set the image
        
        // set the name
        usaidSectorHolder.sectorNameView.setText(USAidFilterFragment.sectorDataHeader.get(position).label);
        
        usaidSectorHolder.sectorCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                
                Log.d(LOG_TAG, "-----------------------------------------onCheckedChanged: " + position);
                Log.d(LOG_TAG, "-----------------------------------------isChecked: " + isChecked);
                
                setChildChecked(position, isChecked);
                
            }
            
        });
        
        
        usaidSectorHolder.sectorCheckBox.setChecked(USAidFilterFragment.sectorDataHeader.get(position).selected);
        
        return currentView;
        
    } // end getView
    
    /**
     * This method sets the check value after the checkbox has been selected.
     * 
     * @param position  The position in the list.
     * @param value     The new checked value.
     */
    private void setChildChecked(int position, boolean value) {
        
        USAidFilterFragment.sectorDataHeader.get(position).selected = value;
        
        USAidMainActivity.countryQuery = USAidFilterFragment.makeFilterQuery(USAidProjectsUtility.getUrlOverview(this.getContext()), true);
        USAidMainActivity.countryQueryResults = null;
        
    }
    
    /**
     * Static class for view holder pattern.
     * 
     * @author spotell at t-sciences.com
     *
     */
    static class USAidSectorHolder {
        
        ImageView typeImageView;
        TextView sectorNameView;
        CheckBox sectorCheckBox;
        
    }

} // end USAidSectorListAdapter
