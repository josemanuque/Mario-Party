/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Progra;

import java.awt.Image;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;




/**
 *
 * @author jmque
 */
public class GuessWho extends javax.swing.JFrame {
    public final int DIMENSIONES = 10;
    public final int DIMENSION_B = 50;
    private ImageIcon im;
    private int personaje;
    public Tablero.Tablero tablero;
    private String[] nombresPersonajes = {
        "Mario", "Luigi", "Bowser", "Peach", "Daisy", 
        "Donkey Kong", "Diddy Kong", "Yoshi", "Toad", "Shy Guy",
        "Wario", "Waluigi", "Koopa", "Floruga", "Bullet" 
    };
    JLabel imageField;
    /**
     * Creates new form GuessWho
     */
    public GuessWho() {
        initComponents();
        generarCuadros();
        setImage();
        generarOpcionBotones();
    }
    
    JButton[][] buttonArray = new JButton[DIMENSIONES][DIMENSIONES];
    JButton[] characterOptionsArray = new JButton[15];
    int[][] logicArray = new int[DIMENSIONES][DIMENSIONES];
    ImageIcon iconoVacio = new ImageIcon("resources/questionMark.jpg");
    
    public void generarCuadros(){
        for (int i = 0; i < DIMENSIONES; i++){
            for (int j = 0; j < DIMENSIONES; j++){
                buttonArray[i][j] = new JButton(iconoVacio);
                jPanel1.add(buttonArray[i][j]);
                buttonArray[i][j].setBounds(j * DIMENSION_B, i * DIMENSION_B, DIMENSION_B, DIMENSION_B);
                buttonArray[i][j].setActionCommand(i + ", " + j);
//                buttonArray[i][j].addActionListener(new ActionListener() {
//                public void actionPerformed(java.awt.event.ActionEvent evt) {
//                    clickSobreTablero(evt);
//                    
//                }
//                });
            }
        }
        deleteRandomButtons();
    }
    public void clickSobreTablero(java.awt.event.ActionEvent evt)
    {
        // obtiene el boton 
        JButton botonTemp = (JButton)evt.getSource();
        // obtiene el i,j de action command del boton
        String identificadorBoton = botonTemp.getActionCommand();
        // separa el string del action comand para obtener columnas
        int selectedPlayer = 
          Integer.parseInt(identificadorBoton);

        if (selectedPlayer == personaje){
            JOptionPane.showMessageDialog(this, "Ha Ganado!!!");
            tablero.gameEnded(1);
            this.dispose();
        }
        else{
            JOptionPane.showMessageDialog(this, "Ha perdido.");
            tablero.gameEnded(0);
            this.dispose();
        }
        
    }
    

    public void setImage(){

        setRandomImage();
        im = new ImageIcon(im.getImage().getScaledInstance(500, 500, Image.SCALE_DEFAULT));
        imageField = new JLabel();
        imageField.setIcon(im);
        jPanel1.add(imageField);
        imageField.setBounds(0, 0, 500, 500);

    }
    
    public void deleteRandomButtons(){
        int randomQuantity = new Random().nextInt(5) + 4; // cantidad de botones a eliminar
        int randomFila;
        int randomColumna;
        for (int i = 0; i < randomQuantity; i++){
            randomFila = new Random().nextInt(DIMENSIONES);
            randomColumna = new Random().nextInt(DIMENSIONES);
            jPanel1.remove(buttonArray[randomFila][randomColumna]);
            jPanel1.repaint();
        }
    }
    
    
    public void generarOpcionBotones(){
        for (int i = 0; i < 15; i++){
            characterOptionsArray[i] = new JButton(nombresPersonajes[i]);
            characterOptionsArray[i].setActionCommand((i + 1) + "" );
            pnlCharacters.add(characterOptionsArray[i]);
            characterOptionsArray[i].setBounds(0, i * 40, 200, 40);
            characterOptionsArray[i].addActionListener(new ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    clickSobreTablero(evt);
                }
                });
        }
    }
    /*
    1. mario
    2. luigi
    3. bowser
    4. peach
    5. daisy
    6. donkeyKong
    7. diddyKong
    8. yoshi
    9. toad
    10. shyGuy
    11. wario
    12. waluigi
    13. koopa
    14. floruga
    15. bullet
    */
//    
    
    public void setRandomImage(){
        
        int randomNum = new Random().nextInt(12) + 1;
        personaje = randomNum;
        //int randomNum = 15;
        switch (randomNum){
            case 1->{
                im = new ImageIcon("resources/GWImages/mario.png");
            }
            case 2->{
                im = new ImageIcon("resources/GWImages/luigi.png");
            }
            case 3->{
                im = new ImageIcon("resources/GWImages/bowser.jpg");
            }
            case 4->{
                im = new ImageIcon("resources/GWImages/peach.png");
            }
            case 5->{
                im = new ImageIcon("resources/GWImages/daisy.png");
            }
            case 6->{
                im = new ImageIcon("resources/GWImages/donkeyKong.png");
            }
            case 7->{
                im = new ImageIcon("resources/GWImages/diddyKong.png");
            }
            case 8->{
                im = new ImageIcon("resources/GWImages/yoshi.jpg");
            }
            case 9->{
                im = new ImageIcon("resources/GWImages/toad.jpg");
            }
            case 10->{
                im = new ImageIcon("resources/GWImages/shyGuy.png");
            }
            case 11->{
                im = new ImageIcon("resources/GWImages/wario.png");
            }
            case 12->{
                im = new ImageIcon("resources/GWImages/waluigi.png");
            }
            case 13->{
                im = new ImageIcon("resources/GWImages/koopa.png");
            }
            case 14->{
                im = new ImageIcon("resources/GWImages/floruga.png");
            }
            case 15->{
                im = new ImageIcon("resources/GWImages/bullet.png");
            }
            
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
        pnlCharacters = new javax.swing.JPanel();
        lblCharactersTitle = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 498, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 498, Short.MAX_VALUE)
        );

        pnlCharacters.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout pnlCharactersLayout = new javax.swing.GroupLayout(pnlCharacters);
        pnlCharacters.setLayout(pnlCharactersLayout);
        pnlCharactersLayout.setHorizontalGroup(
            pnlCharactersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 198, Short.MAX_VALUE)
        );
        pnlCharactersLayout.setVerticalGroup(
            pnlCharactersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 598, Short.MAX_VALUE)
        );

        lblCharactersTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblCharactersTitle.setText("Character");

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblTitle.setText("Guess Who?");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 89, Short.MAX_VALUE)
                .addComponent(pnlCharacters, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(135, 135, 135)
                .addComponent(lblTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblCharactersTitle)
                .addGap(107, 107, 107))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCharactersTitle)
                            .addComponent(lblTitle))
                        .addGap(18, 18, 18)
                        .addComponent(pnlCharacters, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(24, Short.MAX_VALUE))
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
            java.util.logging.Logger.getLogger(GuessWho.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GuessWho.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GuessWho.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GuessWho.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GuessWho().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblCharactersTitle;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnlCharacters;
    // End of variables declaration//GEN-END:variables
}
