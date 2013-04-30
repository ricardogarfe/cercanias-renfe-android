/**
 * 
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
