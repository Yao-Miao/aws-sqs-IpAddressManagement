package com.example.IpAddressManagement;

import java.util.Scanner;

public class ManagerApp {
    private SQSManager sqsManager;
    private final String queueName = "A1_IP_ADDRESS_QUEUE.fifo";
    public ManagerApp(SQSManager sqsManager){
        this.sqsManager = sqsManager;
    }
    public void startManagerApp(){

        String queueUrl = "https://sqs.us-east-2.amazonaws.com/675649352655/A1_IP_ADDRESS_QUEUE.fifo";
        Scanner scanner = new Scanner(System.in);
        String operationCode = "";
        String ipAddress = "";

        while(1==1){
            //create sqs queue, add ip or return main menu
            if(operationCode.length() == 0){
                System.out.print("*************************************************\n" +
                                 "* <Manager> You can do the following things:    *\n" +
                                 "* 1.Create a new ip queue(Enter C)              *\n" +
                                 "* 2.Add a new ip address to the queue(Enter A)  *\n" +
                                 "* 3.Return to main menu(Press any other key)    *\n" +
                                 "*************************************************\n");
                System.out.print(">>");
                operationCode = scanner.nextLine().toUpperCase();
            }
            if(operationCode.equals("C")){
                //use this code to create sqs queue
                queueUrl = sqsManager.createQueue(queueName);
                System.out.println("Create SQS Queue Successfully! The URL is : " + queueUrl);

                //choose next operation
                System.out.print("*************************************************\n" +
                                 "* 1.Add a new ip address to the queue(Enter A)  *\n" +
                                 "* 2.Return to main menu(Press any other key)    *\n" +
                                 "*************************************************\n");
                System.out.print(">>");
                operationCode = scanner.nextLine().toUpperCase();
                if(operationCode.equals("A")){
                    continue;
                }else{
                    break;
                }

            }else if(operationCode.equals("A")){
                //use this code to add ip (send message)
                System.out.println("Please enter the new ip address:");
                System.out.print(">>");
                ipAddress = scanner.nextLine();
                if(!checkIpAddress(ipAddress)){
                    System.out.println("IP Address format error!");
                    continue;
                }
                boolean sendResult = sqsManager.sendIpAddress(ipAddress, queueUrl);

                if(sendResult){
                    System.out.println("Add new IP address successfully!!");
                }

                //choose next operation
                System.out.print("*************************************************\n" +
                                 "* 1.Continue to add ip address (Enter A)        *\n" +
                                 "* 2.Return to main menu(Press any other key)    *\n" +
                                 "*************************************************\n");
                System.out.print(">>");
                operationCode = scanner.nextLine().toUpperCase();
                if(operationCode.equals("A")){
                    continue;
                }else{
                    break;
                }
            }else{
                break;
            }
        }

    }

    //check the ip address format
    private boolean checkIpAddress(String ipAddress){
        String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\." +
        "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\." +
        "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\." +
        "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";

        if (ipAddress.matches(regex)) {
            return true;
        }
        return false;
    }

}
