package fortumo.tests;

import junit.framework.TestSuite;

import org.junit.Test;

public class AllTests {
    public static TestSuite suite() {
        TestSuite suite = new TestSuite("Test for test");
        suite.addTestSuite(ExampleTest.class);
        return suite;
    }
}
