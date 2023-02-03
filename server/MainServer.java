package server;


import java.io.IOException;
import java.net.*;

//Clase principal del servidor
//El servidor debe:
//  - Extraer una bola del 1 al 90, una vez por segundo
//  - Enviar la bola a los clientes
//  - Recibir la señal de bingo de los clientes
//  - Notificar que la partida a terminado 

public class MainServer extends Thread{
    public static void main(String[] args) {
        Server server = new Server();
        MulticastSocket s = null;

        try {
            //Se une al grupo MultiCasrt
            InetAddress group = InetAddress.getByName("225.0.0.100");
            s = new MulticastSocket(6789);
            s.joinGroup(group);
            
            //Iniciamos el hilo que espera a que se cante bingo
            new Listener(server,s).start();
            
            while (server.getSizeNumbers() != 0 && server.getBingo() != true) {
                //Extraer una bola (mostrandola por pantalla)
                int bola = server.extraerBola();
                
                
                if(bola!=-1) {
                    char bolaExtraida[] = new char[2];
                    
                    //Primer caracter
                    bolaExtraida[0] = Character.forDigit(bola / 10, 10);
                    
                    //Segundo caracter
                    bolaExtraida[1] = Character.forDigit(bola % 10, 10);
                    
                    byte[] b = new byte[bolaExtraida.length];

                    //Metemos cada caracter ASCII en un byte
                    for (int i = 0; i < bolaExtraida.length; i++) {
                        b[i] = (byte) bolaExtraida[i];
                    }

                    //Enviamos el mensaje con la bola
                    DatagramPacket messageOut = new DatagramPacket(b, b.length, group, 6789);
                    s.send(messageOut);
                }
                
                //Esperamos un segundo
                Thread.sleep(1000);
            }
            
            //Este caso no se va a dar nunca
            if(server.getBingo()==false){
                System.out.println("No hay ganadador");  
            }
            
            //Abandonamos el grupo MultiCast
            s.leaveGroup(group);
        } catch(SocketException e){
            System.out.println("Socket: " + e.getMessage());
        } catch(IOException e) {
            System.out.println("IO: " + e.getMessage());
        } catch(InterruptedException e) {
            System.out.println("Interrupted: "+e.getMessage());
        } finally{
            //Cerramos el socket
            if (s != null) s.close();
        }
    }
}
