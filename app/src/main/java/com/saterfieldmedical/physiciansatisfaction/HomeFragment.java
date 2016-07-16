package com.saterfieldmedical.physiciansatisfaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.saterfieldmedical.physiciansatisfaction.model.Survey;

/**
 * Created by jamesbray on 7/6/16.
 */
public class HomeFragment extends MedicalAbstractFragment {

    private static final String TAG = "HomeFragment";
    private Button fabButton;

    OnNextViewListener mCallback;

    public HomeFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Log.d(TAG,"ENTER:: onCreateView(LayoutInflater,ViewGroup,Bundle");
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        fabButton = (Button)v.findViewById(R.id.fab_btn);
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Survey survey = Survey.getInstance();
                //SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_APPEND);
                //String defaultValue = "no_value";
                //String key = getString(R.string.site_code);
                //String siteCode = sharedPref.getString(key,defaultValue);
                //survey.setSiteCode(siteCode);
                mCallback.onGetNextView(SatisfactionFragment.getInstance(), getResources().getString(R.string.satisfaction));
            }
        });

        Log.d(TAG,"EXIT:: onCreateView(LayoutInflater,ViewGroup,Bundle");
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mCallback =(OnNextViewListener)context;
        } catch(ClassCastException cce) {
            Log.e(TAG,cce.getMessage());
        }
    }
}
