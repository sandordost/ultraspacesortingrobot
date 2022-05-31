package com.sandor.hardware;

import com.sandor.ArduinoConnector;
import com.sandor.UltraSpaceSortingMachine;

import static java.lang.Thread.sleep;

public class SorteerRobot {
    ArduinoConnector arduinoConnector;

    public SorteerRobot(){
        arduinoConnector = UltraSpaceSortingMachine.arduinoConnector;
    }

    //Pos 1 = ball loading, Pos 2 = scanning position, Pos 3 = release ball
    public void moveGate(int pos){
        if(pos == 1){
            arduinoConnector.sendSerialString("gate0_load", ArduinoConnector.sorteerPort);
        }
        else if (pos == 2){
            arduinoConnector.sendSerialString("gate0_scanpos", ArduinoConnector.sorteerPort);
        }
        else if (pos == 3){
            arduinoConnector.sendSerialString("gate0_release", ArduinoConnector.sorteerPort);
        }
    }
}
