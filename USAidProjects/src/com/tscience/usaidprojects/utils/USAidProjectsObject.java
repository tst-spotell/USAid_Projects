/**
 * Copyright (c) 2013 Thermopylae Sciences and Technology. All rights reserved.
 */
package com.tscience.usaidprojects.utils;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * These are the object that contains the project data.
 * 
 * @author spotell at t-sciences.com
 *
 */
public class USAidProjectsObject implements Parcelable {
    
    public String projectName;
    
    public String startDate;
    public String stopDate;
    
    public String description;
    
    public String awardAmount;
    public String partner;
    
    public USAidProjectsObject() {}

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
        dest.writeString(startDate);
        dest.writeString(stopDate);
        dest.writeString(description);
        dest.writeString(awardAmount);
        dest.writeString(partner);
        
    }
    
    public static final Parcelable.Creator<USAidProjectsObject> CREATOR = new Parcelable.Creator<USAidProjectsObject>() {
        public USAidProjectsObject createFromParcel(Parcel in) {
            return new USAidProjectsObject(in);
        }
        
        public USAidProjectsObject[] newArray(int size) {
            return new USAidProjectsObject[size];
        }
    };
    
    /* Reconstruct the object from the Parcelable data. */
    private USAidProjectsObject(Parcel in) {
        
        projectName = in.readString();
        startDate = in.readString();
        stopDate = in.readString();
        description = in.readString();
        awardAmount = in.readString();
        partner = in.readString();
        
    }
    

} // end USAidProjectsObject
