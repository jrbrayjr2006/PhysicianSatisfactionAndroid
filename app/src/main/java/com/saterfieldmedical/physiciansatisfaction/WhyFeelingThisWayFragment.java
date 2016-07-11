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
public class WhyFeelingThisWayFragment extends MedicalAbstractFragment {

    private static final String TAG = "WhyFeelingThisWay";
    private Button whyFeelingNextBtn;

    OnNextViewListener mCallback;

    private static WhyFeelingThisWayFragment whyFeelingFragment;

    public static WhyFeelingThisWayFragment getInstance() {
        if(whyFeelingFragment == null) {
            whyFeelingFragment = new WhyFeelingThisWayFragment();
        }
        return whyFeelingFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_why_feeling, container, false);
        whyFeelingNextBtn = (Button)v.findViewById(R.id.whyFeelingNextBtn);
        whyFeelingNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onGetNextView(ResponseFragement.getInstance(), getResources().getString(R.string.response));
            }
        });
        //TODO

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
