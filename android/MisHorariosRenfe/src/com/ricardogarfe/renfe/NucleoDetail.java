/*
 * Copyright [2013] [Ricardo García Fernández] [ricardogarfe@gmail.com]
 * 
 * This file is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This file is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.ricardogarfe.renfe;

import com.ricardogarfe.renfe.views.MapTabView;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class NucleoDetail extends TabActivity implements OnTabChangeListener {

    private static final String LIST_TAB_TAG = "List";
    private static final String MAP_TAB_TAG = "Map";

    private TabHost mTabHost;
    private FrameLayout mFrameLayout;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nucleo_detail);

        mTabHost = getTabHost();
        Context context = this.getApplicationContext();

        TabSpec tabNucleosSpec = mTabHost.newTabSpec(LIST_TAB_TAG);
        tabNucleosSpec.setIndicator(LIST_TAB_TAG);
        Intent nucleosIntent = new Intent(context, NucleosActivity.class);
        tabNucleosSpec.setContent(nucleosIntent);

        mTabHost.addTab(tabNucleosSpec);

        TabSpec tabMapSpec = mTabHost.newTabSpec(MAP_TAB_TAG);
        tabMapSpec.setIndicator(MAP_TAB_TAG);
        Intent mapIntent = new Intent(context, MapTabView.class);
        tabMapSpec.setContent(mapIntent);

        mTabHost.addTab(tabMapSpec);

        mTabHost.setCurrentTab(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nucleo_detail, menu);
        return true;
    }

    public void onTabChanged(String tabName) {
        // TODO Auto-generated method stub
        if (tabName.equals(MAP_TAB_TAG)) {
            // do something on the map
        } else if (tabName.equals(LIST_TAB_TAG)) {
            // do something on the list
        }

    }

}
