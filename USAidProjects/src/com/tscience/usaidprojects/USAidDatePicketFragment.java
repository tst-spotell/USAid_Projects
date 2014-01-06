/**
 * Copyright (c) 2014 Thermopylae Sciences and Technology. All rights reserved.
 */
package com.tscience.usaidprojects;

import java.util.Calendar;
import java.util.TimeZone;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.DatePicker;

import com.actionbarsherlock.app.SherlockDialogFragment;


/**
 * This allows a user to select a date.
 * 
 * @author spotell at t-sciences.com
 */
public class USAidDatePicketFragment extends SherlockDialogFragment implements DatePickerDialog.OnDateSetListener {
    
    int dateToSet = 0;
    
    boolean wasCanceled = true;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        
        dateToSet = args.getInt(USAidConstants.USAID_SET_DATE);
        
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        
        return new USAidDatePickerDialog(getActivity(), this, year, month, day);
    }

    /* (non-Javadoc)
     * @see android.app.DatePickerDialog.OnDateSetListener#onDateSet(android.widget.DatePicker, int, int, int)
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        
        if (!wasCanceled) {
            
            ((USAidFilterFragment) getTargetFragment()).onUSAidDateSelected(year, month, day, dateToSet);
            
        }
        
    }
    
    /**
     * It was necessary to create our own class to handle back press cancel that was loading date.
     * 
     * @author spotell at t-sciences.com
     */
    private class USAidDatePickerDialog  extends DatePickerDialog {
        
        public USAidDatePickerDialog(Context context, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
            super(context, callBack, year, monthOfYear, dayOfMonth);
            
            this.setCanceledOnTouchOutside(false);
            
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            
            // the user pressed the done button so save the time change
            wasCanceled = false;
            
            super.onClick(dialog, which);
        }
        
    } // end USAidDatePickerDialog

} // end USAidDatePicketFragment
