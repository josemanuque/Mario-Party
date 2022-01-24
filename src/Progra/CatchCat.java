/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Progra;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author jmque
 */
public class CatchCat extends javax.swing.JFrame {
    public int DIMENSIONES = 11;
    public int DIMENSION_B = 70;
    private int gatoFila = 5;
    private int gatoColumna = 5;
    private boolean leftIsLocked = false;
    private boolean rightIsLocked = false;
    private boolean upIsLocked = false;
    private boolean downIsLocked = false;
    private boolean stuck = false;
    public Tablero.Tablero tablero;
    
    /**
     * Creates new form CatchCat
     */
    public CatchCat() {
        initComponents();
        generarCuadros();
    }

    JButton[][] buttonArray = new JButton[DIMENSIONES][DIMENSIONES];
    int[][] logicArray = new int[DIMENSIONES][DIMENSIONES];
    ArrayList<Integer> distances = new ArrayList<>();
    JButton gato;
    
     public void generarCuadros(){
        for (int i = 0; i < DIMENSIONES; i++){
            for (int j = 0; j < DIMENSIONES; j++){
                buttonArray[i][j] = new JButton();
                jPanel1.add(buttonArray[i][j]);
                buttonArray[i][j].setBackground(new Color(205,255,50));
                buttonArray[i][j].setBounds(j * DIMENSION_B, i * DIMENSION_B, DIMENSION_B, DIMENSION_B);
//                if (i % 2 != 0){
//                    buttonArray[i][j].setBounds(j * DIMENSION_B + 35, i * DIMENSION_B, DIMENSION_B, DIMENSION_B);
//                }
                
                buttonArray[i][j].setActionCommand(i + ", " + j);
////                logicArray[i][j] = 1;
                buttonArray[i][j].addActionListener(new ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    clickSobreTablero(evt);
                    
                }
                });
            }
        }
