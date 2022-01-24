/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Progra;

import Progra.sopaFrame;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public class SopaTimeThread extends Thread {
    private sopaFrame sopaFrame;
    private int minutes = 2;
    private int seconds = 0;
    
    public SopaTimeThread(sopaFrame sopaFrame) {
        this.sopaFrame = sopaFrame;
        
    }
    
    
    @Override
    public void run() {
        while(true) {
            try {
                sleep(1000);
                if(seconds == 0) {
                    if(minutes == 0) {
                        sopaFrame.gameOver();
                        break;
                }
                    else {
                    minutes--;
                    seconds = 59; }
                }
                String time = minutes + ":" + setNiceTime(seconds);
                sopaFrame.setTime(time);
             
                seconds--;
            } catch (InterruptedException ex) {
                Logger.getLogger(SopaTimeThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    public String setNiceTime(int number){
        if (number < 10)
            return "0" + number;
        return "" + number;
    }
}

