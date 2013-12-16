/**
 * Copyright (c) 2013 Thermopylae Sciences and Technology. All rights reserved.
 */
package com.tscience.usaidprojects.utils;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Used to display a list of projects for a country.
 * 
 * @author spotell at t-sciences.com
 */
public class USAidProjectsListObject implements Parcelable {
    
    public String projectName;
    public String projectID;
    
    /** Empty public constructor. */
    public USAidProjectsListObject() {}

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
        
        dest.writeString(projectName);
        dest.writeString(projectID);
        
    }
    
    public static final Parcelable.Creator<USAidProjectsListObject> CREATOR = new Parcelable.Creator<USAidProjectsListObject>() {
        public USAidProjectsListObject createFromParcel(Parcel in) {
            return new USAidProjectsListObject(in);
        }
        
        public USAidProjectsListObject[] newArray(int size) {
            return new USAidProjectsListObject[size];
        }
    };
    
    /* Reconstruct the object from the Parcelable data. */
    private USAidProjectsListObject(Parcel in) {
        
        projectName = in.readString();
        projectID = in.readString();
        
    }

} // end USAidProjectsListObject
