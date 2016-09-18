package com.kismatkonnect.kismatkonnect;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mou on 8/30/16.
 */
public class Activity13_OnBoarding_Question2 extends Activity {


    private CheckBox new_friend;
    private CheckBox casual_dating;
    private CheckBox soul_mate;
    private ImageButton imgBtn_back;
    private ImageButton imgBtn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity13_onboarding_question2);
        loadAvatar(1);
        Typeface newFont = Typeface.createFromAsset(getAssets(), getString(R.string.custom_font));
        TextView txtView_title = (TextView) findViewById(R.id.activity13_question2_txtview_title);
        txtView_title.setTypeface(newFont);
        TextView txtView_look_for = (TextView) findViewById(R.id.activity13_question2_txtview_look_for);
        txtView_look_for.setTypeface(newFont);
        TextView txtView_look_for_newFriend = (TextView) findViewById(R.id.activity13_question2_txtview_look_for_new_friend);
        txtView_look_for_newFriend.setTypeface(newFont);
        txtView_look_for_newFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new_friend.setChecked(new_friend.isChecked()?false:true);
            }
        });
        TextView txtView_look_for_casual = (TextView) findViewById(R.id.activity13_question2_txtview_look_for_casual_date);
        txtView_look_for_casual.setTypeface(newFont);
        txtView_look_for_casual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                casual_dating.setChecked(casual_dating.isChecked()?false:true);
            }
        });
        TextView txtView_look_for_soulMate = (TextView) findViewById(R.id.activity13_question2_txtview_look_for_soul_mate);
        txtView_look_for_soulMate.setTypeface(newFont);
        txtView_look_for_soulMate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soul_mate.setChecked(soul_mate.isChecked()?false:true);
            }
        });
        imgBtn_back = (ImageButton) findViewById(R.id.activity13_quetions2_imgbtn_back);
        imgBtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity13_OnBoarding_Question2.this, Activity13_OnBoarding_Question1.class);
                startActivity(intent);
                finish();
            }
        });
        imgBtn_next = (ImageButton) findViewById(R.id.activity13_quetions2_imgbtn_next);
        imgBtn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!new_friend.isChecked() &&
                        !casual_dating.isChecked() &&
                        !soul_mate.isChecked()) {
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.looking_for_choose_one), Toast.LENGTH_LONG).show();
                } else {
                    try {
                        if( new_friend.isChecked() ){
                            GlobalData.getInstance().getSearchPreference().put("friendship", true);
                        } else {
                            GlobalData.getInstance().getSearchPreference().put("friendship", false);
                        };
                        if( casual_dating.isChecked() ){
                            GlobalData.getInstance().getSearchPreference().put("casual", true);
                        } else {
                            GlobalData.getInstance().getSearchPreference().put("casual", false);
                        }
                        if( soul_mate.isChecked() ){
                            GlobalData.getInstance().getSearchPreference().put("soulmates", true);
                        } else {
                            GlobalData.getInstance().getSearchPreference().put("soulmates", false);
                        }
                    } catch (JSONException e){
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(Activity13_OnBoarding_Question2.this, Activity13_OnBoarding_Question3.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        new_friend = (CheckBox) findViewById(R.id.activity13_question2_checkbox_new_friend);
        casual_dating = (CheckBox) findViewById(R.id.activity13_question2_checkbox_casual_date);
        soul_mate = (CheckBox) findViewById(R.id.activity13_question2_checkbox_soul_mate);

    }

    private void loadAvatar(int i){
        ImageLoader imageLoader = SingletonVolley.getInstance(this).getImageLoader();
        try {
            imageLoader.get(GlobalData.getInstance().getUserInfo().getJSONArray("pics").getString(i), new ImageLoader.ImageListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.v("activity13_Q2", "Avatar Load Error: " + error.getMessage());
                }

                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                    if (response.getBitmap() != null) {
                        ImageView imageView = (ImageView) findViewById(R.id.activity13_question2_avatar_pic);
                        imageView.setImageBitmap(response.getBitmap());
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}



