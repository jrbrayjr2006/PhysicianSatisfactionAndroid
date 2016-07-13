package com.saterfieldmedical.physiciansatisfaction;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.saterfieldmedical.physiciansatisfaction.model.Survey;

/**
 * Created by jamesbray on 7/11/16.
 */
public class ResponseFragement extends MedicalAbstractFragment {

    private static final String TAG = "ResponseFragement";
    private Button responseNextBtn;
    private EditText responseEditText;

    OnNextViewListener mCallback;

    private static ResponseFragement responseFragement;

    public static ResponseFragement getInstance() {
        if(responseFragement == null) {
            responseFragement = new ResponseFragement();
        }
        return responseFragement;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_response, container, false);
        responseEditText = (EditText)v.findViewById(R.id.responseEditText);
        responseNextBtn = (Button)v.findViewById(R.id.responseNextBtn);
        responseNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Survey survey = Survey.getInstance();
                survey.setResponse(responseEditText.getText().toString());
                mCallback.onGetNextView(SurveyCompleteFragment.getInstance(), getResources().getString(R.string.survey_completed));
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
