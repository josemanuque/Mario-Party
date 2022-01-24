/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Progra;

import java.util.LinkedList;
import java.util.Random;
import Progra.Punto;

/**
 *
 * @author Usuario
 */
public class sopa {
private LinkedList<String> palabras;
private LinkedList<Integer> tamaños;
private String horizontal;
private String vertical;
private String diag1;
private String diag2;
private char[][] matriz;
private int tamaño;
private String abecedario = "abcdefghijklmnopqrstuvwqyz";
private int llamadas;
private LinkedList<Punto> puntosHorizontal;
private LinkedList<Punto> puntosVertical;
private LinkedList<Punto> puntosDiag1;
private LinkedList<Punto> puntosDiag2;

public sopa() { 
    llamadas = 0;
    tamaños = new LinkedList();
    tamaños.add(10);
    tamaños.add(15);
    tamaños.add(20);
    Random random = new Random();
    tamaño = tamaños.get(random.nextInt(3));
    matriz = new char[tamaño][tamaño];
    palabras = new LinkedList();
    palabras.add("mario");
    palabras.add("luigi");
    palabras.add("koopa");
    palabras.add("bowser");
    palabras.add("castillo");
    palabras.add("bomba");
    palabras.add("yoshi");
    palabras.add("peach");
    palabras.add("hongo");
    palabras.add("fuego");
    palabras.add("fiesta");
    palabras.add("juego");
    palabras.add("letras");
    palabras.add("memoria");
    palabras.add("roedor");
    palabras.add("noviembre");
    palabras.add("firma");
    palabras.add("anillo");
    palabras.add("sombrilla");
    palabras.add("futbol");
    palabras.add("hielo");
    palabras.add("agua");
    palabras.add("hojas");
    palabras.add("salto");
    palabras.add("prohibir");
    palabras.add("princesa");
    palabras.add("villano");
    palabras.add("nivel");
    palabras.add("tuberia");
    palabras.add("zacate");
    palabras.add("alimento");
    palabras.add("hermano");
    palabras.add("moneda");
    palabras.add("daisy");
    palabras.add("premio");
    palabras.add("ganar");
    palabras.add("derrota");
    palabras.add("victoria");
    palabras.add("tablero");
    palabras.add("aviones");
    palabras.add("mostaza");
    palabras.add("arriba");
    palabras.add("abajo");
    palabras.add("duelo");
    palabras.add("facil");
    palabras.add("pintura");
    palabras.add("final");
    palabras.add("antena");
    palabras.add("empezar");
    palabras.add("flores");
    palabras.add("italia");
    palabras.add("japon");
    palabras.add("razon");
    palabras.add("cascada");
    palabras.add("crecer");
    palabras.add("castigo");
    palabras.add("pizza");
    palabras.add("bigote");
    palabras.add("pelea");
    palabras.add("mente");
    palabras.add("super");
    palabras.add("rural");
    palabras.add("escrito");
    palabras.add("galaxia");
    palabras.add("carro");
    palabras.add("amistad");
    palabras.add("lleno");
    palabras.add("cancion");
    palabras.add("cerdo");
    palabras.add("carton");
    palabras.add("celular");
    palabras.add("silla");
    palabras.add("cortar");
    palabras.add("carbon");
    palabras.add("espacio");
    palabras.add("salud");
    palabras.add("corazon");
    palabras.add("mundo");
    palabras.add("tierra");
    palabras.add("valor");
    palabras.add("curar");
    palabras.add("pesado");
    palabras.add("cuadro");
    palabras.add("platano");
    palabras.add("empate");
    palabras.add("mortal");
    palabras.add("union");
    palabras.add("denso");
    palabras.add("trabajo");
    palabras.add("abeja");
    palabras.add("carga");
    palabras.add("deporte");
    palabras.add("wario");
    palabras.add("libro");
    palabras.add("vegetal");
    palabras.add("cortina");
    palabras.add("lapiz");
    palabras.add("falso");
    palabras.add("gusano");
    palabras.add("verdad");
}

public void generarMatriz() {
    Random random = new Random(); 
    horizontal = palabras.get(random.nextInt(100));
    puntosHorizontal = new LinkedList();
    if(llamadas > 10) {
        tamaño = 20;
        matriz = new char[tamaño][tamaño];
    }
    if(tamaño == 10) {
        while(true) {
            if (horizontal.length() > 6) {
                horizontal = palabras.get(random.nextInt(100));
            }
            else {
                break;
            }
        }
    int index = random.nextInt(3);
    for(int i = index; i < horizontal.length() + index; i++) {
        Punto p = new Punto(index, i);
        puntosHorizontal.addLast(p);
        matriz[index][i] = horizontal.charAt(i - index);
    }
    vertical = palabras.get(random.nextInt(100));
    puntosVertical = new LinkedList();
    while(true) {
        if(vertical == horizontal) 
            vertical = palabras.get(random.nextInt(100));
        else break;
    }
    if(index > 0) 
        index--;
    for(int i = index; i < vertical.length() + index; i++) {
        Punto p = new Punto(i, tamaño - 1 - index);
        puntosVertical.addLast(p);
        matriz[i][tamaño - 1 - index] = vertical.charAt(i - index);
    }
    diag1 = palabras.get(random.nextInt(100));
    while(true) {
        if(diag1 == horizontal || diag1 == vertical || diag1.length() > 6) 
            diag1 = palabras.get(random.nextInt(100));
        else break;
    }
    index = random.nextInt(3);
    index = index + 7;
    puntosDiag1 = new LinkedList();
    for(int i = 0; i < diag1.length(); i++ ) {
        Punto p = new Punto(index, 9 - index);
        puntosDiag1.addFirst(p);
        matriz[index][9 - index] = diag1.charAt(i);
        index--;
    }
    diag2 = palabras.get(random.nextInt(100));
    while(true) {
        if(diag2 == horizontal || diag2 == vertical || diag2.length() > 5 || diag2 == diag1) 
            diag2 = palabras.get(random.nextInt(100));
        else break;
    }
    index = random.nextInt(2);
    index = index + 8;
    puntosDiag2 = new LinkedList();
    for(int i = 0; i < diag2.length(); i++ ) {
        Punto p = new Punto(index, 12 - index);
        puntosDiag2.addFirst(p);
        matriz[index][12 - index] = diag2.charAt(i);
        index--;
    }
        
    }
    else {
        int fila = random.nextInt(tamaño);
        int columna = random.nextInt(tamaño - 8);
        for(int i = columna; i < horizontal.length() + columna; i++) {
            System.out.println(fila + " - " + i);
            matriz[fila][i] = horizontal.charAt(i - columna); 
            Punto p = new Punto(fila, i);
            puntosHorizontal.addLast(p);
        }
        
        vertical = palabras.get(random.nextInt(100));
        puntosVertical = new LinkedList();
        while(true) {
            if(vertical == horizontal) 
                vertical = palabras.get(random.nextInt(100));
            else break;
        }
        fila = random.nextInt(tamaño - 8);
        columna = random.nextInt(tamaño);
        for(int i = fila; i < vertical.length() + fila; i++) {
            System.out.println(columna + " - " + i);
            if(Character.isLetter(matriz[i][columna])) {
                if(!(matriz[i][columna] == vertical.charAt(i - fila))) {
                    llamadas++;
                    generarMatriz();
                    return;
                }
            }
            matriz[i][columna] = vertical.charAt(i - fila);
            Punto p = new Punto(i, columna);
            puntosVertical.addLast(p);
        }
        
        diag1 = palabras.get(random.nextInt(100));
        puntosDiag1 = new LinkedList();
        while(true) {
            if(diag1 == horizontal || diag1 == vertical)
                diag1 = palabras.get(random.nextInt(100));
            else break;
        }
        fila = random.nextInt(tamaño - 8);
        columna = random.nextInt(tamaño - 8);
        for(int i = fila; i < diag1.length() + fila; i++) {
            System.out.println(columna + " - " + i);
            if(Character.isLetter(matriz[i][columna])) {
                if(!(matriz[i][columna] == diag1.charAt(i - fila))) {
                    llamadas++;
                    generarMatriz();
                    return;
                }
            }
            matriz[i][columna] = diag1.charAt(i - fila);
            Punto p = new Punto(i, columna);
            puntosDiag1.addLast(p);
            columna++;
        }
        
        diag2 = palabras.get(random.nextInt(100));
        puntosDiag2 = new LinkedList();
        while(true) {
            if(diag2 == horizontal || diag2 == vertical || diag2 == diag1)
                diag2 = palabras.get(random.nextInt(100));
            else break;
        }
        fila += 7;
        columna = random.nextInt(tamaño - 6);
        for(int i = columna; i < diag2.length() + columna; i++) {
            System.out.println(fila + " - " + i);
            if(Character.isLetter(matriz[fila][i])) {
                if(!(matriz[fila][i] == diag2.charAt(i - columna))) {
                    llamadas++;
                    generarMatriz();
                    return;
                }
            }
            matriz[fila][i] = diag2.charAt(i - columna);
            Punto p = new Punto(fila, i);
            puntosDiag2.addFirst(p);
            fila--;
        }
        
        
    }
    for(int f = 0; f < tamaño; f++) {
            for(int c = 0; c < tamaño; c++) {
                char car = matriz[f][c];
                if(!Character.isLetter(car)) 
                    matriz[f][c] = abecedario.charAt(random.nextInt(26));
            }
        }
}

public void print() {
    for(int f = 0; f < tamaño; f++) {
        for (int c = 0; c < tamaño; c++) {
            System.out.print(matriz[f][c]);
            System.out.print("   ");
        }
        System.out.println("\n");
    }
}

