/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Progra;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author jmque
 */
public class MemoryPath extends javax.swing.JFrame {
    public final int FILAS = 6;
    public final int COLUMNAS = 3;
    public final int ANCHO_B = 100;
    public final int ALTO_B = 90;
    private int currentFila = FILAS - 1;
    public int VIDAS = 3;
    public boolean win = false;
    public JButton returnBtn;
    public Tablero.Tablero tablero;
    /**
     * Creates new form MemoryPath
     */
    public MemoryPath() {
        initComponents();
        generarArregloLogico();
        logicPrintTest();
        generarCuadros();
        
    }
    
    JButton[][] buttonArray = new JButton[FILAS][COLUMNAS];
    int[][] logicArray = new int[FILAS][COLUMNAS];
    
    public void generarCuadros(){
        for (int i = 0; i < FILAS; i++){
            for (int j = 0; j < COLUMNAS; j++){
                buttonArray[i][j] = new JButton();
                jPanel1.add(buttonArray[i][j]);
                buttonArray[i][j].setBounds(j * ANCHO_B, i * ALTO_B, ANCHO_B, ALTO_B);
                buttonArray[i][j].setActionCommand(i + ", " + j);
                
                if (i != FILAS - 1)
                    buttonArray[i][j].setEnabled(false);
                
                buttonArray[i][j].addActionListener(new ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    clickSobreTablero(evt);
                }
                });
            }
        }
        
    }
    
    public void generarArregloLogico(){
        for (int i = 0; i < FILAS; i++){
            int randomChoice = new Random().nextInt(3);
            logicArray[i][randomChoice] = 1;
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
    
    private boolean isDead(){
        return VIDAS == 0;
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
        
        //System.out.println("FILA " + fila + "curr " + currentFila);
        
        
        if (fila != currentFila) // chequea que no se presione un botón de fila ya validada
            return;
        
        // SI GANA--------------------
        if (currentFila == 0 && logicArray[fila][columna] == 1){
            botonTemp.setText("Correct");
            botonTemp.setBackground(Color.green);
            JOptionPane.showMessageDialog(this, "HA GANADO!!!");
            tablero.gameEnded(1);
            this.dispose();
            return;
        }
        
        
        if (logicArray[fila][columna] == 1){ // Si pasa de FILA
//            System.out.println("PASADO");
            enableNextRows();
            botonTemp.setText("Correct");
            botonTemp.setBackground(Color.green);
            // Mostrar en pantalla que fue valido (muestre algo en el boton)
            
        }
        else{
            // Si no pasa de FILA
//            System.out.println("No pasado");
            VIDAS--;
            lblVIDAS.setText("Lives: " + VIDAS); 
            Thread th1 = new Thread() {
                public void run() {
                    try {
                        botonTemp.setText("FAIL");
                        botonTemp.setBackground(Color.red);
                        sleep(700);
                        reset();
                    } 
                    catch (Exception e) {
                    }

                }
            };
            th1.start();
            
        }

        if (isDead()){
            JOptionPane.showMessageDialog(this, "Ha perdido");
            tablero.gameEnded(0);
            this.dispose();
        }
    }
    
    public void reset(){
        currentFila = FILAS - 1;
        System.out.println("........");
        for (int i = 0; i < FILAS; i++){
            for (int j = 0; j < COLUMNAS; j++){
                buttonArray[i][j].setEnabled(true);
                buttonArray[i][j].setBackground(null);
                buttonArray[i][j].setText("");
                //System.out.println(buttonArray[i][j].getActionCommand());
                if (i != currentFila)
                    buttonArray[i][j].setEnabled(false);
            }
        }
            
    }
    
    public void enableNextRows(){ // ENABLES siguiente fila y actualiza currentFila
        currentFila--;
        for (int j = 0; j < COLUMNAS; j++){
            
            buttonArray[currentFila][j].setEnabled(true);
        }
    }
    
    public void addReturnButton(JButton returnBtn){
        this.returnBtn = returnBtn;
        pnlReturn.add(returnBtn);
        returnBtn.addActionListener(new ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    dispose();
                }
                });
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        lblTitle = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblVIDAS = new javax.swing.JLabel();
        pnlReturn = new javax.swing.JPanel();

        jTextField1.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitle.setText("Memory Path");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 377, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 550, Short.MAX_VALUE)
        );

        lblVIDAS.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblVIDAS.setText("Lives: 3");

        javax.swing.GroupLayout pnlReturnLayout = new javax.swing.GroupLayout(pnlReturn);
        pnlReturn.setLayout(pnlReturnLayout);
        pnlReturnLayout.setHorizontalGroup(
            pnlReturnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 96, Short.MAX_VALUE)
        );
        pnlReturnLayout.setVerticalGroup(
            pnlReturnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 46, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnlReturn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(433, 433, 433)
                        .addComponent(lblVIDAS))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(80, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pnlReturn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30)
                .addComponent(lblVIDAS)
                .addGap(67, 67, 67)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(156, Short.MAX_VALUE))
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
            java.util.logging.Logger.getLogger(MemoryPath.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MemoryPath.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MemoryPath.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MemoryPath.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MemoryPath().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblVIDAS;
    private javax.swing.JPanel pnlReturn;
    // End of variables declaration//GEN-END:variables
}
