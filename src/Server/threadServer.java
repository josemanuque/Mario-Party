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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author Usuario
 */
public class threadServer extends Thread {
     threadServer enemigoGato = null;
     threadServer enemigoMemory = null;
     Socket cliente = null;//referencia a socket de comunicacion de cliente
     DataInputStream entrada=null;//Para leer comunicacion
     DataOutputStream salida=null;//Para enviar comunicacion	
     String nameUser; //Para el nombre del usuario de esta conexion
     MarioServer servidor;// referencia al servidor
     int numeroDeJugador;
     StartFrame ventanaInicial;
     LinkedList<threadServer> enemigos;
     boolean romper = false;
     Random random = new Random();
     int indicador;
     
     public threadServer(Socket cliente, MarioServer servidor, int numeroDeJugador) {
         this.cliente = cliente;
         this.servidor = servidor;
         this.numeroDeJugador = numeroDeJugador;
         enemigos = new LinkedList();
        
     }
     public void run() {
         System.out.println("Empezando threadServer");
         
         /////////////////////// Inicializando el juego ///////////////////////
         try {
             entrada=new DataInputStream(cliente.getInputStream());
             salida=new DataOutputStream(cliente.getOutputStream()); //inicialización de i/o
             nameUser = entrada.readUTF();
             System.out.println("nick del jugador " + numeroDeJugador + ": " + nameUser);
             
         } catch (IOException ex) {
             System.out.println("Error al inicializar jugador.");
         }
         
         /////////////////////// Nombre de jugador ///////////////////////
         int enemigosSize = servidor.getThreads().size();
         if(enemigosSize > 0) {
             enemigos = servidor.getThreads();
             for(int i = 0; i < enemigosSize; i++) {
                 try {
                     threadServer enemigo = servidor.getThreads().get(i);
                     if(enemigo.nameUser != this.nameUser) {
                         enemigo.salida.writeInt(1);
                         enemigo.salida.writeUTF(this.nameUser);
                         enemigo.salida.writeInt(this.numeroDeJugador);
                         salida.writeInt(1); //Le indica al cliente que tiene que escribir un enemigo
                         salida.writeUTF(enemigo.nameUser); //Manda el nombre del enemigo
                         salida.writeInt(enemigo.numeroDeJugador); //Manda el # del enemigo
                     }
                     else {
                         salida.writeInt(3); //Manda al cliente sus propios datos
                         salida.writeUTF(this.nameUser);
                         salida.writeInt(this.numeroDeJugador); //
                     }
                 } catch (IOException ex) {
                     System.out.println("Error al leer el nombre del jugador");
                 }
             }
         }
         /////////////////////// Opciones generales ///////////////////////
         int opcion = -1;
         threadServer enemigo;
         while(true) { 
             try {
                //Ciclo que espera a salir de la primer ventana
                opcion = entrada.readInt();
                System.out.println("Servidor recibió opción: " + opcion);
                switch(opcion) {
                    case 1-> { //Recibir un color que se marcó
                        String color = entrada.readUTF(); //recibe el color
                        enemigos = servidor.getThreads();
                        servidor.frame.mostrar(this.nameUser + " chose " + color);
                        for (int i = 0; i < enemigos.size(); i++) { //Ciclo que manda los colores al enemigo
                           enemigo = enemigos.get(i);
                           enemigo.salida.writeInt(2);
                           enemigo.salida.writeUTF(color);
                        }
                    }
                   case 2->{ //Salir de ventana de colores
                        int randomNumber = random.nextInt(1000) + 1;
                        enemigosSize = servidor.getThreads().size();
                        indicador = 0;
                        servidor.frame.mostrar("Getting out of color picker");
                        for (int i = 0; i < enemigosSize; i++) {
                           threadServer ts = servidor.getThreads().get(i);
                           ts.salida.writeInt(3);
                           ts.salida.writeInt(indicador);
                           if(indicador == 1) {
                               ts.salida.writeInt(randomNumber);
                            }
                        }
                   }  
                   case 4-> { //Salir de ventana inicial
                        enemigosSize = servidor.getThreads().size();
                        for(int i = 0; i < enemigosSize; i++) {
                            threadServer ts = servidor.getThreads().get(i);
                            ts.salida.writeInt(2);
                        }
                    }
                    case 5-> { //Salir de la ventana de sorteo
                        indicador = entrada.readInt();
                        enemigosSize = servidor.getThreads().size();
                        servidor.frame.mostrar("Getting out of select order window");
                        for(int i = 0; i < enemigosSize; i++) {
                            threadServer ts = servidor.getThreads().get(i);
                            ts.salida.writeInt(1);
                            for (int j = 0; j < 26; j++){
                                System.out.print(servidor.tiposCasilla.get(j) + " ");
                                ts.salida.writeInt(servidor.tiposCasilla.get(j));
                            }
                            
                            System.out.println("");
                        }
                        if(indicador == 1) {
                            for(int j = 0; j < enemigos.size(); j++) {
                                int resultado = entrada.readInt();
                                for(int i = 0; i < enemigos.size(); i++) {
                                    threadServer ts = servidor.getThreads().get(i);
                                    System.out.println("Server de jugador" + this.numeroDeJugador + " recibió " + resultado);
                                    ts.salida.writeInt(resultado);
                                }
                                 }
                            }
                    }
                    case 6-> { //Movimiento de jugador en tablero
                        enemigosSize = servidor.getThreads().size();
                        int numCasilla = entrada.readInt();
                        String estadoDados = entrada.readUTF();
                        servidor.frame.mostrar(this.nameUser + " moves to position " + numCasilla);
                        for(int i = 0; i < enemigosSize; i++) {
                            threadServer ts = servidor.getThreads().get(i);
                            ts.salida.writeInt(1); // se va a siguient while de cliente...
                            ts.salida.writeInt(numCasilla); // posicion donde se a a mover.
                            ts.salida.writeUTF(estadoDados); // posicion donde se a a mover.
                        }
                    }
                    
                    case 7 -> { //Mandar resultado de sorteoFrame()
                     int resultado = entrada.readInt();
                     enemigosSize = servidor.getThreads().size();
                     for(int i = 0; i < enemigosSize; i++) {
                         threadServer ts = servidor.getThreads().get(i);
                         ts.salida.writeInt(2);
                         ts.salida.writeInt(resultado);
                         ts.salida.writeUTF(nameUser);
                     }
                    }
                    
                    case 8 -> { //Mandar carta marcada en mario cards
                        int resultado = entrada.readInt(); //Recibe el resultado
                        for(int i = 0; i < enemigosSize; i++) {
                            threadServer ts = servidor.getThreads().get(i);
                            ts.salida.writeInt(3); // Envía instrucción a cliente
                            ts.salida.writeInt(resultado); // envía resultado
                        }
                }
                    case 9 -> { //Mandar a ejecutar MarioCards
                        LinkedList<Integer> posiblesValores = new LinkedList();
                        Random random = new Random();
                        for(int i = 0; i < 52; i++) {
                            posiblesValores.add(random.nextInt(posiblesValores.size() + 1), i);
                         }
                        for(int i = 0; i < enemigosSize; i++) {
                            threadServer ts = servidor.getThreads().get(i);
                            ts.salida.writeInt(4); // Envía instrucción a cliente
                            ts.salida.writeInt(this.numeroDeJugador);
                            for(int j = 0; j < 52; j++) {
                                ts.salida.writeInt(posiblesValores.get(j));
                         }
                        }
                    }
                    
                    case 10 -> { //Indica que el main player ganó en el juego de MarioCards o gato
                        this.salida.writeInt(5);
                }
                    case 11 -> { //Indica que el main player perdió en el juego de MarioCards o gato
                        this.salida.writeInt(6);
                    }
                    case 12 -> {//Enviar una casilla marcada en Gato
                        int columna = entrada.readInt();
                        int fila = entrada.readInt();
                        this.enemigoGato.salida.writeInt(8);
                        this.enemigoGato.salida.writeInt(columna);
                        this.enemigoGato.salida.writeInt(fila);
                    }
                    case 13-> { //Inicia una partida de gato; 
                        while(true) { //Se elije al enemigo de manera al azar
                            enemigoGato = enemigos.get(random.nextInt(enemigos.size()));
                            if(enemigoGato.nameUser != this.nameUser) //Verifica que no se haya elegido al mismo usuario
                                break;
                        }
                        enemigoGato.enemigoGato = this;
                        this.salida.writeInt(7);
                        enemigoGato.salida.writeInt(7);
                        this.salida.writeUTF(enemigoGato.nameUser);
                        enemigoGato.salida.writeUTF(nameUser);
                        this.salida.writeInt(this.numeroDeJugador);
                        enemigoGato.salida.writeInt(this.numeroDeJugador);
                        this.salida.writeInt(enemigoGato.numeroDeJugador);
                        enemigoGato.salida.writeInt(enemigoGato.numeroDeJugador);
                        
                    }
                    
                    case 14 -> { //recibe un número de StartAdivinar()
                        int numero = entrada.readInt();
                        for(int i = 0; i< enemigos.size(); i++) {
                            threadServer ts = servidor.getThreads().get(i);
                            ts.salida.writeInt(3);
                            ts.salida.writeInt(numero);
                        }
                    }
                    
                    case 20-> { //Inicia una partida de memory; 
                        while(true) { //Se elije al enemigo de manera al azar
                            enemigoMemory = enemigos.get(random.nextInt(enemigos.size()));
                            if(enemigoMemory.nameUser != this.nameUser) //Verifica que no se haya elegido al mismo usuario
                                break;
                        }
                        
                        enemigoMemory.enemigoMemory = this;
                        this.salida.writeInt(20);
                        enemigoMemory.salida.writeInt(20);
                        this.salida.writeUTF(enemigoMemory.nameUser);
                        enemigoMemory.salida.writeUTF(nameUser);
                        this.salida.writeInt(this.numeroDeJugador);
                        enemigoMemory.salida.writeInt(this.numeroDeJugador);
                        this.salida.writeInt(enemigoMemory.numeroDeJugador);
                        enemigoMemory.salida.writeInt(enemigoMemory.numeroDeJugador);
                        ArrayList<Integer> preparaPares = new ArrayList<>();
                        ArrayList<Integer> pares = new ArrayList<>();
                        for (int i = 0; i < 18; i++){
                            preparaPares.add(i % 9);
                        }
                        for (int i = 0; i < 18; i++){
                            pares.add(preparaPares.remove(new Random().nextInt(preparaPares.size())));
                        }
                        for (int i = 0; i < 18; i++){
                            this.salida.writeInt(pares.get(i));
                            enemigoMemory.salida.writeInt(pares.get(i));
                        }
                        
                    }
                    case 21 -> {//Enviar pares marcados en memory
                        int columna = entrada.readInt();
                        int fila = entrada.readInt();
                        this.enemigoMemory.salida.writeInt(21);
                        this.enemigoMemory.salida.writeInt(columna);
                        this.enemigoMemory.salida.writeInt(fila);
                    }
                    case 98->{
                        int numCasilla = entrada.readInt();
                        String estadoDados = entrada.readUTF();
                        servidor.frame.mostrar(this.nameUser + " moves to position " + numCasilla);
                        for(int i = 0; i < enemigosSize; i++) {
                            threadServer ts = servidor.getThreads().get(i);
                            ts.salida.writeInt(98); // se va a siguient while de cliente...
                            ts.salida.writeInt(numCasilla); // posicion donde se a a mover.
                            ts.salida.writeUTF(estadoDados); // posicion donde se a a mover.
                        }
                    }
                    case 99-> { // actualiza turno
                        for(int i = 0; i < enemigosSize; i++) {
                            threadServer ts = servidor.getThreads().get(i);
                            ts.salida.writeInt(2);
                        }
                        servidor.frame.mostrar("Actualizó el turno");
                    }
                    case 100-> {
                        System.out.println("PORQUE PASA A");
                        enemigosSize = servidor.getThreads().size();
                        int excluye = entrada.readInt();
                        int perjudicado = excluye;
                        while (perjudicado == excluye){
                            perjudicado = new Random().nextInt(enemigosSize);
                        }
                        int tipoCastigo = entrada.readInt();
                        servidor.getThreads().get(perjudicado).salida.writeInt(100);
                        servidor.getThreads().get(perjudicado).salida.writeInt(tipoCastigo);
                        
                    }
                    case 101->{
                        //sets index of player globally
                        servidor.frame.mostrar("FLOR DE FUEGO ACTUALIZANDO AL INICIO");
                        int jugador = entrada.readInt();
                        int pos = entrada.readInt();
                        for(int i = 0; i < enemigosSize; i++) {
                            threadServer ts = servidor.getThreads().get(i);
                            ts.salida.writeInt(101); // se va a siguient while de cliente...
                            ts.salida.writeInt(jugador); // posicion donde se a a mover.
                            ts.salida.writeInt(pos); // posicion donde se a a mover.
                        }
                    }
                }
             } catch (IOException ex) {
                 System.out.println("Cliente desconectado");
                 servidor.frame.mostrar("Cliente desconectado");
                 break;
             }
         }
     }
}


