package com.siddiquinoor.restclient.activity;

/**
 * Activity for presenting the Dashboard of the application
 *
 * @author Siddiqui Noor
 * @desc Technical Director, TechnoDhaka.
 * @link www.SiddiquiNoor.com
 * @version 1.3.0
 * @since 1.0
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.siddiquinoor.restclient.R;
import com.siddiquinoor.restclient.activity.sub_activity.gps_sub.SearchLocation;
import com.siddiquinoor.restclient.controller.AppController;
import com.siddiquinoor.restclient.data_model.AGR_DataModel;
import com.siddiquinoor.restclient.data_model.CTDataModel;
import com.siddiquinoor.restclient.fragments.BaseActivity;
import com.siddiquinoor.restclient.manager.SQLiteHandler;
import com.siddiquinoor.restclient.manager.SyncDatabase;
import com.siddiquinoor.restclient.network.ConnectionDetector;
import com.siddiquinoor.restclient.parse.Parser;
import com.siddiquinoor.restclient.utils.KEY;
import com.siddiquinoor.restclient.utils.UtilClass;
import com.siddiquinoor.restclient.views.adapters.DistributionSaveDataModel;
import com.siddiquinoor.restclient.views.helper.SpinnerHelper;
import com.siddiquinoor.restclient.views.notifications.AlertDialogManager;
import com.siddiquinoor.restclient.views.spinner.SpinnerLoader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    public static final String LIBERIA_COUNTRY_CODE = "0004";


    private final String TAG = MainActivity.class.getSimpleName();


    private SQLiteHandler db;
    // Connection detector class
    ConnectionDetector cd;
    // flag for Internet connection status
    Boolean isInternetPresent = false;

    public static Activity main_activity;
    private View my_view;

    AlertDialogManager alert;


    private TextView txtName;
    private TextView txtEmail;
    private Button btnLogout;
    private Button btnDynamicData;

    private Button btnNewReg;
    private Button btnSummaryRep;
    ///private Button btnViewRec;
    private Button btnSyncRec;
    private SQLiteHandler sqlH;
    private Spinner spCountry;
    private String idCountry;
    private String strCountry;
    private static final String Y = "YES";
    private static final String N = "NO";
    // button declear here
    private Button btnService, btnGPS, btnAssign, btnGraduation, btnCardRequest, btnDistribution, btnGroup;

    private ProgressDialog progressDialog;

    private TextView tvGeoData, tvLastSync, tvSyncRequired, tvOperationMode, tvDeviceId;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_activity = this;
        mContext = this;
        spCountry = (Spinner) findViewById(R.id.spDCountry);// add the spinner
        sqlH = new SQLiteHandler(this); //  it should be other wise it will show null point Exception


        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // connection manager
        cd = new ConnectionDetector(getApplicationContext());
        // find View by ID for all Views
        viewReference();


        SharedPreferences settings;

        settings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        boolean isFirstRun = settings.getBoolean(IS_APP_FIRST_RUN, false);

        showOperationModelabel(settings);
        txtName.setText(getUserName());
        tvLastSync.setText(db.lastSyncStatus());

        /**
         * todo : file e save kori
         */
        String imeiNo = getIMEINumber();

        tvDeviceId.setText(imeiNo);



     /*   btnViewRec = (Button) findViewById(R.id.btnViewRecord);
        btnViewRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iView = new Intent(getApplicationContext(), MW_RegisterViewRecord.class);
                startActivity(iView);
                //main_activity.finish();
            }
        });*/


        // Logout button click event
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });


        buttonSetListener();
        // when the MainActivity run for first time  The JSon Data inject to the
        // SQLite database from text file
        if (isFirstRun) {
            SharedPreferences.Editor editor;
            settings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
            editor = settings.edit();
            new Inject_All_DataIntoSQLite().execute();
            editor.putBoolean(IS_APP_FIRST_RUN, false);
            editor.apply();
        }
        loadCountry();
        setAllButtonDisabled();
        viewAccessController(settings);
        //showOperationModelabel(settings);
