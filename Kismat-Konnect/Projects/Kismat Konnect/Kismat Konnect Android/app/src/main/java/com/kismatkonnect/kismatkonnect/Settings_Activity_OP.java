package com.kismatkonnect.kismatkonnect;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import static com.kismatkonnect.kismatkonnect.R.layout.setting_activity_op;

/**
 * Created by omkar pawar on 9/13/2016.
 */

public class Settings_Activity_OP extends AppCompatActivity{

    private Toolbar toolbar;
    private boolean daily_alert=false;
    private boolean weekly_report=false;
    private boolean instagram=false;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(setting_activity_op);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.settings_daily_notification:

                if(daily_alert==false) {
                    daily_alert = true;
                    RadioButton rb = (RadioButton) findViewById(view.getId());
                    rb.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#00beda")));
                    rb.setTextColor(Color.parseColor("#00beda"));
                    break;
                }
                else{
                    daily_alert = false;
                    RadioButton rb = (RadioButton) findViewById(view.getId());
                    rb.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#e1e2ec")));
                    rb.setTextColor(Color.parseColor("#e1e2ec"));
                    break;
                }
            case R.id.settings_weekly_reports_notification:
                if(weekly_report==false) {
                    weekly_report = true;
                    RadioButton rb = (RadioButton) findViewById(view.getId());
                    rb.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#00beda")));
                    rb.setTextColor(Color.parseColor("#00beda"));
                    break;
                }
                else{
                    weekly_report = false;
                    RadioButton rb = (RadioButton) findViewById(view.getId());
                    rb.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#e1e2ec")));
                    rb.setTextColor(Color.parseColor("#e1e2ec"));
                    break;
                }
            case R.id.settings_instagram_public:
                if(instagram==false) {
                    instagram = true;
                    RadioButton rb = (RadioButton) findViewById(view.getId());
                    rb.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#00beda")));
                    rb.setTextColor(Color.parseColor("#00beda"));
                    break;
                }
                else{
                    instagram = false;
                    RadioButton rb = (RadioButton) findViewById(view.getId());
                    rb.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#e1e2ec")));
                    rb.setTextColor(Color.parseColor("#e1e2ec"));
                    break;
                }
            case R.id.settings_terms_conditions:
                //what to do on pressing button term_condition
                break;
            case R.id.settings_privacy_policy:
                //what to do on pressing button privacy_policy
                break;
            case R.id.settings_help_support:
                //what to do on pressing button help_support
                break;
            case R.id.settings_log_out:
                Intent intent = new Intent(this,Activity10_OnBoarding.class);
                break;
            case R.id.settings_save:
                ///call to the database to save the changes made by the user

                //////url to the php file that does the update of the user database
                //////remove comments of // to run the code
                //String URL = "http://omega.uta.edu/~oap8293/GetStudentAppointment.php";
                //StringRequest stringReq = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    //@Override
                    //public void onResponse(String response) {
                    //}
                //}, new Response.ErrorListener() {
                   // @Override
                    //public void onErrorResponse(VolleyError error) {
                    //}
                //})
                //{
                   // @Override
                    //protected Map<String,String> getParams() throws AuthFailureError {
                      //  Map<String,String> parameters =new HashMap<String, String>();
                        //parameters.put("username",net_id);
                            ///////inplace of username, we will have the column name(eg: user_id) in the database and net_id will be the value of user id.
                        //return parameters;
                    //}
                //};
                //AppController.getmInstance().addToRequestQueue(stringReq);

                ///end of call to the database
                break;
            case R.id.settings_disable_account:
                ///what to do when disable activity
                break;
            case R.id.imageButtonLeft:
                System.out.println("left button");
                break;
            case R.id.imageButtonRight:
                System.out.println("right button");
                break;
            default:Toast.makeText(getApplicationContext(), "entered default", Toast.LENGTH_LONG).show();
                System.out.println("case default");
                break;

        }
    }
}
