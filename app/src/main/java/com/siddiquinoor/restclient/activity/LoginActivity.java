package com.siddiquinoor.restclient.activity;

/**
 * Activity for login validation and collect existing data from online
 *
 * @author Siddiqui Noor
 * @desc Technical Director, TechnoDhaka.
 * @link www.SiddiquiNoor.com
 * @version 1.3.0
 * @since 1.0
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.siddiquinoor.restclient.R;
import com.siddiquinoor.restclient.controller.AppConfig;
import com.siddiquinoor.restclient.controller.AppController;
import com.siddiquinoor.restclient.data_model.AdmCountryDataModel;
import com.siddiquinoor.restclient.data_model.FDPItem;
import com.siddiquinoor.restclient.data_model.ProgramMasterDM;
import com.siddiquinoor.restclient.data_model.ServiceCenterItem;
import com.siddiquinoor.restclient.data_model.TemOpMonth;
import com.siddiquinoor.restclient.data_model.VillageItem;
import com.siddiquinoor.restclient.fragments.BaseActivity;
import com.siddiquinoor.restclient.manager.SQLiteHandler;
import com.siddiquinoor.restclient.network.ConnectionDetector;
import com.siddiquinoor.restclient.parse.Parser;
import com.siddiquinoor.restclient.utils.UtilClass;
import com.siddiquinoor.restclient.views.notifications.AlertDialogManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import java.util.logging.Handler;


public class LoginActivity extends BaseActivity {
    // LogCat tag
    private static final String TAG = LoginActivity.class.getSimpleName();
    public static final String REG_HOUSE_HOLD_DATA = "reg_house_hold_data";
    public static final String REG_MEMBER_DATA = "reg_member_data";
    public static final String REG_MEMBER_PROG_GROUP_DATA = "reg_member_prog_grp_data";
    public static final String SERVICE_DATA = "service_data";
    public static final String ALL_DATA = "all_data";
    public static final String DYNAMIC_TABLE = "dynamic_table";
    public static final String ENU_TABLE = "enu_table";
    private static final int REG_MODE = 0;
    private static final int DIST_MODE = 1;
    private static final int SERV_MODE = 2;
    private static final int OTHER_MODE = 3;


    /**
     * function to verify login details & select 2 village
     */
    List<VillageItem> villageNameList = new ArrayList<VillageItem>();
    List<AdmCountryDataModel> countryNameList = new ArrayList<AdmCountryDataModel>();
    Dialog mdialog;
    ArrayList<VillageItem> aL_itemsSelected = new ArrayList<VillageItem>();
    ArrayList<AdmCountryDataModel> aCountryL_itemsSelected = new ArrayList<AdmCountryDataModel>();
    ArrayList<VillageItem> selectedVillageList = new ArrayList<VillageItem>();
    ArrayList<AdmCountryDataModel> selectedCountryList = new ArrayList<AdmCountryDataModel>();
    boolean[] itemChecked;
    boolean[] itemCheckedOpearationMode;

    String[] villageNameStringArray;
    String[] countryNameStringArray;
    private final String[] operationModeStringArray = {"Registration", "Distribution", "Service", "Other"};


    // Login Button
    private Button btnLogin;
    // User hhName Input box
    private EditText inputUsername;
    //password input box
    private EditText inputPassword;
    //progress mdialog wigedt
    private ProgressDialog barPDialog; //Bar Progress Dialog
    //progress handler
    private Handler barPDialogHandler;
    //sqlLite Database handler
    private SQLiteHandler db;
    // connection Detector
    ConnectionDetector cd;
    // flag for connectivity
    Boolean isInternetAvailable = false;
    //alert Dialog Manager
    AlertDialogManager alert;
    //Application configuration
    private AppConfig ac;
    //progress mdialog
    private ProgressDialog pDialog;
    // size  = 0, int type
    private int size = 0;
    //mContext
    private final Context mContext = LoginActivity.this;
    //exit button
    private Button btnExit;
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    private Button btnClean;

    /**
     * function to verify login details & select 2 FDP
     */

    List<ServiceCenterItem> serviceCenterNameList = new ArrayList<ServiceCenterItem>();
    ArrayList<ServiceCenterItem> selectedServiceCenterList = new ArrayList<ServiceCenterItem>();

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        barPDialogHandler = new Handler();

        /**
         * Initialize Button and Input Boxes
         */
        inputUsername = (EditText) findViewById(R.id.user_name);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnExit = (Button) findViewById(R.id.btnExit);
        btnClean = (Button) findViewById(R.id.btnClean);

        settings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2


        // Progress mdialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // connectivity manager
        cd = new ConnectionDetector(getApplicationContext());


        setListener();


    }

    private void setListener() {
// todo: move the code ANNotification class
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Exit Application?");
                builder.setMessage("Click yes to exit!");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(0);
                        finish();

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog exitDailog = builder.create();
                exitDailog.show();


            }
        });
        btnClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (db.selectUploadSyntextRowCount() > 0) {
                    //Toast.makeText(LoginActivity.this, "", Toast.LENGTH_SHORT).show();
                    showAlert("There are records not yet Synced. Clean attempt denied");
                } else {


                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Delete Database?");
                    builder.setMessage("Sure to delete database?");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            try {
                                dialog.dismiss();
                                db.deleteUsersWithSelected_LayR4_FDP_Srv_Country();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog exitDailog = builder.create();
                    exitDailog.show();


                }
            }
        });

        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                String user_name = "";
                String password = "";
                // in developments mode
                if (ac.DEV_ENVIRONMENT) {
                    user_name = "nkalam";
                    password = "p3";
                } else {
                    user_name = inputUsername.getText().toString().trim();
                    password = inputPassword.getText().toString().trim();
                }
