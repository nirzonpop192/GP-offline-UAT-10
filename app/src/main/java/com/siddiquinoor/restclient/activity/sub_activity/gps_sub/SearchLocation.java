package com.siddiquinoor.restclient.activity.sub_activity.gps_sub;

import android.annotation.TargetApi;
import android.content.Intent;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.siddiquinoor.restclient.R;
import com.siddiquinoor.restclient.activity.MainActivity;
import com.siddiquinoor.restclient.activity.MapActivity;
import com.siddiquinoor.restclient.fragments.BaseActivity;
import com.siddiquinoor.restclient.manager.SQLiteHandler;
import com.siddiquinoor.restclient.utils.KEY;
import com.siddiquinoor.restclient.views.adapters.SearchLocationAdapter;
import com.siddiquinoor.restclient.views.helper.LocationHelper;
import com.siddiquinoor.restclient.views.helper.SpinnerHelper;

import java.util.List;

public class SearchLocation extends BaseActivity {
    /**
     * Adm Country Code
     */
    private String idCountry;
    /**
     * Button Search Location
     */
    Button btn_search_loc;
    /**
     * Database Helper Object
     */
    private SQLiteHandler sqlH;

    private static final String TAG = "SearchSubGroup";
    /**
     * Location List
     */
    private ListView lv_location;
    private EditText edt_searchLocation;

    Button btnHome, btnGps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_sub_group);
        initialize();
        Intent intent = getIntent();
        idCountry = intent.getExtras().getString(KEY.COUNTRY_ID);
        String dir = intent.getStringExtra(KEY.DIR_CLASS_NAME_KEY);
        if (dir.equals("MainActivity")) {

            Log.d(TAG, "id Country : id " + idCountry);

        }
        loadLocation(idCountry, "");

        searchLocation();
        footerAction();
    }

    private void searchLocation() {
        btn_search_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String locationName = edt_searchLocation.getText().toString().trim();
                if (locationName.length() > 0) {
                    loadLocation(idCountry, locationName);
                }
            }
        });
    }

    private void initialize() {
        viewReference();
        sqlH = new SQLiteHandler(SearchLocation.this);

    }

    /**
     * Refer the non java  object with java object
     */

    private void viewReference() {
        btn_search_loc = (Button) findViewById(R.id.btn_location_search);
        lv_location = (ListView) findViewById(R.id.lv_location);
        edt_searchLocation = (EditText) findViewById(R.id.edt_searchLocation);
    }


    /**
     * <p>This method Load the Location onto the spinner</p>
     *
     * @param cCode         Country Code
     * @param searchLocName Location Name
     */

    private void loadLocation(final String cCode, final String searchLocName) {
      /*  int position = 0;
        String criteria = "SELECT " + SQLiteHandler.GROUP_CODE_COL + " || '' ||" + SQLiteHandler.SUB_GROUP_CODE_COL + " || '' ||" + SQLiteHandler.LOCATION_CODE_COL
                + "," + SQLiteHandler.LOCATION_NAME_COL
                + " FROM " + SQLiteHandler.GPS_LOCATION_TABLE
                + " WHERE " + SQLiteHandler.COUNTRY_CODE_COL + " = '" + cCode + "'"
                + " AND " + SQLiteHandler.LOCATION_NAME_COL + " LIKE '%" + searchLocName + "%' "
                + " ORDER BY " + SQLiteHandler.LOCATION_NAME_COL + " ASC ";*/

        // Spinner Drop down elements for Location
//        final List<SpinnerHelper> listOfLocation = sqlH.getListAndID(SQLiteHandler.CUSTOM_QUERY, criteria, null, false);
        final List<LocationHelper> listOfLocation = sqlH.getLocationList(cCode, searchLocName);


        final SearchLocationAdapter dataAdapter = new SearchLocationAdapter(SearchLocation.this, listOfLocation, idCountry);


        lv_location.setAdapter(dataAdapter);

        lv_location.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }

    private void footerAction() {
        btnHome = (Button) findViewById(R.id.btnRegisterFooter);
        btnGps = (Button) findViewById(R.id.btnHomeFooter);

        setUpHomeButton();
        setUpGoToGpsButton();


        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(SearchLocation.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(SearchLocation.this, MapActivity.class);
                intent.putExtra(KEY.COUNTRY_ID, idCountry);
                intent.putExtra(KEY.DIR_CLASS_NAME_KEY, TAG);

                startActivity(intent);
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setUpHomeButton() {

        btnHome.setText("");
        Drawable imageHome = getResources().getDrawable(R.drawable.home_b);
        btnHome.setCompoundDrawablesRelativeWithIntrinsicBounds(imageHome, null, null, null);
        btnHome.setPadding(180, 10, 180, 10);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setUpGoToGpsButton() {
        btnGps.setText("");
        Drawable saveImage = getResources().getDrawable(R.drawable.add);
        btnGps.setCompoundDrawablesRelativeWithIntrinsicBounds(saveImage, null, null, null);
        btnGps.setPadding(180, 10, 180, 10);
    }

}
