/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Progra;

import java.awt.Color;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;



/**
 *
 * @author Usuario
 */
public class BomberMario extends javax.swing.JFrame {
    private int ANCHO = 40;
    private int ALTO = 40;
    private int[][] logicArray;
    JButton[][] buttonArray;
    private LinkedList<Bomba> bombas;
    Random random = new Random();
    private int tamaño;
    LinkedList<Integer> probabilidad;
    Bomba current;
    int bombasRestantes;
    public Tablero.Tablero tablero;
    ImageIcon iconoVacio;

    /**
     * Creates new form BomberMario
     */
    public BomberMario() {
        initComponents();
        bombasRestantes = 7;
        probabilidad = new LinkedList();
        bombas = new LinkedList();
        Bomba b = new Bomba("simple");
        bombas.addLast(b);
        b = new Bomba("doble");
        bombas.addLast(b);
        b = new Bomba("cruz");
        bombas.addLast(b);
        b = new Bomba("linea");
        bombas.addLast(b);
        probabilidad.addLast(0);
        probabilidad.addLast(1);
        probabilidad.addLast(0);
        probabilidad.addLast(2);
        probabilidad.addLast(3);
        probabilidad.addLast(0);
        generarArregloLogico();
        generarCuadros();
        generarBomba();
        print();
    }
    
    public void generarArregloLogico() {
        int index = random.nextInt(3);
        switch(index) {
            case 0 -> {
                tamaño = 10;
                ANCHO = 60;
                ALTO = 60;
                iconoVacio = new ImageIcon("resources/questionMark.jpg");
            }
            case 1 -> {
                tamaño = 15;
                ANCHO = 42;
                ALTO = 42;
                iconoVacio = new ImageIcon("resources/littleQuestionMark.jpg");
            }
            case 2 -> {
                tamaño = 20;
                ANCHO = 30;
                ALTO = 30;
                iconoVacio = new ImageIcon("resources/littleQuestionMark.jpg");
            }     
    }
        logicArray = new int[tamaño][tamaño];
        int fila = random.nextInt(tamaño - 1);
        int columna = random.nextInt(tamaño - 1);
        logicArray[fila][columna] = 1;
        logicArray[fila][columna + 1] = 2;
        logicArray[fila + 1][columna] = 3;
        logicArray[fila + 1][columna + 1] = 4;
    }
    
    public void generarCuadros() {
        buttonArray = new JButton[tamaño][tamaño];
        for(int f = 0; f < tamaño; f++) {
            for(int c = 0; c < tamaño; c++) {
                buttonArray[f][c] = new JButton(iconoVacio);
                jPanel1.add(buttonArray[f][c]);
                buttonArray[f][c].setBackground(null);
                buttonArray[f][c].setBounds(c * ANCHO, f * ALTO, ANCHO, ALTO);
                buttonArray[f][c].setActionCommand(f + "," + c);
                buttonArray[f][c].addActionListener((java.awt.event.ActionEvent evt) -> {
                    clickSobreBoton(evt);
                });
                    }
                
                }
    }
    public void clickSobreBoton(java.awt.event.ActionEvent evt) {
        JButton boton = (JButton) evt.getSource();
        String identificadorBoton = boton.getActionCommand();
        // separa el string del action comand para obtener fila y columnas
        int f = 
          Integer.parseInt(identificadorBoton.substring(0,identificadorBoton.indexOf(",")));
        int c = 
          Integer.parseInt(identificadorBoton.substring(1+identificadorBoton.indexOf(","), identificadorBoton.length()));
        
        int confirmacion = 1;
        confirmacion = JOptionPane.showConfirmDialog(rootPane, "¿Desea explotar la bomba?", "Explotar bomba", JOptionPane.YES_NO_OPTION);
        System.out.println(confirmacion);
        if(confirmacion == 0) {
            explotarBomba(f, c);
        }
    }
    
