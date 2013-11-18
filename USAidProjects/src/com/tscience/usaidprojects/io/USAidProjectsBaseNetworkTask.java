/**
 * Copyright (c) 2013 Thermopylae Sciences and Technology. All rights reserved.
 */
package com.tscience.usaidprojects.io;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;


/**
 * This is the base class that handles getting the json data from the server.
 * 
 * @author spotell at t-sciences.com
 *
 */
public class USAidProjectsBaseNetworkTask extends AsyncTask<String, Void, JSONObject> {

    /** Log id of this class name. */
    private static final String LOG_TAG = "USAidProjectsBaseNetworkTask";

    /* (non-Javadoc)
     * @see android.os.AsyncTask#doInBackground(Params[])
     */
    @Override
    protected JSONObject doInBackground(String... params) {
        
        String urlString = null;
        
        try {
            
            // do we have a url to connect to
            if (params != null) {
                
                urlString = params[0];
                
            } else {
                // no values passed in we are done
                return null;
            }
            
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(urlString);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            
            // get the response
            HttpEntity httpEntity = httpResponse.getEntity();
            InputStream is = httpEntity.getContent();
            
            // read the json string
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            
            String line = null;
            StringBuilder jsonStringBuilder = new StringBuilder();
            
            try {
                while ((line = rd.readLine()) != null) {
                    jsonStringBuilder.append(line);
                    if (this.isCancelled()) {
                        break;
                    }
                }
            }
            finally {
                // close the reader
                rd.close();
            }
            
            String newString = jsonStringBuilder.toString();
            
            Log.d(LOG_TAG, "-------------------------------------------------- " + newString);
            
            // create the json object
            JSONObject jsonObject = new JSONObject(newString);
            
            return jsonObject;
        
        }
        catch (Exception e) {
            
            Log.e(LOG_TAG, "------------------------------------- " + e.toString());
            
        }
        
        // if here return null
        return null;
        
    } // end doInBackground
    
    
} // end USAidProjectsBaseNetworkTask
