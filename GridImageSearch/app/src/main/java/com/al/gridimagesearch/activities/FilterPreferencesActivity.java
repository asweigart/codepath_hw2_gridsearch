package com.al.gridimagesearch.activities;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.al.gridimagesearch.R;

public class FilterPreferencesActivity extends ActionBarActivity {
    private Spinner spImageSize;
    private Spinner spImageType;
    private Spinner spColorFilter;
    private EditText etSiteFilter;
    private SharedPreferences mSettings;

    // constants for the indexes of the string arrays for spinner options
    public static int ALL_SIZES = 0;
    public static int ICON = 1;
    public static int SMALL = 2;
    public static int MEDIUM = 3;
    public static int LARGE = 4;
    public static int X_LARGE = 5;
    public static int XX_LARGE = 6;
    public static int HUGE = 7;

    public static int ALL_TYPES = 0;
    public static int FACE = 1;
    public static int PHOTO = 2;
    public static int CLIPART = 3;
    public static int LINE_ART = 4;

    public static int ALL_COLORS = 0;
    public static int BLACK = 1;
    public static int BLUE = 2;
    public static int BROWN = 3;

    public static String SETTINGS = "Settings";
    public static String NO_STRING_SETTING = "no setting";
    public static int NO_INT_SETTING = -1;
    public static String IMAGE_SIZE_SETTING = "imageSize";
    public static String IMAGE_TYPE_SETTING = "imageType";
    public static String IMAGE_COLOR_SETTING = "imageColor";
    public static String IMAGE_SITE_SETTING = "imageSite";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_preferences);

        getSupportActionBar().hide();

        // find and store the views
        spImageSize = (Spinner) findViewById(R.id.spImageSize);
        spImageType = (Spinner) findViewById(R.id.spImageType);
        spColorFilter = (Spinner) findViewById(R.id.spColorFilter);
        etSiteFilter = (EditText) findViewById(R.id.etSiteFilter);

        mSettings = getApplicationContext().getSharedPreferences(SETTINGS, 0);
        SharedPreferences.Editor editor = mSettings.edit();

        if (mSettings.getInt(IMAGE_SIZE_SETTING, NO_INT_SETTING) == NO_INT_SETTING) {
            editor.putInt(IMAGE_SIZE_SETTING, ALL_SIZES);
        } else {
            spImageSize.setSelection(mSettings.getInt(IMAGE_SIZE_SETTING, 0));
        }


        if (mSettings.getInt(IMAGE_TYPE_SETTING, NO_INT_SETTING) == NO_INT_SETTING) {
            editor.putInt(IMAGE_TYPE_SETTING, ALL_TYPES);
        } else {
            spImageType.setSelection(mSettings.getInt(IMAGE_TYPE_SETTING, 0));
        }

        if (mSettings.getInt(IMAGE_COLOR_SETTING, NO_INT_SETTING) == NO_INT_SETTING) {
            editor.putInt(IMAGE_COLOR_SETTING, ALL_COLORS);
        } else {
            spColorFilter.setSelection(mSettings.getInt(IMAGE_COLOR_SETTING, 0));
        }

        if (mSettings.getString(IMAGE_SITE_SETTING, NO_STRING_SETTING).equals(NO_STRING_SETTING)) {
            editor.putString(IMAGE_SITE_SETTING, "");
        } else {
            etSiteFilter.setText(mSettings.getString(IMAGE_SITE_SETTING, ""));
        }

        editor.commit();

        // Set up the listeners for the spinners
        spImageSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences(SETTINGS, 0).edit();
                editor.putInt(IMAGE_SIZE_SETTING, position);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        spImageType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences(SETTINGS, 0).edit();
                editor.putInt(IMAGE_TYPE_SETTING, position);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        spColorFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences(SETTINGS, 0).edit();
                editor.putInt(IMAGE_COLOR_SETTING, position);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        etSiteFilter.addTextChangedListener(new TextWatcher() { // TODO: This is probably inefficient since it saves on each character change.
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences(SETTINGS, 0).edit();
                editor.putString(IMAGE_SITE_SETTING, s.toString());
                editor.commit();
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filter_preferences, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onResetClick(View v) {
        // reset all the settings back to the "All" defaults.
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putInt(IMAGE_SIZE_SETTING, 0);
        editor.putInt(IMAGE_TYPE_SETTING, 0);
        editor.putInt(IMAGE_COLOR_SETTING, 0);
        editor.putString(IMAGE_SITE_SETTING, "");
        editor.commit();

        spImageSize.setSelection(0);
        spColorFilter.setSelection(0);
        spImageType.setSelection(0);
        etSiteFilter.setText("");
    }
}
