/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Progra;

import Progra.CollectCoinsTime;
import java.awt.Color;
import static java.lang.Thread.sleep;
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
public class collectTheCoins extends javax.swing.JFrame {
    public static int ANCHO = 47;
    public static int ALTO = 25;
    private static int tamaño = 25;
    private int[][] arregloLogico;
    JButton[][] buttonArray;
    private int puntos;
    Random random;
    private LinkedList<Integer> valorMonedas;
    private CollectCoinsTime timeThread;
    public Tablero.Tablero tablero;
    ImageIcon coin;
    
    /**
     * Creates new form collectTheCoins
     */
    public collectTheCoins() {
        puntos = 0;
        initComponents();
        random = new Random();
        valorMonedas = new LinkedList();
        valorMonedas.addLast(-10);
        valorMonedas.addLast(-9);
        valorMonedas.addLast(-8);
        valorMonedas.addLast(-7);
        valorMonedas.addLast(-6);
        valorMonedas.addLast(-5);
        valorMonedas.addLast(-4);
        valorMonedas.addLast(-3);
        valorMonedas.addLast(-2);
        valorMonedas.addLast(-1);
        valorMonedas.addLast(1);
        valorMonedas.addLast(2);
        valorMonedas.addLast(3);
        valorMonedas.addLast(4);
        valorMonedas.addLast(5);
        valorMonedas.addLast(6);
        valorMonedas.addLast(7);
        valorMonedas.addLast(8);
        valorMonedas.addLast(9);
        valorMonedas.addLast(10);
        coin = new ImageIcon("resources/marioCoin.png");
        generarArregloLogico();
        generarCuadros();
        timeThread = new CollectCoinsTime(this);
        timeThread.start();
        puntosEtiqueta.setText("Puntos: " + puntos);
    }
    
    public void generarArregloLogico() {
        int index = 0;
        arregloLogico = new int[tamaño][tamaño];
        for(int f = 0; f < tamaño; f++) {
            for(int c = 0; c < tamaño; c++) {
                if(index == 0){
                    arregloLogico[f][c] = valorMonedas.get(random.nextInt(10));
                    index = 1;
                }
                else if(index == 1) {
                    arregloLogico[f][c] = valorMonedas.get(10+random.nextInt(10));
                    index = 0;
                }
            }
        }
        print();
    }
    
    public void generarCuadros() {
        buttonArray = new JButton[tamaño][tamaño];
        for(int f = 0; f < tamaño; f++) {
            for(int c = 0; c < tamaño; c++) {
                buttonArray[f][c] = new JButton();
                jPanel1.add(buttonArray[f][c]);
                buttonArray[f][c].setBounds(c * ANCHO, f * ALTO, ANCHO, ALTO);
                buttonArray[f][c].setActionCommand(f + "," + c);
                buttonArray[f][c].setText("?");
                buttonArray[f][c].addActionListener((java.awt.event.ActionEvent evt) -> {
                    clickSobreBoton(evt);
                });
                buttonArray[f][c].setBackground(Color.white);
                    }
                
                }
    }
    public void clickSobreBoton(java.awt.event.ActionEvent evt) {
        JButton current = (JButton) evt.getSource();
        String identificadorBoton = current.getActionCommand();
        // separa el string del action comand para obtener fila y columnas
        int f = 
          Integer.parseInt(identificadorBoton.substring(0,identificadorBoton.indexOf(",")));
        int c = 
          Integer.parseInt(identificadorBoton.substring(1+identificadorBoton.indexOf(","), identificadorBoton.length()));
        
        if (current.getBackground() == Color.white) {
            if (arregloLogico[f][c] > 0) {
                current.setBackground(Color.green);
            }
            else current.setBackground(Color.red);
            
            Thread thread = new Thread() {
            public void run() {
                try {
                    current.setIcon(coin);
                    sleep(500);
                    current.setIcon(null);
                    current.setText(String.valueOf(arregloLogico[f][c]));
                } catch (InterruptedException ex) {
                    Logger.getLogger(collectTheCoins.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            };
            thread.start();

            puntos += arregloLogico[f][c];
            puntosEtiqueta.setText("Puntos: " + puntos);
        }
    }
    
    public void print() {
        for(int f = 0; f < tamaño; f++) {
            for(int c = 0; c < tamaño; c++) {
                System.out.print(arregloLogico[f][c] + " ");
                
            }
            System.out.println("");
        }
    }
    
    public void setTime(String time) {
        timeEtiqueta.setText(time);
    }
    
    public void gameOver() {
        if(puntos < 1){
            JOptionPane.showMessageDialog(this, "Ha perdido :( Su puntuación fue: " + puntos);
            tablero.gameEnded(0);
        }
        else{ 
            JOptionPane.showMessageDialog(this, "Ha ganado :) Su puntuación fue: " + puntos);
            tablero.gameEnded(1);
        }
        
        this.dispose();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        puntosEtiqueta = new javax.swing.JLabel();
        timeEtiqueta = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel1.setText("Collect the Coins");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1210, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 646, Short.MAX_VALUE)
        );

        puntosEtiqueta.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        puntosEtiqueta.setText("jLabel2");

        timeEtiqueta.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        timeEtiqueta.setText("jLabel2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(puntosEtiqueta, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(373, 373, 373)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(timeEtiqueta, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(106, 106, 106))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(puntosEtiqueta, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(timeEtiqueta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            java.util.logging.Logger.getLogger(collectTheCoins.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(collectTheCoins.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(collectTheCoins.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(collectTheCoins.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new collectTheCoins().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel puntosEtiqueta;
    private javax.swing.JLabel timeEtiqueta;
    // End of variables declaration//GEN-END:variables
}
