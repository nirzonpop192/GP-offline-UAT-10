package com.siddiquinoor.restclient.activity.sub_activity.summary_sub;

/**
 * Activity for presenting the list of all entry in a list view
 * with image and details
 *
 * @author Faisal Mohammad, Refat Hassan
 * @desc TechnoDhaka.
 * @link
 * @version 1.3.0
 * @since 1.0
 * Created on 3/5/2015
 */

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.siddiquinoor.restclient.activity.MainActivity;
import com.siddiquinoor.restclient.R;
import com.siddiquinoor.restclient.activity.Register;
import com.siddiquinoor.restclient.fragments.BaseActivity;
import com.siddiquinoor.restclient.manager.SQLiteHandler;
import com.siddiquinoor.restclient.utils.KEY;
import com.siddiquinoor.restclient.views.adapters.SummaryListAdapter;
import com.siddiquinoor.restclient.views.adapters.SummaryModel;

import java.util.ArrayList;
import java.util.List;


public class Summary extends BaseActivity {

    private Button btnReg;
    private Button btnHome;
    private TextView title;
    private SQLiteHandler sqlH;

    private ArrayList<SummaryModel> summaryArr = new ArrayList<SummaryModel>();

    private SummaryListAdapter adapter;
    private ListView listView;
    private String village;
    private String village_code;
    private String TAG = Summary.class.getName();
    String idCountry;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set View to register.xml
        setContentView(R.layout.activity_summary);


        viewReference();
        Intent intent = getIntent();
        idCountry = intent.getStringExtra(KEY.COUNTRY_ID);

        //  Log.d(TAG, " id country : " + idCountry);

        sqlH = new SQLiteHandler(getApplicationContext());

        setListener();


        String layer_title = sqlH.getLayerLabel(getCountryCode(), "4");
        //   Log.d(TAG, " layer_title : " + layer_title);


        title.setText(layer_title);


        List<SummaryModel> listData = sqlH.getTotalRecords(idCountry);

        for (SummaryModel data : listData) {
            // add contacts data in arrayList
            summaryArr.add(data);
        }

        adapter = new SummaryListAdapter(this, summaryArr);

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                village_code = summaryArr.get(position).getVillCode();
                village = summaryArr.get(position).getVillName();

                //  Log.d(TAG, "village :" + village + " , village_code : " + village_code);

                Intent dIntent = new Intent(Summary.this, RegisterRecordView.class);

                dIntent.putExtra("village_code", village_code);
                dIntent.putExtra("village", village);

                finish();
                startActivity(dIntent);


            }
        });

    } // end onCreate

    private void setListener() {

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent iReg = new Intent(getApplicationContext(), Register.class);
                iReg.putExtra("country_code", idCountry);
                //   iReg.putExtra("country_name", strCountry);
                // startActivity(iReg);
                finish();
                startActivity(iReg);


            }
        });


        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentHome = new Intent(getApplicationContext(), MainActivity.class);
                finish();
                startActivity(intentHome);

            }
        });
    }

    private void viewReference() {
        btnHome = (Button) findViewById(R.id.btnRegisterFooter);
        btnReg = (Button) findViewById(R.id.btnHomeFooter);
        title = (TextView) findViewById(R.id.hh_s_text_table);
        listView = (ListView) findViewById(R.id.list_village_records); // in activity_summary.xml


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


}
