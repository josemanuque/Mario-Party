/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Progra;

import Progra.sopa;

/**
 *
 * @author Usuario
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        sopa sopa = new sopa();
        System.out.println(sopa.getTamaño());
        sopa.generarMatriz();
        System.out.println(sopa.getHorizontal() + " - " + sopa.getVertical() + " - " + sopa.getDiag1() + " - " + sopa.getDiag2());
        sopa.print();
    }
    
}
