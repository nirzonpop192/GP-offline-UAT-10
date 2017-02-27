package com.siddiquinoor.restclient.activity;
/**
 * @autor : Faisal Mohammad
 * This activity is used to navigate the member to Assign an graduation module
 */

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.siddiquinoor.restclient.R;
import com.siddiquinoor.restclient.fragments.BaseActivity;
import com.siddiquinoor.restclient.manager.SQLiteHandler;
import com.siddiquinoor.restclient.manager.sqlsyntax.SQLiteQuery;
import com.siddiquinoor.restclient.utils.KEY;
import com.siddiquinoor.restclient.views.adapters.AssignDataModel;
import com.siddiquinoor.restclient.views.adapters.MemberSearchAdapter;
import com.siddiquinoor.restclient.views.helper.SpinnerHelper;
import com.siddiquinoor.restclient.views.spinner.SpinnerLoader;


import java.util.ArrayList;
import java.util.List;

import static com.siddiquinoor.restclient.manager.SQLiteHandler.COUNTRY_CODE_COL;
import static com.siddiquinoor.restclient.manager.SQLiteHandler.LAY_R1_LIST_CODE_COL;
import static com.siddiquinoor.restclient.manager.SQLiteHandler.LAY_R2_LIST_CODE_COL;
import static com.siddiquinoor.restclient.manager.SQLiteHandler.LAY_R3_LIST_CODE_COL;
import static com.siddiquinoor.restclient.manager.SQLiteHandler.LAY_R4_LIST_CODE_COL;
import static com.siddiquinoor.restclient.manager.SQLiteHandler.VILLAGE_NAME_COL;
import static com.siddiquinoor.restclient.manager.SQLiteHandler.VILLAGE_TABLE;

public class MemberSearchPage extends BaseActivity {


    private static final String TAG = "MemberSearchPage";
    private SQLiteHandler sqlH;
    private Spinner spVillage;
    private String idVillage;
    private String strVillage;
    private String idCountry;
    private static ProgressDialog pDialog;
    private ListView listOfMember;
    private MemberSearchAdapter adapter;

    private String idDistrictC;
    private String idUpazilaC;
    private String idUnitC;
    private String idVillageC;

    private Button btnHome;

