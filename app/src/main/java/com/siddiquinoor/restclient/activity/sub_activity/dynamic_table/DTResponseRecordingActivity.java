package com.siddiquinoor.restclient.activity.sub_activity.dynamic_table;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Base64;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import com.siddiquinoor.restclient.R;
import com.siddiquinoor.restclient.data_model.DTQResModeDataModel;
import com.siddiquinoor.restclient.data_model.DTResponseTableDataModel;
import com.siddiquinoor.restclient.data_model.DT_ATableDataModel;
import com.siddiquinoor.restclient.fragments.BaseActivity;
import com.siddiquinoor.restclient.manager.SQLiteHandler;
import com.siddiquinoor.restclient.manager.sqlsyntax.SQLServerSyntaxGenerator;
import com.siddiquinoor.restclient.utils.CameraUtils;
import com.siddiquinoor.restclient.utils.KEY;
import com.siddiquinoor.restclient.views.adapters.DynamicDataIndexDataModel;
import com.siddiquinoor.restclient.views.adapters.DynamicTableQuesDataModel;
import com.siddiquinoor.restclient.views.helper.SpinnerHelper;
import com.siddiquinoor.restclient.views.notifications.ADNotificationManager;
import com.siddiquinoor.restclient.views.spinner.SpinnerLoader;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.siddiquinoor.restclient.utils.CameraUtils.CAMERA_REQUEST_1;


public class DTResponseRecordingActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {

    public static final String TEXT = "Text";
    public static final String NUMBER = "Number";
    public static final String Date = "Date";
    public static final String Date_OR_Time = "Datetime";
    public static final String TEXT_BOX = "Textbox";
    //public static final String PHOTO = "Photo";
    public static final String COMBO_BOX = "Combobox";
    public static final String GEO_LAYER_3 = "Geo Layer 3";

    public static final String GEO_LAYER_2 = "Geo Layer 2";
    public static final String GEO_LAYER_1 = "Geo Layer 1";
    public static final String GEO_LAYER_4 = "Geo Layer 4";
    public static final String GEO_LAYER_ADDRESS = "Geo Layer Address";
    public static final String SERVICE_SITE = "Service Site";
    public static final String DISTRIBUTION_POINT = "Distribution Point";
    public static final String COMMUNITY_GROUP = "Community Group";
    public static final String ORGANIZATION_LIST = "Organization List";
    public static final String CHECK_BOX = "Checkbox";
    public static final String RADIO_BUTTON = "Radio Button";
    public static final String DATE_TIME = "Datetime";
    public static final String PHOTO = "Image";
    public static final String DATE = "Date";
    public static final String RADIO_BUTTON_N_TEXTBOX = "Radio Button, Textbox";
    public static final String CHECKBOX_N_TEXTBOX = "Checkbox, Textbox";
    public static final String LOOKUP_LIST = "Lookup List";
    /**
     * Database helper
     */
    private SQLiteHandler sqlH;
    /**
     * alert Dialog
     */
    private ADNotificationManager dialogManager;
    private final Context mContext = DTResponseRecordingActivity.this;
    private DynamicDataIndexDataModel dyIndex;
    private int totalQuestion;
    private TextView tv_DtQuestion;
    private Button btnNextQues;
    private Button btnHome;
    private DynamicTableQuesDataModel mDTQ;
    private Button btnPreviousQus;

    /**
     * used in {@link #onActivityResult(int, int, Intent)}
     */
    Bitmap mPhotoBitmap;

    /**
     * fileUri is use for image rotation
     */
    //private Uri fileUri;
    /**
     * question index
     */
    int mQusIndex;
    /**
     * For Date time picker
     */
    private SimpleDateFormat mFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);
    private Calendar calendar = Calendar.getInstance();
    /**
     * Dynamic view
     */
    private Spinner dt_spinner;
    private ImageView dt_photo;
    private EditText dt_edt;
    private TextView _dt_tv_DatePicker;
    private RadioGroup radioGroup;
