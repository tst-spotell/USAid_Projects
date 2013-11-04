/**
 * 
 */
package com.tscience.usaidprojects;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * @author spotell at t-sciences.com
 *
 */
public class USAidMapFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
        View rootView = inflater.inflate(R.layout.usaid_map_layout, container, false);
        
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    
    

} // end USAidMapFragment
