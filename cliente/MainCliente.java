package cliente;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;

//Programa principal del cliente
//El cliente deberá:
//  - Crear su cartón con 15 números distintos aleatorios
//  - Recibir los números que le envía el servidor
//  - Recibir la señal de bingo de otros clientes
//  - Enviar una señal cuando haya hecho bingo

public class MainCliente {
    public static void main(String[] args) {
        MulticastSocket s = null;
        int numero;
        String n;
        Cliente cliente;
        
        //No sirve para nada porque siempre se canta bingo, pero se ha
        //contemplado este caso para comprobar posibles fallos del programa
        int contador = 0;
        
        //Se reparten los números del cartón
        //(se hace en el constructor de la clase cliente)
        cliente = new Cliente();
        
        //Se imprime el cartón del cliente
        System.out.println(cliente.toString());
        
        try {
            //El cliente se une al grupo de multicast
            InetAddress group = InetAddress.getByName("225.0.0.100");
            s = new MulticastSocket(6789);
            s.joinGroup(group);
                
            //la única condición que se va a dar es la segunda
            while(contador<90 && cliente.getBingo()==false) {
                
                //buffer para guardar el datagrama que se recibe
                byte[] buffer = new byte[5];
                
                DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
                s.receive(messageIn);
                
                //Se convierte a un String
                n = new String(messageIn.getData());
                
                //Se coge la subcadena del tamaño del mensaje enviado
                n = n.substring(0, messageIn.getLength());
                
                //Si se recibe bingo es que alguien a terminado la partida
                if(n.equals("bingo")){
                    if(!cliente.getBingo()){
                        System.out.println("Alguien ha cantado bingo");
                    }
                    //Se sale del bucle
                    break;
                }
                
                //Se imprime la bola extraida
                System.out.println("La bola extraída es:" + n);
                
                //Se transforma a entero
                numero = Integer.parseInt(n);

                //Se comprueba si el número está en el cartón del cliente
                cliente.comprobarNumero(numero);
                //Se comprueba si es bingo y si lo es lo comunica al grupo
                if (cliente.bingo()) {
                    System.out.println("Bingo");
                    String msg = "bingo";
                    cliente.setBingo(true);
                    byte[] m = msg.getBytes();
                    DatagramPacket messageOut = new DatagramPacket(m, m.length, group, 6789);
                    s.send(messageOut);
                }
                
                //Se incrementa el contador
                contador++;
            }
            
            //Este es un caso imposible
            if(contador == 90){
                System.out.println("No hay ganadador"); 
            }
            
            //Salimos del grupo
            s.leaveGroup(group);
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            //Se cierra el socket
            if (s != null) s.close();
        }
    }
}