    public String getHorizontal() {
        return horizontal;
    }

    public String getVertical() {
        return vertical;
    }

    public String getDiag1() {
        return diag1;
    }

    public String getDiag2() {
        return diag2;
    }

    public int getTamaño() {
        return tamaño;
    }

    public char[][] getMatriz() {
        return matriz;
    }

    public String encontrarPalabra(LinkedList<Punto> puntos) {
        boolean  encontrarHor = true;
        boolean  encontrarVer = true;
        boolean  encontrarDiag1 = true;
        boolean  encontrarDiag2 = true;
        for(int f = 0; f < horizontal.length(); f++) {
            try {
                System.out.println(puntosHorizontal.get(f).toString() + " - " + puntos.get(f).toString());
                if(puntosHorizontal.get(f).getX() != puntos.get(f).getX() || puntosHorizontal.get(f).getY() != puntos.get(f).getY() ) {
                    encontrarHor = false;
                }
            }
            catch(Exception e) {
                encontrarHor = false;
            }
            
            
        }
        
        for(int f = 0; f < vertical.length(); f++) {
            try {
                System.out.println(puntosVertical.get(f).toString() + " - " + puntos.get(f).toString());
                if(puntosVertical.get(f).getX() != puntos.get(f).getX() || puntosVertical.get(f).getY() != puntos.get(f).getY() ) {
                    encontrarVer = false;
                }
            }
            catch(Exception e) {
                encontrarVer = false;
            }
        }
        
        for(int f = 0; f < diag1.length(); f++) {
            try {
                System.out.println(puntosDiag1.get(f).toString() + " - " + puntos.get(f).toString());
                if(puntosDiag1.get(f).getX() != puntos.get(f).getX() || puntosDiag1.get(f).getY() != puntos.get(f).getY() ) {
                    encontrarDiag1 = false;
                }
            }
            catch(Exception e) {
                encontrarDiag1 = false;
            }
        }
        
        for(int f = 0; f < diag2.length(); f++) {
            try {
                System.out.println(puntosDiag2.get(f).toString() + " - " + puntos.get(f).toString());
                if(puntosDiag2.get(f).getX() != puntos.get(f).getX() || puntosDiag2.get(f).getY() != puntos.get(f).getY() ) {
                    encontrarDiag2 = false;
                }
            }
            catch(Exception e) {
                encontrarDiag2 = false;
            }
        }
        if(horizontal == "") encontrarHor = false;
        if(vertical == "") encontrarVer = false;
        if(diag1 == "") encontrarDiag1 = false;
        if(diag2 == "") encontrarDiag2 = false;
        if(encontrarHor) {
            System.out.println("z");
            horizontal = "";
            return "horizontal"; 
        }
        else if(encontrarVer) {
            System.out.println("e");
            vertical = "";
            return "vertical"; 
        }
        else if(encontrarDiag1) {
            System.out.println("b");
            diag1 = "";
            return "diag1"; 
        }
        else if(encontrarDiag2) {
            System.out.println("a");
            diag2 = "";
            return "diag2"; 
        }
        else return "";
    }

}

