/**
 * Copyright (c) 2013 Thermopylae Sciences and Technology. All rights reserved.
 */
package com.tscience.usaidprojects.utils;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * These are the object that contains the snapshot data.
 * 
 * @author spotell at t-sciences.com
 *
 */
public class USAidProjectsSnapshotObject implements Parcelable {
	
	public int objectType;
	public String name;
	public String label;
	public String countryUrl;
	public String parentRegion;
	public int parentRegionId;
	public String countryCode;
	
	// empty constructor
    public USAidProjectsSnapshotObject() {}

	@Override
	public int describeContents() {
		return 0;
	}
	
	/**
	 * Takes the string value of the parent and gens the id as well.
	 * 
	 * @param value    The string value of the parent region.
	 */
	public void setRegionString(String value) {
	    parentRegion = value;
	    parentRegionId = USAidProjectsUtility.setParentRegion(value);
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
		dest.writeInt(objectType);
		dest.writeString(name);
		dest.writeString(label);
		dest.writeString(countryUrl);
		dest.writeString(parentRegion);
		dest.writeInt(parentRegionId);
		dest.writeString(countryCode);
		
	} // end writeToParcel
	
	public static final Parcelable.Creator<USAidProjectsSnapshotObject> CREATOR = new Parcelable.Creator<USAidProjectsSnapshotObject>() {
        public USAidProjectsSnapshotObject createFromParcel(Parcel in) {
            return new USAidProjectsSnapshotObject(in);
        }
        
        public USAidProjectsSnapshotObject[] newArray(int size) {
            return new USAidProjectsSnapshotObject[size];
        }
    };
	
	/* Reconstruct the object from the Parcelable data. */
	private USAidProjectsSnapshotObject(Parcel in) {
		
		objectType = in.readInt();
		name = in.readString();
		label = in.readString();
		countryUrl = in.readString();
		parentRegion = in.readString();
		parentRegionId = in.readInt();
		countryCode = in.readString();
		
	}

} // end USAidProjectsSnapshotObject
