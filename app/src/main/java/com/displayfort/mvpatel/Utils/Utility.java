package com.displayfort.mvpatel.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.displayfort.mvpatel.MvPatelApplication;
import com.displayfort.mvpatel.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Locale;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

/**
 * Created by Husain on 07-03-2016.
 */
public class Utility {
    public static void ShowLog(String text, Context context) {
        Log.i(context.toString(), text);
    }

    public static void ShowToast(String text, Context context) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * Hides the soft input keyboard
     *
     * @param editText the container edit text for which soft keyboard is shown
     */
    public static void hideSoftKeyboard(EditText editText) {
        if (null != editText) {
            InputMethodManager imm = (InputMethodManager) editText.getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo networkinfo = cm.getActiveNetworkInfo();
        if (networkinfo != null && networkinfo.isConnected()) {
            return true;
        }
        return false;
    }

    public static void ShowSoftKeyboard(EditText editText) {
        if (null != editText) {
            InputMethodManager imm = (InputMethodManager) editText.getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus()
                .getWindowToken(), 0);
    }

    public static void showLog(String string) {
        Log.i("Utility", "VALUE:" + string);
    }


    public static String getDifference(Calendar startDateTime, Calendar endDateTime) {
        String timeDiff;
        if (startDateTime == null || endDateTime == null)
            return "0:00";
//        Calendar startDateTime = Calendar.getInstance();
//        startDateTime.setTime(startTime);
//        Calendar endDateTime = Calendar.getInstance();
//        endDateTime.setTime(endTime);
        long milliseconds1 = startDateTime.getTimeInMillis();
        long milliseconds2 = endDateTime.getTimeInMillis();
        long diff = milliseconds2 - milliseconds1;
        long hours = diff / (60 * 60 * 1000);
        long minutes = diff / (60 * 1000);
        minutes = minutes - 60 * hours;
        long seconds = diff / (1000);

        timeDiff = hours + ":" + minutes;
        return timeDiff;
    }

    public static String showPriceInUK(String price) {
        String value;
        try {
            value = showPriceInUK(Double.parseDouble(price));
        } catch (Exception e) {
            value = showPriceInUK(0.00);
        }
        return value;
    }


    public static String showPriceInUK(double price) {
        NumberFormat nf = (NumberFormat.getCurrencyInstance(Locale.ENGLISH));
        nf.setCurrency(Currency.getInstance(Locale.getDefault()));
        NumberFormat currencyFormat = NumberFormat
                .getCurrencyInstance(MvPatelApplication.getAppContext().getResources().getConfiguration().locale);
//        String moneyString = currencyFormat.format(price);
        NumberFormat numberFormat = new DecimalFormat("#,###.##");
        String moneyString = numberFormat.format(price);
        return MvPatelApplication.getAppContext().getString(R.string.Rs) + moneyString + " ";
        // + MvPatelApplication.getAppContext().getString(R.string.KWD);
    }

    public static String showPriceInUK(String price, boolean showKWD) {
        String value;
        try {
            value = showPriceInUK(Double.parseDouble(price), showKWD);
        } catch (Exception e) {
            value = showPriceInUK(0.00);
        }
        return value;
    }

    public static String showPriceInUK(double price, boolean showKWD) {
        NumberFormat nf = (NumberFormat.getCurrencyInstance(Locale.ENGLISH));
        nf.setCurrency(Currency.getInstance(Locale.getDefault()));
        NumberFormat currencyFormat = NumberFormat
                .getCurrencyInstance(MvPatelApplication.getAppContext().getResources().getConfiguration().locale);
//        String moneyString = currencyFormat.format(price);
        NumberFormat numberFormat = new DecimalFormat("#,###.000");
        String moneyString = numberFormat.format(price);
        if (showKWD) {
            return moneyString + " ";// + MvPatelApplication.getAppContext().getString(R.string.KWD);
        } else {
            return moneyString + " ";
        }
    }

    public static boolean isGPsEnabled(Context context) {
        final LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps(context);
            return false;
        } else {
            return true;
        }
    }

    private static void buildAlertMessageNoGps(final Context context) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        context.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();

    }

    public static String getText(Context context) {
        String jsonResponse = "";
        AssetManager assetManager = context.getAssets();
        InputStream input;
        try {
            input = assetManager.open("sample_json.txt");

            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();

            // byte buffer into a string
            String text = new String(buffer);
            jsonResponse = text;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonResponse;
    }

    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }


    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    public static DisplayMetrics getDisplayMetrix(Activity context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public static void setImage(final Context context, final String url, final ImageView imageView, Character type) {
        final String imageUrl = url.replace(" ", "%20");
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                Log.v("ImageLoader", "Could not fetch image " + exception.getMessage());
                exception.printStackTrace();
            }
        });
        builder.build().load(imageUrl).into(imageView);
        Log.v("IMAGEURL", "URL :- " + imageUrl);
        switch (type) {
            case 'G':
                try {
                    Glide.with(context).load(imageUrl)
                            .thumbnail(0.5f)
                            .apply(RequestOptions.placeholderOf(R.drawable.dummy_image).error(R.drawable.dummy_image))
                            .into(imageView);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            case 'A':

                AQuery query = new AQuery(context);
                if (imageUrl != null && !imageUrl.equalsIgnoreCase("") && isOnline(context)) {
                    File file = query.getCachedFile(imageUrl);
                    if (file != null && file.exists()) {
                        query.id(imageView).image(file, 0);
                    } else {
                        query.id(imageView).image(imageUrl, false, true, 0, 0);
                    }
                    // }
                }
                break;
            default:
                Picasso.with(context)
                        .load(imageUrl)
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .into(imageView, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {
                                Log.v("Picassoa", "Could not fetch image");
                                //Try again online if cache failed
                                Picasso.with(context)
                                        .load(imageUrl)
                                        .error(R.drawable.dummy_image)
                                        .into(imageView, new Callback() {
                                            @Override
                                            public void onSuccess() {

                                            }

                                            @Override
                                            public void onError() {
                                                Log.v("Picasso", "Could not fetch image");
                                            }
                                        });
                            }
                        });
                break;
        }
    }

    public static void setImage(final Context context, String url, final ImageView imageView) {

        setImage(context, url, imageView, 'P');


    }

    public String loadJSONFromAsset(String filename, Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    public static Bitmap getBitmap(String imageUrl) {
        Bitmap image = null;
        Log.d("onBitmapLoad", imageUrl + "");
        try {
            java.net.URL url = new URL(imageUrl);
            image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            Log.d("onBitmapLoaded B", image.getWidth() + "");
        } catch (IOException e) {
            System.out.println(e);
        }
        return image;

    }

    public static Uri getBitmapFromDrawable(Bitmap bmp, Context context) {

        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            // Use methods on Context to access package-specific directories on external storage.
            // This way, you don't need to request external read/write permission.
            // See https://youtu.be/5xVh-7ywKpE?t=25m25s
            File file = new File(context.getFilesDir(), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.close();
            if (Build.VERSION.SDK_INT >= 24) {
                // wrap File object into a content provider. NOTE: authority here should match authority in manifest declaration
                bmpUri = FileProvider.getUriForFile(context, "com.displayfort.mvpatel", file);  // use this version for API >= 24
            } else {
                bmpUri = Uri.fromFile(file);
            }

            // **Note:** For API < 24, you may use bmpUri = Uri.fromFile(file);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }
}