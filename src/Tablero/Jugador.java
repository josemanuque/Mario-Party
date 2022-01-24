/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tablero;

import javax.swing.JButton;

/**
 *
 * @author diemo
 */
public class Jugador {
    private int index;
    private String nombre;
    private JButton refButton;
    private int yOffset;
    public boolean move = true;

    public Jugador(int index, String nombre, JButton refButton, int yOffset) {
        this.index = index;
        this.nombre = nombre;
        this.refButton = refButton;
        this.yOffset = yOffset;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public JButton getRefButton() {
        return refButton;
    }

    public void setRefButton(JButton refButton) {
        this.refButton = refButton;
    }

    public int getyOffset() {
        return yOffset;
    }
    
    
    
    
    
}
