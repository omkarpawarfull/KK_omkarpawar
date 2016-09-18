package com.kismatkonnect.kismatkonnect;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by mou on 8/30/16.
 */
public class Activity10_Viewpager_Adaper extends PagerAdapter {


    private String[] content = {"Join for free today!",
            "Find your match with our powerful algorithms",
            "Match with people who you have crossed paths with"};
    private Context context;
    private LayoutInflater layoutInflater;

    public Activity10_Viewpager_Adaper(Context ctx){
        this.context = ctx;
    }


    @Override
    public int getCount() {
        return content.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.activity10_onboarding_viewpager_txtview, container, false);
        TextView txtView = (TextView) view.findViewById(R.id.activity10_viewpager_txtview);
        txtView.setText(content[position]);
        Typeface newFont = Typeface.createFromAsset(context.getAssets(), context.getString(R.string.custom_font));
        txtView.setTypeface(newFont);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}