    public void explotarBomba(int f, int c) {
        if (current.getType() == "simple") {
            revisarBoton(f, c);
        }
        else if(current.getType() == "doble") {
            if(f+1 == tamaño && c == 0) {
                revisarBoton(f, c);
                revisarBoton(f, c+1);
                revisarBoton(f-1, c+1);
                revisarBoton(f-1, c);
            }
            else if(c == 0) {
                revisarBoton(f, c);
                revisarBoton(f, c+1);
                revisarBoton(f+1, c+1);
                revisarBoton(f+1, c);
            }
            else if(c+1 == tamaño && f+1 == tamaño) {
                revisarBoton(f, c);
                revisarBoton(f, c-1);
                revisarBoton(f-1, c-1);
                revisarBoton(f-1, c); }
            else if(c+1 == tamaño) {
                revisarBoton(f, c);
                revisarBoton(f, c-1);
                revisarBoton(f+1, c-1);
                revisarBoton(f-1, c);
            }
            else if(f == 0) {
                revisarBoton(f, c);
                revisarBoton(f, c+1);
                revisarBoton(f+1, c+1);
                revisarBoton(f+1, c);
            }
            
            else if(f+1 == tamaño) {
                revisarBoton(f, c);
                revisarBoton(f, c-1);
                revisarBoton(f-1, c-1);
                revisarBoton(f-1, c);
            }
            else {
                int opcion = random.nextInt(4);
                switch(opcion) {
                    case 0 -> {
                        revisarBoton(f, c);
                        revisarBoton(f, c+1);
                        revisarBoton(f+1, c+1);
                        revisarBoton(f+1, c);
                    }
                    case 1 -> {
                        revisarBoton(f, c);
                        revisarBoton(f, c+1);
                        revisarBoton(f-1, c+1);
                        revisarBoton(f-1, c);
                    }
                    case 2 -> {
                        revisarBoton(f, c);
                        revisarBoton(f, c-1);
                        revisarBoton(f-1, c-1);
                        revisarBoton(f-1, c);
                    }
                    case 3 -> {
                        revisarBoton(f, c);
                        revisarBoton(f, c-1);
                        revisarBoton(f-1, c-1);
                        revisarBoton(f-1, c);
                    }
                }
            }
        }
        
        else if(current.getType() == "cruz") {
            if((f == 0 && c == 0) || (f+1 == tamaño && c == 0) || (f == 0 && c+1 == tamaño) || (c+1 == tamaño && f+1 == tamaño)) {
                JOptionPane.showMessageDialog(this, "La bomba de cruz no se puede explotar en las esquinas");
                return;
            }
            
            else if(c == 0) {
                revisarBoton(f, c);
                revisarBoton(f , c+1);
                revisarBoton(f, c+2);
                revisarBoton(f-1, c+1);
                revisarBoton(f+1, c+1); 
            }
            
            else if(f == 0) {
                revisarBoton(f, c);
                revisarBoton(f+1 , c -1);
                revisarBoton(f+1, c+1);
                revisarBoton(f+1, c);
                revisarBoton(f+2, c); }
            
            else if(f+1 == tamaño) {
                revisarBoton(f, c);
                revisarBoton(f-1 , c - 1);
                revisarBoton(f-1, c+1);
                revisarBoton(f-1, c);
                revisarBoton(f-2, c); 
            }
            
            else if(c+1 == tamaño) {
                revisarBoton(f, c);
                revisarBoton(f , c-1);
                revisarBoton(f, c-2);
                revisarBoton(f-1, c-1);
                revisarBoton(f+1, c-1); 
            }
            
            else {
                int opcion = random.nextInt(4);
                switch(opcion) {
                    case 0 -> {
                        revisarBoton(f, c);
                        revisarBoton(f , c+1);
                        revisarBoton(f, c+2);
                        revisarBoton(f-1, c+1);
                        revisarBoton(f+1, c+1); 
                    }
                    case 1 -> {
                        revisarBoton(f, c);
                        revisarBoton(f+1 , c -1);
                        revisarBoton(f+1, c+1);
                        revisarBoton(f+1, c);
                        revisarBoton(f+2, c);
                    }
                    case 2 -> {
                        revisarBoton(f, c);
                        revisarBoton(f-1 , c - 1);
                        revisarBoton(f-1, c+1);
                        revisarBoton(f-1, c);
                        revisarBoton(f-2, c); 
                    }
                    case 3 -> {
                        revisarBoton(f, c);
                        revisarBoton(f , c-1);
                        revisarBoton(f, c-2);
                        revisarBoton(f-1, c-1);
                        revisarBoton(f+1, c-1); 
                    }
                }
            }
            
        }
        
        
        
        
        else if(current.getType() == "linea") {
            if(c < 3) {
                revisarBoton(f, c);
                revisarBoton(f, c+1);
                revisarBoton(f, c+2);
                revisarBoton(f, c+3);
            }
            
            else if(c > tamaño - 4) {
                revisarBoton(f, c);
                revisarBoton(f, c-1);
                revisarBoton(f, c-2);
                revisarBoton(f, c-3);
            }
            
            else if(f < 3) {
                revisarBoton(f, c);
                revisarBoton(f + 1, c);
                revisarBoton(f + 2, c);
                revisarBoton(f + 3, c);
            }
            
            else if(f > tamaño - 4) {
                revisarBoton(f, c);
                revisarBoton(f - 1, c);
                revisarBoton(f - 2, c);
                revisarBoton(f - 3, c);
            }
            
            else {
                int opcion = random.nextInt(4);
                switch(opcion) {
                    case 0 -> {
                        revisarBoton(f, c);
                        revisarBoton(f, c+1);
                        revisarBoton(f, c+2);
                        revisarBoton(f, c+3);
                    }
                    case 1 -> {
                        revisarBoton(f, c);
                        revisarBoton(f, c-1);
                        revisarBoton(f, c-2);
                        revisarBoton(f, c-3);
                    }
                    case 2 -> {
                        revisarBoton(f, c);
                        revisarBoton(f + 1, c);
                        revisarBoton(f + 2, c);
                        revisarBoton(f + 3, c);
                    }
                    case 3 -> {
                        revisarBoton(f, c);
                        revisarBoton(f - 1, c);
                        revisarBoton(f - 2, c);
                        revisarBoton(f - 3, c);
                    }
                }
            }
           
        }
        bombasRestantes--;
        revisarGane();
        restantesText.setText("Bombas restantes: " + bombasRestantes);
        generarBomba();
    }
    