/**
 * back up db
 */
        Button restorDb = (Button) findViewById(R.id.btnRestoreDB);
        restorDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newBackupDBfromApp();
            }
        });
    }

    /**
     * this method get the IMEI no
     *
     * @return IMEI no odf device
     */

    private String getIMEINumber() {
        TelephonyManager teMg = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        return teMg.getDeviceId();
    }

    /**
     * this method bring the database front Internal memory
     */
    public void newBackupDBfromApp() {
        try {
            File sd = Environment.getExternalStorageDirectory();
            //   File data = Environment.getDataDirectory();

            String dbBy = getStaffID();
            String dbByName = getUserName();
            String backupDate = getDateTime();
            String backupdbName = "G_path_" + dbBy + "-" + dbByName + "_" + backupDate + ".db";

            if (sd.canWrite()) {
                String currentDBPath = "/data/data/" + getPackageName() + "/databases/pci";
                String backupDBPath = backupdbName;
                File currentDB = new File(currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                    Toast.makeText(getApplicationContext(), "Import Successful! " + backupDB.getAbsolutePath(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Backup Failed!", Toast.LENGTH_SHORT)
                    .show();

        }
    }


    /**
     * <p>
     * This method control the button view with respect to operation
     * </p>
     *
     * @param settings Shared Preference Object
     */

    private void viewAccessController(SharedPreferences settings) {
        int operationMode = settings.getInt(UtilClass.OPERATION_MODE, 0);

        switch (operationMode) {
            case UtilClass.REGISTRATION_OPERATION_MODE:
                btnNewReg.setEnabled(true);
                btnAssign.setEnabled(true);
                //   btnService.setEnabled(true);
//                btnCardRequest.setEnabled(true);
                btnGraduation.setEnabled(true);
                btnGroup.setEnabled(true);


                break;
            case UtilClass.DISTRIBUTION_OPERATION_MODE:
                btnDistribution.setEnabled(true);

                break;
            case UtilClass.SERVICE_OPERATION_MODE:
                btnService.setEnabled(true);

                break;

            case UtilClass.OTHER_OPERATION_MODE:
                btnDynamicData.setEnabled(true);
                btnGPS.setEnabled(true);
                btnSummaryRep.setEnabled(false);
                break;
        }
    }


    private void buttonSetListener() {
        btnNewReg.setOnClickListener(this);
        btnSummaryRep.setOnClickListener(this);
        btnCardRequest.setOnClickListener(this);
        btnSyncRec.setOnClickListener(this);
        btnDistribution.setOnClickListener(this);
        btnService.setOnClickListener(this);
        btnGPS.setOnClickListener(this);
        btnGraduation.setOnClickListener(this);
        btnAssign.setOnClickListener(this);
        btnGroup.setOnClickListener(this);
        btnDynamicData.setOnClickListener(this);

    }

    /**
     * <p>
     * set all button disable first expect syn & summary report button
     * </p>
     */
    private void setAllButtonDisabled() {
        btnNewReg.setEnabled(false);
        btnCardRequest.setEnabled(false);
        btnDistribution.setEnabled(false);
        btnService.setEnabled(false);
        btnGPS.setEnabled(false);
        btnGraduation.setEnabled(false);
        btnAssign.setEnabled(false);
        btnGroup.setEnabled(false);
        btnDynamicData.setEnabled(false);

    }

    private void viewReference() {


        txtName = (TextView) findViewById(R.id.user_name);
        //txtEmail = (TextView) findViewById(R.id.email);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        tvGeoData = (TextView) findViewById(R.id.tv_geo_data_1);
        tvLastSync = (TextView) findViewById(R.id.tv_last_sync);
        tvSyncRequired = (TextView) findViewById(R.id.tv_sync_required);
//        tvOperationMode = (TextView) findViewById(R.id.tv_operation_mode);

        btnNewReg = (Button) findViewById(R.id.btnNewReg);
        btnSummaryRep = (Button) findViewById(R.id.btnSummaryReport);
        btnCardRequest = (Button) findViewById(R.id.btnCardRequest);
        btnGPS = (Button) findViewById(R.id.btnGPS);
        btnSyncRec = (Button) findViewById(R.id.btnSyncRecord);
        btnGraduation = (Button) findViewById(R.id.btnGraduation);
        btnAssign = (Button) findViewById(R.id.btnAssinge);
        btnService = (Button) findViewById(R.id.btnService);
        btnDistribution = (Button) findViewById(R.id.btnDistribution);
        tvLastSync = (TextView) findViewById(R.id.tv_last_sync);
        btnGroup = (Button) findViewById(R.id.btnGroup);
        btnDynamicData = (Button) findViewById(R.id.btnDynamicData);
        tvOperationMode = (TextView) findViewById(R.id.tv_operation_mode);
        tvGeoData = (TextView) findViewById(R.id.tv_geo_data_1);
        tvDeviceId = (TextView) findViewById(R.id.tv_deviceId);


        if (db.selectUploadSyntextRowCount() > 0) {
            tvSyncRequired.setText(Y);
        } else {
            tvSyncRequired.setText(N);
        }

    }

    private void synchronizeData(View v) {
        final AppController globalVariable = (AppController) getApplicationContext();
        globalVariable.setMainViewContext(v);
        MainTask main_task = new MainTask();
        main_task.execute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGroup:

                Intent iGroupSear = new Intent(getApplicationContext(), GroupSearchPage.class);
                iGroupSear.putExtra(KEY.COUNTRY_ID, idCountry);
                iGroupSear.putExtra(KEY.STR_COUNTRY, strCountry);
                startActivity(iGroupSear);

                break;
            case R.id.btnNewReg:
                Intent iReg;
                if (idCountry.equals(LIBERIA_COUNTRY_CODE)) {
                    iReg = new Intent(getApplicationContext(), RegisterLiberia.class);
                    iReg.putExtra("country_code", idCountry);
                    iReg.putExtra("country_name", strCountry);
                    startActivity(iReg);

                } else {
                    iReg = new Intent(getApplicationContext(), Register.class);
                    iReg.putExtra("country_code", idCountry);
                    iReg.putExtra("country_name", strCountry);
                    startActivity(iReg);
                }
                break;
            case R.id.btnSummaryReport:
                Intent iSumm = new Intent(getApplicationContext(), AllSummaryActivity.class);
                iSumm.putExtra(KEY.COUNTRY_ID, idCountry);
                iSumm.putExtra(KEY.STR_COUNTRY, strCountry);
                startActivity(iSumm);
                //main_activity.finish();
                break;
            case R.id.btnSyncRecord:
                my_view = v;
                isInternetPresent = cd.isConnectingToInternet();

                if (isInternetPresent)
                    synchronizeData(my_view);
                else {

                    showAlert("Check your internet connectivity!!");
                }
                SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);
                Date now = new Date();
                String SyncDate = date.format(now);
                db.insertIntoLastSyncTraceStatus(getUserID(), getUserName(), SyncDate);
                //tvSyncRequired = (TextView)findViewById(R.id.tv_sync_required);
                tvSyncRequired.setText(N);
                if (db.lastSyncStatus().equals("")) {
                    tvLastSync.setText("N/A");
                } else {
                    tvLastSync.setText(db.lastSyncStatus());
                }
                break;

            case R.id.btnCardRequest:
                finish();
                Intent iCardR = new Intent(getApplicationContext(), CardRequestActivity.class);
                iCardR.putExtra("ID_COUNTRY", idCountry);
                iCardR.putExtra("STR_COUNTRY", strCountry);
                startActivity(iCardR);
                break;
            case R.id.btnDistribution:
                finish();
                Intent iDist = new Intent(getApplicationContext(), DistributionActivity.class);
                iDist.putExtra(KEY.COUNTRY_ID, idCountry);
                iDist.putExtra(KEY.STR_COUNTRY, strCountry);
                iDist.putExtra(KEY.DIR_CLASS_NAME_KEY, "MainActivity");
                startActivity(iDist);
                break;

            case R.id.btnService:
                finish();
                Intent iSer = new Intent(getApplicationContext(), ServiceActivity.class);
                iSer.putExtra(KEY.COUNTRY_ID, idCountry);
                iSer.putExtra(KEY.STR_COUNTRY, strCountry);
                iSer.putExtra(KEY.DIR_CLASS_NAME_KEY, "MainActivity");
                startActivity(iSer);
                break;
            case R.id.btnGPS:
                //   Intent iMap = new Intent(getApplicationContext(), MapActivity.class);
                finish();
                Intent iMap = new Intent(getApplicationContext(), SearchLocation.class);
                iMap.putExtra(KEY.COUNTRY_ID, idCountry);
                iMap.putExtra(KEY.STR_COUNTRY, strCountry);
                iMap.putExtra(KEY.DIR_CLASS_NAME_KEY, "MainActivity");
                startActivity(iMap);
                break;

            case R.id.btnGraduation:
                finish();
                /**
                 *  when user press the assign page it will take to the member page
                 */

                Intent iMemSearchPage_1 = new Intent(getApplicationContext(), MemberSearchPage.class);
                iMemSearchPage_1.putExtra(KEY.COUNTRY_ID, idCountry);
                iMemSearchPage_1.putExtra(KEY.DIR_CLASS_NAME_KEY, "MainActivity");

                startActivity(iMemSearchPage_1);

                break;

            case R.id.btnAssinge:
                finish();
                /**
                 *  when user press the assign page it will take to the member page
                 */


                Intent iMemSearchPage = new Intent(getApplicationContext(), MemberSearchPage.class);
                iMemSearchPage.putExtra(KEY.COUNTRY_ID, idCountry);
                iMemSearchPage.putExtra(KEY.STR_COUNTRY, strCountry);
                iMemSearchPage.putExtra(KEY.DIR_CLASS_NAME_KEY, "MainActivity");


                startActivity(iMemSearchPage);
                break;
            case R.id.btnDynamicData:
                finish();
                Intent iDynamicData = new Intent(getApplicationContext(), DynamicData.class);
                iDynamicData.putExtra(KEY.COUNTRY_ID, idCountry);

                startActivity(iDynamicData);
                break;
        }

    }

    private class MainTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            try {
                //doSomeTasks();
            } catch (Exception e) {
                Log.e("Async task", "Task Failed " + e);
            }
            return "Success";
        }

        @Override
        protected void onPreExecute() {
            SyncDatabase sync = new SyncDatabase(getApplicationContext(), main_activity);
            sync.startTask();
        }

        ;

        @Override
        protected void onPostExecute(String result) {
            //finalizeSync();
            Log.d(TAG, "Task Finish");
            //SyncDatabase.pDialog.dismiss();
        }


    } // end Asynchronous class MainTask


    /**
     * LOAD :: COUNTRY
     */
    private void loadCountry() {
        SharedPreferences settings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        int operationMode = settings.getInt(UtilClass.OPERATION_MODE, 0);

        SpinnerLoader.loadCountryLoader(mContext, sqlH, spCountry, operationMode, idCountry, strCountry);
        spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strCountry = ((SpinnerHelper) spCountry.getSelectedItem()).getValue();
                idCountry = ((SpinnerHelper) spCountry.getSelectedItem()).getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    } // end Load Spinner

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private class Inject_RegNAssProgSrvDataIntoSQLite extends AsyncTask<Void, Integer, Void> {


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            new Inject_DynamicTableIntoSQLite().execute();

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected Void doInBackground(Void... params) {

            String retrieveData = readDataFromFile("reg_ass_prog_srv_data");
            try {


                int size;

                JSONObject jObj = new JSONObject(retrieveData);

                if (!jObj.isNull(Parser.REG_M_ASSIGN_PROG_SRV_JSON_A)) {
                    Parser.regNAssignProgSrvParser(jObj.getJSONArray(Parser.REG_M_ASSIGN_PROG_SRV_JSON_A), db);

                }


                publishProgress(++progressIncremental);
                if (!jObj.isNull(Parser.REGN_PW_JSON_A)) {
                    Parser.regNPWParser(jObj.getJSONArray(Parser.REGN_PW_JSON_A), db);

                }


                publishProgress(++progressIncremental);
                if (!jObj.isNull(Parser.REGN_LM_JSON_A)) {
                    Parser.regNLMParser(jObj.getJSONArray(Parser.REGN_LM_JSON_A), db);

                }


                publishProgress(++progressIncremental);
                if (!jObj.isNull(Parser.REGN_CU_2_JSON_A)) {
                    Parser.regNCU2Parser(jObj.getJSONArray(Parser.REGN_CU_2_JSON_A), db);

                }


                publishProgress(++progressIncremental);
                if (!jObj.isNull(Parser.REGN_CA_2)) {
                    Parser.RegN_CA2Parser(jObj.getJSONArray(Parser.REGN_CA_2), db);
                }


                publishProgress(++progressIncremental);
                if (!jObj.isNull(Parser.REG_N_AGR_JSON_A)) {


                    JSONArray reg_n_agr_tableDatas = jObj.getJSONArray(Parser.REG_N_AGR_JSON_A);
                    size = reg_n_agr_tableDatas.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject reg_n_agr_tableData = reg_n_agr_tableDatas.getJSONObject(i);
                        AGR_DataModel data = new AGR_DataModel();
                        data.setCountryCode(reg_n_agr_tableData.getString(Parser.ADM_COUNTRY_CODE));

                        data.setDistrictCode(reg_n_agr_tableData.getString(Parser.LAY_R_1_LIST_CODE));
                        data.setUpazillaCode(reg_n_agr_tableData.getString(Parser.LAY_R_2_LIST_CODE));
                        data.setUnitCode(reg_n_agr_tableData.getString(Parser.LAY_R_3_LIST_CODE));
                        data.setVillageCode(reg_n_agr_tableData.getString(Parser.LAY_R_4_LIST_CODE));
                        data.setHhId(reg_n_agr_tableData.getString(Parser.HHID));

                        data.setHhMemId(reg_n_agr_tableData.getString(Parser.MEM_ID));
                        data.setRegnDate(reg_n_agr_tableData.getString(Parser.REG_N_DATE));
                        data.setElderleyYN(reg_n_agr_tableData.getString(Parser.ELDERLY_YN));
                        data.setLandSize(reg_n_agr_tableData.getString(Parser.LAND_SIZE));
                        data.setDepenonGanyu(reg_n_agr_tableData.getString(Parser.DEPEND_ON_GANYU));
                        data.setWillingness(reg_n_agr_tableData.getString(Parser.WILLINGNESS));
                        data.setWinterCultivation(reg_n_agr_tableData.getString(Parser.WINTER_CULTIVATION));
                        data.setVulnerableHh(reg_n_agr_tableData.getString(Parser.VULNERABLE_HH));
                        data.setPlantingVcrop(reg_n_agr_tableData.getString(Parser.PLANTING_VALUE_CHAIN_CROP));


                        String AGOINVC = reg_n_agr_tableData.getString("AGOINVC");
                        String AGONASFAM = reg_n_agr_tableData.getString("AGONASFAM");
                        String AGOCU = reg_n_agr_tableData.getString("AGOCU");
                        String AGOOther = reg_n_agr_tableData.getString("AGOOther");
                        int LSGoat = Integer.parseInt(reg_n_agr_tableData.getString("LSGoat"));
                        int LSChicken = Integer.parseInt(reg_n_agr_tableData.getString("LSChicken"));
                        int LSPigeon = Integer.parseInt(reg_n_agr_tableData.getString("LSPigeon"));
                        int LSOther = Integer.parseInt(reg_n_agr_tableData.getString("LSOther"));


                        db.addInRegNAgrTableFromOnline(data, AGOINVC, AGONASFAM, AGOCU, AGOOther, LSGoat, LSChicken, LSPigeon
                                , LSOther);

                    }
                }
                publishProgress(++progressIncremental);


                if (!jObj.isNull(Parser.REG_N_CT_JSON_A)) {
                    JSONArray reg_n_ct_tableDatas = jObj.getJSONArray(Parser.REG_N_CT_JSON_A);
                    size = reg_n_ct_tableDatas.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject reg_n_ct_tableData = reg_n_ct_tableDatas.getJSONObject(i);
                        CTDataModel data = new CTDataModel();
                        data.setCountryCode(reg_n_ct_tableData.getString(Parser.ADM_COUNTRY_CODE));
                        data.setDistrictCode(reg_n_ct_tableData.getString(Parser.LAY_R_1_LIST_CODE));
                        data.setUpazillaCode(reg_n_ct_tableData.getString(Parser.LAY_R_2_LIST_CODE));
                        data.setUnitCode(reg_n_ct_tableData.getString(Parser.LAY_R_3_LIST_CODE));
                        data.setVillageCode(reg_n_ct_tableData.getString(Parser.LAY_R_4_LIST_CODE));
                        data.setHhId(reg_n_ct_tableData.getString(Parser.HHID));
                        data.setMemID(reg_n_ct_tableData.getString(Parser.MEM_ID));
                        data.setC11CtPr(reg_n_ct_tableData.getString(Parser.C_11_CT_PR));
                        data.setC21CtPr(reg_n_ct_tableData.getString(Parser.C_21_CT_PR));
                        data.setC31CtPr(reg_n_ct_tableData.getString(Parser.C_31_CT_PR));
                        data.setC32CtPr(reg_n_ct_tableData.getString(Parser.C_32_CT_PR));
                        data.setC33CtPr(reg_n_ct_tableData.getString(Parser.C_33_CT_PR));
                        data.setC34CtPr(reg_n_ct_tableData.getString(Parser.C_34_CT_PR));
                        data.setC35CtPr(reg_n_ct_tableData.getString(Parser.C_35_CT_PR));
                        data.setC36CtPr(reg_n_ct_tableData.getString(Parser.C_36_CT_PR));
                        data.setC37CtPr(reg_n_ct_tableData.getString(Parser.C_37_CT_PR));
                        data.setC38CtPr(reg_n_ct_tableData.getString(Parser.C_38_CT_PR));


                        db.addMemIntoCT_Table(data);

                    }
                }


                if (!jObj.isNull("reg_n_ffa")) {
                    Parser.reg_N_FFAParser(jObj.getJSONArray("reg_n_ffa"), db);
                }

                if (!jObj.isNull("reg_n_we")) {
                    Parser.reg_N_WEParser(jObj.getJSONArray("reg_n_we"), db);
                }


            } catch (Exception e) {
                Log.e(TAG, "Exception : " + e);
                e.printStackTrace();
            }
            return null;

        }
    }


    private class Inject_DynamicTableIntoSQLite extends AsyncTask<Void, Integer, Void> {


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            new Inject_EnuTableIntoSQLite().execute();

        }

        @Override
        protected Void doInBackground(Void... params) {


            /**
             * Read JSON DATA  from the text file
             * */
            String retrieveData = readDataFromFile(LoginActivity.DYNAMIC_TABLE);

            try {

                /**                 * The total string Convert into JSON object                 * */

                JSONObject jObj = new JSONObject(retrieveData);

                publishProgress(++progressIncremental);

                if (!jObj.isNull("D_T_answer")) {
                    Parser.DTA_Parser(jObj.getJSONArray("D_T_answer"), db);
                }

                if (!jObj.isNull("D_T_basic")) {
                    Parser.DTBasicParser(jObj.getJSONArray("D_T_basic"), db);
                }

                if (!jObj.isNull("D_T_category")) {
                    Parser.DTCategoryParser(jObj.getJSONArray("D_T_category"), db);
                }

                if (!jObj.isNull("D_T_CountryProgram")) {
                    Parser.DTCountryProgramParser(jObj.getJSONArray("D_T_CountryProgram"), db);
                }


                if (!jObj.isNull("D_T_GeoListLevel")) {
                    Parser.DTGeoListLevelParser(jObj.getJSONArray("D_T_GeoListLevel"), db);
                }


                if (!jObj.isNull("D_T_QTable")) {
                    Parser.DTQTableParser(jObj.getJSONArray("D_T_QTable"), db);
                }

                if (!jObj.isNull("D_T_QResMode")) {
                    Parser.DTQResModeParser(jObj.getJSONArray("D_T_QResMode"), db);
                }

                if (!jObj.isNull("D_T_ResponseTable")) {
                    Parser.DTResponseTableParser(jObj.getJSONArray("D_T_ResponseTable"), db);
                }

                if (!jObj.isNull("D_T_TableDefinition")) {
                    Parser.DTTableDefinitionParser(jObj.getJSONArray("D_T_TableDefinition"), db);
                }

                if (!jObj.isNull("D_T_TableListCategory")) {
                    Parser.DTTableListCategoryParser(jObj.getJSONArray("D_T_TableListCategory"), db);
                }

                if (!jObj.isNull("D_T_LUP")) {
                    Parser.DTLUPParser(jObj.getJSONArray("D_T_LUP"), db);
                }


            } catch (Exception e) {
                Log.e(TAG, "Exception : " + e);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(values[0]);
        }
    }

    private class Inject_EnuTableIntoSQLite extends AsyncTask<Void, Integer, Void> {


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            hideDialog();
            loadCountry();

            // set the user name
            txtName.setText(getUserName());
        }

        @Override
        protected Void doInBackground(Void... params) {


            /**             * Read JSON DATA  from the text file             **/
            String retrieveData = readDataFromFile(LoginActivity.ENU_TABLE);

            try {


                /**                 * The total string Convert into JSON object                 * */

                JSONObject jObj = new JSONObject(retrieveData);
                publishProgress(++progressIncremental);

                if (!jObj.isNull("enu_table")) {
                    Parser.DTEnu_Parser(jObj.getJSONArray("enu_table"), db);
                }

            } catch (Exception e) {
                Log.e(TAG, "Exception : " + e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(values[0]);
        }
    }


    private class Inject_Reg_HouseH_DataIntoSQLite extends AsyncTask<Void, Integer, Void> {


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            new Inject_Service_DataIntoSQLite().execute();

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected Void doInBackground(Void... params) {

            String receiveData = readDataFromFile(LoginActivity.REG_HOUSE_HOLD_DATA);
            /**             * the parsing held by other class             */

            Parser.RegistrationNHHParser(receiveData, db);


            return null;
        }
    }

    private class Inject_Service_DataIntoSQLite extends AsyncTask<Void, Integer, Void> {


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // insert assigne data
            new Inject_Reg_Member_DataIntoSQLite().execute();

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected Void doInBackground(Void... params) {

            String retreiveData = readDataFromFile(LoginActivity.SERVICE_DATA);


            /**
             * The total string Convert into JSON object
             * */

            try {
                JSONObject jObj = new JSONObject(retreiveData);

                if (!jObj.isNull(Parser.SERVICE_TABLE_JSON_A)) {
                    JSONArray services_data = jObj.getJSONArray(Parser.SERVICE_TABLE_JSON_A);
                    publishProgress(++progressIncremental);
                    Parser.SrvTableParser(services_data, db);
                }


                if (!jObj.isNull("service_exe_table")) {// this is not servie
                    JSONArray services_exe_table = jObj.getJSONArray("service_exe_table");

                    publishProgress(++progressIncremental);
                    Parser.SrvExtTableParser(services_exe_table, db);
                }
            } catch (Exception e) {
                Log.d(TAG, "Exception : " + e);
                e.printStackTrace();
            }


            return null;
        }
    }

    /**
     * inject Reg member data
     */

    private class Inject_Reg_Member_DataIntoSQLite extends AsyncTask<Void, Integer, Void> {


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            new Inject_Reg_Member_Prog_Grp_DataIntoSQLite().execute();

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected Void doInBackground(Void... params) {

            String retreiveData = readDataFromFile(LoginActivity.REG_MEMBER_DATA);
            Parser.RegNMemberParser(retreiveData, db);
            publishProgress(++progressIncremental);

            return null;
        }
    }


    /**
     * inject Reg member Program  Group data
     */

    private class Inject_Reg_Member_Prog_Grp_DataIntoSQLite extends AsyncTask<Void, Integer, Void> {


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            new Inject_RegNAssProgSrvDataIntoSQLite().execute();

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected Void doInBackground(Void... params) {

            String retreiveData = readDataFromFile(LoginActivity.REG_MEMBER_PROG_GROUP_DATA);
// todo change the  structure
            Parser.RegNMemProGrpParser(retreiveData, db);
            publishProgress(++progressIncremental);

            Parser.GpsLocationContentParser(retreiveData, db);
            publishProgress(++progressIncremental);

            Parser.SrvSpecificTableParser(retreiveData, db);
            publishProgress(++progressIncremental);


            return null;
        }
    }

    private int progressIncremental;


    private class Inject_All_DataIntoSQLite extends AsyncTask<Void, Integer, Void> {


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            /**
             * Read JSON DATA  from the text file
             * */
            String retrieveData = readDataFromFile(LoginActivity.ALL_DATA);

            try {
                int size;
                /**
                 * The total string Convert into JSON object
                 * */

                JSONObject jObj = new JSONObject(retrieveData);

                String user_id = jObj.getString(Parser.USR_ID);
                JSONObject user = jObj.getJSONObject(Parser.USER_JSON_A);
                String country_code = user.getString(Parser.ADM_COUNTRY_CODE);
                String login_name = user.getString(Parser.USR_LOG_IN_NAME);
                String login_pw = user.getString(Parser.USR_LOG_IN_PW);
                String first_name = user.getString(Parser.USR_FIRST_NAME);
                String last_name = user.getString(Parser.USR_LAST_NAME);
                String email = user.getString(Parser.USR_EMAIL);
                String email_verification = user.getString(Parser.USR_EMAIL_VERIFICATION);
                String user_status = user.getString(Parser.USR_STATUS);
                String entry_by = user.getString(Parser.ENTRY_BY);
                String entry_date = user.getString(Parser.ENTRY_DATE);

                setUserName(first_name); // Setting User hhName into session
                setStaffID(user_id); // Setting Staff ID to use when sync data


                setUserCountryCode(country_code); // Setting Country code


                db.addUser(user_id, country_code, login_name, login_pw, first_name, last_name, email, email_verification, user_status, entry_by, entry_date);
//                Log.d("MOR_12",user_id +  country_code +  login_name +  login_pw +  first_name +  last_name +  email +  email_verification +  user_status +  entry_by +  entry_date);


                publishProgress(++progressIncremental);


                if (!jObj.isNull(Parser.COUNTRIES_JSON_A)) {

                    Parser.AdmCountryParser(jObj.getJSONArray(Parser.COUNTRIES_JSON_A), db);
                }


                publishProgress(++progressIncremental);


                if (!jObj.isNull(Parser.VALID_DATES_JSON_A)) {
                    JSONArray valid_dates = jObj.getJSONArray(Parser.VALID_DATES_JSON_A);
                    size = valid_dates.length();

                    for (int i = 0; i < size; i++) {
                        JSONObject valid_date = valid_dates.getJSONObject(i);
                        String AdmCountryCode = valid_date.getString(Parser.ADM_COUNTRY_CODE);
                        String StartDate = valid_date.getString(Parser.START_DATE);
                        String EndDate = valid_date.getString(Parser.END_DATE);

                        db.addValidDateRange(AdmCountryCode, StartDate, EndDate);


                    }
                }
                publishProgress(++progressIncremental);
                if (!jObj.isNull(Parser.GPS_GROUP_JSON_A)) {
                    Parser.gpsGroupParser(jObj.getJSONArray(Parser.GPS_GROUP_JSON_A), db);
                }

                publishProgress(++progressIncremental);
                if (!jObj.isNull(Parser.GPS_SUBGROUP_JSON_A)) {
                    Parser.gpsSubGroupParser(jObj.getJSONArray(Parser.GPS_SUBGROUP_JSON_A), db);
                }

                publishProgress(++progressIncremental);
                if (!jObj.isNull(Parser.GPS_LOCATION_JSON_A)) {
                    Parser.gpsLocationParse(jObj.getJSONArray(Parser.GPS_LOCATION_JSON_A), db);
                }

                publishProgress(++progressIncremental);
                if (!jObj.isNull(Parser.ADM_COUNTRY_AWARD_JSON_A)) {
                    Parser.admCountryAwardParser(jObj.getJSONArray(Parser.ADM_COUNTRY_AWARD_JSON_A), db);
                }
                publishProgress(++progressIncremental);
                if (!jObj.isNull(Parser.ADM_AWARD_JSON_A)) {
                    Parser.admAwardParser(jObj.getJSONArray(Parser.ADM_AWARD_JSON_A), db);
                }


                publishProgress(++progressIncremental);
                if (!jObj.isNull(Parser.ADM_DONOR_JSON_A)) {
                    Parser.admDonorParser(jObj.getJSONArray(Parser.ADM_DONOR_JSON_A), db);
                }

                publishProgress(++progressIncremental);
                if (!jObj.isNull(Parser.ADM_PROGRAM_MASTER_JSON_A)) {
                    Parser.admProgramMasterParser(jObj.getJSONArray(Parser.ADM_PROGRAM_MASTER_JSON_A), db);
                }

                publishProgress(++progressIncremental);
                if (!jObj.isNull(Parser.ADM_SERVICE_MASTER_JSON_A)) {
                    Parser.admServiceMasterParser(jObj.getJSONArray(Parser.ADM_SERVICE_MASTER_JSON_A), db);

                }

                publishProgress(++progressIncremental);
                //adm_op_month


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
                        String StartDate = adm_op_month.getString(Parser.START_DATE);
                        String EndDate = adm_op_month.getString(Parser.END_DATE);

                        String UsaStartDate = adm_op_month.getString(Parser.USA_START_DATE);
                        String UsaEndDate = adm_op_month.getString(Parser.USA_END_DATE);
                        String Status = adm_op_month.getString("Status");
                        db.addOpMonthFromOnline(AdmCountryCode, AdmDonorCode, AdmAwardCode, OpCode, OpMonthCode, MonthLabel, StartDate, EndDate, UsaStartDate, UsaEndDate, Status);


                    }
                }

                publishProgress(++progressIncremental);


                if (!jObj.isNull(Parser.ADM_COUNTRY_PROGRAM_JSON_A)) {
                    JSONArray adm_country_programs = jObj.getJSONArray(Parser.ADM_COUNTRY_PROGRAM_JSON_A);
                    size = adm_country_programs.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject adm_country_program = adm_country_programs.getJSONObject(i);
                        String AdmCountryCode = adm_country_program.getString(Parser.ADM_COUNTRY_CODE);
                        String AdmDonorCode = adm_country_program.getString(Parser.ADM_DONOR_CODE);
                        String AdmAwardCode = adm_country_program.getString(Parser.ADM_AWARD_CODE);
                        String AdmProgCode = adm_country_program.getString(Parser.ADM_PROG_CODE);
                        String AdmSrvCode = adm_country_program.getString(Parser.ADM_SRV_CODE);
                        String ProgFlag = adm_country_program.getString("ProgFlag");
                        String FoodFlag = adm_country_program.getString(Parser.FOOD_FLAG);
                        String NFoodFlag = adm_country_program.getString(Parser.N_FOOD_FLAG);
                        String CashFlag = adm_country_program.getString(Parser.CASH_FLAG);
                        String VOFlag = adm_country_program.getString(Parser.VO_FLAG);
                        String DefaultFoodDays = adm_country_program.getString(Parser.DEFAULT_FOOD_DAYS);
                        String DefaultNFoodDays = adm_country_program.getString(Parser.DEFAULT_N_FOOD_DAYS);
                        String DefaultCashDays = adm_country_program.getString(Parser.DEFAULT_CASH_DAYS);
                        String DefaultVODays = adm_country_program.getString(Parser.DEFAULT_VO_DAYS);
                        String SrvSpecific = adm_country_program.getString(Parser.SRV_SPECIFIC);

                        db.insertAdmCountryProgram(AdmCountryCode, AdmDonorCode, AdmAwardCode, AdmProgCode, AdmSrvCode, ProgFlag, FoodFlag, NFoodFlag, CashFlag, VOFlag, DefaultFoodDays, DefaultNFoodDays, DefaultCashDays, DefaultVODays, SrvSpecific);


                    }
                }

                publishProgress(++progressIncremental);


                // * Adding data into  dob_service_center  Table


                if (!jObj.isNull(Parser.DOB_SERVICE_CENTER_JSON_A)) {// this is not servie
                    JSONArray dob_service_centers = jObj.getJSONArray(Parser.DOB_SERVICE_CENTER_JSON_A);
                    size = dob_service_centers.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject dob_service_center = dob_service_centers.getJSONObject(i);

                        String AdmCountryCode = dob_service_center.getString(Parser.ADM_COUNTRY_CODE);
                        String SrvCenterCode = dob_service_center.getString(Parser.SRV_CENTER_CODE);
                        String SrvCenterName = dob_service_center.getString(Parser.SRV_CENTER_NAME);
                        // String SrvCenterAddress = dob_service_center.getString("SrvCenterAddress");
                        //   String SrvCenterCatCode = dob_service_center.getString("SrvCenterCatCode");

                        String FDPCode = dob_service_center.getString(Parser.FDP_CODE);


                        db.addServiceCenter(AdmCountryCode, SrvCenterCode, SrvCenterName, FDPCode);

//                        Log.d("NIR1", "In Service Center Table - AdmCountryCode :" + AdmCountryCode + " SrvCenterCode : " + SrvCenterCode + " SrvCenterName : " + SrvCenterName);
                        //+ " SrvCenterAddress : " + SrvCenterAddress + " SrvCenterCatCode  : " + SrvCenterCatCode + " FDPCode  : " + FDPCode );
                    }
                }
                publishProgress(++progressIncremental);


                if (!jObj.isNull(Parser.STAFF_ACCESS_INFO_JSON_A)) {// this is not servie
                    JSONArray staff_access_info_accesses = jObj.getJSONArray(Parser.STAFF_ACCESS_INFO_JSON_A);
                    size = staff_access_info_accesses.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject staff_access_info_access = staff_access_info_accesses.getJSONObject(i);

                        String StfCode = staff_access_info_access.getString(Parser.STF_CODE);
                        String AdmCountryCode = staff_access_info_access.getString(Parser.ADM_COUNTRY_CODE);
                        String AdmDonorCode = staff_access_info_access.getString(Parser.ADM_DONOR_CODE);
                        String AdmAwardCode = staff_access_info_access.getString(Parser.ADM_AWARD_CODE);
                        String LayRListCode = staff_access_info_access.getString(Parser.LAY_R_LIST_CODE);
                        String btnNew = staff_access_info_access.getString(Parser.BTN_NEW1);
                        String btnSave = staff_access_info_access.getString(Parser.BTN_SAVE);
                        String btnDel = staff_access_info_access.getString(Parser.BTN_DEL);
                        String btnPepr = staff_access_info_access.getString(Parser.BTN_PEPR);
                        String btnAprv = staff_access_info_access.getString(Parser.BTN_APRV);
                        String btnRevw = staff_access_info_access.getString(Parser.BTN_REVW);
                        String btnVrfy = staff_access_info_access.getString(Parser.BTN_VRFY);
                        String btnDTran = staff_access_info_access.getString(Parser.BTN_D_TRAN);


                        //String FDPCode = dbo_staff_geo_info_access.getString("FDPCode");
                        String disCode = LayRListCode.substring(0, 2);
                        String upCode = LayRListCode.substring(2, 4);
                        String unCode = LayRListCode.substring(4, 6);
                        String vCode = LayRListCode.substring(6);
                        db.addStaffGeoAccessInfoFromOnline(StfCode, AdmCountryCode, AdmDonorCode, AdmAwardCode, LayRListCode, disCode, upCode, unCode, vCode, btnNew, btnSave, btnDel, btnPepr, btnAprv, btnRevw, btnVrfy, btnDTran);//, SrvCenterCatCode, FDPCode);


                        //  Log.d(TAG, "In addStaffGeoAccessInfoFromOnline Table- StfCode :" + StfCode + " AdmCountryCode : " + AdmCountryCode + " AdmDonorCode : " + AdmDonorCode +               " AdmAwardCode : " + AdmAwardCode + " LayRListCode  : " + LayRListCode);// + " FDPCode  : " + FDPCode );
                    }
                }


                if (!jObj.isNull(Parser.LB_REG_HH_CATEGORY_JSON_A)) {
                    JSONArray lb_reg_hh_categorys = jObj.getJSONArray(Parser.LB_REG_HH_CATEGORY_JSON_A);
                    size = lb_reg_hh_categorys.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject lb_reg_hh_category = lb_reg_hh_categorys.getJSONObject(i);
                        String AdmCountryCode = lb_reg_hh_category.getString(Parser.ADM_COUNTRY_CODE);
                        String HHHeadCatCode = lb_reg_hh_category.getString(Parser.HH_HEAD_CAT_CODE);
                        String CatName = lb_reg_hh_category.getString(Parser.CAT_NAME);
                        db.addHHCategory(AdmCountryCode, HHHeadCatCode, CatName);


                        // Log.d(TAG, "In House hold Category Table: AdmCountryCode : " + AdmCountryCode + " HHHeadCatCode : " + HHHeadCatCode +" SubGrpName : " + SubGrpName + " Description : " + Description  );
                    }
                }

                publishProgress(++progressIncremental);


                if (!jObj.isNull(Parser.REG_LUP_GRADUATION_JSON_A)) {
                    JSONArray reg_lup_graduations = jObj.getJSONArray(Parser.REG_LUP_GRADUATION_JSON_A);
                    size = reg_lup_graduations.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject reg_lup_graduation = reg_lup_graduations.getJSONObject(i);

                        String AdmProgCode = reg_lup_graduation.getString(Parser.ADM_PROG_CODE);
                        String AdmSrvCode = reg_lup_graduation.getString(Parser.ADM_SRV_CODE);
                        String GRDCode = reg_lup_graduation.getString(Parser.GRD_CODE);
                        String GRDTitle = reg_lup_graduation.getString(Parser.GRD_TITLE);
                        String DefaultCatActive = reg_lup_graduation.getString(Parser.DEFAULT_CAT_ACTIVE);
                        String DefaultCatExit = reg_lup_graduation.getString(Parser.DEFAULT_CAT_EXIT);


                        db.addGraduation(AdmProgCode, AdmSrvCode, GRDCode, GRDTitle, DefaultCatActive, DefaultCatExit);


                        // Log.d(TAG, "In reg_lup_graduation: AdmProgCode : " + AdmProgCode + " AdmSrvCode : " + AdmSrvCode + " GRDCode : " + GRDCode + " GRDTitle : " + GRDTitle + " DefaultCatActive : " + DefaultCatActive+ " DefaultCatExit : " + DefaultCatExit   );
                    }
                }
                publishProgress(++progressIncremental);
                // Adding data into Layer Label Table
                if (!jObj.isNull(Parser.LAYER_LABELS_JSON_A)) {
                    JSONArray layer_labels = jObj.getJSONArray(Parser.LAYER_LABELS_JSON_A);
                    size = layer_labels.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject layer_label = layer_labels.getJSONObject(i);

                        String AdmCountryCode = layer_label.getString(Parser.ADM_COUNTRY_CODE);
                        String GeoLayRCode = layer_label.getString(Parser.GEO_LAY_R_CODE);
                        String GeoLayRName = layer_label.getString(Parser.GEO_LAY_R_NAME);
                        db.addLayerLabel(AdmCountryCode, GeoLayRCode, GeoLayRName);


                    }
                }
                publishProgress(++progressIncremental);

                // Adding data into District Table
                if (!jObj.isNull(Parser.DISTRICT)) {
                    JSONArray district = jObj.getJSONArray(Parser.DISTRICT);
                    size = district.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject dist = district.getJSONObject(i);

                        String AdmCountryCode = dist.getString(Parser.ADM_COUNTRY_CODE);
                        String GeoLayRCode = dist.getString(Parser.GEO_LAY_R_CODE);
                        String LayRListCode = dist.getString(Parser.LAY_R_LIST_CODE);
                        String LayRListName = dist.getString(Parser.LAY_R_LIST_NAME);

                        db.addDistrict(AdmCountryCode, GeoLayRCode, LayRListCode, LayRListName);


                    }
                }
                publishProgress(++progressIncremental);
                // Adding data into Upazilla Table
                if (!jObj.isNull(Parser.UPAZILLA)) {

                    JSONArray upazilla = jObj.getJSONArray(Parser.UPAZILLA);

                    size = upazilla.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject up = upazilla.getJSONObject(i);

                        String AdmCountryCode = up.getString(Parser.ADM_COUNTRY_CODE);
                        String GeoLayRCode = up.getString(Parser.GEO_LAY_R_CODE);
                        String LayR1ListCode = up.getString(Parser.LAY_R_1_LIST_CODE);
                        String LayR2ListCode = up.getString(Parser.LAY_R_2_LIST_CODE);
                        String LayR2ListName = up.getString(Parser.LAY_R_2_LIST_NAME);

                        db.addUpazilla(AdmCountryCode, GeoLayRCode, LayR1ListCode, LayR2ListCode, LayR2ListName);

                        //  Log.d(TAG, "AdmCountryCode : " + AdmCountryCode + " LayR1ListCode : " + LayR1ListCode + " LayR2ListCode : " + LayR2ListCode + " LayR2ListName : " + LayR2ListName);
                    }
                }
                publishProgress(++progressIncremental);

                // Adding data into Unit Table
                if (!jObj.isNull(Parser.UNIT_JSON_A)) {

                    JSONArray unit = jObj.getJSONArray(Parser.UNIT_JSON_A);
                    size = unit.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject un = unit.getJSONObject(i);

                        String AdmCountryCode = un.getString(Parser.ADM_COUNTRY_CODE);
                        String GeoLayRCode = un.getString(Parser.GEO_LAY_R_CODE);
                        String LayR1ListCode = un.getString(Parser.LAY_R_1_LIST_CODE);
                        String LayR2ListCode = un.getString(Parser.LAY_R_2_LIST_CODE);
                        String LayR3ListCode = un.getString(Parser.LAY_R_3_LIST_CODE);
                        String LayR3ListName = un.getString(Parser.LAY_R_3_LIST_NAME);

                        db.addUnit(AdmCountryCode, GeoLayRCode, LayR1ListCode, LayR2ListCode, LayR3ListCode, LayR3ListName);

                        // Log.d(TAG, "AdmCountryCode : " + AdmCountryCode + " LayR1ListCode : " + LayR1ListCode + " LayR2ListCode : " + LayR2ListCode + " LayR3ListCode : " + LayR3ListCode + " LayR3ListName : " + LayR3ListName);
                    }
                }
                publishProgress(++progressIncremental);
                // Adding data into Village Table
                if (!jObj.isNull(Parser.VILLAGE_JSON_A)) {

                    JSONArray village = jObj.getJSONArray(Parser.VILLAGE_JSON_A);

                    size = village.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject vil = village.getJSONObject(i);

                        String AdmCountryCode = vil.getString(Parser.ADM_COUNTRY_CODE);
                        String GeoLayRCode = vil.getString(Parser.GEO_LAY_R_CODE);
                        String LayR1ListCode = vil.getString(Parser.LAY_R_1_LIST_CODE);
                        String LayR2ListCode = vil.getString(Parser.LAY_R_2_LIST_CODE);
                        String LayR3ListCode = vil.getString(Parser.LAY_R_3_LIST_CODE);
                        String LayR4ListCode = vil.getString(Parser.LAY_R_4_LIST_CODE);
                        String LayR4ListName = vil.getString(Parser.LAY_R_4_LIST_NAME);
                        String HHCount = vil.getString(Parser.HH_COUNT);

                        db.addVillage(AdmCountryCode, GeoLayRCode, LayR1ListCode, LayR2ListCode, LayR3ListCode, LayR4ListCode, LayR4ListName, HHCount);


                    }
                }
                publishProgress(++progressIncremental);

                // Adding data into Relation Table
                if (!jObj.isNull(Parser.RELATION_JSON_A)) {

                    JSONArray relation = jObj.getJSONArray(Parser.RELATION_JSON_A);

                    size = relation.length();

                    for (int i = 0; i < size; i++) {

                        JSONObject rel = relation.getJSONObject(i);

                        String Relation_Code = rel.getString(Parser.HH_RELATION_CODE);
                        String RelationName = rel.getString(Parser.RELATION_NAME);

                        db.addRelation(Relation_Code, RelationName);

//                        Log.d(TAG, "Relation_Code : " + Relation_Code + " RelationName : " + RelationName);
                    }
                }
                publishProgress(++progressIncremental);


                if (!jObj.isNull(Parser.REPORT_TEMPLATE)) {
                    JSONArray report_templates = jObj.getJSONArray(Parser.REPORT_TEMPLATE);
                    size = report_templates.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject report_template = report_templates.getJSONObject(i);

                        String AdmCountryCode = report_template.getString(Parser.ADM_COUNTRY_CODE);
                        String RptLabel = report_template.getString(Parser.RPT_LABEL);
                        String Code = report_template.getString(Parser.RPT_G_N_CODE);

                        db.addCardType(AdmCountryCode, RptLabel, Code);

//                        Log.d(TAG, "In Report Template Table: RptLabel : " + RptLabel + " Code : " + Code);
                    }
                }

                publishProgress(++progressIncremental);


                if (!jObj.isNull(Parser.CARD_PRINT_REASON)) {
                    JSONArray card_print_reasons = jObj.getJSONArray(Parser.CARD_PRINT_REASON);
                    size = card_print_reasons.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject card_print_reason = card_print_reasons.getJSONObject(i);

                        String ReasonCode = card_print_reason.getString(Parser.REASON_CODE);
                        String ReasonTitle = card_print_reason.getString(Parser.REASON_TITLE);

                        db.addCardPrintReason(ReasonCode, ReasonTitle);

//                        Log.d(TAG, "In Card Reason Table: ReasonCode : " + ReasonCode + " ReasonTitle : " + ReasonTitle);
                    }
                }
