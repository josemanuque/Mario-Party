/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tablero;

import Server.Client;
import Server.StartFrame;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author diemo
 */
public class Tablero extends javax.swing.JFrame {
    public static final int BUTTON_SIZE = 120;
    public static final int BOARD_SIZE = 28;
    public static final int PLAYER_HEIGH = 20;
    public static final int PLAYER_WIDTH = 40;
    
    private Color colorArray[] = {new Color(220,20,60), new Color(138,43,226), Color.BLUE, Color.GREEN, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.CYAN, Color.DARK_GRAY, new Color(85, 107, 47)};
    private JButton[] buttonArray= new JButton[BOARD_SIZE];
    private ArrayList<Jugador> playersArray = new ArrayList<Jugador>();
    private LinkedList<Integer> tipoCasillas;
    private Casilla[] casillas = new Casilla[BOARD_SIZE];
    public int localPlayerNumber;
    public Color localPlayerColor;
    private int turno = 1;
    private Server.Client cliente;
    private String nick;
    public int castigo = 0; 
    public boolean perdioPrevio = false;
    LinkedList<String> playersInOrder;
    LinkedList<String> originalPlayers;
    int contador = 0;
    
    /**
     * Creates new form Tablero
     */
    public Tablero() {
        initComponents();
        originalPlayers = new LinkedList();
        playersInOrder = new LinkedList();
        btnDados.setEnabled(false);
//        initPossibleContents();
//        initBoard();
    }
    
    public void setCliente(Client cliente) {
        this.cliente = cliente;
    }

    public void setTipoCasillas(LinkedList<Integer> tipoCasillas) {
        this.tipoCasillas = tipoCasillas;
    }
    
    
    
    public void initPlayers(int playerQty, LinkedList<String> colors, LinkedList<String> players, LinkedList<String> originalPlayers){
        System.out.println("Jugadores generados; " + playerQty);
        //buttonArray[0].setLocation(100, 0);
        System.out.println("NUMERO DE JUGADOR LOCAL "+ localPlayerNumber);
        this.originalPlayers = originalPlayers;
        this.playersInOrder = players;
        turno = originalPlayers.indexOf(playersInOrder.get(contador)) + 1;
        lblTurno.setText("Turno " + this.turno + "(" + originalPlayers.get(turno - 1) + ")");
        for (int i = 0; i < playerQty; i++) {
            for(int j = 0; j < 10; j++) {
                if(colors.get(i).equals(colorArray[j].toString())) {
                    System.out.println("creando ficha");
                    System.out.println("Jugador " + players.get(i) + " eligió: " + colorArray[j].toString());
                    JButton newButton = new JButton((originalPlayers.indexOf(players.get(i))) + 1 + "");
                    newButton.setBackground(colorArray[j]);
                    jPanel1.add(newButton);
                    newButton.setVisible(true);
                    newButton.setBounds(0, i*PLAYER_HEIGH,PLAYER_WIDTH, PLAYER_HEIGH);
                    playersArray.add(new Jugador(0, "Jugador "+(i + 1), newButton, i*PLAYER_HEIGH));
                }
            }
        } 
        if (localPlayerNumber == turno){
            btnDados.setEnabled(true);
        }
        initPossibleContents();
        initBoard();
    }
    
