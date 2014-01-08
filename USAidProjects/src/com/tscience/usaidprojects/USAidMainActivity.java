/**
 * Copyright (c) 2013 Thermopylae Sciences and Technology. All rights reserved.
 */
package com.tscience.usaidprojects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.tscience.usaidprojects.utils.USAidProjectsCountryObject;
import com.tscience.usaidprojects.utils.USAidProjectsLatLngCenterObject;

@SuppressLint("NewApi")
public class USAidMainActivity extends SherlockFragmentActivity implements ActionBar.TabListener {
    
    /** Log id of this class name. */
    private static final String LOG_TAG = "USAidMainActivity";

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the sections. We use a
     * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which will keep every loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    FragmentStatePagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    static ViewPager mViewPager;
    
    public static String countryQuery;
    
    public static ArrayList<USAidProjectsCountryObject> countryQueryResults;
    
    public static USAidWrapperFragment mapUSAidWrapperFragment;
    
    public static USAidWrapperFragment countryUSAidWrapperFragment;
    
    public static HashMap<String, USAidProjectsLatLngCenterObject> usaidCenterHashMap;
    
    public static int currentPage = 0;
    
    public interface OnCountryQueryUpdate {
        public void updateCountryData();
    }

    @SuppressLint({ "InlinedApi", "NewApi" })
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usaid_main);

        // Set up the action bar.
        final ActionBar actionBar = this.getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the app.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        
        mViewPager.setOffscreenPageLimit(2);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                
                currentPage = position;
                
                actionBar.setSelectedNavigationItem(position);
                Log.d(LOG_TAG, "-------------------------------------------SimpleOnPageChangeListener");
                
                if (position == 0) {
                    
                    // clear child back stacks if any
                    int count = mapUSAidWrapperFragment.getChildFragmentManager().getBackStackEntryCount();
                    
                    Log.d(LOG_TAG, "----------------------------------- map backstack count: " + count);
                    
                    if (count > 1) {
                        for (int i = 1; i < count; i++) {
                            mapUSAidWrapperFragment.getChildFragmentManager().popBackStackImmediate();
                        }
                    }
                    
                    count = countryUSAidWrapperFragment.getChildFragmentManager().getBackStackEntryCount();
                    
                    Log.d(LOG_TAG, "----------------------------------- list backstack count: " + count);
                    
                    if (count > 1) {
                        for (int i = 1; i < count; i++) {
                            countryUSAidWrapperFragment.getChildFragmentManager().popBackStackImmediate();
                        }
                    }
                    
                }
                
                // find the fragment
                else if (position == 1) {
                    
                    ((USAidMapFragment)mapUSAidWrapperFragment.getChildFragmentManager().findFragmentByTag(USAidConstants.USAID_FRAGMENT_NAME_MAP)).onResume();
                    
                } else if (position == 2) {
                    
                    ((USAidCountryListFragment)countryUSAidWrapperFragment.getChildFragmentManager().findFragmentByTag(USAidConstants.USAID_FRAGMENT_NAME_COUNTRY)).onResume();
                    
                }
                
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(actionBar.newTab().setText(mSectionsPagerAdapter.getPageTitle(i)).setTabListener(this));
        }
        
    } // end onCreate

    @Override
    public void onBackPressed() {
        
        if (mViewPager.getCurrentItem() == 1) {
            
            // TODO handle back within the stack
            int numfragments = mapUSAidWrapperFragment.getChildFragmentManager().getBackStackEntryCount();
            
            Log.d(LOG_TAG, "-------------------------------------------numfragments: " + numfragments);
            
            if (numfragments > 1) {
                mapUSAidWrapperFragment.getChildFragmentManager().popBackStackImmediate();
                Log.d(LOG_TAG, "-------------------------------------------popBackStackImmediate");
                return;
            }
            
        } else if (mViewPager.getCurrentItem() == 2) {
            
            // handle back within the stack
        	int numfragments = countryUSAidWrapperFragment.getChildFragmentManager().getBackStackEntryCount();
        	
        	Log.d(LOG_TAG, "-------------------------------------------numfragments: " + numfragments);
        	
        	if (numfragments > 1) {
        		countryUSAidWrapperFragment.getChildFragmentManager().popBackStackImmediate();
        		Log.d(LOG_TAG, "-------------------------------------------popBackStackImmediate");
        		return;
        	}
        	
        }
        
        // TODO may need to kill fragments
        
        super.onBackPressed();
    }



    @Override
    protected void onResume() {
        
        // check for Google Play services
        int checkPlayService = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        
        if (checkPlayService != ConnectionResult.SUCCESS) {
            
            // if not there or disabled display error and go get it
            GooglePlayServicesUtil.getErrorDialog(checkPlayService, this, 0);
            
        }
        
        super.onResume();
    }
    
    public static void setCountryQueryResults(ArrayList<USAidProjectsCountryObject> value) {
        
        countryQueryResults = value;
        
        // let fragment know results are available
        int currentTabId = mViewPager.getCurrentItem();
        
        Log.d(LOG_TAG, "-------------------------------------------currentTabId: " + currentTabId);
        
        try {
            // find the fragment
            if (currentTabId == 1) {
                
            	((USAidMapFragment)mapUSAidWrapperFragment.getChildFragmentManager().findFragmentByTag(USAidConstants.USAID_FRAGMENT_NAME_MAP)).updateData();
            	
            	((USAidCountryListFragment)countryUSAidWrapperFragment.getChildFragmentManager().findFragmentByTag(USAidConstants.USAID_FRAGMENT_NAME_COUNTRY)).setTheListData();
                
            } else if (currentTabId == 2) {
                
            	((USAidCountryListFragment)countryUSAidWrapperFragment.getChildFragmentManager().findFragmentByTag(USAidConstants.USAID_FRAGMENT_NAME_COUNTRY)).setTheListData();
            	
            	((USAidMapFragment)mapUSAidWrapperFragment.getChildFragmentManager().findFragmentByTag(USAidConstants.USAID_FRAGMENT_NAME_MAP)).updateData();
                
            }
        }
        catch (Exception ignore) {
            Log.e(LOG_TAG, "-------------------------------------------setCountryQueryResults: " + ignore.toString());
        }
        
    } // end setCountryQueryResults

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            
            Log.d(LOG_TAG, "-------------------------------------------getItem: " + position);
            
            switch (position) {
                
                case 0: {
                    
                    Fragment fragment = new USAidFilterFragment();
                    return fragment;
                    
                }
                
                case 1: {
                    
                    USAidWrapperFragment newUSAidWrapperFragment = new USAidWrapperFragment();
                    
                    Bundle bundle = new Bundle();
                    bundle.putInt(USAidConstants.USAID_BUNDLE_FRAGMENT_TYPE, USAidConstants.USAID_TYPE_MAP);
                    newUSAidWrapperFragment.setArguments(bundle);
                    
                    return newUSAidWrapperFragment;
                    
                }
                
                case 2: {
                    
                    USAidWrapperFragment newUSAidWrapperFragment = new USAidWrapperFragment();
                    
                    Bundle bundle = new Bundle();
                    bundle.putInt(USAidConstants.USAID_BUNDLE_FRAGMENT_TYPE, USAidConstants.USAID_TYPE_LIST);
                    newUSAidWrapperFragment.setArguments(bundle);
                    
                    return newUSAidWrapperFragment;
                    
                }
                
            }
            
            return null;

        } // end getItem

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
        
    } // end getItem

    /* (non-Javadoc)
     * @see com.actionbarsherlock.app.ActionBar.TabListener#onTabSelected(com.actionbarsherlock.app.ActionBar.Tab, android.support.v4.app.FragmentTransaction)
     */
    @Override
    public void onTabSelected(Tab tab, android.support.v4.app.FragmentTransaction ft) {
        
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
        
    }

    /* (non-Javadoc)
     * @see com.actionbarsherlock.app.ActionBar.TabListener#onTabUnselected(com.actionbarsherlock.app.ActionBar.Tab, android.support.v4.app.FragmentTransaction)
     */
    @Override
    public void onTabUnselected(Tab tab, android.support.v4.app.FragmentTransaction ft) {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see com.actionbarsherlock.app.ActionBar.TabListener#onTabReselected(com.actionbarsherlock.app.ActionBar.Tab, android.support.v4.app.FragmentTransaction)
     */
    @Override
    public void onTabReselected(Tab tab, android.support.v4.app.FragmentTransaction ft) {
        // TODO Auto-generated method stub
        
    }

}