    private Button btn_searchMember;
    private EditText edt_memberId;
    private final Context mContext = MemberSearchPage.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_search_page);
        initialize();

        Intent intent = getIntent();
        String dir = intent.getStringExtra(KEY.DIR_CLASS_NAME_KEY);
        if (dir.equals("MainActivity")) {
            idCountry = intent.getStringExtra(KEY.COUNTRY_ID);
            loadLayR4List();
        } else {

            idCountry = intent.getStringExtra(KEY.COUNTRY_ID);
            idVillage = intent.getStringExtra(KEY.VILLAGE_CODE);
            strVillage = intent.getStringExtra(KEY.VILLAGE_NAME);
            loadLayR4List();
        }


        setListener();


    }

    private void setListener() {
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainActivity((Activity) mContext);
            }
        });

        btn_searchMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idMember = edt_memberId.getText().toString();
                if (idMember.length() > 0) {

                    LoadListView loading = new LoadListView(idCountry, idDistrictC, idUpazilaC, idUnitC, idVillageC, idMember, strVillage);
                    loading.execute();
                }
            }
        });
    }

    /**
     * initialize the global variable
     */

    private void initialize() {
        sqlH = new SQLiteHandler(MemberSearchPage.this);

        viewReference();


    }

    /**
     * Refer the XML views with java object
     */
    private void viewReference() {
        spVillage = (Spinner) findViewById(R.id.search_mem_spVillage);
        listOfMember = (ListView) findViewById(R.id.lv_mem_search);
        btnHome = (Button) findViewById(R.id.btnHomeFooter);
        Button btnSummary = (Button) findViewById(R.id.btnRegisterFooter);
        btnSummary.setVisibility(View.GONE);

        btn_searchMember = (Button) findViewById(R.id.btn_memberSearch);
        edt_memberId = (EditText) findViewById(R.id.edt_memberSearch);

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void addIconHomeButton() {
        btnHome.setText("");
        Drawable imageHome = getResources().getDrawable(R.drawable.home_b);
        btnHome.setCompoundDrawablesRelativeWithIntrinsicBounds(imageHome, null, null, null);
        setPaddingButton(mContext, imageHome, btnHome);
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
        addIconHomeButton();
    }

    /**
     *
     */
    private void loadLayR4List() {

        SpinnerLoader.loadLayR4ListLoader(mContext, sqlH, spVillage, idVillage, strVillage, SQLiteQuery.loadLayR4List_sql());


        spVillage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strVillage = ((SpinnerHelper) spVillage.getSelectedItem()).getValue();
                idVillage = ((SpinnerHelper) spVillage.getSelectedItem()).getId();
                Log.d(TAG, "village id :" + idVillage);
                if (Integer.parseInt(idVillage) > 0) {
                    // after the village is loaded the search button is enable


                    String countryCode = idVillage.substring(0, 4);
                    idDistrictC = idVillage.substring(4, 6);
                    idUpazilaC = idVillage.substring(6, 8);
                    idUnitC = idVillage.substring(8, 10);
                    idVillageC = idVillage.substring(10);


                    LoadListView loading = new LoadListView(countryCode, idDistrictC, idUpazilaC, idUnitC, idVillageC, "", strVillage);
                    loading.execute();


                } else {
                    adapter = new MemberSearchAdapter();
                    adapter.notifyDataSetChanged();
                    listOfMember.setAdapter(adapter);

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }// end of the village spinner

    /**
     * This Class i used For  Loading List in thread while data loading into view it will show a loader
     * dialog
     */

    private class LoadListView extends AsyncTask<Void, Integer, String> {


        private String temCCode;
        private String temDistCode;
        private String temUpazilaCode;
        private String temUnitCode;
        private String temVillageCode;
        private String memId;
        private String temVillageName;


        public LoadListView(final String temCCode, final String temDistCode, final String temUpazilaCode, final String temUnitCode, final String temVillageCode, final String memId, final String temVillageName) {

            this.temCCode = temCCode;
            this.temDistCode = temDistCode;
            this.temUpazilaCode = temUpazilaCode;
            this.temUnitCode = temUnitCode;
            this.temVillageCode = temVillageCode;
            this.memId = memId;
            this.temVillageName = temVillageName;

        }

        @Override
        protected String doInBackground(Void... params) {
            loadAssignedListData(temCCode, temDistCode, temUpazilaCode, temUnitCode, temVillageCode, memId, temVillageName);

            return "successes";


        }

        /**
         * Initiate the dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            startProgressBar("Data is Loading");

        }


        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();


            if (adapter != null) {
                adapter.notifyDataSetChanged();
                listOfMember.setAdapter(adapter);
                listOfMember.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                });
                listOfMember.setFocusableInTouchMode(true);

            } else {
                Log.d(TAG, "Adapter Is Empty ");

            }

        }
    }

    /**
     * @param msg text massage
     */

    private void startProgressBar(String msg) {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage(msg);
        pDialog.setCancelable(true);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.show();
    }

    /**
     * @param cCode  Country Code
     * @param dCode  LayR1 code
     * @param upCode LayR2 code
     * @param uCode  LayR3 code
     * @param vCode  LayR4 code
     * @param memId  15 digit member id
     */

    private void loadAssignedListData(final String cCode, final String dCode, final String upCode, final String uCode, final String vCode, final String memId, final String vName) {

        List<AssignDataModel> memberList = sqlH.getMemberList(cCode, dCode, upCode, uCode, vCode, memId);


        ArrayList<AssignDataModel> assignedArray = new ArrayList<AssignDataModel>();
        if (memberList.size() != 0) {

            assignedArray.clear();

            for (AssignDataModel data : memberList) {
                // add contacts data in arrayList
                assignedArray.add(data);
            }

/**
 * Assign the Adapter in list
 */
            adapter = new MemberSearchAdapter((Activity) MemberSearchPage.this, assignedArray, vCode, vName);
        }
    }


}