//                publishProgress(33);
                publishProgress(++progressIncremental);


                if (!jObj.isNull(Parser.REG_MEM_CARD_REQUEST_JSON_A)) {
                    JSONArray reg_mem_card_requests = jObj.getJSONArray(Parser.REG_MEM_CARD_REQUEST_JSON_A);
                    size = reg_mem_card_requests.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject reg_mem_card_request = reg_mem_card_requests.getJSONObject(i);

                        String AdmCountryCode = reg_mem_card_request.getString(Parser.ADM_COUNTRY_CODE);
                        String AdmDonorCode = reg_mem_card_request.getString(Parser.ADM_DONOR_CODE);
                        String AdmAwardCode = reg_mem_card_request.getString(Parser.ADM_AWARD_CODE);
                        String LayR1ListCode = reg_mem_card_request.getString(Parser.LAY_R_1_LIST_CODE);
                        String LayR2ListCode = reg_mem_card_request.getString(Parser.LAY_R_2_LIST_CODE);
                        String LayR3ListCode = reg_mem_card_request.getString(Parser.LAY_R_3_LIST_CODE);
                        String LayR4ListCode = reg_mem_card_request.getString(Parser.LAY_R_4_LIST_CODE);
                        String HHID = reg_mem_card_request.getString(Parser.HHID);
                        String MemID = reg_mem_card_request.getString(Parser.MEM_ID);
                        String RptGroup = reg_mem_card_request.getString(Parser.RPT_GROUP);
                        String RptCode = reg_mem_card_request.getString(Parser.RPT_CODE);
                        String RequestSL = reg_mem_card_request.getString(Parser.REQUEST_SL);
                        String ReasonCode = reg_mem_card_request.getString(Parser.REASON_CODE);
                        String RequestDate = reg_mem_card_request.getString(Parser.REQUEST_DATE);
                        String PrintDate = reg_mem_card_request.getString(Parser.PRINT_DATE);
                        String PrintBy = reg_mem_card_request.getString(Parser.PRINT_BY);
                        String DeliveryDate = reg_mem_card_request.getString(Parser.DELIVERY_DATE);
                        String DeliveredBy = reg_mem_card_request.getString(Parser.DELIVERED_BY);
                        String DelStatus = reg_mem_card_request.getString(Parser.DEL_STATUS);
                        String EntryBy = reg_mem_card_request.getString(Parser.ENTRY_BY);
                        String EntryDate = reg_mem_card_request.getString(Parser.ENTRY_DATE);

                        db.addCardRequestDataFromOnline(AdmCountryCode, AdmDonorCode, AdmAwardCode, LayR1ListCode, LayR2ListCode, LayR3ListCode, LayR4ListCode,
                                HHID, MemID, RptGroup, RptCode, RequestSL, ReasonCode, RequestDate,
                                PrintDate, PrintBy, DeliveryDate, DeliveredBy, DelStatus, EntryBy, EntryDate);

                        //Log.d(TAG, "In Reg Mem Card Request Table: AdmCountryCode : " + AdmCountryCode + " AdmDonorCode : " + AdmDonorCode + " LayR1ListCode : " + LayR1ListCode + " LayR2ListCode : " + LayR2ListCode + " LayR3ListCode : " + LayR3ListCode + " LayR4ListCode : " + LayR4ListCode + " HHID : " + HHID);
                    }
                }


                publishProgress(++progressIncremental);


                if (!jObj.isNull(Parser.STAFF_FDP_ACCESS_JSON_A)) {
                    JSONArray staff_fdp_accesses = jObj.getJSONArray(Parser.STAFF_FDP_ACCESS_JSON_A);
                    size = staff_fdp_accesses.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject staff_fdp_access = staff_fdp_accesses.getJSONObject(i);

                        String StfCode = staff_fdp_access.getString(Parser.STF_CODE);
                        String AdmCountryCode = staff_fdp_access.getString(Parser.ADM_COUNTRY_CODE);
                        String FDPCode = staff_fdp_access.getString(Parser.FDP_CODE);
                        String btnNew = staff_fdp_access.getString(Parser.BTN_NEW);
                        String btnSave = staff_fdp_access.getString(Parser.BTN_SAVE);
                        String btnDel = staff_fdp_access.getString(Parser.BTN_DEL);


                        db.addStaffFDPAccess(StfCode, AdmCountryCode, FDPCode, btnNew, btnSave, btnDel);

                        //  Log.d(TAG, "In Reg Mem Card Request Table: AdmCountryCode : " + AdmCountryCode + " AdmDonorCode : " + AdmDonorCode + " LayR1ListCode : " + LayR1ListCode + " LayR2ListCode : "
                        //        + LayR2ListCode + " LayR3ListCode : " + LayR3ListCode + " LayR4ListCode : " + LayR4ListCode+ " HHID : " + HHID);
                    }
                }


                publishProgress(++progressIncremental);

                if (!jObj.isNull(Parser.FDP_MASTER_JSON_A)) {
                    JSONArray fdp_masters = jObj.getJSONArray(Parser.FDP_MASTER_JSON_A);
                    size = fdp_masters.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject fdp_master = fdp_masters.getJSONObject(i);

                        String AdmCountryCode = fdp_master.getString(Parser.ADM_COUNTRY_CODE);
                        String FDPCode = fdp_master.getString(Parser.FDP_CODE);
                        String FDPName = fdp_master.getString(Parser.FDP_NAME);
                        String FDPCatCode = fdp_master.getString(Parser.FDP_CAT_CODE);
                        String WHCode = fdp_master.getString(Parser.WH_CODE);
                        String LayR1Code = fdp_master.getString(Parser.LAY_R_1_CODE);
                        String LayR2Code = fdp_master.getString(Parser.LAY_R_2_CODE);


                        db.addFDPMaster(AdmCountryCode, FDPCode, FDPName, FDPCatCode, WHCode, LayR1Code, LayR2Code);

                        //  Log.d(TAG, "In Reg Mem Card Request Table: AdmCountryCode : " + AdmCountryCode + " AdmDonorCode : " + AdmDonorCode + " LayR1ListCode : " + LayR1ListCode + " LayR2ListCode : "
                        //        + LayR2ListCode + " LayR3ListCode : " + LayR3ListCode + " LayR4ListCode : " + LayR4ListCode+ " HHID : " + HHID);
                    }
                }


                publishProgress(++progressIncremental);


                if (!jObj.isNull(Parser.DISTRIBUTION_TABLE_JSON_A)) {
                    JSONArray distribution_tableDatas = jObj.getJSONArray(Parser.DISTRIBUTION_TABLE_JSON_A);
                    size = distribution_tableDatas.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject distribution_tableData = distribution_tableDatas.getJSONObject(i);
                        DistributionSaveDataModel data = new DistributionSaveDataModel();
                        data.setCountryCode(distribution_tableData.getString(Parser.ADM_COUNTRY_CODE));
                        data.setAdmDonorCode(distribution_tableData.getString(Parser.ADM_DONOR_CODE));
                        data.setAdmAwardCode(distribution_tableData.getString(Parser.ADM_AWARD_CODE));
                        data.setDistrictCode(distribution_tableData.getString(Parser.LAY_R_1_LIST_CODE));
                        data.setUpCode(distribution_tableData.getString(Parser.LAY_R_2_LIST_CODE));
                        data.setUniteCode(distribution_tableData.getString(Parser.LAY_R_3_LIST_CODE));
                        data.setVillageCode(distribution_tableData.getString(Parser.LAY_R_4_LIST_CODE));
                        data.setProgCode(distribution_tableData.getString(Parser.PROG_CODE));
                        data.setSrvCode(distribution_tableData.getString(Parser.SRV_CODE));
                        data.setOpMonthCode(distribution_tableData.getString(Parser.OP_MONTH_CODE));
                        data.setFDPCode(distribution_tableData.getString(Parser.FDP_CODE));
                        data.setID(distribution_tableData.getString(Parser.ID));
                        data.setDistStatus(distribution_tableData.getString(Parser.DIST_STATUS));
                        data.setSrvOpMonthCode(distribution_tableData.getString(Parser.SRV_OP_MONTH_CODE));
                        data.setDistFlag(distribution_tableData.getString(Parser.DIST_FLAG));
                        data.setWd(distribution_tableData.getString("WD"));

                        db.addInDistributionTableFormOnLine(data);

                        //  Log.d(TAG, "In Reg Mem Card Request Table: AdmCountryCode : " + AdmCountryCode + " AdmDonorCode : " + AdmDonorCode + " LayR1ListCode : " + LayR1ListCode + " LayR2ListCode : "
                        //        + LayR2ListCode + " LayR3ListCode : " + LayR3ListCode + " LayR4ListCode : " + LayR4ListCode+ " HHID : " + HHID);
                    }
                }


                publishProgress(++progressIncremental);


                if (!jObj.isNull(Parser.DISTRIBUTION_EXT_TABLE_JSON_A)) {
                    JSONArray distribution_ext_tableDatas = jObj.getJSONArray(Parser.DISTRIBUTION_EXT_TABLE_JSON_A);
                    size = distribution_ext_tableDatas.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject distribution_ext_tableData = distribution_ext_tableDatas.getJSONObject(i);
                        // DistributionSaveDataModel data = new DistributionSaveDataModel();

                        String AdmCountryCode = distribution_ext_tableData.getString(Parser.ADM_COUNTRY_CODE);
                        String AdmDonorCode = distribution_ext_tableData.getString(Parser.ADM_DONOR_CODE);
                        String AdmAwardCode = distribution_ext_tableData.getString(Parser.ADM_AWARD_CODE);
                        String LayR1ListCode = distribution_ext_tableData.getString(Parser.LAY_R_1_LIST_CODE);
                        String LayR2ListCode = distribution_ext_tableData.getString(Parser.LAY_R_2_LIST_CODE);
                        String LayR3ListCode = distribution_ext_tableData.getString(Parser.LAY_R_3_LIST_CODE);
                        String LayR4ListCode = distribution_ext_tableData.getString(Parser.LAY_R_4_LIST_CODE);
                        String ProgCode = distribution_ext_tableData.getString(Parser.PROG_CODE);
                        String SrvCode = distribution_ext_tableData.getString(Parser.SRV_CODE);
                        String OpMonthCode = distribution_ext_tableData.getString(Parser.OP_MONTH_CODE);
                        String FDPCode = distribution_ext_tableData.getString(Parser.FDP_CODE);
                        String ID = distribution_ext_tableData.getString(Parser.ID);
                        String VOItmSpec = distribution_ext_tableData.getString(Parser.VO_ITM_SPEC);
                        String VOItmUnit = distribution_ext_tableData.getString(Parser.VO_ITM_UNIT);
                        String VORefNumber = distribution_ext_tableData.getString(Parser.VO_REF_NUMBER);
                        String SrvOpMonthCode = distribution_ext_tableData.getString(Parser.SRV_OP_MONTH_CODE);
                        String DistFlag = distribution_ext_tableData.getString(Parser.DIST_FLAG);


                        //data.setDistStatus(distribution_ext_tableData.getString(DIST_STATUS);
                        // // TODO: 9/25/2016  create Method for download online

                        db.addInDistributionExtendedTable(AdmCountryCode, AdmDonorCode, AdmAwardCode,
                                LayR1ListCode, LayR2ListCode, LayR3ListCode, LayR4ListCode, ProgCode,
                                SrvCode, OpMonthCode, FDPCode, ID, VOItmSpec, VOItmUnit,
                                VORefNumber, SrvOpMonthCode, DistFlag,

                                "", "", "1");

                        //  Log.d(TAG, "In Reg Mem Card Request Table: AdmCountryCode : " + AdmCountryCode + " AdmDonorCode : " + AdmDonorCode + " LayR1ListCode : " + LayR1ListCode + " LayR2ListCode : "
                        //        + LayR2ListCode + " LayR3ListCode : " + LayR3ListCode + " LayR4ListCode : " + LayR4ListCode+ " HHID : " + HHID);
                    }
                }


                publishProgress(++progressIncremental);

                if (!jObj.isNull("distbasic_table")) {
                    JSONArray distbasic_table = jObj.getJSONArray("distbasic_table");
                    size = distbasic_table.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject distbasic = distbasic_table.getJSONObject(i);
                        // DistributionSaveDataModel data = new DistributionSaveDataModel();

                        String AdmCountryCode = distbasic.getString(Parser.ADM_COUNTRY_CODE);
                        String AdmDonorCode = distbasic.getString(Parser.ADM_DONOR_CODE);
                        String AdmAwardCode = distbasic.getString(Parser.ADM_AWARD_CODE);
                        String ProgCode = distbasic.getString(Parser.PROG_CODE);
                        String OpCode = distbasic.getString("OpCode");
                        String SrvOpMonthCode = distbasic.getString("SrvOpMonthCode");
                        String DisOpMonthCode = distbasic.getString("DisOpMonthCode");
                        String FDPCode = distbasic.getString(Parser.FDP_CODE);
                        String DistFlag = distbasic.getString("DistFlag");
                        ///   String FoodFlag = distbasic.getString("FoodFlag");
                        String OrgCode = distbasic.getString("OrgCode");
                        String Distributor = distbasic.getString("Distributor");
                        String DistributionDate = distbasic.getString("DistributionDate");
                        String DeliveryDate = distbasic.getString("DeliveryDate");
                        String Status = distbasic.getString("Status");
                        String PreparedBy = distbasic.getString("PreparedBy");
                        String VerifiedBy = distbasic.getString("VerifiedBy");
                        String ApproveBy = distbasic.getString("ApproveBy");


                        //data.setDistStatus(distribution_ext_tableData.getString(DIST_STATUS);

                        db.addInDistributionNPlaneTable(AdmCountryCode, AdmDonorCode, AdmAwardCode, ProgCode,
                                OpCode, SrvOpMonthCode, DisOpMonthCode, FDPCode, DistFlag, OrgCode, Distributor,
                                DistributionDate, DeliveryDate, Status, PreparedBy, VerifiedBy, ApproveBy);


                      /*  Log.d(TAG, "AdmCountryCode: " + AdmCountryCode + AdmDonorCode + AdmAwardCode + ProgCode +
                                OpCode + SrvOpMonthCode + DisOpMonthCode + FDPCode + DistFlag + OrgCode + Distributor +
                                DistributionDate + DeliveryDate + Status + PreparedBy + VerifiedBy + ApproveBy);*/

                        //  Log.d(TAG, "In Reg Mem Card Request Table: AdmCountryCode : " + AdmCountryCode + " AdmDonorCode : " + AdmDonorCode + " LayR1ListCode : " + LayR1ListCode + " LayR2ListCode : "
                        //        + LayR2ListCode + " LayR3ListCode : " + LayR3ListCode + " LayR4ListCode : " + LayR4ListCode+ " HHID : " + HHID);
                    }
                }


                publishProgress(++progressIncremental);


                if (!jObj.isNull(Parser.LUP_SRV_OPTION_LIST)) {
                    JSONArray lup_srv_option_listDatas = jObj.getJSONArray(Parser.LUP_SRV_OPTION_LIST);
                    size = lup_srv_option_listDatas.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject lup_srv_option_listData = lup_srv_option_listDatas.getJSONObject(i);

                        String countryCode = lup_srv_option_listData.getString(Parser.ADM_COUNTRY_CODE);

                        String programCode = lup_srv_option_listData.getString(Parser.PROG_CODE);
                        String serviceCode = lup_srv_option_listData.getString(Parser.SRV_CODE);
                        String LUPOptionCode = lup_srv_option_listData.getString(Parser.LUP_OPTION_CODE);
                        String LUPOptionName = lup_srv_option_listData.getString(Parser.LUP_OPTION_NAME);


                        db.addInLupSrvOptionListFromOnline(countryCode, programCode, serviceCode, LUPOptionCode, LUPOptionName);

                        //  Log.d(TAG, "In Reg Mem Card Request Table: AdmCountryCode : " + AdmCountryCode + " AdmDonorCode : " + AdmDonorCode + " LayR1ListCode : " + LayR1ListCode + " LayR2ListCode : "
                        //        + LayR2ListCode + " LayR3ListCode : " + LayR3ListCode + " LayR4ListCode : " + LayR4ListCode+ " HHID : " + HHID);
                    }
                }

                publishProgress(++progressIncremental);


                if (!jObj.isNull("vo_itm_table")) {
                    JSONArray vo_itm_tableDatas = jObj.getJSONArray("vo_itm_table");
                    size = vo_itm_tableDatas.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject vo_itm_tableData = vo_itm_tableDatas.getJSONObject(i);
                        //AGR_DataModel data = new AGR_DataModel();
                        String CatCode = vo_itm_tableData.getString("CatCode");
                        String ItmCode = vo_itm_tableData.getString("ItmCode");
                        String ItmName = vo_itm_tableData.getString("ItmName");


                        db.addVoucherItemTableFromOnline(CatCode, ItmCode, ItmName);

//                        Log.d(TAG, "In Voucher item table : CatCode : " + CatCode + " ItmCode : " + ItmCode + " ItmName : " + ItmName);

                    }
                }

