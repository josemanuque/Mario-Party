/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Progra;

/**
 *
 * @author Usuario
 */
public class Punto {
    private int x;
    private int y;
    
    public Punto(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public boolean equals(Punto p) {
        if(p.getX() == x || p.getY() == y)
            return true;
        return false;
    }
    
    public String toString() {
        String xString = String.valueOf(x);
        String yString = String.valueOf(y);
        return "(" + xString + ", " + yString + ")";
    }
            
}
