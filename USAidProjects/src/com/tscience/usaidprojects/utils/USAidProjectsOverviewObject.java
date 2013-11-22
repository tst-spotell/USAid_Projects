/**
 * Copyright (c) 2013 Thermopylae Sciences and Technology. All rights reserved.
 */
package com.tscience.usaidprojects.utils;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * These objects contain the overview data for country project count.
 * 
 * @author spotell at t-sciences.com
 *
 */
public class USAidProjectsOverviewObject implements Parcelable {

    public int totalProjects;
    public String countryID;
    public int countryCode;
    
    // empty constructor
    public USAidProjectsOverviewObject() {}
    
    /* (non-Javadoc)
     * @see android.os.Parcelable#describeContents()
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /* (non-Javadoc)
     * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // TODO Auto-generated method stub

    }

} // end USAidProjectsOverviewObject
