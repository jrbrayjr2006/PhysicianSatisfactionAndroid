package com.saterfieldmedical.physiciansatisfaction;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.saterfieldmedical.physiciansatisfaction.model.Survey;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements MedicalAbstractFragment.OnNextViewListener {

    private static final String TAG = "MainActivity";
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
    }

    @Override
    public void onSubmitSurvey() {
        Log.d(TAG,"ENTER:: onSubmitSurvey()...");
        //TODO add logic to submit JSON
        convertToJson();
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

    private JSONObject convertToJson() {
        JSONObject json = new JSONObject();

        try{
            json.put("rating", survey.getRating());
            json.put("whyfeeling", survey.getWhyFeeling());
            json.put("response", survey.getResponse());

            Log.d(TAG, json.toString());
        } catch(JSONException jsone) {
            Log.e(TAG, "Error occurred while creating JSON..." + jsone.getMessage());
        }
        return json;
    }

    class SendDataToServer extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... params) {
            return null;
        }
    }
}
