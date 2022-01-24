/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Progra;

import Server.Client;
import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
public class Gato extends javax.swing.JFrame {
    int DIMENSIONES = 3;
    JButton[][] tableroLabels = new JButton[DIMENSIONES][DIMENSIONES];
    int[][] tableroLogico = new int[DIMENSIONES][DIMENSIONES];
    ImageIcon iconoVacio = new ImageIcon("resources/cvacio.GIF");
    ImageIcon iconoEquiz = new ImageIcon(getClass().getResource("cequiz.GIF"));
    ImageIcon iconoCirculo = new ImageIcon(getClass().getResource("ccirculo.GIF"));
    Client cliente;
    int turno = 1;
    int localPlayerNumber;
    int mainPlayer;
    int notMainplayer;
    /**
     * Creates new form Gato
     */
    public Gato() {
        initComponents();
        generarTablero();
    }
    
    void generarTablero()
    {
        for(int i=0;i<DIMENSIONES;i++)
        {
            for(int j=0;j<DIMENSIONES;j++)
            {
                // coloca imagen a todos vacio
                tableroLabels[i][j] = new JButton(iconoVacio);
                jPanel1.add(tableroLabels[i][j]);
                tableroLabels[i][j].setBounds(100+50*i, 100+50*j, 50, 50);
                // coloca el comand como i , j 
                tableroLabels[i][j].setActionCommand(i+","+j);//i+","+j
                tableroLabels[i][j].setVisible(true);
                tableroLabels[i][j].addMouseListener(new MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                        
                    clickSobreTablero(evt);
                    
                }
                });
                // en logico indica estado en disponible
                tableroLogico[i][j]=0;
            }
        }
    }
    
    // reiniciar el juego es poner todo como en un inicio
    public void reiniciarJuego()
    {
        turno=1;
        for(int i=0;i<DIMENSIONES;i++)
        {
            for(int j=0;j<DIMENSIONES;j++)
            {
                tableroLabels[i][j].setIcon(iconoVacio);
                tableroLogico[i][j]=0;
            }
        }
    }
    
    // este metodo es la respuesta del cliente al clic del enemigo
    public void marcar(int columna, int fila)
    {
        // marca el tablero con num de jugador
        tableroLogico[columna][fila]=turno;
        // si soy el 1, marco con o que es el 2, sino con X
        // pues es el turno del enemigo que estoy marcando
        if (localPlayerNumber == mainPlayer)
            tableroLabels[columna][fila].setIcon(iconoCirculo);
        else
            tableroLabels[columna][fila].setIcon(iconoEquiz);
            
        // pregunta si gano el enemigo
            if(haGanado())
            {
                JOptionPane.showMessageDialog(null, "Ha ganado el jugador "+turno);
                if(mainPlayer == localPlayerNumber) {
                    if(localPlayerNumber == turno) {
                        try {
                            cliente.salida.writeInt(10);
                        } catch (IOException ex) {
                            Logger.getLogger(Gato.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    else{
                        try {
                            cliente.salida.writeInt(11);
                        } catch (IOException ex) {
                            Logger.getLogger(Gato.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                this.setVisible(false);
                return;
            }    
            
            if(empate()) {
                JOptionPane.showMessageDialog(null, "Empate");
                if(mainPlayer == localPlayerNumber) {
                    try {
                        cliente.salida.writeInt(11);
                    } catch (IOException ex) {
                        Logger.getLogger(Gato.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                this.setVisible(false);
            }
        turno=localPlayerNumber;
        jLabel1.setText("Turno del Jugador "+turno);
        
    }
    
    public void bomba(int col, int fila)
    {
        JOptionPane.showMessageDialog(this, "Generar bombas y enviarlas una " +
                "a una al enemigo ("+col+","+fila+")");
    }

    public void setCliente(Client cliente) {
        this.cliente = cliente;
    }

    public void setLocalPlayerNumber(int localPlayerNumber) {
        this.localPlayerNumber = localPlayerNumber;
    }

    public void setMainPlayer(int mainPlayer) {
        this.mainPlayer = mainPlayer;
        turno = mainPlayer;
        jLabel1.setText("Turno del Jugador "+turno);
    }

    public void setNotMainplayer(int notMainplayer) {
        this.notMainplayer = notMainplayer;
    }
    
    
    
    
    
    public void clickSobreTablero(java.awt.event.MouseEvent evt)
    {
        // obtiene el boton 
        JButton botonTemp = (JButton)evt.getComponent();
        // obtiene el i,j de action command del boton
        String identificadorBoton = botonTemp.getActionCommand();
        
        // separa el string del action comand para obtener columnas
        int columna = 
          Integer.parseInt(identificadorBoton.substring(0,identificadorBoton.indexOf(",")));
        int fila = 
          Integer.parseInt(identificadorBoton.substring(1+identificadorBoton.indexOf(",")));
        
        // si ya se disparo entonces nada
        if(tableroLogico[columna][fila]!=0)
            return;
        
        // si es mi turno continua, si no return
        if (localPlayerNumber != turno)
            return;
        
        // como es turno del cliente marca el logico con su numero
        tableroLogico[columna][fila]=turno;
        // si era el jugador 1 marca con x y cambia el turno a jugador 2
        if (localPlayerNumber == mainPlayer)
        {
            
            tableroLabels[columna][fila].setIcon(iconoEquiz);
            turno=notMainplayer;
        }
        else
        {
            // si era jugador 3, marca circulo y turno jugador 1
            tableroLabels[columna][fila].setIcon(iconoCirculo);
            turno=mainPlayer;
        }
        // muestra el turno del jugador
         jLabel1.setText("Turno del jugador" + turno);
        
        try {
            // como el cliente dio clic debe enviar al servidor las coordenadas
            // el servidor se las pasara al thread cliente para que este
            // las muestre (haga el marcar)
            // envia las coordenadas
            cliente.salida.writeInt(12);
            cliente.salida.writeInt(columna);
            cliente.salida.writeInt(fila);
        } catch (IOException ex) {
            
        }
         
        // si gano el jugador 1 lo indica
        if(haGanado())
            {
                JOptionPane.showMessageDialog(null, "Ha ganado el jugador "+localPlayerNumber);
                if(mainPlayer == localPlayerNumber) {

                        try {
                            cliente.salida.writeInt(10);
                        } catch (IOException ex) {
                            Logger.getLogger(Gato.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    
                   
                    }
                
                this.setVisible(false);
                return;
            }    
            
            if(empate()) {
                JOptionPane.showMessageDialog(null, "Empate");
                if(mainPlayer == localPlayerNumber) {
                    try {
                        cliente.salida.writeInt(11);
                    } catch (IOException ex) {
                        Logger.getLogger(Gato.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                this.setVisible(false);
            }  
    }
    
    
    boolean haGanado()
    {
        
        //Ganó en las filas
        for(int i=0;i<3;i++)
        {
        if ((tableroLogico[i][0]==tableroLogico[i][1])
                &&(tableroLogico[i][1]==tableroLogico[i][2])
                && !(tableroLogico[i][0]==0))
        {
            return true;
        }
        }
        
        //Gano en las columnas
        for(int i=0;i<3;i++)
        {
        if ((tableroLogico[0][i]==tableroLogico[1][i])
                &&(tableroLogico[1][i]==tableroLogico[2][i])
                && !(tableroLogico[0][i]==0))
        {
            return true;
        }
        }
        //Verificar diagonal 1
        if ((tableroLogico[0][0]==tableroLogico[1][1])
                &&(tableroLogico[1][1]==tableroLogico[2][2])
                && !(tableroLogico[0][0]==0))
            return true;
        
        //Verificar diagonal 2
        if ((tableroLogico[2][0]==tableroLogico[1][1])
                &&(tableroLogico[1][1]==tableroLogico[0][2])
                && !(tableroLogico[2][0]==0))
            return true;
        
        return false;
    }
    
    boolean empate() { //Recorre el tablero a ver si queda alguna casilla sin marcar
        for(int f = 0; f < DIMENSIONES; f++ ) {
            for(int c = 0; c < DIMENSIONES; c++) {
                if(tableroLogico[f][c] == 0) {
                    return false;
                }
            }
        }
        return true;
    }
    
    // set el nombre del enemigo
    public void setEnemigo(String enem)
    {
        etiquetaRival.setText("rival: "+enem);
    }
    
    public void setNick(String nick) {
        this.setTitle(nick);
    }
    
                       

//private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
//    JOptionPane.showMessageDialog(this, localPlayerNumber+","+turno);
//}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        etiquetaTitulo = new javax.swing.JLabel();
        etiquetaRival = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        etiquetaTitulo.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        etiquetaTitulo.setText("Gato");

        etiquetaRival.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        etiquetaRival.setText("rival: ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 318, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel1.setText("Turno del jugador 1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(163, 163, 163)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(122, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(etiquetaTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(etiquetaRival, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(etiquetaTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(etiquetaRival, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
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
            java.util.logging.Logger.getLogger(Gato.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Gato.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Gato.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Gato.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Gato().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel etiquetaRival;
    private javax.swing.JLabel etiquetaTitulo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
