package com.sandor.threads;

import com.sandor.ArduinoConnector;
import com.sandor.UltraSpaceSortingMachine;

public class SerialReader extends Thread{
    public String lastMessage = "";
    private ArduinoConnector arduinoConnector;

    public SerialReader(){
        arduinoConnector = UltraSpaceSortingMachine.arduinoConnector;
    }

    public void run(){
        String message = "";
        while(true){
            //Do
            message = arduinoConnector.readString();
            if(!message.equals("")){
                System.out.println("Message recieved: " + message);
                lastMessage = message;
            }

            //Wait for * milisec
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
