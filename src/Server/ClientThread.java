/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Miscellaneous.SorteoFrame;
import Miscellaneous.StartAdivinar;
import Progra.Gato;
import Progra.SuperBrosMemory;
import java.awt.Color;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public class ClientThread extends Thread {
    DataInputStream entrada;
    prograMario ventanaInicial;
    StartFrame ventanaColores;
    Client cliente;
    String nick;
    Color color;
    LinkedList<String> enemigos;
    LinkedList<String> colores;
    LinkedList<Integer> tipoCasillas;
    SorteoFrame ventanaSorteo;
    Tablero.Tablero tablero;
    int numJugadores;
    Progra.MarioCards marioCards;
    boolean lastPlayer = false; //Indica si el jugador es el último, para la elección de colores. 
    int num; //Guarda el número de jugador
    Gato gato;
    SuperBrosMemory memory;
    StartAdivinar ventanaSorteo2;
    int contadorSorteo;
    int indicadorSorteo;
    public ClientThread(DataInputStream entrada, prograMario ventanaInicial, Client cliente) throws IOException
   {
      this.entrada=entrada;
      this.ventanaInicial=ventanaInicial;
      this.cliente = cliente;
      colores = new LinkedList();
   }
    
    public void run() {
        //ventanaInicial.setVisible(true);
        boolean romper = false;
        enemigos = new LinkedList();
        tipoCasillas = new LinkedList();
        //////Ciclo del menú de bienvenida///////
        while(true) {
            if(romper)
                break;
            try {
                int opcion;
                opcion = entrada.readInt();
                System.out.println("Cliente leyó opción: " + opcion);
                switch(opcion) {
                    case 1:{ //Recibir un enemigo 
                        String enemigo = entrada.readUTF();
                        int n = entrada.readInt(); //Lee el número de jugador
                        enemigos.add(enemigo);
                        ventanaInicial.mostrar(n + ". " + enemigo);
                        ventanaInicial.sumarJugador();
                        System.out.println(opcion);
                        break;
                    }
                        //Actualizar la ventana de inicio con los datos del jugador. 
                    case 2: {
                        System.out.println("Entrando en opción 2" + " - "+ opcion);
                        if(this.num == ventanaInicial.getJugadores())
                            lastPlayer = true;
                        ventanaInicial.setVisible(false);
                        ventanaColores = new StartFrame();
                        ventanaColores.setVisible(true);
                        ventanaColores.setCliente(cliente);
                        ventanaColores.setNumJugador(this.num);
                        ventanaColores.setNick(nick);
                        ventanaColores.setLastPlayer(lastPlayer);
                    }
                        //iniciar la elección de colores
                        romper = true;
                        break;
                    case 3: //recibir el nombre del cliente
                        nick = entrada.readUTF();
                        enemigos.add(nick);
                        num = entrada.readInt(); //Lee el número de jugador
                        ventanaInicial.mostrar(num + ". " + nick);
                        ventanaInicial.setNumJugador(num);
                        ventanaInicial.sumarJugador();
                        break;
                }
            } catch (IOException ex) {
                Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        //Ciclo del menú inicial
        romper = false;
        while(true) {
            if(romper) {
                break;
            }
           int opcion;
           try {
                opcion = entrada.readInt(); //Se queda esperando hasta leer un número
                switch(opcion) {
                    case 1: //Recibir el nombre de un enemigo. 
                        boolean avanzar = true;
                        String enemigo = entrada.readUTF(); //Recibe el nick desde el server
                        for(int i = 0; i < enemigos.size(); i++) { //Revisa que el nombre no esté en la lista, para evitar errores
                            if(enemigos.get(i) == enemigo)
                                avanzar = false;
                        }
                        if(avanzar) {
                            enemigos.add(enemigo); //añade el enemigo
                            //ventanaInicial.actualizarJugadores(); //Actualiza los jugadores
                        }
                        break;
                    case 2: //recibe un color
                        String colorRecibido = entrada.readUTF(); //lee el Color.color.toString()
                        colores.add(colorRecibido);
                        ventanaColores.recibirColor(colorRecibido);
                        //ventanaInicial.actualizarJugadores();
                        break;
                    case 3: //Pasar al sorteo
                        indicadorSorteo = entrada.readInt();
                        color = ventanaColores.getColor();
//                        System.out.println("PASA A SORTEO " + color);
                        if(indicadorSorteo == 0) {
                        ventanaColores.setVisible(false);
                        ventanaSorteo = new SorteoFrame();
                        ventanaSorteo.setVisible(true);
                        ventanaSorteo.setCliente(this.cliente);
                        ventanaSorteo.setNumJugador(this.num);
                        ventanaSorteo.setNick(this.nick);
                        ventanaSorteo.setTotalJugadores(this.enemigos.size()); }
                        else {
                            int randomNumber = entrada.readInt();
                            ventanaColores.setVisible(false);
                            ventanaSorteo2 = new StartAdivinar();
                            ventanaSorteo2.setCliente(cliente);
                            ventanaSorteo2.setRandomNumber(randomNumber);
                            ventanaSorteo2.setTotalPlayers(enemigos.size());
                            ventanaSorteo2.setNick(nick);
                            ventanaSorteo2.setVisible(true);
                            ventanaSorteo2.setLocalPlayerNumber(this.num);
                            
                        }
                        romper = true;
                        break;
                        
           }
            } catch (IOException ex) {
                Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        }
        romper = false;
        LinkedList<Integer> resultadosSorteo = new LinkedList();
        int index;
        boolean repetido;
        while(true) {
            if(romper)
                break;
            try {
                int opcion = entrada.readInt(); //Se queda esperando hasta leer un número
                switch(opcion) {
                    
                        case 1: //Pasa al tablero
                        LinkedList<Integer> resultadosOrdenados = new LinkedList();
                        LinkedList<String> jugadoresOrdenados = new LinkedList();
                        LinkedList<String> coloresOrdenados = new LinkedList();
                        repetido = false;
                        contadorSorteo = 0;
                        
                        tablero = new Tablero.Tablero();
                        tablero.setNick(this.nick);
                        for (int i = 0; i < 26; i++){
                            tipoCasillas.add(entrada.readInt());
                        }
                        if(indicadorSorteo == 1) {
                            
                            for(int i = 0; i < enemigos.size(); i++) {
                                int resultado = entrada.readInt();
                                resultadosOrdenados.add(resultado);
                                System.out.println("Se añadió " + resultado + " a la lista ordenada");
                            }
                            for(int i = 0; i < resultadosSorteo.size(); i++) {
                                index = resultadosSorteo.indexOf(resultadosOrdenados.get(i));
                                System.out.println("Index: " + index);
                                jugadoresOrdenados.add(enemigos.get(index));
                                System.out.println("Jugador añadido a la lista de ordenados: " + jugadoresOrdenados.get(i));
                                coloresOrdenados.add(colores.get(index));
                            }
                            ventanaSorteo2.setVisible(false);
                        }
                        else {
                        ventanaSorteo.setVisible(false);
//                        System.out.println("Leyó valores lista.");
                        LinkedList<Integer> resultadosCopy = new LinkedList();
                        LinkedList<Integer> resultadosCopy2 = new LinkedList();
                        for(int i = 0; i < resultadosSorteo.size(); i++) {
                            resultadosCopy.addLast(resultadosSorteo.get(i));
                            resultadosCopy2.addLast(resultadosSorteo.get(i));
                        }
                        
                        int tamaño = resultadosSorteo.size();
                        for(int i=0; i < tamaño; i++) {
                            int max = resultadosSorteo.get(0);
                            for(int j = 0; j < resultadosSorteo.size(); j++) {
                                if(resultadosSorteo.get(j) > max)
                                    max = resultadosSorteo.get(j);
                            }
                            System.out.println("Máximo: " + max);
                            index = resultadosCopy2.indexOf(max);
                            System.out.println("Index: " + index);
                            for(int j = 0; j < resultadosOrdenados.size(); j++) {
                                if(resultadosOrdenados.get(j) == max) {
                                    resultadosCopy.remove(resultadosCopy.indexOf(max));
                                    contadorSorteo++;
                                    repetido = true;
                                    
                                }
                            }
                            if(repetido) {
                                index = resultadosCopy.indexOf(max)+ contadorSorteo;
                                resultadosCopy = new LinkedList();
                                for(int j = 0; j < resultadosCopy2.size(); j++) {
                                    resultadosCopy.addLast(resultadosCopy2.get(i));
                        }
                            }
                            
                            repetido = false;
                            jugadoresOrdenados.add(enemigos.get(index));
                            System.out.println("Jugador añadido a la lista de ordenados: " + jugadoresOrdenados.get(i));
                            resultadosOrdenados.add(max);
                            coloresOrdenados.add(colores.get(index));
                            index = resultadosSorteo.indexOf(max);
                            resultadosSorteo.remove(index);
                            contadorSorteo = 0;
                        }
                        }
                        tablero.localPlayerNumber = this.num;
                        tablero.setTipoCasillas(tipoCasillas);
//                        System.out.println("Se seteó la lista");
                        tablero.setVisible(true);
                        tablero.setCliente(this.cliente);
                        tablero.initPlayers(enemigos.size(), coloresOrdenados, jugadoresOrdenados, enemigos);
                        romper = true;
                        break;
                    case 2: //Recibe un resultado en SorteoFrame()
                        int resultado = entrada.readInt();
                        String nombre = entrada.readUTF();
                        resultadosSorteo.add(resultado);
                        ventanaSorteo.recibirResultado(resultado, nombre);
                        break;
                     case 3: //Recibir un resultado en StartAdivinar
                        int res = entrada.readInt();
                        System.out.println("Se añadió " + res + " a resultadosSorteo");
                        resultadosSorteo.add(res);
                        ventanaSorteo2.recibirResultado(res);
                        break;
                }
            } catch (IOException ex) {
                Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        romper = false;
        while(true) {
            if(romper)
                break;
            try {
                int opcion = entrada.readInt(); //Se queda esperando hasta leer un número
//                System.out.println(opcion);
                switch(opcion) {
                    case 1: //Moverse de posicion
                        int numCasilla = entrada.readInt();
                        String estadoDados = entrada.readUTF();
//                        System.out.println("LEE NUMERO CASILLA: "+ numCasilla);
                        tablero.setTxfDados(estadoDados);
                        tablero.movePlayer(numCasilla);
                        break;
                    case 2: // actualiza turno 
                        tablero.updateTurno();
                        break;
                    case 4: //Crea juego de cartas
                        marioCards = new Progra.MarioCards();
                        int mainPlayer = entrada.readInt();
                        marioCards.setCliente(cliente);
                        marioCards.setLocalPlayer(num);
                        marioCards.setTitle(nick);
                        marioCards.setMainPlayer(mainPlayer);
                        Random random = new Random();
                        marioCards.setTotalPlayers(enemigos.size());
                        LinkedList<Integer> posiblesValores = new LinkedList();
                        for(int i = 0; i < 52; i++) {
                            posiblesValores.add(entrada.readInt());
                         }
                        marioCards.setPosiblesValores(posiblesValores);
                        marioCards.setVisible(true);
                        break;
                        
                    case 3: //recibir resultado de carta
                        int resultado = entrada.readInt();
                        marioCards.recibirResultado(resultado);
                        break;
                        
                    case 5:
                        tablero.gameEnded(1);
                        break;
                    
                    case 6:
                        tablero.gameEnded(0);
                        break;
                    case 7: //Nuevo gato
                        String enemigoGato = entrada.readUTF();
                        int mainPlayerGato = entrada.readInt();
                        int notMainPlayer = entrada.readInt();
                        gato = new Gato();
                        gato.setCliente(cliente);
                        gato.setLocalPlayerNumber(this.num);
                        gato.setMainPlayer(mainPlayerGato);
                        gato.setEnemigo(enemigoGato);
                        gato.setNick(nick);
                        gato.setNotMainplayer(notMainPlayer);
                        gato.setVisible(true);
                        break;
                        
                    case 8: //Marcar casilla en gato
                        int columna = entrada.readInt();
                        int fila = entrada.readInt();
                        gato.marcar(columna, fila);
                        break;
                    
                    case 20:
                        //Nuevo SB memory
                        String enemigoMemory = entrada.readUTF();
                        int mainPlayerMemory = entrada.readInt();
                        int notMainPlayerMemory = entrada.readInt();
                        memory = new SuperBrosMemory();
                        memory.setCliente(cliente);
                        memory.setLocalPlayerNumber(this.num);
                        memory.setMainPlayer(mainPlayerMemory);
                        memory.setEnemigo(enemigoMemory);
                        memory.setNick(nick);
                        memory.setNotMainplayer(notMainPlayerMemory);
                        ArrayList<Integer> pares = new ArrayList<>();
                        for(int i = 0; i < 18; i++) {
                            pares.add(entrada.readInt());
                         }
                        memory.setPares(pares);
                        memory.setVisible(true);
                        break;
                    case 21: //Marcar casilla en gato
                        int c1 = entrada.readInt();
                        int f1 = entrada.readInt();
                        memory.marcar(c1, f1);
                        
                        break;   
                        
                    case 98:
                        int numTubo = entrada.readInt();
                        String nulo = entrada.readUTF();
//                        System.out.println("LEE NUMERO CASILLA: "+ numTubo);
                        tablero.setTxfDados("");
                        tablero.moverTubo(numTubo);
                        break;
                    case 100:
                        int tipoCastigo = entrada.readInt();
                        tablero.recibirCastigo(tipoCastigo);
                        break;
                    case 101:
                        int jugador = entrada.readInt();
                        int pos = entrada.readInt();
                        tablero.moveSelectedPlayer(jugador, pos);
                        break;
                }
            } catch (IOException ex) {
                Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
