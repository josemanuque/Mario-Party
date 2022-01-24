/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Progra;

import Progra.SopaTimeThread;
import static java.lang.Thread.sleep;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import Progra.collectTheCoins;

/**
 *
 * @author Usuario
 */
public class CollectCoinsTime extends Thread {
    private collectTheCoins game;
    private int seconds;
    Random random;
    
    public CollectCoinsTime(collectTheCoins game) {
        this.game = game;
        random = new Random();
        int index = random.nextInt(3);
        if(index == 0) {
            seconds = 30;
        }
        if(index == 1) {
            seconds = 45; 
        }
        if(index == 2)
            seconds = 60;
    }
    
    
    @Override
    public void run() {
        while(true) {
            try {
                sleep(1000);
                if(seconds == 0) {
                   game.gameOver();
                   break;
                }

                String time = setNiceTime(seconds);
                game.setTime(time);
             
                seconds--;
            }
                catch (InterruptedException ex) {
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

