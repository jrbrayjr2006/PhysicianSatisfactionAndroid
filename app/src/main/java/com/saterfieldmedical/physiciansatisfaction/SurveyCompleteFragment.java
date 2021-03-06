package com.saterfieldmedical.physiciansatisfaction;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by jamesbray on 7/11/16.
 */
public class SurveyCompleteFragment extends MedicalAbstractFragment {

    private static final String TAG = "SurveyCompleteFragment";
    private Button submitSurveyBtn;

    OnNextViewListener mCallback;

    private static SurveyCompleteFragment surveyCompleteFragment;

    public static SurveyCompleteFragment getInstance() {
        if(surveyCompleteFragment == null) {
            surveyCompleteFragment = new SurveyCompleteFragment();
        }
        return surveyCompleteFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_survey_complete, container, false);
        submitSurveyBtn = (Button)v.findViewById(R.id.submitSurveyBtn);
        submitSurveyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onSubmitSurvey();
            }
        });

        //TODO

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
