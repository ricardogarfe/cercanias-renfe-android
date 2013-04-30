/**
 * 
 */
package com.ricardogarfe.renfe.test;

import junit.framework.Test;
import junit.framework.TestSuite;
import android.test.suitebuilder.TestSuiteBuilder;

/**
 * @author ricardo
 * 
 */
public class AllTests extends TestSuite {

    public static Test suite() {
        return new TestSuiteBuilder(AllTests.class)
                .includeAllPackagesUnderHere().build();
    }

}
