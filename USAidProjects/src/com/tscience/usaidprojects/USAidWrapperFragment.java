/**
 * 
 */
package com.tscience.usaidprojects;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.google.android.gms.maps.GoogleMap;
import com.tscience.usaidprojects.USAidMapFragment.OnMapReadyListener;


/**
 * @author spotell at t-sciences.com
 *
 */
public class USAidWrapperFragment extends SherlockFragment implements OnMapReadyListener {

	/** Log id of this class name. */
    private static final String LOG_TAG = "USAidWrapperFragment";
    
	private GoogleMap mMap;
	
	private USAidMapFragment usaidMapFragment;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.setRetainInstance(true);
        
        Bundle bundle = this.getArguments();
        int fragmentType = bundle.getInt(USAidConstants.USAID_BUNDLE_FRAGMENT_TYPE, 1);
        
        if (fragmentType == USAidConstants.USAID_TYPE_MAP) {
            
            USAidMainActivity.mapUSAidWrapperFragment = this;

            
            // add the map fragment
        	usaidMapFragment = new USAidMapFragment();
            
            getChildFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, usaidMapFragment, USAidConstants.USAID_FRAGMENT_NAME_MAP)
                .addToBackStack(null)
                .commit();
            
            
        } else if (fragmentType == USAidConstants.USAID_TYPE_LIST) {
            
            USAidMainActivity.countryUSAidWrapperFragment = this;
            
            // add the country list fragment
            USAidCountryListFragment usaidCountryListFragment = new USAidCountryListFragment();
            
            getChildFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, usaidCountryListFragment, USAidConstants.USAID_FRAGMENT_NAME_COUNTRY)
                .addToBackStack(null)
                .commit();
            
        }
        
    } // end onCreate

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // There has to be a view with id `container` inside `wrapper.xml`
        return inflater.inflate(R.layout.usaid_fragment_container, container, false);
    }

	@Override
	public void onMapReady() {
		
		Log.d(LOG_TAG, "---------------------------------------- onMapReady");
		
		mMap = usaidMapFragment.getMap();
		
	}

} // end USAidWrapperFragment
