package com.sandor.hardware;

import com.sandor.ArduinoConnector;

public class SorteerRobot {
    ArduinoConnector arduinoConnector = new ArduinoConnector();
    //Pos 1 = ball loading, Pos 2 = scanning position, Pos 3 = release ball
    public void moveGate(int pos){
        if(pos == 1){
            arduinoConnector.sendSerialString("gate_load");
        }
        else if (pos == 2){
            arduinoConnector.sendSerialString("gate_scanpos");
        }
        else if (pos == 3){
            arduinoConnector.sendSerialString("gate_release");
        }
    }
}
