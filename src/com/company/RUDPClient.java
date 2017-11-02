package com.company;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/*
	@author Joel&Eranus
*/
class RUDPClient
{
    public static void main(String argv[]) throws Exception
    {

        DatagramSocket socket = new DatagramSocket();
        byte[] buf = new byte[256];
        DatagramPacket rcvPacket = new DatagramPacket(buf, buf.length);

        socket.receive(rcvPacket);
        String getServerLine = new String(rcvPacket.getData());
        System.out.println("FROM SERVER: " + getServerLine);
        buf = getServerLine.getBytes();

        //client sends a request to the server
        InetAddress address = rcvPacket.getAddress();
        int port = rcvPacket.getPort();
        //address = InetAddress.getByName(args[0]);
        DatagramPacket sendPacket = new DatagramPacket(buf, buf.length,
                address, port);
        socket.send(sendPacket);

    }

}