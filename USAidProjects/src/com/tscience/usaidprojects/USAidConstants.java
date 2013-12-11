/**
 * Copyright (c) 2013 Thermopylae Sciences and Technology. All rights reserved.
 */
package com.tscience.usaidprojects;


/**
 * These are the constants used in the application.
 * 
 * @author spotell at t-sciences.com
 *
 */
public class USAidConstants {
    
    // bundle data
    public static final String USAID_BUNDLE_DATA_OBJECT = "usaid_data";
    public static final String USAID_BUNDLE_HEADER_DATA = "header";
    public static final String USAID_BUNDLE_SECTOR_DATA = "sector";
    
	// regions
	public static final String USAID_REGION_TEXT_AFPAK = "AFPAK"; 	// AFGHANISTAN & PAKISTAN
	public static final String USAID_REGION_TEXT_EEA = "EEA";		// EUROPE & EURASIA
	public static final String USAID_REGION_TEXT_AFR = "AFR";		// SUB SAHARAN AFRICA
	public static final String USAID_REGION_TEXT_ASIA = "ASIA";		// ASIA
	public static final String USAID_REGION_TEXT_LAC = "LAC";		// LATIN AMERICA & THE CARRIBEAN
	public static final String USAID_REGION_TEXT_ME = "ME";			// MIDDLE EAST
    
    // Regional constants
    public static final int USAID_REGION_AFPAK = 0;
    public static final int USAID_REGION_ASIA = 1;
    public static final int USAID_REGION_EUROPE = 2;
    public static final int USAID_REGION_LATIN_AMERICA = 3;
    public static final int USAID_REGION_MIDDLE_EAST = 4;
    public static final int USAID_REGION_AFRICA = 5;
    public static final int USAID_REGION_OTHER = 6;
    
    
    // type constants (for object type)
    public static final int USAID_TYPE_INITIATIVES = 0;
    public static final int USAID_TYPE_LOCATIONS = 1;
    public static final int USAID_TYPE_REGIONS = 2;
    public static final int USAID_TYPE_SECTORS = 3;
    public static final int USAID_TYPE_SUBINITIATIVES = 4;

} // end USAidConstants
