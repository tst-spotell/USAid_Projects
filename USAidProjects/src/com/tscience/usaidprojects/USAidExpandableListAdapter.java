/**
 * Copyright (c) 2013 Thermopylae Sciences and Technology. All rights reserved.
 */
package com.tscience.usaidprojects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.tscience.usaidprojects.utils.USAidProjectsSnapshotObject;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;


/**
 * @author spotell at t-sciences.com
 *
 */
public class USAidExpandableListAdapter extends BaseExpandableListAdapter {
    
    /** Log id of this class name. */
    private static final String LOG_TAG = "USAidExpandableListAdapter";
    
    private ArrayList<USAidProjectsSnapshotObject> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, ArrayList<USAidProjectsSnapshotObject>> _listDataChild;
    
    private LayoutInflater inflater;
    
    public USAidExpandableListAdapter(Context context, ArrayList<USAidProjectsSnapshotObject> listDataHeader,
            HashMap<String, ArrayList<USAidProjectsSnapshotObject>> listChildData) {
    	
    	inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    /* (non-Javadoc)
     * @see android.widget.ExpandableListAdapter#getChild(int, int)
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition).name).get(childPosition).label;
    }

    /* (non-Javadoc)
     * @see android.widget.ExpandableListAdapter#getChildId(int, int)
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /* (non-Javadoc)
     * @see android.widget.ExpandableListAdapter#getChildView(int, int, boolean, android.view.View, android.view.ViewGroup)
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        
        View currentView = convertView;
        
        // if the view has not been created create it
        if (currentView == null) {
            
            currentView = inflater.inflate(R.layout.usaid_list_item, null);
            
            // create the tag we are using
            currentView.setTag(new USAidProjectsFilterViewHolder());
            
        }
        
        USAidProjectsFilterViewHolder usaidViewHolder = (USAidProjectsFilterViewHolder) currentView.getTag();
        
        // load the holder if empty
        if (usaidViewHolder.textView == null) {
            
            usaidViewHolder.textView = (TextView) currentView.findViewById(R.id.lblListItem);
            usaidViewHolder.checkBox = (CheckBox) currentView.findViewById(R.id.lblCheckbox);
            
        }
        
        final String childText = (String) getChild(groupPosition, childPosition);
        
        usaidViewHolder.textView.setText(childText);
        
        
        // TODO do checked by arrays
        
        return currentView;
        
    }

    /* (non-Javadoc)
     * @see android.widget.ExpandableListAdapter#getChildrenCount(int)
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition).name).size();
    }

    /* (non-Javadoc)
     * @see android.widget.ExpandableListAdapter#getGroup(int)
     */
    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition).label;
    }

    /* (non-Javadoc)
     * @see android.widget.ExpandableListAdapter#getGroupCount()
     */
    @Override
    public int getGroupCount() {
        
        if (_listDataHeader != null) {
            return this._listDataHeader.size();
        }
        
        return 0;
    }

    /* (non-Javadoc)
     * @see android.widget.ExpandableListAdapter#getGroupId(int)
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /* (non-Javadoc)
     * @see android.widget.ExpandableListAdapter#getGroupView(int, boolean, android.view.View, android.view.ViewGroup)
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

            String headerTitle = (String) getGroup(groupPosition);
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.usaid_list_group, null);
            }
     
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
 
        return convertView;
    }

    /* (non-Javadoc)
     * @see android.widget.ExpandableListAdapter#hasStableIds()
     */
    @Override
    public boolean hasStableIds() {
        return false;
    }

    /* (non-Javadoc)
     * @see android.widget.ExpandableListAdapter#isChildSelectable(int, int)
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    
    /**
     * Static class for view holder pattern.
     * 
     * @author spotell at t-sciences.com
     *
     */
    static class USAidProjectsFilterViewHolder {
        
        TextView textView;
        CheckBox checkBox;
        
    } // end SearchResultsViewHolder

} // end USAidExpandableListAdapter
