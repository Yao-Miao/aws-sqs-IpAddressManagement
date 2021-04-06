package com.example.IpAddressManagement;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.*;

import java.text.SimpleDateFormat;
import java.util.List;


public class SQSManager {
    //static var
    private static final AWSCredentials credentials = new BasicAWSCredentials("key_id","access_key");
    private static final AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);

    //private var
    private AmazonSQS sqs;
    public SQSManager(){
        sqs = AmazonSQSClientBuilder.standard().withCredentials(credentialsProvider).withRegion(Regions.US_EAST_2).build();
    }

    //Create sqs queue
    public String createQueue(String queueName){
        CreateQueueRequest create_request = new CreateQueueRequest(queueName)
                .addAttributesEntry("DelaySeconds", "5")
                .addAttributesEntry("MessageRetentionPeriod", "86400")
                .addAttributesEntry("FifoQueue", "true");
        try {
            sqs.createQueue(create_request);
        } catch (AmazonSQSException e) {
            if (!e.getErrorCode().equals("QueueAlreadyExists")) {
                throw e;
            }
        }

        String queueUrl = sqs.getQueueUrl(queueName).getQueueUrl();
        return queueUrl;
    }

    //send message to sqs queue
    public boolean sendIpAddress(String ipAddress, String queueUrl) {

        SimpleDateFormat tempDate = new SimpleDateFormat("YYYYMMDDHHmmss");
        String datetime = tempDate.format(new java.util.Date());
        //send message
        SendMessageRequest send_msg_request = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(ipAddress)
                .withMessageGroupId(datetime)
                .withMessageDeduplicationId(datetime);
        SendMessageResult smr = sqs.sendMessage(send_msg_request);
        return true;
    }

    //receive message from sqs queue
    public String receiveIpAddress(String queueUrl){

        List<Message> messages = sqs.receiveMessage(queueUrl).getMessages();
        String ipAddress = "";
        Message m = null;
        if(!messages.isEmpty()){
            m = messages.get(0);
            //System.out.println(m.getBody());
            sqs.deleteMessage(queueUrl, m.getReceiptHandle());
            return m.getBody();
        }
        return "";
    }

    //close connection
    public void closeSQS(){
        sqs.shutdown();
    }



}
