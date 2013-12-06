/**
 * Copyright (c) 2013 Thermopylae Sciences and Technology. All rights reserved.
 */
package com.tscience.usaidprojects;

import java.util.ArrayList;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.tscience.usaidprojects.utils.USAidProjectsOverviewObject;

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
    
    public static ArrayList<USAidProjectsOverviewObject> countryQueryResults;
    
    public static USAidFilterFragment usaidFilterFragment;
    
    private static USAidMapFragment usaidMapFragment;
    
    private static USAidCountryListFragment usaidCountryListFragment;
    
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

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                
                actionBar.setSelectedNavigationItem(position);
                Log.d(LOG_TAG, "-------------------------------------------SimpleOnPageChangeListener");
                
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
    protected void onResume() {
        
        // check for Google Play services
        int checkPlayService = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        
        if (checkPlayService != ConnectionResult.SUCCESS) {
            
            // if not there or disabled display error and go get it
            GooglePlayServicesUtil.getErrorDialog(checkPlayService, this, 0);
            
        }
        
        super.onResume();
    }
    
    public static void setCountryQueryResults(ArrayList<USAidProjectsOverviewObject> value) {
        
        countryQueryResults = value;
        
        // let fragment know results are available
        int currentTabId = mViewPager.getCurrentItem();
        
        Log.d(LOG_TAG, "-------------------------------------------currentTabId: " + currentTabId);
        
        // TODO find the fragment
        if (currentTabId == 1) {
            
            usaidMapFragment.updateData();
            
        } else if (currentTabId == 2) {
            
            usaidCountryListFragment.setTheListData();
            
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
                    
                    usaidFilterFragment = new USAidFilterFragment();
                    return usaidFilterFragment;
                    
                }
                
                case 1: {
                    
                    usaidMapFragment = new USAidMapFragment();
                    return usaidMapFragment;
                    
                }
                
                case 2: {
                    
                    usaidCountryListFragment = new USAidCountryListFragment();
                    return usaidCountryListFragment;
                    
                }
                
                default: {
                    
                    // getItem is called to instantiate the fragment for the given page.
                    // Return a DummySectionFragment (defined as a static inner class
                    // below) with the page number as its lone argument.
                    Fragment fragment = new DummySectionFragment();
                    Bundle args = new Bundle();
                    args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
                    fragment.setArguments(args);
                    return fragment;   
                    
                }
            }

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
    }

    /**
     * A dummy fragment representing a section of the app, but that simply displays dummy text.
     */
    public static class DummySectionFragment extends Fragment {

        /**
         * The fragment argument representing the section number for this fragment.
         */
        public static final String ARG_SECTION_NUMBER = "section_number";

        public DummySectionFragment() {}

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_usaid_main_dummy, container, false);
            TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
            dummyTextView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

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
