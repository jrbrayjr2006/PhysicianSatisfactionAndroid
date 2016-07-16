package com.saterfieldmedical.physiciansatisfaction.util;

import com.saterfieldmedical.physiciansatisfaction.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by jamesbray on 7/15/16.
 */
public class RegisterEULA {

    private Activity mContext;
    private String EULA_PREFIX = "appeula";
    private String siteCode;

    public RegisterEULA(Activity context) {
        mContext = context;
    }

    private PackageInfo getPackageInfo() {
        PackageInfo info = null;
        try {
            info = mContext.getPackageManager().getPackageInfo(
                    mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return info;
    }

    /**
     * <b>Description</b>
     * <p>
     *     Show the registration dialog and have the user enter the SITECODE value
     * </p>
     */
    public void show() {
        PackageInfo versionInfo = getPackageInfo();

        // The eulaKey changes every time you increment the version number in
        // the AndroidManifest.xml
        final String eulaKey = EULA_PREFIX + versionInfo.versionCode;
        final SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(mContext);

        boolean bAlreadyAccepted = prefs.getBoolean(eulaKey, false);
        if (bAlreadyAccepted == false) {

            // EULA title
            String title = mContext.getString(R.string.app_name) + " v"
            + versionInfo.versionName;

            // EULA text
            String message = mContext.getString(R.string.eula_string);

            // Disable orientation changes, to prevent parent activity
            // reinitialization
            mContext.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            LayoutInflater inflater = mContext.getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.dialog_register_eula,null);
            builder.setView(dialogView);

            final EditText siteCodeEditText = (EditText)dialogView.findViewById(R.id.siteCodeEditText);

            builder.setTitle(title);
            builder.setMessage(message);
            builder.setCancelable(false)
                    .setPositiveButton(R.string.accept,
                            new Dialog.OnClickListener() {

                                @Override
                                public void onClick(
                                        DialogInterface dialogInterface, int i) {
                                    // Mark this version as read.
                                    SharedPreferences.Editor editor = prefs
                                            .edit();
                                    editor.putBoolean(eulaKey, true);
                                    siteCode = siteCodeEditText.getText().toString();

                                    if((siteCode != null) && (siteCode.length() > 0)) {
                                        String key = mContext.getString(R.string.site_code);
                                        editor.putString(key, siteCode);
                                        editor.commit();

                                        // Close dialog
                                        dialogInterface.dismiss();
                                    }

                                    // Enable orientation changes based on
                                    // device's sensor
                                    mContext.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                                }
                            })
                    .setNegativeButton(android.R.string.cancel,
                            new Dialog.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // Close the activity as they have declined
                                    // the EULA
                                    mContext.finish();
                                    mContext.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                                }

                            });
            builder.create().show();
        }
    }
}
