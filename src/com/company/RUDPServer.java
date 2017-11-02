package com.company;


import java.io.BufferedReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Timer;
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
    static int nextSeqNum = 0;                // next sequence number in window
    static BufferedReader getLine;  //reads user lines from text file
    static byte[] sendData = new byte[256];

    /*Not so sure about semaphore*/
    Semaphore s;				// guard CS for base, nextSeqNum
    static boolean isTransferComplete;         	// if receiver has completely received the file

    boolean inThreadDone = true;
    boolean outThreadDone = true;


    public static void main(String[] args) throws IOException {

        String userInput = "";

        //opens a bufferedReader on a file name alice.txt
//        try {
//            BufferedReader inputStream = new BufferedReader(new FileReader("alice.txt"));
//            getLine = inputStream;
//
//        } catch (FileNotFoundException e) {
//            System.err.println("Couldn't open quote file.  Serving time instead.");
//        }


        DatagramSocket skt = new DatagramSocket(9876);
        byte[] sendData = new byte[1024];

        //server receives request from client
        byte[] buf = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        //copies info from client into pkt
        skt.receive(packet);

        //sends response to Client over DatagramSocket
        InetAddress address = packet.getAddress();
        int port = packet.getPort();
        packet = new DatagramPacket(buf, buf.length, address, port);
        skt.send(packet);

        while (true) {

            String sentence = new String(packet.getData());
            System.out.println("RECEIVED: " + sentence);
            System.out.println(nextSeqNum + packet.getLength());

            nextSeqNum += packet.getLength();
            //userInput = getLine.readLine();

            skt.close();
        }
    }
}

