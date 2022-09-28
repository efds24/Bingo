package com.company;

import java.util.ArrayList;
import java.util.Random;

public class Server {
    private ArrayList<Integer> numbers;
    private Random random;
    private Boolean bingo;

    public Server() {
        random = new Random();
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
        if (!bingo && !numbers.isEmpty()) {
            int index = random.nextInt(numbers.size());
            ultimaExtraccion = numbers.get(index);
            numbers.remove(index);
            System.out.println("La bola extraída es: " + ultimaExtraccion);
        } else {
            ultimaExtraccion = -1;
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
