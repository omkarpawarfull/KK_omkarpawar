package com.kismatkonnect.kismatkonnect;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.Dimension;
import android.support.annotation.Size;
import android.support.v4.view.PagerAdapter;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by mou on 8/30/16.
 */
public class Activity15_Viewpager_Adaper extends PagerAdapter {

    private int[] img_res = {
            R.drawable.free_trial_phone,
            R.drawable.free_trial_reports,
            R.drawable.free_trial_features
    };
    private String[] txt_title = {
            "Call Your Match",
            "View Custom Weekly Reports",
            "Extra Features"
    };
    private String[][] txt_content = {
            {"Call your match without giving\nyour phone number"},
            {"See the complete list of people who like you", "List of recent visitors"},
            {"Ability to see mutual friends", "Message read receipts"}
    };
    private Context context;
    private LayoutInflater layoutInflater;

    public Activity15_Viewpager_Adaper(Context ctx){
        this.context = ctx;
    }


    @Override
    public int getCount() {
        return txt_title.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.activity15_onboarding_viewpager_layout, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.activity15_viewpager_image);
        imageView.setImageResource(img_res[position]);
        TextView title = (TextView) view.findViewById(R.id.activity15_viewpager_txtview_title);
        title.setText(txt_title[position]);
        Typeface newFont = Typeface.createFromAsset(context.getAssets(), context.getString(R.string.custom_font));
        title.setTypeface(newFont);
        LinearLayout detail_layout = (LinearLayout) view.findViewById(R.id.activity15_viewpager_detail_group);
        for(int i=0; i< txt_content[position].length; ++i) {
            //each detail LinearLayout
            LinearLayout linearLayout = new LinearLayout(view.getContext());
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setLayoutParams( new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            //detail_tick
            ImageView detail_tick = new ImageView(view.getContext());
            detail_tick.setImageResource(R.drawable.free_trial_tick);
            detail_tick.setLayoutParams( new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            );
            linearLayout.addView(detail_tick);
            //detail_content
            TextView detail_content = new TextView(view.getContext());
            detail_content.setText(txt_content[position][i]);
            detail_content.setTextColor(Color.WHITE);
            detail_content.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
            detail_content.setTypeface(newFont);
            detail_content.setGravity(Gravity.CENTER_HORIZONTAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(40,0,0,0);
            detail_content.setLayoutParams(params);
            linearLayout.addView(detail_content);
            detail_layout.addView(linearLayout);
        }

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}
