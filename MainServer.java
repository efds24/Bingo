package com.company;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.*;

public class MainServer {
    public static void main(String[] args) {
        Server server = new Server();
        byte[] buffer = new byte[5];
        DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);

        //Mensaje con la bola
        MulticastSocket s = null;
        try {


            while (server.getSizeNumbers()+1 != 0 && server.getBingo() != true) {
                InetAddress group = InetAddress.getByName("225.0.0.100");
                s = new MulticastSocket(6789);
                s.joinGroup(group);
                //Recibir la señal de bingo
                s.setSoTimeout(1000);
                try {
                    s.receive(messageIn);
                    String n = new String(messageIn.getData());
                    if (n.equals("bingo")) {
                        server.setBingo(true);
                        System.out.println("Se ha hecho bingo");
                    }
                } catch(SocketTimeoutException e){
                    //Extraer una bola (mostrandola por pantalla)
                    int bola = server.extraerBola();
                    if(bola!=-1) {
                        char bolaExtraida[] = new char[2];
                        bolaExtraida[0] = Character.forDigit(bola / 10, 10);
                        bolaExtraida[1] = Character.forDigit(bola % 10, 10);
                        byte[] b = new byte[bolaExtraida.length];

                        for (int i = 0; i < bolaExtraida.length; i++) {
                            b[i] = (byte) bolaExtraida[i];
                        }

                        DatagramPacket messageOut = new DatagramPacket(b, b.length, group, 6789);
                        s.send(messageOut);
                    }

                    s.leaveGroup(group);
                }
            }
        } catch(SocketException e){
            System.out.println("Socket: " + e.getMessage());
        } catch(IOException e){
            System.out.println("IO: " + e.getMessage());
        } finally{
            if (s != null) s.close();
        }
    }
}
