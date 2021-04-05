package com.example.IpAddressManagement;

import java.util.Scanner;

public class ClientApp {
    private SQSManager sqsManager;

    public ClientApp(SQSManager sqsManager) {
        this.sqsManager = sqsManager;
    }

    public void StartClientApp() {
        String queueUrl = "https://sqs.us-east-2.amazonaws.com/675649352655/A1_IP_ADDRESS_QUEUE.fifo";
        Scanner scanner = new Scanner(System.in);
        String operationCode = "";
        String ipAddress = "";
        while(1==1){
            //create apply ip or return main menu
            if(operationCode.length() == 0){
                System.out.print("*************************************************\n" +
                                 "* <Client> You can do the following things:     *\n" +
                                 "* 1.Apply a new IP address(Enter A)             *\n" +
                                 "* 2.Return to main menu(Press any other key)    *\n" +
                                 "*************************************************\n");
                System.out.print(">>");
                operationCode = scanner.nextLine().toUpperCase();
            }
            if(operationCode.equals("A")){
                //use this code to apply ip (receive message)
                ipAddress = sqsManager.receiveIpAddress(queueUrl);
                if(ipAddress.length() != 0){
                    System.out.println("You get an IP address: " + ipAddress);
                }else{
                    System.out.println("There is no available IP address now");
                }

                //choose next operation
                System.out.print("*************************************************\n" +
                                 "* 1.Apply other IP address(Enter A)             *\n" +
                                 "* 2.Return to main menu(Press any other key)    *\n" +
                                 "*************************************************\n");
                System.out.print(">>");
                operationCode = scanner.nextLine().toUpperCase();
                if(operationCode.equals("A") && ipAddress.length() != 0){
                    //release ip to sqs(send message)
                    boolean sendResult = sqsManager.sendIpAddress(ipAddress, queueUrl);
                    if(sendResult){
                        System.out.println("Release IP address(" + ipAddress + ") successfully!!");
                    }
                }
                continue;
            }else{
                if(ipAddress.length() != 0){
                    //release ip to sqs(send message)
                    boolean sendResult = sqsManager.sendIpAddress(ipAddress, queueUrl);
                    if(sendResult){
                        System.out.println("Release IP address(" + ipAddress + ") successfully!!");
                    }
                }
                break;
            }
        }
    }


}
