/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
public class Client {
   public static String IP_SERVER = "localhost"; 
   DataInputStream entrada = null;
   public DataOutputStream salida = null;//referencias a i/o
   Socket cliente = null;
   String nombre;
   prograMario ventanaInicio;
   
   public Client(prograMario ventanaInicio) {
       this.ventanaInicio = ventanaInicio;
   }
   public void connect() throws IOException {
       try{
        cliente = new Socket(IP_SERVER, 8093);

        ////////////Inizialización de I/O///////////
        entrada = new DataInputStream(cliente.getInputStream());
        salida = new DataOutputStream(cliente.getOutputStream());
        /////////////////////////////////////////////////////////

        nombre = JOptionPane.showInputDialog("Introduzca su nick: "); // Pide el nombre al cliente

        salida.writeUTF(nombre); /// Envía el nombre al server
        ventanaInicio.setVisible(true);
        ventanaInicio.setNick(nombre);
        new ClientThread(entrada, ventanaInicio, this).start();
       } catch(Exception ex){
            System.out.println("\tEl servidor esta apagado :(");
            System.out.println("\t=============================");
            System.out.println(ex);
       }
   }

    public String getNombre() {
        return nombre;
    }
   
   
}
