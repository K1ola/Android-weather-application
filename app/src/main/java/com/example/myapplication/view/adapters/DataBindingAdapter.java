package com.example.myapplication.view.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

public class DataBindingAdapter {
    public static Context context;
    @BindingAdapter("imageResource")
    public static void setImageResource(ImageView imageView, String resource){
        Resources resources = context.getResources();
        if (resource == null) return;
        final int resourceId = resources.getIdentifier(resource, "drawable",
                context.getPackageName());
        imageView.setImageResource(resourceId);
    }
}
