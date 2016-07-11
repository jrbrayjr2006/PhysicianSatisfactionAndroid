package com.saterfieldmedical.physiciansatisfaction;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements MedicalAbstractFragment.OnNextViewListener {

    private static final String TAG = "MainActivity";
    private FragmentManager fragmentManager;
    private Fragment homeFragment;
    private Fragment satisfactionFragment;
    private Fragment healthCareExperienceFragment;

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
    }

    @Override
    public void onStartSurvey() {
        Log.d(TAG,"ENTER:: onStartSurvey()...");
        //TODO conditional logic for navigation
        gotToSatifactionSurvey();
        Log.d(TAG,"EXIT:: onStartSurvey()...");
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
}
