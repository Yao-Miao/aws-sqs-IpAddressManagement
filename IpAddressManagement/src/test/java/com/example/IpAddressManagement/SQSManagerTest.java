package com.example.IpAddressManagement;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class SQSManagerTest extends TestCase{
    private SQSManager sm;
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public SQSManagerTest(String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( SQSManagerTest.class );
    }

    public void setUp() throws Exception
    {
        this.sm = new SQSManager();
    }

    public void testCreateQueue()
    {
        sm.createQueue("MYTestQueue.fifo");
    }

    public void testSendIpAddress(){

        sm.sendIpAddress("192.168.0.1", "https://sqs.us-east-2.amazonaws.com/675649352655/MYTestQueue.fifo");
    }

    public void testReceiveIpAddress(){
        sm.receiveIpAddress("https://sqs.us-east-2.amazonaws.com/675649352655/MYTestQueue.fifo");
    }

}