//                publishProgress(39);
                publishProgress(++progressIncremental);

                if (!jObj.isNull(Parser.VO_ITM_MEAS_TABLE_JSON_A)) {
                    JSONArray vo_itm_meas_tableDatas = jObj.getJSONArray(Parser.VO_ITM_MEAS_TABLE_JSON_A);
                    size = vo_itm_meas_tableDatas.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject vo_itm_meas_tableData = vo_itm_meas_tableDatas.getJSONObject(i);
                        //AGR_DataModel data = new AGR_DataModel();
                        String MeasRCode = vo_itm_meas_tableData.getString("MeasRCode");
                        String UnitMeas = vo_itm_meas_tableData.getString("UnitMeas");
                        String MeasTitle = vo_itm_meas_tableData.getString("MeasTitle");


                        db.addVoucherItemMeasFromOnline(MeasRCode, UnitMeas, MeasTitle);

//                        Log.d(TAG, "In Voucher item table : MeasRCode : " + MeasRCode + " UnitMeas : " + UnitMeas + " MeasTitle : " + MeasTitle);

                    }
                }

                publishProgress(++progressIncremental);
                if (!jObj.isNull("vo_country_prog_itm")) {
                    JSONArray vo_country_prog_itmDatas = jObj.getJSONArray("vo_country_prog_itm");
                    size = vo_country_prog_itmDatas.length();


                    String AdmCountryCode;
                    String AdmDonorCode;
                    String AdmAwardCode;
                    String AdmProgCode;
                    String AdmSrvCode;
                    String CatCode;
                    String ItmCode;
                    String MeasRCode;
                    String VOItmSpec;
                    String UnitCost;
                    String Active;
                    String Currency;
                    for (int i = 0; i < size; i++) {
                        JSONObject vo_country_prog_itmData = vo_country_prog_itmDatas.getJSONObject(i);
                        //AGR_DataModel data = new AGR_DataModel();
                        AdmCountryCode = vo_country_prog_itmData.getString("AdmCountryCode");
                        AdmDonorCode = vo_country_prog_itmData.getString("AdmDonorCode");
                        AdmAwardCode = vo_country_prog_itmData.getString("AdmAwardCode");
                        AdmProgCode = vo_country_prog_itmData.getString("AdmProgCode");
                        AdmSrvCode = vo_country_prog_itmData.getString("AdmSrvCode");
                        CatCode = vo_country_prog_itmData.getString("CatCode");
                        ItmCode = vo_country_prog_itmData.getString("ItmCode");
                        MeasRCode = vo_country_prog_itmData.getString("MeasRCode");
                        VOItmSpec = vo_country_prog_itmData.getString(Parser.VO_ITM_SPEC);
                        UnitCost = vo_country_prog_itmData.getString("UnitCost");
                        Active = vo_country_prog_itmData.getString("Active");
                        Currency = vo_country_prog_itmData.getString("Currency");


                        db.addVoucherCountryProgItemFromOnline(AdmCountryCode, AdmDonorCode, AdmAwardCode, AdmProgCode, AdmSrvCode, CatCode, ItmCode, MeasRCode, VOItmSpec, UnitCost, Active, Currency);

                        //                        Log.d(TAG, "In Voucher Country  Prog Item  table : AdmCountryCode : " + AdmCountryCode + " AdmDonorCode : " + AdmDonorCode + " AdmAwardCode : " + AdmAwardCode);

                    }
                }

                publishProgress(++progressIncremental);
                if (!jObj.isNull(Parser.LUP_GPS_TABLE_JSON_A)) {
                    JSONArray lup_gps_table_Datas = jObj.getJSONArray(Parser.LUP_GPS_TABLE_JSON_A);
                    size = lup_gps_table_Datas.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject lup_gps_tableData = lup_gps_table_Datas.getJSONObject(i);

                        String GrpCode = lup_gps_tableData.getString("GrpCode");
                        String SubGrpCode = lup_gps_tableData.getString("SubGrpCode");
                        String AttributeCode = lup_gps_tableData.getString("AttributeCode");
                        String LookUpCode = lup_gps_tableData.getString("LookUpCode");
                        String LookUpName = lup_gps_tableData.getString("LookUpName");


                        db.addLUP_GPS_TableFromOnline(GrpCode, SubGrpCode, AttributeCode, LookUpCode, LookUpName);
                       /* Log.d("NIR2", "addLUP_GPS_TableFromOnline : GrpCode : " + GrpCode + " SubGrpCode : "
                                + SubGrpCode + " AttributeCode : " + AttributeCode
                                + " LookUpCode : " + LookUpCode + " LookUpName : " + LookUpName
                        );*/

                    }
                }

                publishProgress(++progressIncremental);
                if (!jObj.isNull(Parser.GPS_SUB_GROUP_ATTRIBUTES_JSON_A)) {
                    JSONArray gps_sub_group_attributes_Datas = jObj.getJSONArray(Parser.GPS_SUB_GROUP_ATTRIBUTES_JSON_A);
                    size = gps_sub_group_attributes_Datas.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject gps_sub_group_attributes_Data = gps_sub_group_attributes_Datas.getJSONObject(i);


                        String GrpCode = gps_sub_group_attributes_Data.getString("GrpCode");
                        String SubGrpCode = gps_sub_group_attributes_Data.getString("SubGrpCode");
                        String AttributeCode = gps_sub_group_attributes_Data.getString("AttributeCode");
                        String AttributeTitle = gps_sub_group_attributes_Data.getString("AttributeTitle");
                        String DataType = gps_sub_group_attributes_Data.getString("DataType");
                        String LookUpCode = gps_sub_group_attributes_Data.getString("LookUpCode");


                        db.addGPS_SubGroupAttributesFromOnline(GrpCode, SubGrpCode, AttributeCode, AttributeTitle, DataType, LookUpCode);
                        /*Log.d("NIR2", "addGPS_SubGroupAttributesFromOnline : GrpCode : " + GrpCode
                                + " SubGrpCode : " + SubGrpCode + " AttributeCode : " + AttributeCode
                                + " AttributeTitle : " + AttributeTitle + " DataType : " + DataType
                                + " LookUpCode : " + LookUpCode);*/

                    }
                }

                publishProgress(++progressIncremental);
                if (!jObj.isNull(Parser.GPS_LOCATION_ATTRIBUTES_JSON_A)) {
                    JSONArray gps_location_attributes_Datas = jObj.getJSONArray(Parser.GPS_LOCATION_ATTRIBUTES_JSON_A);
                    size = gps_location_attributes_Datas.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject gps_location_attributes_Data = gps_location_attributes_Datas.getJSONObject(i);


                        String AdmCountryCode = gps_location_attributes_Data.getString("AdmCountryCode");
                        String GrpCode = gps_location_attributes_Data.getString("GrpCode");
                        String SubGrpCode = gps_location_attributes_Data.getString("SubGrpCode");
                        String LocationCode = gps_location_attributes_Data.getString("LocationCode");
                        String AttributeCode = gps_location_attributes_Data.getString("AttributeCode");
                        String AttributeValue = gps_location_attributes_Data.getString("AttributeValue");
                        String AttPhoto = gps_location_attributes_Data.getString("AttPhoto");

                        db.addGPSLocationAttributesFromOnline(AdmCountryCode, GrpCode, SubGrpCode, LocationCode, AttributeCode, AttributeValue, AttPhoto);
                     /*   Log.d("NIR2", "addGPS_SubGroupAttributesFromOnline : AdmCountryCode : " + AdmCountryCode
                                + " GrpCode : " + GrpCode + " SubGrpCode : " + SubGrpCode
                                + " LocationCode : " + LocationCode + " AttributeCode : " + AttributeCode

                                + " AttributeValue : " + AttributeValue
                        );*/

                    }
                }

                publishProgress(++progressIncremental);
                if (!jObj.isNull("community_group")) {

                    Parser.CommunityGroupParser(jObj.getJSONArray("community_group"), db);
                }

                publishProgress(++progressIncremental);
                if (!jObj.isNull("lup_community_animal")) {
                    JSONArray lup_community_animal_Datas = jObj.getJSONArray("lup_community_animal");
                    size = lup_community_animal_Datas.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject lup_community_animal_Data = lup_community_animal_Datas.getJSONObject(i);


                        String AdmCountryCode = lup_community_animal_Data.getString("AdmCountryCode");
                        String AdmDonorCode = lup_community_animal_Data.getString("AdmDonorCode");
                        String AdmAwardCode = lup_community_animal_Data.getString("AdmAwardCode");
                        String AdmProgCode = lup_community_animal_Data.getString("AdmProgCode");
                        String AnimalCode = lup_community_animal_Data.getString("AnimalCode");
                        String AnimalType = lup_community_animal_Data.getString("AnimalType");


                        db.addLUP_AnimalTypeFromOnline(AdmCountryCode, AdmDonorCode, AdmAwardCode, AdmProgCode, AnimalCode, AnimalType);


                    }
                }


                publishProgress(++progressIncremental);
                if (!jObj.isNull("lup_prog_group_crop")) {
                    Parser.lupProgGroupCropParser(jObj.getJSONArray("lup_prog_group_crop"), db);
                }


                publishProgress(++progressIncremental);
                if (!jObj.isNull("lup_community_loan_source")) {
                    JSONArray lup_community_loan_source_Datas = jObj.getJSONArray("lup_community_loan_source");
                    size = lup_community_loan_source_Datas.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject lup_community_loan_source_Data = lup_community_loan_source_Datas.getJSONObject(i);


                        String AdmCountryCode = lup_community_loan_source_Data.getString("AdmCountryCode");
                        String AdmDonorCode = lup_community_loan_source_Data.getString("AdmDonorCode");
                        String AdmAwardCode = lup_community_loan_source_Data.getString("AdmAwardCode");
                        String AdmProgCode = lup_community_loan_source_Data.getString("AdmProgCode");
                        String LoanCode = lup_community_loan_source_Data.getString("LoanCode");
                        String LoanSource = lup_community_loan_source_Data.getString("LoanSource");


                /*        Log.d("InTest", "AdmCountryCode:" + AdmCountryCode
                                + "AdmDonorCode: " + AdmDonorCode + "AdmAwardCode : " + AdmAwardCode + "AdmProgCode:" + AdmProgCode
                                + " LoanCode: " + LoanCode + "LoanSource:" + LoanSource);
*/
                        db.addLUP_CommunityLoanSource(AdmCountryCode, AdmDonorCode, AdmAwardCode, AdmProgCode
                                , LoanCode, LoanSource);
                    }
                }

                publishProgress(++progressIncremental);
                if (!jObj.isNull("lup_community__lead_position")) {
                    JSONArray lup_community_lead_position_Datas = jObj.getJSONArray("lup_community__lead_position");
                    size = lup_community_lead_position_Datas.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject lup_community_lead_position_Data = lup_community_lead_position_Datas.getJSONObject(i);


                        String AdmCountryCode = lup_community_lead_position_Data.getString("AdmCountryCode");
                        String AdmDonorCode = lup_community_lead_position_Data.getString("AdmDonorCode");
                        String AdmAwardCode = lup_community_lead_position_Data.getString("AdmAwardCode");
                        String AdmProgCode = lup_community_lead_position_Data.getString("AdmProgCode");
                        String LeadCode = lup_community_lead_position_Data.getString("LeadCode");
                        String LeadPosition = lup_community_lead_position_Data.getString("LeadPosition");


              /*          Log.d("InTest", "AdmCountryCode:" + AdmCountryCode
                                + "AdmDonorCode: " + AdmDonorCode + "AdmAwardCode : " + AdmAwardCode + "AdmProgCode:" + AdmProgCode
                                + " LeadCode: " + LeadCode + "LeadPosition:" + LeadPosition);*/
                        db.addLUP_CommunityLeadPostition(AdmCountryCode, AdmDonorCode, AdmAwardCode, AdmProgCode, LeadCode, LeadPosition);

                    }
                }

                publishProgress(++progressIncremental);
                if (!jObj.isNull("community_group_category")) {
                    JSONArray community_group_category_Datas = jObj.getJSONArray("community_group_category");
                    size = community_group_category_Datas.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject community_group_category_Data = community_group_category_Datas.getJSONObject(i);


                        String AdmCountryCode = community_group_category_Data.getString("AdmCountryCode");
                        String AdmDonorCode = community_group_category_Data.getString("AdmDonorCode");
                        String AdmAwardCode = community_group_category_Data.getString("AdmAwardCode");
                        String AdmProgCode = community_group_category_Data.getString("AdmProgCode");
                        String GrpCatCode = community_group_category_Data.getString("GrpCatCode");
                        String GrpCatName = community_group_category_Data.getString("GrpCatName");
                        String GrpCatShortName = community_group_category_Data.getString("GrpCatShortName");


           /*             Log.d("InTest", "AdmCountryCode:" + AdmCountryCode
                                + "AdmDonorCode: " + AdmDonorCode + "AdmAwardCode : " + AdmAwardCode + "AdmProgCode:" + AdmProgCode
                                + " GrpCatCode: " + GrpCatCode + " GrpCatName:" + GrpCatName
                                + " GrpCatShortName: " + GrpCatShortName
                        );*/
                        db.addCommunityGroupCategoryFromOnline(AdmCountryCode, AdmDonorCode, AdmAwardCode, AdmProgCode, GrpCatCode, GrpCatName, GrpCatShortName);

                    }
                }
                publishProgress(++progressIncremental);
                if (!jObj.isNull("lup_reg_n_add_lookup")) {
                    JSONArray lup_reg_n_add_lookup = jObj.getJSONArray("lup_reg_n_add_lookup");
                    size = lup_reg_n_add_lookup.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject Lup_reg_n_add_lookup = lup_reg_n_add_lookup.getJSONObject(i);


                        String AdmCountryCode = Lup_reg_n_add_lookup.getString("AdmCountryCode");
                        String RegNAddLookupCode = Lup_reg_n_add_lookup.getString("RegNAddLookupCode");
                        String RegNAddLookup = Lup_reg_n_add_lookup.getString("RegNAddLookup");
                        String LayR1ListCode = Lup_reg_n_add_lookup.getString("LayR1ListCode");
                        String LayR2ListCode = Lup_reg_n_add_lookup.getString("LayR2ListCode");
                        String LayR3ListCode = Lup_reg_n_add_lookup.getString("LayR3ListCode");
                        String LayR4ListCode = Lup_reg_n_add_lookup.getString("LayR4ListCode");

/*
                        Log.d("InTest", "AdmCountryCode:" + AdmCountryCode + "RegNAddLookupCode" + RegNAddLookupCode
                                + "RegNAddLookup: " + RegNAddLookup + "LayR1ListCode : " + LayR1ListCode + "LayR2ListCode:" + LayR2ListCode
                                + " LayR3ListCode: " + LayR3ListCode + "LayR4ListCode:" + LayR4ListCode);*/
                        db.addLUP_RegNAddLookup(AdmCountryCode, RegNAddLookupCode, RegNAddLookup, LayR1ListCode, LayR2ListCode, LayR3ListCode, LayR4ListCode);

                    }
                }

                publishProgress(++progressIncremental);
                if (!jObj.isNull("prog_org_n_role")) {
                    JSONArray prog_org_n_role_Datas = jObj.getJSONArray("prog_org_n_role");
                    size = prog_org_n_role_Datas.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject prog_org_n_role_Data = prog_org_n_role_Datas.getJSONObject(i);
                        String AdmCountryCode = prog_org_n_role_Data.getString("AdmCountryCode");
                        String AdmDonorCode = prog_org_n_role_Data.getString("AdmDonorCode");
                        String AdmAwardCode = prog_org_n_role_Data.getString("AdmAwardCode");
                        String OrgNCode = prog_org_n_role_Data.getString("OrgNCode");
                        String PrimeYN = prog_org_n_role_Data.getString("PrimeYN");
                        String SubYN = prog_org_n_role_Data.getString("SubYN");
                        String TechYN = prog_org_n_role_Data.getString("TechYN");
                        String LogYN = prog_org_n_role_Data.getString("LogYN");
                        String ImpYN = prog_org_n_role_Data.getString("ImpYN");
                        String OthYN = prog_org_n_role_Data.getString("OthYN");
       /*                 Log.d("InTest", "AdmCountryCode:" + AdmCountryCode
                                + "AdmDonorCode: " + AdmDonorCode + "AdmAwardCode : " + AdmAwardCode + "OrgNCode:" + OrgNCode
                                + " PrimeYN: " + PrimeYN + " SubYN:" + SubYN
                                + " TechYN: " + TechYN
                                + " LogYN: " + LogYN + "ImpYN:" + ImpYN + " OthYN: " + OthYN
                        );*/
                        db.insertIntoProgOrgNRole(AdmCountryCode, AdmDonorCode, AdmAwardCode, OrgNCode, PrimeYN, SubYN, TechYN, ImpYN, LogYN, OthYN);
                    }
                }
                publishProgress(++progressIncremental);
                if (!jObj.isNull("org_n_code")) {
                    JSONArray org_n_code_Datas = jObj.getJSONArray("org_n_code");
                    size = org_n_code_Datas.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject org_n_code_Data = org_n_code_Datas.getJSONObject(i);
                        String OrgNCode = org_n_code_Data.getString("OrgNCode");
                        String OrgNName = org_n_code_Data.getString("OrgNName");
                        String OrgNShortName = org_n_code_Data.getString("OrgNShortName");
                        Log.d(TAG, "OrgNName:" + OrgNName + "OrgNShortName:" + OrgNShortName);
                        db.insertIntoProgOrgN(OrgNCode, OrgNName, OrgNShortName);
                    }
                }
