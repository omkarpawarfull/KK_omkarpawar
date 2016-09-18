package com.kismatkonnect.kismatkonnect;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by mou on 9/7/16.
 */
public class Navigation_Drawer_Adapter extends ArrayAdapter<String> {

    private Context context;
    private String[] title;
    private int[] icon;
    private LayoutInflater layoutInflater;

    public Navigation_Drawer_Adapter(Context _context, String[] _title, int[] _icon) {
        super(_context, R.layout.profile_choice_list_eachrow, _title);
        this.context = _context;
        this.icon = _icon;
        this.title = _title;
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public String getItem(int position) {
        return title[position];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class Holder{
        public TextView textView;
        public ImageView imageView;

        public Holder(View v){
            this.textView = (TextView) v.findViewById(R.id.navigation_drawer_eachrow_title);
            this.imageView = (ImageView) v.findViewById(R.id.navigation_drawer_eachrow_icon);
        }
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        Holder holder;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView==null) {
            convertView = layoutInflater.inflate(R.layout.navigation_drawer_eachrow, parent, false);
            holder = new Holder(convertView);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.textView.setText(title[position]);
        Typeface newFont = Typeface.createFromAsset(context.getAssets(), context.getString(R.string.custom_font));
        holder.textView.setTypeface(newFont);
        holder.imageView.setImageResource(icon[position]);
        return convertView;

    }




}
