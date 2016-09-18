package com.kismatkonnect.kismatkonnect;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.daprlabs.cardstack.SwipeDeck;

import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mou on 9/9/16.
 */
public class Activity20_Dashboard_Swipe_Fragment extends Fragment {

    Typeface newFont;
    SwipeDeck cardStack;
    ArrayList<String> candiList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity20_dashboard_swipe_fragment, container, false);
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        newFont = Typeface.createFromAsset(getActivity().getAssets(), getString(R.string.custom_font));

        candiList = new ArrayList<>();
        candiList.add("0");
        candiList.add("1");
        candiList.add("2");
        candiList.add("3");
        candiList.add("4");
        cardStack = (SwipeDeck) getActivity().findViewById(R.id.activity20_dashboard_swipe_deck);
        cardStack.setLeftImage(R.id.activity20_dashboard_swipe_fragment_right_sign);
        cardStack.setRightImage(R.id.activity20_dashboard_swipe_fragment_left_sign);
        final SwipeDeckAdapter adapter = new SwipeDeckAdapter(candiList, getActivity());
        cardStack.setAdapter(adapter);

        cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {
                candiListUpdate();
                Log.i("MainActivity", "card was swiped left, position in adapter: " + position);
            }

            @Override
            public void cardSwipedRight(int position) {
                candiListUpdate();
                Log.i("MainActivity", "card was swiped right, position in adapter: " + position);
            }

            @Override
            public void cardsDepleted() {
                Log.i("MainActivity", "no more cards");
            }

            @Override
            public void cardActionDown() {

            }

            @Override
            public void cardActionUp() {

            }
        });
    }


    private void candiListUpdate(){
        candiList.add("haha");
    }






    public class SwipeDeckAdapter extends BaseAdapter {

        private List<String> data;
        private Context context;

        public SwipeDeckAdapter(List<String> data, Context context) {
            this.data = data;
            this.context = context;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;
            if(v == null){
                LayoutInflater inflater =  getActivity().getLayoutInflater();
                // normally use a viewholder
                v = inflater.inflate(R.layout.activity20_dashboard_swipe_fragment_eachcard, parent, false);
            }
            Button btn_pass = (Button) v.findViewById(R.id.activity20_dashboard_swipe_fragment_pass);
            btn_pass.setTypeface(newFont);
            btn_pass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cardStack.swipeTopCardLeft(180);
                }
            });
            Button btn_like = (Button) v.findViewById(R.id.activity20_dashboard_swipe_fragment_like);
            btn_like.setTypeface(newFont);
            btn_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cardStack.swipeTopCardRight(180);
                }
            });
            TextView name = (TextView) v.findViewById(R.id.activity20_dashboard_swipe_fragment_name);
            name.setTypeface(newFont);
            TextView age_loc = (TextView) v.findViewById(R.id.activity20_dashboard_swipe_fragment_age_and_location);
            age_loc.setTypeface(newFont);
            TextView distance = (TextView) v.findViewById(R.id.activity20_dashboard_swipe_fragment_distance);
            distance.setTypeface(newFont);
            TextView miles_away = (TextView) v.findViewById(R.id.activity20_dashboard_swipe_fragment_miles_away);
            miles_away.setTypeface(newFont);

            return v;
        }
    }





}




