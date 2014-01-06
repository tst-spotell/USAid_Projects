/**
 * Copyright (c) 2013 Thermopylae Sciences and Technology. All rights reserved.
 */
package com.tscience.usaidprojects;

import java.util.ArrayList;

import com.tscience.usaidprojects.utils.USAidProjectsSnapshotObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * This is the array adapter class for displaying the sectors in a list.
 * 
 * @author spotell at t-sciences.com
 *
 */
public class USAidSectorListAdapter extends ArrayAdapter<USAidProjectsSnapshotObject> {

    private ArrayList<USAidProjectsSnapshotObject> items;
    
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
        
        items = value;
        
        // create the inflater
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        
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
        
        // the usaid DataObject object we are working with
        usaidSectorHolder.usaidDataObject = items.get(position);
        
        // TODO set the image
        
        // set the name
        usaidSectorHolder.sectorNameView.setText(usaidSectorHolder.usaidDataObject.label);
        
        usaidSectorHolder.sectorCheckBox.setSelected(usaidSectorHolder.usaidDataObject.selected);
        
        return currentView;
        
    } // end getView
    
    static class USAidSectorHolder {
        
        ImageView typeImageView;
        TextView sectorNameView;
        CheckBox sectorCheckBox;
        
        USAidProjectsSnapshotObject usaidDataObject;
        
    }

} // end USAidSectorListAdapter
