package com.kismatkonnect.kismatkonnect;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by mou on 9/5/16.
 */
public class Activity15_OnBoarding_Free_Trial extends Activity {

    private ViewPager viewPager;
    private Activity15_Viewpager_Adaper adaper;
    private ImageView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity15_onboarding_freetrial);

        viewPager = (ViewPager) findViewById(R.id.activity15_viewpager);
        adaper = new Activity15_Viewpager_Adaper(this);
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

        TextView close = (TextView) findViewById(R.id.activity15_txtview_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Button via_email = (Button) findViewById(R.id.activity15_btn_via_email);
        via_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.free_trial_invite_message_subject));
                intent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.free_trial_invite_message_body));
                startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });
        Button via_txtmsg = (Button) findViewById(R.id.activity15_btn_via_txtmsg);
        via_txtmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"));
                sendIntent.putExtra("sms_body", getResources().getString(R.string.free_trial_invite_message_body));
                startActivity(sendIntent);
            }
        });



    }



    private void viewPagerIndicatorInit(){
        LinearLayout dotHolder = (LinearLayout) findViewById(R.id.activity15_viewpager_dots_holder);
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


}
