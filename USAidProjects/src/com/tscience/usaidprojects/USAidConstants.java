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
    public static final String USAID_BUNDLE_QUERY_STRING = "query_string";
    public static final String USAID_BUNDLE_QUERY_RESULTS = "query_results";
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
    
    // type constants (for object type)
    public static final int USAID_TYPE_INITIATIVES = 0;
    public static final int USAID_TYPE_LOCATIONS = 1;
    public static final int USAID_TYPE_REGIONS = 2;
    public static final int USAID_TYPE_SECTORS = 3;
    public static final int USAID_TYPE_SUBINITIATIVES = 4;

} // end USAidConstants
