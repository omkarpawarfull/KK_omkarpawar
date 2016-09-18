package com.kismatkonnect.kismatkonnect;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import org.json.JSONException;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by mou on 9/7/16.
 */
public class Activity20_Dashboard extends AppCompatActivity {


    Typeface newFont;
    private FragmentTransaction fragmentTransaction;
    Activity20_Dashboard_Search_Fragment search_fragment;
    Activity20_Dashboard_Swipe_Fragment swipe_fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity20_dashboard);

        newFont = Typeface.createFromAsset(getAssets(), getString(R.string.custom_font));
        createToolbar(R.string.global_toolbar_title_activity20_dashboard, true, null);
        my_matches_init();
        tab_init();
        fragment_init();


    }







    private void my_matches_init(){
        int matchNum = 7;
        if( matchNum<1 ){
            return;
        }
        View my_mathces  = getLayoutInflater().inflate(R.layout.activity20_dashboard_existing_matches, null, false);
        TextView my_matches_title = (TextView) my_mathces.findViewById(R.id.activity20_dashboard_existing_matches_title);
        my_matches_title.setText(getString(R.string.dashboard_my_matches_title)+" (" + matchNum + ")");
        my_matches_title.setTypeface(newFont);
        LinearLayout hScroll = (LinearLayout) my_mathces.findViewById(R.id.activity20_dashboard_existing_matches_hscrollview);
        for(int i=0; i< matchNum; ++i){
            View eachMatch = getLayoutInflater().inflate(R.layout.activity20_dashboard_existing_matches_each, null, false);
            CircleImageView circleImageView = (CircleImageView) eachMatch.findViewById(R.id.activity20_dashboard_existing_matches_each_avatar);
            circleImageView.setImageResource(R.drawable.avatar_add_photo);
            TextView name = (TextView) eachMatch.findViewById(R.id.activity20_dashboard_existing_matches_each_name);
            name.setText("Jack");
            name.setTypeface(newFont);
            hScroll.addView(eachMatch);
        }
        LinearLayout activity20_main  = (LinearLayout) findViewById(R.id.activity20_dashboard_existing_matches_frame);
        activity20_main.addView(my_mathces);

    }

    private void tab_init(){
        final CheckBox search_btn = (CheckBox) findViewById(R.id.activity20_dashboard_tab_search);
        final CheckBox swipe_btn = (CheckBox) findViewById(R.id.activity20_dashboard_tab_swipe);
        search_btn.setChecked(true);
        search_btn.setBackgroundResource(R.drawable.chkbox_dashboard_tab_checked);
        search_btn.setTextColor(Color.WHITE);
        search_btn.setTypeface(newFont);
        search_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b && !swipe_btn.isChecked()){
                    search_btn.setChecked(true);
                    return;
                } else if ( b && swipe_btn.isChecked() ){
                    search_btn.setBackgroundResource(R.drawable.chkbox_dashboard_tab_checked);
                    search_btn.setTextColor(Color.WHITE);
                    swipe_btn.setChecked(false);
                    swipe_btn.setBackgroundResource(R.drawable.chkbox_dashboard_tab_unchecked);
                    swipe_btn.setTextColor(ContextCompat.getColor(getBaseContext(),R.color.Turquoise));
                    fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.activity20_dashboard_fragment_holder, search_fragment);
                    fragmentTransaction.commit();
                    LinearLayout match_layout = (LinearLayout) findViewById(R.id.activity20_dashboard_existing_matches_frame);
                    match_layout.setVisibility(View.VISIBLE);
                }
            }
        });
        swipe_btn.setTypeface(newFont);
        swipe_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b && !search_btn.isChecked()){
                    swipe_btn.setChecked(true);
                    return;
                } else if ( b && search_btn.isChecked() ){
                    swipe_btn.setBackgroundResource(R.drawable.chkbox_dashboard_tab_checked);
                    swipe_btn.setTextColor(Color.WHITE);
                    search_btn.setChecked(false);
                    search_btn.setBackgroundResource(R.drawable.chkbox_dashboard_tab_unchecked);
                    search_btn.setTextColor(ContextCompat.getColor(getBaseContext(),R.color.Turquoise));
                    fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.activity20_dashboard_fragment_holder, swipe_fragment);
                    fragmentTransaction.commit();
                    LinearLayout match_layout = (LinearLayout) findViewById(R.id.activity20_dashboard_existing_matches_frame);
                    match_layout.setVisibility(View.GONE);
                }
            }
        });
    }

    private void fragment_init(){
        search_fragment = new Activity20_Dashboard_Search_Fragment();
        swipe_fragment = new Activity20_Dashboard_Swipe_Fragment();
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.activity20_dashboard_fragment_holder, search_fragment);
        fragmentTransaction.commit();
    }






    /************** Sliding Menu Related **************/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.global_toolbar_menu_email:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.free_trial_invite_message_subject));
                intent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.free_trial_invite_message_body));
                startActivity(Intent.createChooser(intent, "Send Email"));
                break;
        }
        return true;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.global_toolbar_right_side_icon_primary, menu);

        return true;
    }


    /**
     * To create a toolbar with/without a sliding menu
     * @param res   The string resource id representing the tile of the toolbar
     * @param hasSlidingMenu    Do you need a sliding menu, true/false
     * @param listener  If there is a sliding menu, set it null; if there is one, set it a listener bound to the back button
     */
    private void createToolbar(int res, boolean hasSlidingMenu, View.OnClickListener listener)  {
        Toolbar toolbar = (Toolbar) findViewById(R.id.global_toolbar);
        toolbar.setTitle("");
        Typeface newFont = Typeface.createFromAsset(getAssets(), getString(R.string.custom_font));
        TextView toolbar_title = (TextView) findViewById(R.id.global_toolbar_title);
        toolbar_title.setText(res);
        toolbar_title.setTypeface(newFont);
        setSupportActionBar(toolbar);
        if( hasSlidingMenu ) {
            //Add hamburger icon to toolbar
            toolbar.setNavigationIcon(R.drawable.global_toolbar_menu);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.activity20_drawerlayout);
                    drawerLayout.openDrawer(Gravity.LEFT);
                }
            });
            //listview in sliding menu initialized
            String[] titles = getResources().getStringArray(R.array.navigation_menu_title);
            TypedArray ar = getResources().obtainTypedArray(R.array.navigation_menu_icons);
            int len = ar.length();
            int[] icons = new int[len];
            for (int i = 0; i < len; i++)
                icons[i] = ar.getResourceId(i, 0);
            ar.recycle();
            ListView listView = (ListView) findViewById(R.id.navigation_drawer_lower_list);
            ListAdapter adapter = new Navigation_Drawer_Adapter(this, titles, icons);
            listView.setAdapter(adapter);
            //load information and pic
            try {
                TextView welcome_name = (TextView) findViewById(R.id.navigation_drawer_upper_welcome);
                welcome_name.setText("Hi, " + GlobalData.getInstance().getUserInfo().getString("first_name"));
                ImageLoader imageLoader = SingletonVolley.getInstance(this).getImageLoader();
                imageLoader.get(GlobalData.getInstance().getCoverPhotoUrl(), new ImageLoader.ImageListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("activity20_sliding_menu", "Cover photo Loading Error: " + error.getMessage());
                    }

                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                        if (response.getBitmap() != null) {
                            ImageView imageView = (ImageView) findViewById(R.id.navigation_drawer_upper_background);
                            imageView.setImageBitmap(response.getBitmap());
                        }
                    }
                });
                imageLoader.get(GlobalData.getInstance().getUserInfo().getJSONArray("pics").getString(0), new ImageLoader.ImageListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("activity20_sliding_menu", "Avatar Loading Error: " + error.getMessage());
                    }

                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                        if (response.getBitmap() != null) {
                            ImageView imageView = (ImageView) findViewById(R.id.navigation_drawer_avatar);
                            imageView.setImageBitmap(response.getBitmap());
                        }
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            //Add back icon to toolbar
            toolbar.setNavigationIcon(R.drawable.arrow_back_white);
            toolbar.setNavigationOnClickListener(listener);
        }
    }



}