//// TODO: 10/18/2016  this parsing of the group detail set to parse class

                publishProgress(++progressIncremental);
                if (!jObj.isNull("community_grp_detail")) {

                    Parser.CommunityGroupDetailsParser(jObj.getJSONArray("community_grp_detail"), db);
                }

                publishProgress(++progressIncremental);
                if (!jObj.isNull("staff_master")) {
                    JSONArray staff_master_Datas = jObj.getJSONArray("staff_master");
                    size = staff_master_Datas.length();

                    String StfCode;
                    String OrigAdmCountryCode;
                    String StfName;
                    String OrgNCode;
                    String OrgNDesgNCode;
                    String StfStatus;
                    String StfCategory;
                    String UsrLogInName;
                    String UsrLogInPW;
                    String StfAdminRole;
                    for (int i = 0; i < size; i++) {
                        JSONObject staff_master_Data = staff_master_Datas.getJSONObject(i);


                        StfCode = staff_master_Data.getString("StfCode");
                        OrigAdmCountryCode = staff_master_Data.getString("OrigAdmCountryCode");
                        StfName = staff_master_Data.getString("StfName");
                        OrgNCode = staff_master_Data.getString("OrgNCode");
                        OrgNDesgNCode = staff_master_Data.getString("OrgNDesgNCode");
                        StfStatus = staff_master_Data.getString("StfStatus");
                        StfCategory = staff_master_Data.getString("StfCategory");
                        UsrLogInName = staff_master_Data.getString("UsrLogInName");
                        UsrLogInPW = staff_master_Data.getString("UsrLogInPW");
                        StfAdminRole = staff_master_Data.getString("StfAdminRole");


                        db.insertIntoStaffMasterTable(StfCode, OrigAdmCountryCode, StfName, OrgNCode, OrgNDesgNCode, StfStatus, StfCategory, UsrLogInName, UsrLogInPW, StfAdminRole);

                    }
                }

                publishProgress(++progressIncremental);
                if (!jObj.isNull("gps_lup_list")) {
                    JSONArray gps_lup_list_data = jObj.getJSONArray("gps_lup_list");
                    size = gps_lup_list_data.length();

                    String GrpCode;
                    String SubGrpCode;
                    String AttributeCode;
                    String LupValueCode;
                    String LupValueText;

                    for (int i = 0; i < size; i++) {
                        JSONObject gps_lup_list = gps_lup_list_data.getJSONObject(i);


                        GrpCode = gps_lup_list.getString("GrpCode");
                        SubGrpCode = gps_lup_list.getString("SubGrpCode");
                        AttributeCode = gps_lup_list.getString("AttributeCode");
                        LupValueCode = gps_lup_list.getString("LupValueCode");
                        LupValueText = gps_lup_list.getString("LupValueText");


                        db.insertIntoLupGpsList(GrpCode, SubGrpCode, AttributeCode, LupValueCode, LupValueText);

                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            showProgressDialog();
            progressIncremental = 0;
        }

        @Override
        protected void onPostExecute(Void string) {

            new Inject_Reg_HouseH_DataIntoSQLite().execute();
        }
    }

    private void hideDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    /**
     * Read JSON DATA Insert into SQLite
     */
    private void showProgressDialog() {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(88);
        progressDialog.setMessage("Retrieving...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }


    private void showOperationModelabel(SharedPreferences settings) {
        int operationMode = settings.getInt(UtilClass.OPERATION_MODE, 0);
        // Log.d("NIR1", "operation mode : " + operationMode);
        switch (operationMode) {
            case UtilClass.REGISTRATION_OPERATION_MODE:

                tvOperationMode.setText("REGISTRATION");
                List<String> list;
                list = db.selectGeoDataVillage();
                String villageName = "";
                for (int i = 0; i < list.size(); i++) {
                    villageName += list.get(i) + "\n";
                }
                tvGeoData.setText(villageName);

                break;
            case UtilClass.DISTRIBUTION_OPERATION_MODE:

                tvOperationMode.setText("DISTRIBUTION");
                list = db.selectGeoDataFDP();
                String fdPName = "";
                for (int i = 0; i < list.size(); i++) {
                    fdPName += list.get(i) + "\n";
                }
                tvGeoData.setText(fdPName);
                break;
            case UtilClass.SERVICE_OPERATION_MODE:

                tvOperationMode.setText("SERVICE");
                list = db.selectGeoDataCenter();
                String centerName = "";
                for (int i = 0; i < list.size(); i++) {
                    centerName += list.get(i) + "\n";
                }
                tvGeoData.setText(centerName);

                break;


            case UtilClass.OTHER_OPERATION_MODE:
                tvOperationMode.setText("ORTHER");
                tvGeoData.setText("NOT APPLICABLE");

                break;
        }
    }
}
