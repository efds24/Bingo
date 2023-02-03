package server;

import java.util.ArrayList;
import java.util.Random;

public class Server {
    private ArrayList<Integer> numbers;
    private Random random;
    private Boolean bingo;

    public Server() {
        
        random = new Random(System.currentTimeMillis());
        bingo = false;
        numbers = new ArrayList<>(90);
        int i;
        for (i = 1; i<91; i++) {
            numbers.add(i);
        }
    }

    //Función para extraer una bola al azar
    public int extraerBola() {
        int ultimaExtraccion = -1;
        
        //Si no se ha hecho bingo, ni han salido todas las bolas
        if (!bingo && !numbers.isEmpty()) {
            
            //Se toma un número de 0 a el tamaño de la lista
            int index = random.nextInt(numbers.size());
            
            //Se extrae el número de la lista que esta en esa posición
            ultimaExtraccion = numbers.get(index);
            numbers.remove(index);
            
            //Se imprime por pantalla
            System.out.println("La bola extraída es: " + ultimaExtraccion);
        }
        
        return ultimaExtraccion;
    }

    public int getSizeNumbers() {
        return numbers.size();
    }

    public Boolean getBingo() {
        return bingo;
    }

    public void setBingo(Boolean bingo) {
        this.bingo = bingo;
    }
}
