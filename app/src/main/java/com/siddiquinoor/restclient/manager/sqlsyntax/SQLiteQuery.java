package com.siddiquinoor.restclient.manager.sqlsyntax;

import android.util.Log;

import com.siddiquinoor.restclient.data_model.RegNAssgProgSrv;
import com.siddiquinoor.restclient.manager.SQLiteHandler;
import com.siddiquinoor.restclient.utils.UtilClass;
import com.siddiquinoor.restclient.views.adapters.DynamicDataIndexDataModel;
import com.siddiquinoor.restclient.views.adapters.GraduationGridDataModel;
import com.siddiquinoor.restclient.views.adapters.ServiceDataModel;
import com.siddiquinoor.restclient.activity.sub_activity.gps_sub.PointAttributes;

import static com.siddiquinoor.restclient.activity.sub_activity.dynamic_table.DTResponseRecordingActivity.COMMUNITY_GROUP;
import static com.siddiquinoor.restclient.activity.sub_activity.dynamic_table.DTResponseRecordingActivity.DISTRIBUTION_POINT;
import static com.siddiquinoor.restclient.activity.sub_activity.dynamic_table.DTResponseRecordingActivity.GEO_LAYER_1;
import static com.siddiquinoor.restclient.activity.sub_activity.dynamic_table.DTResponseRecordingActivity.GEO_LAYER_2;
import static com.siddiquinoor.restclient.activity.sub_activity.dynamic_table.DTResponseRecordingActivity.GEO_LAYER_3;
import static com.siddiquinoor.restclient.activity.sub_activity.dynamic_table.DTResponseRecordingActivity.GEO_LAYER_4;
import static com.siddiquinoor.restclient.activity.sub_activity.dynamic_table.DTResponseRecordingActivity.GEO_LAYER_ADDRESS;
import static com.siddiquinoor.restclient.activity.sub_activity.dynamic_table.DTResponseRecordingActivity.LOOKUP_LIST;
import static com.siddiquinoor.restclient.activity.sub_activity.dynamic_table.DTResponseRecordingActivity.ORGANIZATION_LIST;
import static com.siddiquinoor.restclient.activity.sub_activity.dynamic_table.DTResponseRecordingActivity.SERVICE_SITE;
import static com.siddiquinoor.restclient.manager.SQLiteHandler.*;
import static com.siddiquinoor.restclient.manager.SQLiteHandler.DONOR_CODE_COL;
import static com.siddiquinoor.restclient.manager.SQLiteHandler.GRP_LAY_R1_LIST_CODE_COL;
import static com.siddiquinoor.restclient.manager.SQLiteHandler.LAY_R1_LIST_CODE_COL;

import com.siddiquinoor.restclient.activity.MapActivity;

/**
 * @author FAISAL MOHAMMAD on 1/17/2016.
 *         <p>
 *         This class provided  all the local queries
 *         </p>
 */
public class SQLiteQuery {

    private static final String YES = "Y";

    public static String getUpzillaJoinQuery(String countryCode, String districtCode) {
        return " JOIN " + SQLiteHandler.SELECTED_VILLAGE_TABLE +
                " ON " + SQLiteHandler.UPAZILLA_TABLE + "." + COUNTRY_CODE_COL + " = " + SQLiteHandler.SELECTED_VILLAGE_TABLE + "." + COUNTRY_CODE_COL +
                " AND " + SQLiteHandler.UPAZILLA_TABLE + "." + LAY_R1_LIST_CODE_COL + " = " + SQLiteHandler.SELECTED_VILLAGE_TABLE + "." + LAY_R1_LIST_CODE_COL +
                " AND " + SQLiteHandler.UPAZILLA_TABLE + "." + LAY_R2_LIST_CODE_COL + " = " + SQLiteHandler.SELECTED_VILLAGE_TABLE + "." + LAY_R2_LIST_CODE_COL +
                " WHERE " + SQLiteHandler.UPAZILLA_TABLE + "." + COUNTRY_CODE_COL + "='" + countryCode + "' AND "
                + SQLiteHandler.UPAZILLA_TABLE + "." + LAY_R1_LIST_CODE_COL + "='" + districtCode + "' GROUP BY " +
                SQLiteHandler.UPAZILLA_TABLE + "." + LAY_R2_LIST_CODE_COL + ", " + SQLiteHandler.UPAZILLA_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL;
    }

    public static String getUnionJoinQuery(String countryCode, String districtCode, String upzellaCode) {
        return " JOIN " + SQLiteHandler.SELECTED_VILLAGE_TABLE +
                " ON " + UNIT_TABLE + "." + COUNTRY_CODE_COL + " = " + SQLiteHandler.SELECTED_VILLAGE_TABLE + "." + COUNTRY_CODE_COL +
                " AND " + UNIT_TABLE + "." + LAY_R1_LIST_CODE_COL + " = " + SQLiteHandler.SELECTED_VILLAGE_TABLE + "." + LAY_R1_LIST_CODE_COL +
                " AND " + UNIT_TABLE + "." + LAY_R2_LIST_CODE_COL + " = " + SQLiteHandler.SELECTED_VILLAGE_TABLE + "." + LAY_R2_LIST_CODE_COL +
                " AND " + UNIT_TABLE + "." + LAY_R3_LIST_CODE_COL + " = " + SQLiteHandler.SELECTED_VILLAGE_TABLE + "." + LAY_R3_LIST_CODE_COL +
                " WHERE " + UNIT_TABLE + "." + COUNTRY_CODE_COL + "='" + countryCode + "' AND "
                + UNIT_TABLE + "." + LAY_R1_LIST_CODE_COL + "='" + districtCode + "' AND " +
                UNIT_TABLE + "." + LAY_R2_LIST_CODE_COL + "='" + upzellaCode + "' " +
                " GROUP BY " + UNIT_TABLE + "." + LAY_R3_LIST_CODE_COL + ", " + UNIT_TABLE + "." + UNITE_NAME_COL;
    }

    public static String getVillageJoinQuery(String countryCode, String districtCode, String upzellaCode, String unionCode) {
        return " JOIN " + SELECTED_VILLAGE_TABLE +
                " ON " + VILLAGE_TABLE + "." + COUNTRY_CODE_COL + " = " + SELECTED_VILLAGE_TABLE + "." + COUNTRY_CODE_COL +
                " AND " + VILLAGE_TABLE + "." + LAY_R1_LIST_CODE_COL + " = " + SELECTED_VILLAGE_TABLE + "." + LAY_R1_LIST_CODE_COL +
                " AND " + VILLAGE_TABLE + "." + LAY_R2_LIST_CODE_COL + " = " + SELECTED_VILLAGE_TABLE + "." + LAY_R2_LIST_CODE_COL +
                " AND " + VILLAGE_TABLE + "." + LAY_R3_LIST_CODE_COL + " = " + SELECTED_VILLAGE_TABLE + "." + LAY_R3_LIST_CODE_COL +
                " AND " + VILLAGE_TABLE + "." + LAY_R4_LIST_CODE_COL + " = " + SELECTED_VILLAGE_TABLE + "." + LAY_R4_LIST_CODE_COL +

                " WHERE " + VILLAGE_TABLE + "." + COUNTRY_CODE_COL + "='" + countryCode + "' AND "
                + VILLAGE_TABLE + "." + LAY_R1_LIST_CODE_COL + "='" + districtCode + "' AND " +
                VILLAGE_TABLE + "." + LAY_R2_LIST_CODE_COL + "='" + upzellaCode + "' AND " +
                VILLAGE_TABLE + "." + LAY_R3_LIST_CODE_COL + "='" + unionCode + "' " +
                " GROUP BY " + VILLAGE_TABLE + "." + LAY_R4_LIST_CODE_COL + ", " + VILLAGE_TABLE + "." + VILLAGE_NAME_COL;
    }

    public static String getDistrictJoinQuery(String countryCode) {
        return " JOIN " + SQLiteHandler.SELECTED_VILLAGE_TABLE +
                " ON " + SQLiteHandler.DISTRICT_TABLE + "." + COUNTRY_CODE_COL + " = " + SQLiteHandler.SELECTED_VILLAGE_TABLE + "." + COUNTRY_CODE_COL +
                " AND " + SQLiteHandler.DISTRICT_TABLE + "." + LAY_R1_LIST_CODE_COL + " = " + SQLiteHandler.SELECTED_VILLAGE_TABLE + "." + LAY_R1_LIST_CODE_COL +
                " WHERE " + SQLiteHandler.DISTRICT_TABLE + "." + COUNTRY_CODE_COL + "='" + countryCode + "' GROUP BY " + SQLiteHandler.DISTRICT_TABLE + "." + LAY_R1_LIST_CODE_COL + ", " + SQLiteHandler.DISTRICT_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL;

    }


    /**
     * <p>This method return the sql String of Assign </p>
     *
     * @param cCode     Country Code
     * @param dstCode   District Code =Layer1Code
     * @param upCode    Upzella Code=Layer2Code
     * @param unCode    Union Code= Layer3Code
     * @param vCode     Village Code =Layer4Code
     * @param donorCode Donor Code
     * @param awardCode Award Code
     * @param prgCode   program Code
     * @param srvCode   service Code
     * @return sql String
     */

    public static String getSingleMemberForAssign_sql(String cCode, String dstCode, String upCode, String unCode, String vCode, String hhid, String memberId, String donorCode, String awardCode, String prgCode, String srvCode) {
        String getMemName;
        /**
         * 0004= Liberia's Country Code
         */
        if (cCode.equals("0004")) {
            getMemName = REGISTRATION_MEMBER_TABLE + "." + MEM_NAME_FIRST_COL + " || ' ' || " +
                    REGISTRATION_MEMBER_TABLE + "." + MEM_NAME_MIDDLE_COL + " || ' ' || " +
                    REGISTRATION_MEMBER_TABLE + "." + MEM_NAME_LAST_COL;

        } else {
            getMemName = SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.MEM_NAME_COL;

        }

        return "SELECT " + REGISTRATION_MEMBER_TABLE + "." + HHID_COL
                + ", " + REGISTRATION_MEMBER_TABLE + "." + HH_MEM_ID
                + ", " + REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL
                + " || '' || " + REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL
                + " || '' || " + REGISTRATION_MEMBER_TABLE + "." + UNITE_NAME_COL
                + " || '' || " + REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL
                + " || '' || " + REGISTRATION_MEMBER_TABLE + "." + HHID_COL
                + " || '' || " + REGISTRATION_MEMBER_TABLE + "." + HH_MEM_ID + " AS newId "

                + ", " + getMemName + " As memName "
                + ", " + REGISTRATION_MEMBER_TABLE + "." + MEM_AGE
                + ", " + REGISTRATION_MEMBER_TABLE + "." + SEX_COL
                + ", " + RELATION_TABLE + "." + RELATION_NAME + " AS HHRelation, " +
                " CASE WHEN LENGTH ( " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + HH_MEM_ID + ") > 0 " +
                " THEN 'Y' ELSE 'N' END  AS Assign "
                + ", " + REGISTRATION_MEMBER_TABLE + "." + COUNTRY_CODE_COL
                + ", " + REGISTRATION_MEMBER_TABLE + "." + DISTRICT_NAME_COL
                + ", " + REGISTRATION_MEMBER_TABLE + "." + UPZILLA_NAME_COL
                + ", " + REGISTRATION_MEMBER_TABLE + "." + UNITE_NAME_COL
                + ", " + REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL
                + ", " + REGISTRATION_TABLE + "." + SQLiteHandler.PNAME_COL
                + ", " + SQLiteHandler.SERVICE_MASTER_TABLE + "." + SQLiteHandler.SERVICE_NAME_COL + "  AS AssignCriteria "

                + " , " + " regMemGrp." + GROUP_CODE_COL + " AS grpCode "
                + " , " + " grp." + GROUP_NAME_COL + " AS grpName "
                + " , regMemGrp." + ACTIVE_COL + " AS activeCode "


                + " , grpCat." + GROUP_CAT_CODE_COL + " AS catCode "
                + " , grpCat." + GROUP_CAT_NAME_COL + " AS catName "

                //", " +
               /* " ( SELECT " + SQLiteHandler.SERVICE_NAME_COL + " FROM " + SQLiteHandler.SERVICE_MASTER_TABLE + " WHERE " + SQLiteHandler.PROGRAM_CODE_COL + " = '" + program + "' AND " +
                SQLiteHandler.SERVICE_CODE_COL + " = '" + service + "' GROUP BY " + SQLiteHandler.SERVICE_CODE_COL + " ) AS AssignCriteria " + // EXTRA FOR CRITERIA
*/

                + "FROM " + REGISTRATION_TABLE +
                " JOIN " + SQLiteHandler.REGISTRATION_MEMBER_TABLE +
                " ON " + REGISTRATION_TABLE + "." + COUNTRY_CODE_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + COUNTRY_CODE_COL +
                " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL +
                " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL +
                " AND " + REGISTRATION_TABLE + "." + UNITE_NAME_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + UNITE_NAME_COL +
                " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL +
                " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.PID_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HHID_COL + " " +
                " LEFT JOIN " + SQLiteHandler.RELATION_TABLE + " ON " + SQLiteHandler.RELATION_TABLE + "." + SQLiteHandler.RELATION_CODE + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.RELATION_COL +
                " JOIN " + SQLiteHandler.SERVICE_MASTER_TABLE + " ON " + SQLiteHandler.SERVICE_MASTER_TABLE + "." + PROGRAM_CODE_COL + " = '" + prgCode + "' " +
                " AND " + SQLiteHandler.SERVICE_MASTER_TABLE + "." + SERVICE_CODE_COL + " = '" + srvCode + "' " +
                " LEFT JOIN " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + " ON " +
                SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + COUNTRY_CODE_COL + " = " + REGISTRATION_TABLE + "." + COUNTRY_CODE_COL +
                " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R1_LIST_CODE_COL + " = " + REGISTRATION_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL +
                " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R2_LIST_CODE_COL + " = " + REGISTRATION_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL +
                " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R3_LIST_CODE_COL + " = " + REGISTRATION_TABLE + "." + UNITE_NAME_COL +
                " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R4_LIST_CODE_COL + " = " + REGISTRATION_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL +

                " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + HHID_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HHID_COL +
                " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + HH_MEM_ID + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HH_MEM_ID +

                " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + DONOR_CODE_COL + " = '" + donorCode + "' " +
                " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + AWARD_CODE_COL + " = '" + awardCode + "' " +
                " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + PROGRAM_CODE_COL + " = '" + prgCode + "' " +
                " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + SERVICE_CODE_COL + " = '" + srvCode + "' "
                + " LEFT JOIN " + SQLiteHandler.REG_N_MEM_PROG_GRP_TABLE + " AS regMemGrp ON "

                + " regMemGrp." + COUNTRY_CODE_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + COUNTRY_CODE_COL
                + " AND  regMemGrp." + LAY_R1_LIST_CODE_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL
                + " AND  regMemGrp." + LAY_R2_LIST_CODE_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL
                + " AND  regMemGrp." + LAY_R3_LIST_CODE_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + UNITE_NAME_COL
                + " AND  regMemGrp." + LAY_R4_LIST_CODE_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL
                + " AND  regMemGrp." + DONOR_CODE_COL + " = '" + donorCode + "'"
                + " AND  regMemGrp." + AWARD_CODE_COL + " = '" + awardCode + "'"
                + " AND  regMemGrp." + HHID_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HHID_COL
                + " AND  regMemGrp." + HH_MEM_ID + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HH_MEM_ID
                + " AND  regMemGrp." + PROGRAM_CODE_COL + " = '" + prgCode + "'"
                + " AND  regMemGrp." + SERVICE_CODE_COL + " = '" + srvCode + "'"


                + " LEFT JOIN " + COMMUNITY_GROUP_TABLE + " AS grp "
                + " ON "
                + " grp." + COUNTRY_CODE_COL + " = '" + cCode + "'"
                + " AND grp." + DONOR_CODE_COL + " = '" + donorCode + "'"
                + " AND grp." + AWARD_CODE_COL + " = '" + awardCode + "'"
                + " AND grp." + PROGRAM_CODE_COL + " = '" + prgCode + "'"
                + " AND grp. " + GROUP_CODE_COL + " = " + " regMemGrp." + GROUP_CODE_COL

                + " LEFT JOIN " + COMMUNITY_GROUP_CATEGORY_TABLE + " AS grpCat "
                + " ON "
                + " grpCat." + COUNTRY_CODE_COL + " = '" + cCode + "'"
                + " AND grpCat." + DONOR_CODE_COL + " = '" + donorCode + "'"
                + " AND grpCat." + AWARD_CODE_COL + " = '" + awardCode + "'"
                + " AND grpCat." + PROGRAM_CODE_COL + " = '" + prgCode + "'"
                + " AND grpCat." + GROUP_CAT_CODE_COL + " = " + " grp." + GROUP_CAT_CODE_COL //+" regMemGrp." + SQLiteHandler.GROUP_CAT_CODE_COL
                + " WHERE " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + COUNTRY_CODE_COL + " =  '" + cCode + "'" +
                " AND " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL + " =  '" + dstCode + "'" +
                " AND " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL + " =  '" + upCode + "' "
                + " AND " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + UNITE_NAME_COL + " =  '" + unCode + "' "
                + " AND " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL + " =  '" + vCode + "' "
                + " AND " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HHID_COL + " =  '" + hhid + "' "
                + " AND " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HH_MEM_ID + " =  '" + memberId + "' "
                + " GROUP BY newId ";


        //return sql;




/*
        return CREATE_TABLE_IF_NOT_EXISTS + SQLiteHandler.COMMUNITY_GROUP_CATEGORY_TABLE + " ("


                + " grp. " + SQLiteHandler.COUNTRY_CODE_COL + " = "

                + " grp. " + SQLiteHandler.DONOR_CODE_COL + " = "
                + " grp. " + SQLiteHandler.AWARD_CODE_COL + " = "
                + " grp. " + SQLiteHandler.PROGRAM_CODE_COL + " = "
                + " grpCat." + SQLiteHandler.GROUP_CAT_CODE_COL + " = "
                + " grpCat." + SQLiteHandler.GROUP_CAT_NAME_COL + "  "
                + " , " + SQLiteHandler.GROUP_CAT_SHORT_NAME_COL + "  "


        return CREATE_TABLE_IF_NOT_EXISTS + SQLiteHandler.COMMUNITY_GROUP_TABLE + " ("


                + " grp." + SQLiteHandler.COUNTRY_CODE_COL + " = "
                + " grp." + SQLiteHandler.DONOR_CODE_COL + " = "
                + " grp." + SQLiteHandler.AWARD_CODE_COL + " = "
                + " grp." + SQLiteHandler.PROGRAM_CODE_COL + " = "
                + " grp. " + SQLiteHandler.GROUP_CODE_COL + " = "
                + " grp. " + SQLiteHandler.GROUP_NAME_COL + " VARCHAR(100) "
                + " grp. " + SQLiteHandler.GROUP_CAT_CODE_COL + " VARCHAR(4) "
                + " , " + SQLiteHandler.LAY_R1_LIST_CODE_COL + " VARCHAR(4) "
                + " , " + SQLiteHandler.LAY_R2_LIST_CODE_COL + " VARCHAR(4) "
                + " , " + SQLiteHandler.SERVICE_CENTER_CODE_COL + " VARCHAR(4) "
                + " )";*/


        /** very good thinking
         * User can search by House hold & member Id also*/
        /*        SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.COUNTRY_CODE_COL + " || \"\" || "
                + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL + " || \"\" || "
                + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL + " || \"\" || "
                + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.UNITE_NAME_COL + " || \"\" || "
                + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL + " || \"\" || "
                + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.HHID_COL + " || \"\" || "
                + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.HH_MEM_ID + " LIKE '%" + memberSearchId + "%' " +
                // +" GROUP BY " + REGISTRATION_MEMBER_TABLE + "." + HHID_COL;*//*+
                " ORDER BY " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.HHID_COL
                + " DESC "*;*/


    }

    /**
     * <p>This method return the sql String of Assign </p>
     *
     * @param cCode          Country Code
     * @param dstCode        District Code =Layer1Code
     * @param upCode         Upzella Code=Layer2Code
     * @param unCode         Union Code= Layer3Code
     * @param vCode          Village Code =Layer4Code
     * @param donorCode      Donor Code
     * @param awardCode      Award Code
     * @param prgCode        program Code
     * @param srvCode        service Code
     * @param memberSearchId search by member Id
     * @return sql String
     */

    public static String getAssignListViewSelectQuery(String cCode, String dstCode, String upCode, String unCode, String vCode, String donorCode, String awardCode, String prgCode, String srvCode, String memberSearchId) {
        String getMemName;
        /**
         * 0004= Liberia's Country Code
         */
        if (cCode.equals("0004")) {
            getMemName = SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.MEM_NAME_FIRST_COL + " || ' ' || " +
                    SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.MEM_NAME_MIDDLE_COL + " || ' ' || " +
                    SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.MEM_NAME_LAST_COL;

        } else {
            getMemName = SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.MEM_NAME_COL;

        }

        return "SELECT " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HHID_COL
                + ", " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HH_MEM_ID
                + ", " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL
                + " || '' || " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL
                + " || '' || " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + UNITE_NAME_COL
                + " || '' || " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL
                + " || '' || " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HHID_COL
                + " || '' || " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HH_MEM_ID + " AS newId "

                + ", " + getMemName + " As memName "
                + ", " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.MEM_AGE
                + ", " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.SEX_COL
                + ", " + SQLiteHandler.RELATION_TABLE + "." + SQLiteHandler.RELATION_NAME + " AS HHRelation, " +
                " CASE WHEN LENGTH ( " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + HH_MEM_ID + ") > 0 " +
                " THEN 'Y' ELSE 'N' END  AS Assign "
                + ", " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + COUNTRY_CODE_COL
                + ", " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL
                + ", " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL
                + ", " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + UNITE_NAME_COL
                + ", " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL
                + ", " + REGISTRATION_TABLE + "." + SQLiteHandler.PNAME_COL
                + ", " +

                SQLiteHandler.SERVICE_MASTER_TABLE + "." + SQLiteHandler.SERVICE_NAME_COL + "  AS AssignCriteria " +
                //", " +
               /* " ( SELECT " + SQLiteHandler.SERVICE_NAME_COL + " FROM " + SQLiteHandler.SERVICE_MASTER_TABLE + " WHERE " + SQLiteHandler.PROGRAM_CODE_COL + " = '" + program + "' AND " +
                SQLiteHandler.SERVICE_CODE_COL + " = '" + service + "' GROUP BY " + SQLiteHandler.SERVICE_CODE_COL + " ) AS AssignCriteria " + // EXTRA FOR CRITERIA
*/

                "FROM " + REGISTRATION_TABLE +
                " JOIN " + SQLiteHandler.REGISTRATION_MEMBER_TABLE +
                " ON " + REGISTRATION_TABLE + "." + COUNTRY_CODE_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + COUNTRY_CODE_COL +
                " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL +
                " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL +
                " AND " + REGISTRATION_TABLE + "." + UNITE_NAME_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + UNITE_NAME_COL +
                " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL +
                " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.PID_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HHID_COL + " " +
                " LEFT JOIN " + SQLiteHandler.RELATION_TABLE + " ON " + SQLiteHandler.RELATION_TABLE + "." + SQLiteHandler.RELATION_CODE + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.RELATION_COL +
                " JOIN " + SQLiteHandler.SERVICE_MASTER_TABLE + " ON " + SQLiteHandler.SERVICE_MASTER_TABLE + "." + PROGRAM_CODE_COL + " = '" + prgCode + "' " +
                " AND " + SQLiteHandler.SERVICE_MASTER_TABLE + "." + SERVICE_CODE_COL + " = '" + srvCode + "' " +
                " LEFT JOIN " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + " ON " +
                SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + COUNTRY_CODE_COL + " = " + REGISTRATION_TABLE + "." + COUNTRY_CODE_COL +
                " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R1_LIST_CODE_COL + " = " + REGISTRATION_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL +
                " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R2_LIST_CODE_COL + " = " + REGISTRATION_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL +
                " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R3_LIST_CODE_COL + " = " + REGISTRATION_TABLE + "." + UNITE_NAME_COL +
                " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R4_LIST_CODE_COL + " = " + REGISTRATION_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL +

                " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + HHID_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HHID_COL +
                " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + HH_MEM_ID + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HH_MEM_ID +

                " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + DONOR_CODE_COL + " = '" + donorCode + "' " +
                " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + AWARD_CODE_COL + " = '" + awardCode + "' " +
                " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + PROGRAM_CODE_COL + " = '" + prgCode + "' " +
                " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + SERVICE_CODE_COL + " = '" + srvCode + "' " +


                " WHERE " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + COUNTRY_CODE_COL + " =  '" + cCode + "'" +
                " AND " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL + " =  '" + dstCode + "'" +
                " AND " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL + " =  '" + upCode + "' "
                + " AND " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + UNITE_NAME_COL + " =  '" + unCode + "' "
                + " AND " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL + " =  '" + vCode + "' "
                + " AND " +
                /** very good thinking
                 * User can search by House hold & member Id also*/
                SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + COUNTRY_CODE_COL + " || \"\" || "
                + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL + " || \"\" || "
                + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL + " || \"\" || "
                + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + UNITE_NAME_COL + " || \"\" || "
                + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL + " || \"\" || "
                + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HHID_COL + " || \"\" || "
                + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HH_MEM_ID + " LIKE '%" + memberSearchId + "%' " +
                // +" GROUP BY " + REGISTRATION_MEMBER_TABLE + "." + HHID_COL;/*+
                " ORDER BY " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HHID_COL
                + " DESC ";


    }

    /**
     * @param cCode   Country Code
     * @param dstCode District Code =Layer1Code
     * @param upCode  Upzella Code=Layer2Code
     * @param unCode  Union Code= Layer3Code
     * @param vCode   Village Code =Layer4Code
     * @return sql String
     */

    public static String getMemberListView_searchBy_ID_sql(final String cCode, final String dstCode, final String upCode, final String unCode, final String vCode, final String memberSearchId) {
        String getMemName;
        /**
         * 0004= Liberia's Country Code
         */
        if (cCode.equals("0004")) {
            getMemName = SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.MEM_NAME_FIRST_COL + " || ' ' || " +
                    SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.MEM_NAME_MIDDLE_COL + " || ' ' || " +
                    SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.MEM_NAME_LAST_COL;

        } else {
            getMemName = SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.MEM_NAME_COL;

        }

        return "SELECT " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HHID_COL
                + ", " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HH_MEM_ID
                + ", " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL
                + " || '' || " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL
                + " || '' || " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + UNITE_NAME_COL
                + " || '' || " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL
                + " || '' || " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HHID_COL
                + " || '' || " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HH_MEM_ID + " AS newId "

                + ", " + getMemName + " As memName "
                + ", " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.MEM_AGE
                + ", " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.SEX_COL
                + ", " + SQLiteHandler.RELATION_TABLE + "." + SQLiteHandler.RELATION_NAME + " AS HHRelation "

                + ", " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + COUNTRY_CODE_COL
                + ", " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL
                + ", " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL
                + ", " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + UNITE_NAME_COL
                + ", " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL
                + ", " + REGISTRATION_TABLE + "." + SQLiteHandler.PNAME_COL
                + " , " + SQLiteHandler.VILLAGE_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL + " AS layR4Name "
                + " , " + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE + "." + SQLiteHandler.REGN_ADDRESS_LOOKUP_NAME_COL + " As address "

//
                + " FROM " + REGISTRATION_TABLE
                + " JOIN " + SQLiteHandler.REGISTRATION_MEMBER_TABLE
                + " ON " + REGISTRATION_TABLE + "." + COUNTRY_CODE_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + COUNTRY_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL
                + " AND " + REGISTRATION_TABLE + "." + UNITE_NAME_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + UNITE_NAME_COL
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.PID_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HHID_COL + " "
                + " LEFT JOIN " + SQLiteHandler.RELATION_TABLE
                + " ON " + SQLiteHandler.RELATION_TABLE + "." + SQLiteHandler.RELATION_CODE + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.RELATION_COL
                + " left JOIN " + SQLiteHandler.VILLAGE_TABLE + " ON "

                + REGISTRATION_TABLE + "." + COUNTRY_CODE_COL + " = " + SQLiteHandler.VILLAGE_TABLE + "." + COUNTRY_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL + " = " + SQLiteHandler.VILLAGE_TABLE + "." + LAY_R1_LIST_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL + " = " + SQLiteHandler.VILLAGE_TABLE + "." + LAY_R2_LIST_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + UNITE_NAME_COL + " = " + SQLiteHandler.VILLAGE_TABLE + "." + LAY_R3_LIST_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL + " = " + SQLiteHandler.VILLAGE_TABLE + "." + LAY_R4_LIST_CODE_COL

                + " LEFT JOIN " + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE
                + " ON " + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE + "." + COUNTRY_CODE_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + COUNTRY_CODE_COL

                + " AND " + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE + "." + LAY_R1_LIST_CODE_COL + " = " + REGISTRATION_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL
                + " AND  " + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE + "." + LAY_R2_LIST_CODE_COL + " = " + REGISTRATION_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL
                + " AND  " + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE + "." + LAY_R3_LIST_CODE_COL + " = " + REGISTRATION_TABLE + "." + UNITE_NAME_COL
                + " AND  " + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE + "." + LAY_R4_LIST_CODE_COL + " = " + REGISTRATION_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL

                + " AND " + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE + "." + SQLiteHandler.REGN_ADDRESS_LOOKUP_CODE_COL + " = " + REGISTRATION_TABLE + "." + SQLiteHandler.REGN_ADDRESS_LOOKUP_CODE_COL


                + " WHERE " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + COUNTRY_CODE_COL + " =  '" + cCode + "'"
                + " AND " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL + " =  '" + dstCode + "'"
                + " AND " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL + " =  '" + upCode + "' "
                + " AND " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + UNITE_NAME_COL + " =  '" + unCode + "' "
                + " AND " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL + " =  '" + vCode + "' "
                + " AND "
                /** very good thinking
                 * User can search by House hold & member Id also*/
                + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL + " || \"\" || "
                + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL + " || \"\" || "
                + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + UNITE_NAME_COL + " || \"\" || "
                + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL + " || \"\" || "
                + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HHID_COL + " || \"\" || "
                + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HH_MEM_ID + " LIKE '%" + memberSearchId + "%' "

                // group by
                + " GROUP BY " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL + " , "
                + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL + " , "
                + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + UNITE_NAME_COL + " , "
                + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL + " , "
                + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HHID_COL + " , "
                + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HH_MEM_ID + " "


                + " ORDER BY " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HHID_COL + " DESC "
                ;


    }


    /**
     * @param cCode   Country Code
     * @param dstCode District Code =Layer1Code
     * @param upCode  Upzella Code=Layer2Code
     * @param unCode  Union Code= Layer3Code
     * @param vCode   Village Code =Layer4Code
     * @return sql String
     */

    public static String getMemberListView_searchBy_Name_sql(final String cCode, final String dstCode, final String upCode, final String unCode, final String vCode, final String memberSearchName) {
        String getMemName;
        /**
         * 0004= Liberia's Country Code
         */
        if (cCode.equals("0004")) {
            getMemName = SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.MEM_NAME_FIRST_COL + " || ' ' || " +
                    SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.MEM_NAME_MIDDLE_COL + " || ' ' || " +
                    SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.MEM_NAME_LAST_COL;

        } else {
            getMemName = SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.MEM_NAME_COL;

        }

        return "SELECT " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HHID_COL
                + " , " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HH_MEM_ID
                + " , " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL
                + " || '' || " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL
                + " || '' || " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + UNITE_NAME_COL
                + " || '' || " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL
                + " || '' || " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HHID_COL
                + " || '' || " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HH_MEM_ID + " AS newId "

                + " , " + getMemName + " As memName "
                + " , " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.MEM_AGE
                + " , " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.SEX_COL
                + " , " + SQLiteHandler.RELATION_TABLE + "." + SQLiteHandler.RELATION_NAME + " AS HHRelation "

                + " , " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + COUNTRY_CODE_COL
                + " , " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL
                + " , " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL
                + " , " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + UNITE_NAME_COL
                + " , " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL
                + " , " + REGISTRATION_TABLE + "." + SQLiteHandler.PNAME_COL
                + " , " + SQLiteHandler.VILLAGE_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL + " AS layR4Name "
                + " , " + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE + "." + SQLiteHandler.REGN_ADDRESS_LOOKUP_NAME_COL + " As address "

//
                + " FROM " + REGISTRATION_TABLE
                + " JOIN " + SQLiteHandler.REGISTRATION_MEMBER_TABLE
                + " ON " + REGISTRATION_TABLE + "." + COUNTRY_CODE_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + COUNTRY_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL
                + " AND " + REGISTRATION_TABLE + "." + UNITE_NAME_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + UNITE_NAME_COL
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.PID_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HHID_COL + " "
                + " LEFT JOIN " + SQLiteHandler.RELATION_TABLE
                + " ON " + SQLiteHandler.RELATION_TABLE + "." + SQLiteHandler.RELATION_CODE + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.RELATION_COL
                + " left JOIN " + SQLiteHandler.VILLAGE_TABLE + " ON "

                + REGISTRATION_TABLE + "." + COUNTRY_CODE_COL + " = " + SQLiteHandler.VILLAGE_TABLE + "." + COUNTRY_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL + " = " + SQLiteHandler.VILLAGE_TABLE + "." + LAY_R1_LIST_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL + " = " + SQLiteHandler.VILLAGE_TABLE + "." + LAY_R2_LIST_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + UNITE_NAME_COL + " = " + SQLiteHandler.VILLAGE_TABLE + "." + LAY_R3_LIST_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL + " = " + SQLiteHandler.VILLAGE_TABLE + "." + LAY_R4_LIST_CODE_COL

                + " LEFT JOIN " + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE
                + " ON " + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE + "." + COUNTRY_CODE_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + COUNTRY_CODE_COL

                + " AND " + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE + "." + LAY_R1_LIST_CODE_COL + " = " + REGISTRATION_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL
                + " AND  " + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE + "." + LAY_R2_LIST_CODE_COL + " = " + REGISTRATION_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL
                + " AND  " + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE + "." + LAY_R3_LIST_CODE_COL + " = " + REGISTRATION_TABLE + "." + UNITE_NAME_COL
                + " AND  " + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE + "." + LAY_R4_LIST_CODE_COL + " = " + REGISTRATION_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL

                + " AND " + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE + "." + SQLiteHandler.REGN_ADDRESS_LOOKUP_CODE_COL + " = " + REGISTRATION_TABLE + "." + SQLiteHandler.REGN_ADDRESS_LOOKUP_CODE_COL


                + " WHERE " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + COUNTRY_CODE_COL + " =  '" + cCode + "'"
                + " AND " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL + " =  '" + dstCode + "'"
                + " AND " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL + " =  '" + upCode + "' "
                + " AND " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + UNITE_NAME_COL + " =  '" + unCode + "' "
                + " AND " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL + " =  '" + vCode + "' "
                + " AND "
                /** very good thinking
                 * User can search by House hold & member Name also*/
                + getMemName + " LIKE '%" + memberSearchName + "%' "

                + " ORDER BY " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HHID_COL
                + " DESC "
                ;


    }


    public static String getMemberGraduationStatusList_sql(String cCode, String donorCode, String awardCode, String programCode, String srvCode, String memberId) {
        String getMemberName;
        if (cCode.equals("0004")) {
            getMemberName = SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.MEM_NAME_FIRST_COL +
                    "|| ' ' || " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.MEM_NAME_MIDDLE_COL +
                    "|| ' ' || " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.MEM_NAME_LAST_COL;

        } else
            getMemberName = SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.MEM_NAME_COL;


        return "SELECT " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + HHID_COL +
                " , " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + HH_MEM_ID +

                " , " + getMemberName + " AS memName " +
                " , " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + COUNTRY_CODE_COL +
                " , " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R1_LIST_CODE_COL +
                " , " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R2_LIST_CODE_COL +
                " , " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R3_LIST_CODE_COL +
                " , " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R4_LIST_CODE_COL +
                " , " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + PROGRAM_CODE_COL +
                " , " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + SERVICE_CODE_COL +
                " , " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + SQLiteHandler.GRD_DATE_COL +
                " , " + SQLiteHandler.VILLAGE_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL +
                " , ( SELECT " + SQLiteHandler.GRD_TITLE_COL + " FROM " + SQLiteHandler.REG_N_LUP_GRADUATION_TABLE +
                " WHERE " + PROGRAM_CODE_COL + " = '" + programCode + "'"
                + " AND " + SERVICE_CODE_COL + " = '" + srvCode + "'"
                + " AND " + SQLiteHandler.GRD_CODE_COL + " = " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + SQLiteHandler.GRD_CODE_COL
                + " ) AS GRDTitle "
                + " , " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R1_LIST_CODE_COL
                + " || '' || " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R2_LIST_CODE_COL
                + " || '' || " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R3_LIST_CODE_COL
                + " || '' || " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R4_LIST_CODE_COL
                + " || '' || " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + HHID_COL
                + " || '' || " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + HH_MEM_ID + " AS nMemId "
//                "
                + " FROM " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE
                + " INNER JOIN " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + " ON "
                + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + COUNTRY_CODE_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + COUNTRY_CODE_COL
                + " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R1_LIST_CODE_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL
                + " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R2_LIST_CODE_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL
                + " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R3_LIST_CODE_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + UNITE_NAME_COL
                + " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R4_LIST_CODE_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL
                + " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + HHID_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HHID_COL
                + " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + HH_MEM_ID + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HH_MEM_ID
                + " INNER JOIN " + SQLiteHandler.VILLAGE_TABLE + " ON "
                + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + COUNTRY_CODE_COL + " = " + SQLiteHandler.VILLAGE_TABLE + "." + COUNTRY_CODE_COL
                + " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + COUNTRY_CODE_COL + " = " + SQLiteHandler.VILLAGE_TABLE + "." + COUNTRY_CODE_COL
                + " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R1_LIST_CODE_COL + " = " + SQLiteHandler.VILLAGE_TABLE + "." + LAY_R1_LIST_CODE_COL
                + " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R2_LIST_CODE_COL + " = " + SQLiteHandler.VILLAGE_TABLE + "." + LAY_R2_LIST_CODE_COL
                + " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R3_LIST_CODE_COL + " = " + SQLiteHandler.VILLAGE_TABLE + "." + LAY_R3_LIST_CODE_COL
                + " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R4_LIST_CODE_COL + " = " + SQLiteHandler.VILLAGE_TABLE + "." + LAY_R4_LIST_CODE_COL
                + " WHERE " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + COUNTRY_CODE_COL + " = '" + cCode + "'"
                + " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + PROGRAM_CODE_COL + " = '" + programCode + "'"
                + " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + SERVICE_CODE_COL + " = '" + srvCode + "'"
                + " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + DONOR_CODE_COL + " = '" + donorCode + "'"
                + " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + AWARD_CODE_COL + " = '" + awardCode + "'"
                + " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R1_LIST_CODE_COL
                + " || '' || " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R2_LIST_CODE_COL
                + " || '' || " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R3_LIST_CODE_COL
                + " || '' || " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R4_LIST_CODE_COL
                + " || '' || " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + HHID_COL
                + " || '' || " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + HH_MEM_ID + " LIKE  '%" + memberId + "%' "
                + " GROUP BY " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.MEM_NAME_COL
                + " ORDER BY  " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.MEM_NAME_COL;
    }

    public static String getSingleLiberiaMemberDataQuery(String country, String district, String upazilla, String union, String village, String household, String member) {

        return "SELECT  "
                + SQLiteHandler.MEM_NAME_COL
                + " , " + SQLiteHandler.ENTRY_BY
                + " , " + SQLiteHandler.ENTRY_DATE

                //  + REG_DATE_COL + " , " if it need latter
                + " , " + SQLiteHandler.BIRTH_YEAR_COL
                + " , " + SQLiteHandler.MARITAL_STATUS_COL
                + " , " + SQLiteHandler.CONTACT_NO_COL
                + " , " + SQLiteHandler.MEMBER_OTHER_ID_COL
                + " , " + SQLiteHandler.MEM_NAME_FIRST_COL
                + " , " + SQLiteHandler.MEM_NAME_MIDDLE_COL
                + " , " + SQLiteHandler.MEM_NAME_LAST_COL
                + " , " + SQLiteHandler.PHOTO_COL
                + " , " + SQLiteHandler.TYPE_ID_COL
                + " , " + SQLiteHandler.ID_NO_COL
                + " , " + SQLiteHandler.V_BSC_MEM_1_NAME_FIRST_COL
                + " , " + SQLiteHandler.V_BSC_MEM_1_NAME_MIDDLE_COL
                + " , " + SQLiteHandler.V_BSC_MEM_1_NAME_LAST_COL
                + " , " + SQLiteHandler.V_BSC_MEM_1_TITLE_COL
                + " , " + SQLiteHandler.V_BSC_MEM_2_NAME_FIRST_COL
                + " , " + SQLiteHandler.V_BSC_MEM_2_NAME_MIDDLE_COL
                + " , " + SQLiteHandler.V_BSC_MEM_2_NAME_LAST_COL
                + " , " + SQLiteHandler.V_BSC_MEM_2_TITLE_COL
                + " , " + SQLiteHandler.PROXY_DESIGNATION_COL
                + " , " + SQLiteHandler.PROXY_NAME_FIRST_COL
                + " , " + SQLiteHandler.PROXY_NAME_MIDDLE_COL
                + " , " + SQLiteHandler.PROXY_NAME_LAST_COL
                + " , " + SQLiteHandler.PROXY_BIRTH_YEAR_COL
                + " , " + SQLiteHandler.PROXY_PHOTO_COL
                + " , " + SQLiteHandler.PROXY_TYPE_ID_COL
                + " , " + SQLiteHandler.PROXY_ID_NO_COL
                + " , " + SQLiteHandler.PROXY_BSC_MEM_1_NAME_FIRST_COL
                + " , " + SQLiteHandler.PROXY_BSC_MEM_1_NAME_MIDDLE_COL
                + " , " + SQLiteHandler.PROXY_BSC_MEM_1_NAME_LAST_COL
                + " , " + SQLiteHandler.PROXY_BSC_MEM_1_TITLE_COL
                + " , " + SQLiteHandler.PROXY_BSC_MEM_2_NAME_FIRST_COL
                + " , " + SQLiteHandler.PROXY_BSC_MEM_2_NAME_MIDDLE_COL
                + " , " + SQLiteHandler.PROXY_BSC_MEM_2_NAME_LAST_COL
                + " , " + SQLiteHandler.PROXY_BSC_MEM_2_TITLE_COL
                +

                "   FROM "
                + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "  " +
                " WHERE " + COUNTRY_CODE + "='" + country + "' " +
                " AND " + SQLiteHandler.DISTRICT_NAME_COL + "='" + district + "' " +
                " AND " + SQLiteHandler.UPZILLA_NAME_COL + "='" + upazilla + "' " +
                " AND " + UNITE_NAME_COL + "='" + union + "' " +
                " AND " + SQLiteHandler.VILLAGE_NAME_COL + "='" + village + "' " +
                " AND " + HHID_COL + "='" + household + "' " +
                " AND " + HH_MEM_ID + "='" + member + "' ";
    }

    public static String getDeletedHouseHoldIDQuery(String personId) {
        return "SELECT "
                + COUNTRY_CODE_COL + " , "
                + SQLiteHandler.DISTRICT_NAME_COL + " , "
                + SQLiteHandler.UPZILLA_NAME_COL + " , "
                + UNITE_NAME_COL + " , "
                + SQLiteHandler.VILLAGE_NAME_COL + " , "
                + SQLiteHandler.PID_COL + "  "
                + " FROM " + REGISTRATION_TABLE
                + " WHERE " + SQLiteHandler.PID_COL + " = '" + personId + "' ";
    }

    public static String getDeletedMemberIDQuery(int memberId) {
        return "SELECT "
                + COUNTRY_CODE_COL + " , "
                + SQLiteHandler.DISTRICT_NAME_COL + " , "
                + SQLiteHandler.UPZILLA_NAME_COL + " , "
                + UNITE_NAME_COL + " , "
                + SQLiteHandler.VILLAGE_NAME_COL + " , "
                + HHID_COL + " , "
                + HH_MEM_ID + "  "
                + " FROM " + SQLiteHandler.REGISTRATION_MEMBER_TABLE
                + " WHERE " + SQLiteHandler.ID_COL + " = '" + memberId + "' ";
    }

    public static String getSavePermissionSelectQuery(String tableName, String columnName, String country, String district, String upzella, String unit, String village) {
        return " SELECT " + columnName +
                // " SELECT "+SQLiteHandler.BTN_SAVE_COL+
                //" FROM "+SQLiteHandler.STAFF_GEO_INFO_ACCESS_TABLE+
                " FROM " + tableName +
                " WHERE " +
                COUNTRY_CODE_COL + " = '" + country + "' " +
                " AND " + LAY_R1_LIST_CODE_COL + " = '" + district + "' " +
                " AND " + LAY_R2_LIST_CODE_COL + " = '" + upzella + "' " +
                " AND " + LAY_R3_LIST_CODE_COL + " = '" + unit + "' " +
                " AND " + LAY_R4_LIST_CODE_COL + " = '" + village + "' ";
    }

    public static String getFFAMemberListForServiceSelectQuery(String country, String donor,
                                                               String award, String program, String service,
                                                               String opCode, String opMonthCode,
                                                               String memberId, String groupCode, String distFlag) {
        String getMemberName;
        if (country.equals("0004")) {
            getMemberName = SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.MEM_NAME_FIRST_COL +
                    "|| ' ' || " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.MEM_NAME_MIDDLE_COL +
                    "|| ' ' || " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.MEM_NAME_LAST_COL;

        } else
            getMemberName = SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.MEM_NAME_COL;


        return "SELECT  " +
                SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HHID_COL
                + "," + REGISTRATION_TABLE + "." + SQLiteHandler.PNAME_COL
                + "," + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HH_MEM_ID
                + ", " + getMemberName + " AS memName "
                + " ," + REGISTRATION_MEMBER_TABLE + "." + SEX_COL
                + "," + REGISTRATION_MEMBER_TABLE + "." + MEM_AGE
                + "," + REG_N_ASSIGN_PROG_SRV_TABLE + "." + COUNTRY_CODE_COL
                + "," + REG_N_ASSIGN_PROG_SRV_TABLE + "." + DONOR_CODE_COL
                + "," + REG_N_ASSIGN_PROG_SRV_TABLE + "." + AWARD_CODE_COL
                + "," + REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R1_LIST_CODE_COL
                + "," + REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R2_LIST_CODE_COL
                + "," + REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R3_LIST_CODE_COL
                + "," + REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R4_LIST_CODE_COL
                + "," + REG_N_ASSIGN_PROG_SRV_TABLE + "." + HHID_COL
                + "," + REG_N_ASSIGN_PROG_SRV_TABLE + "." + PROGRAM_CODE_COL
                + "," + REG_N_ASSIGN_PROG_SRV_TABLE + "." + SERVICE_CODE_COL
                + "," +

                "( SELECT  COUNT (*) " +
                " FROM " + SQLiteHandler.SERVICE_TABLE
                + " WHERE "
                + COUNTRY_CODE_COL + " = " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + COUNTRY_CODE_COL
                + " AND " + DONOR_CODE_COL + " = " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + DONOR_CODE_COL
                + " AND " + AWARD_CODE_COL + " = " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + AWARD_CODE_COL
                + " AND " + HHID_COL + " = " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + HHID_COL
                + " AND " + HH_MEM_ID + " = " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + HH_MEM_ID
                + " AND " + PROGRAM_CODE_COL + " = " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + PROGRAM_CODE_COL
                + " AND " + SERVICE_CODE_COL + " = " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + SERVICE_CODE_COL
                + " AND " + SQLiteHandler.OPERATION_CODE_COL + " = '2'"
                + " AND " + OP_MONTH_CODE_COL + " = '" + opMonthCode + "' "
                + " AND " + SQLiteHandler.DIST_FLAG_COL + " = '" + distFlag + "' "
                + " ) AS SrvRecieved "

                + " , " +
                SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R1_LIST_CODE_COL
                + " || \"\" || " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R2_LIST_CODE_COL
                + " || \"\" || " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R3_LIST_CODE_COL
                + " || \"\" || " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R4_LIST_CODE_COL
                + " || \"\" || " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + HHID_COL
                + " || \"\" || " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HH_MEM_ID + " AS NewID "

                /** Add new string  id show ..*/


                + " , IFNULL(" + SQLiteHandler.SERVICE_TABLE + "." + SQLiteHandler.WORK_DAY_COL + ",'0') AS WD"

                + " FROM " +
                SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE +
                " INNER JOIN " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + " ON " +
                SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + COUNTRY_CODE_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + COUNTRY_CODE_COL
                + " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R1_LIST_CODE_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL
                + " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R2_LIST_CODE_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL
                + " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R3_LIST_CODE_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + UNITE_NAME_COL
                + " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R4_LIST_CODE_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL

                + " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + HHID_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HHID_COL
                + " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + HH_MEM_ID + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HH_MEM_ID
                    /*
                    REG_N_ASSIGN_PROG_SRV_TABLE+"."+LAY_R4_LIST_CODE_COL+" = "+REGISTRATION_MEMBER_TABLE+"."+VILLAGE_NAME_COL+" AND "+
                    REG_N_ASSIGN_PROG_SRV_TABLE+"."+HHID_COL+" = "+REGISTRATION_MEMBER_TABLE+"."+HHID_COL+" AND "+
                    */

                + " INNER JOIN " +
                REGISTRATION_TABLE
                + " ON " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + COUNTRY_CODE_COL + " = " + REGISTRATION_TABLE + "." + COUNTRY_CODE_COL
                + " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R1_LIST_CODE_COL + " = " + REGISTRATION_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL
                + " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R2_LIST_CODE_COL + " = " + REGISTRATION_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL
                + " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R3_LIST_CODE_COL + " = " + REGISTRATION_TABLE + "." + UNITE_NAME_COL
                + " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R4_LIST_CODE_COL + " = " + REGISTRATION_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL
                + " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + HHID_COL + " = " + REGISTRATION_TABLE + "." + SQLiteHandler.PID_COL

                + " INNER JOIN " +
                SQLiteHandler.REG_N_MEM_PROG_GRP_TABLE + " ON " +

                SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + COUNTRY_CODE_COL + " = " + SQLiteHandler.REG_N_MEM_PROG_GRP_TABLE + "." + COUNTRY_CODE_COL
                + " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R1_LIST_CODE_COL + " = " + SQLiteHandler.REG_N_MEM_PROG_GRP_TABLE + "." + LAY_R1_LIST_CODE_COL
                + " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R2_LIST_CODE_COL + " = " + SQLiteHandler.REG_N_MEM_PROG_GRP_TABLE + "." + LAY_R2_LIST_CODE_COL
                + " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R3_LIST_CODE_COL + " = " + SQLiteHandler.REG_N_MEM_PROG_GRP_TABLE + "." + LAY_R3_LIST_CODE_COL
                + " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + HHID_COL + " = " + SQLiteHandler.REG_N_MEM_PROG_GRP_TABLE + "." + HHID_COL
                + " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + HH_MEM_ID + " = " + SQLiteHandler.REG_N_MEM_PROG_GRP_TABLE + "." + HH_MEM_ID
                + " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + DONOR_CODE_COL + " = " + SQLiteHandler.REG_N_MEM_PROG_GRP_TABLE + "." + DONOR_CODE_COL
                + " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + AWARD_CODE_COL + " = " + SQLiteHandler.REG_N_MEM_PROG_GRP_TABLE + "." + AWARD_CODE_COL
                + " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + PROGRAM_CODE_COL + " = " + SQLiteHandler.REG_N_MEM_PROG_GRP_TABLE + "." + PROGRAM_CODE_COL
                + " AND " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + SERVICE_CODE_COL + " = " + SQLiteHandler.REG_N_MEM_PROG_GRP_TABLE + "." + SERVICE_CODE_COL
                + " AND " + SQLiteHandler.REG_N_MEM_PROG_GRP_TABLE + "." + GROUP_CODE_COL + " = '" + groupCode + "'"
                + " LEFT JOIN "
                + SQLiteHandler.SERVICE_TABLE + " ON "
                + SQLiteHandler.SERVICE_TABLE + "." + COUNTRY_CODE_COL + " = " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + COUNTRY_CODE_COL
                + " AND " + SQLiteHandler.SERVICE_TABLE + "." + DONOR_CODE_COL + " = " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + DONOR_CODE_COL
                + " AND " + SQLiteHandler.SERVICE_TABLE + "." + AWARD_CODE_COL + " = " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + AWARD_CODE_COL
                + " AND " + SQLiteHandler.SERVICE_TABLE + "." + LAY_R1_LIST_CODE_COL + " = " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R1_LIST_CODE_COL
                + " AND " + SQLiteHandler.SERVICE_TABLE + "." + LAY_R2_LIST_CODE_COL + " = " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R2_LIST_CODE_COL
                + " AND " + SQLiteHandler.SERVICE_TABLE + "." + LAY_R3_LIST_CODE_COL + " = " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R3_LIST_CODE_COL
                + " AND " + SQLiteHandler.SERVICE_TABLE + "." + LAY_R4_LIST_CODE_COL + " = " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R4_LIST_CODE_COL
                + " AND " + SQLiteHandler.SERVICE_TABLE + "." + HHID_COL + " = " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + HHID_COL
                + " AND " + SQLiteHandler.SERVICE_TABLE + "." + HH_MEM_ID + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HH_MEM_ID
                + " AND " + SQLiteHandler.SERVICE_TABLE + "." + PROGRAM_CODE_COL + " = " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + PROGRAM_CODE_COL
                + " AND " + SQLiteHandler.SERVICE_TABLE + "." + SERVICE_CODE_COL + " = " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + SERVICE_CODE_COL
                + " AND " + SQLiteHandler.SERVICE_TABLE + "." + SQLiteHandler.OPERATION_CODE_COL + " = '2'"
                + " AND " + SQLiteHandler.SERVICE_TABLE + "." + OP_MONTH_CODE_COL + " = '" + opMonthCode + "' "
                + " WHERE " +
                SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + COUNTRY_CODE_COL + " = '" + country + "'  AND " +
                SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + DONOR_CODE_COL + " = '" + donor + "'  AND " +
                SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + AWARD_CODE_COL + " = '" + award + "'  AND " +
                SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + PROGRAM_CODE_COL + " = '" + program + "'  AND " +
                SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + SERVICE_CODE_COL + " = '" + service + "' AND " +
                /**  id Searching  with 15 digit*/
                SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R1_LIST_CODE_COL + " || \"\" || " +
                SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R2_LIST_CODE_COL + " || \"\" || " +
                SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R3_LIST_CODE_COL + " || \"\" || " +
                SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R4_LIST_CODE_COL + " || \"\" || " +
                SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + HHID_COL + " || \"\" || " +
                SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HH_MEM_ID
                + " LIKE '%" + memberId + "%' " +
                " GROUP BY " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HHID_COL +
                //  " , "+ REGISTRATION_MEMBER_TABLE+"."+HH_MEM_ID+
                "  ORDER BY " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HHID_COL + " DESC, RegMembers.RegisterID DESC";
        //  " , "+ REGISTRATION_MEMBER_TABLE+"."+HH_MEM_ID+ "  ORDER BY "+ REGISTRATION_MEMBER_TABLE+"."+HHID_COL+" DESC, RegMembers.RegisterID DESC";


    }

    private static String dbo_Get_SrvMemCount(String opMonthCode, String distFlag, String condTable) {
        return "SELECT  COUNT (*) " +
                " FROM " + SERVICE_TABLE +
                " WHERE "
                + COUNTRY_CODE_COL + " = " + condTable + "." + COUNTRY_CODE_COL
                + " AND " + DONOR_CODE_COL + " = " + condTable + "." + DONOR_CODE_COL
                + " AND " + AWARD_CODE_COL + " = " + condTable + "." + AWARD_CODE_COL
                + " AND " + HHID_COL + " = " + condTable + "." + HHID_COL
                + " AND " + HH_MEM_ID + " = " + REGISTRATION_MEMBER_TABLE + "." + HH_MEM_ID
                + " AND " + PROGRAM_CODE_COL + " = " + condTable + "." + PROGRAM_CODE_COL
                + " AND " + SERVICE_CODE_COL + " = " + condTable + "." + SERVICE_CODE_COL
                + " AND " + OPERATION_CODE_COL + " = '2'"
                + " AND " + OP_MONTH_CODE_COL + " = '" + opMonthCode + "' "
                + " AND " + DIST_FLAG_COL + " = '" + distFlag + "' ";
    }

    private static String dbo_Get_OpMonthEndDate(String cCode, String donorCode, String awardCode, String opCode, String opMonthCode) {
        return " Select date(substr(" + USA_END_DATE + ",7,4)||'-'|| substr(" + USA_END_DATE + ",1,2)||'-'|| substr(" + USA_END_DATE + ",4,2))" +
                "from " + OP_MONTH_TABLE + " where "
                + COUNTRY_CODE_COL + "= '" + cCode + "' " +
                " AND " + DONOR_CODE_COL + "= '" + donorCode + "'" +
                " AND " + AWARD_CODE_COL + "= '" + awardCode + "' " +
                " AND " + OPERATION_CODE_COL + "='" + opCode + "'" +
                " AND " + OP_MONTH_CODE_COL + "='" + opMonthCode + "'";
    }


    private static String dbo_Get_OpMonthStartDate(String cCode, String donorCode, String awardCode, String opCode, String opMonthCode) {
        return " Select date(substr(" + USA_START_DATE + ",7,4)||'-'|| substr(" + USA_START_DATE + ",1,2)||'-'|| substr(" + USA_START_DATE + ",4,2))" +
                "from " + OP_MONTH_TABLE + " where "
                + COUNTRY_CODE_COL + "= '" + cCode + "' " +
                " AND " + DONOR_CODE_COL + "= '" + donorCode + "'" +
                " AND " + AWARD_CODE_COL + "= '" + awardCode + "' " +
                " AND " + OPERATION_CODE_COL + "='" + opCode + "'" +
                " AND " + OP_MONTH_CODE_COL + "='" + opMonthCode + "'";
    }

    public static String dbo_Get_MemCU2DOB(String cCode, String layR1Code, String layR2Code, String layR3Code, String layR4Code, String hhId, String memId) {
        return "SELECT " + CU2DOB_DATE_COL + " FROM " + REG_N_CU2_TABLE
                + " WHERE    " + COUNTRY_CODE_COL + " = " + cCode
                + " AND " + LAY_R1_LIST_CODE_COL + " = " + layR1Code
                + " AND " + LAY_R2_LIST_CODE_COL + " = " + layR2Code
                + " AND " + LAY_R3_LIST_CODE_COL + " = " + layR3Code
                + " AND " + LAY_R4_LIST_CODE_COL + " = " + layR4Code
                + " AND " + HHID_COL + " = " + hhId
                + " AND " + HH_MEM_ID + " = " + memId + "  ";
    }

    public static String dbo_Get_dayDifference(String cCode, String donorCode, String awardCode, String opMonthCode, String tableName, String columnName) {
        return "CAST ( ( (" +
                "SELECT julianday(date(substr(usaEndDate, 7, 4) || '-' || substr(usaEndDate, 1, 2) || '-' || substr(usaEndDate, 4, 2) ) ) AS d " +
                "        FROM " + OP_MONTH_TABLE +
                "        WHERE " + COUNTRY_CODE_COL + " = '" + cCode + "' AND" +
                "                " + DONOR_CODE_COL + " = '" + donorCode + "' AND" +
                "                " + AWARD_CODE_COL + " = '" + awardCode + "' AND" +
                "                " + OPERATION_CODE_COL + " = '2' AND" +
                "                " + OP_MONTH_CODE_COL + " = '" + opMonthCode + "'" +
                "        )" +
                "        -      (" +
                "                SELECT julianday(date(substr(" + columnName + ", 7, 4) || '-' || substr(" + columnName + ", 1, 2) || '-' || substr(" + columnName + ", 4, 2) ) ) AS dd " +
                "        FROM " + tableName + " " +
                "        WHERE " + COUNTRY_CODE_COL + " = regAss." + COUNTRY_CODE_COL + " AND" +
                "                " + LAY_R1_LIST_CODE_COL + " = regAss." + LAY_R1_LIST_CODE_COL + " AND " +
                "                " + LAY_R2_LIST_CODE_COL + " = regAss." + LAY_R2_LIST_CODE_COL + " AND " +
                "                " + LAY_R3_LIST_CODE_COL + " = regAss." + LAY_R3_LIST_CODE_COL + " AND" +
                "                " + LAY_R4_LIST_CODE_COL + " = regAss." + LAY_R4_LIST_CODE_COL + " AND" +
                "                " + HHID_COL + " = regAss." + HHID_COL + " AND" +
                "                " + HH_MEM_ID + " = regAss." + HH_MEM_ID + "" +
                "        )" +
                "        ) AS INTEGER) AS daydiffernce";
    }

    public static String getRptMemberServiceList_cu2_sql(String country, String donor, String award, String program, String srvCode, String opCode, String opMonthCode, String memId, String grpCode, String distFlag,
                                                         String grpLayR1Code, String grpLayR2Code, String grpLayR3Code
    ) {


        return "SELECT  " +
                dbo_Get_dayDifference(country, donor, award, opMonthCode, REG_N_CU2_TABLE, CU2DOB_DATE_COL) +

                ", " + REGISTRATION_MEMBER_TABLE + "." + HHID_COL + "," +
                REGISTRATION_TABLE + "." + PNAME_COL + "," +
                REGISTRATION_MEMBER_TABLE + "." + HH_MEM_ID + ", " +
                REGISTRATION_MEMBER_TABLE + "." + MEM_NAME_COL + " AS memName ," +
                REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.SEX_COL + "," +
                REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.MEM_AGE + "," +
                " regAss." + COUNTRY_CODE_COL + "," +
                " regAss." + DONOR_CODE_COL + "," +
                " regAss." + AWARD_CODE_COL + "," +
                " regAss." + LAY_R1_LIST_CODE_COL + "," +
                " regAss." + LAY_R2_LIST_CODE_COL + "," +
                " regAss." + LAY_R3_LIST_CODE_COL + "," +
                " regAss." + LAY_R4_LIST_CODE_COL + "," +
                " regAss." + HHID_COL + "," +
                " regAss." + PROGRAM_CODE_COL + "," +
                " regAss." + SERVICE_CODE_COL + "," +

                "( " + dbo_Get_SrvMemCount(opMonthCode, distFlag, " regAss") + " ) AS SrvRecieved ," +
                " regAss." + LAY_R1_LIST_CODE_COL + " || \"\" || " +
                " regAss." + LAY_R2_LIST_CODE_COL + " || \"\" || " +
                " regAss." + LAY_R3_LIST_CODE_COL + " || \"\" || " +
                " regAss." + LAY_R4_LIST_CODE_COL + " || \"\" || " +
                " regAss." + HHID_COL + " || \"\" || " +
                REGISTRATION_MEMBER_TABLE + "." + HH_MEM_ID + " AS NewID " +
                /** Add new string  id show ..*/

                "FROM " + REG_N_ASSIGN_PROG_SRV_TABLE + " AS regAss" +
                " INNER JOIN " + REGISTRATION_MEMBER_TABLE + " ON "
                + " regAss." + COUNTRY_CODE_COL + " = " + REGISTRATION_MEMBER_TABLE + "." + COUNTRY_CODE_COL
                + " AND  regAss." + LAY_R1_LIST_CODE_COL + " = " + REGISTRATION_MEMBER_TABLE + "." + DISTRICT_NAME_COL
                + " AND  regAss." + LAY_R2_LIST_CODE_COL + " = " + REGISTRATION_MEMBER_TABLE + "." + UPZILLA_NAME_COL
                + " AND  regAss." + LAY_R3_LIST_CODE_COL + " = " + REGISTRATION_MEMBER_TABLE + "." + UNITE_NAME_COL
                + " AND  regAss." + LAY_R4_LIST_CODE_COL + " = " + REGISTRATION_MEMBER_TABLE + "." + VILLAGE_NAME_COL
                + " AND  regAss." + HHID_COL + " = " + REGISTRATION_MEMBER_TABLE + "." + HHID_COL
                + " AND  regAss." + HH_MEM_ID + " = " + REGISTRATION_MEMBER_TABLE + "." + HH_MEM_ID
                +

                " INNER JOIN " +
                REGISTRATION_TABLE + " ON "
                + " regAss." + COUNTRY_CODE_COL + " = " + REGISTRATION_TABLE + "." + COUNTRY_CODE_COL
                + " AND  regAss." + LAY_R1_LIST_CODE_COL + " = " + REGISTRATION_TABLE + "." + DISTRICT_NAME_COL
                + " AND  regAss." + LAY_R2_LIST_CODE_COL + " = " + REGISTRATION_TABLE + "." + UPZILLA_NAME_COL
                + " AND  regAss." + LAY_R3_LIST_CODE_COL + " = " + REGISTRATION_TABLE + "." + UNITE_NAME_COL
                + " AND  regAss." + LAY_R4_LIST_CODE_COL + " = " + REGISTRATION_TABLE + "." + VILLAGE_NAME_COL
                + " AND  regAss." + HHID_COL + " = " + REGISTRATION_TABLE + "." + PID_COL


// for test
                + " INNER JOIN " +
                REG_N_MEM_PROG_GRP_TABLE + " ON "

                + " regAss." + COUNTRY_CODE_COL + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + COUNTRY_CODE_COL
                + " AND  regAss." + LAY_R1_LIST_CODE_COL + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + LAY_R1_LIST_CODE_COL
                + " AND  regAss." + LAY_R2_LIST_CODE_COL + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + LAY_R2_LIST_CODE_COL
                + " AND  regAss." + LAY_R3_LIST_CODE_COL + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + LAY_R3_LIST_CODE_COL
                + " AND  regAss." + HHID_COL + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + HHID_COL
                + " AND  regAss." + HH_MEM_ID + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + HH_MEM_ID
                + " AND  regAss." + DONOR_CODE_COL + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + DONOR_CODE_COL
                + " AND  regAss." + AWARD_CODE_COL + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + AWARD_CODE_COL
                + " AND  regAss." + PROGRAM_CODE_COL + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + PROGRAM_CODE_COL
                + " AND  regAss." + SERVICE_CODE_COL + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + SERVICE_CODE_COL


                + " WHERE " +
                " regAss." + COUNTRY_CODE_COL + " = '" + country + "'" +
                "  AND " + " regAss." + DONOR_CODE_COL + " = '" + donor + "'" +
                "  AND " + " regAss." + AWARD_CODE_COL + " = '" + award + "'" +
                "  AND " + " regAss." + PROGRAM_CODE_COL + " = '" + program + "'" +
                "  AND " + " regAss." + SERVICE_CODE_COL + " = '" + srvCode + "'" +

                " AND date(substr(regAss." + REG_N_DAT_COL + ", 7, 4) || '-' || substr(regAss." + REG_N_DAT_COL + ", 1, 2) || '-' || substr(regAss." + REG_N_DAT_COL + ", 4, 2)) <=" + "(" + dbo_Get_OpMonthEndDate(country, donor, award, "2", opMonthCode) + ")" +
                " AND date(substr(regAss." + GRD_DATE_COL + ", 7, 4) || '-' || substr(regAss." + GRD_DATE_COL + ", 1, 2) || '-' || substr(regAss." + GRD_DATE_COL + ", 4, 2)) >=" + "(" + dbo_Get_OpMonthStartDate(country, donor, award, "2", opMonthCode) + ")" +

                /**  id Searching  with 15 digit*/
                " AND "
                + " daydiffernce <= 720 " +

                " AND " + REG_N_MEM_PROG_GRP_TABLE + "." + GROUP_CODE_COL + " = '" + grpCode + "'" +
                " AND " + REG_N_MEM_PROG_GRP_TABLE + "." + GRP_LAY_R1_LIST_CODE_COL + " = '" + grpLayR1Code + "'" +
                " AND " + REG_N_MEM_PROG_GRP_TABLE + "." + GRP_LAY_R2_LIST_CODE_COL + " = '" + grpLayR2Code + "'" +
                " AND " + REG_N_MEM_PROG_GRP_TABLE + "." + GRP_LAY_R3_LIST_CODE_COL + " = '" + grpLayR3Code + "'" +
                " AND " +
                " regAss." + LAY_R1_LIST_CODE_COL + " || \"\" || " +
                " regAss." + LAY_R2_LIST_CODE_COL + " || \"\" || " +
                " regAss." + LAY_R3_LIST_CODE_COL + " || \"\" || " +
                " regAss." + LAY_R4_LIST_CODE_COL + " || \"\" || " +
                " regAss." + HHID_COL + " || \"\" || " +
                REGISTRATION_MEMBER_TABLE + "." + HH_MEM_ID
                + " LIKE '%" + memId + "%' " +
                " GROUP BY " + REGISTRATION_MEMBER_TABLE + "." + HHID_COL +
                //  " , "+ REGISTRATION_MEMBER_TABLE+"."+HH_MEM_ID+
                "  ORDER BY " + REGISTRATION_MEMBER_TABLE + "." + HHID_COL + " DESC, RegMembers.RegisterID DESC";
        //  " , "+ REGISTRATION_MEMBER_TABLE+"."+HH_MEM_ID+ "  ORDER BY "+ REGISTRATION_MEMBER_TABLE+"."+HHID_COL+" DESC, RegMembers.RegisterID DESC";

      /*  SELECT CAST (((
                SELECT
        julianday(date(substr(usaEndDate, 7, 4) || '-' || substr(usaEndDate, 1, 2) || '-' || substr(usaEndDate, 4, 2)))
        AS d
        FROM OpMonthTable
        WHERE CountryCode = '0002' AND
                DonorCode = '01' AND
                AwardCode = '01' AND
                OpCode = '2' AND
                OpMonthCode = '15'
        )
        -(
                SELECT
        julianday(date(substr(CU2Dob, 7, 4) || '-' || substr(CU2Dob, 1, 2) || '-' || substr(CU2Dob, 4, 2)))
        AS dd
        FROM RegN_CU2
        WHERE CountryCode = regAss.CountryCode AND
                DistrictCode = regAss.DistrictCode AND
                UpazillaCode = regAss.UpazillaCode AND
                UnitCode = regAss.UnitCode AND
                VillageCode = regAss.VillageCode AND
                RegisterID = regAss.RegisterID AND
                MemberID = regAss.MemberID
        )
        )AS INTEGER)AS daydiffernce,
        RegMembers.RegisterID,
                Registration.PersonName,
                RegMembers.MemberID,
                RegMembers.MemberName AS memName,
        RegMembers.Sex,
                RegMembers.MemAge,
                regAss.CountryCode,
                regAss.DonorCode,
                regAss.AwardCode,
                regAss.DistrictCode,
                regAss.UpazillaCode,
                regAss.UnitCode,
                regAss.VillageCode,
                regAss.RegisterID,
                regAss.ProgramCode,
                regAss.ServiceCode,
                (
                        SELECT COUNT( *)
        FROM Service
        WHERE CountryCode = regAss.CountryCode AND
                DonorCode = regAss.DonorCode AND
                AwardCode = regAss.AwardCode AND
                RegisterID = regAss.RegisterID AND
                MemberID = RegMembers.MemberID AND
                ProgramCode = regAss.ProgramCode AND
                ServiceCode = regAss.ServiceCode AND
                OpCode = '2' AND
                OpMonthCode = '15' AND
                DistFLAG = 'FoodFlag'
        )
        AS SrvRecieved,
        regAss.DistrictCode || "" || regAss.UpazillaCode || "" || regAss.UnitCode || "" || regAss.VillageCode || "" || regAss.RegisterID || "" || RegMembers.MemberID
        AS NewID
        FROM RegNAssignProgService AS regAss
        INNER JOIN
        RegMembers ON regAss.CountryCode = RegMembers.CountryCode AND
        regAss.DistrictCode = RegMembers.DistrictName AND
        regAss.UpazillaCode = RegMembers.UpazillaName AND
        regAss.UnitCode = RegMembers.UnitName AND
        regAss.VillageCode = RegMembers.VillageName AND
        regAss.RegisterID = RegMembers.RegisterID AND
        regAss.MemberID = RegMembers.MemberID
        INNER JOIN
        Registration ON regAss.CountryCode = Registration.CountryCode AND
        regAss.DistrictCode = Registration.DistrictName AND
        regAss.UpazillaCode = Registration.UpazillaName AND
        regAss.UnitCode = Registration.UnitName AND
        regAss.VillageCode = Registration.VillageName AND
        regAss.RegisterID = Registration.RegistrationID
        WHERE regAss.CountryCode = '0002' AND
        regAss.DonorCode = '01' AND
        regAss.AwardCode = '01' AND
        regAss.ProgramCode = '001' AND
        regAss.ServiceCode = '03' AND
        date(substr(regAss.RegNDate, 7, 4) || '-' || substr(regAss.RegNDate, 1, 2) || '-' || substr(regAss.RegNDate, 4, 2)) <= (
                SELECT
        date(substr(usaEndDate, 7, 4) || '-' || substr(usaEndDate, 1, 2) || '-' || substr(usaEndDate, 4, 2))
        FROM OpMonthTable
        WHERE CountryCode = '0002' AND
                DonorCode = '01' AND
                AwardCode = '01' AND
                OpCode = '2' AND
                OpMonthCode = '15'
        )
        AND
        date(substr(regAss.GRDDate, 7, 4) || '-' || substr(regAss.GRDDate, 1, 2) || '-' || substr(regAss.GRDDate, 4, 2)) >= (
                SELECT
        date(substr(usaStartDate, 7, 4) || '-' || substr(usaStartDate, 1, 2) || '-' || substr(usaStartDate, 4, 2))
        FROM OpMonthTable
        WHERE CountryCode = '0002' AND
                DonorCode = '01' AND
                AwardCode = '01' AND
                OpCode = '2' AND
                OpMonthCode = '15'
        )
        AND
        daydiffernce <= 720 AND
        regAss.DistrictCode || "" || regAss.UpazillaCode || "" || regAss.UnitCode || "" || regAss.VillageCode || "" || regAss.RegisterID || "" || RegMembers.MemberID
        LIKE '%%'
        GROUP BY RegMembers.RegisterID
        ORDER BY RegMembers.RegisterID DESC,
        RegMembers.RegisterID DESC;*/

    }


    public static String getRptMemberServiceList_ca2_sql(String country, String donor, String award, String program, String srvCode, String opCode, String opMonthCode, String memId, String grpCode, String distFlag, String grpLayR1Code, String grpLayR2Code, String grpLayR3Code) {


        return "SELECT  " +
                dbo_Get_dayDifference(country, donor, award, opMonthCode, REG_N_CA2_TABLE, CA2DOB_DATE_COL) +

                ", " + REGISTRATION_MEMBER_TABLE + "." + HHID_COL + "," +
                REGISTRATION_TABLE + "." + PNAME_COL + "," +
                REGISTRATION_MEMBER_TABLE + "." + HH_MEM_ID + ", " +
                REGISTRATION_MEMBER_TABLE + "." + MEM_NAME_COL + " AS memName ," +
                REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.SEX_COL + "," +
                REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.MEM_AGE + "," +
                " regAss." + COUNTRY_CODE_COL + "," +
                " regAss." + DONOR_CODE_COL + "," +
                " regAss." + AWARD_CODE_COL + "," +
                " regAss." + LAY_R1_LIST_CODE_COL + "," +
                " regAss." + LAY_R2_LIST_CODE_COL + "," +
                " regAss." + LAY_R3_LIST_CODE_COL + "," +
                " regAss." + LAY_R4_LIST_CODE_COL + "," +
                " regAss." + HHID_COL + "," +
                " regAss." + PROGRAM_CODE_COL + "," +
                " regAss." + SERVICE_CODE_COL + "," +

                "( " + dbo_Get_SrvMemCount(opMonthCode, distFlag, " regAss") + " ) AS SrvRecieved ," +
                " regAss." + LAY_R1_LIST_CODE_COL + " || \"\" || " +
                " regAss." + LAY_R2_LIST_CODE_COL + " || \"\" || " +
                " regAss." + LAY_R3_LIST_CODE_COL + " || \"\" || " +
                " regAss." + LAY_R4_LIST_CODE_COL + " || \"\" || " +
                " regAss." + HHID_COL + " || \"\" || " +
                REGISTRATION_MEMBER_TABLE + "." + HH_MEM_ID + " AS NewID " +
                /** Add new string  id show ..*/

                "FROM " + REG_N_ASSIGN_PROG_SRV_TABLE + " AS regAss" +
                " INNER JOIN " + REGISTRATION_MEMBER_TABLE + " ON "
                + " regAss." + COUNTRY_CODE_COL + " = " + REGISTRATION_MEMBER_TABLE + "." + COUNTRY_CODE_COL
                + " AND  regAss." + LAY_R1_LIST_CODE_COL + " = " + REGISTRATION_MEMBER_TABLE + "." + DISTRICT_NAME_COL
                + " AND  regAss." + LAY_R2_LIST_CODE_COL + " = " + REGISTRATION_MEMBER_TABLE + "." + UPZILLA_NAME_COL
                + " AND  regAss." + LAY_R3_LIST_CODE_COL + " = " + REGISTRATION_MEMBER_TABLE + "." + UNITE_NAME_COL
                + " AND  regAss." + LAY_R4_LIST_CODE_COL + " = " + REGISTRATION_MEMBER_TABLE + "." + VILLAGE_NAME_COL
                + " AND  regAss." + HHID_COL + " = " + REGISTRATION_MEMBER_TABLE + "." + HHID_COL
                + " AND  regAss." + HH_MEM_ID + " = " + REGISTRATION_MEMBER_TABLE + "." + HH_MEM_ID
                +

                " INNER JOIN " +
                REGISTRATION_TABLE + " ON "
                + " regAss." + COUNTRY_CODE_COL + " = " + REGISTRATION_TABLE + "." + COUNTRY_CODE_COL
                + " AND  regAss." + LAY_R1_LIST_CODE_COL + " = " + REGISTRATION_TABLE + "." + DISTRICT_NAME_COL
                + " AND  regAss." + LAY_R2_LIST_CODE_COL + " = " + REGISTRATION_TABLE + "." + UPZILLA_NAME_COL
                + " AND  regAss." + LAY_R3_LIST_CODE_COL + " = " + REGISTRATION_TABLE + "." + UNITE_NAME_COL
                + " AND  regAss." + LAY_R4_LIST_CODE_COL + " = " + REGISTRATION_TABLE + "." + VILLAGE_NAME_COL
                + " AND  regAss." + HHID_COL + " = " + REGISTRATION_TABLE + "." + PID_COL


// for test
                + " INNER JOIN " +
                REG_N_MEM_PROG_GRP_TABLE + " ON "

                + " regAss." + COUNTRY_CODE_COL + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + COUNTRY_CODE_COL
                + " AND  regAss." + LAY_R1_LIST_CODE_COL + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + LAY_R1_LIST_CODE_COL
                + " AND  regAss." + LAY_R2_LIST_CODE_COL + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + LAY_R2_LIST_CODE_COL
                + " AND  regAss." + LAY_R3_LIST_CODE_COL + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + LAY_R3_LIST_CODE_COL
                + " AND  regAss." + HHID_COL + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + HHID_COL
                + " AND  regAss." + HH_MEM_ID + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + HH_MEM_ID
                + " AND  regAss." + DONOR_CODE_COL + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + DONOR_CODE_COL
                + " AND  regAss." + AWARD_CODE_COL + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + AWARD_CODE_COL
                + " AND  regAss." + PROGRAM_CODE_COL + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + PROGRAM_CODE_COL
                + " AND  regAss." + SERVICE_CODE_COL + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + SERVICE_CODE_COL


                + " WHERE " +
                " regAss." + COUNTRY_CODE_COL + " = '" + country + "'" +
                "  AND " + " regAss." + DONOR_CODE_COL + " = '" + donor + "'" +
                "  AND " + " regAss." + AWARD_CODE_COL + " = '" + award + "'" +
                "  AND " + " regAss." + PROGRAM_CODE_COL + " = '" + program + "'" +
                "  AND " + " regAss." + SERVICE_CODE_COL + " = '" + srvCode + "'" +

                " AND date(substr(regAss." + REG_N_DAT_COL + ", 7, 4) || '-' || substr(regAss." + REG_N_DAT_COL + ", 1, 2) || '-' || substr(regAss." + REG_N_DAT_COL + ", 4, 2)) <=" + "(" + dbo_Get_OpMonthEndDate(country, donor, award, "2", opMonthCode) + ")" +
                " AND date(substr(regAss." + GRD_DATE_COL + ", 7, 4) || '-' || substr(regAss." + GRD_DATE_COL + ", 1, 2) || '-' || substr(regAss." + GRD_DATE_COL + ", 4, 2)) >=" + "(" + dbo_Get_OpMonthStartDate(country, donor, award, "2", opMonthCode) + ")" +

                /**  id Searching  with 15 digit*/
                " AND "
                + " daydiffernce <= 1800 " +

                " AND " + REG_N_MEM_PROG_GRP_TABLE + "." + GROUP_CODE_COL + " = '" + grpCode + "'" +
                " AND " + REG_N_MEM_PROG_GRP_TABLE + "." + GRP_LAY_R1_LIST_CODE_COL + " = '" + grpLayR1Code + "'" +
                " AND " + REG_N_MEM_PROG_GRP_TABLE + "." + GRP_LAY_R2_LIST_CODE_COL + " = '" + grpLayR2Code + "'" +
                " AND " + REG_N_MEM_PROG_GRP_TABLE + "." + GRP_LAY_R3_LIST_CODE_COL + " = '" + grpLayR3Code + "'" +
                " AND " +
                " regAss." + LAY_R1_LIST_CODE_COL + " || \"\" || " +
                " regAss." + LAY_R2_LIST_CODE_COL + " || \"\" || " +
                " regAss." + LAY_R3_LIST_CODE_COL + " || \"\" || " +
                " regAss." + LAY_R4_LIST_CODE_COL + " || \"\" || " +
                " regAss." + HHID_COL + " || \"\" || " +
                REGISTRATION_MEMBER_TABLE + "." + HH_MEM_ID
                + " LIKE '%" + memId + "%' " +
                " GROUP BY " + REGISTRATION_MEMBER_TABLE + "." + HHID_COL +
                //  " , "+ REGISTRATION_MEMBER_TABLE+"."+HH_MEM_ID+
                "  ORDER BY " + REGISTRATION_MEMBER_TABLE + "." + HHID_COL + " DESC, RegMembers.RegisterID DESC";
        //  " , "+ REGISTRATION_MEMBER_TABLE+"."+HH_MEM_ID+ "  ORDER BY "+ REGISTRATION_MEMBER_TABLE+"."+HHID_COL+" DESC, RegMembers.RegisterID DESC";


    }


    public static String getRptMemberServiceList_lm_sql(String country, String donor, String award, String program, String srvCode, String opCode, String opMonthCode, String memId, String grpCode, String distFlag, String grpLayR1Code, String grpLayR2Code, String grpLayR3Code) {


        return "SELECT  " +
                dbo_Get_dayDifference(country, donor, award, opMonthCode, REG_N_LM_TABLE, LM_DATE_COL) +

                ", " + REGISTRATION_MEMBER_TABLE + "." + HHID_COL + "," +
                REGISTRATION_TABLE + "." + PNAME_COL + "," +
                REGISTRATION_MEMBER_TABLE + "." + HH_MEM_ID + ", " +
                REGISTRATION_MEMBER_TABLE + "." + MEM_NAME_COL + " AS memName ," +
                REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.SEX_COL + "," +
                REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.MEM_AGE + "," +
                " regAss." + COUNTRY_CODE_COL + "," +
                " regAss." + DONOR_CODE_COL + "," +
                " regAss." + AWARD_CODE_COL + "," +
                " regAss." + LAY_R1_LIST_CODE_COL + "," +
                " regAss." + LAY_R2_LIST_CODE_COL + "," +
                " regAss." + LAY_R3_LIST_CODE_COL + "," +
                " regAss." + LAY_R4_LIST_CODE_COL + "," +
                " regAss." + HHID_COL + "," +
                " regAss." + PROGRAM_CODE_COL + "," +
                " regAss." + SERVICE_CODE_COL + "," +

                "( " + dbo_Get_SrvMemCount(opMonthCode, distFlag, " regAss") + " ) AS SrvRecieved ," +
                " regAss." + LAY_R1_LIST_CODE_COL + " || \"\" || " +
                " regAss." + LAY_R2_LIST_CODE_COL + " || \"\" || " +
                " regAss." + LAY_R3_LIST_CODE_COL + " || \"\" || " +
                " regAss." + LAY_R4_LIST_CODE_COL + " || \"\" || " +
                " regAss." + HHID_COL + " || \"\" || " +
                REGISTRATION_MEMBER_TABLE + "." + HH_MEM_ID + " AS NewID " +
                /** Add new string  id show ..*/

                "FROM " + REG_N_ASSIGN_PROG_SRV_TABLE + " AS regAss" +
                " INNER JOIN " + REGISTRATION_MEMBER_TABLE + " ON "
                + " regAss." + COUNTRY_CODE_COL + " = " + REGISTRATION_MEMBER_TABLE + "." + COUNTRY_CODE_COL
                + " AND  regAss." + LAY_R1_LIST_CODE_COL + " = " + REGISTRATION_MEMBER_TABLE + "." + DISTRICT_NAME_COL
                + " AND  regAss." + LAY_R2_LIST_CODE_COL + " = " + REGISTRATION_MEMBER_TABLE + "." + UPZILLA_NAME_COL
                + " AND  regAss." + LAY_R3_LIST_CODE_COL + " = " + REGISTRATION_MEMBER_TABLE + "." + UNITE_NAME_COL
                + " AND  regAss." + LAY_R4_LIST_CODE_COL + " = " + REGISTRATION_MEMBER_TABLE + "." + VILLAGE_NAME_COL
                + " AND  regAss." + HHID_COL + " = " + REGISTRATION_MEMBER_TABLE + "." + HHID_COL
                + " AND  regAss." + HH_MEM_ID + " = " + REGISTRATION_MEMBER_TABLE + "." + HH_MEM_ID
                +

                " INNER JOIN " +
                REGISTRATION_TABLE + " ON "
                + " regAss." + COUNTRY_CODE_COL + " = " + REGISTRATION_TABLE + "." + COUNTRY_CODE_COL
                + " AND  regAss." + LAY_R1_LIST_CODE_COL + " = " + REGISTRATION_TABLE + "." + DISTRICT_NAME_COL
                + " AND  regAss." + LAY_R2_LIST_CODE_COL + " = " + REGISTRATION_TABLE + "." + UPZILLA_NAME_COL
                + " AND  regAss." + LAY_R3_LIST_CODE_COL + " = " + REGISTRATION_TABLE + "." + UNITE_NAME_COL
                + " AND  regAss." + LAY_R4_LIST_CODE_COL + " = " + REGISTRATION_TABLE + "." + VILLAGE_NAME_COL
                + " AND  regAss." + HHID_COL + " = " + REGISTRATION_TABLE + "." + PID_COL


// for test
                + " INNER JOIN " +
                REG_N_MEM_PROG_GRP_TABLE + " ON "

                + " regAss." + COUNTRY_CODE_COL + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + COUNTRY_CODE_COL
                + " AND  regAss." + LAY_R1_LIST_CODE_COL + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + LAY_R1_LIST_CODE_COL
                + " AND  regAss." + LAY_R2_LIST_CODE_COL + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + LAY_R2_LIST_CODE_COL
                + " AND  regAss." + LAY_R3_LIST_CODE_COL + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + LAY_R3_LIST_CODE_COL
                + " AND  regAss." + HHID_COL + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + HHID_COL
                + " AND  regAss." + HH_MEM_ID + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + HH_MEM_ID
                + " AND  regAss." + DONOR_CODE_COL + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + DONOR_CODE_COL
                + " AND  regAss." + AWARD_CODE_COL + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + AWARD_CODE_COL
                + " AND  regAss." + PROGRAM_CODE_COL + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + PROGRAM_CODE_COL
                + " AND  regAss." + SERVICE_CODE_COL + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + SERVICE_CODE_COL


                + " WHERE " +
                " regAss." + COUNTRY_CODE_COL + " = '" + country + "'" +
                "  AND " + " regAss." + DONOR_CODE_COL + " = '" + donor + "'" +
                "  AND " + " regAss." + AWARD_CODE_COL + " = '" + award + "'" +
                "  AND " + " regAss." + PROGRAM_CODE_COL + " = '" + program + "'" +
                "  AND " + " regAss." + SERVICE_CODE_COL + " = '" + srvCode + "'" +

                " AND date(substr(regAss." + REG_N_DAT_COL + ", 7, 4) || '-' || substr(regAss." + REG_N_DAT_COL + ", 1, 2) || '-' || substr(regAss." + REG_N_DAT_COL + ", 4, 2)) <=" + "(" + dbo_Get_OpMonthEndDate(country, donor, award, "2", opMonthCode) + ")" +
                " AND date(substr(regAss." + GRD_DATE_COL + ", 7, 4) || '-' || substr(regAss." + GRD_DATE_COL + ", 1, 2) || '-' || substr(regAss." + GRD_DATE_COL + ", 4, 2)) >=" + "(" + dbo_Get_OpMonthStartDate(country, donor, award, "2", opMonthCode) + ")" +

                /**  id Searching  with 15 digit*/
                " AND "
                + " daydiffernce <= 180 " +

                " AND " + REG_N_MEM_PROG_GRP_TABLE + "." + GROUP_CODE_COL + " = '" + grpCode + "'" +
                " AND " + REG_N_MEM_PROG_GRP_TABLE + "." + GRP_LAY_R1_LIST_CODE_COL + " = '" + grpLayR1Code + "'" +
                " AND " + REG_N_MEM_PROG_GRP_TABLE + "." + GRP_LAY_R2_LIST_CODE_COL + " = '" + grpLayR2Code + "'" +
                " AND " + REG_N_MEM_PROG_GRP_TABLE + "." + GRP_LAY_R3_LIST_CODE_COL + " = '" + grpLayR3Code + "'" +
                " AND " +
                " regAss." + LAY_R1_LIST_CODE_COL + " || \"\" || " +
                " regAss." + LAY_R2_LIST_CODE_COL + " || \"\" || " +
                " regAss." + LAY_R3_LIST_CODE_COL + " || \"\" || " +
                " regAss." + LAY_R4_LIST_CODE_COL + " || \"\" || " +
                " regAss." + HHID_COL + " || \"\" || " +
                REGISTRATION_MEMBER_TABLE + "." + HH_MEM_ID
                + " LIKE '%" + memId + "%' " +
                " GROUP BY " + REGISTRATION_MEMBER_TABLE + "." + HHID_COL +
                //  " , "+ REGISTRATION_MEMBER_TABLE+"."+HH_MEM_ID+
                "  ORDER BY " + REGISTRATION_MEMBER_TABLE + "." + HHID_COL + " DESC, RegMembers.RegisterID DESC";
        //  " , "+ REGISTRATION_MEMBER_TABLE+"."+HH_MEM_ID+ "  ORDER BY "+ REGISTRATION_MEMBER_TABLE+"."+HHID_COL+" DESC, RegMembers.RegisterID DESC";


    }


    public static String getRptMemberServiceList_pw_sql(String country, String donor, String award, String program, String srvCode, String opCode, String opMonthCode, String memId, String grpCode, String distFlag, String grpLayR1Code, String grpLayR2Code, String grpLayR3Code) {


        return "SELECT  " +
                dbo_Get_dayDifference(country, donor, award, opMonthCode, REG_N_PW_TABLE, LMP_DATE_COL) +

                ", " + REGISTRATION_MEMBER_TABLE + "." + HHID_COL + "," +
                REGISTRATION_TABLE + "." + PNAME_COL + "," +
                REGISTRATION_MEMBER_TABLE + "." + HH_MEM_ID + ", " +
                REGISTRATION_MEMBER_TABLE + "." + MEM_NAME_COL + " AS memName ," +
                REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.SEX_COL + "," +
                REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.MEM_AGE + "," +
                " regAss." + COUNTRY_CODE_COL + "," +
                " regAss." + DONOR_CODE_COL + "," +
                " regAss." + AWARD_CODE_COL + "," +
                " regAss." + LAY_R1_LIST_CODE_COL + "," +
                " regAss." + LAY_R2_LIST_CODE_COL + "," +
                " regAss." + LAY_R3_LIST_CODE_COL + "," +
                " regAss." + LAY_R4_LIST_CODE_COL + "," +
                " regAss." + HHID_COL + "," +
                " regAss." + PROGRAM_CODE_COL + "," +
                " regAss." + SERVICE_CODE_COL + "," +

                "( " + dbo_Get_SrvMemCount(opMonthCode, distFlag, " regAss") + " ) AS SrvRecieved ," +
                " regAss." + LAY_R1_LIST_CODE_COL + " || \"\" || " +
                " regAss." + LAY_R2_LIST_CODE_COL + " || \"\" || " +
                " regAss." + LAY_R3_LIST_CODE_COL + " || \"\" || " +
                " regAss." + LAY_R4_LIST_CODE_COL + " || \"\" || " +
                " regAss." + HHID_COL + " || \"\" || " +
                REGISTRATION_MEMBER_TABLE + "." + HH_MEM_ID + " AS NewID " +
                /** Add new string  id show ..*/

                "FROM " + REG_N_ASSIGN_PROG_SRV_TABLE + " AS regAss" +
                " INNER JOIN " + REGISTRATION_MEMBER_TABLE + " ON "
                + " regAss." + COUNTRY_CODE_COL + " = " + REGISTRATION_MEMBER_TABLE + "." + COUNTRY_CODE_COL
                + " AND  regAss." + LAY_R1_LIST_CODE_COL + " = " + REGISTRATION_MEMBER_TABLE + "." + DISTRICT_NAME_COL
                + " AND  regAss." + LAY_R2_LIST_CODE_COL + " = " + REGISTRATION_MEMBER_TABLE + "." + UPZILLA_NAME_COL
                + " AND  regAss." + LAY_R3_LIST_CODE_COL + " = " + REGISTRATION_MEMBER_TABLE + "." + UNITE_NAME_COL
                + " AND  regAss." + LAY_R4_LIST_CODE_COL + " = " + REGISTRATION_MEMBER_TABLE + "." + VILLAGE_NAME_COL
                + " AND  regAss." + HHID_COL + " = " + REGISTRATION_MEMBER_TABLE + "." + HHID_COL
                + " AND  regAss." + HH_MEM_ID + " = " + REGISTRATION_MEMBER_TABLE + "." + HH_MEM_ID
                +

                " INNER JOIN " +
                REGISTRATION_TABLE + " ON "
                + " regAss." + COUNTRY_CODE_COL + " = " + REGISTRATION_TABLE + "." + COUNTRY_CODE_COL
                + " AND  regAss." + LAY_R1_LIST_CODE_COL + " = " + REGISTRATION_TABLE + "." + DISTRICT_NAME_COL
                + " AND  regAss." + LAY_R2_LIST_CODE_COL + " = " + REGISTRATION_TABLE + "." + UPZILLA_NAME_COL
                + " AND  regAss." + LAY_R3_LIST_CODE_COL + " = " + REGISTRATION_TABLE + "." + UNITE_NAME_COL
                + " AND  regAss." + LAY_R4_LIST_CODE_COL + " = " + REGISTRATION_TABLE + "." + VILLAGE_NAME_COL
                + " AND  regAss." + HHID_COL + " = " + REGISTRATION_TABLE + "." + PID_COL


// for test
                + " INNER JOIN " +
                REG_N_MEM_PROG_GRP_TABLE + " ON "

                + " regAss." + COUNTRY_CODE_COL + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + COUNTRY_CODE_COL
                + " AND  regAss." + LAY_R1_LIST_CODE_COL + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + LAY_R1_LIST_CODE_COL
                + " AND  regAss." + LAY_R2_LIST_CODE_COL + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + LAY_R2_LIST_CODE_COL
                + " AND  regAss." + LAY_R3_LIST_CODE_COL + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + LAY_R3_LIST_CODE_COL
                + " AND  regAss." + HHID_COL + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + HHID_COL
                + " AND  regAss." + HH_MEM_ID + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + HH_MEM_ID
                + " AND  regAss." + DONOR_CODE_COL + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + DONOR_CODE_COL
                + " AND  regAss." + AWARD_CODE_COL + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + AWARD_CODE_COL
                + " AND  regAss." + PROGRAM_CODE_COL + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + PROGRAM_CODE_COL
                + " AND  regAss." + SERVICE_CODE_COL + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + SERVICE_CODE_COL


                + " WHERE " +
                " regAss." + COUNTRY_CODE_COL + " = '" + country + "'" +
                "  AND " + " regAss." + DONOR_CODE_COL + " = '" + donor + "'" +
                "  AND " + " regAss." + AWARD_CODE_COL + " = '" + award + "'" +
                "  AND " + " regAss." + PROGRAM_CODE_COL + " = '" + program + "'" +
                "  AND " + " regAss." + SERVICE_CODE_COL + " = '" + srvCode + "'" +

                " AND date(substr(regAss." + REG_N_DAT_COL + ", 7, 4) || '-' || substr(regAss." + REG_N_DAT_COL + ", 1, 2) || '-' || substr(regAss." + REG_N_DAT_COL + ", 4, 2)) <=" + "(" + dbo_Get_OpMonthEndDate(country, donor, award, "2", opMonthCode) + ")" +
                " AND date(substr(regAss." + GRD_DATE_COL + ", 7, 4) || '-' || substr(regAss." + GRD_DATE_COL + ", 1, 2) || '-' || substr(regAss." + GRD_DATE_COL + ", 4, 2)) >=" + "(" + dbo_Get_OpMonthStartDate(country, donor, award, "2", opMonthCode) + ")" +

                /**  id Searching  with 15 digit*/
                " AND "
                + " daydiffernce <= 277 " +

                " AND " + REG_N_MEM_PROG_GRP_TABLE + "." + GROUP_CODE_COL + " = '" + grpCode + "'" +
                " AND " + REG_N_MEM_PROG_GRP_TABLE + "." + GRP_LAY_R1_LIST_CODE_COL + " = '" + grpLayR1Code + "'" +
                " AND " + REG_N_MEM_PROG_GRP_TABLE + "." + GRP_LAY_R2_LIST_CODE_COL + " = '" + grpLayR2Code + "'" +
                " AND " + REG_N_MEM_PROG_GRP_TABLE + "." + GRP_LAY_R3_LIST_CODE_COL + " = '" + grpLayR3Code + "'" +
                " AND " +
                " regAss." + LAY_R1_LIST_CODE_COL + " || \"\" || " +
                " regAss." + LAY_R2_LIST_CODE_COL + " || \"\" || " +
                " regAss." + LAY_R3_LIST_CODE_COL + " || \"\" || " +
                " regAss." + LAY_R4_LIST_CODE_COL + " || \"\" || " +
                " regAss." + HHID_COL + " || \"\" || " +
                REGISTRATION_MEMBER_TABLE + "." + HH_MEM_ID
                + " LIKE '%" + memId + "%' " +
                " GROUP BY " + REGISTRATION_MEMBER_TABLE + "." + HHID_COL +
                //  " , "+ REGISTRATION_MEMBER_TABLE+"."+HH_MEM_ID+
                "  ORDER BY " + REGISTRATION_MEMBER_TABLE + "." + HHID_COL + " DESC, RegMembers.RegisterID DESC";
        //  " , "+ REGISTRATION_MEMBER_TABLE+"."+HH_MEM_ID+ "  ORDER BY "+ REGISTRATION_MEMBER_TABLE+"."+HHID_COL+" DESC, RegMembers.RegisterID DESC";


    }

    /**
     * this too much wrong
     *
     * @param country     Country Code
     * @param donor       donor Code
     * @param award       award Code
     * @param program     program Code
     * @param srvCode     Service Code
     * @param opCode      operation Code
     * @param opMonthCode operation Month Code
     * @param memId       member Id
     * @param grpCode     Group Code
     * @return Sql String of member list for service
     */


    public static String getRptMemberServiceList_sql(String country, String donor, String award, String program, String srvCode,
                                                     String opCode, String opMonthCode, String memId, String grpCode, String distFlag) {
        String getMemberName;
        if (country.equals("0004")) {
            getMemberName = REGISTRATION_MEMBER_TABLE + "." + MEM_NAME_FIRST_COL +
                    "|| ' ' || " + REGISTRATION_MEMBER_TABLE + "." + MEM_NAME_MIDDLE_COL +
                    "|| ' ' || " + REGISTRATION_MEMBER_TABLE + "." + MEM_NAME_LAST_COL;

        } else
            getMemberName = SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.MEM_NAME_COL;


        return "SELECT  " +
                REGISTRATION_MEMBER_TABLE + "." + HHID_COL + "," +
                REGISTRATION_TABLE + "." + PNAME_COL + "," +
                REGISTRATION_MEMBER_TABLE + "." + HH_MEM_ID + ", " +
                getMemberName + " AS memName ," +
                REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.SEX_COL + "," +
                REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.MEM_AGE + "," +
                REG_N_ASSIGN_PROG_SRV_TABLE + "." + COUNTRY_CODE_COL + "," +
                REG_N_ASSIGN_PROG_SRV_TABLE + "." + DONOR_CODE_COL + "," +
                REG_N_ASSIGN_PROG_SRV_TABLE + "." + AWARD_CODE_COL + "," +
                REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R1_LIST_CODE_COL + "," +
                REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R2_LIST_CODE_COL + "," +
                REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R3_LIST_CODE_COL + "," +
                REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R4_LIST_CODE_COL + "," +
                REG_N_ASSIGN_PROG_SRV_TABLE + "." + HHID_COL + "," +
                REG_N_ASSIGN_PROG_SRV_TABLE + "." + PROGRAM_CODE_COL + "," +
                REG_N_ASSIGN_PROG_SRV_TABLE + "." + SERVICE_CODE_COL + "," +


                "( " + dbo_Get_SrvMemCount(opMonthCode, distFlag, REG_N_ASSIGN_PROG_SRV_TABLE) + " ) AS SrvRecieved ," +
                REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R1_LIST_CODE_COL + " || \"\" || " +
                REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R2_LIST_CODE_COL + " || \"\" || " +
                REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R3_LIST_CODE_COL + " || \"\" || " +
                REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R4_LIST_CODE_COL + " || \"\" || " +
                REG_N_ASSIGN_PROG_SRV_TABLE + "." + HHID_COL + " || \"\" || " +
                REGISTRATION_MEMBER_TABLE + "." + HH_MEM_ID + " AS NewID " +
                /** Add new string  id show ..*/

                "FROM " +
                REG_N_ASSIGN_PROG_SRV_TABLE +
                " INNER JOIN " + REGISTRATION_MEMBER_TABLE + " ON " +
                REG_N_ASSIGN_PROG_SRV_TABLE + "." + COUNTRY_CODE_COL + " = " + REGISTRATION_MEMBER_TABLE + "." + COUNTRY_CODE_COL
                + " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R1_LIST_CODE_COL + " = " + REGISTRATION_MEMBER_TABLE + "." + DISTRICT_NAME_COL
                + " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R2_LIST_CODE_COL + " = " + REGISTRATION_MEMBER_TABLE + "." + UPZILLA_NAME_COL
                + " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R3_LIST_CODE_COL + " = " + REGISTRATION_MEMBER_TABLE + "." + UNITE_NAME_COL
                + " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R4_LIST_CODE_COL + " = " + REGISTRATION_MEMBER_TABLE + "." + VILLAGE_NAME_COL
                + " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + HHID_COL + " = " + REGISTRATION_MEMBER_TABLE + "." + HHID_COL
                + " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + HH_MEM_ID + " = " + REGISTRATION_MEMBER_TABLE + "." + HH_MEM_ID
                +//" AND "+
                    /*
                    REG_N_ASSIGN_PROG_SRV_TABLE+"."+LAY_R4_LIST_CODE_COL+" = "+REGISTRATION_MEMBER_TABLE+"."+VILLAGE_NAME_COL+" AND "+
                    REG_N_ASSIGN_PROG_SRV_TABLE+"."+HHID_COL+" = "+REGISTRATION_MEMBER_TABLE+"."+HHID_COL+" AND "+
                    REG_N_ASSIGN_PROG_SRV_TABLE+"."+HH_MEM_ID+" = "+REGISTRATION_MEMBER_TABLE+"."+HH_MEM_ID+*/

                " INNER JOIN " +
                REGISTRATION_TABLE + " ON " +
                REG_N_ASSIGN_PROG_SRV_TABLE + "." + COUNTRY_CODE_COL + " = " + REGISTRATION_TABLE + "." + COUNTRY_CODE_COL
                + " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R1_LIST_CODE_COL + " = " + REGISTRATION_TABLE + "." + DISTRICT_NAME_COL
                + " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R2_LIST_CODE_COL + " = " + REGISTRATION_TABLE + "." + UPZILLA_NAME_COL
                + " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R3_LIST_CODE_COL + " = " + REGISTRATION_TABLE + "." + UNITE_NAME_COL
                + " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R4_LIST_CODE_COL + " = " + REGISTRATION_TABLE + "." + VILLAGE_NAME_COL
                + " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + HHID_COL + " = " + REGISTRATION_TABLE + "." + PID_COL
               /*     REGISTRATION_MEMBER_TABLE+"."+VILLAGE_NAME_COL+" = "+REGISTRATION_TABLE+"."+VILLAGE_NAME_COL+" AND "+
                    REGISTRATION_MEMBER_TABLE+"."+HHID_COL+" = "+REGISTRATION_TABLE+"."+PID_COL +*/

             /* for test */ + " INNER JOIN " +
                REG_N_MEM_PROG_GRP_TABLE + " ON " +

                REG_N_ASSIGN_PROG_SRV_TABLE + "." + COUNTRY_CODE_COL + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + COUNTRY_CODE_COL
                + " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R1_LIST_CODE_COL + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + LAY_R1_LIST_CODE_COL
                + " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R2_LIST_CODE_COL + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + LAY_R2_LIST_CODE_COL
                + " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R3_LIST_CODE_COL + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + LAY_R3_LIST_CODE_COL
                + " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + HHID_COL + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + HHID_COL
                + " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + HH_MEM_ID + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + HH_MEM_ID
                + " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + DONOR_CODE_COL + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + DONOR_CODE_COL
                + " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + AWARD_CODE_COL + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + AWARD_CODE_COL
                + " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + PROGRAM_CODE_COL + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + PROGRAM_CODE_COL
                + " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + SERVICE_CODE_COL + " = " + REG_N_MEM_PROG_GRP_TABLE + "." + SERVICE_CODE_COL
                + " AND " + REG_N_MEM_PROG_GRP_TABLE + "." + GROUP_CODE_COL + " = '" + grpCode + "'"

                + " WHERE " +
                REG_N_ASSIGN_PROG_SRV_TABLE + "." + COUNTRY_CODE_COL + " = '" + country + "'  AND " +
                REG_N_ASSIGN_PROG_SRV_TABLE + "." + DONOR_CODE_COL + " = '" + donor + "'  AND " +
                REG_N_ASSIGN_PROG_SRV_TABLE + "." + AWARD_CODE_COL + " = '" + award + "'  AND " +
                REG_N_ASSIGN_PROG_SRV_TABLE + "." + PROGRAM_CODE_COL + " = '" + program + "'  AND " +
                REG_N_ASSIGN_PROG_SRV_TABLE + "." + SERVICE_CODE_COL + " = '" + srvCode + "' AND " +
                /**  id Searching  with 15 digit*/
                REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R1_LIST_CODE_COL + " || \"\" || " +
                REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R2_LIST_CODE_COL + " || \"\" || " +
                REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R3_LIST_CODE_COL + " || \"\" || " +
                REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R4_LIST_CODE_COL + " || \"\" || " +
                REG_N_ASSIGN_PROG_SRV_TABLE + "." + HHID_COL + " || \"\" || " +
                REGISTRATION_MEMBER_TABLE + "." + HH_MEM_ID
                + " LIKE '%" + memId + "%' " +
                " GROUP BY " + REGISTRATION_MEMBER_TABLE + "." + HHID_COL +
                //  " , "+ REGISTRATION_MEMBER_TABLE+"."+HH_MEM_ID+
                "  ORDER BY " + REGISTRATION_MEMBER_TABLE + "." + HHID_COL + " DESC, RegMembers.RegisterID DESC";
        //  " , "+ REGISTRATION_MEMBER_TABLE+"."+HH_MEM_ID+ "  ORDER BY "+ REGISTRATION_MEMBER_TABLE+"."+HHID_COL+" DESC, RegMembers.RegisterID DESC";


    }

    public static String getAddressQuery(String countryCode, String districtCode, String upzellaCode, String unitCode, String villageCode) {
        return " WHERE "
                + COUNTRY_CODE_COL + " = '" + countryCode + "' AND "
                + LAY_R1_LIST_CODE_COL + " = '" + districtCode + "' AND "
                + LAY_R2_LIST_CODE_COL + " = '" + upzellaCode + "' AND "
                + LAY_R3_LIST_CODE_COL + " = '" + unitCode + "' AND "
                + LAY_R4_LIST_CODE_COL + " = '" + villageCode + "'";

        /**
         * " SELECT "+SQLiteHandler.REGN_ADDRESS_LOOKUP_CODE_COL+" , "+ SQLiteHandler.REGN_ADDRESS_LOOKUP_NAME_COL
         +" FROM "+SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE
         */
    }

    /**
     * To show distribution table
     */

    public static String getDistributionGridShowData(String countryCode, String donorCode, String awardCode, String progCode, String serviceOpMonthCode, String fdpCode, String memberId) {
        return "SELECT "
                + SQLiteHandler.SERVICE_TABLE + "." + COUNTRY_CODE_COL + " AS country , "
                + SQLiteHandler.SERVICE_TABLE + "." + DONOR_CODE_COL + " AS donor , "
                + SQLiteHandler.SERVICE_TABLE + "." + AWARD_CODE_COL + " AS award , "
                + SQLiteHandler.SERVICE_TABLE + "." + LAY_R1_LIST_CODE_COL + " AS district , "
                + SQLiteHandler.SERVICE_TABLE + "." + LAY_R2_LIST_CODE_COL + " AS upzella , "
                + SQLiteHandler.SERVICE_TABLE + "." + LAY_R3_LIST_CODE_COL + " AS unite, "
                + SQLiteHandler.SERVICE_TABLE + "." + LAY_R4_LIST_CODE_COL + " AS village, "
                + SQLiteHandler.SERVICE_TABLE + "." + PROGRAM_CODE_COL + " AS program , "
                + SQLiteHandler.SERVICE_TABLE + "." + SERVICE_CODE_COL + " AS service , "
                + SQLiteHandler.SERVICE_MASTER_TABLE + "." + SQLiteHandler.SERVICE_SHORT_NAME_COL + " AS srvName , "

                + SQLiteHandler.SERVICE_TABLE + "." + LAY_R1_LIST_CODE_COL + " || "
                + SQLiteHandler.SERVICE_TABLE + "." + LAY_R2_LIST_CODE_COL + " || "
                + SQLiteHandler.SERVICE_TABLE + "." + LAY_R3_LIST_CODE_COL + " || "
                + SQLiteHandler.SERVICE_TABLE + "." + LAY_R4_LIST_CODE_COL + " || "
                + SQLiteHandler.SERVICE_TABLE + "." + HHID_COL + " AS HHID , "

                + SQLiteHandler.SERVICE_TABLE + "." + LAY_R1_LIST_CODE_COL + " || "
                + SQLiteHandler.SERVICE_TABLE + "." + LAY_R2_LIST_CODE_COL + " || "
                + SQLiteHandler.SERVICE_TABLE + "." + LAY_R3_LIST_CODE_COL + " || "
                + SQLiteHandler.SERVICE_TABLE + "." + LAY_R4_LIST_CODE_COL + " || "
                + SQLiteHandler.SERVICE_TABLE + "." + HHID_COL + " || "
                + SQLiteHandler.SERVICE_TABLE + "." + HH_MEM_ID + " AS MEMBERID , "
                + REGISTRATION_TABLE + "." + SQLiteHandler.PNAME_COL + " AS HhName , "
                + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.MEM_NAME_COL + " AS MemName , "
                + SQLiteHandler.SERVICE_TABLE + "." + OP_MONTH_CODE_COL + " , "
                + SQLiteHandler.SERVICE_TABLE + "." + SQLiteHandler.SERVICE_CENTER_CODE_COL + " ,  "
                + SQLiteHandler.SERVICE_TABLE + "." + SQLiteHandler.WORK_DAY_COL + " AS wd  "

                // can n't sub query or join because of where condition
                // + "  "+getDistributionStatusFromDistributionTableQuery()+ " )   AS DistStatus "
                // + " CASE WHEN " + SQLiteHandler.DISTRIBUTION_TABLE + "." + SQLiteHandler.DISTRIBUTION_STATUS_COL + " IS NULL THEN '-' ELSE 'R'  AS DistStatus"

                //     --,COUNT(Service.ServiceSL)

                + " FROM " + SQLiteHandler.SERVICE_TABLE + "  INNER JOIN " + SQLiteHandler.COUNTRY_PROGRAM_TABLE + " ON  "
                + SQLiteHandler.SERVICE_TABLE + "." + COUNTRY_CODE_COL + " = " + SQLiteHandler.COUNTRY_PROGRAM_TABLE + "." + COUNTRY_CODE_COL
                + " AND " + SQLiteHandler.SERVICE_TABLE + "." + DONOR_CODE_COL + " = " + SQLiteHandler.COUNTRY_PROGRAM_TABLE + "." + DONOR_CODE_COL
                + " AND " + SQLiteHandler.SERVICE_TABLE + "." + AWARD_CODE_COL + " = " + SQLiteHandler.COUNTRY_PROGRAM_TABLE + "." + AWARD_CODE_COL
                + " AND " + SQLiteHandler.SERVICE_TABLE + "." + PROGRAM_CODE_COL + " = " + SQLiteHandler.COUNTRY_PROGRAM_TABLE + "." + PROGRAM_CODE_COL
                + " AND " + SQLiteHandler.SERVICE_TABLE + "." + SERVICE_CODE_COL + " = " + SQLiteHandler.COUNTRY_PROGRAM_TABLE + "." + SERVICE_CODE_COL
                + " INNER JOIN " + REGISTRATION_TABLE + " ON "
                + SQLiteHandler.SERVICE_TABLE + "." + COUNTRY_CODE_COL + " || "
                + SQLiteHandler.SERVICE_TABLE + "." + LAY_R1_LIST_CODE_COL + " || "
                + SQLiteHandler.SERVICE_TABLE + "." + LAY_R2_LIST_CODE_COL + " || "
                + SQLiteHandler.SERVICE_TABLE + "." + LAY_R3_LIST_CODE_COL + " || "
                + SQLiteHandler.SERVICE_TABLE + "." + LAY_R4_LIST_CODE_COL + " || "
                + SQLiteHandler.SERVICE_TABLE + "." + HHID_COL + " = "

                + REGISTRATION_TABLE + "." + COUNTRY_CODE_COL + " || "
                + REGISTRATION_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL + " || "
                + REGISTRATION_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL + " || "
                + REGISTRATION_TABLE + "." + UNITE_NAME_COL + " || "
                + REGISTRATION_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL + " || "
                + REGISTRATION_TABLE + "." + SQLiteHandler.PID_COL
                + " INNER JOIN " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + " ON "
                + SQLiteHandler.SERVICE_TABLE + "." + COUNTRY_CODE_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + COUNTRY_CODE_COL
                + " AND " + SQLiteHandler.SERVICE_TABLE + "." + LAY_R1_LIST_CODE_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL
                + " AND " + SQLiteHandler.SERVICE_TABLE + "." + LAY_R2_LIST_CODE_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL
                + " AND " + SQLiteHandler.SERVICE_TABLE + "." + LAY_R3_LIST_CODE_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + UNITE_NAME_COL
                + " AND " + SQLiteHandler.SERVICE_TABLE + "." + LAY_R4_LIST_CODE_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL
                + " AND " + SQLiteHandler.SERVICE_TABLE + "." + HHID_COL + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HHID_COL
                + " AND " + SQLiteHandler.SERVICE_TABLE + "." + HH_MEM_ID + " = " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HH_MEM_ID
                + " INNER JOIN " + SQLiteHandler.SERVICE_MASTER_TABLE
                + " ON " + SQLiteHandler.SERVICE_TABLE + "." + PROGRAM_CODE_COL + " = " + SQLiteHandler.SERVICE_MASTER_TABLE + "." + PROGRAM_CODE_COL
                + " AND " + SQLiteHandler.SERVICE_TABLE + "." + SERVICE_CODE_COL + " = " + SQLiteHandler.SERVICE_MASTER_TABLE + "." + SERVICE_CODE_COL
               /* + " LEFT JOIN " + SQLiteHandler.DISTRIBUTION_TABLE + " ON "
                + SQLiteHandler.DISTRIBUTION_TABLE + "." + SQLiteHandler.COUNTRY_CODE_COL + " = '" + countryCode + "' "
                + " AND " + SQLiteHandler.DISTRIBUTION_TABLE + "." + SQLiteHandler.DONOR_CODE_COL + " = '" + donorCode + "' "
                + " AND " + SQLiteHandler.DISTRIBUTION_TABLE + "." + SQLiteHandler.AWARD_CODE_COL + " = '" + awardCode + "' "
                + " AND " + SQLiteHandler.DISTRIBUTION_TABLE + "." + SQLiteHandler.LAY_R1_LIST_CODE_COL    + " = " +SQLiteHandler.SERVICE_TABLE + "." + SQLiteHandler.LAY_R1_LIST_CODE_COL
                + " AND " + SQLiteHandler.DISTRIBUTION_TABLE + "." + SQLiteHandler.LAY_R2_LIST_CODE_COL           + " = " +SQLiteHandler.SERVICE_TABLE + "." + SQLiteHandler.LAY_R2_LIST_CODE_COL
                + " AND " + SQLiteHandler.DISTRIBUTION_TABLE + "." + SQLiteHandler.LAY_R3_LIST_CODE_COL            + " = " +SQLiteHandler.SERVICE_TABLE + "." + SQLiteHandler.LAY_R3_LIST_CODE_COL
                + " AND " + SQLiteHandler.DISTRIBUTION_TABLE + "." + SQLiteHandler.LAY_R4_LIST_CODE_COL            + " = " +SQLiteHandler.SERVICE_TABLE + "." + SQLiteHandler.LAY_R4_LIST_CODE_COL
                + " AND " + SQLiteHandler.DISTRIBUTION_TABLE + "." + SQLiteHandler.PROGRAM_CODE_COL     + " = " +SQLiteHandler.SERVICE_TABLE + "." + SQLiteHandler.PROGRAM_CODE_COL
                + " AND " + SQLiteHandler.DISTRIBUTION_TABLE + "." + SQLiteHandler.SERVICE_CODE_COL     + " = " +SQLiteHandler.SERVICE_TABLE + "." + SQLiteHandler.SERVICE_CODE_COL
                + " AND " + SQLiteHandler.DISTRIBUTION_TABLE + "." + SQLiteHandler.OP_MONTH_CODE_COL    + " = '" +distributionOpMonthCode +"' "
                + " AND " + SQLiteHandler.DISTRIBUTION_TABLE + "." + SQLiteHandler.FDP_CODE_COL         + " = '" + fdpCode + "' "
                + " AND " + SQLiteHandler.DISTRIBUTION_TABLE + "." + SQLiteHandler.MEM_ID_15_D_COL + " = " +
*/
                + " WHERE (" + SQLiteHandler.COUNTRY_PROGRAM_TABLE + "." + SQLiteHandler.FOOD_FLAG + " = '1' OR "
                + SQLiteHandler.COUNTRY_PROGRAM_TABLE + "." + SQLiteHandler.NON_FOOD_FLAG + " = '1' OR "
                + SQLiteHandler.COUNTRY_PROGRAM_TABLE + "." + SQLiteHandler.CASH_FLAG + " = '1' OR "
                + SQLiteHandler.COUNTRY_PROGRAM_TABLE + "." + SQLiteHandler.VOUCHER_FLAG + " = '1'" +

                " ) "
                + " AND " + SQLiteHandler.SERVICE_TABLE + "." + COUNTRY_CODE_COL + " = '" + countryCode + "' "
                + " AND " + SQLiteHandler.SERVICE_TABLE + "." + DONOR_CODE_COL + " = '" + donorCode + "' "
                + " AND " + SQLiteHandler.SERVICE_TABLE + "." + AWARD_CODE_COL + " = '" + awardCode + "' "
                + " AND " + SQLiteHandler.SERVICE_TABLE + "." + PROGRAM_CODE_COL + " = '" + progCode + "' "
                + " AND " + SQLiteHandler.SERVICE_TABLE + "." + OP_MONTH_CODE_COL + " = '" + serviceOpMonthCode + "' "

                + " AND (" + SQLiteHandler.SERVICE_TABLE + "." + SQLiteHandler.DISTRIBUTION_STATUS_COL + " IN ( 'S', 'P' )) "
                + " AND  " + SQLiteHandler.SERVICE_TABLE + "." + SQLiteHandler.DIST_DATE_COL + " = 'null' "

                + " AND (" + SQLiteHandler.SERVICE_TABLE + "." + SQLiteHandler.SERVICE_CENTER_CODE_COL + " IN ( SELECT "
                + SQLiteHandler.SERVICE_CENTER_TABLE + "." + SQLiteHandler.SERVICE_CENTER_CODE_COL + " FROM "
                + SQLiteHandler.SERVICE_CENTER_TABLE + " WHERE " + SQLiteHandler.SERVICE_CENTER_TABLE + "." + COUNTRY_CODE_COL + " = '" + countryCode + "' "
                + " AND " + SQLiteHandler.SERVICE_CENTER_TABLE + "." + FDP_CODE_COL + " = '" + fdpCode + "' ))"
                /**  id Searching  with 15 digit*/
                + " AND " + SQLiteHandler.SERVICE_TABLE + "." + LAY_R1_LIST_CODE_COL + " || \"\" || " +
                SQLiteHandler.SERVICE_TABLE + "." + LAY_R2_LIST_CODE_COL + " || \"\" || " +
                SQLiteHandler.SERVICE_TABLE + "." + LAY_R3_LIST_CODE_COL + " || \"\" || " +
                SQLiteHandler.SERVICE_TABLE + "." + LAY_R4_LIST_CODE_COL + " || \"\" || " +
                SQLiteHandler.SERVICE_TABLE + "." + HHID_COL + " || \"\" || " +
                SQLiteHandler.SERVICE_TABLE + "." + HH_MEM_ID
                + " LIKE '%" + memberId + "%' "

                ;


    }

    public static String getDistributionStatusFromDistributionTableQuery(String countryCode, String donorCode, String awardCode, String districtCode, String upzillaCode, String uniteCode, String villageCode, String programCode, String srviceCode, String distMonthCode, String fdpCode, final String distFlag, String id) {
        String sql = "SELECT CASE WHEN " + SQLiteHandler.DISTRIBUTION_STATUS_COL + " IS NULL THEN '-' ELSE " + SQLiteHandler.DISTRIBUTION_STATUS_COL + " END   AS " + SQLiteHandler.DISTRIBUTION_STATUS_COL
                + " FROM " + SQLiteHandler.DISTRIBUTION_TABLE
                + " WHERE "
                + SQLiteHandler.DISTRIBUTION_TABLE + "." + COUNTRY_CODE_COL + " = '" + countryCode + "' "
                + " AND " + SQLiteHandler.DISTRIBUTION_TABLE + "." + DONOR_CODE_COL + " = '" + donorCode + "' "
                + " AND " + SQLiteHandler.DISTRIBUTION_TABLE + "." + AWARD_CODE_COL + " = '" + awardCode + "' "
                + " AND " + SQLiteHandler.DISTRIBUTION_TABLE + "." + LAY_R1_LIST_CODE_COL + " = '" + districtCode + "' "
                + " AND " + SQLiteHandler.DISTRIBUTION_TABLE + "." + LAY_R2_LIST_CODE_COL + " = '" + upzillaCode + "' "
                + " AND " + SQLiteHandler.DISTRIBUTION_TABLE + "." + LAY_R3_LIST_CODE_COL + " = '" + uniteCode + "' "
                + " AND " + SQLiteHandler.DISTRIBUTION_TABLE + "." + LAY_R4_LIST_CODE_COL + " = '" + villageCode + "' "
                + " AND " + SQLiteHandler.DISTRIBUTION_TABLE + "." + PROGRAM_CODE_COL + " = '" + programCode + "' "
                + " AND " + SQLiteHandler.DISTRIBUTION_TABLE + "." + SERVICE_CODE_COL + " = '" + srviceCode + "' "
                + " AND " + SQLiteHandler.DISTRIBUTION_TABLE + "." + OP_MONTH_CODE_COL + " = '" + distMonthCode + "' "
                + " AND " + SQLiteHandler.DISTRIBUTION_TABLE + "." + FDP_CODE_COL + " = '" + fdpCode + "' "
                + " AND " + SQLiteHandler.DISTRIBUTION_TABLE + "." + SQLiteHandler.DIST_FLAG_COL + " = '" + distFlag + "' "
                + " AND " + SQLiteHandler.DISTRIBUTION_TABLE + "." + MEM_ID_15_D_COL + " = '" + id + "' ";
        Log.d("All_1", "sql:" + sql);
        return sql;
    }

    public static String getSingleHouseHoldDataForLiberiaQuery(String countryCode, String districtCode, String upzellaCode, String unitCode, String villageCode, String houseHoldId) {
        return " SELECT " +
               /* SQLiteHandler.COUNTRY_CODE_COL + " , " +
                SQLiteHandler.DISTRICT_NAME_COL + " , " +
                SQLiteHandler.UPZILLA_NAME_COL + " , " +
                SQLiteHandler.UNITE_NAME_COL + " , " +
                SQLiteHandler.VILLAGE_NAME_COL + " , " +
                SQLiteHandler.PID_COL + " , " +*/
                SQLiteHandler.REG_DATE_COL + " , " +
                SQLiteHandler.PNAME_COL + " , " +
                SQLiteHandler.HOUSE_HOLD_TYPE_CODE_COL + " , " +// also retrives House hold catagories string
                SQLiteHandler.HOUSE_HOLD_CATEGORY_TABLE + "." + SQLiteHandler.CATEGORY_NAME_COL + " AS HHType, " +// also retrives House hold catagories string
                SQLiteHandler.LT2YRS_M_COL + " , " +
                SQLiteHandler.LT2YRS_F_COL + " , " +
                SQLiteHandler.M_2TO5YRS_COL + " , " +
                SQLiteHandler.F_2TO5YRS_COL + " , " +
                SQLiteHandler.M_6TO12YRS_COL + " , " +
                SQLiteHandler.F_6TO12YRS_COL + " , " +
                SQLiteHandler.M_13TO17YRS_COL + " , " +
                SQLiteHandler.F_13TO17YRS_COL + " , " +
                SQLiteHandler.ORPHN_LT18YRS_M_COL + " , " +
                SQLiteHandler.ORPHN_LT18YRS_F_COL + " , " +
                SQLiteHandler.ADLT_18TO59_M_COL + " , " +
                SQLiteHandler.ADLT_18TO59_F_COL + " , " +
                SQLiteHandler.ELD_60P_M_COL + " , " +
                SQLiteHandler.ELD_60P_F_COL + " , " +
                SQLiteHandler.PLW_NO_COL + " , " +
                SQLiteHandler.CHRO_ILL_NO_COL + " , " +

                SQLiteHandler.DECEASED_CONTRACT_EBOLA_COL + " , " +
                SQLiteHandler.EXTRA_CHILD_CAUSE_EBOLA_COL + " , " +
                SQLiteHandler.EXTRA_ELDERLY_CAUSE_EBOLA_COL + " , " +
                SQLiteHandler.EXTRA_CHRONICALLY_ILL_CAUSE_EBOLA_COL + " , " +


                /////////////////////
                SQLiteHandler.BRF_COUNT_CATTLE_COL + " , " +
                SQLiteHandler.BRF_VALUE_CATTLE_COL + " , " +
                SQLiteHandler.AFT_COUNT_CATTLE_COL + " , " +
                SQLiteHandler.AFT_VALUE_CATTLE_COL + " , " +
                SQLiteHandler.BRF_COUNT_SGOATS_COL + " , " +
                SQLiteHandler.BRF_VALUE_SGOATS_COL + " , " +
                SQLiteHandler.AFT_COUNT_SGOATS_COL + " , " +
                SQLiteHandler.AFT_VALUE_SGOATS_COL + " , " +
                SQLiteHandler.BRF_COUNT_POULTRY_COL + " , " +
                SQLiteHandler.BRF_VALUE_POULTRY_COL + " , " +
                SQLiteHandler.AFT_COUNT_POULTRY_COL + " , " +
                SQLiteHandler.AFT_VALUE_POULTRY_COL + " , " +
                SQLiteHandler.BRF_COUNT_OTHER_COL + " , " +
                SQLiteHandler.BRF_VALUE_OTHER_COL + " , " +
                SQLiteHandler.AFT_COUNT_OTHER_COL + " , " +
                SQLiteHandler.AFT_VALUE_OTHER_COL + " , " +
                SQLiteHandler.BRF_ACRE_CULTIVABLE_COL + " , " +
                SQLiteHandler.BRF_VALUE_CULTIVABLE_COL + " , " +
                SQLiteHandler.AFT_ACRE_CULTIVABLE_COL + " , " +
                SQLiteHandler.AFT_VALUE_CULTIVABLE_COL + " , " +
                SQLiteHandler.BRF_ACRE_NON_CULTIVABLE_COL + " , " +
                SQLiteHandler.BRF_VAL_NON_CULTIVABLE_COL + " , " +
                SQLiteHandler.AFT_ACRE_NON_CULTIVABLE + " , " +
                SQLiteHandler.AFT_VAL_NON_CULTIVABLE + " , " +
                SQLiteHandler.BRF_ACRE_ORCHARDS + " , " +
                SQLiteHandler.BRF_VAL_ORCHARDS + " , " +
                SQLiteHandler.AFT_ACRE_ORCHARDS + " , " +
                SQLiteHandler.AFT_VAL_ORCHARDS + " , " +
                SQLiteHandler.BRF_VAL_CROP + " , " +
                SQLiteHandler.AFT_VAL_CROP + " , " +
                SQLiteHandler.BRF_VAL_LIVESTOCK + " , " +
                SQLiteHandler.AFT_VAL_LIVESTOCK + " , " +
                SQLiteHandler.BRF_VAL_SMALL_BUSINESS + " , " +
                SQLiteHandler.AFT_VAL_SMALL_BUSINESS + " , " +
                SQLiteHandler.BRF_VAL_EMPLOYMENT + " , " +
                SQLiteHandler.AFT_VAL_EMPLOYMENT + " , " +
                SQLiteHandler.BRF_VAL_REMITTANCES + " , " +
                SQLiteHandler.AFT_VAL_REMITTANCES + " , " +
                SQLiteHandler.BRF_CNT_WAGEENR + " , " +
                SQLiteHandler.AFT_CNT_WAGEENR +
                " FROM  " + REGISTRATION_TABLE +
                " LEFT JOIN " + SQLiteHandler.HOUSE_HOLD_CATEGORY_TABLE
                + " ON " + SQLiteHandler.HOUSE_HOLD_CATEGORY_TABLE + "." + CATEGORY_CODE_COL + " = " + REGISTRATION_TABLE + "." + SQLiteHandler.HOUSE_HOLD_TYPE_CODE_COL + " " +
                " WHERE " + REGISTRATION_TABLE + "." +
                COUNTRY_CODE_COL + " = '" + countryCode + "' " +
                " AND " + SQLiteHandler.DISTRICT_NAME_COL + " = '" + districtCode + "' " +
                " AND " + SQLiteHandler.UPZILLA_NAME_COL + " = '" + upzellaCode + "' " +
                " AND " + UNITE_NAME_COL + " = '" + unitCode + "'" +
                " AND " + SQLiteHandler.VILLAGE_NAME_COL + " = '" + villageCode + "' " +
                " AND " + SQLiteHandler.PID_COL + " = '" + houseHoldId + "' ";
    }

    public static String getVillageNameWHERECondition(String countryCode, String districtCode, String upazillaCode, String unitCode, String villageCode) {
        return " WHERE " + COUNTRY_CODE_COL + " = '" + countryCode + "' AND "
                + LAY_R1_LIST_CODE_COL + " = '" + districtCode + "' AND "
                + LAY_R2_LIST_CODE_COL + " = '" + upazillaCode + "' AND "
                + LAY_R3_LIST_CODE_COL + " = '" + unitCode + "' AND "
                + LAY_R4_LIST_CODE_COL + " = '" + villageCode + "'  ";
    }

    public static String getProgramsNames_WHERE_Condition(String awardCode, String donorCode) {
        return " WHERE " + SQLiteHandler.COUNTRY_PROGRAM_TABLE + "." + AWARD_CODE_COL + "='" + awardCode + "'"
                + " AND " + SQLiteHandler.COUNTRY_PROGRAM_TABLE + "." + DONOR_CODE_COL + "='" + donorCode + "'";

    }

    /**
     * getDistProgramsNames_WHERE_Condition() is For Distribution table
     */
    public static String getDistProgramsNames_WHERE_Condition(String awardCode, String donorCode, String columnName) {
        return " WHERE " + SQLiteHandler.COUNTRY_PROGRAM_TABLE + "." + AWARD_CODE_COL + "='" + awardCode + "'"
                + " AND " + SQLiteHandler.COUNTRY_PROGRAM_TABLE + "." + DONOR_CODE_COL + "='" + donorCode + "'"
                + " AND " + SQLiteHandler.COUNTRY_PROGRAM_TABLE + "." + columnName + "='" + 1 + "'";

    }

    public static String getServiceMonths_WHERE_Service_Open_Condition(String countryCode) {
        return " WHERE " +
                COUNTRY_CODE_COL + " = '" + countryCode + "'"
                + " AND " + STATUS + " = '" + "A" + "' "
                + " AND " + SQLiteHandler.OPERATION_CODE_COL + " = '2' ";
    }

    public static String getServiceMonths_WHERE_Service_Close_Condition(String countryCode) {
        return " WHERE " +
                COUNTRY_CODE_COL + " = '" + countryCode + "'"
                + " AND " + STATUS + " = '" + "C" + "' "
                + " AND " + SQLiteHandler.OPERATION_CODE_COL + " = '2' "
                + " ORDER BY OpMonthID   DESC   LIMIT 1"
                ;


    }


    public static String getDistributionMonths_WHERE_Condition(String countryCode) {
        return " WHERE " +
                COUNTRY_CODE_COL + " = '" + countryCode + "'"
                + " AND " + STATUS + " = '" + "A" + "' "
                + " AND " + SQLiteHandler.OPERATION_CODE_COL + " = '3' "
                + " ORDER BY OpMonthID   DESC "
                + "        LIMIT 1";
    }

    public static String getCriteriaNames_WHERE_Condition(String awardCode, String donorCode, String programCode) {
        return " WHERE " + SQLiteHandler.COUNTRY_PROGRAM_TABLE + "." + AWARD_CODE_COL + "='" + awardCode + "'"
                + " AND " + SQLiteHandler.COUNTRY_PROGRAM_TABLE + "." + DONOR_CODE_COL + "='" + donorCode + "'"
                + " AND " + SQLiteHandler.COUNTRY_PROGRAM_TABLE + "." + PROGRAM_CODE_COL + "='" + programCode + "'";

    }

    public static String getFDPNames_Where_Condition(String idCountry, String laryR2) {
        return " WHERE " + SQLiteHandler.STAFF_FDP_ACCESS_TABLE + "." + COUNTRY_CODE + " = '" + idCountry + "'"
                + " AND " + SQLiteHandler.FDP_MASTER_TABLE + "." + LAY_R1_LIST_CODE_COL + " || " + SQLiteHandler.FDP_MASTER_TABLE + "." + LAY_R2_LIST_CODE_COL + " = '" + laryR2 + "' "
                + " AND " + SQLiteHandler.STAFF_FDP_ACCESS_TABLE + "." + SQLiteHandler.BTN_NEW_COL + " = '1' "
                + " AND " + SQLiteHandler.STAFF_FDP_ACCESS_TABLE + "." + SQLiteHandler.BTN_SAVE_COL + " = '1' "
                + " AND " + SQLiteHandler.STAFF_FDP_ACCESS_TABLE + "." + SQLiteHandler.BTN_DEL_COL + " = '1' "
                + "  GROUP BY " + SQLiteHandler.FDP_MASTER_TABLE + "." + SQLiteHandler.FDP_NAME_COL
                + "  ORDER BY " + SQLiteHandler.FDP_MASTER_TABLE + "." + SQLiteHandler.FDP_NAME_COL;
    }

    public static String checkDataExitsQueryInCT_TableAssignForLiberia(String countryCode, String districtCode, String upzellaCode, String unitCode, String villageCode, String houseHoldId, String memberID) {
        return "SELECT * FROM " + SQLiteHandler.REG_N_CT_TABLE
                + " WHERE " + COUNTRY_CODE_COL + " = '" + countryCode + "' "
                + " AND " + LAY_R1_LIST_CODE_COL + " = '" + districtCode + "' "
                + " AND " + LAY_R2_LIST_CODE_COL + " = '" + upzellaCode + "' "
                + " AND " + LAY_R3_LIST_CODE_COL + " = '" + unitCode + "'"
                + " AND " + LAY_R4_LIST_CODE_COL + " = '" + villageCode + "' "
                + " AND " + HHID_COL + " = '" + houseHoldId + "' "
                + " AND " + HH_MEM_ID + " = '" + memberID + "' ";

    }
   /* // havet use it

    public static String checkAssignedCriteriaQueryInCT_TableForLiberia(String columnName, String countryCode, String districtCode, String upzellaCode, String unitCode, String villageCode, String houseHoldId, String memberID) {
        return "SELECT " + columnName + " FROM " + SQLiteHandler.REG_N_CT_TABLE
                + " WHERE " + SQLiteHandler.COUNTRY_CODE_COL + " = '" + countryCode + "' "
                + " AND " + SQLiteHandler.LAY_R1_LIST_CODE_COL + " = '" + districtCode + "' "
                + " AND " + SQLiteHandler.LAY_R2_LIST_CODE_COL + " = '" + upzellaCode + "' "
                + " AND " + SQLiteHandler.LAY_R3_LIST_CODE_COL + " = '" + unitCode + "'"
                + " AND " + SQLiteHandler.LAY_R4_LIST_CODE_COL + " = '" + villageCode + "' "
                + " AND " + SQLiteHandler.HHID_COL + " = '" + houseHoldId + "' "
                + " AND " + SQLiteHandler.HH_MEM_ID + " = '" + memberID + "' ";

    }*/


    public static String getLastServiceDateQuery(ServiceDataModel service) {
        return " SELECT " + SQLiteHandler.SERVICE_DT_COL
                + " FROM " + SQLiteHandler.SERVICE_TABLE
                + " WHERE " + COUNTRY_CODE + " = '" + service.getC_code() + "'"
                + " AND " + DONOR_CODE_COL + " = '" + service.getDonor_code() + "'"
                + " AND " + AWARD_CODE_COL + " = '" + service.getAward_code() + "'"
                + " AND " + LAY_R1_LIST_CODE_COL + " = '" + service.getDistrictCode() + "'"
                + " AND " + LAY_R2_LIST_CODE_COL + " = '" + service.getUpazillaCode() + "'"
                + " AND " + LAY_R3_LIST_CODE_COL + " = '" + service.getUnitCode() + "'"
                + " AND " + LAY_R4_LIST_CODE_COL + " = '" + service.getVillageCode() + "'"
                + " AND " + HHID_COL + " = '" + service.getHHID() + "'"
                + " AND " + HH_MEM_ID + " = '" + service.getMemberId() + "'"
                + " AND " + PROGRAM_CODE_COL + " = '" + service.getProgram_code() + "'"
                + " AND " + SERVICE_CODE_COL + " = '" + service.getService_code() + "'"
                + " AND " + SQLiteHandler.OPERATION_CODE_COL + " = '" + service.getOpCode() + "'"
                + " AND " + OP_MONTH_CODE_COL + " = '" + service.getOpMontheCode() + "'" +
                "ORDER BY " + SQLiteHandler.SERVICE_SL_COL + " desc limit 1";
    }

    // todo: Analysis the Query to replace the Sub Query
    public static String getAssignCriteriaListSelectQuery(String countryCode, String donorCode, String awardCode, String programCode, String districtCode, String upzellaCode, String unitCode, String villageCode) {
        return "SELECT " + SERVICE_MASTER_TABLE + "." + SERVICE_SHORT_NAME_COL + " AS Criteria , "
                + SQLiteHandler.SERVICE_MASTER_TABLE + "." + PROGRAM_CODE_COL + " || '' || " + SERVICE_MASTER_TABLE + "." + SERVICE_CODE_COL + " AS IdCriteria ,  "
                + "( SELECT COUNT (*) FROM " + REG_N_ASSIGN_PROG_SRV_TABLE
                + " WHERE "
                + REG_N_ASSIGN_PROG_SRV_TABLE + "." + PROGRAM_CODE_COL + " = " + SERVICE_MASTER_TABLE + "." + PROGRAM_CODE_COL +
                " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + SERVICE_CODE_COL + " = " + SERVICE_MASTER_TABLE + "." + SERVICE_CODE_COL
                + " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + COUNTRY_CODE_COL + " = '" + countryCode + "' "
                + " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + DONOR_CODE_COL + " = '" + donorCode + "' "
                + " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + AWARD_CODE_COL + " = '" + awardCode + "' "
                + " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + PROGRAM_CODE_COL + " = '" + programCode + "' "
                + " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R1_LIST_CODE_COL + " = '" + districtCode + "' "
                + " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R2_LIST_CODE_COL + " = '" + upzellaCode + "' "
                + " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R3_LIST_CODE_COL + " = '" + unitCode + "' "
                + " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R4_LIST_CODE_COL + " = '" + villageCode + "' ) AS AssignCount "
                + " FROM " + REG_N_ASSIGN_PROG_SRV_TABLE
                //" JOIN "+SERVICE_CENTER_TABLE +" ON "+REG_N_ASSIGN_PROG_SRV_TABLE+"."+COUNTRY_CODE_COL +" = "+ SERVICE_CENTER_TABLE+"."+COUNTRY_CODE_COL+
                + " JOIN " + SERVICE_MASTER_TABLE + " ON "
                + REG_N_ASSIGN_PROG_SRV_TABLE + "." + PROGRAM_CODE_COL + " = " + SERVICE_MASTER_TABLE + "." + PROGRAM_CODE_COL
                + " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + SERVICE_CODE_COL + " = " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + SERVICE_CODE_COL
                + " WHERE " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + COUNTRY_CODE_COL + " = '" + countryCode + "'"
                + " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R1_LIST_CODE_COL + " = '" + districtCode + "' "
                + " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R2_LIST_CODE_COL + " = '" + upzellaCode + "' "
                + " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R3_LIST_CODE_COL + " = '" + unitCode + "' "
                + " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R4_LIST_CODE_COL + " = '" + villageCode + "'"
                + " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + DONOR_CODE_COL + " = '" + donorCode + "'"
                + " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + AWARD_CODE_COL + " = '" + awardCode + "'"
                + " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + PROGRAM_CODE_COL + " = '" + programCode + "'"
                + " GROUP BY " + SERVICE_MASTER_TABLE + "." + SERVICE_SHORT_NAME_COL;

    }

    public static String doesMemberAssignedInDifferentServiceQuery(RegNAssgProgSrv regData) {
        return "SELECT " + HH_MEM_ID
                + " FROM " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE
                + " WHERE " + COUNTRY_CODE_COL + " = '" + regData.getCountryCode() + "'"
                + " AND " + LAY_R1_LIST_CODE_COL + " = '" + regData.getDistrictCode() + "'"
                + " AND " + LAY_R2_LIST_CODE_COL + " = '" + regData.getUpazillaCode() + "'"
                + " AND " + LAY_R3_LIST_CODE_COL + " = '" + regData.getUnitCode() + "'"
                + " AND " + LAY_R4_LIST_CODE_COL + " = '" + regData.getVillageCode() + "'"
                + " AND " + DONOR_CODE_COL + " = '" + regData.getDonorCode() + "'"
                + " AND " + AWARD_CODE_COL + " = '" + regData.getAwardCode() + "'"
                + " AND " + HHID_COL + " = '" + regData.getHhId() + "'"
                + " AND " + HH_MEM_ID + " = '" + regData.getMemberId() + "'"
                + " AND " + PROGRAM_CODE_COL + " = '" + regData.getProgramCode() + "'"
                + " AND " + SERVICE_CODE_COL + " != '" + regData.getServiceCode() + "'"
                + " AND (" + SQLiteHandler.GRD_CODE_COL + " = ( " + getGraduationDefaultActiveReason_Select_Query(regData.getProgramCode(), regData.getServiceCode()) + " ) )";
    }

    public static String getGraduationDefaultActiveReason_Select_Query(String prrogramCode, String serviceCode) {
        return "SELECT " + SQLiteHandler.GRD_CODE_COL + " FROM " + SQLiteHandler.REG_N_LUP_GRADUATION_TABLE +
                " WHERE  " + PROGRAM_CODE_COL + " = '" + prrogramCode + "'"
                + " AND " + SERVICE_CODE_COL + " = '" + serviceCode + "'"
                + " AND " + SQLiteHandler.DEFAULT_CAT_ACTIVE_COL + " = '" + YES + "'  ";
    }

    public static String getMemberDataFrom_RegNAssProgSrv_Query(GraduationGridDataModel member) {
        return "SELECT * FROM " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE +
                " WHERE  " + COUNTRY_CODE_COL + " = '" + member.getCountryCode() + "' "
                + " AND " + LAY_R1_LIST_CODE_COL + " = '" + member.getDistrictCode() + "' "
                + " AND " + LAY_R2_LIST_CODE_COL + " = '" + member.getUpazillaCode() + "' "
                + " AND " + LAY_R3_LIST_CODE_COL + " = '" + member.getUnitCode() + "' "
                + " AND " + LAY_R4_LIST_CODE_COL + " = '" + member.getVillageCode() + "' "
                + " AND " + HHID_COL + " = '" + member.getHh_id() + "' "
                + " AND " + HH_MEM_ID + " = '" + member.getMember_Id() + "' "
                + " AND " + DONOR_CODE_COL + " = '" + member.getDonor_code() + "' "
                + " AND " + AWARD_CODE_COL + " = '" + member.getAward_code() + "' "
                + " AND " + PROGRAM_CODE_COL + " = '" + member.getProgram_code() + "' "
                + " AND " + SERVICE_CODE_COL + " = '" + member.getService_code() + "'  ";
    }

    /**
     * AGR Table Check
     */
   /* public static String checkDataExitsQueryInAGR_TableAssignForMalwai(String countryCode, String districtCode, String upzellaCode, String unitCode, String villageCode, String houseHoldId, String memberID) {
        return "SELECT * FROM " + SQLiteHandler.REG_N_AGR_TABLE
                + " WHERE " + SQLiteHandler.COUNTRY_CODE_COL + " = '" + countryCode + "' "
                + " AND " + SQLiteHandler.LAY_R1_LIST_CODE_COL + " = '" + districtCode + "' "
                + " AND " + SQLiteHandler.LAY_R2_LIST_CODE_COL + " = '" + upzellaCode + "' "
                + " AND " + SQLiteHandler.LAY_R3_LIST_CODE_COL + " = '" + unitCode + "'"
                + " AND " + SQLiteHandler.LAY_R4_LIST_CODE_COL + " = '" + villageCode + "' "
                + " AND " + SQLiteHandler.HHID_COL + " = '" + houseHoldId + "' "
                + " AND " + SQLiteHandler.HH_MEM_ID + " = '" + memberID + "' ";
    }*/
    public static String checkDataExitsQueryInRegN_ARG_TableSQL(String countryCode, String districtCode, String upzellaCode, String unitCode, String villageCode, String houseHoldId, String memberID) {
        return "SELECT * FROM " + SQLiteHandler.REG_N_AGR_TABLE
                + " WHERE " + COUNTRY_CODE_COL + " = '" + countryCode + "' "
                + " AND " + LAY_R1_LIST_CODE_COL + " = '" + districtCode + "' "
                + " AND " + LAY_R2_LIST_CODE_COL + " = '" + upzellaCode + "' "
                + " AND " + LAY_R3_LIST_CODE_COL + " = '" + unitCode + "'"
                + " AND " + LAY_R4_LIST_CODE_COL + " = '" + villageCode + "' "
                + " AND " + HHID_COL + " = '" + houseHoldId + "' "
                + " AND " + HH_MEM_ID + " = '" + memberID + "' ";
    }


    public static String checkDataExitsQueryInRegN_FFA_TableSQL(String countryCode, String districtCode, String upzellaCode, String unitCode, String villageCode, String houseHoldId, String memberID) {
        return "SELECT * FROM " + SQLiteHandler.REG_N_FFA_TABLE
                + " WHERE " + COUNTRY_CODE_COL + " = '" + countryCode + "' "
                + " AND " + LAY_R1_LIST_CODE_COL + " = '" + districtCode + "' "
                + " AND " + LAY_R2_LIST_CODE_COL + " = '" + upzellaCode + "' "
                + " AND " + LAY_R3_LIST_CODE_COL + " = '" + unitCode + "'"
                + " AND " + LAY_R4_LIST_CODE_COL + " = '" + villageCode + "' "
                + " AND " + HHID_COL + " = '" + houseHoldId + "' "
                + " AND " + HH_MEM_ID + " = '" + memberID + "' ";
    }

    public static String checkDataExitsQueryInRegN__TableSQL(String tableName, String countryCode, String districtCode, String upzellaCode, String unitCode, String villageCode, String houseHoldId, String memberID) {
        return "SELECT * FROM " + tableName
                + " WHERE " + COUNTRY_CODE_COL + " = '" + countryCode + "' "
                + " AND " + LAY_R1_LIST_CODE_COL + " = '" + districtCode + "' "
                + " AND " + LAY_R2_LIST_CODE_COL + " = '" + upzellaCode + "' "
                + " AND " + LAY_R3_LIST_CODE_COL + " = '" + unitCode + "'"
                + " AND " + LAY_R4_LIST_CODE_COL + " = '" + villageCode + "' "
                + " AND " + HHID_COL + " = '" + houseHoldId + "' "
                + " AND " + HH_MEM_ID + " = '" + memberID + "' ";
    }
// havet use it


    public static String getAssignDataIfExitsInRegNFFA_table_sql(String countryCode, String districtCode, String upzellaCode, String unitCode, String villageCode, String houseHoldId, String memberID) {
        return " SELECT " + SQLiteHandler.CHILD_HEADED_COL
                + " , " + SQLiteHandler.ELDERLY_HEADED_COL
                + " , " + SQLiteHandler.CHRONICALLY_ILL_COL
                + " , " + SQLiteHandler.FEMALE_HEADED_COL
                + " , " + SQLiteHandler.CROP_FAILURE_COL
                + " , " + SQLiteHandler.CHILDREN_REC_SUPP_FEED_N_COL
                + " , " + SQLiteHandler.WILLINGNESS_COL + "  "

                + " FROM " + SQLiteHandler.REG_N_FFA_TABLE

                + " WHERE " + COUNTRY_CODE_COL + " = '" + countryCode + "' "
                + " AND " + LAY_R1_LIST_CODE_COL + " = '" + districtCode + "' "
                + " AND " + LAY_R2_LIST_CODE_COL + " = '" + upzellaCode + "' "
                + " AND " + LAY_R3_LIST_CODE_COL + " = '" + unitCode + "'"
                + " AND " + LAY_R4_LIST_CODE_COL + " = '" + villageCode + "' "
                + " AND " + HHID_COL + " = '" + houseHoldId + "' "
                + " AND " + HH_MEM_ID + " = '" + memberID + "' ";
    }

    /**
     * AGR Table Check
     */
    public static String checkDataExitsQueryInAGR_TableAssignForMalwai(String countryCode, String districtCode, String upzellaCode, String unitCode, String villageCode, String houseHoldId, String memberID, boolean impelmetedinMain) {
        return " SELECT " + SQLiteHandler.ELDERLY_YN_COL + " , "
                + SQLiteHandler.LAND_SIZE_COL + " , "
                + SQLiteHandler.DEPEND_ON_GANYU_COL + " , "
                + SQLiteHandler.WILLINGNESS_COL + " , "
                + SQLiteHandler.WINTER_CULTIVATION_COL + " , "
                + SQLiteHandler.VULNERABLE_HH_COL + " , "
                + SQLiteHandler.PLANTING_VALUE_CHAIN_CROP_COL + " , "
                + SQLiteHandler.REG_N_DAT_COL + " , "
                + SQLiteHandler.LUP_SRV_OPTION_LIST_TABLE + "." + SQLiteHandler.LUP_OPTION_NAME_COL + " AS vcCropStr ,"

                + SQLiteHandler.AG_INVC_COL + " , "
                + SQLiteHandler.AG_NASFAM_COL + " , "
                + SQLiteHandler.AG_CU_COL + ", "
                + SQLiteHandler.AG_OTHER_COL + " , "
                + SQLiteHandler.AG_L_S_GOAT_COL + " , "
                + SQLiteHandler.AG_L_S_CHICKEN_COL + " , "
                + SQLiteHandler.AG_L_S_PIGION_COL + " , "
                + SQLiteHandler.AG_L_S_OTHER_COL + "  "

                + " FROM " + SQLiteHandler.REG_N_AGR_TABLE
                + " LEFT JOIN " + SQLiteHandler.LUP_SRV_OPTION_LIST_TABLE
                + " ON " + SQLiteHandler.REG_N_AGR_TABLE + " . " + COUNTRY_CODE_COL + " = " + SQLiteHandler.LUP_SRV_OPTION_LIST_TABLE + " . " + COUNTRY_CODE_COL +
                " AND " + SQLiteHandler.PLANTING_VALUE_CHAIN_CROP_COL + " = " + SQLiteHandler.LUP_OPTION_CODE_COL
                + " WHERE " + SQLiteHandler.REG_N_AGR_TABLE + "." + COUNTRY_CODE_COL + " = '" + countryCode + "' "
                + " AND " + LAY_R1_LIST_CODE_COL + " = '" + districtCode + "' "
                + " AND " + LAY_R2_LIST_CODE_COL + " = '" + upzellaCode + "' "
                + " AND " + LAY_R3_LIST_CODE_COL + " = '" + unitCode + "'"
                + " AND " + LAY_R4_LIST_CODE_COL + " = '" + villageCode + "' "
                + " AND " + HHID_COL + " = '" + houseHoldId + "' "
                + " AND " + HH_MEM_ID + " = '" + memberID + "' ";


    }

    public static String get_RegNAssProgSrvRegistrationDateRangeSelectQuery(String cCode) {
        return "SELECT " + SQLiteHandler.USA_START_DATE + " , " + SQLiteHandler.USA_END_DATE + " FROM " + SQLiteHandler.OP_MONTH_TABLE +
                " WHERE " + COUNTRY_CODE_COL + " = '" + cCode + "' " +
                " AND " + SQLiteHandler.OPERATION_CODE_COL + " = '" + SQLiteHandler.REGISTRATION_OP_CODE + "' " +
                " AND " + STATUS + " = '" + SQLiteHandler.ACTIVE + "' ";

    }

/*    public static String getTotalListOfMemberRAssignedSummary_sql(String countryCode, String districtCode, String upzellaCode, String unionCode, String villageCode, String donorCode, String awardCord, String programCode, String srvCode) {


        String getMemberName;
        if (countryCode.equals("0004")) {
            getMemberName = SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.MEM_NAME_FIRST_COL +
                    "|| ' ' || " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.MEM_NAME_MIDDLE_COL +
                    "|| ' ' || " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.MEM_NAME_LAST_COL;

        } else
            getMemberName = SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.MEM_NAME_COL;
        return "SELECT " + "rgAss." + LAY_R1_LIST_CODE_COL
                + " || '' || " + "rgAss." + LAY_R2_LIST_CODE_COL
                + " || '' || " + "rgAss." + LAY_R3_LIST_CODE_COL
                + " || '' || " + "rgAss." + LAY_R4_LIST_CODE_COL
                + " || '' || " + "rgAss." + HHID_COL
                + " || '' || " + "rgAss." + HH_MEM_ID + " AS NewID "
                + ", " + getMemberName + "  AS memberName"
                + " , " + "rgAss." + REG_N_DAT_COL + " AS regDate "
                // + " || '' || " + "memGrp." + GROUP_CODE_COL
                + " , memGrp." + GROUP_CODE_COL
                + " , CASE WHEN cg." + GROUP_NAME_COL+" IS NULL  THEN '' ELSE cg." + GROUP_NAME_COL+ " END AS grpName "
                + " FROM " + REG_N_ASSIGN_PROG_SRV_TABLE + " AS rgAss "
                + " INNER JOIN " + REGISTRATION_MEMBER_TABLE
                + " ON " + "rgAss." + COUNTRY_CODE_COL + " = " + REGISTRATION_MEMBER_TABLE + "." + COUNTRY_CODE_COL +
                " AND " + "rgAss." + LAY_R1_LIST_CODE_COL + " = " + REGISTRATION_MEMBER_TABLE + "." + DISTRICT_NAME_COL +
                " AND " + "rgAss." + LAY_R2_LIST_CODE_COL + " = " + REGISTRATION_MEMBER_TABLE + "." + UPZILLA_NAME_COL +
                " AND " + "rgAss." + LAY_R3_LIST_CODE_COL + " = " + REGISTRATION_MEMBER_TABLE + "." + UNITE_NAME_COL +
                " AND " + "rgAss." + LAY_R4_LIST_CODE_COL + " = " + REGISTRATION_MEMBER_TABLE + "." + VILLAGE_NAME_COL +
                " AND " + "rgAss." + HHID_COL + " = " + REGISTRATION_MEMBER_TABLE + "." + HHID_COL +
                " AND " + "rgAss." + HH_MEM_ID + " = " + REGISTRATION_MEMBER_TABLE + "." + HH_MEM_ID + " " +
                      //   " JOIN "+SERVICE_MASTER_TABLE+" ON "+REG_N_ASSIGN_PROG_SRV_TABLE+"."+PROGRAM_CODE_COL+" = "+SERVICE_MASTER_TABLE+"."+PROGRAM_CODE_COL+
                 //" AND " +REG_N_ASSIGN_PROG_SRV_TABLE+"."+SERVICE_CODE_COL+" = "+SERVICE_MASTER_TABLE+"."+SERVICE_CODE_COL+

                " LEFT JOIN " + REG_N_MEM_PROG_GRP_TABLE + " AS memGrp "
                + " ON  memGrp." + COUNTRY_CODE_COL + " = " + " rgAss." + COUNTRY_CODE_COL
                + " AND memGrp." + LAY_R1_LIST_CODE_COL + " = " + " rgAss." + LAY_R1_LIST_CODE_COL
                + " AND memGrp." + LAY_R2_LIST_CODE_COL + " = " + " rgAss." + LAY_R2_LIST_CODE_COL
                + " AND memGrp." + LAY_R3_LIST_CODE_COL + " = " + " rgAss." + LAY_R3_LIST_CODE_COL
                + " AND memGrp." + LAY_R4_LIST_CODE_COL + " = " + " rgAss." + LAY_R4_LIST_CODE_COL
                + " AND memGrp." + DONOR_CODE_COL + " = " + " rgAss." + DONOR_CODE_COL
                + " AND memGrp." + AWARD_CODE_COL + " = " + " rgAss." + AWARD_CODE_COL
                + " AND memGrp." + HHID_COL + " = " + " rgAss." + HHID_COL
                + " AND memGrp." + HH_MEM_ID + " = " + " rgAss." + HH_MEM_ID
                + " AND memGrp." + PROGRAM_CODE_COL + " = " + " rgAss." + PROGRAM_CODE_COL
                + " AND memGrp." + SERVICE_CODE_COL + " = " + " rgAss." + SERVICE_CODE_COL +

                " LEFT JOIN " + COMMUNITY_GROUP_TABLE + " AS cg "
                + " ON  cg." + COUNTRY_CODE_COL + " = " + " rgAss." + COUNTRY_CODE_COL
                + " AND cg." + AWARD_CODE_COL + " = " + " rgAss." + AWARD_CODE_COL
                + " AND cg." + DONOR_CODE_COL + " = " + " rgAss." + DONOR_CODE_COL
                + " AND cg." + PROGRAM_CODE_COL + " = " + " rgAss." + PROGRAM_CODE_COL
                + " AND cg." + GROUP_CODE_COL + " = " + " memGrp." + GROUP_CODE_COL+

            //    + " AND cg." + GRP_LAY_R1_LIST_CODE_COL + " = " + " memGrp." + GRP_LAY_R1_LIST_CODE_COL
              //  + " AND cg." + GRP_LAY_R2_LIST_CODE_COL + " = " + " memGrp." + GRP_LAY_R2_LIST_CODE_COL
            //    + " AND cg." + GRP_LAY_R3_LIST_CODE_COL + " = " + " memGrp." + GRP_LAY_R3_LIST_CODE_COL +


                " WHERE " + "rgAss." + COUNTRY_CODE_COL + "= '" + countryCode + "'" +
                " AND " + "rgAss." + DONOR_CODE_COL + " = '" + donorCode + "'" +
                " AND " + "rgAss." + AWARD_CODE_COL + " = '" + awardCord + "'" +
                " AND " + "rgAss." + PROGRAM_CODE_COL + " = '" + programCode + "'" +
                " AND " + "rgAss." + SERVICE_CODE_COL + " = '" + srvCode + "'" +
                " AND " + "rgAss." + LAY_R1_LIST_CODE_COL + " = '" + districtCode + "'" +
                " AND " + "rgAss." + LAY_R2_LIST_CODE_COL + " = '" + upzellaCode + "'" +
                " AND " + "rgAss." + LAY_R3_LIST_CODE_COL + " = '" + unionCode + "'" +
                " AND " + "rgAss." + LAY_R4_LIST_CODE_COL + " = '" + villageCode + "'" +


                //  +
                " GROUP BY NewID" +//+ REG_N_ASSIGN_PROG_SRV_TABLE+"."+HHID_COL+
                " ORDER BY " + "rgAss." + HHID_COL +
                " , " + "rgAss." + HH_MEM_ID + "  ASC ";

        *//*return "SELECT " + SQLiteHandler.REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R1_LIST_CODE_COL
                + " || '' || " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R2_LIST_CODE_COL
                + " || '' || " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R3_LIST_CODE_COL
                + " || '' || " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R4_LIST_CODE_COL
                + " || '' || " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + HHID_COL
                + " || '' || " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + HH_MEM_ID + " AS NewID "
                + ", " + getMemberName + "  AS memberName"
                + " , " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + REG_N_DAT_COL + " AS regDate"
                + " FROM " + REG_N_ASSIGN_PROG_SRV_TABLE + " JOIN " + SQLiteHandler.REGISTRATION_MEMBER_TABLE
                + " ON " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + COUNTRY_CODE_COL + " = " + REGISTRATION_MEMBER_TABLE + "." + COUNTRY_CODE_COL +
                " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R1_LIST_CODE_COL + " = " + REGISTRATION_MEMBER_TABLE + "." + DISTRICT_NAME_COL +
                " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R2_LIST_CODE_COL + " = " + REGISTRATION_MEMBER_TABLE + "." + UPZILLA_NAME_COL +
                " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R3_LIST_CODE_COL + " = " + REGISTRATION_MEMBER_TABLE + "." + UNITE_NAME_COL +
                " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R4_LIST_CODE_COL + " = " + REGISTRATION_MEMBER_TABLE + "." + VILLAGE_NAME_COL +
                " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + HHID_COL + " = " + REGISTRATION_MEMBER_TABLE + "." + HHID_COL +
                " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + HH_MEM_ID + " = " + REGISTRATION_MEMBER_TABLE + "." + HH_MEM_ID + " " +
                      //   " JOIN "+SERVICE_MASTER_TABLE+" ON "+REG_N_ASSIGN_PROG_SRV_TABLE+"."+PROGRAM_CODE_COL+" = "+SERVICE_MASTER_TABLE+"."+PROGRAM_CODE_COL+
                 //" AND " +REG_N_ASSIGN_PROG_SRV_TABLE+"."+SERVICE_CODE_COL+" = "+SERVICE_MASTER_TABLE+"."+SERVICE_CODE_COL+
                " WHERE " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + COUNTRY_CODE_COL + "= '" + countryCode + "'" +
                " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + DONOR_CODE_COL + " = '" + donorCode + "'" +
                " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + AWARD_CODE_COL + " = '" + awardCord + "'" +
                " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + PROGRAM_CODE_COL + " = '" + programCode + "'" +
                " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + SERVICE_CODE_COL + " = '" + srvCode + "'" +
                " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R1_LIST_CODE_COL + " = '" + districtCode + "'" +
                " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R2_LIST_CODE_COL + " = '" + upzellaCode + "'" +
                " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R3_LIST_CODE_COL + " = '" + unionCode + "'" +
                " AND " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + LAY_R4_LIST_CODE_COL + " = '" + villageCode + "'" +
                //  +
                " GROUP BY NewID" +//+ REG_N_ASSIGN_PROG_SRV_TABLE+"."+HHID_COL+
                " ORDER BY " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + HHID_COL +
                " , " + REG_N_ASSIGN_PROG_SRV_TABLE + "." + HH_MEM_ID + "  ASC ";*//*


    }*/

    public static String getTotalListOfMemberRAssignedSummary_sql(String countryCode, String districtCode, String upzellaCode, String unionCode, String villageCode, String donorCode, String awardCord, String programCode, String srvCode) {


        String getMemberName;
        if (countryCode.equals("0004")) {
            getMemberName = SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.MEM_NAME_FIRST_COL +
                    "|| ' ' || " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.MEM_NAME_MIDDLE_COL +
                    "|| ' ' || " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.MEM_NAME_LAST_COL;

        } else
            getMemberName = SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.MEM_NAME_COL;


        return "SELECT " + "rgAss." + LAY_R1_LIST_CODE_COL
                + " || '' || " + "rgAss." + LAY_R2_LIST_CODE_COL
                + " || '' || " + "rgAss." + LAY_R3_LIST_CODE_COL
                + " || '' || " + "rgAss." + LAY_R4_LIST_CODE_COL
                + " || '' || " + "rgAss." + HHID_COL
                + " || '' || " + "rgAss." + HH_MEM_ID + " AS NewID "
                + ", " + getMemberName + "  AS memberName"
                + " , " + "rgAss." + REG_N_DAT_COL + " AS regDate "
                // + " || '' || " + "memGrp." + GROUP_CODE_COL
                + " , memGrp." + GROUP_CODE_COL
                + " , CASE WHEN cg." + GROUP_NAME_COL+" IS NULL  THEN '' ELSE cg." + GROUP_NAME_COL+ " END AS grpName "
                + " FROM " + REG_N_ASSIGN_PROG_SRV_TABLE + " AS rgAss "
                + " INNER JOIN " + REGISTRATION_MEMBER_TABLE
                + " ON " + "rgAss." + COUNTRY_CODE_COL + " = " + REGISTRATION_MEMBER_TABLE + "." + COUNTRY_CODE_COL +
                " AND " + "rgAss." + LAY_R1_LIST_CODE_COL + " = " + REGISTRATION_MEMBER_TABLE + "." + DISTRICT_NAME_COL +
                " AND " + "rgAss." + LAY_R2_LIST_CODE_COL + " = " + REGISTRATION_MEMBER_TABLE + "." + UPZILLA_NAME_COL +
                " AND " + "rgAss." + LAY_R3_LIST_CODE_COL + " = " + REGISTRATION_MEMBER_TABLE + "." + UNITE_NAME_COL +
                " AND " + "rgAss." + LAY_R4_LIST_CODE_COL + " = " + REGISTRATION_MEMBER_TABLE + "." + VILLAGE_NAME_COL +
                " AND " + "rgAss." + HHID_COL + " = " + REGISTRATION_MEMBER_TABLE + "." + HHID_COL +
                " AND " + "rgAss." + HH_MEM_ID + " = " + REGISTRATION_MEMBER_TABLE + "." + HH_MEM_ID + " " +
                      /*   " JOIN "+SERVICE_MASTER_TABLE+" ON "+REG_N_ASSIGN_PROG_SRV_TABLE+"."+PROGRAM_CODE_COL+" = "+SERVICE_MASTER_TABLE+"."+PROGRAM_CODE_COL+
                 " AND " +REG_N_ASSIGN_PROG_SRV_TABLE+"."+SERVICE_CODE_COL+" = "+SERVICE_MASTER_TABLE+"."+SERVICE_CODE_COL+*/

                " LEFT JOIN " + REG_N_MEM_PROG_GRP_TABLE + " AS memGrp "
                + " ON  memGrp." + COUNTRY_CODE_COL + " = " + " rgAss." + COUNTRY_CODE_COL
                + " AND memGrp." + LAY_R1_LIST_CODE_COL + " = " + " rgAss." + LAY_R1_LIST_CODE_COL
                + " AND memGrp." + LAY_R2_LIST_CODE_COL + " = " + " rgAss." + LAY_R2_LIST_CODE_COL
                + " AND memGrp." + LAY_R3_LIST_CODE_COL + " = " + " rgAss." + LAY_R3_LIST_CODE_COL
                + " AND memGrp." + LAY_R4_LIST_CODE_COL + " = " + " rgAss." + LAY_R4_LIST_CODE_COL
                + " AND memGrp." + DONOR_CODE_COL + " = " + " rgAss." + DONOR_CODE_COL
                + " AND memGrp." + AWARD_CODE_COL + " = " + " rgAss." + AWARD_CODE_COL
                + " AND memGrp." + HHID_COL + " = " + " rgAss." + HHID_COL
                + " AND memGrp." + HH_MEM_ID + " = " + " rgAss." + HH_MEM_ID
                + " AND memGrp." + PROGRAM_CODE_COL + " = " + " rgAss." + PROGRAM_CODE_COL
                + " AND memGrp." + SERVICE_CODE_COL + " = " + " rgAss." + SERVICE_CODE_COL +

                " LEFT JOIN " + COMMUNITY_GROUP_TABLE + " AS cg "
                + " ON  cg." + COUNTRY_CODE_COL + " = " + " rgAss." + COUNTRY_CODE_COL
                + " AND cg." + AWARD_CODE_COL + " = " + " rgAss." + AWARD_CODE_COL
                + " AND cg." + DONOR_CODE_COL + " = " + " rgAss." + DONOR_CODE_COL
                + " AND cg." + PROGRAM_CODE_COL + " = " + " rgAss." + PROGRAM_CODE_COL
                + " AND cg." + GROUP_CODE_COL + " = " + " memGrp." + GROUP_CODE_COL+
                " AND cg." + GRP_LAY_R1_LIST_CODE_COL + " = " + " memGrp." + GRP_LAY_R1_LIST_CODE_COL
               + " AND cg." + GRP_LAY_R2_LIST_CODE_COL + " = " + " memGrp." + GRP_LAY_R2_LIST_CODE_COL
               + " AND cg." + GRP_LAY_R3_LIST_CODE_COL + " = " + " memGrp." + GRP_LAY_R3_LIST_CODE_COL +

                " WHERE " + "rgAss." + COUNTRY_CODE_COL + "= '" + countryCode + "'" +
                " AND " + "rgAss." + DONOR_CODE_COL + " = '" + donorCode + "'" +
                " AND " + "rgAss." + AWARD_CODE_COL + " = '" + awardCord + "'" +
                " AND " + "rgAss." + PROGRAM_CODE_COL + " = '" + programCode + "'" +
                " AND " + "rgAss." + SERVICE_CODE_COL + " = '" + srvCode + "'" +
                " AND " + "rgAss." + LAY_R1_LIST_CODE_COL + " = '" + districtCode + "'" +
                " AND " + "rgAss." + LAY_R2_LIST_CODE_COL + " = '" + upzellaCode + "'" +
                " AND " + "rgAss." + LAY_R3_LIST_CODE_COL + " = '" + unionCode + "'" +
                " AND " + "rgAss." + LAY_R4_LIST_CODE_COL + " = '" + villageCode + "'" +


                //  +
                " GROUP BY NewID" +//+ REG_N_ASSIGN_PROG_SRV_TABLE+"."+HHID_COL+
                " ORDER BY " + "rgAss." + HHID_COL +
                " , " + "rgAss." + HH_MEM_ID + "  ASC ";


    }

    public static String get_ProgramMultipleSrv_SelectQuery(String donorCode, String awardCode, String programCode) {

        return "SELECT " + SQLiteHandler.MULTIPLE_SERVICE_FLAG_COL + " FROM " + ADM_PROGRAM_MASTER_TABLE
                + " WHERE " + DONOR_CODE_COL + " = '" + donorCode + "' " +
                " AND " + AWARD_CODE_COL + " = '" + awardCode + "' " +
                " AND " + PROGRAM_CODE_COL + " = '" + programCode + "' ";
    }

    public static String get_SrvCriteriaList_SelectQuery(String cCode, String donorCode, String awardCord, String opMCode, String programCode, String distFlag) {
        return " SELECT " + SQLiteHandler.SERVICE_MASTER_TABLE + "." + SQLiteHandler.SERVICE_SHORT_NAME_COL + " AS Criteria , " +
                SQLiteHandler.SERVICE_MASTER_TABLE + "." + PROGRAM_CODE_COL + " || '' || " + SQLiteHandler.SERVICE_MASTER_TABLE + "." + SERVICE_CODE_COL + " AS IdCriteria ,  " +
                "( SELECT COUNT (*) FROM " + SQLiteHandler.SERVICE_TABLE +
                " WHERE " + SQLiteHandler.SERVICE_TABLE + "." + PROGRAM_CODE_COL + " = " + SQLiteHandler.SERVICE_MASTER_TABLE + "." + PROGRAM_CODE_COL +
                " AND " + SQLiteHandler.SERVICE_TABLE + "." + SERVICE_CODE_COL + " = " + SQLiteHandler.SERVICE_MASTER_TABLE + "." + SERVICE_CODE_COL
                + " AND " + SQLiteHandler.SERVICE_TABLE + "." + OP_MONTH_CODE_COL + " = " + SQLiteHandler.OP_MONTH_TABLE + "." + OP_MONTH_CODE_COL
                + " AND " + SQLiteHandler.SERVICE_TABLE + "." + SQLiteHandler.SERVICE_STATUS_COL + " = 'O'"

                + " ) AS Count " +

                " FROM " + SQLiteHandler.SERVICE_TABLE + " JOIN " + SQLiteHandler.SERVICE_CENTER_TABLE
                + " ON " + SQLiteHandler.SERVICE_TABLE + "." + COUNTRY_CODE_COL + " = " + SQLiteHandler.SERVICE_CENTER_TABLE + "." + COUNTRY_CODE_COL +

                " JOIN " + SQLiteHandler.OP_MONTH_TABLE + " ON " + SQLiteHandler.SERVICE_TABLE + "." + COUNTRY_CODE_COL + " = " + SQLiteHandler.OP_MONTH_TABLE + "." + COUNTRY_CODE_COL
                + " AND " + SQLiteHandler.SERVICE_TABLE + "." + SQLiteHandler.OPERATION_CODE_COL + " = " + SQLiteHandler.OP_MONTH_TABLE + "." + SQLiteHandler.OPERATION_CODE_COL
                + " AND " + SQLiteHandler.SERVICE_TABLE + "." + OP_MONTH_CODE_COL + " = " + SQLiteHandler.OP_MONTH_TABLE + "." + OP_MONTH_CODE_COL
                + " AND " + SQLiteHandler.SERVICE_TABLE + "." + DONOR_CODE_COL + " = " + SQLiteHandler.OP_MONTH_TABLE + "." + DONOR_CODE_COL
                + " AND " + SQLiteHandler.SERVICE_TABLE + "." + AWARD_CODE_COL + " = " + SQLiteHandler.OP_MONTH_TABLE + "." + AWARD_CODE_COL + " " +

                " JOIN " + SQLiteHandler.SERVICE_MASTER_TABLE + " ON " + SQLiteHandler.SERVICE_TABLE + "." + PROGRAM_CODE_COL + " = " + SQLiteHandler.SERVICE_MASTER_TABLE + "." + PROGRAM_CODE_COL + " AND " +

                SQLiteHandler.SERVICE_TABLE + "." + SERVICE_CODE_COL + " = " + SQLiteHandler.SERVICE_MASTER_TABLE + "." + SERVICE_CODE_COL +

                " WHERE  " + SQLiteHandler.SERVICE_TABLE + "." + COUNTRY_CODE_COL + " = '" + cCode + "' "
                + " AND " + SQLiteHandler.SERVICE_TABLE + "." + DONOR_CODE_COL + " = '" + donorCode + "' "
                + " AND " + SQLiteHandler.SERVICE_TABLE + "." + AWARD_CODE_COL + " = '" + awardCord + "'"
                + " AND " + SQLiteHandler.SERVICE_TABLE + "." + OP_MONTH_CODE_COL + " = '" + opMCode + "'  "
                + " AND " + SQLiteHandler.SERVICE_TABLE + "." + PROGRAM_CODE_COL + " = '" + programCode + "' "
                + " AND " + SQLiteHandler.SERVICE_TABLE + "." + SQLiteHandler.DIST_FLAG_COL + " = '" + distFlag + "' "
                + " GROUP BY " + SQLiteHandler.SERVICE_MASTER_TABLE + "." + SQLiteHandler.SERVICE_SHORT_NAME_COL;
    }


    public static String get_DistCriteriaList_SelectQuery(String cCode, String donorCode, String awardCord, String opMCode, String programCode, String distFlag) {


        return " SELECT " + SQLiteHandler.SERVICE_MASTER_TABLE + "." + SQLiteHandler.SERVICE_SHORT_NAME_COL + " AS Criteria , " +
                SQLiteHandler.SERVICE_MASTER_TABLE + "." + PROGRAM_CODE_COL + " || '' || " + SQLiteHandler.SERVICE_MASTER_TABLE + "." + SERVICE_CODE_COL + " AS IdCriteria"
       /* ,( Select Count(*) From Service  WHERE Service.ProgramCode = ServiceMaster.ProgramCode
        AND Service.ServiceCode = ServiceMaster.ServiceCode
        AND Service.ServiceStatus='C'
        and Service.DistributionStatus in('S','P') ) as plane*/

                + " ,  ( SELECT COUNT (*) FROM  " + SQLiteHandler.SERVICE_TABLE
                + " WHERE " + SQLiteHandler.SERVICE_TABLE + "." + PROGRAM_CODE_COL + " = " + SQLiteHandler.SERVICE_MASTER_TABLE + "." + PROGRAM_CODE_COL
                + " AND " + SQLiteHandler.SERVICE_TABLE + "." + SERVICE_CODE_COL + " = " + SQLiteHandler.SERVICE_MASTER_TABLE + "." + SERVICE_CODE_COL

                + " AND " + SQLiteHandler.SERVICE_TABLE + "." + SQLiteHandler.SERVICE_STATUS_COL + " = 'C' "
                + " AND " + SQLiteHandler.SERVICE_TABLE + "." + SQLiteHandler.DISTRIBUTION_STATUS_COL + " IN ('S','P') ) AS plan "


                + " ,  ( SELECT COUNT (*) FROM " + SQLiteHandler.DISTRIBUTION_TABLE
                + " WHERE " + SQLiteHandler.DISTRIBUTION_TABLE + "." + PROGRAM_CODE_COL + " = " + SQLiteHandler.SERVICE_MASTER_TABLE + "." + PROGRAM_CODE_COL
                + " AND " + SQLiteHandler.DISTRIBUTION_TABLE + "." + SERVICE_CODE_COL + " = " + SQLiteHandler.SERVICE_MASTER_TABLE + "." + SERVICE_CODE_COL
                + " AND " + SQLiteHandler.DISTRIBUTION_TABLE + "." + OP_MONTH_CODE_COL + " = " + SQLiteHandler.OP_MONTH_TABLE + "." + OP_MONTH_CODE_COL
                + " AND " + SQLiteHandler.DISTRIBUTION_TABLE + "." + SQLiteHandler.DISTRIBUTION_STATUS_COL + " = 'R'"

                + " ) AS receive " +

                " FROM " + SQLiteHandler.DISTRIBUTION_TABLE
                //+ " JOIN " + SQLiteHandler.SERVICE_CENTER_TABLE
                // + " ON " + SQLiteHandler.SERVICE_TABLE + "." + SQLiteHandler.COUNTRY_CODE_COL + " = " + SQLiteHandler.SERVICE_CENTER_TABLE + "." + SQLiteHandler.COUNTRY_CODE_COL +

                + " JOIN " + SQLiteHandler.OP_MONTH_TABLE
                + " ON " + SQLiteHandler.DISTRIBUTION_TABLE + "." + COUNTRY_CODE_COL + " = " + SQLiteHandler.OP_MONTH_TABLE + "." + COUNTRY_CODE_COL
                + " AND " + SQLiteHandler.DISTRIBUTION_TABLE + "." + DONOR_CODE_COL + " = " + SQLiteHandler.OP_MONTH_TABLE + "." + DONOR_CODE_COL
                + " AND " + SQLiteHandler.DISTRIBUTION_TABLE + "." + AWARD_CODE_COL + " = " + SQLiteHandler.OP_MONTH_TABLE + "." + AWARD_CODE_COL + " "
                + " AND " + SQLiteHandler.OP_MONTH_TABLE + "." + SQLiteHandler.OPERATION_CODE_COL + " = '3' "
                + " AND " + SQLiteHandler.DISTRIBUTION_TABLE + "." + OP_MONTH_CODE_COL + " = " + SQLiteHandler.OP_MONTH_TABLE + "." + OP_MONTH_CODE_COL

                + " JOIN " + SQLiteHandler.SERVICE_MASTER_TABLE
                + " ON " + SQLiteHandler.DISTRIBUTION_TABLE + "." + PROGRAM_CODE_COL + " = " + SQLiteHandler.SERVICE_MASTER_TABLE + "." + PROGRAM_CODE_COL
                + " AND " + SQLiteHandler.DISTRIBUTION_TABLE + "." + SERVICE_CODE_COL + " = " + SQLiteHandler.SERVICE_MASTER_TABLE + "." + SERVICE_CODE_COL +

                " WHERE  " + SQLiteHandler.DISTRIBUTION_TABLE + "." + COUNTRY_CODE_COL + " = '" + cCode +
                "' AND " + SQLiteHandler.DISTRIBUTION_TABLE + "." + DONOR_CODE_COL + " = '" + donorCode +
                "' AND " + SQLiteHandler.DISTRIBUTION_TABLE + "." + AWARD_CODE_COL + " = '" + awardCord + "'" +
                " AND " + SQLiteHandler.DISTRIBUTION_TABLE + "." + OP_MONTH_CODE_COL + " = '" + opMCode + "'  " +
                " AND " + SQLiteHandler.DISTRIBUTION_TABLE + "." + PROGRAM_CODE_COL + " = '" + programCode + "'  " +
                " AND " + SQLiteHandler.DISTRIBUTION_TABLE + "." + SQLiteHandler.DIST_FLAG_COL + " = '" + distFlag + "'  " +
                " GROUP BY " + SQLiteHandler.SERVICE_MASTER_TABLE + "." + SQLiteHandler.SERVICE_SHORT_NAME_COL;
    }


    public static String getSrvExtendedItemSummaryList_SelectQuery(String cCode, String donorCode, String awardCord, String opMCode, String programCode) {


        return " SELECT " + VOUCHER_ITEM_TABLE + "." + ITEM_NAME_COL

                + " || '-' || " + VOUCHER_ITEM__MEAS_TABLE + "." + UNITE_MEAS_COL
                + " ||' '||  " + VOUCHER_ITEM__MEAS_TABLE + "." + MEASE_TITLE_COL + " AS item , "
                + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + VOUCHER_ITEM_SPEC_COL + " AS voucherID ,  " +
                " sum( " + SERVICE_EXTENDED_TABLE + "." + VOUCHER_UNIT_COL + ") AS unitCount " +
                " FROM " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE +
                "  INNER JOIN " + VOUCHER_ITEM_TABLE
                + " ON " + VOUCHER_ITEM_TABLE + "." + CATEGORY_CODE_COL + " || " + VOUCHER_ITEM_TABLE + "." + ITEM_CODE_COL + " = SUBSTR(" + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + VOUCHER_ITEM_SPEC_COL + ",0,8) " +
                "  INNER JOIN " + VOUCHER_ITEM__MEAS_TABLE
                + " ON " + VOUCHER_ITEM__MEAS_TABLE + "." + MEAS_R_CODE_COL + " = SUBSTR(" + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + VOUCHER_ITEM_SPEC_COL + ",8) " +
                "  INNER JOIN " + SERVICE_EXTENDED_TABLE
                + " ON " + SERVICE_EXTENDED_TABLE + "." + COUNTRY_CODE_COL + " = " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + COUNTRY_CODE_COL
                + " AND " + SERVICE_EXTENDED_TABLE + "." + DONOR_CODE_COL + " = " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + DONOR_CODE_COL
                + " AND " + SERVICE_EXTENDED_TABLE + "." + AWARD_CODE_COL + " = " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + AWARD_CODE_COL
                + " AND " + SERVICE_EXTENDED_TABLE + "." + PROGRAM_CODE_COL + " = " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + PROGRAM_CODE_COL
                + " AND " + SERVICE_EXTENDED_TABLE + "." + SERVICE_CODE_COL + " = " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + SERVICE_CODE_COL
                + " AND " + SERVICE_EXTENDED_TABLE + "." + VOUCHER_ITEM_SPEC_COL + " = " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + VOUCHER_ITEM_SPEC_COL


                + " WHERE  " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + COUNTRY_CODE_COL + " = '" + cCode + "' "
                + " AND " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + DONOR_CODE_COL + " = '" + donorCode + "' "
                + " AND " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + AWARD_CODE_COL + " = '" + awardCord + "'"
                + " AND " + SERVICE_EXTENDED_TABLE + "." + OP_MONTH_CODE_COL + " = '" + opMCode + "'  "
                + " AND " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + PROGRAM_CODE_COL + " = '" + programCode + "'  "
                + " GROUP BY " + SERVICE_EXTENDED_TABLE + "." + VOUCHER_ITEM_SPEC_COL;

    }

    public static String getDistExtendedItemSummaryList_SelectQuery(String cCode, String donorCode, String awardCord, String opMCode, String programCode) {
        return " SELECT " + VOUCHER_ITEM_TABLE + "." + ITEM_NAME_COL

                + " || '-' || " + VOUCHER_ITEM__MEAS_TABLE + "." + UNITE_MEAS_COL
                + " ||' '||  " + VOUCHER_ITEM__MEAS_TABLE + "." + MEASE_TITLE_COL + " AS item , "
                + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + VOUCHER_ITEM_SPEC_COL + " AS voucherID ,  " +
                " sum( " + DISTRIBUTION_EXTENDED_TABLE + "." + VOUCHER_UNIT_COL + ") AS unitCount " +
                " FROM " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE +
                "  INNER JOIN " + VOUCHER_ITEM_TABLE
                + " ON " + VOUCHER_ITEM_TABLE + "." + CATEGORY_CODE_COL + " || " + VOUCHER_ITEM_TABLE + "." + ITEM_CODE_COL + " = SUBSTR(" + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + VOUCHER_ITEM_SPEC_COL + ",0,8) " +
                "  INNER JOIN " + VOUCHER_ITEM__MEAS_TABLE
                + " ON " + VOUCHER_ITEM__MEAS_TABLE + "." + MEAS_R_CODE_COL + " = SUBSTR(" + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + VOUCHER_ITEM_SPEC_COL + ",8) " +
                "  INNER JOIN " + DISTRIBUTION_EXTENDED_TABLE
                + " ON " + DISTRIBUTION_EXTENDED_TABLE + "." + COUNTRY_CODE_COL + " = " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + COUNTRY_CODE_COL
                + " AND " + DISTRIBUTION_EXTENDED_TABLE + "." + DONOR_CODE_COL + " = " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + DONOR_CODE_COL
                + " AND " + DISTRIBUTION_EXTENDED_TABLE + "." + AWARD_CODE_COL + " = " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + AWARD_CODE_COL
                + " AND " + DISTRIBUTION_EXTENDED_TABLE + "." + PROGRAM_CODE_COL + " = " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + PROGRAM_CODE_COL
                + " AND " + DISTRIBUTION_EXTENDED_TABLE + "." + SERVICE_CODE_COL + " = " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + SERVICE_CODE_COL
                + " AND " + DISTRIBUTION_EXTENDED_TABLE + "." + VOUCHER_ITEM_SPEC_COL + " = " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + VOUCHER_ITEM_SPEC_COL


                + " WHERE  " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + COUNTRY_CODE_COL + " = '" + cCode + "' "
                + " AND " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + DONOR_CODE_COL + " = '" + donorCode + "' "
                + " AND " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + AWARD_CODE_COL + " = '" + awardCord + "'"
                + " AND " + DISTRIBUTION_EXTENDED_TABLE + "." + OP_MONTH_CODE_COL + " = '" + opMCode + "'  "
                + " AND " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + PROGRAM_CODE_COL + " = '" + programCode + "'  "
                + " GROUP BY "
                + DISTRIBUTION_EXTENDED_TABLE + "." + DONOR_CODE_COL
                + " , " + DISTRIBUTION_EXTENDED_TABLE + "." + AWARD_CODE_COL
                + " , " + DISTRIBUTION_EXTENDED_TABLE + "." + PROGRAM_CODE_COL
                + " , " + DISTRIBUTION_EXTENDED_TABLE + "." + VOUCHER_ITEM_SPEC_COL;


    }

    public static String getTotalServiceAttendanceSummary_SelectQuery(String cCode, String donorCode, String awardCord, String opMCode, String prgCode, String srvCode, String distFlag) {
        String getMemName;
        if (cCode.equals("0004")) {
            getMemName = SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.MEM_NAME_FIRST_COL + " || ' ' || " +
                    SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.MEM_NAME_MIDDLE_COL + " || ' ' || " +
                    SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.MEM_NAME_LAST_COL;

        } else {
            getMemName = SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.MEM_NAME_COL;

        }


        return " SELECT " + SQLiteHandler.SERVICE_TABLE + "." + LAY_R1_LIST_CODE_COL + " || '' || " +
                SQLiteHandler.SERVICE_TABLE + "." + LAY_R2_LIST_CODE_COL + " || '' || " +
                SQLiteHandler.SERVICE_TABLE + "." + LAY_R3_LIST_CODE_COL + " || '' || " +
                SQLiteHandler.SERVICE_TABLE + "." + LAY_R4_LIST_CODE_COL + " || '' || " +
                SQLiteHandler.SERVICE_TABLE + "." + HHID_COL + " || '' || " +
                SQLiteHandler.SERVICE_TABLE + "." + HH_MEM_ID + " AS NewID , " +
                /** HERE COUNT THE SERVICE */


                SQLiteHandler.SERVICE_TABLE + "." + SQLiteHandler.SERVICE_SL_COL
                + " , " + getMemName + " AS memberName "
                + " , " + SQLiteHandler.SERVICE_TABLE + "." + SQLiteHandler.SERVICE_STATUS_COL + " AS status " +
                //   SERVICE_TABLE + "." + SERVICE_DT_COL +
                " FROM " + SQLiteHandler.SERVICE_TABLE +
                " JOIN " + SQLiteHandler.SERVICE_CENTER_TABLE + " ON " + SQLiteHandler.SERVICE_TABLE + "." + COUNTRY_CODE_COL + " = " + SQLiteHandler.SERVICE_CENTER_TABLE + "." + COUNTRY_CODE_COL +

                " JOIN " + SQLiteHandler.OP_MONTH_TABLE + " ON " + SQLiteHandler.SERVICE_TABLE + "." + COUNTRY_CODE_COL + " = " + SQLiteHandler.OP_MONTH_TABLE + "." + COUNTRY_CODE_COL +
                " AND " + SQLiteHandler.SERVICE_TABLE + "." + SQLiteHandler.OPERATION_CODE_COL + " = " + SQLiteHandler.OP_MONTH_TABLE + "." + SQLiteHandler.OPERATION_CODE_COL +
                " AND " + SQLiteHandler.SERVICE_TABLE + "." + OP_MONTH_CODE_COL + " = " + SQLiteHandler.OP_MONTH_TABLE + "." + OP_MONTH_CODE_COL +
                " AND " + SQLiteHandler.SERVICE_TABLE + "." + DONOR_CODE_COL + " = " + SQLiteHandler.OP_MONTH_TABLE + "." + DONOR_CODE_COL +
                " AND " + SQLiteHandler.SERVICE_TABLE + "." + AWARD_CODE_COL + " = " + SQLiteHandler.OP_MONTH_TABLE + "." + AWARD_CODE_COL + "  " +
                " JOIN " + SQLiteHandler.SERVICE_MASTER_TABLE + " ON " + SQLiteHandler.SERVICE_TABLE + "." + PROGRAM_CODE_COL + " = " + SQLiteHandler.SERVICE_MASTER_TABLE + "." + PROGRAM_CODE_COL +
                " AND " + SQLiteHandler.SERVICE_TABLE + "." + SERVICE_CODE_COL + " = " + SQLiteHandler.SERVICE_MASTER_TABLE + "." + SERVICE_CODE_COL + "  " +
                " INNER JOIN " + SQLiteHandler.REGISTRATION_MEMBER_TABLE +
                " ON " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + COUNTRY_CODE_COL + " = " + SQLiteHandler.SERVICE_TABLE + "." + COUNTRY_CODE_COL +
                " AND " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL + " = " + SQLiteHandler.SERVICE_TABLE + "." + LAY_R1_LIST_CODE_COL +
                " AND " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL + " = " + SQLiteHandler.SERVICE_TABLE + "." + LAY_R2_LIST_CODE_COL +
                " AND " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + UNITE_NAME_COL + " = " + SQLiteHandler.SERVICE_TABLE + "." + LAY_R3_LIST_CODE_COL +
                " AND " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL + " = " + SQLiteHandler.SERVICE_TABLE + "." + LAY_R4_LIST_CODE_COL +


                " AND " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HHID_COL + " = " + SQLiteHandler.SERVICE_TABLE + "." + HHID_COL +

                " AND " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HH_MEM_ID + " = " + SQLiteHandler.SERVICE_TABLE + "." + HH_MEM_ID +

                " WHERE " + SQLiteHandler.SERVICE_TABLE + "." + COUNTRY_CODE_COL + " = '" + cCode + "'" +
                " AND " + SQLiteHandler.SERVICE_TABLE + "." + DONOR_CODE_COL + " = '" + donorCode + "'" +
                " AND " + SQLiteHandler.SERVICE_TABLE + "." + AWARD_CODE_COL + " = '" + awardCord + "'" +
                " AND " + SQLiteHandler.SERVICE_TABLE + "." + PROGRAM_CODE_COL + " = '" + prgCode + "'" +
                " AND " + SQLiteHandler.SERVICE_TABLE + "." + SERVICE_CODE_COL + " = '" + srvCode + "'" +
                " AND " + SQLiteHandler.SERVICE_TABLE + "." + OP_MONTH_CODE_COL + " = '" + opMCode + "'  " +
                " AND " + SQLiteHandler.SERVICE_TABLE + "." + SQLiteHandler.DIST_FLAG_COL + " = '" + distFlag + "'  " +
                " AND " + SQLiteHandler.SERVICE_TABLE + "." + SQLiteHandler.SERVICE_STATUS_COL + " = 'O'  " +
                " GROUP BY " + SQLiteHandler.SERVICE_TABLE + "." + LAY_R1_LIST_CODE_COL + " , " +
                SQLiteHandler.SERVICE_TABLE + "." + LAY_R2_LIST_CODE_COL + " , " +
                SQLiteHandler.SERVICE_TABLE + "." + LAY_R3_LIST_CODE_COL + " , " +
                SQLiteHandler.SERVICE_TABLE + "." + LAY_R4_LIST_CODE_COL + " , " +
                SQLiteHandler.SERVICE_TABLE + "." + HHID_COL + " , " +
                SQLiteHandler.SERVICE_TABLE + "." + HH_MEM_ID + " , " +
                SQLiteHandler.SERVICE_TABLE + "." + SQLiteHandler.SERVICE_SL_COL + "  ";

    }

    public static String getTotalDistributionAttendanceSummary_SelectQuery(String cCode, String donorCode, String awardCord, String opMCode, String prgCode, String srvCode, String distFlag) {
        String getMemName;
        if (cCode.equals("0004")) {
            getMemName = SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.MEM_NAME_FIRST_COL + " || ' ' || " +
                    SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.MEM_NAME_MIDDLE_COL + " || ' ' || " +
                    SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.MEM_NAME_LAST_COL;

        } else {
            getMemName = SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.MEM_NAME_COL;

        }
/**
 * todo: check status
 */

        return " SELECT " + SQLiteHandler.DISTRIBUTION_TABLE + "." + MEM_ID_15_D_COL + " AS NewID "
                + " , " + SQLiteHandler.DISTRIBUTION_TABLE + "." + SQLiteHandler.DISTRIBUTION_STATUS_COL + " AS status "
                + " , CASE   WHEN length(" + SQLiteHandler.DISTRIBUTION_TABLE + "." + MEM_ID_15_D_COL + ") >=15 "
                + " THEN " + getMemName
                + " ELSE " + REGISTRATION_TABLE + "." + SQLiteHandler.PNAME_COL + " END AS rptName "

                + " FROM " + SQLiteHandler.DISTRIBUTION_TABLE
                + " JOIN " + SQLiteHandler.OP_MONTH_TABLE
                + " ON " + SQLiteHandler.DISTRIBUTION_TABLE + "." + COUNTRY_CODE_COL + " = " + SQLiteHandler.OP_MONTH_TABLE + "." + COUNTRY_CODE_COL
                + " AND " + SQLiteHandler.OP_MONTH_TABLE + "." + SQLiteHandler.OPERATION_CODE_COL + " = '3' "
                + " AND " + SQLiteHandler.DISTRIBUTION_TABLE + "." + OP_MONTH_CODE_COL + " = " + SQLiteHandler.OP_MONTH_TABLE + "." + OP_MONTH_CODE_COL
                + " AND " + SQLiteHandler.DISTRIBUTION_TABLE + "." + DONOR_CODE_COL + " = " + SQLiteHandler.OP_MONTH_TABLE + "." + DONOR_CODE_COL
                + " AND " + SQLiteHandler.DISTRIBUTION_TABLE + "." + AWARD_CODE_COL + " = " + SQLiteHandler.OP_MONTH_TABLE + "." + AWARD_CODE_COL
                + " LEFT JOIN " + SQLiteHandler.REGISTRATION_MEMBER_TABLE
                + " ON " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + COUNTRY_CODE_COL + " = " + SQLiteHandler.DISTRIBUTION_TABLE + "." + COUNTRY_CODE_COL
                + " AND " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL
                + " || " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL
                + " || " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + UNITE_NAME_COL
                + " || " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL
                + " || " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HHID_COL
                + " || " + SQLiteHandler.REGISTRATION_MEMBER_TABLE + "." + HH_MEM_ID + " = " + SQLiteHandler.DISTRIBUTION_TABLE + "." + MEM_ID_15_D_COL


                + " LEFT JOIN " + REGISTRATION_TABLE
                + " ON " + REGISTRATION_TABLE + "." + COUNTRY_CODE_COL + " = " + SQLiteHandler.DISTRIBUTION_TABLE + "." + COUNTRY_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL
                + " || " + REGISTRATION_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL
                + " || " + REGISTRATION_TABLE + "." + UNITE_NAME_COL
                + " || " + REGISTRATION_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL
                + " || " + REGISTRATION_TABLE + "." + SQLiteHandler.PID_COL
                + " = " + SQLiteHandler.DISTRIBUTION_TABLE + "." + MEM_ID_15_D_COL

                + " WHERE " + SQLiteHandler.DISTRIBUTION_TABLE + "." + COUNTRY_CODE_COL + " = '" + cCode + "' "
                + " AND " + SQLiteHandler.DISTRIBUTION_TABLE + "." + DONOR_CODE_COL + " = '" + donorCode + "' "
                + " AND " + SQLiteHandler.DISTRIBUTION_TABLE + "." + AWARD_CODE_COL + " = '" + awardCord + "' "
                + " AND " + SQLiteHandler.DISTRIBUTION_TABLE + "." + PROGRAM_CODE_COL + " = '" + prgCode + "' "
                + " AND " + SQLiteHandler.DISTRIBUTION_TABLE + "." + SERVICE_CODE_COL + " = '" + srvCode + "' "
                + " AND " + SQLiteHandler.DISTRIBUTION_TABLE + "." + OP_MONTH_CODE_COL + " = '" + opMCode + "' "
                + " AND " + SQLiteHandler.DISTRIBUTION_TABLE + "." + SQLiteHandler.DIST_FLAG_COL + " = '" + distFlag + "' "
                ;
    }

    public static String getTotal_Service_Itemize_AttendanceSummary_SelectQuery(String cCode, String donorCode, String awardCord, String opMCode, String prgCode/*, String srvCode*/, String vouItSpec) {
        return " SELECT " + SERVICE_EXTENDED_TABLE + "." + LAY_R1_LIST_CODE_COL + " || '' || "
                + SERVICE_EXTENDED_TABLE + "." + LAY_R2_LIST_CODE_COL + " || '' || "
                + SERVICE_EXTENDED_TABLE + "." + LAY_R3_LIST_CODE_COL + " || '' || "
                + SERVICE_EXTENDED_TABLE + "." + LAY_R4_LIST_CODE_COL + " || '' || "
                + SERVICE_EXTENDED_TABLE + "." + HHID_COL + " || '' || "
                + SERVICE_EXTENDED_TABLE + "." + HH_MEM_ID + " AS NewID , "
                +
                /** HERE COUNT THE SERVICE */
                SERVICE_EXTENDED_TABLE + "." + VOUCHER_UNIT_COL + "  "
                + " ," + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + SQLiteHandler.UNITE_COST_COL + " AS cost "
                + " FROM " + SERVICE_EXTENDED_TABLE +
                " JOIN " + SQLiteHandler.SERVICE_CENTER_TABLE + " ON " + SERVICE_EXTENDED_TABLE + "." + COUNTRY_CODE_COL + " = " + SQLiteHandler.SERVICE_CENTER_TABLE + "." + COUNTRY_CODE_COL +
                " JOIN " + SQLiteHandler.OP_MONTH_TABLE + " ON " + SERVICE_EXTENDED_TABLE + "." + COUNTRY_CODE_COL + " = " + SQLiteHandler.OP_MONTH_TABLE + "." + COUNTRY_CODE_COL +
                " AND " + SERVICE_EXTENDED_TABLE + "." + SQLiteHandler.OPERATION_CODE_COL + " = " + SQLiteHandler.OP_MONTH_TABLE + "." + SQLiteHandler.OPERATION_CODE_COL +
                " AND " + SERVICE_EXTENDED_TABLE + "." + OP_MONTH_CODE_COL + " = " + SQLiteHandler.OP_MONTH_TABLE + "." + OP_MONTH_CODE_COL +
                " AND " + SERVICE_EXTENDED_TABLE + "." + DONOR_CODE_COL + " = " + SQLiteHandler.OP_MONTH_TABLE + "." + DONOR_CODE_COL +
                " AND " + SERVICE_EXTENDED_TABLE + "." + AWARD_CODE_COL + " = " + SQLiteHandler.OP_MONTH_TABLE + "." + AWARD_CODE_COL + "  " +
                " JOIN " + SQLiteHandler.SERVICE_MASTER_TABLE + " ON " + SERVICE_EXTENDED_TABLE + "." + PROGRAM_CODE_COL + " = " + SQLiteHandler.SERVICE_MASTER_TABLE + "." + PROGRAM_CODE_COL +
                " AND " + SERVICE_EXTENDED_TABLE + "." + SERVICE_CODE_COL + " = " + SQLiteHandler.SERVICE_MASTER_TABLE + "." + SERVICE_CODE_COL + "  " +
                " INNER JOIN " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE +
                " ON " + SERVICE_EXTENDED_TABLE + "." + COUNTRY_CODE_COL + " = " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + COUNTRY_CODE_COL +
                " AND  " + SERVICE_EXTENDED_TABLE + "." + DONOR_CODE_COL + " = " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + DONOR_CODE_COL +
                " AND  " + SERVICE_EXTENDED_TABLE + "." + AWARD_CODE_COL + " = " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + AWARD_CODE_COL +
                " AND  " + SERVICE_EXTENDED_TABLE + "." + PROGRAM_CODE_COL + " = " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + PROGRAM_CODE_COL +
                " AND  " + SERVICE_EXTENDED_TABLE + "." + SERVICE_CODE_COL + " = " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + SERVICE_CODE_COL +
                " AND  " + SERVICE_EXTENDED_TABLE + "." + VOUCHER_ITEM_SPEC_COL + " = " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + VOUCHER_ITEM_SPEC_COL +
                " WHERE " + SERVICE_EXTENDED_TABLE + "." + COUNTRY_CODE_COL + " = '" + cCode + "'" +
                " AND " + SERVICE_EXTENDED_TABLE + "." + DONOR_CODE_COL + " = '" + donorCode + "'" +
                " AND " + SERVICE_EXTENDED_TABLE + "." + AWARD_CODE_COL + " = '" + awardCord + "'" +
                " AND " + SERVICE_EXTENDED_TABLE + "." + PROGRAM_CODE_COL + " = '" + prgCode + "'" +
                //** use it latter    " AND " + SERVICE_EXTENDED_TABLE + "." + SERVICE_CODE_COL + " = '" + srvCode + "'" +
                " AND " + SERVICE_EXTENDED_TABLE + "." + OP_MONTH_CODE_COL + " = '" + opMCode + "'  " +
                " AND " + SERVICE_EXTENDED_TABLE + "." + VOUCHER_ITEM_SPEC_COL + " = '" + vouItSpec + "'  " +
                " GROUP BY " + SERVICE_EXTENDED_TABLE + "." + LAY_R1_LIST_CODE_COL + " , " +
                SERVICE_EXTENDED_TABLE + "." + LAY_R2_LIST_CODE_COL + " , " +
                SERVICE_EXTENDED_TABLE + "." + LAY_R3_LIST_CODE_COL + " , " +
                SERVICE_EXTENDED_TABLE + "." + LAY_R4_LIST_CODE_COL + " , " +
                SERVICE_EXTENDED_TABLE + "." + HHID_COL + " , " +
                SERVICE_EXTENDED_TABLE + "." + HH_MEM_ID;
//                + " , " +               SERVICE_EXTENDED_TABLE + "." + SERVICE_SL_COL + "  ";

    }

    public static String getTotal_Distribution_Itemize_AttendanceSummary_SelectQuery(String cCode, String donorCode, String awardCord, String opMCode, String prgCode/*, String srvCode*/, String vouItSpec) {
        return " SELECT "
                + DISTRIBUTION_EXTENDED_TABLE + "." + MEM_ID_15_D_COL + " AS NewID , " +
                /** HERE Unit  */
                DISTRIBUTION_EXTENDED_TABLE + "." + VOUCHER_UNIT_COL + "  " +
                " ," + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + SQLiteHandler.UNITE_COST_COL + " AS cost " +
                " FROM " + DISTRIBUTION_EXTENDED_TABLE +
                " JOIN " + SQLiteHandler.SERVICE_CENTER_TABLE + " ON " + DISTRIBUTION_EXTENDED_TABLE + "." + COUNTRY_CODE_COL + " = " + SQLiteHandler.SERVICE_CENTER_TABLE + "." + COUNTRY_CODE_COL +
                " JOIN " + SQLiteHandler.OP_MONTH_TABLE + " ON " + DISTRIBUTION_EXTENDED_TABLE + "." + COUNTRY_CODE_COL + " = " + SQLiteHandler.OP_MONTH_TABLE + "." + COUNTRY_CODE_COL +
                //" AND " + SERVICE_EXTENDED_TABLE + "." + OPERATION_CODE_COL + " = " + OP_MONTH_TABLE + "." + OPERATION_CODE_COL +
                " AND " + DISTRIBUTION_EXTENDED_TABLE + "." + OP_MONTH_CODE_COL + " = " + SQLiteHandler.OP_MONTH_TABLE + "." + OP_MONTH_CODE_COL +
                " AND " + DISTRIBUTION_EXTENDED_TABLE + "." + DONOR_CODE_COL + " = " + SQLiteHandler.OP_MONTH_TABLE + "." + DONOR_CODE_COL +
                " AND " + DISTRIBUTION_EXTENDED_TABLE + "." + AWARD_CODE_COL + " = " + SQLiteHandler.OP_MONTH_TABLE + "." + AWARD_CODE_COL + "  " +
                " JOIN " + SQLiteHandler.SERVICE_MASTER_TABLE + " ON " + DISTRIBUTION_EXTENDED_TABLE + "." + PROGRAM_CODE_COL + " = " + SQLiteHandler.SERVICE_MASTER_TABLE + "." + PROGRAM_CODE_COL +
                " AND " + DISTRIBUTION_EXTENDED_TABLE + "." + SERVICE_CODE_COL + " = " + SQLiteHandler.SERVICE_MASTER_TABLE + "." + SERVICE_CODE_COL + "  " +
                " INNER JOIN " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE +
                " ON " + DISTRIBUTION_EXTENDED_TABLE + "." + COUNTRY_CODE_COL + " = " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + COUNTRY_CODE_COL +
                " AND  " + DISTRIBUTION_EXTENDED_TABLE + "." + DONOR_CODE_COL + " = " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + DONOR_CODE_COL +
                " AND  " + DISTRIBUTION_EXTENDED_TABLE + "." + AWARD_CODE_COL + " = " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + AWARD_CODE_COL +
                " AND  " + DISTRIBUTION_EXTENDED_TABLE + "." + PROGRAM_CODE_COL + " = " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + PROGRAM_CODE_COL +
                " AND  " + DISTRIBUTION_EXTENDED_TABLE + "." + SERVICE_CODE_COL + " = " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + SERVICE_CODE_COL +
                " AND  " + DISTRIBUTION_EXTENDED_TABLE + "." + VOUCHER_ITEM_SPEC_COL + " = " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + VOUCHER_ITEM_SPEC_COL +
                " WHERE " + DISTRIBUTION_EXTENDED_TABLE + "." + COUNTRY_CODE_COL + " = '" + cCode + "'" +
                " AND " + DISTRIBUTION_EXTENDED_TABLE + "." + DONOR_CODE_COL + " = '" + donorCode + "'" +
                " AND " + DISTRIBUTION_EXTENDED_TABLE + "." + AWARD_CODE_COL + " = '" + awardCord + "'" +
                " AND " + DISTRIBUTION_EXTENDED_TABLE + "." + PROGRAM_CODE_COL + " = '" + prgCode + "'" +
                //** use it latter    " AND " + SERVICE_EXTENDED_TABLE + "." + SERVICE_CODE_COL + " = '" + srvCode + "'" +
                " AND " + DISTRIBUTION_EXTENDED_TABLE + "." + OP_MONTH_CODE_COL + " = '" + opMCode + "'  " +
                " AND " + DISTRIBUTION_EXTENDED_TABLE + "." + VOUCHER_ITEM_SPEC_COL + " = '" + vouItSpec + "'  " +
                " GROUP BY " + DISTRIBUTION_EXTENDED_TABLE + "." + MEM_ID_15_D_COL;
//


    }

    public static String getServiceDelete_WhereCondition(String cCode, String donorCode, String awardCode, String distId, String upId, String unId, String villId, String hhId, String memId, String progCode, String srvCode, String opMonthCode, String slNo) {
        return COUNTRY_CODE_COL + " = '" + cCode + "'" +
                " AND " + DONOR_CODE_COL + " = '" + donorCode + "' " +
                " AND " + AWARD_CODE_COL + " = '" + awardCode + "' " +
                " AND " + LAY_R1_LIST_CODE_COL + " = '" + distId + "' " +
                " AND " + LAY_R2_LIST_CODE_COL + " = '" + upId + "' " +
                " AND " + LAY_R3_LIST_CODE_COL + " = '" + unId + "' " +
                " AND " + LAY_R4_LIST_CODE_COL + " = '" + villId + "' " +
                " AND " + HHID_COL + " = '" + hhId + "' " +
                " AND " + HH_MEM_ID + " = '" + memId + "' " +
                " AND " + PROGRAM_CODE_COL + " = '" + progCode + "' " +
                " AND " + SERVICE_CODE_COL + " = '" + srvCode + "' " +
                " AND " + SQLiteHandler.OPERATION_CODE_COL + " = '2' " +
                " AND " + OP_MONTH_CODE_COL + " = '" + opMonthCode + "' " +
                " AND " + SQLiteHandler.SERVICE_SL_COL + " = '" + slNo + "' ";
    }

    public static String getRegisteredData_ifVillageExt_SelectQuery(String ext_village, String hhId) {


        String sql = "SELECT "
                + REGISTRATION_TABLE + "." + SQLiteHandler.ID_COL + ", "
                + REGISTRATION_TABLE + "." + SQLiteHandler.REG_DATE_COL + ", "
                + REGISTRATION_TABLE + "." + COUNTRY_CODE + ", "
                + REGISTRATION_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL + " AS R_District, "
                + REGISTRATION_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL + " AS R_Upazilla, "
                + REGISTRATION_TABLE + "." + UNITE_NAME_COL + " AS R_Union, "
                + REGISTRATION_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL + " AS R_Village, "
                + SQLiteHandler.COUNTRY_TABLE + "." + SQLiteHandler.COUNTRY_NAME + ", "
                + SQLiteHandler.DISTRICT_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL + ", "
                + SQLiteHandler.UPAZILLA_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL + ","
                + UNIT_TABLE + "." + UNITE_NAME_COL + ", "
                + SQLiteHandler.VILLAGE_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL + ","
                + REGISTRATION_TABLE + "." + SQLiteHandler.PID_COL + ","
                + REGISTRATION_TABLE + "." + SQLiteHandler.PNAME_COL + ","
                + "(" + " CASE WHEN " + SQLiteHandler.SEX_COL + "==" + "'F'" + " THEN " + "'Female'" + " ELSE " + "'Male'" + " END " + ")  AS Sex" + ","
                + REGISTRATION_TABLE + "." + SQLiteHandler.HH_SIZE + ","
                + REGISTRATION_TABLE + "." + SQLiteHandler.LATITUDE_COL + ","
                + REGISTRATION_TABLE + "." + SQLiteHandler.LONGITUDE_COL + ","
                + REGISTRATION_TABLE + "." + SQLiteHandler.AG_LAND + ","
                + "(" + " CASE WHEN " + SQLiteHandler.V_STATUS + "==" + "'Y'" + " THEN " + "'Yes'" + " ELSE " + "'No'" + " END " + ") AS VStatus" + ","
                + " " + SQLiteHandler.M_STATUS + " AS MStatus " + ","
                + REGISTRATION_TABLE + "." + SQLiteHandler.ENTRY_BY + ","
                + REGISTRATION_TABLE + "." + SQLiteHandler.ENTRY_DATE + " , "
                + REGISTRATION_TABLE + "." + SQLiteHandler.VSLA_GROUP + " , "
                + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE + "." + SQLiteHandler.REGN_ADDRESS_LOOKUP_NAME_COL + " as addname , "//25
                + REGISTRATION_TABLE + "." + SQLiteHandler.REGN_ADDRESS_LOOKUP_CODE_COL + " as addcode ,"//26
                + REGISTRATION_TABLE + "." + SQLiteHandler.W_RANK_COL + " as wRank ,"//26
                + REGISTRATION_TABLE + "." + SQLiteHandler.M_STATUS + " as status "//26
                + " FROM " + REGISTRATION_TABLE
                + " LEFT JOIN " + SQLiteHandler.COUNTRY_TABLE
                + " ON " + REGISTRATION_TABLE + "." + COUNTRY_CODE_COL + "=" + SQLiteHandler.COUNTRY_TABLE + "." + COUNTRY_CODE
                + " LEFT JOIN " + SQLiteHandler.DISTRICT_TABLE
                + " ON " + REGISTRATION_TABLE + "." + COUNTRY_CODE_COL + "=" + SQLiteHandler.DISTRICT_TABLE + "." + COUNTRY_CODE
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL + "=" + SQLiteHandler.DISTRICT_TABLE + "." + LAY_R1_LIST_CODE_COL
                + " LEFT JOIN " + SQLiteHandler.UPAZILLA_TABLE
                + " ON " + REGISTRATION_TABLE + "." + COUNTRY_CODE_COL + "=" + SQLiteHandler.UPAZILLA_TABLE + "." + COUNTRY_CODE
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL + "=" + SQLiteHandler.UPAZILLA_TABLE + "." + LAY_R1_LIST_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL + "=" + SQLiteHandler.UPAZILLA_TABLE + "." + LAY_R2_LIST_CODE_COL
                + " LEFT JOIN " + UNIT_TABLE
                + " ON " + REGISTRATION_TABLE + "." + COUNTRY_CODE_COL + "=" + UNIT_TABLE + "." + COUNTRY_CODE
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL + "=" + UNIT_TABLE + "." + LAY_R1_LIST_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL + "=" + UNIT_TABLE + "." + LAY_R2_LIST_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + UNITE_NAME_COL + "=" + UNIT_TABLE + "." + LAY_R3_LIST_CODE_COL
                + " LEFT JOIN " + SQLiteHandler.VILLAGE_TABLE
                + " ON " + REGISTRATION_TABLE + "." + COUNTRY_CODE_COL + "=" + SQLiteHandler.VILLAGE_TABLE + "." + COUNTRY_CODE
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL + "=" + SQLiteHandler.VILLAGE_TABLE + "." + LAY_R1_LIST_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL + "=" + SQLiteHandler.VILLAGE_TABLE + "." + LAY_R2_LIST_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + UNITE_NAME_COL + "=" + SQLiteHandler.VILLAGE_TABLE + "." + LAY_R3_LIST_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL + "=" + SQLiteHandler.VILLAGE_TABLE + "." + LAY_R4_LIST_CODE_COL
                + " LEFT JOIN " + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE
                + " ON " + REGISTRATION_TABLE + "." + COUNTRY_CODE_COL + "=" + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE + "." + COUNTRY_CODE
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL + "=" + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE + "." + LAY_R1_LIST_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL + "=" + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE + "." + LAY_R2_LIST_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + UNITE_NAME_COL + "=" + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE + "." + LAY_R3_LIST_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL + "=" + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE + "." + LAY_R4_LIST_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.REGN_ADDRESS_LOOKUP_CODE_COL + " = " + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE + "." + SQLiteHandler.REGN_ADDRESS_LOOKUP_CODE_COL

                + " WHERE " +// + LAY_R4_LIST_CODE_COL + "='" + ext_village +" AND "+
                REGISTRATION_TABLE + "." + COUNTRY_CODE_COL + " || '' || "
                + REGISTRATION_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL + " || '' || "
                + REGISTRATION_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL + " || '' || "
                + REGISTRATION_TABLE + "." + UNITE_NAME_COL + " || '' || "
                + REGISTRATION_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL + " = '" + ext_village + "' "
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.PID_COL + " LIKE '%" + hhId + "%' "
                + " GROUP BY " + REGISTRATION_TABLE + "." + SQLiteHandler.ID_COL /// this GROUP BY PREVENT TO SHOW DUPLICATED VALUES // Faisal Mohammad  @email:nirzon192@gmail.com @email:nirzon192@gmail.com @email:nirzon192@gmail.com @email:nirzon192@gmail.commodify
                + " ORDER BY " + REGISTRATION_TABLE + "." + SQLiteHandler.ID_COL + " DESC";
        Log.d("MAR", sql);
        return sql;

    }


    public static String getSingleRegisteredData_sql(String cCode, String layR1Code, String layR2Code, String layR3Code, String layR4Code, final String hhId) {


        return "SELECT "
                + REGISTRATION_TABLE + "." + SQLiteHandler.ID_COL + ", "
                + REGISTRATION_TABLE + "." + SQLiteHandler.REG_DATE_COL + ", "
                + REGISTRATION_TABLE + "." + COUNTRY_CODE + ", "
                + REGISTRATION_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL + " AS R_District, "
                + REGISTRATION_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL + " AS R_Upazilla, "
                + REGISTRATION_TABLE + "." + UNITE_NAME_COL + " AS R_Union, "
                + REGISTRATION_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL + " AS R_Village, "
                + SQLiteHandler.COUNTRY_TABLE + "." + SQLiteHandler.COUNTRY_NAME + ", "
                + SQLiteHandler.DISTRICT_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL + ", "
                + SQLiteHandler.UPAZILLA_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL + ","
                + UNIT_TABLE + "." + UNITE_NAME_COL + ", "
                + SQLiteHandler.VILLAGE_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL + ","
                + REGISTRATION_TABLE + "." + SQLiteHandler.PID_COL + ","
                + REGISTRATION_TABLE + "." + SQLiteHandler.PNAME_COL + ","
                + "(" + " CASE WHEN " + SQLiteHandler.SEX_COL + "==" + "'F'" + " THEN " + "'Female'" + " ELSE " + "'Male'" + " END " + ")  AS Sex" + ","
                + REGISTRATION_TABLE + "." + SQLiteHandler.HH_SIZE + ","
                + REGISTRATION_TABLE + "." + SQLiteHandler.LATITUDE_COL + ","
                + REGISTRATION_TABLE + "." + SQLiteHandler.LONGITUDE_COL + ","
                + REGISTRATION_TABLE + "." + SQLiteHandler.AG_LAND + ","
                + "(" + " CASE WHEN " + SQLiteHandler.V_STATUS + "==" + "'Y'" + " THEN " + "'Yes'" + " ELSE " + "'No'" + " END " + ") AS VStatus" + ","
                + "(" + " CASE WHEN " + SQLiteHandler.M_STATUS + "==" + "'Y'" + " THEN " + "'Yes'" + " ELSE " + "'No'" + " END " + ") AS MStatus" + ","
                + REGISTRATION_TABLE + "." + SQLiteHandler.ENTRY_BY + ","
                + REGISTRATION_TABLE + "." + SQLiteHandler.ENTRY_DATE + " , "
                + REGISTRATION_TABLE + "." + SQLiteHandler.VSLA_GROUP + " , "
                + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE + "." + SQLiteHandler.REGN_ADDRESS_LOOKUP_NAME_COL + " as addname , "//25
                + REGISTRATION_TABLE + "." + SQLiteHandler.REGN_ADDRESS_LOOKUP_CODE_COL + " as addcode "//26
                + " FROM " + REGISTRATION_TABLE
                + " LEFT JOIN " + SQLiteHandler.COUNTRY_TABLE
                + " ON " + REGISTRATION_TABLE + "." + COUNTRY_CODE_COL + "=" + SQLiteHandler.COUNTRY_TABLE + "." + COUNTRY_CODE
                + " LEFT JOIN " + SQLiteHandler.DISTRICT_TABLE
                + " ON " + REGISTRATION_TABLE + "." + COUNTRY_CODE_COL + "=" + SQLiteHandler.DISTRICT_TABLE + "." + COUNTRY_CODE
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL + "=" + SQLiteHandler.DISTRICT_TABLE + "." + LAY_R1_LIST_CODE_COL
                + " LEFT JOIN " + SQLiteHandler.UPAZILLA_TABLE
                + " ON " + REGISTRATION_TABLE + "." + COUNTRY_CODE_COL + "=" + SQLiteHandler.UPAZILLA_TABLE + "." + COUNTRY_CODE
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL + "=" + SQLiteHandler.UPAZILLA_TABLE + "." + LAY_R1_LIST_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL + "=" + SQLiteHandler.UPAZILLA_TABLE + "." + LAY_R2_LIST_CODE_COL
                + " LEFT JOIN " + UNIT_TABLE
                + " ON " + REGISTRATION_TABLE + "." + COUNTRY_CODE_COL + "=" + UNIT_TABLE + "." + COUNTRY_CODE
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL + "=" + UNIT_TABLE + "." + LAY_R1_LIST_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL + "=" + UNIT_TABLE + "." + LAY_R2_LIST_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + UNITE_NAME_COL + "=" + UNIT_TABLE + "." + LAY_R3_LIST_CODE_COL
                + " LEFT JOIN " + SQLiteHandler.VILLAGE_TABLE
                + " ON " + REGISTRATION_TABLE + "." + COUNTRY_CODE_COL + "=" + SQLiteHandler.VILLAGE_TABLE + "." + COUNTRY_CODE
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL + "=" + SQLiteHandler.VILLAGE_TABLE + "." + LAY_R1_LIST_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL + "=" + SQLiteHandler.VILLAGE_TABLE + "." + LAY_R2_LIST_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + UNITE_NAME_COL + "=" + SQLiteHandler.VILLAGE_TABLE + "." + LAY_R3_LIST_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL + "=" + SQLiteHandler.VILLAGE_TABLE + "." + LAY_R4_LIST_CODE_COL
                + " LEFT JOIN " + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE
                + " ON " + REGISTRATION_TABLE + "." + COUNTRY_CODE_COL + "=" + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE + "." + COUNTRY_CODE
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL + "=" + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE + "." + LAY_R1_LIST_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL + "=" + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE + "." + LAY_R2_LIST_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + UNITE_NAME_COL + "=" + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE + "." + LAY_R3_LIST_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL + "=" + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE + "." + LAY_R4_LIST_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.REGN_ADDRESS_LOOKUP_CODE_COL + " = " + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE + "." + SQLiteHandler.REGN_ADDRESS_LOOKUP_CODE_COL

                + " WHERE " +
                REGISTRATION_TABLE + "." + COUNTRY_CODE_COL + " = '" + cCode + "' AND "
                + REGISTRATION_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL + " = '" + layR1Code + "' AND "
                + REGISTRATION_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL + " = '" + layR2Code + "' AND "
                + REGISTRATION_TABLE + "." + UNITE_NAME_COL + " = '" + layR3Code + "' AND "
                + REGISTRATION_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL + " = '" + layR4Code + "'   "
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.PID_COL + " = '" + hhId + "' ";
        // + " GROUP BY " + SQLiteHandler.REGISTRATION_TABLE + "." + SQLiteHandler.ID_COL /// this GROUP BY PREVENT TO SHOW DUPLICATED VALUES // Faisal Mohammad  @email:nirzon192@gmail.com @email:nirzon192@gmail.com @email:nirzon192@gmail.com @email:nirzon192@gmail.commodify
        //   + " ORDER BY " + SQLiteHandler.REGISTRATION_TABLE + "." + SQLiteHandler.ID_COL + " DESC";
        //   Log.d("MAR",sql);
        // return sql;

    }


    public static String getRegisteredData_ifVillageExt_SearchByName_sql(String ext_village, String hhName) {


        return "SELECT "
                + REGISTRATION_TABLE + "." + SQLiteHandler.ID_COL + ", "
                + REGISTRATION_TABLE + "." + SQLiteHandler.REG_DATE_COL + ", "
                + REGISTRATION_TABLE + "." + COUNTRY_CODE + ", "
                + REGISTRATION_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL + " AS R_District, "
                + REGISTRATION_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL + " AS R_Upazilla, "
                + REGISTRATION_TABLE + "." + UNITE_NAME_COL + " AS R_Union, "
                + REGISTRATION_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL + " AS R_Village, "
                + SQLiteHandler.COUNTRY_TABLE + "." + SQLiteHandler.COUNTRY_NAME + ", "
                + SQLiteHandler.DISTRICT_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL + ", "
                + SQLiteHandler.UPAZILLA_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL + ","
                + UNIT_TABLE + "." + UNITE_NAME_COL + ", "
                + SQLiteHandler.VILLAGE_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL + ","
                + REGISTRATION_TABLE + "." + SQLiteHandler.PID_COL + ","
                + REGISTRATION_TABLE + "." + SQLiteHandler.PNAME_COL + ","
                + "(" + " CASE WHEN " + SQLiteHandler.SEX_COL + "==" + "'F'" + " THEN " + "'Female'" + " ELSE " + "'Male'" + " END " + ")  AS Sex" + ","
                + REGISTRATION_TABLE + "." + SQLiteHandler.HH_SIZE + ","
                + REGISTRATION_TABLE + "." + SQLiteHandler.LATITUDE_COL + ","
                + REGISTRATION_TABLE + "." + SQLiteHandler.LONGITUDE_COL + ","
                + REGISTRATION_TABLE + "." + SQLiteHandler.AG_LAND + ","
                + "(" + " CASE WHEN " + SQLiteHandler.V_STATUS + "==" + "'Y'" + " THEN " + "'Yes'" + " ELSE " + "'No'" + " END " + ") AS VStatus" + ","
                + " " + SQLiteHandler.M_STATUS + " AS MStatus" + ","
                + REGISTRATION_TABLE + "." + SQLiteHandler.ENTRY_BY + ","
                + REGISTRATION_TABLE + "." + SQLiteHandler.ENTRY_DATE + " , "
                + REGISTRATION_TABLE + "." + SQLiteHandler.VSLA_GROUP + " , "
                + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE + "." + SQLiteHandler.REGN_ADDRESS_LOOKUP_NAME_COL + " as addname , "//25
                + REGISTRATION_TABLE + "." + SQLiteHandler.REGN_ADDRESS_LOOKUP_CODE_COL + " as addcode ,"//26
                + REGISTRATION_TABLE + "." + SQLiteHandler.W_RANK_COL + " as wRank ,"//26
                + REGISTRATION_TABLE + "." + SQLiteHandler.M_STATUS + " as status "//26

                + " FROM " + REGISTRATION_TABLE
                + " LEFT JOIN " + SQLiteHandler.COUNTRY_TABLE
                + " ON " + REGISTRATION_TABLE + "." + COUNTRY_CODE_COL + "=" + SQLiteHandler.COUNTRY_TABLE + "." + COUNTRY_CODE
                + " LEFT JOIN " + SQLiteHandler.DISTRICT_TABLE
                + " ON " + REGISTRATION_TABLE + "." + COUNTRY_CODE_COL + "=" + SQLiteHandler.DISTRICT_TABLE + "." + COUNTRY_CODE
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL + "=" + SQLiteHandler.DISTRICT_TABLE + "." + LAY_R1_LIST_CODE_COL
                + " LEFT JOIN " + SQLiteHandler.UPAZILLA_TABLE
                + " ON " + REGISTRATION_TABLE + "." + COUNTRY_CODE_COL + "=" + SQLiteHandler.UPAZILLA_TABLE + "." + COUNTRY_CODE
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL + "=" + SQLiteHandler.UPAZILLA_TABLE + "." + LAY_R1_LIST_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL + "=" + SQLiteHandler.UPAZILLA_TABLE + "." + LAY_R2_LIST_CODE_COL
                + " LEFT JOIN " + UNIT_TABLE
                + " ON " + REGISTRATION_TABLE + "." + COUNTRY_CODE_COL + "=" + UNIT_TABLE + "." + COUNTRY_CODE
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL + "=" + UNIT_TABLE + "." + LAY_R1_LIST_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL + "=" + UNIT_TABLE + "." + LAY_R2_LIST_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + UNITE_NAME_COL + "=" + UNIT_TABLE + "." + LAY_R3_LIST_CODE_COL
                + " LEFT JOIN " + SQLiteHandler.VILLAGE_TABLE
                + " ON " + REGISTRATION_TABLE + "." + COUNTRY_CODE_COL + "=" + SQLiteHandler.VILLAGE_TABLE + "." + COUNTRY_CODE
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL + "=" + SQLiteHandler.VILLAGE_TABLE + "." + LAY_R1_LIST_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL + "=" + SQLiteHandler.VILLAGE_TABLE + "." + LAY_R2_LIST_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + UNITE_NAME_COL + "=" + SQLiteHandler.VILLAGE_TABLE + "." + LAY_R3_LIST_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL + "=" + SQLiteHandler.VILLAGE_TABLE + "." + LAY_R4_LIST_CODE_COL
                + " LEFT JOIN " + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE
                + " ON " + REGISTRATION_TABLE + "." + COUNTRY_CODE_COL + "=" + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE + "." + COUNTRY_CODE
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL + "=" + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE + "." + LAY_R1_LIST_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL + "=" + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE + "." + LAY_R2_LIST_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + UNITE_NAME_COL + "=" + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE + "." + LAY_R3_LIST_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL + "=" + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE + "." + LAY_R4_LIST_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.REGN_ADDRESS_LOOKUP_CODE_COL + " = " + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE + "." + SQLiteHandler.REGN_ADDRESS_LOOKUP_CODE_COL

                + " WHERE " +// + LAY_R4_LIST_CODE_COL + "='" + ext_village +" AND "+
                REGISTRATION_TABLE + "." + COUNTRY_CODE_COL + " || '' || "
                + REGISTRATION_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL + " || '' || "
                + REGISTRATION_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL + " || '' || "
                + REGISTRATION_TABLE + "." + UNITE_NAME_COL + " || '' || "
                + REGISTRATION_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL + " = '" + ext_village + "' "
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.PNAME_COL + " LIKE '%" + hhName + "%' "
                + " GROUP BY " + REGISTRATION_TABLE + "." + SQLiteHandler.ID_COL /// this GROUP BY PREVENT TO SHOW DUPLICATED VALUES // Faisal Mohammad  @email:nirzon192@gmail.com @email:nirzon192@gmail.com @email:nirzon192@gmail.com @email:nirzon192@gmail.commodify
                + " ORDER BY " + REGISTRATION_TABLE + "." + SQLiteHandler.ID_COL + " DESC";
        //   Log.d("MAR",sql);
        // return sql;

    }


    public static String getRegisteredData_ifVillage_NOT_Ext_SelectQuery() {
        return "SELECT "
                + REGISTRATION_TABLE + "." + SQLiteHandler.ID_COL + ", "
                + REGISTRATION_TABLE + "." + SQLiteHandler.REG_DATE_COL + ", "
                + REGISTRATION_TABLE + "." + COUNTRY_CODE + ", "
                + REGISTRATION_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL + " AS R_District, "
                + REGISTRATION_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL + " AS R_Upazilla, "
                + REGISTRATION_TABLE + "." + UNITE_NAME_COL + " AS R_Union, "
                + REGISTRATION_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL + " AS R_Village, "

                + SQLiteHandler.COUNTRY_TABLE + "." + SQLiteHandler.COUNTRY_NAME + ", "
                + SQLiteHandler.DISTRICT_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL + ", "
                + SQLiteHandler.UPAZILLA_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL + ","
                + UNIT_TABLE + "." + UNITE_NAME_COL + ", "
                + SQLiteHandler.VILLAGE_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL + ","

                + REGISTRATION_TABLE + "." + SQLiteHandler.PID_COL + ","
                + REGISTRATION_TABLE + "." + SQLiteHandler.PNAME_COL + ","
                + "(" + " CASE WHEN " + SQLiteHandler.SEX_COL + "==" + "'F'" + " THEN " + "'Female'" + " ELSE " + "'Male'" + " END " + ") AS Sex" + ","
                + REGISTRATION_TABLE + "." + SQLiteHandler.HH_SIZE + ","
                + REGISTRATION_TABLE + "." + SQLiteHandler.LATITUDE_COL + ","
                + REGISTRATION_TABLE + "." + SQLiteHandler.LONGITUDE_COL + ","
                + REGISTRATION_TABLE + "." + SQLiteHandler.AG_LAND + ","
                + "(" + " CASE WHEN " + SQLiteHandler.V_STATUS + "==" + "'Y'" + " THEN " + "'Yes'" + " ELSE " + "'No'" + " END " + ") AS VStatus" + ","
                + "  " + SQLiteHandler.M_STATUS + " AS MStatus " + ","
                + REGISTRATION_TABLE + "." + SQLiteHandler.ENTRY_BY + ","
                + REGISTRATION_TABLE + "." + SQLiteHandler.ENTRY_DATE + ","
                + REGISTRATION_TABLE + "." + SQLiteHandler.VSLA_GROUP + " , "
                + " , "
                + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE + "." + SQLiteHandler.REGN_ADDRESS_LOOKUP_NAME_COL + " as addname , "//25
                + REGISTRATION_TABLE + "." + SQLiteHandler.REGN_ADDRESS_LOOKUP_CODE_COL + " as addcode ,"//26
                + REGISTRATION_TABLE + "." + SQLiteHandler.W_RANK_COL + " as wRank ,"//26
                + REGISTRATION_TABLE + "." + SQLiteHandler.M_STATUS + " as status "//26

                + " FROM " + REGISTRATION_TABLE
                + " LEFT JOIN " + SQLiteHandler.COUNTRY_TABLE
                + " ON " + REGISTRATION_TABLE + "." + COUNTRY_CODE_COL + "=" + SQLiteHandler.COUNTRY_TABLE + "." + COUNTRY_CODE
                + " LEFT JOIN " + SQLiteHandler.DISTRICT_TABLE

                + " ON " + REGISTRATION_TABLE + "." + COUNTRY_CODE_COL + "=" + SQLiteHandler.DISTRICT_TABLE + "." + COUNTRY_CODE
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL + "=" + SQLiteHandler.DISTRICT_TABLE + "." + LAY_R1_LIST_CODE_COL
                + " LEFT JOIN " + SQLiteHandler.UPAZILLA_TABLE
                + " ON " + REGISTRATION_TABLE + "." + COUNTRY_CODE_COL + "=" + SQLiteHandler.UPAZILLA_TABLE + "." + COUNTRY_CODE
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL + "=" + SQLiteHandler.UPAZILLA_TABLE + "." + LAY_R1_LIST_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL + "=" + SQLiteHandler.UPAZILLA_TABLE + "." + LAY_R2_LIST_CODE_COL
                + " LEFT JOIN " + UNIT_TABLE
                + " ON " + REGISTRATION_TABLE + "." + COUNTRY_CODE_COL + "=" + UNIT_TABLE + "." + COUNTRY_CODE
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL + "=" + UNIT_TABLE + "." + LAY_R1_LIST_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL + "=" + UNIT_TABLE + "." + LAY_R2_LIST_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + UNITE_NAME_COL + "=" + UNIT_TABLE + "." + LAY_R3_LIST_CODE_COL
                + " LEFT JOIN " + SQLiteHandler.VILLAGE_TABLE
                + " ON " + REGISTRATION_TABLE + "." + COUNTRY_CODE_COL + "=" + SQLiteHandler.VILLAGE_TABLE + "." + COUNTRY_CODE
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL + "=" + SQLiteHandler.VILLAGE_TABLE + "." + LAY_R1_LIST_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL + "=" + SQLiteHandler.VILLAGE_TABLE + "." + LAY_R2_LIST_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + UNITE_NAME_COL + "=" + SQLiteHandler.VILLAGE_TABLE + "." + LAY_R3_LIST_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL + "=" + SQLiteHandler.VILLAGE_TABLE + "." + LAY_R4_LIST_CODE_COL
                + " LEFT JOIN " + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE
                + " ON " + REGISTRATION_TABLE + "." + COUNTRY_CODE_COL + "=" + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE + "." + COUNTRY_CODE
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL + "=" + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE + "." + LAY_R1_LIST_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL + "=" + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE + "." + LAY_R2_LIST_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + UNITE_NAME_COL + "=" + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE + "." + LAY_R3_LIST_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL + "=" + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE + "." + LAY_R4_LIST_CODE_COL
                + " AND " + REGISTRATION_TABLE + "." + SQLiteHandler.REGN_ADDRESS_LOOKUP_CODE_COL + " = " + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE + "." + SQLiteHandler.REGN_ADDRESS_LOOKUP_CODE_COL

                + " GROUP BY " + REGISTRATION_TABLE + "." + SQLiteHandler.ID_COL  /// this GROUP BY PREVENT TO SHOW DUPLICATED VALUES // Faisal Mohammad  @email:nirzon192@gmail.com @email:nirzon192@gmail.com @email:nirzon192@gmail.com @email:nirzon192@gmail.commodify
                + " ORDER BY " + REGISTRATION_TABLE + "." + SQLiteHandler.ID_COL + " DESC";
    }


    public static String getSrvSpecificByMemberId_SelectQuery(String cCode, String donorCode, String awardCode
            , String programCode, String srvCode, String opCode, String opMonthCode, String srvCenterCode
            , String fdpCode, String mem15Id) {
        return "SELECT "
                + SQLiteHandler.BABY_STATUS_COL
                + " , " + GMP_ATTENDACE_COL
                + " , " + WEIGHT_STATUS_COL
                + " , " + NUT_ATTENDANCE_COL
                + " , " + VITA_UNDER5_COL
                + " , " + EXCLUSIVE_CURRENTLYBF_COL
                + " , " + DATE_COMPFEEDING_COL
                + " , " + CMAMREF_COL
                + " , " + CMAMADD_COL
                + " , " + ANCVISIT_COL
                + " , " + PNCVISIT_2D_COL
                + " , " + PNCVISIT_1W_COL
                + " , " + PNCVISIT_6W_COL
                + " , " + DELIVERY_STAFF_1_COL
                + " , " + DELIVERY_STAFF_2_COL
                + " , " + DELIVERY_STAFF_3_COL
                + " , " + HOME_SUPPORT24H_1D_COL
                + " , " + HOME_SUPPORT24H_2D_COL
                + " , " + HOME_SUPPORT24H_3D_COL
                + " , " + HOME_SUPPORT24H_8D_COL
                + " , " + HOME_SUPPORT24H_14D_COL
                + " , " + HOME_SUPPORT24H_21D_COL
                + " , " + HOME_SUPPORT24H_30D_COL
                + " , " + HOME_SUPPORT24H_60D_COL
                + " , " + HOME_SUPPORT24H_90D_COL

                + " , " + HOME_SUPPORT48H_1D_COL
                + " , " + HOME_SUPPORT48H_3D_COL
                + " , " + HOME_SUPPORT48H_8D_COL
                + " , " + HOME_SUPPORT48H_30D_COL
                + " , " + HOME_SUPPORT48H_60D_COL
                + " , " + HOME_SUPPORT48H_90D_COL

                + " , " + MATERNAL_BLEEDING_COL
                + " , " + MATERNAL_SEIZURE_COL
                + " , " + MATERNAL_INFECTION_COL
                + " , " + MATERNAL_PROLONGEDLABOR_COL
                + " , " + MATERNAL_OBSTRUCTEDLABOR_COL
                + " , " + MATERNAL_PPRM_COL
                + " , " + NBORN_ASPHYXIA_COL
                + " , " + NBORN_SEPSIS_COL
                + " , " + NBORN_HYPOTHERMIA_COL
                + " , " + NBORN_HYPERTHERMIA_COL
                + " , " + NBORN_NOSUCKLING_COL
                + " , " + NBORN_JAUNDICE_COL
                + " , " + CHILD_DIARRHEA_COL
                + " , " + CHILD_PNEUMONIA_COL
                + " , " + CHILD_FEVER_COL
                + " , " + CHILD_CEREBRALPALSY_COL
                + " , " + IMMU_POLIO_COL
                + " , " + IMMU_BCG_COL
                + " , " + IMMU_MEASLES_COL
                + " , " + IMMU_DPT_HIB_COL
                + " , " + IMMU_LOTTA_COL
                + " , " + IMMU_OTHER_COL
                + " , " + FPCOUNSEL_MALECONDOM_COL
                + " , " + FPCOUNSEL_FEMALECONDOM_COL
                + " , " + FPCOUNSEL_PILL_COL
                + " , " + FPCOUNSEL_DEPO_COL
                + " , " + FPCOUNSEL_LONGPARMANENT_COL
                + " , " + FPCOUNSEL_NOMETHOD_COL

                + " , " + SQLiteHandler.CROP_CODE_COL
                + " , " + SQLiteHandler.LOAN_SOURCE_COL
                + " , " + SQLiteHandler.LOAN_AMT_COL
                + " , " + SQLiteHandler.ANIMAL_CODE_COL
                + " , " + SQLiteHandler.LEAD_CODE_COL

                + " FROM " + SQLiteHandler.SERVICE_SPECIFIC_TABLE
                + " WHERE " + COUNTRY_CODE_COL + " = '" + cCode + "'"
                + " AND  " + DONOR_CODE_COL + " = '" + donorCode + "'"
                + " AND  " + AWARD_CODE_COL + " = '" + awardCode + "'"
                + " AND  " + PROGRAM_CODE_COL + " = '" + programCode + "'"
                + " AND  " + SERVICE_CODE_COL + " = '" + srvCode + "'"
                + " AND  " + SQLiteHandler.OPERATION_CODE_COL + " = '" + opCode + "'"
                + " AND  " + OP_MONTH_CODE_COL + " = '" + opMonthCode + "'"
                + " AND  " + SQLiteHandler.SERVICE_CENTER_CODE_COL + " = '" + srvCenterCode + "'"
                + " AND  " + FDP_CODE_COL + " = '" + fdpCode + "'"
                + " AND  " + LAY_R1_LIST_CODE_COL + " || '' || " + LAY_R2_LIST_CODE_COL + " || '' || " + LAY_R3_LIST_CODE_COL
                + " || '' || " + LAY_R4_LIST_CODE_COL + " || '' || " + HHID_COL + " || '' || " + HH_MEM_ID + " = '" + mem15Id + "'";

    }

    public static String getServiceDateRange_selectQuery(String cCode, String srvOpMonthCode) {
        return "SELECT  " + SQLiteHandler.OPERATION_CODE_COL
                + " , " + OP_MONTH_CODE_COL
                + " , " + SQLiteHandler.USA_START_DATE
                + " , " + SQLiteHandler.USA_END_DATE
                + " , " + SQLiteHandler.MONTH_LABEL
                + " FROM " + SQLiteHandler.OP_MONTH_TABLE
                + " WHERE " + COUNTRY_CODE_COL + " = '" + cCode + "' "
                + " AND " + STATUS + "= 'A'"
                + " AND " + SQLiteHandler.OPERATION_CODE_COL + " = '2'"
                + " AND " + OP_MONTH_CODE_COL + " = '" + srvOpMonthCode + "'";
    }


    public static String getProgramGraduationDateOfMember_sql(String cCode, String disCode, String upCode, String unCode, String vCode, String hhID, String memID, String donorCode, String awardCode, String progCode) {
        return "SELECT  MAX( " + SQLiteHandler.GRD_DATE_COL + " )  AS grdDate"
                + " FROM " + REG_N_ASSIGN_PROG_SRV_TABLE
                + " WHERE " + COUNTRY_CODE_COL + " = '" + cCode + "' "
                + " AND " + LAY_R1_LIST_CODE_COL + " = '" + disCode + "' "
                + " AND " + LAY_R2_LIST_CODE_COL + " = '" + upCode + "' "
                + " AND " + LAY_R3_LIST_CODE_COL + " = '" + unCode + "' "
                + " AND " + LAY_R4_LIST_CODE_COL + " = '" + vCode + "' "
                + " AND " + HHID_COL + " = '" + hhID + "' "
                + " AND " + HH_MEM_ID + " = '" + memID + "' "
                + " AND " + DONOR_CODE_COL + " = '" + donorCode + "' "
                + " AND " + AWARD_CODE_COL + " = '" + awardCode + "' "
                + " AND " + PROGRAM_CODE_COL + " = '" + progCode + "' ";
    }

    public static String checkAdmCountryProgramsVoucherFlag_sql(String cCode, String donorCode, String awardCode, String progCode) {
        return "SELECT " + SQLiteHandler.VOUCHER_FLAG + " FROM " + SQLiteHandler.COUNTRY_PROGRAM_TABLE +
                " WHERE " + COUNTRY_CODE_COL + " = '" + cCode + "'" +
                " AND " + DONOR_CODE_COL + " = '" + donorCode + "'" +
                " AND " + AWARD_CODE_COL + " = '" + awardCode + "'" +
                " AND " + PROGRAM_CODE_COL + " = '" + progCode + "'";
    }


    public static String checkAdmCountryProgramsNoneFoodFlag_sql(String cCode, String donorCode, String awardCode, String progCode, String srvCode) {
        return "SELECT " + SQLiteHandler.NON_FOOD_FLAG + " FROM " + SQLiteHandler.COUNTRY_PROGRAM_TABLE
                + " WHERE " + COUNTRY_CODE_COL + " = '" + cCode + "'"
                + " AND " + DONOR_CODE_COL + " = '" + donorCode + "'"
                + " AND " + AWARD_CODE_COL + " = '" + awardCode + "'"
                + " AND " + PROGRAM_CODE_COL + " = '" + progCode + "'"
                + " AND " + SERVICE_CODE_COL + " = '" + srvCode + "'"
                ;
    }

    public static String get_VOItmUnitMeas(String measRCode) {
        return " SELECT " + VOUCHER_ITEM__MEAS_TABLE + "." + UNITE_MEAS_COL
                + " FROM " + VOUCHER_ITEM__MEAS_TABLE
                + " WHERE " + VOUCHER_ITEM__MEAS_TABLE + "." + MEAS_R_CODE_COL + " = " + measRCode;

    }


    public static String getServExtentedItemName(final String cCode, final String donorCode, final String awardCode, final String opMCode, final String programCode) {
        return " SELECT " +
                SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + VOUCHER_ITEM_SPEC_COL + " AS voucherID" +

                " , " + VOUCHER_ITEM_TABLE + "." + ITEM_NAME_COL

                + " || '-' || " + VOUCHER_ITEM__MEAS_TABLE + "." + UNITE_MEAS_COL
                + " ||' '||  " + VOUCHER_ITEM__MEAS_TABLE + "." + MEASE_TITLE_COL + " AS item" +

                " FROM " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE +
                "  INNER JOIN " + VOUCHER_ITEM_TABLE
                + " ON " + VOUCHER_ITEM_TABLE + "." + CATEGORY_CODE_COL + " || " + VOUCHER_ITEM_TABLE + "." + ITEM_CODE_COL + " = SUBSTR(" + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + VOUCHER_ITEM_SPEC_COL + ",0,8) " +
                "  INNER JOIN " + VOUCHER_ITEM__MEAS_TABLE
                + " ON " + VOUCHER_ITEM__MEAS_TABLE + "." + MEAS_R_CODE_COL + " = SUBSTR(" + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + VOUCHER_ITEM_SPEC_COL + ",8) " +
                "  INNER JOIN " + DISTRIBUTION_EXTENDED_TABLE
                + " ON " + DISTRIBUTION_EXTENDED_TABLE + "." + COUNTRY_CODE_COL + " = " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + COUNTRY_CODE_COL
                + " AND " + DISTRIBUTION_EXTENDED_TABLE + "." + DONOR_CODE_COL + " = " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + DONOR_CODE_COL
                + " AND " + DISTRIBUTION_EXTENDED_TABLE + "." + AWARD_CODE_COL + " = " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + AWARD_CODE_COL
                + " AND " + DISTRIBUTION_EXTENDED_TABLE + "." + PROGRAM_CODE_COL + " = " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + PROGRAM_CODE_COL
                + " AND " + DISTRIBUTION_EXTENDED_TABLE + "." + SERVICE_CODE_COL + " = " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + SERVICE_CODE_COL
                + " AND " + DISTRIBUTION_EXTENDED_TABLE + "." + VOUCHER_ITEM_SPEC_COL + " = " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + VOUCHER_ITEM_SPEC_COL


                + " WHERE  " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + COUNTRY_CODE_COL + " = '" + cCode + "' "
                + " AND " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + DONOR_CODE_COL + " = '" + donorCode + "' "
                + " AND " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + AWARD_CODE_COL + " = '" + awardCode + "'"
                + " AND " + DISTRIBUTION_EXTENDED_TABLE + "." + OP_MONTH_CODE_COL + " = '" + opMCode + "'  "
                + " AND " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + PROGRAM_CODE_COL + " = '" + programCode + "'  "
                + " GROUP BY "
                + DISTRIBUTION_EXTENDED_TABLE + "." + DONOR_CODE_COL
                + " , " + DISTRIBUTION_EXTENDED_TABLE + "." + AWARD_CODE_COL
                + " , " + DISTRIBUTION_EXTENDED_TABLE + "." + PROGRAM_CODE_COL
                + " , " + DISTRIBUTION_EXTENDED_TABLE + "." + VOUCHER_ITEM_SPEC_COL;

    }


    public static String getDistExtentedItemName(final String cCode, final String donorCode, final String awardCode, final String opMCode, final String programCode) {
        return " SELECT " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + VOUCHER_ITEM_SPEC_COL + " AS voucherID "
                + " , " + VOUCHER_ITEM_TABLE + "." + ITEM_NAME_COL
                + " || '-' || " + VOUCHER_ITEM__MEAS_TABLE + "." + UNITE_MEAS_COL
                + " ||' '||  " + VOUCHER_ITEM__MEAS_TABLE + "." + MEASE_TITLE_COL + " AS item "
                + " FROM " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE
                + "  INNER JOIN " + VOUCHER_ITEM_TABLE
                + " ON " + VOUCHER_ITEM_TABLE + "." + CATEGORY_CODE_COL + " || " + VOUCHER_ITEM_TABLE + "." + ITEM_CODE_COL + " = SUBSTR(" + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + VOUCHER_ITEM_SPEC_COL + ",0,8) "
                + "  INNER JOIN " + VOUCHER_ITEM__MEAS_TABLE
                + " ON " + VOUCHER_ITEM__MEAS_TABLE + "." + MEAS_R_CODE_COL + " = SUBSTR(" + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + VOUCHER_ITEM_SPEC_COL + ",8) " +
                "  INNER JOIN " + SERVICE_EXTENDED_TABLE
                + " ON " + SERVICE_EXTENDED_TABLE + "." + COUNTRY_CODE_COL + " = " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + COUNTRY_CODE_COL
                + " AND " + SERVICE_EXTENDED_TABLE + "." + DONOR_CODE_COL + " = " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + DONOR_CODE_COL
                + " AND " + SERVICE_EXTENDED_TABLE + "." + AWARD_CODE_COL + " = " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + AWARD_CODE_COL
                + " AND " + SERVICE_EXTENDED_TABLE + "." + PROGRAM_CODE_COL + " = " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + PROGRAM_CODE_COL
                + " AND " + SERVICE_EXTENDED_TABLE + "." + SERVICE_CODE_COL + " = " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + SERVICE_CODE_COL
                + " AND " + SERVICE_EXTENDED_TABLE + "." + VOUCHER_ITEM_SPEC_COL + " = " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + VOUCHER_ITEM_SPEC_COL +
                " WHERE  " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + COUNTRY_CODE_COL + " = '" + cCode + "' "
                + " AND " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + DONOR_CODE_COL + " = '" + donorCode + "' "
                + " AND " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + AWARD_CODE_COL + " = '" + awardCode + "'"
                + " AND " + SERVICE_EXTENDED_TABLE + "." + OP_MONTH_CODE_COL + " = '" + opMCode + "'  "
                + " AND " + SQLiteHandler.VOUCHER_COUNTRY_PROGRAM_ITEM_TABLE + "." + PROGRAM_CODE_COL + " = '" + programCode + "'  "
                + " GROUP BY " + SERVICE_EXTENDED_TABLE + "." + VOUCHER_ITEM_SPEC_COL;
    }

    public static String getGroupSummaryList_sql(final String cCode) {
        return "SELECT " +

                "   cg." + COUNTRY_CODE_COL + " " +
                " , cg." + DONOR_CODE_COL +
                " , cg." + AWARD_CODE_COL +
                " , cg." + PROGRAM_CODE_COL +
                " , cgc." + GROUP_CAT_CODE_COL +
                " , cgc." + GROUP_CAT_SHORT_NAME_COL +
                " , cg." + GROUP_CODE_COL +
                " , cg." + GROUP_NAME_COL +
                " , cg." + GRP_LAY_R3_LIST_CODE_COL +
                " , ut." + UNITE_NAME_COL +
                " , srv." + SERVICE_SHORT_NAME_COL +

                " , ( select Count(*) from " + REG_N_MEM_PROG_GRP_TABLE + " AS regNgrp "
                + " WHERE  regNgrp." + COUNTRY_CODE_COL + " = cg." + COUNTRY_CODE_COL
                + " AND  " + "regNgrp." + DONOR_CODE_COL + " = cg." + DONOR_CODE_COL
                + " AND  " + "regNgrp." + AWARD_CODE_COL + " = cg." + AWARD_CODE_COL
                + " AND regNgrp." + PROGRAM_CODE_COL + " = cg." + PROGRAM_CODE_COL
                + " AND  " + "regNgrp." + GROUP_CODE_COL + " = cg." + GROUP_CODE_COL


                + " )  AS c " +

                " FROM " + COMMUNITY_GROUP_TABLE + "  AS cg " +

                " LEFT JOIN " + REG_N_MEM_PROG_GRP_TABLE + " AS regG " +
                " ON regG." + COUNTRY_CODE_COL + " = cg." + COUNTRY_CODE_COL + " " +
                " AND regG." + DONOR_CODE_COL + " = cg." + DONOR_CODE_COL +
                " AND regG." + AWARD_CODE_COL + " = cg." + AWARD_CODE_COL +
                " AND regG." + PROGRAM_CODE_COL + " = cg." + PROGRAM_CODE_COL +
                " AND regG." + GROUP_CODE_COL + " = cg." + GROUP_CODE_COL +
                " LEFT JOIN " + COMMUNITY_GROUP_CATEGORY_TABLE + " AS cgc " +
                " ON cg." + COUNTRY_CODE_COL + " = cgc." + COUNTRY_CODE_COL +
                " AND cg." + DONOR_CODE_COL + " = cgc." + DONOR_CODE_COL +
                " AND cg." + AWARD_CODE_COL + " = cgc." + AWARD_CODE_COL +
                " AND cg." + PROGRAM_CODE_COL + " = cgc." + PROGRAM_CODE_COL +
                " AND cg." + GROUP_CAT_CODE_COL + " = cgc." + GROUP_CAT_CODE_COL +
                " LEFT JOIN " + SERVICE_MASTER_TABLE + " AS srv " +
                " ON cg." + PROGRAM_CODE_COL + " = srv. " + PROGRAM_CODE_COL +
                " INNER JOIN " + SELECTED_VILLAGE_TABLE + " AS sv " +
                " ON cg." + GRP_LAY_R1_LIST_CODE_COL + " = sv. " + LAY_R1_LIST_CODE_COL +
                " AND cg." + GRP_LAY_R2_LIST_CODE_COL + " = sv." + LAY_R2_LIST_CODE_COL +
                " AND cg." + GRP_LAY_R3_LIST_CODE_COL + " = sv." + LAY_R3_LIST_CODE_COL +
                " AND regG." + SERVICE_CODE_COL + " = srv." + SERVICE_CODE_COL +
                " INNER JOIN " + UNIT_TABLE + " AS ut " +
                " ON ut." + COUNTRY_CODE_COL + " = cg." + COUNTRY_CODE_COL +
                " AND ut." + LAY_R1_LIST_CODE_COL + " = cg." + GRP_LAY_R1_LIST_CODE_COL +
                " AND  ut." + LAY_R2_LIST_CODE_COL + " = cg." + GRP_LAY_R2_LIST_CODE_COL +
                " AND  ut." + LAY_R3_LIST_CODE_COL + " = cg." + GRP_LAY_R2_LIST_CODE_COL +


                " WHERE cg." + COUNTRY_CODE_COL + " = '" + cCode + "' " +
                " GROUP BY cgc." + GROUP_CAT_CODE_COL + ", cg." + GROUP_CODE_COL;

        // OLD QUERY CONFIRM THE NEW ONE THEN DELETE OLD ONE (BELLOW)
       /* return "SELECT " +

                "   cg." + COUNTRY_CODE_COL + " " +
                " , cg." + DONOR_CODE_COL +
                " , cg." + AWARD_CODE_COL +
                " , cg." + PROGRAM_CODE_COL +
                " , cgc." + GROUP_CAT_CODE_COL +
                " , cgc." + GROUP_CAT_SHORT_NAME_COL +
                " , cg." + GROUP_CODE_COL +
                " , cg." + GROUP_NAME_COL +
                " , srv." + SQLiteHandler.SERVICE_SHORT_NAME_COL +

                " , ( select Count(*) from " + SQLiteHandler.REG_N_MEM_PROG_GRP_TABLE + " AS regNgrp "
                + " WHERE  regNgrp." + COUNTRY_CODE_COL + " = cg." + COUNTRY_CODE_COL
                + " AND  " + "regNgrp." + DONOR_CODE_COL + " = cg." + DONOR_CODE_COL
                + " AND  " + "regNgrp." + AWARD_CODE_COL + " = cg." + AWARD_CODE_COL
                + " AND regNgrp." + PROGRAM_CODE_COL + " = cg." + PROGRAM_CODE_COL
                + " AND  " + "regNgrp." + GROUP_CODE_COL + " = cg." + GROUP_CODE_COL


                + " )  AS c " +

                " FROM " + COMMUNITY_GROUP_TABLE + "  AS cg " +

                " LEFT JOIN " + REG_N_MEM_PROG_GRP_TABLE + " AS regG " +
                " ON regG." + COUNTRY_CODE_COL + " = cg." + COUNTRY_CODE_COL + " " +
                " AND regG." + DONOR_CODE_COL + " = cg." + DONOR_CODE_COL +
                " AND regG." + AWARD_CODE_COL + " = cg." + AWARD_CODE_COL +
                " AND regG." + PROGRAM_CODE_COL + " = cg." + PROGRAM_CODE_COL +
                " AND regG." + GROUP_CODE_COL + " = cg." + GROUP_CODE_COL +
                " LEFT JOIN " + COMMUNITY_GROUP_CATEGORY_TABLE + " AS cgc " +
                " ON cg." + COUNTRY_CODE_COL + " = cgc." + COUNTRY_CODE_COL +
                " AND cg." + DONOR_CODE_COL + " = cgc." + DONOR_CODE_COL +
                " AND cg." + AWARD_CODE_COL + " = cgc." + AWARD_CODE_COL +
                " AND cg." + PROGRAM_CODE_COL + " = cgc." + PROGRAM_CODE_COL +
                " AND cg." + GROUP_CAT_CODE_COL + " = cgc." + GROUP_CAT_CODE_COL +
                " LEFT JOIN " + SERVICE_MASTER_TABLE + " AS srv " +
                " ON cg." + PROGRAM_CODE_COL + " = srv. " + PROGRAM_CODE_COL +
                " AND regG." + SERVICE_CODE_COL + " = srv." + SERVICE_CODE_COL +
                " WHERE cg." + COUNTRY_CODE_COL + " = '" + cCode + "' " +
                " GROUP BY cgc." + GROUP_CAT_CODE_COL + ", cg." + GROUP_CODE_COL;*/
    }

    public static String loadVillageInAssignSummary_sql(String cCode) {
        return "SELECT " + " v." + COUNTRY_CODE_COL + " || '' ||  v." + LAY_R1_LIST_CODE_COL + " || '' || v." + LAY_R2_LIST_CODE_COL + " || '' || v." +
                LAY_R3_LIST_CODE_COL + " || '' || v." + LAY_R4_LIST_CODE_COL + " AS v_code," +
                " v." + SQLiteHandler.VILLAGE_NAME_COL + " AS Vill_Name " +
                     /*   " COUNT("+PID_COL+") AS records"*/" FROM " + SQLiteHandler.VILLAGE_TABLE + " AS v" +
                " LEFT JOIN " + REGISTRATION_TABLE + " AS r" +
                " ON r." + COUNTRY_CODE_COL + "= v." + COUNTRY_CODE_COL
                + " AND " + "r." + SQLiteHandler.DISTRICT_NAME_COL + "= v." + LAY_R1_LIST_CODE_COL
                + " AND " + "r." + SQLiteHandler.UPZILLA_NAME_COL + "= v." + LAY_R2_LIST_CODE_COL
                + " AND " + "r." + UNITE_NAME_COL + "= v." + LAY_R3_LIST_CODE_COL
                + " AND " + "r." + SQLiteHandler.VILLAGE_NAME_COL + "= v." + LAY_R4_LIST_CODE_COL +
                " Inner join " + SQLiteHandler.SELECTED_VILLAGE_TABLE + " AS s"
                + " on " + " s." + COUNTRY_CODE_COL + "= v." + COUNTRY_CODE_COL
                + " AND " + "s." + LAY_R1_LIST_CODE_COL + "= v." + LAY_R1_LIST_CODE_COL
                + " AND " + "s." + LAY_R2_LIST_CODE_COL + "= v." + LAY_R2_LIST_CODE_COL
                + " AND " + "s." + LAY_R3_LIST_CODE_COL + "= v." + LAY_R3_LIST_CODE_COL
                + " AND " + "s." + LAY_R4_LIST_CODE_COL + "= v." + LAY_R4_LIST_CODE_COL +

                " WHERE v." + COUNTRY_CODE_COL + "='" + cCode + "'" + /** send the no of village for selected country added by Faisal Mohammad*/
                "  GROUP BY v." + COUNTRY_CODE_COL + ",v." + LAY_R1_LIST_CODE_COL + ",v." + LAY_R2_LIST_CODE_COL + ",v." + LAY_R3_LIST_CODE_COL + ",v." + LAY_R4_LIST_CODE_COL;

    }


    public static String getIdListInGroupInGroupSummary_sql(String cCode, String donorCode, String awardCode, String prgCode, String grpCode) {


        String getMemberName;
        if (cCode.equals("0004")) {
            getMemberName = " regMem." + SQLiteHandler.MEM_NAME_FIRST_COL +
                    "|| ' ' || " + " regMem." + SQLiteHandler.MEM_NAME_MIDDLE_COL +
                    "|| ' ' || " + " regMem." + SQLiteHandler.MEM_NAME_LAST_COL;

        } else
            getMemberName = " regMem." + SQLiteHandler.MEM_NAME_COL;
        return "SELECT " +


                "  regG." + LAY_R1_LIST_CODE_COL +
                " || '' || regG." + LAY_R2_LIST_CODE_COL +
                " || '' || regG." + LAY_R3_LIST_CODE_COL +
                " || '' || regG." + LAY_R4_LIST_CODE_COL +
                " || '' || regG." + HHID_COL +
                " || '' || regG." + HH_MEM_ID + " AS idMem " +

                " , srv." + SERVICE_SHORT_NAME_COL +
                " , " + getMemberName + " AS memName " +
                " , cg." + GROUP_NAME_COL +
                " FROM " + REG_N_MEM_PROG_GRP_TABLE + " AS regG " +


                " LEFT JOIN " + SERVICE_MASTER_TABLE + " AS srv " +
                " ON regG." + PROGRAM_CODE_COL + " = srv. " + PROGRAM_CODE_COL +
                " AND regG." + SERVICE_CODE_COL + " = srv." + SERVICE_CODE_COL +

                " LEFT JOIN " + REGISTRATION_MEMBER_TABLE + " AS regMem " +

                " ON " + " regG." + COUNTRY_CODE_COL + " = " + " regMem." + COUNTRY_CODE_COL +
                " AND " + " regG." + LAY_R1_LIST_CODE_COL + " = " + " regMem." + DISTRICT_NAME_COL +
                " AND " + " regG." + LAY_R2_LIST_CODE_COL + " = " + " regMem." + UPZILLA_NAME_COL +
                " AND " + " regG." + LAY_R3_LIST_CODE_COL + " = " + " regMem." + UNITE_NAME_COL +
                " AND " + " regG." + LAY_R4_LIST_CODE_COL + " = " + " regMem." + VILLAGE_NAME_COL +
                " AND " + " regG." + HHID_COL + " = " + " regMem." + HHID_COL +
                " AND " + " regG." + HH_MEM_ID + " = " + " regMem." + HH_MEM_ID + " " +

                " INNER JOIN " + COMMUNITY_GROUP_TABLE + " AS cg "
                + " ON  cg." + COUNTRY_CODE_COL + " = " + " regG." + COUNTRY_CODE_COL
                + " AND cg." + AWARD_CODE_COL + " = " + " regG." + AWARD_CODE_COL
                + " AND cg." + DONOR_CODE_COL + " = " + " regG." + DONOR_CODE_COL
                + " AND cg." + PROGRAM_CODE_COL + " = " + " regG." + PROGRAM_CODE_COL
                + " AND cg." + GROUP_CODE_COL + " = " + " regG." + GROUP_CODE_COL

                + " AND cg." + GRP_LAY_R1_LIST_CODE_COL + " = " + " regG." + GRP_LAY_R1_LIST_CODE_COL
                + " AND cg." + GRP_LAY_R2_LIST_CODE_COL + " = " + " regG." + GRP_LAY_R2_LIST_CODE_COL
                + " AND cg." + GRP_LAY_R3_LIST_CODE_COL + " = " + " regG." + GRP_LAY_R3_LIST_CODE_COL +


                " WHERE regG." + COUNTRY_CODE_COL + " = '" + cCode + "' " +
                " AND regG." + DONOR_CODE_COL + " = '" + donorCode + "' " +
                " AND regG." + AWARD_CODE_COL + " = '" + awardCode + "' " +
                " AND regG." + PROGRAM_CODE_COL + " = '" + prgCode + "' " +
                " AND regG." + GROUP_CODE_COL + " = '" + grpCode + "' " +
                " GROUP BY idMem "
                ;

    }

    public static String loadProgramWhereMemberAreAssigned_sql(final String idcCode, final String donorCode, final String awardCode, final String memId) {
        return "SELECT " + ADM_PROGRAM_MASTER_TABLE + "." + PROGRAM_CODE_COL
                + " , " + ADM_PROGRAM_MASTER_TABLE + "." + PROGRAM_SHORT_NAME_COL
                + " FROM " + ADM_PROGRAM_MASTER_TABLE
                + " INNER JOIN " + ADM_COUNTRY_AWARD_TABLE
                + " ON " + ADM_COUNTRY_AWARD_TABLE + "." + DONOR_CODE_COL + " = " + ADM_PROGRAM_MASTER_TABLE + "." + DONOR_CODE_COL
                + " AND " + ADM_COUNTRY_AWARD_TABLE + "." + AWARD_CODE_COL + " = " + ADM_PROGRAM_MASTER_TABLE + "." + AWARD_CODE_COL
                + " INNER JOIN " + REG_N_ASSIGN_PROG_SRV_TABLE + " AS regAss "
                + " ON regAss." + PROGRAM_CODE_COL + " = " + ADM_PROGRAM_MASTER_TABLE + "." + PROGRAM_CODE_COL
                + " WHERE " + ADM_PROGRAM_MASTER_TABLE + "." + AWARD_CODE_COL + "='" + awardCode + "'"
                + " AND " + ADM_PROGRAM_MASTER_TABLE + "." + DONOR_CODE_COL + "='" + donorCode + "'"
                + " AND regAss." + LAY_R1_LIST_CODE_COL
                + " || '' || regAss." + LAY_R2_LIST_CODE_COL
                + " || '' || regAss." + LAY_R3_LIST_CODE_COL
                + " || '' || regAss." + LAY_R4_LIST_CODE_COL
                + " || '' || regAss." + HHID_COL
                + " || '' || regAss." + HH_MEM_ID + " = '" + memId + "'";

    }

    public static String loadOrganization_sql(final String cCode, final String donorCode, final String awardCode) {
        return "SELECT progOR." + ORG_CODE_COL +
                " ,  pOrg." + ORGANIZATION_NAME + " " +
                "                                FROM " + PROGRAM_ORGANIZATION_ROLE_TABLE + " AS progOR "
                + "                               INNER JOIN " +
                "                                " + PROGRAM_ORGANIZATION_NAME_TABLE + " AS pOrg " +
                "                               ON progOR." + ORG_CODE_COL + " = pOrg." + ORG_CODE_COL + "  " +
                "                                WHERE (progOR." + COUNTRY_CODE_COL + " = '" + cCode + "')" +
                "                                AND (progOR." + DONOR_CODE_COL + " = '" + donorCode + "') " +
                "                                AND (progOR." + AWARD_CODE_COL + " = '" + awardCode + "') " +
                "                                AND (progOR." + IMP_Y_N_COL + " = 'Y')" +
                " GROUP BY progOR." + ORG_CODE_COL +
                "                                ORDER BY pOrg." + ORGANIZATION_NAME;

    }

    public static String editMemberIn_RegNmemProgGroup_where_sql(String cCode, String donorCode, String awardCode, String disttCode, String upCode, String unCode, String vCode, String hhID, String memID
            , String progCode, String srvCode) {
        return COUNTRY_CODE_COL + "= '" + cCode + "' "
                + " AND " + DONOR_CODE_COL + "= '" + donorCode + "'"
                + " AND " + AWARD_CODE_COL + "= '" + awardCode + "'"
                + " AND " + LAY_R1_LIST_CODE_COL + "= '" + disttCode + "'"
                + " AND " + LAY_R2_LIST_CODE_COL + "= '" + upCode + "'"
                + " AND " + LAY_R3_LIST_CODE_COL + "= '" + unCode + "'"
                + " AND " + LAY_R4_LIST_CODE_COL + "= '" + vCode + "'"
                + " AND " + HHID_COL + "= '" + hhID + "'"
                + " AND " + HH_MEM_ID + "=  '" + memID + "'"
                + " AND " + PROGRAM_CODE_COL + "=  '" + progCode + "'"
                + " AND " + SERVICE_CODE_COL + "=  '" + srvCode + "'";
    }

    public static String getCommunityGroupList_sql(final String cCode, final String donorCode, final String awardCode, final String progCode, final String groupName) {
        return "SELECT " +
                " cg." + GROUP_NAME_COL

                + " , cg." + GROUP_CODE_COL
                //  + " , cg." + SQLiteHandler.LAY_R2_LIST_CODE_COL
                + " , un." + UNITE_NAME_COL
                + " , un." + LAY_R3_LIST_CODE_COL
                + " , un." + LAY_R2_LIST_CODE_COL
                + " , un." + LAY_R1_LIST_CODE_COL

                + " , " + " cgc." + GROUP_CAT_CODE_COL
                + " , " + " cgc." + GROUP_CAT_NAME_COL
                + " , " + " cgc." + GROUP_CAT_SHORT_NAME_COL
                + " , " + " pm." + PROGRAM_SHORT_NAME_COL
                + " , " + " pm." + PROGRAM_CODE_COL
                + " , " + " pm." + PROGRAM_NAME_COL
                + " , " + " don." + DONOR_NAME_COL + "|| '-' || awd." + AWARD_SHORT_NAME_COL + " AS awardName "
                + " , " + " cgc." + AWARD_CODE_COL

                + " , " + " grpDetail." + ORG_CODE_COL
                + " , " + " org." + ORGANIZATION_NAME
                + " , " + " grpDetail." + STAFF_CODE_COL
                + " , " + " staff." + STAFF_NAME_COL

                + " , " + " grpDetail." + LAND_SIZE_UNDER_IRRIGATION_COL
                + " , " + " grpDetail." + IRRIGATION_SYSTEM_USED_COL
                + " , " + " grpDetail." + FUND_SUPPORT_COL
                + " , " + " grpDetail." + ACTIVE_COL
                + " , " + " grpDetail." + REP_NAME_COL
                + " , " + " grpDetail." + REP_PHONE_NUMBER_COL
                + " , " + " grpDetail." + FORMATION_DATE_COL
                + " , " + " grpDetail." + TYPE_OF_GROUP
                + " , " + " grpDetail." + STATUS
                + " , " + " grpDetail." + PROJECT_NO_COL
                + " , " + " grpDetail." + PROJECT_TITLE

                + " FROM " + COMMUNITY_GROUP_CATEGORY_TABLE + " AS cgc "

                + " INNER JOIN "
                + COMMUNITY_GROUP_TABLE + " AS cg "
                + " ON cgc." + COUNTRY_CODE_COL + " =     cg." + COUNTRY_CODE_COL
                + " AND cgc." + DONOR_CODE_COL + " =      cg." + DONOR_CODE_COL
                + " AND cgc." + AWARD_CODE_COL + " =      cg." + AWARD_CODE_COL
                + " AND cgc." + PROGRAM_CODE_COL + " =    cg." + PROGRAM_CODE_COL
                + " AND cgc." + GROUP_CAT_CODE_COL + " =  cg." + GROUP_CAT_CODE_COL

                + " INNER JOIN " +
                ADM_PROGRAM_MASTER_TABLE + " AS pm "
                + " ON cgc." + DONOR_CODE_COL + " = pm." + DONOR_CODE_COL
                + " AND cgc." + AWARD_CODE_COL + " = pm." + AWARD_CODE_COL
                + " AND cgc." + PROGRAM_CODE_COL + " = pm." + PROGRAM_CODE_COL
                + " LEFT JOIN " +
                UNIT_TABLE + " AS un"
                + " ON un." + COUNTRY_CODE_COL + " = cgc." + COUNTRY_CODE_COL
                + " AND un." + LAY_R1_LIST_CODE_COL + " = cg." + GRP_LAY_R1_LIST_CODE_COL
                + " AND un." + LAY_R2_LIST_CODE_COL + " = cg." + GRP_LAY_R2_LIST_CODE_COL
                + " AND un." + LAY_R3_LIST_CODE_COL + " = cg." + GRP_LAY_R3_LIST_CODE_COL
                + " INNER JOIN " +
                ADM_COUNTRY_AWARD_TABLE + " AS awd "
                + " ON awd." + COUNTRY_CODE_COL + " = cgc." + COUNTRY_CODE_COL
                + " AND awd." + DONOR_CODE_COL + " = cgc." + DONOR_CODE_COL
                + " AND awd." + AWARD_CODE_COL + " = cgc." + AWARD_CODE_COL

                + " INNER JOIN "
                + ADM_DONOR_TABLE + " AS don "
                + " ON "


                + "  don." + DONOR_CODE_COL + " = cgc." + DONOR_CODE_COL
                + " LEFT JOIN " +
                COMMUNITY_GRP_DETAIL_TABLE + " AS grpDetail "
                + " ON "
                + " grpDetail." + COUNTRY_CODE_COL + " = cgc." + COUNTRY_CODE_COL
                + " AND grpDetail." + DONOR_CODE_COL + " = cgc." + DONOR_CODE_COL
                + " AND grpDetail." + AWARD_CODE_COL + " = cgc." + AWARD_CODE_COL
                + " AND grpDetail." + PROGRAM_CODE_COL + " = pm." + PROGRAM_CODE_COL
                + " AND grpDetail." + GROUP_CODE_COL + " = cg." + GROUP_CODE_COL
                + " AND grpDetail." + GRP_LAY_R1_LIST_CODE_COL + " = cg." + GRP_LAY_R1_LIST_CODE_COL
                + " AND grpDetail." + GRP_LAY_R2_LIST_CODE_COL + " = cg." + GRP_LAY_R2_LIST_CODE_COL
                + " AND grpDetail." + GRP_LAY_R3_LIST_CODE_COL + " = cg." + GRP_LAY_R3_LIST_CODE_COL


                + " LEFT JOIN " + PROGRAM_ORGANIZATION_NAME_TABLE + " AS org "
                + " ON org." + ORG_CODE_COL + " = grpDetail." + ORG_CODE_COL

                + " LEFT JOIN " + STAFF_MASTER_TABLE + " AS staff "
                + " ON staff." + STAFF_ID_COL + " = " + " grpDetail." + STAFF_CODE_COL
                + " AND staff." + COUNTRY_CODE + " = " + " cgc." + COUNTRY_CODE_COL
                + " AND staff." + ORG_CODE_COL + " = " + " grpDetail." + ORG_CODE_COL

                + " INNER JOIN " + SELECTED_VILLAGE_TABLE + " AS selV "
                + " ON cg." + GRP_LAY_R1_LIST_CODE_COL + " = selV." + LAY_R1_LIST_CODE_COL
                + " AND cg." + GRP_LAY_R2_LIST_CODE_COL + " = selV." + LAY_R2_LIST_CODE_COL
                + " AND cg." + GRP_LAY_R3_LIST_CODE_COL + " = selV." + LAY_R3_LIST_CODE_COL

                + "   WHERE cgc." + COUNTRY_CODE_COL + " = '" + cCode + "' "
                + " AND cgc." + DONOR_CODE_COL + " = '" + donorCode + "' "
                + " AND cgc." + AWARD_CODE_COL + " ='" + awardCode + "' "
                + " AND cgc." + PROGRAM_CODE_COL + " = '" + progCode + "' "
                + " AND cg." + GROUP_NAME_COL + " LIKE '%" + groupName + "%' "
                + " GROUP BY "
                + " cg." + COUNTRY_CODE_COL
                + ", cg." + DONOR_CODE_COL
                + ", cg." + AWARD_CODE_COL
                + ", cg." + PROGRAM_CODE_COL
                + ", cg." + GROUP_CAT_CODE_COL
                + ",  cg." + GROUP_CODE_COL
                + ", cg." + GRP_LAY_R1_LIST_CODE_COL
                + ", cg." + GRP_LAY_R2_LIST_CODE_COL
                + ", cg." + GRP_LAY_R3_LIST_CODE_COL;

    }

    public static String getDistributionExtedVoucherSummaryDataList_sql(String cCode, String discode, String upCode, String unCode, String vCode, String memId, String donorCode, String awardCode, String programCode, String serviceCode, String opMonthCode, String fdpCode) {
        return "SELECT " + COUNTRY_CODE_COL
                + "  , " + DONOR_CODE_COL + " , " + AWARD_CODE_COL +
                " , " + LAY_R1_LIST_CODE_COL + " , " + LAY_R2_LIST_CODE_COL +
                " , " + LAY_R3_LIST_CODE_COL + " , " + LAY_R4_LIST_CODE_COL +
                " , " + PROGRAM_CODE_COL + " , " + SERVICE_CODE_COL +
                " , " + OP_MONTH_CODE_COL +
                " , (Select " + ITEM_NAME_COL + " from " + VOUCHER_ITEM_TABLE +
                " where " + CATEGORY_CODE_COL + " || " + ITEM_CODE_COL + " = substr(" + VOUCHER_ITEM_SPEC_COL + ",0,8)) AS ItemName " +
                " , (Select " + UNITE_MEAS_COL + " ||' '|| " + MEASE_TITLE_COL + " from " + VOUCHER_ITEM__MEAS_TABLE +
                " where " + MEAS_R_CODE_COL + " = substr(" + VOUCHER_ITEM_SPEC_COL + ",8,3) ) as measerment " +
                " , " + VOUCHER_REFERENCE_NUMBER_COL +
                " , " + VOUCHER_UNIT_COL +
                " , " + VOUCHER_ITEM_SPEC_COL +
                " FROM  " + DISTRIBUTION_EXTENDED_TABLE +
                " WHERE  " + COUNTRY_CODE_COL + " = '" + cCode + "' " +
                " AND  " + DONOR_CODE_COL + " = '" + donorCode + "' " +
                " AND  " + AWARD_CODE_COL + " = '" + awardCode + "' " +
                " AND  " + LAY_R1_LIST_CODE_COL + " = '" + discode + "' " +
                " AND  " + LAY_R2_LIST_CODE_COL + " = '" + upCode + "' " +
                " AND  " + LAY_R3_LIST_CODE_COL + " = '" + unCode + "' " +
                " AND  " + LAY_R4_LIST_CODE_COL + " = '" + vCode + "' " +
                " AND  " + PROGRAM_CODE_COL + " = '" + programCode + "' " +
                " AND  " + SERVICE_CODE_COL + " = '" + serviceCode + "' " +
                " AND  " + OP_MONTH_CODE_COL + " = '" + opMonthCode + "' " +
                " AND  " + MEM_ID_15_D_COL + " = '" + memId + "' " +
                " AND  " + FDP_CODE_COL + " = '" + fdpCode + "' ";
    }

    public static String getServiceExtedVoucherSummaryDataList_sql(String cCode, String discode, String upCode, String unCode, String vCode, String memId, String donorCode, String awardCode, String programCode, String serviceCode, String opMonthCode) {
        return "SELECT " + COUNTRY_CODE_COL
                + "  , " + DONOR_CODE_COL + " , " + AWARD_CODE_COL
                + " , " + LAY_R1_LIST_CODE_COL
                + " , " + LAY_R2_LIST_CODE_COL
                + " , " + LAY_R3_LIST_CODE_COL
                + " , " + LAY_R4_LIST_CODE_COL
                + " , " + PROGRAM_CODE_COL
                + " , " + SERVICE_CODE_COL
                + " , " + OP_MONTH_CODE_COL
                + " , " + VOUCHER_ITEM_TABLE + "." + ITEM_NAME_COL + " AS ItemName "
                + " , " + VOUCHER_ITEM__MEAS_TABLE + "." + UNITE_MEAS_COL + " ||' '|| " + MEASE_TITLE_COL + " AS measerment " +
                " , " + VOUCHER_REFERENCE_NUMBER_COL
                + " , " + VOUCHER_UNIT_COL
                + " , " + VOUCHER_ITEM_SPEC_COL
                + " FROM  " + SERVICE_EXTENDED_TABLE
                + " INNER JOIN " + VOUCHER_ITEM_TABLE
                + " ON " + VOUCHER_ITEM_TABLE + "." + CATEGORY_CODE_COL + " || " + VOUCHER_ITEM_TABLE + "." + ITEM_CODE_COL + " = substr(" + SERVICE_EXTENDED_TABLE + "." + VOUCHER_ITEM_SPEC_COL + ",0,8)"
                + " INNER JOIN " + VOUCHER_ITEM__MEAS_TABLE
                + " ON " + VOUCHER_ITEM__MEAS_TABLE + "." + MEAS_R_CODE_COL + " = substr(" + SERVICE_EXTENDED_TABLE + "." + VOUCHER_ITEM_SPEC_COL + ",8,3)  "
                + " WHERE  " + COUNTRY_CODE_COL + " = '" + cCode + "' " +
                " AND  " + DONOR_CODE_COL + " = '" + donorCode + "' " +
                " AND  " + AWARD_CODE_COL + " = '" + awardCode + "' " +
                " AND  " + LAY_R1_LIST_CODE_COL + " = '" + discode + "' " +
                " AND  " + LAY_R2_LIST_CODE_COL + " = '" + upCode + "' " +
                " AND  " + LAY_R3_LIST_CODE_COL + " = '" + unCode + "' " +
                " AND  " + LAY_R4_LIST_CODE_COL + " = '" + vCode + "' " +
                " AND  " + PROGRAM_CODE_COL + " = '" + programCode + "' " +
                " AND  " + SERVICE_CODE_COL + " = '" + serviceCode + "' " +
                " AND  " + OP_MONTH_CODE_COL + " = '" + opMonthCode + "' " +
                " AND  " + LAY_R1_LIST_CODE_COL + " || " + LAY_R2_LIST_CODE_COL + " || " + LAY_R3_LIST_CODE_COL + " || " + LAY_R4_LIST_CODE_COL + " || " + HHID_COL + " || " + HH_MEM_ID + " = '" + memId + "' ";
    }

    /**
     * @param cCode             country
     * @param donorCode         donor
     * @param awardCode         award
     * @param foodFlagTypeQuery an and condition which will be dynamically setted
     * @return Them method only return a query that return program name and service name  concretely and  their code
     */
    public static String loadServiceRecodeCriteria(String cCode, String donorCode, String awardCode, String foodFlagTypeQuery) {
        return " SELECT "
                + COUNTRY_PROGRAM_TABLE + "." + PROGRAM_CODE_COL + " ||  '' || " + COUNTRY_PROGRAM_TABLE + "." + SERVICE_CODE_COL + " AS criteriaId "
                + ", " + ADM_PROGRAM_MASTER_TABLE + "." + PROGRAM_SHORT_NAME_COL + " || '-' ||  " + SERVICE_MASTER_TABLE + "." + SERVICE_SHORT_NAME_COL + " AS Criteria"
                + " FROM " + COUNTRY_PROGRAM_TABLE
                + " INNER JOIN " + ADM_PROGRAM_MASTER_TABLE
                + " ON " + ADM_PROGRAM_MASTER_TABLE + "." + PROGRAM_CODE_COL + " = " + COUNTRY_PROGRAM_TABLE + "." + PROGRAM_CODE_COL
                + " AND " + ADM_PROGRAM_MASTER_TABLE + "." + PROGRAM_CODE_COL + " = " + COUNTRY_PROGRAM_TABLE + "." + PROGRAM_CODE_COL
                + " AND " + ADM_PROGRAM_MASTER_TABLE + "." + DONOR_CODE_COL + " = " + COUNTRY_PROGRAM_TABLE + "." + DONOR_CODE_COL
                + " AND " + ADM_PROGRAM_MASTER_TABLE + "." + AWARD_CODE_COL + " = " + COUNTRY_PROGRAM_TABLE + "." + AWARD_CODE_COL
                + " INNER JOIN " + SERVICE_MASTER_TABLE + " ON "
                + SERVICE_MASTER_TABLE + "." + PROGRAM_CODE_COL + " = " + COUNTRY_PROGRAM_TABLE + "." + PROGRAM_CODE_COL
                + " AND " + SERVICE_MASTER_TABLE + "." + SERVICE_CODE_COL + " = " + COUNTRY_PROGRAM_TABLE + "." + SERVICE_CODE_COL

                + " WHERE " + COUNTRY_PROGRAM_TABLE + "." + COUNTRY_CODE_COL + " = '" + cCode + "' "
                + " AND " + COUNTRY_PROGRAM_TABLE + "." + DONOR_CODE_COL + " = '" + donorCode + "' "
                + " AND " + COUNTRY_PROGRAM_TABLE + "." + AWARD_CODE_COL + " = '" + awardCode + "'"
                + foodFlagTypeQuery
                + " ORDER BY Criteria ";
    }

    public static String layR4ListServicePage_sql() {
        return " JOIN " + STAFF_GEO_INFO_ACCESS_TABLE + " AS geoAccess " +
                " ON " + VILLAGE_TABLE + "." + COUNTRY_CODE_COL + " = " + "geoAccess." + COUNTRY_CODE_COL +
                " AND " + VILLAGE_TABLE + "." + LAY_R1_LIST_CODE_COL + " = " + "geoAccess." + LAY_R1_LIST_CODE_COL +
                " AND " + VILLAGE_TABLE + "." + LAY_R2_LIST_CODE_COL + " = " + "geoAccess." + LAY_R2_LIST_CODE_COL +
                " AND " + VILLAGE_TABLE + "." + LAY_R3_LIST_CODE_COL + " = " + "geoAccess." + LAY_R3_LIST_CODE_COL +
                " AND " + VILLAGE_TABLE + "." + LAY_R4_LIST_CODE_COL + " = " + "geoAccess." + LAY_R4_LIST_CODE_COL +
                " GROUP BY " + VILLAGE_TABLE + "." + LAY_R4_LIST_CODE_COL + ", " + VILLAGE_TABLE + "." + VILLAGE_NAME_COL;
    }

    public static String getHouseHoldData_sql(String hhID, String c_code, String layR1Code, String layR2Code, String layR3Code, String layR4Code) {
        return "SELECT "
                + REGISTRATION_TABLE + "." + ID_COL + ", "
                + REGISTRATION_TABLE + "." + REG_DATE_COL + ", "

                + REGISTRATION_TABLE + "." + COUNTRY_CODE + ", "
                + REGISTRATION_TABLE + "." + DISTRICT_NAME_COL + " AS R_District, "
                + REGISTRATION_TABLE + "." + UPZILLA_NAME_COL + " AS R_Upazilla, "
                + REGISTRATION_TABLE + "." + UNITE_NAME_COL + " AS R_Union, "
                + REGISTRATION_TABLE + "." + VILLAGE_NAME_COL + " AS R_Village, "

                + COUNTRY_TABLE + "." + COUNTRY_NAME + " AS country_name, "
                + DISTRICT_TABLE + "." + DISTRICT_NAME_COL + ", "
                + UPAZILLA_TABLE + "." + UPZILLA_NAME_COL + ","
                + UNIT_TABLE + "." + UNITE_NAME_COL + ", "
                + VILLAGE_TABLE + "." + VILLAGE_NAME_COL + ","

                + REGISTRATION_TABLE + "." + PID_COL + ","
                + REGISTRATION_TABLE + "." + PNAME_COL + ","
                + "(" + " CASE WHEN " + SEX_COL + "==" + "'F'" + " THEN " + "'Female'" + " ELSE " + "'Male'" + " END " + ")  AS Sex" + ","
                + REGISTRATION_TABLE + "." + HH_SIZE + ","
                + REGISTRATION_TABLE + "." + LATITUDE_COL + ","
                + REGISTRATION_TABLE + "." + LONGITUDE_COL + ","
                + REGISTRATION_TABLE + "." + AG_LAND + ","
                + "(" + " CASE WHEN " + V_STATUS + "==" + "'Y'" + " THEN " + "'Yes'" + " ELSE " + "'No'" + " END " + ") AS VStatus" + ","
                + "(" + " CASE WHEN " + M_STATUS + "==" + "'Y'" + " THEN " + "'Yes'" + " ELSE " + "'No'" + " END " + ") AS MStatus" + ","
                + REGISTRATION_TABLE + "." + ENTRY_BY + ","
                + REGISTRATION_TABLE + "." + ENTRY_DATE
                + " FROM " + REGISTRATION_TABLE
                + " LEFT JOIN " + DISTRICT_TABLE
                + " ON " + REGISTRATION_TABLE + "." + DISTRICT_NAME_COL + "=" + DISTRICT_TABLE + "." + LAY_R1_LIST_CODE_COL
                + " LEFT JOIN " + UPAZILLA_TABLE + " ON " + REGISTRATION_TABLE + "." + UPZILLA_NAME_COL + "=" + UPAZILLA_TABLE + "." + LAY_R2_LIST_CODE_COL
                + " LEFT JOIN " + UNIT_TABLE + " ON " + REGISTRATION_TABLE + "." + UNITE_NAME_COL + "=" + UNIT_TABLE + "." + LAY_R3_LIST_CODE_COL
                + " LEFT JOIN " + VILLAGE_TABLE + " ON " + REGISTRATION_TABLE + "." + VILLAGE_NAME_COL + "=" + VILLAGE_TABLE + "." + LAY_R4_LIST_CODE_COL
                + " LEFT JOIN " + COUNTRY_TABLE + " ON " + REGISTRATION_TABLE + "." + COUNTRY_CODE + "=" + COUNTRY_TABLE + "." + COUNTRY_CODE

                + " WHERE " + PID_COL + "='" + hhID
                + "' AND " + REGISTRATION_TABLE + "." + COUNTRY_CODE + "='" + c_code
                + "' AND R_District='" + layR1Code + "'"
                + " AND R_Upazilla='" + layR2Code + "'"
                + " AND R_Union='" + layR3Code + "'"
                + " AND R_Village='" + layR4Code

                + "' ORDER BY " + REGISTRATION_TABLE + "." + ID_COL + " DESC";
    }

    /***
     * @return and condition query
     */
    public static String srvTable_And_sql(String cCode, String donorCode, String awardCode, String layR1Code, String lay2Code, String layR3Code, String lay4Code, String hhid, String memid, String progCode, String srvCode, String opCode, String opMonthCode, String srvDate, String srvSerial, String distFlag) {
        return COUNTRY_CODE_COL + "= '" + cCode + "' "
                + " AND " + DONOR_CODE_COL + " = '" + donorCode + "' "
                + " AND " + AWARD_CODE_COL + " = '" + awardCode + "' "
                + " AND " + LAY_R1_LIST_CODE_COL + " = '" + layR1Code + "' "
                + " AND " + LAY_R2_LIST_CODE_COL + " = '" + lay2Code + "' "
                + " AND " + LAY_R3_LIST_CODE_COL + " = '" + layR3Code + "' "
                + " AND " + LAY_R4_LIST_CODE_COL + " = '" + lay4Code + "' "
                + " AND " + HHID_COL + " = '" + hhid + "' "
                + " AND " + HH_MEM_ID + " = '" + memid + "' "
                + " AND " + PROGRAM_CODE_COL + " = '" + progCode + "' "
                + " AND " + SERVICE_CODE_COL + " = '" + srvCode + "' "
                + " AND " + OPERATION_CODE_COL + " = '" + opCode + "' "
                + " AND " + OP_MONTH_CODE_COL + " = '" + opMonthCode + "' "
                + " AND " + SERVICE_DT_COL + " = '" + srvDate + "' "
                + " AND " + SERVICE_SL_COL + " = '" + srvSerial + "' "
                + " AND " + DIST_FLAG_COL + " = '" + distFlag + "' ";
    }

    public static String selectSrvTable_sql(String cCode, String donorCode, String awardCode, String layR1Code, String lay2Code, String layR3Code, String lay4Code, String hhid, String memid, String progCode, String srvCode, String opCode, String opMonthCode, String srvDate, String srvSerial, String distFlag) {
        return "SELECT * FROM " + SERVICE_TABLE + " WHERE " + srvTable_And_sql(cCode, donorCode, awardCode, layR1Code, lay2Code, layR3Code, lay4Code, hhid, memid, progCode, srvCode, opCode, opMonthCode, srvDate, srvSerial, distFlag);
    }

    /**
     * Only fore the Service recording Activity to Specifies the Service Center
     *
     * @param opMode operation Mode of Device
     * @return query
     */
    public static String loadServiceCenter_sql(int opMode) {
        String criteria = "";
        switch (opMode) {
            case UtilClass.SERVICE_OPERATION_MODE:
                criteria = "SELECT  CASE " + FDP_CODE_COL + " WHEN  'null'  THEN '000'  ELSE " + FDP_CODE_COL + " END " + " || '' || " + SERVICE_CENTER_CODE_COL + " , " +
                        SERVICE_CENTER_NAME_COL + " FROM " + SERVICE_CENTER_TABLE
                        + " WHERE " + SERVICE_CENTER_TABLE + "." + SERVICE_CENTER_CODE_COL + " || '' || "
                        + SERVICE_CENTER_TABLE + "." + COUNTRY_CODE_COL
                        + " IN ( SELECT "
                        + SELECTED_SERVICE_CENTER_TABLE + "." + SERVICE_CENTER_CODE_COL + " || '' || "
                        + SELECTED_SERVICE_CENTER_TABLE + "." + SQLiteHandler.COUNTRY_CODE_COL + " from " + SELECTED_SERVICE_CENTER_TABLE + ")"
                        + " AND " + FDP_CODE_COL + " != 'null' "
                        + " GROUP BY " + SERVICE_CENTER_TABLE + "." + SERVICE_CENTER_CODE_COL;


                break;
            default:
                criteria = "SELECT  CASE " + FDP_CODE_COL + " WHEN  'null'  THEN '000'  ELSE " + FDP_CODE_COL + " END " + " || '' || " + SERVICE_CENTER_CODE_COL + " , " +
                        SERVICE_CENTER_NAME_COL + " FROM " + SERVICE_CENTER_TABLE;
                break;
        }
        return criteria;
    }

    /**
     * this method only use for dynamic response
     *
     * @param cCode         Country Code
     * @param resLupText    response look up text
     * @param lup_TableName look up table Name
     * @return dynamic query
     */
    public static String loadDynamicSpinnerListLoader_sql(String cCode, String resLupText, String lup_TableName, DynamicDataIndexDataModel dyBasic) {
        String udf = "";

        switch (resLupText) {

            case GEO_LAYER_3:

                udf = "SELECT " + SQLiteHandler.UNIT_TABLE + "." + SQLiteHandler.LAY_R3_LIST_CODE_COL
                        + ", " + SQLiteHandler.UNIT_TABLE + "." + SQLiteHandler.UNITE_NAME_COL
                        + " FROM " + SQLiteHandler.UNIT_TABLE
                        + " WHERE " + SQLiteHandler.UNIT_TABLE + "." + SQLiteHandler.COUNTRY_CODE_COL + "='" + cCode + "'";


                break;
            case GEO_LAYER_2:
                udf = "SELECT " + SQLiteHandler.UPAZILLA_TABLE + "." + SQLiteHandler.LAY_R2_LIST_CODE_COL
                        + ", " + SQLiteHandler.UPAZILLA_TABLE + "." + SQLiteHandler.UPZILLA_NAME_COL
                        + " FROM " + SQLiteHandler.UPAZILLA_TABLE
                        + " WHERE " + SQLiteHandler.UPAZILLA_TABLE + "." + SQLiteHandler.COUNTRY_CODE_COL + "='" + cCode + "'";


                break;

            case GEO_LAYER_1:

                udf = "SELECT " + SQLiteHandler.DISTRICT_TABLE + "." + SQLiteHandler.LAY_R1_LIST_CODE_COL
                        + ", " + SQLiteHandler.DISTRICT_TABLE + "." + SQLiteHandler.DISTRICT_NAME_COL
                        + " FROM " + SQLiteHandler.DISTRICT_TABLE
                        + " WHERE " + SQLiteHandler.DISTRICT_TABLE + "." + SQLiteHandler.COUNTRY_CODE_COL + "='" + cCode + "'";


                break;

            case GEO_LAYER_4:

                udf = "SELECT " + SQLiteHandler.VILLAGE_TABLE + "." + SQLiteHandler.LAY_R4_LIST_CODE_COL
                        + ", " + SQLiteHandler.VILLAGE_TABLE + "." + SQLiteHandler.VILLAGE_NAME_COL
                        + " FROM " + SQLiteHandler.VILLAGE_TABLE
                        + " WHERE " + SQLiteHandler.VILLAGE_TABLE + "." + SQLiteHandler.COUNTRY_CODE_COL + "='" + cCode + "'";


                break;

            case GEO_LAYER_ADDRESS:

                udf = "SELECT " + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE + "." + SQLiteHandler.REGN_ADDRESS_LOOKUP_CODE_COL
                        + ", " + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE + "." + SQLiteHandler.REGN_ADDRESS_LOOKUP_NAME_COL
                        + " FROM " + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE
                        + " WHERE " + SQLiteHandler.LUP_REGN_ADDRESS_LOOKUP_TABLE + "." + SQLiteHandler.COUNTRY_CODE_COL + "='" + cCode + "'";


                break;

            case SERVICE_SITE:

                udf = "SELECT " + SQLiteHandler.SERVICE_CENTER_TABLE + "." + SQLiteHandler.SERVICE_CENTER_CODE_COL
                        + ", " + SQLiteHandler.SERVICE_CENTER_TABLE + "." + SQLiteHandler.SERVICE_CENTER_NAME_COL
                        + " FROM " + SQLiteHandler.SERVICE_CENTER_TABLE
                        + " WHERE " + SQLiteHandler.SERVICE_CENTER_TABLE + "." + SQLiteHandler.COUNTRY_CODE_COL + "='" + cCode + "'";


                break;

            case DISTRIBUTION_POINT:

                udf = "SELECT " + SQLiteHandler.FDP_MASTER_TABLE + "." + SQLiteHandler.FDP_CODE_COL
                        + ", " + SQLiteHandler.FDP_MASTER_TABLE + "." + SQLiteHandler.FDP_NAME_COL
                        + " FROM " + SQLiteHandler.FDP_MASTER_TABLE
                        + " WHERE " + SQLiteHandler.FDP_MASTER_TABLE + "." + SQLiteHandler.COUNTRY_CODE_COL + "='" + cCode + "'";


                break;


            case LOOKUP_LIST:

                udf = "SELECT " + SQLiteHandler.DT_LUP_TABLE + "." + SQLiteHandler.LIST_CODE_COL
                        + ", " + SQLiteHandler.DT_LUP_TABLE + "." + SQLiteHandler.LIST_NAME_COL
                        + " FROM " + SQLiteHandler.DT_LUP_TABLE
                        + " WHERE " + SQLiteHandler.DT_LUP_TABLE + "." + SQLiteHandler.COUNTRY_CODE_COL + "= '" + cCode + "' "
                        + " AND " + SQLiteHandler.DT_LUP_TABLE + "." + SQLiteHandler.TABLE_NAME_COL + "= '" + lup_TableName + "'"
                ;


                break;


            case COMMUNITY_GROUP:
                udf = "SELECT "

                        + " commGrp." + DONOR_CODE_COL + " || '' || "
                        + " commGrp." + AWARD_CODE_COL + " || '' ||"
                        + " commGrp." + PROGRAM_CODE_COL + " || '' ||"

                        + " commGrp." + GROUP_CODE_COL + " || '' || "
                        + " commGrp." + GRP_LAY_R1_LIST_CODE_COL + " || '' || "
                        + " commGrp." + GRP_LAY_R2_LIST_CODE_COL + " || '' || "
                        + " commGrp." + GRP_LAY_R3_LIST_CODE_COL

                        + " , award." + AWARD_SHORT_NAME_COL + " || '-' ||"
                        + " counAward." + AWARD_SHORT_NAME_COL + " || '-' ||"
                        + " admProg." + PROGRAM_SHORT_NAME_COL + " || '-' ||"
                        + " commGrp." + GROUP_NAME_COL
                        + " FROM " + COMMUNITY_GROUP_TABLE + " AS commGrp "

                        + " LEFT JOIN " + ADM_AWARD_TABLE + " AS award "
                        + " ON award." + DONOR_CODE_COL + " = commGrp." + DONOR_CODE_COL
                        + " AND award." + AWARD_CODE_COL + " = commGrp." + AWARD_CODE_COL

                        + " INNER JOIN " + ADM_COUNTRY_AWARD_TABLE + " AS counAward "

                        + " ON counAward." + DONOR_CODE_COL + " = commGrp." + DONOR_CODE_COL
                        + " AND counAward." + AWARD_CODE_COL + " = commGrp." + AWARD_CODE_COL

                        + " INNER JOIN " + ADM_PROGRAM_MASTER_TABLE + " AS admProg "

                        + " ON admProg." + DONOR_CODE_COL + " = commGrp." + DONOR_CODE_COL
                        + " AND admProg." + AWARD_CODE_COL + " = commGrp." + AWARD_CODE_COL
                        + " AND admProg." + PROGRAM_CODE_COL + " = commGrp." + PROGRAM_CODE_COL


                        + " WHERE " + "commGrp." + COUNTRY_CODE_COL + "='" + cCode + "'"
                        + " ORDER BY award." + AWARD_SHORT_NAME_COL + " || '-' ||"
                        + " counAward." + AWARD_SHORT_NAME_COL + " || '-' ||"
                        + " admProg." + PROGRAM_SHORT_NAME_COL + " || '-' ||"
                        + " commGrp." + GROUP_NAME_COL;


                break;
            case ORGANIZATION_LIST:

                udf = "SELECT  progOR." + ORG_CODE_COL + ", pOrg." + ORGANIZATION_NAME + " " +
                        "                                FROM " + PROGRAM_ORGANIZATION_ROLE_TABLE + " AS progOR "
                        + "                               INNER JOIN " +
                        "                                " + PROGRAM_ORGANIZATION_NAME_TABLE + " AS pOrg " +
                        "                               ON progOR." + ORG_CODE_COL + " = pOrg." + ORG_CODE_COL + "  " +
                        "                                WHERE (progOR." + COUNTRY_CODE_COL + " = '" + cCode + "')" +
                        " GROUP BY pOrg." + ORGANIZATION_NAME;

                break;


        }

        Log.d("toma", udf);
        return udf;
    }


    public static String loadDtMonth_sql(String cCode, String opCode, String opMonthCode) {

        String criteria = "";
        if (opMonthCode.length() > 1)
            criteria = " AND " + OP_MONTH_CODE_COL + " = '" + opMonthCode + "' ";

        return "SELECT " + OP_MONTH_CODE_COL + " AS OpMonthID, "
                + MONTH_LABEL + " FROM " + OP_MONTH_TABLE
                + " WHERE " +
                COUNTRY_CODE_COL + " = '" + cCode + "'"
                + " AND " + STATUS + " = '" + "A" + "' "
                + " AND " + OPERATION_CODE_COL + " = '" + opCode + "' "
                + criteria
                + " ORDER BY OpMonthID   DESC ";
    }


    public static String loadCountry_sql(int appOpMode, boolean multipleCountryAccessUserFlag) {
        String sql = "";
        switch (appOpMode) {
            case UtilClass.REGISTRATION_OPERATION_MODE:
                sql = " INNER JOIN " + SQLiteHandler.SELECTED_VILLAGE_TABLE + " ON "
                        + SQLiteHandler.COUNTRY_TABLE + "." + SQLiteHandler.COUNTRY_CODE_COL + " = "
                        + SQLiteHandler.SELECTED_VILLAGE_TABLE + "." + SQLiteHandler.COUNTRY_CODE_COL;


                break;
            case UtilClass.DISTRIBUTION_OPERATION_MODE:
                sql = " INNER JOIN " + SQLiteHandler.SELECTED_FDP_TABLE + " ON "
                        + SQLiteHandler.COUNTRY_TABLE + "." + SQLiteHandler.COUNTRY_CODE_COL + " = "
                        + SQLiteHandler.SELECTED_FDP_TABLE + "." + SQLiteHandler.COUNTRY_CODE_COL;

                break;
            case UtilClass.SERVICE_OPERATION_MODE:
                sql = " INNER JOIN " + SQLiteHandler.SELECTED_SERVICE_CENTER_TABLE + " ON "
                        + SQLiteHandler.COUNTRY_TABLE + "." + SQLiteHandler.COUNTRY_CODE_COL + " = "
                        + SQLiteHandler.SELECTED_SERVICE_CENTER_TABLE + "." + SQLiteHandler.COUNTRY_CODE_COL;


                break;

            case UtilClass.OTHER_OPERATION_MODE:
                /**                 * check  user  has access in multiple countries                 */
                if (multipleCountryAccessUserFlag) {
                    sql = " INNER JOIN " + SQLiteHandler.SELECTED_COUNTRY_TABLE + " ON "
                            + SQLiteHandler.COUNTRY_TABLE + "." + SQLiteHandler.COUNTRY_CODE_COL + " = "
                            + SQLiteHandler.SELECTED_COUNTRY_TABLE + "." + SQLiteHandler.COUNTRY_CODE_COL;


                } else {


                    sql = " INNER JOIN " + SQLiteHandler.STAFF_GEO_INFO_ACCESS_TABLE + " AS staffAcces ON "
                            + SQLiteHandler.COUNTRY_TABLE + "." + SQLiteHandler.COUNTRY_CODE_COL + " = " + "staffAcces." + SQLiteHandler.COUNTRY_CODE_COL
                            + " WHERE (" + SQLiteHandler.BTN_NEW_COL + " = '1' " +
                            " OR " + SQLiteHandler.BTN_SAVE_COL + " = 1" +
                            " OR " + SQLiteHandler.BTN_DEL_COL + " = 1 ) GROUP BY " + " staffAcces." + SQLiteHandler.COUNTRY_CODE_COL;


                }

                break;
        }
        return sql;
    }

    public static String showNearBy_gpsSubGroup_sql(String cCode) {
        return "SELECT DISTINCT gpsSubGrp." + GROUP_CODE_COL + " || '' || gpsSubGrp." + SUB_GROUP_CODE_COL
                + " , gpsGrp." + GROUP_NAME_COL + "|| '-' || gpsSubGrp." + SUB_GROUP_NAME_COL
                + " FROM " + GPS_LOCATION_TABLE + " AS gpsLoc " + " INNER JOIN " + GPS_SUB_GROUP_TABLE + " AS gpsSubGrp "
                + " ON gpsLoc." + GROUP_CODE_COL + " = gpsSubGrp." + GROUP_CODE_COL
                + " AND  gpsLoc." + SUB_GROUP_CODE_COL + " = gpsSubGrp." + SUB_GROUP_CODE_COL
                + " INNER JOIN " + GPS_GROUP_TABLE + " AS gpsGrp "
                + " ON gpsLoc." + GROUP_CODE_COL + " = gpsGrp." + GROUP_CODE_COL
                + " WHERE gpsLoc." + COUNTRY_CODE_COL + " = '" + cCode + "' "
                + " ORDER BY gpsSubGrp." + SUB_GROUP_NAME_COL;


    }

    public static String getDynamicTableIndexList_sql(String cCode, String dtTitleSearch, String staffId) {
        return "SELECT dtB." + DT_TITLE_COL + "  " +
                " , dtCPgr." + DT_BASIC_COL + " AS dtBasicCode  " +
                " , donor." + DONOR_NAME_COL + " || '-' || award." + AWARD_SHORT_NAME_COL + " AS awardName  " +
                " , dtCPgr." + DONOR_CODE_COL + " || '' || dtCPgr." + AWARD_CODE_COL + " AS awardCode  " +
                " , prg." + PROGRAM_SHORT_NAME_COL + "  " +
                " , dtCPgr." + PROGRAM_CODE_COL + "  " +
                " , dtCPgr." + PROG_ACTIVITY_TITLE_COL +
                " , dtCPgr." + COUNTRY_CODE_COL +
                " , dtB." + DT_OP_MODE_COL +

                " , dtCPgr." + DONOR_CODE_COL +
                " , dtCPgr." + PROG_ACTIVITY_CODE_COL +
                " , dtB." + DT_SHORT_NAME_COL
                + "  FROM " +
                DT_COUNTRY_PROGRAM_TABLE + " AS dtCPgr  " +
                " LEFT JOIN " + DT_BASIC_TABLE + "  AS dtB  " +
                " ON dtB." + DT_BASIC_COL + " = dtCpgr." + DT_BASIC_COL + "   " +
                " LEFT JOIN " +
                ADM_COUNTRY_AWARD_TABLE + " as award  " +
                " ON award." + COUNTRY_CODE_COL + " = dtCpgr." + COUNTRY_CODE_COL + "  " +
                " AND award." + DONOR_CODE_COL + " = dtCpgr." + DONOR_CODE_COL + "  " +
                " AND award." + AWARD_CODE_COL + "= dtCpgr." + AWARD_CODE_COL + "  " +
                " LEFT JOIN " +
                ADM_DONOR_TABLE + " AS donor  " +
                " ON donor." + DONOR_CODE_COL + " = dtCpgr." + DONOR_CODE_COL + "  " +
                " LEFT JOIN " +
                ADM_PROGRAM_MASTER_TABLE + " AS prg  " +
                " ON prg." + DONOR_CODE_COL + " = dtCpgr." + DONOR_CODE_COL + "  " +
                " AND prg." + AWARD_CODE_COL + " = dtCpgr." + AWARD_CODE_COL + "  " +
                " AND prg." + PROGRAM_CODE_COL + " = dtCpgr." + PROGRAM_CODE_COL + "  " +
                "  INNER JOIN " + DT_ENU_TABLE + " AS dtEnu ON " +
                " dtEnu." + DT_BASIC_COL + " = dtCPgr." + DT_BASIC_COL +
                " WHERE dtCPgr." + COUNTRY_CODE_COL + " = '" + cCode + "' "
                + " AND dtEnu." + DT_STF_CODE_COL + " = '" + staffId + "' "
                + " AND dtB." + DT_TITLE_COL + " LIKE '%" + dtTitleSearch + "%'";
    }

    /**
     * {@link PointAttributes#loadLocation(String, String, String)} method use this query
     *
     * @param cCode      country Code
     * @param grpCode    group Code
     * @param subGrpCode Sub group Code
     * @return location name
     */
    public static String loadLocationLoader_sql(String cCode, String grpCode, String subGrpCode) {
        return "SELECT " + LOCATION_CODE_COL + "," + LOCATION_NAME_COL + " FROM " +
                GPS_LOCATION_TABLE +
                " WHERE " + COUNTRY_CODE_COL + " = '" + cCode + "' " +
                " AND " + GROUP_CODE_COL + "='" + grpCode + "' " +
                " AND " + SUB_GROUP_CODE_COL + "='" + subGrpCode + "'";
    }

    /**
     * the sql used only Map Activity
     * {@link MapActivity#loadLocation(String)} method used this query
     *
     * @param cCode country code
     * @return location name, groupCode + subGroupCode + locationCode
     */
    public static String loadLocationLoader_sql(String cCode) {
        return "SELECT " + GROUP_CODE_COL + " || '' ||" + SUB_GROUP_CODE_COL + " || '' ||" + LOCATION_CODE_COL
                + "," + LOCATION_NAME_COL
                + " FROM " + GPS_LOCATION_TABLE
                + " WHERE " + COUNTRY_CODE_COL + " = '" + cCode + "'"
                + " ORDER BY " + LOCATION_NAME_COL + " ASC ";
    }

    /**
     * this method sql to load Service Name and code to Criteria spinner in
     * {@link com.siddiquinoor.restclient.activity.AssignActivity#loadCriteria(String, String, String, String)}
     *
     * @param awardCode   award Code
     * @param donorCode   donor Code
     * @param programCode program Code
     * @return srvCode & srvName
     */
    public static String loadCriteria_sql(String awardCode, String donorCode, String programCode) {
        return "SELECT " + COUNTRY_PROGRAM_TABLE + "." + SERVICE_CODE_COL + " AS criteriaId" + " , " +
                SERVICE_MASTER_TABLE + "." + SERVICE_NAME_COL + " || '-' ||  " + SERVICE_MASTER_TABLE + "." + SERVICE_SHORT_NAME_COL + " AS Criteria" +
                " FROM " + COUNTRY_PROGRAM_TABLE + " JOIN " + SERVICE_MASTER_TABLE +
                " ON " + SERVICE_MASTER_TABLE + "." + PROGRAM_CODE_COL + " = " + COUNTRY_PROGRAM_TABLE + "." + PROGRAM_CODE_COL
                + " AND " + SERVICE_MASTER_TABLE + "." + SERVICE_CODE_COL + " = " + COUNTRY_PROGRAM_TABLE + "." + SERVICE_CODE_COL + " " +
                " WHERE " + COUNTRY_PROGRAM_TABLE + "." + AWARD_CODE_COL + "='" + awardCode + "'"
                + " AND " + COUNTRY_PROGRAM_TABLE + "." + DONOR_CODE_COL + "='" + donorCode + "'"
                + " AND " + COUNTRY_PROGRAM_TABLE + "." + PROGRAM_CODE_COL + "='" + programCode + "'"
                + " AND " + COUNTRY_PROGRAM_TABLE + "." + PROG_FLAG + "= '1'"
                + " GROUP BY " + COUNTRY_PROGRAM_TABLE + "." + SERVICE_CODE_COL;

    }

    /**
     * @return layRlist Code and name
     */
    public static String loadLayR4List_sql() {
        return "SELECT  v." +
                COUNTRY_CODE_COL + " || '' || v." +
                LAY_R1_LIST_CODE_COL + " || '' || v." + LAY_R2_LIST_CODE_COL + " || '' || v." + LAY_R3_LIST_CODE_COL + " || '' || v." + LAY_R4_LIST_CODE_COL
                + ", v." + VILLAGE_NAME_COL + " FROM " + VILLAGE_TABLE + " AS v   INNER JOIN " + SELECTED_VILLAGE_TABLE + " as S "
                + " ON S." + COUNTRY_CODE_COL

                + " ||''|| S." + LAY_R1_LIST_CODE_COL
                + " ||''|| S." + LAY_R2_LIST_CODE_COL
                + " ||''|| S." + LAY_R3_LIST_CODE_COL
                + " ||''|| S." + LAY_R4_LIST_CODE_COL

                + "  = v." + COUNTRY_CODE_COL
                + " ||''|| v." + LAY_R1_LIST_CODE_COL
                + " ||''|| v." + LAY_R2_LIST_CODE_COL
                + " ||''|| v." + LAY_R3_LIST_CODE_COL
                + " ||''|| v." + LAY_R4_LIST_CODE_COL + " " +
                " GROUP BY  v." + COUNTRY_CODE_COL
                + " ||''|| v." + LAY_R1_LIST_CODE_COL
                + " ||''|| v." + LAY_R2_LIST_CODE_COL
                + " ||''|| v." + LAY_R3_LIST_CODE_COL
                + " ||''|| v." + LAY_R4_LIST_CODE_COL + " ";
    }

    /**
     * this query check is specific member exits in RefNAssProgSrv for specific program an Service
     *
     * @param cCode     country Code
     * @param layR1Code layR1Code
     * @param layR2Code layR1Code
     * @param layR3Code layR1Code
     * @param lay4Code  layR1Code
     * @param hh        house hold id
     * @param memID     member id
     * @param progCode  program Code
     * @param srvCode   service Code
     * @return
     */
    public static String ifExistsInRegNAssProgSrv_sql(String cCode, String layR1Code, String layR2Code, String layR3Code, String lay4Code, String hh, String memID, String progCode, String srvCode) {
        return "SELECT * FROM " + REG_N_ASSIGN_PROG_SRV_TABLE
                + " WHERE    " + COUNTRY_CODE_COL + " = '" + cCode
                + "' AND " + LAY_R1_LIST_CODE_COL + " = '" + layR1Code + "'" +
                " AND " + LAY_R2_LIST_CODE_COL + " = '" + layR2Code + "'" +
                " AND " + LAY_R3_LIST_CODE_COL + " = '" + layR3Code + "'" +
                " AND " + LAY_R4_LIST_CODE_COL + " = '" + lay4Code + "'" +
                " AND " + HHID_COL + " = '" + hh + "'"
                + " AND " + HH_MEM_ID + " = '" + memID + "'  "
                + " AND " + PROGRAM_CODE_COL + " = '" + progCode + "'  "
                + " AND " + SERVICE_CODE_COL + " = '" + srvCode + "'  ";
    }

    public static String getSelectedVillageList_sql() {
        return "SELECT " + LAYER_CODE_COL
                + " , " + VILLAGE_NAME_COL + " FROM " + SELECTED_VILLAGE_TABLE
                + " GROUP BY " + LAYER_CODE_COL;
    }

    public static String getSelectedCountryCode_sql() {
        return "SELECT " + COUNTRY_CODE_COL
                + " FROM " + SELECTED_VILLAGE_TABLE
                + " GROUP BY " + COUNTRY_CODE_COL;
    }

}//end of class
