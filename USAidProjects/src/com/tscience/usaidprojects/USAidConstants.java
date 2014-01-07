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
    
    // button filter selected
    public static final int USAID_BUTTON_LOCATION = 0;
    public static final int USAID_BUTTON_SECTOR = 1;
    public static final int USAID_BUTTON_INITIATIVE = 2;
    public static final int USAID_BUTTON_TIME = 3;
    
    public static final String USAID_SET_DATE = "setdate";
    public static final int USAID_SET_START_DATE = 0;
    public static final int USAID_SET_END_DATE = 1;
    public static final String USAID_DASH = "-";
    
    // bundle data
    public static final String USAID_BUNDLE_COUNTRY_DATA = "country";
    public static final String USAID_BUNDLE_DATA_OBJECT = "usaid_data";
    public static final String USAID_BUNDLE_HEADER_DATA = "header";
    public static final String USAID_BUNDLE_SECTOR_DATA = "sector";
    public static final String USAID_BUNDLE_INIT_DATA = "init";
    public static final String USAID_BUNDLE_SUBINIT_DATA = "subinit";
    public static final String USAID_BUNDLE_START_DATA = "start_date";
    public static final String USAID_BUNDLE_END_DATA = "end_date";
    public static final String USAID_BUNDLE_CENTER_DATA = "center_date";
    public static final String USAID_BUNDLE_FRAGMENT_TYPE = "fragment";
    
    // fragment names
    public static final String USAID_FRAGMENT_NAME_MAP = "map";
    public static final String USAID_FRAGMENT_NAME_COUNTRY = "country";
    public static final String USAID_FRAGMENT_NAME_PROJECTS = "projects";
    
    // fragment types
    public static final int USAID_TYPE_MAP = 1;
    public static final int USAID_TYPE_LIST = 2;
    
	// regions
//	public static final String USAID_REGION_TEXT_AFPAK = "AFPAK"; 	// AFGHANISTAN & PAKISTAN
//	public static final String USAID_REGION_TEXT_EEA = "EEA";		// EUROPE & EURASIA
//	public static final String USAID_REGION_TEXT_AFR = "AFR";		// SUB SAHARAN AFRICA
//	public static final String USAID_REGION_TEXT_ASIA = "ASIA";		// ASIA
//	public static final String USAID_REGION_TEXT_LAC = "LAC";		// LATIN AMERICA & THE CARRIBEAN
//	public static final String USAID_REGION_TEXT_ME = "ME";			// MIDDLE EAST
    
    // Regional constants
//    public static final int USAID_REGION_AFPAK = 0;
//    public static final int USAID_REGION_ASIA = 1;
//    public static final int USAID_REGION_EUROPE = 2;
//    public static final int USAID_REGION_LATIN_AMERICA = 3;
//    public static final int USAID_REGION_MIDDLE_EAST = 4;
//    public static final int USAID_REGION_AFRICA = 5;
//    public static final int USAID_REGION_OTHER = 6;
    
    // type constants (for object type)
    public static final int USAID_TYPE_INITIATIVES = 0;
    public static final int USAID_TYPE_LOCATIONS = 1;
    public static final int USAID_TYPE_REGIONS = 2;
    public static final int USAID_TYPE_SECTORS = 3;
    public static final int USAID_TYPE_SUBINITIATIVES = 4;

} // end USAidConstants
