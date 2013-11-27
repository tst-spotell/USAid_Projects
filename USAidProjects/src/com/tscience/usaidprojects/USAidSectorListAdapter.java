/**
 * Copyright (c) 2013 Thermopylae Sciences and Technology. All rights reserved.
 */
package com.tscience.usaidprojects;

import com.tscience.usaidprojects.utils.USAidProjectsSnapshotObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


/**
 * This is the array adapter class for displaying the sectors in a list.
 * 
 * @author spotell at t-sciences.com
 *
 */
public class USAidSectorListAdapter extends ArrayAdapter<USAidProjectsSnapshotObject> {

    // the view inflater used by this adapter
    private LayoutInflater inflater;
    
    /**
     * Constructor.
     * 
     * @param context
     * @param resource
     */
    public USAidSectorListAdapter(Context context, int resource) {
        super(context, resource);
        
        // create the inflater
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        
        
        
        
        return super.getView(position, convertView, parent);
        
    } // end getView

} // end USAidSectorListAdapter
