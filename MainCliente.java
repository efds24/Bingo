package com.company;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;

public class MainCliente {
    public static void main(String[] args) {
        MulticastSocket s = null;
        int numero;
        String n;
        Cliente cliente1;
        //Se reparten los números del cartón
        cliente1 = new Cliente("Cliente 1");
        //Se imprime el cartón del cliente
        System.out.println(cliente1.toString());
        //Se inicia el servidor
        //El servidor envía un número
        //El cliente recibe un número
        try {
            while(cliente1.getBingo()==false) {
                InetAddress group = InetAddress.getByName("225.0.0.100");
                s = new MulticastSocket(6789);
                s.joinGroup(group);
                byte[] buffer = new byte[2];
                DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
                s.receive(messageIn);
                n = new String(messageIn.getData());
                if(n.equals("bi")){
                    System.out.println("Alguien ha cantado bingo");
                    cliente1.setBingo(true);
                    break;
                }
                n = new String(messageIn.getData());
                System.out.println("La bola extraída es:" + n);
                numero = Integer.parseInt(n);

                //Se comprueba si el número está en el cartón del cliente
                System.out.println(numero);
                cliente1.comprobarNumero(numero);
                //Se comprueba si es bingo y si lo tiene lo comunica al servidor
                if (cliente1.bingo()) {
                    System.out.println("Bingo");
                    String msg = "bingo";
                    cliente1.setBingo(true);
                    byte[] m = msg.getBytes();
                    DatagramPacket messageOut = new DatagramPacket(m, m.length, group, 6789);
                    s.send(messageOut);
                }
            }
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            if (s != null) s.close();
        }
    }
}
