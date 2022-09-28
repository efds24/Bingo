package com.company;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Cliente {
    //Conjunto de números que forman el cartón y un booleano(true si ha salido, false en caso contrario);
    private HashMap<Integer, Boolean> carton;
    //Variable en la que se almacena si alguien ha cantado bingo
    private Boolean bingo;
    //Nombre del jugador
    private String nombre;

    public Cliente(String nombre) {
        int i;
        //Se crea el objeto random para genera números aleatorios
        Random random = new Random();
        //Se crea una lista de los 90 posibles números
        ArrayList<Integer> numbers = new ArrayList<>(90);

        //Se asigna el nombre del jugador
        this.nombre = nombre;

        //Se inicializa cartón
        carton = new HashMap<>(15);

        //Al principio no habrá bingo
        bingo = false;

        //Reparto del carton
        //Se añaden números de 1 al 90
        for (i = 1; i<91; i++){
            numbers.add(i);
        }
        for (i = 0; i<15;i++){
            //Se coge un indice aleatorio de la lista de 90 elementos
            int index = random.nextInt(numbers.size());
            //Se añade a carton el número que hay en ese indice
            carton.put(numbers.get(index), false);
            //Se borra de la lista para que no se pueda volver a repetir
            numbers.remove(index);
        }
    }

    @Override
    public String toString() {
        String s = nombre + ":\n";
        s += "==============================\n";
        int contador = 0;
        for (Integer numero: carton.keySet()) {
            contador++;
            if (numero/10==0){
                s += "|0" + numero;
            } else { s += "|" + numero;}
            if(carton.get(numero)){
                s+=" x|";
            } else {
                s+="  |";
            }
            if (contador%5==0&&contador<=15) {
                s += "\n==============================\n";
            }
        }
        return s;
    }

    public String getNombre() {
        return nombre;
    }

    //Comprobar si el número está en el cartón
    public void comprobarNumero(int numero){
        if(carton.containsKey(numero)&&carton.get(numero)==false){
            carton.put(numero, true);
        }
        System.out.println(toString());
    }

    //Comprobar si se tiene bingo
    public boolean bingo(){
        for(Integer i: carton.keySet()){
            if(!carton.get(i)){return false;};
        }
        return true;
    }

    public Boolean getBingo() {
        return bingo;
    }

    public void setBingo(Boolean bingo) {
        this.bingo = bingo;
    }
}
