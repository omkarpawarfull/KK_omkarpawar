package com.kismatkonnect.kismatkonnect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by mou on 8/30/16.
 */
public class Activity10_OnBoarding extends Activity{

    CallbackManager callbackManager;
    private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();
            if( accessToken.getUserId()!= null ){
                Log.v("acticity10", "Facebook Login Succeeds");
                Intent intent = new Intent(Activity10_OnBoarding.this, Activity11_OnBoarding_Animation.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
                overridePendingTransition(0,0);
                SingletonServer.VolleyRequestCallback regCallback = new SingletonServer.VolleyRequestCallback() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        try {
                            String result = response.getString("result");
                            if(result.equalsIgnoreCase("success")) {
                                GlobalData.getInstance().updateUserInfo(response);
                                GlobalData.getInstance().setNeedRegister(true);
                            } else {
                                Log.v("activity10", "Fetching profile fails.");
                            }
                        } catch (JSONException e) {
                            Log.v("activity10", "Fetching profile errs.");
                            e.printStackTrace();
                        }

                    }
                };
                SingletonServer.VolleyRequestCallback noRegCallback = new SingletonServer.VolleyRequestCallback() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        try {
                            String result = response.getString("result");
                            if(result.equalsIgnoreCase("success")) {
                                Log.v("activity10", response.toString());
                                GlobalData.getInstance().updateUserInfo(response);
                            } else {
                                Log.v("activity10", "Fetching profile fails.");
                            }
                        } catch (JSONException e) {
                            Log.v("activity10", "Fetching profile errs.");
                            e.printStackTrace();
                        }
//                        finish();
                    }
                };
                SingletonServer.getInstance(getApplicationContext()).registerIfNotAndFetchProfile(accessToken, regCallback, noRegCallback);
            }
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {

        }
    };

    private ViewPager viewPager;
    private Activity10_Viewpager_Adaper adaper;
    private ImageView[] dots;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity10_onboarding);

        Profile profile = Profile.getCurrentProfile();
        if( profile!= null ) {
            Log.v("activity10", "Facebook Logged in Already");
            SingletonServer.VolleyRequestCallback callback = new SingletonServer.VolleyRequestCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    try {
                        String result = response.getString("result");
                        if(result.equalsIgnoreCase("success")) {
                            Log.v("activity10", response.toString());
                            GlobalData.getInstance().updateUserInfo(response);
                        } else {
                            Log.v("activity10", "Fetching profile fails.");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            SingletonServer.getInstance(getApplicationContext()).registerIfNotAndFetchProfile(AccessToken.getCurrentAccessToken(),
                    new SingletonServer.VolleyRequestCallback() {
                        @Override
                        public void onSuccess(JSONObject response) {

                        }
                    },
                    callback);
            Intent intent = new Intent(Activity10_OnBoarding.this, Activity11_OnBoarding_Animation.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
            overridePendingTransition(0,0);


        }

        LoginButton loginButton = (LoginButton) findViewById(R.id.facebook_login_button);
        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_photos", "user_friends", "user_work_history"));
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, callback);
        loginButton.registerCallback(callbackManager,callback);
        viewPager = (ViewPager) findViewById(R.id.activity10_viewpager);
        adaper = new Activity10_Viewpager_Adaper(this);
        viewPager.setAdapter(adaper);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int dotsCount = adaper.getCount();
                for (int i = 0; i < dotsCount; i++) {
                    dots[i].setImageDrawable(getResources().getDrawable(R.drawable.viewpager_indicator_not_selected));
                }
                dots[position].setImageDrawable(getResources().getDrawable(R.drawable.viewpager_indicator_selected));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPagerIndicatorInit();
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }



    private void viewPagerIndicatorInit(){
        LinearLayout dotHolder = (LinearLayout) findViewById(R.id.activity10_viewpager_dots_holder);
        int dotsCount = adaper.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.viewpager_indicator_not_selected));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(4, 0, 4, 0);
            dotHolder.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.viewpager_indicator_selected));

    }


    ///////to prevent from using backbutton after logging out......omkarpawarfull
    //@Override
    //public void onBackPressed() {
    //}

}
