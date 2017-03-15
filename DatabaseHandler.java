package com.poochplay.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import com.poochplay.dto.Care;
import com.poochplay.dto.Company;
import com.poochplay.dto.FoodPlan;
import com.poochplay.dto.ManualActivity;
import com.poochplay.dto.MenstrualCycleDTO;
import com.poochplay.dto.NotificationsDTO;
import com.poochplay.dto.Product;
import com.poochplay.dto.StepCount;
import com.poochplay.dto.StoriesDTO;
import com.poochplay.dto.VaccinationDTO;
import com.poochplay.dto.WormDTO;
import com.poochplay.sharedprefrence.SharedPrefrence;
import com.poochplay.utils.Consts;

import java.io.File;
import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "PoochPlay.db";

    // Contacts table name
    private static final String TABLE_STEP_COUNT_HOUR = "countHourly";
    // Contacts table name
    // private static final String TABLE_STEP_COUNT_DATE = "countDaily";
    // Contacts Table Columns names
    private static final String DEVICE_ID = "deviceId";
    private static final String CONNECTED_DATE = "connectedDate";
    private static final String STEP_COUNT = "stepCount";
    private static final String TOTAL_COUNT = "totalCount";
    private static final String IS_SYNCED = "isSynced";


    // Contacts table name
    private static final String TABLE_CARE = "care";
    // Table Column
    private static final String PET_ID = "petId";
    private static final String ID = "Id";
    private static final String CATE_NAME = "cateName";
    private static final String FORMATTED_TIME = "formatedTime";
    private static final String FORMATTED_DATE = "formatedDate";
    private static final String LONG_TIME = "longTime";
    private static final String OWNER_EMAIL = "ownerEmail";
    private static final String IS_DONE = "isDone";
    private static final String INTERVAL = "interval";
    private static final String NOTE = "note";
    private static final String TITLE = "title";
    private static final String SET_NONE = "setNone";
    private static final String IS_ENABLE = "isEnable";

    // Contacts table name
    private static final String MENSTRUAL_CYCLE = "menstrual_cycle";
    // Table Column
    //private static final String PET_ID = "petId";
    //private static final String CATE_NAME = "cateName";
    private static final String START_DATE_ID = "start_date_id";
    private static final String START_DATE_LONG = "start_date_long";
    private static final String END_DATE_ID = "end_date_id";
    private static final String END_DATE_LONG = "end_date_long";
    private static final String EXPECTED_DATE_ID = "expected_date_id";
    private static final String EXPECTED_DATE_LONG = "expected_date_long";
    //private static final String OWNER_EMAIL = "ownerEmail";
    //private static final String IS_DONE = "isDone";


    // Vaccination table name
    private static final String TABLE_VACCINATION = "vaccination";
    // Table Column
    private static final String VACCINATION_ID = "id";
    //private static final String PET_ID = "pet_id";
    private static final String USER_EMAIL = "user_email";
    private static final String VACCINATION_CATEGORY = "category";
    private static final String VACCINATION_LAST_DATE_DONE = "last_done";
    private static final String VACCINATION_DUE_DATE = "due_date";
    private static final String VACCINATION_FREQUENCY = "frequency";
    private static final String VACCINATION_NAME = "vaccination_name";
    private static final String VACCINATION_TITLE = "vaccination_title";


    // Vaccination table name
    private static final String TABLE_WORM = "worm";
    // Table Column
    private static final String WORM_ID = "id";
    //private static final String PET_ID = "pet_id";
//    private static final String USER_EMAIL = "user_email";
    private static final String WORM_CATEGORY = "category";
    private static final String WORM_LAST_DATE_DONE = "last_done";
    private static final String WORM_DUE_DATE = "due_date";
    private static final String WORM_FREQUENCY = "frequency";
    private static final String WORM_NAME = "vaccination_name";
    private static final String WORM_TITLE = "worm_title";


    // Stories table name
    private static final String TABLE_STORIES = "stories";
    // Table Column
    private static final String STORIES_ID = "id";
    private static final String STORIES_TITLE = "title";
    private static final String STORIES_DESCRIPTION = "description";
    private static final String STORIES_IMAGE = "image";


    // Stories table name
    private static final String TABLE_NOTIFICATION = "notifications";
    // Table Column
