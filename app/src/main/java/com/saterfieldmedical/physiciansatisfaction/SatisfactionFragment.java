package com.saterfieldmedical.physiciansatisfaction;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

/**
 * Created by jamesbray on 7/8/16.
 */
public class SatisfactionFragment extends MedicalAbstractFragment {

    private static final String TAG = "SatisfactionFragment";
    private Spinner satifactionSpinner;
    private Button satisfactionNextBtn;
    private ArrayAdapter<CharSequence> satisfactionAdapter;

    private static SatisfactionFragment satisfactionFragment;

    OnNextViewListener mCallback;

    public static SatisfactionFragment getInstance() {
        if(satisfactionFragment == null) {
            satisfactionFragment = new SatisfactionFragment();
        }
        return satisfactionFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Log.d(TAG,"ENTER:: onCreateView(LayoutInflater,ViewGroup,Bundle...");
        View v = inflater.inflate(R.layout.fragment_satisfaction, container, false);
        satifactionSpinner = (Spinner)v.findViewById(R.id.spinner);
        satisfactionAdapter = ArrayAdapter.createFromResource(getContext(), R.array.rating, android.R.layout.simple_spinner_item);
        satisfactionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        satifactionSpinner.setAdapter(satisfactionAdapter);

        satisfactionNextBtn = (Button)v.findViewById(R.id.satisfactionNextBtn);
        satisfactionNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCallback.onGetNextView(WhyFeelingThisWayFragment.getInstance(), getResources().getString(R.string.why_do_you_feel));
            }
        });

        Log.d(TAG,"EXIT:: onCreateView(LayoutInflater,ViewGroup,Bundle...");
        return v;
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        try {
            mCallback = (OnNextViewListener)context;
        } catch(ClassCastException cce) {
            Log.e(TAG, cce.getMessage());
        }
    }

}
