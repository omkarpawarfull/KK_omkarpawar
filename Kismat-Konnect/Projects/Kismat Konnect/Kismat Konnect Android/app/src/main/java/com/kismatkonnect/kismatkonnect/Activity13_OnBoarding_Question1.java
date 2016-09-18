package com.kismatkonnect.kismatkonnect;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

/**
 * Created by mou on 8/30/16.
 */
public class Activity13_OnBoarding_Question1 extends Activity {

    private CheckBox male;
    private CheckBox female;
    private ImageButton imgBtn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity13_onboarding_question1);
        loadAvatar(0);
        Typeface newFont = Typeface.createFromAsset(getAssets(), getString(R.string.custom_font));
        TextView txtView_title = (TextView) findViewById(R.id.activity13_question1_txtview_title);
        txtView_title.setTypeface(newFont);
        TextView txtView_gender_choice = (TextView) findViewById(R.id.activity13_question1_txtview_gender_interested);
        txtView_gender_choice.setTypeface(newFont);
        imgBtn_next = (ImageButton) findViewById(R.id.activity13_question1_imgbtn_next);
        imgBtn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (male.isChecked()) {
                        GlobalData.getInstance().getUserInfo().put("interested_in", "male");
                    } else {
                        GlobalData.getInstance().getUserInfo().put("interested_in", "female");
                    }
                    Intent intent = new Intent(Activity13_OnBoarding_Question1.this, Activity13_OnBoarding_Question2.class);
                    startActivity(intent);
                    finish();
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
        male = (CheckBox) findViewById(R.id.activity13_question1_checkbox_male);
        male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if( b && female.isChecked()){
                    female.setChecked(false);
                }
                if( !b && !female.isChecked()){
                    male.setChecked(true);
                }
            }
        });
        female = (CheckBox) findViewById(R.id.activity13_question1_checkbox_female);
        female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if( b && male.isChecked()){
                    male.setChecked(false);
                }
                if( !b && !male.isChecked()){
                    female.setChecked(true);
                }
            }
        });
        //default gender of interest
        try {
            if (GlobalData.getInstance().getUserInfo().getString("interested_in").equalsIgnoreCase("male")) {
                male.setChecked(true);
            } else {
                female.setChecked(true);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }


    }


    private void loadAvatar(int i){
        ImageLoader imageLoader = SingletonVolley.getInstance(this).getImageLoader();
        try {
            imageLoader.get(GlobalData.getInstance().getUserInfo().getJSONArray("pics").getString(i), new ImageLoader.ImageListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.v("activity13_Q1", "Avatar Load Error: " + error.getMessage());
                }

                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                    if (response.getBitmap() != null) {
                        ImageView imageView = (ImageView) findViewById(R.id.activity13_question1_avatar_pic);
                        imageView.setImageBitmap(response.getBitmap());
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
