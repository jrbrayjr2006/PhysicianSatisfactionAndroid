package com.saterfieldmedical.physiciansatisfaction;

import android.support.v4.app.Fragment;
import android.os.Bundle;

/**
 * <p>
 *     Base fragment that other fragments inherit from
 * </p>
 * Created by jamesbray on 6/29/16.
 */
public class MedicalAbstractFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public interface OnNextViewListener {

        void onStartSurvey();

        void onGetNextView(Fragment fragment, String title);

    }
}
