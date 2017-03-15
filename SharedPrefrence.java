package com.poochplay.sharedprefrence;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.poochplay.dto.AddPetDTO;
import com.poochplay.dto.AppointmentDTO;
import com.poochplay.dto.BodyScoreDTO;
import com.poochplay.dto.BreedDTO;
import com.poochplay.dto.DeviceInfo;
import com.poochplay.dto.FoodPlan;
import com.poochplay.dto.GalleryDTO;
import com.poochplay.dto.Insurance;
import com.poochplay.dto.LastRecord;
import com.poochplay.dto.UserDTO;
import com.poochplay.dto.VaccinationDTO;
import com.poochplay.utils.Consts;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class SharedPrefrence {
    public static SharedPreferences myPrefs;
    public static SharedPreferences.Editor prefsEditor;
    public static SharedPrefrence myObj;
    public static String FAVOURITE = "favourite_list";
    public static final String USER_EMAIL = "userEmail";
    public static final String NULL_VALUE = "null";
    public static final String ItemIndex = "0";
    public static final String GET_PET_DETAIL = "getPetDetail";
    public static final String IS_FIRST_RUN = "isFirstRun";
    public static final String CURRENT_INDEX = "currentIndex";
    public static final String ADD_PET_INDEX = "addPetIndex";
    public static final String PET_APPOINTMENTS_DETAIL = "petAppointments";
    public static final String USER_DETAIL = "userDetails";
    public static final String BREED_LIST = "breedList";
    public static final String BREED_TARGET = "breedNameWithTarget";
    public static final String CALENDAR = "myCalendar";
    public static final String NOTIFIOCATION_ENABLE = "notificationAlert";
    public static final String URI = "mediaUri";
    // public static final String DEVICE_INFO = "deviceInfo";

    public static final String GET_PET_GALLERY = "getPetGallery";
    public static final String GET_REJECTION_LIST = "getRejectionList";

    public static final String IS_LOGIN = "isLogin";
    public static final String BODY_SCORE = "bodyScore";

    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";


    public static final String INSURANCE = "insurance";
    public static final String VACCINATION_RECORD = "vaccinationRecord";

//    public static final String PLAN_QUANTITY = "plan_quantity";
//    public static final String PLAN_UNIT = "plan_unit";


    public static final String NOTIFICATION_COUNT = "notificationCount";

    public static final String FORGOT_PASS_EMAIL = "forgotPassEmail";
    public static final String IS_FORGOT_PASS_OK = "forgotPassResult";

    public static final String WEIGHT_LAST_RECORD = "weightManagement";

    private SharedPrefrence() {

    }
	/**
     * Making share-preference as single class
     *
     * @param Context : Context value
     * @return void.
     */
    public static SharedPrefrence getInstance(Context ctx) {
        if (myObj == null) {
            myObj = new SharedPrefrence();
            myPrefs = ctx.getSharedPreferences("com.poochplay", Context.MODE_PRIVATE);
            prefsEditor = myPrefs.edit();
        }
        return myObj;
    }
	/**
     * get long value from preference
     *
     * @param String : Tag Name
     * @return Long.
     */
    public long getLongValue(String Tag) {

        if (Tag.equals(CALENDAR))
            return myPrefs.getLong(Tag, 0L);
        return myPrefs.getLong(Tag, 0L);

    }
	/**
     * set long value in preference
     *
     * @param long 	 : value
     * @param String : String Tag
     * @return void.
     */
    public void setLongValue(String Tag, long token) {
        prefsEditor.putLong(Tag, token);
        prefsEditor.commit();
    }
	/**
     * get boolean value from preference
     *
     * @param String : String Tag
     * @return boolean.
     */
    public boolean getBooleanValue(String Tag) {

        if (Tag.equals(IS_LOGIN))
            return myPrefs.getBoolean(Tag, false);
        if (Tag.equals(NOTIFIOCATION_ENABLE))
            return myPrefs.getBoolean(Tag, true);
        return myPrefs.getBoolean(Tag, false);

    }
	/**
     * set boolean value in preference
     *
     * @param String : String Tag
     * @param boolean : boolean value
     * @return void.
     */
    public void setBooleanValue(String Tag, boolean token) {
        prefsEditor.putBoolean(Tag, token);
        prefsEditor.commit();
    }

	/**
     * get int value from preference
     *
     * @param String : String Tag
     * @return void.
     */
    public int getIntValue(String Tag) {

        if (Tag.equals(NOTIFICATION_COUNT))
            return myPrefs.getInt(Tag, 0);
        return myPrefs.getInt(Tag, 0);

    }
	/**
     * set int value in preference
     *
     * @param String : String Tag
     * @param int : int value
     * @return void.
     */
    public void setIntValue(String Tag, int value) {
        prefsEditor.putInt(Tag, value);
        prefsEditor.commit();
    }

	/**
     * get string value from preference
     *
     * @param String : String Tag
     * @return String as value.
     */
    public String getValue(String Tag) {

        if (Tag.equals(IS_FIRST_RUN))
            return myPrefs.getString(Tag, "true");
        else if (Tag.equals(CURRENT_INDEX))
            return myPrefs.getString(Tag, "0");
        else if (Tag.equals(ADD_PET_INDEX))
            return myPrefs.getString(Tag, "0");
        else if (Tag.equals(USER_EMAIL))
            return myPrefs.getString(Tag, "niranjan@poochplay.com");
//        else if (Tag.equals(Consts.BLUETOOTH_DEVICE_ID))
//            return myPrefs.getString(Tag, "default");
        else if (Tag.equals(FORGOT_PASS_EMAIL))
            return myPrefs.getString(Tag, "");
        else if (Tag.equals(IS_FORGOT_PASS_OK))
            return myPrefs.getString(Tag, "");
        else if (Tag.equals(URI))
            return myPrefs.getString(Tag, "");
//        else if (Tag.equals(PLAN_QUANTITY))
//            return myPrefs.getString(Tag, "00");
//        else if (Tag.equals(PLAN_UNIT))
//            return myPrefs.getString(Tag, "grm");
        return myPrefs.getString(Tag, "default");
    }

	/**
     * set string value in preference
     *
     * @param String : String Tag
     * @param String : String value
     * @return void.
     */
    public void setValue(String Tag, String token) {
        prefsEditor.putString(Tag, token);
        prefsEditor.commit();
    }

	
	/**
     * set List value in preference
     *
     * @param String : String Tag
     * @param ArrayList<String> : ArrayList<String> value
     * @return void.
     */
    public void setListValue(String Tag, ArrayList<String> lst) {
        Gson gson = new Gson();
        String json = gson.toJson(lst);
        prefsEditor.putString(Tag, json);
        prefsEditor.commit();
    }

	/**
     * set Vaccination object into preference
     *
     * @param String : String Tag
     * @param ArrayList<VaccinationDTO> : ArrayList<VaccinationDTO> value
     * @return void.
     */
    public void setVaccinationRecordList(String Tag, ArrayList<VaccinationDTO> lst) {
        Gson gson = new Gson();
        String json = gson.toJson(lst);
        prefsEditor.putString(Tag, json);
        prefsEditor.commit();
    }
	/**
     * get string Vaccination from preference
     *
     * @param String : String Tag
     * @return ArrayList<VaccinationDTO>.
     */
    public ArrayList<VaccinationDTO> getVaccinationRecordList(String Tag) {
        String obj = myPrefs.getString(Tag, "defValue");
        if (obj.equals("defValue")) {
            return null;
        } else {
            Type type = new TypeToken<ArrayList<VaccinationDTO>>() {
            }.getType();
            Gson gson = new Gson();
            ArrayList<VaccinationDTO> List = gson.fromJson(obj, type);
            return List;
        }
    }


}