/**************************************************************************
 *
 * Activity to show the LCD of the MK
 *
 * Author:  Marcus -LiGi- Bueschleb   
 *
 * Project URL:
 *  http://mikrokopter.de/ucwiki/en/DUBwise
 *
 * License:
 *  http://creativecommons.org/licenses/by-nc-sa/2.0/de/ 
 *  (Creative Commons / Non Commercial / Share Alike)
 *  Additionally to the Creative Commons terms it is not allowed
 *  to use this project in _any_ violent manner! 
 *  This explicitly includes that lethal Weapon owning "People" and 
 *  Organisations (e.g. Army & Police) 
 *  are not allowed to use this Project!
 *
 **************************************************************************/

package org.ligi.android.dubwise_mk.lcd;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import org.ligi.android.dubwise_mk.BaseActivity;
import org.ligi.android.dubwise_mk.app.App;
import org.ligi.android.dubwise_mk.app.App;
import org.ligi.android.dubwise_mk.conn.SwitchDeviceListActivity;
import org.ligi.tracedroid.logging.Log;
import org.ligi.ufo.MKCommunicator;

public class LCDActivity extends BaseActivity implements OnTouchListener {

    private LCDView lcd_view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        App.getMK().user_intent = MKCommunicator.USER_INTENT_LCD;
        lcd_view = new LCDView(this);
        lcd_view.setOnTouchListener(this);
        setContentView(lcd_view);
    }

    public final static int MENU_PREV = 0;
    public final static int MENU_NEXT = 1;
    private static final int MENU_SWITCH = 2;
    private static final int MENU_GOTO = 3;


    /* Creates the menu items */
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuItem prev_menu = menu.add(0, MENU_PREV, 0, "Previous");
        prev_menu.setIcon(android.R.drawable.btn_minus);

        MenuItem switch_menu = menu.add(0, MENU_SWITCH, 0, "Switch Device");
        switch_menu.setIcon(android.R.drawable.ic_menu_edit);

        MenuItem goto_menu = menu.add(0, MENU_GOTO, 0, "Goto Page");
        goto_menu.setIcon(android.R.drawable.ic_menu_mylocation);


        MenuItem next_menu = menu.add(0, MENU_NEXT, 0, "NEXT");
        next_menu.setIcon(android.R.drawable.btn_plus);

        return true;
    }

    /* Handles item selections */
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case MENU_NEXT:
                App.getMK().LCD.LCD_NEXTPAGE();
                return true;
            case MENU_PREV:
                App.getMK().LCD.LCD_PREVPAGE();
                return true;
            case MENU_SWITCH:
                startActivity(new Intent(this, SwitchDeviceListActivity.class));
                return true;

            case MENU_GOTO:

                LinearLayout lin = new LinearLayout(this);


                final SeekBar page_seeker = new SeekBar(this);
                page_seeker.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
                page_seeker.setMax(App.getMK().LCD.getPageCount());
                final TextView page_txt = new TextView(this);
                page_txt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20.0f);
                page_txt.setPadding(5, 0, 5, 0);
                page_txt.setText("" + 1);
                lin.addView(page_txt);
                lin.addView(page_seeker);
                class PageSeekListener implements OnSeekBarChangeListener {
                    @Override
                    public void onProgressChanged(SeekBar arg0, int arg1,
                                                  boolean arg2) {
                        page_txt.setText("" + (arg1 + 1));

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar arg0) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar arg0) {
                    }

                }
                page_seeker.setOnSeekBarChangeListener(new PageSeekListener());
                new AlertDialog.Builder(this).setTitle("Jump").setMessage("Jump to which Page?").setView(lin)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //String value = input.getText().toString();
                                App.getMK().LCD.set_page(page_seeker.getProgress() + 1);


                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Do nothing.
                    }
                }).show();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //@Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.i("LCD Touch");
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (event.getX() > lcd_view.getWidth() / 2) {
                Log.i("LCD Nextpage");
                App.getMK().LCD.LCD_NEXTPAGE();
            } else {
                App.getMK().LCD.LCD_PREVPAGE();
            }
            lcd_view.invalidate();
        }
        return true;
    }
}