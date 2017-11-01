package com.company;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/*
	@author Joel&Eranus
*/
class RUDPClient
{
    public static void main(String argv[]) throws Exception
    {
        String userInput= "";
        String serverResponce= "";
        String endCmd = "-1", nullInput = "";

        //create inputStream
        BufferedReader inputFromUser = new BufferedReader(new InputStreamReader(System.in));
        //create clientSocket
        Socket clientSocket = new Socket("localhost", 6789);
        //create outputStream to server
        DataOutputStream outputToServer = new DataOutputStream(clientSocket.getOutputStream());
        //establish clientServerConnection
        BufferedReader inputFromServer = new BufferedReader
                (new InputStreamReader(clientSocket.getInputStream()));

        //printCommand(clientSocket);

        while (true) {
            //System.out.print("Command: ");
            userInput = inputFromUser.readLine();
            outputToServer.writeBytes(userInput + '\n');

            String[] splitted = userInput.trim().split(" ");

            if(nullInput.equals(userInput)){

                break;
            }else
                for(int i = Integer.parseInt(splitted[1]); i <= Integer.parseInt(splitted[2]); i++){
                    serverResponce = inputFromServer.readLine();
                    System.out.println(serverResponce);
                }
            //System.out.println("-1");
        }

        clientSocket.close();

    }

//    public static void printCommand(Socket clientSocket) {
//
//        if (clientSocket.isConnected()) {
//            System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
//            System.out.println("+-+-+-+-+-+-+   Simple TCP Service (STS)  +-+-+-+-+-+-+");
//            System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
//            System.out.println("Commands allowed by the server for this client:");
//            System.out.println("\t\tdownload [starting line #] [ending line #]");
//        }
//    }

    public static void rudpEncapsulate(){
//        Segment {
//            int seqAckNum;
//            byte [] data;
//        };
    }
}