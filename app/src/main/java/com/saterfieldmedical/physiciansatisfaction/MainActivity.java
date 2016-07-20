package com.saterfieldmedical.physiciansatisfaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.saterfieldmedical.physiciansatisfaction.model.Survey;
import com.saterfieldmedical.physiciansatisfaction.util.RegisterEULA;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity implements MedicalAbstractFragment.OnNextViewListener {

    private static final String TAG = "MainActivity";
    private static final String BASE_URL = "http://www.strawberry23.net:8080/satterfieldmedical/insert/";

    private FragmentManager fragmentManager;
    private Fragment homeFragment;
    private Fragment satisfactionFragment;
    protected Survey survey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        homeFragment = fragmentManager.findFragmentById(R.id.homeFragment);
        if(homeFragment == null) {
            homeFragment = new HomeFragment();
            transaction.add(R.id.fragment_container, homeFragment).commit();
        }
        survey = Survey.getInstance();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);  //getPreferences(Context.MODE_PRIVATE);
        String defaultValue = "no_value";
        String siteCode = sharedPref.getString(getString(R.string.site_code),defaultValue);
        if(siteCode.equals(defaultValue)) {
            new RegisterEULA(this).show();
        }
    }

    @Override
    public void onSubmitSurvey() {
        Log.d(TAG,"ENTER:: onSubmitSurvey()...");
        convertToJson();
        SendDataToServerTask task = new SendDataToServerTask();
        String url = generateUrl(survey);
        Log.d(TAG, "The url is:  " + url);
        task.execute(url);
        Log.d(TAG,"EXIT:: onSubmitSurvey()...");
        this.finish();
    }

    /**
     * <p>
     *     Implementation of screen navigation
     * </p>
     * @param fragment
     */
    @Override
    public void onGetNextView(Fragment fragment, String title) {
        Log.d(TAG,"ENTER:: onGetNextView()...");
        //TODO conditional logic for navigation
        String defaultValue = "no_value";
        String key = getString(R.string.site_code);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String siteCode = sharedPref.getString(key,defaultValue);
        survey.setSiteCode(siteCode);
        Log.d(TAG, "The site code is:  " + survey.getSiteCode());
        Log.d(TAG, "The rating is:  " + survey.getRating());
        Log.d(TAG, "Why feeling is:  " + survey.getWhyFeeling());
        Log.d(TAG, "Response is:  " + survey.getResponse());
        goToNextScreenView(fragment, title);
        Log.d(TAG,"EXIT:: onGetNextView()...");
    }

    private void gotToSatifactionSurvey() {
        if(fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
        }
        satisfactionFragment = fragmentManager.findFragmentById(R.id.satisfactionFragment);
        if(satisfactionFragment == null) {
            satisfactionFragment = new SatisfactionFragment();
        }
        fragmentManager.beginTransaction().replace(R.id.fragment_container,satisfactionFragment).commit();
    }

    private void goToNextScreenView(Fragment fragment, String title) {
        if(fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
        }

        if(fragment == null) {
            Log.e(TAG, "The requested fragment is null!");
            return;
        }

        setTitle(title);
        fragmentManager.beginTransaction().replace(R.id.fragment_container,fragment).commit();
    }

    private void logout() {
        this.finish();
    }

    /**
     * <p>
     *     <code>http://localhost:8080/satterfieldmedical/insert/DEMO/5/staff/something/</code>
     * </p>
     * @param s
     * @return
     */
    private String generateUrl(Survey s) {
        StringBuffer url = new StringBuffer();
        url.append(BASE_URL);
        url.append(survey.getSiteCode()).append("/");
        url.append(survey.getRating()).append("/");
        try {
            url.append(URLEncoder.encode(survey.getWhyFeeling(), "UTF-8")).append("/");
            url.append(URLEncoder.encode(survey.getResponse(), "UTF-8")).append("/");  //this is the comment
        } catch(UnsupportedEncodingException usee) {
            Log.e(TAG, usee.getMessage());
        }

        return url.toString();
    }

    private JSONObject convertToJson() {
        JSONObject json = new JSONObject();

        try{
            json.put("rating", survey.getRating());
            json.put("whyfeeling", survey.getWhyFeeling());
            json.put("response", survey.getResponse());
            json.put("sitecode", survey.getSiteCode());

            Log.d(TAG, json.toString());
        } catch(JSONException jsone) {
            Log.e(TAG, "Error occurred while creating JSON..." + jsone.getMessage());
        }
        return json;
    }

    class SendDataToServerTask extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... params) {
            String result = postSurveyResponse(params[0]);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
        }


        private String postSurveyResponse(String mUrl) {
            InputStream is = null;
            int length = 500;

            try {
                URL url = new URL(mUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.connect();
                int responseCode = conn.getResponseCode();
                Log.d(TAG, "The response is:  " + responseCode);
                is = conn.getInputStream();
                String result = convertInputToString(is, length);
                return result;

            } catch(MalformedURLException mfue) {
                Log.e(TAG, mfue.getMessage());
            } catch(IOException ioe) {
                Log.e(TAG, ioe.getMessage());
            } finally {
                if(is != null) {
                    try {
                        is.close();
                    } catch(IOException ioe) {
                        //do nothing
                    }
                }
            }
            return null;
        }

        public String convertInputToString(InputStream stream, int length) {
            Reader reader = null;
            char[] buffer = new char[length];
            try {
                reader = new InputStreamReader(stream, "UTF-8");
                reader.read(buffer);
            } catch(UnsupportedEncodingException usee) {
                Log.e(TAG, usee.getMessage());
            } catch(IOException ioe) {
                Log.e(TAG, ioe.getMessage());
            }
            return new String(buffer);
        }
    }
}
