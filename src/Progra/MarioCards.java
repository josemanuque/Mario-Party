/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Progra;

import Server.Client;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
public class MarioCards extends javax.swing.JFrame {
    public final int FILAS = 4;
    public final int COLUMNAS = 13;
    public final int ANCHO_B = 110;
    public final int ALTO_B = 150;
    private LinkedList<Integer> posiblesValores = new LinkedList();
    private Client cliente;
    Random random = new Random();
    private int currentPlayer = 1;
    private int localPlayer;
    private int totalPlayers;
    private LinkedList<Integer> resultados = new LinkedList();
    ImageIcon card0 = new ImageIcon("resources/MarioCard/card0.jpg");
    ImageIcon card1 = new ImageIcon("resources/MarioCard/card1.jpg");
    ImageIcon card2 = new ImageIcon("resources/MarioCard/card2.jpg");
    ImageIcon card3 = new ImageIcon("resources/MarioCard/card3.jpg");
    ImageIcon card4 = new ImageIcon("resources/MarioCard/card4.jpg");
    ImageIcon card5 = new ImageIcon("resources/MarioCard/card5.jpg");
    ImageIcon card6 = new ImageIcon("resources/MarioCard/card6.jpg");
    ImageIcon card7 = new ImageIcon("resources/MarioCard/card7.jpg");
    ImageIcon card8 = new ImageIcon("resources/MarioCard/card8.jpg");
    ImageIcon card9 = new ImageIcon("resources/MarioCard/card9.jpg");
    ImageIcon card10 = new ImageIcon("resources/MarioCard/card10.jpg");
    ImageIcon card11 = new ImageIcon("resources/MarioCard/card11.jpg");
    ImageIcon card12 = new ImageIcon("resources/MarioCard/card12.jpg");
    ImageIcon card13 = new ImageIcon("resources/MarioCard/card13.jpg");
    ImageIcon card14 = new ImageIcon("resources/MarioCard/card14.jpg");
    ImageIcon card15 = new ImageIcon("resources/MarioCard/card15.jpg");
    ImageIcon card16 = new ImageIcon("resources/MarioCard/card16.jpg");
    ImageIcon card17 = new ImageIcon("resources/MarioCard/card17.jpg");
    ImageIcon card18 = new ImageIcon("resources/MarioCard/card18.jpg");
    ImageIcon card19 = new ImageIcon("resources/MarioCard/card19.jpg");
    ImageIcon card20 = new ImageIcon("resources/MarioCard/card20.jpg");
    ImageIcon card21 = new ImageIcon("resources/MarioCard/card21.jpg");
    ImageIcon card22 = new ImageIcon("resources/MarioCard/card22.jpg");
    ImageIcon card23 = new ImageIcon("resources/MarioCard/card23.jpg");
    ImageIcon card24 = new ImageIcon("resources/MarioCard/card24.jpg");
    ImageIcon card25 = new ImageIcon("resources/MarioCard/card25.jpg");
    ImageIcon card26 = new ImageIcon("resources/MarioCard/card26.jpg");
    ImageIcon card27 = new ImageIcon("resources/MarioCard/card27.jpg");
    ImageIcon card28 = new ImageIcon("resources/MarioCard/card28.jpg");
    ImageIcon card29 = new ImageIcon("resources/MarioCard/card29.jpg");
    ImageIcon card30 = new ImageIcon("resources/MarioCard/card30.jpg");
    ImageIcon card31 = new ImageIcon("resources/MarioCard/card31.jpg");
    ImageIcon card32 = new ImageIcon("resources/MarioCard/card31.jpg");
    ImageIcon card33 = new ImageIcon("resources/MarioCard/card33.jpg");
    ImageIcon card34 = new ImageIcon("resources/MarioCard/card34.jpg");
    ImageIcon card35 = new ImageIcon("resources/MarioCard/card35.jpg");
    ImageIcon card36 = new ImageIcon("resources/MarioCard/card36.jpg");
    ImageIcon card37 = new ImageIcon("resources/MarioCard/card37.jpg");
    ImageIcon card38 = new ImageIcon("resources/MarioCard/card38.jpg");
    ImageIcon card39 = new ImageIcon("resources/MarioCard/card39.jpg");
    ImageIcon card40 = new ImageIcon("resources/MarioCard/card40.jpg");
    ImageIcon card41 = new ImageIcon("resources/MarioCard/card41.jpg");
    ImageIcon card42 = new ImageIcon("resources/MarioCard/card42.jpg");
    ImageIcon card43 = new ImageIcon("resources/MarioCard/card43.jpg");
    ImageIcon card44 = new ImageIcon("resources/MarioCard/card44.jpg");
    ImageIcon card45 = new ImageIcon("resources/MarioCard/card45.jpg");
    ImageIcon card46 = new ImageIcon("resources/MarioCard/card46.jpg");
    ImageIcon card47 = new ImageIcon("resources/MarioCard/card47.jpg");
    ImageIcon card48 = new ImageIcon("resources/MarioCard/card48.jpg");
    ImageIcon card49 = new ImageIcon("resources/MarioCard/card49.jpg");
    ImageIcon card50 = new ImageIcon("resources/MarioCard/card50.jpg");
    ImageIcon card51 = new ImageIcon("resources/MarioCard/card51.jpg");
    int mainPlayer;
    /**
     * Creates new form MarioCards
     */
    public MarioCards() {
        initComponents();
        //generarPosiblesValores();
        generarCuadros();
        //generarArregloLogico();
    }
    
