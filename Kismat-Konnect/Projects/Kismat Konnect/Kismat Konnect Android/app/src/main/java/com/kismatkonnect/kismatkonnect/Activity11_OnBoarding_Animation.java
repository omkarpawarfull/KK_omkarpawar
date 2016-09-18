package com.kismatkonnect.kismatkonnect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by mou on 9/5/16.
 */
public class Activity11_OnBoarding_Animation extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity11_onboarding_animation);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                finish();
                Intent intent;
                if( GlobalData.getInstance().needRegister() ) {
                    intent = new Intent(Activity11_OnBoarding_Animation.this, Activity13_OnBoarding_Question1.class);
                } else {
                    intent = new Intent(Activity11_OnBoarding_Animation.this, Activity20_Dashboard.class);
                }
                startActivity(intent);
            }
        }, 2000);

    }

}
