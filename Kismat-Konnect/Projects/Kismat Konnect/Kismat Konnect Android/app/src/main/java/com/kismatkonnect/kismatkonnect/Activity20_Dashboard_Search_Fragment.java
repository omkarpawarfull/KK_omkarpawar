package com.kismatkonnect.kismatkonnect;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * Created by mou on 9/9/16.
 */
public class Activity20_Dashboard_Search_Fragment extends Fragment {

    Typeface newFont;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity20_dashboard_search_fragment, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        newFont = Typeface.createFromAsset(getActivity().getAssets(), getString(R.string.custom_font));

        LinearLayout scroll  = (LinearLayout) getActivity().findViewById(R.id.activity20_dashboard_search_fragment);
        int totalNum = 9;
        int rowNum = totalNum/2;
        int rem = totalNum - 2 * rowNum;
        for(int i=0; i<rowNum; ++i){
            LinearLayout eachRow = new LinearLayout(getActivity());
            eachRow.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0,0,0,25);
            eachRow.setLayoutParams(params);
            for(int j=0; j<2; ++j) {
                View candi  = getActivity().getLayoutInflater().inflate(R.layout.activity20_dashboard_search_fragment_eachcandidate, null, false);
                LinearLayout.LayoutParams param_for_candi = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                param_for_candi.weight = (float)0.5;
                if(j==0){
                    param_for_candi.setMargins(0,0, 10,0);
                } else {
                    param_for_candi.setMargins(10,0, 0,0);
                }
                candi.setLayoutParams( param_for_candi);
                ImageView pic = (ImageView) candi.findViewById(R.id.activity20_dashboard_search_fragment_eachcandidate_pic);
                pic.setImageResource(R.drawable.avatar_add_photo);
                TextView name = (TextView) candi.findViewById(R.id.activity20_dashboard_search_fragment_eachcandidate_name);
                name.setText("John");
                name.setTypeface(newFont);
                TextView location = (TextView) candi.findViewById(R.id.activity20_dashboard_search_fragment_eachcandidate_loction);
                location.setText("26 mi. Tonw. ST.");
                location.setTypeface(newFont);
                TextView percent = (TextView) candi.findViewById(R.id.activity20_dashboard_search_fragment_eachcandidate_percent);
                percent.setText("80%");
                percent.setTypeface(newFont);
                TextView match = (TextView) candi.findViewById(R.id.activity20_dashboard_search_fragment_eachcandidate_match_sign);
                match.setTypeface(newFont);
                ToggleButton like = (ToggleButton) candi.findViewById(R.id.activity20_dashboard_search_fragment_eachcandidate_like);
                like.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    }
                });
                ImageButton dislike = (ImageButton) candi.findViewById(R.id.activity20_dashboard_search_fragment_eachcandidate_dislike);
                dislike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                eachRow.addView(candi);
            }
            scroll.addView(eachRow);
        }
    }


}
