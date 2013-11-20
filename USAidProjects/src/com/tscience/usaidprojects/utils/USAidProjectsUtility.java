/**
 * Copyright (c) 2013 Thermopylae Sciences and Technology. All rights reserved.
 */
package com.tscience.usaidprojects.utils;

import android.content.Context;

import com.tscience.usaidprojects.R;
import com.tscience.usaidprojects.USAidConstants;

/**
 * These are the utility classes used in the application.
 * 
 * @author spotell at t-sciences.com
 *
 */
public class USAidProjectsUtility {

	/**
	 * This class takes a parent region string and returns a parent region id.
	 * 
	 * @param value	The string value of the parent region.
	 * 
	 * @return	The ID value for this parent region.
	 */
	public static int setParentRegion (String value) {
	    
	    if (value == null) {
	        return USAidConstants.USAID_REGION_OTHER;
	    }
	    
	    if (value.equalsIgnoreCase(USAidConstants.USAID_REGION_TEXT_AFPAK)) {
	        return USAidConstants.USAID_REGION_AFPAK;
	    } else if (value.equalsIgnoreCase(USAidConstants.USAID_REGION_TEXT_EEA)) {
            return USAidConstants.USAID_REGION_EUROPE;
        } else if (value.equalsIgnoreCase(USAidConstants.USAID_REGION_TEXT_AFR)) {
            return USAidConstants.USAID_REGION_AFRICA;
        } else if (value.equalsIgnoreCase(USAidConstants.USAID_REGION_TEXT_ASIA)) {
            return USAidConstants.USAID_REGION_ASIA;
        } else if (value.equalsIgnoreCase(USAidConstants.USAID_REGION_TEXT_LAC)) {
            return USAidConstants.USAID_REGION_LATIN_AMERICA;
        } else if (value.equalsIgnoreCase(USAidConstants.USAID_REGION_TEXT_ME)) {
            return USAidConstants.USAID_REGION_MIDDLE_EAST;
        }
		
	    // not defined
		return USAidConstants.USAID_REGION_OTHER;
		
	} // end setParentRegion
	
	/**
	 * Convenience method to get the server url for snapshot.
	 *  
	 * @param context  The context requesting the url.
	 * 
	 * @return The string url to the server.
	 */
	public static String getUrlSnapshot(Context context) {
	    
	    StringBuffer sb = new StringBuffer();
	    sb.append(context.getString(R.string.usaid_server_url));
	    sb.append(context.getString(R.string.usaid_server_snapshot));
	    
	    return sb.toString();
	    
	} // end getUrlSnapshot

} // end USAidProjectsUtility
