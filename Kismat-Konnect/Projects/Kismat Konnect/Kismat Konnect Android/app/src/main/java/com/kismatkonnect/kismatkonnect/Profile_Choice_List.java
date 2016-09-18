package com.kismatkonnect.kismatkonnect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by mou on 8/31/16.
 */
public class Profile_Choice_List extends Activity {


    private ListView listView;
    private int curr_selected_pos = -1;
    private String selected = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_choice_list);
        Typeface newFont = Typeface.createFromAsset(getAssets(), getString(R.string.custom_font));
        String clickFrom = getIntent().getExtras().getString("from");
        TextView listview_title = (TextView) findViewById(R.id.activity13_question3_choice_list_title);
        listview_title.setText(clickFrom);
        listview_title.setTypeface(newFont);

        ImageButton arrowBack = (ImageButton) findViewById(R.id.activity13_question3_choice_list_btnBack);
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                if(selected==null) {
                    setResult(Activity.RESULT_CANCELED, returnIntent);
                } else {
                    returnIntent.putExtra("res", selected);
                    setResult(Activity.RESULT_OK, returnIntent);
                }
                finish();
            }
        });
        String[] content = null;
        switch (clickFrom){
            case "Education":
                content = getResources().getStringArray(R.array.education_list);
                break;
            case "Religion":
                content = getResources().getStringArray(R.array.religion_list);
                break;
            case "Community":
                content = getResources().getStringArray(R.array.community_list);
                break;
        }
        listView = (ListView) findViewById(R.id.activity13_question3_choice_list_listview);
        final ListAdapter adapter = new Activity13_OnBoarding_Question3_Listview_Adapter(this, content);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                if (curr_selected_pos!=-1 && curr_selected_pos != pos) {
                    CheckBox pre_chkbox = (CheckBox) adapterView.getChildAt(curr_selected_pos).findViewById(R.id.activity13_question3_choice_list_listview_chkbox);
                    pre_chkbox.setChecked(false);
                    Log.v("KK", curr_selected_pos + " cancled");
                } else if (curr_selected_pos==pos){
                    CheckBox cur_chkbox = (CheckBox) view.findViewById(R.id.activity13_question3_choice_list_listview_chkbox);
                    cur_chkbox.setChecked(false);
                    curr_selected_pos = -1;
                    Log.v("KK", pos + " is cancled. Current is " + curr_selected_pos);
                    return;
                }
                CheckBox cur_chkbox = (CheckBox) view.findViewById(R.id.activity13_question3_choice_list_listview_chkbox);
                cur_chkbox.setChecked(true);
                curr_selected_pos = pos;
                TextView cur_txtview = (TextView) view.findViewById(R.id.activity13_question3_choice_list_listview_text);
                selected = cur_txtview.getText().toString();
                Log.v("KK", selected + "--" + curr_selected_pos);

            }

        });

    }






    public class Activity13_OnBoarding_Question3_Listview_Adapter extends ArrayAdapter<String> {


        private Context context;
        private String[] content;
        private LayoutInflater layoutInflater;

        public Activity13_OnBoarding_Question3_Listview_Adapter(Context _context, String[] _content) {
            super(_context, R.layout.profile_choice_list_eachrow, _content);
            this.context = _context;
            this.content = _content;
        }

        @Override
        public int getCount() {
            return content.length;
        }

        @Override
        public String getItem(int position) {
            return content[position];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        public class Holder{
            public TextView textView;
            public CheckBox checkBox;

            public Holder(View v){
                this.textView = (TextView) v.findViewById(R.id.activity13_question3_choice_list_listview_text);
                this.checkBox = (CheckBox) v.findViewById(R.id.activity13_question3_choice_list_listview_chkbox);
            }
        }

        @Override
        public View getView(int position, View convertView, final ViewGroup parent) {
            Holder holder;
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView==null) {
                convertView = layoutInflater.inflate(R.layout.profile_choice_list_eachrow, parent, false);
                holder = new Holder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            holder.textView.setText(content[position]);
            Typeface newFont = Typeface.createFromAsset(context.getAssets(), context.getString(R.string.custom_font));
            holder.textView.setTypeface(newFont);
            holder.checkBox.setTag(position);
            holder.checkBox.setOnClickListener(onStateChangedListener(holder.checkBox));
            if(position==curr_selected_pos){
                holder.checkBox.setChecked(true);
            } else {
                holder.checkBox.setChecked(false);
            }
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    notifyDataSetChanged();
                }
            });
            return convertView;

        }


        private View.OnClickListener onStateChangedListener(final CheckBox checkBox){
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(checkBox.isChecked()){
                        curr_selected_pos = Integer.valueOf(checkBox.getTag().toString());
                        selected = content[curr_selected_pos];
                        Log.v("KK", selected);
                    } else {
                        curr_selected_pos = -1;
                        selected = null;
                        Log.v("KK", "nothing chosen");
                    }
                    notifyDataSetChanged();
                }
            };
        }




    }






}
