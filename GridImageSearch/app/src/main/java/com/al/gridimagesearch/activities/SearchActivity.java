package com.al.gridimagesearch.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.al.gridimagesearch.R;
import com.al.gridimagesearch.adapters.ImageResultsAdapter;
import com.al.gridimagesearch.controllers.EndlessScrollListener;
import com.al.gridimagesearch.models.ImageResult;
import com.etsy.android.grid.StaggeredGridView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SearchActivity extends ActionBarActivity {
    private static int RESULT_SIZE = 8;

    private SearchView searchView;
    private StaggeredGridView gvResults;
    private static String IMAGE_SEARCH_URL = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=" + String.valueOf(RESULT_SIZE) + "&q=";
    private ArrayList<ImageResult> imageResults;
    private ImageResultsAdapter aImageResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Set up views & members
        gvResults = (StaggeredGridView) findViewById(R.id.gvResults);
        // set up code so that clicking on thumbnail launches full image display activity (ImageDisplayActivity)
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Launch the ImageDisplayActivity
                Intent i = new Intent(SearchActivity.this, ImageDisplayActivity.class);
                ImageResult result = imageResults.get(position);
                i.putExtra("result", result);
                startActivity(i);
            }
        });
        imageResults = new ArrayList<ImageResult>();
        aImageResult = new ImageResultsAdapter(this, imageResults); // attach adapter to data source
        gvResults.setAdapter(aImageResult); // link adapter to the grid view

        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                fetchSearchResults(searchView.getQuery().toString(), page, false);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                fetchSearchResults(s, 0, true);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
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

    public void onImageSearch(View v) {
        // Search button pressed; perform the image search based on the query text and filters.
        String query = searchView.getQuery().toString();
        this.imageResults.clear(); // clear the images from the array (clear out old search results)
        fetchSearchResults(query, 0, true);
        //fetchSearchResults(query, 1); // TODO - Bug in EndlessScroller that I should bring up.
    }

    private void fetchSearchResults(String query, int page, boolean clearResults) {
        if (clearResults) {
            this.imageResults.clear(); // clear the images from the array (clear out old search results)
        }

        AsyncHttpClient client = new AsyncHttpClient();
        String fullRequestUrl = IMAGE_SEARCH_URL + query + "&start=" + String.valueOf(page * RESULT_SIZE);

        // Get the preferences and append the url string for them.
        SharedPreferences mSettings = getApplicationContext().getSharedPreferences(SettingsFragment.SETTINGS, 0);
        if (mSettings.getInt(SettingsFragment.IMAGE_SIZE_SETTING, SettingsFragment.NO_INT_SETTING) != SettingsFragment.NO_INT_SETTING) {
            int sizePref = mSettings.getInt(SettingsFragment.IMAGE_SIZE_SETTING, 0);
            if (sizePref == 1) {
                fullRequestUrl += "&imgsz=icon";
            } else if (sizePref == 2) {
                fullRequestUrl += "&imgsz=small";
            } else if (sizePref == 3) {
                fullRequestUrl += "&imgsz=medium";
            } else if (sizePref == 3) {
                fullRequestUrl += "&imgsz=large";
            } else if (sizePref == 4) {
                fullRequestUrl += "&imgsz=xlarge";
            } else if (sizePref == 5) {
                fullRequestUrl += "&imgsz=xxlarge";
            } else if (sizePref == 6) {
                fullRequestUrl += "&imgsz=huge";
            }
        }

        if (mSettings.getInt(SettingsFragment.IMAGE_COLOR_SETTING, SettingsFragment.NO_INT_SETTING) != SettingsFragment.NO_INT_SETTING) {
            int colorPref = mSettings.getInt(SettingsFragment.IMAGE_COLOR_SETTING, 0);
            if (colorPref == 1) {
                fullRequestUrl += "&imgcolor=black";
            } else if (colorPref == 2) {
                fullRequestUrl += "&imgcolor=blue";
            } else if (colorPref == 3) {
                fullRequestUrl += "&imgcolor=brown";
            } else if (colorPref == 4) {
                fullRequestUrl += "&imgcolor=gray";
            } else if (colorPref == 5) {
                fullRequestUrl += "&imgcolor=green";
            } else if (colorPref == 6) {
                fullRequestUrl += "&imgcolor=orange";
            } else if (colorPref == 7) {
                fullRequestUrl += "&imgcolor=pink";
            } else if (colorPref == 8) {
                fullRequestUrl += "&imgcolor=purple";
            } else if (colorPref == 9) {
                fullRequestUrl += "&imgcolor=red";
            } else if (colorPref == 10) {
                fullRequestUrl += "&imgcolor=teal";
            } else if (colorPref == 11) {
                fullRequestUrl += "&imgcolor=white";
            } else if (colorPref == 12) {
                fullRequestUrl += "&imgcolor=yellow";
            }
        }

        if (mSettings.getInt(SettingsFragment.IMAGE_TYPE_SETTING, SettingsFragment.NO_INT_SETTING) != SettingsFragment.NO_INT_SETTING) {
            int typePref = mSettings.getInt(SettingsFragment.IMAGE_TYPE_SETTING, 0);
            if (typePref == 1) {
                fullRequestUrl += "&imgtype=face";
            } else if (typePref == 2) {
                fullRequestUrl += "&imgtype=photo";
            } else if (typePref == 3) {
                fullRequestUrl += "&imgtype=clipart";
            } else if (typePref == 3) {
                fullRequestUrl += "&imgtype=lineart";
            }
        }

        if (!mSettings.getString(SettingsFragment.IMAGE_SITE_SETTING, SettingsFragment.NO_STRING_SETTING).equals(SettingsFragment.NO_STRING_SETTING)) {
            String sitePref = mSettings.getString(SettingsFragment.IMAGE_SITE_SETTING, "");
            if (!sitePref.equals("")) {
                fullRequestUrl += "&as_sitesearch=" + sitePref;
            }
        }

        client.get(fullRequestUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // successfully obtained image search results, now parse them

                JSONArray imageResultsJson = null;
                try {
                    imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
                    aImageResult.addAll(ImageResult.fromJSONArray(imageResultsJson)); // populate with ImageResult objects
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                // failed to do image search
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.No_internet_connection_right_now), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onSettingsAction(MenuItem mi) {
        /*
        Intent i = new Intent(this, FilterPreferencesActivity.class);
        startActivity(i);
        */
        FragmentManager fm = getSupportFragmentManager();
        SettingsFragment editNameDialog = SettingsFragment.newInstance();
        editNameDialog.show(fm, "fragment_settings");
    }
}
