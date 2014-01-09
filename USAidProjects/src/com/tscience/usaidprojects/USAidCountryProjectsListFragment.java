/**
 * Copyright (c) 2013 Thermopylae Sciences and Technology. All rights reserved.
 */
package com.tscience.usaidprojects;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.tscience.usaidprojects.io.USAidProjectsByCountryTask;
import com.tscience.usaidprojects.io.USAidProjectsDetailTask;
import com.tscience.usaidprojects.utils.USAidProjectsListObject;
import com.tscience.usaidprojects.utils.USAidProjectsObject;
import com.tscience.usaidprojects.utils.USAidProjectsUtility;

/**
 * This is the fragment for displaying projects within a country search results.
 * 
 * @author spotell at t-sciences.com
 */
public class USAidCountryProjectsListFragment extends SherlockListFragment {
    
    public static String countryName;
    
    private ArrayList<USAidProjectsListObject> serverData;
    
    private USAidCountryProjectsListAdapter usaidCountryProjectsListAdapter;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
        // layout the view
        View listView = inflater.inflate(R.layout.usaid_country_projects, container, false);
        
        return listView;
        
    } // end onCreateView

    @Override
	public void onListItemClick(ListView l, View v, int position, long id) {
    	
		// get the display data
    	USAidProjectsDetailTask usaidProjectsDetailTask = new USAidProjectsDetailTask(this);
    	usaidProjectsDetailTask.execute(USAidProjectsUtility.getUrlProjectDetail(getActivity(), serverData.get(position).projectID));
    	
		super.onListItemClick(l, v, position, id);
	}

    @Override
    public void onResume() {
        super.onResume();
        
        if (serverData == null) {
            
            // get a list of projects by country
            USAidProjectsByCountryTask usaidProjectsByCountryTask = new USAidProjectsByCountryTask(this);
            String queryString = USAidFilterFragment.makeFilterQuery(USAidProjectsUtility.getUrlProjectsByCountry(getActivity(), countryName), false);
            usaidProjectsByCountryTask.execute(queryString);
            
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
        
        // show num projects header
        if ((value == null) || (value.size() == 0)) {
            
            TextView title = (TextView) this.getView().findViewById(R.id.project_title);
            title.setVisibility(View.GONE);
            
        } else {
            
            TextView title = (TextView) this.getView().findViewById(R.id.project_title);
            title.setVisibility(View.VISIBLE);
            title.setText(value.size() + " projects in " + countryName);
            
            serverData = value;
            
            usaidCountryProjectsListAdapter = new USAidCountryProjectsListAdapter(getActivity(), R.layout.usaid_project_item, value);
            
            this.setListAdapter(usaidCountryProjectsListAdapter);
            
            usaidCountryProjectsListAdapter.notifyDataSetChanged();
            
        }
        
    } // end prepareListData
    
    public void displayProject(USAidProjectsObject value) {
    	
    	// display the dialog with data
    	// make the new bundle
    	Bundle bundle = new Bundle();
    	bundle.putParcelable(USAidConstants.USAID_BUNDLE_DATA_OBJECT, value);
    	
    	USAidProjectDialog usaidProjectDialog = USAidProjectDialog.newInstance(bundle);
    	usaidProjectDialog.show(getChildFragmentManager(), "project");
    	
    }
    
    @Override
    public void onDetach() {
        super.onDetach();

        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * This is the array adapter class used to display our custom view.
     * 
     * @author spotell at t-sciences.com
     */
    private class USAidCountryProjectsListAdapter extends ArrayAdapter<USAidProjectsListObject> {
    	
    	ArrayList<USAidProjectsListObject> items;
    	
    	private LayoutInflater inflater;
    	
    	public USAidCountryProjectsListAdapter(Context context, int resource, ArrayList<USAidProjectsListObject> objects) {
			super(context, resource, objects);
			
			items = objects;
            
            inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			View currentView = convertView;
			
			// if the view has not been created create it
            if (currentView == null) {
                
                currentView = inflater.inflate(R.layout.usaid_project_item, null);
                
                // create the tag we are using
                currentView.setTag(new USAidProjectHolder());
                
            }
            
            USAidProjectHolder usaidProjectHolder = (USAidProjectHolder) currentView.getTag();
            
            if (usaidProjectHolder.projectNameView == null) {
            	
            	usaidProjectHolder.projectNameView = (TextView) currentView.findViewById(R.id.usaid_project_item_name);
            	
            }
            
            // set the name
            usaidProjectHolder.projectNameView.setText(items.get(position).projectName);
			
			return currentView;
			
		} // end getView
    	
    } // end USAidCountryProjectsListAdapter
    
    static class USAidProjectHolder {
    	
        TextView projectNameView;
    	
    }

} // end USAidCountryProjectsListFragment
