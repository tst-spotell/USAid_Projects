/**
 * Copyright (c) 2013 Thermopylae Sciences and Technology. All rights reserved.
 */
package com.tscience.usaidprojects.utils;

import android.content.Context;
import android.util.Log;

import com.tscience.usaidprojects.R;
import com.tscience.usaidprojects.USAidConstants;

/**
 * These are the utility classes used in the application.
 * 
 * @author spotell at t-sciences.com
 *
 */
public class USAidProjectsUtility {
    
    /** Log id of this class name. */
    private static final String LOG_TAG = "USAidProjectsUtility";

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
	 * @param context  	The context requesting the url.
	 * @param value		The filter string.
	 * 
	 * @return The string url to the server.
	 */
	public static String getUrlSnapshot(Context context, String value) {
	    
	    StringBuffer sb = new StringBuffer();
	    sb.append(context.getString(R.string.usaid_server_url));
	    sb.append(context.getString(R.string.usaid_server_snapshot));
	    
	    if (value != null) {
	    	sb.append(value);
	    }
	    
	    return sb.toString();
	    
	} // end getUrlSnapshot
	
	/**
	 * Convenience method to get the server url for overview.
	 *  
	 * @param context   The context requesting the url.
	 * @param value		The filter string.
	 * 
	 * @return The string url to the server.
	 */
	public static String getUrlOverview(Context context, String value) {
	    
	    StringBuffer sb = new StringBuffer();
	    sb.append(context.getString(R.string.usaid_server_url));
	    sb.append(context.getString(R.string.usaid_server_overview));
	    
	    if (value != null) {
	    	sb.append(value);
	    }
	    
	    return sb.toString();
	    
	} // end getUrlSnapshot
	
	/**
	 * Convenience method to get the server url for projects by country.
	 * 
	 * @param context      The context requesting the url.
	 * @param countryName  The name of the country to get the projects for.
	 * 
	 * @return The string url to the server.
	 */
	public static String getUrlProjectsByCountry(Context context, String countryName) {
	    
	    StringBuffer sb = new StringBuffer();
	    
	    sb.append(context.getString(R.string.usaid_server_url));
	    sb.append(context.getString(R.string.usaid_server_projects));
	    sb.append(context.getString(R.string.usaid_server_country_start));
	    sb.append(countryName);
	    sb.append(context.getString(R.string.usaid_server_flag));
	    
	    return sb.toString();
	    
	}
	
	/**
     * This replaces the space with %20 for search purposes.
     * 
     * @param value The string to convert.
     * @return      The converted string.
     */
    public static String convertName(String value) {
        
        StringBuffer sb = new StringBuffer(value.length());
        
        int len = value.length();
        
        char c;
        
        for (int i = 0; i < len; i++) {
            
            c = value.charAt(i);
            
            if (c == ' ') {
                sb.append("%20");
            } else {
                sb.append(c);
            }
            
        }
        
        return sb.toString();
        
    } // end convertName

} // end USAidProjectsUtility