//                downLoadEnuTable(user_name, password);
                // Check for empty data in the form
                if (user_name.trim().length() > 0 && password.trim().length() > 0) {

                    if (db.isValidLocalLogin(user_name, password)) {

                        gotoHomePage();

                    } else {
/**
 * This block determine is Internet available
 */
                        isInternetAvailable = cd.isConnectingToInternet();
                        if (isInternetAvailable) {
/***
 * This if  block determine is there any un-synchronized  data exits in local device
 */
                            if (db.selectUploadSyntextRowCount() > 0) {
/**
 * This block check the user is country admin or not
 * if the the user is country admin or admin
 * than the app will be unlocked . but will remain for previous user
 */
                                if (db.isValidAdminLocalLogin(user_name, password)) {
                                    gotoHomePage();
                                } else {
                                    showAlert(getResources().getString(R.string.unsyn_msg));
                                }

                            } else {
                                pDialog = new ProgressDialog(mContext);
                                pDialog.setCancelable(false);
                                pDialog.setMessage("Loading..");
                                pDialog.show();
                                /**
                                 * for selecting operation Mood
                                 *
                                 */
                                getOperationModeAlert(user_name, password);
                            }


                        } else
                            showAlert("Check your internet connectivity!!");
                    }
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the credentials!", Toast.LENGTH_LONG)
                            .show();
                }

            }

        });
    }


    String strOperationMode = "";

    /**
     * @param user_name user name
     * @param password  password
     */

    private void getOperationModeAlert(final String user_name, final String password) {
        aCountryL_itemsSelected = (ArrayList<AdmCountryDataModel>) insertCountryNameListToSArray();
        itemCheckedOpearationMode = new boolean[operationModeStringArray.length];
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Operation Mode");

        builder.setSingleChoiceItems(operationModeStringArray, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                strOperationMode = "";
                strOperationMode = operationModeStringArray[which];
            }
        });
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (!strOperationMode.equals("")) {

                    for (int mode_index = 0; mode_index < itemCheckedOpearationMode.length; mode_index++) {
                        if (operationModeStringArray[mode_index].equals(strOperationMode)) {

                            pDialog = new ProgressDialog(mContext);
                            pDialog.setCancelable(false);
                            pDialog.setMessage("Downloading  data .");
                            pDialog.show();
                            switch (mode_index) {
                                case REG_MODE:
                                    checkVillageSelection(user_name, password, "1");
                                    break;
                                case DIST_MODE:

                                    checkFDPSelection(user_name, password);
                                    break;
                                case SERV_MODE:
                                    checkProgramSelection(user_name, password);
                                    break;

                                case OTHER_MODE:
                                    checkCountrySelection(user_name, password, "4");


                                    break;

                                default:
                                    hideDialog();
                                    mdialog.dismiss();
                                    Toast.makeText(LoginActivity.this, "Select  any one", Toast.LENGTH_SHORT).show();
                                    break;

                            }
                        } else {
                            hideDialog();
                            mdialog.dismiss();
                        }

                    }
                } else {
                    mdialog.dismiss();
                    hideDialog();
                }


            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                hideDialog();


                mdialog.dismiss();
            }
        });
        mdialog = builder.create();
        mdialog.show();
    }


    private void gotoHomePage() {
        setLogin(true);        // login success

        // Getting local User information
        HashMap<String, String> user = db.getUserDetails();
        setUserName(user.get("name"));  // Setting Username into session
        setStaffID(user.get("code"));   // Setting StaffCode into session
        setUserID(user.get("username"));
        setUserPassword(user.get("password"));

        setUserCountryCode(user.get("c_code")); // Setting Country Code into session

        /**
         *  Launch main activity
         *  */
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);

        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public void checkServiceCenterSelection(final String user_name, final String password, final String cCode, final String donorCode, final String awardCode, final String progCode, final String opMothCode, final String distFlag) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";


        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.API_LINK, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                /***
                 * @deis: IN THIS STRING RESPONSE WRITE THE JSON DATA
                 *
                 */
                AppController.getInstance().getRequestQueue().getCache().clear();

                String CountryNo = "0";
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {

                        int size = 0;
                        // count no countries assigne
                        if (!jObj.isNull("countrie_no")) {

                            JSONArray village = jObj.getJSONArray("countrie_no");

                            size = village.length();
                            for (int i = 0; i < size; i++) {
                                JSONObject vil = village.getJSONObject(i);

                                CountryNo = vil.getString("CountryNo");

                            }
                        }

                        if (!jObj.isNull(Parser.COUNTRIES_JSON_A)) {
                            countryNameList.clear();
                            countryNameList = Parser.AdmCountryParser(jObj.getJSONArray(Parser.COUNTRIES_JSON_A));
                        }


                        if (!jObj.isNull(Parser.DOB_SERVICE_CENTER_JSON_A)) {// this is not servie
                            JSONArray dob_service_centers = jObj.getJSONArray(Parser.DOB_SERVICE_CENTER_JSON_A);
                            size = dob_service_centers.length();
                            for (int i = 0; i < size; i++) {
                                JSONObject dob_service_center = dob_service_centers.getJSONObject(i);

                                String AdmCountryCode = dob_service_center.getString(Parser.ADM_COUNTRY_CODE);
                                String SrvCenterCode = dob_service_center.getString(Parser.SRV_CENTER_CODE);
                                String SrvCenterName = dob_service_center.getString(Parser.SRV_CENTER_NAME);

                                ServiceCenterItem servCenterItem = new ServiceCenterItem();
                                servCenterItem.setAdmCountryCode(AdmCountryCode);
                                servCenterItem.setServiceCenterCode(SrvCenterCode);
                                servCenterItem.setServiceCenterName(SrvCenterName);
                                serviceCenterNameList.add(servCenterItem);


                            }
                        }


                        hideDialog();


                        getServiceCenterAlert(user_name, password, false);


                    } else {
                        // Error in login. Invalid UserName or Password
                        hideDialog();
                        String errorMsg = response.substring(response.indexOf("error_msg") + 11);
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error + " Stack Tracr = " + error.getStackTrace() + " Detail = " + error.getMessage());

                hideDialog();
                showAlert("Failed to retrieve data\r\nPlease try again checking your internet connectivity, Username and Password.");

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("key", "PhEUT5R251");
                params.put("task", "is_down_load_service_center_name");
                params.put("user_name", user_name);
                params.put("password", password);
                params.put("country_code", cCode);
                params.put("donor_code", donorCode);
                params.put("award_code", awardCode);
                params.put("program_code", progCode);
                params.put("opMothCode", opMothCode);
                params.put("distFlag", distFlag);


                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    public void checkProgramSelection(final String user_name, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login_";
//        AdmProgramNameList.clear();

        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.API_LINK, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {


                /***
                 * Clear the Cache memory
                 *
                 */
                AppController.getInstance().getRequestQueue().getCache().clear();

                /**
                 * hide the Dialog bar
                 */
                hideDialog();

                String CountryNo = "0";
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {

                        int size = 0;
/**
 * Clean the Temporary Data Base
 */
                        db.cleanTemTableForService();
                        // count no countries assigne
                        if (!jObj.isNull("countrie_no")) {

                            JSONArray village = jObj.getJSONArray("countrie_no");

                            size = village.length();
                            for (int i = 0; i < size; i++) {
                                JSONObject vil = village.getJSONObject(i);

                                CountryNo = vil.getString("CountryNo");

                            }
                        }

                        if (!jObj.isNull(Parser.COUNTRIES_JSON_A)) {
                            countryNameList.clear();
                            countryNameList = Parser.AdmCountryParser(jObj.getJSONArray(Parser.COUNTRIES_JSON_A));
                        }


                        if (!jObj.isNull(Parser.ADM_OP_MONTH_JSON_A)) {
                            JSONArray adm_op_months = jObj.getJSONArray(Parser.ADM_OP_MONTH_JSON_A);
                            size = adm_op_months.length();
                            for (int i = 0; i < size; i++) {
                                JSONObject adm_op_month = adm_op_months.getJSONObject(i);

                                String AdmCountryCode = adm_op_month.getString(Parser.ADM_COUNTRY_CODE);
                                String AdmDonorCode = adm_op_month.getString(Parser.ADM_DONOR_CODE);
                                String AdmAwardCode = adm_op_month.getString(Parser.ADM_AWARD_CODE);
                                String OpCode = adm_op_month.getString(Parser.OP_CODE);
                                String OpMonthCode = adm_op_month.getString(Parser.OP_MONTH_CODE);
                                String MonthLabel = adm_op_month.getString(Parser.MONTH_LABEL);
                                String UsaStartDate = adm_op_month.getString(Parser.USA_START_DATE);
                                String UsaEndDate = adm_op_month.getString(Parser.USA_END_DATE);
                                String Status = adm_op_month.getString("Status");
                                db.addTemporaryOpMonth(AdmCountryCode, AdmDonorCode, AdmAwardCode, OpCode, OpMonthCode, MonthLabel, UsaStartDate, UsaEndDate, Status);


                            }
                        }


                        if (!jObj.isNull(Parser.ADM_PROGRAM_MASTER_JSON_A)) {
                            JSONArray adm_program_masters = jObj.getJSONArray(Parser.ADM_PROGRAM_MASTER_JSON_A);
                            size = adm_program_masters.length();
                            for (int i = 0; i < size; i++) {
                                JSONObject adm_program_master = adm_program_masters.getJSONObject(i);

                                String AdmCountryCode = adm_program_master.getString("AdmCountryCode");
                                String AdmProgCode = adm_program_master.getString(Parser.ADM_PROG_CODE);
                                String AdmAwardCode = adm_program_master.getString(Parser.ADM_AWARD_CODE);
                                String AdmDonorCode = adm_program_master.getString(Parser.ADM_DONOR_CODE);
                                String ProgName = adm_program_master.getString(Parser.PROG_NAME);
                                String ProgShortName = adm_program_master.getString(Parser.PROG_SHORT_NAME);

                                db.addTemporaryAdmCountryProgram(AdmCountryCode, AdmDonorCode, AdmAwardCode, AdmProgCode, ProgName, ProgShortName);

                            }
                        }


                        hideDialog();


                        // if user hsa 1 country assigned
                        if (CountryNo.equals("1")) {
                            getProgramAlert(user_name, password, countryNameList.get(0).getAdmCountryCode());

                        } else {
                            selectedCountryList.clear();
                            getCountryAlert(user_name, password, UtilClass.SERVICE_OPERATION_MODE);
                        }


                    } else {
                        // Error in login. Invalid UserName or Password
                        hideDialog();
                        String errorMsg = response.substring(response.indexOf("error_msg") + 11);
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error + " Stack Tracr = " + error.getStackTrace() + " Detail = " + error.getMessage());
                // hide the mdialog
                hideDialog();
                showAlert("Failed to retrieve data\r\nPlease try again checking your internet connectivity, Username and Password.");

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("key", "PhEUT5R251");
                params.put("task", "is_down_load_programName");
                params.put("user_name", user_name);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    /**
     * function to verify login details & select 2 FDP
     */

    List<FDPItem> fdpNameList = new ArrayList<FDPItem>();
    ArrayList<FDPItem> selectedFdpList = new ArrayList<FDPItem>();
    String[] fdpNameStringArray;

    public void checkFDPSelection(final String user_name, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";


        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.API_LINK, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                /***
                 * @deis: IN THIS STRING RESPONSE WRITE THE JSON DATA
                 *
                 */
                AppController.getInstance().getRequestQueue().getCache().clear();

                /**
                 * hide the Dialog bar
                 */
                hideDialog();

                String CountryNo = "0";
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {

                        int size = 0;
                        // count no countries assigne
                        if (!jObj.isNull("countrie_no")) {

                            JSONArray village = jObj.getJSONArray("countrie_no");

                            size = village.length();
                            for (int i = 0; i < size; i++) {
                                JSONObject vil = village.getJSONObject(i);

                                CountryNo = vil.getString("CountryNo");

                            }
                        }


                        if (!jObj.isNull(Parser.COUNTRIES_JSON_A)) {
                            countryNameList.clear();
                            countryNameList = Parser.AdmCountryParser(jObj.getJSONArray(Parser.COUNTRIES_JSON_A));
                        }


                        if (!jObj.isNull(Parser.STAFF_FDP_ACCESS_JSON_A)) {

                            JSONArray village = jObj.getJSONArray(Parser.STAFF_FDP_ACCESS_JSON_A);
                            fdpNameList.clear();
                            size = village.length();
                            for (int i = 0; i < size; i++) {
                                JSONObject fdp_list = village.getJSONObject(i);

                                //String GeoLayRName = fdp_list.getString("GeoLayRName");
                                String AdmCountryCode = fdp_list.getString("AdmCountryCode");
                                String FDPCode = fdp_list.getString("FDPCode");
                                String FDPName = fdp_list.getString("FDPName");


                                FDPItem fdpItem = new FDPItem();
                                fdpItem.setAdmCountryCode(AdmCountryCode);
                                fdpItem.setFDPCode(FDPCode);
                                fdpItem.setFDPName(FDPName);
                                fdpNameList.add(fdpItem);


                            }
                        }
                        hideDialog();
                        // if user hsa 1 country assigned
                        if (CountryNo.equals("1")) {
                            getFDPAlert(user_name, password, false);
                        } else {
                            selectedCountryList.clear();
                            getCountryAlert(user_name, password, UtilClass.DISTRIBUTION_OPERATION_MODE);
                        }


                    } else {
                        // Error in login. Invalid UserName or Password
                        hideDialog();
                        String errorMsg = response.substring(response.indexOf("error_msg") + 11);
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error + " Stack Tracr = " + error.getStackTrace() + " Detail = " + error.getMessage());
                // hide the mdialog
                hideDialog();
                showAlert("Failed to retrieve data\r\nPlease try again checking your internet connectivity, Username and Password.");

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("key", "PhEUT5R251");
                params.put("task", "is_down_load_fdp_name");
                params.put("user_name", user_name);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void checkCountrySelection(final String user_name, final String password, final String operationMode) {
        String tag_string_req = "req_country";

        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.API_LINK, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                /***
                 * Clear the Cache memory
                 *
                 */

                AppController.getInstance().getRequestQueue().getCache().clear();

                /**
                 * hide the Dialog bar
                 */
                hideDialog();
                String CountryNo = "0";
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {

                        int size = 0;

                        if (!jObj.isNull("countrie_no")) {

                            JSONArray jsonArray = jObj.getJSONArray("countrie_no");

                            size = jsonArray.length();
                            for (int i = 0; i < size; i++) {
                                JSONObject vil = jsonArray.getJSONObject(i);
                                CountryNo = vil.getString("CountryNo");

                            }
                        }


                        if (!jObj.isNull(Parser.COUNTRIES_JSON_A)) {
                            countryNameList.clear();
                            countryNameList = Parser.AdmCountryParser(jObj.getJSONArray(Parser.COUNTRIES_JSON_A));
                        }


                        hideDialog();
                        /**
                         *  if user haa 1 country assigned
                         */

                        if (CountryNo.equals("1")) {


                            JSONArray jaary = new JSONArray();


                            Log.d("CHAPA", "jeson to string :" + jaary.toString());
                            /** for Other  MOde*/
                            checkLogin(user_name, password, jaary, "4"); // checking online

                            editor.putInt(UtilClass.OPERATION_MODE, UtilClass.OTHER_OPERATION_MODE);
                            editor.commit();

                        } else {
                            selectedCountryList.clear();
                            getCountryAlert(user_name, password, UtilClass.OTHER_OPERATION_MODE);
                        }


                    } else {
                        // Error in login. Invalid UserName or Password
                        hideDialog();
                        String errorMsg = response.substring(response.indexOf("error_msg") + 11);
                        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error + " Stack Tracr = " + error.getStackTrace() + " Detail = " + error.getMessage());

                hideDialog();
                showAlert("Failed to retrieve data\r\nPlease try again checking your internet connectivity, Username and Password.");

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("key", "PhEUT5R251");
                params.put("task", "is_down_load_village_name");
                params.put("user_name", user_name);
                params.put("password", password);
                params.put("operation_mode", operationMode);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void checkVillageSelection(final String user_name, final String password, final String operationMode) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";


        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.API_LINK, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                /***                 *  IN THIS STRING RESPONSE WRITE THE JSON DATA                  */
                AppController.getInstance().getRequestQueue().getCache().clear();

                /**                 * hide the Dialog bar                 */
                hideDialog();

                String CountryNo = "0";
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {

                        int size = 0;

                        if (!jObj.isNull("countrie_no")) {

                            JSONArray village = jObj.getJSONArray("countrie_no");

                            size = village.length();
                            for (int i = 0; i < size; i++) {
                                JSONObject vil = village.getJSONObject(i);
                                CountryNo = vil.getString("CountryNo");

                            }
                        }


                        if (!jObj.isNull(Parser.COUNTRIES_JSON_A)) {
                            countryNameList.clear();
                            countryNameList = Parser.AdmCountryParser(jObj.getJSONArray(Parser.COUNTRIES_JSON_A));
                        }


                        if (!jObj.isNull(Parser.VILLAGE_JSON_A)) {

                            JSONArray village = jObj.getJSONArray(Parser.VILLAGE_JSON_A);
                            villageNameList.clear();
                            size = village.length();
                            for (int i = 0; i < size; i++) {
                                JSONObject vil = village.getJSONObject(i);

                                String GeoLayRName = vil.getString("GeoLayRName");
                                String AdmCountryCode = vil.getString("AdmCountryCode");
                                String LayRCode = vil.getString("LayRCode");
                                String LayR4ListName = vil.getString("LayR4ListName");


                                VillageItem villageItem = new VillageItem();
                                villageItem.setGeoLayRName(GeoLayRName);
                                villageItem.setAdmCountryCode(AdmCountryCode);
                                villageItem.setLayRCode(LayRCode);
                                villageItem.setLayR4ListName(LayR4ListName);
                                villageNameList.add(villageItem);


                            }
                        }
                        hideDialog();
                        // if user hsa 1 country assigned
                        if (CountryNo.equals("1")) {
                            getVillageAlert(user_name, password, false);
                        } else {
                            selectedCountryList.clear();
                            getCountryAlert(user_name, password, UtilClass.REGISTRATION_OPERATION_MODE);
                        }


                    } else {
                        // Error in login. Invalid UserName or Password
                        hideDialog();
                        String errorMsg = response.substring(response.indexOf("error_msg") + 11);
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                        // hideDialog();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error + " Stack Tracr = " + error.getStackTrace() + " Detail = " + error.getMessage());
                // hide the mdialog
                hideDialog();
                showAlert("Failed to retrieve data\r\nPlease try again checking your internet connectivity, Username and Password.");

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("key", "PhEUT5R251");
                params.put("task", "is_down_load_village_name");
                params.put("user_name", user_name);
                params.put("password", password);
                params.put("password", password);
                params.put("operation_mode", operationMode);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    public List<FDPItem> insertFDPNameListToSArray(boolean countrySpec) {
        int i;
        if (countrySpec) {
            ArrayList<FDPItem> temCountySpecicFDPList = new ArrayList<FDPItem>();
            for (i = 0; i < fdpNameList.size(); ++i) {
                if (selectedCountryList.get(0).getAdmCountryCode().equals(fdpNameList.get(i).getAdmCountryCode())) {
                    temCountySpecicFDPList.add(fdpNameList.get(i));
                }

            }
            fdpNameList.clear();
            for (i = 0; i < temCountySpecicFDPList.size(); ++i) {

                fdpNameList.add(temCountySpecicFDPList.get(i));


            }


            fdpNameStringArray = new String[fdpNameList.size()];

            for (i = 0; i < fdpNameList.size(); ++i) {

                FDPItem fdpItem = fdpNameList.get(i);
                fdpNameStringArray[i] = fdpItem.getFDPName();

            }


            return fdpNameList;
        } else {
            fdpNameStringArray = new String[fdpNameList.size()];

            for (i = 0; i < fdpNameList.size(); ++i) {

                FDPItem fdpItem = fdpNameList.get(i);
                fdpNameStringArray[i] = fdpItem.getFDPName();

            }


            return fdpNameList;
        }

    }


    public List<VillageItem> insertVillageNameListToSArray(boolean countrySpec) {
        int i;
        if (countrySpec) {
            ArrayList<VillageItem> temCountySpecicVillageList = new ArrayList<VillageItem>();
            for (i = 0; i < villageNameList.size(); ++i) {
                if (selectedCountryList.get(0).getAdmCountryCode().equals(villageNameList.get(i).getAdmCountryCode())) {
                    temCountySpecicVillageList.add(villageNameList.get(i));
                }

            }
            villageNameList.clear();
            for (i = 0; i < temCountySpecicVillageList.size(); ++i) {

                villageNameList.add(temCountySpecicVillageList.get(i));


            }
/***
 * convert into array string
 */

            villageNameStringArray = new String[villageNameList.size()];

            for (i = 0; i < villageNameList.size(); ++i) {

                VillageItem villageItem = villageNameList.get(i);
                villageNameStringArray[i] = villageItem.getLayR4ListName();

            }


            return villageNameList;
        } else {
            villageNameStringArray = new String[villageNameList.size()];

            for (i = 0; i < villageNameList.size(); ++i) {

                VillageItem villageItem = villageNameList.get(i);
                villageNameStringArray[i] = villageItem.getLayR4ListName();

            }


            return villageNameList;
        }

    }


    public List<AdmCountryDataModel> insertCountryNameListToSArray() {
        int i;
        countryNameStringArray = new String[countryNameList.size()];

        for (i = 0; i < countryNameList.size(); ++i) {

            AdmCountryDataModel countryItem = countryNameList.get(i);
            countryNameStringArray[i] = countryItem.getAdmCountryName();

        }


        return countryNameList;

    }


    String strCountryMode = "";

    private void getCountryAlert(final String user_name, final String password, final int operationMode) {
        aCountryL_itemsSelected = (ArrayList<AdmCountryDataModel>) insertCountryNameListToSArray();
        if (countryNameStringArray.length > 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select  A Country ");
            builder.setSingleChoiceItems(countryNameStringArray, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    strCountryMode = "";
                    strCountryMode = countryNameStringArray[which];
                }
            });

            builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    /**
                     * Clean the table
                     */
                    selectedVillageList.clear();
                    selectedCountryList.clear();
                    selectedServiceCenterList.clear();
                    if (!strCountryMode.equals("")) {

                        for (int i = 0; i < countryNameStringArray.length; i++) {
                            /**
                             * store the selected country in selectedCountryList
                             */
                            if (countryNameStringArray[i].equals(strCountryMode)) {
                                selectedCountryList.add(aCountryL_itemsSelected.get(i));
                            }
                        }
                        switch (operationMode) {
                            case UtilClass.REGISTRATION_OPERATION_MODE:
                                getVillageAlert(user_name, password, true);
                                break;
                            case UtilClass.DISTRIBUTION_OPERATION_MODE:
                                getFDPAlert(user_name, password, true);
                                break;
                            case UtilClass.SERVICE_OPERATION_MODE:
                                getProgramAlert(user_name, password, selectedCountryList.get(0).getAdmCountryCode());

                                break;

                            case UtilClass.OTHER_OPERATION_MODE:
                                /**
                                 * only need the json array for put parameter
                                 */

                                JSONArray jaary = new JSONArray();
                                /**
                                 * only for multiple Country Access user
                                 * value will be insert
                                 */
                                db.insertSelectedCountry(selectedCountryList.get(0).getAdmCountryCode(), selectedCountryList.get(0).getAdmCountryName());

                                jaary = UtilClass.jsonConverter(selectedCountryList.get(0).getAdmCountryCode());
                                Log.d(TAG, "jeson to string :" + jaary.toString());
                                /** for Other  MOde*/
                                checkLogin(user_name, password, jaary, "4"); // checking online

                                editor.putInt(UtilClass.OPERATION_MODE, UtilClass.OTHER_OPERATION_MODE);
                                editor.commit();


                                break;
                        }
                    } else {
                        mdialog.dismiss();
                        hideDialog();
                    }

                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    aL_itemsSelected.clear();
                    selectedCountryList.clear();
                    dialog.dismiss();
                }
            });
            mdialog = builder.create();
            mdialog.show();
        }
    }

    String temSelectedProgram;

    private void getProgramAlert(final String user_name, final String password, String countryCode) {


        final List<ProgramMasterDM> programNames = db.getProgramListNames(countryCode);


        final String[] proNamesString = new String[programNames.size()];
        for (int i = 0; i < programNames.size(); ++i) {
            ProgramMasterDM data = programNames.get(i);
            proNamesString[i] = data.getProgName();

        }


        itemChecked = new boolean[proNamesString.length];
        if (proNamesString.length > 0) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select  A Program");


            builder.setSingleChoiceItems(proNamesString, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    temSelectedProgram = "";
                    temSelectedProgram = proNamesString[which];

                }
            })

                    .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int pos) {

                            String selectedProgCode = "";
                            String selectedCountryCode = "";
                            String selectedDonorCode = "";
                            String selectedAwardCode = "";
                            for (int i = 0; i < itemChecked.length; i++) {
                                /**
                                 * if indexed item is selected  than
                                 */
                                if (programNames.get(i).getProgName().equals(temSelectedProgram)) { //simplify code itemChecked[i] == true
                                    // todo : update in database
                                    selectedCountryCode = programNames.get(i).getAdmCountryCode();
                                    selectedDonorCode = programNames.get(i).getAdmDonorCode();
                                    selectedAwardCode = programNames.get(i).getAdmAwardCode();
                                    selectedProgCode = programNames.get(i).getAdmProgCode();


                                }
                            }


                            if (selectedProgCode.length() > 0) {
                                /**
                                 * opMonth dialog
                                 */
                                hideDialog();
                                getOpMonthAlert(user_name, password, selectedCountryCode, selectedDonorCode, selectedAwardCode, selectedProgCode);
                            }


                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {


                        }
                    });
            mdialog = builder.create();
            mdialog.show();
        } else {
            hideDialog();
            Toast.makeText(LoginActivity.this, " Contact Admin.", Toast.LENGTH_LONG).show();
        }

    }


    String tem;

    private void getOpMonthAlert(final String user_name, final String password, final String countryCode, final String selectedDonorCode, final String selectedAwardCode, final String selectedProgCode) {


        final List<TemOpMonth> opMonths = db.getOpMonthList(countryCode);


        final String[] opMonthNamesString = new String[opMonths.size()];
        for (int i = 0; i < opMonths.size(); ++i) {
            TemOpMonth data = opMonths.get(i);
            opMonthNamesString[i] = data.getOpMonthLable();

        }


        itemChecked = new boolean[opMonthNamesString.length];
        if (opMonthNamesString.length > 0) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select  A Month");


            builder.setSingleChoiceItems(opMonthNamesString, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    tem = "";
                    tem = opMonthNamesString[which];

                }
            })

                    .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int pos) {

                            String selectedOpMonthCode = "";


                            for (int i = 0; i < itemChecked.length; i++) {
                                /**
                                 * if indexed item is selected  than
                                 */
                                if (opMonths.get(i).getOpMonthLable().equals(tem)) { //simplify code itemChecked[i] == true
                                    // todo : update in database
                                    selectedOpMonthCode = opMonths.get(i).getOpMonthCode();


                                }
                            }


                            if (selectedOpMonthCode.length() > 0) {
                                hideDialog();
                                getTypeFlagAlert(user_name, password, countryCode, selectedDonorCode, selectedAwardCode, selectedProgCode, selectedOpMonthCode);

                            }


                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {


                        }
                    });
            mdialog = builder.create();
            mdialog.show();
        } else {
            hideDialog();
            Toast.makeText(LoginActivity.this, "No Service Month is open for Service. Contact Admin. ", Toast.LENGTH_LONG).show();
        }

    }


    private void getTypeFlagAlert(final String user_name, final String password, final String countryCode, final String selectedDonorCode, final String selectedAwardCode, final String selectedProgCode, final String selectedOpMonthCode) {


        final String[] typeFlagNames = mContext.getResources().getStringArray(R.array.arrflagType);


        itemChecked = new boolean[typeFlagNames.length];
        if (typeFlagNames.length > 0) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select  Type");


            builder.setSingleChoiceItems(typeFlagNames, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    tem = "";
                    tem = typeFlagNames[which];

                }
            })

                    .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int pos) {

                            String typeFlag = "";


                            if (tem.equals("Food")) {

                                typeFlag = DistributionActivity.FOOD_TYPE;
                            } else if (tem.equals("Non Food")) {

                                typeFlag = DistributionActivity.NON_FOOD_TYPE;
                            } else if (tem.equals("Cash")) {

                                typeFlag = DistributionActivity.CASH_TYPE;
                            } else {

                                typeFlag = DistributionActivity.VOUCHER_TYPE;
                            }


                            if (typeFlag.length() > 0) {
                                hideDialog();


                                checkServiceCenterSelection(user_name, password, countryCode, selectedDonorCode, selectedAwardCode, selectedProgCode, selectedOpMonthCode, typeFlag);

                            }


                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {


                        }
                    });
            mdialog = builder.create();
            mdialog.show();
        } else {
            hideDialog();
            Toast.makeText(LoginActivity.this, "No OpMonth is open for Service. Contact Admin. ", Toast.LENGTH_LONG).show();
        }

    }


    private void getServiceCenterAlert(final String user_name, final String password, boolean countrySpecificFlag) {

        final ArrayList<ServiceCenterItem> aLServiceCenter_itemsSelected = (ArrayList<ServiceCenterItem>) serviceCenterNameList; //(ArrayList<ServiceCenterItem>) insertServiceCenterNameListToSArray(countrySpecificFlag);
        // Initial
        final String[] serviceCenterNameStringArray = new String[serviceCenterNameList.size()];

        for (int i = 0; i < serviceCenterNameList.size(); ++i) {
            ServiceCenterItem serviceCenterItem = serviceCenterNameList.get(i);
            serviceCenterNameStringArray[i] = serviceCenterItem.getServiceCenterName();
        }
        itemChecked = new boolean[serviceCenterNameStringArray.length];
        if (serviceCenterNameStringArray.length > 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select Max TWO Service Centers ");
            builder.setMultiChoiceItems(serviceCenterNameStringArray, null,
                    new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int selectedItemId, boolean isSelected) {
                            if (isSelected) {

                                if (((AlertDialog) dialog).getListView().getCheckedItemCount() <= 2) {
                                    itemChecked[selectedItemId] = isSelected;

                                    Log.d("REFAT----> position ", "" + selectedItemId);
                                } else {
                                    Toast.makeText(LoginActivity.this, "You can not permitted to select more than Two ServiceCenter ", Toast.LENGTH_SHORT).show();

                                    ((AlertDialog) dialog).getListView().setItemChecked(selectedItemId, false);
                                }
                            } else if (aLServiceCenter_itemsSelected.contains(selectedItemId)) {
                                aLServiceCenter_itemsSelected.remove(Integer.valueOf(selectedItemId));

                            }
                        }
                    })
                    .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int pos) {
                            //Your logic when OK button is clicked
                            db.deleteFromSelectedServiceCenter();
                            selectedServiceCenterList.clear();
                            for (int i = 0; i < itemChecked.length; i++) {
                                if (itemChecked[i]) {


                                    selectedServiceCenterList.add(aLServiceCenter_itemsSelected.get(i));


                                }
                            }

                            if (selectedServiceCenterList.size() > 0) {
                                JSONArray serviceCenterJSONarry = UtilClass.srvCenterCodeJSONConverter("LoginActivity", selectedServiceCenterList, db);
                                aLServiceCenter_itemsSelected.clear();
                                Log.d(TAG, " Service Center  jeson to string :" + serviceCenterJSONarry.toString());
                                /** 3 is operation code Service */
                                checkLogin(user_name, password, serviceCenterJSONarry, "3"); // checking online
                                editor.putInt(UtilClass.OPERATION_MODE, 3);
                                editor.commit();
                            } else {
                                hideDialog();
                                Toast.makeText(LoginActivity.this, "No Community Center in service center . Contact Admin.", Toast.LENGTH_LONG).show();
                            }


                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {


                        }
                    });
            mdialog = builder.create();
            mdialog.show();
        } else {
            hideDialog();
            Toast.makeText(LoginActivity.this, "No Service Center assigned. Contact Admin.", Toast.LENGTH_LONG).show();
        }

    }


    ArrayList<FDPItem> aLfdp_itemsSelected = new ArrayList<FDPItem>();

    private void getFDPAlert(final String user_name, final String password, boolean countrySpecificFlag) {

        aLfdp_itemsSelected = (ArrayList<FDPItem>) insertFDPNameListToSArray(countrySpecificFlag);

        itemChecked = new boolean[fdpNameStringArray.length];
        if (fdpNameStringArray.length > 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select Max TWO FDPs ");
            builder.setMultiChoiceItems(fdpNameStringArray, null,
                    new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int selectedItemId, boolean isSelected) {
                            if (isSelected) {

                                if (((AlertDialog) dialog).getListView().getCheckedItemCount() <= 2) {
                                    itemChecked[selectedItemId] = isSelected;


                                } else {
                                    Toast.makeText(LoginActivity.this, "You can not permitted to select more than Two FDP", Toast.LENGTH_SHORT).show();

                                    ((AlertDialog) dialog).getListView().setItemChecked(selectedItemId, false);
                                }
                            } else if (aLfdp_itemsSelected.contains(selectedItemId)) {
                                aLfdp_itemsSelected.remove(Integer.valueOf(selectedItemId));

                            }
                        }
                    })
                    .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int pos) {
                            //Your logic when OK button is clicked
                            db.deleteFromSelectedFDP();
                            selectedFdpList.clear();
                            for (int i = 0; i < itemChecked.length; i++) {
                                if (itemChecked[i]) {
                                    selectedFdpList.add(aLfdp_itemsSelected.get(i));

                                }
                            }

                            JSONArray fdpJSONarry = UtilClass.fdpCodeJSONConverter("LoginActivity", selectedFdpList, db);
                            aLfdp_itemsSelected.clear();

                            /** 2 is operation code Distribution */
                            checkLogin(user_name, password, fdpJSONarry, "2"); // checking online
                            editor.putInt(UtilClass.OPERATION_MODE, 2);
                            editor.commit();

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {


                        }
                    });
            mdialog = builder.create();
            mdialog.show();
        } else {
            hideDialog();
            Toast.makeText(LoginActivity.this, "No FDP assigned. Contact Admin.", Toast.LENGTH_LONG).show();
        }

    }


    private void getVillageAlert(final String user_name, final String password, boolean countrySpecificFlag) {

        aL_itemsSelected = (ArrayList<VillageItem>) insertVillageNameListToSArray(countrySpecificFlag);

        itemChecked = new boolean[villageNameStringArray.length];
        if (villageNameStringArray.length > 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select " + aL_itemsSelected.get(0).getGeoLayRName() + " Maximum Two ");
            builder.setMultiChoiceItems(villageNameStringArray, null,
                    new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int selectedItemId, boolean isSelected) {
                            if (isSelected) {

                                if (((AlertDialog) dialog).getListView().getCheckedItemCount() <= 2) {
                                    itemChecked[selectedItemId] = isSelected;


                                } else {
                                    Toast.makeText(LoginActivity.this, "You can not permitted to select more than Two " + aL_itemsSelected.get(0).getGeoLayRName(), Toast.LENGTH_SHORT).show();

                                    ((AlertDialog) dialog).getListView().setItemChecked(selectedItemId, false);
                                }
                            } else if (aL_itemsSelected.contains(selectedItemId)) {
                                aL_itemsSelected.remove(Integer.valueOf(selectedItemId));

                            }
                        }
                    })
                    .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int pos) {
                            //Your logic when OK button is clicked
                            db.deleteFromSelectedVillage();
                            selectedVillageList.clear();
                            for (int i = 0; i < itemChecked.length; i++) {
                                if (itemChecked[i]) {
                                    selectedVillageList.add(aL_itemsSelected.get(i));

                                }
                            }

                            JSONArray jaary = UtilClass.layR4CodeJSONConverter("LoginActivity", selectedVillageList, db);
                            aL_itemsSelected.clear();
                            Log.d(TAG, "jeson to string :" + jaary.toString());
                            /** for registration */
                            checkLogin(user_name, password, jaary, "1"); // checking online

                            editor.putInt(UtilClass.OPERATION_MODE, UtilClass.REGISTRATION_OPERATION_MODE);
                            editor.commit();


                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {


                            Toast.makeText(LoginActivity.this, "No " + aL_itemsSelected.get(0).getGeoLayRName() + " Selected. Sync attempt denied." +
                                    "\n Select " + aL_itemsSelected.get(0).getGeoLayRName() + " to Sync properly.\n Try to login again.", Toast.LENGTH_SHORT).show();
                            aL_itemsSelected.clear();

                        }
                    });
            mdialog = builder.create();
            mdialog.show();
        } else {
            hideDialog();
            Toast.makeText(LoginActivity.this, "No village assigned. Contact Admin.", Toast.LENGTH_LONG).show();
        }

    }


    /**
     * function to verify login details in mysql db
     */
    public void checkLogin(final String user_name, final String password, final JSONArray selectedVilJArry, final String operationMode) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";
        pDialog = new ProgressDialog(mContext);
        pDialog.setCancelable(false);
        pDialog.setMessage("Downloading  data .");
        pDialog.show();

        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.API_LINK, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                /***
                 * @deis: IN THIS STRING RESPONSE WRITE THE JSON DATA
                 *
                 */
                AppController.getInstance().getRequestQueue().getCache().clear();
                writeJSONToTextFile(response, ALL_DATA);

                // DOING STRING OPERATION TO AVOID ALLOCATE CACHE MEMORY
                String errorResult = response.substring(9, 14);


                /**
                 *  simplifies the the code
                 *  */
                //   boolean error = errorResult.equals("false") ? false : true;


                boolean error = !errorResult.equals("false");
                if (!error) {
                    Log.d("TAG", "Before downLoad RegNHouseHold 6" + "  user_name:" + user_name + " password :" + password + " selectedVilJArry:" + selectedVilJArry + "operationMode:" + operationMode);
                    downLoadRegNHouseHold(user_name, password, selectedVilJArry, operationMode);

                } else {
                    // Error in login. Invalid UserName or Password
                    hideDialog();
                    String errorMsg = response.substring(response.indexOf("error_msg") + 11);
                    Toast.makeText(getApplicationContext(),
                            errorMsg, Toast.LENGTH_LONG).show();

                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error + " Stack Tracr = " + error.getStackTrace() + " Detail = " + error.getMessage());
                hideDialog();
                showAlert("Failed to retrieve data\r\nPlease try again checking your internet connectivity, Username and Password.");

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("key", "PhEUT5R251");
                params.put("task", "is_valid_user");
                params.put("user_name", user_name);
                params.put("password", password);
                params.put("lay_r_code_j", selectedVilJArry.toString());
                params.put("operation_mode", operationMode);
                Log.d("TAG1", "params:" + params.toString());
                return params;
            }

        };
        Log.d("TAG1", "strReq:" + strReq.toString());

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    /**
     * function to verify login details download RegN house hold
     */
    public void downLoadRegNHouseHold(final String user_Name, final String pass_word, final JSONArray selectedVilJArry, final String operationMode) {
        // Tag used to cancel the request
        String tag_string_req = "req_reg_hh";


        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.API_LINK, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                /***
                 * @deis: IN THIS STRING RESPONSE WRITE THE JSON DATA
                 *
                 */
                AppController.getInstance().getRequestQueue().getCache().clear();
                writeJSONToTextFile(response, REG_HOUSE_HOLD_DATA);

                /**
                 *  DOING STRING OPERATION TO AVOID ALLOCATE CACHE MEMORY
                 */

                String errorResult = response.substring(9, 14);


                boolean error = !errorResult.equals("false");

                if (!error) {
                    Log.d("TAG", "Before downLoad RegNMembers 5" + "  user_Name:" + user_Name + " pass_word :" + pass_word + " selectedVilJArry:" + selectedVilJArry + "operationMode:" + operationMode);
                    downLoadRegNMembers(user_Name, pass_word, selectedVilJArry, operationMode);

                } else {
                    // Error in login. Invalid UserName or Password
                    hideDialog();
                    String errorMsg = response.substring(response.indexOf("error_msg") + 11);
                    Toast.makeText(getApplicationContext(),
                            errorMsg, Toast.LENGTH_LONG).show();

                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error + " Stack Tracr = " + error.getStackTrace() + " Detail = " + error.getMessage());
                hideDialog();
                showAlert("Failed to retrieve data\r\nPlease try again checking your internet connectivity, Username and Password.");


            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("key", "PhEUT5R251");
                params.put("task", "is_down_load_reg_house_hold");
                params.put("user_name", user_Name);
                params.put("password", pass_word);
                params.put("lay_r_code_j", selectedVilJArry.toString());
                params.put("operation_mode", operationMode);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    /**
     * function to verify login details download RegN member data
     */
    public void downLoadRegNMembers(final String user_Name, final String pass_word, final JSONArray selectedVilJArry, final String operationMode) {
        // Tag used to cancel the request
        String tag_string_req = "req_reg";


        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.API_LINK, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                /***
                 * @deis: IN THIS STRING RESPONSE WRITE THE JSON DATA
                 */
                AppController.getInstance().getRequestQueue().getCache().clear();
                writeJSONToTextFile(response, REG_MEMBER_DATA);


                // DOING STRING OPERATION TO AVOID ALLOCATE CACHE MEMORY

                String errorResult = response.substring(9, 14);

/**
 * if Json string get false than it return false
 */
                boolean error = !errorResult.equals("false");


                if (!error) {
                    Log.d("TAG", "Before downLoad RegNMemberProgGroup 4" + "  user_Name:" + user_Name + " pass_word :" + pass_word + " selectedVilJArry:" + selectedVilJArry + "operationMode:" + operationMode);
                    downLoadRegNMemberProgGroup(user_Name, pass_word, selectedVilJArry, operationMode);

                } else {
                    // Error in login. Invalid UserName or Password
                    hideDialog();
                    String errorMsg = response.substring(response.indexOf("error_msg") + 11);
                    Toast.makeText(getApplicationContext(),
                            errorMsg, Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error + " Stack Tracr = " + error.getStackTrace() + " Detail = " + error.getMessage());
                // hide the mdialog
                hideDialog();
                showAlert("Failed to retrieve data\r\nPlease try again checking your internet connectivity, Username and Password.");


            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("key", "PhEUT5R251");
                params.put("task", "is_down_load_reg_member");
                params.put("user_name", user_Name);
                params.put("password", pass_word);
                params.put("lay_r_code_j", selectedVilJArry.toString());
                params.put("operation_mode", operationMode);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    /**
     * function to verify login details download RegN member prog group code
     */
    public void downLoadRegNMemberProgGroup(final String user_Name, final String pass_word, final JSONArray selectedVilJArry, final String operationMode) {
        // Tag used to cancel the request
        String tag_string_req = "req_reg_mem_grp";


        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.API_LINK, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                // clear catch
                AppController.getInstance().getRequestQueue().getCache().clear();
                writeJSONToTextFile(response, REG_MEMBER_PROG_GROUP_DATA);

                Log.d("DIM", " After RegN Member Prog Group in txt stape: 4");


                // DOING STRING OPERATION TO AVOID ALLOCATE CACHE MEMORY

                String errorResult = response.substring(9, 14);


                boolean error = !errorResult.equals("false");

                if (!error) {
                    Log.d("TAG", "Before downLoad ServiceData 3" + "  user_Name:" + user_Name + " pass_word :" + pass_word + " selectedVilJArry:" + selectedVilJArry + "operationMode:" + operationMode);
                    downLoadServiceData(user_Name, pass_word, selectedVilJArry, operationMode);

                } else {
                    // Error in login. Invalid UserName or Password
                    hideDialog();
                    String errorMsg = response.substring(response.indexOf("error_msg") + 11);
                    Toast.makeText(getApplicationContext(),
                            errorMsg, Toast.LENGTH_LONG).show();
                    // hideDialog();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error + " Stack Tracr = " + error.getStackTrace() + " Detail = " + error.getMessage());
                // hide the mdialog
                hideDialog();
                showAlert("Failed to retrieve data\r\nPlease try again checking your internet connectivity, Username and Password.");


            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("key", "PhEUT5R251");
                params.put("task", "is_down_load_reg_mem_grp_data");
                params.put("user_name", user_Name);
                params.put("password", pass_word);
                params.put("lay_r_code_j", selectedVilJArry.toString());
                params.put("operation_mode", operationMode);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    /**
     * function to verify login details download Service
     */
    public void downLoadServiceData(final String user_Name, final String pass_word, final JSONArray selectedVilJArry, final String operationMode) {
        // Tag used to cancel the request
        String tag_string_req = "req_reg";


        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.API_LINK, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                // clear catch
                AppController.getInstance().getRequestQueue().getCache().clear();
                writeJSONToTextFile(response, SERVICE_DATA);

                Log.d("DIM", " After write data in Service Data . stape :5");


                String errorResult = response.substring(9, 14);


                boolean error = !errorResult.equals("false");

                if (!error) {


                    Log.d("TAG", "Before downLoad AssignProgSrv 2" + "  user_Name:" + user_Name + " pass_word :" + pass_word + " selectedVilJArry:" + selectedVilJArry + "operationMode:" + operationMode);
                    downLoadAssignProgSrv(user_Name, pass_word, selectedVilJArry, operationMode);

                } else {
                    // Error in login. Invalid UserName or Password
                    hideDialog();
                    String errorMsg = response.substring(response.indexOf("error_msg") + 11);
                    Toast.makeText(getApplicationContext(),
                            errorMsg, Toast.LENGTH_LONG).show();
                    // hideDialog();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error + " Stack Tracr = " + error.getStackTrace() + " Detail = " + error.getMessage());

                hideDialog();
                showAlert("Failed to retrieve data\r\nPlease try again checking your internet connectivity, Username and Password.");
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("key", "PhEUT5R251");
                params.put("task", "is_down_load_service_data");
                params.put("user_name", user_Name);
                params.put("password", pass_word);
                params.put("lay_r_code_j", selectedVilJArry.toString());
                params.put("operation_mode", operationMode);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    /**
     * function to verify login details download RegN AssProgSrv in mysql db
     */
    public void downLoadAssignProgSrv(final String user_Name, final String pass_word, final JSONArray selectedVilJArry, final String operationMode) {
        // Tag used to cancel the request
        String tag_string_req = "req_ass_prog";
        Log.d("MOR", "Before Response Calling ");

        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.API_LINK, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                /***
                 *  IN THIS STRING RESPONSE WRITE THE JSON DATA
                 *
                 */
                AppController.getInstance().getRequestQueue().getCache().clear();
                writeJSONToTextFile(response, "reg_ass_prog_srv_data");

                Log.d("DIM", " After Load Assign Program Service in txt last stap :6");


                //hideDialog();


                /**
                 *  DOING STRING OPERATION TO AVOID ALLOCATE CACHE MEMORY
                 */

                String errorResult = response.substring(9, 14);

/**
 * If Json String  get False than it return false
 */
                boolean error = !errorResult.equals("false");

                if (!error) {
                    Log.d("TAG", "Before Downloading Dynamic " + "  user_Name:" + user_Name + " pass_word :" + pass_word + " selectedVilJArry:" + selectedVilJArry + "operationMode:" + operationMode);
                    downLoadDynamicData(user_Name, pass_word, selectedVilJArry, operationMode);
      /*              *//**
                     * IF GET NO ERROR  THAN GOTO THE MAIN ACTIVITY
                     */
                } else {
                    // Error in login. Invalid UserName or Password
                    hideDialog();
                    String errorMsg = response.substring(response.indexOf("error_msg") + 11);
                    Toast.makeText(getApplicationContext(),
                            errorMsg, Toast.LENGTH_LONG).show();

                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error + " Stack Tracr = " + error.getStackTrace() + " Detail = " + error.getMessage());
                // hide the mdialog
                hideDialog();
                showAlert("Failed to retrieve data\r\nPlease try again checking your internet connectivity, Username and Password.");


            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("key", "PhEUT5R251");
                params.put("task", "is_down_load_reg_assn_prog");
                params.put("user_name", user_Name);
                params.put("password", pass_word);
                params.put("lay_r_code_j", selectedVilJArry.toString());
                params.put("operation_mode", operationMode);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    /**
     * function to verify login details download RegN AssProgSrv in mysql db
     */
    public void downLoadDynamicData(final String user_Name, final String pass_word, final JSONArray selectedVilJArry, final String operationMode) {
        // Tag used to cancel the request
        String tag_string_req = "req_ass_prog";
        Log.d(TAG, "operationMode: " + operationMode);

        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.API_LINK, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                /***
                 *  IN THIS STRING RESPONSE WRITE THE JSON DATA
                 *
                 */
                AppController.getInstance().getRequestQueue().getCache().clear();
                writeJSONToTextFile(response, DYNAMIC_TABLE);

                Log.d(TAG, " After Loading Dynamic Table in txt last stap :7");


                // hideDialog();


                /**
                 *  DOING STRING OPERATION TO AVOID ALLOCATE CACHE MEMORY
                 */

                String errorResult = response.substring(9, 14);

/**
 * If Json String  get False than it return false
 */
                boolean error = !errorResult.equals("false");

                if (!error) {


                    /**
                     * IF GET NO ERROR  THAN GOTO THE MAIN ACTIVITY
                     */
                    downLoadEnuTable(user_Name, pass_word);

                } else {
                    // Error in login. Invalid UserName or Password
                    String errorMsg = response.substring(response.indexOf("error_msg") + 11);
                    Toast.makeText(getApplicationContext(),
                            errorMsg, Toast.LENGTH_LONG).show();

                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error + " Stack Tracr = " + error.getStackTrace() + " Detail = " + error.getMessage());
                // hide the mdialog
                hideDialog();
                showAlert("Failed to retrieve data\r\nPlease try again checking your internet connectivity, Username and Password.");


            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("key", "PhEUT5R251");
                params.put("task", "is_down_load_dynamic_table");
                params.put("user_name", user_Name);
                params.put("password", pass_word);
                params.put("lay_r_code_j", selectedVilJArry.toString());
                params.put("operation_mode", operationMode);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void downLoadEnuTable(final String user_Name, final String pass_word) {
        // Tag used to cancel the request
        String tag_string_req = "enu";

        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.API_LINK_ENU, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                // clear catch
                AppController.getInstance().getRequestQueue().getCache().clear();
                writeJSONToTextFile(response, ENU_TABLE);

                Log.d(TAG, " After Loading Dynamic Table in txt last stap :7");


                hideDialog();


                /**
                 *  DOING STRING OPERATION TO AVOID ALLOCATE CACHE MEMORY
                 */

                String errorResult = response.substring(9, 14);

/**
 * If Json String  get False than it return false
 */
                boolean error = !errorResult.equals("false");

                if (!error) {


                    /**
                     * IF GET NO ERROR  THAN GOTO THE MAIN ACTIVITY
                     */

                    setLogin(true);        // login success
                    /**
                     *  Launch main activity
                     */
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    setUserID(user_Name);
                    setUserPassword(pass_word);
                    editor.putBoolean(IS_APP_FIRST_RUN, true);
                    editor.commit();

                    startActivity(intent);
                } else {
                    // Error in login. Invalid UserName or Password
                    String errorMsg = response.substring(response.indexOf("error_msg") + 11);
                    Toast.makeText(getApplicationContext(),
                            errorMsg, Toast.LENGTH_LONG).show();

                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error + " Stack Tracr = " + error.getStackTrace() + " Detail = " + error.getMessage());
                // hide the mdialog
                hideDialog();
                showAlert("Failed to retrieve data\r\nPlease try again checking your internet connectivity, Username and Password.");


            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


}
