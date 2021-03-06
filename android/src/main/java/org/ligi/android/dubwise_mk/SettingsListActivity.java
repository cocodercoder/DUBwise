/**************************************************************************
 *
 * Main Menu ( & startup ) Activity for DUBwise 
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

package org.ligi.android.dubwise_mk;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;

import org.ligi.android.dubwise_mk.blackbox.BlackBoxPrefsActivity;
import org.ligi.android.dubwise_mk.graph.GraphSettingsActivity;
import org.ligi.android.dubwise_mk.helper.IconicAdapter;
import org.ligi.android.dubwise_mk.helper.IconicMenuItem;
import org.ligi.android.dubwise_mk.piloting.PilotingPrefsActivity;
import org.ligi.android.dubwise_mk.voice.VoicePrefsActivity;

import java.util.Vector;

public class SettingsListActivity extends BaseListActivity {

    public final static int ACTIONID_QUIT = 1;

    public void refreshSettingsList() {
        Vector<IconicMenuItem> menu_items_list = new Vector<IconicMenuItem>();

        menu_items_list.add(new IconicMenuItem("General",
                android.R.drawable.ic_menu_agenda, new Intent(this,
                DUBwisePrefsActivity.class)));

        menu_items_list.add(new IconicMenuItem("Voice",
                android.R.drawable.ic_lock_silent_mode_off, new Intent(this,
                VoicePrefsActivity.class)));

        menu_items_list.add(new IconicMenuItem("Graph",
                android.R.drawable.ic_menu_view, new Intent(this,
                GraphSettingsActivity.class)));

        menu_items_list.add(new IconicMenuItem("Piloting",
                android.R.drawable.ic_menu_share, new Intent(this,
                PilotingPrefsActivity.class)));

        menu_items_list.add(new IconicMenuItem("BlackBox",
                android.R.drawable.ic_menu_save, new Intent(this,
                BlackBoxPrefsActivity.class)));

        menu_items_list.add(new IconicMenuItem("Background Tasks",
                android.R.drawable.ic_menu_agenda, new Intent(this,
                BackgroundTaskListActivity.class)));


        this.setListAdapter(new IconicAdapter(this, (menu_items_list.toArray())));

    }


    @Override
    protected void onResume() {
        super.onResume();
        refreshSettingsList();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        IconicMenuItem item = ((IconicMenuItem) (this.getListAdapter()
                .getItem(position)));

        if (item.intent != null)
            startActivity(item.intent);

    }
}
