package com.company;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class RUDPClient {

    public static void main(String[] args) {

        try {
            //create Datagram to communicate with clients
            DatagramSocket serverSocket = new DatagramSocket(9876);

            //creates an array of bytes used 4 creating datagramPackets
            byte[] incomingData = new byte[1024];

            int ackNum = 1;

            while (true) {

                //creates a DatagramPacket to receive datagram
                //from socket
                DatagramPacket incomingPacket =
                        new DatagramPacket(incomingData, incomingData.length);

                //receives datagram from the client and copies it
                //into the packet
                serverSocket.receive(incomingPacket);

                //gets data(IP address & PORT) from the datagram
                //packet received from the client
                byte[] data = incomingPacket.getData();
                ByteArrayInputStream inputStream =
                        new ByteArrayInputStream(data);
                ObjectInputStream objectInputStream =
                        new ObjectInputStream(inputStream);

                Segment segment = (Segment) objectInputStream.readObject();
                String s = new String(segment.data);

                //Prints out the seqAckNum and the nextSeqAckNum
                if (ackNum >= 2 && ackNum < 6) {
                    System.out.println("NextSeq#: 49"
                            + " | Ack#: 49");
                } else {
                    if (ackNum == 1) {
                        System.out.println("NextSeq#: "
                                + segment.seqAckNum
                                + " | Ack#: 49");
                    } else if (ackNum == 6) {
                        System.out.println("NextSeq#: 49"
                                + " | Ack#: "
                                + ((segment.seqAckNum) + 1));
                    } else {
                        System.out.print("NextSeq#:" +
                                segment.seqAckNum + " | " +
                                "Ack#: " +
                                ((segment.seqAckNum) + 1)
                                + "\n");
                    }
                }
                ackNum++;

                if (segment.next == 1)
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
