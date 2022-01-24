/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Progra;

import Server.Client;
import java.awt.Color;
import java.awt.Component;
import java.awt.List;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;


/**
 *
 * @author jmque
 */
public class SuperBrosMemory extends javax.swing.JFrame {
    private final int FILAS = 3;
    private final int COLUMNAS = 6;
    public final int ANCHO_B = 120;
    public final int ALTO_B = 160;
    private int cardsPressed = 0;
    private String firstCardValue;
    Client cliente;
    ArrayList<Integer> pares = new ArrayList<>();
    int turno = 1;
    int localPlayerNumber;
    int mainPlayer;
    int notMainplayer;
    int paresMain;
    int paresEnemigo;
    String nick;
    String enem;
    /**
     * Creates new form SuperBrosMemory
     */
    public SuperBrosMemory() {
        initComponents();
        
//        generarPares();
//        logicPrintTest();
    }
    
    public void setPares(ArrayList<Integer> pares){
        this.pares = pares;
        generarPares();
        logicPrintTest();
        lblMain.setText("Pares Jugador "+ mainPlayer + ": " + paresMain);
        lblNotMain.setText("Pares Jugador "+ notMainplayer+ ": " + paresEnemigo);
    }
    
    
    JButton[][] buttonArray = new JButton[FILAS][COLUMNAS];
    int[][] logicArray = new int[FILAS][COLUMNAS];
    ImageIcon im = new ImageIcon("resources/Memory/hiddenCard.png");
    
        public void setCliente(Client cliente) {
        this.cliente = cliente;
    }

    public void setLocalPlayerNumber(int localPlayerNumber) {
        this.localPlayerNumber = localPlayerNumber;
    }

    public void setMainPlayer(int mainPlayer) {
        this.mainPlayer = mainPlayer;
        turno = mainPlayer;
        lblTurno.setText("Turno del Jugador "+turno);
    }

    public void setNotMainplayer(int notMainplayer) {
        this.notMainplayer = notMainplayer;
    }
    
    public void setEnemigo(String enem)
    {
        this.enem = enem;
        etiquetaEnemigo.setText("rival: "+enem);
    }
    
    public void setNick(String nick) {
        this.nick = nick;
        this.setTitle(nick);
    }    
    
