package com.siddiquinoor.restclient.activity;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.siddiquinoor.restclient.R;
import com.siddiquinoor.restclient.activity.sub_activity.commu_group_sub.CommunityGroupNDetailsRecodes;
import com.siddiquinoor.restclient.fragments.BaseActivity;
import com.siddiquinoor.restclient.manager.SQLiteHandler;
import com.siddiquinoor.restclient.utils.KEY;
import com.siddiquinoor.restclient.views.adapters.CommunityGroupAdapter;
import com.siddiquinoor.restclient.views.adapters.CommunityGroupDataModel;
import com.siddiquinoor.restclient.views.helper.SpinnerHelper;

import java.util.ArrayList;
import java.util.List;

public class GroupSearchPage extends BaseActivity {
    // TODO: 10/18/2016  page reloading problem  
    private static final String TAG = "GroupSearchPage";
    private SQLiteHandler sqlH;
    private static ProgressDialog pDialog;
    private Button btnAddGroup,btnHome,btn_searchGroup;

    private EditText edt_groupSearch;
    private ListView listOfGroup;
    private CommunityGroupAdapter adapter;
    private String idCountry;
    private Spinner spCriteria;
    private String strCriteria;
    private String idCriteria;
    private String idAward,idDonor,idProgram;

    //   private String idService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_search_page);
        initialize();

