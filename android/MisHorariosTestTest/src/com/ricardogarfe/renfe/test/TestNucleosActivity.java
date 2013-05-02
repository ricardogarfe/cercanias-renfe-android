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

package com.ricardogarfe.renfe.test;

import com.jayway.android.robotium.solo.Solo;
import com.ricardogarfe.renfe.EstacionesNucleoViajeActivity;
import com.ricardogarfe.renfe.NucleosActivity;

import android.test.ActivityInstrumentationTestCase2;

/**
 * @author ricardo
 * 
 */
public class TestNucleosActivity extends
        ActivityInstrumentationTestCase2<NucleosActivity> {

    private Solo solo;

    public TestNucleosActivity() {
        super(NucleosActivity.class);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    /**
     * Test to check navigation between nucleos and estaciones activities.
     */
    public void testSelectItemList() {
        solo.clickInList(1);
        assertTrue(solo.waitForActivity(EstacionesNucleoViajeActivity.class
                .getSimpleName()));
        solo.goBack();
    }
}