    public void setLocalPlayer(int number, Color color){
        this.localPlayerNumber = number;
        this.localPlayerColor = color;
        
    }
    private void initBoard(){
        buttonArray[0] = new JButton("Start");
        jPanel1.add(buttonArray[0]);
        buttonArray[0].setBounds(0, 0, BUTTON_SIZE, BUTTON_SIZE);
        for (int i = 1; i < buttonArray.length - 1; i++) {
            String label = (i) + "\n" + casillas[i].nombreCasilla();
            buttonArray[i] = new JButton();
            buttonArray[i].setText((String)("<html>" + label.replaceAll("\\n", "<br>") + "</html>"));
            jPanel1.add(buttonArray[i]);
            buttonArray[i].setActionCommand(i + "");
            
            if (i <=8)
                buttonArray[i].setBounds(i*BUTTON_SIZE, 0, BUTTON_SIZE, BUTTON_SIZE);
            else if (i >= 9 && i<=13){
                buttonArray[i].setBounds(8 * BUTTON_SIZE, (i-8) * BUTTON_SIZE, BUTTON_SIZE, BUTTON_SIZE);
            }
            else if (i >= 14 && i<=21){
                buttonArray[i].setBounds(840-(i-15)*BUTTON_SIZE, 6 * BUTTON_SIZE, BUTTON_SIZE, BUTTON_SIZE);
            }
            else if (i == 26){
                buttonArray[i].setBounds(2 * BUTTON_SIZE, 2 * BUTTON_SIZE, BUTTON_SIZE, BUTTON_SIZE);
            }
            else{
                buttonArray[i].setBounds(BUTTON_SIZE,600-(i-22)*BUTTON_SIZE, BUTTON_SIZE, BUTTON_SIZE);
            }
            buttonArray[i].addActionListener(new ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    casillaPressed(evt);
                }
                });
            //System.out.println((i+1) + ".\t " + buttonArray[i].getBounds().x + "," + buttonArray[i].getBounds().y );
        }

        buttonArray[27] = new JButton("Finish");
        jPanel1.add(buttonArray[27]);
        buttonArray[27].setBounds(3 * BUTTON_SIZE, 2 * BUTTON_SIZE, BUTTON_SIZE, BUTTON_SIZE);
        
        
    }
    
    public void casillaPressed(java.awt.event.ActionEvent evt){
        //System.out.println("LCL PLYR" + localPlayerNumber);
////        System.out.println(localPlayerNumber == turno);
////        System.out.println(casillas[playersArray.get(localPlayerNumber - 1).getIndex()].getTipo() == 19);
        if (localPlayerNumber != turno)
            return;
        
        if(casillas[playersArray.get(contador).getIndex()].getTipo() == 19){
            // cola
            JButton botonTemp = (JButton)evt.getSource();
        
            int index = 
              Integer.parseInt(botonTemp.getActionCommand());
            
            int playerPos = playersArray.get(contador).getIndex();
            if (index < playerPos - 3 || index > playerPos + 3)
                return;
            if (playerPos == index)
                return;
            playersArray.get(contador).setIndex(index);
            try {
                cliente.salida.writeInt(6);
                cliente.salida.writeInt(index);
                cliente.salida.writeUTF("");
                //cliente.salida.writeInt(99);
            } catch (IOException ex) {
                
            }
            
        }
        
    }
    
    private void initPossibleContents(){
        
        int size = tipoCasillas.size();
        for (int i = 1; i < size + 1; i++){
            casillas[i] = new Casilla(this);
            casillas[i].setTipo(tipoCasillas.get(i - 1));
            casillas[i].setCliente(this.cliente);
        }
        
        casillas[0] = new Casilla(this);
        casillas[27] = new Casilla(this);
        
        casillas[0].setTipo(0);
        casillas[27].setTipo(0);
        
        
    }
    
    

    
    
    private int generarDados(){
        int dado1 = (new Random()).nextInt(6) + 1;
        int dado2 = (new Random()).nextInt(6) + 1;
        //cliente.salida.writeInt(1);
        return dado1+dado2;
    }
    
    
    private void nextPlayer(){
        contador++;
        if(contador >playersArray.size() - 1)
            contador = 0;     
        turno = originalPlayers.indexOf(playersInOrder.get(contador)) + 1;      
    }
    
    public void updateTurno(){
        if (this.localPlayerNumber == turno && castigo > 0){
            btnDados.setEnabled(false);
            castigo--;
        }
        nextPlayer();
        lblTurno.setText("Turno " + this.turno + "(" + originalPlayers.get(turno - 1) + ")");
////        System.out.println("CASTIGO " + castigo);
        if (this.localPlayerNumber == turno)
            btnDados.setEnabled(true);
////        else
////            castigo--;
    }

    public void setTxfDados(String estadoDados) {
        this.txfDados.setText(estadoDados);
    }
    
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnDados = new javax.swing.JButton();
        txfDados = new javax.swing.JTextField();
        lblTurno = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1172, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 895, Short.MAX_VALUE)
        );

        btnDados.setText("Dados");
        btnDados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDadosActionPerformed(evt);
            }
        });

        txfDados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfDadosActionPerformed(evt);
            }
        });

        lblTurno.setText("Turno 1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(133, 133, 133)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnDados)
                            .addComponent(lblTurno))
                        .addContainerGap(43, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txfDados, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addComponent(btnDados)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txfDados, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(lblTurno)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDadosActionPerformed
        
        if (castigo > 0){ // SI YA VENIA CATIGADO NADA MAS CAMBIA DE TURNO
            btnDados.setEnabled(false); 
            System.out.println("No debería correrlo");
            try {
                cliente.salida.writeInt(99);
            } catch (IOException ex) {
                
            }
            return;
        }
        
        
        int dado1 = 1;//(new Random()).nextInt(6) + 1;
        int dado2 = 0;//(new Random()).nextInt(6) + 1;
        
        
        int valorDados = dado1 + dado2;

        
        this.txfDados.setText(dado1 + " + " + dado2 + " = " + valorDados +"");
        
        
        int posTablero = this.calcularPosTabla(valorDados);
        
        
        if (dado1 == 6 && dado2 == 6){
            this.txfDados.setText("castigo" + " + " + "castigo" + " = " + "pierde 2 turnos");
            castigo = 2;
        }
        else if (dado1 == 6){
            this.txfDados.setText("castigo" + " + " + dado2 + " = " + "pierde este turno" +"");
            castigo = 1;
        }
        else if (dado2 == 6){
            this.txfDados.setText(dado1 + " + " + "castigo" + " = " + "pierde este turno" +"");
            castigo = 1;
        }
        
       if (perdioPrevio){
            System.out.println("SI PERDIO PREVIAMENTE");
            posTablero = this.calcularPosTabla(-valorDados); 
        }
        
        if (castigo > 0){ // SI ACABA DE SER CASTIGADO RESETEA A POS VIEJA Y CAMBIA TURNO
            btnDados.setEnabled(false);
            posTablero = this.calcularPosTabla(-valorDados); 
            System.out.println("Acaba de ser castigado, no se va a mover");
            try {
                cliente.salida.writeInt(99);
            } catch (IOException ex) {
                
            }
            return;
        }
        
        

        try {
////            System.out.println("Tablero envia pos");
            cliente.salida.writeInt(6);
            cliente.salida.writeInt(posTablero);
            cliente.salida.writeUTF(this.txfDados.getText());
            btnDados.setEnabled(false);
//            this.updateTurno();
        } catch (IOException ex) {
            Logger.getLogger(Tablero.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_btnDadosActionPerformed

    private void txfDadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfDadosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txfDadosActionPerformed

    
    public int calcularPosTabla(int valorAvance){
        int posTablero = playersArray.get(contador).getIndex();
        
        System.out.println("PosTABLERO: " + posTablero);
        playersArray.get(contador).setIndex(posTablero + valorAvance);
//        System.out.println(playersArray.get(turno-1).getIndex());
        posTablero = playersArray.get(contador).getIndex();
        
        
        return posTablero;
    }
    
    public void movePlayer(int posTablero){
        
//        if (castigo > 0){
//            System.out.println("ESTA CASTIGADO");
//            btnDados.setEnabled(false);
//            castigo--;
//        }
        
        JButton currentButton = playersArray.get(contador).getRefButton();
        if (posTablero > 27){
            int regresa = posTablero - 27;
            playersArray.get(contador).setIndex(27 - regresa);
            movePlayer(playersArray.get(contador).getIndex());
            return;
        }
//        System.out.println(posTablero);
        if (posTablero > 8 && posTablero <= 13){
            currentButton.setLocation(8 * BUTTON_SIZE, playersArray.get(contador).getyOffset() + ((posTablero - 8) * BUTTON_SIZE));
        }
        if (posTablero > 13 && posTablero <= 20){
            currentButton.setLocation(((8 - (posTablero - 14)) * BUTTON_SIZE), 6 * BUTTON_SIZE + playersArray.get(contador).getyOffset());
            //currentButton.setLocation((7 - (playersArray.get(turno-1).getIndex() - 13)) * BUTTON_SIZE, currentButton.getLocation().y + (6 * BUTTON_SIZE));
        }
        else if (posTablero > 20 && posTablero < 26){

            currentButton.setLocation(BUTTON_SIZE, ((7 - (posTablero - 20)) * BUTTON_SIZE) + playersArray.get(contador).getyOffset());
        }
        else if (posTablero == 26){
            currentButton.setLocation(2 * BUTTON_SIZE, 2 * BUTTON_SIZE + playersArray.get(contador).getyOffset());
        }
        else if (posTablero <= 8){
            currentButton.setLocation(posTablero * BUTTON_SIZE, playersArray.get(contador).getyOffset());
        }
        if (posTablero == 27){
            currentButton.setLocation(3 * BUTTON_SIZE, 2 * BUTTON_SIZE + playersArray.get(contador).getyOffset());
            String message = "El jugador " + turno + " ha ganado ";
            JOptionPane.showMessageDialog(this, message); 
        }
        
        if (localPlayerNumber == turno){
            casillas[posTablero].run();
            if ((casillas[posTablero].getTipo() != 21 && casillas[posTablero].getTipo() != 19) || castigo > 0){ // estrella && cola || castigado
//                try {
////////                    System.out.println("UPDATES______________________________________________");
                    System.out.println("TIPO" + casillas[posTablero].getTipo());
////                    cliente.salida.writeInt(99);
//                } catch (IOException ex) {
//                }
            }
            if (casillas[posTablero].getTipo() == 20 ||
                    casillas[posTablero].getTipo() == 24 || 
                    casillas[posTablero].getTipo() == 25 || 
                    casillas[posTablero].getTipo() == 26){
                try {
                    cliente.salida.writeInt(99);
                } catch (IOException ex) {
                    Logger.getLogger(Tablero.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (casillas[posTablero].getTipo() != 19){
            
        }
    }
    
    
    public void moverTubo(int posTablero){
        
        JButton currentButton = playersArray.get(contador).getRefButton();
        if (posTablero > 27){
            int regresa = posTablero - 27;
            playersArray.get(contador).setIndex(27 - regresa);
            movePlayer(playersArray.get(contador).getIndex());
            return;
        }
//        System.out.println(posTablero);
        if (posTablero > 8 && posTablero <= 13){
            currentButton.setLocation(8 * BUTTON_SIZE, playersArray.get(contador).getyOffset() + ((posTablero - 8) * BUTTON_SIZE));
        }
        if (posTablero > 13 && posTablero <= 20){
            currentButton.setLocation(((8 - (posTablero - 14)) * BUTTON_SIZE), 6 * BUTTON_SIZE + playersArray.get(contador).getyOffset());
            //currentButton.setLocation((7 - (playersArray.get(turno-1).getIndex() - 13)) * BUTTON_SIZE, currentButton.getLocation().y + (6 * BUTTON_SIZE));
        }
        else if (posTablero > 20 && posTablero < 26){

            currentButton.setLocation(BUTTON_SIZE, ((7 - (posTablero - 20)) * BUTTON_SIZE) + playersArray.get(contador).getyOffset());
        }
        else if (posTablero == 26){
            currentButton.setLocation(2 * BUTTON_SIZE, 2 * BUTTON_SIZE + playersArray.get(contador).getyOffset());
        }
        else if (posTablero <= 8){
            currentButton.setLocation(posTablero * BUTTON_SIZE, playersArray.get(contador).getyOffset());
        }
        if (posTablero == 27){
            currentButton.setLocation(3 * BUTTON_SIZE, 2 * BUTTON_SIZE + playersArray.get(contador).getyOffset());
            String message = "El jugador " + localPlayerNumber + "Ha ganado ";
            JOptionPane.showMessageDialog(this, message); 
        }
        
    }
    public void mandarMoverseTubo(int tubo){
//        System.out.println("NUMERO TUBO " + tubo);
        int posTablero = tipoCasillas.indexOf(tubo) + 1;
        playersArray.get(contador).setIndex(posTablero);
//        System.out.println("INDICE TUBO " + posTablero);
        try {
            cliente.salida.writeInt(98);
            cliente.salida.writeInt(posTablero);
            cliente.salida.writeUTF("");
//            cliente.salida.writeInt(99);
        } catch (IOException ex) {
        }
        
    }
    
    
    public void mandarCastigoEnemigo(int tipoCastigo){
        try {
            cliente.salida.writeInt(100);
            cliente.salida.writeInt(contador);
            cliente.salida.writeInt(tipoCastigo);
        } catch (IOException ex) {
            
        }
    }
    public void recibirCastigo(int tipoCastigo){
        if (tipoCastigo == 1){ // Hielo
            castigo = 2;
        }
        else if (tipoCastigo == 2){ // fuego
            System.out.println("EL INDICE INTERNO ANTERIOR ES: " + playersArray.get(contador).getIndex());
            System.out.println("JUGADOR " + localPlayerNumber);
            playersArray.get(localPlayerNumber-1).setIndex(0);
            
            System.out.println("EL INDICE INTERNO ES: " + playersArray.get(turno-1).getIndex());
            try {
                
                cliente.salida.writeInt(101);
                cliente.salida.writeInt(localPlayerNumber);
                cliente.salida.writeInt(0);
////                cliente.salida.writeUTF("Jugador " + localPlayerNumber + "\n vuelve al inicio");
                
                
            }
            catch (IOException ex){
                
            }
        }
        try {
            cliente.salida.writeInt(99);
        } catch (IOException ex) {
            Logger.getLogger(Tablero.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void moveSelectedPlayer(int player, int posTablero){
        JButton currentButton = playersArray.get(player-1).getRefButton();
        playersArray.get(player-1).setIndex(0);
        if (posTablero > 27){
            int regresa = posTablero - 27;
            playersArray.get(contador).setIndex(27 - regresa);
            movePlayer(playersArray.get(contador).getIndex());
            return;
        }
//        System.out.println(posTablero);
        if (posTablero > 8 && posTablero <= 13){
            currentButton.setLocation(8 * BUTTON_SIZE, playersArray.get(player-1).getyOffset() + ((posTablero - 8) * BUTTON_SIZE));
        }
        if (posTablero > 13 && posTablero <= 20){
            currentButton.setLocation(((8 - (posTablero - 14)) * BUTTON_SIZE), 6 * BUTTON_SIZE + playersArray.get(player-1).getyOffset());
            //currentButton.setLocation((7 - (playersArray.get(turno-1).getIndex() - 13)) * BUTTON_SIZE, currentButton.getLocation().y + (6 * BUTTON_SIZE));
        }
        else if (posTablero > 20 && posTablero < 26){

            currentButton.setLocation(BUTTON_SIZE, ((7 - (posTablero - 20)) * BUTTON_SIZE) + playersArray.get(player-1).getyOffset());
        }
        else if (posTablero == 26){
            currentButton.setLocation(2 * BUTTON_SIZE, 2 * BUTTON_SIZE + playersArray.get(player-1).getyOffset());
        }
        else if (posTablero <= 8){
            currentButton.setLocation(posTablero * BUTTON_SIZE, playersArray.get(player-1).getyOffset());
        }
        if (posTablero == 27){
            currentButton.setLocation(3 * BUTTON_SIZE, 2 * BUTTON_SIZE + playersArray.get(player-1).getyOffset());
            String message = "El jugador " + player + "Ha ganado ";
            JOptionPane.showMessageDialog(this, message); 
        }
    }
    
    
    public void gameEnded(int won){
        if (won == 0){
            perdioPrevio = true;
        }
        if (won == 1){
            perdioPrevio = false;
        }
        try {
            cliente.salida.writeInt(99);
        } catch (IOException ex) {
            Logger.getLogger(Tablero.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void actualizaIndex(int jugador, int casilla){
        playersArray.get(jugador).setIndex(casilla);
    }
    
    public JButton getBtnDados(){
        return btnDados;
    }
    
    
    public void setNick(String nick){
        this.nick = nick;
        this.setTitle(nick);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Tablero.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Tablero.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Tablero.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Tablero.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Tablero().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDados;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblTurno;
    private javax.swing.JTextField txfDados;
    // End of variables declaration//GEN-END:variables
}
