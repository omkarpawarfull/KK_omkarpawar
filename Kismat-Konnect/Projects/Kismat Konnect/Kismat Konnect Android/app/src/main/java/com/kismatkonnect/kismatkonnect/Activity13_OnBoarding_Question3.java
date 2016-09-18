package com.kismatkonnect.kismatkonnect;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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

import java.util.Calendar;

/**
 * Created by mou on 8/31/16.
 */
public class Activity13_OnBoarding_Question3 extends Activity {

    TextView txtview_location;
    TextView txtview_age;
    TextView txtview_work;
    TextView txtview_education;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity13_onboarding_question3);
        loadAvatar(2);
        txtview_location = (TextView) findViewById(R.id.activity13_question3_txtview_location_content);
        txtview_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), getResources().getString(R.string.profile_edit_warning), Toast.LENGTH_LONG).show();
            }
        });
        txtview_age = (TextView) findViewById(R.id.activity13_question3_txtview_age_content);
        txtview_age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), getResources().getString(R.string.profile_edit_warning), Toast.LENGTH_LONG).show();
            }
        });
        txtview_work = (TextView) findViewById(R.id.activity13_question3_txtview_career_content);
        txtview_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), getResources().getString(R.string.profile_edit_warning), Toast.LENGTH_LONG).show();
            }
        });
        txtview_education = (TextView) findViewById(R.id.activity13_question3_txtview_education_content);
        txtview_education.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), getResources().getString(R.string.profile_edit_warning), Toast.LENGTH_LONG).show();
            }
        });
        try {
            txtview_location.setText(GlobalData.getInstance().getUserInfo().getString("location"));
            String cur_career = GlobalData.getInstance().getUserInfo().getString("career");
            if( cur_career.equalsIgnoreCase("null") ){
                txtview_work.setText("");
            } else {
                txtview_work.setText(cur_career);
            }
            txtview_education.setText(GlobalData.getInstance().getUserInfo().getString("education"));
            String birthday = GlobalData.getInstance().getUserInfo().getString("birthday");
            txtview_age.setText( String.valueOf(getAge(birthday)) );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        customizeFont();
        ImageButton heightBtn = (ImageButton) findViewById(R.id.activity13_question3_btn_height);
        heightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity13_OnBoarding_Question3.this, Profile_Height_Selection.class);
                startActivityForResult(intent,1);
            }
        });
        ImageButton religionBtn = (ImageButton) findViewById(R.id.activity13_question3_btn_religion);
        religionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity13_OnBoarding_Question3.this, Profile_Choice_List.class);
                intent.putExtra("from", "Religion");
                startActivityForResult(intent,2);
            }
        });
        ImageButton communityBtn = (ImageButton) findViewById(R.id.activity13_question3_btn_community);
        communityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity13_OnBoarding_Question3.this, Profile_Choice_List.class);
                intent.putExtra("from", "Community");
                startActivityForResult(intent,3);
            }
        });
        Button confirmBtn = (Button) findViewById(R.id.activity13_question3_btn_confirm);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView txt_height = (TextView) findViewById(R.id.activity13_question3_txtview_height_content);
                if( txt_height.getText().toString().equalsIgnoreCase(getResources().getString(R.string.default_profile_height)) ){
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.profile_confirm_warning_height), Toast.LENGTH_LONG).show();
                    return;
                }
                TextView txt_religion = (TextView) findViewById(R.id.activity13_question3_txtview_religion_content);
                if( txt_religion.getText().toString().isEmpty() ){
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.profile_confirm_warning_religion), Toast.LENGTH_LONG).show();
                    return;
                }
                TextView txt_community = (TextView) findViewById(R.id.activity13_question3_txtview_community_content);
                if( txt_community.getText().toString().isEmpty() ){
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.profile_confirm_warning_community), Toast.LENGTH_LONG).show();
                    return;
                }
                try {
                    GlobalData.getInstance().getUserInfo().put("height", txt_height.getText().toString().toLowerCase());
                    GlobalData.getInstance().getUserInfo().put("religion", txt_religion.getText().toString().toLowerCase());
                    GlobalData.getInstance().getUserInfo().put("community", txt_community.getText().toString().toLowerCase());
                    SingletonServer.getInstance(getApplicationContext()).confirmProfile(GlobalData.getInstance().getUserInfo().getString("facebook_id"));
                } catch (JSONException e){
                    e.printStackTrace();
                }
                Intent intent = new Intent(Activity13_OnBoarding_Question3.this, Activity15_OnBoarding_Free_Trial.class);
                startActivity(intent);
                finish();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==Activity.RESULT_OK) {
            String res = data.getExtras().getString("res");
            TextView txtView;
            switch (requestCode) {
                case 1:
                    txtView = (TextView) findViewById(R.id.activity13_question3_txtview_height_content);
                    txtView.setText(res);
                    break;
                case 2:
                    txtView = (TextView) findViewById(R.id.activity13_question3_txtview_religion_content);
                    txtView.setText(res);
                    break;
                case 3:
                    txtView = (TextView) findViewById(R.id.activity13_question3_txtview_community_content);
                    txtView.setText(res);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Apply new font
     */
    private void customizeFont(){
        Typeface newFont = Typeface.createFromAsset(getAssets(), getString(R.string.custom_font));
        ScrollView  scrollView = (ScrollView) findViewById(R.id.activity13_question3_scrollview);
        LinearLayout linearLayout = (LinearLayout) scrollView.getChildAt(0);
        int rowNum = linearLayout.getChildCount();
        for( int i =0; i< rowNum-1; ++i){
            RelativeLayout eachRow =  (RelativeLayout) linearLayout.getChildAt(i);
            TextView txtView1 = (TextView) eachRow.getChildAt(0);
            txtView1.setTypeface(newFont);
            TextView txtView2 = (TextView) eachRow.getChildAt(1);
            txtView2.setTypeface(newFont);
        }
        Button btn = (Button) linearLayout.getChildAt(rowNum-1);
        btn.setTypeface(newFont);
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
                        ImageView imageView = (ImageView) findViewById(R.id.activity13_question3_avatar_pic);
                        imageView.setImageBitmap(response.getBitmap());
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //yyyy-mm-dd
    private int getAge(String birthday) {
        int this_year = Calendar.getInstance().get(Calendar.YEAR);
        int birth_year = Integer.valueOf(birthday.substring(0,4));
        return (this_year-birth_year);
    }


}