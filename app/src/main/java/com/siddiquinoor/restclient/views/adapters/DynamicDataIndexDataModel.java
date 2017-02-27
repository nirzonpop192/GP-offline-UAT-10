package com.siddiquinoor.restclient.views.adapters;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by USER on 9/26/2016.
 */
public class DynamicDataIndexDataModel implements Parcelable {
    private String dtTittle;
    private String dtBasicCode;
    private String awardCode;
    private String awardName;
    private String programCode;
    private String programName;
    private String prgActivityTitle;
    private String cCode;
    private String opMode;
    private String donorCode;
    private String OpMonthCode;
    private String OpMonthDate;
    private String programActivityCode;
    private String dtShortName;

    public DynamicDataIndexDataModel() {
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public DynamicDataIndexDataModel createFromParcel(Parcel in) {
            return new DynamicDataIndexDataModel(in);
        }

        public DynamicDataIndexDataModel[] newArray(int size) {
            return new DynamicDataIndexDataModel[size];
        }
    };

    public DynamicDataIndexDataModel(Parcel in) {
        readFromParcel(in);
    }


    private void readFromParcel(Parcel in) {
        dtTittle = in.readString();
        dtBasicCode = in.readString();
        awardCode = in.readString();
        awardName = in.readString();
        programCode = in.readString();
        programName = in.readString();
        prgActivityTitle = in.readString();
        cCode = in.readString();
        opMode = in.readString();
        donorCode = in.readString();
        OpMonthCode = in.readString();
        OpMonthDate = in.readString();
        programActivityCode = in.readString();
        dtShortName = in.readString();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dtTittle);
        dest.writeString(dtBasicCode);
        dest.writeString(awardCode);
        dest.writeString(awardName);
        dest.writeString(programCode);
        dest.writeString(programName);
        dest.writeString(prgActivityTitle);
        dest.writeString(cCode);
        dest.writeString(opMode);
        dest.writeString(donorCode);
        dest.writeString(OpMonthCode);
        dest.writeString(OpMonthDate);
        dest.writeString(programActivityCode);
        dest.writeString(dtShortName);
    }

    public String getProgramActivityCode() {
        return programActivityCode;
    }

    public void setProgramActivityCode(String programActivityCode) {
        this.programActivityCode = programActivityCode;
    }

    public String getOpMonthDate() {
        return OpMonthDate;
    }

    public void setOpMonthDate(String opMonthDate) {
        OpMonthDate = opMonthDate;
    }

    public String getOpMonthCode() {
        return OpMonthCode;
    }

    public void setOpMonthCode(String opMonthCode) {
        OpMonthCode = opMonthCode;
    }

    public String getDonorCode() {
        return donorCode;
    }

    public void setDonorCode(String donorCode) {
        this.donorCode = donorCode;
    }

    public String getOpMode() {
        return opMode;
    }

    public void setOpMode(String opMode) {
        this.opMode = opMode;
    }

    public String getcCode() {
        return cCode;
    }

    public void setcCode(String cCode) {
        this.cCode = cCode;
    }

    public String getDtShortName() {
        return dtShortName;
    }

    public void setDtShortName(String dtShortName) {
        this.dtShortName = dtShortName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getDtTittle() {
        return dtTittle;
    }

    public void setDtTittle(String dtTittle) {
        this.dtTittle = dtTittle;
    }

    public String getDtBasicCode() {
        return dtBasicCode;
    }

    public void setDtBasicCode(String dtBasicCode) {
        this.dtBasicCode = dtBasicCode;
    }

    public String getAwardCode() {
        return awardCode;
    }

    public void setAwardCode(String awardCode) {
        this.awardCode = awardCode;
    }

    public String getAwardName() {
        return awardName;
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName;
    }

    public String getProgramCode() {
        return programCode;
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getPrgActivityTitle() {
        return prgActivityTitle;
    }

    public void setPrgActivityTitle(String prgActivityTitle) {
        this.prgActivityTitle = prgActivityTitle;
    }
}
