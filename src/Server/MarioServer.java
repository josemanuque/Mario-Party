/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.awt.Color;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

/**
 *
 * @author usuario
 */
public class MarioServer {
    servidorFrame frame;
    LinkedList<Socket> sockets;
   public static LinkedList<threadServer> threads = new LinkedList();
   boolean start = false;
   static int jugadores = 0; 
   public static LinkedList<Color> coloresElegidos = new LinkedList();
   public LinkedList<Integer> tiposCasilla = new Tablero.GeneradorLista().getLista();
    
    public MarioServer(servidorFrame frame) {
        this.frame = frame;
        sockets = new LinkedList();
    }
    
    public void runServer() throws IOException {
        frame.mostrar("-----------MARIO PARTY-----------");
        frame.mostrar("Server intialized. Waiting for clients");
        ServerSocket serv = new ServerSocket(8093);
        
        
        while(true) {
            //System.out.println("a");
            if(start || jugadores == 6) {
                if(jugadores > 1) {
                    break;
                }
            }
            Socket cliente = serv.accept();
            //System.out.println("b");
            jugadores++;
            frame.mostrar("Client #" + jugadores + " acepted.");
            if (jugadores == 1)
                frame.mostrar("Client #1 is the host of the game");
            addSocket(cliente);
            threadServer thread = new threadServer(cliente, this, jugadores); //Crea el hilo para el jugador
            System.out.println(threads.size());
            threads.addLast(thread);
            thread.start();
            //System.out.println("c");
                
        }
        System.out.println("Saliendo de la fase de aceptar clientes");
        
        while(true) {
            
        }
    }
    
    public void addSocket(Socket s) {
        sockets.addLast(s);
    }
    
    public void addThread(threadServer ts) {
        threads.addLast(ts);
    }

    public LinkedList<threadServer> getThreads() {
        return threads;
    }
    
    public void addColor(Color c) {
        coloresElegidos.addLast(c);
    }

    public LinkedList<Color> getColoresElegidos() {
        return coloresElegidos;
    }
    
    public MarioServer getServer() {
        return this;
    }
    
}
