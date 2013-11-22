/**
 * Copyright (c) 2013 Thermopylae Sciences and Technology. All rights reserved.
 */
package com.tscience.usaidprojects.io;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import com.tscience.usaidprojects.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
    
    // dialog used to show user that actions are running
    protected ProgressDialog progressDialog;
    
    // the context we are working with
    protected Context context;
    
    // the current json object we are working with
    protected JSONObject workingData;
    
    // are we using cached data default is no
    protected boolean usingChachedData = false;
    
    // the name of the cache file we are working with
    protected String cacheFileName;
    
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        
        // show the progress dialog
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(R.string.usaid_connection_server);       
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setButton(ProgressDialog.BUTTON_NEUTRAL, context.getText(R.string.usaid_button_cancel), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                
                // cancel task
                cancelingTask();
                
            }
            
        });
        
        progressDialog.show();
        
    } // end onPreExecute

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
            
            if (newString.startsWith("(")) {
            
                // must strip off the first and last two characters -- strange wrappers
                newString = newString.substring(1, newString.length() - 2);
            
            }
            
            Log.d(LOG_TAG, "--------------------------------------------------string size " + newString.length());
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
    

    @Override
    protected void onPostExecute(JSONObject result) {
        
        // null most likely caused by connection error
        if (result == null) {
            
            Log.d(LOG_TAG, "---------------------------------- no results from server");
            
            // use cache if available
            InputStream in = null;
            
            try {
                
                in = new BufferedInputStream(context.openFileInput(cacheFileName));
                
                // string buffer to store results
                StringBuffer sb = new StringBuffer();
                
                int bufferSize = 8192;
                
                // byte array to store input
                byte[] contents = new byte[bufferSize];
                
                int numBytes = 0;
                
                while (in.available() > 0) {
                    
                    Log.d(LOG_TAG, "---------------------------------- in.available(): " + in.available());
                    
                    // read the buffer full
                    numBytes = in.read(contents, numBytes, bufferSize);
                    
                    Log.d(LOG_TAG, "---------------------------------- numBytes: " + numBytes);
                    
                    // add to the string buffer
                    sb.append(new String(contents));
                    
                    // reset
                    numBytes = 0;
                    contents = new byte[bufferSize];
                    
                }
                
                workingData = new JSONObject(sb.toString());
                
                usingChachedData = true;
                
            }
            catch (Exception ignore) {
                
                workingData = null;
                
                // turn the progress dialog off
                try {
                    progressDialog.dismiss();
                } catch (Exception ignoreAgain) {}
                
                ignore.printStackTrace();
                return;
            }
            
        } else {
            
            // was the task canceled
            if (this.isCancelled()) {
                workingData = null;
                return;
            }
            
            // cache the json object
            workingData = result;
            
            // if the file already exists delete it
            File file = new File(context.getFilesDir(), cacheFileName);
            
            if (file.length() > 0) {
                
                file.delete();
                
            }
            
            String string = result.toString();
            FileOutputStream outputStream;

            // write the json object
            try {
                outputStream = context.openFileOutput(cacheFileName, Context.MODE_PRIVATE);
                outputStream.write(string.getBytes());
                outputStream.close();
            } catch (Exception e) {
                
                // turn the progress dialog off
                try {
                    progressDialog.dismiss();
                } catch (Exception ignoreAgain) {}
                
                e.printStackTrace();
                return;
            }
            
        } // end cache data
        
    } // end onPostExecute

    // method for canceling this task
    private void cancelingTask() {
        this.cancel(true);
    }
    
} // end USAidProjectsBaseNetworkTask
