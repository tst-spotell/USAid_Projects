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
public class USAidProjectsCountryObject implements Parcelable {

    public int totalProjects;
    public String countryID;
    public int countryCode;
    
    // empty constructor
    public USAidProjectsCountryObject() {}
    
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
        
        dest.writeInt(totalProjects);
        dest.writeString(countryID);
        dest.writeInt(countryCode);

    }
    
    public static final Parcelable.Creator<USAidProjectsCountryObject> CREATOR = new Parcelable.Creator<USAidProjectsCountryObject>() {
        public USAidProjectsCountryObject createFromParcel(Parcel in) {
            return new USAidProjectsCountryObject(in);
        }
        
        public USAidProjectsCountryObject[] newArray(int size) {
            return new USAidProjectsCountryObject[size];
        }
    };
    
    /* Reconstruct the object from the Parcelable data. */
    private USAidProjectsCountryObject(Parcel in) {
        
        totalProjects = in.readInt();
        countryID = in.readString();
        countryCode = in.readInt();
        
    }

} // end USAidProjectsCountryObject
