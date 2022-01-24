/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public class threadAceptar extends Thread {
    ServerSocket serv; //referencia al serverSocket
    int jugador; //número de jugador que se espera aceptar(1-6)
    servidorFrame frame; //referencia a la ventana del server
    MarioServer principal;
    public threadAceptar(ServerSocket serv, int jugador, servidorFrame frame, MarioServer principal) {
        this.serv = serv;
        this.jugador = jugador;
        this.frame = frame;
        this.principal = principal;
    }
    
    @Override
    public void run() {
        try {
            Socket cliente = serv.accept();
            frame.mostrar("Cliente #" + jugador + " aceptado.");
            principal.addSocket(cliente);
            threadServer thread = new threadServer(cliente, principal, jugador);
            thread.run();
        } catch (IOException ex) {
            
        }
        
    }
}