    JButton[][] buttonArray = new JButton[FILAS][COLUMNAS];
    int[][] logicArray = new int[FILAS][COLUMNAS];
    
    
    public void generarPosiblesValores() {
        for(int i = 0; i < 52; i++) {
            posiblesValores.add(random.nextInt(posiblesValores.size() + 1), i);
        }
    }

    public void setPosiblesValores(LinkedList<Integer> posiblesValores) {
        this.posiblesValores = posiblesValores;
        generarArregloLogico();
        logicPrintTest();
    }

    public void setMainPlayer(int mainPlayer) {
        this.mainPlayer = mainPlayer;
    }
    
    
    
    public void generarCuadros(){
        ImageIcon iconoVacio = new ImageIcon("resources/MarioCard/hiddenCard.png");
        for (int i = 0; i < FILAS; i++){
            for (int j = 0; j < COLUMNAS; j++){
                buttonArray[i][j] = new JButton();
                jPanel1.add(buttonArray[i][j]);
                buttonArray[i][j].setIcon(iconoVacio);
                buttonArray[i][j].setBounds(j * ANCHO_B, i * ALTO_B, ANCHO_B, ALTO_B);
                buttonArray[i][j].setActionCommand(i + "," + j);
                buttonArray[i][j].setBackground(Color.white);
                buttonArray[i][j].addActionListener(new ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    clickSobreTablero(evt);
                }
                });
            }
        }
        
    }
    
    public void generarArregloLogico(){
        for (int f = 0; f < FILAS; f++){
            for(int c = 0; c < COLUMNAS; c++) {
                logicArray[f][c] = posiblesValores.remove();
            }
        }
    }
    
    public void logicPrintTest(){
        for (int i = 0; i < FILAS; i++){
            for (int j = 0; j < COLUMNAS; j++){
                System.out.print(logicArray[i][j] + " ");
            }
            System.out.println("");
        }
    }
    
    public void clickSobreTablero(java.awt.event.ActionEvent evt)
    {
        if(currentPlayer != localPlayer) {
            return;
        }
        // se obtiene el botón presionado
        JButton currentBoton = (JButton)evt.getSource();
        // obtiene el i,j de action command del boton
        String identificadorBoton = currentBoton.getActionCommand();
        
        if(currentBoton.getBackground().equals(Color.white)) {
            try {
                currentBoton.setBackground(Color.white);
                // separa el string del action comand para obtener filas y columnas
                int fila =
                        Integer.parseInt(identificadorBoton.substring(0,identificadorBoton.indexOf(",")));
                int columna =
                        Integer.parseInt(identificadorBoton.substring(1+identificadorBoton.indexOf(","), identificadorBoton.length()));
                System.out.println("Fila: " + fila + " ---- Columna: " + columna);
                cliente.salida.writeInt(8);
                cliente.salida.writeInt(logicArray[fila][columna]);
                System.out.println("Se envió: " + logicArray[fila][columna]);
            } catch (IOException ex) {
                Logger.getLogger(MarioCards.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
    }
    
    public void recibirResultado(int result) {
        resultados.add(result);
        for(int f = 0; f < FILAS; f++) {
            for(int c = 0; c < COLUMNAS; c++) {
                if (logicArray[f][c] == result) {
                    System.out.println("Resultado" + logicArray[f][c]);
                    if(buttonArray[f][c].getBackground().equals(Color.white)) {
                        buttonArray[f][c].setBackground(Color.red);
                        buttonArray[f][c].setIcon(revelarCarta(logicArray[f][c]));
                    }
                }
            }
        }
        
        currentPlayer++;
        if(currentPlayer > totalPlayers) {
            int max = resultados.get(0);
            for(int j = 0; j < resultados.size(); j++) {
                if(resultados.get(j) > max)
                    max = resultados.get(j);
            }
        int ganador = resultados.indexOf(max) + 1;
        JOptionPane.showMessageDialog(this, "Ha ganado el jugador " + ganador);
        if(this.mainPlayer == this.localPlayer) {
            if(this.localPlayer == ganador) {
                try {
                    cliente.salida.writeInt(10);
                } catch (IOException ex) {
                    Logger.getLogger(MarioCards.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else {
                try {
                    cliente.salida.writeInt(11);
                } catch (IOException ex) {
                    Logger.getLogger(MarioCards.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        this.setVisible(false);
        }
        
        else {
            etiquetaElegirCarta.setText("Jugador " + currentPlayer + ", Elija su carta:");
        }
    }

    public void setCliente(Client cliente) {
        this.cliente = cliente;
    }

    public void setLocalPlayer(int localPlayer) {
        this.localPlayer = localPlayer;
    }

    public void setTotalPlayers(int totalPlayers) {
        this.totalPlayers = totalPlayers;
    }
    
    public ImageIcon revelarCarta(int opcion) {
        switch(opcion) {
            case 0 -> {
                return card0;
            }
            case 1 -> {
                return card1; }
            
            case 2 -> {
                return card2;
            }
            case 3 -> {
                return card3;
            }
            case 4-> {
                return card4;
            }
            
            case 5 -> {
                return card5;
            }
            case 6 -> {
                return card6; }
            
            case 7 -> {
                return card7;
            }
            case 8 -> {
                return card8;
            }
            case 9-> {
                return card9;
            }
            
            case 10 -> {
                return card10;
            }
            case 11 -> {
                return card11; }
            
            case 12 -> {
                return card12;
            }
            case 13 -> {
                return card13;
            }
            case 14-> {
                return card14;
            }
            
            case 15 -> {
                return card15;
            }
            case 16 -> {
                return card16; }
            
            case 17 -> {
                return card17;
            }
            case 18 -> {
                return card18;
            }
            case 19-> {
                return card19;
            }
            
            case 20 -> {
                return card20;
            }
            case 21 -> {
                return card21; }
            
            case 22 -> {
                return card22;
            }
            case 23 -> {
                return card23;
            }
            case 24-> {
                return card24;
            }
            
            case 25 -> {
                return card25;
            }
            case 26 -> {
                return card26; }
            
            case 27 -> {
                return card27;
            }
            case 28 -> {
                return card28;
            }
            case 29-> {
                return card29;
            }
            case 30 -> {
                return card30;
            }
            case 31 -> {
                return card31; }
            
            case 32 -> {
                return card32;
            }
            case 33 -> {
                return card33;
            }
            case 34-> {
                return card34;
            }
            
            case 35 -> {
                return card35;
            }
            case 36 -> {
                return card36; }
            
            case 37 -> {
                return card37;
            }
            case 38 -> {
                return card38;
            }
            case 39-> {
                return card39;
            }
            
            case 40 -> {
                return card40;
            }
            case 41 -> {
                return card41; }
            
            case 42 -> {
                return card42;
            }
            case 43 -> {
                return card43;
            }
            case 44-> {
                return card44;
            }
            
            case 45 -> {
                return card45;
            }
            case 46 -> {
                return card46; }
            
            case 47 -> {
                return card47;
            }
            case 48 -> {
                return card48;
            }
            case 49-> {
                return card49;
            }
           
            case 50-> {
                return card50;
            }
            
            case 51-> {
                return card51;
            }
        }
        return null;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        etiquetaTitulo = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        etiquetaElegirCarta = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        etiquetaTitulo.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        etiquetaTitulo.setText("Mario cards");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1435, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 748, Short.MAX_VALUE)
        );

        etiquetaElegirCarta.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        etiquetaElegirCarta.setText("Jugador 1, elija su carta:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(etiquetaTitulo)
                        .addGap(388, 388, 388)
                        .addComponent(etiquetaElegirCarta))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(etiquetaTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(etiquetaElegirCarta))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            java.util.logging.Logger.getLogger(MarioCards.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MarioCards.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MarioCards.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MarioCards.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MarioCards().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel etiquetaElegirCarta;
    private javax.swing.JLabel etiquetaTitulo;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
