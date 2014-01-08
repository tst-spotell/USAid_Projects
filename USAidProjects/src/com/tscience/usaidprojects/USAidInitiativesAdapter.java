/**
 * Copyright (c) 2014 Thermopylae Sciences and Technology. All rights reserved.
 */
package com.tscience.usaidprojects;

import com.tscience.usaidprojects.USAidExpandableListAdapter.USAidProjectsFilterViewHolder;
import com.tscience.usaidprojects.utils.USAidProjectsUtility;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;


/**
 * This is the adapter for displaying the Initiatives.
 * 
 * @author spotell at t-sciences.com
 */
public class USAidInitiativesAdapter extends BaseExpandableListAdapter {
    
    /** Log id of this class name. */
    private static final String LOG_TAG = "USAidInitiativesAdapter";
    
    private LayoutInflater inflater;
    
    private boolean headerChange = false;
    
    public USAidInitiativesAdapter(Context context) {
        
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
    }

    /* (non-Javadoc)
     * @see android.widget.ExpandableListAdapter#getChild(int, int)
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return USAidFilterFragment.initiativeDataChild.get(USAidFilterFragment.initiativeDataHeader.get(groupPosition).name).get(childPosition).label;
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
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        
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
        
        // add the check change listener to update the objects
        usaidViewHolder.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                
                Log.d(LOG_TAG, "-----------------------------------------onCheckedChanged: " + groupPosition + "  " + childPosition);
                
                setChildChecked(groupPosition, childPosition, isChecked);
                
            }
            
        });
        
        usaidViewHolder.checkBox.setChecked(getChildChecked(groupPosition, childPosition));
        
        return currentView;
        
    }
    
    /**
     * Get the checked value.
     * 
     * @param groupPosition The group position name in the list.
     * @param childPosition The child position in the list.
     * 
     * @return The value of the onjects checked.
     */
    private boolean getChildChecked(int groupPosition, int childPosition) {
        
        return USAidFilterFragment.initiativeDataChild.get(USAidFilterFragment.initiativeDataHeader.get(groupPosition).name).get(childPosition).selected;
        
    }
    
    /**
     * This method sets the check value after the checkbox has been selected.
     * 
     * @param groupPosition The group position name in the list.
     * @param childPosition The child position in the list.
     * @param value         The new checked value.
     */
    private void setChildChecked(int groupPosition, int childPosition, boolean value) {
        
        USAidFilterFragment.initiativeDataChild.get(USAidFilterFragment.initiativeDataHeader.get(groupPosition).name).get(childPosition).selected = value;
        
        // individual checked
        if (!headerChange) {
            USAidMainActivity.countryQuery = USAidFilterFragment.makeFilterQuery(USAidProjectsUtility.getUrlOverview(this.inflater.getContext()));
            USAidMainActivity.countryQueryResults = null;
        }
        
    }

    /* (non-Javadoc)
     * @see android.widget.ExpandableListAdapter#getChildrenCount(int)
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        if (groupPosition == 0) {
            return USAidFilterFragment.initiativeDataChild.get(USAidFilterFragment.initiativeDataHeader.get(groupPosition).name).size();
        }
        
        return 0;
    }

    /* (non-Javadoc)
     * @see android.widget.ExpandableListAdapter#getGroup(int)
     */
    @Override
    public Object getGroup(int groupPosition) {
        return USAidFilterFragment.initiativeDataHeader.get(groupPosition).label;
    }

    /* (non-Javadoc)
     * @see android.widget.ExpandableListAdapter#getGroupCount()
     */
    @Override
    public int getGroupCount() {
        
        if (USAidFilterFragment.initiativeDataHeader != null) {
            return USAidFilterFragment.initiativeDataHeader.size();
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
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.usaid_list_group, null);
        }
     
        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        
        CheckBox lblHeaderCheckbox = (CheckBox) convertView.findViewById(R.id.lblHeaderCheckbox);
        lblHeaderCheckbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                
                Log.d(LOG_TAG, "-----------------------------------------onHeaderCheckedChanged: " + groupPosition);
                
                setHeaderChecked(groupPosition, isChecked);
                
            }
            
        });
        
        lblHeaderCheckbox.setChecked(USAidFilterFragment.initiativeDataHeader.get(groupPosition).selected);
 
        return convertView;
        
    } // end getGroupView
    
    /**
     * Called when a header is checked.
     * 
     * @param position  The header position selected.
     * @param checked   True is checked and false if not checked.
     */
    private void setHeaderChecked(int position, boolean checked) {
        
        headerChange = true;
        
        USAidFilterFragment.initiativeDataHeader.get(position).selected = checked;
        
        if (position == 0) {
            
             // check or uncheck all children
             int numberChild = USAidFilterFragment.initiativeDataChild.get(USAidFilterFragment.initiativeDataHeader.get(position).name).size();
             
             for (int i = 0; i < numberChild; i++) {
                 
                 USAidFilterFragment.initiativeDataChild.get(USAidFilterFragment.initiativeDataHeader.get(position).name).get(i).selected = checked;
                 
             }
             
             this.notifyDataSetChanged();
            
        }
        
        // update the filter query
        USAidMainActivity.countryQuery = USAidFilterFragment.makeFilterQuery(USAidProjectsUtility.getUrlOverview(this.inflater.getContext()));
        USAidMainActivity.countryQueryResults = null;
        
        headerChange = false;
        
    }

    /* (non-Javadoc)
     * @see android.widget.ExpandableListAdapter#hasStableIds()
     */
    @Override
    public boolean hasStableIds() {
        return true;
    }

    /* (non-Javadoc)
     * @see android.widget.ExpandableListAdapter#isChildSelectable(int, int)
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

} // end USAidInitiativesAdapter
