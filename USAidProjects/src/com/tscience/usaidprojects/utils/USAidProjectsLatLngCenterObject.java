/**
 * 
 */
package com.tscience.usaidprojects.utils;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;


/**
 * This holds the LatLng center map point.
 * 
 * @author spotell at t-sciences.com
 *
 */
public class USAidProjectsLatLngCenterObject implements Parcelable {
    
    public LatLng center;
    
    // empty constructor
    public USAidProjectsLatLngCenterObject() {}

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
        
        if (center != null) {
            // write the lat lon if available
            dest.writeDouble(center.latitude);
            dest.writeDouble(center.longitude);
            
        } else {
            // always write something here
            dest.writeInt(0);
            dest.writeInt(0);
            
        }
        
    }
    
    public static final Parcelable.Creator<USAidProjectsLatLngCenterObject> CREATOR = new Parcelable.Creator<USAidProjectsLatLngCenterObject>() {
        public USAidProjectsLatLngCenterObject createFromParcel(Parcel in) {
            return new USAidProjectsLatLngCenterObject(in);
        }
        
        public USAidProjectsLatLngCenterObject[] newArray(int size) {
            return new USAidProjectsLatLngCenterObject[size];
        }
    };
    
    /* Reconstruct the object from the Parcelable data. */
    private USAidProjectsLatLngCenterObject(Parcel in) {
        
        int tempInt = in.readInt();
        
        if (tempInt != 0) {
            
            center = new LatLng(in.readDouble(), in.readDouble());
            
        }
        
    }

} // end USAidProjectsLatLngCenterObject
