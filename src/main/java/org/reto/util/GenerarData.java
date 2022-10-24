package org.reto.util;

import java.util.Random;

public class GenerarData {
    public GenerarData() {
    }
    String data;
    Random rnd = new Random();
    public String generarData(){
        data = "";
        for (int i = 0; i < 10; i++) {
            int y = rnd.nextInt(2);
            if(y==0){
                data +=(char)(rnd.nextInt(26)+65);
            }else {
                data +=(char)(rnd.nextInt(9)+48);
            }
        }
        return data;
    }
}