    public void generarPares(){
        
        
        
        for (int i = 0; i < FILAS; i++){
            for (int j = 0; j < COLUMNAS; j++){
                
                buttonArray[i][j] = new JButton(im);
                jPanel1.add(buttonArray[i][j]);
                buttonArray[i][j].setBounds(j * ANCHO_B + 15 * j, i * ALTO_B + 15 * i, ANCHO_B, ALTO_B);
                buttonArray[i][j].setActionCommand(i + ", " + j);
//                buttonArray[i][j].setEnabled(false);
                logicArray[i][j] = pares.remove(0);
                buttonArray[i][j].addActionListener(new ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    clickSobreTablero(evt);
                    System.out.println("HOla");
//                    System.out.println("Hola");
                }
                });
            }
        }
    }
    
    public void clickSobreTablero(java.awt.event.ActionEvent evt)
    {
        
        // obtiene el boton 
        JButton botonTemp = (JButton)evt.getSource();
        // obtiene el i,j de action command del boton
        String identificadorBoton = botonTemp.getActionCommand();
        // separa el string del action comand para obtener columnas
        int fila = 
          Integer.parseInt(identificadorBoton.substring(0,identificadorBoton.indexOf(",")));
        int columna = 
          Integer.parseInt(identificadorBoton.substring(2+identificadorBoton.indexOf(",")));
        
        
        // si ya se disparo entonces nada
        if(logicArray[fila][columna] == 11 || logicArray[fila][columna] == 12)
            return;
        System.out.println("LOCL "+ localPlayerNumber);
        System.out.println("Turno "+ turno);
        if (localPlayerNumber != turno)
            return;
        
        // si es mi turno continua, si no return
        if (localPlayerNumber != turno){
            System.out.println("NO SSIRVE");
            return;
        }

        try {
            cliente.salida.writeInt(21);
            cliente.salida.writeInt(columna);
            cliente.salida.writeInt(fila);
            } catch (IOException ex) {
                Logger.getLogger(SuperBrosMemory.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        if (cardsPressed == 0){
            firstCardValue = identificadorBoton;
        }
        
        if (cardsPressed != 2){
            revealCard(fila, columna);
            cardsPressed++;
        }
        
        if (cardsPressed == 2){
            int previousButtonFila = 
                Integer.parseInt(firstCardValue.substring(0,firstCardValue.indexOf(",")));
            int previousButtonColumna = 
                Integer.parseInt(firstCardValue.substring(2+firstCardValue.indexOf(",")));
            if (buttonArray[fila][columna] == buttonArray[previousButtonFila][previousButtonColumna]){
                cardsPressed = 1;
                return;
            }
            if (checkPair(fila, columna)){
//                JOptionPane.showMessageDialog(this, "PAREJA!");
                
                logicArray[previousButtonFila][previousButtonColumna] = mainPlayer+10;
                logicArray[fila][columna] = mainPlayer+10;
                paresMain++;
                lblMain.setText("Pares Jugador "+ nick+ ": " + paresMain);
                
//                try {
//                    cliente.salida.writeInt(22);
//                } catch (IOException ex) {
//                    Logger.getLogger(SuperBrosMemory.class.getName()).log(Level.SEVERE, null, ex);
//                }
            }
            else{
                hidePair(fila, columna);
            }
            if (turno == mainPlayer)
            {
                turno=notMainplayer;
            }
            else
            {
                turno=mainPlayer;
            }
        lblTurno.setText("Turno del jugador click " + turno);
            cardsPressed = 0;
        }
        int contadorMain = 0;
        int contadorEnemigo = 0;
        int sobrantes = 0;
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 6; j++){
                if (logicArray[i][j] == (mainPlayer + 10)){
                    contadorMain++;
                }
                else if (logicArray[i][j] == (notMainplayer + 10)){
                    contadorEnemigo++;
                }
                else sobrantes++;
            }
        }
        if (sobrantes == 0){
            logicPrintTest();
            if (contadorMain > contadorEnemigo){
                JOptionPane.showMessageDialog(this, "Ha ganado el jugador " + nick);
                try {
                    if(localPlayerNumber == mainPlayer) {
                        System.out.println(localPlayerNumber + " - " + mainPlayer);
                        cliente.salida.writeInt(10);  }
                } catch (IOException ex) {
                    Logger.getLogger(SuperBrosMemory.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (contadorMain < contadorEnemigo){
                JOptionPane.showMessageDialog(this, "Ha ganado el jugador " + enem);
                try {
                    if(localPlayerNumber == mainPlayer) {
                        System.out.println(localPlayerNumber + " - " + mainPlayer);
                        cliente.salida.writeInt(11);}
                } catch (IOException ex) {
                    Logger.getLogger(SuperBrosMemory.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (contadorMain == contadorEnemigo){
                JOptionPane.showMessageDialog(this, "Empate");
                try {
                    if(localPlayerNumber == mainPlayer) {
                        cliente.salida.writeInt(11); }
                } catch (IOException ex) {
                    Logger.getLogger(SuperBrosMemory.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            this.dispose();
            return;
        }
    }
    
    int c1;
    int f1;
    
    public void marcar(int columna, int fila){
             
        // si ya se disparo entonces nada
        if(logicArray[fila][columna] == 11 || logicArray[fila][columna] == 12)
            return;
        
//        if (cardsPressed > 0)
//            return;
        
        if (cardsPressed == 0){
            c1 = columna;
            f1 = fila;
        }
        
        if (cardsPressed != 2){
            revealCard(fila, columna);
            cardsPressed++;
        }
        
        if (cardsPressed == 2){
            if (buttonArray[fila][columna] == buttonArray[f1][c1]){
                cardsPressed = 1;
                return;
            }
            if (logicArray[f1][c1] == logicArray[fila][columna]){
//                try {
//                    cliente.salida.writeInt(22);
//                } catch (IOException ex) {
//                    Logger.getLogger(SuperBrosMemory.class.getName()).log(Level.SEVERE, null, ex);
//                }
                paresEnemigo++;
                lblNotMain.setText("Pares Jugador "+ enem+ ": " + paresEnemigo);
                logicArray[f1][c1] = notMainplayer+10;
                logicArray[fila][columna] = notMainplayer+10;
            }
            else{
                hidePairMarcado(fila, columna);
            }
            cardsPressed = 0;
        }
        
        int contadorMain = 0;
        int contadorEnemigo = 0;
        int sobrantes = 0;
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 6; j++){
                if (logicArray[i][j] == (mainPlayer + 10)){
                    contadorMain++;
                }
                else if (logicArray[i][j] == (notMainplayer + 10)){
                    contadorEnemigo++;
                }
                else sobrantes++;
            }
        }
        if (sobrantes == 0){
            logicPrintTest();
            if (contadorMain > contadorEnemigo){
                JOptionPane.showMessageDialog(this, "Ha ganado el jugador " + nick);
                try {
                    if(localPlayerNumber == mainPlayer) {
                        System.out.println(localPlayerNumber + " - " + mainPlayer);
                        cliente.salida.writeInt(10); }
                } catch (IOException ex) {
                    Logger.getLogger(SuperBrosMemory.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (contadorMain < contadorEnemigo){
                JOptionPane.showMessageDialog(this, "Ha ganado el jugador " + enem);
                try {
                    if(localPlayerNumber == mainPlayer) {
                        System.out.println(localPlayerNumber + " - " + mainPlayer);
                        cliente.salida.writeInt(11); }
                } catch (IOException ex) {
                    Logger.getLogger(SuperBrosMemory.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (contadorMain == contadorEnemigo){
                JOptionPane.showMessageDialog(this, "Empate");
                try {
                    if(localPlayerNumber == mainPlayer) {
                        cliente.salida.writeInt(11); }
                } catch (IOException ex) {
                    Logger.getLogger(SuperBrosMemory.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            this.dispose();
            return;
        }
        turno=localPlayerNumber;
        lblTurno.setText("Turno del Jugador "+turno);
    }
    
    public boolean checkPair(int fila2, int columna2){
        int fila1 = 
          Integer.parseInt(firstCardValue.substring(0,firstCardValue.indexOf(",")));
        int columna1 = 
          Integer.parseInt(firstCardValue.substring(2+firstCardValue.indexOf(",")));
        return logicArray[fila2][columna2] == logicArray[fila1][columna1];
    }
    
    public void revealCard(int fila, int columna){
        ImageIcon revealedIcon = chooseImage(logicArray[fila][columna]);
        buttonArray[fila][columna].setIcon(revealedIcon);
    }
    
    public void hidePair(int fila2, int columna2){
        int fila1 = 
          Integer.parseInt(firstCardValue.substring(0,firstCardValue.indexOf(",")));
        int columna1 = 
          Integer.parseInt(firstCardValue.substring(2+firstCardValue.indexOf(",")));
        
        Thread th1 = new Thread() {
                public void run() {
                    try {
                        sleep(1000);
                        buttonArray[fila1][columna1].setIcon(chooseImage(10));
                        buttonArray[fila2][columna2].setIcon(chooseImage(10));
                    } 
                    catch (Exception e) {
                    }

                }
            };
        th1.start();
        
    }
    
    
    public void hidePairMarcado(int fila2, int columna2){
        
        Thread th1 = new Thread() {
                public void run() {
                    try {
                        sleep(1000);
                        buttonArray[f1][c1].setIcon(chooseImage(10));
                        buttonArray[fila2][columna2].setIcon(chooseImage(10));
                    } 
                    catch (Exception e) {
                    }

                }
            };
        th1.start();
        
    }
    
    
    
    public void logicPrintTest(){
        for (int i = 0; i < FILAS; i++){
            for (int j = 0; j < COLUMNAS; j++){
                System.out.print(logicArray[i][j] + " ");
            }
            System.out.println("");
        }
    }

    public ImageIcon chooseImage(int num){
        switch (num){
            case 1->{
                return new ImageIcon("resources/Memory/1.png");
            }
            case 2->{
                return new ImageIcon("resources/Memory/2.png");
            }
            case 3->{
                return new ImageIcon("resources/Memory/3.png");
            }
            case 4->{
                return new ImageIcon("resources/Memory/4.png");
            }
            case 5->{
                return new ImageIcon("resources/Memory/5.png");
            }
            case 6->{
                return new ImageIcon("resources/Memory/6.png");
            }
            case 7->{
                return new ImageIcon("resources/Memory/7.png");
            }
            case 8->{
                return new ImageIcon("resources/Memory/8.png");
            }
            case 0->{
                return new ImageIcon("resources/Memory/9.png");
            }
        }
        return new ImageIcon("resources/Memory/hiddenCard.png");
    }
    @Override
    public Component add(Component comp) {
        return super.add(comp); //To change body of generated methods, choose Tools | Templates.
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        etiquetaEnemigo = new javax.swing.JLabel();
        lblTurno = new javax.swing.JLabel();
        lblMain = new javax.swing.JLabel();
        lblNotMain = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitle.setText("Super Bro's Memory");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 830, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 631, Short.MAX_VALUE)
        );

        etiquetaEnemigo.setText("Enemigo: ");

        lblTurno.setText("Turno");

        lblMain.setText("Main");

        lblNotMain.setText("NotMain");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMain)
                            .addComponent(lblNotMain))
                        .addContainerGap(109, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(etiquetaEnemigo)
                        .addGap(132, 132, 132)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTurno)
                        .addGap(134, 134, 134))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(etiquetaEnemigo)
                        .addComponent(lblTurno)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addComponent(lblMain)
                        .addGap(44, 44, 44)
                        .addComponent(lblNotMain)))
                .addContainerGap(52, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(SuperBrosMemory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SuperBrosMemory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SuperBrosMemory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SuperBrosMemory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SuperBrosMemory().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel etiquetaEnemigo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblMain;
    private javax.swing.JLabel lblNotMain;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTurno;
    // End of variables declaration//GEN-END:variables
}
