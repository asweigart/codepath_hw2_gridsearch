package com.al.gridimagesearch.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.al.gridimagesearch.R;


public class SettingsFragment extends DialogFragment {
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


    public SettingsFragment() {
        // Empty constructor required for DialogFragment
    }

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container);
        getDialog().setTitle(getResources().getString(R.string.Advanced_Search_Options));



        // find and store the views
        spImageSize = (Spinner) view.findViewById(R.id.spImageSize);
        spImageType = (Spinner) view.findViewById(R.id.spImageType);
        spColorFilter = (Spinner) view.findViewById(R.id.spColorFilter);
        etSiteFilter = (EditText) view.findViewById(R.id.etSiteFilter);

        mSettings = getActivity().getSharedPreferences(SETTINGS, 0);
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
                SharedPreferences.Editor editor = getActivity().getSharedPreferences(SETTINGS, 0).edit();
                editor.putInt(IMAGE_SIZE_SETTING, position);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        spImageType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences.Editor editor = getActivity().getSharedPreferences(SETTINGS, 0).edit();
                editor.putInt(IMAGE_TYPE_SETTING, position);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        spColorFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences.Editor editor = getActivity().getSharedPreferences(SETTINGS, 0).edit();
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
                SharedPreferences.Editor editor = getActivity().getSharedPreferences(SETTINGS, 0).edit();
                editor.putString(IMAGE_SITE_SETTING, s.toString());
                editor.commit();
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        return view;
    }
}