//        logicArray[gatoFila][gatoColumna] = 0;
//        logicArray[gatoFila - 1][gatoColumna] = 0;
//        logicArray[gatoFila + 1][gatoColumna] = 0;
//        logicArray[gatoFila][gatoColumna + 1] = 0;
//        logicArray[gatoFila][gatoColumna - 1] = 0;
//        logicArray[gatoFila - 1][gatoColumna - 1] = 0;
//        logicArray[gatoFila + 1][gatoColumna + 1] = 0;
//        logicArray[gatoFila - 1][gatoColumna + 1] = 0;
//        logicArray[gatoFila + 1][gatoColumna - 1] = 0;
//        
//        buttonArray[gatoFila - 1][gatoColumna].setBackground(new Color(205,255,50));
//        buttonArray[gatoFila - 1][gatoColumna].setBackground(new Color(205,255,50));
//        buttonArray[gatoFila + 1][gatoColumna].setBackground(new Color(205,255,50));
//        buttonArray[gatoFila][gatoColumna + 1].setBackground(new Color(205,255,50));
//        buttonArray[gatoFila][gatoColumna - 1].setBackground(new Color(205,255,50));
//        buttonArray[gatoFila - 1][gatoColumna - 1].setBackground(new Color(205,255,50));
//        buttonArray[gatoFila + 1][gatoColumna + 1].setBackground(new Color(205,255,50));
//        buttonArray[gatoFila - 1][gatoColumna + 1].setBackground(new Color(205,255,50));
//        buttonArray[gatoFila + 1][gatoColumna - 1].setBackground(new Color(205,255,50));
//
//        for (int i = 3; i < 8; i++){
//            for (int j = 3; j < 8; j++){
//                logicArray[i][j] = 0;
//                buttonArray[i][j].setBackground(new Color(205,255,50));
//            }
//        }
        
        gato = buttonArray[5][5]; 
        gato.setBackground(Color.black);
        jPanel1.repaint();
    }
   
    public void clickSobreTablero(java.awt.event.ActionEvent evt)
    {
        
        
        // obtiene el boton 
        JButton botonTemp = (JButton)evt.getSource();
        // obtiene el i,j de action command del boton
        if (botonTemp == gato)
            return;
        String identificadorBoton = botonTemp.getActionCommand();
        // separa el string del action comand para obtener columnas
        int fila = 
          Integer.parseInt(identificadorBoton.substring(0,identificadorBoton.indexOf(",")));
        int columna = 
          Integer.parseInt(identificadorBoton.substring(2+identificadorBoton.indexOf(",")));
        
        
       
        if(logicArray[fila][columna] == 1)
            return;
        
        botonTemp.setBackground(new Color(74,93,0));
        //System.out.println("HOLA");
        logicArray[fila][columna] = 1;
        if (gatoFila == 0 || gatoColumna == 0 || gatoFila == 10 || gatoColumna == 10){
            gato.setBackground(new Color(205,255,50));
            JOptionPane.showMessageDialog(this, "Ha perdido");
            tablero.gameEnded(0);
            this.dispose();
            return;
        }
        if (logicArray[gatoFila - 1][gatoColumna] == 1
                && logicArray[gatoFila + 1][gatoColumna] == 1
                && logicArray[gatoFila][gatoColumna + 1] == 1
                && logicArray[gatoFila][gatoColumna - 1] == 1
                && logicArray[gatoFila - 1][gatoColumna - 1] == 1
                && logicArray[gatoFila - 1][gatoColumna + 1] == 1
                && logicArray[gatoFila + 1][gatoColumna + 1] == 1
                && logicArray[gatoFila + 1][gatoColumna - 1] == 1){
            JOptionPane.showMessageDialog(this, "Ha Ganado!!!");
            tablero.gameEnded(1);
            this.dispose();
            return;
        }
        try{
        gatoMove();
        }
        catch(Exception e){
            System.out.println("HOLA");
            gatoMove();
        }
//        doIfLocked();
    }
    
    public void doIfLocked(){
        while (stuck){
            System.out.println("Locked");
            getSmallestValue();
            gatoMove();
            
        }
        
    }
    
    
    public int getSmallestValue(){
        int izquierda = gatoColumna;
        int derecha = DIMENSIONES - (gatoColumna + 1);
        int arriba= gatoFila;
        int abajo = DIMENSIONES - (gatoFila + 1);
        
        distances = new ArrayList<>();
        if (!leftIsLocked){
            distances.add(izquierda); // 0
//            System.out.println("AAAAA");
        }
        else
            distances.add(100);
        if (!rightIsLocked){
            distances.add(derecha); // 1
//            System.out.println("AAAAA");
        }
        else
            distances.add(100);
        if (!upIsLocked){
            distances.add(arriba); // 2
//            System.out.println("AAAAA");
        }
        else
            distances.add(100);
        if (!downIsLocked){
            distances.add(abajo); // 3
//            System.out.println("AAAAA");
        }
        else distances.add(100);
//        leftIsLocked = false;
//        rightIsLocked = false;
//        upIsLocked = false;
//        downIsLocked = false;
        return Collections.min(distances);
    }
    
    public void gatoMove(){
        gato.setBackground(new Color(205,255,50)); // restaura casilla donde estaba gato
        
        
        int smallestValue = getSmallestValue(); // gets the shortest distance
        
        ArrayList<Integer> shortestDistances = new ArrayList<>(); // obtiene los índices de todos las distancias minimas
        for (int i = 0; i < distances.size(); i++){
            if (distances.get(i) == smallestValue){
                shortestDistances.add(i);
            }
        }
        
        // Randomizar movimiento cuando hay varias "distancias minimas"
        
        int index = distances.indexOf(smallestValue);
        
        
        if (shortestDistances.size() != 1){
            index = shortestDistances.get(new Random().nextInt(shortestDistances.size()));
        }
        
        System.out.print("Gato: " + gatoFila + " " + gatoColumna + "    Smallest: " + smallestValue + "     Index: " + index + " Distances size: " + distances.size() + "    ");
        for (int i = 0; i < distances.size(); i++){
            System.out.print(distances.get(i) + " ");
        }
        System.out.println("");
        // decide direccion del movimiento
        int valid = -1;
        //System.out.println(index);
        switch (index){
            
            case 0->{
                if (logicArray[gatoFila - 1][gatoColumna - 1] == 1 
                        && logicArray[gatoFila][gatoColumna - 1] == 1
                        && logicArray[gatoFila + 1][gatoColumna - 1] == 1){
                    // method to call when one sided closed
                    leftIsLocked = true;
                    stuck = true;
                    getSmallestValue();
                    gatoMove();
                } 
                else{
                    while (valid == -1){
                        valid = moveLeft();
                    }
//                    leftIsLocked = false;
//                    rightIsLocked = false;
//                    upIsLocked = false;
//                    downIsLocked = false;
                }
            }
            case 1->{
                if (logicArray[gatoFila - 1][gatoColumna + 1] == 1 
                        && logicArray[gatoFila][gatoColumna + 1] == 1
                        && logicArray[gatoFila + 1][gatoColumna + 1] == 1){
                    // method to call when one sided closed
                    rightIsLocked = true;
//                    stuck = true;
                    getSmallestValue();
                    gatoMove();
                } 
                else{
                    while (valid == -1){
                        valid = moveRight();
                    }
//                    leftIsLocked = false;
//                    rightIsLocked = false;
//                    upIsLocked = false;
//                    downIsLocked = false;
                }
            }
            case 2->{
                if (logicArray[gatoFila - 1][gatoColumna - 1] == 1 
                        && logicArray[gatoFila - 1][gatoColumna] == 1
                        && logicArray[gatoFila - 1][gatoColumna + 1] == 1){
                    upIsLocked = true;
                    stuck = true;
                    getSmallestValue();
                    gatoMove();
                } 
                else{
                    while (valid == -1){
                        valid = moveUp();
                    }
//                    leftIsLocked = false;
//                    rightIsLocked = false;
//                    upIsLocked = false;
//                    downIsLocked = false;
                }
            }
            case 3->{
                if (logicArray[gatoFila + 1][gatoColumna - 1] == 1 
                        && logicArray[gatoFila + 1][gatoColumna] == 1
                        && logicArray[gatoFila + 1][gatoColumna + 1] == 1){
                    downIsLocked = true;
                    stuck = true;
                    getSmallestValue();
                    gatoMove();
                } 
                else{
                    while (valid == -1){
                        valid = moveDown();
                    }
//                    leftIsLocked = false;
//                    rightIsLocked = false;
//                    upIsLocked = false;
//                    downIsLocked = false;
                }
            }
            
        }
        
        distances = new ArrayList<>();
        shortestDistances = null;
        gato.setBackground(Color.black);
    }
    

    
    public int moveLeft(){
        int randomL = new Random().nextInt(3);
        switch (randomL){
            case 0->{
                if (logicArray[gatoFila - 1][gatoColumna - 1] == 1){
                    return -1;
                }
                else{
                    gatoFila--;
                    gatoColumna--;
                    gato = buttonArray[gatoFila][gatoColumna];
                    return 0;
                }
            }
            case 1->{
                if (logicArray[gatoFila][gatoColumna - 1] == 1){
                    return -1;
                }
                else{
                    gatoColumna--;
                    gato = buttonArray[gatoFila][gatoColumna];
                    return 0;
                }
            }
            case 2->{
                if (logicArray[gatoFila + 1][gatoColumna - 1] == 1){
                    return -1;
                }
                else{
                    gatoFila++;
                    gatoColumna--;
                    gato = buttonArray[gatoFila][gatoColumna];
                    return 0;
                }
            }
        }
        return -1;
    }
    
    public int moveRight(){
        int randomL = new Random().nextInt(3);
        switch (randomL){
            case 0->{
                if (logicArray[gatoFila - 1][gatoColumna + 1] == 1){
                    return -1;
                }
                else{
                    gatoFila--;
                    gatoColumna++;
                    gato = buttonArray[gatoFila][gatoColumna];
                    return 0;
                }
            }
            case 1->{
                if (logicArray[gatoFila][gatoColumna + 1] == 1){
                    return -1;
                }
                else{
                    gatoColumna++;
                    gato = buttonArray[gatoFila][gatoColumna];
                    return 0;
                }
            }
            case 2->{
                if (logicArray[gatoFila + 1][gatoColumna + 1] == 1){
                    return -1;
                }
                else{
                    gatoFila++;
                    gatoColumna++;
                    gato = buttonArray[gatoFila][gatoColumna];
                    return 0;
                }
            }
        }
        return -1;
    }
    
    public int moveUp(){
        int randomL = new Random().nextInt(3);
        switch (randomL){
            case 0->{
                if (logicArray[gatoFila - 1][gatoColumna - 1] == 1){
                    return -1;
                }
                else{
                    gatoFila--;
                    gatoColumna--;
                    gato = buttonArray[gatoFila][gatoColumna];
                    return 0;
                }
            }
            case 1->{
                if (logicArray[gatoFila - 1][gatoColumna] == 1){
                    return -1;
                }
                else{
                    gatoFila--;
                    gato = buttonArray[gatoFila][gatoColumna];
                    return 0;
                }
            }
            case 2->{
                if (logicArray[gatoFila - 1][gatoColumna + 1] == 1){
                    return -1;
                }
                else{
                    gatoFila--;
                    gatoColumna++;
                    gato = buttonArray[gatoFila][gatoColumna];
                    return 0;
                }
            }
        }
        return -1;
    }
    
    public int moveDown(){
        int randomL = new Random().nextInt(3);
        switch (randomL){
            case 0->{
                if (logicArray[gatoFila + 1][gatoColumna - 1] == 1){
                    return -1;
                }
                else{
                    gatoFila++;
                    gatoColumna--;
                    gato = buttonArray[gatoFila][gatoColumna];
                    return 0;
                }
            }
            case 1->{
                if (logicArray[gatoFila + 1][gatoColumna] == 1){
                    return -1;
                }
                else{
                    gatoFila++;
                    gato = buttonArray[gatoFila][gatoColumna];
                    return 0;
                }
            }
            case 2->{
                if (logicArray[gatoFila + 1][gatoColumna + 1] == 1){
                    return -1;
                }
                else{
                    gatoFila++;
                    gatoColumna++;
                    gato = buttonArray[gatoFila][gatoColumna];
                    return 0;
                }
            }
        }
        return -1;
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
        lblTitle = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 768, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 768, Short.MAX_VALUE)
        );

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitle.setText("Catch The Cat");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addComponent(lblTitle))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(207, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblTitle)
                .addGap(31, 31, 31)
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
            java.util.logging.Logger.getLogger(CatchCat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CatchCat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CatchCat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CatchCat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CatchCat().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblTitle;
    // End of variables declaration//GEN-END:variables
}
