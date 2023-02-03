package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;

//Hilo que espera a recibir "bingo"
public class Listener extends Thread{
    private final Server server;
    private final MulticastSocket s;

    public Listener(Server server, MulticastSocket s){
        this.server = server;
        this.s = s;

    }
    @Override
    public void run() {
        byte[] buffer = new byte[5];
        DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
        
        //Mientras no se haga bingo
        while (server.getSizeNumbers() != 0 && server.getBingo() != true) {
            try {
                //Se recibe un mensaje
                s.receive(messageIn);
                String n = new String(messageIn.getData());
                
                //Si no es bingo se sigue escuchando, si lo es finaliza el programa
                if (n.equals("bingo")) {
                    server.setBingo(true);
                    System.out.println("Se ha hecho bingo");
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
