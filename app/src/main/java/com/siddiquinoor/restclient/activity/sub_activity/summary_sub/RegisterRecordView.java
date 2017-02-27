package com.siddiquinoor.restclient.activity.sub_activity.summary_sub;

/**
 * Activity for presenting the list of all entry in a list view
 * with image and details
 *
 * @author
 * @desc TechnoDhaka.
 * @link www.SiddiquiNoor.com
 * @version 1.3.0
 * @since 1.0
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.siddiquinoor.restclient.activity.MainActivity;
import com.siddiquinoor.restclient.R;
import com.siddiquinoor.restclient.activity.Register;
import com.siddiquinoor.restclient.fragments.BaseActivity;
import com.siddiquinoor.restclient.manager.SQLiteHandler;
import com.siddiquinoor.restclient.views.adapters.ListAdapterHelper;
import com.siddiquinoor.restclient.views.adapters.ListDataModel;
import com.siddiquinoor.restclient.views.helper.SpinnerHelper;

import java.util.ArrayList;
import java.util.List;

public class RegisterRecordView extends BaseActivity {

    private static final String TAG = RegisterRecordView.class.getSimpleName();

    private Button btnReg;
    private Button btnHome;
    private Spinner spVillage;
    private EditText edt_hhSearch;
    private Button btn_hhSearch;

    private ProgressDialog pDialog;
    private ArrayList<ListDataModel> personArray = new ArrayList<ListDataModel>();

    private ListView listViewRecord;
    private ListAdapterHelper adapter;


    private SQLiteHandler sqlH;
    private String ext_village = null;
    private String ext_village_name = null;
    private String idVill = null;

    int position = 0;
    private String criteria = "";
    private String idCountry;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_records);
        pDialog = new ProgressDialog(this);

        sqlH = new SQLiteHandler(getApplicationContext());
        viewReference();

        setListener();


        Intent intnt = getIntent();
        ext_village = intnt.getStringExtra("village_code");
        ext_village_name = intnt.getStringExtra("village");

        idCountry = ext_village.substring(0, 4);


        loadLayR4CodeForRegisterRecordView();

        if (ext_village != null) {

            LoadingListView load = new LoadingListView(ext_village, "");
            load.execute();
            for (int i = 0; i < spVillage.getCount(); i++) {
                String village = spVillage.getItemAtPosition(i).toString();
                if (village.equals(ext_village_name)) {
                    position = i;
                }
            }
            spVillage.setSelection(position);
        }
    }

    private void setListener() {
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iReg = new Intent(getApplicationContext(), Register.class);
                iReg.putExtra("country_code", idCountry);
                startActivity(iReg);
                finish();
            }
        });


        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHome = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentHome);
                finish();
            }
        });

        btn_hhSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hhSearch = edt_hhSearch.getText().toString();
                if (hhSearch.length() > 0) {
                    if (ext_village != null) {
                        // idVill = ((SpinnerHelper) spVillage.getSelectedItem()).getId();
                        LoadingListView lv = new LoadingListView(ext_village, hhSearch);
                        lv.execute();
                    }
                }
            }
        });
    }


    private void viewReference() {
        btnReg = (Button) findViewById(R.id.btnRegisterFooter);
        edt_hhSearch = (EditText) findViewById(R.id.edt_hhSearch);
        btn_hhSearch = (Button) findViewById(R.id.btn_hhSearch);
        btnHome = (Button) findViewById(R.id.btnHomeFooter);
        spVillage = (Spinner) findViewById(R.id.spVillagerecord);
        listViewRecord = (ListView) findViewById(R.id.list_registered_user); // in activity_view_records.xmlml

        addIconHomeButton();
        addIconRegistrationButton();




    }

    private void addIconHomeButton() {

        btnHome.setText("");
        Drawable imageHome = getResources().getDrawable(R.drawable.home_b);
        btnHome.setCompoundDrawablesRelativeWithIntrinsicBounds(imageHome, null, null, null);
        btnHome.setPadding(180, 5, 180, 5);
    }

    private void addIconRegistrationButton() {

        btnReg.setText("");
        Drawable imageHome = getResources().getDrawable(R.drawable.registration);
        btnReg.setCompoundDrawablesRelativeWithIntrinsicBounds(imageHome, null, null, null);
        btnReg.setPadding(180, 5, 180, 5);
    }


    private void loadLayR4CodeForRegisterRecordView() {
        String criteria = "SELECT " + " v." + SQLiteHandler.COUNTRY_CODE_COL + " || '' ||  v." + SQLiteHandler.LAY_R1_LIST_CODE_COL + " || '' || v." + SQLiteHandler.LAY_R2_LIST_CODE_COL + " || '' || v." +
                SQLiteHandler.LAY_R3_LIST_CODE_COL + " || '' || v." + SQLiteHandler.LAY_R4_LIST_CODE_COL + " AS v_code," +
                " v." + SQLiteHandler.VILLAGE_NAME_COL + " AS Vill_Name " +
                     /*   " COUNT("+PID_COL+") AS records"*/" FROM " + SQLiteHandler.VILLAGE_TABLE + " AS v" +
                " LEFT JOIN " + SQLiteHandler.REGISTRATION_TABLE + " AS r" +
                " ON r." + SQLiteHandler.COUNTRY_CODE_COL + "= v." + SQLiteHandler.COUNTRY_CODE_COL + " AND " +
                "r." + SQLiteHandler.DISTRICT_NAME_COL + "= v." + SQLiteHandler.LAY_R1_LIST_CODE_COL + " AND " +
                "r." + SQLiteHandler.UPZILLA_NAME_COL + "= v." + SQLiteHandler.LAY_R2_LIST_CODE_COL + " AND " +
                "r." + SQLiteHandler.UNITE_NAME_COL + "= v." + SQLiteHandler.LAY_R3_LIST_CODE_COL + " AND " +
                "r." + SQLiteHandler.VILLAGE_NAME_COL + "= v." + SQLiteHandler.LAY_R4_LIST_CODE_COL +
                " Inner join " + SQLiteHandler.SELECTED_VILLAGE_TABLE + " AS s"
                + " on " + " s." + SQLiteHandler.COUNTRY_CODE_COL + "= v." + SQLiteHandler.COUNTRY_CODE_COL + " AND " +
                "s." + SQLiteHandler.LAY_R1_LIST_CODE_COL + "= v." + SQLiteHandler.LAY_R1_LIST_CODE_COL + " AND " +
                "s." + SQLiteHandler.LAY_R2_LIST_CODE_COL + "= v." + SQLiteHandler.LAY_R2_LIST_CODE_COL + " AND " +
                "s." + SQLiteHandler.LAY_R3_LIST_CODE_COL + "= v." + SQLiteHandler.LAY_R3_LIST_CODE_COL + " AND " +
                "s." + SQLiteHandler.LAY_R4_LIST_CODE_COL + "= v." + SQLiteHandler.LAY_R4_LIST_CODE_COL +

                " WHERE v." + SQLiteHandler.COUNTRY_CODE_COL + "='" + getCountryCode() + "'" + /** send the no of village for selected country added by Faisal Mohammad*/
                "  GROUP BY v." + SQLiteHandler.COUNTRY_CODE_COL + ",v." + SQLiteHandler.LAY_R1_LIST_CODE_COL + ",v." + SQLiteHandler.LAY_R2_LIST_CODE_COL + ",v." + SQLiteHandler.LAY_R3_LIST_CODE_COL + ",v." + SQLiteHandler.LAY_R4_LIST_CODE_COL;

        List<SpinnerHelper> listVillage = sqlH.getListAndID(SQLiteHandler.CUSTOM_QUERY, criteria, getCountryCode(), false);


        // Creating adapter for spinner
        final ArrayAdapter<SpinnerHelper> dataAdapter = new ArrayAdapter<SpinnerHelper>(this, R.layout.spinner_layout, listVillage);
        // Drop down layout style
        dataAdapter.setDropDownViewResource(R.layout.spinner_layout);
        // attaching data adapter to spinner
        spVillage.setAdapter(dataAdapter);
        //dataAdapter.notifyDataSetChanged();

        spVillage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                personArray.clear();

                if (ext_village == null) {
                    idVill = ((SpinnerHelper) spVillage.getSelectedItem()).getId();

                    LoadingListView load = new LoadingListView(idVill, "");
                    load.execute();
                } else {

                    Log.d(TAG, "village code: " + ext_village);

                    LoadingListView load = new LoadingListView(ext_village, "");
                    load.execute();
                    //ext_village = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void loadListData(String village_code, String houseHoldId) {
        List<ListDataModel> listData = sqlH.getRegisteredData(village_code, houseHoldId);
        personArray.clear(); ///here cearing array list for uddate

        for (ListDataModel data : listData) {
            // add contacts data in arrayList
            personArray.add(data);
        }

        adapter = new ListAdapterHelper(this, personArray);
        adapter.notifyDataSetChanged();
        // listViewRecord.setAdapter(adapter);


    }

    /*@Override
    protected void onResume() {
        super.onResume();
        //adapter.notifyDataSetChanged();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //hidePDialog();
    }*/


    /**
     *  2015-10-12
     */
    private class LoadingListView extends AsyncTask<Void, Integer, String> {

        private String villageCode;
        private String temHhId;
        // for member id search

        public LoadingListView(String villageCode, String temHhId) {
            this.villageCode = villageCode;
            this.temHhId = temHhId;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {


                loadListData(villageCode, temHhId);


            } catch (Exception e) {
                pDialog.dismiss();
                return "UNKNOWN";
            }
            return "success";


        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            startProgressBar("Data is Loading");

        }


        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            adapter.notifyDataSetChanged();
            listViewRecord.setAdapter(adapter);

            listViewRecord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            listViewRecord.setFocusableInTouchMode(true);
            pDialog.dismiss();

        }
    }


    private void startProgressBar(String msg) {

        //pDialog = new ProgressDialog(my_activity, ProgressDialog.STYLE_SPINNER);
        pDialog.setMessage(msg);
        pDialog.setCancelable(false);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //  pDialog.setProgress(increment);
        //  pDialog.setMax(max);
        pDialog.show();
    }

}
