/**
 * Copyright (c) 2013 Thermopylae Sciences and Technology. All rights reserved.
 */
package com.tscience.usaidprojects.utils;

import java.net.URLEncoder;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
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
	    try {
	        sb.append(URLEncoder.encode(countryName, "UTF-8"));
	    }
	    catch (Exception ignore) {
	        return null;
	    }
	    sb.append(context.getString(R.string.usaid_server_flag));
	    
	    return sb.toString();
	    
	}
	
	/**
	 * Convenience method to get the server url for details of a project.
	 * 
	 * @param context		The context requesting the url.
	 * @param projectId		The id of the detail to retrieve.
	 * 
	 * @return	The string url to the server.
	 */
	public static String getUrlProjectDetail(Context context, String projectId) {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(context.getString(R.string.usaid_server_url));
        sb.append(context.getString(R.string.usaid_server_project_detail));
        sb.append(projectId);
		
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
    
    public static LatLng convertStringToLatLng(String value) {
        
        LatLng southWest = null;
        LatLng northEast = null;
        
        // get the values separated by comma
        int startHere = 0;
        int currentComma = 0;
        String tempString = null;
        double lat = 0;
        double lon = 0;
        
        // get southWest
        currentComma = value.indexOf(",", startHere);
        
        tempString = value.substring(startHere, currentComma).trim();
        
        lat = Double.parseDouble(tempString);
        
        startHere = currentComma + 1;
        
        currentComma = value.indexOf(",", startHere);

        tempString = value.substring(startHere, currentComma).trim();
        
        lon = Double.parseDouble(tempString);
        
        southWest = new LatLng(lat, lon);
        
        // get northEast
        startHere = currentComma + 1;
        
        currentComma = value.indexOf(",", startHere);
        
        tempString = value.substring(startHere, currentComma).trim();
        
        lat = Double.parseDouble(tempString);
        
        startHere = currentComma + 1;
        
        tempString = value.substring(startHere).trim();
        
        lon = Double.parseDouble(tempString);
        
        northEast = new LatLng(lat, lon);
        
        LatLngBounds bounds = new LatLngBounds(southWest, northEast);
        
        LatLng centerPoint = bounds.getCenter();
        
        return centerPoint;
        
    } // end convertStringToGeoPoints

    /**
     * Convenience method to format the date as we want it.
     * 
     * @param year  The year.
     * @param month The month of the year.
     * @param day   The day of the month.
     * 
     * @return  The new string value of the date.
     */
    public static String formatTheDate(int year, int month, int day) {
        
        StringBuffer dateString = new StringBuffer();
        dateString.append(year);
        dateString.append(USAidConstants.USAID_DASH);
        
        // add the extra zero
        int correctMonth = month + 1;
        if (correctMonth < 10) {
            dateString.append(0);
        }
        
        dateString.append(correctMonth);
        dateString.append(USAidConstants.USAID_DASH);
        
        // add the extra zero
        if (day < 10) {
            dateString.append(0);
        }
        
        dateString.append(day);
        
        return dateString.toString();
        
    } // end getDateString

} // end USAidProjectsUtility
