/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tablero;
import Progra.*;
import Server.Client;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author diemo
 */
public class Casilla {
    private int tipo = 0;
    public MemoryPath mp;
    public GuessWho gw;
    public CatchCat cat;
    public BomberMario bomber;
    public collectTheCoins coins;
    public SuperBrosMemory memory;
    public sopaFrame sopa;
//    public
//    public
//    public JButton btnReturn;
//    public JFrame frame;
    public boolean finished = false;
    public int arrayIndex;
    public Tablero tablero; 
    private Client cliente;
    
    public Casilla(Tablero tablero){
        this.tablero = tablero;
    }
    
    
    public void setTipo(int tipo){
        this.tipo = tipo;
    }

    public int getTipo() {
        return tipo;
    }

    public void setCliente(Client cliente) {
        this.cliente = cliente;
    }
    
    
    public void run(){
        System.out.println(tipo);
////        tipo = 7;
//            tipo = 3;
        //if(tipo != 19 && tipo != 20 && tipo != 21 && tipo != 22 && tipo != 23 && tipo != 24 && tipo != 25 && tipo != 26) tipo = 100;
        switch(tipo){
            case 1 ->{
                System.out.println("Memory Path");
                mp = new MemoryPath();
                mp.setVisible(true);
                mp.tablero = tablero;
            }
            case 2 ->{
                System.out.println("Guess Who");
                gw = new GuessWho();
                gw.setVisible(true);
                gw.tablero = tablero;

                
            }
            case 3 ->{
                System.out.println("Catch the Cat");
                cat = new CatchCat();
                cat.setVisible(true);
                cat.tablero = tablero;
            }
            case 4 ->{
                System.out.println("Super Bro's Memory");
                try {
                cliente.salida.writeInt(20);
            } catch (IOException ex) {
                Logger.getLogger(Casilla.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            }
            case 5 ->{
                System.out.println("Gato");
            try {
                cliente.salida.writeInt(13);
            } catch (IOException ex) {
                Logger.getLogger(Casilla.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
            case 6 ->{
                System.out.println("Collect the coins");
                coins = new collectTheCoins(); // Coins
                coins.setVisible(true);
                coins.tablero = tablero;
            }
            case 7 ->{
                System.out.println("Sopa de Letras");
                sopa = new sopaFrame(); // Sopa
                sopa.setVisible(true);
                sopa.tablero = tablero;
            }
            case 8 ->{
                System.out.println("Bomber Mario");
                bomber = new BomberMario(); // Bomber
                bomber.setVisible(true);
                bomber.tablero = tablero;
            }
            case 9 ->{
                try {
                    System.out.println("Mario Cards");
                    cliente.salida.writeInt(9);
                } catch (IOException ex) {
                    Logger.getLogger(Casilla.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            case 19 ->{
                System.out.println("Cola");
                //se hace en tablero xd
            }
            case 20 ->{
                System.out.println("Cárcel");
                tablero.castigo = 3;
            }
            case 21 ->{
                System.out.println("Estrella");
                tablero.getBtnDados().setEnabled(true);
                // permitir lanzar los dados inmediatamente
            }
            case 22->{
                System.out.println("Flor de fuego");
                tablero.mandarCastigoEnemigo(2);
            }
            case 23 ->{
                System.out.println("Flor de hielo");
                tablero.mandarCastigoEnemigo(1);
            }
            case 24 ->{
                System.out.println("Tubo 1");
               tablero.mandarMoverseTubo(25);
            }
            case 25 ->{
                System.out.println("Tubo 2");
                tablero.mandarMoverseTubo(26);
            }
            case 26 ->{
                System.out.println("Tubo 3");
                tablero.mandarMoverseTubo(24);
            }
            case 100 ->{
                tablero.gameEnded(1);
            }
        }
        //setupActionListener();
    }
    public String nombreCasilla(){
        switch(tipo){
            case 1->{
                return "MemPath";
            }
            case 2->{
                return "Guess";
            }
            case 3->{
                return "CatchCat";
            }
            case 4->{
                return "Memory";
            }
            case 5->{
                return "Gato";
            }
            case 6->{
                return "Coins";
            }
            case 7->{
                return "Letras";
            }
            case 8->{
                return "Bomber";
            }
            case 9->{
                return "Cards";
            }
            case 19->{
                return "Cola";
            }
            case 20->{
                return "Cárcel";
            }
            case 21->{
                return "Star";
            }
            case 22->{
                return "Fuego";
            }
            case 23->{
                return "Hielo";
            }
            case 24->{
                return "Tubo";
            }
            case 25->{
                return "Tubo";
            }
            case 26->{
                return "Tubo";
            }
        }
        return "";
    }
    
    
//    public void setupActionListener(){
//        btnReturn.addActionListener(new ActionListener() {
//                public void actionPerformed(java.awt.event.ActionEvent evt) {
//                    System.out.println("HOLA");
//                    tablero.
//                }
//                });
//    }
    
 
}