    public void revisarBoton(int f, int c) {
        ImageIcon iconoEstrella;
       if(logicArray[f][c] == 1 || logicArray[f][c] == 2 || logicArray[f][c] == 3 || logicArray[f][c] == 4) {
           if(logicArray[f][c] == 1)  {
                iconoEstrella = new ImageIcon("resources/star1.jpg"); }
           else if(logicArray[f][c] == 2) {
               iconoEstrella = new ImageIcon("resources/star2.jpg"); }
           else if(logicArray[f][c] == 3) {
               iconoEstrella = new ImageIcon("resources/star3.jpg"); }
           else {
               iconoEstrella = new ImageIcon("resources/star4.jpg"); }
           
           buttonArray[f][c].setBackground(Color.black);
           buttonArray[f][c].setIcon(iconoEstrella);
           
            }
            
            else {
                buttonArray[f][c].setVisible(false);
            }
    }
    
    public void generarBomba() {
        if (bombasRestantes < 1) {
            gameOver();
        }
         current = bombas.get(probabilidad.get(random.nextInt(6)));
         bombasText.setText("Bomba actual: " + current.getType());
    }
    
    public void revisarGane() {
        int encontradas = 0;
        for(int f = 0; f < tamaño; f++) {
            for(int c = 0; c < tamaño; c++) {
                if (buttonArray[f][c].getBackground()== Color.black) {
                    encontradas++;
                }
            }
        }
        if(encontradas == 4) {
            JOptionPane.showMessageDialog(this, "Ha ganado");
            tablero.gameEnded(1);
            this.dispose();
        }
    }
    
    public void gameOver() {
        JOptionPane.showMessageDialog(this, "Ha perdido :(");
        tablero.gameEnded(0);
        this.dispose();
    }
    
    public void print() {
        for(int f = 0; f < tamaño; f++) {
            for(int c = 0; c < tamaño; c++) {
                System.out.print(logicArray[f][c] + " ");
                
            }
            System.out.println("");
        }
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
        Title1 = new javax.swing.JLabel();
        restantesText = new javax.swing.JLabel();
        bombasText = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 925, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 913, Short.MAX_VALUE)
        );

        Title1.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        Title1.setText("Bomber Mario");

        restantesText.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        restantesText.setText("Bombas restantes: 7");

        bombasText.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        bombasText.setText("Bombas restantes: 7");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(Title1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(restantesText, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(bombasText, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Title1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(restantesText, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bombasText, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
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
            java.util.logging.Logger.getLogger(BomberMario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BomberMario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BomberMario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BomberMario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BomberMario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Title1;
    private javax.swing.JLabel bombasText;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel restantesText;
    // End of variables declaration//GEN-END:variables
}
