package com.al.gridimagesearch.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.al.gridimagesearch.R;
import com.al.gridimagesearch.views.TouchImageView;
import com.al.gridimagesearch.models.ImageResult;
import com.squareup.picasso.Picasso;

public class ImageDisplayActivity extends ActionBarActivity {
    private TouchImageView ivImageResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);

        // Get the full url from the intent
        ImageResult result = getIntent().getParcelableExtra("result");
        ivImageResult = (TouchImageView) findViewById(R.id.ivImageResult);
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);

        // populate views
        Picasso.with(this).load(result.fullUrl).placeholder(R.drawable.loading).into(ivImageResult);
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

    public void onShareAction(MenuItem mi) {
        Uri bmpUri = getLocalBitmapUri(ivImageResult);
        if (bmpUri != null) {
            // Construct a ShareIntent with link to image
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
            shareIntent.setType("image/*");
            // Launch sharing dialog for image
            startActivity(Intent.createChooser(shareIntent, "Share Image"));
        } else {
            // ...sharing failed, handle error
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.Failed_to_share_this_image), Toast.LENGTH_SHORT).show();
        }
    }

    // this code does not create a file
    public Uri getLocalBitmapUri(ImageView imageView) {
        Drawable mDrawable = ivImageResult.getDrawable();
        Bitmap mBitmap = ((BitmapDrawable)mDrawable).getBitmap();

        String path = MediaStore.Images.Media.insertImage(getContentResolver(),
                mBitmap, "Image Description", null);

        Uri uri = Uri.parse(path);
        return uri;
    }
}
