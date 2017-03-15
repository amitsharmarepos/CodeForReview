package com.poochplay.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.ExifInterface;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.poochplay.R;
import com.poochplay.activity.SysApplication;
import com.poochplay.dto.AddPetDTO;
import com.poochplay.dto.VaccinationDTO;
import com.poochplay.sharedprefrence.SharedPrefrence;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Advantal on 21-Mar-16.
 */
public class ProjectUtils {

    public static final String TAG = "ProjectUtility";
    private static AlertDialog dialog;
    private static Toast toast;
    private static ProgressDialog mProgressDialog;
    private static final String VERSION_UNAVAILABLE = "N/A";

    public static ArrayList<String> countryList = new ArrayList<>();



    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    //For Changing Status Bar Color if Device is above 5.0(Lollipop)
    public static void changeStatusBarColor(Activity activity) {

        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(activity.getResources().getColor(R.color.base_color));
        }
    }

    public static void changeStatusBarColorNew(Activity activity, int color) {
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(activity.getResources().getColor(color));
        }
    }


    //For Progress Alert Box
    public static ProgressDialog getProgressDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(context.getResources().getString(R.string.please_wait));
        return progressDialog;
    }

    //For Long Period Toast Message
    public static void showLong(Context context, String message) {
        if (message == null) {
            return;
        }
        if (toast == null && context != null) {
            toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        }
        if (toast != null) {
            toast.setText(message);
            toast.show();
        }
    }

    // For Alert in App
    public static Dialog createDialog(Context context, int titleId, int messageId,
                                      DialogInterface.OnClickListener positiveButtonListener,
                                      DialogInterface.OnClickListener negativeButtonListener) {
        Builder builder = new Builder(context);
        builder.setTitle(titleId);
        builder.setMessage(messageId);
        builder.setPositiveButton(R.string.ok, positiveButtonListener);
        builder.setNegativeButton(R.string.cancel, negativeButtonListener);

        return builder.create();
    }

    // For Alert on Custom View in App
    public static Dialog createDialog(Context context, int titleId, int messageId, View view,
                                      DialogInterface.OnClickListener positiveClickListener,
                                      DialogInterface.OnClickListener negativeClickListener) {

        Builder builder = new Builder(context);
        builder.setTitle(titleId);
        builder.setMessage(messageId);
        builder.setView(view);
        builder.setPositiveButton(R.string.ok, positiveClickListener);
        builder.setNegativeButton(R.string.cancel, negativeClickListener);

        return builder.create();
    }

    public static void showDialog(Context context, String title, String msg,
                                  DialogInterface.OnClickListener OK, boolean isCancelable) {

        if (title == null)
            title = context.getResources().getString(R.string.app_name);

        if (OK == null)
            OK = new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    hideDialog();
                }
            };

        if (dialog == null) {
            Builder builder = new Builder(context);
            builder.setTitle(title);
            builder.setMessage(msg);
            builder.setPositiveButton("OK", OK);
            dialog = builder.create();
            dialog.setCancelable(isCancelable);
        }

        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Static method to show the dialog with custom message on it
     *
     * @param context      Context of the activity where to show the dialog
     * @param title        Title to be shown either custom or application name
     * @param msg          Custom message to be shown on dialog
     * @param OK           Overridden click listener for OK button in dialog
     * @param cancel       Overridden click listener for cancel button in dialog
     * @param isCancelable : Sets whether this dialog is cancelable with the BACK key.
     */
    public static void showDialog(Context context, String title, String msg,
                                  DialogInterface.OnClickListener OK,
                                  DialogInterface.OnClickListener cancel, boolean isCancelable) {

        if (title == null)
            title = context.getResources().getString(R.string.app_name);

        if (OK == null)
            OK = new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface paramDialogInterface,
                                    int paramInt) {
                    hideDialog();
                }
            };

        if (cancel == null)
            cancel = new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface paramDialogInterface,
                                    int paramInt) {
                    hideDialog();
                }
            };

        if (dialog == null) {
            Builder builder = new Builder(context);
            builder.setTitle(title);
            builder.setMessage(msg);
            builder.setPositiveButton("OK", OK);
            builder.setNegativeButton("Cancel", cancel);
            dialog = builder.create();
            dialog.setCancelable(isCancelable);
        }

        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Static method to show the progress dialog.
     *
     * @param context      : Context of the activity where to show the dialog
     * @param isCancelable : Sets whether this dialog is cancelable with the BACK key.
     * @param message      : Message to be shwon on the progress dialog.
     * @return Object of progress dialog.
     */
    public static Dialog showProgressDialog(Context context,
                                            boolean isCancelable, String message) {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
        mProgressDialog.setCancelable(isCancelable);
        return mProgressDialog;
    }

    /**
     * Static method to pause the progress dialog.
     */
    public static void pauseProgressDialog() {
        try {
            if (mProgressDialog != null) {
                mProgressDialog.cancel();
                mProgressDialog.dismiss();
                mProgressDialog = null;
            }
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Static method to cancel the Dialog.
     */
    public static void cancelDialog() {

        try {
            if (dialog != null) {
                dialog.cancel();
                dialog.dismiss();
                dialog = null;
            }
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Static method to hide the dialog if visible
     */
    public static void hideDialog() {

        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog.cancel();
            dialog = null;
        }
    }

    /**
     * This method will create alert dialog
     *
     * @param context  Context of calling class
     * @param title    Title of the dialog to be shown
     * @param msg      Msg of the dialog to be shown
     * @param btnText  array of button texts
     * @param listener
     */
    public static void showAlertDialog(Context context, String title,
                                       String msg, String btnText,
                                       DialogInterface.OnClickListener listener) {

        if (listener == null)
            listener = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface paramDialogInterface,
                                    int paramInt) {
                    paramDialogInterface.dismiss();
                    paramDialogInterface.dismiss();
                }
            };

        Builder builder = new Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton(btnText, listener);
        dialog = builder.create();
        dialog.setCancelable(false);
        try {
            dialog.show();
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    public static void showAlertDialogWithCancel(Context context, String title,
                                                 String msg, String btnText,
                                                 DialogInterface.OnClickListener listener, String cancelTxt, DialogInterface.OnClickListener cancelListenr) {

        if (listener == null)
            listener = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface paramDialogInterface,
                                    int paramInt) {
                    paramDialogInterface.dismiss();
                }
            };
        if (cancelListenr == null) {
            cancelListenr = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            };
        }

        Builder builder = new Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setIcon(context.getResources().getDrawable(R.drawable.app_icon));
        builder.setPositiveButton(btnText, listener);
        builder.setNegativeButton(cancelTxt, cancelListenr);
        dialog = builder.create();
        dialog.setCancelable(false);
        try {
            dialog.show();
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    public static AlertDialog showCustomtDialog(Context context,
                                                String title, String msg, String[] btnText, int layout_id,
                                                DialogInterface.OnClickListener listener) {
        if (listener == null)
            listener = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface paramDialogInterface,
                                    int paramInt) {
                    paramDialogInterface.dismiss();
                }
            };
        LayoutInflater factory = LayoutInflater.from(context);
        final View textEntryView = factory.inflate(layout_id,
                null);
        Builder builder = new Builder(context);
        builder.setTitle(title);
        // builder.setMessage(msg);
        // builder.setView(mEmail_forgot);

        builder.setPositiveButton(btnText[0], listener);
        if (btnText.length != 1) {
            builder.setNegativeButton(btnText[1], listener);
        }
        dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setView(textEntryView, 10, 10, 10, 10);
        dialog.show();
        return dialog;

    }

    /**
     * Checks the validation of email address.
     * Takes pattern and checks the text entered is valid email address or not.
     *
     * @param email : email in string format
     * @return True if email address is correct.
     */
    public static boolean isEmailValid(String email) {
        String expression = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            return true;
        } else if (email.equals("")) {
            return false;
        }
        return false;
    }


    /**
     * Method checks if the given phone number is valid or not.
     *
     * @param number : Phone number is to be checked.
     * @return True if the number is valid.
     * False if number is not valid.
     */
    public static boolean isPhoneNumberValid(String number) {

        //String regexStr = "^([0-9\\(\\)\\/\\+ \\-]*)$";
        String regexStr = "^((0)|(91)|(00)|[7-9]){1}[0-9]{3,14}$";

        if (number.length() < 10 || number.length() > 13 || number.matches(regexStr) == false) {
            //	Log.d("tag", "Number is not valid");
            return false;
        }

        return true;
    }

    public static boolean isPasswordValid(String number) {

        //String regexStr = "^([0-9\\(\\)\\/\\+ \\-]*)$";
        String regexStr = " (?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{8,20})$";

        if (number.length() < 10 || number.length() > 13 || number.matches(regexStr) == false) {
            //	Log.d("tag", "Number is not valid");
            return false;
        }

        return true;
    }


    /**
     * Checks if any text box is null or not.
     *
     * @param text : Text view for which validation is to be checked.
     * @return True if not null.
     */
    public static boolean isEditTextFilled(EditText text) {
        if (text.getText() != null && text.getText().toString().trim().length() > 0) {
            return true;
        } else {
            return false;
        }
    }
	/**
     * Check password length 
     *
     * @param EditText  : EditText object
     * @return void.
     */
    public static boolean isPasswordLengthCorrect(EditText text) {
        if (text.getText() != null && text.getText().toString().trim().length() >= 8) {
            return true;
        } else {
            return false;
        }
    }
	/**
     * Check internet connectivity
     *
     * @param Context  : Context object
     * @return boolean.
     */
    public static boolean isNetworkConnected(Context mContext) {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            return false;
        } else
            return true;
    }

    public static void InternetAlertDialog(Context mContext) {
        Builder alertDialog = new Builder(mContext);

        //Setting Dialog Title
        alertDialog.setTitle("Error Connecting");

        //Setting Dialog Message
        alertDialog.setMessage("No Internet Connection");

        //On Pressing Setting button
        alertDialog.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

	/**
     * check for service is running 
     */
    public static boolean isMyServiceRunning(Class<?> serviceClass, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static int getAppVersion(Context ctx) {
        try {
            PackageInfo packageInfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    public static float getDpi(Activity activity) {
        float dp = 0;
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        if (metrics.density == 3.0) {
            dp = 1;
        }
        return dp;
    }
	/**
     * save bitmap into disk 
     *
     * @param int      : blobId for mapping bitmap
     * @param Bitmap   : Bitmap object
     * @param Context  : Context object
     * @return boolean.
     */
    public static void putBitmapInDiskCache(int blobId, Bitmap avatar, Context mContext) {
        // Create a path pointing to the system-recommended cache dir for the app, with sub-dir named
        // thumbnails
        File cacheDir = new File(mContext.getCacheDir(), "thumbnails-nudgebuddies");
        if (!cacheDir.exists())
            cacheDir.mkdir();
        // Create a path in that dir for a file, named by the default hash of the url

        File cacheFile = new File(cacheDir, "" + blobId);
        try {
            // Create a file at the file path, and open it for writing obtaining the output stream
            cacheFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(cacheFile);
            // Write the bitmap to the output stream (and thus the file) in PNG format (lossless compression)
            avatar.compress(Bitmap.CompressFormat.PNG, 100, fos);
            // Flush and close the output stream
            fos.flush();
            fos.close();
            avatar.recycle();
        } catch (Exception e) {
            // Log anything that might go wrong with IO to file
            Log.e("IMAGE CACHE", "Error when saving image to cache. ", e);
        }
    }

	/**
     * save bitmap into disk 
     *
     * @param int      : blobId for mapping bitmap
     * @param Context  : Context object
     * @return boolean.
     */
    public static Bitmap getBitmapFromDiskCache(int blobId, Context mContext) {
        // Create a path pointing to the system-recommended cache dir for the app, with sub-dir named
        // thumbnails
        Bitmap avatar = null;
        File cacheDir = new File(mContext.getCacheDir(), "thumbnails-nudgebuddies");
        // Create a path in that dir for a file, named by the default hash of the url

        File cacheFile = new File(cacheDir, "" + blobId);
        try {
            if (cacheFile.exists()) {

                FileInputStream fis = new FileInputStream(cacheFile);
                // Read a bitmap from the file (which presumable contains bitmap in PNG format, since
                // that's how files are created)
                avatar = BitmapFactory.decodeStream(fis);
                // Write the bitmap to the output stream (and thus the file) in PNG format (lossless compression)
            }
            // Create a file at the file path, and open it for writing obtaining the output stream
            // Flush and close the output stream
        } catch (Exception e) {
            // Log anything that might go wrong with IO to file
            Log.e("IMAGE CACHE", "Error when saving image to cache. ", e);
        }
        return avatar;
    }

   /**
     * get image in month
     *
     * @param String      : string date
     * @return boolean.
     */
    public static String getAgeInMonth(String strDate) {
        int years = 0;
        int months = 0;
        int days = 0;

        try {
            long timeInMillis = Long.parseLong(strDate);
            Date birthDate = new Date(timeInMillis);
            //create calendar object for birth day
            Calendar birthDay = Calendar.getInstance();
            birthDay.setTimeInMillis(birthDate.getTime());
            //create calendar object for current day
            long currentTime = System.currentTimeMillis();
            Calendar now = Calendar.getInstance();
            now.setTimeInMillis(currentTime);
            //Get difference between years
            years = now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
            int currMonth = now.get(Calendar.MONTH) + 1;
            int birthMonth = birthDay.get(Calendar.MONTH) + 1;
            //Get difference between months
            months = currMonth - birthMonth;
            //if month difference is in negative then reduce years by one and calculate the number of months.
            if (months < 0) {
                years--;
                months = 12 - birthMonth + currMonth;
                if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
                    months--;
            } else if (months == 0 && now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
                years--;
                months = 11;
            }
            //Calculate the days
            if (now.get(Calendar.DATE) > birthDay.get(Calendar.DATE))
                days = now.get(Calendar.DATE) - birthDay.get(Calendar.DATE);
            else if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
                int today = now.get(Calendar.DAY_OF_MONTH);
                now.add(Calendar.MONTH, -1);
                days = now.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay.get(Calendar.DAY_OF_MONTH) + today;
            } else {
                days = 0;
                if (months == 12) {
                    years++;
                    months = 0;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        int month = (years * 12) + months;
        //Create new Age object
//        return years + " Y " + months + " M " + days + " days";
        return month + "";
    }

	/**
     * calculate age 
     *
     * @param String      : string date
     * @return String as age.
     */
    public static String calculateAge(String strDate) {

        int years = 0;
        int months = 0;
        int days = 0;

        try {
            long timeInMillis = Long.parseLong(strDate);
            Date birthDate = new Date(timeInMillis);


            //create calendar object for birth day
            Calendar birthDay = Calendar.getInstance();
            birthDay.setTimeInMillis(birthDate.getTime());
            //create calendar object for current day
            long currentTime = System.currentTimeMillis();
            Calendar now = Calendar.getInstance();
            now.setTimeInMillis(currentTime);
            //Get difference between years
            years = now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
            int currMonth = now.get(Calendar.MONTH) + 1;
            int birthMonth = birthDay.get(Calendar.MONTH) + 1;
            //Get difference between months
            months = currMonth - birthMonth;
            //if month difference is in negative then reduce years by one and calculate the number of months.
            if (months < 0) {
                years--;
                months = 12 - birthMonth + currMonth;
                if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
                    months--;
            } else if (months == 0 && now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
                years--;
                months = 11;
            }
            //Calculate the days
            if (now.get(Calendar.DATE) > birthDay.get(Calendar.DATE))
                days = now.get(Calendar.DATE) - birthDay.get(Calendar.DATE);
            else if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
                int today = now.get(Calendar.DAY_OF_MONTH);
                now.add(Calendar.MONTH, -1);
                days = now.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay.get(Calendar.DAY_OF_MONTH) + today;
            } else {
                days = 0;
                if (months == 12) {
                    years++;
                    months = 0;
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        //Create new Age object
        return years + " Y " + months + " M " + days + " days";
    }

    public static boolean hasPermissionInManifest(Activity activity, int requestCode, String permissionName) {
        if (ContextCompat.checkSelfPermission(activity,
                permissionName)
                != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(activity,
                    new String[]{permissionName},
                    requestCode);
        } else {
            return true;
        }
        return false;
    }

   
	/**
     * calculate age 
     *
     * @param String      : string date
     * @return String as age.
     */

    public static String getFormattedHourly(String strDate) {


        String formattedDate = "";
        try {
            DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            DateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = originalFormat.parse(strDate);

            formattedDate = targetFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return formattedDate;
    }

	/**
     * Formatted time - human readable time string
     *
     * @param String      : string strTime
     * @return String as time.
     */
    public static String getFormattedTime(String strTime) {


        String formattedTime = "";
        try {
            long timeInMillis = Long.parseLong(strTime);
            Date date = new Date(timeInMillis);
            SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");

            formattedTime = formatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return formattedTime;
    }
	/**
     * Formatted time - human readable time string
     *
     * @param String      : string longVal
     * @return Long as time.
     */
    public static long parseLong(String longVal) {
        long val = 1483294809;
        try {
            val = Long.parseLong(longVal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return val;
    }

	/**
     * Screen Name on Goggle Analytic
     *
     * @param SysApplication : System Application
     * @param String         : Screen name
     * @return Long as time.
     */
    public static void showAnalytics(SysApplication sysApplication, String nameOfScreen) {
        try {
            Tracker mTracker = sysApplication.getDefaultTracker();
            mTracker.setScreenName(nameOfScreen);
            mTracker.send(new HitBuilders.ScreenViewBuilder().build());
            mTracker.enableExceptionReporting(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	/**
     * get current date
     *
     */
    public static String giveCurrentDate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy");
        return sdf.format(cal.getTime());
    }
}