        idCountry = getIntent().getStringExtra(KEY.COUNTRY_ID);
        loadProgram(idCountry);
        setListener();

    }

    private void setListener() {
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent iHome = new Intent(GroupSearchPage.this, MainActivity.class);
                startActivity(iHome);
            }
        });

        btn_searchGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String grpName = edt_groupSearch.getText().toString();
                if (grpName.length() > 0) {
                    if (idCriteria.length() > 2) {
                        LoadListView loading = new LoadListView(idCountry, idDonor, idAward, idProgram,grpName);
                        loading.execute();
                    }
                }


            }
        });

        btnAddGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupSearchPage.this, CommunityGroupNDetailsRecodes.class);

                intent.putExtra(KEY.ADD_FLAG_KEY, true);
                intent.putExtra(KEY.DONOR_CODE, idDonor);
                intent.putExtra(KEY.AWARD_CODE, idAward);
                intent.putExtra(KEY.PROGRAM_CODE, idProgram);


                startActivity(intent);
            }
        });
    }

    private void initialize() {
        sqlH = new SQLiteHandler(GroupSearchPage.this);

        viewReference();


    }

    /**
     * LOAD :: Criteria
     *
     * @param cCode Country Code
     */
    private void loadProgram(final String cCode) {

        int position = 0;
        String criteria = "SELECT " +
                SQLiteHandler.ADM_PROGRAM_MASTER_TABLE + "." + SQLiteHandler.DONOR_CODE_COL + " || '' || "
                + SQLiteHandler.ADM_PROGRAM_MASTER_TABLE + "." + SQLiteHandler.AWARD_CODE_COL + " || '' || "
                + SQLiteHandler.ADM_PROGRAM_MASTER_TABLE + "." + SQLiteHandler.PROGRAM_CODE_COL + " AS criteriaId" + " , " +
                SQLiteHandler.ADM_PROGRAM_MASTER_TABLE + "." + SQLiteHandler.PROGRAM_SHORT_NAME_COL + " AS Criteria" +
                " FROM " + SQLiteHandler.ADM_PROGRAM_MASTER_TABLE

                + " INNER JOIN " + SQLiteHandler.COUNTRY_PROGRAM_TABLE
                + " ON " + SQLiteHandler.ADM_PROGRAM_MASTER_TABLE + "." + SQLiteHandler.DONOR_CODE_COL + " = " + SQLiteHandler.COUNTRY_PROGRAM_TABLE + "." + SQLiteHandler.DONOR_CODE_COL
                + " AND " + SQLiteHandler.ADM_PROGRAM_MASTER_TABLE + "." + SQLiteHandler.AWARD_CODE_COL + " = " + SQLiteHandler.COUNTRY_PROGRAM_TABLE + "." + SQLiteHandler.AWARD_CODE_COL
                + " AND " + SQLiteHandler.ADM_PROGRAM_MASTER_TABLE + "." + SQLiteHandler.PROGRAM_CODE_COL + " =  " + SQLiteHandler.COUNTRY_PROGRAM_TABLE + "." + SQLiteHandler.PROGRAM_CODE_COL

                + " WHERE " + SQLiteHandler.COUNTRY_PROGRAM_TABLE + "." + SQLiteHandler.COUNTRY_CODE_COL + " = '" + cCode + "' "

                + " GROUP BY " + SQLiteHandler.ADM_PROGRAM_MASTER_TABLE + "." + SQLiteHandler.PROGRAM_SHORT_NAME_COL
                + " ORDER BY Criteria ";



        List<SpinnerHelper> listCriteria = sqlH.getListAndID(SQLiteHandler.CUSTOM_QUERY, criteria, null, false);
        ArrayAdapter<SpinnerHelper> dataAdapter = new ArrayAdapter<SpinnerHelper>(this, R.layout.spinner_layout, listCriteria);
        dataAdapter.setDropDownViewResource(R.layout.spinner_layout);

        spCriteria.setAdapter(dataAdapter);


        if (idCriteria != null) {
            for (int i = 0; i < spCriteria.getCount(); i++) {
                String award = spCriteria.getItemAtPosition(i).toString();
                if (award.equals(strCriteria)) {
                    position = i;
                }
            }
            spCriteria.setSelection(position);
        }


        spCriteria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strCriteria = ((SpinnerHelper) spCriteria.getSelectedItem()).getValue();
                idCriteria = ((SpinnerHelper) spCriteria.getSelectedItem()).getId();
                if (idCriteria.length() > 2) {

                    if (idCriteria.length() > 2) {
                        idDonor = idCriteria.substring(0, 2);
                        idAward = idCriteria.substring(2, 4);
                        idProgram = idCriteria.substring(4, 7);
                        // idService = idCriteria.substring(7);
                        /**                         * for test purpose (to check error )   {@link GroupSearchPage#loadCommunityGroupListData(String, String, String, String, String) method}                   */
                       // loadCommunityGroupListData(idCountry, idDonor, idAward, idProgram, "");
                        LoadListView loading = new LoadListView(idCountry, idDonor, idAward, idProgram, "");
                        loading.execute();
                    }


                }


            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    } // end Load Spinner


    /**
     * Refer the XML views with java object
     */
    private void viewReference() {

        listOfGroup = (ListView) findViewById(R.id.lv_group);
        btnHome = (Button) findViewById(R.id.btnRegisterFooter);
        btnAddGroup = (Button) findViewById(R.id.btnHomeFooter);
        spCriteria = (Spinner) findViewById(R.id.search_Group_spCriteria);
        btn_searchGroup = (Button) findViewById(R.id.btn_groupSearch);
        edt_groupSearch = (EditText) findViewById(R.id.edt_groupSearch);

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void addIconHomeButton() {
        btnHome.setText("");
        Drawable imageHome = getResources().getDrawable(R.drawable.home_b);
        btnHome.setCompoundDrawablesRelativeWithIntrinsicBounds(imageHome, null, null, null);
        setPaddingButton(GroupSearchPage.this, imageHome, btnHome);

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void addIconAddGroupButton() {
        btnAddGroup.setText("");
        Drawable addImage = getResources().getDrawable(R.drawable.add);
        btnAddGroup.setCompoundDrawablesRelativeWithIntrinsicBounds(addImage, null, null, null);
        setPaddingButton(GroupSearchPage.this, addImage, btnAddGroup);

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
        addIconAddGroupButton();
    }

    private class LoadListView extends AsyncTask<Void, Integer, String> {

        // private String mMemberIdS;// for member id search
        private String temCCode;
        private String temDonorCode;
        private String temAwardCode;
        private String temProgCode;
        private String temSrvCode;
        private String groupName;


        public LoadListView(final String temCCode, final String temDonorCode, final String temAwardCode, final String temProgCode, final String groupName) {
            /*this.mMemberIdS = mMemberIdS;*/
            this.temCCode = temCCode;
            this.temDonorCode = temDonorCode;
            this.temAwardCode = temAwardCode;
            this.temProgCode = temProgCode;
            // this.temSrvCode = temSrvCode;
            this.groupName = groupName;

        }

        @Override
        protected String doInBackground(Void... params) {


            loadCommunityGroupListData(temCCode, temDonorCode, temAwardCode, temProgCode, groupName);


            return "successes";


        }


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
                listOfGroup.setAdapter(adapter);

                listOfGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                });
                listOfGroup.setFocusableInTouchMode(true);

            } else {
//                Log.d(TAG, "Adapter Is Empety ");

            }

        }
    }

    private void startProgressBar(String msg) {

        pDialog = new ProgressDialog(this);
        pDialog.setMessage(msg);
        pDialog.setCancelable(true);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.show();
    }

    private void loadCommunityGroupListData(final String cCode, final String donorCode, final String awardCode, final String progCode, final String groupName) { // mwmSId = memeber searchin variable

        List<CommunityGroupDataModel> groupList = sqlH.getCommunityGroupList(cCode, donorCode, awardCode, progCode, groupName);


        ArrayList<CommunityGroupDataModel> groupArray = new ArrayList<CommunityGroupDataModel>();
        if (groupList.size() != 0) {
            groupArray.clear();
            for (CommunityGroupDataModel asdata : groupList) {

                groupArray.add(asdata);
            }
            adapter = new CommunityGroupAdapter((Activity) GroupSearchPage.this, groupArray);
        }
    }


}
