package com.al.gridimagesearch.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.al.gridimagesearch.R;
import com.al.gridimagesearch.models.ImageResult;
import com.squareup.picasso.Picasso;
import com.diegocarloslima.byakugallery.lib.TouchImageView;

public class ImageDisplayActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);

        getSupportActionBar().hide();

        // Get the full url from the intent
        ImageResult result = getIntent().getParcelableExtra("result");
        TouchImageView ivImageResult = (TouchImageView) findViewById(R.id.ivImageResult);
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);

        // populate views
        Picasso.with(this).load(result.fullUrl).into(ivImageResult);
        tvTitle.setText(Html.fromHtml(result.title));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_display, menu);
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
}
