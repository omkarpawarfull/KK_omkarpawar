package com.kismatkonnect.kismatkonnect;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by mou on 9/2/16.
 */
public class Profile_Height_Selection extends Activity {

    TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selector_height_selection);
        Typeface newFont = Typeface.createFromAsset(getAssets(), getString(R.string.custom_font));
        TextView title = (TextView) findViewById(R.id.selector_height_selection_title);
        display = (TextView) findViewById(R.id.selector_height_selection_display);
        title.setTypeface(newFont);
        display.setTypeface(newFont);

        ImageButton arrowBack = (ImageButton) findViewById(R.id.selector_height_selection_btnBack);
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String res = display.getText().toString();
                Intent returnIntent = new Intent();
                if(res==null) {
                    setResult(Activity.RESULT_CANCELED, returnIntent);
                } else {
                    returnIntent.putExtra("res", res);
                    setResult(Activity.RESULT_OK, returnIntent);
                }
                finish();
            }
        });

        SeekBar seekBar = (SeekBar) findViewById(R.id.selector_height_selection_seekbar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int num = seekBar.getProgress();
                String height = String.valueOf(num/12+4) + "\' " + String.valueOf(num%12);
                display.setText(height);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

}