//    private static final String PET_ID = "petId";
    private static final String NOTIFICATION_ID = "id";
    private static final String NOTIFICATION_TITLE = "title";
    private static final String NOTIFICATION_DESCRIPTION = "description";
    private static final String NOTIFICATION_TIME = "time";
    private static final String NOTIFICATION_CATEGORY = "category";
    private static final String NOTIFICATION_ROOT_CATEGORY = "rootCategory";
    private static final String NOTIFICATION_ITEM_ID = "itemID";
    private static final String NOTIFICATION_IMAGE = "image";

    // Stories table name
    private static final String TABLE_COMPANY = "company";
    // Table Column
    private static final String COM_ID = "c_id";
    private static final String COM_NAME = "c_name";
    private static final String COM_IMAGE = "c_img_path";

    // Stories table name
    private static final String TABLE_PRODUCT = "product";
    // Table Column
    private static final String P_COM_ID = "c_id";
    private static final String P_COM_NAME = "c_name";
    private static final String BREED_CATEGORIES = "breedcategories";
    private static final String P_IMAGE = "img_path";
    private static final String P_IS_ADULT = "isadult";
    private static final String P_ID = "p_id";
    private static final String P_NAME = "p_name";
    private static final String P_THRESHOLD = "threshold";
    private static final String P_UNIT = "unit";


    // Stories table name
    private static final String TABLE_MANUAL_ACTIVITY = "manualActivity";
    // Table Column
    private static final String MA_USER_EMAIL = "userEmail";
    private static final String MA_PET_ID = "petId";
    private static final String MA_LONG = "longDate";
    private static final String MA_HUMAN_DATE = "humanDate";
    private static final String MA_ACTIVITY_VALUE = "activityValue";
    private static final String MA_UNIT = "unit";
    private static final String MA_TARGET = "target";

    // Stories table name
    private static final String TABLE_WEIGHT = "weight";
    // Table Column
    private static final String W_USER_EMAIL = "userEmail";
    private static final String W_PET_ID = "petId";
    private static final String W_LONG = "longDate";
    private static final String W_HUMAN_DATE = "humanDate";
    private static final String W_WEIGHT = "weight";
    private static final String W_UNIT = "unit";


    // Stories table name
    private static final String TABLE_FOOD = "food";
    // Table Column
    private static final String F_PET_ID = "petId";
    private static final String F_PRODUCT_ID = "productId";
    private static final String F_PRODUCT_NAME = "productName";
    private static final String F_COM_ID = "comId";
    private static final String F_COM_NAME = "comName";
    private static final String F_QUANTITY = "quantity";
    private static final String F_UNIT = "unit";


    SharedPrefrence share;
    Context ctx;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //super(context, makeDBFilePath(), null, DATABASE_VERSION);
        ctx = context;
        share = SharedPrefrence.getInstance(ctx);
    }

    public static String makeDBFilePath() {
        File file = new File(Environment.getExternalStorageDirectory()
                .getPath(), Consts.POOCH_PLAY + File.separator + Consts.DATABASE);
        if (!file.exists()) {
            file.mkdirs();
        }
        String dbPath = (file.getPath() + File.separator + DATABASE_NAME);
        return dbPath;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_STEP_COUNT_HOUR = "CREATE TABLE " + TABLE_STEP_COUNT_HOUR + "("
                + PET_ID + " TEXT,"
                + USER_EMAIL + " TEXT,"
                + DEVICE_ID + " TEXT,"
                + CONNECTED_DATE + " TEXT,"
                + STEP_COUNT + " TEXT,"
                + TOTAL_COUNT + " TEXT,"
                + IS_SYNCED + " TEXT" + ")";
        db.execSQL(CREATE_TABLE_STEP_COUNT_HOUR);

//        String CREATE_TABLE_STEP_COUNT_DATE = "CREATE TABLE " + TABLE_STEP_COUNT_DATE + "("
//                + DEVICE_ID + " TEXT,"
//                + CONNECTED_DATE + " TEXT,"
//                + TOTAL_COUNT + " TEXT" + ")";
//        db.execSQL(CREATE_TABLE_STEP_COUNT_DATE);


        String CREATE_TABLE_CARE = "CREATE TABLE " + TABLE_CARE + "("
                + PET_ID + " TEXT,"
                + ID + " TEXT,"
                + CATE_NAME + " TEXT,"
                + FORMATTED_TIME + " TEXT,"
                + FORMATTED_DATE + " TEXT,"
                + LONG_TIME + " TEXT,"
                + OWNER_EMAIL + " TEXT,"
                + IS_DONE + " TEXT,"
                + INTERVAL + " TEXT,"
                + NOTE + " TEXT,"
                + TITLE + " TEXT,"
                + IS_ENABLE + " TEXT,"
                + SET_NONE + " TEXT" + ")";
        db.execSQL(CREATE_TABLE_CARE);

        String CREATE_TABLE_MC = "CREATE TABLE " + MENSTRUAL_CYCLE + "("
                + PET_ID + " TEXT,"
                + START_DATE_ID + " TEXT,"
                + START_DATE_LONG + " TEXT,"
                + END_DATE_ID + " TEXT,"
                + END_DATE_LONG + " TEXT,"
                + EXPECTED_DATE_ID + " TEXT,"
                + EXPECTED_DATE_LONG + " TEXT" + ")";
        db.execSQL(CREATE_TABLE_MC);

        String CREATE_TABLE_STORIES = "CREATE TABLE " + TABLE_STORIES + "("
                + STORIES_ID + " TEXT,"
                + STORIES_TITLE + " TEXT,"
                + STORIES_DESCRIPTION + " TEXT,"
                + STORIES_IMAGE + " TEXT" + ")";
        db.execSQL(CREATE_TABLE_STORIES);


        String CREATE_TABLE_NOTIFICATIONS = "CREATE TABLE " + TABLE_NOTIFICATION + "("
                + PET_ID + " TEXT,"
                + NOTIFICATION_ID + " TEXT,"
                + NOTIFICATION_TITLE + " TEXT,"
                + NOTIFICATION_DESCRIPTION + " TEXT,"
                + NOTIFICATION_CATEGORY + " TEXT,"
                + NOTIFICATION_ROOT_CATEGORY + " TEXT,"
                + NOTIFICATION_ITEM_ID + " TEXT,"
                + NOTIFICATION_IMAGE + " TEXT,"
                + NOTIFICATION_TIME + " TEXT" + ")";
        db.execSQL(CREATE_TABLE_NOTIFICATIONS);


        String CREATE_TABLE_VACCINATION = "CREATE TABLE " + TABLE_VACCINATION + "("
                + VACCINATION_ID + " TEXT,"
                + VACCINATION_NAME + " TEXT,"
                + VACCINATION_TITLE + " TEXT,"
                + PET_ID + " TEXT,"
                + USER_EMAIL + " TEXT,"
                + OWNER_EMAIL + " TEXT,"
                + VACCINATION_CATEGORY + " TEXT,"
                + VACCINATION_LAST_DATE_DONE + " TEXT,"
                + VACCINATION_DUE_DATE + " TEXT,"
                + VACCINATION_FREQUENCY + " TEXT" + ")";
        db.execSQL(CREATE_TABLE_VACCINATION);


        String CREATE_TABLE_WORM = "CREATE TABLE " + TABLE_WORM + "("
                + WORM_ID + " TEXT,"
                + WORM_NAME + " TEXT,"
                + WORM_TITLE + " TEXT,"
                + PET_ID + " TEXT,"
                + USER_EMAIL + " TEXT,"
                + OWNER_EMAIL + " TEXT,"
                + WORM_CATEGORY + " TEXT,"
                + WORM_LAST_DATE_DONE + " TEXT,"
                + WORM_DUE_DATE + " TEXT,"
                + WORM_FREQUENCY + " TEXT" + ")";
        db.execSQL(CREATE_TABLE_WORM);

        String CREATE_TABLE_COMPANY = "CREATE TABLE " + TABLE_COMPANY + "("
                + COM_ID + " TEXT,"
                + PET_ID + " TEXT,"
                + COM_NAME + " TEXT,"
                + COM_IMAGE + " TEXT" + ")";
        db.execSQL(CREATE_TABLE_COMPANY);


        String CREATE_TABLE_PRODUCT = "CREATE TABLE " + TABLE_PRODUCT + "("
                + P_COM_ID + " TEXT,"
                + P_COM_NAME + " TEXT,"
                + BREED_CATEGORIES + " TEXT,"
                + P_IMAGE + " TEXT,"
                + P_IS_ADULT + " TEXT,"
                + P_ID + " TEXT,"
                + P_THRESHOLD + " TEXT,"
                + P_UNIT + " TEXT,"
                + P_NAME + " TEXT" + ")";
        db.execSQL(CREATE_TABLE_PRODUCT);


        String CREATE_TABLE_MANUAL_ACTIVITY = "CREATE TABLE " + TABLE_MANUAL_ACTIVITY + "("
                + MA_USER_EMAIL + " TEXT,"
                + MA_PET_ID + " TEXT,"
                + MA_LONG + " TEXT,"
                + MA_HUMAN_DATE + " TEXT,"
                + MA_ACTIVITY_VALUE + " TEXT,"
                + MA_TARGET + " TEXT,"
                + MA_UNIT + " TEXT" + ")";
        db.execSQL(CREATE_TABLE_MANUAL_ACTIVITY);

        String CREATE_TABLE_FOOD = "CREATE TABLE " + TABLE_FOOD + "("
                + F_PET_ID + " TEXT,"
                + F_PRODUCT_ID + " TEXT,"
                + F_PRODUCT_NAME + " TEXT,"
                + F_COM_ID + " TEXT,"
                + F_COM_NAME + " TEXT,"
                + F_QUANTITY + " TEXT,"
                + F_UNIT + " TEXT" + ")";
        db.execSQL(CREATE_TABLE_FOOD);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.e("amit", "database version " + oldVersion);
        Log.e("amit", "database new version " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STEP_COUNT_HOUR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARE);
        db.execSQL("DROP TABLE IF EXISTS " + MENSTRUAL_CYCLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VACCINATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPANY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MANUAL_ACTIVITY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD);
        onCreate(db);
    }


