/**
 * Copyright (c) 2013 Thermopylae Sciences and Technology. All rights reserved.
 */
package com.tscience.usaidprojects;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;

/**
 * This is the fragment for displaying single project data.
 * 
 * @author spotell at t-sciences.com
 */
public class USAidProjectDialog extends DialogFragment {
	
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
		
		// TODO get the current project data from the bundle
		
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    	
    	// Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        
        // TODO
        
        Dialog d = builder.create();
        d.setCanceledOnTouchOutside(false);
        
        return d;
        
	} // end onCreateDialog
	
	

} // end USAidProjectDialog
