package com.company;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

/*
	@author Joel&Eranus
*/
class RUDPServer
{

    static int win_size = 10;
    static int timeoutVal = 300;		// 300ms until timeout
    static Timer timer;
    static int base;					// base sequence number of window
    static int nextSeqNum;				// next sequence number in window
    static BufferedReader getLine;  //reads user lines from text file

    /*Not so sure about semaphore*/
    Semaphore s;				// guard CS for base, nextSeqNum
    static boolean isTransferComplete;         	// if receiver has completely received the file

    boolean inThreadDone = true;
    boolean outThreadDone = true;


    public static void main(String[] args) throws IOException {

        String userInput= "";

        //create socket at port 6789 to communicate with all clients
        ServerSocket welcomeSocket = new ServerSocket(6789);
        Socket connectionSocket = welcomeSocket.accept();

        //opens a bufferedReader on a file name alice.txt
        try {
            BufferedReader inputStream = new BufferedReader(new FileReader("alice.txt"));
            getLine = inputStream;
        }
        catch (FileNotFoundException e){
            System.err.println("Couldn't open quote file.  Serving time instead.");
        }


        while(true){

            userInput = getLine.readLine();

            //server receives request from client
            byte[] buf = new byte[256];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            /*FIXME*/welcomeSocket.receive(packet);

            //server response after receiving client request
            while (!isTransferComplete){

                //sends packet if window is not full
                if(nextSeqNum < base + win_size){
                    //doSomething
                }

                if(base == nextSeqNum){
                    setTimer(true); //if first packet of window
                }
            }

        }

        //server receives request from client
        byte[] buf = new byte[256];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        /*FIXME*/welcomeSocket.receive(packet);

        //server response after receiving client request
        while (!isTransferComplete){

            //sends packet if window is not full
            if(nextSeqNum < base + win_size){
                //doSomething
            }

            if(base == nextSeqNum){
                setTimer(true); //if first packet of window
            }
        }

    }


    //FIXME
    // to start or stop the timer
    public static void setTimer(boolean isNewTimer) {
        if (timer != null) {
            timer.cancel();
        }
        if (isNewTimer) {
            timer = new Timer();
            timer.schedule(new Timeout(), timeoutVal);
        }
    }
//
//    //FIXME
//    // Timeout task
//    public class Timeout extends TimerTask {
//
//        public void run() {
//            try {
//                s.acquire();
//                /**
//                 * *** enter CS ****
//                 */
//                System.out.println("Sender: Timeout!");
//                nextSeqNum = base;	// resets nextSeqNum
//                s.release();
//                /**
//                 * *** leave CS ****
//                 */
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }// END CLASS Timeout

}

