/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tablero;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author jmque
 */
public class GeneradorLista {
    LinkedList<Integer> possibleContents = new LinkedList<>();
    LinkedList<Integer> lista = new LinkedList<>();
    
    
    public GeneradorLista(){
        initLista();
        reorderList();
    }
    
    public void initLista(){
        for (int i = 0; i < 9; i++){ // JUEGOS
            possibleContents.add(i + 1);
            possibleContents.add(i + 1);
        }
        for (int i = 18; i < 26; i++){ // COMODINES
            possibleContents.add(i + 1);
        }
        int size = possibleContents.size();
//        System.out.println(size);
        for (int i = 1; i < (size + 1); i++){
            int number = possibleContents.remove(new Random().nextInt(possibleContents.size()));
//            System.out.println(i);
            lista.add(number);
            System.out.print(number + " ");
        }
        System.out.println("");
    }
        
    public void reorderList(){
        if (lista.indexOf(25) < lista.indexOf(24))
            Collections.swap(lista, lista.indexOf(25), lista.indexOf(24));
        if (lista.indexOf(26) < lista.indexOf(24))
            Collections.swap(lista, lista.indexOf(26), lista.indexOf(24));
        if (lista.indexOf(26) < lista.indexOf(25))
            Collections.swap(lista, lista.indexOf(25), lista.indexOf(26));
        for (int i = 0; i < lista.size(); i++){
            System.out.print(lista.get(i) + " ");
        }
        System.out.println("");
    }

    public LinkedList<Integer> getLista() {
        return lista;
    }

    public void setLista(LinkedList<Integer> lista) {
        this.lista = lista;
    }
    
    
}
