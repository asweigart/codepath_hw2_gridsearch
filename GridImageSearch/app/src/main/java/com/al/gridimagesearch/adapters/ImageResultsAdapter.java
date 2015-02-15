package com.al.gridimagesearch.adapters;

import android.content.Context;
import android.text.Html;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.al.gridimagesearch.R;
import com.al.gridimagesearch.models.ImageResult;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 *
 */
public class ImageResultsAdapter extends ArrayAdapter<ImageResult> {

    public ImageResultsAdapter(Context context, List<ImageResult> images) {
        super(context, android.R.layout.simple_list_item_1, images);
    }

    private static class ViewHolder {
        ImageView ivImage;
        TextView tvTitle;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageResult imageRes = getItem(position);
        ViewHolder vh; // used for the ViewHolder pattern

        if (convertView == null) {
            vh = new ViewHolder();

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result, parent, false);
            vh.ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
            vh.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        // populate the views
        vh.ivImage.setImageResource(0); // clear the image view
        Picasso.with(getContext()).load(imageRes.thumbUrl).into(vh.ivImage);
        vh.tvTitle.setText(Html.fromHtml(imageRes.title));

        return convertView;
    }
}