//    private RadioButton rdbtn;
    /**
     * To determined the either any Check box is Selected or nor
     */
    private int countChecked = 0;

    private String idSpinner;
    private String strSpinner;


    /**
     * DTQResMode
     */
    DTQResModeDataModel mDTQResMode;

    /**
     * #mDTATable is Deliminator of Check Box item &  value
     * it is assigned by {@link #displayQuestion(DynamicTableQuesDataModel)} method
     */
    List<DT_ATableDataModel> mDTATableList;
    /**
     * List for Dynamic
     */

    private List<RadioButton> mRadioButton_List = new ArrayList<RadioButton>();

    private List<CheckBox> mCheckBox_List = new ArrayList<CheckBox>();

    /**
     * we use global for
     */


    private RadioGroup radioGroupForRadioAndEditText;

    /**
     * this is a list of Radio button which is created in runtime dynamically.
     * in a linear layout  {@link #ll_editText}
     */
    private List<RadioButton> mRadioButtonForRadioAndEdit_List = new ArrayList<RadioButton>();
    private List<EditText> mEditTextForRadioAndEdit_List = new ArrayList<EditText>();
    private List<EditText> mEditTextForCheckBoxAndEdit_List = new ArrayList<EditText>();
    private List<CheckBox> mCheckBoxForCheckBoxAndEdit_List = new ArrayList<CheckBox>();

    private LinearLayout dt_layout_Radio_N_EditText;

    /**
     * this is base64 image to upload
     */
    private String imageString;

    public String getImageString() {
        return imageString;
    }

    /**
     * @param imageString base64 string of image
     */
    public void setImageString(String imageString) {
        this.imageString = imageString;
    }

    /**
     * Layout
     */
    private LinearLayout parent_layout_onlyFor_CB;
    private LinearLayout parent_layout_FOR_CB_N_ET;
    /**
     * This layout is child of
     * {@link #parent_layout_FOR_CB_N_ET}
     */
    private LinearLayout subParent_CB_layout_FOR_CB_N_ET;
    /**
     * This layout is child of
     * {@link #parent_layout_FOR_CB_N_ET}
     */
    private LinearLayout subParent_ET_layout_FOR_CB_N_ET;

    private LinearLayout ll_editText;


    /**
     * mDTResponse Sequence  DTRSeq
     */
    private int mDTRSeq;
    private int surveyNumber;
    /**
     * used in  {@link #showPhotoCaptureDialog(int)}  method
     */

    private Dialog imageCaptureOptionDialog;

    /**
     * @param sIState savedInstanceState
     */

    @Override
    protected void onCreate(Bundle sIState) {
        super.onCreate(sIState);
        setContentView(R.layout.activity_dt__qustion);
        inti();
        setListener();
    }


    private void setListener() {
        btnNextQues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProcessValidation();
            }
        });
        btnPreviousQus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mQusIndex != totalQuestion) {
                    removeStopIconNextButton(btnNextQues);
                }
                previousQuestion();
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToHomeWithDialog();
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void addIconHomeButton() {
        btnHome.setText("");
        Drawable imageHome = getResources().getDrawable(R.drawable.home_b);
        btnHome.setCompoundDrawablesRelativeWithIntrinsicBounds(imageHome, null, null, null);
        setPaddingButton(mContext, imageHome, btnHome);
    }


    /**
     * this method set icon in method
     *
     * @param button button view
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void addNextOrPreviousButton(Button button) {
        button.setText("");
        Drawable imageHome;
        if (button == btnNextQues)
            imageHome = getResources().getDrawable(R.drawable.goto_forward);
        else
            imageHome = getResources().getDrawable(R.drawable.goto_back);

        button.setCompoundDrawablesRelativeWithIntrinsicBounds(imageHome, null, null, null);
        setPaddingButton(mContext, imageHome, button);
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
        addNextOrPreviousButton(btnNextQues);
        addNextOrPreviousButton(btnPreviousQus);
    }

    /**
     *
     */
    private void goToHomeWithDialog() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        /**
         *  in unfinished condition if anyone press home button
         *  Setting Dialog Title
         */

        alertDialog.setTitle("Home");

        String massage;
        if (mQusIndex < totalQuestion) {

            massage = "Your response is incomplete.\nDo you want to quit ?";
            // On pressing Settings button
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();

                    dialogManager.deleteResponseConfirmationDialog(DTResponseRecordingActivity.this, dyIndex.getDtBasicCode(), dyIndex.getcCode(), dyIndex.getDonorCode(), dyIndex.getAwardCode(), dyIndex.getProgramCode(), dyIndex.getOpMode(), dyIndex.getOpMonthCode(), getStaffID(), mDTRSeq, sqlH);
                }
            });

        } else {
            massage = " Do you want to go to Home page ?";
            // On pressing Settings button
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    goToMainActivity((Activity) mContext);

                }
            });

        }
        alertDialog.setMessage(massage);
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }


    /**
     * Change The Color of Question  to Indicate the Error occurred
     * if user
     */

    private void errorIndicator() {
        tv_DtQuestion.setTextColor(getResources().getColor(R.color.red));
    }

    /**
     * Change the color of question at normal stage
     */
    private void normalIndicator() {
        tv_DtQuestion.setTextColor(getResources().getColor(R.color.blue_dark));
    }

    /**
     * Check All type of Validation For
     */

    private void saveProcessValidation() {

        int i = 0;
        String responseControl = mDTQResMode.getDtResponseValueControl();


        if (mDTQ.getAllowNullFlag().equals("N")) {

            switch (responseControl) {
                case TEXT_BOX:

                    String edtInput = dt_edt.getText().toString();

                    if (edtInput.equals("Text") || edtInput.equals("Number") || edtInput.length() == 0) {
                        errorIndicator();
                        displayError("Insert  Text");
                    } else {
                        normalIndicator();
                        saveData(edtInput, "", mDTATableList.get(0));

                        // load next Question
                        nextQuestion();

                    }

                    break;
                case Date_OR_Time:

                    if (_dt_tv_DatePicker.getText().toString().equals("Click for Date")) {
                        errorIndicator();
                        displayError("Set Date First");

                    } else {
                        normalIndicator();

                        /**                         * mDTATableList.get(0) wil be single                         */
                        saveData(_dt_tv_DatePicker.getText().toString(), "", mDTATableList.get(0));
                        nextQuestion();
                    }
                    break;
                case COMBO_BOX:
                    /** here it get null point reference if spinner get no values */
                    if (idSpinner != null) {
                        if (idSpinner.equals("00")) {

                            errorIndicator();
                            displayError("Select Item");

                        } else {
                            normalIndicator();
                            /**                             * {@link DTResponseRecordingActivity#saveData(String, DT_ATableDataModel)}                             */
                            saveData(strSpinner, "", mDTATableList.get(0));

                            /** load   NEXT QUESTION                         */
                            nextQuestion();
                        }
                    }
                    break;
                case CHECK_BOX:

                    if (countChecked <= 0) {
                        errorIndicator();
                        displayError("Select a option.");

                    } else {
                        normalIndicator();

                        i = 0;
                        for (CheckBox cb : mCheckBox_List) {
                            if (cb.isChecked()) {

                                saveData("", "", mDTATableList.get(i));
                            }// end of if condition
                            i++;
                        }// end of for each loop
                    }// end of else

                    nextQuestion();
                    break;

                case RADIO_BUTTON:
                    i = 0;
                    for (RadioButton rb : mRadioButton_List) {
                        if (rb.isChecked()) {

                            saveData("", "", mDTATableList.get(i));
                        }
                        i++;
                    }
                    nextQuestion();
                    break;


                case RADIO_BUTTON_N_TEXTBOX:

                    boolean error = false;

                    i = 0;
                    for (RadioButton rb : mRadioButtonForRadioAndEdit_List) {
                        if (rb.isChecked()) {

                            if (mEditTextForRadioAndEdit_List.get(i).getText().length() == 0) {
                                errorIndicator();
                                displayError("Insert value for Selected Option");
                                error = true;
                                break;

                            } else {
                                normalIndicator();

                                saveData(mEditTextForRadioAndEdit_List.get(i).getText().toString(), "", mDTATableList.get(i));
                            }
                        }
                        i++;    //increment
                    }

                    if (!error)
                        nextQuestion();
                    break;

                case CHECKBOX_N_TEXTBOX:


                    normalIndicator();
                    int k = 0;
                    for (CheckBox cb : mCheckBoxForCheckBoxAndEdit_List) {
                        if (cb.isChecked()) {
                            Toast.makeText(mContext, "Radio Button no:" + (k + 1) + " is checked"
                                    + " the value of the : " + mEditTextForCheckBoxAndEdit_List.get(k).getText(), Toast.LENGTH_SHORT).show();
                            if (mEditTextForCheckBoxAndEdit_List.get(k).getText().length() == 0) {
                                errorIndicator();
                                displayError("Insert value for Selected Option");
                                break;
                            } else {
                                normalIndicator();

                                saveData(mEditTextForCheckBoxAndEdit_List.get(k).getText().toString(), "", mDTATableList.get(k));
                            }


                        }
                        k++;
                    }
                    nextQuestion();
                    break;
                case PHOTO:

                    if (getImageString() == null) {
                        errorIndicator();
                        displayError("Insert  Image");
                    } else {

                        saveData("", getImageString(), mDTATableList.get(i));
                        normalIndicator();
                        nextQuestion();
//                        Toast.makeText(mContext, "PHOto save ", Toast.LENGTH_SHORT).show();
                    }
                    break;


            }// end of switch

        }// end of the AllowNullFlag
        else {


            /**
             *  TODO: 9/29/2016  save method & update method
             */
//                    saveData("");


            /**
             * //NEXT QUESTION
             */
            nextQuestion();


        }// end of else where ans is not magneto


    }


    /**
     * this method show the error dialog
     *
     * @param errorMsg Massage In valid
     */

    private void displayError(String errorMsg) {
        dialogManager.showWarningDialog(mContext, errorMsg);
    }

    /**
     * @param ansValue answer value
     * @param dtATable dynamic Answer table
     */
    /**
     * @param ansValue    answer value
     * @param imageString image String base64 Format
     * @param dtATable    dynamic Answer table
     */
    private void saveData(String ansValue, String imageString, DT_ATableDataModel dtATable) {
        saveOnResponseTable(ansValue, imageString, dtATable);
    }

    /**
     * @param ansValue user input
     * @param dtATable DTA Table
     */
    /**
     * @param ansValue    user input / spinner value / radio button code / edit text values
     * @param imageString base 64 image string
     * @param dtATable    dynamic Answer table module
     */

    private void saveOnResponseTable(String ansValue, String imageString, DT_ATableDataModel dtATable) {


        String DTBasic = dyIndex.getDtBasicCode();
        String AdmCountryCode = dyIndex.getcCode();
        String AdmDonorCode = dyIndex.getDonorCode();
        String AdmAwardCode = dyIndex.getAwardCode();
        String AdmProgCode = dyIndex.getProgramCode();
        String DTEnuID = getStaffID();

        String DTQCode = mDTQ.getDtQCode();
        String DTQText = mDTQ.getqText();
        String DTACode = dtATable.getDt_ACode();
        /**    DTRSeq is user input serial no         */
        int DTRSeq = mDTRSeq;
        String DTAValue = null;
        String ProgActivityCode = dyIndex.getProgramActivityCode();
        String DTTimeString = null;
        try {
            DTTimeString = getDateTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String OpMode = dyIndex.getOpMode();
        String OpMonthCode = dyIndex.getOpMonthCode();
        /**
         * todo : set the  DT Q data type
         */
        String DataType = dtATable.getDataType();


        DTAValue = dtATable.getDt_AValue().equals("null") || dtATable.getDt_AValue().length() == 0 ? ansValue : dtATable.getDt_AValue();


        /**
         * main execute
         * Insert or update operation
         */
        if (sqlH.isDataExitsInDTAResponse_Table(DTBasic, AdmCountryCode, AdmDonorCode, AdmAwardCode, AdmProgCode, DTEnuID, DTQCode, DTACode, mDTRSeq)) {
            sqlH.updateIntoDTResponseTable(DTBasic, AdmCountryCode, AdmDonorCode, AdmAwardCode, AdmProgCode, DTEnuID, DTQCode, DTACode,
                    String.valueOf(DTRSeq), DTAValue, ProgActivityCode, DTTimeString, OpMode, OpMonthCode, DataType, imageString);
            sqlH.updateIntoDTSurveyTable(DTBasic, AdmCountryCode, AdmDonorCode, AdmAwardCode, AdmProgCode, DTEnuID, DTQCode, DTACode,
                    String.valueOf(DTRSeq), DTAValue, ProgActivityCode, DTTimeString, OpMode, OpMonthCode, DataType, DTQText, surveyNumber);

        } else {

            sqlH.addIntoDTResponseTable(DTBasic, AdmCountryCode, AdmDonorCode, AdmAwardCode, AdmProgCode, DTEnuID, DTQCode, DTACode,
                    String.valueOf(DTRSeq), DTAValue, ProgActivityCode, DTTimeString, OpMode, OpMonthCode, DataType, imageString
                    , true);
            sqlH.addIntoDTSurveyTable(DTBasic, AdmCountryCode, AdmDonorCode, AdmAwardCode, AdmProgCode, DTEnuID, DTQCode, DTACode,
                    String.valueOf(DTRSeq), DTAValue, ProgActivityCode, DTTimeString, OpMode, OpMonthCode, DataType, DTQText, surveyNumber);

        }


    }


    /**
     * this method  delete the unfinished data form the
     *
     * @param dtBasicCode   Dynamic Table  Basic Code
     * @param cCode         Adm Country Code
     * @param donorCode     Adm Donor Code
     * @param awardCode     Adm Award Code
     * @param progCode      Adm Program Code
     * @param OpMode        Op Code
     * @param opMonthCode   op Month Code
     * @param DTEnuID       Dt Eliminator Id
     * @param DTRSeq        Dynamic Table Response Sequence
     * @param sqLiteHandler sqLiteHandler
     */

    public static void deleteFromResponseTable(String dtBasicCode, String cCode, String donorCode, String awardCode, String progCode, String OpMode, String opMonthCode, String DTEnuID, int DTRSeq, SQLiteHandler sqLiteHandler) {

        /**         *  total Question no is less then index no
         *  the Delete Syntax in  the {@link SQLiteHandler#deleteFromDTResponseTable(String, String, String, String, String, String, int, String, String)}         */


        if (dtBasicCode != null && cCode != null && donorCode != null && awardCode != null && progCode != null && DTEnuID != null && DTRSeq != 0)
            sqLiteHandler.deleteFromDTResponseTable(dtBasicCode, cCode, donorCode, awardCode, progCode, DTEnuID, DTRSeq, OpMode, opMonthCode);

    }

    /**
     * Load the next Question
     */

    private void nextQuestion() {


        if (btnPreviousQus.getVisibility() == View.INVISIBLE)
            btnPreviousQus.setVisibility(View.VISIBLE);


        // increments the question no index
        ++mQusIndex;

        //to check does index exceed the maximum value

        if (mQusIndex != totalQuestion)
            hideViews();


        if (mQusIndex < totalQuestion) {
            DynamicTableQuesDataModel nextQus = loadNextQuestion(dyIndex.getDtBasicCode(), mQusIndex);

            displayQuestion(nextQus);
            // set arrow icon instead of stop icon . set previous  state
            removeStopIconNextButton(btnPreviousQus);
            // no need to delete bellow code
            //   if (mQusIndex == totalQuestion - 1) {
            //   addSaveIconButton(btnNextQues);
            //   }


        } else if (mQusIndex == totalQuestion) {

            // when all the saved complete generate as Sp
            SQLServerSyntaxGenerator mSyntaxGenerator = new SQLServerSyntaxGenerator();
            mSyntaxGenerator.setDTBasic(dyIndex.getDtBasicCode());
            mSyntaxGenerator.setDtShortName(dyIndex.getDtShortName());
            mSyntaxGenerator.setAdmCountryCode(dyIndex.getcCode());
            mSyntaxGenerator.setAdmDonorCode(dyIndex.getDonorCode());
            mSyntaxGenerator.setAdmAwardCode(dyIndex.getAwardCode());
            mSyntaxGenerator.setAdmProgCode(dyIndex.getProgramCode());
            mSyntaxGenerator.setOpMonthCode(dyIndex.getOpMonthCode());


            sqlH.insertIntoUploadTable(mSyntaxGenerator.sqlSpDTShortName_Save());
            Toast.makeText(mContext, "Saved Successfully", Toast.LENGTH_SHORT).show();

            /// Bellow Code end the
            addStopIconButton(btnNextQues);


        } else if (mQusIndex > totalQuestion) {
            continueDialog();
        }
    }

    /**
     * This method show a dialog to ask user to collect the survey data again.
     * If user press yes then  this method  will invoke the
     * {@link #initialWithFirstQues()} to start to collect response again.
     * For continuing to a new question series, no need to show the previous image. So I just make the global variable {@link #mPhotoBitmap} null
     */
    private void continueDialog() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setTitle("Continue !!");

        // On pressing Settings button
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                mPhotoBitmap.recycle();
                mPhotoBitmap = null;
                removeStopIconNextButton(btnNextQues);
                initialWithFirstQues();
            }
        });

        // Setting Dialog Message
        alertDialog.setMessage("Do you want to continue ?");

        // on pressing cancel button
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                goToMainActivity((Activity) mContext);
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    /**
     * this method load previous Question. from data set
     */

    private void previousQuestion() {
        --mQusIndex;
        hideViews();

        // to check does index exceed the minimum  value

        if (mQusIndex >= 0) {
            DynamicTableQuesDataModel nextQus = loadPreviousQuestion(dyIndex.getDtBasicCode(), mQusIndex);
            displayQuestion(nextQus);

            if (mQusIndex == 0)
                addStopIconButton(btnPreviousQus);

        } else if (mQusIndex < 0) {
            mQusIndex = 0;

        }
    }

    /**
     * @param qusObject DTQTable object
     *                  {@link #mDTATableList} must be assigned before invoking {@link #loadDT_QResMode(String)}
     *                  {@link #mDTATableList} needed in {@link #saveData(String, String, DT_ATableDataModel)}  method
     */

    private void displayQuestion(DynamicTableQuesDataModel qusObject) {
        tv_DtQuestion.setText(qusObject.getqText());
        mDTATableList = sqlH.getDTA_Table(qusObject.getDtBasicCode(), qusObject.getDtQCode());
        /**
         * {@link #mDTATableList} if it's size is zero than there will be IndexOutOfBoundsException
         * occur
         * the poxy data prevent to occur that Exception
         */
        if (mDTATableList.size() == 0) {
            DT_ATableDataModel proxyDATA_data = new DT_ATableDataModel(mDTQ.getDtBasicCode(), mDTQ.getDtQCode(), "null", "No Recoded in DB", "null", "null", "null", "null", "null", "null", "N", 0, 0, "Text", "null");
            mDTATableList.add(proxyDATA_data);
        }

        loadDT_QResMode(qusObject.getqResModeCode());

    }

    /**
     * initial state
     * also views refer
     * initiate {@link #sqlH} & {@link #dialogManager}
     * invoke :{@link #viewReference()} method  for instance
     */

    private void inti() {
        viewReference();
        sqlH = new SQLiteHandler(mContext);

        dialogManager = new ADNotificationManager();
        Intent intent = getIntent();
        dyIndex = intent.getParcelableExtra(KEY.DYNAMIC_INDEX_DATA_OBJECT_KEY);
        totalQuestion = intent.getIntExtra(KEY.DYNAMIC_T_QUES_SIZE, 0);
        initialWithFirstQues();
    }

    /**
     * this method load the first question
     */
    private void initialWithFirstQues() {
        // if 1st question appears  then remove the previous button
        btnPreviousQus.setVisibility(View.INVISIBLE);
        surveyNumber = sqlH.getSurveyNumber(dyIndex.getDtBasicCode(), dyIndex.getcCode(), dyIndex.getDonorCode(), dyIndex.getAwardCode(), dyIndex.getProgramCode(), getStaffID());

        // set question index to zero
        mQusIndex = 0;
        mDTRSeq = sqlH.getNextDTResponseSequence(dyIndex.getDtBasicCode(), dyIndex.getcCode(), dyIndex.getDonorCode(), dyIndex.getAwardCode(), dyIndex.getProgramCode(), getStaffID());

        // hide the view
        hideViews();

        DynamicTableQuesDataModel qus = fistQuestion(dyIndex.getDtBasicCode());
        displayQuestion(qus);
    }

    /**
     * Hide the dynamic views
     */
    private void hideViews() {
        _dt_tv_DatePicker.setVisibility(View.GONE);
        dt_edt.setVisibility(View.GONE);
        dt_spinner.setVisibility(View.GONE);
        parent_layout_onlyFor_CB.setVisibility(View.GONE);
        radioGroup.setVisibility(View.GONE);
        dt_layout_Radio_N_EditText.setVisibility(View.GONE);
        parent_layout_FOR_CB_N_ET.setVisibility(View.GONE);
        dt_photo.setVisibility(View.GONE);
    }

    /**
     * Refer the all the necessary view in java object
     */
    private void viewReference() {
        tv_DtQuestion = (TextView) findViewById(R.id.tv_DtQuestion);

        btnHome = (Button) findViewById(R.id.btnHomeFooter);
        btnNextQues = (Button) findViewById(R.id.btn_dt_next);
        btnPreviousQus = (Button) findViewById(R.id.btn_dt_preview);
        Button btnGone = (Button) findViewById(R.id.btnRegisterFooter);
        btnGone.setVisibility(View.GONE);
        // next & preview button

        parent_layout_onlyFor_CB = (LinearLayout) findViewById(R.id.ll_checkBox);
        _dt_tv_DatePicker = (TextView) findViewById(R.id.tv_dtTimePicker);
        dt_edt = (EditText) findViewById(R.id.edt_dt);
        dt_spinner = (Spinner) findViewById(R.id.dt_sp);
        dt_photo = (ImageView) findViewById(R.id.dt_iv_photo);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        ll_editText = (LinearLayout) findViewById(R.id.llEditText);
        radioGroupForRadioAndEditText = (RadioGroup) findViewById(R.id.radioGroupForRadioAndEdit);
        //CheckBox and EditText

        parent_layout_FOR_CB_N_ET = (LinearLayout) findViewById(R.id.ll_CheckBoxAndEditTextParent);
        subParent_CB_layout_FOR_CB_N_ET = (LinearLayout) findViewById(R.id.ll_checkBoxAndEditTextCheckbox);
        subParent_ET_layout_FOR_CB_N_ET = (LinearLayout) findViewById(R.id.et_CheckBoxAndEditText);
        dt_layout_Radio_N_EditText = (LinearLayout) findViewById(R.id.ll_radioGroupAndEditText);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void addStopIconButton(Button button) {
        button.setText("");
        Drawable imageHome = getResources().getDrawable(R.drawable.stop);
        button.setCompoundDrawablesRelativeWithIntrinsicBounds(imageHome, null, null, null);
        setPaddingButton(mContext, imageHome, button);
    }


    /**
     * This method is used for  hiding the soft keypad.
     * parameter will take the view which is shown right now.
     * .Note : this method must be called after view is Visible , otherwise it exception
     *
     * @param view button /spinner/ check box / Edit text /Text View any thing
     */
    public void hideSoftKayPad(View view) {
        InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    /**
     * this method set the error point
     *
     * @param button either {@link #btnPreviousQus} or {{@link #btnNextQues}}
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void removeStopIconNextButton(Button button) {
        button.setText("");
        Drawable imageHome;
        if (button == btnPreviousQus)
            imageHome = getResources().getDrawable(R.drawable.goto_back);
        else
            imageHome = getResources().getDrawable(R.drawable.goto_forward);

        button.setCompoundDrawablesRelativeWithIntrinsicBounds(imageHome, null, null, null);
        setPaddingButton(mContext, imageHome, button);
    }


    /**
     * the method invoked once  in {@link  #onCreate(Bundle)}
     *
     * @param dtBasic dtBasic code as primary key
     * @return Ques object  of first index
     */
    private DynamicTableQuesDataModel fistQuestion(final String dtBasic) {
        return loadQuestion(dtBasic, 0);
    }

    /**
     * this method load the next question
     * invoking in {@link #btnNextQues}
     *
     * @param dtBasic  dtBasic code as primary key
     * @param qusIndex Qus  index
     * @return Ques object  of given index
     */
    private DynamicTableQuesDataModel loadNextQuestion(final String dtBasic, final int qusIndex) {
        return loadQuestion(dtBasic, qusIndex);
    }

    /**
     * invoking in {@link #btnPreviousQus}
     *
     * @param dtBasic  dtBasic code as primary key
     * @param qusIndex Qus  index
     * @return Ques object  of given index
     */

    private DynamicTableQuesDataModel loadPreviousQuestion(final String dtBasic, final int qusIndex) {

        return loadQuestion(dtBasic, qusIndex);
    }


    public DynamicTableQuesDataModel loadQuestion(final String dtBasic, final int qusIndex) {
        mDTQ = sqlH.getSingleDynamicQuestion(dtBasic, qusIndex);
        return mDTQ;
    }

    /**
     * loadDT_QResMode(String) is equivalent to ans view loader
     *
     * @param resMode repose Mode
     */
    private void loadDT_QResMode(String resMode) {

/**
 *  the {@link #mDTQResMode} is needed in the save process in {@link #saveProcessValidation()}
 */
        mDTQResMode = sqlH.getDT_QResMode(resMode);
        String responseControl = mDTQResMode.getDtResponseValueControl();
        String dataType = mDTQResMode.getDtDataType();
        String resLupText = mDTQResMode.getDtQResLupText();
        //    Log.d("Nir", "responseControl :" + responseControl + "\n dataType:" + dataType + " \n resLupText :" + resLupText);
/**
 * Resort Data if Data exits
 */
        DTResponseTableDataModel loadAns = sqlH.getDTResponseTableData(dyIndex.getDtBasicCode(), dyIndex.getcCode(), dyIndex.getDonorCode(), dyIndex.getAwardCode(), dyIndex.getProgramCode(), getStaffID(), mDTQ.getDtQCode(), mDTATableList.get(0).getDt_ACode(), mDTRSeq);

        countChecked = 0;
        if (dataType != null) {
            switch (responseControl) {
                case TEXT_BOX:

                    dt_edt.setVisibility(View.VISIBLE);
                    /**
                     * if data exit show data
                     */
                    if (loadAns != null)
                        dt_edt.setText(loadAns.getDtaValue());
                    else
                        dt_edt.setText("");

                    switch (dataType) {
                        case TEXT:
                            dt_edt.setHint("Text");
                            dt_edt.setInputType(InputType.TYPE_CLASS_TEXT);
                            break;
                        case NUMBER:

                            dt_edt.setHint("Number");
                            if (resLupText.equals("Number (not decimal)"))
                                dt_edt.setInputType(InputType.TYPE_CLASS_NUMBER);
                            else
                                dt_edt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                            break;

                    }// end of switch

                    break;


                case Date_OR_Time:

                    _dt_tv_DatePicker.setVisibility(View.VISIBLE);

                    /**
                     * if data exit show data
                     */
                    if (loadAns != null)
                        _dt_tv_DatePicker.setText(loadAns.getDtaValue());
                    else
                        _dt_tv_DatePicker.setText("Select Date");

                    switch (dataType) {
                        case DATE_TIME:
                            getTimeStamp(_dt_tv_DatePicker);
                            break;
                        case DATE:

                            _dt_tv_DatePicker.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    setDate();
                                }
                            });
                            break;
                    }


                    break;
                case COMBO_BOX:
                    dt_spinner.setVisibility(View.VISIBLE);

                    //hide the key pad when spinner Appears
                    hideSoftKayPad(dt_spinner);
                    /**
                     * if data exist get the Spinner String
                     * set position •
                     */
                    if (loadAns != null)
                        strSpinner = loadAns.getDtaValue();
                    else
                        strSpinner = null;
                    loadDynamicSpinnerList(dyIndex.getcCode(), resLupText);


                    break;
                case CHECK_BOX:

                    parent_layout_onlyFor_CB.setVisibility(View.VISIBLE);

                    //hide the key pad when spinner Appears
                    hideSoftKayPad(dt_spinner);
                    if (mDTATableList.size() > 0)
                        loadDynamicCheckBox(mDTATableList);


                    break;
                case RADIO_BUTTON:
                    radioGroup.setVisibility(View.VISIBLE);

                    //hide the key pad when spinner Appears
                    hideSoftKayPad(dt_spinner);

                    if (mDTATableList.size() > 0)
                        loadRadioButtons(mDTATableList);

                    break;
                case RADIO_BUTTON_N_TEXTBOX:
                    dt_layout_Radio_N_EditText.setVisibility(View.VISIBLE);

                    if (mDTATableList.size() > 0)
                        loadRadioButtonAndEditText(mDTATableList, dataType);

                    break;
                case CHECKBOX_N_TEXTBOX:
                    parent_layout_FOR_CB_N_ET.setVisibility(View.VISIBLE);

                    if (mDTATableList.size() > 0)
                        loadDynamicCheckBoxAndEditText(mDTATableList, dataType);
                    break;
                case PHOTO:
                    loadDynamicPhoto();
                    break;

            }// end of switch
        }// end of if

    }//  end of loadDT_QResMode

    /**
     * Handel the the mPhotoBitmap capture section and save into the db
     */
    private void loadDynamicPhoto() {
        dt_photo.setVisibility(View.VISIBLE);

        //hide the key pad when spinner Appears
        hideSoftKayPad(dt_spinner);
        resetImageView();
        dt_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhotoCaptureDialog(CAMERA_REQUEST_1);
            }
        });

    }

    /**
     * reset image view with icons if {@link #mPhotoBitmap} is null . else
     */
    private void resetImageView() {
        if (mPhotoBitmap == null) {
            dt_photo.setImageDrawable(mContext.getResources().getDrawable(R.drawable.camera_icon));
        } else {
            dt_photo.setImageBitmap(mPhotoBitmap);
        }
    }

    private void showPhotoCaptureDialog(final int requestCode) {


        final CharSequence[] items = getResources().getStringArray(R.array.cameraOption);

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(new ContextThemeWrapper(mContext, android.R.style.Theme_Holo_Light_Dialog));
        builder.setTitle("Photo:");
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case CameraUtils.CAPTURED_IMAGE:
                        CameraUtils camera = new CameraUtils();
                        camera.captureImageAlert(DTResponseRecordingActivity.this, requestCode);

                        break;
                    case CameraUtils.DELETE_IMAGE:
                        // checkPhotoAvailability(CountryCode, GrpCode, subGrpCode, LocationCode, ContentCode);
                        break;
                    case CameraUtils.CANCEL:
                        imageCaptureOptionDialog.dismiss();
                        break;
                }
                imageCaptureOptionDialog.dismiss();
            }
        });
        imageCaptureOptionDialog = builder.create();
        imageCaptureOptionDialog.show();
    }


    /**
     * Here we store the file url as it will be null after returning from camera
     * app. save file url in bundle as it will be null on screen orientation change
     */
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putParcelable("file_uri", fileUri);
//    }
//
//    /**
//     * get the file url
//     *
//     * @param savedInstanceState bundle of image
//     */
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        fileUri = savedInstanceState.getParcelable("file_uri");
//    }

    /**
     * @param requestCode Request Code For Camera
     * @param resultCode  Result Code
     * @param data        data Image stream
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST_1 && resultCode == RESULT_OK && data != null) {
            mPhotoBitmap = (Bitmap) data.getExtras().get("data");

            adjustImageView(mPhotoBitmap);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            if (mPhotoBitmap != null)
                mPhotoBitmap.compress(Bitmap.CompressFormat.PNG, 99, stream);
            byte[] byteArray = stream.toByteArray();
            String base64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
            base64 = base64.trim();
            setImageString(base64);

        }
    }


    /**
     * this method do some adjustment Over Image View With Photo
     *
     * @param bitmap mPhotoBitmap
     */
    private void adjustImageView(Bitmap bitmap) {
        dt_photo.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
        dt_photo.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        dt_photo.setImageBitmap(bitmap);
    }

    /**
     * Shuvo vai
     *
     * @param dtA_Table_Data ans Mode
     */

    private void loadDynamicCheckBox(List<DT_ATableDataModel> dtA_Table_Data) {
        /**
         * If there are any Children in layout Container it will reMove
         * And the list of the Check Box {@link #mCheckBox_List} clear
         *
         */
        if (parent_layout_onlyFor_CB.getChildCount() > 0) {
            mCheckBox_List.clear();
            parent_layout_onlyFor_CB.removeAllViews();
        }


        for (int i = 0; i < dtA_Table_Data.size(); i++) {
            TableRow row = new TableRow(this);
            row.setId(i);
            LinearLayout.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(layoutParams);
            CheckBox checkBox = new CheckBox(this);
            checkBox.setOnCheckedChangeListener(DTResponseRecordingActivity.this);
            checkBox.setId(i);

            ///set Text label
            checkBox.setText(dtA_Table_Data.get(i).getDt_ALabel());

            // set check box is checked or not

            DTResponseTableDataModel loadAns = sqlH.getDTResponseTableData(dyIndex.getDtBasicCode(), dyIndex.getcCode(), dyIndex.getDonorCode(), dyIndex.getAwardCode(), dyIndex.getProgramCode(), getStaffID(), mDTQ.getDtQCode(), dtA_Table_Data.get(i).getDt_ACode(), mDTRSeq);
            if (loadAns != null) {
                if (loadAns.getDtaValue().equals("Y")) {
                    checkBox.setChecked(true);
                }

            }

            row.addView(checkBox);
            /**             * {@link #btnNextQues} needed             */
            mCheckBox_List.add(checkBox);
            parent_layout_onlyFor_CB.addView(row);
        }


    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {


        if (isChecked) {
            //              increase number of Selected Check box
            countChecked++;
        } else {
            //              decrease number of  Selected  Check box
            countChecked--;
        }


    }


    /**
     * Date & time Session
     */
    public void getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        String strDate = mFormat.format(calendar.getTime());
        displayDate(strDate);
    }

    private void displayDate(String strDate) {
        _dt_tv_DatePicker.setText(strDate);
    }

    public void setDate() {
        new DatePickerDialog(mContext, datePickerListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * date Time picker Listener
     * The Date Listener invoke in {@link #setDate()}
     */

    DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDate();
        }
    };

    public void updateDate() {
        displayDate(mFormat.format(calendar.getTime()));
    }


    /**
     * this method load spinner value for different dt basic table dynamicly
     *
     * @param cCode      Country Code
     * @param resLupText res lup
     */
    private void loadDynamicSpinnerList(final String cCode, final String resLupText) {

        SpinnerLoader.loadDynamicSpinnerListLoader(mContext, sqlH, dt_spinner, cCode, resLupText, strSpinner, mDTQ, dyIndex);


        dt_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strSpinner = ((SpinnerHelper) dt_spinner.getSelectedItem()).getValue();
                idSpinner = ((SpinnerHelper) dt_spinner.getSelectedItem()).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void loadRadioButtons(List<DT_ATableDataModel> radioButtonItemName) {

        if (radioGroup.getChildCount() > 0) {
            mRadioButton_List.clear();
            radioGroup.removeAllViews();
        }


        for (int i = 0; i < radioButtonItemName.size(); i++) {
            RadioButton rdbtn = new RadioButton(this);
            rdbtn.setId(i);
            rdbtn.setTextSize(24); // set text size

            rdbtn.setPadding(0, 10, 0, 10);     // set padding

            rdbtn.setText(radioButtonItemName.get(i).getDt_ALabel()); // set label
            // jodi nicher line ta na execute  kori tahole  ager qus er jei redio
            // button ta select kori pore quesion ta korte pari na
            if (i == 0)
                rdbtn.setChecked(true);


            radioGroup.addView(rdbtn);

            mRadioButton_List.add(rdbtn);

        }// end of for loop

    }
    /**
     * Radio - EditText & CheckBox - EditText
     */

    /**
     * @param List_DtATable
     */

    public void loadRadioButtonAndEditText(List<DT_ATableDataModel> List_DtATable, String dataType) {

        if (ll_editText.getChildCount() > 0) {
            mRadioButtonForRadioAndEdit_List.clear();
            mEditTextForRadioAndEdit_List.clear();
            radioGroupForRadioAndEditText.removeAllViews();
            ll_editText.removeAllViews();
        }


        for (int i = 0; i < List_DtATable.size(); i++) {
            String label = List_DtATable.get(i).getDt_ALabel();
            RadioButton rdbtn = new RadioButton(this);

            rdbtn.setId(i);
            rdbtn.setText(label); // set label
            // jodi nicher line ta na execute  kori tahole  ager qus er jei radio button
            // ta select kori pore question ta korte pari na
            if (i == 0)
                rdbtn.setChecked(true);

            rdbtn.setTextSize(24); // set text size
            rdbtn.setPadding(0, 10, 0, 10);     // set padding
            rdbtn.setOnCheckedChangeListener(DTResponseRecordingActivity.this);


            EditText et = new EditText(this);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 5, 0, 5);
            et.setLayoutParams(params);
            et.setHint(label);
            et.setId(i);

            // soft keyboard controller
            if (dataType.equals(NUMBER)) {
                et.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

            }
            et.setBackgroundColor(Color.WHITE);


/**
 *
 * todo aad index after set DTRespose Sequn {@link #saveOnResponseTable(String, DT_ATableDataModel)}
 */
            DTResponseTableDataModel loadAns = sqlH.getDTResponseTableData(dyIndex.getDtBasicCode(), dyIndex.getcCode(), dyIndex.getDonorCode(), dyIndex.getAwardCode(), dyIndex.getProgramCode(), getStaffID(), mDTQ.getDtQCode(), List_DtATable.get(i).getDt_ACode(), mDTRSeq);
            if (loadAns != null) {
                rdbtn.setChecked(true);
                String value = loadAns.getDtaValue();
                et.setText(value);
            }


            radioGroupForRadioAndEditText.addView(rdbtn);
            mRadioButtonForRadioAndEdit_List.add(rdbtn);

            ll_editText.addView(et);
            mEditTextForRadioAndEdit_List.add(et);

        }


    }

    /**
     * If there are any Children in layout Container it will reMove
     * And the list of the Check Box {@link #mEditTextForCheckBoxAndEdit_List}
     * and {@link #mCheckBoxForCheckBoxAndEdit_List }
     * clear
     */

    private void loadDynamicCheckBoxAndEditText(List<DT_ATableDataModel> List_DtATable, String dataType) {

        if (subParent_CB_layout_FOR_CB_N_ET.getChildCount() > 0) {
            subParent_ET_layout_FOR_CB_N_ET.removeAllViews();
            subParent_CB_layout_FOR_CB_N_ET.removeAllViews();
            mCheckBoxForCheckBoxAndEdit_List.clear();
            mEditTextForCheckBoxAndEdit_List.clear();
        }


        for (int i = 0; i < List_DtATable.size(); i++) {

            String label = List_DtATable.get(i).getDt_ALabel();
            TableRow row = new TableRow(this);
            row.setId(i);
            LinearLayout.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(layoutParams);
            CheckBox checkBox = new CheckBox(this);
            checkBox.setOnCheckedChangeListener(DTResponseRecordingActivity.this);
            checkBox.setId(i);
            checkBox.setText(label); //  set Text label
            row.addView(checkBox);

            EditText et = new EditText(this);
            et.setHint(label);
            et.setId(i);

            /**
             * sof keyboard type
             */
            if (dataType.equals(NUMBER)) {
                et.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

            }
            et.setBackgroundColor(Color.WHITE);

            /**
             * This snippets work for Check Box Well  but not for the radio button
             * todo aad index after set DTRespose Sequn {@link #saveOnResponseTable(String, DT_ATableDataModel)}
             */
            DTResponseTableDataModel loadAns = sqlH.getDTResponseTableData(dyIndex.getDtBasicCode(), dyIndex.getcCode(), dyIndex.getDonorCode(), dyIndex.getAwardCode(), dyIndex.getProgramCode(), getStaffID(), mDTQ.getDtQCode(), List_DtATable.get(i).getDt_ACode(), mDTRSeq);
            if (loadAns != null) {
                checkBox.setChecked(true);
                String value = loadAns.getDtaValue();
                et.setText(value);
            }

            subParent_ET_layout_FOR_CB_N_ET.addView(et);
            /**             * {@link #btnNextQues} needed */

            mEditTextForCheckBoxAndEdit_List.add(et);
            subParent_CB_layout_FOR_CB_N_ET.addView(row);
            mCheckBoxForCheckBoxAndEdit_List.add(checkBox);
        }


    }

    /**
     * Shuvo
     * this method only show the System Current Time
     *
     * @param tv Text view For Show
     */

    private void getTimeStamp(TextView tv) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DATE);
        int hourOfDay = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        String am_pm = (hourOfDay < 12) ? "AM" : "PM";
        String timeStamp = year + "/" + month + "/" + day + "  " + hourOfDay + ":" + minute + " " + am_pm;
        tv.setText(timeStamp);
    }

}

