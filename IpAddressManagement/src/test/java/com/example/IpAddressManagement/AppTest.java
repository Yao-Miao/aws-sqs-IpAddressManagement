package com.example.IpAddressManagement;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    private App app;
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    public void setUp() throws Exception
    {
        this.app = new App("E");
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        app.startApp();
        app.stopApp();
    }
}
