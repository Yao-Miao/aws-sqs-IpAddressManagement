package com.example.IpAddressManagement;

import java.util.Scanner;

/**
 *
 *
 */
public class App 
{
    //private var
    private SQSManager sqsManager; //used to communicate with sqs
    private ManagerApp managerApp;
    private ClientApp clientApp;
    private Scanner scanner;
    private String role;

    public App() {
        sqsManager = new SQSManager();
        managerApp = new ManagerApp(sqsManager);
        clientApp = new ClientApp(sqsManager);
        scanner = new Scanner(System.in);
        role = "";
    }

    public App(String role) {
        sqsManager = new SQSManager();
        managerApp = new ManagerApp(sqsManager);
        clientApp = new ClientApp(sqsManager);
        scanner = new Scanner(System.in);
        this.role = role;
    }

    //The entrance of the application
    public static void main( String[] args )
    {
        App app = new App();
        app.startApp();
        app.stopApp();
    }

    //Start the app, show the main menu
    public void startApp(){
        String operationCode = "";
        while(1==1){
            //Choose role: M => Manager, C => Client, E => Exit
            if(role.length() == 0){
                System.out.print("*************************************************\n" +
                        "* Please Chose Your Role :                      *\n" +
                        "* 1.Manager (Enter M)                           *\n" +
                        "* 2.Client (Enter C)                            *\n" +
                        "* 3.Exit (Enter E)                              *\n" +
                        "*************************************************\n");
                System.out.print(">>");
                role = scanner.nextLine().toUpperCase();
            }
            if(role.equals("M")){
                managerApp.startManagerApp();
                role = "";
            }else if(role.equals("C")){
                clientApp.StartClientApp();
                role = "";
            }else if(role.equals("E")){
                break;
            }else{
                role = "";
                continue;
            }
        }
    }

    //stop the app and close connection with sqs
    public void stopApp(){
        sqsManager.closeSQS();
    }
}
