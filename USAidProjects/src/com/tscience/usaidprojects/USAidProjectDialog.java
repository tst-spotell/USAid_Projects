/**
 * Copyright (c) 2013 Thermopylae Sciences and Technology. All rights reserved.
 */
package com.tscience.usaidprojects;

import com.tscience.usaidprojects.utils.USAidProjectsCountryObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

/**
 * This is the fragment for displaying single project data.
 * 
 * @author spotell at t-sciences.com
 */
public class USAidProjectDialog extends DialogFragment {
    
    USAidProjectsCountryObject currentCountry;
	
	/**
     * Create a single instance of this dialog.
     * 
     * @param bundle	Arguments passed to the dialog.
     * 
     * @return			The dialog fragment.
     */
	public static USAidProjectDialog newInstance(Bundle bundle) {
		
		USAidProjectDialog f = new USAidProjectDialog();
		f.setArguments(bundle);
    	
    	return f;
		
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// get the current project data from the bundle
		currentCountry = (USAidProjectsCountryObject) this.getArguments().get(USAidConstants.USAID_BUNDLE_DATA_OBJECT);
		
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    	
    	// Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        
        // Pass null as the parent view because its going in the dialog layout
        View projectView = inflater.inflate(R.layout.usaid_project_view, null);
        
        builder.setView(projectView);
        
        Dialog d = builder.create();
        d.setCanceledOnTouchOutside(false);
        
        return d;
        
	} // end onCreateDialog
	
	

} // end USAidProjectDialog
