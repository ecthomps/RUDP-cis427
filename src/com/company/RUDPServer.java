package com.company;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Random;

public class RUDPServer {

    public static void main(String[] arg) {

        ArrayList<Segment> segmentArrayList = new ArrayList<>();

        try {
            //Binds datagramSocket to any localHost
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress IP = InetAddress.getByName("localhost");
            byte[] incomingData = new byte[1024];

            //reads file into bufferReader
            BufferedReader br = new BufferedReader(new FileReader("alice.txt"));

            String line;
            int seqNr = 0;

            //adds line into segmentArrayList while
            //line != null
            while ((line = br.readLine()) != null) {
                line += '\n';
                Segment segment = new Segment();
                segment.seqAckNum = seqNr;
                segment.data = line.getBytes();
                segmentArrayList.add(segment);
                seqNr += segment.data.length;
            }

            int windowSize = 0;
            byte[] receiveData = new byte[1024];

            //creates a "receive" packet and uses the DatagramSocket
            //receive method to receive the reply from the server
            for (Segment s : segmentArrayList) {
                if (s == segmentArrayList.get(segmentArrayList.size() - 1))
                    s.next = 1;

//                if(windowSize < 13) {
                sendSegment(s, clientSocket, IP);
                windowSize++;
//                }
//                }else if(windowSize >= 6) {
//                    // Receive the server's packet
//                    DatagramPacket received = new DatagramPacket(receiveData, receiveData.length);
//                    clientSocket.receive(received);
//                }
//                windowSize++;
                Random rand = new Random();
                Thread.sleep(rand.nextInt(50 - 30) + 30);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendSegment(Segment s, DatagramSocket clientSocket, InetAddress IP) {
        try {
            ByteArrayOutputStream outputStream =
                    new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream =
                    new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(s);

            //creates DatagramPacket destined for the IP and Port on
            //the host
            byte[] data = outputStream.toByteArray();
            DatagramPacket sendPacket =
                    new DatagramPacket(data, data.length, IP, 9876);

            //sends a request to the server for information
            clientSocket.send(sendPacket);
            System.out.println("packet sent : " + s.seqAckNum);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
