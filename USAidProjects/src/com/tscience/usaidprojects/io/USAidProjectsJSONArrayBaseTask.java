/**
 * 
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
import org.json.JSONArray;

import com.tscience.usaidprojects.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;


/**
 * @author spotell at t-sciences.com
 *
 */
public class USAidProjectsJSONArrayBaseTask extends AsyncTask<String, Void, JSONArray> {
    
    /** Log id of this class name. */
    private static final String LOG_TAG = "USAidProjectsJSONArrayBaseTask";
    
    // dialog used to show user that actions are running
    protected ProgressDialog progressDialog;
    
    // the context we are working with
    protected Context context;
    
    // the current json object we are working with
    protected JSONArray workingData;
    
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
    protected JSONArray doInBackground(String... params) {
        
        String urlString = null;
        
        try {
            
            // do we have a url to connect to
            if (params != null) {
                
                urlString = params[0];
                
            } else {
                // no values passed in we are done
                Log.d(LOG_TAG, "--------------------------------------------------no values passed in");
                return null;
            }
            
            Log.d(LOG_TAG, "--------------------------------------------------urlString " + urlString);
            
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
            JSONArray jsonArray = new JSONArray(newString);
            
            workingData = jsonArray;
            
            return jsonArray;
            
        }
        catch (Exception e) {
            
            Log.e(LOG_TAG, "------------------------------------- " + e.toString());
            
        }
        
        // if here return null
        return null;
            
    } // end doInBackground
    
    // method for canceling this task
    private void cancelingTask() {
        this.cancel(true);
    }

} // end USAidProjectsJSONArrayBaseTask