//
//    public void addDailyData(StepCount stepCount) {
//
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        String[] date = stepCount.getDate().split(" ");
//
//        if (checkDate(stepCount.getDate(), db)) {
//            updateCountsDaily(stepCount, stepCount.getDate(), db);
//        } else {
//            addCountsDaily(stepCount, stepCount.getDate(), db);
//        }
//    }


/*    public void addDailyData(StepCount stepCount, SQLiteDatabase db) {

        String[] date = stepCount.getDate().split(" ");

        if (checkDate(date[0], db)) {
            updateCountsDaily(stepCount, date[0], db);
        } else {
            addCountsDaily(stepCount, date[0], db);
        }
    }*/

    /**
    * public method : For Add Hourly Data and manage Graph in Sensor Section
	*
	* @param StepCount : StepCount class contain all Hardware related 
	* @return void.
    */

    public void addHourlyData(StepCount stepCount) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String value = "0";
            value = checkHourly(stepCount.getDate(), db, stepCount);

            if (!value.equals("0")) {
                String newVal = (Integer.parseInt(value) + Integer.parseInt(stepCount.getSteps())) + "";
                stepCount.setSteps(newVal);
                updateCountsHourly(stepCount, db);
            } else {
                addCountsHourly(stepCount, db);
            }
            db.close(); // Closing database connection
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   
	/**
    * public method : Check if data exist into table
	*
	* @param String         : date 
	* @param SQLiteDatabase : database object reference 
	* @param StepCount      : StepCount class contain all Hardware related 
	* @return String as true or false.
    */

    public String checkHourly(String date, SQLiteDatabase db, StepCount stepCount) {

        String selectQuery = "SELECT * FROM " + TABLE_STEP_COUNT_HOUR + " WHERE " + CONNECTED_DATE + " ='" + date + "' and " + DEVICE_ID + "='" + stepCount.getDeviceId() + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        // boolean check = false;
        String steps = "0";
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    steps = cursor.getString(cursor.getColumnIndex(STEP_COUNT));
                    break;
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        return steps;
    }

  
	/**
    * public method : Update count hourly for every steps 
	*
	* @param SQLiteDatabase : database object reference 
	* @param StepCount      : StepCount class contain all Hardware related 
	* @return void.
    */
    public void updateCountsHourly(StepCount stepCount, SQLiteDatabase db) {
        String update = "UPDATE " + TABLE_STEP_COUNT_HOUR + " SET " + STEP_COUNT + "='" + stepCount.getSteps() + "', " + TOTAL_COUNT + "='" + stepCount.getTotalSteps() + "' WHERE " + CONNECTED_DATE + "='" + stepCount.getDate() + "' and " + DEVICE_ID + "='" + stepCount.getDeviceId() + "'";
        db.execSQL(update);
    }

   
	/**
    * public method : Add every count into table for backup on cloud
	*
	* @param SQLiteDatabase : database object reference 
	* @param StepCount      : StepCount class contain all Hardware related 
	* @return void.
    */
    public void addCountsHourly(StepCount stepCount, SQLiteDatabase db) {


        ContentValues values = new ContentValues();

        values.put(PET_ID, stepCount.getPetID());
        values.put(USER_EMAIL, stepCount.getUserEmailId());
        values.put(DEVICE_ID, stepCount.getDeviceId());
        values.put(CONNECTED_DATE, stepCount.getDate());
        values.put(IS_SYNCED, stepCount.getIsSynced());
        values.put(STEP_COUNT, stepCount.getSteps());
        values.put(TOTAL_COUNT, stepCount.getTotalSteps());
        db.insert(TABLE_STEP_COUNT_HOUR, null, values);

    }


  
	/**
    * public method : Get hourly progress for showing into graph
	*
	* @param String  : hardware device Id
	* @param String  : date object
	* @param boolean : boolean value 
	* @return void.
    */
    public String getHourlyForProgress(String deviceIds, String date, boolean isDifferDate) {
        String value = "0";
        int totalSteps = 0;
        try {
            ArrayList<StepCount> recentLst = new ArrayList<StepCount>();
            String selectQuery = "SELECT  * FROM " + TABLE_STEP_COUNT_HOUR + " WHERE " + DEVICE_ID + " ='" + deviceIds + "' and " + CONNECTED_DATE + " LIKE '" + date + "%' order by " + CONNECTED_DATE;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);


            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {

                        StepCount stepCount = new StepCount();

                        String deviceId = cursor.getString(cursor.getColumnIndex(DEVICE_ID));
                        String dateVal = cursor.getString(cursor.getColumnIndex(CONNECTED_DATE));
                        String count = cursor.getString(cursor.getColumnIndex(STEP_COUNT));
                        String total = cursor.getString(cursor.getColumnIndex(TOTAL_COUNT));
                        String petID = cursor.getString(cursor.getColumnIndex(PET_ID));
                        String userMail = cursor.getString(cursor.getColumnIndex(USER_EMAIL));
                        String isSynced = cursor.getString(cursor.getColumnIndex(IS_SYNCED));

                        totalSteps = totalSteps + getInt(count);

                        stepCount.setDate(dateVal);
                        stepCount.setDeviceId(deviceId);
                        stepCount.setSteps(count);
                        stepCount.setTotalSteps(total);
                        stepCount.setPetID(petID);
                        stepCount.setUserEmailId(userMail);
                        stepCount.setIsSynced(isSynced);

                        recentLst.add(stepCount);
                    } while (cursor.moveToNext());
                }
            }
            cursor.close();
            db.close();
            if (recentLst.size() > 0) {
                StepCount stepCount = recentLst.get(recentLst.size() - 1);
                value = stepCount.getTotalSteps();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (isDifferDate)
            return totalSteps + "";
        else
            return value + "";
    }

   
    public int getInt(String count) {
        int value = 0;
        try {
            value = value + Integer.parseInt(count);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

  
	/**
    * public method : Check if data exist into table
	*
	* @param String  : hardware device Id
	* @param String  : date object
	* @return Array List of StepCount Object.
    */
    
    public ArrayList<StepCount> getHourly(String deviceIds, String date) {
        ArrayList<StepCount> recentLst = new ArrayList<StepCount>();
        String selectQuery = "SELECT  * FROM " + TABLE_STEP_COUNT_HOUR + " WHERE " + DEVICE_ID + " ='" + deviceIds + "' and " + CONNECTED_DATE + " LIKE '" + date + "%'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {

                    StepCount stepCount = new StepCount();

                    String deviceId = cursor.getString(cursor.getColumnIndex(DEVICE_ID));
                    String dateVal = cursor.getString(cursor.getColumnIndex(CONNECTED_DATE));
                    String count = cursor.getString(cursor.getColumnIndex(STEP_COUNT));
                    String total = cursor.getString(cursor.getColumnIndex(TOTAL_COUNT));
                    String petID = cursor.getString(cursor.getColumnIndex(PET_ID));
                    String userMail = cursor.getString(cursor.getColumnIndex(USER_EMAIL));
                    String isSynced = cursor.getString(cursor.getColumnIndex(IS_SYNCED));

                    stepCount.setDate(dateVal);
                    stepCount.setDeviceId(deviceId);
                    stepCount.setSteps(count);
                    stepCount.setTotalSteps(total);
                    stepCount.setPetID(petID);
                    stepCount.setUserEmailId(userMail);
                    stepCount.setIsSynced(isSynced);

                    recentLst.add(stepCount);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return recentLst;
    }

   
	/**
    * public method : Get Daily count from as hourly
	*
	* @param String  : hardware device Id
	* @param String  : date object
	* @return StepCount Object.
    */
    public StepCount getDailyCountFromHourly(String deviceIds, String date) {
        String selectQuery = "SELECT  * FROM " + TABLE_STEP_COUNT_HOUR + " WHERE " + DEVICE_ID + " ='" + deviceIds + "' and " + CONNECTED_DATE + " LIKE '" + date + "%'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        StepCount stepCount = new StepCount();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {


                    String deviceId = cursor.getString(cursor.getColumnIndex(DEVICE_ID));
                    String dateVal = cursor.getString(cursor.getColumnIndex(CONNECTED_DATE));
                    String count = cursor.getString(cursor.getColumnIndex(STEP_COUNT));
                    String total = cursor.getString(cursor.getColumnIndex(TOTAL_COUNT));
                    String petID = cursor.getString(cursor.getColumnIndex(PET_ID));
                    String userMail = cursor.getString(cursor.getColumnIndex(USER_EMAIL));
                    String isSynced = cursor.getString(cursor.getColumnIndex(IS_SYNCED));

                    stepCount.setDate(dateVal);
                    stepCount.setDeviceId(deviceId);
                    stepCount.setSteps(count);
                    stepCount.setTotalSteps(total);
                    stepCount.setPetID(petID);
                    stepCount.setUserEmailId(userMail);
                    stepCount.setIsSynced(isSynced);

                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return stepCount;
    }


	/**
    * public method : Get All data data for uploading on cloud
	*
	* @return Array List of StepCount Object.
    */
    public ArrayList<StepCount> getUnSyncedData() {
        String selectQuery = "SELECT  * FROM " + TABLE_STEP_COUNT_HOUR;
        Log.e("amit", "" + selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<StepCount> stepCountLst = new ArrayList<>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {

                    StepCount stepCount = new StepCount();
                    String deviceId = cursor.getString(cursor.getColumnIndex(DEVICE_ID));
                    String dateVal = cursor.getString(cursor.getColumnIndex(CONNECTED_DATE));
                    String count = cursor.getString(cursor.getColumnIndex(STEP_COUNT));
                    String total = cursor.getString(cursor.getColumnIndex(TOTAL_COUNT));
                    String petID = cursor.getString(cursor.getColumnIndex(PET_ID));
                    String userMail = cursor.getString(cursor.getColumnIndex(USER_EMAIL));
                    String isSynced = cursor.getString(cursor.getColumnIndex(IS_SYNCED));

                    stepCount.setDate(dateVal);
                    stepCount.setDeviceId(deviceId);
                    stepCount.setSteps(count);
                    stepCount.setTotalSteps(total);
                    stepCount.setPetID(petID);
                    stepCount.setUserEmailId(userMail);
                    stepCount.setIsSynced(isSynced);

                    stepCountLst.add(stepCount);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return stepCountLst;
    }


	/**
    * public method : Food Management Add/Edit/Delete
	*
	* @param FoodPlan  : FoodPlan Object
	* @return void.
    */
    public void addFood(FoodPlan foodPlan) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            boolean bool = checkFood(db, foodPlan);
            if (bool) {
                updateFood(foodPlan, db);
            } else {
                insertFood(foodPlan, db);
            }
            db.close(); // Closing database connection
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	/**
    * public method : check if data exist
	*
	* @param SQLiteDatabase  : Database Object
	* @param FoodPlan  : FoodPlan Object
	* @return boolean as true or false.
    */
    public boolean checkFood(SQLiteDatabase db, FoodPlan foodPlan) {

        String selectQuery = "SELECT * FROM " + TABLE_FOOD + " WHERE " + F_PET_ID + " ='"
                + foodPlan.getPetId() + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        boolean check = false;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    check = true;
                    break;
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        return check;
    }

	/**
    * public method : update food record
	*
	* @param SQLiteDatabase  : Database Object
	* @param FoodPlan  : FoodPlan Object
	* @return void.
    */
    public void updateFood(FoodPlan foodPlan, SQLiteDatabase db) {
        String update = "UPDATE " + TABLE_FOOD + " SET " + F_PET_ID + "='" + foodPlan.getPetId() + "'" +
                "," + F_PRODUCT_ID + "='" + foodPlan.getProductId() + "'" +
                "," + F_PRODUCT_NAME + "='" + foodPlan.getProductName() + "'" +
                "," + F_COM_ID + "='" + foodPlan.getComId() + "'" +
                "," + F_COM_NAME + "='" + foodPlan.getComName() + "'" +
                "," + F_QUANTITY + "='" + foodPlan.getQuantity() + "'" +
                "," + F_UNIT + "='" + foodPlan.getUnit() + "' WHERE " + F_PET_ID + " ='" + foodPlan.getPetId() + "'";
        db.execSQL(update);
    }

	/**
    * public method : insert food record
	*
	* @param SQLiteDatabase  : Database Object
	* @param FoodPlan  : FoodPlan Object
	* @return void.
    */
    public void insertFood(FoodPlan foodPlan, SQLiteDatabase db) {

        ContentValues values = new ContentValues();

        values.put(F_PET_ID, foodPlan.getPetId());
        values.put(F_PRODUCT_ID, foodPlan.getProductId());
        values.put(F_PRODUCT_NAME, foodPlan.getProductName());
        values.put(F_COM_ID, foodPlan.getComId());
        values.put(F_COM_NAME, foodPlan.getComName());
        values.put(F_QUANTITY, foodPlan.getQuantity());
        values.put(F_UNIT, foodPlan.getUnit());
        db.insert(TABLE_FOOD, null, values);

    }

	/**
    * public method : get food record
	*
	* @param String  : petId to whom we have set food 
	* @return FoodPlan object.
    */
    public FoodPlan getFood(String petId) {
        String selectQuery = "SELECT  * FROM " + TABLE_FOOD + " WHERE " + F_PET_ID + "='" + petId + "'";
        FoodPlan foodPlan = new FoodPlan();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {


                    foodPlan.setPetId(cursor.getString(cursor.getColumnIndex(F_PET_ID)));
                    foodPlan.setProductId(cursor.getString(cursor.getColumnIndex(F_PRODUCT_ID)));
                    foodPlan.setProductName(cursor.getString(cursor.getColumnIndex(F_PRODUCT_NAME)));
                    foodPlan.setComId(cursor.getString(cursor.getColumnIndex(F_COM_ID)));
                    foodPlan.setComName(cursor.getString(cursor.getColumnIndex(F_COM_NAME)));
                    foodPlan.setUnit(cursor.getString(cursor.getColumnIndex(F_UNIT)));
                    foodPlan.setQuantity(cursor.getString(cursor.getColumnIndex(F_QUANTITY)));

                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return foodPlan;
    }
}