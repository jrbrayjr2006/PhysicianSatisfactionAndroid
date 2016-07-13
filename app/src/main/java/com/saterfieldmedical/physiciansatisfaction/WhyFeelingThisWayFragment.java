package com.saterfieldmedical.physiciansatisfaction;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.saterfieldmedical.physiciansatisfaction.model.Survey;

/**
 * Created by jamesbray on 7/11/16.
 */
public class WhyFeelingThisWayFragment extends MedicalAbstractFragment {

    private static final String TAG = "WhyFeelingThisWay";
    private Button whyFeelingNextBtn;
    private CheckBox hospitalAdministrationCheckBox;
    private CheckBox staffInteractionsCheckBox;
    private CheckBox communicationsCheckBox;
    private CheckBox documentationCheckBox;
    private CheckBox pharmacyCheckBox;
    private CheckBox otherCheckBox;

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
        hospitalAdministrationCheckBox = (CheckBox)v.findViewById(R.id.hospitalAdministrationCheckBox);
        staffInteractionsCheckBox = (CheckBox)v.findViewById(R.id.staffInteractionsCheckBox);
        communicationsCheckBox = (CheckBox)v.findViewById(R.id.communicationsCheckBox);
        documentationCheckBox = (CheckBox)v.findViewById(R.id.documentationCheckBox);
        pharmacyCheckBox = (CheckBox)v.findViewById(R.id.pharmacyCheckBox);
        otherCheckBox = (CheckBox)v.findViewById(R.id.otherCheckBox);
        whyFeelingNextBtn = (Button)v.findViewById(R.id.whyFeelingNextBtn);
        whyFeelingNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Survey survey = Survey.getInstance();
                survey.setWhyFeeling(generateWhyFeeling());
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

    /**
     * <p>
     *     Generate a text response based on the checkboxes selected
     * </p>
     * @return
     */
    private String generateWhyFeeling() {
        StringBuffer sb = new StringBuffer();
        if(hospitalAdministrationCheckBox.isChecked() == true) {
            if(sb.length() > 1) {
                sb.append(", ");
            }
            sb.append(getResources().getString(R.string.hospital_administration));
        }
        if(staffInteractionsCheckBox.isChecked() == true) {
            if(sb.length() > 1) {
                sb.append(", ");
            }
            sb.append(getResources().getString(R.string.staff_interactions));
        }
        if(communicationsCheckBox.isChecked() == true) {
            if(sb.length() > 1) {
                sb.append(", ");
            }
            sb.append(getResources().getString(R.string.communications));
        }
        if(documentationCheckBox.isChecked() == true) {
            if(sb.length() > 1) {
                sb.append(", ");
            }
            sb.append(getResources().getString(R.string.documentation));
        }
        if(pharmacyCheckBox.isChecked() == true) {
            if(sb.length() > 1) {
                sb.append(", ");
            }
            sb.append(getResources().getString(R.string.pharmacy));
        }
        if(otherCheckBox.isChecked() == true) {
            if(sb.length() > 1) {
                sb.append(", ");
            }
            sb.append(getResources().getString(R.string.other));
        }
        if(sb.length() < 1) {
            sb.append("N/A");
        }
        return sb.toString();
    }
}
