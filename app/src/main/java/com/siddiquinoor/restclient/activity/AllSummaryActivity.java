package com.siddiquinoor.restclient.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.siddiquinoor.restclient.R;
import com.siddiquinoor.restclient.activity.sub_activity.summary_sub.GroupSummary;
import com.siddiquinoor.restclient.activity.sub_activity.summary_sub.ServiceSummaryMenu;
import com.siddiquinoor.restclient.fragments.BaseActivity;
import com.siddiquinoor.restclient.activity.sub_activity.summary_sub.Summary;
import com.siddiquinoor.restclient.activity.sub_activity.summary_sub.SummaryAssignCriteria;
import com.siddiquinoor.restclient.manager.SQLiteHandler;
import com.siddiquinoor.restclient.utils.CalculationPadding;
import com.siddiquinoor.restclient.utils.KEY;
import com.siddiquinoor.restclient.utils.UtilClass;
import com.siddiquinoor.restclient.views.notifications.ADNotificationManager;

/**
 * This is Summary Option Page
 */


public class AllSummaryActivity extends BaseActivity {
    private RadioButton rbHouseHold, rbService, rbDistribution;
    private Button btnGo, btnHome;
    private String idCountry;
    private RadioButton rbAssign;
    private RadioButton rbtGroup;

    private ADNotificationManager dialog;
    private final Context mContext = AllSummaryActivity.this;
    private SQLiteHandler sqlH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_all_summary);

        initial();
        //Intent innt = getIntent();
        idCountry = sqlH.getSelectedCountryCode();//innt.getStringExtra(KEY.COUNTRY_ID);

        viewAccessController();
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
////                if(rbtGroup.isChecked()){
//               // Toast.makeText(mContext,"Hun marbo",Toast.LENGTH_SHORT).show();
//                Intent grpIntent = new Intent(AllSummaryActivity.this, GroupSummary.class);
//                finish();
//                startActivity(grpIntent);
//                }

                //Intent intent = null;
                if (rbHouseHold.isChecked()) {
                    Intent iRegSum = new Intent(AllSummaryActivity.this, Summary.class);
                    iRegSum.putExtra(KEY.COUNTRY_ID, idCountry);
                    finish();
                    startActivity(iRegSum);
                } else if (rbService.isChecked()) {
                    Intent iSrvSum = new Intent(AllSummaryActivity.this, ServiceSummaryMenu.class);
                    iSrvSum.putExtra(KEY.COUNTRY_ID, idCountry);
                    iSrvSum.putExtra(KEY.FLAG, KEY.SRV_FLAG);
                    iSrvSum.putExtra(KEY.DIR_CLASS_NAME_KEY, "AllSummaryActivity");
                    finish();
                    startActivity(iSrvSum);
                } else if (rbAssign.isChecked()) {
                    Intent iAsgSum = new Intent(AllSummaryActivity.this, SummaryAssignCriteria.class);
                    iAsgSum.putExtra(KEY.COUNTRY_ID, idCountry);
                    finish();
                    startActivity(iAsgSum);
                } else if (rbDistribution.isChecked()) {
                   Intent iDistSum= new Intent(AllSummaryActivity.this, ServiceSummaryMenu.class);
                    iDistSum.putExtra(KEY.COUNTRY_ID, idCountry);
                    iDistSum.putExtra(KEY.FLAG, KEY.DIST_FLAG);
                    iDistSum.putExtra(KEY.DIR_CLASS_NAME_KEY, "AllSummaryActivity");
                    finish();
                    startActivity(iDistSum);
                } else if (rbtGroup.isChecked()) {
                    Intent iGrpSum= new Intent(AllSummaryActivity.this, GroupSummary.class);
                    finish();
                    startActivity(iGrpSum);
                    //   intent.putExtra(KEY.COUNTRY_ID, idCountry);
                } else
                    dialog.showErrorDialog(mContext, "No Menu is selected yet");


                /*if (intent != null) {

                    startActivity(intent);
                }*/


            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent iHome = new Intent(mContext, MainActivity.class);
                startActivity(iHome);
            }
        });
    }

    private void initial() {
        reference();
        dialog = new ADNotificationManager();
        sqlH = new SQLiteHandler(mContext);
        rbDistribution.setEnabled(false);
        rbHouseHold.setEnabled(false);
        rbService.setEnabled(false);
        rbAssign.setEnabled(false);
        rbtGroup.setEnabled(false);
    }


    private void viewAccessController() {

        SharedPreferences settings;

        settings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        int operationMode = settings.getInt(UtilClass.OPERATION_MODE, 0);
//        Log.d("NIR1", "operation mode : " + operationMode);
        switch (operationMode) {
            case UtilClass.REGISTRATION_OPERATION_MODE:
                rbHouseHold.setEnabled(true);
                rbAssign.setEnabled(true);
                rbtGroup.setEnabled(true);

                break;
            case UtilClass.DISTRIBUTION_OPERATION_MODE:
                rbDistribution.setEnabled(true);
                break;
            case UtilClass.SERVICE_OPERATION_MODE:
                rbService.setEnabled(true);

                break;
        }
    }

    private void reference() {
        rbHouseHold = (RadioButton) findViewById(R.id.rbtn_household_samm);
        rbService = (RadioButton) findViewById(R.id.rbtn_service_samm);
        rbDistribution = (RadioButton) findViewById(R.id.rbtn_distribution_samm);
        rbAssign = (RadioButton) findViewById(R.id.rbtn_Assign_summary);
        rbtGroup = (RadioButton) findViewById(R.id.rbtn_Group_summary);

        /**
         * view swipt views
         *
         */

        btnHome = (Button) findViewById(R.id.btnRegisterFooter);
        btnGo = (Button) findViewById(R.id.btnHomeFooter);


    }

    /**
     * calling getWidth() and getHeight() too early:
     * When  the UI has not been sized and laid out on the screen yet..
     *
     * @param hasFocus the value will be true when UI is focus
     */

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        setUpHomeButton();
        setUpGoButton();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setUpGoButton() {
        btnGo.setText("");
        Drawable imageGoto = getResources().getDrawable(R.drawable.goto_forward);
        btnGo.setCompoundDrawablesRelativeWithIntrinsicBounds(imageGoto, null, null, null);
        //  int leftPadd, rightPadd,topPadd,bottomPadd;
        //  CalculationPadding calPadd = new CalculationPadding();

        //  leftPadd = rightPadd = calPadd.calculateViewPadding(mContext, imageGoto, btnGo);
        /**
         * set the value in resource
         */
        // topPadd=bottomPadd=getResources().getInteger(R.integer.top_bottom_icon_pad_int_5);
        // btnGo.setPadding(leftPadd,topPadd , rightPadd, bottomPadd);

        setPaddingButton(mContext, imageGoto, btnGo);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setUpHomeButton() {

        btnHome.setText("");
        Drawable imageHome = getResources().getDrawable(R.drawable.home_b);
        btnHome.setCompoundDrawablesRelativeWithIntrinsicBounds(imageHome, null, null, null);
        // int leftPadd, rightPadd,topPadd,bottomPadd;
        // CalculationPadding calPadd = new CalculationPadding();

        //  leftPadd = rightPadd = calPadd.calculateViewPadding(mContext, imageHome, btnHome);
        /**
         * set the value in resource
         */
        //  topPadd=bottomPadd=getResources().getInteger(R.integer.top_bottom_icon_pad_int_5);

        //  btnHome.setPadding(leftPadd, topPadd, rightPadd, bottomPadd);
        setPaddingButton(mContext, imageHome, btnHome);
    }

   /* @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // go button
            case R.id.btnHomeFooter:
                Intent intent = null;
                if (rbHouseHold.isChecked()) {
                    intent = new Intent(AllSummaryActivity.this, Summary.class);
                    intent.putExtra(KEY.COUNTRY_ID, idCountry);
                } else if (rbService.isChecked()) {
                    intent = new Intent(AllSummaryActivity.this, ServiceSummaryMenu.class);
                    intent.putExtra(KEY.COUNTRY_ID, idCountry);
                    intent.putExtra(KEY.FLAG, KEY.SRV_FLAG);
                    intent.putExtra(KEY.DIR_CLASS_NAME_KEY, "AllSummaryActivity");
                } else if (rbAssign.isChecked()) {
                    intent = new Intent(AllSummaryActivity.this, SummaryAssignCriteria.class);
                    intent.putExtra(KEY.COUNTRY_ID, idCountry);
                } else if (rbDistribution.isChecked()) {
                    intent = new Intent(AllSummaryActivity.this, ServiceSummaryMenu.class);
                    intent.putExtra(KEY.COUNTRY_ID, idCountry);
                    intent.putExtra(KEY.FLAG, KEY.DIST_FLAG);
                    intent.putExtra(KEY.DIR_CLASS_NAME_KEY, "AllSummaryActivity");
                }else if(rbtGroup.isChecked()){
                  Intent   grpIntent = new Intent(AllSummaryActivity.this, GroupSummary.class);
                    startActivity(grpIntent);
                 //   intent.putExtra(KEY.COUNTRY_ID, idCountry);
                } else
                    dialog.showErrorDialog(mContext, "No Menu is selected yet");


//                if (intent != null) {
//
//                    startActivity(intent);
//                }


                break;
            // home button
            case R.id.btnRegisterFooter:
                finish();
                Intent iHome = new Intent(mContext, MainActivity.class);
                startActivity(iHome);

                break;
        }
    }*/


  /*  @Override
    public void onBackPressed() {
       *//* Intent noBackpressWork=new Intent(AllSummaryActivity.this,AllSummaryActivity.class);
        startActivity(noBackpressWork);*//*
    }*/
}
